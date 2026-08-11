/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import oracle.jdbc.driver.json.BufferPoolImpl;
import oracle.jdbc.driver.json.JakartaParserWrapper;
import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.JsonpParserWrapper;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.parser.JsonTokenizer.JsonToken;
import oracle.jdbc.driver.json.tree.OracleJsonArrayImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;
import oracle.jdbc.driver.json.tree.OracleJsonObjectImpl;
import oracle.jdbc.driver.json.tree.OracleJsonStringImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonStructure;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonValue.OracleJsonType;

/**
 * JSON parser implementation. 
 *
 * @author Jitendra Kotamraju
 * @author Kin-man Chung
 */
public class JsonParserImpl implements OracleJsonParser {

  private Context currentContext = new NoneContext();
  private Event currentEvent;

  private final Stack stack = new Stack();
  private final JsonTokenizer tokenizer;

  public JsonParserImpl(Reader reader, BufferPoolImpl bufferPool) {
    tokenizer = new JsonTokenizer(reader, bufferPool);
  }

  public JsonParserImpl(InputStream in, BufferPoolImpl bufferPool) {
    UnicodeDetectingInputStream uin = new UnicodeDetectingInputStream(in);
    tokenizer = new JsonTokenizer(new InputStreamReader(uin, uin.getCharset()), bufferPool);
  }

  private ExceptionFactory factory() {
    return OracleJsonExceptions.ORACLE_FACTORY;
  }
  
  @Override
  public String getString() {
    if (currentEvent == Event.KEY_NAME || currentEvent == Event.VALUE_STRING
        || currentEvent == Event.VALUE_DECIMAL) {
      return tokenizer.getValue();
    }
    throw OracleJsonExceptions.PARSER_GETSTRING_ERR.create(factory(), currentEvent);
  }

  @Override
  public boolean isIntegralNumber() {
    if (currentEvent != Event.VALUE_DECIMAL) {
      throw OracleJsonExceptions.PARSER_ISINTEGRAL_ERR.create(factory(), currentEvent);
    }
    return tokenizer.isIntegral();
  }

  @Override
  public int getInt() {
    if (currentEvent != Event.VALUE_DECIMAL) {
      throw OracleJsonExceptions.PARSER_GETBIGDECIMAL_ERR.create(factory(), currentEvent);
    }
    return tokenizer.getInt();
  }

  /** This should be optimized */
  @Override
  public double getDouble() {
    return getBigDecimal().doubleValue();
  }

  /** This should be optimized */
  @Override
  public float getFloat() {
    return getBigDecimal().floatValue();
  }

  @Override
  public BigInteger getBigInteger() {
    return getBigDecimal().toBigInteger();
  }

  boolean isDefinitelyInt() {
    return tokenizer.isDefinitelyInt();
  }

  boolean isDefinitelyLong() {
    return tokenizer.isDefinitelyLong();
  }

  @Override
  public long getLong() {
    if (currentEvent != Event.VALUE_DECIMAL) {
      throw OracleJsonExceptions.PARSER_GETLONG_ERR.create(factory(), currentEvent);
    }
    return tokenizer.getLong();
  }

  @Override
  public BigDecimal getBigDecimal() {
    if (currentEvent != Event.VALUE_DECIMAL) {
      throw OracleJsonExceptions.PARSER_GETBIGDECIMAL_ERR.create(factory(), currentEvent);
    }
    return tokenizer.getBigDecimal();
  }

  @Override
  public OracleJsonArray getArray() {
    if (currentEvent != Event.START_ARRAY) {
      throw OracleJsonExceptions.PARSER_GETARRAY_ERR.create(factory(), currentEvent);
    }
    return getValue().asJsonArray();
  }

  @Override
  public OracleJsonObject getObject() {
    if (currentEvent != Event.START_OBJECT) {
      throw OracleJsonExceptions.PARSER_GETOBJECT_ERR.create(factory(), currentEvent);
    }
    return getValue().asJsonObject();
  }

  @Override
  public OracleJsonValue getValue() {
    ArrayDeque<OracleJsonStructure> stack = new ArrayDeque<OracleJsonStructure>(4);
    OracleJsonValue value = null;
    String currentKey = null;
    if (currentEvent == null || currentEvent == Event.END_ARRAY || currentEvent == Event.END_OBJECT) {
      throw OracleJsonExceptions.BAD_PARSER_STATE_VALUE.create(factory());
    }
    while (true) {
      switch(currentEvent) {
      case END_ARRAY:
      case END_OBJECT: {
        value = stack.pop();
        break;
      }
      case KEY_NAME: {
        currentKey = getString();
        next();
        continue;
      }
      case START_ARRAY: {
        value = new OracleJsonArrayImpl();
        addValue(value, stack, currentKey);
        stack.push(value.asJsonArray());
        break;
      }
      case START_OBJECT: {
        value = new OracleJsonObjectImpl();
        addValue(value, stack, currentKey);
        stack.push(value.asJsonObject());
        break;
      }
      case VALUE_DECIMAL: {
        if (isDefinitelyInt())
          value = new OracleJsonDecimalImpl(getInt(), null);
        else if (isDefinitelyLong())
          value = new OracleJsonDecimalImpl(getLong(), null);
        else 
          value = new OracleJsonDecimalImpl(getBigDecimal());
        
        addValue(value, stack, currentKey);
        break;
      }
      case VALUE_STRING: {
        value = new OracleJsonStringImpl(getString());
        addValue(value, stack, currentKey);
        break;
      }
      case VALUE_TRUE: {
        value = OracleJsonValue.TRUE;
        addValue(value, stack, currentKey);
        break;
      }
      case VALUE_FALSE: {
        value = OracleJsonValue.FALSE;
        addValue(value, stack, currentKey);
        break;
      }
      case VALUE_NULL: {
        value = OracleJsonValue.NULL;
        addValue(value, stack, currentKey);
        break;
      }
      case VALUE_BINARY: 
      case VALUE_DATE:
      case VALUE_DOUBLE:
      case VALUE_FLOAT:
      case VALUE_INTERVALDS:
      case VALUE_INTERVALYM:
      case VALUE_TIMESTAMP:
      case VALUE_VECTOR:
      default:
        throw new IllegalStateException();
      }
      currentKey = null;
      if (stack.isEmpty()) {
        break;
      }
      next();
    } 
      
    return value;
  }

  private static void addValue(OracleJsonValue v, ArrayDeque<OracleJsonStructure> stack, String currentKey) {
    if (stack.isEmpty()) {
      return;
    }
    OracleJsonStructure parent = stack.peek();
    if (parent.getOracleJsonType() == OracleJsonType.OBJECT) {
      parent.asJsonObject().put(currentKey, v);
    } else {
      parent.asJsonArray().add(v);
    }
  }
    
 
  @Override
  public void skipArray() {
    if (currentEvent == Event.START_ARRAY) {
      currentContext.skip();
      currentContext = stack.pop();
      currentEvent = Event.END_ARRAY;
    }
  }

  @Override
  public void skipObject() {
    if (currentEvent == Event.START_OBJECT) {
      currentContext.skip();
      currentContext = stack.pop();
      currentEvent = Event.END_OBJECT;
    }
  }

  public JsonLocationImpl getLocation() {
    return tokenizer.getLocation();
  }

  public JsonLocationImpl getLastCharLocation() {
    return tokenizer.getLastCharLocation();
  }

  @Override
  public boolean hasNext() {
    if (stack.isEmpty() && (currentEvent != null && currentEvent.compareTo(Event.KEY_NAME) > 0)) {
      JsonToken token = tokenizer.nextToken();
      if (token != JsonToken.EOF) {
        throw OracleJsonExceptions.PARSER_EXPECTED_EOF.create(factory(), currentEvent);
      }
      return false;
    } else if (!stack.isEmpty() && !tokenizer.hasNextToken()) {
      currentEvent = currentContext.getNextEvent();
      return false;
    }
    return true;
  }

  @Override
  public Event next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    return currentEvent = currentContext.getNextEvent();
  }

  @Override
  public void close() {
    try {
      tokenizer.close();
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(factory(), currentEvent);
    }
  }

  // Using the optimized stack impl as we don't require other things
  // like iterator etc.
  private static final class Stack {
    private Context head;

    private void push(Context context) {
      context.next = head;
      head = context;
    }

    private Context pop() {
      if (head == null) {
        throw new NoSuchElementException();
      }
      Context temp = head;
      head = head.next;
      return temp;
    }

    private boolean isEmpty() {
      return head == null;
    }
  }

  private abstract class Context {
    Context next;
    abstract Event getNextEvent();
    abstract void skip();
  }

  private final class NoneContext extends Context {
    @Override
    public Event getNextEvent() {
      // Handle 1. {   2. [   3. value
      JsonToken token = tokenizer.nextToken();
      if (token == JsonToken.CURLYOPEN) {
        stack.push(currentContext);
        currentContext = new ObjectContext();
        return Event.START_OBJECT;
      } else if (token == JsonToken.SQUAREOPEN) {
        stack.push(currentContext);
        currentContext = new ArrayContext();
        return Event.START_ARRAY;
      } else if (token.isValue()) {
        return token.getEvent();
      }
      throw parsingException(token, "[CURLYOPEN, SQUAREOPEN, STRING, NUMBER, TRUE, FALSE, NULL]");
    }

    @Override
    void skip() {
      // no-op
    }
  }

  private RuntimeException parsingException(JsonToken token, String expectedTokens) {
    JsonLocationImpl location = getLastCharLocation();
    return OracleJsonExceptions.PARSER_INVALID_TOKEN.create(factory(), token, 
      location.getLineNumber(), location.getColumnNumber(), expectedTokens);
  }

  private final class ObjectContext extends Context {
    private boolean firstValue = true;

    /*
     * Some more things could be optimized. For example, instead
     * tokenizer.nextToken(), one could use tokenizer.matchColonToken() to
     * match ':'. That might optimize a bit, but will fragment nextToken().
     * I think the current one is more readable.
     *
     */
    @Override
    public Event getNextEvent() {
      // Handle 1. }   2. name:value   3. ,name:value
      JsonToken token = tokenizer.nextToken();
      if (token == JsonToken.EOF) {
        switch (currentEvent) {
          case START_OBJECT:
            throw parsingException(token, "[STRING, CURLYCLOSE]");
          case KEY_NAME:
            throw parsingException(token, "[COLON]");
          default:
            throw parsingException(token, "[COMMA, CURLYCLOSE]");
        }
      } else if (currentEvent == Event.KEY_NAME) {
        // Handle 1. :value
        if (token != JsonToken.COLON) {
          throw parsingException(token, "[COLON]");
        }
        token = tokenizer.nextToken();
        if (token.isValue()) {
          return token.getEvent();
        } else if (token == JsonToken.CURLYOPEN) {
          stack.push(currentContext);
          currentContext = new ObjectContext();
          return Event.START_OBJECT;
        } else if (token == JsonToken.SQUAREOPEN) {
          stack.push(currentContext);
          currentContext = new ArrayContext();
          return Event.START_ARRAY;
        }
        throw parsingException(token, "[CURLYOPEN, SQUAREOPEN, STRING, NUMBER, TRUE, FALSE, NULL]");
      } else {
        // Handle 1. }   2. name   3. ,name
        if (token == JsonToken.CURLYCLOSE) {
          currentContext = stack.pop();
          return Event.END_OBJECT;
        }
        if (firstValue) {
          firstValue = false;
        } else {
          if (token != JsonToken.COMMA) {
            throw parsingException(token, "[COMMA]");
          }
          token = tokenizer.nextToken();
        }
        if (token == JsonToken.STRING) {
          return Event.KEY_NAME;
        }
        throw parsingException(token, "[STRING]");
      }
    }

    @Override
    void skip() {
      JsonToken token;
      int depth = 1;
      do {
        token = tokenizer.nextToken();
        switch (token) {
          case CURLYCLOSE:
            depth--;
            break;
          case CURLYOPEN:
            depth++;
            break;
          default:
            break;
        }
      } while (!(token == JsonToken.CURLYCLOSE && depth == 0));
    }
  }

  private final class ArrayContext extends Context {
    private boolean firstValue = true;

    // Handle 1. ]   2. value   3. ,value
    @Override
    public Event getNextEvent() {
      JsonToken token = tokenizer.nextToken();
      if (token == JsonToken.EOF) {
        switch (currentEvent) {
          case START_ARRAY:
            throw parsingException(token, "[CURLYOPEN, SQUAREOPEN, STRING, NUMBER, TRUE, FALSE, NULL]");
          default:
            throw parsingException(token, "[COMMA, CURLYCLOSE]");
        }
      }
      if (token == JsonToken.SQUARECLOSE) {
        currentContext = stack.pop();
        return Event.END_ARRAY;
      }
      if (firstValue) {
        firstValue = false;
      } else {
        if (token != JsonToken.COMMA) {
          throw parsingException(token, "[COMMA]");
        }
        token = tokenizer.nextToken();
      }
      if (token.isValue()) {
        return token.getEvent();
      } else if (token == JsonToken.CURLYOPEN) {
        stack.push(currentContext);
        currentContext = new ObjectContext();
        return Event.START_OBJECT;
      } else if (token == JsonToken.SQUAREOPEN) {
        stack.push(currentContext);
        currentContext = new ArrayContext();
        return Event.START_ARRAY;
      }
      throw parsingException(token, "[CURLYOPEN, SQUAREOPEN, STRING, NUMBER, TRUE, FALSE, NULL]");
    }

    @Override
    void skip() {
      JsonToken token;
      int depth = 1;
      do {
        token = tokenizer.nextToken();
        switch (token) {
          case SQUARECLOSE:
            depth--;
            break;
          case SQUAREOPEN:
            depth++;
            break;
          default:
            break;
        }
      } while (!(token == JsonToken.SQUARECLOSE && depth == 0));
    }
  }

  @SuppressWarnings({ "unchecked", "resource" })
  @Override
  public <T> T wrap(Class<T> wrapper) {
    try {
      if (Jsonp.isJakartaJsonStream(wrapper)) {
        return wrapper.cast((T)new JakartaParserWrapper(this));
      } else {
        return wrapper.cast((T)new JsonpParserWrapper(this));
      }
    } catch (ClassCastException e) {
      throw OracleJsonExceptions.BAD_WRAP.create(factory(), e, wrapper.getName());
    }
  }

  @Override
  public Period getPeriod() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Duration getDuration() {
    throw new UnsupportedOperationException();
  }

  @Override
  public byte[] getBytes() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OffsetDateTime getOffsetDateTime() {
    throw new UnsupportedOperationException();
  }

  @Override
  public LocalDateTime getLocalDateTime() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void getBytes(OutputStream out) {
    throw new UnsupportedOperationException();
  }

}

// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Wrapper;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.stream.JsonLocation;
import javax.json.stream.JsonParser;

import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonParser;
import oracle.jdbc.driver.json.binary.OsonParserImpl;

/**
 *  @author  jspiegel
 *  @since   release specific (what release of product did this appear in)
 */
public class JsonpParserWrapper implements Wrapper, JsonParser {

  OracleJsonParser wrapped;

  public JsonpParserWrapper(OracleJsonParser wrapped) {
    this.wrapped = wrapped;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T unwrap(Class<T> wrapped) throws SQLException {
    if (wrapped.isInstance(this.wrapped)) {
      return (T)this.wrapped;
    }
    throw new SQLException(OracleJsonExceptions.BAD_WRAP.create(
        OracleJsonExceptions.ORACLE_FACTORY, wrapped.getName()).getMessage());
  }
  
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return iface.isInstance(wrapped);
  }

  @Override
  public void close() {
    try {
      wrapped.close();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }

  @Override
  public BigDecimal getBigDecimal() {
    return wrapped.getBigDecimal();
  }

  @Override
  public int getInt() {
    return wrapped.getInt();
  }

  @Override
  public JsonLocation getLocation() {
    JsonLocation NO_LOCATION = new JsonLocation() {
      @Override
      public long getColumnNumber() {
        return -1;
      }

      @Override
      public long getLineNumber() {
        return -1;
      }

      @Override
      public long getStreamOffset() {
        if (wrapped instanceof OsonParserImpl)
          return ((OsonParserImpl) wrapped).getStreamOffset();
        return -1;
      }
    };
    return NO_LOCATION;
  }

  @Override
  public long getLong() {
    return wrapped.getLong();
  }

  @Override
  public String getString() {
    return wrapped.getString();
  }

  @Override
  public boolean hasNext() {
    try {
      return wrapped.hasNext();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }

  @Override
  public boolean isIntegralNumber() {
    return wrapped.isIntegralNumber();
  }

  @Override
  public Event next() {
    
    OracleJsonParser.Event event;
    try {
      event = wrapped.next();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }

    switch (event) {
    case END_ARRAY:
      return JsonParser.Event.END_ARRAY;
    case END_OBJECT:
      return JsonParser.Event.END_OBJECT;
    case KEY_NAME:
      return JsonParser.Event.KEY_NAME;
    case START_ARRAY:
      return JsonParser.Event.START_ARRAY;
    case START_OBJECT:
      return JsonParser.Event.START_OBJECT;
    case VALUE_BINARY:
    case VALUE_TIMESTAMP:
    case VALUE_TIMESTAMPTZ:
    case VALUE_DATE:
    case VALUE_INTERVALDS:
    case VALUE_INTERVALYM:
    case VALUE_STRING:
      return JsonParser.Event.VALUE_STRING;
    case VALUE_DOUBLE:
    case VALUE_FLOAT:
    case VALUE_DECIMAL:
      return JsonParser.Event.VALUE_NUMBER;
    case VALUE_FALSE:
      return JsonParser.Event.VALUE_FALSE;
    case VALUE_TRUE:
      return JsonParser.Event.VALUE_TRUE;
    case VALUE_NULL:
      return JsonParser.Event.VALUE_NULL;
    case VALUE_VECTOR:
    default:
      throw new UnsupportedOperationException(event.toString());
    }
  }
  
  //@Override JSONP 1.1
  public JsonValue getValue() {
    try {
      return wrapped.getValue().wrap(JsonValue.class);
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  //@Override JSONP 1.1
  public JsonObject getObject() {
    try {
      return wrapped.getObject().wrap(JsonObject.class);
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  //@Override JSONP 1.1
  public JsonArray getArray() {
    try {
      return wrapped.getArray().wrap(JsonArray.class);
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  //@Override JSONP 1.1
  public void skipObject() {
    try {
      wrapped.skipObject();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  //@Override JSONP 1.1
  public void skipArray() {
    try {
      wrapped.skipArray();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }

}

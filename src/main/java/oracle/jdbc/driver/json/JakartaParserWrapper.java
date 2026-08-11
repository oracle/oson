// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Wrapper;

import jakarta.json.JsonArray;
import jakarta.json.JsonException;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonLocation;
import jakarta.json.stream.JsonParser;
import oracle.jdbc.driver.json.binary.OsonParserImpl;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonParser;

/**
 * Adapter for JSON-P 2.0 (jakarta.json packaging)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpParserWrapper.  
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpParserWrapper as well.
 *       
 */
public class JakartaParserWrapper implements Wrapper, JsonParser {

  OracleJsonParser wrapped;
  
  /** Used for generating multiple events from a VECTOR */
  JsonArray currentVector;
  int vectorPosition;

  public JakartaParserWrapper(OracleJsonParser wrapped) {
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
    if (currentVector != null && vectorPosition < currentVector.size()) {
      return ((JsonNumber)currentVector.get(vectorPosition)).bigDecimalValue();
    }
    return wrapped.getBigDecimal();
  }

  @Override
  public int getInt() {
    if (currentVector != null && vectorPosition < currentVector.size()) {
      return ((JsonNumber)currentVector.get(vectorPosition)).intValue();
    }
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
    if (currentVector != null && vectorPosition < currentVector.size()) {
      return ((JsonNumber)currentVector.get(vectorPosition)).longValue();
    }
    return wrapped.getLong();
  }

  @Override
  public String getString() {
    return wrapped.getString();
  }

  @Override
  public boolean hasNext() {
    try {
      return currentVector != null || wrapped.hasNext();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }

  @Override
  public boolean isIntegralNumber() {
    if (currentVector != null && vectorPosition < currentVector.size()) {
      return ((JsonNumber)currentVector.get(vectorPosition)).isIntegral();
    }
    return wrapped.isIntegralNumber();
  }

  @Override
  public Event next() {
    if (currentVector != null) {
      if (vectorPosition < currentVector.size()-1) {
        vectorPosition++;
        return JsonParser.Event.VALUE_NUMBER;
      } else if (vectorPosition == currentVector.size()-1){
        vectorPosition++;
        return JsonParser.Event.END_ARRAY;
      } else {
        currentVector = null;
        // fall through
      }
    }
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
    case VALUE_VECTOR:
      this.currentVector = wrapped.getValue().asJsonVector().wrap(JsonArray.class);
      this.vectorPosition = -1;
      return JsonParser.Event.START_ARRAY;
    case VALUE_NULL:
    default:
      return JsonParser.Event.VALUE_NULL;
    }
  }
  
  @Override
  public JsonValue getValue() {
    try {
      if (currentVector != null) {
        if (vectorPosition == -1)
          return currentVector;
        else if (vectorPosition < currentVector.size())
          return currentVector.get(vectorPosition);
        else
          throw new IllegalStateException();
      }
      return wrapped.getValue().wrap(JsonValue.class);
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  @Override
  public JsonObject getObject() {
    try {
      return wrapped.getObject().wrap(JsonObject.class);
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  @Override
  public JsonArray getArray() {
    if (currentVector != null) {
      if (vectorPosition == -1) {
        return currentVector;
      }
      throw new IllegalStateException();
    }
    try {
      return wrapped.getArray().wrap(JsonArray.class);
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  @Override
  public void skipObject() {
    try {
      wrapped.skipObject();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }
  
  @Override
  public void skipArray() {
    try {
      wrapped.skipArray();
    } catch (OracleJsonException e) {
      throw new JsonException(e.getMessage(), e);
    }
  }

}

// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Map;

import jakarta.json.JsonArray;
import jakarta.json.JsonException;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerationException;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;

import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonGenerationException;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonValue;

/**
 * Adapter for JSON-P 2.0 (jakarta.json packaging)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpGeneratorWrapper.  
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpGeneratorWrapper as well.
 * 
 */
public class JakartaGeneratorWrapper implements Wrapper, JsonGenerator {

  OracleJsonGenerator wrapped;
  
  public JakartaGeneratorWrapper(OracleJsonGenerator wrapped) { 
    this.wrapped = wrapped;
  }
  
  @Override
  public void close() {
    try {
      wrapped.close();
    } catch (OracleJsonException e) {
      throw translate(e);
    }
  }

  @Override
  public void flush() {
    try {
      wrapped.flush();
    } catch (OracleJsonException e) {
      throw translate(e);
    }
  }

  public JsonGenerator writeKey(String key) {
    try {
      wrapped.writeKey(key);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }
    
  private JsonException translate(OracleJsonException e) {
    if (e instanceof OracleJsonGenerationException) {
      return new JsonGenerationException(e.getMessage(), e);
    } else {
      return new JsonException(e.getMessage(), e);
    }
  }

  @Override
  public JsonGenerator write(String key, JsonValue arg) {
    try {
      wrapped.writeKey(key);
      write(arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(JsonValue arg) {
    if (arg instanceof Wrapper) {
      /* Try to preserve underlying type system if one of ours */ 
      Wrapper w = (Wrapper)arg;
      try {
        if (w.isWrapperFor(OracleJsonValue.class)) {
          wrapped.write(w.unwrap(OracleJsonValue.class));
          return this;
        }
      } catch (SQLException e) {
        throw new IllegalStateException(e);
      }
    }
    writeJsonValue(arg);
    return this; 
  }
  
  private void writeJsonValue(JsonValue value) {
    switch (value.getValueType()) { 
    case OBJECT: {
      JsonObject obj = (JsonObject)value;
      writeStartObject();
      for(Map.Entry<String, JsonValue> entry : obj.entrySet()) {
        writeKey(entry.getKey());
        writeJsonValue(entry.getValue());
      }
      writeEnd();
      break;
    }
    case ARRAY: {
      JsonArray arr = (JsonArray)value;
      writeStartArray();
      for (JsonValue v : arr) {
        writeJsonValue(v);
      }
      writeEnd();
      break;
    }
    case STRING: {
      JsonString str = (JsonString)value;
      write(str.getString());
      break;
    }
    case NUMBER: {
      JsonNumber num = (JsonNumber)value;
      writeOraNum(num.bigDecimalValue());
      break;
    }
    case TRUE: {
      write(true);
      break;
    }
    case FALSE: {
      write(false);
      break;
    }
    case NULL:
      writeNull();
      break;
    }
  }  
  
  public void writeJsonParser(Object p) {
    JsonParser parser = (JsonParser)p;
    int depth = 0;
    while (parser.hasNext()) {
      switch (parser.next()) {
      case END_ARRAY:
        writeEnd();
        depth--;
        break;
      case END_OBJECT:
        writeEnd();
        depth--;
        break;
      case KEY_NAME:
        writeKey(parser.getString());
        break;
      case START_ARRAY:
        writeStartArray();
        depth++;
        break;
      case START_OBJECT:
        writeStartObject();
        depth++;
        break;
      case VALUE_FALSE:
        write(false);
        break;
      case VALUE_NULL:
        writeNull();
        break;
      case VALUE_NUMBER:
        writeOraNum(parser.getBigDecimal());
        break;
      case VALUE_STRING:
        write(parser.getString());
        break;
      case VALUE_TRUE:
        write(true);
        break;
      }
      if (depth <= 0) {
        break;
      }
    }
  }
  
  @Override
  public JsonGenerator write(String arg) {
    try {
      wrapped.write(arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(BigDecimal arg) {
    try {
      wrapped.write(arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(BigInteger arg) {
    wrapped.write(arg);
    return this;
  }

  @Override
  public JsonGenerator write(int arg) {
    try {
      wrapped.write(arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(long arg) {
    try {
      wrapped.write(arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(double arg) {
    try {
      wrapped.write(arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }      
    return this;
  }

  @Override
  public JsonGenerator write(boolean arg) {
    try {
      wrapped.write(arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(String key, String arg) {
    try {
      wrapped.write(key, arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(String key, BigInteger arg) {
    try {
      wrapped.write(key, arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(String key, BigDecimal arg) {
    try {
      wrapped.write(key, arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(String key, int arg) {
    try {
      wrapped.write(key, arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(String key, long arg) {
    try {
      wrapped.write(key, arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(String key, double arg) {
    try {
      wrapped.write(key, arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator write(String key, boolean arg) {
    try {
      wrapped.write(key, arg);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator writeEnd() {
    try {
      wrapped.writeEnd();
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator writeNull() {
    try {
      wrapped.writeNull();
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator writeNull(String key) {
    try {
      wrapped.writeNull(key);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator writeStartArray() {
    try {
      wrapped.writeStartArray();
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator writeStartArray(String key) {
    try {
      wrapped.writeStartArray(key);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator writeStartObject() {
    try {
      wrapped.writeStartObject();
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }

  @Override
  public JsonGenerator writeStartObject(String key) {
    try {
      wrapped.writeStartObject(key);
    } catch (OracleJsonException e) {
      throw translate(e);
    }
    return this;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public <T> T unwrap(Class<T> wrapped) throws SQLException {
    if (wrapped.isInstance(this.wrapped)) {
      return (T)this.wrapped;
    }
    throw new SQLException(OracleJsonExceptions.BAD_WRAP.create(OracleJsonExceptions.ORACLE_FACTORY, wrapped.getName()).getMessage());
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return iface.isInstance(wrapped);
  }

  private void writeOraNum(BigDecimal value) {
    try {
      wrapped.write(new OracleJsonDecimalImpl(value));
    } catch (OracleJsonException e) {
      throw translate(e);
    }
  }  
}

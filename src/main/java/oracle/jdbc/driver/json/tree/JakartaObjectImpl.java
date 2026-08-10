/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonValue;

/**
 * Adapter for JSON-P 2.0 (jakarta.json)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpObjectImpl.  
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpObjectImpl as well.
 *       
 */
public class JakartaObjectImpl extends AbstractMap<String, JsonValue> implements Wrapper, JsonObject {

  OracleJsonObject wrapped;
  
  public JakartaObjectImpl(OracleJsonObject wrapped) {
    this.wrapped = wrapped;
  }
  
  @Override
  public ValueType getValueType() {
    return ValueType.OBJECT;
  }

  @Override
  public boolean getBoolean(String name) {
    return wrapped.getBoolean(name);
  }

  @Override
  public boolean getBoolean(String name, boolean value) {
    return wrapped.getBoolean(name, value);
  }

  @Override
  public int getInt(String name) {
    return wrapped.getInt(name);
  }

  @Override
  public int getInt(String name, int value) {
    return wrapped.getInt(name, value);
  }

  @Override
  public JsonArray getJsonArray(String name) {
    return wrapped.get(name).asJsonArray().wrap(JsonArray.class);
  }

  @Override
  public JsonNumber getJsonNumber(String name) {
    return wrapped.get(name).wrap(JsonNumber.class);
  }

  @Override
  public JsonObject getJsonObject(String name) {
    return wrapped.get(name).asJsonObject().wrap(JsonObject.class);
  }

  @Override
  public JsonString getJsonString(String name) {
    return wrapped.get(name).wrap(JsonString.class);
  }

  @Override
  public String getString(String name) {
    return ((JsonString)get(name)).getString();
  }

  @Override
  public String getString(String name, String defaultValue) {
    JsonValue v = get(name);
    if (v == null || v.getValueType() != ValueType.STRING) {
      return defaultValue;
    }
    return ((JsonString)v).getString();
  }

  @Override
  public boolean isNull(String name) {
    return wrapped.isNull(name);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    try {
      return iface.cast(wrapped);
    } catch (ClassCastException e) {
      throw new SQLException(e.getMessage(), e);
    }
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return iface.isAssignableFrom(OracleJsonObject.class);
  }

  @Override
  public Set<Entry<String, JsonValue>> entrySet() {
    return new AbstractSet<Entry<String, JsonValue>>() {

      @Override
      public Iterator<Entry<String, JsonValue>> iterator() {
        return new Iterator<Entry<String, JsonValue>>() {
          
          Iterator<Entry<String, OracleJsonValue>> iter = wrapped.entrySet().iterator();
          
          @Override
          public boolean hasNext() {
            return iter.hasNext();
          }

          @Override
          public Entry<String, JsonValue> next() {
            final Entry<String, OracleJsonValue> oentry = iter.next();
            return new Entry<String, JsonValue>() {

              @Override
              public String getKey() {
                return oentry.getKey();
              }

              @Override
              public JsonValue getValue() {
                return oentry.getValue().wrap(JsonValue.class);
              }

              @Override
              public JsonValue setValue(JsonValue value) {
                throw new UnsupportedOperationException();
              }
              
            };
          }
          
        };
      }

      @Override
      public int size() {
        return wrapped.size();
      }
    };
  }

  @Override 
  public JsonValue get(Object name) {
    OracleJsonValue v = wrapped.get(name);
    if (v == null) {
      return null;
    }
    return v.wrap(JsonValue.class);
  }

  @Override
  public boolean containsKey(Object name) {
    return wrapped.containsKey(name);
  }
  
}

// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.AbstractList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonValue;

/**
 * @since   release specific (what release of product did this appear in)
 */
public class JsonpArrayImpl extends AbstractList<JsonValue> implements JsonArray, Wrapper {

  OracleJsonArrayImpl wrapped;
  
  JsonpArrayImpl(OracleJsonArrayImpl wrapped) {
    this.wrapped = wrapped;
  }
  
  @Override
  public ValueType getValueType() {
    return JsonValue.ValueType.ARRAY;
  }

  @Override
  public int size() {
    return wrapped.size();
  }

  @Override
  public boolean isEmpty() {
    return wrapped.isEmpty();
  }

  @Override
  public JsonValue get(int index) {
    OracleJsonValue v = wrapped.get(index);
    return v.wrap(JsonValue.class);
  }

  @Override
  public List<JsonValue> subList(int fromIndex, int toIndex) {
    return wrapped.subList(fromIndex, toIndex).wrap(JsonArray.class);
  }
  
  @Override
  public boolean getBoolean(int i) {
    return wrapped.getBoolean(i);
  }

  @Override
  public boolean getBoolean(int i, boolean d) {
    if (i < 0 || i >= wrapped.size()) {
      return d;
    }
    OracleJsonValue v = wrapped.get(i);
    if (OracleJsonValue.TRUE.equals(v)) {
      return true;
    } else if (OracleJsonValue.FALSE.equals(v)) {
      return false;
    } else {
      return d;
    }
  }

  @Override
  public int getInt(int i) {
    return wrapped.getInt(i);
  }

  @Override
  public int getInt(int i, int d) {
    if (i < 0 || i >= wrapped.size()) {
      return d;
    }
    OracleJsonValue v = wrapped.get(i);
    if (v instanceof OracleJsonNumber) {
      return ((OracleJsonNumber)v).intValue();
    }
    return d;
  }

  @Override
  public JsonArray getJsonArray(int i) {
    return wrapped.get(i).asJsonArray().wrap(JsonArray.class);
  }

  @Override
  public JsonNumber getJsonNumber(int i) {
    return ((OracleJsonNumber)wrapped.get(i)).wrap(JsonNumber.class);
  }

  @Override
  public JsonObject getJsonObject(int i) {
    return wrapped.get(i).asJsonObject().wrap(JsonObject.class);
  }

  @Override
  public JsonString getJsonString(int i) {
    return wrapped.get(i).wrap(JsonString.class);
  }

  @Override
  public <T extends JsonValue> List<T> getValuesAs(Class<T> arg0) {
    return new AbstractList<T>() {
      @SuppressWarnings("unchecked")
      @Override
      public T get(int index) {
        return (T) JsonpArrayImpl.this.get(index);
      }

      @Override
      public int size() {
        return wrapped.size();
      }
      
    };
  }

  @Override
  public String getString(int i) {
    return ((JsonString)get(i)).getString();
  }

  @Override
  public String getString(int i, String d) {
    if (i < 0 || i >= wrapped.size()) {
      return d;
    }
    OracleJsonValue v = wrapped.get(i);
    switch (v.getOracleJsonType()) {
    case BINARY:
    case DATE:
    case INTERVALDS:
    case INTERVALYM:
    case STRING:
    case TIMESTAMP:
      return v.wrap(JsonString.class).getString();
    default:
      return d;
    }
  }

  @Override
  public boolean isNull(int index) {
    return wrapped.isNull(index);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return iface.isAssignableFrom(OracleJsonArray.class);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    try {
      return iface.cast(wrapped);
    } catch (ClassCastException e) {
      throw new SQLException(e.getMessage(), e);
    }
  }
  
}

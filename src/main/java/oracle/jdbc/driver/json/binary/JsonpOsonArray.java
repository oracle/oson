/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;

import oracle.jdbc.driver.json.JsonpGeneratorWrapper;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class JsonpOsonArray extends OsonAbstractArray implements JsonArray, Wrapper {

  public JsonpOsonArray(OsonContext ctx, int pos) {
    super(ctx, pos);
  }
  
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    try {
      return iface.cast(new OsonArrayImpl(new OsonContext(ctx), pos));
    } catch (ClassCastException e) {
      throw new SQLException(e.getMessage(), e);
    }
  }  
  
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return iface.isAssignableFrom(OsonArrayImpl.class);
  }
  
  @Override
  public JsonObject getJsonObject(int i) {
    return (JsonObject) getJsonObjectInternal(getOffsetWithError(i));
  }
  
  @Override
  public JsonArray getJsonArray(int i) {
    return (JsonArray) getArrayInternal(getOffsetWithError(i));
  }
  
  @Override
  public JsonNumber getJsonNumber(int i) {
    return (JsonNumber) getValueInternal(getOffsetWithError(i));
  }
  
  @Override
  public JsonString getJsonString(int i) {
    return (JsonString) get(i);
  }
  
  @Override
  public String getString(int i) {
    return getJsonString(i).getString();
  }
  
  public String getString(int i, String defaultValue) {
    if (i < 0 || i >= size()) {
      return defaultValue;
    }
    JsonValue v = get(i);
    if (v.getValueType() != ValueType.STRING) {
      return defaultValue;
    }
    return ((JsonString)v).getString();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public <T extends JsonValue> List<T> getValuesAs(Class<T> c) {
    return (List<T>)this;
  }

  @Override
  public ValueType getValueType() {
    return ValueType.ARRAY;
  }
  
  @Override
  public Iterator<JsonValue> iterator() {
    return new ValueIter<JsonValue>();
  }  
  
  @Override
  public boolean add(JsonValue e) {
    throw new UnsupportedOperationException();
  }  
  
  @Override
  public boolean addAll(Collection<? extends JsonValue> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int index, Collection<? extends JsonValue> c) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public JsonValue get(int i) {
    return (JsonValue) getInternal(i);
  }

  @Override
  public JsonValue set(int index, JsonValue element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int index, JsonValue element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public JsonValue remove(int index) {
    throw new UnsupportedOperationException();
  } 
  
  @Override
  public ListIterator<JsonValue> listIterator() {
    return listIterator(0);
  }
  
  @Override
  public ListIterator<JsonValue> listIterator(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    return new ListIter<JsonValue>(index);
  }
    
  @Override
  public List<JsonValue> subList(int fromIndex, int toIndex) {
    return super.sublist(fromIndex, toIndex);
  }

  @Override
  public String toString() {
    StringWriter writer = new StringWriter();
    JsonGenerator ser = new JsonpGeneratorWrapper(new JsonSerializerImpl(writer));
    ser.write(this);
    ser.close();
    return writer.toString();
  }



}

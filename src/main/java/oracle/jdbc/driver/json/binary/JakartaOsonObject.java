// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerator;
import oracle.jdbc.driver.json.JakartaGeneratorWrapper;

/**
 * Adapter for JSON-P 2.0 (jakarta.json)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpOsonObject.  
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpOsonObject as well.
 *       
 */
public class JakartaOsonObject extends OsonAbstractObject implements JsonObject, Wrapper {
      
  public JakartaOsonObject(OsonContext ctx) {
    super(ctx);
  }

  public JakartaOsonObject(OsonContext ctx, int pos) {
    super(ctx, pos);
  }
  
  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    try {
      return iface.cast(new OsonObjectImpl(new OsonContext(ctx), pos));
    } catch (ClassCastException e) {
      throw new SQLException(e.getMessage(), e);
    }
  }  
  
  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return iface.isAssignableFrom(OsonObjectImpl.class);
  }
  
  @Override
  public JsonValue get(Object key) {
    return (JsonValue)getInternal(key);
  }
  
  @Override
  public JsonArray getJsonArray(String key) {
    return (JsonArray)getJsonArrayInternal(key);
  }
  
  @Override
  public JsonObject getJsonObject(String key) {
    return (JsonObject)getJsonObjectInternal(key);
  }
  
  @Override
  public JsonNumber getJsonNumber(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset < 0) {
      return null;
    }
    return (JsonNumber)this.getValueInternal(childOffset);
  }
  
  @Override
  public JsonString getJsonString(String key) {
    return (JsonString)get(key);
  }
  
  @Override
  public String getString(String key) {
    return getJsonString(key).getString();
  }
  
  @Override
  public String getString(String key, String defaultValue) {
    JsonValue v = get(key);
    if (v == null) {
      return defaultValue;
    }
    if (v.getValueType() == ValueType.STRING) {
      return ((JsonString)v).getString();
    }
    return defaultValue;
  }
  
  @Override
  public ValueType getValueType() {
    return ValueType.OBJECT;
  }
  
  @Override
  public JsonValue put(String key, JsonValue value) {
    throw new UnsupportedOperationException();
  }
  

  @Override
  public JsonValue remove(Object key) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public void putAll(Map<? extends String, ? extends JsonValue> m) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public Collection<JsonValue> values() {
    return new OsonObjectValues<JsonValue>();
  }
  
  @Override
  public Set<Entry<String, JsonValue>> entrySet() {
    return new OsonEntrySet<JsonValue>();
  }
  
  @Override
  public int hashCode() {
    int result = 0;
    for (Entry<String, JsonValue> e : entrySet()) {
      result += e.hashCode();
    }
    return result;
  }

  @Override
  public String toString() {
    StringWriter writer = new StringWriter();
    JsonGenerator ser = new JakartaGeneratorWrapper(new JsonSerializerImpl(writer));
    ser.write(this);
    ser.close();
    return writer.toString();
  }

}

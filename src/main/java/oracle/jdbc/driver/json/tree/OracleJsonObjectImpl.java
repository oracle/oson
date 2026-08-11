/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.sql.json.OracleJsonDecimal.TargetType;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonValue;


/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonObjectImpl implements OracleJsonObject {

  final Map<String, OracleJsonValue> map;
  
  public OracleJsonObjectImpl() {
    this.map = new LinkedHashMap<String, OracleJsonValue>();
  }
  
  public OracleJsonObjectImpl(OracleJsonObject other) {
    this.map = new HashMap<String, OracleJsonValue>();
    for (Entry<String, OracleJsonValue> e : other.entrySet()) {
      String key = e.getKey();
      OracleJsonValue value = e.getValue();
      switch (value.getOracleJsonType()) {
      case OBJECT:
        map.put(key,  new OracleJsonObjectImpl(value.asJsonObject()));
        break;
      case ARRAY:
        map.put(key,  new OracleJsonArrayImpl(value.asJsonArray()));
        break;
      default:
        // values are always immutable
        map.put(key, value);
        break;
      }
    }
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.OBJECT;
  }
  
  private void checkNull(OracleJsonValue v) {
    if (v == null) {
      throw new NullPointerException();
    }
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  @Override
  public OracleJsonValue get(Object key) {
    return map.get(key);
  }
  
  @Override
  public OracleJsonValue put(String key, OracleJsonValue value) {
    checkNull(value);
    return map.put(key, value);
  }

  @Override
  public OracleJsonValue remove(Object key) {
    return map.remove(key);
  }

  @Override
  public void putAll(Map<? extends String, ? extends OracleJsonValue> m) {
    for (Entry<? extends String, ? extends OracleJsonValue> e : m.entrySet()) {
      checkNull(e.getValue());
      map.put(e.getKey(), e.getValue());
    }
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public Set<String> keySet() {
    return map.keySet();
  }

  @Override
  public Collection<OracleJsonValue> values() {
    return map.values();
  }

  @Override
  public Set<Entry<String, OracleJsonValue>> entrySet() {
    return map.entrySet();
  }

  @Override
  public String getString(String name) {
    return map.get(name).asJsonString().getString();
  }

  @Override
  public String getString(String name, String defaultValue) {
    OracleJsonValue v = map.get(name);
    return v == null || v.getOracleJsonType() != OracleJsonType.STRING ? 
        defaultValue : v.asJsonString().getString(); 
  }

  @Override
  public int getInt(String name) {
    return ((OracleJsonNumber)map.get(name)).intValue();
  }

  @Override
  public int getInt(String name, int defaultValue) {
    OracleJsonValue v = map.get(name);
    return (v instanceof OracleJsonNumber) ? 
        ((OracleJsonNumber)v).intValue() : defaultValue;
  }

  @Override
  public double getDouble(String name) {
    return ((OracleJsonNumber)map.get(name)).doubleValue();
  }
  
  
  @Override
  public long getLong(String key) {
    return ((OracleJsonNumber)map.get(key)).longValue();
  }

  @Override
  public BigDecimal getBigDecimal(String key) {
    return ((OracleJsonNumber)map.get(key)).bigDecimalValue();
  }

  @Override
  public double getDouble(String name, double defaultValue) {
    OracleJsonValue v = map.get(name);
    return v instanceof OracleJsonNumber ? 
        ((OracleJsonNumber)v).doubleValue() : defaultValue;
  }

  @Override
  public long getLong(String key, long defaultValue) {
    OracleJsonValue v = map.get(key);
    return v instanceof OracleJsonNumber ?
        ((OracleJsonNumber)v).longValue() : defaultValue;
  }

  @Override
  public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
    OracleJsonValue v = map.get(key);
    return v instanceof OracleJsonNumber ? 
        ((OracleJsonNumber)v).bigDecimalValue() : defaultValue;
  }

  @Override
  public boolean getBoolean(String name) {
    OracleJsonValue v = map.get(name);
    if (v.equals(OracleJsonValue.TRUE)) {
      return true;
    } else if (v.equals(OracleJsonValue.FALSE)) {
      return false;
    }
    throw new ClassCastException();
  }

  @Override
  public boolean getBoolean(String name, boolean defaultValue) {
    OracleJsonValue v = map.get(name);
    if (v == null) {
      return defaultValue;
    }
    if (v.equals(OracleJsonValue.TRUE)) {
      return true;
    } else if (v.equals(OracleJsonValue.FALSE)) {
      return false;
    } else {
      return defaultValue;
    }
  }

  @Override
  public boolean isNull(String name) {
    return map.get(name).equals(OracleJsonValue.NULL);
  }

  @Override
  public LocalDateTime getLocalDateTime(String key) {
    OracleJsonValue v = map.get(key);
    if (v.getOracleJsonType() == OracleJsonType.DATE) {
      return v.asJsonDate().getLocalDateTime();  
    } else {
      return v.asJsonTimestamp().getLocalDateTime();  
    }
  }

  @Override
  public OffsetDateTime getOffsetDateTime(String key) {
    OracleJsonValue v = map.get(key);
    return v.asJsonTimestampTZ().getOffsetDateTime();
  }
  
  @Override
  public LocalDateTime getLocalDateTime(String key, LocalDateTime defaultValue) {
    OracleJsonValue v = map.get(key);
    if (v == null) {
      return defaultValue;
    }
    if (v.getOracleJsonType() == OracleJsonType.DATE) {
      return v.asJsonDate().getLocalDateTime();
    } else if (v.getOracleJsonType() == OracleJsonType.TIMESTAMP) {
      return v.asJsonTimestamp().getLocalDateTime();
    } else {
      return defaultValue;
    } 
  }

  @Override
  public OffsetDateTime getOffsetDateTime(String key, OffsetDateTime defaultValue) {
    OracleJsonValue v = map.get(key);
    if (v == null) {
      return defaultValue;
    }
    if (v.getOracleJsonType() == OracleJsonType.TIMESTAMPTZ) {
      return v.asJsonTimestampTZ().getOffsetDateTime();
    } 
    return defaultValue;
  }

  @Override
  public byte[] getBytes(String name) {
    return get(name).asJsonBinary().getBytes();
  }

  @Override
  public byte[] getBytes(String name, byte[] defaultValue) {
    OracleJsonValue v = map.get(name);
    if (v == null) {
      return defaultValue;
    } else if (v.getOracleJsonType() == OracleJsonType.BINARY) {
      return v.asJsonBinary().getBytes();
    } else {
      return defaultValue;
    }
  }

  @Override
  public OracleJsonValue put(String name, byte[] value) {
    return map.put(name, new OracleJsonBinaryImpl(value, false));
  }

  @Override
  public OracleJsonValue put(String name, String value) {
    return put(name, new OracleJsonStringImpl(value));
  }

  @Override
  public OracleJsonValue put(String name, int value) {
    return put(name, new OracleJsonDecimalImpl(value, TargetType.INT));
  }
  
  @Override
  public OracleJsonValue put(String name, long value) {
    return put(name, new OracleJsonDecimalImpl(value, TargetType.LONG));
  }

  @Override
  public OracleJsonValue put(String name, BigDecimal value) {
    return put(name, new OracleJsonDecimalImpl(value));
  }

  @Override
  public OracleJsonValue put(String name, double value) {
    return put(name, new OracleJsonDoubleImpl(value));
  }
  
  @Override
  public OracleJsonValue put(String name, boolean value) {
    return put(name, value ? OracleJsonValue.TRUE : OracleJsonValue.FALSE);
  }

  @Override
  public OracleJsonValue putNull(String name) {
    return put(name, OracleJsonValue.NULL);
  }

  @Override
  public OracleJsonValue put(String key, OffsetDateTime value) {
    return put(key, new OracleJsonTimestampTZImpl(value));
  }

  @Override
  public OracleJsonValue put(String key, LocalDateTime value) {
    return put(key, new OracleJsonTimestampImpl(value));
  }
  
  @Override
  public String toString() {
    StringWriter writer = new StringWriter();
    JsonSerializerImpl ser = new JsonSerializerImpl(writer);
    ser.write(this);
    ser.close();
    return writer.toString();
  }
  
  @Override
  public boolean equals(Object other) {
    return this.map.equals(other);
  }
  
  @Override
  public int hashCode() {
    return this.map.hashCode();
  }
  
  @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c))
      return c.cast(new JakartaObjectImpl(this));
    else
      return c.cast(new JsonpObjectImpl(this));
  }

}

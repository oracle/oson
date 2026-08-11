/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.jakarta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonStructure;
import oracle.sql.json.OracleJsonValue;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class JsonpTreeTest extends JsonTestCase {
  
  OracleJsonFactory FACTORY = new OracleJsonFactory();
  
  @SuppressWarnings("unlikely-arg-type")
  public void testObject() throws SQLException {
    OracleJsonObject oobj = FACTORY.createObject();
    oobj.put("hello", "world");
    oobj.put("boolean", true);
    oobj.put("int", 123);
    oobj.put("arr", FACTORY.createArray());
    oobj.put("obj", FACTORY.createObject());
    oobj.put("null", FACTORY.createNull());
    LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime();
    oobj.put("timestamp", FACTORY.createTimestamp(ldt));
    oobj.put("date", FACTORY.createDate(ldt));
    oobj.put("binary", FACTORY.createBinary(new byte[] { 0 }));
    oobj.put("period", FACTORY.createIntervalYM(Period.ofYears(12)));
    oobj.put("duration", FACTORY.createIntervalDS(Duration.ofDays(12)));
    
    JsonObject jobj = oobj.wrap(JsonObject.class);
    
    assertEquals(ValueType.OBJECT, jobj.getValueType());
    assertEquals(true, jobj.getBoolean("boolean"));
    assertEquals(true, jobj.getBoolean("hello", true));
    assertEquals(123, jobj.getInt("int", 456));
    assertEquals(123, jobj.getInt("int"));
    assertEquals(456, jobj.getInt("hello", 456));
    assertEquals("[]", jobj.getJsonArray("arr").toString());
    assertEquals("{}", jobj.getJsonObject("obj").toString());
    assertTrue(jobj.isNull("null"));
    assertFalse(jobj.isNull("arr"));
    assertEquals("1970-01-01T00:00:00", jobj.getString("timestamp"));
    assertEquals("1970-01-01T00:00:00", jobj.getString("date"));
    assertEquals("00", jobj.getString("binary"));
    assertEquals("P12Y", jobj.getString("period"));
    assertEquals("P12D", jobj.getString("duration"));
    
    
    List<JsonValue> values = new ArrayList<JsonValue>();
    assertEquals(jobj.size(), oobj.size());
    Set<Entry<String,JsonValue>> entries = jobj.entrySet();
    for (Entry<String, JsonValue> e : entries) {
      String key = e.getKey();
      assertTrue(jobj.containsKey(key));
      assertTrue(oobj.containsKey(key));
      JsonValue v = e.getValue();
      assertEquals(jobj.get(key), e.getValue());
      values.add(v);
      try {
        e.setValue(null);
        fail();
      } catch (UnsupportedOperationException err) {
        
      }
      if (v != JsonValue.NULL && v != JsonValue.TRUE && v != JsonValue.FALSE) {
        OracleJsonValue ov = ((Wrapper)v).unwrap(OracleJsonValue.class);
        assertTrue(ov.toString(), oobj.containsValue(ov));
      }
      
    }
    assertEquals(values.size(), jobj.size());

    
    
    Wrapper wrapper = (Wrapper)jobj;
    assertTrue(wrapper.isWrapperFor(OracleJsonObject.class));
    assertTrue(wrapper.isWrapperFor(OracleJsonStructure.class));
    assertTrue(wrapper.isWrapperFor(OracleJsonValue.class));
    
    OracleJsonValue oobj2 = wrapper.unwrap(OracleJsonObject.class); 
    assertEquals(oobj, oobj2);
    assertFalse(oobj.equals(jobj));
    
    OracleJsonValue value = wrapper.unwrap(OracleJsonValue.class); 
    assertEquals(oobj, value);
    
    try {
      wrapper.unwrap(java.lang.String.class);
      fail();
    } catch (SQLException e) {
      
    }
  }
  
  public void testArray() throws SQLException {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add("world");
    arr.add(true);
    arr.add(123);
    arr.add(FACTORY.createArray());  // 3
    arr.add(FACTORY.createObject()); // 4
    arr.add(FACTORY.createNull());   // 5
    
    LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime();
    arr.add(FACTORY.createTimestamp(ldt)); // 6
    arr.add(FACTORY.createDate(ldt));
    arr.add(FACTORY.createBinary(new byte[] { 0 }));
    arr.add(FACTORY.createIntervalYM(Period.ofYears(12)));
    arr.add(FACTORY.createIntervalDS(Duration.ofDays(12))); // 10
    arr.add(false); // 11
    arr.add(123.456d); // 12
    arr.add(new BigDecimal("123.456")); // 13
    arr.add(new BigDecimal("123")); // 14
    
    JsonArray jarr = arr.wrap(JsonArray.class);
    assertFalse(jarr.isEmpty());
    assertEquals(jarr.subList(1, 2).get(0), jarr.get(1));
    assertEquals(ValueType.ARRAY, jarr.getValueType());
    assertEquals("world", jarr.getJsonString(0).getString());
    
    assertEquals("foo", jarr.getString(-1, "foo"));
    assertEquals("foo", jarr.getString(100, "foo"));
    assertEquals("world", jarr.getString(0, "foo"));
    assertEquals("foo", jarr.getString(1, "foo"));
    assertEquals("1970-01-01T00:00:00", jarr.getString(6, "foo"));
    
    
    assertEquals(true, jarr.getBoolean(1));
    assertEquals(true, jarr.getBoolean(1, false));
    assertEquals(true, jarr.getBoolean(4, true));
    assertEquals(true, jarr.getBoolean(40, true));
    assertEquals(123, jarr.getInt(2, 456));
    assertEquals(123, jarr.getInt(2));
    assertEquals(456, jarr.getInt(0, 456));    
    
    assertEquals(456, jarr.getInt(-1, 456));    
    assertEquals(456, jarr.getInt(100, 456));    
    assertEquals("[]", jarr.getJsonArray(3).toString());
    assertEquals("{}", jarr.getJsonObject(4).toString());
    assertTrue(jarr.isNull(5));
    assertFalse(jarr.isNull(4));
    assertEquals("1970-01-01T00:00:00", jarr.getString(6));
    assertEquals("1970-01-01T00:00:00", jarr.getString(7));
    assertEquals("00", jarr.getString(8));
    assertEquals("P12Y", jarr.getString(9));
    assertEquals("P12D", jarr.getString(10));
    assertEquals(false, jarr.getBoolean(11, true));
    assertEquals(false, jarr.getBoolean(99, false));
    assertEquals(false, jarr.getBoolean(-99, false));
    assertEquals(123.456d, jarr.getJsonNumber(12).doubleValue());
    assertEquals(new BigDecimal("123.456"), jarr.getJsonNumber(13).bigDecimalValue());
    assertEquals(BigInteger.valueOf(123), jarr.getJsonNumber(14).bigIntegerValue());
    
    Wrapper w = (Wrapper)jarr;
    assertTrue(w.isWrapperFor(OracleJsonValue.class));
    assertTrue(w.isWrapperFor(OracleJsonStructure.class));
    assertTrue(w.isWrapperFor(OracleJsonArray.class));
    assertFalse(w.isWrapperFor(String.class));
    
    try {
      w.unwrap(String.class);
    } catch (SQLException e) {
      
    }
    
  }
  
  public void testArrGetValuesAs() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add("a");
    arr.add("b");
    JsonArray jarr = arr.wrap(JsonArray.class);
    List<JsonString> strings = jarr.getValuesAs(JsonString.class);
    assertEquals(2, strings.size());
    assertEquals("a", strings.get(0).getString());
    assertEquals("b", strings.get(1).getString());
  }
  
}

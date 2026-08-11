/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.jakarta;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonConstants;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaNumberImpl;
import oracle.sql.NUMBER;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonObject;
/**
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public class PrimitivesTest extends JsonTestCase {
    
    public void testString() {
        JsonString osonString = ((JsonArray)getValue("[\"hello\"]")).getJsonString(0);
        JsonString jsonpString = Json.createArrayBuilder().add("hello").build().getJsonString(0);
        assertEquals(jsonpString.getString(), osonString.getString());
        assertEquals(jsonpString.toString(), osonString.toString());
        assertEquals(jsonpString.getChars(), osonString.getChars());
        assertEquals(jsonpString, osonString);
        assertEquals(jsonpString.hashCode(), osonString.hashCode());
    }
    
    public void testNumbers() {
        assertTrue(getNumber("123").isIntegral());
        assertEquals(123, getNumber("123").intValue());
        assertEquals(123, getNumber("123").intValueExact());
        assertEquals(12356789123l, getNumber("12356789123").longValue());
        assertEquals(12356789123l, getNumber("12356789123").longValueExact());
        assertEquals(BigInteger.valueOf(12356789123l), getNumber("12356789123").bigIntegerValue());
        assertEquals(BigInteger.valueOf(12356789123l), getNumber("12356789123").bigIntegerValueExact());
        assertEquals(12.23d, getNumber("12.23").doubleValue());
        assertEquals(12.23f, ((JakartaNumberImpl)getNumber("12.23")).floatValue());
        assertEquals("12.23", getNumber("12.23").toString());
        
    }
    
    private JsonNumber getNumber(String number) {
        return ((JsonArray)getValue("[" + number + "]")).getJsonNumber(0);
    }
    
    private JsonValue getValue(String jzn) {
        return new OracleJsonFactory().createJsonTextValue(new StringReader(jzn)).wrap(JsonValue.class);
    }
    
    
    public void testJsonpNumberMappings() {
      OracleJsonFactory f = new OracleJsonFactory();
      OracleJsonObject o = f.createObject();
      o.put("n", f.createDecimal(123));
      o.put("d", f.createDouble(12.3d));
      o.put("f", f.createFloat(1));

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
      gen.write(o);
      gen.close();
      byte[] oson = baos.toByteArray();
      OracleJsonObject o2 = f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();
      
      doNumberMappingTest(o2.wrap(JsonObject.class));
      doNumberMappingTest(o.wrap(JsonObject.class));
    }
    
    private void doNumberMappingTest(JsonObject o) {
      assertEquals(123, o.getInt("n"));
      assertEquals(12, o.getInt("d"));
      assertEquals(1, o.getInt("f"));

      assertEquals(123, o.getJsonNumber("n").intValue());
      assertEquals(12.3d, o.getJsonNumber("d").doubleValue());
      assertEquals(1d, o.getJsonNumber("f").doubleValue());
      
    }
    
    public void testJsonpNumberMappingsArr() {
      OracleJsonFactory f = new OracleJsonFactory();
      
      OracleJsonArray a = f.createArray();
      a.add(f.createDecimal(123));
      a.add(f.createDouble(12.3d));
      a.add(f.createFloat(1));

      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
      gen.write(a);
      gen.close();
      byte[] oson = baos.toByteArray();
      OracleJsonArray a2 = f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonArray();
      
      doNumberMappingTestArr(a2.wrap(JsonArray.class));
      doNumberMappingTestArr(a.wrap(JsonArray.class));
    }

    private void doNumberMappingTestArr(JsonArray a) {
      assertEquals(123, a.getInt(0));
      assertEquals(12, a.getInt(1));
      assertEquals(1, a.getInt(2));
      
      assertEquals(123, a.getJsonNumber(0).intValue());
      assertEquals(12.3d, a.getJsonNumber(1).doubleValue());
      assertEquals(1d, a.getJsonNumber(2).doubleValue());
    }
    
    public void testJsonpStringMappings() {
      OracleJsonFactory f = new OracleJsonFactory();
      OracleJsonObject o = f.createObject();
      o.put("s", f.createString("str"));
      LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime();
      o.put("d", f.createDate(ldt));
      o.put("t", f.createTimestamp(ldt));
      o.put("b", f.createBinary(new byte[] { 1, 2, 3 }));
      o.put("id", f.createIntervalDS(Duration.ofDays(2)));
      o.put("iy", f.createIntervalYM(Period.ofYears(12)));
      o.put("i", f.createDecimal(123));
      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
      gen.write(o);
      gen.close();
      byte[] oson = baos.toByteArray();
      OracleJsonObject o2 = f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();
      
      doStringMappingTest(o2.wrap(JsonObject.class));
      doStringMappingTest(o.wrap(JsonObject.class));
    }

    private void doStringMappingTest(JsonObject o) {
      assertEquals("str", o.getString("s"));
      assertEquals("str", o.getString("s", null));
      assertEquals("str", o.getJsonString("s").getString());
      
      assertEquals("1970-01-01T00:00:00", o.getString("d"));
      assertEquals("1970-01-01T00:00:00", o.getString("d", null));
      assertEquals("1970-01-01T00:00:00", o.getJsonString("d").getString());
      
      assertEquals("1970-01-01T00:00:00", o.getString("t"));
      assertEquals("1970-01-01T00:00:00", o.getString("t", null));
      assertEquals("1970-01-01T00:00:00", o.getJsonString("t").getString());
      
      assertEquals("010203", o.getString("b"));
      assertEquals("010203", o.getString("b", null));
      assertEquals("010203", o.getJsonString("b").getString());
      
      assertEquals("P2D", o.getString("id"));
      assertEquals("P2D", o.getString("id", null));
      assertEquals("P2D", o.getJsonString("id").getString());
      
      assertEquals("P12Y", o.getString("iy"));
      assertEquals("P12Y", o.getString("iy", null));
      assertEquals("P12Y", o.getJsonString("iy").getString());
      
      assertEquals("def", o.getString("not there", "def"));
      assertEquals("def", o.getString("i", "def"));
    }
    
    public void testOracleJsonNumber() {
      OracleJsonFactory f = new OracleJsonFactory();
      assertEquals(123, f.createDecimal(123).asJsonNumber().intValue());
      assertEquals(123.456, f.createDouble(123.456).asJsonNumber().doubleValue());
    }
    
    public void testJsonpStringMappingsArr() {
      OracleJsonFactory f = new OracleJsonFactory();
      
      OracleJsonArray a = f.createArray();
      a.add(f.createString("str"));
      LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime();
      a.add(f.createDate(ldt));
      a.add(f.createTimestamp(ldt));
      a.add(f.createBinary(new byte[] { 1, 2, 3 }));
      a.add(f.createIntervalDS(Duration.ofDays(2)));
      a.add(f.createIntervalYM(Period.ofYears(12)));
      a.add(f.createDecimal(123));
      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
      gen.write(a);
      gen.close();
      byte[] oson = baos.toByteArray();
      OracleJsonArray a2 = f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonArray();
      
      doStringMappingTestArr(a2.wrap(JsonArray.class));
      doStringMappingTestArr(a.wrap(JsonArray.class));
    }
    
    public void testLargeStrings() {
      doLargeStringTest(OsonConstants.UB1_MAXSZ+10);
      doLargeStringTest(OsonConstants.UB2_MAXSZ+10);
    }

    private void doLargeStringTest(int sz) {
      OracleJsonFactory f = new OracleJsonFactory();
      OracleJsonArray a = f.createArray();
      StringBuilder builder=  new StringBuilder();
      for (int i = 0; i < sz; i++) {
        builder.append("x");
      }
      String value = builder.toString();
      a.add(value);
      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
      gen.write(a);
      gen.close();
      byte[] oson = baos.toByteArray();
      
      OracleJsonArray arr = f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonArray();
      assertEquals(value, arr.getString(0));
    }

    private void doStringMappingTestArr(JsonArray a) {
      assertEquals("str", a.getString(0));
      assertEquals("str", a.getString(0, null));
      assertEquals("str", a.getJsonString(0).getString());
      
      assertEquals("1970-01-01T00:00:00", a.getString(1));
      assertEquals("1970-01-01T00:00:00", a.getString(1, null));
      assertEquals("1970-01-01T00:00:00", a.getJsonString(1).getString());
      
      assertEquals("1970-01-01T00:00:00", a.getString(2));
      assertEquals("1970-01-01T00:00:00", a.getString(2, null));
      assertEquals("1970-01-01T00:00:00", a.getJsonString(2).getString());
      
      assertEquals("010203", a.getString(3));
      assertEquals("010203", a.getString(3, null));
      assertEquals("010203", a.getJsonString(3).getString());
      
      assertEquals("P2D", a.getString(4));
      assertEquals("P2D", a.getString(4, null));
      assertEquals("P2D", a.getJsonString(4).getString());
      
      assertEquals("P12Y", a.getString(5));
      assertEquals("P12Y", a.getString(5, null));
      assertEquals("P12Y", a.getJsonString(5).getString());
      
      assertEquals("def", a.getString(6, "def"));
      assertEquals("def", a.getString(234, "def"));
    }
    
    }

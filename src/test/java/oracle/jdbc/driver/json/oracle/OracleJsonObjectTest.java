// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonStructureImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonDouble;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonValue.OracleJsonType;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonObjectTest extends JsonTestCase {
  
  static OracleJsonFactory FACTORY = new OracleJsonFactory();
  
  public void testCopy() {
    OracleJsonObject obj = allTypesBinary();
    OracleJsonObject copy = FACTORY.createObject(obj);
    assertTrue(obj.equals(copy));
    
  }
  
  public void testEmpty() {
    OracleJsonObject obj = FACTORY.createObject();
    assertTrue(obj.isEmpty());
    assertEquals(0, obj.size());
  }
  
  public void testContainsValue() {
    OracleJsonObject obj = FACTORY.createObject();
    obj.put("item", FACTORY.createDecimal(123));
    assertTrue(obj.containsValue(FACTORY.createDecimal(123)));
    
    obj.remove("item");
    assertTrue(obj.isEmpty());
  }
  
  public void testPutAll() {
    Map<String, OracleJsonValue> map = new HashMap<String, OracleJsonValue>();
    map.put("a", FACTORY.createBoolean(true));
    map.put("b", FACTORY.createString("asdf"));
    OracleJsonObject obj = FACTORY.createObject();
    obj.putAll(map);
    assertEquals(2, obj.size());
    assertEquals("{\"a\":true,\"b\":\"asdf\"}", obj.toString());
  
    Set<String> keyset = obj.keySet();
    assertTrue(keyset.contains("a"));
    assertTrue(keyset.contains("b"));
    
    
    Collection<OracleJsonValue> values = obj.values();
    assertEquals(2, values.size());
    
    
    obj.clear();
    assertEquals(0, keyset.size());
  }
  
    private LocalDateTime toLDT(Instant i) {
    return i.atOffset(ZoneOffset.UTC).toLocalDateTime();
  }
  public void testNoJavaNull() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonObject obj = f.createObject();
    assertNPE(() -> obj.put("x", (OracleJsonValue)null));
  }
  
  
  public void testGetString() {
    doStringTest(allTypesTree());
    doStringTest(allTypesBinary());
  }

  private void doStringTest(OracleJsonObject obj) {
    assertEquals("string value", obj.getString("string"));
    assertNPE(() -> obj.getString("not there"));
    assertCCE(() -> obj.getString("number"));

    assertEquals("string value", obj.getString("string", null));
    assertEquals(null, obj.getString("not there", null));
    assertEquals(null, obj.getString("number", null));
  }
  
  
  public void testGetNumber() {
    doNumberTest(allTypesTree());
    doNumberTest(allTypesBinary());
  }
  
  public void testToString() {
    doToStringTest(allTypesTree());
    doToStringTest(allTypesBinary());
  }
  
  public void testEquality() {
    assertTrue(allTypesTree().equals(allTypesBinary()));
    assertTrue(allTypesTree().equals(allTypesTree()));
    assertTrue(allTypesBinary().equals(allTypesBinary()));
  }
  
  public void testHashCode() {
    assertTrue(allTypesTree().hashCode() == allTypesBinary().hashCode());
    assertTrue(allTypesTree().hashCode() == allTypesTree().hashCode());
    assertTrue(allTypesBinary().hashCode() == allTypesBinary().hashCode());
  }

  private void doToStringTest(OracleJsonObject obj) {
    String jzn = obj.toString();
    assertTrue(jzn.contains("\"string\":\"string value\""));
    assertTrue(jzn.contains("\"number\":123"));
    assertTrue(jzn.contains("\"double\":123.456"));
    assertTrue(jzn.contains("\"float\":123.0"));
    assertTrue(jzn.contains("\"binary\":\"0102030405060708090A0B0C0D0E0F\""));
    assertTrue(jzn.contains("\"timestamp\":\"1970-01-01T00:00:00\""));
    assertTrue(jzn.contains("\"date\":\"1970-01-01T00:00:00\""));
    assertTrue(jzn.contains("\"intervalds\":\"P5D\""));
    assertTrue(jzn.contains("\"intervalym\":\"P25Y\""));
    assertTrue(jzn.contains("\"boolean\":true,\""));
    assertTrue(jzn.contains("\"null\":null"));
  }

  private void doNumberTest(OracleJsonObject obj) {
    assertEquals(123, obj.getInt("number"));
    assertEquals(123, obj.getInt("double"));
    assertEquals((int)123f, obj.getInt("float"));
    assertNPE(() -> obj.getInt("not there"));
    assertCCE(() -> obj.getInt("string"));
    
    assertEquals(123d, obj.getDouble("number"));
    assertEquals(123.456d, obj.getDouble("double"));
    assertEquals((double)(123f), obj.getDouble("float"));
    assertNPE(() -> obj.getDouble("not there"));
    assertCCE(() -> obj.getDouble("string"));
    
    assertEquals(new BigDecimal(123), obj.getBigDecimal("number"));
    assertEquals(BigDecimal.valueOf(123.456d), obj.getBigDecimal("double"));
    assertEquals(BigDecimal.valueOf((123f)), obj.getBigDecimal("float"));
    assertNPE(() -> obj.getBigDecimal("not there"));
    assertCCE(() -> obj.getBigDecimal("string"));
    
    assertEquals(123l, obj.getLong("number"));
    assertEquals(123l, obj.getLong("double"));
    assertEquals(123l, obj.getLong("float"));
    assertNPE(() -> obj.getLong("not there"));
    assertCCE(() -> obj.getLong("string"));
    
    ///
    
    assertEquals(123, obj.getInt("number", 555));
    assertEquals(123, obj.getInt("double", 555));
    assertEquals(555, obj.getInt("string", 555));
    assertEquals(555, obj.getInt("not there", 555));
    
    assertEquals(123d, obj.getDouble("number", 555));
    assertEquals(123.456d, obj.getDouble("double", 555));
    assertEquals(555.5d, obj.getDouble("string", 555.5d));
    assertEquals(555d, obj.getDouble("not there", 555d));
    
    assertEquals(123l, obj.getLong("number", 555l));
    assertEquals(123l, obj.getLong("double", 555l));
    assertEquals(555l, obj.getLong("string", 555l));
    assertEquals(555l, obj.getLong("not there", 555l));
    
    assertEquals(new BigDecimal(123), obj.getBigDecimal("number", new BigDecimal(555)));
    assertEquals(BigDecimal.valueOf(123.456), obj.getBigDecimal("double", new BigDecimal(555)));
    assertEquals(new BigDecimal(555), obj.getBigDecimal("string", new BigDecimal(555)));
    assertEquals(new BigDecimal(555), obj.getBigDecimal("not there", new BigDecimal(555)));

  }
  
  public void testGetInstants() {
    doInstantTests(allTypesTree());
    doInstantTests(allTypesBinary());
  }

  private void doInstantTests(OracleJsonObject obj) {
    assertEquals(toLDT(Instant.ofEpochMilli(0)), obj.getLocalDateTime("timestamp"));
    assertEquals(toLDT(Instant.ofEpochMilli(0)), obj.getLocalDateTime("date"));
    assertNPE(() -> obj.getLocalDateTime("not there"));
    assertCCE(() -> obj.getLocalDateTime("number"));
    assertEquals(toLDT(Instant.ofEpochMilli(0)), obj.getLocalDateTime("timestamp", null));
    assertEquals(toLDT(Instant.ofEpochMilli(0)), obj.getLocalDateTime("date", null));
    assertEquals(null, obj.getLocalDateTime("not there", null));
    assertEquals(toLDT(Instant.ofEpochMilli(0)), obj.getLocalDateTime("number", toLDT(Instant.ofEpochMilli(0))));
  }

  public void testGetBooleans() {
    doBooleanTests(allTypesTree());
    doBooleanTests(allTypesBinary());
  }
  
  private void doBooleanTests(OracleJsonObject obj) {
    assertEquals(true, obj.getBoolean("boolean"));
    assertEquals(false, obj.getBoolean("boolean2"));
    assertNPE(() -> obj.getBoolean("not there"));
    assertCCE(() -> obj.getBoolean("number"));
    
    assertEquals(true, obj.getBoolean("boolean", false));
    assertEquals(false, obj.getBoolean("boolean2", true));
    assertEquals(false, obj.getBoolean("number", false));
    assertEquals(true, obj.getBoolean("not there", true));
  }

  private void assertNPE(Runnable r) {
    try {
      r.run();
      fail();
    } catch (NullPointerException e) {
      
    }
  }
  private void assertCCE(Runnable r) {
    try {
      r.run();
      fail();
    } catch (ClassCastException e) {
      
    }
  }

  private OracleJsonObject allTypesTree() {
    OracleJsonFactory factory = new OracleJsonFactory();
    OracleJsonObject obj = factory.createObject();
    obj.put("string", "string value");
    obj.put("number", factory.createDecimal(123));
    obj.put("double", factory.createDouble(123.456d));
    obj.put("float", factory.createFloat(123f));
    obj.put("binary", new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf });
    obj.put("timestamp", factory.createTimestamp(toLDT(Instant.ofEpochMilli(0))));
    obj.put("date", factory.createDate(toLDT(Instant.ofEpochMilli(0))));
    obj.put("intervalds", factory.createIntervalDS(Duration.ofDays(5)));
    obj.put("intervalym", factory.createIntervalYM(Period.ofYears(25)));
    obj.put("boolean", OracleJsonValue.TRUE);
    obj.put("boolean2", OracleJsonValue.FALSE);
    obj.put("null", OracleJsonValue.NULL);
    obj.put("obj", factory.createObject());
    obj.put("arr", factory.createArray());
    
    obj.get("arr").asJsonArray().add("abc");
    obj.get("obj").asJsonObject().put("hello", "world");
    return obj;
  }
  
  public void testPutNumbers() {
    OracleJsonObject obj = FACTORY.createObject();
    obj.put("i", 123);
    obj.put("b", new BigDecimal(456));
    obj.put("l", 789l);
    obj.put("d", 123d);
    assertEquals(123, obj.getInt("i"));
    assertEquals(new BigDecimal(456), obj.getBigDecimal("b"));
    assertEquals(789l, obj.getInt("l"));
    assertEquals(123d, obj.getDouble("d"));
  }
  
  public void testPutOthers() {
    OracleJsonObject obj = FACTORY.createObject();
    obj.put("b", true);
    obj.put("b2", false);
    obj.put("i", toLDT(Instant.ofEpochMilli(0)));
    obj.putNull("n");
    assertTrue(obj.isNull("n"));
    assertEquals(true, obj.getBoolean("b"));
    assertEquals(false, obj.getBoolean("b2"));
    assertEquals(toLDT(Instant.ofEpochMilli(0)), obj.getLocalDateTime("i"));
  }
  
  public void testIsNull() {
    assertTrue(allTypesTree().isNull("null"));
  }
  
  public void testGetBytes() {
    doGetBytesTest(allTypesTree());
    doGetBytesTest(allTypesBinary());
  }
  
  private void doGetBytesTest(OracleJsonObject obj) {
    assertTrue(Arrays.equals(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf }, obj.getBytes("binary")));
  
    assertTrue(Arrays.equals(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf }, obj.getBytes("binary", null)));
    assertEquals(null, obj.getBytes("xyz", null));
    assertEquals(null, obj.getBytes("obj", null));
    assertNPE(() -> obj.getBytes("asd"));
  }
  
  public void testDuplicateKeys() {
    String str = "ff4a5a012106010002000e00022c00000161840201010008000b21c10221c103";
    byte[] oson = hexToRaw(str);
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonValue o = f.createJsonBinaryValue(ByteBuffer.wrap(oson));
    String jzn = "{\"a\":1,\"a\":2}";
    
    assertEquals(jzn, o.toString());
    OracleJsonObject obj = o.asJsonObject();
    assertEquals(1, obj.getInt("a"));
    int ct = 0;
    OracleJsonParser parser = f.createJsonBinaryParser(ByteBuffer.wrap(oson));
    while (parser.hasNext()) {
      OracleJsonParser.Event event = parser.next();
      switch (event) {
      case START_OBJECT:
        break;
      case KEY_NAME:
        ct++;
        assertEquals("a", parser.getString());
        break;
      case VALUE_DECIMAL:
        break;
      case END_OBJECT:
        break;
      default:
        throw new IllegalStateException();
      }
    }
    assertEquals(2, ct);
  }

  public static byte[] hexToRaw(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }
  
  private OracleJsonObject allTypesBinary() {
    OracleJsonObject obj = allTypesTree();
    return toBinary(obj);
    
  }

  private OracleJsonObject toBinary(OracleJsonObject obj) {
    OracleJsonFactory factory = new OracleJsonFactory();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    OracleJsonGenerator gen = factory.createJsonBinaryGenerator(out);
    gen.write(obj);
    gen.close();
    return factory.createJsonBinaryValue(ByteBuffer.wrap(out.toByteArray())).asJsonObject();
  }
  
  public void testGetValues() {
    OracleJsonObject obj = FACTORY.createObject();
    obj.put("x", "y");
    obj.put("z", "w");
    obj = toBinary(obj);
    Collection<OracleJsonValue> values = obj.values();
    assertEquals(2, values.size());
    assertTrue(values.contains(FACTORY.createString("w")));
    assertTrue(values.contains(FACTORY.createString("y")));
    assertFalse(values.contains(FACTORY.createString("xyz")));
  }
  
  public void testGetBuffer() {
    OracleJsonObject obj = FACTORY.createObject();
    obj.put("x", "y");
    obj.put("z", "w");
    obj = toBinary(obj);
    OsonStructureImpl oobj = (OsonStructureImpl)obj;
    ByteBuffer b = oobj.getBuffer();
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonValue v = f.createJsonBinaryValue(b);
    assertEquals(v, obj);
  }
  
  public void testBinaryMutators() {
    OracleJsonObject obj = toBinary(FACTORY.createObject());
    assertUnsupported(() -> obj.put("k", (OracleJsonValue)null));
    assertUnsupported(() -> obj.remove("k"));
    assertUnsupported(() -> obj.putAll(null));
    assertUnsupported(() -> obj.clear());
    assertUnsupported(() -> obj.put("x", "y"));
    assertUnsupported(() -> obj.put("x", 1));
    assertUnsupported(() -> obj.put("x", 1l));
    assertUnsupported(() -> obj.put("x", (BigDecimal)null));
    assertUnsupported(() -> obj.put("x", 2d));
    assertUnsupported(() -> obj.put("x", true));
    assertUnsupported(() -> obj.put("x", (LocalDateTime)null));
    assertUnsupported(() -> obj.put("x", (byte[])null));
    assertUnsupported(() -> obj.put("x", (byte[])null));
    assertUnsupported(() -> obj.putNull("x"));
  }

  private void assertUnsupported(Runnable r) {
    try {
      r.run();
      fail();
    } catch (UnsupportedOperationException e) {
      assertTrue(e.getMessage().startsWith("ORA-26321"));
    }
  }

    public void testGetObject() {
    OracleJsonObject obj = FACTORY.createObject();
    obj.put("o", FACTORY.createObject());
    OracleJsonObject obj2 = obj.getObject("o");
    assertEquals("{}", obj2.toString());
  }
  
  public void testGetArray() {
    OracleJsonObject obj = FACTORY.createObject();
    obj.put("a", FACTORY.createArray());
    OracleJsonArray a = obj.getArray("a");
    assertEquals("[]", a.toString());
  }

  public void testNaNAndInfinity() {
    // Test float
    doNumberTest("FF4A5A01210601000A002600011000000942494E5F56414C5545C00300080012001C860101000D7F007FFFFF9C000800177FFFC000009C000800217FFF800000", "FLOAT");
    // Test double
    doNumberTest("FF4A5A01210601000A003200011000000942494E5F56414C5545C003000800160024860101000D36000FFFFFFFFFFFFF9C0008001B36FFF80000000000009C0008002936FFF0000000000000", "DOUBLE");  
  }
  
  public void doNumberTest(String s, String type) {
    OracleJsonFactory factory = new OracleJsonFactory();
    List<Byte> byteList = new ArrayList<>();
    for (int i = 0; i < s.length(); i += 2) {
      char ch1 = s.charAt(i);
      char ch2 = s.charAt(i + 1);
      byteList.add((byte) ((Character.digit(ch1, 16) << 4) + Character.digit(ch2, 16)));
    }

    byte[] bytes = new byte[byteList.size()];
    IntStream.range(0, bytes.length).forEach((i) -> {
      bytes[i] = byteList.get(i);
    });

    OracleJsonValue value = factory.createJsonBinaryValue(ByteBuffer.wrap(bytes));
    String[] vals = {"\"-Inf\"","\"Nan\"","\"Inf\""};
    int i = 0; 

    for (OracleJsonValue obj : value.asJsonArray()) {
      for (Map.Entry<String, OracleJsonValue> entry : obj.asJsonObject().entrySet()) {
        assertEquals(entry.getValue().toString(), vals[i++]);
        assertEquals(entry.getValue().getOracleJsonType().toString(), type);
      }
    }  
  }

}

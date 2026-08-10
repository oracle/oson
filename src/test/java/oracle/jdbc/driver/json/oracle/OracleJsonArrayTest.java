/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonArrayImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonValue;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonArrayTest extends JsonTestCase {

  static OracleJsonFactory FACTORY = new OracleJsonFactory();
  
  public void testAddLong() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(2543l);
    assertEquals(2543l, arr.get(0).asJsonDecimal().longValue());
  }
  
  public void testAddBigDecimal() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(new BigDecimal(12345));
    assertEquals(new BigDecimal(12345), arr.get(0).asJsonDecimal().bigDecimalValue());
  }
  
  public void testAddBoolean() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(true);
    arr.add(false);
    assertEquals(OracleJsonValue.TRUE, arr.get(0));
    assertEquals(OracleJsonValue.FALSE, arr.get(1));
  }
  
  public void testAddAll() {
    OracleJsonArray arr = FACTORY.createArray();
    OracleJsonArray arr2 = FACTORY.createArray();
    arr.add(1);
    arr2.addAll(arr);
    assertEquals(1, arr2.getInt(0));
    
    arr = FACTORY.createArray();
    arr2.addAll(arr);
    assertEquals(1, arr2.getInt(0));
  }
  
  public void testAddAll2() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    
    ArrayList<OracleJsonValue> values = new ArrayList<OracleJsonValue>();
    values.add(FACTORY.createNull());
    
    arr.addAll(1, values);
    assertEquals("[1,null,2,3]", arr.toString());
    
    values.set(0, null);
    assertNPE(() -> arr.addAll(values));
    assertNPE(() -> arr.addAll(0, values));
  }
  
  public void testRemoveAllRetainAll() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    
    OracleJsonArray arr2 = FACTORY.createArray();
    arr2.add(1);
    arr2.add(2);
    
    arr.removeAll(arr2);
    assertEquals(2, arr.size());
    assertEquals(4, arr.getInt(1));
    
    arr = FACTORY.createArray();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    
    arr.retainAll(arr2);
    
    assertEquals(arr, arr2);
  }
  
  public void testSetBoolean() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(1);
    arr.set(0, true);
    arr.set(0, false);
    assertFalse(arr.getBoolean(0));
  }
  
  public void testSetValue() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.set(0, FACTORY.createDecimal(1));
    assertEquals(1, arr.getInt(0));
  }
  
  public void testSetString() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.set(0, "asdf");
    assertEquals("asdf", arr.getString(0));
  }
  
  public void testSetInt() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.set(0, 123);
    assertEquals(123, arr.getInt(0));
  }
  
  public void testSetDouble() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.set(0, 123d);
    assertEquals(123d, arr.getDouble(0));
  }
  
  public void testSetLong() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.set(0, 123l);
    assertEquals(123l, arr.getLong(0));
  }
  
  public void testSetBigDecimal() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.set(0, new BigDecimal(123));
    assertEquals(new BigDecimal(123), arr.getBigDecimal(0));
  }
  
  public void testSetInstant() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime(); 
    arr.set(0, ldt);
    assertEquals(ldt, arr.getLocalDateTime(0));
  }
  
  public void testSetNull() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.setNull(0);
    assertEquals(arr.get(0), OracleJsonValue.NULL);
  }
  
  public void testAddValue() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.add(0, FACTORY.createDecimal(1));
    assertEquals(1, arr.getInt(0));
  }
  
  public void testSetBytes() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(0);
    arr.set(0, new byte[] {});
    byte[] bytes = arr.getBytes(0);
    assertEquals(0, bytes.length);
  }
  
  public void testRemove() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(FACTORY.createDecimal(0));
    arr.remove(FACTORY.createDecimal(1));
    assertEquals(1, arr.size());
    arr.remove(FACTORY.createDecimal(0));
    assertEquals(0, arr.size());
  }
  
  public void testToString() {
    doToStringTest(allTypesTree());
    doToStringTest(allTypesBinary());
  } 
  
  private void doToStringTest(OracleJsonArray arr) {
    assertEquals("[\"string value\",123,123.456,123.0,\"0102030405060708090A0B0C0D0E0F\","
        + "\"1970-01-01T00:00:00\",\"1970-01-01T00:00:00\",\"P5D\",\"P25Y\",true,null,[],{},[\"hello\"],{\"hello\":\"world\"}]", arr.toString());
  }
  
  public void testSubList() {
    doSubListTest(allTypesTree());
    doSubListTest(allTypesBinary());
  }
  
  private void doSubListTest(OracleJsonArray arr) {
    List<OracleJsonValue> tester = new ArrayList<OracleJsonValue>();
    tester.add(arr.get(3));
    tester.add(arr.get(4));
    tester.add(arr.get(5));
    assertEquals(tester, arr.subList(3, 6));
    List<OracleJsonValue> tester2 = new ArrayList<OracleJsonValue>(arr);
    
    assertTrue(tester2.subList(1, 1).equals(arr.subList(1, 1)));
    assertTrue(tester2.subList(0, 14).equals(arr.subList(0, 14)));
    assertTrue(tester2.subList(0, 0).equals(arr.subList(0, 0)));
  }

  public void testIsNull() {
    assertTrue(allTypesTree().isNull(10));
    assertFalse(allTypesTree().isNull(0));
    assertTrue(allTypesBinary().isNull(10));
    assertFalse(allTypesBinary().isNull(0));
  }
  
  public void testGetInstant() {
    doGetInstantTest(allTypesTree());
    doGetInstantTest(allTypesBinary());
  }
  
  private void doGetInstantTest(OracleJsonArray arr) {
    LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime(); 
    assertEquals(ldt, arr.getLocalDateTime(5));
    assertEquals(ldt, arr.getLocalDateTime(6));
  }

  public void testGetBytes() {
    doGetBytes(allTypesTree());
    doGetBytes(allTypesBinary());
  }
  
  private void doGetBytes(OracleJsonArray arr) {
    assertTrue(Arrays.equals(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf }, arr.getBytes(4)));
    
  }
  
  public void testGetString() {
    doGetStringTest(allTypesTree());
    doGetStringTest(allTypesBinary());
  }
  
  private void doGetStringTest(OracleJsonArray arr) {
    assertEquals("string value", arr.getString(0));
    assertIOB(() -> arr.getString(144));
    assertCCE(() -> arr.getString(1));
  }
  
  public void testGetInt() {
    doGetIntTest(allTypesTree());
    doGetIntTest(allTypesBinary());
  }
  
  private void doGetIntTest(OracleJsonArray arr) {
    assertEquals(123, arr.getInt(1));
    assertIOB(() -> arr.getInt(144));
    assertCCE(() -> arr.getInt(0));
  }
  
  public void testGetBigDecimal() {
    doGetBigDecimal(allTypesTree());
    doGetBigDecimal(allTypesBinary());
  }
  
  private void doGetBigDecimal(OracleJsonArray arr) {
    assertEquals(BigDecimal.valueOf(123), arr.getBigDecimal(1));
    assertIOB(() -> arr.getBigDecimal(144));
    assertCCE(() -> arr.getBigDecimal(0));
  }  
  
  public void testGetLong() {
    doGetLongTest(allTypesTree());
    doGetLongTest(allTypesBinary());
  }
  
  private void doGetLongTest(OracleJsonArray arr) {
    assertEquals(123, arr.getLong(1));
    assertIOB(() -> arr.getLong(144));
    assertCCE(() -> arr.getLong(0));
  }  
  
  public void testGetBoolean() {
    OracleJsonArray arr = allTypesTree();
    arr.set(10, OracleJsonValue.FALSE);
    doGetBooleanTest(arr);
    doGetBooleanTest(toBinary(arr));
  }
  
  private void doGetBooleanTest(OracleJsonArray arr) {
    assertTrue(arr.getBoolean(9));
    assertFalse(arr.getBoolean(10));
    assertIOB(() -> arr.getBoolean(144));
    assertCCE(() -> arr.getBoolean(0));
  }  
  
  public void testGetDouble() {
    doGetDoubleTest(allTypesTree());
    doGetDoubleTest(allTypesBinary());
  }
  
  private void doGetDoubleTest(OracleJsonArray arr) {
    assertEquals(123.456d, arr.getDouble(2));
    assertIOB(() -> arr.getDouble(144));
    assertCCE(() -> arr.getDouble(0));
  }

  private void assertIOB(Runnable r) {
    try {
      r.run();
      fail();
    } catch (IndexOutOfBoundsException e) {
      
    }
  }

  private void assertCCE(Runnable r) {
    try {
      r.run();
      fail();
    } catch (ClassCastException e) {
      
    }
  }

  public void testGetValuesAs() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonArray arr = f.createArray();
    arr.add("one");
    arr.add("two");
    arr.add("three");
    doGetValuesAsTest(arr);
    doGetValuesAsTest(toBinary(arr));
  }
  
  private void doGetValuesAsTest(OracleJsonArray arr) {
    List<OracleJsonString> strings = arr.getValuesAs(OracleJsonString.class);
    assertEquals("one", strings.get(0).getString());
    assertEquals("two", strings.get(1).getString());
    assertEquals("three", strings.get(2).getString());
  }

  public void testCopy() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonArray orig = allTypesTree();
    OracleJsonArray copy = f.createArray(orig);
    assertTrue(orig.equals(copy));
    
    OracleJsonArray binary = toBinary(orig);
    OracleJsonArray copy2 = f.createArray(binary);
    
    assertTrue(binary.equals(copy2));
    assertTrue(copy2.equals(copy));
    assertTrue(orig.equals(binary));
  }
  
  public void testListIterator() {
    doTestListIterator(allTypesTree());
    doTestListIterator(allTypesBinary());
  }
  
  public void testBinaryListIterator() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(1);
    arr = toBinary(arr);
    ListIterator<OracleJsonValue> iter = arr.listIterator();
    try {
      iter.previous();
      fail();
    } catch (NoSuchElementException e) {
      
    }
    assertUnsupported(() -> iter.remove(), false);
    assertUnsupported(() -> iter.set(null), false);
    assertUnsupported(() -> iter.add(null), false);
  }
  
  private void doTestListIterator(OracleJsonArray arr) {
    ListIterator<OracleJsonValue> iter = arr.listIterator(10);
    assertTrue(iter.hasNext());
    assertEquals(OracleJsonValue.NULL, iter.next());
  }

  public void testIsEmpty() {
    assertFalse(allTypesTree().isEmpty());
    assertFalse(allTypesBinary().isEmpty());
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonArray arr = f.createArray();
    assertTrue(arr.isEmpty());
    assertTrue(toBinary(arr).isEmpty());
  }
  
  public void testContains() {
    doContainsTest(allTypesTree());
    doContainsTest(allTypesBinary());
  }
  
  private void doContainsTest(OracleJsonArray arr) {
    OracleJsonFactory f = new OracleJsonFactory();
    assertTrue(arr.contains(f.createString("string value"))); 
    assertTrue(arr.contains(f.createDecimal(123)));
    assertTrue(arr.contains(f.createDouble(123.456d)));
    assertTrue(arr.contains(f.createFloat(123f)));
    assertTrue(arr.contains(f.createBinary(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf })));
    LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime(); 
    assertTrue(arr.contains(f.createTimestamp(ldt)));
    assertTrue(arr.contains(f.createDate(ldt)));
    assertTrue(arr.contains(f.createIntervalDS(Duration.ofDays(5))));
    assertTrue(arr.contains(f.createIntervalYM(Period.ofYears(25))));
    assertTrue(arr.contains(OracleJsonValue.TRUE));
    assertTrue(arr.contains(OracleJsonValue.NULL));
    
    assertFalse(arr.contains(OracleJsonValue.FALSE));
    assertFalse(arr.contains(f.createDecimal(456)));
    assertFalse(arr.contains(f.createString("string value2"))); 
    
    assertFalse(arr.contains(f.createDouble(123.451236d)));
    assertFalse(arr.contains(f.createFloat(12f)));
    assertFalse(arr.contains(f.createBinary(new byte[] { 1, 2, 3, 4, 9, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf })));
    LocalDateTime ldtx = Instant.ofEpochMilli(1).atOffset(ZoneOffset.UTC).toLocalDateTime(); 
    assertFalse(arr.contains(f.createTimestamp(ldtx)));
    LocalDateTime ldtx2 = Instant.ofEpochMilli(123746237).atOffset(ZoneOffset.UTC).toLocalDateTime(); 
    assertFalse(arr.contains(f.createDate(ldtx2)));
    assertFalse(arr.contains(f.createIntervalDS(Duration.ofDays(2))));
    assertFalse(arr.contains(f.createIntervalYM(Period.ofYears(30))));
  }
  
  public void testContainsAll() {
    assertTrue(allTypesTree().containsAll(allTypesBinary()));
    assertTrue(allTypesTree().containsAll(allTypesTree()));
    assertTrue(allTypesBinary().containsAll(allTypesBinary()));
    
    OracleJsonArray arr = allTypesTree();
    arr.add("foo");
    assertFalse(allTypesBinary().containsAll(arr));
  }
  
  public void testToArray() {
    doToArrayTest(allTypesTree());
    doToArrayTest(allTypesBinary());
  }
  
  private void doToArrayTest(OracleJsonArray arr) {
    Object[] jarr = arr.toArray();
    List<OracleJsonValue> arr2 = new ArrayList<OracleJsonValue>();
    for (Object o : jarr) {
      arr2.add((OracleJsonValue)o);
    }
    assertTrue(arr.equals(arr2));
  }

  public void testToArray2() {
    doToArrayTest2(allTypesTree());
    doToArrayTest2(allTypesBinary());
  }
  
  private void doToArrayTest2(OracleJsonArray arr) {
    OracleJsonValue[] jarr = new OracleJsonValue[arr.size()];
    jarr = arr.toArray(jarr);
    List<OracleJsonValue> arr2 = new ArrayList<OracleJsonValue>();
    for (OracleJsonValue o : jarr) {
      arr2.add(o);
    }
    assertTrue(arr.equals(arr2));
  }
  
  public void testToArray3() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonArray arr = f.createArray();
    arr.add("a");
    arr.add("b");
    arr.add("c");
    doToArrayTest3(arr);
    doToArrayTest3(toBinary(arr));
  }
  
  private void doToArrayTest3(OracleJsonArray arr) {
    OracleJsonString[] jarr = new OracleJsonString[arr.size()];
    jarr = arr.toArray(jarr);
    List<OracleJsonString> arr2 = new ArrayList<OracleJsonString>();
    for (OracleJsonString o : jarr) {
      arr2.add(o);
    }
    assertTrue(arr.equals(arr2));
  }
  
  public void testClear() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonArray arr = f.createArray();
    arr.add("adf");
    assertEquals(1, arr.size());
    arr.clear();
    assertEquals(0, arr.size());
    assertTrue(arr.isEmpty());
  }
  
  public void testIndexOf() {
    doIndexOfTest(allTypesTree());
    doIndexOfTest(allTypesBinary());
  }
  
  private void doIndexOfTest(OracleJsonArray arr) {
    for (int i = 0; i < arr.size(); i++) {
      assertEquals(i, arr.indexOf(arr.get(i)));
      assertEquals(i, arr.lastIndexOf(arr.get(i)));
    }
    try {
      OracleJsonValue v = arr.remove(3);
      assertEquals(-1, arr.indexOf(v));
    
      arr.add(v);
      arr.add(v);
      assertEquals(arr.size()-1, arr.lastIndexOf(v));
    } catch (UnsupportedOperationException e) {
      assertTrue(arr instanceof OsonArrayImpl);
    }
  }

  public void testNoJavaNull() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonArray arr = f.createArray();
    assertNPE(() -> arr.add((OracleJsonValue)null));
    assertNPE(() -> arr.set(0, (OracleJsonValue)null));
    ArrayList<OracleJsonValue> l = new ArrayList<OracleJsonValue>();
    l.add(null);
    assertNPE(() -> arr.addAll(l));
  }
  
  private void assertNPE(Runnable r) {
    try {
      r.run();
      fail();
    } catch(NullPointerException npe) {
      
    }
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

    
  public void testBinaryMutators() {
    OracleJsonArray arr = allTypesBinary();
    assertIOB(() -> arr.listIterator(-1));
    assertCCE(() -> arr.getLocalDateTime(3));
    assertUnsupported(() -> arr.add(FACTORY.createNull()));
    assertUnsupported(() -> arr.addAll(null));
    assertUnsupported(() -> arr.addAll(1, null));
    assertUnsupported(() -> arr.set(1, (OracleJsonValue)null));
    assertUnsupported(() -> arr.add(1, (OracleJsonValue)null));
    assertUnsupported(() -> arr.set(1, (String)null));
    assertUnsupported(() -> arr.set(1, (byte[])null));
    assertUnsupported(() -> arr.set(1, (BigDecimal)null));
    assertUnsupported(() -> arr.set(1, (LocalDateTime)null));
    assertUnsupported(() -> arr.set(1, true));
    assertUnsupported(() -> arr.set(1, 1l));
    assertUnsupported(() -> arr.set(1, 1));
    assertUnsupported(() -> arr.setNull(1));
    assertUnsupported(() -> arr.set(1, 123d));
    assertUnsupported(() -> arr.add((String)null));
    assertUnsupported(() -> arr.addNull());
    assertUnsupported(() -> arr.add((byte[])null));
    assertUnsupported(() -> arr.add((LocalDateTime)null));
    assertUnsupported(() -> arr.add(1));
    assertUnsupported(() -> arr.add(new BigDecimal(1)));
    assertUnsupported(() -> arr.add(1l));
    assertUnsupported(() -> arr.add(1d));
    assertUnsupported(() -> arr.add(true));
    
    
  }
  
  public void testGetObject() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(FACTORY.createObject());
    OracleJsonObject obj = arr.getObject(0);
    assertEquals("{}", obj.toString());
  }
  
  public void testGetArray() {
    OracleJsonArray arr = FACTORY.createArray();
    arr.add(FACTORY.createArray());
    OracleJsonArray a = arr.getArray(0);
    assertEquals("[]", a.toString());
  }
  
  public void assertUnsupported(Runnable r) {
    assertUnsupported(r, true);
  }
  
  public void assertUnsupported(Runnable r, boolean testMessage) {
    try {
      r.run();
      fail();
    } catch (UnsupportedOperationException e) {
      if (testMessage) {
        assertTrue(e.getMessage().startsWith("ORA-26322"));
      }
    }
  }
  
  private OracleJsonArray allTypesTree() {
    OracleJsonFactory factory = new OracleJsonFactory();
    OracleJsonArray arr = factory.createArray();
    /* 0 */ arr.add("string value"); 
    /* 1 */ arr.add(123);
    /* 2 */ arr.add(123.456d);
    /* 3 */ arr.add(factory.createFloat(123f));
    /* 4 */ arr.add(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf });
    LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime();
    /* 5 */ arr.add(ldt);
    /* 6 */ arr.add(factory.createDate(ldt));
    /* 7 */ arr.add(factory.createIntervalDS(Duration.ofDays(5)));
    /* 8 */ arr.add(factory.createIntervalYM(Period.ofYears(25)));
    /* 9 */ arr.add(true);
    /*10 */ arr.addNull();
    /*11 */ arr.add(factory.createArray());
    /*12 */ arr.add(factory.createObject());
    /*13 */ arr.add(factory.createArray());
    /*14 */ arr.add(factory.createObject());
    arr.get(13).asJsonArray().add("hello");
    arr.get(14).asJsonObject().put("hello", "world");
    return arr;
  }
  private OracleJsonArray allTypesBinary() {
    OracleJsonArray tree = allTypesTree();
    return toBinary(tree);
    
  }

  private OracleJsonArray toBinary(OracleJsonArray tree) {
    OracleJsonFactory factory = new OracleJsonFactory();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    OracleJsonGenerator gen = factory.createJsonBinaryGenerator(out);
    gen.write(tree);
    gen.close();
    return factory.createJsonBinaryValue(ByteBuffer.wrap(out.toByteArray())).asJsonArray();
  }
}

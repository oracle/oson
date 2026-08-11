/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.jakarta;


import java.nio.ByteBuffer;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonFactory;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonArrayTest extends JsonTestCase {
    static OracleJsonFactory FACT = new OracleJsonFactory();
    
    public void testGetJsonObject() {
        JsonArray a = getArray("[1,{\"a\":true}]");
        
        cce(() -> a.getJsonObject(0));
        iob(() -> a.getJsonObject(3));
        iob(() -> a.getJsonObject(-1));
     
        JsonObject o = a.getJsonObject(1);
        assertTrue(o.getBoolean("a"));
    }
    
    public void testGetJsonArray() {
        JsonArray a = getArray("[1,[\"foo\"]]");
        cce(() -> a.getJsonArray(0));
        iob(() -> a.getJsonArray(3));
        iob(() -> a.getJsonArray(-1));
     
        JsonArray a2 = a.getJsonArray(1);
        assertEquals("foo", a2.getString(0));
    }
    
    public void testGetJsonNumber() {
        JsonArray a = getArray("[true,123]");
        cce(() -> a.getJsonNumber(0));
        iob(() -> a.getJsonNumber(3));
        iob(() -> a.getJsonNumber(-1));
     
        JsonNumber n = a.getJsonNumber(1);
        assertEquals(123, n.intValue());
    }
    
    public void testGetJsonString() {
        JsonArray a = getArray("[true,\"foo\"]");
        cce(() -> a.getJsonString(0));
        iob(() -> a.getJsonString(3));
        iob(() -> a.getJsonString(-1));
     
        JsonString n = a.getJsonString(1);
        assertEquals("foo", n.getString());
    }
    
    public void testGetValuesAs() {
        JsonArray a = getArray("[\"bar\",\"foo\"]");
        List<JsonString> strings = a.getValuesAs(JsonString.class);
        JsonString s = strings.get(0);
        assertEquals("bar", s.getString());
        s = strings.get(1);
        assertEquals("foo", s.getString());
        
        final JsonArray a2 = getArray("[\"bar\",1]");
        cce(() -> a2.getValuesAs(JsonString.class).get(1).getString()); 
    }   
    
    public void testGetString() {
        JsonArray a = getArray("[true,\"foo\"]");
        cce(() -> a.getString(0));
        iob(() -> a.getString(3));
        iob(() -> a.getString(-1));
     
        String s = a.getString(1);
        assertEquals("foo", s);
    }
    
    public void testGetStringDefault() {
        JsonArray a = getArray("[true,\"foo\"]");
        assertEquals("x", a.getString(0, "x"));
        assertEquals("x", a.getString(3, "x"));
        assertEquals("x", a.getString(-1, "x"));
        assertEquals("foo", a.getString(1, "x"));
    }
    
    public void testGetInt() {
        JsonArray a = getArray("[true,123]");
        cce(() -> a.getInt(0));
        iob(() -> a.getInt(3));
        iob(() -> a.getInt(-1));
        assertEquals(123,  a.getInt(1));
    }
    
    public void testGetIntDefault() {
        JsonArray a = getArray("[true,123]");
        assertEquals(456, a.getInt(0, 456));
        assertEquals(456, a.getInt(3, 456));
        assertEquals(456, a.getInt(-1, 456));
        assertEquals(123, a.getInt(1, 456));
    }
    
    public void testGetBoolean() {
        JsonArray a = getArray("[null,true]");
        cce(() -> a.getBoolean(0));
        iob(() -> a.getBoolean(3));
        iob(() -> a.getBoolean(-1));
        assertEquals(true,  a.getBoolean(1));
    }
    
    public void testGetBooleanDefault() {
        JsonArray a = getArray("[true,123]");
        assertEquals(false, a.getBoolean(1, false));
        assertEquals(false, a.getBoolean(3, false));
        assertEquals(false, a.getBoolean(-1, false));
        assertEquals(true, a.getBoolean(0, false));
    }    
    
    public void testIsNull() {
        JsonArray a = getArray("[null,true]");
        iob(() -> a.isNull(3));
        iob(() -> a.isNull(-1));
        
        assertTrue(a.isNull(0));
        assertFalse(a.isNull(1));
    }
    
    @SuppressWarnings("unlikely-arg-type")
    public void testContais() {
        JsonArray a = getArray("[true,123]");
        assertTrue(a.contains(JsonValue.TRUE));
        assertFalse(a.contains(JsonValue.FALSE));
        assertFalse(a.contains(new StringBuilder()));
        JsonNumber n = getArray("[123]").getJsonNumber(0);
        assertTrue(a.contains(n));
    }
    
    public void testIterator() {
        JsonArray a = getArray("[true,123,\"foo\"]");
        Iterator<JsonValue> iter = a.iterator();
        assertTrue(iter.hasNext());
        assertEquals(JsonValue.TRUE, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(getArray("[123]").getJsonNumber(0), iter.next());
        assertTrue(iter.hasNext());
        assertEquals("foo", ((JsonString)iter.next()).getString());
        assertFalse(iter.hasNext());
    }
    
    public void testArray() {
        List<JsonValue> a = getArray("[true,123,\"foo\"]");
        Object[] arr = a.toArray();
        assertEquals(3, arr.length);
        assertEquals(JsonValue.TRUE, arr[0]);
        assertEquals(getArray("[123]").getJsonNumber(0), arr[1]);
        assertEquals("foo", ((JsonString)arr[2]).getString());
    }
    
    public void testArray2() {
        List<JsonValue> a = getArray("[\"foo\",\"bar\"]");
        
        JsonString[] arr = new JsonString[1];
        JsonString[] arr2 = a.toArray(arr);
        assertFalse(arr == arr2);
        assertEquals(2, arr2.length);
        assertEquals("foo", arr2[0].getString());
        assertEquals("bar", arr2[1].getString());
        
        
        // too big
        JsonString[] arr3 = new JsonString[5];
        for (int i = 0; i < arr3.length; i++) {
            arr3[i] = getArray("[\"x\"]").getJsonString(0);
        }
        JsonString[] arr4 = a.toArray(arr3);
        assertTrue(arr4 == arr3);
        assertEquals(5, arr4.length);
        assertEquals("foo", arr2[0].getString());
        assertEquals("bar", arr2[1].getString());
        assertNull(arr4[2]);
        
        // just right
        JsonString[] arr5 = new JsonString[2];
        JsonString[] arr6 = a.toArray(arr5);
        assertTrue(arr5 == arr6);
        assertEquals(2, arr6.length);
        assertEquals("foo", arr6[0].getString());
        assertEquals("bar", arr6[1].getString());
    }
    
    
    public void testToString() {
        String jzn = "[1,2,3,\"hello\"]";
        JsonArray arr = getArray(jzn);
        assertEquals(jzn, arr.toString());
    }
    
    public void testContainsAll() {
        assertTrue(getArray("[1,2,3,4]").containsAll(getArray("[1,4]")));
        assertFalse(getArray("[1,2,3,4]").containsAll(getArray("[1,5]")));
    }
    
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        assertTrue(getArray("[1,2,3]").equals(getArray("[1,2,3]")));
        assertFalse(getArray("[1,2,3]").equals(getArray("[2,3]")));
        assertTrue(getArray("[1]").equals(Collections.singletonList(getArray("[1]").get(0))));
        assertFalse(getArray("[1]").equals(Collections.singleton(1)));
        assertFalse(getArray("[1]").equals(123));
        assertFalse(getArray("[1,2,3]").equals(getArray("[1,2,56]")));
    }
    
    @SuppressWarnings("unlikely-arg-type")
    public void testIndexOf() {
        JsonArray arr = getArray("[1,2,\"foo\"]");
        assertEquals(0, arr.indexOf(arr.get(0)));
        assertEquals(1, arr.indexOf(arr.get(1)));
        assertEquals(2, arr.indexOf(arr.get(2)));
        assertEquals(-1, arr.indexOf("asdf"));
        assertEquals(0, getArray("[1,1,1]").indexOf(arr.get(0)));
    }
    
    public void testLastIndexOf() {
        List<JsonValue> arr = getArray("[1,1,1]");
        assertEquals(2, arr.lastIndexOf(arr.get(0)));
        
        arr = getArray("[1,1,2,1,2]");
        assertEquals(3, arr.lastIndexOf(arr.get(0)));
        
        assertEquals(-1, getArray("[4,5,6]").lastIndexOf(arr.get(0)));
    }
    
    public void testHashCode() {
        assertEquals(getArray("[1,2,3]").hashCode(), getArray("[1,2,3]").hashCode());
        assertNotSame(getArray("[1,2,3]").hashCode(), getArray("[2,3]").hashCode());
        
        List<JsonValue> l = getArray("[1,2,3]");
        List<JsonValue> l2 = new ArrayList<JsonValue>();
        l2.addAll(l);
        assertEquals(l, l2);
        assertEquals(l.hashCode(), l2.hashCode());
        
    }
    
    public void testGet() {
        JsonArray a = getArray("[true,\"foo\"]");
        iob(() -> a.get(3));
        iob(() -> a.get(-1));
        JsonValue v = a.get(1);
        assertEquals("foo", ((JsonString)v).getString());
    }
    
    public void testListIterator() {
        JsonArray arr = getArray("[\"a\",\"b\",\"c\",\"d\"]");
        
        JsonString a = arr.getJsonString(0);
        JsonString b = arr.getJsonString(1);
        JsonString c = arr.getJsonString(2);
        JsonString d = arr.getJsonString(3);
        
        List<JsonValue> arr2 = new ArrayList<JsonValue>();
        arr2.addAll(arr);
        
        ListIterator<JsonValue> iter = arr.listIterator();
        
        // forward to a
        assertFalse(iter.hasPrevious());
        assertTrue(iter.hasNext());
        assertEquals(-1, iter.previousIndex());
        assertEquals(0, iter.nextIndex());
        assertEquals(a, iter.next());
        
        assertEquals(a, iter.previous());
        assertEquals(a, iter.next());
        
        assertEquals(0, iter.previousIndex());
        assertEquals(1, iter.nextIndex());
        assertTrue(iter.hasPrevious());
        assertTrue(iter.hasNext());
        assertEquals(b, iter.next());

        assertEquals(b, iter.previous());
        assertEquals(a, iter.previous());
        assertEquals(a, iter.next());
        assertEquals(b, iter.next());
        
        assertEquals(1, iter.previousIndex());
        assertEquals(2, iter.nextIndex());
        assertTrue(iter.hasPrevious());
        assertTrue(iter.hasNext());
        assertEquals(c, iter.next());
        
        assertEquals(2, iter.previousIndex());
        assertEquals(3, iter.nextIndex());
        assertTrue(iter.hasPrevious());
        assertTrue(iter.hasNext());
        assertEquals(d, iter.next());
        
        assertEquals(3, iter.previousIndex());
        assertEquals(4, iter.nextIndex());
        assertFalse(iter.hasNext());
        assertTrue(iter.hasPrevious());
        
        assertEquals(d, iter.previous());
        assertEquals(c, iter.previous());
        assertEquals(b, iter.previous());
        assertEquals(a, iter.previous());
        
        iter = arr.listIterator(2);
        assertEquals(c, iter.next());
        assertEquals(c, iter.previous());
        assertEquals(b, iter.previous());
        assertEquals(a, iter.previous());
        
        iter = arr.listIterator(3);
        assertEquals(d, iter.next());
        
        iter = arr.listIterator(0);
        assertEquals(a, iter.next());
    }
    
    public void testSubList() {
        JsonArray arr = getArray("[\"a\",\"b\",\"c\",\"d\"]");
        
        JsonString a = arr.getJsonString(0);
        JsonString b = arr.getJsonString(1);
        JsonString c = arr.getJsonString(2);
        JsonString d = arr.getJsonString(3);
        
        List<JsonValue> arr2 = new ArrayList<JsonValue>();
        arr2.addAll(arr);
        
        List<JsonValue> sub1 = arr.subList(0, 1);
        assertEquals(1, sub1.size());
        assertEquals(a, sub1.get(0));
        
        List<JsonValue> sub2 = arr.subList(1, 3);
        assertEquals(2, sub2.size());
        assertEquals(b, sub2.get(0));
        assertEquals(c, sub2.get(1));

        List<JsonValue> sub3 = arr.subList(3, 4);
        assertEquals(1, sub3.size());
        assertEquals(d, sub3.get(0));
        
        List<JsonValue> sub4 = arr.subList(0, 4);
        assertEquals(arr, sub4);
    }

    
    
    public void testUnsupported() {
        unsupported(() -> getArray("[]").add(null));
        unsupported(() -> getArray("[]").remove(null));
        unsupported(() -> getArray("[]").addAll(null));
        unsupported(() -> getArray("[]").addAll(1, null));
        unsupported(() -> getArray("[]").removeAll(null));
        unsupported(() -> getArray("[]").retainAll(null));
        unsupported(() -> getArray("[]").clear());
        unsupported(() -> getArray("[]").set(1, null));
        unsupported(() -> getArray("[]").add(null));
        unsupported(() -> getArray("[]").add(1, (JsonValue)null));
        unsupported(() -> getArray("[]").remove(1));
        iob(() -> getArray("[]").listIterator(-10));
        iob(() -> getArray("[]").listIterator(10));
    }
    
    public void testBadUnwrap() {
      Wrapper w = (Wrapper)getArray("[]");
      try {
        w.unwrap(String.class);
        fail();
      } catch (SQLException e) {
        assertEquals("Cannot cast oracle.jdbc.driver.json.binary.OsonArrayImpl to java.lang.String", e.getMessage());
      }
    }
    
    private void cce(Runnable r) {
        try {
            r.run();
            fail();
        } catch (ClassCastException cce) {

        }
    }
    
    private void iob(Runnable r) {
        try {
            r.run();
            fail();
        } catch (IndexOutOfBoundsException iob) {
            
        }
    }
    
    private void unsupported(Runnable r) {
        try {
            r.run();
            fail();
        } catch (UnsupportedOperationException e) {
            
        }
    }
    
    private JsonArray getArray(String jzn) {
        return (JsonArray) getValue(jzn);
    }

    private JsonValue getValue(String jzn) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        FACT.createJsonBinaryGenerator(bytes)
            .write(FACT.createJsonTextValue(new StringReader(jzn))).close();
        return FACT.createJsonBinaryValue(ByteBuffer.wrap(bytes.toByteArray())).wrap(JsonValue.class);
    }
}

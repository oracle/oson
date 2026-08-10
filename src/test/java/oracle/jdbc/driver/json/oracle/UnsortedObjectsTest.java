/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2020, 2026, Oracle and/or its affiliates. */

package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl;
import oracle.jdbc.driver.json.binary.OsonObjectImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonValue;

/**
 */
public class UnsortedObjectsTest extends JsonTestCase {
  
  public static void testUnsorted() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = (OsonGeneratorImpl) f.createJsonBinaryGenerator(baos);
    writeUnsortedObject(gen);
    gen.close();
    byte[] oson = baos.toByteArray();
    
    OsonObjectImpl obj = (OsonObjectImpl) f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();
    for (int i = 0; i < 100; i++) {
      assertEquals(obj.get(i), obj.get(String.valueOf(i)));
    }
    String expected  = "{\"0\":\"0\",\"1\":\"1\",\"2\":\"2\",\"3\":\"3\"";
    assertEquals(expected, obj.toString().substring(0, expected.length()));
  }

  private static void writeUnsortedObject(OracleJsonGenerator gen) {
    writeObject(gen, false);
  }
  
  private static void writeObject(OracleJsonGenerator gen, boolean sorted) {
    ((OsonGeneratorImpl)gen).writeStartObject(sorted);
    for (int i = 0; i < 100; i++) {
      String s = String.valueOf(i);
      gen.write(s, s);
    }
    gen.writeEnd();
  }
  
  public static void testNestedUnsorted() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = (OsonGeneratorImpl) f.createJsonBinaryGenerator(baos);
    gen.writeStartObject();
    gen.writeKey("wrapper");
    writeUnsortedObject(gen);
    gen.writeEnd();
    gen.close();
    byte[] oson = baos.toByteArray();
    
    OsonObjectImpl wrapper = (OsonObjectImpl) f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();
    OsonObjectImpl obj = (OsonObjectImpl) wrapper.get("wrapper");
    for (int i = 0; i < 100; i++) {
      assertEquals(obj.get(i), obj.get(String.valueOf(i)));
    }
    String expected  = "{\"0\":\"0\",\"1\":\"1\",\"2\":\"2\",\"3\":\"3\"";
    assertEquals(expected, obj.toString().substring(0, expected.length()));
  }
  
  public static void testNoFidSharing() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = (OsonGeneratorImpl) f.createJsonBinaryGenerator(baos);
    gen.writeStartArray();
    writeUnsortedObject(gen);
    writeObject(gen, true);
    writeUnsortedObject(gen);
    writeObject(gen, true);
    writeUnsortedObject(gen);
    writeObject(gen, true);
    gen.writeEnd();
    gen.close();
    byte[] oson = baos.toByteArray();
    
    OracleJsonArray arr =  f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonArray();
    for (int k = 0; k < arr.size(); k+=2) {
      OracleJsonValue v = arr.get(k);
      OsonObjectImpl obj = (OsonObjectImpl) v;
      for (int i = 0; i < 100; i++) {
        assertEquals(obj.get(i), obj.get(String.valueOf(i)));
      }
      String expected  = "{\"0\":\"0\",\"1\":\"1\",\"2\":\"2\",\"3\":\"3\"";
      assertEquals(expected, obj.toString().substring(0, expected.length()));
    }
  }
  
}

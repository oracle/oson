// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

import oracle.jdbc.driver.VectorData;
import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonValue.OracleJsonType;
import oracle.sql.json.OracleJsonVector;

/**
 */
public class OracleJsonVectorTest extends JsonTestCase {
  
  static OracleJsonFactory FACT = new OracleJsonFactory();
  
  public void testOracleJsonVectorMethods() throws SQLException {

    byte[] oson = getBasicOson();
    OracleJsonValue v = FACT.createJsonBinaryValue(ByteBuffer.wrap(oson));
    OracleJsonObject obj = v.asJsonObject();
    OracleJsonVector vector = obj.get("embedding").asJsonVector();
    assertTrue(vector.getOracleJsonType() == OracleJsonType.VECTOR);
    assertEquals("[1.0,2.0,3.0]", vector.toString());
    
    assertEquals("[1.0, 2.0, 3.0]", Arrays.toString(vector.getDoubleArray()));
    assertEquals("{\"embedding\":[1.0,2.0,3.0]}", obj.toString());
  }
    
  public void testOracleJsonGenerator() {
    byte[] oson = getBasicOson();
    OracleJsonValue v = FACT.createJsonBinaryValue(ByteBuffer.wrap(oson));
    OracleJsonVector vector = v.asJsonObject().get("embedding").asJsonVector();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = FACT.createJsonBinaryGenerator(baos);
    gen.writeStartObject();
    gen.writeKey("embedding");
    gen.write(vector);
    gen.writeEnd();
    gen.close();
    byte[] oson2 = baos.toByteArray();
    assertTrue(Arrays.equals(oson, oson2));

    OracleJsonValue v2 = FACT.createJsonBinaryValue(ByteBuffer.wrap(oson2));
    OracleJsonObject obj2 = v2.asJsonObject();
    OracleJsonVector vector2 = obj2.get("embedding").asJsonVector();
    assertTrue(vector2.getOracleJsonType() == OracleJsonType.VECTOR);
  }

    
  public void testOracleJsonParser() {
    byte[] oson = getBasicOson();
    OracleJsonParser parser = FACT.createJsonBinaryParser(ByteBuffer.wrap(oson));
    assertEquals(parser.next(), OracleJsonParser.Event.START_OBJECT);
    assertEquals(parser.next(), OracleJsonParser.Event.KEY_NAME);
    assertEquals(parser.next(), OracleJsonParser.Event.VALUE_VECTOR);
    assertEquals("[1.0,2.0,3.0]", parser.getValue().toString());
    assertEquals(parser.next(), OracleJsonParser.Event.END_OBJECT);
    parser.close();
    
    parser = FACT.createJsonBinaryParser(ByteBuffer.wrap(oson));
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = FACT.createJsonBinaryGenerator(baos);
    gen.writeParser(parser);
    gen.close();
    byte[] oson2 = baos.toByteArray();
    assertTrue(Arrays.equals(oson, oson2));
  
  }
  
  public void testCreateVectorInt() throws SQLException {
    OracleJsonVector vect = FACT.createVector(new byte[] { 5,8,9 });
    OracleJsonObject o = FACT.createObject();
    o.put("vect", vect);
    assertEquals("{\"vect\":[5,8,9]}", o.toString());
    
    assertEquals(5d, vect.getDoubleArray()[0]);
    assertEquals(8d, vect.getDoubleArray()[1]);
    assertEquals(9d, vect.getDoubleArray()[2]);
    
    assertEquals(5, vect.getByteArray()[0]);
    assertEquals(8, vect.getByteArray()[1]);
    assertEquals(9, vect.getByteArray()[2]);

    assertEquals(5f, vect.getFloatArray()[0]);
    assertEquals(8f, vect.getFloatArray()[1]);
    assertEquals(9f, vect.getFloatArray()[2]);
    
  }
  
  public void testCreateVectorDouble() {
    OracleJsonVector vect = FACT.createVector(new double[] { 23.3, 45.6, 78.34 });
    OracleJsonObject o = FACT.createObject();
    o.put("vect", vect);
    assertEquals("{\"vect\":[23.3,45.6,78.34]}", o.toString());
    
    
    assertEquals(23.3d, vect.getDoubleArray()[0]);
    assertEquals(45.6d, vect.getDoubleArray()[1]);
    assertEquals(78.34d, vect.getDoubleArray()[2]);
    
    assertEquals(23, vect.getByteArray()[0]);
    assertEquals(45, vect.getByteArray()[1]);
    assertEquals(78, vect.getByteArray()[2]);

    assertEquals(23.3f, vect.getFloatArray()[0]);
    assertEquals(45.6f, vect.getFloatArray()[1]);
    assertEquals(78.34f, vect.getFloatArray()[2]);
  }
  
  public void testCreateVectorFloat() {
    OracleJsonVector vect = FACT.createVector(new float[] { 23.3f, 45.6f, 78.34f });
    OracleJsonObject o = FACT.createObject();
    o.put("vect", vect);
    assertEquals("{\"vect\":[23.3,45.6,78.34]}", o.toString());
    
    assertEquals((double)(23.3f), vect.getDoubleArray()[0]);
    assertEquals((double)(45.6f), vect.getDoubleArray()[1]);
    assertEquals((double)(78.34f), vect.getDoubleArray()[2]);
    
    assertEquals(23, vect.getByteArray()[0]);
    assertEquals(45, vect.getByteArray()[1]);
    assertEquals(78, vect.getByteArray()[2]);

    assertEquals(23.3f, vect.getFloatArray()[0]);
    assertEquals(45.6f, vect.getFloatArray()[1]);
    assertEquals(78.34f, vect.getFloatArray()[2]);
  }

  /**
   * Verifies the standalone VectorData helpers against the VECTOR encodings
   * used by JDBC. The direct VECTOR comparisons are omitted from the copied
   * OSON project; the remaining assertions are intentionally self-contained.
   */
  public void testStandaloneVectorDataCompatibility() throws SQLException {
    double[] doubles = { 1d, -2d, 3d };
    float[] floats = { 1f, -2f, 3f };
    byte[] bytes = { 1, -2, 3 };

    byte[] doubleData = VectorData.encode(doubles);
    byte[] floatData = VectorData.encode(floats);
    byte[] byteData = VectorData.encode(bytes);

    assertEquals("2wAAEgMAAAADwA3u6hFoP0m/8AAAAAAAAD//////////wAgAAAAAAAA=",
      Base64.getEncoder().encodeToString(doubleData));
    assertEquals("2wAAEgIAAAADwA3u6hFoP0m/gAAAP////8BAAAA=",
      Base64.getEncoder().encodeToString(floatData));
    assertEquals("2wAAEgQAAAADwA3u6hFoP0kB/gM=",
      Base64.getEncoder().encodeToString(byteData));

    assertTrue(VectorData.isFloat32(floatData));
    assertFalse(VectorData.isFloat32(doubleData));
    assertTrue(VectorData.isInt8(byteData));
    assertFalse(VectorData.isInt8(floatData));
    try {
      VectorData.isInt8(new byte[] { 0 });
      fail("Expected SQLException for malformed VECTOR encoding");
    }
    catch (SQLException expected) {
    }

    assertTrue(Arrays.equals(doubles,
      VectorData.decode(doubleData, double[].class, false)));
    assertTrue(Arrays.equals(floats,
      VectorData.decode(doubleData, float[].class, false)));
    assertTrue(Arrays.equals(bytes,
      VectorData.decode(doubleData, byte[].class, false)));
    assertTrue(Arrays.equals(floats,
      VectorData.decode(floatData, float[].class, false)));
    assertTrue(Arrays.equals(new double[] { 1d, -2d, 3d },
      VectorData.decode(floatData, double[].class, false)));
    assertTrue(Arrays.equals(bytes,
      VectorData.decode(floatData, byte[].class, false)));
    assertTrue(Arrays.equals(new double[] { 1d, -2d, 3d },
      VectorData.decode(byteData, double[].class, false)));
    assertTrue(Arrays.equals(floats,
      VectorData.decode(byteData, float[].class, false)));
    assertTrue(Arrays.equals(bytes,
      VectorData.decode(byteData, byte[].class, false)));

    byte[] sameFloatData = VectorData.encode(new float[] { 1f, -2f, 3f });
    assertTrue(VectorData.equals(floatData, sameFloatData));
    assertFalse(VectorData.equals(floatData, doubleData));
    assertFalse(VectorData.equals(floatData,
      VectorData.encode(new float[] { 1f, -2f, 4f })));
    assertEquals(VectorData.hashCode(floatData),
      VectorData.hashCode(sameFloatData));

      }

  public void testStandaloneVectorDataRejectsTruncatedPayload()
    throws SQLException {
    byte[] data = VectorData.encode(new double[] { 1d });
    byte[] truncated = Arrays.copyOf(data, data.length - 1);

    try {
      VectorData.decode(truncated, double[].class, false);
      fail("Expected decode failure for truncated VECTOR payload");
    }
    catch (ArrayIndexOutOfBoundsException expected) {
    }
  }
  
  // {"embedding":vector([1,2,3])}
  private byte[] getBasicOson() {
    String base64 = "/0paASEGAQAKACgAAPIAAAllbWJlZGRpbmeEAQEABXsBAAAAHdsAAAYCAAAAA8AN7uoRaD9Jv4AAAMAAAADAQAAA";
    byte[] oson = Base64.getDecoder().decode(base64);
    return oson;
  }
  
}

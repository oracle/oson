/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2024, 2026, Oracle and/or its affiliates. */
package oracle.jdbc.driver.json.jakarta;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Base64;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonParser;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.tree.OracleJsonBinaryImpl;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonVector;

public class JakartaVectorTest extends JsonTestCase {
  
  static OracleJsonFactory FACT = new OracleJsonFactory();
  
  public void testJakartaParser() {
    byte[] oson = getBasicOson();
    OracleJsonParser parser = FACT.createJsonBinaryParser(ByteBuffer.wrap(oson));
    JsonParser jparser = parser.wrap(JsonParser.class);
    assertEquals(jparser.next(), JsonParser.Event.START_OBJECT);
    assertEquals(jparser.next(), JsonParser.Event.KEY_NAME);

    assertEquals(jparser.next(), JsonParser.Event.START_ARRAY);
    JsonArray arr = jparser.getArray();
    assertEquals("[1.0, 2.0, 3.0]", arr.toString());
    assertEquals("[1.0, 2.0, 3.0]", jparser.getValue().toString());
    
    assertEquals(jparser.next(), JsonParser.Event.VALUE_NUMBER);
    assertEquals(jparser.getInt(), 1);
    assertEquals(jparser.getLong(), 1);
    assertEquals(((JsonNumber)jparser.getValue()).doubleValue(), 1d);
    assertEquals(jparser.getBigDecimal(), BigDecimal.valueOf(1d));
    
    assertEquals(jparser.next(), JsonParser.Event.VALUE_NUMBER);
    assertEquals(jparser.getInt(), 2);
    assertEquals(jparser.getLong(), 2);
    assertEquals(((JsonNumber)jparser.getValue()).doubleValue(), 2d);
    assertEquals(jparser.getBigDecimal(), BigDecimal.valueOf(2d));
    
    assertEquals(jparser.next(), JsonParser.Event.VALUE_NUMBER);
    assertEquals(jparser.getInt(), 3);
    assertEquals(jparser.getLong(), 3);
    assertEquals(((JsonNumber)jparser.getValue()).doubleValue(), 3d);
    assertEquals(jparser.getBigDecimal(), BigDecimal.valueOf(3d));
    
    assertEquals(jparser.next(), JsonParser.Event.END_ARRAY);
    try {
      jparser.getInt();
      fail();
    } catch (IllegalStateException e) {
      
    }
    
    try {
      jparser.getObject();
      fail();
    } catch (IllegalStateException e) {
      
    }
    
    try {
      jparser.getArray();
      fail();
    } catch (IllegalStateException e) {
      
    }
    assertEquals(jparser.next(), JsonParser.Event.END_OBJECT);
    parser.close();
  }
  
  public void testJakartaTree() throws SQLException {
    byte[] oson = getBasicOson();
    OracleJsonValue obj = FACT.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();
    JsonValue jakarta = obj.wrap(JsonValue.class);
    JsonObject jakartaObj = jakarta.asJsonObject();
    JsonArray jakartaArray = jakartaObj.get("embedding").asJsonArray();
    for (JsonValue jv : jakartaArray) {
      assertEquals(JsonValue.ValueType.NUMBER, jv.getValueType());
    }
    assertEquals(2d, jakartaArray.getJsonNumber(1).doubleValue());
    assertEquals("[1.0, 2.0, 3.0]", jakartaArray.toString());
    OracleJsonVector unwrapped = ((java.sql.Wrapper)jakartaArray).unwrap(OracleJsonVector.class);
    assertEquals("[1.0,2.0,3.0]", unwrapped.toString());
  }
  
  // {"embedding":vector([1,2,3])}
  private byte[] getBasicOson() {
    String base64 = "/0paASEGAQAKACgAAPIAAAllbWJlZGRpbmeEAQEABXsBAAAAHdsAAAYCAAAAA8AN7uoRaD9Jv4AAAMAAAADAQAAA";
    byte[] oson = Base64.getDecoder().decode(base64);
    return oson;
  }
  
}

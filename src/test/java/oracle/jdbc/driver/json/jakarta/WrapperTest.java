// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.jakarta;

import java.nio.ByteBuffer;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.sql.SQLException;
import java.sql.Wrapper;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonValue.OracleJsonType;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class WrapperTest extends JsonTestCase {
  
  public void testObject() throws SQLException {
    String jzn = "{\"hello\":\"world\"}";
    OracleJsonObject obj = (OracleJsonObject)getOracleValue(jzn);
    OracleJsonString str = (OracleJsonString)obj.get("hello");
    assertEquals("world", str.getString());
    assertEquals(OracleJsonType.OBJECT, obj.getOracleJsonType());
    
    JsonObject jobj = obj.wrap(JsonObject.class);
    assertEquals("world", jobj.getString("hello"));
    assertEquals(ValueType.OBJECT, jobj.getValueType());
    
    Wrapper wobj = (Wrapper)jobj;
    obj = (OracleJsonObject)wobj.unwrap(OracleJsonObject.class);
    str = (OracleJsonString)obj.get("hello");
    assertEquals("world", str.getString());
    assertEquals(OracleJsonType.OBJECT, obj.getOracleJsonType());
    
    wobj = (Wrapper)getJsonValue(jzn);
    obj = (OracleJsonObject)wobj.unwrap(OracleJsonObject.class);
    str = (OracleJsonString)obj.get("hello");
    assertEquals("world", str.getString());
    assertEquals(OracleJsonType.OBJECT, obj.getOracleJsonType());
  }
  
  public void testArray() throws SQLException {
    String jzn = "[{\"hello\":\"world\"}]";
    OracleJsonArray arr = (OracleJsonArray)getOracleValue(jzn);
    OracleJsonObject obj = (OracleJsonObject)arr.get(0);
    assertEquals("world", obj.getString("hello"));
    assertEquals(OracleJsonType.ARRAY, arr.getOracleJsonType());
    assertEquals(1, arr.size());
    
    JsonArray jarr = arr.wrap(JsonArray.class);
    JsonObject jobj = jarr.getJsonObject(0);
    assertEquals("world", jobj.getString("hello"));
    assertEquals(ValueType.OBJECT, jobj.getValueType());
    assertEquals(ValueType.ARRAY, jarr.getValueType());
    assertEquals(1, jarr.size());
    
    Wrapper wobj = (Wrapper)jarr;
    arr = (OracleJsonArray)wobj.unwrap(OracleJsonArray.class);
    obj = (OracleJsonObject)arr.get(0);
    assertEquals("world", obj.getString("hello"));
    assertEquals(OracleJsonType.OBJECT, obj.getOracleJsonType());
    assertEquals(OracleJsonType.ARRAY, arr.getOracleJsonType());
    
    wobj = (Wrapper)getJsonValue(jzn);
    arr = (OracleJsonArray)wobj.unwrap(OracleJsonArray.class);
    obj = (OracleJsonObject)arr.get(0);
    assertEquals("world", obj.getString("hello"));
    assertEquals(OracleJsonType.OBJECT, obj.getOracleJsonType());
    assertEquals(OracleJsonType.ARRAY, arr.getOracleJsonType());
  }
  
  protected OracleJsonValue getOracleValue(String json) {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonValue textValue = f.createJsonTextValue(new StringReader(json));
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    f.createJsonBinaryGenerator(bytes).write(textValue).close();
    return f.createJsonBinaryValue(ByteBuffer.wrap(bytes.toByteArray()));
  }
  
  protected JsonValue getJsonValue(String json) {
    return getOracleValue(json).wrap(JsonValue.class);
  }
  
}

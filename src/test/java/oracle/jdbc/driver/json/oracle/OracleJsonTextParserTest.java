// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonParser.Event;

/**
 *  @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonTextParserTest extends JsonTestCase {

  OracleJsonFactory factory;

  public OracleJsonTextParserTest() {
    this.factory = new OracleJsonFactory();
  }

  private OracleJsonParser createParser(String json) {
    return createParser(new StringReader(json));
  }

  private OracleJsonParser createParser(Reader json) {
    return factory.createJsonTextParser(json);
  }

  public void testInvalidToken() {
    OracleJsonParser parser = createParser("{\n[]}");
    try {
      parser.next();
      parser.next();
      fail();
    } catch (Exception e) {
      assertEquals("ORA-26328: Invalid token SQUAREOPEN at line 2, column 1. Expected tokens are: [STRING].", e.getMessage());
    }
  }

  public void testUnexpectedCharacter() {
    OracleJsonParser parser = createParser("\n\n      $");
    try {
      parser.next();
      fail();
    } catch (Exception e) {
      assertEquals("ORA-26326: Unexpected character '$' at line 3, column 7.", e.getMessage());
    }
  }

  public void testUnexpectedCharacter2() {
    OracleJsonParser parser = createParser("\n\n      \u00b7");
    try {
      parser.next();
      fail();
    } catch (Exception e) {
      assertEquals("ORA-26326: Unexpected character 0xb7 at line 3, column 7.", e.getMessage());
    }
  }

  public void testExpectedCharacter() {
    OracleJsonParser parser = createParser("\n{\"asdf\":\n            truue}");
    try {
      parser.next();
      parser.next();
      parser.next();
      parser.next();
      parser.next();
      fail();
    } catch (Exception e) {
      assertEquals("ORA-26327: Unexpected character 'u' at line 3, column 16. Expected: 'e'.", e.getMessage());
    }
  }

  public void testSimple() {
    OracleJsonParser p = createParser("{\"hello\":123}");
    assertTrue(p.hasNext());
    assertEquals(Event.START_OBJECT, p.next());
    assertTrue(p.hasNext());
    assertEquals(Event.KEY_NAME, p.next());
    assertEquals("hello", p.getString());
    assertTrue(p.hasNext());
    assertEquals(Event.VALUE_DECIMAL, p.next());
    assertEquals(BigInteger.valueOf(123), p.getBigInteger());
    assertEquals(123, p.getInt());
    assertEquals(123d, p.getDouble());
    assertEquals(123f, p.getFloat());
    assertTrue(p.hasNext());
    assertEquals(Event.END_OBJECT, p.next());
    assertFalse(p.hasNext());
    p.close();
  }

  public void testSimpleValue() {
    String j = "{\"hello\":123}";
    OracleJsonParser p = createParser(j);
    p.next();
    OracleJsonObject obj = p.getValue().asJsonObject();
    p.close();
    assertEquals(obj.getInt("hello"), 123);


    OracleJsonObject obj2 = factory.createJsonTextValue(new StringReader(j))
                            .asJsonObject();
    assertEquals(obj2.getInt("hello"), 123);

    OracleJsonParser p3 = createParser(j);
    p3.next();
    OracleJsonObject obj3 = p3.getObject();
    p3.close();
    assertEquals(obj3.getInt("hello"), 123);
  }

  public void testSimpleArray() {
    String j = "[1, 2, 3]";
    OracleJsonParser p = createParser(j);
    p.next();
    OracleJsonArray arr = p.getValue().asJsonArray();
    p.close();
    assertEquals(arr.getInt(1), 2);


    OracleJsonArray obj2 = factory.createJsonTextValue(new StringReader(j))
                           .asJsonArray();
    assertEquals(obj2.getInt(1), 2);

    OracleJsonParser p3 = createParser(j);
    p3.next();
    OracleJsonArray obj3 = p3.getArray();
    p3.close();
    assertEquals(obj3.getInt(1), 2);

  }


}

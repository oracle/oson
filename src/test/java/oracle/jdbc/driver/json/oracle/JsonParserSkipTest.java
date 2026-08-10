/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.oracle;


import java.io.Reader;
import java.io.StringReader;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonParser;

/**
 *
 * @author lukas
 */
public class JsonParserSkipTest extends JsonTestCase {

  OracleJsonFactory factory;
  
  public JsonParserSkipTest() {
    this.factory = new OracleJsonFactory();
  }
  
  private OracleJsonParser createParser(Reader json) {
    return factory.createJsonTextParser(json);
  }

  public void testSkipArrayReader() {
    try (OracleJsonParser parser = createParser(new StringReader("[[],[[]]]"))) {
      testSkipArray(parser);
    }
  }

  private static void testSkipArray(OracleJsonParser parser) {
    assertEquals(OracleJsonParser.Event.START_ARRAY, parser.next());
    parser.skipArray();
    assertEquals(false, parser.hasNext());
  }

  public void testSkipArrayInObjectReader() {
    try (OracleJsonParser parser = createParser(new StringReader("{\"array\":[[],[[]]],\"object\":\"value2\"}"))) {
      testSkipArrayInObject(parser);
    }
  }

  private static void testSkipArrayInObject(OracleJsonParser parser) {
    assertEquals(OracleJsonParser.Event.START_OBJECT, parser.next());
    assertEquals(OracleJsonParser.Event.KEY_NAME, parser.next());
    assertEquals(OracleJsonParser.Event.START_ARRAY, parser.next());
    parser.skipArray();
    assertTrue(parser.hasNext());
    assertEquals(OracleJsonParser.Event.KEY_NAME, parser.next());
    assertEquals(OracleJsonParser.Event.VALUE_STRING, parser.next());
    assertEquals(OracleJsonParser.Event.END_OBJECT, parser.next());
    assertFalse(parser.hasNext());
  }

  public void testSkipObjectReader() {
    try (OracleJsonParser parser = createParser(new StringReader("{\"array\":[],\"objectToSkip\":{\"huge key\":\"huge value\"},\"simple\":2}"))) {
      testSkipObject(parser);
    }
  }

  private static void testSkipObject(OracleJsonParser parser) {
    assertEquals(OracleJsonParser.Event.START_OBJECT, parser.next());
    assertEquals(OracleJsonParser.Event.KEY_NAME, parser.next());
    assertEquals(OracleJsonParser.Event.START_ARRAY, parser.next());
    assertEquals(OracleJsonParser.Event.END_ARRAY, parser.next());
    assertEquals(OracleJsonParser.Event.KEY_NAME, parser.next());
    assertEquals(OracleJsonParser.Event.START_OBJECT, parser.next());
    parser.skipObject();
    assertEquals(OracleJsonParser.Event.KEY_NAME, parser.next());
    assertEquals(OracleJsonParser.Event.VALUE_DECIMAL, parser.next());
    assertEquals(OracleJsonParser.Event.END_OBJECT, parser.next());
    assertEquals(false, parser.hasNext());
  }
}

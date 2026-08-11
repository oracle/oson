// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.Reader;
import java.io.StringReader;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.parser.JsonLocationImpl;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonParser;

/**
 * JsonParsingException Tests
 *
 * @author Jitendra Kotamraju
 */
public class JsonParsingExceptionTest extends JsonTestCase {
  
  OracleJsonFactory factory;
  
  public JsonParsingExceptionTest() {
    this.factory = new OracleJsonFactory();
  }
  
  private OracleJsonParser createParser(Reader json) {
    return factory.createJsonTextParser(json);
  }

  public void testWrongJson3() {
    testMalformedJson("{[]", null);
  }

  public void testWrongJson4() {
    testMalformedJson("{]", null);
  }

  public void testWrongJson5() {
    testMalformedJson("{\"a\":[]]", null);
  }

  public void testWrongJson6() {
    testMalformedJson("[ {}, [] }", null);
  }

  public void testWrongJson61() {
    testMalformedJson("[ {}, {} }", null);
  }

  public void testWrongJson7() {
    testMalformedJson("{ \"a\" : {}, \"b\": {} ]", null);
  }

  public void testWrongJson8() {
    testMalformedJson("{ \"a\" : {}, \"b\": [] ]", null);
  }

  public void testWrongUnicode() {
    testMalformedJson("[ \"\\uX00F\" ]", null);
    testMalformedJson("[ \"\\u000Z\" ]", null);
    testMalformedJson("[ \"\\u000\" ]", null);
    testMalformedJson("[ \"\\u00\" ]", null);
    testMalformedJson("[ \"\\u0\" ]", null);
    testMalformedJson("[ \"\\u\" ]", null);
    testMalformedJson("[ \"\\u\"", null);
    testMalformedJson("[ \"\\", null);
  }

  public void testControlChar() {
    testMalformedJson("[ \"\u0000\" ]", null);
    testMalformedJson("[ \"\u000c\" ]", null);
    testMalformedJson("[ \"\u000f\" ]", null);
    testMalformedJson("[ \"\u001F\" ]", null);
    testMalformedJson("[ \"\u001f\" ]", null);
  }

  public void testLocation1() {
    testMalformedJson("x", new JsonLocationImpl(1, 1, 0));
    testMalformedJson("{]", new JsonLocationImpl(1, 2, 1));
    testMalformedJson("[}", new JsonLocationImpl(1, 2, 1));
    testMalformedJson("[a", new JsonLocationImpl(1, 2, 1));
    testMalformedJson("[nuLl]", new JsonLocationImpl(1, 4, 3));
    testMalformedJson("[falsE]", new JsonLocationImpl(1, 6, 5));
    // testMalformedJson("[][]", new MyLocation(1, 3, 2));   allowed in 1.1
    testMalformedJson("[1234L]", new JsonLocationImpl(1, 6, 5));
  }

  public void testLocation2() {
    testMalformedJson("[null\n}", new JsonLocationImpl(2, 1, 6));
    testMalformedJson("[null\r\n}", new JsonLocationImpl(2, 1, 7));
    testMalformedJson("[null\n, null\n}", new JsonLocationImpl(3, 1, 13));
    testMalformedJson("[null\r\n, null\r\n}", new JsonLocationImpl(3, 1, 15));
  }

  private void testMalformedJson(String json, JsonLocationImpl expected) {
    OracleJsonParser parser = null;
    try {
      parser = createParser(new StringReader(json));
      while (parser.hasNext()) {
        parser.next();
      }
      fail("Expected to throw JsonParsingException for "+json);
    } catch(OracleJsonException je) {
      // Expected
      /*
      if (expected != null) {

        assertEquals(expected.getLineNumber(), got.getLineNumber());
        assertEquals(expected.getColumnNumber(), got.getColumnNumber());
        assertEquals(expected.getStreamOffset(), got.getStreamOffset());
      }*/
    } finally {
      if (parser != null) {
          parser.close();
      }
    }
  }


}

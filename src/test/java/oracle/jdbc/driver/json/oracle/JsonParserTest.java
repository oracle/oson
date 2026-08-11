// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonParser.Event;


/**
 * OracleJsonParser Tests
 *
 * @author Jitendra Kotamraju
 */
public class JsonParserTest extends JsonTestCase {
  static final Charset UTF_8 = Charset.forName("UTF-8");
  static final Charset UTF_16BE = Charset.forName("UTF-16BE");
  static final Charset UTF_16LE = Charset.forName("UTF-16LE");
  static final Charset UTF_16 = Charset.forName("UTF-16");
  static final Charset UTF_32LE = Charset.forName("UTF-32LE");
  static final Charset UTF_32BE = Charset.forName("UTF-32BE");

  static final String WIKI = "{\n" + 
      "     \"firstName\": \"John\",\n" + 
      "     \"lastName\": \"Smith\",\n" + 
      "     \"age\": 25,\n" + 
      "     \"address\": {\n" + 
      "         \"streetAddress\": \"21 2nd Street\",\n" + 
      "         \"city\": \"New York\",\n" + 
      "         \"state\": \"NY\",\n" + 
      "         \"postalCode\": \"10021\"\n" + 
      "     },\n" + 
      "     \"phoneNumber\": [\n" + 
      "         {\n" + 
      "           \"type\": \"home\",\n" + 
      "           \"number\": \"212 555-1234\"\n" + 
      "         },\n" + 
      "         {\n" + 
      "           \"type\": \"fax\",\n" + 
      "           \"number\": \"646 555-4567\"\n" + 
      "         }\n" + 
      "     ]\n" + 
      "}\n" + 
      "";
  
  OracleJsonFactory factory;
  
  public JsonParserTest() {
    this.factory = new OracleJsonFactory();
  }
  
  private OracleJsonParser createParser(Reader json) {
    return factory.createJsonTextParser(json);
  }
  
  private OracleJsonParser createParser(InputStream json) {
    return factory.createJsonTextParser(json);
  }

  public void testReader() {
    OracleJsonParser reader = createParser(
        new StringReader("{ \"a\" : \"b\", \"c\" : null, \"d\" : [null, \"abc\"] }"));
    reader.close();
  }


  public void testEmptyArrayReader() {
    try (OracleJsonParser parser = createParser(new StringReader("[]"))) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStream() {
    try (OracleJsonParser parser = createParser(
        new ByteArrayInputStream(new byte[]{'[', ']'}))) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStreamUTF8() {
    ByteArrayInputStream bin = new ByteArrayInputStream("[]".getBytes(UTF_8));
    try (OracleJsonParser parser = createParser(bin)) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStreamUTF16LE() {
    ByteArrayInputStream bin = new ByteArrayInputStream("[]".getBytes(UTF_16LE));
    try (OracleJsonParser parser = createParser(bin)) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStreamUTF16BE() {
    ByteArrayInputStream bin = new ByteArrayInputStream("[]".getBytes(UTF_16BE));
    try (OracleJsonParser parser = createParser(bin)) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStreamUTF32LE() {
    ByteArrayInputStream bin = new ByteArrayInputStream("[]".getBytes(UTF_32LE));
    try (OracleJsonParser parser = createParser(bin)) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStreamUTF32BE() {
    ByteArrayInputStream bin = new ByteArrayInputStream("[]".getBytes(UTF_32BE));
    try (OracleJsonParser parser = createParser(bin)) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStreamUTF16() {
    ByteArrayInputStream bin = new ByteArrayInputStream("[]".getBytes(UTF_16));
    try (OracleJsonParser parser = createParser(bin)) {
      testEmptyArray(parser);
    }
  }

  public void testEmptyArrayStreamWithConfig() {
    try (OracleJsonParser parser = createParser(
        new ByteArrayInputStream(new byte[]{'[', ']'}))) {
      testEmptyArray(parser);
    }
  }

  private static void testEmptyArray(OracleJsonParser parser) {
    while (parser.hasNext()) {
      parser.next();
    }
  }


  public void testEmptyArrayIterator3Reader() {
    try (OracleJsonParser parser = createParser(new StringReader("[]"))) {
      testEmptyArrayIterator3(parser);
    }
  }

  static void testEmptyArrayIterator3(OracleJsonParser parser) {
    assertEquals(Event.START_ARRAY, parser.next());
    assertEquals(Event.END_ARRAY, parser.next());
    assertEquals(false, parser.hasNext());
    try {
      parser.next();
      fail("Should have thrown a NoSuchElementException");
    } catch (NoSuchElementException ne) {
    }
  }


  // Tests empty object
  public void testEmptyObjectReader() {
    try (OracleJsonParser parser = createParser(new StringReader("{}"))) {
      testEmptyObject(parser);
    }
  }

  public void testEmptyObjectStream() {
    try (OracleJsonParser parser = createParser(
        new ByteArrayInputStream(new byte[]{'{', '}'}))) {
      testEmptyObject(parser);
    }
  }

  static void testEmptyObject(OracleJsonParser parser) {
    while (parser.hasNext()) {
      parser.next();
    }
  }


  public void testEmptyObjectIteratorReader() {
    try (OracleJsonParser parser = createParser(new StringReader("{}"))) {
      testEmptyObjectIterator(parser);
    }
  }

  static void testEmptyObjectIterator(OracleJsonParser parser) {
    assertEquals(true, parser.hasNext());
    assertEquals(true, parser.hasNext());
    assertEquals(Event.START_OBJECT, parser.next());

    assertEquals(true, parser.hasNext());
    assertEquals(true, parser.hasNext());
    assertEquals(Event.END_OBJECT, parser.next());

    assertEquals(false, parser.hasNext());
    assertEquals(false, parser.hasNext());
    try {
      parser.next();
      fail("Should have thrown a NoSuchElementException");
    } catch (NoSuchElementException ne) {
    }
  }


  public void testEmptyObjectIterator2Reader() {
    try (OracleJsonParser parser = createParser(new StringReader("{}"))) {
      testEmptyObjectIterator2(parser);
    }
  }

  static void testEmptyObjectIterator2(OracleJsonParser parser) {
    assertEquals(Event.START_OBJECT, parser.next());
    assertEquals(Event.END_OBJECT, parser.next());
    try {
      parser.next();
      fail("Should have thrown a NoSuchElementException");
    } catch (NoSuchElementException ne) {
    }
  }


  public void testEmptyObjectIterator3Reader() {
    try (OracleJsonParser parser = createParser(new StringReader("{}"))) {
      testEmptyObjectIterator3(parser);
    }
  }

  static void testEmptyObjectIterator3(OracleJsonParser parser) {
    assertEquals(Event.START_OBJECT, parser.next());
    assertEquals(Event.END_OBJECT, parser.next());
    assertEquals(false, parser.hasNext());
    try {
      parser.next();
      fail("Should have thrown a NoSuchElementException");
    } catch (NoSuchElementException ne) {
      // expected
    }
  }


  public void testWikiIteratorReader() throws Exception {
    try (OracleJsonParser parser = createParser(wikiReader())) {
      testWikiIterator(parser);
    }
  }

  static void testWikiIterator(OracleJsonParser parser) throws Exception {
    while (parser.hasNext()) {
      parser.next();
    }
  }

  public void testWikiInputStream() throws Exception {
    try (OracleJsonParser parser = createParser(wikiStream())) {
      testWiki(parser);
    }
  }

  public void testWikiInputStreamUTF16LE() throws Exception {
    ByteArrayInputStream bin = new ByteArrayInputStream(wikiString()
        .getBytes(UTF_16LE));
    try (OracleJsonParser parser = createParser(bin)) {
      testWiki(parser);
    }
  }

  public void testWikiReader() throws Exception {
    try (OracleJsonParser parser = createParser(wikiReader())) {
      testWiki(parser);
    }
  }

  static void testWiki(OracleJsonParser parser) {

    Event event = parser.next();
    assertEquals(Event.START_OBJECT, event);

    testObjectStringValue(parser, "firstName", "John");
    testObjectStringValue(parser, "lastName", "Smith");

    event = parser.next();
    assertEquals(Event.KEY_NAME, event);
    assertEquals("age", parser.getString());

    event = parser.next();
    assertEquals(Event.VALUE_DECIMAL, event);
    assertEquals(25, parser.getInt());
    assertEquals(25, parser.getLong());
    assertEquals(25, parser.getBigDecimal().intValue());
    assertTrue( parser.isIntegralNumber());

    event = parser.next();
    assertEquals(Event.KEY_NAME, event);
    assertEquals("address", parser.getString());

    event = parser.next();
    assertEquals(Event.START_OBJECT, event);


    testObjectStringValue(parser, "streetAddress", "21 2nd Street");
    testObjectStringValue(parser, "city", "New York");
    testObjectStringValue(parser, "state", "NY");
    testObjectStringValue(parser, "postalCode", "10021");

    event = parser.next();
    assertEquals(Event.END_OBJECT, event);

    event = parser.next();
    assertEquals(Event.KEY_NAME, event);
    assertEquals("phoneNumber", parser.getString());

    event = parser.next();
    assertEquals(Event.START_ARRAY, event);
    event = parser.next();
    assertEquals(Event.START_OBJECT, event);
    testObjectStringValue(parser, "type", "home");
    testObjectStringValue(parser, "number", "212 555-1234");
    event = parser.next();
    assertEquals(Event.END_OBJECT, event);

    event = parser.next();
    assertEquals(Event.START_OBJECT, event);
    testObjectStringValue(parser, "type", "fax");
    testObjectStringValue(parser, "number", "646 555-4567");
    event = parser.next();
    assertEquals(Event.END_OBJECT, event);
    event = parser.next();
    assertEquals(Event.END_ARRAY, event);

    event = parser.next();
    assertEquals(Event.END_OBJECT, event);
  }

  static void testObjectStringValue(OracleJsonParser parser, String name, String value) {
    Event event = parser.next();
    assertEquals(Event.KEY_NAME, event);
    assertEquals(name, parser.getString());

    event = parser.next();
    assertEquals(Event.VALUE_STRING, event);
    assertEquals(value, parser.getString());
  }

  public void testNestedArrayReader() {
    try (OracleJsonParser parser = createParser(new StringReader("[[],[[]]]"))) {
      testNestedArray(parser);
    }
  }

  static void testNestedArray(OracleJsonParser parser) {
    assertEquals(Event.START_ARRAY, parser.next());
    assertEquals(Event.START_ARRAY, parser.next());
    assertEquals(Event.END_ARRAY, parser.next());
    assertEquals(Event.START_ARRAY, parser.next());
    assertEquals(Event.START_ARRAY, parser.next());
    assertEquals(Event.END_ARRAY, parser.next());
    assertEquals(Event.END_ARRAY, parser.next());
    assertEquals(Event.END_ARRAY, parser.next());
    assertEquals(false, parser.hasNext());
    assertEquals(false, parser.hasNext());
  }

  public void testExceptionsReader() throws Exception {
    try (OracleJsonParser parser = createParser(wikiReader())) {
      testExceptions(parser);
    }
  }

  static void testExceptions(OracleJsonParser parser) {

    Event event = parser.next();
    assertEquals(Event.START_OBJECT, event);

    try {
      parser.getString();
      fail("OracleJsonParser#getString() should have thrown exception in START_OBJECT state");
    } catch (IllegalStateException expected) {
      // no-op
    }

    try {
      parser.isIntegralNumber();
      fail("OracleJsonParser#getNumberType() should have thrown exception in START_OBJECT state");
    } catch (IllegalStateException expected) {
      // no-op
    }

    try {
      parser.getInt();
      fail("OracleJsonParser#getInt() should have thrown exception in START_OBJECT state");
    } catch (IllegalStateException expected) {
      // no-op
    }

    try {
      parser.getLong();
      fail("OracleJsonParser#getLong() should have thrown exception in START_OBJECT state");
    } catch (IllegalStateException expected) {
      // no-op
    }

    try {
      parser.getBigDecimal();
      fail("OracleJsonParser#getBigDecimal() should have thrown exception in START_OBJECT state");
    } catch (IllegalStateException expected) {
      // no-op
    }
  }

  static String wikiString() {
    String str;
    try (@SuppressWarnings("resource")
    Scanner scanner = new Scanner(wikiReader())
        .useDelimiter("\\A")) {
      str = scanner.hasNext() ? scanner.next() : "";
    }
    return str;
  }

  static InputStream wikiStream() {
    return new ByteArrayInputStream(WIKI.getBytes(UTF_8));
  }

  static Reader wikiReader() {
    return new StringReader(WIKI);
  }

  public void testIntNumber() {

    Random r = new Random(System.currentTimeMillis());

    for(int i=0; i < 100000; i++) {
      long num = i%2 == 0 ? r.nextInt() : r.nextLong();
      try (OracleJsonParser parser = createParser(new StringReader("["+num+"]"))) {
        parser.next();
        parser.next();
        assertEquals("Fails for num="+num, new BigDecimal(num).intValue(), parser.getInt());
      }
    }

  }

  // Tests for string starting on buffer boundary (JSONP-15)
  // xxxxxxx"xxxxxxxxx"
  //    ^
  //    |
  //     4096
  public void testStringUsingStandardBuffer() throws Throwable {
    StringBuilder sb = new StringBuilder();
    for(int i=0; i < 40000; i++) {
      sb.append('a');
      String name = sb.toString();
      String str = "{\""+name+"\":\""+name+"\"}";
      try (OracleJsonParser parser = createParser(new StringReader(str))) {
        parser.next();
        parser.next();
        assertEquals("Fails for size=" + i, name, parser.getString());
        parser.next();
        assertEquals("Fails for size=" + i, name, parser.getString());
      } catch (Throwable e) {
        throw new Throwable("Failed for size=" + i, e);
      }
    }
  }

  // Tests for int starting on buffer boundary
  // xxxxxxx"xxxxxxxxx"
  //    ^
  //    |
  //     4096
  public void testIntegerUsingStandardBuffer() throws Throwable {
    Random r = new Random(System.currentTimeMillis());
    StringBuilder sb = new StringBuilder();
    for(int i=0; i < 40000; i++) {
      sb.append('a');
      String name = sb.toString();
      int num = r.nextInt();
      String str = "{\"" + name + "\":" + num + "}";
      try (OracleJsonParser parser = createParser(new StringReader(str))) {
        parser.next();
        parser.next();
        assertEquals("Fails for size=" + i, name, parser.getString());
        parser.next();
        assertEquals("Fails for size=" + i, num, parser.getInt());
      } catch (Throwable e) {
        throw new Throwable("Failed for size=" + i, e);
      }
    }
  }

  public void testExceptionsFromHasNext() {
    checkExceptionFromHasNext("{");
    checkExceptionFromHasNext("{\"key\"");
    checkExceptionFromHasNext("{\"name\" : \"prop\"");
    checkExceptionFromHasNext("{\"name\" : 3");
    checkExceptionFromHasNext("{\"name\" : true");
    checkExceptionFromHasNext("{\"name\" : null");
    checkExceptionFromHasNext("{\"name\" : {\"$eq\":\"cdc\"}");
    checkExceptionFromHasNext("{\"name\" : [{\"$eq\":\"cdc\"}]");
    checkExceptionFromHasNext("[");
    checkExceptionFromHasNext("{\"name\" : [{\"key\" : [[{\"a\" : 1}]");
    checkExceptionFromHasNext("{\"unique\":true,\"name\":\"jUnitTestIndexNeg005\", \"fields\":[{\"order\":-1,\"path\":\"city.zip\"}");
  }

  public void testEOFFromHasNext() {
    checkExceptionFromHasNext("{ \"d\" : 1 } 2 3 4");
    checkExceptionFromHasNext("[ {\"d\" : 1 }] 2 3 4");
    checkExceptionFromHasNext("1 2 3 4");
    checkExceptionFromHasNext("null 2 3 4");
  }

  public void testExceptionsFromNext() {
    checkExceptionFromNext("{\"name\" : fal");
    checkExceptionFromNext("{\"name\" : nu");
    checkExceptionFromNext("{\"name\" : \"pro");
    checkExceptionFromNext("{\"key\":");
    checkExceptionFromNext("fal");
  }

  private void checkExceptionFromHasNext(String input) {
    try (OracleJsonParser parser = createParser(new StringReader(input))) {
      try {
        while (parser.hasNext()) {
          try {
            parser.next();
          } catch (Throwable t1) {
            fail("Exception should occur from hasNext() for '" + input + "'");
          }
        }
      } catch (OracleJsonException t) {
        //this is OK
        return;
      }
    }
    fail();
  }

  private void checkExceptionFromNext(String input) {
    try (OracleJsonParser parser = createParser(new StringReader(input))) {
      while (parser.hasNext()) {
        try {
          parser.next();
        } catch (OracleJsonException t) {
          //this is OK
          return;
        }
      }
    }
    fail();
  }
}

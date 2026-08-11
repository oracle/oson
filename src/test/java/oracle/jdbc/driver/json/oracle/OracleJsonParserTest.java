// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Arrays;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonParserImpl;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonParser.Event;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonValue.OracleJsonType;

/**
 * @since   release specific (what release of product did this appear in)
 */
public class OracleJsonParserTest extends JsonTestCase {
  
    
  public void testGetBytes() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    OracleJsonGenerator gen = f.createJsonBinaryGenerator(s);
    gen.writeStartArray();
    gen.write(new byte[] {1});
    
    
    byte[] bytes = new byte[1024];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte)(i % 100);
    }
    gen.write(bytes);
    
    gen.writeEnd();
    gen.close();
    
    OracleJsonParser p =  f.createJsonBinaryParser(ByteBuffer.wrap(s.toByteArray()));
    p.next();
    p.next();
    byte[] r = p.getBytes();
    assertTrue(Arrays.equals(new byte[] {1}, r));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    p.getBytes(baos);
    assertTrue(Arrays.equals(new byte[] {1}, baos.toByteArray()));
    
    p.next();
    
    r = p.getBytes();
    assertTrue(Arrays.equals(bytes, r));
    
    baos.reset();
    p.getBytes(baos);
    assertTrue(Arrays.equals(bytes, baos.toByteArray()));
    
    p.next();
    p.close();
  }
  
  public void testToEntry() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    OracleJsonGenerator gen = f.createJsonBinaryGenerator(s);
    gen.writeStartObject();
    gen.write("a", 1);
    gen.write("b", 2);
    gen.write("c", 3);
    gen.writeEnd();
    gen.close();
    
    OracleJsonParser p =  f.createJsonBinaryParser(ByteBuffer.wrap(s.toByteArray()));
    p.next();
    assertTrue(((OsonParserImpl)p).toEntry("b"));
    p.next();
    assertEquals(2, p.getInt());
    
    ((OsonParserImpl)p).reset();
    p.next();
    assertTrue(((OsonParserImpl)p).toEntry("c"));
    p.next();
    assertEquals(3, p.getInt());
    
    ((OsonParserImpl)p).reset();
    p.next();
    assertFalse(((OsonParserImpl)p).toEntry("x"));
    
    p.close();
  }
  
  }

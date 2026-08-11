// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl.DuplicateKeyMode;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerationException;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonValue.OracleJsonType;

/**
 */
public class OracleJsonGeneratorTest extends JsonTestCase {

  public void testBinary() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
    OracleJsonObject o = writeToGenerator(f, gen);
    byte[] oson = baos.toByteArray();
    OracleJsonObject obj = f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();
    assertEquals(OracleJsonType.STRING,     obj.get("k1").getOracleJsonType());
    assertEquals(OracleJsonType.DECIMAL,     obj.get("k2").getOracleJsonType());
    assertEquals(OracleJsonType.DOUBLE,     obj.get("k3").getOracleJsonType());
    assertEquals(OracleJsonType.FLOAT,      obj.get("k4").getOracleJsonType());
    assertEquals(OracleJsonType.NULL,       obj.get("k5").getOracleJsonType());
    assertEquals(OracleJsonType.TRUE,       obj.get("k6").getOracleJsonType());
    assertEquals(OracleJsonType.FALSE,      obj.get("k7").getOracleJsonType());
    assertEquals(OracleJsonType.TIMESTAMP,  obj.get("k8").getOracleJsonType());
    assertEquals(OracleJsonType.BINARY,     obj.get("k9").getOracleJsonType());
    assertEquals(OracleJsonType.ARRAY,      obj.get("k10").getOracleJsonType());
    assertEquals(o, obj.get("k11"));
    assertEquals(Period.of(1,  2,  0), obj.get("k12").asJsonIntervalYM().getPeriod());
    assertEquals(Duration.ofDays(3), obj.get("k13").asJsonIntervalDS().getDuration());
    assertEquals(obj.getInt("k14"), 123);
    assertEquals("[1]", obj.get("k15").toString());

    assertEquals(OracleJsonType.TIMESTAMPTZ, obj.get("k16").getOracleJsonType());
    assertEquals(OracleJsonType.TIMESTAMP, obj.get("k17").getOracleJsonType());
    assertEquals(OracleJsonType.TIMESTAMPTZ, obj.get("k18").getOracleJsonType());
  }

  public void testText() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = f.createJsonTextGenerator(baos);
    writeToGenerator(f, gen);
    String jzn = new String(baos.toByteArray());
    assertEquals("{\"k1\":\"string value\",\"k2\":123.456,\"k3\":123.345,\"k4\":12.1,"
                 + "\"k5\":null,\"k6\":true,\"k7\":false,\"k8\":\"1970-01-01T00:02:03.456000\","
                 + "\"k9\":\"010203\",\"k10\":[1,2,3],\"k11\":{\"hello\":\"world\"},"
                 + "\"k12\":\"P1Y2M\",\"k13\":\"P3D\",\"k14\":123,\"k15\":[1],"
                 + "\"k16\":\"1970-01-01T00:02:03.456000Z\","
                 + "\"k17\":\"1970-01-01T00:02:03.456000\","
                 + "\"k18\":\"1970-01-01T12:36:03.456000+12:34\"}", jzn);
  }

  private OracleJsonObject writeToGenerator(OracleJsonFactory f, OracleJsonGenerator gen) {
    gen.writeStartObject();

    // String
    gen.write("k1", "string value");

    // Oranum
    gen.write("k2", new BigDecimal("123.456"));

    // double
    gen.write("k3", 123.345d);

    // float
    gen.write("k4", 12.1f);

    // null
    gen.writeNull("k5");

    // true
    gen.write("k6", true);

    // false
    gen.write("k7", false);

    // timestamp
    LocalDateTime ldt = Instant.ofEpochMilli(123456).atOffset(ZoneOffset.UTC).toLocalDateTime();
    gen.write("k8", ldt);

    // binary
    gen.write("k9", new byte[] { 1, 2, 3});

    gen.writeStartArray("k10");
    gen.write(1);
    gen.write(2);
    gen.write(3);
    gen.writeEnd();

    OracleJsonObject o = f.createObject();
    o.put("hello", "world");
    gen.write("k11", o);


    gen.write("k12", Period.of(1, 2, 0));
    gen.write("k13", Duration.ofDays(3));
    gen.write("k14", f.createDecimal(123));


    ByteArrayOutputStream out = new ByteArrayOutputStream();
    OracleJsonGenerator tmp = f.createJsonBinaryGenerator(out);
    tmp.writeStartArray();
    tmp.write(1);
    tmp.writeEnd();
    tmp.close();
    OracleJsonParser parser = f.createJsonBinaryParser(ByteBuffer.wrap(out.toByteArray()));
    gen.writeKey("k15");
    gen.writeParser(parser);


    OffsetDateTime odt = Instant.ofEpochMilli(123456).atOffset(ZoneOffset.UTC);
    gen.writeKey("k16");
    gen.write(odt);

    gen.writeKey("k17");
    gen.write(odt.toLocalDateTime());

    OffsetDateTime k18 = Instant.ofEpochMilli(123456)
                         .atOffset(ZoneOffset.ofHoursMinutes(12, 34));
    gen.writeKey("k18");
    gen.write(k18);

    gen.writeEnd();
    gen.close();
    return o;
  }

  public void testTimestampMilliseconds9() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
    LocalDateTime ldt = Instant.ofEpochSecond(1, 123456789).atOffset(ZoneOffset.UTC).toLocalDateTime();
    gen.write(f.createTimestamp(ldt));
    gen.close();
    byte[] oson = baos.toByteArray();
        OracleJsonValue v = f.createJsonBinaryValue(ByteBuffer.wrap(oson));
      }

  public void testTimestampMilliseconds7() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
    LocalDateTime ldt = Instant.ofEpochSecond(1, 123456100).atOffset(ZoneOffset.UTC).toLocalDateTime();
    gen.write(ldt);
    gen.close();
    byte[] oson = baos.toByteArray();
        OracleJsonValue v = f.createJsonBinaryValue(ByteBuffer.wrap(oson));
      }

  public void testTimestampMilliseconds6() {
    OracleJsonFactory f = new OracleJsonFactory();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonGenerator gen = f.createJsonBinaryGenerator(baos);
    LocalDateTime ldt = Instant.ofEpochSecond(1, 123456).atOffset(ZoneOffset.UTC).toLocalDateTime();
    gen.write(f.createTimestamp(ldt));
    gen.close();
    byte[] oson = baos.toByteArray();
        OracleJsonValue v = f.createJsonBinaryValue(ByteBuffer.wrap(oson));
      }

  public void testDuplicateKeys1() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonFactory factory = new OracleJsonFactory();

    try {
      OracleJsonGenerator gen = factory.createJsonBinaryGenerator(baos);
      gen.writeStartObject();
      gen.write("x", 1);
      gen.write("y", 2);
      gen.write("x", 3);
      gen.writeEnd();
      gen.close();
    } catch (OracleJsonGenerationException e) {
      assertEquals("ORA-26323: JSON object contains duplicate key: x.", e.getMessage());
    }
  }

  public void testDuplicateKeys1Allow() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonFactory factory = new OracleJsonFactory();
    OracleJsonGenerator gen = factory.createJsonBinaryGenerator(baos);
    ((OsonGeneratorImpl)gen).setDuplicateKeyMode(DuplicateKeyMode.ALLOW);
    gen.writeStartObject();
    gen.write("x", 1);
    gen.write("y", 2);
    gen.write("x", 3);
    gen.writeEnd();
    gen.close();

    String jzn = factory.createJsonBinaryValue(ByteBuffer.wrap(baos.toByteArray())).toString();
    assertEquals("{\"x\":1,\"y\":2,\"x\":3}", jzn);
  }


  public void testDuplicateKeys2() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OracleJsonFactory factory = new OracleJsonFactory();
    try {
      OracleJsonGenerator gen = factory.createJsonBinaryGenerator(baos);
      gen.writeStartObject();
      for (int i = 0; i < 100; i++) {
        gen.write("x" + i, 1);
      }
      gen.write("x22", 1);
      gen.writeEnd();
      gen.close();
      fail();
    } catch (OracleJsonGenerationException e) {
      assertEquals("ORA-26323: JSON object contains duplicate key: x22.", e.getMessage());
    }
  }

  }

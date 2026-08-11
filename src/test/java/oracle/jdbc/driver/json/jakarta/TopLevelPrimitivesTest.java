// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.jakarta;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import jakarta.json.JsonNumber;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl;
import oracle.jdbc.driver.json.binary.OsonParserImpl;
import oracle.jdbc.driver.json.tree.OracleJsonNumberImpl;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonDouble;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonValue.OracleJsonType;


/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class TopLevelPrimitivesTest extends JsonTestCase {

  static OracleJsonFactory FACTORY = new OracleJsonFactory();
  
  public void testString() {
    String value = "hello world";
    String json = quotes(value);
    doPrimitiveTest(
      gen -> {
        gen.write(value);
        return json;
      },
      dom -> {
        assertEquals(ValueType.STRING, dom.getValueType());
        assertEquals(value, ((JsonString)dom).getString());
      },
      parser -> {
        assertEquals(Event.VALUE_STRING, parser.next());
        assertEquals(value, parser.getString());
      }
    );
  }
  
  public void testInt() {
    int value = 123;
    String json = String.valueOf(value);
    doPrimitiveTest(
      gen -> {
        gen.write(value);
        return json;
      },
      dom -> {
        assertEquals(ValueType.NUMBER, dom.getValueType());
        try {
          OracleJsonValue odom = ((java.sql.Wrapper)dom).unwrap(OracleJsonValue.class);
          assertEquals(OracleJsonType.DECIMAL, odom.getOracleJsonType());
        } catch (SQLException e) {
          throw new IllegalStateException(e);
        }
        
        assertEquals(value, ((JsonNumber)dom).intValue());
      },
      parser -> {
        assertEquals(Event.VALUE_NUMBER, parser.next());
        assertEquals(value, parser.getInt());
      }
    );
  }
  
  public void testDouble() {
    double value = 123.456;
    String json = String.valueOf(value);
    doPrimitiveTest(
      gen -> {
        gen.write(value);
        return json;
      },
      dom -> {
        assertEquals(ValueType.NUMBER, dom.getValueType());
        try {
          OracleJsonValue v = ((java.sql.Wrapper)dom).unwrap(OracleJsonValue.class);
          assertEquals(OracleJsonType.DOUBLE, v.getOracleJsonType());
        } catch (SQLException e) {
          throw new IllegalStateException(e);
        }
        assertEquals(value, ((JsonNumber)dom).doubleValue());
      },
      parser -> {
        assertEquals(Event.VALUE_NUMBER, parser.next());
        assertEquals(value, parser.getBigDecimal().doubleValue());
      }
    );
  }

  public void testTrue() {
    boolean value = true;
    String json = "true";
    doPrimitiveTest(
      gen -> {
        gen.write(value);
        return json;
      }, 
      dom -> {
        assertEquals(ValueType.TRUE, dom.getValueType());
        assertEquals(JsonValue.TRUE, dom);
        assertTrue(JsonValue.TRUE == dom);
      }, 
      parser -> {
        assertEquals(Event.VALUE_TRUE, parser.next());
      }
    );
  }
  
  public void testTimestamp() {
    LocalDateTime value = LocalDateTime.of(1981, 10, 11, 12, 1, 2, 444000);
    String json = "\"1981-10-11T12:01:02.000444\"";
    doPrimitiveTest(
      gen -> {
        ((OsonGeneratorImpl)gen).write(value);
        return json;
      },
      dom -> {
        assertEquals(ValueType.STRING, dom.getValueType());
        OracleJsonTimestamp ots;
        try {
          ots = ((java.sql.Wrapper)dom).unwrap(OracleJsonTimestamp.class);
        } catch (SQLException e) {
          throw new IllegalStateException(e);
        }
        LocalDateTime i2 = ots.getLocalDateTime();
        assertEquals(value, i2);
        
      },
      parser -> {
        assertEquals(Event.VALUE_STRING, parser.next());
      }
    );
  }
  
  public void testTimestamp7() {
    LocalDateTime value = LocalDateTime.of(1981, 10, 11, 12, 1, 2, 0);
    String json = "\"1981-10-11T12:01:02\"";
    doPrimitiveTest(
      gen -> {
        ((OsonGeneratorImpl)gen).write(value);
        return json;
      },
      dom -> {
        assertEquals(ValueType.STRING, dom.getValueType());
        OracleJsonTimestamp ots;
        try {
          ots = ((java.sql.Wrapper)dom).unwrap(OracleJsonTimestamp.class);
        } catch (SQLException e) {
          throw new IllegalStateException(e);
        }
        LocalDateTime i2 = ots.getLocalDateTime();
        assertEquals(value, i2);
        
      },
      parser -> {
        assertEquals(Event.VALUE_STRING, parser.next());
      }
    );
  } 
  
  
  public void testTimestampTZ() {
    OffsetDateTime value = LocalDateTime.of(1981, 10, 11, 12, 1, 2, 0).atOffset(ZoneOffset.UTC);
    String json = "\"1981-10-11T12:01:02.000000Z\"";
    doPrimitiveTest(
      gen -> {
        ((OsonGeneratorImpl)gen).write(value);
        return json;
      },
      dom -> {
        assertEquals(ValueType.STRING, dom.getValueType());
        OracleJsonTimestampTZ ots;
        try {
          ots = ((java.sql.Wrapper)dom).unwrap(OracleJsonTimestampTZ.class);
        } catch (SQLException e) {
          throw new IllegalStateException(e);
        }
        OffsetDateTime i2 = ots.getOffsetDateTime();
        assertEquals(value, i2);
        
      },
      parser -> {
        assertEquals(Event.VALUE_STRING, parser.next());
      }
    );
  } 
  
  private String quotes(Object value) {
    return "\"" + String.valueOf(value) + "\"";
  }

  public void doPrimitiveTest(
      Function<OsonGeneratorImpl, String> writer, 
      Consumer<JsonValue> domTest,
      Consumer<JsonParser> parserTest) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OsonGeneratorImpl gen = generator(baos);
    String expected  = writer.apply(gen);
    gen.close();
    byte[] oson = baos.toByteArray(); // 24
        domTest.accept(getValue(oson));
    
    OsonParserImpl parser = parser(oson);
    assertTrue(parser.hasNext());
    parserTest.accept(parser.wrap(JsonParser.class));
    assertFalse(parser.hasNext());
    parser.close();
    
    parser = parser(oson);
    parser.next();
    JsonValue dom = parser.wrap(JsonParser.class).getValue();
    domTest.accept(dom);
  }
  
  @SuppressWarnings("unlikely-arg-type")
  public void testNumberComparisons() throws SQLException {
    float f = 2.1f;
    double d = (double)f;
    BigDecimal bd = BigDecimal.valueOf(d);
    assertEquals(BigDecimal.valueOf(f), bd);
    
    OracleJsonDecimal on = FACTORY.createDecimal(bd);
    OracleJsonDouble od = FACTORY.createDouble(d);
    OracleJsonFloat of = FACTORY.createFloat(f);
    
    assertFalse(on.equals(od));
    assertFalse(of.equals(on));
    assertFalse(of.equals(od));
    
    JsonNumber jn = on.wrap(JsonNumber.class);
    JsonNumber jd = od.wrap(JsonNumber.class);
    JsonNumber jf = of.wrap(JsonNumber.class);
    
    assertTrue(jn.equals(jd));
    assertTrue(jf.equals(jd));
    assertTrue(jd.equals(jf));
    assertTrue(jn.equals(jf));
    
    assertFalse(jn.equals(1));
    
    assertEquals(jn.hashCode(), jd.hashCode());
    assertEquals(jf.hashCode(), jd.hashCode());
    assertEquals(jn.hashCode(), jf.hashCode());
    
    assertEquals(jn.toString(), jd.toString());
    assertEquals(jf.toString(), jd.toString());
    assertEquals(jn.toString(), jf.toString());
    
    assertEquals(f, ((OracleJsonNumberImpl)jf).floatValue());
    assertEquals(d, jd.doubleValue());
    assertEquals(bd, jn.bigDecimalValue());
    
    Wrapper wn = (Wrapper)jn;
    Wrapper wd = (Wrapper)jd;
    Wrapper wf = (Wrapper)jf;
    
    assertEquals(on, wn.unwrap(OracleJsonDecimal.class));
    assertEquals(od, wd.unwrap(OracleJsonDouble.class));
    assertEquals(of, wf.unwrap(OracleJsonFloat.class));
    
    assertTrue(wn.isWrapperFor(OracleJsonDecimal.class));
    assertTrue(wd.isWrapperFor(OracleJsonDouble.class));
    assertTrue(wf.isWrapperFor(OracleJsonFloat.class));
    
    try {
      wn.unwrap(String.class);
      fail();
    } catch (SQLException e) {
      
    }
  }
  
  @SuppressWarnings("unlikely-arg-type")
  public void testStringComparisonsTS() throws SQLException {
    LocalDateTime ldt = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC).toLocalDateTime();
    OracleJsonTimestamp ot = FACTORY.createTimestamp(ldt);
    OracleJsonDate od = FACTORY.createDate(ldt);
    OracleJsonString os = FACTORY.createString(od.getString());
    
    assertFalse(ot.equals(od));
    assertFalse(od.equals(ot));
    assertFalse(od.equals(os));
    assertFalse(os.equals(ot));
    
    JsonString jt = ot.wrap(JsonString.class);
    JsonString jd = od.wrap(JsonString.class);
    JsonString js = os.wrap(JsonString.class);
    
    assertTrue(jt.equals(jd));
    assertTrue(jd.equals(jt));
    assertTrue(js.equals(jt));
    assertTrue(jt.equals(js));
    assertTrue(jt.equals(jt));
    
    assertEquals(jt.getChars().toString(), js.getChars().toString());
    
    assertFalse(jt.equals("asdf"));
    
    
    assertEquals(jt.hashCode(), jd.hashCode());
    assertEquals(js.hashCode(), jt.hashCode());
    
    assertEquals(jt.toString(), jd.toString());
    assertEquals(js.toString(), jd.toString());
    
    Wrapper wt = (Wrapper)jt;
    Wrapper wd = (Wrapper)jd;
    Wrapper ws = (Wrapper)js;
    
    assertEquals(ot, wt.unwrap(OracleJsonTimestamp.class));
    assertEquals(od, wd.unwrap(OracleJsonDate.class));
    assertEquals(os, ws.unwrap(OracleJsonString.class));
  }
  
  public void testBinaryComparisons() throws SQLException {
    OracleJsonBinary b = FACTORY.createBinary(new byte[] {1,2});
    OracleJsonString s = FACTORY.createString(b.getString());
    assertFalse(b.equals(s));
    
    JsonString jb = b.wrap(JsonString.class);
    JsonString js = s.wrap(JsonString.class);
    assertEquals(jb, js);
    assertEquals(jb.hashCode(), js.hashCode());
    assertEquals(jb.toString(), js.toString());
    
    Wrapper wb = (Wrapper)jb;
    Wrapper ws = (Wrapper)js;
    
    assertEquals(b, wb.unwrap(OracleJsonBinary.class));
    assertEquals(s, ws.unwrap(OracleJsonString.class));
    
    assertTrue(wb.isWrapperFor(OracleJsonBinary.class));
    assertFalse(wb.isWrapperFor(OracleJsonString.class));
    try {
      wb.unwrap(String.class);
      fail();
    } catch (SQLException e) {
      
    }
  }
  
  public void testIntervalDSComparisons() throws SQLException {
    OracleJsonIntervalDS i = FACTORY.createIntervalDS(Duration.ofDays(12));
    OracleJsonString s = FACTORY.createString(i.getString());
    assertFalse(i.equals(s));
    
    JsonString ji = i.wrap(JsonString.class);
    JsonString js = s.wrap(JsonString.class);
    assertEquals(ji, js);
    assertEquals(ji.hashCode(), js.hashCode());
    assertEquals(ji.toString(), js.toString());
    
    Wrapper wi = (Wrapper)ji;
    Wrapper ws = (Wrapper)js;
    
    assertEquals(i, wi.unwrap(OracleJsonIntervalDS.class));
    assertEquals(s, ws.unwrap(OracleJsonString.class));
  }
  
  public void testIntervalYMComparisons() throws SQLException {
    OracleJsonIntervalYM i = FACTORY.createIntervalYM(Period.ofYears(12));
    OracleJsonString s = FACTORY.createString(i.getString());
    assertFalse(i.equals(s));
    
    JsonString ji = i.wrap(JsonString.class);
    JsonString js = s.wrap(JsonString.class);
    assertEquals(ji, js);
    assertEquals(ji.hashCode(), js.hashCode());
    assertEquals(ji.toString(), js.toString());
    
    Wrapper wi = (Wrapper)ji;
    Wrapper ws = (Wrapper)js;
    
    assertEquals(i, wi.unwrap(OracleJsonIntervalYM.class));
    assertEquals(s, ws.unwrap(OracleJsonString.class));
  }
  
    
  private JsonValue getValue(byte[] oson) {
    return new OracleJsonFactory().createJsonBinaryValue(ByteBuffer.wrap(oson)).wrap(JsonValue.class);
  }

  private OsonGeneratorImpl generator(ByteArrayOutputStream baos) {
    return (OsonGeneratorImpl) new OracleJsonFactory().createJsonBinaryGenerator(baos);
  }
  
  private OsonParserImpl parser(byte[] bytes) {
    return (OsonParserImpl) new OracleJsonFactory().createJsonBinaryParser(ByteBuffer.wrap(bytes));
  }
}

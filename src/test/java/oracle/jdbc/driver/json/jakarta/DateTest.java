/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.jakarta;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerator;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonParserImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonValue;

/**
 * @author  jspiegel
 * @since   release specific (what release of product did this appear in)
 */
public class DateTest extends JsonTestCase {
    static OracleJsonFactory FACT = new OracleJsonFactory();

    public void testBasic() throws SQLException {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OracleJsonGenerator gen = generator(baos);
        Instant i = Instant.ofEpochSecond(123565342);
        gen.writeStartArray();
        LocalDateTime ldt = i.atOffset(ZoneOffset.UTC).toLocalDateTime();
        OracleJsonDate date = FACT.createDate(ldt);
        
        gen.write(date);
        gen.writeEnd();
        gen.close();
        
        byte[] oson = baos.toByteArray();
        String expected = "[\"1973-12-01T03:42:22\"]";
                
        JsonValue value = getValue(oson);
        JsonValue ja = ((JsonArray)value).get(0);
        Wrapper w = (Wrapper)ja;
        OracleJsonValue first = w.unwrap(OracleJsonValue.class);
        assertEquals(OracleJsonValue.OracleJsonType.DATE, first.getOracleJsonType());
        
        OracleJsonDate date2 = (OracleJsonDate)first;
        LocalDateTime ldt2 = date2.getLocalDateTime();
        assertEquals(ldt, ldt2);
        
        OsonParserImpl parser = parser(oson);
        parser.next();
        parser.next();
        OracleJsonDate di = ((OracleJsonDate)parser.getValue());
        assertEquals(ldt,di.getLocalDateTime());
    }
    
    public void testEveryDay() {
        int speedup = 3;
        LocalDateTime ldt = LocalDateTime.of(9999, 12, 30, 12, 59, 59, 0);
        while (true) {
            List<LocalDateTime> times = new ArrayList<LocalDateTime>();
            for (int i = 0; i < 10000; i += 1) {
                times.add(ldt);
                ldt = ldt.minusDays(speedup);
                if (ldt.getYear() <= 0) {
                    break;
                }
                
            }
            conversionTest(times.toArray(new LocalDateTime[0]));
            if (ldt.getYear() <= 0) {
                break;
            }
        }
    }
    
    public void testEveryHour() {
        LocalDateTime ldt = LocalDateTime.of(2100, 03, 30, 12, 59, 59);
        while (true) {
            ArrayList<LocalDateTime> times = new ArrayList<LocalDateTime>();
            for (int i = 0; i < 1000; i++) {
                times.add(ldt);
                int dayOfMonth = ldt.getDayOfMonth();
                while (ldt.getDayOfMonth() == dayOfMonth) {
                    ldt = ldt.minusHours(1);
                    times.add(ldt);
                }
                ldt = ldt.minusHours(1);
            }
            conversionTest(times.toArray(new LocalDateTime[0]));
            if (ldt.getYear() <=1990) {
                break;
            }
        }
    }
    
    public void testEveryMinute() {
        int speedup = 3;
        LocalDateTime ldt = LocalDateTime.of(2019, 03, 30, 12, 59, 59);
        while (true) {
            List<LocalDateTime> times = new ArrayList<LocalDateTime>();
            for (int i = 0; i < 100; i += speedup) {
                times.add(ldt);
                int dayOfMonth = ldt.getDayOfMonth();
                while (ldt.getDayOfMonth() == dayOfMonth) {
                    ldt = ldt.minusMinutes(1);
                    times.add(ldt);
                }
                ldt = ldt.minusMinutes(1);
            }
            conversionTest(times.toArray(new LocalDateTime[0]));
            if (ldt.getYear() <=2010) {
                break;
            }
        }
    }    
    
    public void testEverySecond() {
        LocalDateTime ldt = LocalDateTime.of(2019, 02, 27, 12, 59, 59);
        while (true) {
            List<LocalDateTime> times = new ArrayList<LocalDateTime>();
            for (int i = 0; i < 10; i++) {
                times.add(ldt);
                int dayOfMonth = ldt.getDayOfMonth();
                while (ldt.getDayOfMonth() == dayOfMonth) {
                    ldt = ldt.minusSeconds(1);
                    times.add(ldt);
                }
                ldt = ldt.minusSeconds(1);
            }
            conversionTest(times.toArray(new LocalDateTime[0]));
            if (ldt.getYear() <=2018) {
                break;
            }
        }
    }    
    
    private void conversionTest(LocalDateTime[] times) {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      JsonGenerator gen = FACT.createJsonBinaryGenerator(out).wrap(JsonGenerator.class);
      gen.writeStartArray();
      for (int j = 0; j < times.length; j++) {
          LocalDateTime i = times[j];
          Wrapper wrapper = (Wrapper)gen;
          OracleJsonGenerator ogen;
          try {
            ogen = wrapper.unwrap(OracleJsonGenerator.class);
          } catch (SQLException e) {
            throw new IllegalStateException(e);
          }
          OracleJsonValue value = FACT.createDate(i);
          ogen.write(value);
      }
      gen.writeEnd();
      gen.close();

      byte[] oson = out.toByteArray();
      OracleJsonArray arr = (OracleJsonArray)FACT.createJsonBinaryValue(ByteBuffer.wrap(oson));
      for (int i = 0; i < times.length; i++) {
          LocalDateTime i1 = times[i];
          OracleJsonDate date = (OracleJsonDate)arr.get(i);
          LocalDateTime i2 = date.getLocalDateTime();
          assertEquals(i1, i2);
      }
      
      StringWriter writer = new StringWriter();
      JsonGenerator jgen = FACT.createJsonTextGenerator(writer).wrap(JsonGenerator.class);
      jgen.write(arr.wrap(JsonArray.class));
      jgen.close();
      String javaJson = writer.toString();
          }    
    
    private OsonParserImpl parser(byte[] oson) {
        return (OsonParserImpl) FACT.createJsonBinaryParser(ByteBuffer.wrap(oson));
    }
    
    private JsonValue getValue(byte[] oson) {
        return FACT.createJsonBinaryValue(ByteBuffer.wrap(oson)).wrap(JsonValue.class);
    }

    private OracleJsonGenerator generator(ByteArrayOutputStream baos) {
        return FACT.createJsonBinaryGenerator(baos);
    }
    
}

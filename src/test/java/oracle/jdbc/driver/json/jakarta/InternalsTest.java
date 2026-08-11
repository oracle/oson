/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.jakarta;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl;
import oracle.jdbc.driver.json.binary.OsonParserImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalDSImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonValue;

/**
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public class InternalsTest extends JsonTestCase {
    public void testBasic() throws SQLException {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OsonGeneratorImpl gen = generator(baos);
        gen.writeStartArray();
        gen.writeIntervalDS(Duration.ZERO.plusDays(123));
        gen.writeEnd();
        gen.close();
        
        byte[] bytes = baos.toByteArray();
        String expected = "[\"P123D\"]";
                
        JsonValue value = getValue(bytes);
        JsonValue firstV = ((JsonArray)value).get(0);
        OracleJsonValue first = ((Wrapper)firstV).unwrap(OracleJsonValue.class);
        assertEquals(OracleJsonValue.OracleJsonType.INTERVALDS, first.getOracleJsonType());
        
        OsonParserImpl parser = parser(bytes);
        parser.next();
        parser.next();
        OracleJsonIntervalDSImpl interval = (OracleJsonIntervalDSImpl)parser.getValue();
        assertEquals("P123D", interval.getString());
    }
    
    public void testBasicYM() throws SQLException {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OsonGeneratorImpl gen = generator(baos);
        gen.writeStartArray();
        gen.writeIntervalYM(Period.ZERO.plusYears(1).plusMonths(2));
        gen.writeEnd();
        gen.close();
        
        byte[] bytes = baos.toByteArray();
        String expected = "[\"P1Y2M\"]";
                
        JsonValue value = getValue(bytes);
        
        JsonValue ymValue = ((JsonArray)value).get(0);
        
        OracleJsonValue first = ((Wrapper)ymValue).unwrap(OracleJsonValue.class);
        assertEquals(OracleJsonValue.OracleJsonType.INTERVALYM, first.getOracleJsonType());
        
        OsonParserImpl parser = parser(bytes);
        parser.next();
        parser.next();
        OracleJsonIntervalYMImpl interval = (OracleJsonIntervalYMImpl)parser.getValue();
        assertEquals("P1Y2M", interval.getString());
    }
    
        
    public void testDurationPositiveDays() {
        int i = 0;
        while (i >= 0 && i != Integer.MAX_VALUE) {
            int range = i + 100000;
            if (range < 0) {
                range = Integer.MAX_VALUE;
            }
            List<Duration> durations = new ArrayList<Duration>();
            for (int j = i; j < range; j++) {
                Duration d = Duration.ZERO.plusDays(j);
                durations.add(d);
            }
            durationTest(durations);
            i = range;
            
            i += 100000000;
        }
        durationTest(Collections.singletonList(Duration.ZERO.plusDays(Integer.MAX_VALUE)));
        
        try {
            durationTest(Collections.singletonList(Duration.ZERO.plusDays(100l + Integer.MAX_VALUE)));
            fail();
        } catch (IllegalArgumentException e) {
            
        }
    }
    
    public void testDurationPositiveHours() {
        allHours(Duration.ZERO);
        allHours(Duration.ZERO.plusDays(123));
        allHours(Duration.ZERO.plusMinutes(10));
        allHours(Duration.ZERO.plusSeconds(10));
        allHours(Duration.ZERO.plusNanos(12353));
        allHours(Duration.ZERO.plusDays(123).plusHours(4).plusMinutes(5).plusSeconds(12).plusNanos(23544));
    }
    
    public void testDurationPositiveMinutes() {
        allMinutes(Duration.ZERO);
        allMinutes(Duration.ZERO.plusDays(123));
        allMinutes(Duration.ZERO.plusMinutes(10));
        allMinutes(Duration.ZERO.plusSeconds(10));
        allMinutes(Duration.ZERO.plusNanos(12353));
        allMinutes(Duration.ZERO.plusDays(123).plusHours(4).plusMinutes(5).plusSeconds(12).plusNanos(23544));
    }
    
    public void testDurationPositiveSeconds() {
        allSeconds(Duration.ZERO);
        allSeconds(Duration.ZERO.plusDays(123));
        allSeconds(Duration.ZERO.plusMinutes(10));
        allSeconds(Duration.ZERO.plusSeconds(10));
        allSeconds(Duration.ZERO.plusNanos(12353));
        allSeconds(Duration.ZERO.plusDays(123).plusHours(4).plusMinutes(5).plusSeconds(12).plusNanos(23544));
    }
    
    public void testDurationPositiveNanos() {
        allNanos(Duration.ZERO);
        allNanos(Duration.ZERO.plusDays(123).plusHours(4).plusMinutes(5).plusSeconds(12));
    }
    
    public void testPeriodsPositiveMonths() {
        allMonths(Period.ZERO);
        for (int i = 0; i < 100; i++) {
            allMonths(Period.ZERO.withYears(i));
        }
        allMonths(Period.ZERO.withYears(Integer.MAX_VALUE));
    }
    
    public void allMonths(Period p) {
        List<Period> periods = new ArrayList<Period>();
        for (int i=0; i < 12; i++) {
            periods.add(p.withMonths(i));
        }
        periodTest(periods);
    }
    
    private void allNanos(Duration d) {
        int i = 0;
        while (i >= 0 && i != Integer.MAX_VALUE) {
            int range = i + 10000;
            if (range < 0) {
                range = Integer.MAX_VALUE;
            }
            List<Duration> durations = new ArrayList<Duration>();
            for (int j = i; j < range; j++) {
                d = d.plusNanos(j);
                durations.add(d);
            }
            durationTest(durations);
            i = range;
            
            i += 100000000;
        }        
    }
    
    private void allSeconds(Duration d) {
        List<Duration> durations = new ArrayList<Duration>();
        for (int i = 0; i < 61; i++) {
            durations.add(d.plusSeconds(i));
        }
        durationTest(durations);
    }
    
    private void allMinutes(Duration d) {
        List<Duration> durations = new ArrayList<Duration>();
        for (int i = 0; i < 61; i++) {
            durations.add(d.plusMinutes(i));
        }
        durationTest(durations);
    }
    
    private void allHours(Duration d) {
        List<Duration> durations = new ArrayList<Duration>();
        for (int i = 0; i < 26; i++) {
            durations.add(d.plusHours(i));
        }
        durationTest(durations);
    }

    public void durationTest(List<Duration> durations) {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OsonGeneratorImpl gen = generator(baos);
        gen.writeStartArray();
        for (Duration d : durations) {
            gen.writeIntervalDS(d);
        }
        gen.writeEnd();
        gen.close();
        byte[] bytes = baos.toByteArray();
                
        OsonParserImpl p = parser(bytes);
        p.next();
        OracleJsonArray a = p.getArray();
        assertEquals(a.size(), durations.size());
        for (int i = 0; i < a.size(); i++) {
            Duration duration = ((OracleJsonIntervalDS)a.get(i)).getDuration();
            assertEquals(duration, durations.get(i));
        }
    }
    
    public void periodTest(List<Period> periods) {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OsonGeneratorImpl gen = generator(baos);
        gen.writeStartArray();
        for (Period p : periods) {
            gen.writeIntervalYM(p);
        }
        gen.writeEnd();
        gen.close();
        byte[] bytes = baos.toByteArray();
                
        OsonParserImpl p = parser(bytes);
        p.next();
        OracleJsonArray a = p.getArray();
        assertEquals(a.size(), periods.size());
        for (int i = 0; i < a.size(); i++) {
            Period period = ((OracleJsonIntervalYM)a.get(i)).getPeriod();
            assertEquals(period, periods.get(i));
        }
    }
    
    private JsonValue getValue(byte[] oson) {
        OracleJsonFactory f = new OracleJsonFactory();
        return f.createJsonBinaryValue(ByteBuffer.wrap(oson)).wrap(JsonValue.class);
    }

    private OsonGeneratorImpl generator(ByteArrayOutputStream baos) {
        OracleJsonFactory f = new OracleJsonFactory();
        return (OsonGeneratorImpl) f.createJsonBinaryGenerator(baos);
    }
    
    private OsonParserImpl parser(byte[] bytes) {
        OracleJsonFactory f = new OracleJsonFactory();
        return (OsonParserImpl) f.createJsonBinaryParser(ByteBuffer.wrap(bytes));
    }
}

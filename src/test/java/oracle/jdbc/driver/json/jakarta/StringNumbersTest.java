/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.jakarta;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Wrapper;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerator;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl;
import oracle.jdbc.driver.json.tree.OracleJsonStringNumberImpl;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonValue;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class StringNumbersTest extends JsonTestCase {
    
    static OracleJsonFactory FACTORY = new OracleJsonFactory();
  
    @SuppressWarnings("unlikely-arg-type")
    public void testBasic() throws SQLException {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OsonGeneratorImpl gen = generator(baos);
        gen.writeStartArray();
        gen.writeNumberAsString(BigDecimal.valueOf(1234));
        gen.writeEnd();
        gen.close();
        
        byte[] bytes = baos.toByteArray();
        String expected = "[1234]";
                OracleJsonParser parser = FACTORY.createJsonBinaryParser(ByteBuffer.wrap(bytes));
        parser.next();
        parser.next();
        OracleJsonDecimal number = parser.getValue().asJsonDecimal();
        assertEquals("1234", number.toString());
        assertTrue(number instanceof OracleJsonStringNumberImpl);
        OracleJsonDecimal number2 = FACTORY.createDecimal(1234);
        assertTrue(number.equals(number2));
        assertEquals(number.hashCode(), number2.hashCode());
        assertFalse(number.equals(1));
        
        
        
        
                
        JsonNumber jnumber = number.wrap(JsonNumber.class);
        JsonNumber jnumber2 = number.wrap(JsonNumber.class);
        assertTrue(jnumber.equals(jnumber2));
        assertEquals(jnumber.hashCode(), jnumber2.hashCode());
        assertEquals("1234", number.toString());   
        
        
        // text serializer
        StringWriter writer = new StringWriter();
        OracleJsonGenerator tgen = FACTORY.createJsonTextGenerator(writer);
        tgen.writeStartArray();
        tgen.write(number);
        tgen.write(number2);
        JsonGenerator jtgen = tgen.wrap(JsonGenerator.class);
        jtgen.write(jnumber);
        jtgen.write(jnumber2);
        jtgen.writeEnd();
        jtgen.close();
        String result = writer.toString();
        assertEquals("[1234,1234,1234,1234]", result);
        
        
        JsonValue value = getValue(bytes);
        JsonValue first = ((JsonArray)value).get(0);
        OracleJsonValue ofirst = ((Wrapper)first).unwrap(OracleJsonValue.class);
        assertEquals(OracleJsonValue.OracleJsonType.DECIMAL, ofirst.getOracleJsonType());
        
        JsonNumber n = (JsonNumber)first;
        assertEquals(BigDecimal.valueOf(1234), n.bigDecimalValue());
    }
    
    public void testLongNumbers() {
        
        StringBuilder number = new StringBuilder();
        for (int i = 1; i <= 255; i++) {
            number.append(String.valueOf(i % 10));
            if (i < 150) continue;
            BigDecimal bd = new BigDecimal(number.toString());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OsonGeneratorImpl gen = generator(baos);
            gen.writeStartArray();
            gen.writeNumberAsString(bd);
            gen.writeEnd();
            gen.close();
            byte[] bytes = baos.toByteArray();
                    }
    }
    
    private OsonGeneratorImpl generator(ByteArrayOutputStream baos) {
        return (OsonGeneratorImpl) FACTORY.createJsonBinaryGenerator(baos);
    }
    
    private JsonValue getValue(byte[] oson) {
        return FACTORY.createJsonBinaryValue(ByteBuffer.wrap(oson)).wrap(JsonValue.class);
    }
    
}

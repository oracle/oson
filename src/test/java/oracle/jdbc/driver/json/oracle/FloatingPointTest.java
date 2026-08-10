/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2018, 2026, Oracle and/or its affiliates. */

package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonValue;

/**
 *  @author  jspiegel
 *  @since   release specific (what release of product did this appear in)
 */
public class FloatingPointTest extends JsonTestCase {
    
    public void testBasic() {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OracleJsonGenerator gen = generator(baos);
        gen.writeStartArray();
        gen.write(1.1f);
        gen.write(2.2f);
        gen.write(Float.NaN);
        gen.write(Float.POSITIVE_INFINITY);
        gen.write(Float.NEGATIVE_INFINITY);
        gen.writeEnd();
        gen.close();
        
        byte[] bytes = baos.toByteArray();
                
        OracleJsonValue value = getValue(bytes);
        OracleJsonValue first = ((OracleJsonArray)value).get(0);
        OracleJsonValue second = ((OracleJsonArray)value).get(1);
        
        assertEquals(OracleJsonValue.OracleJsonType.FLOAT, first.getOracleJsonType());
        assertEquals(OracleJsonValue.OracleJsonType.FLOAT, second.getOracleJsonType());
        
        OracleJsonFloat n = (OracleJsonFloat)second;
        assertEquals(2.2f, n.floatValue());
        
        OracleJsonParser p = parser(bytes);
        p.next();
        p.next();
        OracleJsonFloat f = (OracleJsonFloat)p.getValue();
        assertEquals(1.1f, f.floatValue());
        p.next();
        p.next();
        f = (OracleJsonFloat)p.getValue();
        float v = ((OracleJsonFloat)f).floatValue();
        assertTrue(Float.isNaN(v));
        
    }

    private OracleJsonValue getValue(byte[] oson) {
        OracleJsonFactory f = new OracleJsonFactory();
        return f.createJsonBinaryValue(ByteBuffer.wrap(oson));
    }
    
    private OracleJsonParser parser(byte[] oson) {
        OracleJsonFactory f = new OracleJsonFactory();
        return f.createJsonBinaryParser(ByteBuffer.wrap(oson));
    }
    
    private OracleJsonGenerator generator(ByteArrayOutputStream baos) {
        OracleJsonFactory f = new OracleJsonFactory();
        return f.createJsonBinaryGenerator(baos);
    }
}

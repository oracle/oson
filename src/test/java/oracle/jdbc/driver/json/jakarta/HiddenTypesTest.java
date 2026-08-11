// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.jakarta;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Wrapper;

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;

import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonValue;
import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class HiddenTypesTest extends JsonTestCase {
    
    public void testBigDecimal128() throws SQLException {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OracleJsonGenerator gen = generator(baos);
        gen.writeStartArray();
        gen.write(new BigDecimal("1234583838372349857239487519234815792382389172348917324774192386789.987654321"));
        gen.write(new BigDecimal("1"));
        gen.writeEnd();
        gen.close();
        String expected = "[1.23458383837234985723948751923481579238E+66,1]"; 
        byte[] oson = baos.toByteArray();
                
        JsonValue value = getValue(oson);
        JsonArray arr = (JsonArray)value;
        OracleJsonArray oarr = ((Wrapper)arr).unwrap(OracleJsonArray.class);
        for (OracleJsonValue v : oarr) {
            OracleJsonDecimalImpl impl = (OracleJsonDecimalImpl)v;
            assertEquals(impl.getTargetType(), OracleJsonDecimal.TargetType.DECIMAL);
        }
    }
    
    public void testSbInts() throws SQLException {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OracleJsonGenerator gen = generator(baos);
        gen.writeStartArray();
        gen.write(15);
        gen.write(987l);
        gen.writeEnd();
        gen.close();
        String expected = "[15,987]"; 
        byte[] oson = baos.toByteArray();
                
        JsonValue value = getValue(oson);
        JsonArray arr = (JsonArray)value;
        OracleJsonArray oarr = ((Wrapper)arr).unwrap(OracleJsonArray.class);
        OracleJsonDecimalImpl sb4 = (OracleJsonDecimalImpl)oarr.get(0);
        OracleJsonDecimalImpl sb8 = (OracleJsonDecimalImpl)oarr.get(1);
        assertEquals(sb4.getTargetType(), OracleJsonDecimal.TargetType.INT);
        assertEquals(sb8.getTargetType(), OracleJsonDecimal.TargetType.LONG);
    }
    
    private JsonValue getValue(byte[] oson) {
        OracleJsonFactory f = new OracleJsonFactory();
        return f.createJsonBinaryValue(ByteBuffer.wrap(oson)).wrap(JsonValue.class);
    }

    private OracleJsonGenerator generator(ByteArrayOutputStream baos) {
        OracleJsonFactory f = new OracleJsonFactory();
        return f.createJsonBinaryGenerator(baos);
    }
    
}

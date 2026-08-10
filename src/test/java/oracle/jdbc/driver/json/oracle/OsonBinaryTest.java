/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */


package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonConstants;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonBinaryTest extends JsonTestCase {
    
    public void testBinary() {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OracleJsonGenerator gen = generator(baos);
        gen.writeStartArray();
        gen.writeId(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 } );
        gen.writeId(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 } );
        gen.write(new byte[] { 1, 2, 0xa, 0xB, 5, 6});
        gen.writeEnd();
        gen.close();
        byte[] oson = baos.toByteArray();
            }
    
    public void testBinaryBoundary() {
      
      for (int i = OsonConstants.UB2_MAXSZ - 20; i < OsonConstants.UB2_MAXSZ + 10; i++) {
        byte[] arr = new byte[i];
        for (int j = 0; j < arr.length; j++) {
          arr[j] = (byte)j;
        }
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        OracleJsonGenerator gen = generator(baos);
        gen.writeStartArray();
        gen.write(arr);
        gen.writeEnd();
        gen.close();
        byte[] oson = baos.toByteArray();
              }
      
    }

    private OsonGeneratorImpl generator(ByteArrayOutputStream baos) {
        OracleJsonFactory f = new OracleJsonFactory();
        return (OsonGeneratorImpl) f.createJsonBinaryGenerator(baos);
    }
}

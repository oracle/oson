// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 


package oracle.jdbc.driver.json.jakarta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.function.Function;

import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerationException;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.AbstractGenerator;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonParser;

/**
 * This is for tests common to OsonGeneratorImpl and JsonSerializerImpl
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class GenericGeneratorTest extends JsonTestCase {

    public void testStartObject1() {
        negative(gen -> {
            gen.writeStartObject();
            gen.writeEnd();
            try {
                // may not be called twice in no context
                gen.writeStartObject();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26313"));
                return true;
            }
            return false;
        });
    }
    
    public void testStartObject2() {
        negative(gen -> {
            gen.writeStartObject();
            try {
                // needs a key def
                gen.writeStartObject();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }
    
    public void testStartObject3() {
        negative(gen -> {
            gen.writeStartArray();
            try {
                // needs a key def
                gen.writeStartObject("foo");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });
    }
    
    public void testStartObject4() {
        negative(gen -> {
            try {
                // needs a key def
                gen.writeStartObject("foo");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });
    }
    
    public void testWriteKey1( ) {
        negative(gen -> {
            try {
                gen.writeKey("foo");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });        
    }
    
    public void testWriteKey2( ) {
        positive(gen -> {
            gen.writeStartObject()
               .writeKey("foo")
               .write("bar");
            gen
               .writeKey("bat")
               .write("bang")
               .writeEnd()
               .close();
            return "{\"foo\":\"bar\",\"bat\":\"bang\"}";
        });
    }
    
    public void testWriteKey3( ) {
        negative(gen -> {
            gen.writeStartArray();
            try {
                gen.writeKey("foo");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });        
    }
    
    public void testWriteKey4( ) {
        negative(gen -> {
            gen.writeStartObject();
            (gen).writeKey("foo");
            try {
                (gen).writeKey("foo");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });        
    }  
    
    public void testStartArray1( ) {
        negative(gen -> {
            gen.writeStartObject();
            try {
                gen.writeStartArray();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });        
    }
    
    public void testStartArray2( ) {
        positive(gen -> {
            gen.writeStartObject()
               .writeStartArray("foo")
               .writeEnd()
               .writeEnd()
               .close();
            return "{\"foo\":[]}";
        });        
    }
    
    public void testStartArray3( ) {
        negative(gen -> {
            gen.writeStartArray();
            gen.writeEnd();
            try {
                gen.writeStartArray();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26313"));
                return true;
            }
            return false;
        });        
    }
    
    public void testWriteValueWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", Json.createObjectBuilder().build());
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteValueWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", Json.createObjectBuilder()
                       .add("true", true)
                       .add("false", false)
                       .addNull("null")
                       .build())
               .writeEnd()
               .close();
            return "{\"foo\":{\"true\":true,\"false\":false,\"null\":null}}";
        });
    }
    
        
    public void testWriteStringWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", "bar");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteStringWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", "bar")
               .writeEnd()
               .close();;
            return "{\"foo\":\"bar\"}";
        });        
    }
    
    public void testWriteBigIntWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", BigInteger.valueOf(123));
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteBigIntWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", BigInteger.valueOf(123))
               .writeEnd()
               .close();
            return "{\"foo\":123}";
        });        
    }
    
    public void testWriteBigDecWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", BigDecimal.valueOf(123.1));
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteBigDecWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", BigDecimal.valueOf(123.1))
               .writeEnd()
               .close();
            return "{\"foo\":123.1}";
        });        
    }
    
    public void testWriteIntWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", 123);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteIntWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", 123)
               .writeEnd()
               .close();
            return "{\"foo\":123}";
        });        
    }
    
    public void testWriteLongWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", 123l);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteLongWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", 123l)
               .writeEnd()
               .close();
            return "{\"foo\":123}";
        });        
    }
    
    public void testWriteDoubleWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", 1.333333333333333d);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteDoubleWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", 1.333333333333333d)
               .writeEnd()
               .close();
            return "{\"foo\":1.333333333333333}";
        });        
    }    

    public void testWriteBooleanWithKey1() {
        negative(gen -> {
            try {
                gen.write("foo", true);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteBooleanWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .write("foo", true)
               .write("bar", false)
               .writeEnd()
               .close();
            return "{\"foo\":true,\"bar\":false}";
        });        
    } 
    
    public void testWriteNullWithKey1() {
        negative(gen -> {
            try {
                gen.writeNull("foo");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26315"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteNullWithKey2() {
        positive(gen -> {
            gen.writeStartObject()
               .writeNull("foo")
               .writeEnd()
               .close();
            return "{\"foo\":null}";
        });        
    }     
    
    public void testWriteEnd1() {
        negative(gen -> {
            try {
                gen.writeEnd();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26314"));
                return true;
            }
            return false;
        });            
    }
    
    public void testWriteEnd2() {
        negative(gen -> {
            try {
                gen.writeStartArray();
                gen.writeEnd();
                gen.writeEnd();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26314"));
                return true;
            }
            return false;
        });
    }
    
    public void testWriteEnd3() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                (gen).writeKey("foo");
                gen.writeEnd();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26316"));
                return true;
            }
            return false;
        });
    }    
    
    public void testWriteJsonValue1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write(Json.createObjectBuilder().build());
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }
    
    public void testWriteJsonValue2() {
        positive(gen -> {
            gen.write(Json.createObjectBuilder().build()).close();
            return "{}";
        });
    }
    
    public void testWriteJsonValue3() {
        positive(gen -> {
            gen.write(Json.createArrayBuilder().add(1).add(2).add(3).build()).close();
            return "[1,2,3]";
        });
    }
    
    public void testWriteJsonValue4() {
        positive(gen -> {
            gen.writeStartObject();
            gen.write("xxx", Json.createObjectBuilder().build())
               .writeEnd()
               .close();
            return "{\"xxx\":{}}";
        });
    }
    
    public void testWriteJsonValue5() {
        positive(gen -> {
            gen.writeStartArray()
               .write(Json.createObjectBuilder().build())
               .writeEnd()
               .close();
            return "[{}]";
        });
    }  
    
    public void testWriteStringValue1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write("foo");
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }
    
    public void testWriteStringValue2() {
        positive(gen -> {
            gen.writeStartArray()
               .write("foo")
               .write("foo")
               .writeEnd()
               .close();
            return "[\"foo\",\"foo\"]";
        });
    }
    
    public void testWriteBigDecimal1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write(BigDecimal.valueOf(12.34));
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }
    
    public void testWriteBigDecimal2() {
        positive(gen -> {
            gen.writeStartArray()
               .write(BigDecimal.valueOf(12.34))
               .write(BigDecimal.valueOf(56.78))
               .writeEnd()
               .close();
            return "[12.34,56.78]";
        });
    }    
    
    public void testWriteBigInteger1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write(BigDecimal.valueOf(12));
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }

    public void testWriteBigInteger2() {
        positive(gen -> {
            gen.writeStartArray()
               .write(BigInteger.valueOf(12))
               .write(BigInteger.valueOf(56))
               .writeEnd()
               .close();
            return "[12,56]";
        });
    }
    
    public void testWriteInt1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write(12);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }

    public void testWriteInt2() {
        positive(gen -> {
            gen.writeStartArray()
               .write(12)
               .write(56)
               .writeEnd()
               .close();
            return "[12,56]";
        });
    }
    
    public void testWriteLong1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write(12l);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }
    
    public void testWriteLong2() {
        positive(gen -> {
            gen.writeStartArray()
               .write(12l)
               .write(56l)
               .writeEnd()
               .close();
            return "[12,56]";
        });
    }
    
    public void testWriteDouble1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write(12.34d);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }

    public void testWriteDouble2() {
        positive(gen -> {
            gen.writeStartArray()
               .write(12.34d)
               .write(56.78d)
               .writeEnd()
               .close();
            return "[12.34,56.78]";
        });
    }
    
    public void testWriteBoolean1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.write(true);
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }

    public void testWriteBoolean2() {
        positive(gen -> {
            gen.writeStartArray()
               .write(true)
               .write(false)
               .writeEnd()
               .close();
            return "[true,false]";
        });
    }
    
    public void testWriteNull1() {
        negative(gen -> {
            try {
                gen.writeStartObject();
                gen.writeNull();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26312"));
                return true;
            }
            return false;
        });
    }

    public void testWriteNull2() {
        positive(gen -> {
            gen.writeStartArray()
               .writeNull()
               .writeNull()
               .writeEnd()
               .close();
            return "[null,null]";
        });
    }
    
    public void testClose() {
        negative(gen -> {
            try {
                gen.writeStartObject().close();
                fail();
            } catch (JsonGenerationException e) {
                assertTrue(e.getMessage().startsWith("ORA-26311"));
                return true;
            }
            return false;
        });
    }
    
    public void testClose2() {
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException();
            }
        };
        
        OracleJsonFactory o = new OracleJsonFactory();
        try {
            o.createJsonBinaryGenerator(os).writeStartArray().writeEnd().close();
            fail();
        } catch (OracleJsonException e) {
            assertTrue(e.getMessage().startsWith("ORA-26301"));
        }
        
        try {
            o.createJsonTextGenerator(os).writeStartArray().writeEnd().close();
            fail();
        } catch (OracleJsonException e) {
            assertTrue(e.getMessage().startsWith("ORA-26301"));
        }
    }

    
    private void negative(Function<JsonGenerator, Object> test) { 
        t(test, false);
    }
    
    private void positive(Function<JsonGenerator, Object> test) { 
        t(test, true);
    }    
    
    private void t(Function<JsonGenerator, Object> test, boolean positive) {
        OracleJsonFactory f = new OracleJsonFactory();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        JsonGenerator gen = f.createJsonBinaryGenerator(baos).wrap(JsonGenerator.class);
        Object result = test.apply(gen);
        if (positive) {
            // sanity check oson
                    } else {
            assertTrue((Boolean)result);
        }

        baos = new ByteArrayOutputStream();
        gen = f.createJsonTextGenerator(baos).wrap(JsonGenerator.class);
        result = test.apply(gen);
        if (positive) {
            // sanity check json
            JsonParser p = Json.createParser(new ByteArrayInputStream(baos.toByteArray()));
            while (p.hasNext()) {
                p.next();
            }
            p.close();
            if (result != null) {
                            }
        } else {
            assertTrue((Boolean)result);
        }
        /** JSONP 1.1
        if (positive) {
            baos = new ByteArrayOutputStream();
            gen = Json.createGenerator(baos);
            result = test.apply(gen);
            OsonTestUtils.assertJsonEqual((String)result, new String(baos.toByteArray()));
        }*/
    }
    

    
}

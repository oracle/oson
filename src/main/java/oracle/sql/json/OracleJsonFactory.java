// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Objects;

import oracle.jdbc.driver.json.BufferPoolImpl;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonBuffer;
import oracle.jdbc.driver.json.binary.OsonContext;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl;
import oracle.jdbc.driver.json.binary.OsonGeneratorImpl.OsonGeneratorStatePool;
import oracle.jdbc.driver.json.binary.OsonParserImpl;
import oracle.jdbc.driver.json.binary.OsonStructureImpl;
import oracle.jdbc.driver.json.parser.JsonParserImpl;
import oracle.jdbc.driver.json.tree.OracleJsonArrayImpl;
import oracle.jdbc.driver.json.tree.OracleJsonBinaryImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDateImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDoubleImpl;
import oracle.jdbc.driver.json.tree.OracleJsonFloatImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalDSImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
import oracle.jdbc.driver.json.tree.OracleJsonObjectImpl;
import oracle.jdbc.driver.json.tree.OracleJsonStringImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampTZImpl;
import oracle.jdbc.driver.json.tree.OracleJsonVectorImpl;
import oracle.jdbc.driver.VectorData;
import oracle.sql.json.OracleJsonDecimal.TargetType;


/**
 * <p> 
 * A factory for reading, writing, and creating SQL JSON
 * values. The methods on this factory fall into three categories:
 * </p>
 *
 * <table border="1" cellpadding="5" width="800px">
 * <tr>
 * <th>Description</th>
 * <th>Methods</th>
 * </tr>
 * <tr>
 * <td width="200px">Methods for reading and writing
 * <a href="package-summary.html">Oracle binary JSON</a></td>
 * <td>{@link #createJsonBinaryGenerator(OutputStream)}<br/>
 * {@link #createJsonBinaryValue(ByteBuffer)}<br/>
 * {@link #createJsonBinaryValue(InputStream)}<br/>
 * {@link #createJsonBinaryParser(ByteBuffer)}<br/>
 * {@link #createJsonBinaryParser(InputStream)}</td>
 * </tr>
 * <tr>
 * <td>Methods for creating new instances of the JSON type
 * <a href="package-summary.html">object-model</a></td>
 * <td>{@link #createObject()}<br/>
 * {@link #createObject(OracleJsonObject)}<br/>
 * {@link #createArray()}<br/>
 * {@link #createArray(OracleJsonArray)}<br/>
 * {@link #createString(String)}<br/>
 * {@link #createDecimal(BigDecimal)}<br/>
 * {@link #createDecimal(int)}<br/>
 * {@link #createDecimal(long)}<br/>
 * {@link #createDouble(double)}<br/>
 * {@link #createFloat(float)}<br/>
 * {@link #createTimestamp(LocalDateTime)}<br/>
 * {@link #createTimestampTZ(OffsetDateTime)}<br/>
 * {@link #createDate(LocalDateTime)}<br/>
 * {@link #createBinary(byte[])}<br/>
 * {@link #createIntervalDS(Duration)}<br/>
 * {@link #createIntervalYM(Period)}</td>
 * </tr>
 * <tr>
 * <td>Methods for converting values to and from JSON text</td>
 * <td>{@link #createJsonTextGenerator(OutputStream)}<br/>
 * {@link #createJsonTextGenerator(Writer)}<br/>
 * {@link #createJsonTextParser(InputStream)}<br/>
 * {@link #createJsonTextParser(Reader)}<br/>
 * </td>
 * </table>
 * 
 * <p>
 * The following example generates Oracle binary JSON for the JSON object
 * <code>{"hello":"world"}</code>
 * </p>
 * 
 * <pre><code>
 * OracleJsonFactory factory = new OracleJsonFactory();
 * OracleJsonObject obj = factory.createObject();
 * obj.put("hello", "world");
 * 
 * ByteArrayOutputStream out = new ByteArrayOutputStream();
 * JsonGenerator binaryGenerator = factory.createJsonBinaryGenerator(out);
 * binaryGenerator.write(obj);
 * binaryGenerator.close();
 * byte[] binaryJson = out.toByteArray();
 * </code></pre>
 * 
 * <p>
 * Continuing with this example, the Oracle binary JSON can be read as follows:
 * </p>
 *
 * <pre><code>
 * OracleJsonObject bobj = factory.createJsonBinaryValue(ByteBuffer.wrap(binaryJson));
 * System.out.println(bobj.getString("hello"));
 * </code></pre>
 *
 * <p>
 * In this example, the returned object ({@code bobj}) directly references the
 * underlying Oracle binary JSON ({@code binaryJson}). It does not attempt to
 * convert the underlying binary JSON to some other internal data structure.
 * Consequently, the object is immutable and attempts to call mutator methods
 * such as {@code bobj.put(...)} will raise an error. To make a modifiable copy
 * of the object, use {@link #createObject(OracleJsonObject)}. 
 * </p>
 * <p>
 * The object can be
 * printed as JSON text using {@code bobj.toString()} or by using a JSON text
 * generator:
 * </p>
 * 
 * <pre><code>
 * OracleJsonGenerator jsonGenerator = factory.createJsonTextGenerator(System.out);
 * jsonGenerator.write(bobj);
 * jsonGenerator.close();
 * </code></pre>
 * 
 * <p>
 * Using a text generator instead of {@code toString()} gives more control over
 * how the JSON output is written and reuses memory more efficiently.
 * </p>
 * 
 * <p>
 * This factory does not support parsing JSON text. To parse JSON text use
 * {@code javax.json.Json.createParser()} or another third-party JSON parser.
 * The following example shows how to convert JSON text to Oracle binary JSON
 * using a JSON-P parser:
 * </p>
 *
 * <pre><code>
 * JsonParser parser = Json.createParser(new StringReader("{\"hello\":\"world\"}"));
 * OracleJsonGenerator binaryGenerator = factory.createJsonBinaryGenerator(out);
 * binaryGenerator.writeParser(parser);
 * binaryGenerator.close();
 * parser.close();
 * </code></pre>
 * 
 * <p>
 * This factory is thread safe but the objects created from it are not.
 * Temporary memory used by parsers and generators may be pooled and reused by
 * this factory. In general, a single factory may serve an entire application
 * but performance may degrade if many threads access the same factory at once.
 * It may be beneficial, for example, to keep multiple thread-local instances.
 * </p>
 * 
 * jspiegel_oracleapi/3 2018/12/21 14:34:57 jspiegel Exp $
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public final class OracleJsonFactory {

  /** Not intended to be public */
  private static final boolean DISABLE_GENERATOR_POOL = 
      Boolean.getBoolean("oracle.sql.json.OracleJsonFactory.DISABLE_GENERATOR_POOL");
  
  private static final int DEFAULT_BUFFER_SIZE = 1024*8;
  
  private final OsonGeneratorStatePool generatorPool = DISABLE_GENERATOR_POOL ? 
      null : new OsonGeneratorStatePool();
  
  private final BufferPoolImpl bufferPool = new BufferPoolImpl();
  
  public OracleJsonFactory() {
    // do nothing
  }
  
  /**
   * Creates a binary JSON parser from the given byte
   * stream. The contents of the byte stream will be fully stored in
   * the heap. JSON values obtained from the parser may directly
   * reference these underlying bytes.
   * 
   * @param in stream of binary JSON
   * @return the created parser
   * @throws OracleJsonException if an error occurs reading the input
   */
  public OracleJsonParser createJsonBinaryParser(InputStream in) throws OracleJsonException {
    byte[] bytes = readInputStream(in);
    ByteBuffer b = ByteBuffer.wrap(bytes);
    OsonContext ctx = new OsonContext(new OsonBuffer(b));
    OsonParserImpl result = new OsonParserImpl(ctx);
    result.setCloseable(in);
    return result;
  }
  
  /**
   * Creates a JSON text parser from the given byte stream. The unicode
   * character set of the JSON text will be detected automatically.
   * 
   * @param in stream of JSON text
   * @return the created parser
   * @throws OracleJsonException
   *           if an error occurs reading the input
   */
  public OracleJsonParser createJsonTextParser(InputStream in) throws OracleJsonException {
    return new JsonParserImpl(in, bufferPool);
  }
  
  /**
   * Creates a JSON text parser from the given character stream. 
   * 
   * @param in stream of JSON text
   * @return the created parser
   * @throws OracleJsonException
   *           if an error occurs reading the input
   */
  public OracleJsonParser createJsonTextParser(Reader in) throws OracleJsonException {
    return new JsonParserImpl(in, bufferPool);
  }
  
  /**
   * Creates a binary JSON parser from the given buffer. JSON
   * values returned from the parser may rely on a direct reference
   * to the provided byte buffer.  The buffer must not be modified
   * until the parser and any values obtained from it are no longer
   * needed. The parser will not attempt to modify the buffer.
   * 
   * @param in the buffer containing binary JSON
   * @return the created parser
   * @throws OracleJsonException if an error occurs reading the input
   */
  public OracleJsonParser createJsonBinaryParser(ByteBuffer in) throws OracleJsonException {
    OsonContext ctx = new OsonContext(new OsonBuffer(in));
    return new OsonParserImpl(ctx);
  }
  
  /**
   * Creates a {@code OracleJsonValue} from the given binary JSON
   * stream. This is a convenience method that is semantically
   * equivalent to obtaining a value from a JSON parser as follows:
   * 
   * <pre><code>
   * try (OracleJsonParser parser = factory.createJsonBinaryParser(in)) {
   *   parser.next();
   *   OracleJsonValue value = parser.getValue();
   * }
   * </code></pre>
   *
   * <p>
   * This method does close the provided {@code InputStream}.
   * </p>
   *
   * @param in stream of binary JSON
   * @return the JSON value
   * @throws OracleJsonException if an error occurs reading the input
   */
  public OracleJsonValue createJsonBinaryValue(InputStream in) throws OracleJsonException {
    byte[] bytes = readInputStream(in);
    ByteBuffer b = ByteBuffer.wrap(bytes);
    return createJsonBinaryValue(b);
  }
  
  /**
   * Creates a {@code OracleJsonValue} from the given textual JSON
   * stream. This is a convenience method that is semantically
   * equivalent to obtaining a value from a JSON parser as follows:
   * 
   * <pre><code>
   * try (OracleJsonParser parser = factory.createJsonTextParser(in)) {
   *   parser.next();
   *   OracleJsonValue value = parser.getValue();
   * }
   * </code></pre>
   *
   * <p>
   * This method does close the provided {@code InputStream}.
   * </p>
   *
   * @param in stream of textual JSON
   * @return the JSON value
   * @throws OracleJsonException if an error occurs reading the input
   */
  public OracleJsonValue createJsonTextValue(InputStream in) throws OracleJsonException {
    try (OracleJsonParser parser = this.createJsonTextParser(in)) {
      parser.next();
      return parser.getValue();
    }
  }
  
  /**
   * Creates a {@code OracleJsonValue} from the given textual JSON
   * stream. This is a convenience method that is semantically
   * equivalent to obtaining a value from a JSON parser as follows:
   * 
   * <pre><code>
   * try (OracleJsonParser parser = factory.createJsonTextParser(in)) {
   *   parser.next();
   *   OracleJsonValue value = parser.getValue();
   * }
   * </code></pre>
   *
   * <p>
   * This method does close the provided {@code InputStream}.
   * </p>
   *
   * @param in stream of textual JSON
   * @return the JSON value
   * @throws OracleJsonException if an error occurs reading the input
   */
  public OracleJsonValue createJsonTextValue(Reader in) throws OracleJsonException {
    try (OracleJsonParser parser = this.createJsonTextParser(in)) {
      parser.next();
      return parser.getValue();
    }
  }
  
  /**
   * Creates a {@code JsonValue} from the given binary JSON
   * buffer. This is a convenience method that is semantically
   * equivalent to obtaining a value from a JSON parser as follows:
   * 
   * <pre><code>
   * try (OracleJsonParser parser = factory.createJsonBinaryParser(in)) {
   *   parser.next();
   *   OracleJsonValue value = parser.getValue();
   * }
   * </code></pre>
   * 
   * The {@code OracleJsonValue} returned by this function may directly
   * reference the provide {@code ByteBuffer}. The buffer should not
   * be modified until returned {@code OracleJsonValue} and all values
   * derived from it are no longer needed.
   * 
   * @param in the buffer containing binary JSON
   * @return the JSON value
   * @throws OracleJsonException if an error occurs reading the input
   */
  public OracleJsonValue createJsonBinaryValue(ByteBuffer in) throws OracleJsonException {
    OsonContext ctx = new OsonContext(new OsonBuffer(in));
    int offset = ctx.getHeader().getTreeSegmentOffset();
    return (OracleJsonValue)OsonStructureImpl.getValueInternal(offset, ctx.getFactory(), ctx);
  }

  /**
   * Creates a JSON generator to write binary JSON to a byte stream. 
   *  
   * @param out i/o stream to which binary JSON is written
   * @return the created JSON generator
   */
  public final OracleJsonGenerator createJsonBinaryGenerator(OutputStream out) {
    return new OsonGeneratorImpl(generatorPool, out);
  }

  /**
   * Creates a JSON generator to write JSON text to a byte
   * stream. Characters written to the stream are encoded into bytes
   * as UTF8.
   * 
   * @param out i/o stream to which UTF8 JSON is written
   * @return the created JSON generator
   */
  public OracleJsonGenerator createJsonTextGenerator(OutputStream out) {
    return new JsonSerializerImpl(out);
  }
  
  /**
   * Creates a JSON generator to write JSON to a character stream.
   * 
   * @param out character stream to which JSON is written
   * @return the created JSON generator
   */
  public OracleJsonGenerator createJsonTextGenerator(Writer out) {
    return new JsonSerializerImpl(out);
  }
  
  /**
   * Creates a new mutable JSON object.
   *  
   * @return the JSON object
   */
  public OracleJsonObject createObject() { 
    return new OracleJsonObjectImpl();
  }
  
  /** 
   * Creates a new mutable JSON array.
   * 
   * @return the JSON array
   */
  public OracleJsonArray createArray() { 
    return new OracleJsonArrayImpl();
  }
  
  /**
   * Creates a mutable copy of a JSON object.
   * 
   * @param other the JSON object to copy. May be either mutable or immutable.
   * 
   * @return a mutable JSON object.
   */
  public OracleJsonObject createObject(OracleJsonObject other) { 
    return new OracleJsonObjectImpl(other);
  };
  
  /**
   * Creates a mutable copy of a JSON array.
   * 
   * @param other the JSON array to copy.  May be either mutable or immutable.
   * 
   * @return a mutable JSON array.
   */
  public OracleJsonArray createArray(OracleJsonArray other) { 
    return new OracleJsonArrayImpl(other); 
  }
  
  /**
   * Creates a new JSON string.
   * 
   * @param value the string value
   * 
   * @return a JSON string
   */
  public OracleJsonString createString(String value) { 
    return new OracleJsonStringImpl(value); 
  }
  
  /**
   * Creates a new JSON decimal.
   * 
   * @param value the decimal value
   * 
   * @return the JSON decimal
   * 
   * @throws OracleJsonException if the specified value can not be converted to a JSON number. 
   */
  public OracleJsonDecimal createDecimal(BigDecimal value) throws OracleJsonException { 
    return new OracleJsonDecimalImpl(value); 
  }
  
  /**
   * Creates a new JSON decimal.
   * 
   * @param value the value as an integer
   * 
   * @return the JSON decimal value
   */
  public OracleJsonDecimal createDecimal(int value) { 
    return new OracleJsonDecimalImpl(value, TargetType.INT);
  }
  
  /**
   * Creates a new JSON decimal.
   * 
   * @param value the value as a long
   * 
   * @return the JSON decimal value
   */
  public OracleJsonDecimal createDecimal(long value) { 
    return new OracleJsonDecimalImpl(value, TargetType.LONG);
  }
  
  /**
   * Creates a new JSON float.
   * 
   * @param value the value as a float
   * 
   * @return the JSON float value
   */
  public OracleJsonFloat createFloat(float value) { 
    return new OracleJsonFloatImpl(value); 
  }
  
  /**
   * Creates a new JSON double.
   * 
   * @param value the value as a double
   * 
   * @return the JSON double value
   */
  public OracleJsonDouble createDouble (double value) { 
    return new OracleJsonDoubleImpl(value);
  }
  
  /**
   * Creates a new JSON binary value.
   * 
   * @param value the value as a byte array
   * 
   * @return the JSON binary value
   */
  public OracleJsonBinary createBinary (byte[] value) { 
    return new OracleJsonBinaryImpl(value, false);
  }
  
  /**
   * Creates a new JSON boolean value.
   * 
   * @param value the value as a boolean
   * 
   * @return {@code OracleJsonValue.TRUE} or {@code OracleJsonValue.FALSE}
   */
  public OracleJsonValue createBoolean(boolean value) {
    return value ? OracleJsonValue.TRUE : OracleJsonValue.FALSE;
  }
  
  /**
   * Returns {@code OracleJsonValue.NULL}.
   * 
   * @return the null value
   */
  public OracleJsonValue createNull() { 
    return OracleJsonValue.NULL;
  }
    
  /**
   * Creates a new JSON timestamp value.
   *
   * @param value the timestamp as a LocalDateTime
   *
   * @return the timestamp value
   */
  public OracleJsonTimestamp createTimestamp(LocalDateTime value) {
    return new OracleJsonTimestampImpl(value);
  };

  /**
   * Creates a new JSON date value.
   *
   * @param i the date as a LocalDateTime
   *
   * @return the date value
   */
  public OracleJsonDate createDate(LocalDateTime i) {
    return new OracleJsonDateImpl(i);
  };


  /**
   * Creates a new JSON timestamp value.
   *
   * @param i the timestamp as a OffsetDateTime
   *
   * @return the timestamp value
   */
  public OracleJsonTimestampTZ createTimestampTZ(OffsetDateTime i) {
    return new OracleJsonTimestampTZImpl(i);
  };


  /**
   * Creates a new JSON interval value.
   *
   * @param d the interval as a Duration
   *
   * @return the interval value
   */
  public OracleJsonIntervalDS createIntervalDS(Duration d) {
    return new OracleJsonIntervalDSImpl(d);
  }

  /**
   * Creates a new JSON interval value.
   *
   * @param p the interval as a Period
   *
   * @return the interval value
   */
  public OracleJsonIntervalYM createIntervalYM(Period p) {
    return new OracleJsonIntervalYMImpl(p);
  }

  /**
   * Creates a new JSON vector.
   *
   * @param vector the vector as an array of floats
   *
   * @return the vector
   */
  public OracleJsonVector createVector(float[] vector) {
    try {
      return new OracleJsonVectorImpl(VectorData.encode(vector));
    } catch (SQLException e) {
      throw new OracleJsonException(e);
    }
  }

  /**
   * Creates a new JSON vector.
   *
   * @param vector the vector as an array of doubles
   *
   * @return the vector
   */
  public OracleJsonVector createVector(double[] vector) {
    try {
      return new OracleJsonVectorImpl(VectorData.encode(vector));
    } catch (SQLException e) {
      throw new OracleJsonException(e);
    }
  }

  /**
   * Creates a new JSON vector.
   *
   * @param vector the vector as an array of 8-bit integers
   *
   * @return the vector
   */
  public OracleJsonVector createVector(byte[] vector) {
    try {
      return new OracleJsonVectorImpl(VectorData.encode(vector));
    } catch (SQLException e) {
      throw new OracleJsonException(e);
    }
  }

    /**
   * This could be made more efficient.  See Java 9's InputStream.readAllBytes()
   */
  private static byte[] readInputStream(InputStream in) throws OracleJsonException {
    try {
      byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
      int n;
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      while ((n= in.read(buffer)) != -1) {
        baos.write(buffer, 0, n);
      }
      in.close();
      return baos.toByteArray();
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(OracleJsonExceptions.ORACLE_FACTORY, e);
    }
  }  
}

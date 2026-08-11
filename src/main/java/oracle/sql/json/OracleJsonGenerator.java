// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

import java.io.Closeable;
import java.io.Flushable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;

/**
 * <p>
 * Writes a JSON type value to an output source.  A JSON generator starts out
 * in <i>no context</i>.  As methods on the generator are called, the context
 * changes.  The current context determines which methods may be called next
 * on the generator, as defined below. 
 * </p>
 * <p>
 * The generator can be used to create a value as either a sequence of events 
 * or by writing full JSON values (instances of {@link OracleJsonValue}). For
 * example, the following code creates an instance of {@link OracleJsonObject}
 * and then uses {@code OracleJsonGenerator} to write it as binary JSON.
 * </p>
 * <pre><code>    OracleJsonFactory factory = new OracleJsonFactory();
 *   OracleJsonObject obj = factory.createObject();
 *   obj.put("hello", "world");
 *    
 *   ByteArrayOutputStream out = new ByteArrayOutputStream();
 *   OracleJsonGenerator generator = factory.createJsonBinaryGenerator(out);
 *   generator.write(obj);
 *   generator.close();
 *   byte[] binaryJson = out.toByteArray();</code></pre>
 * <p>The next example generates the same binary JSON value from a sequence of events:</p>
 * <pre><code>    ByteArrayOutputStream out = new ByteArrayOutputStream();
 *   OracleJsonGenerator generator = factory.createJsonBinaryGenerator(out);
 *   generator.writeStartObject();
 *   generator.writeKey("hello");
 *   generator.write("world");
 *   generator.writeEnd();
 *   generator.close();
 *   byte[] binaryJson = out.toByteArray();
 * </code></pre>
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonGenerator extends Closeable, Flushable {
  
  /**
   * Begins a new JSON object. It starts a new <i>child object context</i>
   * within which JSON name/value pairs can be written to the object.
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   */
  OracleJsonGenerator writeStartObject();
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code writeStartObject()}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator writeStartObject(String name);
  
  /**
   * Begins a new JSON array. It starts a new <i>child array context</i> within
   * which JSON values can be written to the array.
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   */
  OracleJsonGenerator writeStartArray();
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code writeStartArray()}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator writeStartArray(String name);
  
  /**
   * Writes a JSON name/value pair in the current object context. It starts a
   * field context in which a value may be written.
   * 
   * @param name the name of a JSON field
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   * 
   */
  OracleJsonGenerator writeKey(String name);
  
  /**
   * Writes the specified value.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an field, array, or no context. 
   */
  OracleJsonGenerator write(OracleJsonValue value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   *
   * @param key the name of a JSON field
   *
   * @return this generator
   *
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String key, OracleJsonValue value);
  
  /**
   * Writes the specified string value as a JSON string.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   */
  OracleJsonGenerator write(String value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, String value);
  
  /**
   * Writes the specified value as a JSON number.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonDecimal}
   * @see {@link OracleJsonParser.Event#VALUE_DECIMAL}
   */
  OracleJsonGenerator write(BigDecimal value);

  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, BigDecimal value);
  
  /**
   * Writes the specified value as a JSON number.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonDecimal}
   * @see {@link OracleJsonParser.Event#VALUE_DECIMAL}
   */
  OracleJsonGenerator write(BigInteger value);  
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, BigInteger value);
  
  /**
   * Writes the specified value as a JSON number.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonDecimal}
   * @see {@link OracleJsonParser.Event#VALUE_DECIMAL}
   */
  OracleJsonGenerator write(int value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, int value);
  
  /**
   * Writes the specified value as a JSON number.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonDecimal}
   * @see {@link OracleJsonParser.Event#VALUE_DECIMAL}
   */
  OracleJsonGenerator write(long value);

  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, long value);
  
  /**
   * Writes the specified value as a JSON double.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonDouble}
   * @see {@link OracleJsonParser.Event#VALUE_DOUBLE}
   */
  OracleJsonGenerator write(double value);  
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, double value);
  
  /**
   * Writes the specified value as a JSON float.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonFloat}
   * @see {@link OracleJsonParser.Event#VALUE_FLOAT}
   */
  OracleJsonGenerator write(float value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, float value);

  /**
   * Writes the specified value as JSON true or false.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonValue.OracleJsonType#TRUE}
   * @see {@link OracleJsonValue.OracleJsonType#FALSE}
   * @see {@link OracleJsonParser.Event#VALUE_TRUE}
   * @see {@link OracleJsonParser.Event#VALUE_FALSE}
   */
  OracleJsonGenerator write(boolean value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, boolean value);
  
  /**
   * Writes the specified value as a SQL/JSON timestamp.
   * 
   * To write the local date time as a date rather than a timestamp, use
   * {@link OracleJsonFactory#createDate(LocalDateTime)} and
   * {@link #write(OracleJsonValue)}.
   * 
   * @param value the value to be written
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonTimestamp}
   * @see {@link OracleJsonParser.Event#VALUE_TIMESTAMP}
   */
  OracleJsonGenerator write(LocalDateTime value);
  
  /**
   * Writes the specified value as a SQL/JSON timestamp with timezone.
   * 
   * @param value the value to be written
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonTimestampTZ}
   * @see {@link OracleJsonParser.Event#VALUE_TIMESTAMPTZ}
   */
  OracleJsonGenerator write(OffsetDateTime value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, LocalDateTime value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, OffsetDateTime value);
  
  /**
   * Writes the specified value as a SQL/JSON year/month interval.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonIntervalYM}
   * @see {@link OracleJsonParser.Event#VALUE_INTERVALYM}
   */
  OracleJsonGenerator write(Period value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, Period value);
  
  /**
   * Writes the specified value as a SQL/JSON day/second interval.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonIntervalDS}
   * @see {@link OracleJsonParser.Event#VALUE_INTERVALDS}
   */
  OracleJsonGenerator write(Duration value);
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, Duration value);
    
  /**
   * Writes the specified value as a SQL/JSON binary value.
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonBinary}
   * @see {@link OracleJsonParser.Event#VALUE_BINARY}
   */
  OracleJsonGenerator write(byte[] value);
  
  /**
   * Writes the specified value as a SQL/JSON binary value.  The value will
   * be annotated as an identifier.  
   * 
   * @param value the value to be written
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonBinary}
   * @see {@link OracleJsonParser.Event#VALUE_BINARY}
   * @see {@link OracleJsonBinary#isId()}
   */
  OracleJsonGenerator writeId(byte[] value);
  
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code write(value)}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator write(String name, byte[] value);

  /**
   * Writes JSON null.
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or it is called in no context when a value has already been written
   * @see {@link OracleJsonValue.OracleJsonType#NULL}
   * @see {@link OracleJsonParser.Event#VALUE_NULL}
   */
  OracleJsonGenerator writeNull();
  
  /**
   * A convenience method that is equivalent to calling {@code writeKey(name)}
   * and then {@code writeNull()}.
   * 
   * @param name the name of a JSON field
   * 
   * @return this generator
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is not called within
   * an object context
   */
  OracleJsonGenerator writeNull(String name);
  
  /**
   * Writes the end of the current object or array. The <i>parent context</i>
   * becomes the new current context.
   * 
   * @return this generator
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this method is called in no context
   */
  OracleJsonGenerator writeEnd();
  
  /**
   * Writes the events from the specified parser to this generator. The parser
   * can be either an instance of {@code javax.json.JsonParser} or {@link
   * OracleJsonParser}. The purpose of this method is to allow events from one
   * type of source to be <i>piped</i> into another type of source. For example,
   * this method can be used to convert JSON text to binary JSON by passing in a
   * JSON text parser to a binary generator.
   * <p>
   * The method writes the full value for the current event of the specified
   * parser. The parser will not be closed by this method
   * </p>
   * @param parser the parser to be written
   * @return this generator
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if this is called within an object
   * context or if it no context and a value has already been written
   */
  OracleJsonGenerator writeParser(Object parser);
  
  /**
   * Closes this generator and frees any resources associated with it.
   * The underlying output source is closed.
   * 
   * @throws OracleJsonException - if an i/o error occurs
   * @throws OracleJsonGenerationException - if an incomplete JSON value is 
   * generated (no events have been written or there is still a context that 
   * needs to be closed)
   */
  @Override
  void close();
  
  /**
   * Flushes the underlying output source. 
   * This method may do nothing for some implementations. 
   * 
   * @throws OracleJsonException - if an i/o error occurs
   */
  @Override
  void flush();
  
  /**
   * Returns a JSON-P (javax.json.stream) wrapper around this value. For example:
   * 
   * <pre>
   * <code>
   *   import javax.json.stream.JsonGenerator;
   *   ...
   *   OracleJsonGenerator oraGenerator = ...;
   *   JsonGenerator generator = oraGenerator.wrap(JsonGenerator.class);
   * </code>
   * </pre>
   * <p>
   * The returned object is a logical view of this generator. Any changes
   * to the state of this generator are observed by the returned wrapper object. 
   * </p>
   * 
   * @param wrapper the interface to view this object as. Must be assignable to 
   * {@code javax.json.stream.JsonGenerator}
   */
  <T> T wrap(Class<T> wrapper);

}

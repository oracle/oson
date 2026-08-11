// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 


package oracle.sql.json;

import java.io.Closeable;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;

/**
 * <p>Reads a JSON type value from an input source as a stream of
 * events. Call {@link #next()} to advance the parser to the next
 * event in the stream and use accessor methods such as {@link
 * #getString()} and {@link #getInt()} to access the data associated
 * with the current event. </p>
 * 
 * Example:
 * <pre><code>
 * import java.io.ByteArrayOutputStream;
 * import java.nio.ByteBuffer;
 * 
 * import oracle.sql.json.OracleJsonFactory;
 * import oracle.sql.json.OracleJsonGenerator;
 * import oracle.sql.json.OracleJsonParser;
 * import oracle.sql.json.OracleJsonParser.Event;
 * 
 * public class JsonParserExample {
 *   public static void main(String[] args) {
 *     OracleJsonFactory factory = new OracleJsonFactory();
 *     
 *     // Generate binary JSON value {"hello":"world","arr":[1,2]}
 *     ByteArrayOutputStream out = new ByteArrayOutputStream();
 *     OracleJsonGenerator generator = factory.createJsonBinaryGenerator(out);
 *     generator.writeStartObject();
 *     generator.write("hello", "world");
 *     generator.writeStartArray("arr");
 *     generator.write(1);
 *     generator.write(2);
 *     generator.writeEnd();
 *     generator.writeEnd();
 *     generator.close();
 *     
 *     byte[] binaryJson = out.toByteArray();
 *     
 *     OracleJsonParser parser = factory.createJsonBinaryParser(ByteBuffer.wrap(binaryJson));
 *     while (parser.hasNext()) {
 *       Event e = parser.next();
 *       System.out.println(e);
 *       switch (e) {
 *       case START_OBJECT:
 *       case START_ARRAY:
 *       case END_ARRAY:
 *       case END_OBJECT:
 *         break; // do nothing
 *       case KEY_NAME:
 *         System.out.println(parser.getString());
 *         break;
 *       case VALUE_STRING:
 *         System.out.println(parser.getString());
 *         break;
 *       case VALUE_DECIMAL:
 *         System.out.println(parser.getBigDecimal());
 *         break;
 *       default:
 *         break;
 *       }
 *     }
 *     parser.close();
 *   }
 * }
 * </code></pre>
 * <p>Running this example prints: </p>
 * <pre>
 * START_OBJECT
 * KEY_NAME
 * hello
 * VALUE_STRING
 * world
 * KEY_NAME
 * arr
 * START_ARRAY
 * VALUE_DECIMAL
 * 1
 * VALUE_DECIMAL
 * 2
 * END_ARRAY
 * END_OBJECT
 * </pre> 
 *
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonParser extends Closeable {

  enum Event {
    START_ARRAY,
    
    START_OBJECT, 
    
    KEY_NAME,
    
    VALUE_STRING,
    
    VALUE_DECIMAL, 
    
    VALUE_DOUBLE,
    
    VALUE_FLOAT,
    
    VALUE_BINARY,
    
    VALUE_TIMESTAMP,
    
    VALUE_TIMESTAMPTZ,
    
    VALUE_DATE,
    
    VALUE_INTERVALYM,
    
    VALUE_INTERVALDS,
    
    VALUE_TRUE,
    
    VALUE_FALSE,
    
    VALUE_NULL,
    
    VALUE_VECTOR,
    
    END_OBJECT,
    
    END_ARRAY
  }
  /**
   * Returns true if there are additional parsing events.
   *  
   * @return true if there are more parsing events.
   * 
   * @throws OracleJsonException if an io error occurs
   * @throws OracleJsonParsingException if the JSON is invalid 
   */
  boolean hasNext();
  
  /**
   * Return the next parsing event.
   * 
   * @return the next event
   * 
   * @throws OracleJsonException if an io error occurs
   * @throws OracleJsonParsingException if the JSON is invalid 
   * @throws NoSuchElementException if there are no more parsing events
   */
  Event next();
  
  /**
   * Gets a string for the current event. If the current event is
   * {@code KEY_NAME} this method returns the key value.
   * 
   * @return the string value
   * 
   * @throws IllegalStateException if the current event is not
   * {@code VALUE_STRING}, {@code KEY_NAME}, {@code VALUE_DECIMAL},
   * {@code VALUE_DOUBLE}, {@code VALUE_FLOAT}, {@code VALUE_BINARY},
   * {@code VALUE_INTERVALDS}, {@code VALUE_INTERVALYM}, {@code VALUE_DATE}, {@code VALUE_TIMESTAMP}, or
   * {@code VALUE_TIMESTAMPTZ}
   */
  String getString();

  /**
   * Returns true if the current event is an integral number.
   * Specifically, this method returns true when {@code getBigDecimal().scale()}
   * is equal to 0.
   * 
   * @return true if the current number is an integral number.
   * 
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DECIMAL}, {@code VALUE_DOUBLE}, or {@code VALUE_FLOAT}
   */
  boolean isIntegralNumber();
  
  /**
   * Returns a value equal to getBigDecimal().intValue().
   * 
   * @return the int value
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DECIMAL}, {@code VALUE_DOUBLE}, or {@code VALUE_FLOAT}
   */
  int getInt();
  
  /**
   * Returns a value equal to getBigDecimal().longValue().
   * 
   * @return the long value
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DECIMAL}, {@code VALUE_DOUBLE}, or {@code VALUE_FLOAT}
   */
  long getLong();
  
  /** 
   * Returns current event as a double.
   * 
   * @return the double
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DECIMAL}, {@code VALUE_DOUBLE}, or {@code VALUE_FLOAT}
   */
  double getDouble();
  
  /** 
   * Returns current event as a float.
   * 
   * @return the float
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DECIMAL}, {@code VALUE_DOUBLE}, or {@code VALUE_FLOAT}
   */
  float getFloat();
  
  /** 
   * Returns a value equal to {@code getBigDecimal().toBigInteger()}.
   * 
   * @return the integer
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DECIMAL}, {@code VALUE_DOUBLE}, or {@code VALUE_FLOAT}
   */
  BigInteger getBigInteger();
  
  /** 
   * Returns the current value as a decimal value. 
   * 
   * @return the decimal
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DECIMAL}, {@code VALUE_DOUBLE}, or {@code VALUE_FLOAT}
   */
  BigDecimal getBigDecimal();
  
  /**
   * Returns the current timestamptz as an{@code OffsetDateTime} value. 
   * 
   * @return the offset date time
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_TIMESTAMPTZ}
   */
  OffsetDateTime getOffsetDateTime();
  
  /**
   * Returns the current date or timestamp as a {@code LocalDateTime} value. 
   * 
   * @return the local date time
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_DATE} or {@code VALUE_TIMESTAMP}
   */
  LocalDateTime getLocalDateTime();
  
  /**
   * Return the current interval as a period.
   * 
   * @return the period
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_INTERVALYM}
   */
  Period getPeriod();
  
  /**
   * Return the current interval as a duration.
   * 
   * @return the duration
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_INTERVALDS}
   */
  Duration getDuration();

  /**
   * Return the current binary value as a byte array 
   * 
   * @return the byte array
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_BINARY}.
   */
  byte[] getBytes();
  
  /**
   * Return the current binary value to the specified output stream.
   * 
   * @throws IllegalStateException if the current event is not 
   * {@code VALUE_BINARY}. 
   */
  void getBytes(OutputStream out);

  /**
   * Return the value at the current parser event.  If the current
   * event is {@code START_ARRAY}, the result is the same as call to
   * {@code getArray()}.  If the current event is {@code START_OBJECT},
   * the result is the same as a call to {@getObject()}. In other cases,
   * the current value is read and returned.
   * 
   * @return the value
   * @throws IllegalStateException if the current event is 
   * {@code END_OBJECT} or {@code END_ARRAY}
   */
  OracleJsonValue getValue();
  
  /**
   * Returns the current array value and advances the current state
   * to the corresponding {@code END_ARRAY} event.
   * 
   * @return the array value
   * @throws IllegalStateException if the current event is not
   * {@code START_ARRAY}
   */
  OracleJsonArray getArray();
  
  /**
   * Returns the current object and advances the current state to the
   * corresponding {@code END_OBJECT} event.\
   * @return the object value
   * @throws IllegalStateException if the current event is not
   * {@code START_OBJECT}
   */
  OracleJsonObject getObject();
  
  /**
   * Skips the current array value, advancing the parser to the 
   * corresponding {@code END_ARRAY}.
   * 
   * @throws IllegalStateException if the current event is not 
   * {@code START_OBJECT}
   */
  void skipArray();
  
  /**
   * Skips the current array value, advancing the parser to the 
   * corresponding {@code END_OBJECT}.
   * 
   * @throws IllegalStateException if the current event is not 
   * {@code START_OBJECT}
   */
  void skipObject();
  
  /**
   * Returns a JSON-P wrapper around this value. For example:
   * <pre>
   * <code>
   *   import jakarta.json.stream.JsonParser;
   *   ...
   *   OracleJsonParser oraParser = ...;
   *   JsonParser parser = oraParser.wrap(JsonParser.class); 
   * </code>
   * </pre>
   * <p>
   * The returned object is a logical view of this generator.  Any changes
   * to the state of this parser are observed by the returned wrapper object.
   * </p>
   * @param wrapper the interface to view this object as.  Must be assignable to 
   * {@code javax.json.stream.JsonParser} (deprecated) or 
   * {@code jakarta.json.stream.JsonParser}
   * @return
   */
  <T> T wrap(Class<T> wrapper);
  
  /** 
   * Closes the parser and closes any resources associated with it.
   * 
   * @throws OracleJsonException if an i/o error occurs
   */
  @Override 
  void close();
}


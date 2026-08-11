// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * A JSON object (an unordered collection of zero or more key/value
 * pairs). The object may contain any subtype of {@link OracleJsonValue}
 * which includes the extended SQL types such as {@link OracleJsonTimestamp}.
 * 
 * <p>
 * Instances of {@code OracleJsonObject} may either be mutable or immutable. When
 * an instance is immutable, calling methods that would mutate the object will
 * throw {@code UnsupportedOperationException}. For example,
 * {@code OracleJsonObject} instances that are returned from a
 * {@link java.sql.ResultSet#getObject(int, Class) ResultSet} are immutable.
 * Instances that are returned from {@link OracleJsonFactory#createObject()} and
 * {@link OracleJsonFactory#createObject(OracleJsonObject)} methods are mutable.
 * </p>
 * 
 * Example:
 * <pre><code>
 * import oracle.sql.json.OracleJsonFactory;
 * import oracle.sql.json.OracleJsonObject;
 * 
 * public class JsonObjectExample {
 *   public static void main(String[] args) {
 *     OracleJsonFactory factory = new OracleJsonFactory();
 *     OracleJsonObject obj = factory.createObject();
 *     obj.put("name", "orange");
 *     obj.put("count", 12); 
 *     System.out.println(obj.toString());
 *     System.out.println(obj.getString("name"));
 *   }
 * }</code></pre>
 * <p>Running this example prints:</p>
 * <pre>
 * {"name":"orange","count":12}
 * orange
 * </pre>
 * 
 * jspiegel_oracleapi/4 2018/12/21 14:34:57 jspiegel Exp $
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonObject extends OracleJsonStructure, Map<String, OracleJsonValue> {

  /**
   * Returns the string to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code get(key).asJsonString().getString()}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonString.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  String getString(String key);
  
  /**
   * Returns the integer to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(key)).intValue()}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonNumber.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  int getInt(String key);
  
  /**
   * Returns the double to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(key)).doubleValue()}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonNumber.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  double getDouble(String key);
  
  /**
   * Returns the long to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(key)).longValue()}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonNumber.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  long getLong(String key);
  
  /**
   * Returns the decimal to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(key)).bigDecimalValue()}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonNumber.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  BigDecimal getBigDecimal(String key);
  
  
  /**
   * Returns the boolean to which the specified key is mapped. Specifically,
   * returns {@code true} if the mapped value is equal to
   * {@code OracleJsonValue.TRUE} and {@code false} if the value at the mapped
   * value is equal to {@code OracleJsonValue.FALSE}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not equal to OracleJsonValue.TRUE or OracleJsonValue.FALSE
   * @throws NullPointerException if the specified key does not have a mapping
   */
  boolean getBoolean(String key);
  
  /**
   * Returns true if the specified key is mapped to a value equal to 
   * {@link OracleJsonValue#NULL OracleJsonValue.NULL}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws NullPointerException if the specified key does not have a mapping
   */
  boolean isNull(String key);
  
  /**
   * Returns the value to which the specified key is mapped. 
   * This is a convenience method that is equivalent to
   * {@code get(key).asJsonDate().getLocalDateTime()} or
   * {@code get(key).asJsonTimestamp().getLocalDateTime()} 
   * depending if the value is a date or a timestamp.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonDate or OracleJsonTimestamp.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  LocalDateTime getLocalDateTime(String key);
  
  /**
   * Returns the value to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code get(key).asJsonTimestampTZ().getOffsetDateTime()}.
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonTimestampTZ.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  OffsetDateTime getOffsetDateTime(String key);
  
  /**
   * Returns the binary value to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code get(key).asJsonBinary().getBytes()}
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonBinary.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  byte[] getBytes(String key);
  
  /**
   * Returns the value to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code get(key).asJsonObject()}
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonObject.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  default OracleJsonObject getObject(String key) {
    return get(key).asJsonObject();
  }
  
  /**
   * Returns the value to which the specified key is mapped. This is a
   * convenience method that is equivalent to
   * {@code get(key).asJsonArray()}
   * 
   * @param key the key whose associated value is to be returned
   * 
   * @return the value to which the specified key is mapped
   * 
   * @throws ClassCastException if the value to which the specified key is
   * mapped is not an instance of OracleJsonArray.
   * @throws NullPointerException if the specified key does not have a mapping
   */
  default OracleJsonArray getArray(String key) {
    return get(key).asJsonArray();
  }
  
  /**
   * Returns the string to which the specified key is mapped. If an instance of
   * {@link OracleJsonString} is mapped to the key, then the result of calling
   * {@link OracleJsonString#getString() getString()} on the instance is returned.
   * Otherwise, the specified {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  String getString(String key, String defaultValue);
  
  /**
   * Returns the integer to which the specified key is mapped. If an instance of
   * {@link OracleJsonNumber} is mapped to the key, then the result of calling
   * {@link OracleJsonNumber#intValue() intValue()} the instance is returned.
   * Otherwise, the specified {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  int getInt(String key, int defaultValue);

  /**
   * Returns the double to which the specified key is mapped. If an instance of
   * {@link OracleJsonNumber} is mapped to the key, then the result of calling
   * {@link OracleJsonNumber#doubleValue() doubleValue()} the instance is
   * returned. Otherwise, the specified {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  double getDouble(String key, double defaultValue);
  
  /**
   * Returns the long to which the specified key is mapped. If an instance of
   * {@link OracleJsonNumber} is mapped to the key, then the result of calling
   * {@link OracleJsonNumber#longValue() longValue()} the instance is
   * returned. Otherwise, the specified {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  long getLong(String key, long defaultValue);
  
  /**
   * Returns the long to which the specified key is mapped. If an instance of
   * {@link OracleJsonNumber} is mapped to the key, then the result of calling
   * {@link OracleJsonNumber#bigDecimalValue() bigDecimalValue()} the instance is
   * returned. Otherwise, the specified {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  BigDecimal getBigDecimal(String key, BigDecimal defaultValue);

  /**
   * Returns the boolean to which the specified key is mapped. If the mapped
   * value is equal to {@link OracleJsonValue#TRUE} then {@code true} is
   * returned. If the mapped value is equal to {@link OracleJsonValue#FALSE}
   * then {@code false} is returned. Otherwise, the specified
   * {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  boolean getBoolean(String key, boolean defaultValue);
    
  /**
   * Returns the value to which the specified key is mapped. If the mapped
   * value is a date or a timestamp, then the result of 
   * calling {@code getLocalDateTime()} on the value is returned. Otherwise, the 
   * specified {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  LocalDateTime getLocalDateTime(String key, LocalDateTime defaultValue);

  /**
   * Returns the value to which the specified key is mapped. If the mapped
   * value is an instance of {@link OracleJsonTimestampTZ}, then the result of 
   * calling {@code getOffsetDateTime()} on the value is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  OffsetDateTime getOffsetDateTime(String key, OffsetDateTime defaultValue);

  /**
   * Returns the binary value to which the specified key is mapped. If an instance of
   * {@link OracleJsonBinary} is mapped to the key, then the result of calling
   * {@link OracleJsonBinary#getBytes() getBytes()} on the instance is returned.
   * Otherwise, the specified {@code defaultValue} is returned.
   * 
   * @param key the key whose associated value is to be returned
   * @param defaultValue a default value to return
   * 
   * @return the value to which the specified key is mapped or the default value
   */
  byte[] getBytes(String key, byte[] defaultValue);
  
  /**
   * Associates the specified string value with the specified key. The string is
   * added to the object as an instance of {@link OracleJsonString}. If the
   * object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported
   */
  OracleJsonValue put(String key, String value);
  
  /**
   * Associates the specified integer value with the specified key. The integer is
   * added to the object as an instance of {@link OracleJsonDecimal}. If the
   * object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue put(String key, int value);
  
  /**
   * Associates the specified long value with the specified key. The long is
   * added to the object as an instance of {@link OracleJsonDecimal}. If the
   * object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue put(String key, long value);
  
  /**
   * Associates the specified decimal value with the specified key. The decimal is
   * added to the object as an instance of {@link OracleJsonDecimal}. If the
   * object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue put(String key, BigDecimal value) throws OracleJsonException;
  
  
  /**
   * Associates the specified double value with the specified key. The double is
   * added to the object as an instance of {@link OracleJsonDouble}. If the
   * object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue put(String key, double value);

  /**
   * Associates the specified boolean value with the specified key.  If 
   * the value is {@code true}, {@link OracleJsonValue#TRUE} is associated
   * with the key and if it is {@code false} then {@link OracleJsonValue#FALSE} is 
   * associated with the key.  If the object previously contained a mapping for the key,
   * the old value is replaced. 
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue put(String key, boolean value);

  /**
   * Associates the {@link OracleJsonValue#NULL} with the specified key. If the
   * object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the {@link OracleJsonValue#NULL} value is to
   * be associated
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue putNull(String key);
  
  /**
   * Associates the specified value with the specified key. The value
   * is added to the object as an instance of {@link OracleJsonTimestamp}. If
   * the object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue put(String key, LocalDateTime value);
  
  /**
   * Associates the specified value with the specified key. The value
   * is added to the object as an instance of {@link OracleJsonTimestampTZ}. If
   * the object previously contained a mapping for the key, the old value is
   * replaced.
   * 
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * 
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   * 
   * @throws UnsupportedOperationException if the put operation is not supported 
   */
  OracleJsonValue put(String key, OffsetDateTime value);
  
  /**
   * Associates the specified binary value with the specified key. The binary
   * value is added to the object as an instance of {@link OracleJsonBinary}. If
   * the object previously contained a mapping for the key, the old value is
   * replaced.
   *
   * @param key the key with which the specified value is to be associated
   * @param values the value to be associated with the specified key
   *
   * @return the previous value associated with the key, or null if there was no
   * mapping for key.
   *
   * @throws UnsupportedOperationException if the put operation is not supported
   */
  OracleJsonValue put(String key, byte[] values);

}

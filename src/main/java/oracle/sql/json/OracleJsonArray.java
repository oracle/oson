/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.sql.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * A JSON array (an ordered sequence of zero or more values). The object may
 * contain any subtype of {@link OracleJsonValue} which includes the extended
 * SQL types such as {@link OracleJsonTimestamp}.
 * 
 * <p>
 * Instances of {@code OracleJsonArray} may either be mutable or immutable. When
 * an instance is immutable, calling methods that would mutate the array will
 * throw {@code UnsupportedOperationException}. For example,
 * {@code OracleJsonArray} instances that are returned from a
 * {@link java.sql.ResultSet#getObject(int, Class) ResultSet} are immutable.
 * Instances that are returned from {@link OracleJsonFactory#createArray()} and
 * {@link OracleJsonFactory#createArray(OracleJsonArray)} methods are mutable.
 * </p>
 * Example:
 * <pre><code>
 * import oracle.sql.json.OracleJsonArray;
 * import oracle.sql.json.OracleJsonFactory;
 * 
 * public class JsonArrayExample {
 *   public static void main(String[] args) {
 *     OracleJsonFactory factory = new OracleJsonFactory();
 * 
 *     OracleJsonArray arr = factory.createArray();
 *     arr.add("hello");
 *     arr.add(123);
 *     arr.add(true);
 *     
 *     System.out.println(arr.toString());
 *     System.out.println(arr.getInt(1));
 *   }
 * }</code></pre>
 * <p>Running this example prints:</p>
 * <pre>
 * ["hello",123,true]
 * 123
 * </pre>
 *
 * jspiegel_oracleapi/4 2018/12/21 14:34:57 jspiegel Exp $
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonArray extends OracleJsonStructure, List<OracleJsonValue> {
  
  /**
   * Returns the string at the specified position in the JSON array. This is a
   * convenience method that is equivalent to
   * {@code get(index).asJsonString().getString()}.
   * 
   * @param index the index of the JSON string value
   * @return the string value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonString
   */
  String getString(int index);
  
  /**
   * Returns the int at the specified position in the JSON array. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(index)).intValue()}.
   * 
   * @param index the index of the JSON value
   * @return the int value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonNumber
   */
  int getInt(int index);
  
  /**
   * Returns the double at the specified position in the JSON array. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(index)).doubleValue()}.
   * 
   * @param index the index of the JSON value
   * @return the double value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonNumber
   */
  double getDouble(int index);
  
  /**
   * Returns the double at the specified position in the JSON array. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(index)).bigDecimalValue()}.
   * 
   * @param index the index of the JSON value
   * @return the BigDecimal value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonNumber
   */
  BigDecimal getBigDecimal(int index);

  /**
   * Returns the long at the specified position in the JSON array. This is a
   * convenience method that is equivalent to
   * {@code ((OracleJsonNumber)get(index)).longValue()}.
   * 
   * @param index the index of the JSON value
   * @return the long value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonNumber
   */
  long getLong(int index);

  /**
   * Returns the boolean at the specified position in the JSON array.
   * Specifically, returns {@code true} if the value at the specified position
   * is equal to {@code OracleJsonValue.TRUE} and {@code false} if the value at
   * the specified position is equal to {@code OracleJsonValue.FALSE}.
   * 
   * @param index the index of the JSON value
   * @return the boolean value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not
   * equal to {@code OracleJsonValue.TRUE} or {@code OracleJson.FALSE}.
   */
  boolean getBoolean(int index);
  
  /**
   * Returns {@code true} if the value at the specified position in the array
   * is equal to {@code JsonValue.NULL}.
   * 
   * @param index the index of the JSON value
   * @return the boolean value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  boolean isNull(int index);
  
  /**
   * Returns the binary value at the specified position in the JSON array. This
   * is a convenience method that is equivalent to
   * {@code get(index).asJsonBinary().getBytes()}.
   * 
   * @param index the index of the JSON value
   * @return the binary value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonBinary
   */
  byte[] getBytes(int index);
  
  /**
   * Returns the timestamp or date value at the specified position in the JSON
   * array.  The method is equivalent to 
   * {@code get(index).asJsonDate().getLocalDateTime()}
   * or
   * {@code get(index).asJsonTimestamp().getLocalDateTime()} depending
   * if the value is a date or a timestamp.
   * 
   * @param index the index of the JSON value
   * @return the string value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonTimestamp or OracleJsonDate.
   */
  LocalDateTime getLocalDateTime(int index);
  
  /**
   * Returns the timestamptz value at the specified position in the JSON
   * array.  This is a convenience method that is equivalent to
   * {@code get(index).asJsonTimestampTZ().getOffsetDateTime()}.  
   * 
   * @param index the index of the JSON value
   * @return the string value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonDateTime.
   */
  OffsetDateTime getOffsetDateTime(int index);

  /**
   * Returns the object at the specified position in the JSON
   * array.  This is a convenience method that is equivalent to
   * {@code get(index).asJsonObject()}.  
   * 
   * @param index the index of the JSON value
   * @return the string value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonObject.
   */
  default OracleJsonObject getObject(int index) {
    return get(index).asJsonObject();
  }
  
  /**
   * Returns the array at the specified position in the JSON
   * array.  This is a convenience method that is equivalent to
   * {@code get(index).asJsonArray()}.  
   * 
   * @param index the index of the JSON value
   * @return the string value
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws ClassCastException if the value at the specified position is not an
   * instance of OracleJsonArray.
   */
  default OracleJsonArray getArray(int index) {
    return get(index).asJsonArray();
  }  
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified string. The new string is added to the array as an instance of
   * {@link OracleJsonString}.
   * 
   * @param index the index of the JSON value to replace
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, String value);

  /**
   * Replaces the value at the specified position in the array with the
   * specified integer. The new {@code int} is added to the array as an instance
   * of {@link OracleJsonDecimal}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, int value);
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified {@code double}. The new {@code double} is added to the array as
   * an instance of {@link OracleJsonDouble}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, double value);
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified {@code long}. The new {@code long} is added to the array as
   * an instance of {@link OracleJsonDecimal}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, long value);
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified decimal value. The new value is added to the array as
   * an instance of {@link OracleJsonDecimal}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws OracleJsonException if the value can not be converted to OracleJsonDecimal
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, BigDecimal value) throws OracleJsonException;
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified {@code boolean}. If the specified value is true, then
   * {@link OracleJsonValue#TRUE OracleJsonValue.TRUE} is added and otherwise
   * {@link OracleJsonValue#FALSE OracleJsonValue.FALSE}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range 
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, boolean value);
  
  /** 
   * Replaces the value at the specified position in the array with 
   * {@link OracleJsonValue#NULL OracleJsonValue.NULL}. 
   * 
   * @param index the index of the JSON value to replace
   * @return the value previously at the specified position
   * 
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue setNull(int index);
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified {@code LocalDateTime}. The {@code LocalDateTime} is added to the array as
   * an instance of {@link OracleJsonTimestamp}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, LocalDateTime value);
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified {@code OffsetDateTime}. The {@code OffsetDateTime} is added to the array as
   * an instance of {@link OracleJsonTimestampTZ}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, OffsetDateTime value);
  
  /**
   * Replaces the value at the specified position in the array with the
   * specified byte array. The byte array is added to the array as
   * an instance of {@link OracleJsonBinary}.
   * 
   * @param index the index of the JSON value to replace
   * @param value the value to be set at the specified position
   * @return the value previously at the specified position
   * 
   * @throws NullPointerException if the specified value is null
   * @throws IndexOutOfBoundsException if the index is out of range
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  OracleJsonValue set(int index, byte[] value);
  
  /**
   * Appends the specified string to the end of this array. The string is
   * appended to the array as an instance of {@link OracleJsonString}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(String value);
  
  /**
   * Appends the specified {@code integer} to the end of this array. The
   * {@code int} is appended to the array as an instance of
   * {@link OracleJsonDecimal}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(int value);
  
  /**
   * Appends the specified {@code double} to the end of this array. The
   * {@code double} is appended to the array as an instance of
   * {@link OracleJsonDouble}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(double value);
  
  /**
   * Appends the specified {@code long} to the end of this array. The
   * {@code long} is appended to the array as an instance of
   * {@link OracleJsonDecimal}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(long value);
  
  /**
   * Appends the specified decimal to the end of this array. The
   * decimal is appended to the array as an instance of
   * {@link OracleJsonDecimal}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(BigDecimal value);
  
  /**
   * Appends the specified {@code boolean} to the end of this array. If the
   * specified value is true then {@link OracleJsonValue#TRUE
   * OracleJsonValue.TRUE} is appended to the list and otherwise
   * {@link OracleJsonValue#FALSE}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(boolean value);
  
  /**
   * Appends {@link OracleJsonValue#NULL OracleJsonValue.NULL} to the end of
   * this array.
   * 
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void addNull();
    
  /**
   * Appends the specified LocalDateTime to the end of this array. The {@code LocalDateTime}
   * is appended to the array as an instance of {@link OracleJsonTimestamp}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(LocalDateTime value);
  
  /**
   * Appends the specified OffsetDateTime to the end of this array. The {@code OffsetDateTime}
   * is appended to the array as an instance of {@link OracleJsonTimestampTZ}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(OffsetDateTime value);
  
  /**
   * Appends the specified byte array to the end of this array. The byte array
   * is appended to the array as an instance of {@link OracleJsonBinary}.
   * 
   * @param value the value to be appended to this array
   * 
   * @throws NullPointerException if the specified value is null
   * @throws UnsupportedOperationException if the set operation is not supported
   */
  void add(byte[] value);
  
  /**
   * Returns a view of this array for the given element type.
   * 
   * @param c a subtype of OracleJsonValue
   * @return the view of this array
   */
  <T extends OracleJsonValue> List<T> getValuesAs(Class<T> c);  
}

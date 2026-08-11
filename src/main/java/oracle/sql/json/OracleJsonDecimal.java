// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

/**
 * A SQL/JSON fixed decimal value. Values are commonly backed by SQL NUMBER type
 * and may only support up to 38 digits of precision.
 * 
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonDecimal extends OracleJsonNumber {
  
  /** 
   * Marker indicating if this value is intended to be mapped to Java int, 
   * long, or BigDecimal/BigInteger. 
   */
  enum TargetType { INT, LONG, DECIMAL }
    /**
   * Returns a hash code equal to {@code this bigDecimalValue().hashCode()}.
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonDecimal}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonDecimal} and the result of calling
   * {@code bigDecimalValue().equals(((OracleJsonDecimal)obj).bigDecimalValue()))}
   * returns true.
   * 
   * @param obj the object to be compared for equality
   * 
   * @return true if the specified object is equal to this
   * {@code OracleJsonBinary}.
   */
  @Override
  boolean equals(Object obj);
  
  /**
   * Indicates if this value is intended to be stored in a Java int, long, or 
   * BigDecimal/BigInteger.  This value is determined by the method that created
   * this value such as {@link OracleJsonGenerator#write(int)} and 
   * {@link OracleJsonFactory#createDecimal(int)}.
   * 
   * @return the target type or null if not specified. 
   */
  TargetType getTargetType();
  
}

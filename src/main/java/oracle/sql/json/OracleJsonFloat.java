// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

/**
 * A 32-bit, single-precision floating-point number.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonFloat extends OracleJsonValue, OracleJsonNumber {
    /**
   * Returns a hash code equal to 
   * {@code java.lang.Float.hashCode(floatValue())}.
   *
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonFloat}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonFloat} and the {@code floatValue()} values are equal. 
   * 
   * @param obj the object to be compared for equality
   * 
   * @return true if the specified object is equal to this
   * {@code OracleJsonFloat}.
   */
  @Override
  boolean equals(Object obj);
}


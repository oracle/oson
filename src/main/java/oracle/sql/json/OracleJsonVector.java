/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2024, 2026, Oracle and/or its affiliates. */
package oracle.sql.json;

import oracle.sql.VECTOR;

/**
 * A SQL/JSON vector.
 * 
 */
public interface OracleJsonVector extends OracleJsonValue {

    /**
   * Returns this vector as an array of doubles. The returned value is a lossless
   * representation of the vector.
   * 
   * @return An array containing the dimension values of a vector.
   * @throws OracleJsonException if an error occurs converting the vector to an
   *                             array of doubles
   */
  double[] getDoubleArray();
  /**
   * Converts this vector into an array of floats.
   * 
   * <p>
   * This method may perform lossy conversions as specified in the "SQL to Java
   * Conversions" section of the JavaDoc for {@link oracle.jdbc.OracleType#VECTOR}
   * </p>
   * 
   * @return An array containing the dimension values of a vector.
   * @throws OracleJsonException if an error occurs converting the vector to an
   *                             array of floats
   */
  float[] getFloatArray();
  /**
   * Converts this vector into an array of 8-bit integers.
   * 
   * <p>
   * This method may perform lossy conversions as specified in the "SQL to Java
   * Conversions" section of the JavaDoc for {@link oracle.jdbc.OracleType#VECTOR}
   * </p>
   * 
   * @return An array containing the dimension values of a vector.
   * @throws OracleJsonException if an error occurs converting the vector to an
   *                             array of integers
   */
  byte[] getByteArray();

  /**
   * Returns a hash code for the vector. 
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonVector}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonVector} that is equal to this one.
   * 
   * @param obj the object to be compared for equality
   * 
   * @return true if the specified object is equal to this
   * {@code OracleJsonVector}.
   */
  @Override
  boolean equals(Object obj);
  

}

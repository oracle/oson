// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

/**
 * A SQL/JSON string value.
 * 
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonString extends OracleJsonValue {

  /**
   * Returns this value as a {@code String}.
   * 
   * @return the string
   */
  String getString();

  /**
   * Returns this value as a {@code CharSequence}.
   * 
   * @return the character sequence
   */
  CharSequence getChars();
    /**
   * Returns a hash code that is equal to {@code getString().hashCode()}.
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonString}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonString} and
   * {@code getString().equals(((OracleJsonString)obj).getString())}
   * 
   * @param obj the object to be compared for equality
   * 
   * @return true if the specified object is equal to this
   * {@code OracleJsonString}.
   */
  @Override
  boolean equals(Object obj);
}

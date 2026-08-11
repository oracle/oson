// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

import java.time.LocalDateTime;

/**
 * A SQL/JSON DATE value. The supported value space of this type is equivalent
 * to that of the Oracle DATE data type.
 * 
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonDate extends OracleJsonValue {
  
  /**
   * Returns this value as an {@code LocalDateTime}.
   * 
   * @return the {@code LocalDateTime}
   */
  LocalDateTime getLocalDateTime();
    /**
   * Returns this date as a String in ISO 8601 format.
   * 
   * @return the string value
   */
  String getString();
  
  /**
   * Returns a hash code that is equal to
   * {@code java.util.Arrays.hashCode(getDATE().getBytes())}
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonDate}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonDate} and is equal to this date.
   *
   * @param other the object to be compared for equality
   *
   * @return true if the specified object is equal to this
   * {@code OracleJsonDate}.
   */
  @Override
  boolean equals(Object other);
}


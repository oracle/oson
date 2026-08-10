/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2020, 2026, Oracle and/or its affiliates.*/

package oracle.sql.json;

import java.time.OffsetDateTime;

/**
 * A SQL/JSON timestamp (with a timezone). 
 * 
 */
public interface OracleJsonTimestampTZ extends OracleJsonValue {
    
  /**
   * Returns this value as a {@code OffsetDateTime}.
   * 
   * @return the {@code OffsetDateTime}
   */
  OffsetDateTime getOffsetDateTime();

    /**
   * Returns this date as a String in ISO 8601 format.
   * 
   * @return the string value
   */
  String getString();
  
  /**
   * Returns a hash code that is equal to
   * {@code java.util.Arrays.hashCode(getTIMESTAMPTZ().getBytes())}
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonTimestampTZ}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonTimestampTZ} and is equal to this timestamp.
   *
   * @param other the object to be compared for equality
   *
   * @return true if the specified object is equal to this
   * {@code OracleJsonTimestampTZ}.
   */
  @Override
  boolean equals(Object other);
}



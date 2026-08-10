/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.sql.json;

import java.time.Duration;

/**
 * A SQL/JSON time interval in days, hours, minutes, and seconds.
 * 
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonIntervalDS extends OracleJsonValue {
  
  /** 
   * Returns this interval as a {@code Duration}.
   * 
   * @return the interval
   */
  Duration getDuration();
    /**
   * Returns this interval as an ISO 8601 String.
   * 
   * @return the string value
   */
  String getString();
  
  /**
   * Returns a hash code equal to {@code Arrays.hashCode(getINTERVALDS().getBytes()) }.
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonIntervalDS}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonIntervalDS} and the intervals are equal.
   * 
   * @param obj the object to be compared for equality
   * 
   * @return true if the specified object is equal to this
   * {@code OracleJsonIntervalDS}.
   */
  @Override
  boolean equals(Object obj);
}

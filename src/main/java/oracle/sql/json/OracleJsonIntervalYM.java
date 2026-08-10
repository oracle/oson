/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.sql.json;

import java.time.Period;

/**
 * A SQL/JSON time interval in years and months.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonIntervalYM extends OracleJsonValue {
  
  /** 
   * Returns this interval as a {@code Period}.
   * 
   * @return the interval
   */
  Period getPeriod();
    /**
   * Returns this interval as an ISO 8601 String.
   * 
   * @return the string value
   */
  String getString();
  
  /**
   * Returns a hash code equal to
   * {@code Arrays.hashCode(getINTERVALYM().getBytes()) }.
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonIntervalYM}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonIntervalYM} and the intervals are equal.
   * 
   * @param obj the object to be compared for equality
   * 
   * @return true if the specified object is equal to this
   * {@code OracleJsonIntervalYM}.
   */
  @Override
  boolean equals(Object obj);
  
}

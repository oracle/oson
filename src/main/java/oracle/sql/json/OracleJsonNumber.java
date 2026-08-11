// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 


package oracle.sql.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Super type of {@link OracleJsonDecimal}, {@link OracleJsonDouble}, and {@link OracleJsonFloat}.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonNumber extends OracleJsonValue {
  
  /**
   * Returns true when {@code bigDecimalValue().scale() == 0}.
   * 
   * @return true if this is an integral number 
   */
  boolean isIntegral();
  
  /**
   * Returns this number as an int. This number may lose information about the 
   * overall magnitude and precision of the number value. 
   * 
   * @return the number as an int
   */
  int intValue();
  
  /**
   * Returns this number as an int.
   * 
   * @return the number as an int
   * 
   * @throws ArithmeticException if the number has a non-fractional part or if
   * it does not fit in an int
   */
  int intValueExact();
  
  
  /**
   * Returns this number as a long. This number may lose information about the 
   * overall magnitude and precision of the number value. 
   * 
   * @return the number as an long
   */
  long longValue();
  
  /**
   * Returns this number as an long.
   * 
   * @return the number as an long
   * 
   * @throws ArithmeticException if the number has a non-fractional part or if
   * it does not fit in an long
   */
  long longValueExact();

  /**
   * Returns this number as a {@code BigDecimal} value.
   * 
   * @return the number as a {@code BigDecimal} 
   */
  BigDecimal bigDecimalValue();
  
  /**
   * Returns this number as a {@code BigInteger}. This number may lose information about the 
   * overall magnitude and precision of the number value. 
   * 
   * @return the number as an {@code BigInteger}
   */
  BigInteger bigIntegerValue();
  
  /**
   * Returns this number as a {@code BigInteger}.
   * 
   * @return the number as an {@code BigInteger}
   * 
   * @throws ArithmeticException if the number has a non-fractional part or if
   * it does not fit in an long
   */
  BigInteger bigIntegerValueExact();
  
  /**
   * Returns this value as a {@code double}.  For values of type {@link OracleJsonType#DECIMAL} 
   * this method may lose information about the overall magnitude and precision of the number value.
   * 
   * @return the number as a {@code double}.
   */
  double doubleValue();
  
  /**
   * Returns this value as a {@code float}.  For values of type {@link OracleJsonType#DECIMAL} or {@link OracleJsonType#DOUBLE} 
   * this method may lose information about the overall magnitude and precision of the number value.
   * 
   * @return the number as a {@code float}.
   */
  float floatValue();
  
}

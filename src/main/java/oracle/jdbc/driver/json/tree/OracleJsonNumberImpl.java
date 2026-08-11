// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.math.BigDecimal;
import java.math.BigInteger;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;

/**
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public abstract class OracleJsonNumberImpl {

  public abstract BigDecimal bigDecimalValue();
  
  public boolean isIntegral() {
    return bigDecimalValue().scale() == 0;
  }
  
  public int intValue() {
    return bigDecimalValue().intValue();
  }
  
  public int intValueExact() {
    return bigDecimalValue().intValueExact();
  }
  
  public long longValue() {
    return bigDecimalValue().longValue();
  }

  public long longValueExact() {
    return bigDecimalValue().longValueExact();
  }
  
  public BigInteger bigIntegerValue() {
    return bigDecimalValue().toBigInteger();
  }
  
  public BigInteger bigIntegerValueExact() {
    return bigDecimalValue().toBigIntegerExact();
  }
  
  public double doubleValue() {
    return bigDecimalValue().doubleValue();
  }

  /** The required semantics of toString() by JSON-P */
  public String toString() {
    return bigDecimalValue().toString();
  }
  
  /** The string that will be used to serialize this number within an object or an array */
  public abstract String getString();
  
  public float floatValue() {
    return bigDecimalValue().floatValue();
  }
  
  protected ExceptionFactory getExceptionFactory() {
    return OracleJsonExceptions.ORACLE_FACTORY;
  }

}

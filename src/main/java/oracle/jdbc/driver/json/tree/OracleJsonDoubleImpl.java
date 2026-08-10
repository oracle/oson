/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.math.BigDecimal;

import oracle.jdbc.driver.json.Jsonp;
import oracle.sql.json.OracleJsonDouble;


/**
 * @since   release specific (what release of product did this appear in)
 */
public class OracleJsonDoubleImpl extends OracleJsonNumberImpl implements OracleJsonDouble {

  public static String NAN = "\"Nan\"";
  
  double value;
  
  public OracleJsonDoubleImpl(double value) {
    this.value = value;
  }
  
  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.DOUBLE;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonDouble)) {
      return false;
    }
    OracleJsonDouble otherd = (OracleJsonDouble)other;
    return Double.doubleToLongBits(value) == 
        Double.doubleToLongBits(otherd.doubleValue());
  }
  
  @Override
  public int hashCode() {
    return Double.hashCode(value);
  }
  
  public BigDecimal bigDecimalValue() {
    return BigDecimal.valueOf(value);
  }

  @Override
  public double doubleValue() {
    return value;
  }
  
  @Override
  public String getString() {
    if (value == Double.POSITIVE_INFINITY) {
      return OracleJsonDecimalImpl.POSITIVE_INF;
    } else if (value == Double.NEGATIVE_INFINITY) {
      return OracleJsonDecimalImpl.NEGATIVE_INF;
    } else if (Double.isNaN(value)) {
      return NAN;
    }
    return Double.toString(value);
  }
    @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaDoubleImpl(value));
    } else {
      return c.cast(new JsonpPrimitive.JsonpDoubleImpl(value));
    }
  }
  
  @Override
  public String toString() {
    if (value == Double.POSITIVE_INFINITY) {
      return OracleJsonDecimalImpl.POSITIVE_INF;
    } else if (value == Double.NEGATIVE_INFINITY) {
      return OracleJsonDecimalImpl.NEGATIVE_INF;
    } else if (Double.isNaN(value)) {
      return NAN;
    } else {
      return BigDecimal.valueOf(value).toString();
    }
  }
}

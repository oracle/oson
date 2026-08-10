/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.math.BigDecimal;

import oracle.jdbc.driver.json.Jsonp;
import oracle.sql.json.OracleJsonFloat;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonFloatImpl extends OracleJsonNumberImpl implements OracleJsonFloat {
  
  private static String NAN = "\"Nan\"";
  
  float value;
  
  public OracleJsonFloatImpl(float value) {
    this.value = value;
  }
  
  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.FLOAT;
  }  
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonFloat)) {
      return false;
    }
    OracleJsonFloat otherd = (OracleJsonFloat)other;
    return Float.floatToIntBits(value) == 
        Float.floatToIntBits(otherd.floatValue());
  }
  
  @Override
  public int hashCode() {
    return Float.hashCode(value);
  }
  
  public BigDecimal bigDecimalValue() {
    return BigDecimal.valueOf(value);
  }

  public float floatValue() {
    return value;
  }

  @Override
  public String getString() {
    if (value == Float.POSITIVE_INFINITY) {
      return OracleJsonDecimalImpl.POSITIVE_INF;
    } else if (value == Float.NEGATIVE_INFINITY) {
      return OracleJsonDecimalImpl.NEGATIVE_INF;
    } else if (Float.isNaN(value)) {
      return NAN;
    }
    return Float.toString(value);
  }
    
  @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaFloatImpl(value));
    } else {
      return c.cast(new JsonpPrimitive.JsonpFloatImpl(value));
    }
  }
  
  @Override
  public String toString() {
    if (value == Float.POSITIVE_INFINITY) {
      return OracleJsonDecimalImpl.POSITIVE_INF;
    } else if (value == Float.NEGATIVE_INFINITY) {
      return OracleJsonDecimalImpl.NEGATIVE_INF;
    } else if (Float.isNaN(value)) {
      return NAN;
    } else {
      return BigDecimal.valueOf(value).toString();
    }
  }
}

/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.io.IOException;
import java.math.BigDecimal;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.sql.json.OracleJsonDecimal;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonStringNumberImpl extends OracleJsonNumberImpl implements OracleJsonDecimal {
  
  String value;
  
  public OracleJsonStringNumberImpl(String value) {
    this.value = value;
  }

  public String getString() {
    return value;
  }
  
  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.DECIMAL;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonDecimal)) {
      return false;
    }
    OracleJsonDecimal othern = (OracleJsonDecimal)other;
    return bigDecimalValue().equals(othern.bigDecimalValue());
  }
  
  @Override
  public int hashCode() {
    return bigDecimalValue().hashCode();
  }
  
  public BigDecimal bigDecimalValue() {
    return new BigDecimal(value);
  }

  public void serialize(Appendable out) {
    try {
      out.append(value);
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
  }
    @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaStringNumberImpl(value));
    } else {
      return c.cast(new JsonpPrimitive.JsonpStringNumberImpl(value));
    }
  }

  @Override
  public TargetType getTargetType() {
    return null;
  }
}

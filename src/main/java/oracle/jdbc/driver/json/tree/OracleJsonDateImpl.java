// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.time.LocalDateTime;
import java.util.Arrays;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.sql.json.OracleJsonDate;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonDateImpl implements OracleJsonDate {
  
  byte[] raw;

  public OracleJsonDateImpl(byte[] raw) {
    this.raw = raw;
  }
  
  public OracleJsonDateImpl(LocalDateTime i) {
    this.raw = OsonPrimitiveConversions.toOracleDate(getExceptionFactory(), i);
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.DATE;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonDate)) {
      return false;
    }
    if (!(other instanceof OracleJsonDateImpl)) {
      throw new UnsupportedOperationException();
    }
    OracleJsonDateImpl otherd = (OracleJsonDateImpl)other;
    return Arrays.equals(otherd.raw, raw);
  }
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(raw);
  }

  @Override
  public String toString() {
    return JsonSerializerImpl.serializeString(getString());
  }
  
  @Override
  public LocalDateTime getLocalDateTime() {
    byte[] raw = raw();
    return OsonPrimitiveConversions.dateToLocalDateTime(getExceptionFactory(), raw);
  }
  
  public String getString() { 
    return OsonPrimitiveConversions.dateToString(getExceptionFactory(), raw);
  }
  
  public byte[] raw() {
    return raw;
  }
    @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaDateImpl(raw));
    } else {
      return c.cast(new JsonpPrimitive.JsonpDateImpl(raw));
    }
  }
  
  public ExceptionFactory getExceptionFactory() {
    return OracleJsonExceptions.ORACLE_FACTORY;
  }
}

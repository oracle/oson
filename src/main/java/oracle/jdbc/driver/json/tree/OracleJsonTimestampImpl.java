/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.time.LocalDateTime;
import java.util.Arrays;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.sql.json.OracleJsonTimestamp;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonTimestampImpl implements OracleJsonTimestamp {
  
  byte[] raw;
  
  public OracleJsonTimestampImpl(byte[] raw) {
    this.raw = raw;
  }
  
  public OracleJsonTimestampImpl(LocalDateTime value) {
    this.raw = OsonPrimitiveConversions.toOracleTimestamp(getExceptionFactory(), value);
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.TIMESTAMP;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonTimestamp)) {
      return false;
    }
    if (!(other instanceof OracleJsonTimestampImpl)) {
      throw new UnsupportedOperationException();
    }
    OracleJsonTimestampImpl otherd = (OracleJsonTimestampImpl)other;
    return Arrays.equals(otherd.raw, raw);
  }
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(raw);
  }

  @Override
  public String getString() {
    return OsonPrimitiveConversions.timestampToString(getExceptionFactory(), raw);
  }
  
  @Override
  public String toString() {
    return JsonSerializerImpl.serializeString(getString());
  }
    
  @Override
  public LocalDateTime getLocalDateTime() {
    byte[] raw = raw();
    return OsonPrimitiveConversions.timestampToLocalDateTime(getExceptionFactory(), raw);
  }

  public byte[] raw() {
    return raw;
  }
    @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaTimestampImpl(raw));
    } else {
      return c.cast(new JsonpPrimitive.JsonpTimestampImpl(raw));
    }
  }
  
  public ExceptionFactory getExceptionFactory() {
    return OracleJsonExceptions.ORACLE_FACTORY;
  }

}

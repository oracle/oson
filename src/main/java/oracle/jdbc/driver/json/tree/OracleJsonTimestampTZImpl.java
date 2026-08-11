// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.time.OffsetDateTime;
import java.util.Arrays;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.sql.json.OracleJsonTimestampTZ;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonTimestampTZImpl implements OracleJsonTimestampTZ {
  
  byte[] raw;
  
  public OracleJsonTimestampTZImpl(byte[] raw) {
    this.raw = raw;
    OsonPrimitiveConversions.assertNoRegionTimestampTZ(getExceptionFactory(), raw);
  }
  
  public OracleJsonTimestampTZImpl(OffsetDateTime i) {
    this.raw = OsonPrimitiveConversions.toOracleTimestampTZ(getExceptionFactory(), i);
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.TIMESTAMPTZ;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonTimestampTZ)) {
      return false;
    }
    if (!(other instanceof OracleJsonTimestampTZImpl)) {
      throw new UnsupportedOperationException();
    }
    OracleJsonTimestampTZImpl otherd = (OracleJsonTimestampTZImpl)other;
    return Arrays.equals(otherd.raw, raw);
  }
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(raw);
  }

  @Override
  public String getString() {
    return OsonPrimitiveConversions.timestampTZToString(getExceptionFactory(), raw);
  }
  
  @Override
  public String toString() {
    return JsonSerializerImpl.serializeString(getString());
  }

  public byte[] raw() {
    return raw;
  }
    @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaTimestampTZImpl(raw));
    } else {
      return c.cast(new JsonpPrimitive.JsonpTimestampTZImpl(raw));
    }
  }
  
  public ExceptionFactory getExceptionFactory() {
    return OracleJsonExceptions.ORACLE_FACTORY;
  }

  @Override
  public OffsetDateTime getOffsetDateTime() {
    return OsonPrimitiveConversions.timestamptzToOffsetDateTime(getExceptionFactory(), raw);
  }

}

/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.time.Duration;
import java.util.Arrays;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.sql.json.OracleJsonIntervalDS;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonIntervalDSImpl implements OracleJsonIntervalDS {

  public static final int INTERVALDS_LEN = 11;
  
  byte[] raw;
  
  public OracleJsonIntervalDSImpl(byte[] raw) {
    this.raw = raw;
  }
  
  public OracleJsonIntervalDSImpl(Duration d) {
    this.raw = OsonPrimitiveConversions.durationToIntervalDS(d);
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.INTERVALDS;
  }  
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonIntervalDS)) {
      return false;
    }
    if (!(other instanceof OracleJsonIntervalDSImpl)) {
      throw new UnsupportedOperationException();
    }
    OracleJsonIntervalDSImpl otheri = (OracleJsonIntervalDSImpl)other;
    return Arrays.equals(otheri.raw, raw);
  }
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(raw);
  }
  
  @Override
  public String toString() {
    return JsonSerializerImpl.serializeString(getString());
  }
  
  public String getString() {
    return OsonPrimitiveConversions.serializeIntervalDS(OracleJsonExceptions.ORACLE_FACTORY, raw());
  }
  
  public byte[] raw() {
    return raw;
  }

  public Duration getDuration() {
    return OsonPrimitiveConversions.intervalDSToDuration(raw());
  }

  public static String serializeDuration(Duration d, ExceptionFactory f) {
    byte[] raw = OsonPrimitiveConversions.durationToIntervalDS(d);
    return OsonPrimitiveConversions.serializeIntervalDS(f, raw);
  }
  
    
  @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaIntervalDSImpl(raw));
    } else {
      return c.cast(new JsonpPrimitive.JsonpIntervalDSImpl(raw));
    }
  }

}

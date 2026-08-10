/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>
 */
package oracle.jdbc.driver.json.tree;

import java.time.Period;
import java.util.Arrays;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.sql.json.OracleJsonIntervalYM;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonIntervalYMImpl implements OracleJsonIntervalYM {
  
  public static final int INTERVALYM_LEN = 5;
  
  byte[] raw;
  
  public OracleJsonIntervalYMImpl(byte[] raw) {
    this.raw = raw;
  }
  
  public OracleJsonIntervalYMImpl(Period p) {
    this.raw = OsonPrimitiveConversions.periodToIntervalYM(OracleJsonExceptions.ORACLE_FACTORY, p);
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.INTERVALYM;
  }  
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonIntervalYM)) {
      return false;
    }
    if (!(other instanceof OracleJsonIntervalYMImpl)) {
      throw new UnsupportedOperationException();
    }
    OracleJsonIntervalYMImpl otheri = (OracleJsonIntervalYMImpl)other;
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
    return OsonPrimitiveConversions.serializeIntervalYM(OracleJsonExceptions.ORACLE_FACTORY, raw());
  }
  
  public byte[] raw() {
    return raw;
  }
  
  public Period getPeriod() {
    return OsonPrimitiveConversions.intervalYMToPeriod(raw());
  }
  
  public static String serializePeriod(Period p, ExceptionFactory f) {
    byte[] raw = OsonPrimitiveConversions.periodToIntervalYM(f, p);
    return OsonPrimitiveConversions.serializeIntervalYM(f, raw);
  }
    @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaIntervalYMImpl(raw));
    } else {
      return c.cast(new JsonpPrimitive.JsonpIntervalYMImpl(raw));
    }
  }

}

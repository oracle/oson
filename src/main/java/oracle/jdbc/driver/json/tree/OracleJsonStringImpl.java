// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.sql.SQLException;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonString;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonStringImpl implements OracleJsonString {

  String value;
  
  public OracleJsonStringImpl(String value) {
    this.value = value;
  }
  
  public String getString() {
    return value;
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.STRING;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonString)) {
      return false;
    }
    return this == other || getString().equals(((OracleJsonString)other).getString());
  }

  public int hashCode() {
    return getString().hashCode();
  }
  
  public CharSequence getChars() {
    return getString();
  }
  
  @Override
  public String toString() {
    return JsonSerializerImpl.serializeString(getString());
  }

  @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaStringImpl(value));
    } else {
      return c.cast(new JsonpPrimitive.JsonpStringImpl(value));
    }
  }
  
    protected ExceptionFactory getExceptionFactory() { 
    return OracleJsonExceptions.ORACLE_FACTORY;
  }
}

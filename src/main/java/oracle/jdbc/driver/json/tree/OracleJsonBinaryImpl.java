/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.util.Arrays;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.sql.json.OracleJsonBinary;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonBinaryImpl implements OracleJsonBinary {
  protected static final char[] HEX_UPPER = "0123456789ABCDEF".toCharArray();
  protected static final char[] HEX_LOWER = "0123456789abcdef".toCharArray();
  
  private byte[] bytes;
  
  boolean isId;
  
  public OracleJsonBinaryImpl(byte[] bytes, boolean isId) {
    this.bytes = bytes;
    this.isId = isId;
  }
  
  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.BINARY;
  }
  
  public boolean isId() {
    return isId;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonBinary)) {
      return false;
    }
    OracleJsonBinary b = (OracleJsonBinary)other;
    byte[] bytes1 = getBytes();
    byte[] bytes2 = b.getBytes();
    return Arrays.equals(bytes1, bytes2);
  }
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(getBytes());
  }
  
  @Override
  public String toString() {
    return JsonSerializerImpl.serializeString(getString());
  }

  @Override
  public byte[] getBytes() {
    return bytes;
  }

  @Override
  public String getString() {
    return getString(bytes, isId);
  }
  
  public static String getString(byte[] bytes, boolean isId) {
    return new String(serializeBinary(bytes, isId ? HEX_LOWER : HEX_UPPER));
  }
    private static char[] serializeBinary(byte[] bytes, char[] hex) {
    char[] chars = new char[bytes.length * 2];
    for (int i=0, j=0; i < bytes.length;) {
      byte b = bytes[i++];
      chars[j++] = hex[(b >> 4) & 0xF];
      chars[j++] = hex[b & 0xF];
    }
    return chars;
  }
  
  @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaBinaryImpl(bytes, isId));
    } else {
      return c.cast(new JsonpPrimitive.JsonpBinaryImpl(bytes, isId));
    }
  }
  
}

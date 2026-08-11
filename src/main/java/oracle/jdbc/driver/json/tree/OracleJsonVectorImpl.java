// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.io.StringWriter;
import java.sql.SQLException;

import oracle.jdbc.driver.VectorData;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonVector;

/**
 * A JSON Vector
 * 
 */
public class OracleJsonVectorImpl implements OracleJsonVector {

  /** The vector */
  private byte[] raw;
  
  public OracleJsonVectorImpl(byte[] raw) {
    this.raw = raw;
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.VECTOR;
  }
  
  @Override
  public String toString() {
    StringWriter writer = new StringWriter();
    JsonSerializerImpl ser = new JsonSerializerImpl(writer);
    ser.write(this);
    ser.close();
    return writer.toString();
  }

    @Override
  public double[] getDoubleArray() {
    try {
      return VectorData.decode(raw, double[].class, false);
    } catch (SQLException e) {
      throw new OracleJsonException(e);
    }
  }

  @Override
  public float[] getFloatArray() {
    try {
      return VectorData.decode(raw, float[].class, false);
    } catch (SQLException e) {
      throw new OracleJsonException(e);
    }
  }

  @Override
  public byte[] getByteArray() {
    try {
      return VectorData.decode(raw, byte[].class, false);
    } catch (SQLException e) {
      throw new OracleJsonException(e);
    }
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof OracleJsonVectorImpl
      && VectorData.equals(raw, ((OracleJsonVectorImpl)other).raw);
  }

  @Override
  public int hashCode() {
    return VectorData.hashCode(raw);
  }

  @Override
  public <T> T wrap(Class<T> wrapper) {
    return wrapper.cast(new JakartaPrimitive.JakartaVectorImpl(raw));
  }

  public byte[] raw() {
    return raw;
  }

}

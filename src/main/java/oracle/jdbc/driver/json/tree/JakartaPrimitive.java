/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.AbstractList;
import java.util.List;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonString;

import oracle.jdbc.driver.json.JakartaExceptionFactory;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.jdbc.driver.VectorData;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonValue;

/**
 * 
 * Adapter for JSON-P 2.0 (jakarta.json)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpPrimitive.
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpPrimitive as well.
 *       
 */
public final class JakartaPrimitive {
  
  public static class JakartaNumberImpl extends DefaultJsonNumber {
    
    byte[] raw;
    
    OracleJsonDecimal.TargetType type;
    
    public JakartaNumberImpl(byte[] raw, OracleJsonDecimal.TargetType type) {
      this.raw = raw;
      this.type = type;
    }

    @Override
    public final BigDecimal bigDecimalValue() {
      return OsonPrimitiveConversions.toBigDecimal(raw);
    }
    
    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonDecimalImpl(raw, type);
    }
  }
  
  public static class JakartaFloatImpl extends DefaultJsonNumber {
    
    private float value;
    
    public JakartaFloatImpl(float value) {
      this.value = value;
    }

    @Override
    public BigDecimal bigDecimalValue() {
      return BigDecimal.valueOf(value);
    }
    
    @Override
    public float floatValue() {
      return value;
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonFloatImpl(value);
    }
  } 
  
  public static class JakartaDoubleImpl extends DefaultJsonNumber {
    
    double value;
    
    public JakartaDoubleImpl(double value) {
      this.value = value;
    }
    
    @Override
    public double doubleValue() {
      return value;
    }

    @Override
    public BigDecimal bigDecimalValue() {
      return BigDecimal.valueOf(value);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonDoubleImpl(value);
    }
  }
  
  public static class JakartaStringImpl implements DefaultJsonString {

    String value;
    
    public JakartaStringImpl(String value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object other) {
      return stringsEqual(this, other);
    }
    
    @Override
    public int hashCode() {
      return getString().hashCode();
    }
    
    @Override
    public String getString() {
      return value;
    }
    
    @Override
    public String toString() {
      return JsonSerializerImpl.serializeString(getString());
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonStringImpl(value);
    }
  }
  
  public static class JakartaBinaryImpl implements DefaultJsonString {

    byte[] bytes;
    
    boolean isId;
    
    public JakartaBinaryImpl(byte[] bytes, boolean isId) {
      this.bytes = bytes;
      this.isId = isId;
    }

    @Override
    public boolean equals(Object other) {
      return stringsEqual(this, other);
    } 
    
    @Override
    public int hashCode() {
      return getString().hashCode();
    }
    
    @Override
    public String toString() {
      return JsonSerializerImpl.serializeString(getString());
    }
    
    @Override
    public String getString() {
      return OracleJsonBinaryImpl.getString(bytes, isId);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonBinaryImpl(bytes, isId);
    }
    
  }
  
  public static class JakartaDateImpl implements DefaultJsonString {
    
    byte[] raw;
    
    public JakartaDateImpl(byte[] raw) {
      this.raw = raw;
    }

    @Override
    public boolean equals(Object other) {
      return stringsEqual(this, other);
    }
    
    @Override
    public int hashCode() {
      return getString().hashCode();
    }
    
    @Override
    public String toString() {
      return JsonSerializerImpl.serializeString(getString());
    }
    
    @Override
    public String getString() {
      return OsonPrimitiveConversions.dateToString(JakartaExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonDateImpl(raw);
    }
  }
  
  public static class JakartaTimestampImpl implements DefaultJsonString {

    private byte[] raw;
    
    public JakartaTimestampImpl(byte[] raw) {
      this.raw = raw;
    }

    @Override
    public boolean equals(Object other) {
      return stringsEqual(this, other);
    }
    
    @Override
    public int hashCode() {
      return getString().hashCode();
    }
    
    @Override
    public String toString() {
      return JsonSerializerImpl.serializeString(getString());
    }    
    
    @Override
    public String getString() {
      return OsonPrimitiveConversions.timestampToString(JakartaExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonTimestampImpl(raw);
    }
  }  
  
  public static class JakartaTimestampTZImpl implements DefaultJsonString {

    private byte[] raw;
    
    public JakartaTimestampTZImpl(byte[] raw) {
      this.raw = raw;
    }

    @Override
    public boolean equals(Object other) {
      return stringsEqual(this, other);
    }
    
    @Override
    public int hashCode() {
      return getString().hashCode();
    }
    
    @Override
    public String toString() {
      return JsonSerializerImpl.serializeString(getString());
    }    
    
    @Override
    public String getString() {
      return OsonPrimitiveConversions.timestampTZToString(JakartaExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonTimestampTZImpl(raw);
    }
  }
  
  public static class JakartaIntervalDSImpl implements DefaultJsonString {
    
    private byte[] raw;
    
    public JakartaIntervalDSImpl(byte[] raw) {
      this.raw = raw;
    }

    @Override
    public boolean equals(Object other) {
      return stringsEqual(this, other);
    }
    
    @Override
    public int hashCode() {
      return getString().hashCode();
    }

    @Override
    public String toString() {
      return JsonSerializerImpl.serializeString(getString());
    }
    
    @Override
    public String getString() {
      return OsonPrimitiveConversions.serializeIntervalDS(JakartaExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonIntervalDSImpl(raw);
    }
    
  }  
  
  public static class JakartaIntervalYMImpl implements DefaultJsonString {

    byte[] raw;
    
    public JakartaIntervalYMImpl(byte[] raw) {
      this.raw = raw;
    }

    @Override
    public boolean equals(Object other) {
      return stringsEqual(this, other);
    }
    
    @Override
    public int hashCode() {
      return getString().hashCode();
    }

    @Override
    public String toString() {
      return JsonSerializerImpl.serializeString(getString());
    }
    
    @Override
    public String getString() {
      return OsonPrimitiveConversions.serializeIntervalYM(JakartaExceptionFactory.INSTANCE, raw);
    }
    
    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonIntervalYMImpl(raw);
    }
  }  
  
  public static class JakartaStringNumberImpl extends DefaultJsonNumber {

    private String value;
    
    public JakartaStringNumberImpl(String value) {
      this.value = value;
    }

    @Override
    public BigDecimal bigDecimalValue() {
      return new BigDecimal(value);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonStringNumberImpl(value);
    }
  }
  
  private abstract static class DefaultJsonNumber extends OracleJsonNumberImpl implements JsonNumber, Wrapper {
    
    @Override
    public ValueType getValueType() {
      return ValueType.NUMBER;
    }

    abstract OracleJsonValue getUnwrapped();
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> wrapped) throws SQLException {
      OracleJsonValue unwrapped = getUnwrapped();
      if (wrapped.isInstance(unwrapped)) {
        return (T)unwrapped;
      }
      throw new SQLException(OracleJsonExceptions.BAD_WRAP.create(
          OracleJsonExceptions.ORACLE_FACTORY, wrapped.getName()).getMessage());
    }
    
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return iface.isInstance(getUnwrapped());
    }
    
    /** Not used currently.  Abstract method only called from the serializer which doesn't work directly on jakarta.json */
    @Override
    public String getString() {
      try {
        return ((OracleJsonNumberImpl)this.unwrap(OracleJsonNumber.class)).getString();
      } catch (SQLException e) {
        throw new IllegalStateException();
      }
    }
    
    @Override
    public final boolean equals(Object other) {
      if (!(other instanceof JsonNumber)) {
        return false;
      }
      return this.bigDecimalValue().equals(((JsonNumber)other).bigDecimalValue());
    }
    
    @Override
    public final int hashCode() {
      return bigDecimalValue().hashCode();
    }
  }
  
  public static class JakartaVectorImpl extends JakartaArrayImpl implements JsonArray, Wrapper {

    byte[] raw;
    
    public JakartaVectorImpl(byte[] raw) {
      super(new OracleJsonArrayImpl());
      this.raw = raw;
      try {
        double[] arr = VectorData.decode(raw, double[].class, false);
        for (double d : arr) {
         wrapped.add(d);
        }
      } catch (SQLException e) {
        throw new OracleJsonException(e);
      }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
      OracleJsonVectorImpl result = new OracleJsonVectorImpl(raw);
      if (iface.isInstance(result)) {
        return (T)result;
      }
      throw new SQLException(OracleJsonExceptions.BAD_WRAP.create(
          OracleJsonExceptions.ORACLE_FACTORY, iface.getName()).getMessage());
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return iface.isInstance(new OracleJsonVectorImpl(raw));
    }
  }
  
  private interface DefaultJsonString extends JsonString, Wrapper {
    
    @Override
    default ValueType getValueType() {
      return ValueType.STRING;
    }
    
    OracleJsonValue getUnwrapped();
    
    @SuppressWarnings("unchecked")
    @Override
    default <T> T unwrap(Class<T> wrapped) throws SQLException {
      OracleJsonValue unwrapped = getUnwrapped();
      if (wrapped.isInstance(unwrapped)) {
        return (T)unwrapped;
      }
      throw new SQLException(OracleJsonExceptions.BAD_WRAP.create(
          OracleJsonExceptions.ORACLE_FACTORY, wrapped.getName()).getMessage());
    }
    
    @Override
    default boolean isWrapperFor(Class<?> iface) throws SQLException {
      return iface.isInstance(getUnwrapped());
    }
    
    default public CharSequence getChars() {
      return getString();
    }
  }
  
  private static boolean stringsEqual(JsonString ths, Object other) {
    if (!(other instanceof JsonString)) {
      return false;
    }
    return ths == other || ths.getString().equals(((JsonString)other).getString());
  }
    
  private JakartaPrimitive() {
    
  }
  
}

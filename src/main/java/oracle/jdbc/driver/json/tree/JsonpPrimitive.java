// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Wrapper;

import javax.json.JsonNumber;
import javax.json.JsonString;

import oracle.jdbc.driver.json.JsonpExceptionFactory;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonValue;

/**
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public final class JsonpPrimitive {
  
  public static class JsonpNumberImpl extends DefaultJsonNumber {
    
    byte[] raw;
    
    OracleJsonDecimal.TargetType type;
    
    public JsonpNumberImpl(byte[] raw, OracleJsonDecimal.TargetType type) {
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
  
  public static class JsonpFloatImpl extends DefaultJsonNumber {
    
    private float value;
    
    public JsonpFloatImpl(float value) {
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
  
  public static class JsonpDoubleImpl extends DefaultJsonNumber {
    
    double value;
    
    public JsonpDoubleImpl(double value) {
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
  
  public static class JsonpStringImpl implements DefaultJsonString {

    String value;
    
    public JsonpStringImpl(String value) {
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
  
  public static class JsonpBinaryImpl implements DefaultJsonString {

    byte[] bytes;
    
    boolean isId;
    
    public JsonpBinaryImpl(byte[] bytes, boolean isId) {
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
  
  public static class JsonpDateImpl implements DefaultJsonString {
    
    byte[] raw;
    
    public JsonpDateImpl(byte[] raw) {
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
      return OsonPrimitiveConversions.dateToString(JsonpExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonDateImpl(raw);
    }
  }
  
  public static class JsonpTimestampImpl implements DefaultJsonString {

    private byte[] raw;
    
    public JsonpTimestampImpl(byte[] raw) {
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
      return OsonPrimitiveConversions.timestampToString(JsonpExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonTimestampImpl(raw);
    }
  }  
  
  public static class JsonpTimestampTZImpl implements DefaultJsonString {

    private byte[] raw;
    
    public JsonpTimestampTZImpl(byte[] raw) {
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
      return OsonPrimitiveConversions.timestampTZToString(JsonpExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonTimestampTZImpl(raw);
    }
  }
  
  public static class JsonpIntervalDSImpl implements DefaultJsonString {
    
    private byte[] raw;
    
    public JsonpIntervalDSImpl(byte[] raw) {
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
      return OsonPrimitiveConversions.serializeIntervalDS(JsonpExceptionFactory.INSTANCE, raw);
    }

    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonIntervalDSImpl(raw);
    }
    
  }  
  
  public static class JsonpIntervalYMImpl implements DefaultJsonString {

    byte[] raw;
    
    public JsonpIntervalYMImpl(byte[] raw) {
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
      return OsonPrimitiveConversions.serializeIntervalYM(JsonpExceptionFactory.INSTANCE, raw);
    }
    
    @Override
    public OracleJsonValue getUnwrapped() {
      return new OracleJsonIntervalYMImpl(raw);
    }
  }  
  
  public static class JsonpStringNumberImpl extends DefaultJsonNumber {

    private String value;
    
    public JsonpStringNumberImpl(String value) {
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
    
    /** Not used currently.  Abstract method only called from the serializer which doesn't work directly on javax.json */
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
    
  private JsonpPrimitive() {
    
  }
  
}

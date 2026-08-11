/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.tree.OracleJsonDateImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampTZImpl;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonDouble;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonVector;

public class RawCompareGenerator extends AbstractGenerator {
  
  private static final byte ORARAW_FALSE  = 0x00;
  private static final byte ORARAW_TRUE   = 0x01;
  private static final byte ORARAW_NULL   = 0x02;
  private static final byte ORARAW_NUMBER = 0x03;
  private static final byte ORARAW_STRING = 0x04;
  private static final byte ORARAW_BINARY = 0x07;
  private static final byte ORARAW_ID     = 0x08;
  private static final byte ORARAW_BOOLEAN= 0x09;
  private static final byte ORARAW_DATE   = 0x0A;
  private static final byte ORARAW_INTDS  = 0x15;
  private static final byte ORARAW_INTYM  = 0x14;
  
  private static final byte[] ORARAW_NAN     = { (byte)0xFF, 0x66};
  private static final byte[] ORARAW_NEG_INF = { 0x00 };
  private static final byte[] ORARAW_POS_INF = { (byte)0xFF, 0x65};
  
  private OutputStream out;
  
  public RawCompareGenerator(OutputStream out) {
    this.out = out;
  }
  
  private OracleJsonGenerator writeOraNum(byte[] raw) {
    try {
      out.write(ORARAW_NUMBER);
      out.write(raw);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }
  
  private OracleJsonGenerator writeOraDate(byte[] raw) {
    try {
      out.write(ORARAW_DATE);
      out.write(raw);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }
  
  public ExceptionFactory getExceptionFactory() {
    return OracleJsonExceptions.ORACLE_FACTORY;
  }

  @Override
  public OracleJsonGenerator writeStartObject() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OracleJsonGenerator writeStartArray() {
    throw new UnsupportedOperationException();
  }

  @Override
  public OracleJsonGenerator write(String value) {
    try {
      out.write(ORARAW_STRING);
      out.write(value.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator write(BigDecimal value) {
    byte[] raw = OsonPrimitiveConversions.toNumber(value);
    writeOraNum(raw);
    return this;
  }

  @Override
  public OracleJsonGenerator write(BigInteger value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public OracleJsonGenerator write(int value) {
    byte[] raw = OsonPrimitiveConversions.toNumber(value);
    writeOraNum(raw);
    return this;
  }

  @Override
  public OracleJsonGenerator write(long value) {
    byte[] raw = OsonPrimitiveConversions.toNumber(value);
    writeOraNum(raw);
    return this;
  }

  @Override
  public OracleJsonGenerator write(double value) {
    try {
      out.write(ORARAW_NUMBER);
      if (Double.isNaN(value))
        out.write(ORARAW_NAN);
      else if (value == Double.NEGATIVE_INFINITY)
        out.write(ORARAW_NEG_INF);
      else if (value == Double.POSITIVE_INFINITY)
        out.write(ORARAW_POS_INF);
      else {
        BigDecimal valBd = BigDecimal.valueOf(value);
        out.write(OsonPrimitiveConversions.toNumber(valBd));
      }
        
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator write(float value) {
    try {
      out.write(ORARAW_NUMBER);
      if (Float.isNaN(value))
        out.write(ORARAW_NAN);
      else if (value == Float.NEGATIVE_INFINITY)
        out.write(ORARAW_NEG_INF);
      else if (value == Float.POSITIVE_INFINITY)
        out.write(ORARAW_POS_INF);
      else
        out.write(OsonPrimitiveConversions.toNumber(new BigDecimal(Float.toString(value))));
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator write(boolean value) {
    try {
      out.write(ORARAW_BOOLEAN);
      out.write(value ? ORARAW_TRUE : ORARAW_FALSE);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator write(LocalDateTime value) {
    byte[] raw = OsonPrimitiveConversions.toOracleTimestamp(getExceptionFactory(), value);
    writeOraDate(raw);
    return this;
  }

  @Override
  public OracleJsonGenerator write(OffsetDateTime value) {
    LocalDateTime dt = value.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
    byte[] raw = OsonPrimitiveConversions.toOracleTimestamp(getExceptionFactory(), dt);
    writeOraDate(raw);
    return this;
  }

  @Override
  public OracleJsonGenerator write(String name, LocalDateTime value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public OracleJsonGenerator write(String name, OffsetDateTime value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public OracleJsonGenerator write(Period value) {
    byte[] raw = OsonPrimitiveConversions.periodToIntervalYM(getExceptionFactory() ,value);
    try {
      out.write(ORARAW_INTYM);
      out.write(raw);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator write(Duration value) {
    byte[] raw = OsonPrimitiveConversions.durationToIntervalDS(value);
    try {
      out.write(ORARAW_INTDS);
      out.write(raw);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator write(byte[] value) {
    try {
      out.write(ORARAW_BINARY);
      out.write(value);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator writeId(byte[] value) {
    try {
      out.write(ORARAW_ID);
      out.write(value);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
    return this;
  }

  @Override
  public OracleJsonGenerator write(String name, byte[] value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public OracleJsonGenerator writeNull() {
     try {
      out.write(ORARAW_NULL);
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
     return this;
  }

  @Override
  public OracleJsonGenerator writeEnd() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void close() {
    try {
      out.close();
    } catch (IOException e) {
      throw new OracleJsonException(e);
    }
  }

  @Override
  public void flush() {
    // do nothing
  }

  @Override
  public OracleJsonGenerator writeKey(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected OracleJsonGenerator writeBinary(OracleJsonBinary value) {
    if (value.isId()) {
      writeId(value.getBytes());
    } else {
      write(value.getBytes());
    }
    return this;
  }

  @Override
  protected OracleJsonGenerator writeDouble(OracleJsonDouble value) {
    write(value.doubleValue());
    return this;
  }

  @Override
  protected OracleJsonGenerator writeFloat(OracleJsonFloat value) {
    write(value.floatValue());
    return this;
  }

  @Override
  protected OracleJsonGenerator writeOraNumber(OracleJsonDecimal value) {
    OracleJsonDecimalImpl impl = (OracleJsonDecimalImpl)value;
    if (impl.isDec()) {
      write(impl.bigDecimalValue());
    } else if (impl.isSB4()) {
      write(impl.intValue());
    } else if (impl.isSB8()) {
      write(impl.longValue());
    } else {
      writeOraNum(impl.raw());
    }    
    return this;
  }

  @Override
  protected OracleJsonGenerator writeTimestamp(OracleJsonTimestamp value) {
    LocalDateTime dt = ((OracleJsonTimestampImpl)value).getLocalDateTime();
    write(dt);
    return this;
  }

  @Override
  protected OracleJsonGenerator writeTimestampTZ(OracleJsonTimestampTZ value) {
    
    OffsetDateTime odt = ((OracleJsonTimestampTZImpl)value).getOffsetDateTime();
    write(odt);
    return this;
  }

  @Override
  protected OracleJsonGenerator writeDate(OracleJsonDate value) {
    LocalDateTime dt = ((OracleJsonDateImpl)value).getLocalDateTime();
    write(dt);
    return this;
  }

  @Override
  protected OracleJsonGenerator writeString(OracleJsonString value) {
    write(value.getString());
    return this;
  }

  @Override
  protected OracleJsonGenerator writeIntervalDS(OracleJsonIntervalDS value) {
    write(value.getDuration());
    return this;
  }

  @Override
  protected OracleJsonGenerator writeIntervalYM(OracleJsonIntervalYM value) {
    write(value.getPeriod());
    return null;
  }

  @Override
  protected OracleJsonGenerator writeVector(OracleJsonVector value) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void writeStringFromParser(OracleJsonParser parser) {
    write(parser.getString());
  }

  @Override
  protected void writeDecimalFromParser(OracleJsonParser parser) {
    write(parser.getValue());
  }

}

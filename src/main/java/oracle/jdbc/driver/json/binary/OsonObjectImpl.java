/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonValue;

/**
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonObjectImpl extends OsonAbstractObject implements OracleJsonObject {
  
  public OsonObjectImpl(OsonContext ctx, int pos) {
    super(ctx);
    init(pos);
  }
  
  @Override
  public <T> T wrap(Class<T> c) {
    OsonContext newCtx;
    if (Jsonp.isJakartaJson(c))
      newCtx = new JakartaOsonContext(ctx);
    else
      newCtx = new JsonpOsonContext(ctx);
    return c.cast(newCtx.valueFactory.createObject(newCtx, pos));
  }

  @Override
  public OracleJsonValue get(Object key) {
    return (OracleJsonValue)getInternal(key);
  }
  
  /* Internal - only used by Saturn.  See SAT-3241 */
  public OracleJsonValue get(int position) {
    return (OracleJsonValue)getInternal(position);
  }

  @Override
  public Collection<OracleJsonValue> values() {
    return new OsonObjectValues<OracleJsonValue>();
  }

  @Override
  public Set<Entry<String, OracleJsonValue>> entrySet() {
    return new OsonEntrySet<OracleJsonValue>();
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
  public byte[] getBytes(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    return ((OracleJsonBinary)getValueInternal(childOffset)).getBytes();
  }

  @Override
  public byte[] getBytes(String key, byte[] d) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return d;
    }
    OracleJsonValue v = (OracleJsonValue)getValueInternal(childOffset);
    return v.getOracleJsonType() == OracleJsonType.BINARY ? v.asJsonBinary().getBytes() : d;
  }

  @Override
  public double getDouble(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    return ((OracleJsonNumber)getValueInternal(childOffset)).doubleValue();
  }

  @Override
  public double getDouble(String key, double d) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return d;
    }
    OracleJsonNumber n = getNumeric(childOffset);
    return n == null ? d  : n.doubleValue();
  }

  private OracleJsonNumber getNumeric(int childOffset) {
    Object v = getValueInternal(childOffset);
    return v instanceof OracleJsonNumber ? 
        ((OracleJsonNumber)v) : null;
  }
  
  @Override
  public long getLong(String key, long d) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return d;
    }
    OracleJsonNumber n = getNumeric(childOffset);
    return n == null ? d  : n.longValue();
  }

  @Override
  public BigDecimal getBigDecimal(String key, BigDecimal d) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return d;
    }
    OracleJsonNumber n = getNumeric(childOffset);
    return n == null ? d  : n.bigDecimalValue();
  }

  @Override
  public long getLong(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    return ((OracleJsonNumber)getValueInternal(childOffset)).longValue();
  }

  @Override
  public BigDecimal getBigDecimal(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    return ((OracleJsonNumber)getValueInternal(childOffset)).bigDecimalValue();
  }
  
  @Override
  public LocalDateTime getLocalDateTime(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    OracleJsonValue v = (OracleJsonValue)getValueInternal(childOffset);
    if (v.getOracleJsonType() == OracleJsonType.DATE) {
      return v.asJsonDate().getLocalDateTime();
    }
    return  v.asJsonTimestamp().getLocalDateTime();
  }

  @Override
  public OffsetDateTime getOffsetDateTime(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    OracleJsonTimestampTZ v = (OracleJsonTimestampTZ)getValueInternal(childOffset);
    return v.getOffsetDateTime();
  }
  
  @Override
  public LocalDateTime getLocalDateTime(String key, LocalDateTime defaultValue) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return defaultValue;
    }
    OracleJsonValue v = (OracleJsonValue)getValueInternal(childOffset);
    if (v.getOracleJsonType() == OracleJsonType.DATE) {
      return v.asJsonDate().getLocalDateTime();      
    } else if (v.getOracleJsonType() == OracleJsonType.TIMESTAMP) {
      return v.asJsonTimestamp().getLocalDateTime();
    } else {
      return defaultValue;
    }
  }

  @Override
  public OffsetDateTime getOffsetDateTime(String key, OffsetDateTime defaultValue) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return defaultValue;
    }
    OracleJsonValue v = (OracleJsonValue)getValueInternal(childOffset);
    if (v.getOracleJsonType() == OracleJsonType.TIMESTAMPTZ) {
      return v.asJsonTimestampTZ().getOffsetDateTime();
    }
    return defaultValue;
  }
  
  @Override
  public OracleJsonValue put(String name, String value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue put(String name, int value) {
    throw createNotModifiable();
  }
  
  @Override
  public OracleJsonValue put(String name, long value) {
    throw createNotModifiable();
  }
  
  @Override
  public OracleJsonValue put(String name, BigDecimal value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue put(String name, double value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue put(String name, boolean value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue putNull(String name) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue put(String name, byte[] values) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue put(String key, OracleJsonValue value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue remove(Object key) {
    throw createNotModifiable();
  }

  @Override
  public void putAll(Map<? extends String, ? extends OracleJsonValue> m) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue put(String key, LocalDateTime value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue put(String key, OffsetDateTime value) {
    throw createNotModifiable();
  }  
  
  @Override
  public void clear() {
    throw createNotModifiable();
  }
  
  private UnsupportedOperationException createNotModifiable() {
    throw OracleJsonExceptions.OBJ_NOT_MUTABLE.create(OracleJsonExceptions.ORACLE_FACTORY);
  }

}

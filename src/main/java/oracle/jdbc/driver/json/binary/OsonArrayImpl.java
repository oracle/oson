// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.tree.OracleJsonNumberImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonValue;


/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonArrayImpl extends OsonAbstractArray implements OracleJsonArray {

  public OsonArrayImpl(OsonContext ctx, int pos) {
    super(ctx, pos);
  }  

  @Override
  public Iterator<OracleJsonValue> iterator() {
    return new ValueIter<OracleJsonValue>();
  }

  @Override
  public OracleJsonValue get(int index) {
    return (OracleJsonValue) getInternal(index);
  }

  @Override
  public ListIterator<OracleJsonValue> listIterator() {
    return listIterator(0);
  }

  @Override
  public List<OracleJsonValue> subList(int fromIndex, int toIndex) {
    return super.sublist(fromIndex,  toIndex);
  }

  @Override
  public ListIterator<OracleJsonValue> listIterator(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    return new ListIter<OracleJsonValue>(index);
  }

  @Override
  public <T> T wrap(Class<T> c) {
    OsonContext newCtx;
    if (Jsonp.isJakartaJson(c))
      newCtx = new JakartaOsonContext(ctx);
    else
      newCtx = new JsonpOsonContext(ctx);
    return c.cast(newCtx.valueFactory.createArray(newCtx, pos));
  }
  
  @Override
  public String toString() {
    StringWriter writer = new StringWriter();
    JsonSerializerImpl ser = new JsonSerializerImpl(writer);
    ser.write(this);
    ser.close();
    return writer.toString();
  }

  @SuppressWarnings("unchecked")
  public <T extends OracleJsonValue> List<T> getValuesAs(Class<T> c) {
    return (List<T>)this;
  }

  @Override
  public double getDouble(int index) {
    int childOffset = getOffsetWithError(index);
    return ((OracleJsonNumberImpl)getValueInternal(childOffset)).doubleValue();
  }
  

  @Override
  public BigDecimal getBigDecimal(int index) {
    int childOffset = getOffsetWithError(index);
    return ((OracleJsonNumberImpl)getValueInternal(childOffset)).bigDecimalValue();
  }

  @Override
  public long getLong(int index) {
    int childOffset = getOffsetWithError(index);
    return ((OracleJsonNumberImpl)getValueInternal(childOffset)).longValue();
  }

  @Override
  public LocalDateTime getLocalDateTime(int index) {
    OracleJsonValue o = (OracleJsonValue)getValueInternal(getOffsetWithError(index));
    if (o.getOracleJsonType() == OracleJsonType.DATE) {
      return ((OracleJsonDate)o).getLocalDateTime();        
    }
    return ((OracleJsonTimestamp)o).getLocalDateTime();
  }

  @Override
  public OffsetDateTime getOffsetDateTime(int index) {
    OracleJsonValue o = (OracleJsonValue)getValueInternal(getOffsetWithError(index));
    return ((OracleJsonTimestampTZ)o).getOffsetDateTime();
  }

  @Override
  public byte[] getBytes(int index) {
    int childOffset = getOffsetWithError(index);
    OracleJsonBinary bin  = (OracleJsonBinary)getValueInternal(childOffset);
    return bin.getBytes();
  }
  
  @Override
  public boolean add(OracleJsonValue e) {
    throw createNotModifiable();
  }

  @Override
  public boolean addAll(Collection<? extends OracleJsonValue> c) {
    throw createNotModifiable();
  }

  @Override
  public boolean addAll(int index, Collection<? extends OracleJsonValue> c) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, OracleJsonValue element) {
    throw createNotModifiable();
  }
  
  @Override
  public void add(int index, OracleJsonValue element) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue remove(int index) {
    throw createNotModifiable();
  }
  
  @Override
  public OracleJsonValue set(int index, String value) {
    throw createNotModifiable();
  }

  @Override
  public void add(String value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, int value) {
    throw createNotModifiable();
  }

  @Override
  public void add(int value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, double value) {
    throw createNotModifiable();
  }

  @Override
  public void add(double value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, boolean value) {
    throw createNotModifiable();
  }

  @Override
  public void add(boolean value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue setNull(int index) {
    throw createNotModifiable();
  }

  @Override
  public void addNull() {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, LocalDateTime value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, OffsetDateTime value) {
    throw createNotModifiable();
  }

  @Override
  public void add(LocalDateTime value) {
    throw createNotModifiable();
  }

  @Override
  public void add(OffsetDateTime value) {
    throw createNotModifiable();
  }
  
  @Override
  public OracleJsonValue set(int index, byte[] value) {
    throw createNotModifiable();
  }

  @Override
  public void add(byte[] value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, long value) {
    throw createNotModifiable();
  }

  @Override
  public OracleJsonValue set(int index, BigDecimal value) throws OracleJsonException {
    throw createNotModifiable();
  }

  @Override
  public void add(long value) {
    throw createNotModifiable();
  }

  @Override
  public void add(BigDecimal value) {
    throw createNotModifiable();
  }
  
  private UnsupportedOperationException createNotModifiable() {
    throw OracleJsonExceptions.ARR_NOT_MUTABLE.create(OracleJsonExceptions.ORACLE_FACTORY);
  }

}

/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.tree;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.binary.JsonSerializerImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonDecimal.TargetType;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonValue;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonArrayImpl implements OracleJsonArray {

  List<OracleJsonValue> list;
  
  public OracleJsonArrayImpl() {
    list = new ArrayList<OracleJsonValue>();
  }
  
  public OracleJsonArrayImpl(OracleJsonArray other) {
    list = new ArrayList<OracleJsonValue>();
    for (OracleJsonValue value : other) { 
      switch (value.getOracleJsonType()) {
      case OBJECT:
        list.add(new OracleJsonObjectImpl(value.asJsonObject()));
        break;
      case ARRAY:
        list.add(new OracleJsonArrayImpl(value.asJsonArray()));
        break;
      default:
        // values are always immutable
        list.add(value);
        break;
      }
    }
  }

  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.ARRAY;
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator<OracleJsonValue> iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return list.containsAll(c);
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public OracleJsonValue get(int index) {
    return list.get(index);
  }

  @Override
  public int indexOf(Object o) {
    return list.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator<OracleJsonValue> listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator<OracleJsonValue> listIterator(int index) {
    return list.listIterator(index);
  }

  @SuppressWarnings("unchecked")
  public <T extends OracleJsonValue> List<T> getValuesAs(Class<T> c) {
    return (List<T>)this;
  }

  @Override
  public String getString(int index) {
    return list.get(index).asJsonString().getString();
  }

  @Override
  public int getInt(int index) {
    return ((OracleJsonNumber)list.get(index)).intValue();
  }

  @Override
  public double getDouble(int index) {
    return ((OracleJsonNumber)list.get(index)).doubleValue();
  }

  @Override
  public BigDecimal getBigDecimal(int index) {
    return ((OracleJsonNumber)list.get(index)).bigDecimalValue();
  }

  @Override
  public long getLong(int index) {
    return ((OracleJsonNumber)list.get(index)).longValue();
  }

  @Override
  public boolean getBoolean(int index) {
    OracleJsonValue v = list.get(index);
    if (v.equals(OracleJsonValue.TRUE)) {
      return true;
    } else if (v.equals(OracleJsonValue.FALSE)) {
      return false;
    }
    throw new ClassCastException();
  }

  @Override
  public boolean isNull(int index) {
    return OracleJsonValue.NULL.equals(list.get(index));
  }

  @Override
  public LocalDateTime getLocalDateTime(int index) {
    OracleJsonValue value = list.get(index);
    if (value.getOracleJsonType() == OracleJsonType.DATE) {
      return value.asJsonDate().getLocalDateTime();
    }
    return value.asJsonTimestamp().getLocalDateTime();
  }

  @Override
  public OffsetDateTime getOffsetDateTime(int index) {
    OracleJsonValue value = list.get(index);
    return value.asJsonTimestampTZ().getOffsetDateTime();
  }

  @Override
  public byte[] getBytes(int index) {
    return list.get(index).asJsonBinary().getBytes();
  }
  
  @Override
  public OracleJsonValue set(int index, OracleJsonValue element) {
    checkNull(element);
    return list.set(index,  element);
  }

  @Override
  public OracleJsonArray subList(int fromIndex, int toIndex) {
    OracleJsonArray arr = new OracleJsonArrayImpl();
    for (int i = fromIndex; i < toIndex; i++) {
      arr.add(get(i));
    }
    return arr;
  }
  
  @Override
  public void add(double value) {
    add(new OracleJsonDoubleImpl(value));
  }
  

  @Override
  public void add(long value) {
    add(new OracleJsonDecimalImpl(value, TargetType.LONG));
  }

  @Override
  public void add(BigDecimal value) {
    add(new OracleJsonDecimalImpl(value));
  }
  
  @Override
  public void add(boolean value) {
    add(value ? OracleJsonValue.TRUE : OracleJsonValue.FALSE);
  }

  @Override
  public void add(String value) {
    add(new OracleJsonStringImpl(value));
  }

  @Override
  public void add(int value) {
    add(new OracleJsonDecimalImpl(value, TargetType.INT));
  }

  @Override
  public boolean addAll(Collection<? extends OracleJsonValue> c) {
    for (OracleJsonValue v : c) {
      checkNull(v);
      list.add(v);
    }
    return c.size() > 0;
  }

  @Override
  public boolean addAll(int index, Collection<? extends OracleJsonValue> c) {
    for (OracleJsonValue v : c) {
      checkNull(v);
    }
    return list.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return list.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return list.retainAll(c);
  }
  
  @Override
  public OracleJsonValue set(int index, boolean value) {
    return list.set(index, value ? OracleJsonValue.TRUE : OracleJsonValue.FALSE);
  }

  @Override
  public void add(int index, OracleJsonValue element) {
    checkNull(element);
    list.add(index, element);
  }

  private void checkNull(OracleJsonValue element) {
    if (element == null) {
      throw new NullPointerException();
    }
  }

  @Override
  public OracleJsonValue remove(int index) {
    return list.remove(index);
  }

  @Override
  public OracleJsonValue set(int index, String value) {
    return list.set(index,  new OracleJsonStringImpl(value));
  }

  @Override
  public OracleJsonValue set(int index, int value) {
    return list.set(index, new OracleJsonDecimalImpl(value, TargetType.INT));
  }
  
  @Override
  public OracleJsonValue set(int index, double value) {
    return list.set(index, new OracleJsonDoubleImpl(value));
  }

  @Override
  public OracleJsonValue set(int index, long value) {
    return list.set(index,  new OracleJsonDecimalImpl(value, TargetType.LONG));
  }

  @Override
  public OracleJsonValue set(int index, BigDecimal value) throws OracleJsonException {
    return list.set(index,  new OracleJsonDecimalImpl(value));
  }
  
  @Override
  public OracleJsonValue setNull(int index) {
    return list.set(index, OracleJsonValue.NULL);
  }

  @Override
  public void addNull() {
    list.add(OracleJsonValue.NULL);
  }

  @Override
  public OracleJsonValue set(int index, LocalDateTime value) {
    return list.set(index, new OracleJsonTimestampImpl(value));
  }

  @Override
  public OracleJsonValue set(int index, OffsetDateTime value) {
    return list.set(index, new OracleJsonTimestampTZImpl(value));
  }

  @Override
  public void add(LocalDateTime value) {
    list.add(new OracleJsonTimestampImpl(value));
  }

  @Override
  public void add(OffsetDateTime value) {
    list.add(new OracleJsonTimestampTZImpl(value));
    
  }  
  
  @Override
  public boolean remove(Object o) {
    return list.remove(o);
  }  
  
  @Override
  public OracleJsonValue set(int index, byte[] value) {
    return list.set(index, new OracleJsonBinaryImpl(value, false));
  }

  @Override
  public void add(byte[] value) {
    list.add(new OracleJsonBinaryImpl(value, false));
  }
  

  @Override
  public boolean add(OracleJsonValue e) {
    checkNull(e);
    return list.add(e);
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
  public boolean equals(Object other) {
    return list.equals(other);
  }
  
  @Override
  public int hashCode() {
    return list.hashCode();
  }
  
  @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c))
      return c.cast(new JakartaArrayImpl(this));
    else
      return c.cast(new JsonpArrayImpl(this));
  }



}

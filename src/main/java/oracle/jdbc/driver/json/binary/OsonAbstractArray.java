/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

import static oracle.jdbc.driver.json.binary.OsonConstants.OPCODE_CHILD_SIZE_BITS;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.sql.json.OracleJsonValue.OracleJsonType;
import oracle.jdbc.driver.json.tree.OracleJsonNumberImpl;


/**
 *  @author  jspiegel
 *  @since   release specific (what release of product did this appear in)
 */
public abstract class OsonAbstractArray extends OsonStructureImpl {

  protected class ValueIter<V> extends PositionIter<V> {
    @SuppressWarnings("unchecked")
    @Override
    public V getValue(int pos) {
      return (V) getValueInternal(getChildOffset(pos++));
    }
  }
  
  public class ListIter<T> extends ValueIter<T> implements ListIterator<T> {

    public ListIter(int i) {
      ipos = i;
    }

    @Override
    public boolean hasPrevious() {
      return ipos != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T previous() {
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      }
      return (T)OsonAbstractArray.this.getInternal(--ipos);
    }

    @Override
    public int nextIndex() {
      return ipos;
    }

    @Override
    public int previousIndex() {
      return ipos-1;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void set(T e) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void add(T e) {
      throw new UnsupportedOperationException();
    }
    
  }  
  
  public OsonAbstractArray(OsonContext ctx, int pos) {
    super(ctx);
    init(pos);
  }
  
  protected OsonAbstractArray(OsonContext ctx) {
    super(ctx);
  }
  
  public String getString(int i) {
    int childOffset = getOffsetWithError(i);
    String result = getStringInternal(childOffset);
    if (result == null) {
      throw new ClassCastException();
    }
    return result;
  }

  public boolean getBoolean(int i) {
    int childOffset = getOffsetWithError(i);
    Boolean result = getBooleanInternal(childOffset);
    if (result == null) {
      throw new ClassCastException();
    }
    return result;
  }
  
  public boolean getBoolean(int i, boolean d) {
    int childOffset = getChildOffset(i);
    if (childOffset == -1) {
      return d;
    }
    Boolean result = getBooleanInternal(childOffset);
    if (result == null) {
      return d;
    }
    return result;
  }
  
  public int getInt(int i) {
    int childOffset = getOffsetWithError(i);
    return ((OracleJsonNumberImpl)getValueInternal(childOffset)).intValue();
  }  

  public int getInt(int i, int d) {
    int childOffset = getChildOffset(i);
    if (childOffset == -1) {
      return d;
    }
    Object o = getValueInternal(childOffset); 
    if (!(o instanceof OracleJsonNumberImpl)) {
      return d;
    }
    OracleJsonNumberImpl n = (OracleJsonNumberImpl)o;
    return n.intValue();
  }  
  
  public boolean isNull(int i) {
    int childOffset = getOffsetWithError(i);
    return isNullInternal(childOffset);
  }  

  /// java.util.List
  
  public boolean contains(Object value) {
    for (int i = 0; i < size; i++) {
      Object c = getValueInternal(getChildOffset(i));
      if (c.equals(value)) {
        return true;
      }
    }
    return false;
  }

  public Object[] toArray() {
    Object[] result = new Object[size];
    copyToArray(result);
    return result;
  }
  
  /** Inspired by versions in java.util.ArrayList and AbstractCollection*/
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] a) {
    T[] r = a.length >= size ? a :
        (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
    copyToArray(r);
    if (r.length >= size+1) {
      r[size] = null;
    }
    return r;
  }
  
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }
  
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }   
  
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }
  
  public boolean containsAll(Collection<?> c) {
    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  public void clear() {
    throw new UnsupportedOperationException();
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof List<?>)) {
      return false;
    }
    List<?> otherList = (List<?>)o;
    if (otherList.size() != size) {
      return false;
    }
    for (int i = 0; i < size; i++) {
      Object value = getValueInternal(getChildOffset(i));
      if (!value.equals(otherList.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  @Override
  public int hashCode() {
    int hashCode = 1;
    for (int i = 0; i < size; i++) {
      Object value = getValueInternal(getChildOffset(i));
      hashCode = 31 * hashCode + value.hashCode();
    }
    return hashCode;
  }
  
  public Object getInternal(int i) {
    return getValueInternal(getOffsetWithError(i));
  }

  public int indexOf(Object o) {
    for (int i = 0; i < size; i++) {
      if(getInternal(i).equals(o)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(Object o) {
    int result = -1;
    for (int i = 0; i < size; i++) {
      if(getInternal(i).equals(o)) {
        result = i;
      }
    }
    return result;
  }

  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.ARRAY;
  }
    
  protected void init(int pos) { 
    super.init(pos);
    int op = ctx.b.getUB1(pos);
    initChildOffseUb(op);
    int childSizeBits = (op & OPCODE_CHILD_SIZE_BITS);
    childArrayOffset = pos + 1;
    if (childSizeBits == 0) {
      childArrayOffset++;
      size = ctx.b.getUB1(pos+1);
    } else if (childSizeBits == 0x8) {
      childArrayOffset += 2; 
      size = ctx.b.getUB2(pos+1);
    } else if (childSizeBits == 0x10) {
      childArrayOffset += 4;
      size = ctx.b.getUB4int(pos+1);
    } else {
      throw OracleJsonExceptions.CORRUPT.create(ctx.getExceptionFactory());
    }
    ctx.b.checkRange(childArrayOffset, (long)size * childOffsetUb, ctx.getExceptionFactory());
  }

  @Override
  protected int getChildOffset(int i) {
    if (i < 0 || i >= size) {
      return -1;
    }

    OsonHeader header = ctx.getHeader();
    if (header.relativeOffsets()) {
      // relative to position, signed value
      if (childOffsetUb == 2) {
        short relative = ctx.b.getShort(childArrayOffset + (i*2));
        long childOffset =
            (relative + (long)pos - header.getTreeSegmentOffset()) & 0xffffL;
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            childOffset, ctx.getExceptionFactory()));
      } else {
        int relative = ctx.b.getInt(childArrayOffset + (i*4));
        long childOffset =
            (relative + (long)pos - header.getTreeSegmentOffset()) &
            0xffff_ffffL;
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            childOffset, ctx.getExceptionFactory()));
      }
    } else {
      // relative to treesegment start, unsigned value
      if (childOffsetUb == 2) {
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            ctx.b.getUB2(childArrayOffset + (i*2)),
            ctx.getExceptionFactory()));
      } else {
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            ctx.b.getUB4int(childArrayOffset + (i*4)),
            ctx.getExceptionFactory()));
      }
    }
  }
  
  private void copyToArray(Object[] result) {
    for (int i = 0; i < size; i++) {
      result[i] = getValueInternal(getChildOffset(i));
    }
  }
  
  protected int getOffsetWithError(int i) {
    int childOffset = getChildOffset(i);
    if (childOffset == -1) {
      throw new IndexOutOfBoundsException();
    }
    return childOffset;
  }

  protected <T> AbstractList<T> sublist(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }
    return new AbstractList<T>() {
      @SuppressWarnings("unchecked")
      @Override
      public T get(int index) {
        return (T) getValueInternal(getOffsetWithError(index+fromIndex));
      }

      @Override
      public int size() {
        return toIndex - fromIndex;
      }
    };
  }

}

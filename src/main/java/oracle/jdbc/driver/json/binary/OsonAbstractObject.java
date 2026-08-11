/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

import static oracle.jdbc.driver.json.binary.OsonConstants.OPCODE_CHILD_NO_SORT_BIT;
import static oracle.jdbc.driver.json.binary.OsonConstants.OPCODE_CHILD_SIZE_BITS;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import oracle.jdbc.driver.json.tree.OracleJsonNumberImpl;
import oracle.sql.json.OracleJsonValue;


/**
 * Typically an object is:
 * 
 *   [op code byte] [number of children] [fid array] [child offset array]
 *   
 * But when FID array sharing is turned on, the fid array may be stored 
 * externally.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonAbstractObject extends OsonStructureImpl {
  
  protected class OsonEntrySet<T> extends AbstractSet<Entry<String, T>> {
    @Override
    public Iterator<Entry<String, T>> iterator() {
      return new PositionIter<Entry<String, T>>() {
        @Override
        public Entry<String, T> getValue(int pos) {
          return new Entry<String, T>() {
            
            @Override
            public String getKey() {
              return getFieldName(pos);
            }

            @SuppressWarnings("unchecked")
            @Override
            public T getValue() {
              return (T) getValueInternal(getChildOffset(pos));
            }

            @Override
            public T setValue(T value) {
              throw new UnsupportedOperationException();
            }
            
            @Override
            public int hashCode() {
              return getKey().hashCode() ^ getValue().hashCode();
            }
            
            public boolean equals(Object other) {
              if (other == this) {
                return true;
              }
              if (!(other instanceof Entry)) {
                return false;
              }
              Entry<?,?> o = (Entry<?,?>)other;
              return getKey().equals(o.getKey()) && getValue().equals(o.getValue());
            }
          };
        }
      };
    }
    
    @Override
    public int size() {
      return size;
    }
  };
  
  protected class OsonObjectValues<T> extends AbstractCollection<T> {
    @Override
    public Iterator<T> iterator() {
      return new PositionIter<T>() {
        @SuppressWarnings("unchecked")
        @Override
        public T getValue(int pos) {
          return (T) getValueInternal(getChildOffset(pos++));
        }
      };
    }

    @Override
    public int size() {
      return size;
    }
  };
  
  /** Is the fid array sorted? */
  boolean fidSorted;

  /** 
   * Location of fid array.  May be pointing outside this object when fid 
   * array sharing turned on. 
   */
  int fidArrayOffset;
  
  public OsonAbstractObject(OsonContext ctx) {
    super(ctx);
  }
  
  public OracleJsonValue.OracleJsonType getOracleJsonType() {
    return OracleJsonValue.OracleJsonType.OBJECT;
  } 
  
  public OsonAbstractObject(OsonContext ctx, int pos) {
    super(ctx);
    init(pos);
  }
  
  public OsonAbstractArray getJsonArrayInternal(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return null;
    }
    return getArrayInternal(childOffset);
  }
  
  public OsonAbstractObject getJsonObjectInternal(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return null;
    }
    return getJsonObjectInternal(childOffset);
  }
  
  public String getString(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    String res = getStringInternal(childOffset);
    if (res == null) {
      throw new ClassCastException();
    }
    return res;
  }
  
  public String getString(String key, String d) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return d;
    }
    String res = getStringInternal(childOffset);
    if (res == null) {
      return d;
    }
    return res;
  }
  
  public boolean getBoolean(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    Boolean res = getBooleanInternal(childOffset);
    if (res == null) {
      throw new ClassCastException();
    }
    return res;
  }
  
  public boolean getBoolean(String key, boolean d) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return d;
    }
    Boolean res = getBooleanInternal(childOffset);
    if (res == null) {
      return d;
    }
    return res;
  }
  
  public int getInt(String key, int d) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      return d;
    }
    Object json = getValueInternal(childOffset);
    if (!(json instanceof OracleJsonNumberImpl)) {
      return d;
    }
    return ((OracleJsonNumberImpl)json).intValue();
  }
  
  public int getInt(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    return ((OracleJsonNumberImpl)getValueInternal(childOffset)).intValue();
  }    
  
  public boolean isNull(String key) {
    int childOffset = getChildOffset(key);
    if (childOffset == -1) {
      throw new NullPointerException();
    }
    
    return isNullInternal(childOffset);
  }
  
  public boolean containsKey(Object key) {
    return key instanceof String && getChildOffset((String)key) != -1;
  }
  
  public boolean containsValue(Object value) {
    for (int i = 0; i < size; i++) {
      Object v = getValueInternal(getChildOffset(i));
      if (v.equals(value)) {
        return true;
      }
    }
    return false;
  }
  
  public Object getInternal(Object key) {
    if (!(key instanceof String)) {
      return null;
    }
    int childOffset = getChildOffset((String)key);
    if (childOffset < 0) {
      return null;
    }
    return getValueInternal(childOffset);
  }
  
  public Object getInternal(int position) {
    int childOffset = getChildOffset(position);
    if (childOffset < 0) {
      return null;
    }
    return getValueInternal(childOffset); 
  }
  
  public Set<String> keySet() {
    return new AbstractSet<String>() {
      @Override
      public Iterator<String> iterator() {
        return new PositionIter<String>() {
          @Override
          public String getValue(int pos) {
            return getFieldName(pos);
          }
        };
      }

      @Override
      public int size() {
        return size;
      }
    };
  }
    
  //////
  
  protected void init(int pos) {
    super.init(pos);
    int op = ctx.b.getUB1(pos);
    initChildOffseUb(op);
    fidArrayOffset = pos + 1; // after the op code
    int childSizeBits = (op & OPCODE_CHILD_SIZE_BITS);
    OsonHeader header = ctx.getHeader();
    if (childSizeBits == 0) {
      size = ctx.b.getUB1(pos+1);
      fidArrayOffset++;
      childArrayOffset = checkedTableEnd(fidArrayOffset, size, header.numFieldIdBytes());
    } else if (childSizeBits == 0x8) {
      size = ctx.b.getUB2(pos+1);
      fidArrayOffset += 2;
      childArrayOffset = checkedTableEnd(fidArrayOffset, size, header.numFieldIdBytes());
    } else if (childSizeBits == 0x10) {
      size = ctx.b.getUB4int(pos+1);
      fidArrayOffset += 4;
      childArrayOffset = checkedTableEnd(fidArrayOffset, size, header.numFieldIdBytes());
    } else if (childSizeBits == 0x18) {
      int delegateObjectOffset;
      if (childOffsetUb == 4) {
        delegateObjectOffset = ctx.b.getUB4int(pos+1) + header.getTreeSegmentOffset();
        childArrayOffset = pos + 1 + 4;
      } else {
        delegateObjectOffset = ctx.b.getUB2(pos+1) + header.getTreeSegmentOffset();
        childArrayOffset = pos + 1 + 2;
      }
      int otherOp = ctx.b.getUB1(delegateObjectOffset);
      int otherSizeBits = (otherOp & OPCODE_CHILD_SIZE_BITS);
      fidArrayOffset = delegateObjectOffset + 1;
      if (otherSizeBits == 0) {
        size = ctx.b.getUB1(delegateObjectOffset + 1);
        fidArrayOffset ++;
      } else if (otherSizeBits == 0x8) {
        size = ctx.b.getUB2(delegateObjectOffset + 1);
        fidArrayOffset += 2;
      } else if (otherSizeBits == 0x10) {
        size = ctx.b.getUB4int(delegateObjectOffset + 1);
        fidArrayOffset += 4;
      } else {
        throw new IllegalStateException();
      }
      checkedTableEnd(fidArrayOffset, size, header.numFieldIdBytes());
    }
    ctx.b.checkRange(childArrayOffset, (long)size * childOffsetUb, ctx.getExceptionFactory());
    fidSorted = (op & OPCODE_CHILD_NO_SORT_BIT) == 0 && header.fieldsSorted();
  }

  private int checkedTableEnd(int offset, int count, int width) {
    long byteCount = (long)count * width;
    ctx.b.checkRange(offset, byteCount, ctx.getExceptionFactory());
    return (int)(offset + byteCount);
  }
  
  @Override
  protected int getChildOffset(int fieldPos) {
    if (fieldPos < 0 || fieldPos >= size) {
      return -1;
    }

    OsonHeader header = ctx.getHeader();
    if (header.relativeOffsets()) {
      if (childOffsetUb == 2) {
        short relative = ctx.b.getShort(childArrayOffset + (fieldPos*2));
        long childOffset =
            (relative + (long)pos - header.getTreeSegmentOffset()) & 0xffffL;
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            childOffset, ctx.getExceptionFactory()));
      } else {
        int relative = ctx.b.getInt(childArrayOffset + (fieldPos*4));
        long childOffset =
            (relative + (long)pos - header.getTreeSegmentOffset()) &
            0xffff_ffffL;
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            childOffset, ctx.getExceptionFactory()));
      }
    } else {
      if (childOffsetUb == 2) {
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            ctx.b.getUB2(childArrayOffset + (fieldPos*2)),
            ctx.getExceptionFactory()));
      } else {
        return checkedChildOffset(header.checkedNodeRelativeToTreeStart(
            ctx.b.getUB4int(childArrayOffset + (fieldPos*4)),
            ctx.getExceptionFactory()));
      }
    }
  }

  protected int getChildOffset(String key) {
    int fieldPos = getChildPosition(key);
    if (fieldPos == -1)
      return -1;
    
    return getChildOffset(fieldPos);
  }

  protected int getChildPosition(String key) {
    OsonHeader header = ctx.getHeader();
    int id = header.getFieldId(key);
    if (id == -1) {
      return -1;
    }
    ctx.b.position(pos+1);
    
    int fieldPos;
    if (header.numFieldIdBytes() == 1) {
      if (fidSorted) {
        fieldPos = ctx.b.binarySearchUb1(fidArrayOffset, size, id);
      } else {
        fieldPos = ctx.b.linearSearchUb1(fidArrayOffset, size, id);
      }
    } else if (header.numFieldIdBytes() == 2){
      if(fidSorted) {
        fieldPos = ctx.b.binarySearchUb2(fidArrayOffset, size, id);
      } else {
        fieldPos = ctx.b.linearSearchUb2(fidArrayOffset, size, id);
      }
    } else { // if (ctx.ubFieldId == 4){
      if(fidSorted) {
        fieldPos = ctx.b.binarySearchUb4(fidArrayOffset, size, id);
      } else {
        fieldPos = ctx.b.linearSearchUb4(fidArrayOffset, size, id);
      }
    }
    if (fieldPos < 0) {
      return -1;
    }
    
    return fieldPos;
  }
  
  public String getFieldName(int child) {
    int fid;
    OsonHeader header = ctx.getHeader();
    if (header.numFieldIdBytes() == 1) {
      fid = ctx.b.getUB1(fidArrayOffset+child);
    } else if (header.numFieldIdBytes() == 2) {
      fid = ctx.b.getUB2(fidArrayOffset+(child*2));
    } else {
      fid = ctx.b.getUB4int(fidArrayOffset+(child*4));
    }
    return header.getFieldName(fid-1);
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Map)) {
      return false;
    }
    Map<?,?> m = (Map<?,?>)other;
    if (m.size() != size) {
      return false;
    }
    for (int i = 0; i < size; i++) {
      String key = getFieldName(i);
      Object value = getValueInternal(getChildOffset(i));
      if (!m.containsKey(key) || !value.equals(m.get(key))) {
        return false;
      }
    }
    return true;
  }
  
  @Override
  public int hashCode() {
    int hc = 0;
    for (Entry<String, ?> e : new OsonEntrySet<Object>()) {
      hc += e.hashCode();
    }
    return hc;
  }  

}

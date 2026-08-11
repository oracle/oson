/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_ARRAY_TYP;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JBINUB2L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JBINUB4L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JBOOLF_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JBOOLT_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYDATE_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYDB_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYDS_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYFLT_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYGENID_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYSTAMP7_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYSTAMP_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYSTAMP_TZ_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JDTYYM_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JEXT;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JNULL_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JORA_DTYNUM_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JORA_DTYNUM_DEC_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSNUM_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSUB1L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSUB2L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSUB4L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_OBJECT_TYP;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_STR_OK_5BITS;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_UPD2_FWA;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_UPD4_FWA;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_UPD_OBJ_REF_BITMASK;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_UPD_OVFLW;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_UPD_XSZ_RES;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JVECTOR;
import static oracle.jdbc.driver.json.binary.OsonConstants.OPCODE_OFFSET_SIZE_BIT;
import static oracle.jdbc.driver.json.binary.OsonConstants.isDec_16;
import static oracle.jdbc.driver.json.binary.OsonConstants.isOraNum16;
import static oracle.jdbc.driver.json.binary.OsonConstants.isSB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.isSB8;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.NoSuchElementException;

import oracle.jdbc.driver.json.tree.OracleJsonIntervalDSImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
import oracle.sql.json.OracleJsonDecimal.TargetType;
import oracle.sql.json.OracleJsonValue.OracleJsonType;


/**
 * Superclass of OsonObjectImpl and OsonArrayImpl.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public abstract class OsonStructureImpl {
  
  public abstract class PositionIter <T> implements Iterator<T> {
    protected int ipos = 0;

    @Override
    public boolean hasNext() {
      return ipos < size;
    }

    @Override
    public T next() {
      if (ipos >= size) {
        throw new NoSuchElementException();
      }
      return getValue(ipos++);
    }
    
    public abstract T getValue(int pos);
  }
  
  /** Byte buffer and header information */
  OsonContext ctx;
  
  /** Position of object in tree segment */
  int pos;
    
  public int size;
  
  /** 4 or 2 bytes.  The size of the offsets in the child offset array */
  byte childOffsetUb;
  
  /** Location of the child array */
  int childArrayOffset;
  
  public OsonStructureImpl(OsonContext ctx) {
    this.ctx = ctx;
  }
    
  /** serves java.util.List and java.util.Map */
  public int size() {
    return size;
  }
  
  /** serves java.util.List and java.util.Map */
  public boolean isEmpty() {
    return size() == 0;
  }  

  
  void init(int pos) {
    this.pos = pos;
  }
  
  void initChildOffseUb(int op) {
    if ((op & OPCODE_OFFSET_SIZE_BIT) != 0) {
      childOffsetUb = 4;
    } else {
      childOffsetUb = 2;
    }
  }  
  
  Boolean getBooleanInternal(int offset) {
    int op = ctx.b.getUB1(offset);
    if (op < 0) {
      return null;
    }

    if (op == JZNOCT_JBOOLT_C) {
      return true;
    } else if (op == JZNOCT_JBOOLF_C) {
      return false;
    } else {
      return null;
    }
  }
  
  
  String getStringInternal(int offset) {
    int op = ctx.b.getUB1(offset);
    if (op < 0) {
      return null;
    }
    
    if (op <= 0x1f) { // 000x xxxx
      ctx.b.position(offset+1);
      return ctx.b.readString(op);
    } else if (op == JZNOCT_JSUB1L_C) {
      int len = ctx.b.getUB1(offset+1);
      ctx.b.position(offset+2);
      return ctx.b.readString(len);
    } else if (op == JZNOCT_JSUB2L_C) {
      int len = ctx.b.getUB2(offset+1);
      ctx.b.position(offset+3);
      return ctx.b.readString(len);
    } else if (op == JZNOCT_JSUB4L_C) {
      int len = ctx.b.getUB4int(offset+1);
      ctx.b.position(offset+5);
      return ctx.b.readString(len);
    } else {
      return null;
    }
  }

  boolean isNullInternal(int childOffset) {
    int op = ctx.b.getUB1(childOffset);
    if (op < 0) {
      return false;
    }
    
    return op == JZNOCT_JNULL_C;
  }
  
  public Object getValueInternal(int offset) {
    return getValueInternal(offset, ctx.getFactory(), ctx);
  }
  
  public static Object getValueInternal(int offset, OsonValueFactory factory, OsonContext ctx) {
    offset = ctx.header.checkedNodeOffset(offset, ctx.getExceptionFactory());
    int op = ctx.b.getUB1(offset);
    if ((op & 0xC0) == JZNOCT_ARRAY_TYP) {
      return factory.createArray(ctx, offset);
    } else if ((op & 0xC0) == JZNOCT_OBJECT_TYP) {
      if ((op & JZNOCT_UPD_OBJ_REF_BITMASK) == JZNOCT_UPD_OBJ_REF_BITMASK) {
        return getOverflowValue(offset, factory, ctx);
      }
      return factory.createObject(ctx, offset);
    } else if (op <= JZNOCT_STR_OK_5BITS) { // <1>
      return factory.createString(ctx, checkValueRange(offset, 1, op, ctx), op);
    } else if (isSB4(op)) {
      return factory.createNumber(readRaw(offset+1, op & 0x7, ctx), TargetType.INT);
    } else if (isSB8(op)) {
      return factory.createNumber(readRaw(offset+1, op & 0xF, ctx), TargetType.LONG);
    } else if (isOraNum16(op)) { 
      return factory.createNumber(readRaw(offset+1, (op & 0xF) + 1, ctx), null); 
    } else if (isDec_16(op)) {  
      return factory.createNumber(readRaw(offset+1, (op & 0xF) + 1, ctx), TargetType.DECIMAL);
    }
    
    switch (op) {
    case JZNOCT_JBOOLT_C: 
      return factory.createTrue();
    case JZNOCT_JBOOLF_C:
      return factory.createFalse();
    case JZNOCT_JNULL_C:
      return factory.createNull();
    case JZNOCT_JSUB1L_C: {
      ctx.b.checkRange((long)offset+1, 1, ctx.getExceptionFactory());
      int len = ctx.b.getUB1(offset+1);
      return factory.createString(ctx, checkValueRange(offset, 2, len, ctx), len);
    }
    case JZNOCT_JSUB2L_C: {
      ctx.b.checkRange((long)offset+1, 2, ctx.getExceptionFactory());
      int len = ctx.b.getUB2(offset+1);
      return factory.createString(ctx, checkValueRange(offset, 3, len, ctx), len);
    }
    case JZNOCT_JSUB4L_C: {
      ctx.b.checkRange((long)offset+1, 4, ctx.getExceptionFactory());
      int len = ctx.b.getUB4int(offset+1);
      return factory.createString(ctx, checkValueRange(offset, 5, len, ctx), len);
    }
    case JZNOCT_JDTYDB_C: {
      ctx.b.position(offset+1);
      return factory.createDouble(ctx.b.readDtyDouble());
    }
    case JZNOCT_JDTYFLT_C: {
      ctx.b.position(offset+1);
      return factory.createFloat(ctx.b.readDtyFloat());
    }
    case JZNOCT_JSNUM_C: {
      ctx.b.checkRange((long)offset+1, 1, ctx.getExceptionFactory());
      int len = ctx.b.getUB1(offset+1);
      ctx.b.position(checkValueRange(offset, 2, len, ctx));
      String num = ctx.b.readString(len);
      return factory.createStringNumber(num);
    }
    case JZNOCT_JORA_DTYNUM_C: {
      byte[] raw = readRaw(offset+2, ctx.b.getUB1(offset+1), ctx);
      return factory.createNumber(raw, null);
    }
    case JZNOCT_JORA_DTYNUM_DEC_C: {
      byte[] raw = readRaw(offset+2, ctx.b.getUB1(offset+1), ctx);
      return factory.createNumber(raw, TargetType.DECIMAL);
    }
    case JZNOCT_JDTYGENID_C: {
      ctx.b.checkRange((long)offset+1, 1, ctx.getExceptionFactory());
      int len = ctx.b.getUB1(offset+1);
      if (len > 127 || len < 0) {
        throw new UnsupportedOperationException(String.valueOf(op));
      }
      return factory.createBinary(ctx, checkValueRange(offset, 2, len, ctx), len, true);
    }
    case JZNOCT_JBINUB2L_C: {
      ctx.b.checkRange((long)offset+1, 2, ctx.getExceptionFactory());
      int len = ctx.b.getUB2(offset+1);
      return factory.createBinary(ctx, checkValueRange(offset, 3, len, ctx), len, false);
    }
    case JZNOCT_JBINUB4L_C:  {
      ctx.b.checkRange((long)offset+1, 4, ctx.getExceptionFactory());
      int len = ctx.b.getUB4int(offset+1);
      return factory.createBinary(ctx, checkValueRange(offset, 5, len, ctx), len, false);
    }
    case JZNOCT_JDTYSTAMP_C:
    case JZNOCT_JDTYSTAMP7_C:
      return factory.createTimestamp(readTimestamp(ctx.b, offset));
    case JZNOCT_JDTYSTAMP_TZ_C:
      return factory.createTimestampTZ(readTimestampTZ(ctx.b, offset));
    case JZNOCT_JDTYDATE_C:
      return factory.createDate(readRaw(offset+1, 7, ctx));
    case JZNOCT_JDTYYM_C:
      return factory.createIntervalYM(readRaw(offset+1, OracleJsonIntervalYMImpl.INTERVALYM_LEN, ctx));
    case JZNOCT_JDTYDS_C:
      return factory.createIntervalDS(readRaw(offset+1, OracleJsonIntervalDSImpl.INTERVALDS_LEN, ctx));
    case JZNOCT_UPD_OVFLW:
      return getOverflowValue(offset, factory, ctx);
    case JZNOCT_UPD2_FWA:
      ctx.b.checkRange((long)offset+1, 2, ctx.getExceptionFactory());
      return getForwardedValue(offset, ctx.b.getUB2(offset+1), factory, ctx);
    case JZNOCT_UPD4_FWA:
      ctx.b.checkRange((long)offset+1, 4, ctx.getExceptionFactory());
      return getForwardedValue(offset, ctx.b.getUB4int(offset+1), factory, ctx);
    case JZNOCT_JEXT: {
      int op2 = ctx.b.getUB1(offset+1); 
      if (op2 == (byte)JZNOCT_JVECTOR) {
        ctx.b.checkRange((long)offset+2, 4, ctx.getExceptionFactory());
        int len = ctx.b.getUB4int(offset+2);
        return factory.createVector(ctx, checkValueRange(offset, 6, len, ctx), len);
      } else {
        throw new UnsupportedOperationException(String.valueOf((op << 8) | op2));
      }
    }
    case JZNOCT_UPD_XSZ_RES:
    default:
      throw new UnsupportedOperationException(String.valueOf(op));
    }
  }

  private static Object getForwardedValue(
      int absoluteOffset,
      int relativeOffset,
      OsonValueFactory factory,
      OsonContext ctx) {
    int fwd = ctx.header.checkedExtendedRelativeOffset(
        relativeOffset, ctx.getExceptionFactory());
    if (fwd == absoluteOffset) {
      throw oracle.jdbc.driver.json.OracleJsonExceptions.CORRUPT.create(
          ctx.getExceptionFactory());
    }
    return getValueInternal(fwd, factory, ctx);
  }

  private static Object getOverflowValue(int absoluteOffset, OsonValueFactory factory, OsonContext ctx) {
    int relativeOffset = absoluteOffset - ctx.header.treeSegmentOffset;
    OsonHeader header = ctx.header;
    Integer targetRelativeOffset = header.forwardingAddress == null ?
        null : header.forwardingAddress.get(relativeOffset);
    if (targetRelativeOffset == null) {
      throw oracle.jdbc.driver.json.OracleJsonExceptions.CORRUPT.create(
          ctx.getExceptionFactory());
    }
    int fwd = header.checkedExtendedRelativeOffset(
        targetRelativeOffset, ctx.getExceptionFactory());
    return getValueInternal(fwd, factory, ctx);
  }

  protected int checkedChildOffset(int childOffset) {
    return ctx.header.checkedNodeOffset(
        childOffset, ctx.getExceptionFactory());
  }
  
  private static byte[] readRaw(int offset, int len, OsonContext ctx) {
    ctx.b.checkRange(offset, len, ctx.getExceptionFactory());
    ctx.b.position(offset);
    byte[] res = new byte[len];
    ctx.b.get(res);
    return res;
  }

  private static int checkValueRange(
      int offset,
      int headerBytes,
      int len,
      OsonContext ctx) {
    long valueOffset = (long)offset + headerBytes;
    ctx.b.checkRange(valueOffset, len, ctx.getExceptionFactory());
    return (int)valueOffset;
  }
  
  protected static byte[] readTimestamp(OsonBuffer b, int offset) {
    int op = b.getUB1(offset);
    int len;
    if (op == OsonConstants.JZNOCT_JDTYSTAMP7_C) {
      len = OsonPrimitiveConversions.SIZE_TIMESTAMP_NOFRAC;
    } else if (op == OsonConstants.JZNOCT_JDTYSTAMP_C) {
      len = OsonPrimitiveConversions.SIZE_TIMESTAMP;
    } else {
      throw new ClassCastException();
    }
    byte[] raw = new byte[len];
    b.position(offset+1);
    b.get(raw);
    return raw;
  }
  
  protected static byte[] readTimestampTZ(OsonBuffer b, int offset) {
    byte[] raw = new byte[OsonPrimitiveConversions.SIZE_TIMESTAMPTZ];
    b.position(offset+1);
    b.get(raw);
    return raw;
  }
  
  protected OsonAbstractArray getArrayInternal(int childOffset) {
    int op = ctx.b.getUB1(childOffset);
    if ((op & 0xC0) == JZNOCT_ARRAY_TYP) {
      return ctx.getFactory().createArray(ctx, childOffset);
    } else {
      throw new ClassCastException();
    }
  } 
  
  protected OsonAbstractObject getJsonObjectInternal(int childOffset) {
    int op = ctx.b.getUB1(childOffset);
    if ((op & 0xC0) == JZNOCT_OBJECT_TYP) {
      return ctx.getFactory().createObject(ctx, childOffset);
    } else {
      throw new ClassCastException();
    }
  }
  
  protected abstract int getChildOffset(int child);
  
  public abstract OracleJsonType getOracleJsonType(); 

  public ByteBuffer getBuffer() {
    ByteBuffer b = ctx.b.buffer;
    b.position(0);
    return b;
  }
  
  /**
   * Returns {@code true} if this structure is not the child of a parent structure.
   * This is an internal API used by Coherence.
   */
  public boolean isRoot() {
    return this.pos == ctx.header.getTreeSegmentOffset();
  }

}

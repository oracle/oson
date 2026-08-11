// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Arrays;
import java.util.NoSuchElementException;

import oracle.jdbc.driver.json.JakartaParserWrapper;
import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.JsonpParserWrapper;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.tree.OracleJsonBinaryImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDateImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDoubleImpl;
import oracle.jdbc.driver.json.tree.OracleJsonFloatImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalDSImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
import oracle.jdbc.driver.json.tree.OracleJsonNumberImpl;
import oracle.jdbc.driver.json.tree.OracleJsonStringImpl;
import oracle.jdbc.driver.json.tree.OracleJsonStringNumberImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampTZImpl;
import oracle.jdbc.driver.json.tree.OracleJsonVectorImpl;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonStructure;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonValue.OracleJsonType;

/** 
 * A pull parser for OSON.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonParserImpl extends OracleOsonValueFactory implements OracleJsonParser {

  private static int INITIAL_DEPTH_CAPACITY = 4;
  
  /** 
   * Instances of this don't leave the parser.  getValue() will make a copy. 
   * This is used to delay java.lang.String creation in cases where it isn't needed.
   */ 
  private class StringPointer implements OracleJsonString {
    int pos;
    
    int len;

    @Override
    public OracleJsonType getOracleJsonType() {
      return OracleJsonValue.OracleJsonType.STRING;
    }

    @Override
    public <T> T wrap(Class<T> wrapper) {
      throw new UnsupportedOperationException();
    }

    @Override
    public String getString() {
      ctx.b.position(pos);
      return ctx.b.readString(len);
    }

    @Override
    public CharSequence getChars() {
      return getString();
    }

        public void reset(int pos, int len) {
      this.pos = pos;
      this.len = len;
    }

    public int getLen() {
      return len;
    }

    public int getPos() {
      return pos;
    }
    
  }
  
  private class BinaryPointer implements OracleJsonBinary {
    
    int pos;
    
    int len;
    
    boolean isId;

    @Override
    public OracleJsonType getOracleJsonType() {
      return OracleJsonValue.OracleJsonType.BINARY;
    }

    @Override
    public <T> T wrap(Class<T> wrapper) {
      throw new UnsupportedOperationException();
    }

    @Override
    public String getString() {
      byte[] bytes = getBytes();
      return OracleJsonBinaryImpl.getString(bytes, isId);
    }

    @Override
    public byte[] getBytes() {
      ctx.b.position(pos);
      byte[] res = new byte[len];
      ctx.b.get(res);
      return res;
    }
    
    protected void getBytes(OutputStream out) throws IOException {
      ctx.b.position(pos);
      ctx.b.readBytes(out, len);
    }

        @Override
    public boolean isId() {
      return isId;
    }

    public void reset(int pos, int len, boolean isId) {
      this.pos = pos;
      this.len = len;
      this.isId = isId;
    }

  }
  
  private enum State { START, NEXT_VALUE, AFTER_KEY, FINISHED }
  
  private final OsonContext ctx;
  
  private State state;

  private int depth;
  
  /** Current child being processed at each depth */
  private OracleJsonStructure[] depthStack;
  
  /** Depth deep */
  private int[] currentChild;
  
  /** Depth deep */
  private OsonAbstractObject[] objectCache;
  
  /** Depth deep */
  private OsonAbstractArray[] arrayCache;
  
  /** A number wrapper to cache */
  private OracleJsonDecimalImpl numberCache;

  /** A string wrapper to cache */
  private StringPointer stringPointer = new StringPointer();
  
  /** A binary wrapper to cache */
  private BinaryPointer binaryPointer = new BinaryPointer();
  
  private OracleJsonValue currentPrimitive;
  
  /** Closed on close() if not null */
  private Closeable closeable;
  
  /** last returned event.  added to easily detect if current event is end object/array */
  Event event;
  
  public OsonParserImpl(OsonContext ctx) {
    this.ctx = ctx;
    depthStack = new OracleJsonStructure[INITIAL_DEPTH_CAPACITY];
    currentChild = new int[INITIAL_DEPTH_CAPACITY];
    objectCache = new OsonAbstractObject[INITIAL_DEPTH_CAPACITY];
    arrayCache = new OsonAbstractArray[INITIAL_DEPTH_CAPACITY];
    init();
  }

  private void init() {
    this.state = State.START;
    depth = -1;
    event = null;
  }

  @Override
  public boolean hasNext() {
    return state != State.FINISHED;
  }
  
  @Override
  public Event next() {
    switch (state) {
    case START: {
      int pos = ctx.getHeader().getTreeSegmentOffset();
      pushDepth();
      OracleJsonValue root = (OracleJsonValue)OsonStructureImpl.getValueInternal(pos, this, ctx);
      if (root.getOracleJsonType() != OracleJsonType.ARRAY && root.getOracleJsonType() != OracleJsonType.OBJECT) {
        state = State.FINISHED;
        currentPrimitive = root;
        return event = OracleJsonTypeToEvent(root);
      }
      setCurrent((OracleJsonStructure)root);
      state = State.NEXT_VALUE;
      if (root.getOracleJsonType() == OracleJsonType.ARRAY) {
        return event = Event.START_ARRAY;
      } else {
        return event = Event.START_OBJECT;
      }    
    }
    case NEXT_VALUE:
      return event = nextValue();
    case AFTER_KEY:
      state = State.NEXT_VALUE;
      return event = nextChild();
    case FINISHED:
    default:
      throw new NoSuchElementException();
    }
  }
  
  @Override
  public String getString() {
    if (state == State.AFTER_KEY) {
      return ((OsonAbstractObject)depthStack[depth]).getFieldName(currentChild[depth]);
    } else if (currentPrimitive != null) {
      switch(currentPrimitive.getOracleJsonType()) {
      case BINARY:
        return ((OracleJsonBinary)currentPrimitive).getString();
      case DATE:
        return ((OracleJsonDate)currentPrimitive).getString();
      case DECIMAL:
      case FLOAT:
      case DOUBLE:
        return ((OracleJsonNumberImpl)currentPrimitive).getString();
      case INTERVALDS:
        return ((OracleJsonIntervalDS)currentPrimitive).getString();
      case INTERVALYM:
        return ((OracleJsonIntervalYM)currentPrimitive).getString();
      case STRING:
        return ((OracleJsonString)currentPrimitive).getString();
      case TIMESTAMP:
        return ((OracleJsonTimestamp)currentPrimitive).getString();
      case TIMESTAMPTZ:
        return ((OracleJsonTimestampTZ)currentPrimitive).getString();
      default:
        throw OracleJsonExceptions.BAD_PARSER_STATE3.create(ctx.getExceptionFactory(), event);
      }
    }
    throw OracleJsonExceptions.BAD_PARSER_STATE3.create(ctx.getExceptionFactory(), event);
  }
  
  public boolean isIntegralNumber() {
    assertNumeric();
    return ((OracleJsonNumber)currentPrimitive).isIntegral();
  }
  
  @Override
  public int getInt() {
    assertNumeric();
    return ((OracleJsonNumber)currentPrimitive).intValue();
  }
  
  @Override
  public long getLong() {
    assertNumeric();
    return ((OracleJsonNumber)currentPrimitive).longValue();
  }
  
  @Override
  public BigDecimal getBigDecimal() {
    assertNumeric();
    return ((OracleJsonNumber)currentPrimitive).bigDecimalValue();
  }
  
  @Override
  public BigInteger getBigInteger() {
    assertNumeric();
    return ((OracleJsonNumber)currentPrimitive).bigIntegerValue();
  }
  
  @Override
  public double getDouble() {
    assertNumeric();
    return ((OracleJsonNumber)currentPrimitive).doubleValue();
  }
  
  @Override
  public float getFloat() {
    assertNumeric();
    return ((OracleJsonNumber)currentPrimitive).floatValue();
  }

  @Override
  public OffsetDateTime getOffsetDateTime() {
    if (currentPrimitive == null) {
      throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), OracleJsonType.TIMESTAMPTZ.toString());
    }
    if (currentPrimitive.getOracleJsonType() == OracleJsonType.TIMESTAMPTZ) {
      return  ((OracleJsonTimestampTZ)currentPrimitive).getOffsetDateTime();
    }
    throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), OracleJsonType.TIMESTAMPTZ.toString());
  }

  @Override
  public LocalDateTime getLocalDateTime() {
    if (currentPrimitive == null) {
      throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), OracleJsonType.TIMESTAMP.toString());
    }
    if (currentPrimitive.getOracleJsonType() == OracleJsonType.DATE) {
      return ((OracleJsonDate)currentPrimitive).getLocalDateTime();
    } else if (currentPrimitive.getOracleJsonType() == OracleJsonType.TIMESTAMP) {
      return ((OracleJsonTimestamp)currentPrimitive).getLocalDateTime();
    }
    throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), OracleJsonType.TIMESTAMP.toString());
  }
  
  @Override
  public byte[] getBytes() {
    assertJsonType(OracleJsonType.BINARY);
    return this.binaryPointer.getBytes();
  }
  
  @Override
  public void getBytes(OutputStream out) {
    assertJsonType(OracleJsonType.BINARY);
    try {
      binaryPointer.getBytes(out);
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(ctx.getExceptionFactory(), e);
    }
  }
  
  public boolean isId() {
    assertJsonType(OracleJsonType.BINARY);
    return this.binaryPointer.isId();
  }
  
  @Override
  public Period getPeriod() {
    assertJsonType(OracleJsonType.INTERVALYM);
    return ((OracleJsonIntervalYMImpl)currentPrimitive).getPeriod();
  }

  @Override
  public Duration getDuration() {
    assertJsonType(OracleJsonType.INTERVALDS);
    return ((OracleJsonIntervalDSImpl)currentPrimitive).getDuration();
  }
  
  //@Override JSONP 1.1
  public OracleJsonObject getObject() {
    assertJsonType(OracleJsonType.OBJECT);
    OsonAbstractObject obj = (OsonAbstractObject)getCurrent();
    objectCache[depth] = null;
    pop();
    return (OracleJsonObject)obj;
  }  
  
  //@Override JSONP 1.1
  public OracleJsonValue getValue() {
    final OracleOsonValueFactory f = OracleOsonValueFactory.INSTANCE;
    if (event == null || event == Event.END_ARRAY || event == Event.END_OBJECT) {
      throw OracleJsonExceptions.BAD_PARSER_STATE_VALUE.create(ctx.getExceptionFactory());
    }
    
    if (event == Event.KEY_NAME) {
      return new OracleJsonStringImpl(getString());
    }
    
    if (event == Event.VALUE_NULL) {
      return OracleJsonValue.NULL;
    } else if (event == Event.VALUE_FALSE) {
      return OracleJsonValue.FALSE;
    } else if (event == Event.VALUE_TRUE) {
      return OracleJsonValue.TRUE;
    }
    
    // Copy isn't necessary for some of these primitives as they aren't
    // cached but doing it anyway to guard against future bugs where caching
    // might be added by this method isn't updated
    OracleJsonValue val = currentPrimitive == null ? getCurrent() : currentPrimitive;
    switch (val.getOracleJsonType()) {
    case BINARY:
      OracleJsonBinary bin = (OracleJsonBinary)val;
      return new OracleJsonBinaryImpl(bin.getBytes(), bin.isId());
    case DOUBLE:
      OracleJsonDoubleImpl dbl = (OracleJsonDoubleImpl)val;
      return new OracleJsonDoubleImpl(dbl.doubleValue());
    case FLOAT:
      OracleJsonFloatImpl flt = (OracleJsonFloatImpl)val;
      return new OracleJsonFloatImpl(flt.floatValue());
    case DECIMAL:
      if (val instanceof OracleJsonStringNumberImpl) {
        OracleJsonStringNumberImpl stringNumber = (OracleJsonStringNumberImpl)val;
        return new OracleJsonStringNumberImpl(stringNumber.getString());
      } else {
        OracleJsonDecimalImpl num = (OracleJsonDecimalImpl)val;
        return new OracleJsonDecimalImpl(num.raw(), num.getTargetType());
      }
    case STRING:
      return new OracleJsonStringImpl((((OracleJsonString)val).getString()));
    case TIMESTAMP:
      OracleJsonTimestampImpl ts = (OracleJsonTimestampImpl)val;
      return f.createTimestamp(ts.raw());
    case TIMESTAMPTZ:
      OracleJsonTimestampTZImpl tstz = (OracleJsonTimestampTZImpl)val;
      return f.createTimestampTZ(tstz.raw());
    case DATE:
      OracleJsonDateImpl date = (OracleJsonDateImpl)val;
      return f.createDate(date.raw());
    case INTERVALDS:
      OracleJsonIntervalDSImpl intDs = (OracleJsonIntervalDSImpl)val;
      return f.createIntervalDS(intDs.raw());
    case INTERVALYM:
      OracleJsonIntervalYMImpl intYm = (OracleJsonIntervalYMImpl)val;
      return f.createIntervalYM(intYm.raw());
    case ARRAY:
      return getArray();
    case VECTOR:
      return (OracleJsonVectorImpl)val;
    case OBJECT:
    default:
      return getObject();
    }
  }
  
  //@Override JSONP 1.1
  public OracleJsonArray getArray() {
    assertJsonType(OracleJsonType.ARRAY);
    OsonAbstractArray arr = (OsonAbstractArray)getCurrent();
    arrayCache[depth] = null;
    pop(); 
    return (OracleJsonArray)arr;
  }

  //@Override JSONP 1.1
  public void skipArray() {
    if (depth < 0) {
      return;
    }
    if (getCurrent().getOracleJsonType() == OracleJsonType.ARRAY) {
      pop();
      currentPrimitive = null;
    }
  }
  
  //@Override JSONP 1.1
  public void skipObject() {
    if (depth < 0) {
      return;
    }
    if (getCurrent().getOracleJsonType() == OracleJsonType.OBJECT) {
      pop();
      currentPrimitive = null;
    }
  }
  
  @Override
  public void close() {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        throw OracleJsonExceptions.IO.create(ctx.getExceptionFactory(), e);
      }
      closeable = null;
    }
    depth = -1;
    state = State.FINISHED;
    event = null;
  }  

  /** Used when source is an InputStream */
  public void setCloseable(Closeable closeable) {
    this.closeable = closeable;
  }
  
  /// OsonValueFactory
  
  @Override
  public OsonAbstractArray createArray(OsonContext ctx, int pos) {
    if (arrayCache[depth] == null) {
      arrayCache[depth] = ctx.getFactory().createArray(ctx, pos);
    } else {
      arrayCache[depth].init(pos);
    }
    return arrayCache[depth];
  }
  
  @Override
  public OsonAbstractObject createObject(OsonContext ctx, int pos) {
    if (objectCache[depth] == null) {
      objectCache[depth] = ctx.getFactory().createObject(ctx, pos); 
    } else {
      objectCache[depth].init(pos);
    }
    return objectCache[depth];
  }
  
  @Override
  public Object createString(OsonContext ctx, int pos, int len) {
    stringPointer.reset(pos, len);
    return stringPointer;
  }
  
  @Override
  public Object createBinary(OsonContext ctx, int pos, int len, boolean isId) {
    binaryPointer.reset(pos, len,  isId);
    return binaryPointer;
  }
  
  @Override
  public OracleJsonDecimalImpl createNumber(byte[] raw, OracleJsonDecimal.TargetType type) {
    if (numberCache == null) {
      numberCache = super.createNumber(raw, type);
    } else {
      numberCache.reset(raw, type);
    }
    return numberCache;
  }
  
  public boolean toEntry(String key) {
    if (event != Event.START_OBJECT) {
      throw new IllegalStateException();
    }
        
    OsonObjectImpl obj = (OsonObjectImpl) getCurrent();
    int offset = obj.getChildPosition(key);
    if (offset == -1)
      return false;
        
    currentChild[depth] = offset;
    event = Event.KEY_NAME;
    state = State.AFTER_KEY;
    return true;
  }
         
  public void reset() {
    init();
  }
  
  private void setCurrent(OracleJsonStructure v) {
    currentChild[depth] = 0;
    depthStack[depth] = v;
    currentPrimitive = null;
  }
  
  private OracleJsonStructure getCurrent() {
    return depthStack[depth];
  }
  private void pop() {
    depth--;
    if (depth < 0) {
      state = State.FINISHED;
    }
  }
  
  private Event nextChild() {
    int child = currentChild[depth];
    currentChild[depth]++;
    int pos = ((OsonStructureImpl)depthStack[depth]).getChildOffset(child);
    pushDepth();
    // this may be returning a cached object (see OsonValueFactory) 
    OracleJsonValue v = (OracleJsonValue)OsonStructureImpl.getValueInternal(pos, this, ctx);
    if (v.getOracleJsonType() == OracleJsonType.OBJECT) {
      setCurrent((OracleJsonStructure)v);
      return Event.START_OBJECT;
    } else if (v.getOracleJsonType() == OracleJsonType.ARRAY) {
      setCurrent((OracleJsonStructure)v);
      return Event.START_ARRAY;
    } else {
      currentPrimitive = v;
      depth--;
      return OracleJsonTypeToEvent(v);
    }
  }

  private Event OracleJsonTypeToEvent(OracleJsonValue v) {
    switch (v.getOracleJsonType()) {
    case FALSE:
      return Event.VALUE_FALSE;
    case NULL:
      return Event.VALUE_NULL;
    case DECIMAL:
      return Event.VALUE_DECIMAL;
    case STRING:
      return Event.VALUE_STRING;
    case TRUE:
      return Event.VALUE_TRUE;
    case BINARY:
      return Event.VALUE_BINARY;
    case DOUBLE:
      return Event.VALUE_DOUBLE;
    case FLOAT:
      return Event.VALUE_FLOAT;
    case INTERVALDS:
      return Event.VALUE_INTERVALDS;
    case INTERVALYM:
      return Event.VALUE_INTERVALYM;
    case DATE:
      return Event.VALUE_DATE;
    case TIMESTAMP:
      return Event.VALUE_TIMESTAMP;
    case TIMESTAMPTZ:
      return Event.VALUE_TIMESTAMPTZ;
    case VECTOR:
      return Event.VALUE_VECTOR;
    default:
      throw new IllegalStateException(v.toString());
    }
  }
  
  private void pushDepth() {
    depth++;
    if (depth >= OsonConstants.UB2_MAXSZ) {
      throw OracleJsonExceptions.NEST_DEPTH_EXCEEDED.create(
          ctx.getExceptionFactory(), OsonConstants.UB2_MAXSZ);
    }
    if (depth >= depthStack.length) {
      expand();
    }
  }
  
  private void expand() {
    depthStack  = Arrays.copyOf(depthStack, depthStack.length*2);
    currentChild = Arrays.copyOf(currentChild, depthStack.length);
    objectCache = Arrays.copyOf(objectCache, depthStack.length);
    arrayCache  = Arrays.copyOf(arrayCache, depthStack.length);
  }

  private Event nextValue() {
    if (currentChild[depth] >= ((OsonStructureImpl)depthStack[depth]).size()) {
      if (depthStack[depth].getOracleJsonType() == OracleJsonType.OBJECT) {
        pop();
        return Event.END_OBJECT;
      } else {
        pop();
        return Event.END_ARRAY;
      }
    }
    
    if (depthStack[depth].getOracleJsonType() == OracleJsonType.OBJECT) {
      state = State.AFTER_KEY;
      return Event.KEY_NAME;
    }
    
    return nextChild();
  }
  

  private void assertNumeric() {
    OracleJsonValue val = currentPrimitive == null ? getCurrent() : currentPrimitive;
    OracleJsonType type = val.getOracleJsonType();
    if (type != OracleJsonType.DECIMAL && 
        type != OracleJsonType.DOUBLE && 
        type != OracleJsonType.FLOAT) {
      throw OracleJsonExceptions.BAD_PARSER_STATE3.create(ctx.getExceptionFactory(), event);
    }
  }
  
  private void assertJsonType(OracleJsonType type) {
    if (depth < 0) {
      throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), type.toString());
    }
    switch (type) {
    case ARRAY:
      if (currentPrimitive != null || getCurrent().getOracleJsonType() != OracleJsonType.ARRAY) {
        throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), type.toString());
      }
      break;      
    case OBJECT:
      if (currentPrimitive != null || getCurrent().getOracleJsonType() != OracleJsonType.OBJECT) {
        throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), type.toString());
      }
      break;
    case FALSE:
    case NULL:
    case DECIMAL:
    case STRING:
    case TRUE:
    default:
      if (currentPrimitive == null || currentPrimitive.getOracleJsonType() != type) {
        throw OracleJsonExceptions.BAD_PARSER_STATE.create(ctx.getExceptionFactory(), type.toString());
      }
    }
  }

  @SuppressWarnings({ "unchecked", "resource" })
  @Override
  public <T> T wrap(Class<T> wrapper) {
    try {
      if (Jsonp.isJakartaJsonStream(wrapper)) {
        return wrapper.cast((T)new JakartaParserWrapper(this));
      } else {
        return wrapper.cast((T)new JsonpParserWrapper(this));
      }
    } catch (ClassCastException e) {
      throw OracleJsonExceptions.BAD_WRAP.create(OracleJsonExceptions.ORACLE_FACTORY, e, wrapper.getName());
    }
  }
  
  public long getStreamOffset() {
    if (this.state == State.START)
      return 0;
    return -1;
  }

  public OsonContext getContext() {
    return ctx;
  }
  
  public int getCurrentStringPos() {
    return stringPointer.getPos();
  }
  
  public int getCurrentStringLen() {
    return stringPointer.getLen();
  }

}

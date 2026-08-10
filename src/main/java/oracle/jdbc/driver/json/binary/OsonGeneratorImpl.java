/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_INLINE_LEAF;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_REL_OFFSET;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_SHR_NODES;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_SHR_SIMP_NODES;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_SLEN_IN_PCODE;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_TOT_DISFNM_UB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_ARRAY_TYP;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_BIN_SRCH_TRIG_LIMIT;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_FLDNM_SZ_UB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_HID_USEUB1;
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
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JNULL_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JORA_DTYNUM_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JORA_DTYNUM_DEC_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSNUM_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSUB1L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSUB2L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JSUB4L_C;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_JVECTOR;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_OBJECT_TYP;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_OBJ_FID_REFERRED;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_ORANUM_OK_4BITS;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_STR_OK_5BITS;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_TINY_NODE_STAT;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_TOT_DISFNM_UB2;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_TREE_SZ_UB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.MAGIC_VERSION1;
import static oracle.jdbc.driver.json.binary.OsonConstants.MAGIC_VERSION3;
import static oracle.jdbc.driver.json.binary.OsonConstants.MASK_DEC_16;
import static oracle.jdbc.driver.json.binary.OsonConstants.MASK_ORANUM_16;
import static oracle.jdbc.driver.json.binary.OsonConstants.MASK_SB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.MASK_SB8;
import static oracle.jdbc.driver.json.binary.OsonConstants.OPCODE_CHILD_NO_SORT_BIT;
import static oracle.jdbc.driver.json.binary.OsonConstants.UB1_MAXSZ;
import static oracle.jdbc.driver.json.binary.OsonConstants.UB2_MAXSZ;
import static oracle.jdbc.driver.json.binary.OsonConstants.isDec_16;
import static oracle.jdbc.driver.json.binary.OsonConstants.isOraNum16;
import static oracle.jdbc.driver.json.binary.OsonConstants.isSB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.isSB8;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.tree.OracleJsonDateImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalDSImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampTZImpl;
import oracle.jdbc.driver.json.tree.OracleJsonVectorImpl;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonDouble;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonVector;

/**
 * Encodes an OSON binary value.
 * 
 * In general, encoding OSON is expensive compared to JSON text or 
 * other binary JSON formats.  The value must be materialized
 * in advance in order to decide the required offset sizes.  The
 * field id arrays must be sorted in order to enable efficient
 * in-place reads.
 * 
 * One strategy for encoding OSON would be to construct an in-memory
 * tree representation (or, DOM) such as a OracleJsonObjectImpl, etc.
 * and then convert it to OSON.  However, in XML implementations,
 * it has been shown that "pointer-chasing" of Java is relatively
 * expensive.  Instead, this implementation represents the tree
 * as a set of parallel arrays that encode the tree.  When the 
 * stream is closed and the tree is fully encoded in these arrays,
 * then they are iterated and encoded as OSON.  
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public final class OsonGeneratorImpl extends AbstractGenerator implements OracleJsonGenerator {

  public enum DuplicateKeyMode { ALLOW, DISALLOW }
  
  private static boolean DEFAULT_SIMPLE_VALUE_SHARING = 
      "true".equals(System.getProperty("oracle.jdbc.driver.json.binary.DEFAULT_SIMPLE_VALUE_SHARING", "false"));
  private static boolean DEFAULT_LAST_VALUE_SHARING = 
      "true".equals(System.getProperty("oracle.jdbc.driver.json.binary.DEFAULT_LAST_VALUE_SHARING", "false"));
  private static boolean DEFAULT_RELATIVE_OFFSETS = 
      "true".equals(System.getProperty("oracle.jdbc.driver.json.binary.DEFAULT_RELATIVE_OFFSETS", "false"));
  private static boolean DEFAULT_TINYNODE = 
      "true".equals(System.getProperty("oracle.jdbc.driver.json.binary.DEFAULT_TINYNODE", "true"));
  
  private static final DuplicateKeyMode DEFAULT_DUPLICATE_KEY_MODE;
  
  private static int INITIAL_OPS = 64;
  private static int OUT_BUFFER_SIZE = 1024*8;
  
  /**
   * We do a 256 fix hash table for keys.  At the end we need to iterate this table. 
   * For small documents (say 10 keys) this iteration is relatively expensive so we 
   * maintain a list of seen hash codes, sort it and iterate (see seenHash).  
   * But at some point it makes more sense to just iterate the hash table.  Isn't
   * clear where this threshold is exactly, current number is a guess.
   */
  private static int SEEN_HASH_THRESHOLD = 64; 
  
  private static byte[] ONE = OsonPrimitiveConversions.toNumber(1);
  private static byte[] ZERO = OsonPrimitiveConversions.toNumber(0);
  
  private static final class OsonGeneratorState {

    // ~~~~~~ key segment state  ~~~~~~
  
    /**
     * UB1 hashid -> int field ids with that hash The stored FID is off by
     * one so that when a new key array is allocated we can assume that 0 is
     * uninitialized.
     */
    private int[][] keys = new int[256][];
    /** parallel to keys.  Records last seen value for each key */
    private int[][] keysLastSeenValue;
    
    private boolean keysNeedReset = true;
    int[] seenHash = new int[SEEN_HASH_THRESHOLD];
    int seenHashSize;
    /** index to keys of last seen key */
    int keyI, keyJ;

    private String[] distinctKeys = new String[16];
    private int distinctKeysSize;
  
    /** Field name string concatenations heap */
    private byte[] keyHeap;
    private int keyHeapSize;
  
    /** parallel to distinct keys.  gives offset into key segment of that key */
    private int[] keyHeapOffsets;
  
    /** 
     * fid mapping array.  computed at end when hash ordering is known
     * distinctKeys[i] maps to fid at fidMap[i]
     */
    private int[] fidMap;
  
    /** Less optimized management of larger keys */
    TreeMap<BigKey, Integer> bigKeys;
    int bigKeysHeapSize = 0;
    
    /** transient value to hold the length of a string */
    AtomicInteger keylen = new AtomicInteger();
    
    // ~~~~~~ tree segment state ~~~~~~

    private int numOps;
  
    /** 
     * Op codes of values in the document (pre-order)
     */
    private short[] ops = new short[INITIAL_OPS];
  
    /** 
     * Parallel array to ops.  Value indexes into ops, points to next sibling.
     * e.g.
     * 
     *  sibling (ops[7]) === ops[nextSiblings[7]]
     * 
     * 0 indicates there is no next sibling.  idx 0 holds the root in ops
     * and it can not have a next sibling. 
     */
    private int[] nextSiblings = new int[INITIAL_OPS];
    
    /** key of op at i.  Negative fieldId means it is a bigkey (see bigKeys) */
    private int[] fieldIDs = new int[INITIAL_OPS];
    
    /** Depth of op at position i (important: treat as unsigned value!) */
    private short[] depths = new short[INITIAL_OPS];
    
    /** 
     * Position in value heap of op[i]
     * 
     * if (valueIndex[i] > 0)
     *    op is primitive and this is the index into valueHeap
     * if (valueIndex[i] == 0)
     *    op is an object or array
     */
    private int[] valueIndex = new int[INITIAL_OPS];
    
    /** 
     * Number of children of op.
     * 
     * There are three cases:
     * (1) if the op is an object/array it is the number of elements
     * (2) if the op is a primitive it is the size of the value in 
     *     bytes stored in valueHeap
     * (3) if numChildren is negative, it means that the value is 
     *     a duplicate and the value is the index of the op that 
     *     it duplicates 
     */
    private int[] numChildren = new int[INITIAL_OPS];
    
    /** Offset in tree segment of op i.  computed at end */
    private int[] offsets;
    private int treeSegmentSize;
  
    /** 
     * Primitive values heaped together:
     *  [length?] [value ]
     * So, 
     *  valueHeap[0] corresponds to first primitive in ops.
     *  valueHeap[1] corresponds to second primitive in ops.
     *  ...
     */
    private byte[] valueHeap = new byte[1024];
    private int valueHeapSize;  
    
    private int tinyNodeCount;
  
    short headerFlags;
    // ~~~~~~ transient state ~~~~~~ 
    
    /** index of ops at each depth */
    private int[] opStack = new int[2];
    private int depth;
    
    private int previousSiblingIdx;
    
    /** temporary holders for a nodes child offsets */
    private int[]  temporaryIntArray;
    
    private long[] temporaryLongArray;
    
    private final StreamContext ctx = new StreamContext(null);
    
    private OutputStream out;
    private byte[] outBuffer = new byte[OUT_BUFFER_SIZE];
    private int outBufferPos;

    /** Indicates if should use relative offsets */
    public boolean relativeOffsets;
    
    /** Indicates if should share simple values (true/false/null/0/1/etc) */
    public boolean simpleValueSharing;
    
    /** Indicates if should attempt to share the last value seen for each fid */
    public boolean lastValueSharing;
    
    /** 
     * The index of the op or -1 if not seen yet. 
     */
    int opNull, opTrue, opFalse, opZero, opOne, opEmptyString, opEmptyObject, opEmptyArray;
    
    /** the op index of the last value seen for recently seen key */ 
    int opLastValue;
    
    /** The pool this state came from */
    private OsonGeneratorStatePool pool;
    
    private DuplicateKeyMode duplicateKeyMode = DEFAULT_DUPLICATE_KEY_MODE;
    
    private OsonGeneratorState(OsonGeneratorStatePool pool, OutputStream out) {
      this.pool = pool;
      this.out = out;
      this.ctx.setExceptionFactory(getExceptionFactory());
    }
    
    private void writeNumber(byte[] bytes) {
      if (bytes.length <= JZNOCT_ORANUM_OK_4BITS) {
        int op = (bytes.length -1) | MASK_ORANUM_16;
        if (simpleValueSharing) {
          if (Arrays.equals(ONE, bytes)) {
            addOpAndValueNoPostOp(op, bytes);
            if (opOne == -1) {
              opOne = numOps-1;
            } else {
              headerFlags |= JZNOCT2_SHR_SIMP_NODES;
              markDuplicate(numOps-1, opOne);
            }
            postOp(false);
            return;
          } else if (Arrays.equals(ZERO, bytes)) {
            addOpAndValueNoPostOp(op, bytes);
            if (opZero == -1) {
              opZero = numOps-1;
            } else {
              headerFlags |= JZNOCT2_SHR_SIMP_NODES;
              markDuplicate(numOps-1, opZero);
            }
            postOp(false);
            return;
          }
        }
        addOpAndValue(op, bytes);
      } else if (bytes.length < UB1_MAXSZ) {
        addOpAndValue(JZNOCT_JORA_DTYNUM_C, bytes);
      }
    }

    private void push(int opIndex) {
      if (ctx.depth >= opStack.length) {
        opStack = Arrays.copyOf(opStack, opStack.length*2);
      }
      opStack[depth] = opIndex;
      depth++;
      if (depth >= OsonConstants.UB2_MAXSZ) {
        throw OracleJsonExceptions.NEST_DEPTH_EXCEEDED.create(
            getExceptionFactory(), OsonConstants.UB2_MAXSZ);
      }
      previousSiblingIdx = -1;
    }
    
    private void addOp(int op) {
      int thisOp = numOps++;
      ops[thisOp] = (short)op;
      depths[thisOp] = (short)depth;
      if (previousSiblingIdx != -1) {
        nextSiblings[previousSiblingIdx] = thisOp;
      }
      nextSiblings[thisOp] = -1;
      if (depth > 0) {
        numChildren[opStack[depth-1]]++;
      }
      previousSiblingIdx = thisOp;
    }  
    
    private void expandOp() {
      int l = ops.length * 2;
      ops = Arrays.copyOf(ops, l);
      nextSiblings = Arrays.copyOf(nextSiblings, l);
      fieldIDs = Arrays.copyOf(fieldIDs, l);
      depths = Arrays.copyOf(depths, l);
      numChildren = Arrays.copyOf(numChildren, l);
      valueIndex = Arrays.copyOf(valueIndex, l);
    }

    private void preOp() {
      if (numOps >= ops.length) {
        expandOp();
      }
      numChildren[numOps] = 0;
    }
    
    private void postOp(boolean shareable) {
      if (lastValueSharing && keyI != -1 && keyJ != -1) {
        initKeysLastSeenValue(keyI);
        int idx = numOps-1;
        if (numChildren[idx] >= 0 && shareable) { 
          keysLastSeenValue[keyI][keyJ] = idx;
        }
        keyI = keyJ = -1;
      }
      opLastValue = -1;
    }
    
    /** 
     * op has not yet been added to ops at the time this is called 
     */
    private void addValue(byte[] bytes) {
      expandValueHeap(bytes.length);
      addValueNoCheck(bytes);
    }
    
    private void addValueNoCheck(byte[] bytes) {
      valueIndex[numOps] = valueHeapSize;
      System.arraycopy(bytes, 0, valueHeap, valueHeapSize, bytes.length);
      valueHeapSize += bytes.length;
    }

    private boolean equals(byte[] a1, int a1Start, byte[] a2, int a2Start, int len) {
      for (int i = 0; i < len; i++) {
        if (a1[a1Start] != a2[a2Start]) {
          return false;
        }
        a1Start++;
        a2Start++;
      }
      return true;
    }

    private void expandValueHeap(int len) {
      if (len + valueHeapSize >= valueHeap.length) {
        int newSize = (len + valueHeapSize) * 2;
        if (newSize <= 0) {
          throw OracleJsonExceptions.IMAGE_TOO_BIG.create(getExceptionFactory());
        }
        valueHeap = Arrays.copyOf(valueHeap, newSize);
      }
    }
    
    private void initializeKeyHeap() throws UnsupportedEncodingException {
      if (keyHeap == null) {
        keyHeap = new byte[distinctKeysSize * 15];
      }
      if (keyHeapOffsets == null || keyHeapOffsets.length < distinctKeysSize) {
        keyHeapOffsets = new int[distinctKeysSize];
      }
      keyHeapSize = 0;
      for (int i = 0; i < distinctKeysSize; i++) {
        keyHeapOffsets[i] = keyHeapSize;
        String key = distinctKeys[i];
        int maxBytesAdded = 1 + (key.length()*4);
        if (maxBytesAdded + keyHeapSize >= keyHeap.length) {
          keyHeap = Arrays.copyOf(keyHeap, (keyHeap.length + maxBytesAdded) * 2);
        }
        int result = writeString(key, keyHeap, keyHeapSize+1);
        int len = (result - keyHeapSize - 1);
        keyHeap[keyHeapSize] = (byte)len;
        keyHeapSize = result;
      }
    }
    
    public ExceptionFactory getExceptionFactory() {
      return OracleJsonExceptions.ORACLE_FACTORY;
    }
    
    /** 
     * IMPORTANT: This method assumes that destination has enough room for the
     * encoded array (e.g. upper bound is (4 * value.length())  
     */
    private int writeString(String value, byte[] destination, int destinationPos) {
      int ct = destinationPos;
      final int len = value.length();
      for (int i = 0; i < len; i++) {
        final char c = value.charAt(i);
        if (c >= 0x7f) {
          return slowWriteString(value, destination, destinationPos);
        }
        destination[ct++] = (byte)c;
      }
      return ct;
    }
    
    
    private int writeUTF8String(byte[] source, int sourceOffset, int len, byte[] destination, int destinationPos) {
      int ct = destinationPos;
      for (int i = 0; i < len; i++) {
        destination[ct++] = source[sourceOffset++];
      }
      return ct;
    }
    
    private int slowWriteString(String value, byte[] destination, int destinationPos) {
      byte[] result = value.getBytes(StandardCharsets.UTF_8);
      for (int i = 0; i < result.length; i++) {
        destination[destinationPos++] = result[i];
      }
      return destinationPos;
    }
    
    private void writeHeader() throws IOException {
      if (bigKeys == null) {
        writeInt(MAGIC_VERSION1);
      } else {
        writeInt(MAGIC_VERSION3);
      }

      if (distinctKeysSize >= UB2_MAXSZ) {
        headerFlags |= JZNOCT2_TOT_DISFNM_UB4;
      } else if (distinctKeysSize >= UB1_MAXSZ) {
        headerFlags |= JZNOCT_TOT_DISFNM_UB2;
      }
      
      if (distinctKeysSize > 0) {
        headerFlags |= JZNOCT_HID_USEUB1;
      }
      
      if (keyHeapSize >= UB2_MAXSZ) {
        headerFlags |= JZNOCT_FLDNM_SZ_UB4;
      }

      if (treeSegmentSize > UB2_MAXSZ) {
        headerFlags |= JZNOCT_TREE_SZ_UB4;
      }
      
      if (relativeOffsets) {
        headerFlags |= JZNOCT2_REL_OFFSET;
      }

      if (numOps == 1 && !isObject(ops[0]) && !isArray(ops[0])) {
        int flags = headerFlags;
        flags &= ~JZNOCT_TINY_NODE_STAT;
        flags |= OsonConstants.JZNOCT2_J_SCALAR;
        writeShort(flags);
        writeTreeSegmentSize();
        return;
      }

      writeShort(headerFlags);
          
      if (distinctKeysSize >= UB2_MAXSZ) {
        writeInt(distinctKeysSize);
      } else if (distinctKeysSize >= UB1_MAXSZ) {
        writeShort(distinctKeysSize);
      } else {
        writeByte(distinctKeysSize);
      }
      
      if (keyHeapSize >= UB2_MAXSZ) {
        writeInt(keyHeapSize);
      } else {
        writeShort(keyHeapSize);
      }
      
      if (bigKeys != null) {
        for (BigKey bk : bigKeys.keySet()) {
          bigKeysHeapSize += bk.key.length;
        }
        bigKeysHeapSize += (bigKeys.size() * 2);
        if (bigKeysHeapSize < UB2_MAXSZ) {
          writeShort(OsonConstants.JZNOCT3_FLDNM2_SZ_UB2); 
        } else {
          writeShort(0);
        }
        writeInt(bigKeys.size());
        writeInt(bigKeysHeapSize);
      }
      
      writeTreeSegmentSize();
      
      /* value segment size.  currently only supports inline leaf */
      if ((headerFlags & JZNOCT_TINY_NODE_STAT) != 0) {
        writeShort(tinyNodeCount);
      } else {
        writeShort(0);
      }
    }

    private void writeTreeSegmentSize() throws IOException {
      if (treeSegmentSize > UB2_MAXSZ) {
        writeInt(treeSegmentSize);
      } else {
        writeShort(treeSegmentSize);
      }
    }
    
    private void writeNameDictionary() throws IOException {
      if (fidMap == null || fidMap.length < 
          (distinctKeysSize + (bigKeys == null ? 0 : bigKeys.size()))) {
        fidMap = new int[distinctKeysSize + (bigKeys == null ? 0 : bigKeys.size())];
      }
      
      // Repurpose one of the temporary arrays used later to hold
      // the reordered key offsets
      initTemporaryIntArray(distinctKeysSize);
      
      if (seenHashSize < SEEN_HASH_THRESHOLD) {
        Arrays.sort(seenHash, 0, seenHashSize);
        int offIdx = 0;
        for (int k = 0; k < seenHashSize; k++) { 
          int i = seenHash[k];
          offIdx = processBucket(offIdx, i);
        }
      } else {
        int offIdx = 0;
        for (int i = 0; i < keys.length; i++) {
          if (keys[i] == null) {
            continue;
          }
          offIdx = processBucket(offIdx, i);
        }
      }
      keysNeedReset = false; /* processBucket resets hash table */
      
      /** heap offset array */ 
      if (keyHeapSize >= UB2_MAXSZ) {
        writeUb4Array(temporaryIntArray, distinctKeysSize);
      } else {
        writeUb2Array(temporaryIntArray, distinctKeysSize);
      }
      /** key string heap */
      write(keyHeap, 0, keyHeapSize);
       
    }

    private int processBucket(int offIdx, int hash) throws IOException {
      int[] bucket = keys[hash];
      int[] lastValuesBucket = lastValueSharing ? keysLastSeenValue[hash] : null;
      
      sortBucket(bucket);
      for (int j = 0; j < bucket.length; j++) {
        int off = bucket[j]-1;
        if (off == -1) {
          break;
        }
        writeByte(hash);
        bucket[j] = 0; // reset here
        if (lastValueSharing)
          lastValuesBucket[j] = 0;
        fidMap[off] = offIdx;
        temporaryIntArray[offIdx++] = keyHeapOffsets[off];
      }
      return offIdx;
    }
    
    /** 
     * There is no way to resuse jdk sort here due to stopping conditions.
     * May want to convert this to use quick sort.
     */
    private void sortBucket(int[] bucket) {
      for (int i = 0; i < bucket.length && bucket[i] != 0; i++) {
        for (int j = i+1; j < bucket.length && bucket[j] != 0; j++) {
          int keyHeapOff1 = keyHeapOffsets[bucket[i]-1];
          int keyHeapOff2 = keyHeapOffsets[bucket[j]-1];
          int l1 = (keyHeap[keyHeapOff1] & 0xff);
          int l2 = (keyHeap[keyHeapOff2] & 0xff);
          if (l2 < l1 || (l2 == l1 && 
              memcmp(keyHeapOff2+1, keyHeapOff1+1, l1) < 0)) { 
            int tmp = bucket[i];
            bucket[i] = bucket[j];
            bucket[j] = tmp;
          }
        }
      }
    }

    private int memcmp(int i, int j, int length) {
      for (int k = 0; k < length; k++) {
        int d = (keyHeap[i+k] & 0xff) - (keyHeap[j+k] & 0xff); 
        if (d != 0) return d;
      }
      return 0;
    }

    private void writeNameDictionary2() throws IOException {
      int i = 0;
      for (Entry<BigKey, Integer> e : bigKeys.entrySet()) {
        fidMap[distinctKeysSize + e.getValue()-1] = distinctKeysSize + (i++);
        writeShort(e.getKey().hash);
      }
      int offset = 0;
      for (BigKey bk : bigKeys.keySet()) { 
        if (bigKeysHeapSize < OsonConstants.UB2_MAXSZ) {
          writeShort(offset);
        } else {
          writeInt(offset);
        }
        offset += (2 + bk.key.length);
      }
      for (BigKey bk : bigKeys.keySet()) { 
        writeShort(bk.key.length);
        write(bk.key, 0, bk.key.length);
      }
    }
    
    private void writeTreeNodeSegment() throws IOException {
      for (int index=0; index < numOps; index++) {
        final int op = ops[index];
        if (isShared(index)) {
          continue;
        }
        if (isArray(op)) {
          final int parentOffset = offsets[index];
          final int childCt = numChildren[index];
          writeByte(flagObjectOrArray(op, childCt));
          
          if (childCt < UB1_MAXSZ) {
            writeByte(childCt);
          } else if (childCt < UB2_MAXSZ) {
            writeShort(childCt);
          } else {
            writeInt(childCt);
          }
          initTemporaryIntArray(childCt); 
          int childIdx = index + 1;
          for (int i = 0; i < childCt; i++) {
            temporaryIntArray[i] = offsets[childIdx];
            childIdx = nextSiblings[childIdx];
          }
          writeChildOffsets(childCt, temporaryIntArray, parentOffset);
        } else if (isObject(op)) {
          final int parentOffset = offsets[index];
          final int childCt = numChildren[index];
          writeByte(flagObject(op, childCt));

          initTemporaryLongArray(childCt);
          if (sharesFields(op)) {
            // this object shares its field ids
            int firstChild = firstChild(index);
            int delagateIdx = fieldIDs[firstChild];
            int delagate = offsets[delagateIdx];

            //reset it back to the actual fieldID
            fieldIDs[firstChild] = fieldIDs[firstChild(delagateIdx)];

            
            if (treeSegmentSize < UB2_MAXSZ) {
              writeShort(delagate);
            } else {
              writeInt(delagate);
            }
            
            packOffsets(index, childCt, temporaryLongArray);
            
            if (childCt > JZNOCT_BIN_SRCH_TRIG_LIMIT && (op & OPCODE_CHILD_NO_SORT_BIT) == 0) {
              Arrays.sort(temporaryLongArray, 0, childCt);
            }
            writeChildOffsets(childCt, temporaryLongArray, parentOffset);
          } else {
            
            // [[fid][offset]] into array of long
            packOffsets(index, childCt, temporaryLongArray);
            
            if (childCt > JZNOCT_BIN_SRCH_TRIG_LIMIT && (op & OPCODE_CHILD_NO_SORT_BIT) == 0) {
              Arrays.sort(temporaryLongArray, 0, childCt);
            } else if (duplicateKeyMode == DuplicateKeyMode.DISALLOW) {
              // for sorted arrays, duplicates will be caught below
              checkDuplicateKeys(temporaryLongArray, childCt);
            }
            
            if (childCt < UB1_MAXSZ) {
              writeByte(childCt);
            } else if (childCt < UB2_MAXSZ) {
              writeShort(childCt);
            } else {
              writeInt(childCt);
            }
            
            int lastFid = -1;
            for (int i = 0; i < childCt; i++) {
              int fid = unpackFid(temporaryLongArray[i]);
              if (fid == lastFid && duplicateKeyMode == DuplicateKeyMode.DISALLOW) {
                throw OracleJsonExceptions.DUPLICATE_KEY.create(getExceptionFactory(), reverseFidMap(fid));
              }
              lastFid = fid;
              if (distinctKeysSize >= UB2_MAXSZ) {
                writeInt(fid);
              } else if (distinctKeysSize >= UB1_MAXSZ) {
                writeShort(fid);  
              } else {
                writeByte(fid);
              }
            }

            writeChildOffsets(childCt, temporaryLongArray, parentOffset);
          }
        } else if (op <= JZNOCT_STR_OK_5BITS) { // <1>
          writeOpAndData(op, valueHeap, valueIndex[index], op);
        } else if (isSB4(op) || 
               isSB8(op) ||
               isOraNum16(op) ||
               isDec_16(op)) {
          writeByte(op);
          write(valueHeap, valueIndex[index], numChildren[index]);
        } else {
          switch (op) {
          case JZNOCT_JBOOLT_C:
            writeByte(op);
            break;
          case JZNOCT_JBOOLF_C:
            writeByte(op);
            break;
          case JZNOCT_JNULL_C:
            writeByte(op);
            break;
          case JZNOCT_JORA_DTYNUM_C:
          case JZNOCT_JORA_DTYNUM_DEC_C:
          case JZNOCT_JSUB1L_C: {
            writeByte(op);
            int size = numChildren[index];
            writeByte(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          case JZNOCT_JSUB2L_C: {
            writeByte(op);
            int size = numChildren[index];
            writeShort(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          case JZNOCT_JSUB4L_C: {
            writeByte(op);
            int size = numChildren[index];
            writeInt(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          case JZNOCT_JDTYDB_C:
            writeByte(op);
            write(valueHeap, valueIndex[index], 8);
            break;
          case JZNOCT_JDTYFLT_C:
            writeByte(op);
            write(valueHeap, valueIndex[index], 4);
            break;
          case JZNOCT_JDTYGENID_C: {
            writeByte(op);
            int size = numChildren[index];
            writeByte(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          case JZNOCT_JBINUB2L_C: {
            writeByte(op);
            int size = numChildren[index];
            writeShort(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          case JZNOCT_JBINUB4L_C: {
            writeByte(op);
            int size = numChildren[index];
            writeInt(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          case JZNOCT_JDTYSTAMP_C: {
            writeByte(op);
            write(valueHeap, valueIndex[index], OsonPrimitiveConversions.SIZE_TIMESTAMP);
            break;
          }
          case JZNOCT_JDTYSTAMP7_C: {
            writeByte(op);
            write(valueHeap, valueIndex[index], OsonPrimitiveConversions.SIZE_TIMESTAMP_NOFRAC);
            break;
          }
          case JZNOCT_JDTYSTAMP_TZ_C: {
            writeByte(op);
            write(valueHeap, valueIndex[index], OsonPrimitiveConversions.SIZE_TIMESTAMPTZ);
            break;
          }
          case JZNOCT_JDTYDATE_C: {
            writeByte(op);
            write(valueHeap, valueIndex[index], OsonPrimitiveConversions.SIZE_DATE);
            break;
          }     
          case JZNOCT_JDTYDS_C: {
            writeByte(op);
            write(valueHeap, valueIndex[index], OracleJsonIntervalDSImpl.INTERVALDS_LEN);
            break;
          }
          case JZNOCT_JDTYYM_C: {
            writeByte(op);
            write(valueHeap, valueIndex[index], OracleJsonIntervalYMImpl.INTERVALYM_LEN);
            break;
          }        
          case JZNOCT_JSNUM_C: {
            int size = numChildren[index];
            if (size == 0) 
              break; // this is a shared number, doesn't need to be written
            writeByte(op);
            writeByte(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          case JZNOCT_JVECTOR: {
            int size = numChildren[index];
            writeShort(op);
            writeInt(size);
            write(valueHeap, valueIndex[index], size);
            break;
          }
          default:
            throw new UnsupportedOperationException(String.valueOf(op));
          }  
        }
      }
    }
    
    /** Used for duplicate key error messages only */
    private String reverseFidMap(int fid) {
      for (int i = 0; i < distinctKeysSize; i++) {
        if (fidMap[i] == fid-1) {
          return distinctKeys[i];
        }
      }
      return "";
    }

    private void packOffsets(int index, int childCt, long[] packedArray) {
      int childIdx = index + 1;
      for (int i = 0; i < childCt; i++) {
        int childKeyIdx = fieldIDs[childIdx];
        if (childKeyIdx < 0) {
          //big key
          childKeyIdx = distinctKeysSize + Math.abs(childKeyIdx) - 1;
        }
        long  fid = fidMap[childKeyIdx]+1;
        // pack [fid][offset of child] into long to efficiently sort (fid, offset) by fid
        packedArray[i] = ((fid << 32) | offsets[childIdx]);
        childIdx = nextSiblings[childIdx];
      }
    }
    
    private boolean sharesFields(int op) {
      return (op & 0x18) == 0x18;
    }
    
    private boolean isReferredTo(int op) {
      return (op & JZNOCT_OBJ_FID_REFERRED) == JZNOCT_OBJ_FID_REFERRED; 
    }
    
    private void tryFieldIdSharing(int primaryIndex) {
      int siblingIndex = nextSiblings[primaryIndex];
      while (siblingIndex != -1) {
        if (sameFieldIds(primaryIndex, siblingIndex)) {
          ops[siblingIndex] |= 0x18;
          ops[primaryIndex] |= JZNOCT_OBJ_FID_REFERRED;
          int firstChild = firstChild(siblingIndex);
          // mark first child fid as negative offset of primary
          fieldIDs[firstChild] = primaryIndex;
        }
        siblingIndex = nextSiblings[siblingIndex];
      }
    }
    
    private int firstChild(int index) {
      if (index+1 >= numOps) {
        return -1;
      }
      int thisDepth = depths[index] & 0xffff;
      int nextDepth = depths[index+1] & 0xffff;
      if (nextDepth == thisDepth+1) {
        return index+1;
      }
      return -1;
    }
    
    private boolean sameFieldIds(int p1, int p2) {
      // false if (1) they aren't objects
      //          (2) they don't have the same number of entries
      //          (3) they don't have any children
      //          (4) the user has explicitly requested no sorting for one
      if (!isObject(ops[p1]) || !isObject(ops[p2]) || 
          numChildren[p1] != numChildren[p2] || numChildren[p1] == 0 || 
          (ops[p1] & OPCODE_CHILD_NO_SORT_BIT) != 0 || 
          (ops[p2] & OPCODE_CHILD_NO_SORT_BIT) != 0
         ) {
        return false;
      }
      
      int child1 = firstChild(p1);
      int child2 = firstChild(p2);

      while (true) {
        if (fieldIDs[child1] != fieldIDs[child2]) {
          return false;
        }
        child1 = nextSiblings[child1];
        child2 = nextSiblings[child2];
        if (child1 == -1) {
          return child2 == -1;
        } else if (child2 == -1) {
          return false;
        }
      }
    }
    
    private boolean isArray(int op) {
      return (op & 0xC0) == JZNOCT_ARRAY_TYP;
    }
    
    private boolean isObject(int op) {
      return (op & 0xC0) == JZNOCT_OBJECT_TYP;
    }  
    
    private boolean isStructure(int op) {
      return ((byte)op) < 0;
    }
    
    /** Assumes the offset is in the low 4 bytes of long values */
    private void writeChildOffsets(int childCt, long[] arr, int fixedOffset) throws IOException {
      final int delta = relativeOffsets ? fixedOffset : 0; 
      if (treeSegmentSize < UB2_MAXSZ) {
        for (int i = 0; i < childCt; i++) {
          short off = (short)(arr[i] & 0xffff);
          off -= delta;
          writeShort(off);
        } 
      } else {
        for (int i = 0; i < childCt; i++) {
          int off = (int)(arr[i] & 0xffffffff); 
          off -= delta;
          writeInt(off);
        } 
      }
    }

    private void writeChildOffsets(int childCt, int[] arr, int fixedOffset) throws IOException {
      final int delta = relativeOffsets ? fixedOffset : 0; 
      if (treeSegmentSize < UB2_MAXSZ) {
        for (int i = 0; i < childCt; i++) {
          short off = (short)(arr[i] & 0xffff);
          writeShort(off - delta);
        } 
      } else {
        for (int i = 0; i < childCt; i++) {
          int off = arr[i];
          writeInt(off - delta);
        } 
      }
    }

    private void initTemporaryLongArray(int ct) {
      if (temporaryLongArray == null || temporaryLongArray.length < ct) {
        temporaryLongArray = new long[ct];
      }
    }
    
    private int unpackFid(long packed) {
      return (int)(packed >>> 32);
    }
    
    /** 
     * This should only run for very small arrays (<10). 
     * Otherwise, duplicates are caught when the sorted FID array is written.
     */
    public void checkDuplicateKeys(long[] children, int count) {
      for (int i = 0; i < count; i++) {
        int fid = unpackFid(children[i]);
        for (int j = i+1; j < count; j++) {
          if (unpackFid(children[j]) == fid) {
            String key = fid <= distinctKeysSize ? 
               reverseFidMap(fid) : getBigKeyByFid(fid);
            throw OracleJsonExceptions.DUPLICATE_KEY.create(getExceptionFactory(), key);
          }
        }
      }
    }

    private String getBigKeyByFid(int fid) {
      int fieldId = -1;
      for (int k = 0; k < fidMap.length; k++) {
        if (fidMap[k] == fid) {
          fieldId = k;
          break;
        }
      }
      for (Entry<BigKey, Integer> k : bigKeys.entrySet()) {
        if (k.getValue() == fieldId) {
          return new String(k.getKey().key);
        }
      }
      return "";
    }
    
    private void initTemporaryIntArray(int ct) {
      if (temporaryIntArray == null || temporaryIntArray.length < ct) {
        temporaryIntArray = new int[ct];
      }
    }
    
    private void computeOffsets() {
      if (offsets == null || numOps > offsets.length) {
        offsets = new int[numOps];
      }
      int offset = 0;
      tinyNodeCount = 0;

      /** Try with UB2 child offsets (should be sufficient most of the time) */
      for (int i = 0; i < numOps; i++) {
        
        if (isShared(i)) { // this op is replaced by an earlier one that is the same
          offsets[i] = offsets[-numChildren[i]];
          continue;
        }
        
        offsets[i] = offset;
        
        if (isFirstChildObjectOfArray(i)) {
          tryFieldIdSharing(i);
        }
        int size = sizeOfOp(i, 2);
        countTiny(i, size);
        
        offset += size;
        if (offset >= UB2_MAXSZ) {
          offset = -1;
          break;
        }
      }
       
      if (offset != -1) {
        treeSegmentSize = offset;
        return;
      }
      
      /** Try again, but this time use UB4 */
      offset = 0;
      tinyNodeCount = 0;
      for (int i = 0; i < numOps; i++) {
        
        if (isShared(i)) { // this op is replaced by an earlier one that is the same
          offsets[i] = offsets[-numChildren[i]];
          continue;
        }
        
        offsets[i] = offset;
        
        if (isFirstChildObjectOfArray(i)) {
          tryFieldIdSharing(i);
        }
        
        int size = sizeOfOp(i, 4);
        countTiny(i, size);
        offset += size;
        if (offset < 0) {
          throw OracleJsonExceptions.IMAGE_TOO_BIG.create(getExceptionFactory());
        }
      }
      treeSegmentSize = offset;
      return;
    }

    private boolean isShared(int opIndex) {
      return numChildren[opIndex] < 0;
    }

    private void countTiny(int i, int size) {
      if (isStructure(ops[i])) {
        if (size < OsonConstants.JZNOCT_UPD_UB4_FWA_SZ 
            || (isObject(ops[i]) && isReferredTo(ops[i]))) {
          tinyNodeCount++;
        }
      }
    }
    
    private boolean isFirstChildObjectOfArray(int i) {
      return isObject(ops[i]) && i > 0 && 
           firstChild(i-1) == i && isArray(ops[i-1]);
    }

    private int sizeOfOp(int index, int offsetSize) {
      int op = ops[index];
      if (isShared(index)) {
        return 0;
      }
      
      if (isArray(op)) {
        int numOfChildren = numChildren[index];
        int bytesForNumChildren = bytesForNum(numOfChildren);
        int size = 1 + bytesForNumChildren;
        size += (offsetSize * numOfChildren);
        return size;
      } else if (isObject(op)) {
        int numOfChildren = numChildren[index];
        if (sharesFields(op)) {
          return 1 + offsetSize + (numOfChildren * offsetSize);
        }
        
        int bytesForNumChildren = bytesForNum(numOfChildren);
        int fidArraySize = numOfChildren;
        if (distinctKeysSize >= UB2_MAXSZ) {
          fidArraySize *= 4;
        } else if (distinctKeysSize >= UB1_MAXSZ) {
          fidArraySize *= 2;
        }
        int size = 1 +  // for op code byte
            bytesForNumChildren + fidArraySize;
        size += numOfChildren * offsetSize;
        return size;
      } else if (op <= JZNOCT_STR_OK_5BITS) { // <1>
        return 1 + op;
      } else if (isSB4(op) || 
          isSB8(op) ||
          isOraNum16(op) ||
          isDec_16(op)) {
        return 1 + numChildren[index];
      }
      
      switch (op) {
      case JZNOCT_JBOOLT_C:
        return 1;
      case JZNOCT_JBOOLF_C:
        return 1;
      case JZNOCT_JNULL_C:
        return 1; 
      case JZNOCT_JSUB1L_C: {
        return 2 + numChildren[index];
      }
      case JZNOCT_JSUB2L_C: {
        return 3 + numChildren[index];
      }
      case JZNOCT_JSUB4L_C: {
        return 5 + numChildren[index];
      }
      case JZNOCT_JORA_DTYNUM_DEC_C:
      case JZNOCT_JORA_DTYNUM_C: {
        return 2 + numChildren[index];
      }
      case JZNOCT_JDTYDB_C:
        return 9; // 1op + 8
      case JZNOCT_JDTYFLT_C:
        return 5;
      case JZNOCT_JBINUB2L_C:
        return 3 + numChildren[index];
      case JZNOCT_JBINUB4L_C: 
        return 5 + numChildren[index];
      case JZNOCT_JDTYGENID_C: 
        return 2 + numChildren[index];
      case JZNOCT_JDTYSTAMP_C:
        return OsonPrimitiveConversions.SIZE_TIMESTAMP + 1;
      case JZNOCT_JDTYSTAMP7_C:
        return OsonPrimitiveConversions.SIZE_TIMESTAMP_NOFRAC + 1;
      case JZNOCT_JDTYSTAMP_TZ_C:
        return OsonPrimitiveConversions.SIZE_TIMESTAMPTZ + 1;
      case JZNOCT_JDTYDATE_C:
        return OsonPrimitiveConversions.SIZE_DATE + 1;
      case JZNOCT_JDTYDS_C:
        return OracleJsonIntervalDSImpl.INTERVALDS_LEN + 1;
      case JZNOCT_JDTYYM_C:
        return OracleJsonIntervalYMImpl.INTERVALYM_LEN + 1;
      case JZNOCT_JSNUM_C:
        // size of 0 in this case means the number is shared
        int size = numChildren[index];
        return size == 0 ? 0 : (1 + 1 + size);
      case JZNOCT_JVECTOR:
        return 6 + numChildren[index];
      default:
        throw new UnsupportedOperationException(String.valueOf(op));
      }    
    }
    
    private int bytesForNum(int i) {
      if (i < UB1_MAXSZ) {
        return 1;
      } else if (i < UB2_MAXSZ) {
        return 2;
      } 
      return 4;
    }
    
    private int flagObject(int op, int numChildren) {
      if (numChildren <= JZNOCT_BIN_SRCH_TRIG_LIMIT) {
        return flagObjectOrArray(op, numChildren) | OPCODE_CHILD_NO_SORT_BIT; 
      } 
      return flagObjectOrArray(op, numChildren);
    }
    
    private int flagObjectOrArray(int op, int numChildren) {
      if (numChildren >= UB1_MAXSZ) {
        if (numChildren < UB2_MAXSZ) {
          op |= 0x8;
        } else {
          op |= 0x10;
        }
      } 
    
      if (treeSegmentSize > UB2_MAXSZ) {
        op |= 0x20;
      }
      return op;
    }

    private void writeUb2Array(int[] arr, int len) throws IOException {
      for (int i = 0; i < len; i++) {
        writeShort(arr[i]);
      }
    }
    
    private void writeUb4Array(int[] arr, int len) throws IOException {
      for (int i = 0; i < len; i++) {
        writeInt(arr[i]);
      }
    }  
    
    private final void writeInt(int value) throws IOException {
      if (outBufferPos + 3 >= outBuffer.length) {
        flushBuffer();
      }
      outBuffer[outBufferPos++] = (byte)((value >>> 24) & 0xFF);
      outBuffer[outBufferPos++] = (byte)((value >>> 16) & 0xFF);
      outBuffer[outBufferPos++] = (byte)((value >>> 8)  & 0xFF);
      outBuffer[outBufferPos++] = (byte)((value >>> 0)  & 0xFF);
    }
    
    private final void writeShort(int value) throws IOException {
      if (outBufferPos + 1 >= outBuffer.length) {
        flushBuffer();
      }
      outBuffer[outBufferPos++] = (byte)((value >>> 8) & 0xFF);
      outBuffer[outBufferPos++] = (byte)((value >>> 0) & 0xFF);
    }
    
    private final void writeByte(int b) throws IOException {
      if (outBufferPos >= outBuffer.length) {
        flushBuffer();
      }
      outBuffer[outBufferPos++] = (byte)b;
    }
    
    private void flushBuffer() throws IOException {
      out.write(outBuffer, 0, outBufferPos);
      outBufferPos = 0;
    }
    
    private final void write(byte[] bytes, int start, int len) throws IOException {
      if (outBufferPos + len > outBuffer.length) {
        flushBuffer();
        if (len >= outBuffer.length) {
          out.write(bytes, start, len);
          return;
        }
      }
      System.arraycopy(bytes, start, outBuffer, outBufferPos, len);
      outBufferPos += len;
    }
    
    private final void writeOpAndData(int op, byte[] bytes, int start, int len) throws IOException {
      if (outBufferPos + (len + 1) > outBuffer.length) {
        flushBuffer();
        if ((len + 1) >= outBuffer.length) {
          out.write(op);
          out.write(bytes, start, len);
          return;
        }
      }
      outBuffer[outBufferPos++] = (byte)op;
      System.arraycopy(bytes, start, outBuffer, outBufferPos, len);
      outBufferPos += len;
    }

    private void reset(OutputStream out) {
      this.out = out;
      valueHeapSize = 0;
      numOps = 0;
      distinctKeysSize = 0;
      seenHashSize = 0;
      headerFlags = JZNOCT2_SLEN_IN_PCODE| JZNOCT2_INLINE_LEAF | JZNOCT_TINY_NODE_STAT;
      bigKeys = null;
      bigKeysHeapSize = 0;
      setUseRelativeOffsets(DEFAULT_RELATIVE_OFFSETS);
      setTinyNodeStat(DEFAULT_TINYNODE);
      setSimpleValueSharing(DEFAULT_SIMPLE_VALUE_SHARING);
      setLastValueSharing(DEFAULT_LAST_VALUE_SHARING);
      
      if (keysNeedReset) {
        // In a normal cycle, this data structure gets reset as it is consumed
        for (int i= 0; i < keys.length; i++) {
          for (int j = 0; 
              keys[i] != null && 
              j < keys[i].length && 
              keys[i][j] != 0; j++) {
            keys[i][j] = 0;
            if (lastValueSharing)
              keysLastSeenValue[i][j] = 0;

          }
        }
      }
      opLastValue = -1;
      keysNeedReset = true;
      depth = 0;
      outBufferPos = 0;
      tinyNodeCount = 0;
      duplicateKeyMode = DEFAULT_DUPLICATE_KEY_MODE;
      opTrue = opFalse = opNull = opZero = opOne = opEmptyString = opEmptyObject = opEmptyArray = -1;
      keyI = keyJ = -1;
      
      ctx.init();
      ctx.setExceptionFactory(getExceptionFactory());
    }
    


    private void initKeysLastSeenValue(int i) {
      if (keysLastSeenValue == null)
        keysLastSeenValue = new int[256][];
      
      if (keysLastSeenValue[i] == null) {
        keysLastSeenValue[i] = new int[keys[i].length];
      } else if (keysLastSeenValue[i].length < keys[i].length) {
        keysLastSeenValue[i] = Arrays.copyOf(keysLastSeenValue[i], keys[i].length);
      }
    }

    public void setTinyNodeStat(boolean value) {
      if (value)
        headerFlags |= JZNOCT_TINY_NODE_STAT;
      else
        headerFlags &= ~JZNOCT_TINY_NODE_STAT;
    }
    
    public void setUseRelativeOffsets(boolean value) {
      relativeOffsets = value;
    }
    
    public void setSimpleValueSharing(boolean value) {
      simpleValueSharing = value;
    }
    
    public void setLastValueSharing(boolean value) {
      lastValueSharing = value;
    }
    
    private void writeTimestamp(byte[] raw) {
      if (raw.length == OsonPrimitiveConversions.SIZE_TIMESTAMP)
        fixedBinary(JZNOCT_JDTYSTAMP_C, raw.length, raw);
      else
        fixedBinary(JZNOCT_JDTYSTAMP7_C, raw.length, raw);
    }

    public void writeTimestampTZ(byte[] raw) {
      OsonPrimitiveConversions.assertNoRegionTimestampTZ(getExceptionFactory(), raw);
      fixedBinary(JZNOCT_JDTYSTAMP_TZ_C, raw.length, raw);
    }

    private void writeDate(byte[] raw) {
      fixedBinary(JZNOCT_JDTYDATE_C, OsonPrimitiveConversions.SIZE_DATE, raw);
    }

    private void writeIntervalYM(byte[] raw) {
      fixedBinary(JZNOCT_JDTYYM_C, OracleJsonIntervalYMImpl.INTERVALYM_LEN, raw);
    }

    private void writeIntervalDS(byte[] raw) {
      fixedBinary(JZNOCT_JDTYDS_C, OracleJsonIntervalDSImpl.INTERVALDS_LEN, raw);
    }

    public void writeVector(byte[] raw) {
      fixedBinary(JZNOCT_JVECTOR, raw.length, raw);
    }
    
    private void fixedBinary(int op, int len, byte[] bytes) {
      if (len != bytes.length) {
        throw new IllegalArgumentException();
      }
      addOpAndValue(op, bytes);
    }

    public void close() {
      ctx.close();
      try {
        
        initializeKeyHeap();
        
        /* Populate offsets array and set treeSegmentSize */
        computeOffsets();
        
        writeHeader();
        writeNameDictionary();
        if (bigKeys != null) {
          writeNameDictionary2();
        }
        writeTreeNodeSegment();
        flushBuffer();
        out.close();
      } catch (IOException e) {
        throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
      }
      //Arrays.fill(valueHeap, 0, valueHeapSize, (byte)0);
      //Arrays.fill(outBuffer, (byte)0);
    }
    
    private void writeString(String value) {
      preOp();      
      expandValueHeap(value.length()*4);
      valueIndex[numOps] = valueHeapSize;
      int newPos = writeString(value, valueHeap, valueHeapSize);
      int len = newPos - valueHeapSize;
      valueHeapSize = newPos; 
      writeStringOp(newPos, len);
      // we don't know the string size until encoding it in this case
      // but we can still mark it as a duplicate to save space in the
      // encoded image
      boolean duplicate = checkStringDuplicate(len);
      postOp(!duplicate);
    }

    private boolean checkStringDuplicate(int len) {
      if (lastValueSharing && opLastValue != -1 && 
          ops[numOps-1] == ops[opLastValue] &&
          numChildren[opLastValue] == len && 
          equals(valueHeap, valueIndex[numOps-1], valueHeap, valueIndex[opLastValue], len)) {
        markDuplicate(numOps-1, opLastValue);
        headerFlags |= JZNOCT2_SHR_NODES;
        return true;
      } else if (len == 0 && simpleValueSharing) {
        if (opEmptyString == -1) {
          opEmptyString = numOps - 1; 
        } else {
          headerFlags |= JZNOCT2_SHR_SIMP_NODES;
          markDuplicate(numOps-1, opEmptyString);
        }
        return true;
      }
      return false;
    }

    private void writeUTF8String(byte[] array, int offset, int len) {
      preOp();
      expandValueHeap(len);
      valueIndex[numOps] = valueHeapSize;
      int newPos = writeUTF8String(array, offset, len, valueHeap, valueHeapSize);
      valueHeapSize = newPos; 
      writeStringOp(newPos, len);
      checkStringDuplicate(len);
      postOp(true);
    }

    private void writeStringOp(int newPos, int len) {
      if (len <= JZNOCT_STR_OK_5BITS) {
        addOp(len);
      } else if (len < UB1_MAXSZ) {
        addOp(JZNOCT_JSUB1L_C);
      } else if (len < UB2_MAXSZ) {
        addOp(JZNOCT_JSUB2L_C);
      } else {
        addOp(JZNOCT_JSUB4L_C);
      }
      numChildren[numOps-1] = len;
      ctx.primitive();
    }

    private void writeStartObject() {
      preOp();
      addOp(JZNOCT_OBJECT_TYP);
      push(numOps-1);
      ctx.startObject();
      postOp(false);
    }

    private void writeStartObjectNoSort() {
      preOp();
      addOp(JZNOCT_OBJECT_TYP | OPCODE_CHILD_NO_SORT_BIT);
      push(numOps-1);
      ctx.startObject();
      postOp(false);
    }

    private void writeStartArray() {
      preOp();
      addOp(JZNOCT_ARRAY_TYP);
      push(numOps -1);
      ctx.startArray();
      postOp(false);
    }

    public void writeEnd() {
      ctx.end();
      depth--;
      previousSiblingIdx = opStack[depth];
      
      /** Check for empty object or empty array */
      if (simpleValueSharing && numChildren[opStack[depth]] == 0) {
        int index = opStack[depth];
        if (isArray(ops[index])) {
          if (opEmptyArray == -1) {
            opEmptyArray = index;
          } else {
            headerFlags |= JZNOCT2_SHR_SIMP_NODES;
            markDuplicate(index, opEmptyArray);
          }
        } else {
          if (opEmptyObject == -1) {
            opEmptyObject = index;
          } else {
            headerFlags |= JZNOCT2_SHR_SIMP_NODES;
            markDuplicate(index, opEmptyObject);
          }
        }
      }

    }

    private void writeDouble(double value) {
      byte[] bytes = OsonPrimitiveConversions.doubleToCanonicalFormatBytes(value);
      this.addOpAndValue(OsonConstants.JZNOCT_JDTYDB_C, bytes);
    }
    
    private void writeBoolean(boolean value) {
      preOp();
      if (value) {
        addOp(JZNOCT_JBOOLT_C);
        if (simpleValueSharing) {
          if (opTrue == -1) {
            opTrue = numOps -1;
          } else { 
            headerFlags |= JZNOCT2_SHR_SIMP_NODES;
            markDuplicate(numOps - 1, opTrue);
          }
        }
        
      } else {
        addOp(JZNOCT_JBOOLF_C);
        if (simpleValueSharing) {
          if (opFalse == -1) {
            opFalse = numOps -1;
          } else {
            headerFlags |= JZNOCT2_SHR_SIMP_NODES;
            markDuplicate(numOps - 1, opFalse);
          }
        }
      }
      ctx.primitive();
      postOp(true);
    }
  
    private void writeOraNumber(OracleJsonDecimal value) {
      OracleJsonDecimalImpl impl = (OracleJsonDecimalImpl)value;
      if (impl.isDec()) {
        writeDecimal(impl.bigDecimalValue());
      } else if (impl.isSB4()) {
        writeSB4(impl.intValue());
      } else if (impl.isSB8()) {
        writeSB8(impl.longValue());
      } else {
        writeNumber(impl.raw());
      }      
    }
    
    private void writeDecimal(BigDecimal value) {
      byte[] bytes = OsonPrimitiveConversions.toNumber(value);
      writeDecimal(bytes);
    }
    
    private void writeDecimal(byte[] bytes) {
      if (bytes.length <= JZNOCT_ORANUM_OK_4BITS) {
        addOpAndValue((bytes.length -1) | MASK_DEC_16, bytes);
      } else if (bytes.length < UB1_MAXSZ) {
        addOpAndValue(JZNOCT_JORA_DTYNUM_DEC_C, bytes);
      }
    }
    
    private void writeDecimal(BigInteger value) {
      writeDecimal(OsonPrimitiveConversions.toNumber(value));
    }

    private void writeSB4(int value) {
      byte[] raw = OsonPrimitiveConversions.toNumber(value);
      int op = raw.length | MASK_SB4;
      addOpAndValue(op, raw);
    }


    private void markDuplicate(int index, int replacingIndex) {
      this.numChildren[index] = -replacingIndex;
    }
    
    private boolean tryMarkDuplicate(int op, byte[] bytes) {
      if (lastValueSharing && opLastValue != -1 && 
          op == ops[opLastValue] &&
          numChildren[opLastValue] == bytes.length && 
          equals(bytes, 0, valueHeap, valueIndex[opLastValue], bytes.length)) {
        markDuplicate(numOps, opLastValue);
        headerFlags |= JZNOCT2_SHR_NODES;
        return true;
      }
      return false;
    }
    
    private void addOpAndValue(int op, byte[] raw) {
      addOpAndValueNoPostOp(op, raw);
      postOp(true);
    }

    private void addOpAndValueNoPostOp(int op, byte[] raw) {
      preOp();
      if (!tryMarkDuplicate(op, raw)) {
        addValue(raw);
        numChildren[numOps] = raw.length;
      }
      addOp(op);
      ctx.primitive();
    }

    public void writeSB8(long value) {
      byte[] raw = OsonPrimitiveConversions.toNumber(value);
      addOpAndValue(raw.length | MASK_SB8, raw);
    }

    private void writeNumberAsString(BigDecimal bd) {
      byte[] bytes = bd.toString().getBytes(StandardCharsets.UTF_8);
      if (bytes.length > OsonConstants.UB1_MAXSZ) {
        throw new IllegalArgumentException();
      }
      addOpAndValue(JZNOCT_JSNUM_C, bytes);
    }
    
    private void writeBytes(byte[] bytes) {
      int op = bytes.length < UB2_MAXSZ ? JZNOCT_JBINUB2L_C : JZNOCT_JBINUB4L_C;
      addOpAndValue(op, bytes);
    }
    
    protected void writeId(byte[] bytes) {
      if (bytes.length > 16) {
        throw new UnsupportedOperationException();
      }
      addOpAndValue(JZNOCT_JDTYGENID_C, bytes);
    }

    private void writeFloat(float value) {
      byte[] bytes = OsonPrimitiveConversions.floatToCanonicalFormatBytes(value);
      addOpAndValue(JZNOCT_JDTYFLT_C, bytes);
    }

    private void writeKey(String key) {
      ctx.pendingKey();
      if (numOps >= ops.length) {
        expandOp();
      }
      
      keyI = OsonHeader.ohash(key, keylen);
      if (keylen.get() > OsonConstants.MAX_SMALL_KEY_LENGTH) {
        if (keylen.get() > OsonConstants.MAX_BIG_KEY_LENGTH) {
          throw OracleJsonExceptions.KEY_TOO_LONG.create(getExceptionFactory());
        }
        if (this.bigKeys == null) {
          bigKeys = new TreeMap<BigKey, Integer>();
        }
        BigKey bigKey = new BigKey(key);
        Integer fid = bigKeys.get(bigKey);
        if (fid == null) {
          fid = bigKeys.size()+1;
          bigKeys.put(bigKey, fid);
        }
        fieldIDs[numOps] = -fid;
        return;
      }

      int[] fids = keys[keyI];
      if (fids == null) {
        fids = keys[keyI] = new int[2];
      }
      
      keyJ = 0;
      for (; keyJ < fids.length; keyJ++) {
        final int fid = fids[keyJ]-1;
        if (fid == -1) {
          break;
        }
        if (distinctKeys[fid].equals(key)) {
          fieldIDs[numOps] = fid;
          if (lastValueSharing)
            opLastValue = keysLastSeenValue[keyI][keyJ];
          return;
        }
      }
      if (keyJ >= fids.length) {
        fids = keys[keyI] = Arrays.copyOf(keys[keyI], keys[keyI].length*2);
      } else if (keyJ == 0 && seenHashSize < SEEN_HASH_THRESHOLD) {
        // for small documents avoid iterating full 256 hashes later
        seenHash[seenHashSize++] = keyI;
      }
      if (distinctKeysSize+1 >= distinctKeys.length) {
        distinctKeys = Arrays.copyOf(distinctKeys, distinctKeys.length*2);
      }
      fieldIDs[numOps] = distinctKeysSize;
      distinctKeys[distinctKeysSize++] = key;
      fids[keyJ] = distinctKeysSize;
    }
    
    private void writeNull() {
      preOp();
      addOp(JZNOCT_JNULL_C);
      if (simpleValueSharing) {
        if (opNull == -1) {
          opNull = numOps -1;
        } else {
          headerFlags |= JZNOCT2_SHR_SIMP_NODES;
          markDuplicate(numOps - 1, opNull);
        }
      }
      ctx.primitive();
      postOp(true);
    }
  }
  
  private static class BigKey implements Comparable<BigKey> {
    /** key as utf8 */
    byte[] key;
    
    int hash;
    
    public BigKey(String key) {
      this.key = key.getBytes(StandardCharsets.UTF_8);
      this.hash = OsonHeader.ohash(key, null);
    }

    @Override
    public int compareTo(BigKey o) {
      if (o.hash != hash) {
        return hash - o.hash;
      }
      if (key.length != o.key.length)
        return key.length - o.key.length;
      for (int i = 0; i < key.length; i++) {
        int res = Byte.compare(key[i], o.key[i]);
        if (res == 0) {
          continue;
        }
        return res;
      }
      return 0;
    }
  }

  public static final class OsonGeneratorStatePool {
    
    private volatile WeakReference<ConcurrentLinkedQueue<OsonGeneratorState>> queue;
    
    private OsonGeneratorState getState(OutputStream out) {
      ConcurrentLinkedQueue<OsonGeneratorState> list = getQueue();
      OsonGeneratorState result = null;
      if (list != null) {
        result = list.poll();
      }
      if (result == null) {
        result = new OsonGeneratorState(this, out);
      }
      return result;
    }
    
    private void putState(OsonGeneratorState state) {
      ConcurrentLinkedQueue<OsonGeneratorState> list = getQueue();
      if (list == null) {
        list = new ConcurrentLinkedQueue<OsonGeneratorState>();
        list.offer(state);
        queue = new WeakReference<ConcurrentLinkedQueue<OsonGeneratorState>>(list);
      } else {
        list.offer(state);
      }
    }
    
    private ConcurrentLinkedQueue<OsonGeneratorState> getQueue() {
      WeakReference<ConcurrentLinkedQueue<OsonGeneratorState>> queue = this.queue;
      return queue == null ? null : queue.get();
    }
  }
    
  private OsonGeneratorState state;
  
  public OsonGeneratorImpl(OsonGeneratorStatePool pool, OutputStream out) {
    if (pool != null) {
      this.state = pool.getState(out);
    } else {
      this.state = new OsonGeneratorState(null, out);
    }
    this.state.reset(out);
  }

  public void reset(OutputStream out) {
    state.reset(out);
  }
  
  public void setTinyNodeStat(boolean value) {
    state.setTinyNodeStat(value);
  }
  
  public void setUseRelativeOffsets(boolean value) {
    state.setUseRelativeOffsets(value);
  }
  
  public void setSimpleValueSharing(boolean value) {
    state.setSimpleValueSharing(value);
  }
  
  public void setLastValueSharing(boolean value) {
    state.setLastValueSharing(value);
  }
  
  public boolean getLastValueSharing() {
    return this.state.lastValueSharing;
  }
  
  public boolean getSimpleValuesharing() {
    return this.state.simpleValueSharing;
  }
  
  public boolean getRelativeOffsets() {
    return this.state.relativeOffsets;
  }
  
  @Override
  public OracleJsonGenerator writeStartObject() {
    state.writeStartObject();
    return this;
  }

  /** 
   * Internal - used only by Saturn.  When sort is false, 
   * fid array will not be sorted.  When sort is true, 
   * fid array *may* be sorted.
   */
  public OracleJsonGenerator writeStartObject(boolean sort) {
    if (sort)
      state.writeStartObject();
    else
      state.writeStartObjectNoSort();
    return this;
  }

  @Override
  public OracleJsonGenerator writeKey(String key) {
    state.writeKey(key);
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeStartArray() {
    state.writeStartArray();
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeEnd() {
    state.writeEnd();
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(String value) {
    state.writeString(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(BigDecimal value) {  
    state.writeDecimal(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(BigInteger value) {
    state.writeDecimal(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(int value) {
    state.writeSB4(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(long value) {
    state.writeSB8(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(double value) {
    state.writeDouble(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(boolean value) {
    state.writeBoolean(value);    
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeNull() {
    state.writeNull();
    return this;
  }
  
  @Override
  public void close() {
    if (state != null) {
      state.close();
      if (state.pool != null) {
        state.pool.putState(state);
      }
      state = null;
    }
  }
  
  /// OracleJsonGenerator methods

  public OracleJsonGenerator write(String key, byte[] value) {
    writeKey(key);
    write(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(String key, LocalDateTime value) {
    writeKey(key);
    write(value);
    return this;
  }

  @Override
  public OracleJsonGenerator write(String key, OffsetDateTime value) {
    writeKey(key);
    write(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(byte[] bytes) {
    state.writeBytes(bytes);
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeId(byte[] bytes) {
    state.writeId(bytes);
    return this;
  }

  @Override
  public OracleJsonGenerator write(float value) {
    state.writeFloat(value);
    return this;
  }

  @Override
  public OracleJsonGenerator write(LocalDateTime local) {
    byte[] bytes = OsonPrimitiveConversions.toOracleTimestamp(state.getExceptionFactory(), local);
    state.writeTimestamp(bytes);
    return this;
  }

  @Override
  public OracleJsonGenerator write(OffsetDateTime offset) {
    byte[] bytes = OsonPrimitiveConversions.toOracleTimestampTZ(state.getExceptionFactory(), offset);
    state.writeTimestampTZ(bytes);
    return this;
  }

  public OracleJsonGenerator writeIntervalDS(Duration value) {
    byte[] bytes = OsonPrimitiveConversions.durationToIntervalDS(value);
    state.writeIntervalDS(bytes);
    return this;
  }
  
  public OracleJsonGenerator writeIntervalYM(Period value) {
    byte[] bytes = OsonPrimitiveConversions.periodToIntervalYM(state.getExceptionFactory(), value);
    state.writeIntervalYM(bytes);
    return this;
  }
  
  public OracleJsonGenerator writeNumberAsString(BigDecimal bd) {
    state.writeNumberAsString(bd);
    return this;
  }

  /// specialized write methods for write(OracleJsonValue)
  
  @Override
  protected OracleJsonGenerator writeBinary(OracleJsonBinary value) {
    byte[] bytes = value.getBytes();
    if (value.isId()) {
      state.writeId(bytes);
    } else {
      state.writeBytes(bytes);
    }
    return this;
  }

  protected OracleJsonGenerator writeDouble(OracleJsonDouble value) {
    return write(value.doubleValue());
  }
  
  protected OracleJsonGenerator writeFloat(OracleJsonFloat value) {
    return write(value.floatValue());
  }

  public void writeDecimal(BigDecimal value) {
    state.writeDecimal(value);
  }

  public void writeSB4(int value) {
    state.writeSB4(value);
  }
  
  public void writeSB8(long value) {
    state.writeSB8(value);
  }  
  
  @Override
  protected OracleJsonGenerator writeOraNumber(OracleJsonDecimal value) {
    state.writeOraNumber(value);
    return this;
  }

  @Override
  protected OracleJsonGenerator writeTimestamp(OracleJsonTimestamp value) {
    state.writeTimestamp(((OracleJsonTimestampImpl)value).raw());
    return this;
  }

  @Override
  protected OracleJsonGenerator writeTimestampTZ(OracleJsonTimestampTZ value) {
    state.writeTimestampTZ(((OracleJsonTimestampTZImpl)value).raw());
    return this;
  }
  
  @Override
  protected OracleJsonGenerator writeDate(OracleJsonDate value) {
    state.writeDate(((OracleJsonDateImpl)value).raw());
    return this;
  }
  
  @Override
  protected OracleJsonGenerator writeIntervalDS(OracleJsonIntervalDS value) {
    state.writeIntervalDS(((OracleJsonIntervalDSImpl)value).raw());
    return this;
  }  
  
  @Override
  protected OracleJsonGenerator writeIntervalYM(OracleJsonIntervalYM value) {
    state.writeIntervalYM(((OracleJsonIntervalYMImpl)value).raw());
    return this;
  }
  @Override
  protected OracleJsonGenerator writeVector(OracleJsonVector value) {
    state.writeVector(((OracleJsonVectorImpl)value).raw());
    return this;
  }
  /** This could be optimized to avoid UTF8 -> UTF16 conversion */
  @Override
  protected OracleJsonGenerator writeString(OracleJsonString value) {
    return write(value.getString());
  }

  @Override
  public void flush() {
    // do nothing
  }

  @Override
  public OracleJsonGenerator write(Period p) {
    state.writeIntervalYM(OsonPrimitiveConversions.periodToIntervalYM(state.getExceptionFactory(), p));
    return this;
  }

  @Override
  public OracleJsonGenerator write(Duration d) {
    state.writeIntervalDS(OsonPrimitiveConversions.durationToIntervalDS(d));
    return this;
  }
  
  @Override
  protected void writeStringFromParser(OracleJsonParser parser) {
    if (parser instanceof OsonParserImpl) {
      OsonParserImpl oparser = (OsonParserImpl)parser;
      byte[] arr = oparser.getContext().b.buffer.array();
      state.writeUTF8String(arr, oparser.getCurrentStringPos(), oparser.getCurrentStringLen());
    } else {
      state.writeString(parser.getString());
    }
  }

  @Override
  protected void writeDecimalFromParser(OracleJsonParser parser) {
    // This could be optimized to avoid transient object creation
    // in the case of OsonParserImpl
    write(parser.getValue());
  }

  /** not part of public api */
  public void setDuplicateKeyMode(DuplicateKeyMode mode) {
    state.duplicateKeyMode = mode;
  }

  static {
    String modeStr = System.getProperty("oracle.jdbc.driver.json.binary.OsonGeneratorImpl.DEFAULT_DUPLICATE_KEY_MODE");
    DuplicateKeyMode mode = null;
    if (modeStr == null) {
      mode = DuplicateKeyMode.DISALLOW;
    } else {
      mode = DuplicateKeyMode.valueOf(modeStr);
    }
    DEFAULT_DUPLICATE_KEY_MODE = mode;
  }

}

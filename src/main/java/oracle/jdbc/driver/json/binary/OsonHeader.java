// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_INLINE_LEAF;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_J_SCALAR;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_REL_OFFSET;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_SLEN_IN_PCODE;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT2_TOT_DISFNM_UB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT3_FLDNM2_SZ_UB2;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_FID_NO_SORT;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_FLDNM_SZ_UB4;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_TINY_NODE_STAT;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_TOT_DISFNM_UB2;
import static oracle.jdbc.driver.json.binary.OsonConstants.JZNOCT_TREE_SZ_UB4;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;

/**
 * Summary of image:
 * 
 * [header] [tree segment] [update header] [overflow-addr-mapping] [extended tree segment]
 * [^ version 1,2 ^^^^^^^] [^ only in version 2 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^]
 * 
 * The header segment also contains the unique field dictionaries - one for fields <= 255
 * bytes and one for fields >= 256 bytes.  The second dictionary can only be present if
 * the image version is 3 or greater.
 * 
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonHeader {

  /** Not intended to be public */
  private static final boolean ENABLE_DICTIONARY_VALIDATION =
      Boolean.parseBoolean(
          System.getProperty(
              "oracle.sql.json.OracleJsonFactory.ENABLE_DICTIONARY_VALIDATION",
              "true"));
  
  /** UTF8_MAPPING[i] points to an array of bytes that are the UTF8 */
  private static byte[][] UTF8_MAPPING;
  
  /** OSON format version */
  int version;
  
  /** V1 or higher JZNOCT* and JZNOCT2* */
  short flags;
  
  /** V3 or higher. JZNOCT3* and JZNOCT4* */
  short flags2;

  private int uniqueFields;
  /** parallel fieldNames - for fields <= 255 in length.  UB1 hash. */ 
  private int[] hashIdArray;
  /** parallel hashIdArray */ 
  private String[] fieldNames;
  private int fieldHeapSize;
  
  private int uniqueFields2;
  /** parallel fieldNames2 - for fields > 255 in length.  UB2 hash. */ 
  private int[] hashIdArray2;
  /** parallel hashIdArray2 */ 
  private String[] fieldNames2;
  private int fieldHeapSize2;
  
  int ubFieldId;

  int treeSegmentSize;
  int treeSegmentOffset;
  int tinyNodeCount;
  
  /** update fields (oson v2) */
  int updateFlags;
  int extendedTreeSegmentOffset;
  int extendedTreeSegmentSize;
  Map<Integer, Integer> forwardingAddress;
  
  public OsonHeader(OsonBuffer b, ExceptionFactory f) {
    readHeader(b, f);
    treeSegmentOffset = b.position();
    if (isScalar()) {
      validateTreeSegment(b, f);
      return;
    }
    
    if (ENABLE_DICTIONARY_VALIDATION) {
      validateDictionaryBudget(
        b,
        f,
        uniqueFields,
        1,
        isSet(JZNOCT_FLDNM_SZ_UB4) ? 4 : 2,
        fieldHeapSize,
        1);
    }
    if (uniqueFields > 0) {
      readDictionary(b, f);
    }
    
    if (ENABLE_DICTIONARY_VALIDATION) {
      validateDictionaryBudget(
        b,
        f,
        uniqueFields2,
        2,
        isSet2(JZNOCT3_FLDNM2_SZ_UB2) ? 2 : 4,
        fieldHeapSize2,
        2);
    }
    if (uniqueFields2 > 0) {
      readDictionary2(b, f);
    }

    validateTreeSegment(b, f);

    if (!isSet(JZNOCT2_INLINE_LEAF)) {
      throw new UnsupportedOperationException();
    }

    // update header
    long updateHeaderOffset = (long)treeSegmentOffset + treeSegmentSize;
    if (updateHeaderOffset < b.buffer().limit()) {
      b.position((int)updateHeaderOffset);
      readUpdateHeader(b, f);
    }
  }

  public void readHeader(OsonBuffer b, ExceptionFactory f) {
    // sanity check to mainly catch the empty buffer case
    if (b.buffer().remaining() <= 6) { 
      throw OracleJsonExceptions.CORRUPT2.create(f, b.buffer().remaining());
    }
    
    int magicAndVersion = b.getInt();
    if ((magicAndVersion & 0xff_ff_ff_00) != OsonConstants.MAGIC) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
    
    version = magicAndVersion & 0x00_00_00_ff;
    if (version < 1 || version > 4) {
      throw OracleJsonExceptions.UNSUPPORTED_VERSION.create(f, version);
    }
    
    flags = (short)b.getUB2();
    
    if (!isSet(JZNOCT2_J_SCALAR)) {
      if (isSet(JZNOCT2_TOT_DISFNM_UB4)) {
        ubFieldId = 4;
        uniqueFields = b.getUB4int();
      } else if (isSet(JZNOCT_TOT_DISFNM_UB2)) {
        ubFieldId = 2;
        uniqueFields = b.getUB2();
      } else {
        ubFieldId = 1;
        uniqueFields = b.getUB1();
      }
      fieldHeapSize = isSet(JZNOCT_FLDNM_SZ_UB4) ? b.getUB4int() : b.getUB2();
      
      if (version >= 3) {
        // for field names > 255 in length
        flags2 = (short)b.getUB2();
        uniqueFields2 = b.getUB4int();
        fieldHeapSize2 = b.getUB4int();
      }
      treeSegmentSize = isSet(JZNOCT_TREE_SZ_UB4) ? b.getUB4int() : b.getUB2();
      tinyNodeCount = b.getUB2();
    } else {
      treeSegmentSize = isSet(JZNOCT_TREE_SZ_UB4) ? b.getUB4int() : b.getUB2();
    }
  }
  
  private void readDictionary(OsonBuffer b, ExceptionFactory f) {
    int[] fieldNameOffsets = new int[uniqueFields];
    hashIdArray = new int[uniqueFields];
    fieldNames = new String[uniqueFields];
    ub1(b, hashIdArray);
    if (isSet(JZNOCT_FLDNM_SZ_UB4)) {
      ub4int(b, fieldNameOffsets);
    } else {
      ub2(b, fieldNameOffsets);
    }
    int offset = b.position();
    treeSegmentOffset = b.position() + fieldHeapSize;
    for (int i = 0; i < fieldNameOffsets.length; i++) {
      validateDictionaryOffset(f, fieldNameOffsets[i], fieldHeapSize, 1);
      b.position(fieldNameOffsets[i] + offset);
      int len = b.getUB1();
      validateDictionaryEntry(f, fieldNameOffsets[i], fieldHeapSize, 1, len);
      fieldNames[i] = b.readString(len);
    }
    b.position(treeSegmentOffset);
  }

  private void readDictionary2(OsonBuffer b, ExceptionFactory f) {
    int[] fieldNameOffsets2 = new int[uniqueFields2];
    hashIdArray2 = new int[uniqueFields2];
    fieldNames2 = new String[uniqueFields2];
    ub2(b, hashIdArray2);
    if (isSet2(JZNOCT3_FLDNM2_SZ_UB2)) {
      ub2(b, fieldNameOffsets2);
    } else {
      ub4int(b, fieldNameOffsets2);
    }
    int offset = b.position();
    treeSegmentOffset = b.position() + fieldHeapSize2;
    for (int i = 0; i < fieldNameOffsets2.length; i++) {
      validateDictionaryOffset(f, fieldNameOffsets2[i], fieldHeapSize2, 2);
      b.position(fieldNameOffsets2[i] + offset);
      int len = b.getUB2();
      validateDictionaryEntry(f, fieldNameOffsets2[i], fieldHeapSize2, 2, len);
      fieldNames2[i] = b.readString(len);
    }
    b.position(treeSegmentOffset);
  }

  private void validateDictionaryBudget(
      OsonBuffer b,
      ExceptionFactory f,
      int fieldCount,
      int hashIdWidth,
      int offsetWidth,
      int heapSize,
      int minFieldEntrySize) {
    if (fieldCount == 0) {
      if (heapSize != 0) {
        throw OracleJsonExceptions.CORRUPT.create(f);
      }
      return;
    }

    long minHeapBytes = (long)fieldCount * minFieldEntrySize;
    long tableBytes = (long)fieldCount * (hashIdWidth + offsetWidth);
    long totalBytes = tableBytes + heapSize;
    if (heapSize < minHeapBytes || totalBytes > b.buffer().remaining()) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
  }

  private void validateDictionaryOffset(
      ExceptionFactory f,
      int fieldNameOffset,
      int heapSize,
      int lengthWidth) {
    if (fieldNameOffset < 0 || fieldNameOffset > heapSize - lengthWidth) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
  }

  private void validateDictionaryEntry(
      ExceptionFactory f,
      int fieldNameOffset,
      int heapSize,
      int lengthWidth,
      int fieldNameLength) {
    validateDictionaryOffset(f, fieldNameOffset, heapSize, lengthWidth);
    if (fieldNameLength > heapSize - fieldNameOffset - lengthWidth) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
  }

  private void validateTreeSegment(OsonBuffer b, ExceptionFactory f) {
    b.checkRange(treeSegmentOffset, treeSegmentSize, f);
  }

  int checkedNodeOffset(long offset, ExceptionFactory f) {
    if (inSegment(offset, treeSegmentOffset, treeSegmentSize) ||
        inSegment(offset, extendedTreeSegmentOffset, extendedTreeSegmentSize)) {
      return (int)offset;
    }
    throw OracleJsonExceptions.CORRUPT.create(f);
  }

  int checkedTreeRelativeOffset(long relativeOffset, ExceptionFactory f) {
    return checkedRelativeOffset(
        relativeOffset, treeSegmentOffset, treeSegmentSize, f);
  }

  int checkedNodeRelativeToTreeStart(long relativeOffset, ExceptionFactory f) {
    if (relativeOffset < 0) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
    return checkedNodeOffset((long)treeSegmentOffset + relativeOffset, f);
  }

  int checkedExtendedRelativeOffset(long relativeOffset, ExceptionFactory f) {
    return checkedRelativeOffset(
        relativeOffset, extendedTreeSegmentOffset, extendedTreeSegmentSize, f);
  }

  private int checkedRelativeOffset(
      long relativeOffset,
      int segmentOffset,
      int segmentSize,
      ExceptionFactory f) {
    if (relativeOffset < 0 || relativeOffset >= segmentSize) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
    return checkedNodeOffset((long)segmentOffset + relativeOffset, f);
  }

  private boolean inSegment(long offset, int segmentOffset, int segmentSize) {
    return segmentSize > 0 && offset >= segmentOffset &&
        offset < (long)segmentOffset + segmentSize;
  }

  public String getFieldName(int fid) {
    return fid < uniqueFields ?
      fieldNames[fid] : 
      fieldNames2[fid-uniqueFields];
  }
  
  private void readUpdateHeader(OsonBuffer b, ExceptionFactory f) {
    b.checkRemaining(16, f);
    updateFlags = b.getUB2();
    
    // number of mappings in overflow-addr-mapping-segment
    int numOverflowAddrSeg = b.getUB2();
    
    // reserved
    b.getUB4int(); 
    
    // size of overflow-addr-mapping-segment (might have extra space)
    int overflowMappingSize = b.getUB4int(); 
    
    extendedTreeSegmentSize = b.getUB4int();

    int mappingEntryWidth = isSetUpd(OsonConstants.JZNOCTUPDHDR_OVFLW_SEG_UB2) ?
        4 : 8;
    if ((long)numOverflowAddrSeg * mappingEntryWidth > overflowMappingSize) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
    b.checkRange(b.position(), overflowMappingSize, f);
    
    extendedTreeSegmentOffset = b.position() + overflowMappingSize;
    b.checkRange(extendedTreeSegmentOffset, extendedTreeSegmentSize, f);
    
    forwardingAddress = new HashMap<Integer, Integer>();
    if (isSetUpd(OsonConstants.JZNOCTUPDHDR_OVFLW_SEG_UB2)) {
      for (int i = 0; i < numOverflowAddrSeg; i++) {
        int sourceRelativeOffset = b.getUB2();
        int targetRelativeOffset = b.getUB2();
        validateForwardingAddress(f, sourceRelativeOffset, targetRelativeOffset);
        forwardingAddress.put(sourceRelativeOffset, targetRelativeOffset);
      }
    } else {
      for (int i = 0; i < numOverflowAddrSeg; i++) {
        int sourceRelativeOffset = b.getUB4int();
        int targetRelativeOffset = b.getUB4int();
        validateForwardingAddress(f, sourceRelativeOffset, targetRelativeOffset);
        forwardingAddress.put(sourceRelativeOffset, targetRelativeOffset);
      }
    }
  }

  private void validateForwardingAddress(
      ExceptionFactory f,
      int sourceRelativeOffset,
      int targetRelativeOffset) {
    checkedTreeRelativeOffset(sourceRelativeOffset, f);
    checkedExtendedRelativeOffset(targetRelativeOffset, f);
  }
  
  public boolean isScalar() {
    return isSet(JZNOCT2_J_SCALAR);
  }
  
  public boolean isTinyNodeCount() {
    return isSet(JZNOCT_TINY_NODE_STAT);
  }

  private void ub1(OsonBuffer s, int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = s.getUB1();
    }
  }

  private void ub2(OsonBuffer s, int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = s.getUB2();
    }
  }

  private void ub4int(OsonBuffer s, int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = s.getUB4int();
    }
  }

  public int getTreeSegmentOffset() {
    return treeSegmentOffset;
  }

  public int getFieldHeapSize() {
    return fieldHeapSize;
  }

  public String[] getFields() {
    return this.fieldNames;
  }
  
  public int[] hashIds() {
    return this.hashIdArray;
  }
  
  /** JZNOCT or JZNOCT2 flags */
  private boolean isSet(int f) {
    return (flags & f) != 0;
  }
  
  /** JZNOCT3 or JZNOCT4 flags */
  private boolean isSet2(int f) {
    return (flags2 & f) != 0;
  }
  
  private boolean isSetUpd(int f) {
    return (updateFlags & f) != 0;
  }

  public boolean fieldsSorted() {
    return !isSet(JZNOCT_FID_NO_SORT);
  }
  
  public boolean relativeOffsets() {
    return isSet(JZNOCT2_REL_OFFSET);
  }

  public int getFieldId(String key) {
    int utf8len = utf8len(key);
    if (utf8len <= OsonConstants.MAX_SMALL_KEY_LENGTH && uniqueFields > 0) { 
      return getFieldId(key, hashIdArray, fieldNames);
    } else if (utf8len <= OsonConstants.MAX_BIG_KEY_LENGTH && uniqueFields2 > 0) {
      return uniqueFields + getFieldId(key, hashIdArray2, fieldNames2); 
    }
    return -1;
  }

  private int getFieldId(String key, int[] hashIdArray, String[] fieldNames) {
    int hash = ohash(key, null);
    int idx = Arrays.binarySearch(hashIdArray, hash);
    if (idx < 0) { 
      return -1;
    }
    // Search doesn't guarantee we get the first if there are duplicates (hopefully
    // rare case)
    while (idx > 0 && hashIdArray[idx - 1] == hash) {
      idx--;
    }
    do {
      if (fieldNames[idx].equals(key)) {
        return idx + 1; // storage is 1-based
      }
      idx++;
      if (idx >= hashIdArray.length || hashIdArray[idx] != hash) {
        return -1;
      }
    } while (true);
  }

  /**
   * Length will be set to the length of the string in utf8 bytes
   * if specified (not null).  Atomic integer is just used as a mutable holder
   * for the output parameter, it does not need to be atomic.
   */
  public static int ohash(String key, AtomicInteger length) {
    int hash = 0x811C9DC5;
    final int charlen = key.length();
    int len = charlen;
    for (int i = 0; i < charlen; i++) {
      final char c = key.charAt(i);
      if (c <= 0x7f) {
        hash = (hash ^ c) * 16777619;
      } else if (c <= 0x7ff) {
        len+=1;
        hash = (hash ^ (UTF8_MAPPING[c][0] & 0xff)) * 16777619;
        hash = (hash ^ (UTF8_MAPPING[c][1] & 0xff)) * 16777619;
      } else {
        // This could be significantly optimized
        int cp = key.codePointAt(i);
        if (Character.charCount(cp) == 2) {
          i++;
        }
        String s = new String(Character.toChars(cp));
        final byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        len += (bytes.length-1);
        hash = hashUtf8(hash, bytes);
      }
    }
    int result = len <= OsonConstants.MAX_SMALL_KEY_LENGTH ? (hash & 0xff) :
      (((hash & 0xff) << 8) | ((hash & 0xff00) >> 8));
    if (length != null) {
      length.set(len);
    }
    return result;
  }
  
  public static int ohash(byte[] key) {
    int hash = 0x811C9DC5;
    int res = hashUtf8(hash, key);
    return key.length <= OsonConstants.MAX_SMALL_KEY_LENGTH ? (res & 0xff) :
      (((res & 0xff) << 8) | ((res & 0xff00) >> 8));
  }

  static int utf8len(String key) {
    int result = 0;
    for (int i = 0; i < key.length(); i++) {
      char c = key.charAt(i);
      if (c <= 0x7f)
        result += 1;
      else if (c <= 0x7ff)
        result += 2;
      else 
        return key.getBytes(StandardCharsets.UTF_8).length;
    }
    return result;
  }

  private static int hashUtf8(int hash, byte[] bytes) {
    for (int j = 0; j < bytes.length; j++) {
      hash = (hash ^ (bytes[j] & 0xff)) * 16777619;
    }
    return hash;
  }

  public int numFieldIdBytes() {
    return this.ubFieldId;
  }
  
  static {
    UTF8_MAPPING = new byte[2048][];
    for (int i = 0; i < UTF8_MAPPING.length; i++) {
      UTF8_MAPPING[i] = new String(new char[] { (char) i }).getBytes(StandardCharsets.UTF_8);
    }
  }
}

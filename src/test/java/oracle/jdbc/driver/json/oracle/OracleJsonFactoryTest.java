// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
package oracle.jdbc.driver.json.oracle;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.jdbc.driver.json.binary.OsonConstants;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDouble;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonTimestamp;

public class OracleJsonFactoryTest extends JsonTestCase {
  
    public void testAcceptsValidEmptyFieldName() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonObject obj =
        f.createJsonBinaryValue(ByteBuffer.wrap(createObjectOson(f, "", 1))).asJsonObject();
    assertEquals(1, obj.getInt(""));
  }

  public void testAcceptsValid256ByteFieldName() {
    OracleJsonFactory f = new OracleJsonFactory();
    String key = makeAsciiString(256);
    byte[] oson = createObjectOson(f, key, 1);
    assertNotNull(f.createJsonBinaryParser(ByteBuffer.wrap(oson)));
    OracleJsonObject obj = f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();
    assertEquals(1, obj.getInt(key));
  }

  public void testRejectsImpossibleSmallFieldDictionaryCount() {
    OracleJsonFactory f = new OracleJsonFactory();
    try {
      f.createJsonBinaryValue(ByteBuffer.wrap(corruptSmallDictionaryOson(1_000_000)));
      fail();
    } catch (OracleJsonException e) {
      assertTrue(e.getMessage().startsWith("ORA-26305"));
    }
  }

  public void testRejectsImpossibleBigFieldDictionaryCount() {
    OracleJsonFactory f = new OracleJsonFactory();
    try {
      f.createJsonBinaryParser(ByteBuffer.wrap(corruptBigDictionaryOson(1_000_000)));
      fail();
    } catch (OracleJsonException e) {
      assertTrue(e.getMessage().startsWith("ORA-26305"));
    }
  }

  public void testRejectsFieldDictionaryLengthBeyondHeap() {
    assertCorrupt(corruptSmallDictionaryEntryOson(255));
  }

  public void testRejectsScalarStringLengthBeyondBuffer() {
    assertCorrupt(corruptScalarValueOson(0x38, 1_000_000));
  }

  public void testRejectsScalarBinaryLengthBeyondBuffer() {
    assertCorrupt(corruptScalarValueOson(0x3B, 1_000_000));
  }

  public void testRejectsScalarVectorLengthBeyondBuffer() {
    assertCorrupt(corruptScalarVectorOson(1_000_000));
  }

  public void testRejectsArrayChildTableBeyondBuffer() {
    assertCorrupt(corruptArrayChildTableOson(1_000_000));
  }

  public void testRejectsArrayChildCycleInParser() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonParser parser =
        f.createJsonBinaryParser(ByteBuffer.wrap(corruptArrayChildCycleOson()));
    try {
      for (int i = 0; i <= OsonConstants.UB2_MAXSZ; i++) {
        parser.next();
      }
      fail();
    } catch (OracleJsonException e) {
      assertTrue(e.getMessage().startsWith("ORA-26337"));
    } finally {
      parser.close();
    }
  }

  public void testAllowsArrayChildCycleInValueAccess() {
    OracleJsonFactory f = new OracleJsonFactory();
    OracleJsonArray array =
        f.createJsonBinaryValue(ByteBuffer.wrap(corruptArrayChildCycleOson()))
            .asJsonArray();
    OracleJsonArray child = array.get(0).asJsonArray();
    assertEquals(1, child.size());
  }

  public void testRejectsArrayChildOffsetOutsideSegments() {
    assertCorruptFromArrayAccess(corruptArrayChildOffsetOson(4));
  }

  public void testRejectsForwardingCycle() {
    assertCorruptFromArrayAccess(corruptForwardingCycleOson());
  }

  public void testRejectsMissingOverflowMapping() {
    assertCorruptFromArrayAccess(corruptMissingOverflowMappingOson());
  }

  public void testRejectsForwardingMapTargetBeyondExtendedSegment() {
    assertCorrupt(corruptForwardingMapTargetOson());
  }

  private byte[] createObjectOson(OracleJsonFactory f, String key, int value) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    OracleJsonGenerator generator = f.createJsonBinaryGenerator(bytes);
    generator.writeStartObject();
    generator.write(key, value);
    generator.writeEnd();
    generator.close();
    return bytes.toByteArray();
  }

  private String makeAsciiString(int len) {
    StringBuilder builder = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      builder.append('x');
    }
    return builder.toString();
  }

  private void assertCorrupt(byte[] oson) {
    OracleJsonFactory f = new OracleJsonFactory();
    try {
      f.createJsonBinaryValue(ByteBuffer.wrap(oson));
      fail();
    } catch (OracleJsonException e) {
      assertTrue(e.getMessage().startsWith("ORA-26305"));
    }
  }

  private void assertCorruptFromArrayAccess(byte[] oson) {
    OracleJsonFactory f = new OracleJsonFactory();
    try {
      f.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonArray().get(0);
      fail();
    } catch (OracleJsonException e) {
      assertTrue(e.getMessage().startsWith("ORA-26305"));
    }
  }

  private byte[] corruptSmallDictionaryOson(int uniqueFields) {
    ByteBuffer bytes = ByteBuffer.allocate(16);
    bytes.put(OsonConstants.MAGIC_BYTES);
    bytes.put((byte)1);
    bytes.putShort((short)0x000A);
    bytes.putInt(uniqueFields);
    bytes.putShort((short)0);
    bytes.putShort((short)0);
    bytes.putShort((short)0);
    return bytes.array();
  }

  private byte[] corruptSmallDictionaryEntryOson(int fieldLength) {
    ByteBuffer bytes = ByteBuffer.allocate(17);
    bytes.put(OsonConstants.MAGIC_BYTES);
    bytes.put((byte)1);
    bytes.putShort((short)0x0002);
    bytes.put((byte)1);
    bytes.putShort((short)1);
    bytes.putShort((short)0);
    bytes.putShort((short)0);
    bytes.put((byte)0);
    bytes.putShort((short)0);
    bytes.put((byte)fieldLength);
    return bytes.array();
  }

  private byte[] corruptScalarValueOson(int op, int valueLength) {
    ByteBuffer bytes = ByteBuffer.allocate(13);
    bytes.put(OsonConstants.MAGIC_BYTES);
    bytes.put((byte)1);
    bytes.putShort((short)0x0010);
    bytes.putShort((short)5);
    bytes.put((byte)op);
    bytes.putInt(valueLength);
    return bytes.array();
  }

  private byte[] corruptScalarVectorOson(int valueLength) {
    ByteBuffer bytes = ByteBuffer.allocate(14);
    bytes.put(OsonConstants.MAGIC_BYTES);
    bytes.put((byte)1);
    bytes.putShort((short)0x0010);
    bytes.putShort((short)6);
    bytes.put((byte)0x7B);
    bytes.put((byte)0x01);
    bytes.putInt(valueLength);
    return bytes.array();
  }

  private byte[] corruptArrayChildTableOson(int childCount) {
    ByteBuffer bytes = ByteBuffer.allocate(18);
    bytes.put(OsonConstants.MAGIC_BYTES);
    bytes.put((byte)1);
    bytes.putShort((short)0x0002);
    bytes.put((byte)0);
    bytes.putShort((short)0);
    bytes.putShort((short)5);
    bytes.putShort((short)0);
    bytes.put((byte)0xD0);
    bytes.putInt(childCount);
    return bytes.array();
  }

  private byte[] corruptArrayChildCycleOson() {
    return corruptArrayChildOffsetOson(0);
  }

  private byte[] corruptArrayChildOffsetOson(int childOffset) {
    ByteBuffer bytes = ByteBuffer.allocate(17);
    putNonScalarHeader(bytes, 4);
    bytes.put((byte)0xC0);
    bytes.put((byte)1);
    bytes.putShort((short)childOffset);
    return bytes.array();
  }

  private byte[] corruptForwardingCycleOson() {
    ByteBuffer bytes = ByteBuffer.allocate(39);
    putNonScalarHeader(bytes, 7);
    bytes.put((byte)0xC0);
    bytes.put((byte)1);
    bytes.putShort((short)4);
    bytes.put((byte)0x76);
    bytes.putShort((short)0);
    putUpdateHeader(bytes, 0, 3);
    bytes.put((byte)0x76);
    bytes.putShort((short)0);
    return bytes.array();
  }

  private byte[] corruptMissingOverflowMappingOson() {
    ByteBuffer bytes = ByteBuffer.allocate(35);
    putNonScalarHeader(bytes, 5);
    bytes.put((byte)0xC0);
    bytes.put((byte)1);
    bytes.putShort((short)4);
    bytes.put((byte)0x75);
    putUpdateHeader(bytes, 0, 1);
    bytes.put((byte)0x30);
    return bytes.array();
  }

  private byte[] corruptForwardingMapTargetOson() {
    ByteBuffer bytes = ByteBuffer.allocate(39);
    putNonScalarHeader(bytes, 5);
    bytes.put((byte)0xC0);
    bytes.put((byte)1);
    bytes.putShort((short)4);
    bytes.put((byte)0x75);
    bytes.putShort((short)0x0100);
    bytes.putShort((short)1);
    bytes.putInt(0);
    bytes.putInt(4);
    bytes.putInt(1);
    bytes.putShort((short)4);
    bytes.putShort((short)1);
    bytes.put((byte)0x30);
    return bytes.array();
  }

  private void putNonScalarHeader(ByteBuffer bytes, int treeSegmentSize) {
    bytes.put(OsonConstants.MAGIC_BYTES);
    bytes.put((byte)1);
    bytes.putShort((short)0x0002);
    bytes.put((byte)0);
    bytes.putShort((short)0);
    bytes.putShort((short)treeSegmentSize);
    bytes.putShort((short)0);
  }

  private void putUpdateHeader(
      ByteBuffer bytes,
      int overflowMappingSize,
      int extendedTreeSegmentSize) {
    bytes.putShort((short)0);
    bytes.putShort((short)0);
    bytes.putInt(0);
    bytes.putInt(overflowMappingSize);
    bytes.putInt(extendedTreeSegmentSize);
  }

  private byte[] corruptBigDictionaryOson(int uniqueFields2) {
    ByteBuffer bytes = ByteBuffer.allocate(23);
    bytes.put(OsonConstants.MAGIC_BYTES);
    bytes.put((byte)3);
    bytes.putShort((short)0x0002);
    bytes.put((byte)0);
    bytes.putShort((short)0);
    bytes.putShort((short)0x0100);
    bytes.putInt(uniqueFields2);
    bytes.putInt(0);
    bytes.putShort((short)0);
    bytes.putShort((short)0);
    return bytes.array();
  }
}

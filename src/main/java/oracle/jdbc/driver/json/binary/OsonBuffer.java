// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 


package oracle.jdbc.driver.json.binary;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public final class OsonBuffer {
  
  final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
  
  final ByteBuffer buffer;
  
  char[] charBuffer;
  
  public OsonBuffer(ByteBuffer buffer) {
    buffer.order(ByteOrder.BIG_ENDIAN);
    this.buffer = buffer;
  }

  public int getInt() {
    return buffer.getInt();
  }  
  
  public ByteBuffer buffer() {
    return buffer;
  }
  
  public int position() {
    return buffer.position();
  }
  
  public void position(int pos) {
    buffer.position(pos);
  }

  public void checkRange(long pos, long len, ExceptionFactory f) {
    if (pos < 0 || len < 0 || pos > buffer.limit() ||
        len > ((long)buffer.limit() - pos)) {
      throw OracleJsonExceptions.CORRUPT.create(f);
    }
  }

  public void checkRemaining(int len, ExceptionFactory f) {
    checkRange(buffer.position(), len, f);
  }
  
  public int getUB1() {
    return 0xff & buffer.get();
  }

  public int getUB1(int i) {
    return 0xff & buffer.get(i);
  }
  
  public int getUB2() {
    return 0xffff & buffer.getShort();
  }
  
  public short getShort(int i) {
    return buffer.getShort(i);
  }
  
  public int getUB2(int i) {
    return 0xffff & buffer.getShort(i);
  }
  
  public Map<Integer, String> cache = new HashMap<Integer, String>();
  
  public String readString(int len) {
    if (!buffer.hasArray()) {
      throw new UnsupportedOperationException(); // TODO
    }
    checkRemaining(len, null);
    
    byte[] raw = buffer.array();
    if (charBuffer == null || charBuffer.length < len) {
      charBuffer = new char[len];
    }
    final int bufferPos = buffer.position();
    final int pos = bufferPos + buffer.arrayOffset();
    for (int i = 0; i < len; i++) {
      final byte b = raw[i+pos];
      if (b < 0) {
        // slow path
        String result = new String(buffer.array(), pos,
            len, StandardCharsets.UTF_8);
        buffer.position(bufferPos+len);
        return result;
      }
      charBuffer[i] = (char)b;
    }
    buffer.position(bufferPos+len);
    return new String(charBuffer, 0, len);
  }
  
  public void readBytes(OutputStream out, int len) throws IOException {
    if (!buffer.hasArray()) {
      throw new UnsupportedOperationException(); // TODO
    }
    checkRemaining(len, null);
    final int pos = buffer.position() + buffer.arrayOffset();
    out.write(buffer.array(), pos, len);
  }
  
  public int getUB4int() {
    int res = buffer.getInt();
    if (res < 0) {
      throw OracleJsonExceptions.OVERFLOW.create(null, res);
    }
    return res;
  }

  public int getUB4int(int i) {
    int res = buffer.getInt(i);
    if (res < 0) {
      throw OracleJsonExceptions.OVERFLOW.create(null, res);
    }
    return res;
  }   
  
  public int getInt(int i) {
    return buffer.getInt(i);
  }
  
  public void get(byte[] bytes) {
    buffer.get(bytes);
  }
  
  public double readDtyDouble() {
    byte[] res = new byte[8];
    buffer.get(res);
    return OsonPrimitiveConversions.canonicalFormatBytesToDouble(res);
  }
  
  public float readDtyFloat() {
    byte[] res = new byte[4];
    buffer.get(res);
    return OsonPrimitiveConversions.canonicalFormatBytesToFloat(res);
  }
  
  int binarySearchUb1(int fromPos, int count, int test) {
    int low  = 0;
    int high = count-1;
    byte[] bytes = buffer.array();
    while (low <= high) {
      int mid = (low + high) >>> 1;
      int midValue = (bytes[fromPos + mid] & 0xff); 
      if (midValue < test) {
        low = mid + 1;
      } else if (midValue > test) {
        high = mid - 1;
      } else {
        return mid;
      }
    }
    return -1;
  }

  int binarySearchUb2(int fromPos, int count, int test) {
    int low  = 0;
    int high = count-1;
    while (low <= high) {
      int mid = (low + high) >>> 1;
      int midValue = getUB2(fromPos + (mid*2));
      if (midValue < test) {
        low = mid + 1;
      } else if (midValue > test) {
        high = mid - 1;
      } else {
        return mid;
      }
    }
    return -1;
  }

  int binarySearchUb4(int fromPos, int count, int test) {
    int low  = 0;
    int high = count-1;
    while (low <= high) {
      int mid = (low + high) >>> 1;
      int midValue = getUB4int(fromPos + (mid*4));
      if (midValue < test) {
        low = mid + 1;
      } else if (midValue > test) {
        high = mid - 1;
      } else {
        return mid;
      }
    }
    return -1;
  }
  
  public int linearSearchUb1(int fromPos, int count, int test) {
    for (int i = fromPos; i < (fromPos + count); i++) {
      if (getUB1(i) == test) {
        return i - fromPos;
      }
    }
    return -1;
  }
  
  int linearSearchUb2(int fromPos, int count, int test) {
    int endPos = fromPos + (count*2);
    for (int i = fromPos; i < endPos; i+=2) {
      if (getUB2(i) == test) {
        return (i - fromPos)/2; 
      }
    }
    return -1;
  }  
  
  public int linearSearchUb4(int fromPos, int count, int test) {
    int endPos = fromPos + (count*4);
    for (int i = fromPos; i < endPos; i+=4) {
      if (getUB4int(i) == test) {
        return (i - fromPos)/4;
      }
    }
    return -1;
  }

}

// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * JDK-only VECTOR codec used by the standalone OSON build.
 *
 * <p>This is an LLM-derived, temporary copy of the production
 * {@code VectorData} implementation. It exists until the production version
 * can be made independently compilable outside the rest of JDBC.</p>
 */
public final class VectorData {
  private static final byte MAGIC = (byte)0xDB;
  private static final short LITTLE_ENDIAN = 0x0001;
  private static final short IEEE = 0x0008;
  private static final byte FLOAT32 = 2;
  private static final byte FLOAT64 = 3;
  private static final byte INT8 = 4;
  private static final byte BINARY = 5;
  private static final int HEADER_SIZE = 17;

  private VectorData() { }

  public static byte[] encode(float[] values) throws SQLException {
    return encode(values.length, FLOAT32, values.length * 4, norm(values), b -> {
      for (float value : values) b.putInt(oracleFloatBits(value));
    });
  }
  public static byte[] encode(double[] values) throws SQLException {
    return encode(values.length, FLOAT64, values.length * 8, norm(values), b -> {
      for (double value : values) b.putLong(oracleDoubleBits(value));
    });
  }
  public static byte[] encode(byte[] values) throws SQLException {
    return encode(values.length, INT8, values.length, norm(values), b -> b.put(values));
  }

  public static boolean isInt8(byte[] data) throws SQLException {
    return header(data).type == INT8;
  }

  public static boolean isFloat32(byte[] data) throws SQLException {
    return header(data).type == FLOAT32;
  }

  public static <T> T decode(byte[] data, Class<T> type, boolean ignored)
    throws SQLException {
    Header h = header(data);
    if (type == double[].class) return type.cast(doubles(data, h));
    if (type == float[].class) {
      double[] values = doubles(data, h); float[] result = new float[values.length];
      for (int i = 0; i < result.length; i++) result[i] = (float)values[i];
      return type.cast(result);
    }
    if (type == byte[].class) {
      if (h.type == INT8) return type.cast(bytes(data, h));
      double[] values = doubles(data, h); byte[] result = new byte[values.length];
      for (int i = 0; i < result.length; i++) result[i] = (byte)values[i];
      return type.cast(result);
    }
    throw new SQLException("Unsupported vector conversion: " + type.getName());
  }

  public static boolean equals(byte[] left, byte[] right) {
    try { return type(left) == type(right) && Arrays.equals(doubles(left, header(left)), doubles(right, header(right))); }
    catch (SQLException e) { return false; }
  }

  public static int hashCode(byte[] data) {
    try { return Arrays.hashCode(doubles(data, header(data))); }
    catch (SQLException e) { return Arrays.hashCode(data); }
  }

  private static byte[] encode(int length, byte type, int size, double norm,
    Writer writer) {
    ByteBuffer b = ByteBuffer.allocate(HEADER_SIZE + size).order(ByteOrder.BIG_ENDIAN);
    short flags = (short)(type == BINARY ? 0 : 0x0012);
    b.put(MAGIC).put((byte)0).putShort(flags).put(type).putInt(length)
      .putLong(oracleDoubleBits(norm));
    writer.write(b); return b.array();
  }

  private static double norm(double[] values) {
    double squareSum = 0d;
    for (double value : values) squareSum += value * value;
    return Math.sqrt(squareSum);
  }

  private static double norm(float[] values) {
    double squareSum = 0d;
    for (float value : values) squareSum += value * value;
    return Math.sqrt(squareSum);
  }

  private static double norm(byte[] values) {
    double squareSum = 0d;
    for (byte value : values) squareSum += value * value;
    return Math.sqrt(squareSum);
  }

  private static Header header(byte[] data) throws SQLException {
    if (data == null || data.length < HEADER_SIZE || data[0] != MAGIC) throw new SQLException("Unrecognized VECTOR encoding");
    byte type = data[4]; if (type < FLOAT32 || type > BINARY) throw new SQLException("Unrecognized VECTOR type");
    int length = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getInt(5);
    if (length < 0) throw new SQLException("Invalid VECTOR length");
    long payloadLength = type == FLOAT64 ? 8L * length
      : type == FLOAT32 ? 4L * length
      : type == BINARY ? ((long)length + 7L) / 8L
      : length;
    if (payloadLength > data.length - HEADER_SIZE)
      throw new ArrayIndexOutOfBoundsException(data.length);
    short flags = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getShort(2);
    return new Header(type, length, (flags & LITTLE_ENDIAN) != 0, (flags & IEEE) != 0);
  }

  private static byte type(byte[] data) { try { return header(data).type; } catch (SQLException e) { return 0; } }
  private static ByteBuffer values(byte[] data, Header h) { return ByteBuffer.wrap(data, HEADER_SIZE, data.length - HEADER_SIZE).slice().order(h.little ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN); }
  private static byte[] bytes(byte[] data, Header h) { ByteBuffer b = values(data, h); byte[] result = new byte[h.type == BINARY ? (h.length + 7) / 8 : h.length]; b.get(result); return result; }
  private static double[] doubles(byte[] data, Header h) {
    if (h.type == BINARY) { byte[] packed = bytes(data, h); double[] r = new double[h.length]; for (int i = 0; i < r.length; i++) r[i] = (packed[i >>> 3] & (0x80 >>> (i & 7))) == 0 ? 0d : 1d; return r; }
    ByteBuffer b = values(data, h); double[] r = new double[h.length];
    for (int i = 0; i < r.length; i++) {
      r[i] = h.type == FLOAT64
        ? (h.ieee ? b.getDouble() : oracleDouble(b.getLong()))
        : h.type == FLOAT32
          ? (h.ieee ? b.getFloat() : oracleFloat(b.getInt()))
          : b.get();
    }
    return r;
  }
  private static int oracleFloatBits(float value) {
    int bits = Float.floatToRawIntBits(value);
    return (bits & 0x80000000) == 0 ? bits | 0x80000000 : ~bits;
  }
  private static float oracleFloat(int bits) {
    return Float.intBitsToFloat((bits & 0x80000000) != 0 ? bits & 0x7fffffff : ~bits);
  }
  private static long oracleDoubleBits(double value) {
    long bits = Double.doubleToRawLongBits(value);
    return (bits & 0x8000000000000000L) == 0 ? bits | 0x8000000000000000L : ~bits;
  }
  private static double oracleDouble(long bits) {
    return Double.longBitsToDouble((bits & 0x8000000000000000L) != 0 ? bits & 0x7fffffffffffffffL : ~bits);
  }
  private interface Writer { void write(ByteBuffer b); }
  private static final class Header { final byte type; final int length; final boolean little; final boolean ieee; Header(byte type, int length, boolean little, boolean ieee) { this.type = type; this.length = length; this.little = little; this.ieee = ieee; } }
}

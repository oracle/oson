/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */

package oracle.jdbc.driver.json.binary;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import oracle.jdbc.driver.VectorData;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.tree.OracleJsonBinaryImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDateImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalDSImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
import oracle.jdbc.driver.json.tree.OracleJsonNumberImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampTZImpl;
import oracle.jdbc.driver.json.tree.OracleJsonVectorImpl;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonDouble;
import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonNumber;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonVector;


/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class JsonSerializerImpl extends AbstractGenerator {
  
  private static final byte[] CHAR_TYPES = new byte[Character.MAX_VALUE+1];
  private static final byte CHAR_OTHER = 0;
  private static final byte CHAR_CONTROL = 1;
  private static final byte CHAR_LF = 2; // special case control character
  private static final byte CHAR_QUOTATION = 3;
  private static final byte CHAR_REVERSE_SOLIDUS = 4;
  private static final byte CHAR_SURROGATE = 5;
  private static final byte CHAR_BACKSPACE = 6;
  private static final byte CHAR_FORMFEED = 7;
  private static final byte CHAR_CR = 8;
  private static final byte CHAR_TAB = 9;
  private static final byte CHAR_ASCII = 10;
  
  private static final int ASCII_MAX = 0x7f;
  
  private interface JsonOutput extends Closeable {

    void flush() throws IOException;

    void write(char c) throws IOException;

    /** Write value that is assumed to be ascii range characters */
    void writeAscii(CharSequence value) throws IOException;
    
    void writeAscii(byte value) throws IOException;
    
    void writeSurrogates(char c1, char c2) throws IOException;
    
    boolean utf8();

    void utf8(byte[] array, int offset, int len) throws IOException;
  }
  
  private static final class WriterJsonOutput implements JsonOutput {
    
    char[] buffer = new char[1024];
    
    int pos = 0;
    
    Writer writer;
    
    
    WriterJsonOutput(Writer writer) {
      this.writer = writer;
      
    }

    @Override
    public void close() throws IOException {
      flush();
      writer.close();
      buffer = null;
    }

    @Override
    public void flush() throws IOException {
      writer.write(buffer, 0 , pos);
      pos = 0;
    }

    @Override
    public void writeAscii(CharSequence value) throws IOException {
      for (int i = 0; i < value.length(); i++) {
        if (pos >= buffer.length) {
          flush();
        }
        buffer[pos++] = value.charAt(i);
      }
    }

    @Override
    public void writeAscii(byte value) throws IOException {
      if (pos >= buffer.length) {
        flush();
      }
      buffer[pos++] = (char)value;
    }

    @Override
    public void write(char c) throws IOException {
      if (pos >= buffer.length) {
        flush();
      }
      buffer[pos++] = c;
    }

    @Override
    public void writeSurrogates(char c1, char c2) throws IOException {
      write(c1);
      write(c2);
    }

    @Override
    public boolean utf8() {
      return false;
    }

    @Override
    public void utf8(byte[] array, int offset, int len) throws IOException {
      flush();
      writer.write(new String(array, offset, len, StandardCharsets.UTF_8));
    }
  }
  
  private static class UTF8JsonOutput implements JsonOutput {
    
    byte[] buffer = new byte[1024];
    
    OutputStream out;

    int pos = 0;
    
    CharsetEncoder encoder;
    
    CharBuffer cbuffer;
    
    public UTF8JsonOutput(OutputStream out) {
      this.out = out;
    }
    
    private void initEncoder() {
      if (encoder == null) {
        encoder = StandardCharsets.UTF_8.newEncoder()
            .onMalformedInput(CodingErrorAction.REPLACE)
            .onUnmappableCharacter(CodingErrorAction.REPLACE);
        cbuffer = CharBuffer.allocate(2);
      }
    }

    @Override
    public void close() throws IOException {
      flush();
      out.close();
      buffer = null;
    }

    @Override
    public void writeAscii(CharSequence value) throws IOException {
      for (int i = 0; i < value.length(); i++) {
        if (pos >= buffer.length) {
          flush();
        }
        buffer[pos++] = (byte)value.charAt(i);
      }
    }

    /** Must be <= ASCII_MAX */
    @Override
    public void writeAscii(byte value) throws IOException {
      if (pos >= buffer.length) {
        flush();
      }
      buffer[pos++] = value;
    }

    @Override
    public void flush() throws IOException {
      out.write(buffer, 0, pos);
      pos = 0;
    }

    /** Must not be a surrogate */
    @Override
    public void write(char c) throws IOException {
      if (c <= ASCII_MAX) {
        writeAscii((byte)c);
      } else {
        flush();
        initEncoder();
        cbuffer.clear();
        cbuffer.append(c);
        cbuffer.flip();
        encodeChars();
      }
    }
    
    @Override
    public void writeSurrogates(char c1, char c2) throws IOException {
      flush();
      initEncoder();
      cbuffer.clear();
      cbuffer.append(c1);
      cbuffer.append(c2);
      cbuffer.flip();
      encodeChars();
    }

    @Override
    public boolean utf8() {
      return true;
    }

    private void encodeChars() {
      ByteBuffer b = ByteBuffer.wrap(buffer);
      encoder.encode(cbuffer, b, false);
      pos = b.position();
    }

    @Override
    public void utf8(byte[] array, int offset, int len) throws IOException {
      flush();
      out.write(array, offset, len);
    }
  }
  
  private JsonOutput writer;
  
  private StreamContext ctx;
  
  public JsonSerializerImpl(OutputStream out) {
    this.writer = new UTF8JsonOutput(out);
    this.ctx = new StreamContext(getExceptionFactory());
  }
  
  public JsonSerializerImpl(Writer writer) {
    this.writer = new WriterJsonOutput(writer);
    this.ctx = new StreamContext(getExceptionFactory());
  }
  
  @Override
  public OracleJsonGenerator writeStartObject() {
    writeSeparator();
    ctx.startObject();
    writeAscii('{');
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeStartArray() {
    writeSeparator();
    ctx.startArray();
    writeAscii('[');
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeEnd() {
    if (ctx.inObject()) {
      ctx.end();
      writeAscii('}');
    } else {
      ctx.end();
      writeAscii(']');
    }
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(String value) {
    primitive();
    writeQuotedString(value, writer, getExceptionFactory());
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeKey(String key) {
    writeSeparator();
    ctx.pendingKey();
    writeQuotedString(key, writer, getExceptionFactory());
    writeAscii(':');
    return this;
  }

  private static void writeQuotedString(String value, JsonOutput writer, ExceptionFactory f) {
    try {
      writer.writeAscii((byte)'\"');
      writeEscaped(value, writer);
      writer.writeAscii((byte)'\"');
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(f, e);
    }
  }

  public static String serializeString(String value) {
    StringWriter w = new StringWriter();
    WriterJsonOutput o = new WriterJsonOutput(w);
    writeQuotedString(value, o, null);
    try {
      o.close();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return w.toString();
  }
  
  @Override
  public OracleJsonGenerator write(BigDecimal value) {
    primitive();
    writeBigDecimal(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(BigInteger value) {
    primitive();
    writeBigInteger(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(int value) {
    primitive();
    writeInt(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(long value) {
    primitive();
    writeLong(value);
    return this;
  }

  @Override
  public OracleJsonGenerator write(double value) {
    assertFinite(value);
    primitive();
    writeDouble(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator write(float value) {
    assertFinite(value);
    primitive();
    writeFloat(value);
    return this;
  }  
  
  @Override
  public OracleJsonGenerator write(boolean value) {
    primitive();
    writeBoolean(value);
    return this;
  }
  
  @Override
  public OracleJsonGenerator writeNull() {
    primitive();
    writeNullInternal();
    return this;
  }
  
  @Override
  public void close() {
    ctx.close();
    try {
      writer.close();
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
  }
  
  protected ExceptionFactory getExceptionFactory() {
    return OracleJsonExceptions.ORACLE_FACTORY;
  }
  
  @Override
  public void flush() {
    try {
      writer.flush();
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
  }
  
  /// Custom write-value methods used by write(OracleJsonValue)
  
  @Override
  protected OracleJsonGenerator writeBinary(OracleJsonBinary value) {
    return write(((OracleJsonBinaryImpl)value).getString());
  }

  @Override
  protected OracleJsonGenerator writeDouble(OracleJsonDouble value) {
    return writeNumber(value);
  }
  
  @Override
  protected OracleJsonGenerator writeFloat(OracleJsonFloat value) {
    return writeNumber(value);
  }  

  @Override
  protected OracleJsonGenerator writeOraNumber(OracleJsonDecimal value) {
    return writeNumber(value);
  }

  private OracleJsonGenerator writeNumber(OracleJsonNumber value) {
    primitive();
    try {
      writer.writeAscii(numberToString(value));
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
    return this;
  }
  
  private String numberToString(OracleJsonNumber n) {
    if (n instanceof OracleJsonNumberImpl) {
      return ((OracleJsonNumberImpl)n).getString();
    } else {
      return n.toString();
    }
  }

  @Override
  protected OracleJsonGenerator writeTimestamp(OracleJsonTimestamp value) {
    return write(((OracleJsonTimestampImpl)value).getString());
  }

  @Override
  protected OracleJsonGenerator writeTimestampTZ(OracleJsonTimestampTZ value) {
    return write(((OracleJsonTimestampTZImpl)value).getString());
  }
  
  @Override
  protected OracleJsonGenerator writeDate(OracleJsonDate value) {
    return write(((OracleJsonDateImpl)value).getString());
  }
  
  @Override
  protected OracleJsonGenerator writeIntervalDS(OracleJsonIntervalDS value) {
    return write(((OracleJsonIntervalDSImpl)value).getString());
  }  

  @Override
  protected OracleJsonGenerator writeIntervalYM(OracleJsonIntervalYM value) {
    return write(((OracleJsonIntervalYMImpl)value).getString());
  }

  @Override
  protected OracleJsonGenerator writeVector(OracleJsonVector value) {
    writeStartArray();
    if (value instanceof OracleJsonVectorImpl
      && VectorData.isInt8(((OracleJsonVectorImpl)value).raw())) {
      byte[] bytes = value.getByteArray();
      for (byte valueByte : bytes) {
        primitive();
        writeInt(valueByte);
      }
    } else if (value instanceof OracleJsonVectorImpl
      && VectorData.isFloat32(((OracleJsonVectorImpl)value).raw())) {
      float[] floats = value.getFloatArray();
      for (float valueFloat : floats) {
        primitive();
        writeFloat(valueFloat);
      }
    } else {
      double[] dbls = value.getDoubleArray();
      for (double d : dbls) {
        primitive();
        writeDouble(d);
      }
    }
    writeEnd();
    return this;
  }

  /** This could be optimized to write UTF8 directly into underlying stream */
  @Override
  protected OracleJsonGenerator writeString(OracleJsonString value) {
    return write(value.getString());
  }

  private void writeAscii(CharSequence s) {
    try {
      writer.writeAscii(s);
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
  }
  
  private void writeBigDecimal(BigDecimal value) {
    writeAscii(value.toString());
  }

  private void writeBigInteger(BigInteger value) {
    writeAscii(new BigDecimal(value).toString());
  }
  
  private void writeInt(int value) {
    writeAscii(new BigDecimal(value).toString());
  }

  private void writeLong(long value) {
    writeAscii(new BigDecimal(value).toString());
  }
  
  private void writeFloat(float value) {
    writeAscii(Float.toString(value));
  } 
  
  private void writeDouble(double value) {
    writeAscii(Double.toString(value));
  } 
  
  private void writeBoolean(boolean value) {
    writeAscii(String.valueOf(value));
  }
  
  private void writeNullInternal() {
    writeAscii("null");
  }  
  
  private void writeAscii(char c) {
    try {
      writer.writeAscii((byte)c);
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
  }

  /** Call before writing a single primitive like write(String) */
  private void primitive() {
    writeSeparator();
    ctx.primitive();
  }

  private void writeSeparator() {
    if (ctx.hasChildren() && !ctx.pendingKey) {
      writeAscii(',');
    }
  }

  private static void writeEscaped(String value, JsonOutput writer) throws IOException {
    final int length = value.length();
    for (int i = 0; i < length; i++) {
      final char c = value.charAt(i);
      switch (CHAR_TYPES[c]) {
      case CHAR_ASCII:
        writer.writeAscii((byte)c);
        break;
      case CHAR_OTHER:
        writer.write(c);
        break;
      case CHAR_LF:
        writer.writeAscii("\\n");
        break;
      case CHAR_BACKSPACE:
        writer.writeAscii("\\b");
        break;
      case CHAR_FORMFEED:
        writer.writeAscii("\\f");
        break;
      case CHAR_CR:
        writer.writeAscii("\\r");
        break;
      case CHAR_TAB:
        writer.writeAscii("\\t");
        break;
      case CHAR_QUOTATION:
        writer.writeAscii("\\\"");
        break;
      case CHAR_REVERSE_SOLIDUS:
        writer.writeAscii("\\\\");
        break;
      case CHAR_CONTROL:
        escape(c, writer);
        break;        
      case CHAR_SURROGATE:
        writer.writeSurrogates(value.charAt(i), value.charAt(i+1));
        i++;
        break;  
      }
    }    
  }  

  private void assertFinite(double value) {
    if (Double.isInfinite(value) || Double.isNaN(value)) {
      throw new NumberFormatException(Double.toString(value));
    }
  }

  private static void escape(int cp, JsonOutput writer) throws IOException {
    for (char c : Character.toChars(cp)) {
      String hex = Integer.toHexString(c).toUpperCase();
      writer.writeAscii("\\u");
      for (int i = 0; i < 4 - hex.length(); i++) {
        writer.writeAscii((byte)'0');
      }
      writer.writeAscii(hex);
    }
   }
  
  static {
    for (int i = 0; i <= ASCII_MAX; i++) {
      CHAR_TYPES[i] = CHAR_ASCII;
    }
    for (char i = 0; i <= 0x1f; i++) {
      CHAR_TYPES[i] = CHAR_CONTROL;
    }
    CHAR_TYPES[0x7f] = CHAR_CONTROL;  /** copy XDK-C */
    CHAR_TYPES['\n'] = CHAR_LF;
    CHAR_TYPES['\\'] = CHAR_REVERSE_SOLIDUS;
    CHAR_TYPES['"']  = CHAR_QUOTATION;    
    CHAR_TYPES['\b'] = CHAR_BACKSPACE;
    CHAR_TYPES['\f'] = CHAR_FORMFEED;
    CHAR_TYPES['\r'] = CHAR_CR;
    CHAR_TYPES['\t'] = CHAR_TAB;
    for (char c = Character.MIN_HIGH_SURROGATE; c <= Character.MAX_HIGH_SURROGATE; c++) {
      CHAR_TYPES[c] = CHAR_SURROGATE;
    }
  }

  @Override
  public OracleJsonGenerator write(LocalDateTime local) {
    byte[] bytes = OsonPrimitiveConversions.toOracleTimestamp(getExceptionFactory(), local);
    write(OsonPrimitiveConversions.timestampToString(getExceptionFactory(), bytes));
    return this;
  }

  @Override
  public OracleJsonGenerator write(OffsetDateTime off) {
    byte[] bytes = OsonPrimitiveConversions.toOracleTimestampTZ(getExceptionFactory(), off);
    write(OsonPrimitiveConversions.timestampTZToString(getExceptionFactory(), bytes));
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
  public OracleJsonGenerator write(String key, byte[] value) {
    writeKey(key);
    write(value);
    return this;
  }

  @Override
  public OracleJsonGenerator write(byte[] value) {
    return write(OracleJsonBinaryImpl.getString(value, false));
  }
  
  @Override
  public OracleJsonGenerator writeId(byte[] value) {
    return write(OracleJsonBinaryImpl.getString(value, true));
  }
  

  @Override
  public OracleJsonGenerator write(Period value) {
    return write(OracleJsonIntervalYMImpl.serializePeriod(value, getExceptionFactory()));
  }

  @Override
  public OracleJsonGenerator write(Duration value) {
    return write(OracleJsonIntervalDSImpl.serializeDuration(value, getExceptionFactory()));
  }

  @Override
  protected void writeStringFromParser(OracleJsonParser parser) {
    if (!writer.utf8() || !(parser instanceof OsonParserImpl)) {
      write(parser.getString());
      return;
    }
    // Direct utf-8 to utf-8 mapping 
    primitive();
    OsonParserImpl oparser = (OsonParserImpl)parser;
    byte[] arr = oparser.getContext().b.buffer.array();
    writeQuotedUTF8String(arr, oparser.getCurrentStringPos(), oparser.getCurrentStringLen());
  }
  
  private void writeQuotedUTF8String(byte[] array, int offset, int len) {
    try {
      writeAscii('\"');
      int end = (offset+len);
      for (int i = offset; i < end; i++) {
        byte b = array[i];
        switch(CHAR_TYPES[b & 0xff]) {
        case CHAR_ASCII:
          writer.writeAscii(b); 
          break;
        case CHAR_LF:
          writer.writeAscii("\\n");
          break;
        case CHAR_BACKSPACE:
          writer.writeAscii("\\b");
          break;
        case CHAR_FORMFEED:
          writer.writeAscii("\\f");
          break;
        case CHAR_CR:
          writer.writeAscii("\\r");
          break;
        case CHAR_TAB:
          writer.writeAscii("\\t");
          break;
        case CHAR_QUOTATION:
          writer.writeAscii("\\\"");
          break;
        case CHAR_REVERSE_SOLIDUS:
          writer.writeAscii("\\\\");
          break;
        case CHAR_CONTROL:
          escape(b, writer);
          break;
        default: 
          // These characters don't need to be escaped so in the case of utf8->utf8
          // (e.g. oson -> json text) we can let them pass directly.  We need to figure 
          // out how many utf8 bytes make up this character and write them.  The leading
          // byte determines how many subsequent bytes there are.
          // see http://www.unicode.org/versions/Unicode12.0.0/ch03.pdf#G28070
          if ((b & 0b1110_0000) ==  0b1100_0000) { // 2
            writer.utf8(array, i, 2);
            i++;
          } else if ((b & 0b1111_0000) ==  0b1110_0000) { // 3
            writer.utf8(array, i, 3);
            i+=2;
          } else { // 4
            writer.utf8(array, i, 4);
            i+=3;
          }
          break;
        }
      }
      writeAscii('\"');
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
  }

  @Override
  protected void writeDecimalFromParser(OracleJsonParser parser) {
    primitive();
    try {
      writer.writeAscii(parser.getString());
    } catch (IOException e) {
      throw OracleJsonExceptions.IO.create(getExceptionFactory(), e);
    }
  }

}

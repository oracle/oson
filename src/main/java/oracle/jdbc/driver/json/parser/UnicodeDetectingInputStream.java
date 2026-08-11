/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.parser;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;

/**
 * A filter stream that detects the unicode encoding for the original
 * stream
 *
 * @author Jitendra Kotamraju
 */
class UnicodeDetectingInputStream extends FilterInputStream {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    private static final Charset UTF_16LE = Charset.forName("UTF-16LE");

    private static final Charset UTF_32LE = Charset.forName("UTF-32LE");
    private static final Charset UTF_32BE = Charset.forName("UTF-32BE");

    private static final byte FF = (byte)0xFF;
    private static final byte FE = (byte)0xFE;
    private static final byte EF = (byte)0xEF;
    private static final byte BB = (byte)0xBB;
    private static final byte BF = (byte)0xBF;
    private static final byte NUL = (byte)0x00;

    private final byte[] buf = new byte[4];
    private int bufLen;
    private int curIndex;
    private final Charset charset;

    UnicodeDetectingInputStream(InputStream is) {
        super(is);
        charset = detectEncoding();
    }

    Charset getCharset() {
        return charset;
    }
    
    private ExceptionFactory factory() {
      return OracleJsonExceptions.ORACLE_FACTORY;
    }

    private void fillBuf() {
        int b1;
        int b2;
        int b3;
        int b4;

        try {
            b1 = in.read();
            if (b1 == -1) {
                return;
            }

            b2 = in.read();
            if (b2 == -1) {
                bufLen = 1;
                buf[0] = (byte)b1;
                return;
            }

            b3 = in.read();
            if (b3 == -1) {
                bufLen = 2;
                buf[0] = (byte)b1;
                buf[1] = (byte)b2;
                return;
            }

            b4 = in.read();
            if (b4 == -1) {
                bufLen = 3;
                buf[0] = (byte)b1;
                buf[1] = (byte)b2;
                buf[2] = (byte)b3;
                return;
            }
            bufLen = 4;
            buf[0] = (byte)b1;
            buf[1] = (byte)b2;
            buf[2] = (byte)b3;
            buf[3] = (byte)b4;
        } catch (IOException ioe) {
          throw OracleJsonExceptions.IO.create(factory());
        }
    }
    
    /** TODO this code (as branched from JSONP) does not handle edge cases correctly.
     * It is based on the algorithm in the RFC which did not allow top-level primitives */
    private Charset detectEncoding() {
        fillBuf();
        if (bufLen < 2) {
            throw OracleJsonExceptions.PARSER_ENC_DETECT_FAIL.create(factory());
        } else if (bufLen == 4) {
            // Use BOM to detect encoding
            if (buf[0] == NUL && buf[1] == NUL && buf[2] == FE && buf[3] == FF) {
                curIndex = 4;
                return UTF_32BE;
            } else if (buf[0] == FF && buf[1] == FE && buf[2] == NUL && buf[3] == NUL) {
                curIndex = 4;
                return UTF_32LE;
            } else if (buf[0] == FE && buf[1] == FF) {
                curIndex = 2;
                return UTF_16BE;
            } else if (buf[0] == FF && buf[1] == FE) {
                curIndex = 2;
                return UTF_16LE;
            } else if (buf[0] == EF && buf[1] == BB && buf[2] == BF) {
                curIndex = 3;
                return UTF_8;
            }
            // No BOM, just use JSON RFC's encoding algo to auto-detect
            if (buf[0] == NUL && buf[1] == NUL && buf[2] == NUL) {
                return UTF_32BE;
            } else if (buf[0] == NUL && buf[2] == NUL) {
                return UTF_16BE;
            } else if (buf[1] == NUL && buf[2] == NUL && buf[3] == NUL) {
                return UTF_32LE;
            } else if (buf[1] == NUL && buf[3] == NUL) {
                return UTF_16LE;
            }
        }
        return UTF_8;
    }

    @Override
    public int read() throws IOException {
        if (curIndex < bufLen) {
            return buf[curIndex++];
        }
        return in.read();
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        if (curIndex < bufLen) {
            if (len == 0) {
                return 0;
            }
            if (off < 0 || len < 0 || len > b.length -off) {
                throw new IndexOutOfBoundsException();
            }
            int min = Math.min(bufLen-curIndex, len);
            System.arraycopy(buf, curIndex, b, off, min);
            curIndex += min;
            return min;
        }
        return in.read(b, off, len);
    }

}

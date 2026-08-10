/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2018, 2026, Oracle and/or its affiliates. */
/* All rights reserved.*/

package oracle.jdbc.driver.json.binary;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
/**
 * This class is meant to encapsulate all conversions between SQL
 * raw values and Java types needed by the OSON converter.
 * 
 * Code here was copied (and possibly modified) from JDBC.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public final class OsonPrimitiveConversions {

  private static final int  HUNDIGMAX = 66;
  private static final int  BIGINTARRAYMAX = 54;
  private static final int  BIGLENMAX = 22;
  private static final byte DIGEND = (byte)21;
  private static final int  LNXSGNBT = 128;
  private static final byte LNXDIGS  = 20;
  private static final int  LNXBASE  = 100;
  private static final int  LNXEXPMX = 127;
  private static final byte LNXEXPBS = 64;
  private static final int  LNXEXPMN = 0;
  private static final byte ODIGEND = (byte)9;
  private static final byte MAX_LONG_BASE100_DIGITS = 9;
  
  public static int SIZE_TIMESTAMP = 11;
  public static int SIZE_TIMESTAMP_NOFRAC = 7;
  public static int SIZE_DATE = 7;
  public static int SIZE_TIMESTAMPTZ = 13;
  static int OFFSET_HOUR = 20;
  static int OFFSET_MINUTE = 60;
  
  /** Region id bit flag for TIMESTAMPTZ */
  private static byte  REGIONIDBIT = (byte)0x80 ;
  
  private static final int MAXYEAR = 9999;

  private static int nanos(byte[] bytes) {
    int nanos  = bytes.length == SIZE_TIMESTAMP_NOFRAC ? 0 :
    getNanos(bytes, 7);
    return nanos;
  }
  
  
  /** 
   * The calendar year 0 does not exist in Oracle's timestamp. 
   * ISO 8601 dates and Java dates  (LocalDateTime) typically do.
   * 
   * JDBC works around this by adding 1 to the year (i.e. database
   * year -1 becomes java year 0).  However, this doesn't work in 
   * all cases because the database then disagrees on leap years.  
   * Consider this example:
   * 
   *   String sql = "select to_date('-0004-02-29','SYYYY-MM-DD') from dual";
   *   OracleDataSource ds = new OracleDataSource();
   *   ds.setURL(connectionString("SCOTT", "tiger"));
   *   OracleConnection con = (OracleConnection)ds.getConnection();
   *   PreparedStatement stmt = con.prepareStatement(sql);
   *   ResultSet rs = stmt.executeQuery();
   *   rs.next();
   *   Timestamp ts = rs.getTimestamp(1).getInstant();
   * 
   */
  private static void yearError(ExceptionFactory f, int year) {
    if (year < 1 || year > MAXYEAR) {
      throw OracleJsonExceptions.BAD_YEAR.create(f, year);
    }
  }
  
  public static boolean isPosInf(byte[] b)
  {
    return _isInf(b);
  }
  
  /**
   * The byte[] is the Oracle Number Negative Infinity
   */
  public static boolean isNegInf(byte[] b, int len, int offset) {
    if (len != 1) {
      return false;
    }
    return b[offset] == 0;
  }
  
  private static void appendInt(StringBuilder result, int n, int i) {
    if (n < 0) {
      result.append("-");
      n = -n;
    }
    int tmp = n;
    while (tmp > 0) {
      tmp /= 10;
      i--;
    }
    while (i > 0) {
      result.append('0');
      i--;
    }
    if (n != 0) {
      result.append(n);
    }
  }
    
  //////////////////////////////////////////////
  
  public static String timestampToString(ExceptionFactory f, byte[] bytes) {
    StringBuilder result = new StringBuilder(27);
    int year = getJavaYear(bytes[0] & 0xff, bytes[1] & 0xff);
    yearError(f, year);
    int month   = bytes[2] & 0xFF;
    int day   = bytes[3] & 0xFF;
    int hours   = (bytes[4] & 0xFF) - 1;
    int minutes = (bytes[5] & 0xFF) - 1;
    int seconds = (bytes[6] & 0xFF) - 1;
    int nanos = nanos(bytes);
    
    isoTimestamp(result, false, year, month, day, hours, minutes, seconds, nanos);
    return result.toString();
  }
  
  private static void isoTimestamp(StringBuilder result, 
      boolean alwaysNanos, int year, int month, int day, int hours,
      int minutes, int seconds, int nanos) {
    appendInt(result, year, 4);
    result.append("-");
    appendInt(result, month, 2);
    result.append("-");
    appendInt(result, day, 2);
    result.append("T");
    appendInt(result, hours, 2);
    result.append(":");
    appendInt(result, minutes, 2);
    result.append(":");
    appendInt(result, seconds, 2);
    if (nanos > 0 || alwaysNanos) {
      result.append(".");
      if (nanos % 1000 > 0)
        appendInt(result, nanos, 9);
      else
        appendInt(result, (int)TimeUnit.NANOSECONDS.toMicros(nanos), 6);
    }
  }

  public static String timestampTZToString(ExceptionFactory f, byte[] bytes) {
    StringBuilder result = new StringBuilder();
    OffsetDateTime odt = timestamptzToOffsetDateTime(f, bytes);
    int year = odt.getYear();
    int month = odt.getMonthValue();
    int day = odt.getDayOfMonth();
    int hours = odt.getHour();
    int minutes = odt.getMinute();
    int seconds = odt.getSecond();
    int nanos = odt.getNano();
    
    isoTimestamp(result, true, year, month, day, hours, minutes, seconds, nanos);

    ZoneOffset off = odt.getOffset();
    int offSeconds = Math.abs(off.getTotalSeconds());
    int offHours = offSeconds / (60*60);
    int offMinutes = (offSeconds % (60*60)) / 60;
    
    if (offHours == 0 && offMinutes == 0) {
      result.append("Z");
      return result.toString(); 
    }
    
    if (off.getTotalSeconds() < 0)
      result.append("-");
    else 
      result.append("+");
    
    appendInt(result, offHours, 2);
    result.append(":");
    appendInt(result, offMinutes, 2);
    return result.toString();
  } 
  
  public static void assertNoRegionTimestampTZ(ExceptionFactory f, byte[] bytes) {
    if ((bytes[11] & REGIONIDBIT) != 0) {
      throw OracleJsonExceptions.BAD_TIMESTAMP_TZ.create(f);
    }
  }
  
  /** 
   * This was derived from DateTimecommonAccessor.
   * The conversions in oracle.sql.TIMETAMPTZ do not seem to work correctly.
   */
  public static OffsetDateTime timestamptzToOffsetDateTime(ExceptionFactory f, byte[] bytes) {
    assertNoRegionTimestampTZ(f, bytes);
    // get UTC date time
    LocalDateTime ldt = timestampToLocalDateTime(f, bytes);
    OffsetDateTime utc = OffsetDateTime.of(ldt, ZoneOffset.UTC);
    int offHour = bytes[11] - OFFSET_HOUR;
    int offMinute = Math.abs(bytes[12] - OFFSET_MINUTE);
    
    ZoneOffset zoneOffset = 
      ZoneOffset.ofHoursMinutes(offHour, 
                                (int)Math.signum(offHour) * offMinute);
    return utc.withOffsetSameInstant(zoneOffset);
  }
  
  public static byte[] toOracleTimestampTZ(ExceptionFactory f, OffsetDateTime i) {
    OffsetDateTime utc = i.withOffsetSameInstant(ZoneOffset.UTC);
    int year = utc.getYear();
    yearError(f, year);

    byte[] result = new byte[SIZE_TIMESTAMPTZ];
    result[0] = (byte)(year / 100 + 100);
    result[1] = (byte)(year % 100 + 100);
    result[2] = (byte)utc.getMonthValue();
    result[3] = (byte)utc.getDayOfMonth();
    result[4] = (byte)(utc.getHour() + 1);
    result[5] = (byte)(utc.getMinute() + 1);
    result[6] = (byte)(utc.getSecond() + 1);

    int nanos = utc.getNano();
    result[7] = (byte)(nanos >> 24);
    result[8] = (byte)((nanos >> 16) & 0xFF);
    result[9] = (byte)((nanos >> 8) & 0xFF);
    result[10] = (byte)(nanos & 0xFF);

    int offset = i.getOffset().getTotalSeconds();
    result[11] = (byte)(offset / (60 * 60) + OFFSET_HOUR);
    result[12] = (byte)(offset % (60 * 60) / 60 + OFFSET_MINUTE);
    return result;
  }
  
  public static LocalDateTime dateToLocalDateTime(ExceptionFactory f, byte[] bytes) {
    int year = getJavaYear(bytes[0] & 0xff, bytes[1] & 0xff);
    yearError(f, year);
    int month   = bytes[2] & 0xFF;
    int day   = bytes[3] & 0xFF;
    int hours   = (bytes[4] & 0xFF) - 1;
    int minutes = (bytes[5] & 0xFF) - 1;
    int seconds = (bytes[6] & 0xFF) - 1;
    return LocalDateTime.of(
        year, 
        month, 
        day, 
        hours, 
        minutes, 
        seconds);
  }
  
  public static LocalDateTime timestampToLocalDateTime(ExceptionFactory f, byte[] bytes) {
    int year = getJavaYear(bytes[0] & 0xff, bytes[1] & 0xff);
    yearError(f, year);
    int month   = bytes[2] & 0xFF;
    int day   = bytes[3] & 0xFF;
    int hours   = (bytes[4] & 0xFF) - 1;
    int minutes = (bytes[5] & 0xFF) - 1;
    int seconds = (bytes[6] & 0xFF) - 1;
    int nanos = nanos(bytes);
    return LocalDateTime.of(year, 
        month, 
        day, 
        hours, 
        minutes, 
        seconds, 
        nanos);
  }
  
  public static byte[] toOracleDate(ExceptionFactory f, LocalDateTime local) {
    int year = local.getYear();
    yearError(f, year);
    byte[] result = new byte[SIZE_DATE];
    result[0] = (byte)(year / 100 + 100);
    result[1] = (byte)(year % 100 + 100);
    result[2] = (byte) local.getMonthValue();
    result[3] = (byte) local.getDayOfMonth();
    result[4] = (byte)(local.getHour() + 1);
    result[5] = (byte)(local.getMinute() + 1);
    result[6] = (byte)(local.getSecond() + 1);
    return result;
  }
  
  public static byte[] toOracleTimestamp(ExceptionFactory f, LocalDateTime local) {
    int year = local.getYear();
    yearError(f, year);

    int nanos = local.getNano();
    
    byte[] result = new byte[nanos == 0 ?
        SIZE_TIMESTAMP_NOFRAC : 
        SIZE_TIMESTAMP];
    result[0] = (byte)(year / 100 + 100);
    result[1] = (byte)(year % 100 + 100);
    result[2] = (byte) local.getMonthValue();
    result[3] = (byte) local.getDayOfMonth();
    result[4] = (byte)(local.getHour() + 1);
    result[5] = (byte)(local.getMinute() + 1);
    result[6] = (byte)(local.getSecond() + 1);
    if (nanos > 0) {
      result[7] = (byte)(nanos >> 24);
      result[8] = (byte)((nanos >> 16) & 0xff);
      result[9] = (byte)((nanos >> 8) & 0xff);
      result[10] = (byte)(nanos  & 0xFF);
    }
    return result;
  }

  public static String dateToString(ExceptionFactory f, byte[] bytes) {
    int year = getJavaYear(bytes[0] & 0xff, bytes[1] & 0xff);
    yearError(f, year);
    int month   = bytes[2] & 0xFF;
    int day   = bytes[3] & 0xFF;
    int hours   = (bytes[4] & 0xFF) - 1;
    int minutes = (bytes[5] & 0xFF) - 1;
    int seconds = (bytes[6] & 0xFF) - 1;
    StringBuilder result = new StringBuilder(27);
    appendInt(result, year, 4);
    result.append("-");
    appendInt(result, month, 2);
    result.append("-");
    appendInt(result, day, 2);
    result.append("T");
    appendInt(result, hours, 2);
    result.append(":");
    appendInt(result, minutes, 2);
    result.append(":");
    appendInt(result, seconds, 2);
    return result.toString();
  }

  ///// INTERVALDS
  
  static final int INTERVAL_BYTE_OFFSET = 60;
  static final int INTERVAL_INT_OFFSET= 0x80000000;  
  
  static final int SECONDS_PER_DAY = 86400;
  static final int HOURS_PER_DAY = 24;
  static final int MINUTES_PER_HOUR = 60;
  static final int SECONDS_PER_MINUTE = 60;
  
  public static Duration intervalDSToDuration(byte[] raw) {
    int d = getDaysFromIntervalDS(raw);
    int h = getHoursFromIntervalDS(raw);
    int m = getMinutesFromIntervalDS(raw);
    int s = getSecondsFromIntervalDS(raw);
    int n = getNanosFromIntervalDS(raw);
    long secs = (d * 86400L) + (h * 3600L) + (m * 60L) + s;
    return Duration.ofSeconds(secs, n);
  }
  
  public static String serializeIntervalDS(ExceptionFactory f, byte[] raw) {
    long days = getDaysFromIntervalDS(raw);
    long hrs = getHoursFromIntervalDS(raw);
    long mins = getMinutesFromIntervalDS(raw);
    long secs = getSecondsFromIntervalDS(raw);
    long nanos = getNanosFromIntervalDS(raw);
    int micros = (int) (nanos / 1000);
    nanos = nanos % 1000;
    boolean tim;
    
    if (days < 0 || hrs < 0 || mins < 0 || secs < 0 || micros < 0 || nanos < 0)
      throw OracleJsonExceptions.NOT_IMPLEMENTED.create(f);
    
    tim = (hrs > 0) || (mins > 0) || (secs > 0) || (micros > 0) || (nanos > 0);
    
    StringBuilder result = new StringBuilder();
    result.append("P");
    
    if (days > 0 || !tim) {
      result.append(days);
      result.append("D");
    }
    
    if (tim) {
      result.append("T");
      if (hrs > 0) {
        result.append(hrs).append("H");
      }
      if (mins > 0) {
        result.append(mins).append("M");
      }
      if (secs > 0 || micros > 0 || nanos > 0) {
        result.append(secs);
        if (micros > 0 || nanos > 0) {
          String microsStr = String.valueOf(micros);
          result.append(".");
          for (int i = 0; i < (6-microsStr.length()); i++){
            result.append("0");
          }
          result.append(micros);
          if (nanos > 0) {
            String nanosStr = String.valueOf(nanos);
            for (int i = 0; i < (3-nanosStr.length()); i++){
              result.append("0");
            }
            result.append(nanos);
          }
        }
        result.append("S");
      }
    }
    return result.toString();
  }
  
  public static byte[] durationToIntervalDS(Duration duration) {
    long seconds = duration.getSeconds();
    long days = seconds / SECONDS_PER_DAY;
    long hours = duration.toHours() % HOURS_PER_DAY;
    long min = duration.toMinutes() % MINUTES_PER_HOUR;
    long sec = seconds % SECONDS_PER_MINUTE;
    int frac = duration.getNano();
    int idays = (int)days;
    if (idays != days) {
      throw new IllegalArgumentException();
    }
    byte[] bytes = new byte[11];
    writeIntervalInt(idays, 0, bytes);
    bytes[4] = (byte) (hours + INTERVAL_BYTE_OFFSET);
    bytes[5] = (byte) (min + INTERVAL_BYTE_OFFSET);
    bytes[6] = (byte) (sec + INTERVAL_BYTE_OFFSET);
    writeIntervalInt(frac, 7, bytes);
    return bytes;
  }
  
  private static void writeIntervalInt(int value, int offset, byte[] bytes) {
    value += INTERVAL_INT_OFFSET;
    bytes[offset++] = (byte) ((0xFF_00_00_00 & value) >> 24);
    bytes[offset++] = (byte) ((0x00_FF_00_00 & value) >> 16);
    bytes[offset++] = (byte) ((0x00_00_FF_00 & value) >> 8);
    bytes[offset++] = (byte)  (0x00_00_00_FF & value);
  }

  static int getDaysFromIntervalDS(byte[] bytes) {
    return ((bytes[0] & 0xFF) << 24
         | (bytes[1] & 0xFF) << 16
         | (bytes[2] & 0xFF) << 8
         | (bytes[3] & 0xFF))
         - INTERVAL_INT_OFFSET;
  }
  
  static int getHoursFromIntervalDS(byte[] bytes) {
    return bytes[4] - INTERVAL_BYTE_OFFSET;
  }
  
  static int getMinutesFromIntervalDS(byte[] bytes) {
    return bytes[5] - INTERVAL_BYTE_OFFSET;
  }
  
  static int getSecondsFromIntervalDS(byte[] bytes) {
    return bytes[6] - INTERVAL_BYTE_OFFSET;
  }
  
  static int getNanosFromIntervalDS(byte[] bytes) {
    return ((bytes[7] & 0xFF) << 24
        | (bytes[8] & 0xFF) << 16
        | (bytes[9] & 0xFF) << 8
        | (bytes[10] & 0xFF))
       - INTERVAL_INT_OFFSET;
  }

  //// INTERVALYM
  
  public static Period intervalYMToPeriod(byte[] raw) {
    int years = getYearFromIntervalYM(raw);
    int months = getMonthFromIntervalYM(raw);
    return Period.of(years, months, 0);
  }
  
  public static byte[] periodToIntervalYM(ExceptionFactory f, Period p) {
    
    int years = p.getYears();
    int months = p.getMonths();
    if (months > 11) {
      throw OracleJsonExceptions.NOT_IMPLEMENTED.create(f);
    }

    if (years < 0 || months < 0) {
      throw OracleJsonExceptions.NOT_IMPLEMENTED.create(f);
    }
    if (p.getDays() != 0) {
      throw OracleJsonExceptions.NO_DAYS_ALLOWED.create(f);
    }
    
    
    byte[] result = new byte[OracleJsonIntervalYMImpl.INTERVALYM_LEN];
    writeIntervalInt(p.getYears(), 0, result);
    result[4] = (byte)(p.getMonths() + INTERVAL_BYTE_OFFSET);
    return result;
    
  }

  private static int getMonthFromIntervalYM(byte[] raw) {
    return raw[4] - INTERVAL_BYTE_OFFSET;
  }

  private static int getYearFromIntervalYM(byte[] raw) {
    return ((raw[0] & 0xFF) << 24
         |(raw[1] & 0xFF) << 16
         |(raw[2] & 0xFF) << 8 
         |(raw[3] & 0xFF)) 
         - INTERVAL_INT_OFFSET;
  }
  
  public static String serializeIntervalYM(ExceptionFactory f, byte[] raw) {
    int years = getYearFromIntervalYM(raw);
    int months = getMonthFromIntervalYM(raw);
    if (years < 0 || months < 0) {
      throw OracleJsonExceptions.NOT_IMPLEMENTED.create(f);
    }
    StringBuilder result = new StringBuilder();
    result.append('P');
    if ((years > 0) || (months == 0))
    {
      result.append(years).append('Y');
    }
    if (months > 0)
    {
      result.append(months).append('M');
    }
    return result.toString();
  }
  
  /** TIMESTAMP */
  private static final int getNanos(byte[] buffer, int off)
  {
    int nanos = (buffer[off] & 0xFF) << 24;
    nanos |= (buffer[off+1] & 0xFF) << 16;
    nanos |= (buffer[off+2] & 0xFF) << 8;
    nanos |= (buffer[off+3] & 0xFF) & 0xff;
    return nanos;
  }
  
  /** TIMESTAMP */
  private static int getJavaYear(
      int cent,
      int decade) {

      int year = ((cent - 100) * 100 + (decade - 100));

      if (year < 0) {
        year++;
      }

      return year;
    }
  
  /** BINARY_DOUBLE */
  static byte[] doubleToCanonicalFormatBytes(double _d)
  {
    double d = _d;

    if (d == 0.0)
      d = 0.0;       // coerce no negative zero
    else if (d != d)
      d = Double.NaN;// coerce standard NaN

    long longBits = Double.doubleToLongBits(d);
    byte[] b = new byte[8];
    int lowInt = (int) longBits;
    int highInt = (int) (longBits >> 32);

    int b7 = 
      lowInt;// no need to mask off a byte -- the cast to byte below takes care of that.

    lowInt = lowInt >> 8;

    int b6 = lowInt;

    lowInt = lowInt >> 8;

    int b5 = lowInt;

    lowInt = lowInt >> 8;

    int b4 = lowInt;

    int b3 = highInt;

    highInt = highInt >> 8;

    int b2 = highInt;

    highInt = highInt >> 8;

    int b1 = highInt;

    highInt = highInt >> 8;

    int b0 = highInt;

    if ((b0 & 0x80) == 0)
    {
      b0 = b0 | 0x80;
    }
    else
    {
      b0 = ~b0;
      b1 = ~b1;
      b2 = ~b2;
      b3 = ~b3;
      b4 = ~b4;
      b5 = ~b5;
      b6 = ~b6;
      b7 = ~b7;
    }

    b[7] = (byte) b7;
    b[6] = (byte) b6;
    b[5] = (byte) b5;
    b[4] = (byte) b4;
    b[3] = (byte) b3;
    b[2] = (byte) b2;
    b[1] = (byte) b1;
    b[0] = (byte) b0;

    return  b ;
  }
  
  /** BINARY_DOUBLE */
  static double canonicalFormatBytesToDouble(byte[] b)
  {
    int b0 = (int) b[0];// mask once below
    int b1 = (int) b[1];
    int b2 = (int) b[2];
    int b3 = (int) b[3];
    int b4 = (int) b[4];
    int b5 = (int) b[5];
    int b6 = (int) b[6];
    int b7 = (int) b[7];

    if ((b0 & 0x80) != 0)
    {
      b0 = b0 & 0x7F;
      b1 = b1 & 0xFF;
      b2 = b2 & 0xFF;
      b3 = b3 & 0xFF;
      b4 = b4 & 0xFF;
      b5 = b5 & 0xFF;
      b6 = b6 & 0xFF;
      b7 = b7 & 0xFF;
    }
    else
    {
      b0 = ~b0 & 0xFF;
      b1 = ~b1 & 0xFF;
      b2 = ~b2 & 0xFF;
      b3 = ~b3 & 0xFF;
      b4 = ~b4 & 0xFF;
      b5 = ~b5 & 0xFF;
      b6 = ~b6 & 0xFF;
      b7 = ~b7 & 0xFF;
    }

    int hiBits = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    int loBits = (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
    long longBits = ((long) hiBits << 32) | ((long) loBits & 0xFFFFFFFFL);

    return  java.lang.Double.longBitsToDouble(longBits); 
  }
  
  /** BINARY_FLOAT */
  static byte[] floatToCanonicalFormatBytes(float _f)
  {
    float f = _f;

    if (f == 0.0f)
      f = 0.0f;     // coerce no negative zero
    else if (f != f)
      f = Float.NaN;// coerce standard NaN

    int intBits = Float.floatToIntBits(f);
    byte[] b = new byte[4];

    int b3 = intBits;

    intBits = intBits >> 8;

    int b2 = intBits;

    intBits = intBits >> 8;

    int b1 = intBits;

    intBits = intBits >> 8;

    int b0 = intBits;

    if ((b0 & 0x80) == 0)
      b0 = b0 | 0x80;
    else
    {
      b0 = ~b0;
      b1 = ~b1;
      b2 = ~b2;
      b3 = ~b3;
    }

    b[3] = (byte) b3;
    b[2] = (byte) b2;
    b[1] = (byte) b1;
    b[0] = (byte) b0;

    return  b;
  }

  /** BINARY_FLOAT */
  static float canonicalFormatBytesToFloat(byte[] b)
  {
    int b0 = (int) b[0];// mask once below
    int b1 = (int) b[1];
    int b2 = (int) b[2];
    int b3 = (int) b[3];

    if ((b0 & 0x80) != 0)
    {
      b0 = b0 & 0x7F;
      b1 = b1 & 0xFF;
      b2 = b2 & 0xFF;
      b3 = b3 & 0xFF;
    }
    else
    {
      b0 = ~b0 & 0xFF;
      b1 = ~b1 & 0xFF;
      b2 = ~b2 & 0xFF;
      b3 = ~b3 & 0xFF;
    }

    int intBits = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;

    return  java.lang.Float.intBitsToFloat(intBits); 
  }
  
  /** NUMBER */
  public static byte[] toNumber(BigDecimal BigDecNum)
  {

    if (BigDecNum == null) {
      throw new IllegalArgumentException();
    }

    byte[]  mantissa = new byte[HUNDIGMAX];
    long[]  bnum = new long[BIGINTARRAYMAX]; 
    long    digit[] = new long[BIGLENMAX];
    byte    digidx = DIGEND; 
    byte    bidx = 0;
    int     blen; 
    byte    dstart = DIGEND;
    long    value;
    int     mantlen;
    int     oidx = 0;
    byte[]  temp;
    boolean positive;
    int    exponent = 0;
    int    i;    
    int scale;
    BigDecimal BDABS = BigDecNum.abs();
    int rad;
    int leftdigs = 0;

    // Special case: zero? 
    // Note that -0 != 0. 
    // Replaced compareTo() with signum() for Jdbc driver optimization
 
    if (BigDecNum.signum() == 0) { 
      return _makeZero();
    }

    // Get sign for later
    positive = (BigDecNum.signum() == -1)? false:true;

    // keep scale for exponent calculation
    scale = BigDecNum.scale();

    /*JDK 1.5 allowed negative scale for a given java BigDecimal
      object which was not the case earlier. Because of this 
      negative scale we ended up in wrong calculation of 
      BigInteger oject and subsequent calculation of number byte array.
      For the solution to this, use a equivalent BigDecimal object
      with the scale zero.
    */
    if (scale < 0) {
      BigDecNum = BigDecNum.setScale(0);
      scale =0;
    }

    // Calculate the number of decimal digits
    rad = (BDABS).compareTo(BigDecimal.valueOf(1));
    int moves = 0;
    BigDecimal DBTMP;
    
    if (rad == -1) {
      do {
        moves++;
        DBTMP = BDABS.movePointRight(moves);
      } while (DBTMP.compareTo(BigDecimal.valueOf(1)) < 0);
          
      leftdigs = -moves; 
    }
    else {
      do {
        moves++;
        DBTMP = BDABS.movePointLeft(moves); 
      } while (DBTMP.compareTo(BigDecimal.valueOf(1)) >= 0);

      leftdigs = moves;
    }

    // Produce the BigInteger in binary format
    temp = ((BDABS.movePointRight(scale)).toBigInteger()).toByteArray();

    // TOO MUCH
    if (temp.length > BIGINTARRAYMAX) {
      throw new IllegalArgumentException();
    }

    // Process the rest like a BigInteger

    // We need to turn temp into unsigned values from char []

    for (i = 0; i < temp.length; i++) {
      if(temp[i] < 0) {
        bnum[i] = (long)(temp[i] + 256);
      }
      else {
        bnum[i] = (long)temp[i];
      }
    }

    blen = temp.length;

    // Initialize the value
    switch (blen % 3) {
      case 2:
        digit[digidx] = (bnum[bidx] << 8) + bnum[bidx+1];
        bidx += 2;
        blen -= 2;
        break;
      case 1:
        digit[digidx] = bnum[bidx];
        bidx++;
        blen--;
        break;
      default:
        value = (bnum[bidx] << 16) + (bnum[bidx+1] << 8) + bnum[bidx+2];
        digit[digidx] = value % 1000000;
        digit[digidx -1] = value/1000000;
        dstart -= ((digit[digidx -1] != 0) ? 1:0);
        bidx += 3;
        blen -= 3;
        break;
      }

    // build up the result using 3 byte digits at a time
    while(blen != 0) {
      value = (bnum[bidx] << 4) + (bnum[bidx +1] >> 4);

      for(digidx = DIGEND; digidx >= dstart; digidx--) {
        value += digit[digidx] << 12;
        digit[digidx] = value % 1000000;
        value /= 1000000;
      }
      
      if (value != 0)
      {
        dstart--;
        digit[dstart] = value;
      }

      value = ((bnum[bidx+1] & (long)0X0f) << 8) + bnum[bidx+2];

      for (digidx = DIGEND; digidx >= dstart; digidx--) {
        value += digit[digidx] << 12;
        digit[digidx] = value % 1000000;
        value /= 1000000;
      }

      if (value != 0)
      {
        dstart--;
        digit[dstart] = value;
      }

      bidx += 3;
      blen -= 3;
    }

    // Process the Oracle Digits and set the length

    if ((mantissa[oidx] = (byte)(digit[dstart]/10000)) != 0) {
      mantlen = 3*(DIGEND - dstart) + 3;
      mantissa[oidx+1] = (byte)((digit[dstart] % 10000)/100);
      mantissa[oidx+2] = (byte)(digit[dstart] % 100);
      oidx += 3;
    }
    else {
      if ((mantissa[oidx] = (byte)((digit[dstart] % 10000)/100)) != 0) {
        mantlen = 3*(DIGEND - dstart) + 2;
        mantissa[oidx+1] = (byte)(digit[dstart] % 100);
        oidx += 2;
      }
      else {
        mantissa[oidx] = (byte)(digit[dstart]);
        mantlen = 3*(DIGEND - dstart) + 1;
        oidx++;
      }
    }

    // Store the remaining mantissa digits
    for (digidx = (byte)(dstart + 1); digidx <= DIGEND; digidx++) {
      mantissa[oidx] = (byte)(digit[digidx]/10000);
      mantissa[oidx+1] = (byte)((digit[digidx] % 10000)/100);
      mantissa[oidx+2] = (byte)(digit[digidx] % 100);
      oidx += 3;
    }
    
    // Possibly have trailing zeros here.
    for (i = (oidx - 1); i >= 0; --i) {
      if (mantissa[i] == 0) {
        mantlen--;
      }
      else {
        break;
      }
    }
    
    // May need to shift the mantissa buffer one to the right
    if (scale > 0 && ((scale & 1) != 0)) {
      int len = mantlen;
      byte[] buf = new byte[len+1];

      if (mantissa[0] <= 9 ) {
        for (i = 0; i < len - 1; i++) {
          buf[i] = (byte)(((mantissa[i] % 10) * 10) + (mantissa[i + 1]/10));
        }

        buf[i] = (byte)((mantissa[i] % 10) * 10 );

        if (buf[len - 1] == 0) {
          mantlen--;
        }
      }
      else {
        buf[len] = (byte)((mantissa[len -1] % 10) * 10);

        for (i = len -1; i > 0; i--) {
          buf[i] = (byte)((mantissa[i]/10) + ((mantissa[i - 1] % 10) * 10));
        }

        buf[i] = (byte)(mantissa[i]/10);

        if (buf[len] > 0) {
          mantlen++;
        }
      }
      System.arraycopy(buf, 0, mantissa, 0, mantlen);
    }
 
    // We need to round
    if (mantlen > LNXDIGS) {
      i = LNXDIGS;
      mantlen = LNXDIGS;

      if (mantissa[i] >= 50) {
        i--;
        mantissa[i]++;

        while (mantissa[i] == LNXBASE) {
          if (i == 0) {
            leftdigs++;
            mantissa[i] = 1;
            break;
          }

          mantissa[i] = 0;
          i--;
          mantissa[i]++;
        }
      }

      // Need to check for trailing zeros caused by rounding
      for (i = mantlen -1; i >= 0 ; i--) {
        if (mantissa[i] == 0) {
          mantlen--;
        }
        else {
          break;
        }
      }
    }

    if (leftdigs <= 0) {
      if (mantissa[0] < 10) {
        exponent = -(2 - leftdigs)/2 + 1;
      }
      else {
        exponent = -(2 - leftdigs)/2;
      }
    }
    else {
      exponent = (leftdigs -1)/2;
    }

    if (exponent > (LNXEXPMX - LNXEXPBS - 1)) {
      throw new IllegalArgumentException(); // overflow
    }

    if (exponent < (LNXEXPMN - LNXEXPBS - 1)) {
      throw new IllegalArgumentException(); // underflow
    }

    byte[] oranum = new byte[mantlen + 1];
    oranum[0] = (byte)exponent;
    System.arraycopy(mantissa, 0, oranum, 1, mantlen);

    // convert to oracle number format before returning
    return _toLnxFmt(oranum, positive);
  } // toBytes(BigDecimal)
  
  /** NUMBER */
  public static byte[] toNumber(BigInteger BigIntNum)
  {

    if (BigIntNum == null) {
      throw new IllegalArgumentException(); // underflow
    }

    byte[]  mantissa = new byte[HUNDIGMAX];
    long[]  bnum = new long[BIGINTARRAYMAX]; 
    long    digit[] = new long[BIGLENMAX];
    byte    digidx = DIGEND; 
    byte    bidx = 0;
    int     blen; 
    byte    dstart = DIGEND;
    long    value;
    int     mantlen;
    int     oidx = 0;
    byte[]  temp;
    boolean positive = true;
    int    exponent;
    int    i;

    // Special case for Zero
    // Replaced comparedTo() with signum() for Jdbc driver optimization

    if (BigIntNum.signum() == 0) {
      return _makeZero();
    }

    // deal with positive numbers
    // retrieve array and calculate the number of decimal digits
    if (BigIntNum.signum() == -1) {
      BigInteger Num = BigIntNum.abs();
      positive = false;
      temp = Num.toByteArray();
      exponent = (int)Math.floor((Num.bitLength())*.1505149978319905976D);
    }
    else {
      temp = BigIntNum.toByteArray();
      exponent = (int)Math.floor((BigIntNum.bitLength())*.1505149978319905976D);
    }

    // Bug 1271410
    // This exponent found using the above expression 
    // is just an upperbound.
      
    // Can fail for numbers 2^6 - 99, 2^13 - 10000 etc.        
    // Example : For 66, the exponent returned using above expr.
    // is 1.But the exponent for base 100 is 0.
    // Hence the following check 

    if (BigIntNum.abs().compareTo(BigInteger.valueOf(100).pow(exponent)) < 0) {
      exponent--;
    }

    if (temp.length > BIGINTARRAYMAX) {
      throw new IllegalArgumentException(); // overflow
    }

    // We need to turn temp into unsigned values from char []
    for (i = 0; i < temp.length; i++) {
      if (temp[i] < 0) {
        bnum[i] = (long)(temp[i] + 256);
      }
      else {
        bnum[i] = (long)temp[i];
      }
    }

    blen = temp.length;

    // Initialize the value
    switch(blen % 3) {
      case 2:
        digit[digidx] = (bnum[bidx] << 8) + bnum[bidx+1];
        bidx += 2;
        blen -= 2;
        break;
      case 1:
        digit[digidx] = bnum[bidx];
        bidx++;
        blen--;
        break;
      default:
        value = (bnum[bidx] << 16) + (bnum[bidx+1] << 8) + bnum[bidx+2];
        digit[digidx] = value % 1000000;
        digit[digidx -1] = value/1000000;
        dstart -= ((digit[digidx -1] != 0) ? 1:0);
        bidx += 3;
        blen -= 3;
        break;
    }

    // build up the result using 3 byte digits at a time
    while (blen != 0) {
      value = (bnum[bidx] << 4) + (bnum[bidx +1] >> 4);

      for (digidx = DIGEND; digidx >= dstart; digidx--) {
        value += digit[digidx] << 12;
        digit[digidx] = value % 1000000;
        value /= 1000000;
      }
      
      if (value != 0) {
        dstart--;
        digit[dstart] = value;
      }

      value = ((bnum[bidx+1] & (long)0X0f) << 8) + bnum[bidx+2];

      for (digidx = DIGEND; digidx >= dstart; digidx--) {
        value += digit[digidx] << 12;
        digit[digidx] = value % 1000000;
        value /= 1000000;
      }

      if (value != 0) {
        dstart--;
        digit[dstart] = value;
      }

      bidx += 3;
      blen -= 3;
    }

    // Process the Oracle Digits and set the length
    if ((mantissa[oidx] = (byte)(digit[dstart]/10000)) != 0) {
      mantlen = 3*(DIGEND - dstart) + 3;
      mantissa[oidx+1] = (byte)((digit[dstart] % 10000)/100);
      mantissa[oidx+2] = (byte)(digit[dstart] % 100);
      oidx += 3;
    }
    else {
      if ((mantissa[oidx] = (byte)((digit[dstart] % 10000)/100)) != 0) {
        mantlen = 3*(DIGEND - dstart) + 2;
        mantissa[oidx+1] = (byte)(digit[dstart] % 100);
        oidx += 2;
      }
      else {
        mantissa[oidx] = (byte)(digit[dstart]);
        mantlen = 3*(DIGEND - dstart) + 1;
        oidx++;
      }
    }

    // Store the remaining mantissa digits
    for (digidx = (byte)(dstart + 1); digidx <= DIGEND; digidx++) {
      mantissa[oidx] = (byte)(digit[digidx]/10000);
      mantissa[oidx+1] = (byte)((digit[digidx] % 10000)/100);
      mantissa[oidx+2] = (byte)(digit[digidx] % 100);
      oidx += 3;
    }
    
    // Possibly have trailing zeros here.
    for (i = oidx - 1; i >= 0; --i) {
      if (mantissa[i] == 0) {
        mantlen--;
      }
      else {
        break;
      }
    }
    
    // We need to round
    if (mantlen > LNXDIGS - 1) {
      i = LNXDIGS;
      mantlen = LNXDIGS -1;

      if (mantissa[i] >= 50) {
        i--;
        mantissa[i]++;
        while (mantissa[i] == LNXBASE) {
          if (i == 0) {
            exponent++;
            mantissa[i] = 1;
            break;
          }

          mantissa[i] = 0;
          i--;
          mantissa[i]++;
        }

        // Need to check for trailing zeros caused by rounding
        for (i = mantlen -1; i >= 0 ; i--) {
          if (mantissa[i] == 0) {
            mantlen--;
          }
          else {
            break;
          }
        }
      }
    }

    if (exponent > (LNXEXPMX - LNXEXPBS - 1)) {
      throw new IllegalArgumentException(); // Overflow
    }

    // create array of appropriate length with exponent and mantissa
    byte[] oranum = new byte[mantlen + 1];
    oranum[0] = (byte)exponent;
    System.arraycopy(mantissa, 0, oranum, 1, mantlen);

    // convert to oracle number format before returning
    return _toLnxFmt(oranum, positive);
  } // toBytes(BigInteger)

  
  /** NUMBER */
  static byte[] _makeZero()
  {
    byte[] num = new byte[1];

    // Assumes length separate format
    num[0] = (byte)LNXSGNBT;

    return num;
  } // _makeZero()
  
  /** NUMBER */
  static byte[] _toLnxFmt(byte[] num, boolean pos)
  {
    byte[] tmp;
    int i;
    int numl = num.length;

    // Oracle Numbers have special representation for the mantissa and exponent
    // of positive and negative numbers.

    if (pos)  // positive
      {
        tmp = new byte[numl];
        tmp[0] = (byte)(num[0] + LNXSGNBT + LNXEXPBS + 1);

        for (i = 1; i < numl; i++) 
          tmp[i] = (byte)(num[i] + 1);
      }
    else
      {
        // If numl == MAXBYTES then there will be no trailing 102.

        if ((numl - 1) < LNXDIGS)
          tmp = new byte[numl + 1];
        else
          tmp = new byte[numl];

        tmp[0] = (byte)( ~(num[0] + LNXSGNBT + LNXEXPBS + 1) );

        for (i = 1; i < numl; i++)
          tmp[i] = (byte)((LNXBASE + 1) - num[i]);

        if (i <= LNXDIGS)
          tmp[i] = (byte)(LNXBASE + 2);
      }

    return tmp;
  } // _toLnxFmt()

  /** NUMBER */
  static byte[] toNumber(int value) {
    return lnxmin(value);
  }
  
  /** NUMBER */
  public static byte[] toNumber(long value) {
    return lnxmin(value);
  }  
  
  /** LnxLibThin */
  public static byte[] lnxmin(long longNum) {
    if (longNum <= Integer.MAX_VALUE && longNum >= Integer.MIN_VALUE)
      return lnxmin32((int)longNum);
    else
      return lnxmin64(longNum);
  }
  
  /** LnxLibThin */
  private static byte[] lnxmin32(final int val) {
    final byte[] b;
    int x;
    
    if (val == 0) {
      b = new byte[1];
      b[0] = (byte) LNXSGNBT;
    }
    else if (val < 0) {
      if (val == Integer.MIN_VALUE) {
        b = new byte[7];
        encodeIntMinValue(b, 0);
      }
      else if (-val < 100) {
        b = new byte[3];
        b[0] = (byte) (LNXEXPBS - 2);
        b[1] = (byte) (101 + val);
        b[2] = (byte) 102;
      }
      else if (-val < 10000) {
        x = (-val) % 100;

        if (x != 0) {
          b = new byte[4];
          b[2] = (byte) (101 - x);
          b[3] = (byte) 102;
        }
        else {
          b = new byte[3];
          b[2] = (byte) 102;
        }

        b[0] = (byte) (LNXEXPBS - 3);
        b[1] = (byte) (101 - (-val / 100));
      }
      else if (-val < 1000000) {
        x = (-val) % 100;

        if (x != 0) {
          b = new byte[5];
          b[2] = (byte) (101 - (((-val) % 10000) / 100));
          b[3] = (byte) (101 - x);
          b[4] = (byte) 102;
        }
        else {
          x = ((-val) % 10000) / 100;

          if (x != 0) {
            b = new byte[4];
            b[2] = (byte) (101 - x);
            b[3] = (byte) 102;
          }
          else {
            b = new byte[3];
            b[2] = (byte) 102;
          }
        }

        b[0] = (byte) (LNXEXPBS - 4);
        b[1] = (byte) (101 - (-val / 10000));
      }
      else if (-val < 100000000) {
        x = (-val) % 100;

        if (x != 0) {
          b = new byte[6];
          b[2] = (byte) (101 - (((-val) % 1000000) / 10000));
          b[3] = (byte) (101 - (((-val) % 10000) / 100));
          b[4] = (byte) (101 - x);
          b[5] = (byte) 102;
        }
        else {
          x = ((-val) % 10000) / 100;

          if (x != 0) {
            b = new byte[5];
            b[2] = (byte) (101 - (((-val) % 1000000) / 10000));
            b[3] = (byte) (101 - x);
            b[4] = (byte) 102;
          }
          else {
            x = ((-val) % 1000000) / 10000;

            if (x != 0) {
              b = new byte[4];
              b[2] = (byte) (101 - x);
              b[3] = (byte) 102;
            }
            else {
              b = new byte[3];
              b[2] = (byte) 102;
            }
          }
        }

        b[0] = (byte) (LNXEXPBS - 5);
        b[1] = (byte) (101 - (-val / 1000000));
      }
      else {
        x = (-val) % 100;

        if (x != 0) {
          b = new byte[7];
          b[2] = (byte) (101 - (((-val) % 100000000) / 1000000));
          b[3] = (byte) (101 - (((-val) % 1000000) / 10000));
          b[4] = (byte) (101 - (((-val) % 10000) / 100));
          b[5] = (byte) (101 - x);
          b[6] = (byte) 102;
        }
        else {
          x = ((-val) % 10000) / 100;

          if (x != 0) {
            b = new byte[6];
            b[2] = (byte) (101 - (((-val) % 100000000) / 1000000));
            b[3] = (byte) (101 - (((-val) % 1000000) / 10000));
            b[4] = (byte) (101 - x);
            b[5] = (byte) 102;
          }
          else {
            x = ((-val) % 1000000) / 10000;

            if (x != 0) {
              b = new byte[5];
              b[2] = (byte) (101 - (((-val) % 100000000) / 1000000));
              b[3] = (byte) (101 - x);
              b[4] = (byte) 102;
            }
            else {
              x = ((-val) % 100000000) / 1000000;

              if (x != 0) {
                b = new byte[4];
                b[2] = (byte) (101 - x);
                b[3] = (byte) 102;
              }
              else {
                b = new byte[3];
                b[2] = (byte) 102;
              }
            }
          }
        }

        b[0] = (byte) (LNXEXPBS - 6);
        b[1] = (byte) (101 - (-val / 100000000));
      }
    }
    else {
      if (val < 100) {
        b = new byte[2];
        b[0] = (byte) (LNXSGNBT + LNXEXPBS + 1);
        b[1] = (byte) (val + 1);
      }
      else if (val < 10000) {
        x = val % 100;

        if (x != 0) {
          b = new byte[3];
          b[2] = (byte) (x + 1);
        }
        else {
          b = new byte[2];
        }
        b[0] = (byte) (LNXSGNBT + LNXEXPBS + 2);
        b[1] = (byte) (val / 100 + 1);
      }
      else if (val < 1000000) {
        x = val % 100;

        if (x != 0) {
          b = new byte[4];
          b[2] = (byte) ((val % 10000) / 100 + 1);
          b[3] = (byte) (x + 1);
        }
        else {
          x = (val % 10000) / 100;

          if (x != 0) {
            b = new byte[3];
            b[2] = (byte) (x + 1);
          }
          else {
            b = new byte[2];
          }
        }
        b[0] = (byte) (LNXSGNBT + LNXEXPBS + 3);
        b[1] = (byte) (val / 10000 + 1);
      }
      else if (val < 100000000) {
        x = val % 100;

        if (x != 0) {
          b = new byte[5];
          b[2] = (byte) ((val % 1000000) / 10000 + 1);
          b[3] = (byte) ((val % 10000) / 100 + 1);
          b[4] = (byte) (x + 1);
        }
        else {
          x = (val % 10000) / 100;

          if (x != 0) {
            b = new byte[4];
            b[2] = (byte) ((val % 1000000) / 10000 + 1);
            b[3] = (byte) (x + 1);
          }
          else {
            x = (val % 1000000) / 10000;

            if (x != 0) {
              b = new byte[3];
              b[2] = (byte) (x + 1);
            }
            else {
              b = new byte[2];
            }
          }
        }
        b[0] = (byte) (LNXSGNBT + LNXEXPBS + 4);
        b[1] = (byte) (val / 1000000 + 1);
      }
      else {
        x = val % 100;

        if (x != 0) {
          b = new byte[6];
          b[2] = (byte) ((val % 100000000) / 1000000 + 1);
          b[3] = (byte) ((val % 1000000) / 10000 + 1);
          b[4] = (byte) ((val % 10000) / 100 + 1);
          b[5] = (byte) (x + 1);
        }
        else {
          x = (val % 10000) / 100;

          if (x != 0) {
            b = new byte[5];
            b[2] = (byte) ((val % 100000000) / 1000000 + 1);
            b[3] = (byte) ((val % 1000000) / 10000 + 1);
            b[4] = (byte) (x + 1);
          }
          else {
            x = (val % 1000000) / 10000;

            if (x != 0) {
              b = new byte[4];
              b[2] = (byte) ((val % 100000000) / 1000000 + 1);
              b[3] = (byte) (x + 1);
            }
            else {
              x = (val % 100000000) / 1000000;

              if (x != 0) {
                b = new byte[3];
                b[2] = (byte) (x + 1);
              }
              else {
                b = new byte[2];
              }
            }
          }
        }
        b[0] = (byte) (LNXSGNBT + LNXEXPBS + 5);
        b[1] = (byte) (val / 100000000 + 1);
      }
    }

    return b;
  }
  
  /** LnxLibThin */
  private static void encodeIntMinValue(byte[] b, int offset) {
    b[offset] = (byte) (LNXEXPBS - 6);
    b[offset + 1] = (byte) (101 + Integer.MIN_VALUE / 100000000);
    b[offset + 2] =
      (byte) (101 + (Integer.MIN_VALUE % 100000000) / 1000000);
    b[offset + 3] = (byte) (101 + (Integer.MIN_VALUE % 1000000) / 10000);
    b[offset + 4] = (byte) (101 + (Integer.MIN_VALUE % 10000) / 100);
    b[offset + 5] = (byte) (101 + Integer.MIN_VALUE % 100);
    b[offset + 6] = (byte) 102;
  }
  
  /** LnxLibThin */
  private static byte[] lnxmin64(long val) {
    final boolean sign;
    if (sign = val >= 0)
      val = -val; // Force a positive value to be negative.
    
    final int exponent = log100(val);
    
    // Find the least significant non-zero digit.
    int digits = exponent;
    long leastDigit;
    long qL = val /100;
    if (0 == (leastDigit = (qL * 100) - val)) {
      do {
        digits--;
        val = qL;
        qL = val / 100;
      } while (0 == (leastDigit = (qL * 100) - val));
      
      if (digits == 0) {
        if (sign) {
          byte[] dst = new byte[2];
          lnxminEncodeP1((int) leastDigit, exponent, dst, 0);
          return dst;
        }
        else {
          byte[] dst = new byte[3];
          lnxminEncodeN1((int) leastDigit, exponent, dst, 0);
          return dst;
        }
      }
    }
    
    if (sign) {
      byte[] dst = new byte[digits + 2];
      lnxminEncodeP(qL, exponent, digits, (int)leastDigit, dst, 0);
      return dst;
    }
    else {
      byte[] dst = new byte[digits + 3];
      lnxminEncodeN(qL, exponent, digits, (int)leastDigit, dst, 0);
      return dst;
    }
  }
  
  /** LnxLibThin */
  private static int log100(long longNum) {
    return  longNum > -10000000000L ? 4
           : longNum > -1000000000000L ? 5 
           : longNum > -100000000000000L ? 6
           : longNum > -10000000000000000L ? 7
           : longNum > -1000000000000000000L ? 8 
           : 9;
  }
  
  /** LnxLibThin */
  private static void lnxminEncodeP1(int digit, int exponent, byte[] dst, int offset) {
    dst[offset] = (byte) (193 + exponent);
    dst[offset + 1] = (byte) (1 + digit);
  }
  
  /** LnxLibThin */
  private static void lnxminEncodeN1(int digit, int exponent, byte[] dst, int offset) {
    dst[offset] = (byte) (62 - exponent);
    dst[offset + 1] = (byte) (101 - digit);
    dst[offset + 2] = (byte) 102;
  }
  
  /** LnxLibThin */
  private static void lnxminEncodeP(long val, int exponent, int digits, int leastDigit, byte[] dst, int offset) {
    dst[offset] = (byte) (LNXSGNBT + LNXEXPBS + 1 + exponent);
    
    int pos = offset + digits + 1;
    dst[pos--] = (byte) (1 + leastDigit);
    

    // Extract base 100 digits using 64-bit arithmetic. When the number of 
    // remaining digits reaches 4, execution moves to the 32-bit loop (any
    // 4 digit number can be represented as an int).
    long qL;
    while (digits-- > 4) {
      qL = val / 100;
      dst[pos--] = (byte) (1 + (qL * 100) - val);
      val = qL;
    }
    
    int qI;
    int val32 = (int) val;
    while (val32 <= -100) {
      qI = val32 / 100;
      dst[pos--] = (byte) (1 + (qI * 100) - val32);
      val32 = qI;
    }
    
    dst[pos] = (byte) (1 - val32);
  }
  
  /** LnxLibThin */
  private static void lnxminEncodeN(long val, int exponent, int digits, int leastDigit,
                             byte[] dst, int offset) {
    dst[offset] = (byte) (LNXEXPBS - (2 + exponent));
    
    int pos = offset + digits + 2;
    dst[pos--] = (byte) 102;
    dst[pos--] = (byte) (101 - leastDigit);
    
    // Extract base 100 digits using 64-bit arithmetic. When the number of 
    // remaining digits reaches 4, execution moves to the 32-bit loop (any
    // 4 digit number can be represented as an int).
    long qL;
    while (digits-- > 4) {
      qL = val / 100;
      dst[pos--] = (byte) (101 - ((qL * 100) - val));
      val = qL;
    }
    
    int qI;
    int val32 = (int) val;
    while (val32 <= -100) {
      qI = val32 / 100;
      dst[pos--] = (byte) (101 - ((qI * 100) - val32));
      val32 = qI;
    }
    
    dst[pos] = (byte) (101 + val32);
  }
  
  /**
   * Optimizes case where mantissa can fit in a Java long
   * (8 bytes).  Returns null if specified oranum is too large.
   * 
   * Description of Oracle number is here:
   * /RDBMS_MAIN_WINDOWS.X64/oracore3/src/corehto/lnx/inc/lnx.h
   */
  public static BigDecimal toBigDecimalLong(byte[] num) {
    // 9 * 2 = 18 digits that can safely fit in long.
    int mantlen = getOraNumLength(num) -1;
    if (mantlen > MAX_LONG_BASE100_DIGITS) {
      // This case would get caught as an ArithmeticException
      // while unpacking the mantissa below and handled correctly
      // but we do not want to rely on exceptions for control flow.
      return null;
    }
    
    // assumes base100 [dig].[dig][dig]
    int scale = getScale(num) - mantlen + 1;
    
    // See "Case 1" below
    if (mantlen + scale > MAX_LONG_BASE100_DIGITS) {
      // This case would get caught as an ArithmeticException
      // during "Case 1" below and handled correctly but we do
      // not want to rely on exceptions for control flow.
      return null;
    }
    
    // Unpack mantissa into a Java long
    long mantissa = 0;
    try {
      long n = 1;
      for (int i = mantlen; i > 0; i--) {
        mantissa += Math.multiplyExact(unpackBase100(num, i), n);
        n *= 100;
      }
    } catch (ArithmeticException e) {
      // overflow, should not happen
      return null;
    }
    
    int baseTenScale = scale * 2;
    
    // Scale gets adjusted in two cases:
    
    // Case 1: scale > 0
    //    Unfortunately, to keep compatibility with movePointRight()
    //    call in toBigDecimalFull() set the scale to 0 when scale 
    //    is > 0. Otherwise "100" is 1E2 rather than 100E0. This will 
    //    prevent the optimization for simple cases like 12E22.
    while (baseTenScale > 0) {
      try {
        mantissa = Math.multiplyExact(mantissa, 10);
      } catch (ArithmeticException e) {
        // overflow, should not happen
        return null;
      }
      baseTenScale--;
    } 
      
    // Case 2: scale < 0
    //    To keep compatibility with bug 1506268, we trim tailing zeros
    while (baseTenScale < 0 && mantissa % 10 == 0) {
      mantissa /= 10; // trim 0
      baseTenScale++;
    }
    
    if (!_isPositive(num)) {
      mantissa = -mantissa;
    }
    return BigDecimal.valueOf(mantissa, -baseTenScale);
  }

  /** NUMBER */ 
  public static BigDecimal toBigDecimal(byte[] num)
  {
    /* Special cases checked for first: zero and infinity */
    if (_isZero(num))
      return BigDecimal.valueOf(0);

    if (_isInf(num))
      throw new IllegalArgumentException(); // overflow
    
    BigDecimal result = toBigDecimalLong(num);
    return result == null ?
      toBigDecimalFull(num) : 
      result;
  }
  
  public static BigDecimal toBigDecimalFull(byte[] num) {
    long digit[] = new long[10];
    byte digidx = ODIGEND; 
    byte oidx = 1;
    int clen; 
    byte cnt;
    long value;
    int  numbytes;
    int  arycnt = 0;
    int mantlen;

    // determine sign
    boolean positive = _isPositive(num);

    // convert to base 100
    byte[] temp = _fromLnxFmt(num);

    
    // Calculate the number of Oracle mantissa digits to use.
    clen = mantlen = temp.length -1;

    // Initialize the value of the result
    if((mantlen & 0x1) == 1)
      {
        digit[digidx] = (long) temp[oidx];
        oidx++;
        clen--;
      }
    else
      {
        digit[digidx] = (long) ((temp[oidx] * 100) + temp[oidx+1]);
        oidx += 2;
        clen -=2;
      }

    // Build up the result using 2 Oracle digits at a time.
    cnt = digidx;
    while(clen != 0)
      {
        value = (long) ((temp[oidx] * 100) + temp[oidx+1]);

        for (digidx = ODIGEND; digidx >= cnt; digidx--)
          {
            value += digit[digidx] * 10000;
            digit[digidx] = value & 0xffff;
            value >>= 16;
          }

        if(value != 0);
        {
          cnt--;
          digit[cnt] = value;
        }

        oidx += 2;
        clen -= 2;
      }

    // calculate the number of bytes we will need
    if((digit[cnt] >> 8) != 0)
      numbytes = 2 * (ODIGEND - cnt) + 2;
    else
      numbytes = 2 * (ODIGEND - cnt) + 1;

    byte[] barray = new byte[numbytes];
 
    if((numbytes & 1) == 1)
      {
        barray[arycnt] = (byte) digit[cnt];  
        arycnt++;
      }
    else
      {
        barray[arycnt] = (byte)(digit[cnt] >> 8);
        arycnt++;
        barray[arycnt] = (byte)(digit[cnt] & 0x00ff);
        arycnt++;
      }

    // Store remaining byte digits, 2 at a time.

    for (cnt += 1; cnt <= ODIGEND; cnt++)
      {
        barray[arycnt] = (byte)(digit[cnt] >> 8);
        barray[arycnt+1] = (byte)(digit[cnt] & 0x00ff);
        arycnt += 2;
      }

    // create the BigDecimal representing the mantissa
    BigInteger bigtemp = new BigInteger(positive ? 1:-1, barray);
    BigDecimal result = new BigDecimal(bigtemp);

    // Calculate the scale 
    int scale = temp[0] - mantlen + 1 ;

    // Shift the decimal point     
    result = result.movePointRight(scale*2);
    


    
    // Remove the trailing zero :Bug 1506268
    if ((scale < 0) && (temp[mantlen]%10 == 0))
      result = result.setScale(-(scale*2 + 1));
    return result;
   
  } // toBigDecimal()

  /** NUMBER */
  private static boolean _isZero(byte[] num)
  {
    // Assumes length separate format
    if ((num[0] == (byte)LNXSGNBT) && (num.length == 1))
      return true;
    
    return false;
  } // _isZero()
  
  /** NUMBER */
  private static boolean _isInf(byte[] num)
  {
    // Assumes length separate format
    if (((num.length == 2) && 
         (num[0] == (byte)(LNXEXPMX + LNXSGNBT)) && 
         (num[1] == (byte)(LNXBASE + 1))) ||
        ((num[0] == (byte)0) && (num.length == 1)))
      return true;

    return false;
  } // _isInf()
  
  /** NUMBER */
  private static boolean _isPositive(byte[] num)
  {
    // Assumes length separate format
    if ((num[0] & (byte)LNXSGNBT) != (byte)0)
      return true;

    return false;
  } 
  
  
  /**
   * Some negative Oracle Numbers have a trailing value
   * to make them mem comparable. 
   */
  private static int getOraNumLength(byte[] num) {
    int n = num.length -1;
    return _isPositive(num) || 
        (n == LNXDIGS && num[n] != (byte)(LNXBASE + 2)) ?
            num.length : num.length - 1;
  }
  
  private static int getScale(byte[] num) {
    if (_isPositive(num)) {
      return (byte)((num[0] & ~LNXSGNBT) - (LNXEXPBS + 1));
    } else {
      return (byte)((~num[0] & ~LNXSGNBT) - (LNXEXPBS + 1));
    }
  }
  
  private static long unpackBase100(byte[] num, int i) {
    return _isPositive(num) ? 
        num[i] - 1 : 
        (byte)((LNXBASE + 1) - num[i]);
  }

  
  /** NUMBER */
  private static byte[] _fromLnxFmt(byte[] num)
  {
    byte[] tmp;
    int i;
    int numl = num.length;

    // Oracle Numbers have special representation for the mantissa and exponent
    // of positive and negative numbers.

    if (_isPositive(num)) // positive
      {
        tmp = new byte[numl];
        tmp[0] = (byte)((num[0] & ~LNXSGNBT) - (LNXEXPBS + 1));

        for (i = 1; i < numl; i++)
          tmp[i] = (byte)(num[i] - 1);
      }
    else
      {
        if (((numl - 1) == LNXDIGS) &&
            (num[numl - 1] != (byte)(LNXBASE + 2)))
          tmp = new byte[numl];
        else
          tmp = new byte[numl - 1];

        tmp[0] = (byte)((~num[0] & ~LNXSGNBT) - (LNXEXPBS + 1));

        for (i = 1; i < tmp.length; i++)
          tmp[i] = (byte)((LNXBASE + 1) - num[i]);
      }

    return tmp;
  }

}

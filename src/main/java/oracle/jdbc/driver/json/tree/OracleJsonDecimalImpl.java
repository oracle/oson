// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.tree;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.binary.OsonPrimitiveConversions;
import oracle.sql.json.OracleJsonDecimal;

/**
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonDecimalImpl extends OracleJsonNumberImpl implements OracleJsonDecimal {
  
  public static String NEGATIVE_INF = "\"-Inf\"";
  
  public static String POSITIVE_INF = "\"Inf\"";

  public enum NumberType { NONE, SB4, SB8, DEC128 }
  
  byte[] raw;
  
  TargetType type;
  
  public OracleJsonDecimalImpl(BigDecimal value) {
    this.raw = OsonPrimitiveConversions.toNumber(value);
  }
  
  public OracleJsonDecimalImpl(long value, TargetType type) {
    this.raw = OsonPrimitiveConversions.toNumber(value);
    this.type = type;
  }
  
  public OracleJsonDecimalImpl(int value, TargetType type) {
    this.raw = OsonPrimitiveConversions.toNumber(value);
    this.type = type;
  }
  
  public OracleJsonDecimalImpl(byte[] raw, TargetType type) {
    this.raw = raw;
    this.type = type;
  }
  
  @Override
  public OracleJsonType getOracleJsonType() {
    return OracleJsonType.DECIMAL;
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OracleJsonDecimal)) {
      return false;
    }
    OracleJsonDecimal othern = (OracleJsonDecimal)other;
    return bigDecimalValue().equals(othern.bigDecimalValue());
  }
  
  @Override
  public int hashCode() {
    return bigDecimalValue().hashCode();
  }
  
  /** Should only be called by parser */
  public void reset(byte[] raw, TargetType type) {
    this.raw = raw;
    this.type = type;
  }
  
  public BigDecimal bigDecimalValue() {
    return OsonPrimitiveConversions.toBigDecimal(raw);
  }

  public byte[] raw() {
    return raw;
  }
  
  @Override
  public String  getString() {
    int len = raw.length;
    if (len <= 2) {
      if (OsonPrimitiveConversions.isNegInf(raw, len, 0)) {
        return NEGATIVE_INF;
      } else if (OsonPrimitiveConversions.isPosInf(raw)) {
        return POSITIVE_INF;
      } 
    }

    BigDecimal bd = OsonPrimitiveConversions.toBigDecimal(raw);
    int precision = bd.precision();
    if (precision <= 40) {
      return bd.toPlainString();
    } else {
      return format(bd);
    }
  }

  
  private static String format(BigDecimal x) {
    DecimalFormat format = x.scale() < 0 ? 
        (DecimalFormat) SCIENTIFIC_FORMAT_NEGATIVE_EXP.clone() :
        (DecimalFormat) SCIENTIFIC_FORMAT_POSITIVE_EXP.clone();
    return format.format(x);
  }
  
  public boolean isDec() {
    return type == TargetType.DECIMAL;
  }

  public boolean isSB4() {
    return type == TargetType.INT;
  }

  public boolean isSB8() {
    return type == TargetType.LONG;
  }
    
  @Override
  public <T> T wrap(Class<T> c) {
    if (Jsonp.isJakartaJson(c)) {
      return c.cast(new JakartaPrimitive.JakartaNumberImpl(raw, type));
    } else {
      return c.cast(new JsonpPrimitive.JsonpNumberImpl(raw, type));
    }
  }
  
  @Override
  public TargetType getTargetType() {
    return type;
  }
  
  private static DecimalFormat SCIENTIFIC_FORMAT_POSITIVE_EXP;
  private static DecimalFormat SCIENTIFIC_FORMAT_NEGATIVE_EXP;
  
  static {
    int p1 = 40;
    StringBuilder pattern = new StringBuilder(p1+5);
    pattern.append("0.");
    for (int i = 0; i < p1; i++) {
      pattern.append('#');
    }
    pattern.append("E0");
    DecimalFormat fmt = new DecimalFormat(
      pattern.toString(),
      DecimalFormatSymbols.getInstance(Locale.US)
    );
    fmt.setRoundingMode(RoundingMode.HALF_UP);
    
    SCIENTIFIC_FORMAT_POSITIVE_EXP = fmt;
    
    SCIENTIFIC_FORMAT_NEGATIVE_EXP = (DecimalFormat) fmt.clone();
    
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setExponentSeparator("E+");
    SCIENTIFIC_FORMAT_POSITIVE_EXP.setDecimalFormatSymbols(symbols);
  }

}

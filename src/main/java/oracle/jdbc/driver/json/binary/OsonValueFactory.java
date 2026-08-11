// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import oracle.jdbc.driver.json.tree.OracleJsonNumberImpl;
import oracle.sql.json.OracleJsonDecimal;

/**
 * The purpose of this is to facilitate object reuse when 
 * needed from OsonParser.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public abstract class OsonValueFactory {
  
  abstract OsonAbstractArray createArray(OsonContext ctx, int pos);

  abstract OsonAbstractObject createObject(OsonContext ctx, int pos);
  
  abstract Object createString(OsonContext ctx, int pos, int len);
  
  abstract OracleJsonNumberImpl createNumber(byte[] raw, OracleJsonDecimal.TargetType type);
  
  abstract OracleJsonNumberImpl createStringNumber(String value);
  
  abstract OracleJsonNumberImpl createDouble(double value);
  
  abstract Object createBinary(OsonContext ctx, int pos, int len, boolean isId);
  
  abstract Object createTimestamp(byte[] raw);
  
  abstract Object createTimestampTZ(byte[] raw);

  abstract OracleJsonNumberImpl createFloat(float flt);

  abstract Object createDate(byte[] raw);

  abstract Object createIntervalYM(byte[] raw);

  abstract Object createIntervalDS(byte[] raw);
  
  abstract Object createTrue();
  
  abstract Object createFalse();
  
  abstract Object createNull();
  
  abstract Object createVector(OsonContext ctx, int pos, int len);
}

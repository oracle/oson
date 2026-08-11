// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import oracle.jdbc.driver.json.tree.OracleJsonBinaryImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDateImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDecimalImpl;
import oracle.jdbc.driver.json.tree.OracleJsonDoubleImpl;
import oracle.jdbc.driver.json.tree.OracleJsonFloatImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalDSImpl;
import oracle.jdbc.driver.json.tree.OracleJsonIntervalYMImpl;
import oracle.jdbc.driver.json.tree.OracleJsonStringImpl;
import oracle.jdbc.driver.json.tree.OracleJsonStringNumberImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampImpl;
import oracle.jdbc.driver.json.tree.OracleJsonTimestampTZImpl;
import oracle.jdbc.driver.json.tree.OracleJsonVectorImpl;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonValue;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleOsonValueFactory extends OsonValueFactory {

  public static OracleOsonValueFactory INSTANCE  = new OracleOsonValueFactory();
  
  @Override
  public OsonAbstractArray createArray(OsonContext ctx, int pos) {
    return new OsonArrayImpl(ctx, pos);
  }

  @Override
  public OsonAbstractObject createObject(OsonContext ctx, int pos) {
    return new OsonObjectImpl(ctx, pos);
  }

  @Override
  public Object createString(OsonContext ctx, int pos, int len) {
    ctx.b.position(pos);
    return new OracleJsonStringImpl(ctx.b.readString(len));
  }
  
  @Override
  public OracleJsonDecimalImpl createNumber(byte[] raw, OracleJsonDecimal.TargetType type) {
    return new OracleJsonDecimalImpl(raw, type);
  }
  
  @Override
  public OracleJsonStringNumberImpl createStringNumber(String value) {
    return new OracleJsonStringNumberImpl(value);
  }

  @Override
  public OracleJsonDoubleImpl createDouble(double value) {
    return new OracleJsonDoubleImpl(value);
  }

  @Override
  public Object createBinary(OsonContext ctx, int pos, int len, boolean isId) {
    ctx.b.position(pos);
    byte[] res = new byte[len];
    ctx.b.get(res);
    return new OracleJsonBinaryImpl(res, isId);
  }

  @Override
  public OracleJsonTimestampImpl createTimestamp(byte[] raw) {
    return new OracleJsonTimestampImpl(raw);
  }

  @Override
  public OracleJsonTimestampTZImpl createTimestampTZ(byte[] raw) {
    return new OracleJsonTimestampTZImpl(raw);
  }

  @Override
  public OracleJsonFloatImpl createFloat(float flt) {
    return new OracleJsonFloatImpl(flt);
  }

  @Override
  public OracleJsonDateImpl createDate(byte[] raw) {
    return new OracleJsonDateImpl(raw);
  }

  @Override
  public OracleJsonIntervalYMImpl createIntervalYM(byte[] value) {
    return new OracleJsonIntervalYMImpl(value);
  }

  @Override
  public OracleJsonIntervalDSImpl createIntervalDS(byte[] value) {
    return new OracleJsonIntervalDSImpl(value);
  }

  @Override
  public OracleJsonValue createTrue() {
    return OracleJsonValue.TRUE;
  }

  @Override
  public OracleJsonValue createFalse() {
    return OracleJsonValue.FALSE;
  }

  @Override
  public OracleJsonValue createNull() {
    return OracleJsonValue.NULL;
  }
  
  @Override
  public OracleJsonValue createVector(OsonContext ctx, int pos, int len) {
    ctx.b.position(pos);
    byte[] res = new byte[len];
    ctx.b.get(res);
    return new OracleJsonVectorImpl(res);
  }


  
}

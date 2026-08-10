/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.binary;

import javax.json.JsonValue;

import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpBinaryImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpDateImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpDoubleImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpFloatImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpIntervalDSImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpIntervalYMImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpNumberImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpStringImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpStringNumberImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpTimestampImpl;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpTimestampTZImpl;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonValue;


/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class JsonpOsonValueFactory extends OsonValueFactory {

  public static JsonpOsonValueFactory INSTANCE = new JsonpOsonValueFactory();
  
  public JsonpOsonValueFactory() {
    
  }
  
  @Override
  Object createBinary(OsonContext ctx, int pos, int len, boolean isId) {
    byte[] raw = new byte[len];
    ctx.b.position(pos);
    ctx.b.get(raw);
    return new JsonpBinaryImpl(raw, isId);
  }
  
  @Override
  public OsonAbstractArray createArray(OsonContext ctx, int pos) {
    return new JsonpOsonArray(ctx, pos);
  }

  @Override
  public OsonAbstractObject createObject(OsonContext ctx, int pos) {
    return new JsonpOsonObject(ctx, pos);
  }

  @Override
  public JsonpStringImpl createString(OsonContext ctx, int pos, int len) {
    ctx.b.position(pos);
    return new JsonpStringImpl(ctx.b.readString(len));
  }

  @Override
  public JsonpNumberImpl createNumber(byte[] raw, OracleJsonDecimal.TargetType type) {
    return new JsonpNumberImpl(raw, type);
  }

  @Override
  public JsonpStringNumberImpl createStringNumber(String value) {
    return new JsonpStringNumberImpl(value);
  }

  @Override
  public JsonpDoubleImpl createDouble(double value) {
    return new JsonpDoubleImpl(value);
  }

  @Override
  public JsonpTimestampImpl createTimestamp(byte[] raw) {
    return new JsonpTimestampImpl(raw);
  }

  @Override
  public Object createTimestampTZ(byte[] raw) {
    return new JsonpTimestampTZImpl(raw);
  }
  
  @Override
  public JsonpFloatImpl createFloat(float flt) {
    return new JsonpFloatImpl(flt);
  }

  @Override
  public JsonpDateImpl createDate(byte[] raw) {
    return new JsonpDateImpl(raw);
  }

  @Override
  public JsonpIntervalYMImpl createIntervalYM(byte[] raw) {
    return new JsonpIntervalYMImpl(raw);
  }

  @Override
  public JsonpIntervalDSImpl createIntervalDS(byte[] raw) {
    return new JsonpIntervalDSImpl(raw);
  }

  @Override
  public Object createTrue() {
    return JsonValue.TRUE;
  }

  @Override
  public Object createFalse() {
    return JsonValue.FALSE;
  }

  @Override
  public Object createNull() {
    return JsonValue.NULL;
  }
  
  @Override
  Object createVector(OsonContext ctx, int pos, int len) {
    throw new UnsupportedOperationException(OracleJsonValue.OracleJsonType.VECTOR.toString());
  }

}

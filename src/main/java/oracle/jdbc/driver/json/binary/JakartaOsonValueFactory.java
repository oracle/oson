// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import jakarta.json.JsonValue;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaBinaryImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaDateImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaDoubleImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaFloatImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaIntervalDSImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaIntervalYMImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaNumberImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaStringImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaStringNumberImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaTimestampImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaTimestampTZImpl;
import oracle.jdbc.driver.json.tree.JakartaPrimitive.JakartaVectorImpl;
import oracle.sql.json.OracleJsonDecimal;


/**
 *
 * Adapter for JSON-P 2.0 (jakarta.json)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpOsonValueFactory.  
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpOsonValueFactory as well.
 * 
 */
public class JakartaOsonValueFactory extends OsonValueFactory {

  public static JakartaOsonValueFactory INSTANCE = new JakartaOsonValueFactory();

  private JakartaOsonValueFactory() {
    
  }
  
  @Override
  Object createBinary(OsonContext ctx, int pos, int len, boolean isId) {
    byte[] raw = new byte[len];
    ctx.b.position(pos);
    ctx.b.get(raw);
    return new JakartaBinaryImpl(raw, isId);
  }
  
  @Override
  public OsonAbstractArray createArray(OsonContext ctx, int pos) {
    return new JakartaOsonArray(ctx, pos);
  }

  @Override
  public OsonAbstractObject createObject(OsonContext ctx, int pos) {
    return new JakartaOsonObject(ctx, pos);
  }

  @Override
  public JakartaStringImpl createString(OsonContext ctx, int pos, int len) {
    ctx.b.position(pos);
    return new JakartaStringImpl(ctx.b.readString(len));
  }

  @Override
  public JakartaNumberImpl createNumber(byte[] raw, OracleJsonDecimal.TargetType type) {
    return new JakartaNumberImpl(raw, type);
  }

  @Override
  public JakartaStringNumberImpl createStringNumber(String value) {
    return new JakartaStringNumberImpl(value);
  }

  @Override
  public JakartaDoubleImpl createDouble(double value) {
    return new JakartaDoubleImpl(value);
  }

  @Override
  public JakartaTimestampImpl createTimestamp(byte[] raw) {
    return new JakartaTimestampImpl(raw);
  }

  @Override
  public Object createTimestampTZ(byte[] raw) {
    return new JakartaTimestampTZImpl(raw);
  }
  
  @Override
  public JakartaFloatImpl createFloat(float flt) {
    return new JakartaFloatImpl(flt);
  }

  @Override
  public JakartaDateImpl createDate(byte[] raw) {
    return new JakartaDateImpl(raw);
  }

  @Override
  public JakartaIntervalYMImpl createIntervalYM(byte[] raw) {
    return new JakartaIntervalYMImpl(raw);
  }

  @Override
  public JakartaIntervalDSImpl createIntervalDS(byte[] raw) {
    return new JakartaIntervalDSImpl(raw);
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
    ctx.b.position(pos);
    byte[] res = new byte[len];
    ctx.b.get(res);
    return new JakartaVectorImpl(res);
  }

}

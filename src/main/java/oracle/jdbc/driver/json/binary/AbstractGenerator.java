// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.time.Duration;
import java.time.Period;
import java.util.Map;

import oracle.jdbc.driver.json.JakartaGeneratorWrapper;
import oracle.jdbc.driver.json.JakartaParserWrapper;
import oracle.jdbc.driver.json.Jsonp;
import oracle.jdbc.driver.json.JsonpGeneratorWrapper;
import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.sql.json.OracleJsonArray;
import oracle.sql.json.OracleJsonBinary;
import oracle.sql.json.OracleJsonDate;
import oracle.sql.json.OracleJsonDecimal;
import oracle.sql.json.OracleJsonDouble;
import oracle.sql.json.OracleJsonFloat;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonIntervalDS;
import oracle.sql.json.OracleJsonIntervalYM;
import oracle.sql.json.OracleJsonObject;
import oracle.sql.json.OracleJsonParser;
import oracle.sql.json.OracleJsonString;
import oracle.sql.json.OracleJsonTimestamp;
import oracle.sql.json.OracleJsonTimestampTZ;
import oracle.sql.json.OracleJsonValue;
import oracle.sql.json.OracleJsonVector;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public abstract class AbstractGenerator implements OracleJsonGenerator {
  
  AbstractGenerator() {
    
  }
  
  public abstract OracleJsonGenerator writeKey(String key);
  
  @Override
  public final OracleJsonGenerator writeStartObject(String key) {
    writeKey(key);
    writeStartObject();
    return this;
  }
  
  @Override
  public final OracleJsonGenerator writeStartArray(String key) {
    writeKey(key);
    return writeStartArray();
  }
  
  @Override
  public final OracleJsonGenerator write(String key, OracleJsonValue value) {
    writeKey(key);
    return write(value);
  }
  
  @Override
  public final OracleJsonGenerator write(String key, String value) {
    writeKey(key);
    return write(value);
  }
  
  public final OracleJsonGenerator write(String key, BigInteger value) {
    writeKey(key);
    return write(value);
  }
  
  @Override
  public final OracleJsonGenerator write(String key, BigDecimal value) {
    writeKey(key);
    return write(value);
  }
  
  @Override
  public final OracleJsonGenerator write(String key, int value) {
    writeKey(key);
    return write(value);
  }
  
  @Override
  public final OracleJsonGenerator write(String key, long value) {
    writeKey(key);
    return write(value);
  }  
  
  @Override
  public final OracleJsonGenerator write(String key, double value) {
    writeKey(key);
    return write(value);
  }
  
  @Override
  public final OracleJsonGenerator write(String key, float value) {
    writeKey(key);
    return write(value);
  }
  
  @Override
  public final OracleJsonGenerator write(String key, boolean value) {
    writeKey(key);
    return write(value);
  }
  
  @Override
  public final OracleJsonGenerator writeNull(String key) {
    writeKey(key);
    return writeNull();
  }  

  @Override
  public OracleJsonGenerator write(String key, Period value) {
    writeKey(key);
    return write(value);
  }

  @Override
  public OracleJsonGenerator write(String key, Duration value) {
    writeKey(key);
    return write(value);
  }
  
  protected abstract OracleJsonGenerator writeBinary(OracleJsonBinary value);
  
  protected abstract OracleJsonGenerator writeDouble(OracleJsonDouble value);
  
  protected abstract OracleJsonGenerator writeFloat(OracleJsonFloat value);
  
  protected abstract OracleJsonGenerator writeOraNumber(OracleJsonDecimal value);
  
  protected abstract OracleJsonGenerator writeTimestamp(OracleJsonTimestamp value);
  
  protected abstract OracleJsonGenerator writeTimestampTZ(OracleJsonTimestampTZ value);
  
  protected abstract OracleJsonGenerator writeDate(OracleJsonDate value);
  
  protected abstract OracleJsonGenerator writeString(OracleJsonString value);
  
  protected abstract OracleJsonGenerator writeIntervalDS(OracleJsonIntervalDS value);
  
  protected abstract OracleJsonGenerator writeIntervalYM(OracleJsonIntervalYM value);

  protected abstract OracleJsonGenerator writeVector(OracleJsonVector value);

  /** Transfers a string event from the specified parser to this generator */
  protected abstract void writeStringFromParser(OracleJsonParser parser);
  
  /** Transfers a decimal event from the specified parser to this generator */
  protected abstract void writeDecimalFromParser(OracleJsonParser parser);

  @Override
  public OracleJsonGenerator write(OracleJsonValue value) {
    writeOracleJsonValue(value);
    return this;
  }

  private void writeOracleJsonValue(OracleJsonValue value) {
    switch (value.getOracleJsonType()) {
    case OBJECT:
      OracleJsonObject obj = (OracleJsonObject)value;
      writeStartObject();
      for(Map.Entry<String, OracleJsonValue> entry : obj.entrySet()) {
        writeKey(entry.getKey());
        writeOracleJsonValue(entry.getValue());
      }
      writeEnd();
      break;
    case ARRAY:
      OracleJsonArray arr = (OracleJsonArray)value;
      writeStartArray();
      for (OracleJsonValue v : arr) {
        writeOracleJsonValue(v);
      }
      writeEnd();
      break;
    case BINARY:
      writeBinary((OracleJsonBinary)value);
      break;
    case FLOAT:
      writeFloat((OracleJsonFloat)value);
      break;
    case DOUBLE:
      writeDouble((OracleJsonDouble)value);
      break;
    case DECIMAL:
      writeOraNumber((OracleJsonDecimal)value);
      break;
    case STRING:
      writeString((OracleJsonString)value);
      break;
    case TIMESTAMP:
      writeTimestamp((OracleJsonTimestamp)value);
      break;
    case TIMESTAMPTZ:
      writeTimestampTZ((OracleJsonTimestampTZ)value);
      break;
    case DATE:
      writeDate((OracleJsonDate)value);
      break;
    case INTERVALDS:
      writeIntervalDS((OracleJsonIntervalDS)value);
      break;
    case INTERVALYM:
      writeIntervalYM((OracleJsonIntervalYM)value);
      break;
    case TRUE:
      write(true);
      break;
    case FALSE:
      write(false);
      break;
    case NULL:
      writeNull();
      break;
    case VECTOR:
      writeVector((OracleJsonVector)value);
      break;
    default:
      throw new UnsupportedOperationException();
    }
  }  
  
  @Override
  public OracleJsonGenerator writeParser(Object o) {
    try {
      if (o instanceof OracleJsonParser) {
        writeOracleJsonParser((OracleJsonParser)o);
      } else if (o instanceof Wrapper && ((Wrapper)o).isWrapperFor(OracleJsonParser.class)) {
        writeOracleJsonParser(((Wrapper)o).unwrap(OracleJsonParser.class));
      } else if (isInstance(o, Jsonp.JAKARTA_JSON_PARSER)) {
        wrap(JakartaGeneratorWrapper.class).writeJsonParser(o);
      } else if (isInstance(o, Jsonp.JAVAX_JSON_PARSER)) {
        wrap(JsonpGeneratorWrapper.class).writeJsonParser(o);
      } else {
        throw new IllegalArgumentException();
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
    return this;
  }
  
  private boolean isInstance(Object o, Class<?> c) {
    return c != null && c.isInstance(o);
  }
  
  private void writeOracleJsonParser(OracleJsonParser parser) {
    while (parser.hasNext()) {
      switch (parser.next()) {
      case START_OBJECT:
        writeStartObject();
        break;
      case START_ARRAY:
        writeStartArray();
        break;
      case END_ARRAY:
      case END_OBJECT:
        writeEnd();
        break;
      case KEY_NAME:
        writeKey(parser.getString());
        break;
      case VALUE_STRING: 
        writeStringFromParser(parser);
        break;
      case VALUE_TRUE:
        write(true);
        break;
      case VALUE_FALSE:
        write(false);
        break;
      case VALUE_NULL: 
        writeNull();
        break;
      case VALUE_BINARY:
        writeBinary(parser.getValue().asJsonBinary());
        break;
      case VALUE_DATE:
        writeDate(parser.getValue().asJsonDate());
        break;
      case VALUE_DECIMAL:
        writeDecimalFromParser(parser);
        break;
      case VALUE_DOUBLE:
        write(parser.getDouble());
        break;
      case VALUE_FLOAT:
        write(parser.getFloat());
        break;
      case VALUE_INTERVALDS:
        writeIntervalDS(parser.getValue().asJsonIntervalDS());
        break;
      case VALUE_INTERVALYM:
        writeIntervalYM(parser.getValue().asJsonIntervalYM());
        break;
      case VALUE_TIMESTAMP:
        writeTimestamp(parser.getValue().asJsonTimestamp());
        break;
      case VALUE_TIMESTAMPTZ:
        writeTimestampTZ(parser.getValue().asJsonTimestampTZ());
        break;
      case VALUE_VECTOR:
        writeVector(parser.getValue().asJsonVector());
        break;
      default:
        throw new IllegalStateException();
      }
    }
  }

  @SuppressWarnings({ "unchecked", "resource" })
  @Override
  public <T> T wrap(Class<T> wrapper) {
    try {
      if (Jsonp.hasJakarta() && 
          (Jsonp.isJakartaJsonStream(wrapper) || wrapper == JakartaGeneratorWrapper.class)) {
        return wrapper.cast((T)new JakartaGeneratorWrapper(this));
      } else {
        return wrapper.cast((T)new JsonpGeneratorWrapper(this));
      }
    } catch (ClassCastException e) {
      throw OracleJsonExceptions.BAD_WRAP.create(OracleJsonExceptions.ORACLE_FACTORY, e, wrapper.getName());
    }
  }

}

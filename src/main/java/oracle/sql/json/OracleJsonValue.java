// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.sql.json;

import oracle.jdbc.driver.json.Jsonp;

/**
 * <p>
 * The interface for JSON type in Oracle Database.  This is the super
 * type of all JSON type values: 
 * {@link OracleJsonObject}, {@link OracleJsonArray}, {@link OracleJsonString}, 
 * {@link OracleJsonDecimal}, {@link OracleJsonFloat}, {@link OracleJsonDouble},
 * {@link OracleJsonTimestamp}, {@link OracleJsonTimestampTZ}, {@link OracleJsonDate}, {@link OracleJsonBinary},
 * {@link OracleJsonIntervalDS}, {@link OracleJsonIntervalYM}, 
 * {@link OracleJsonValue#TRUE OracleJsonValue.TRUE},
 * {@link OracleJsonValue#FALSE OracleJsonValue.FALSE}, and 
 * {@link OracleJsonValue#NULL OracleJsonValue.NULL}.  Use the method 
 * {@link #getOracleJsonType()} to determine the specific type of a value. 
 * </p>
 * 
 * Example: <pre><code> 
 * import oracle.sql.json.OracleJsonArray;
 * import oracle.sql.json.OracleJsonDouble;
 * import oracle.sql.json.OracleJsonFactory;
 * import oracle.sql.json.OracleJsonObject;
 * import oracle.sql.json.OracleJsonString;
 * import oracle.sql.json.OracleJsonValue;
 * import oracle.sql.json.OracleJsonValue.OracleJsonType;
 * 
 * public class JsonValueExample {
 * 
 *   public static void main(String[] args) {
 * 
 *     OracleJsonFactory factory = new OracleJsonFactory();
 * 
 *     OracleJsonArray arr = factory.createArray();
 *     arr.add(factory.createString("foo"));
 *     arr.add(factory.createDouble(123.456d));
 *     
 *     OracleJsonObject obj = factory.createObject();
 *     obj.put("hello", "world");
 *     arr.add(obj);
 * 
 *     arr.add(OracleJsonValue.NULL);
 *     arr.add(OracleJsonValue.TRUE);
 *     
 *     System.out.println(arr.toString());
 *     
 *     for (OracleJsonValue value : arr) {
 *       OracleJsonType kind = value.getOracleJsonType();
 *       System.out.println(kind);
 *       switch (kind) {
 *       case DOUBLE:
 *         OracleJsonDouble jsonDouble = value.asJsonDouble();
 *         System.out.println(" - " + jsonDouble.doubleValue());
 *         break;
 *       case STRING:
 *         OracleJsonString jsonString = value.asJsonString();
 *         System.out.println(" - " + jsonString.getString());
 *         break;
 *       case OBJECT:
 *         OracleJsonObject jsonObject = value.asJsonObject();
 *         System.out.println(" - " + jsonObject.toString());
 *         break;
 *       case TRUE:
 *       case NULL:
 *         break; // do nothing
 *       default:
 *         throw new IllegalStateException("Unexpected");
 *       }
 *     }
 *   }
 * }</code></pre>
 *
 * <p>Running this example prints:</p>
 *
 * <pre>
 * ["foo",123.456,{"hello":"world"},null,true]
 * STRING
 *  - foo
 * DOUBLE
 *  - 123.456
 * OBJECT
 *  - {"hello":"world"}
 * NULL
 * TRUE
 * </pre>
 *
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonValue {
  
  enum OracleJsonType {
    /** An instance of {@link OracleJsonArray} */
    ARRAY,
    /** An instance of {@link OracleJsonObject} */
    OBJECT,
    /** An instance of {@link OracleJsonValue} equal to {@link OracleJsonValue#NULL} */
    NULL,
    /** An instance of {@link OracleJsonValue} equal to {@link OracleJsonValue#TRUE} */
    TRUE,
    /** An instance of {@link OracleJsonValue} equal to {@link OracleJsonValue#FALSE} */
    FALSE,
    /** An instance of {@link OracleJsonString} */
    STRING,
    /** An instance of {@link OracleJsonDecimal} */
    DECIMAL,
    /** An instance of {@link OracleJsonFloat} */
    FLOAT,
    /** An instance of {@link OracleJsonDouble} */
    DOUBLE,
    /** An instance of {@link OracleJsonTimestamp} */
    TIMESTAMP,
    /** An instance of {@link OracleJsonTimestampTZ} */
    TIMESTAMPTZ,
    /** An instance of {@link OracleJsonDate} */
    DATE,
    /** An instance of {@link OracleJsonIntervalYM} */
    INTERVALYM,
    /** An instance of {@link OracleJsonIntervalDS} */
    INTERVALDS,
    /** An instance of {@link OracleJsonBinary} */
    BINARY,
    /** An instance of {@link OracleJsonVector} */
    VECTOR
  }
  
  /** JSON null. */
  public static OracleJsonValue NULL = new OracleJsonValue() {
    @Override
    public OracleJsonType getOracleJsonType() {
      return OracleJsonType.NULL;
    }

    @Override
    public <T> T wrap(Class<T> c) {
      if (Jsonp.isJakartaJson(c)) {
        return c.cast(jakarta.json.JsonValue.NULL);
      } else {
        return c.cast(javax.json.JsonValue.NULL);
      }
    }
    
    public String toString() {
      return "null";
    }
    
    @Override
    public int hashCode() {
      return OracleJsonType.NULL.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
      return other == this || 
          (other instanceof OracleJsonValue && ((OracleJsonValue)other).getOracleJsonType() == OracleJsonType.NULL);
    }
  };
  
  /** JSON true. */
  public static OracleJsonValue TRUE = new OracleJsonValue() {
    @Override
    public OracleJsonType getOracleJsonType() {
      return OracleJsonType.TRUE;
    }

    @Override
    public <T> T wrap(Class<T> c) {
      if (Jsonp.isJakartaJson(c)) {
        return c.cast(jakarta.json.JsonValue.TRUE);
      } else {
        return c.cast(javax.json.JsonValue.TRUE);
      }
    }
    
    public String toString() {
      return "true";
    }
    
    @Override
    public int hashCode() {
      return OracleJsonType.TRUE.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
      return other == this || 
          (other instanceof OracleJsonValue && ((OracleJsonValue)other).getOracleJsonType() == OracleJsonType.TRUE);
    }
  };  

  /** JSON false. */
  public static OracleJsonValue FALSE = new OracleJsonValue() {
    @Override
    public OracleJsonType getOracleJsonType() {
      return OracleJsonType.FALSE;
    }

    @Override
    public <T> T wrap(Class<T> c) {
      if (Jsonp.isJakartaJson(c)) {
        return c.cast(jakarta.json.JsonValue.FALSE);
      } else {
        return c.cast(javax.json.JsonValue.FALSE);
      }
    }
    
    public String toString() {
      return "false";
    }
    
    @Override
    public int hashCode() {
      return OracleJsonType.FALSE.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
      return other == this || 
          (other instanceof OracleJsonValue && ((OracleJsonValue)other).getOracleJsonType() == OracleJsonType.FALSE);
    }

  }; 
  
  
  /**
   * Returns the type of this JSON value.
   * 
   * @return the value type
   */
  OracleJsonType getOracleJsonType();

  /** 
   * Returns the JSON text for this value.
   * 
   * @return JSON text
   */
  String toString();

  /**
   * Returns a JSON-P (javax.json) wrapper around this value. For example:
   * 
   * <pre>
   * <code>
   *   import javax.json.JsonObject;
   *   ...
   *   OracleJsonObject oraObject = ...;
   *   JsonObject  jsonObject = oraObject.wrap(JsonObject.class);
   * </code>
   * </pre>
   * <p>
   * The returned object is a logical view of the underlying value. Any changes
   * to the value will be observed by the returned wrapper object. All instances
   * of {@code javax.json.JsonValue} produced by JDBC implement the
   * {@code java.sql.Wrapper} interface which can be used to map back to an
   * instance of {@code oracle.sql.json.OracleJsonValue}. For example:
   * </p>
   * <pre>
   * <code>
   *   import javax.json.JsonObject;
   *   import java.sql.Wrapper
   *   ...
   *   JsonObject jsonObject = ...;
   *   OracleJsonObject oraObject = ((Wrapper)jsonObject).unwrap(OracleJsonObject.class);
   * </code>
   * </pre>
   * <p>
   * The following table summarizes the object-model mappings between
   * {@code oracle.sql.json.OracleJsonValue} and {@code javax.json.JsonValue}.
   * </p>
   * <table border="1" cellpadding="5">
   * <tr>
   * <th>oracle.sql.json</th>
   * <th>javax.json</th>
   * </tr>
   * <tr>
   * <td>{@code oracle.sql.json.OracleJsonObject}</td>
   * <td>{@code javax.json.JsonObject}</td>
   * </tr>
   * <tr>
   * <td>{@code oracle.sql.json.OracleJsonArray}</td>
   * <td>{@code javax.json.JsonArray}</td>
   * </tr>
   * <tr>
   * <td>{@code oracle.sql.json.OracleJsonString}<br>
   * {@code oracle.sql.json.OracleJsonTimestamp}<br>
   * {@code oracle.sql.json.OracleJsonDate}<br>
   * {@code oracle.sql.json.OracleJsonBinary}<br>
   * {@code oracle.sql.json.OracleJsonIntervalDS}<br>
   * {@code oracle.sql.json.OracleJsonIntervalYM}<br>
   * </td>
   * <td>{@code javax.json.JsonString}</td>
   * </tr>
   * <tr>
   * <td>{@code oracle.sql.json.OracleJsonDecimal}<br>
   * {@code oracle.sql.json.OracleJsonDouble}<br>
   * {@code oracle.sql.json.OracleJsonFloat}<br>
   * </td>
   * <td>{@code javax.json.JsonNumber}</td>
   * </tr>
   * <tr>
   * <td>{@code oracle.sql.json.OracleJsonValue.TRUE}</td>
   * <td>{@code javax.json.JsonValue.TRUE}</td>
   * </tr>
   * <tr>
   * <td>{@code oracle.sql.json.OracleJsonValue.FALSE}</td>
   * <td>{@code javax.json.JsonValue.FALSE}</td>
   * </tr>
   * <tr>
   * <td>{@code oracle.sql.json.OracleJsonValue.NULL}</td>
   * <td>{@code javax.json.JsonValue.NULL}</td>
   * </tr>
   * </table>
   * 
   * @param wrapper the interface to view this object as. Must be assignable to 
   * {@code javax.json.JsonValue}
   */
  <T> T wrap(Class<T> wrapper);
  
  /**
   * Returns this value as {@code OracleJsonObject}.  
   * This method is equivalent to {@code (OracleJsonObject)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonObject}.
   * 
   * @return the OracleJsonObject
   */
  default OracleJsonObject asJsonObject() {
    return (OracleJsonObject)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonArray}.  
   * This method is equivalent to {@code (OracleJsonArray)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonArray}.
   * 
   * @return the OracleJsonArray
   */
  default OracleJsonArray asJsonArray() {
    return (OracleJsonArray)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonString}.  
   * This method is equivalent to {@code (OracleJsonString)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonString}.
   * 
   * @return the OracleJsonString
   */
  default OracleJsonString asJsonString() {
    return (OracleJsonString)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonDecimal}.  
   * This method is equivalent to {@code (OracleJsonDecimal)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonDecimal}.
   * 
   * @return the OracleJsonDecimal
   */
  default OracleJsonDecimal asJsonDecimal() {
    return (OracleJsonDecimal)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonDouble}.  
   * This method is equivalent to {@code (OracleJsonDouble)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonDouble}.
   * 
   * @return the OracleJsonDouble
   */
  default OracleJsonDouble asJsonDouble() {
    return (OracleJsonDouble)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonFloat}.  
   * This method is equivalent to {@code (OracleJsonFloat)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonFloat}.
   * 
   * @return the OracleJsonFloat
   */
  default OracleJsonFloat asJsonFloat() {
    return (OracleJsonFloat)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonNumber}.  
   * This method is equivalent to {@code (OracleJsonNumber)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonNumber}.
   * 
   * @return the OracleJsonNumber
   */
  default OracleJsonNumber asJsonNumber() {
    return (OracleJsonNumber)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonIntervalDS}.  
   * This method is equivalent to {@code (OracleJsonIntervalDS)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonIntervalDS}.
   * 
   * @return the OracleJsonIntervalDS
   */
  default OracleJsonIntervalDS asJsonIntervalDS() {
    return (OracleJsonIntervalDS)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonIntervalYM}.  
   * This method is equivalent to {@code (OracleJsonIntervalYM)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonIntervalYM}.
   * 
   * @return the OracleJsonIntervalYM
   */
  default OracleJsonIntervalYM asJsonIntervalYM() {
    return (OracleJsonIntervalYM)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonTimestamp}.  
   * This method is equivalent to {@code (OracleJsonTimestamp)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonTimestamp}.
   * 
   * @return the OracleJsonTimestamp
   */
  default OracleJsonTimestamp asJsonTimestamp() {
    return (OracleJsonTimestamp)this;
  }

  /**
   * Returns this value as {@code OracleJsonTimestampTZ}.  
   * This method is equivalent to {@code (OracleJsonTimestampTZ)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonTimestampTZ}.
   * 
   * @return the OracleJsonTimestampTZ
   */
  default OracleJsonTimestampTZ asJsonTimestampTZ() {
    return (OracleJsonTimestampTZ)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonDate}.  
   * This method is equivalent to {@code (OracleJsonDate)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonDate}.
   * 
   * @return the OracleJsonDate
   */
  default OracleJsonDate asJsonDate() {
    return (OracleJsonDate)this;
  }
  
  /**
   * Returns this value as {@code OracleJsonBinary}.  
   * This method is equivalent to {@code (OracleJsonBinary)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonBinary}.
   * 
   * @return the OracleJsonBinary
   */
  default OracleJsonBinary asJsonBinary() {
    return (OracleJsonBinary)this;
  }

  /**
   * Returns this value as {@code OracleJsonVector}.  
   * This method is equivalent to {@code (OracleJsonVector)this}.
   * 
   * @throws ClassCastException if this value is not an instance of {@code OracleJsonVector}.
   * 
   * @return the OracleJsonVector
   */
  default OracleJsonVector asJsonVector() {
    return (OracleJsonVector)this;
  }

}

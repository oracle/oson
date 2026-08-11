// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
/**
 * The API for JSON type in Oracle Database.
 * 
 * <p>
 * This package contains classes and interfaces for working with SQL JSON type
 * values. Use this package to:
 * </p>
 * <ul>
 *  <li>Store and retrieve JSON type values in the database</li>
 *  <li>Read, create, and modify JSON type values</li>
 *  <li>Encode/decode JSON values as OSON, the same binary JSON format used by the database</li>
 *  <li>Convert JSON type values to and from JSON text</li>
 *  <li>Access JSON type values using JSON-P interfaces </li>
 * </ul>
 * <p>The package contains three components:</p>
 * 
 * <table border="1" cellpadding="5" width="800px">
 * <tr>
 * <th>Description</th>
 * <th>Classes/interfaces</th>
 * </tr>
 * <tr>
 * <td width="280px">JSON type object-model</td>
 * <td>{@link OracleJsonValue}, {@link OracleJsonObject},
 * {@link OracleJsonArray}, {@link OracleJsonString}, {@link OracleJsonDecimal},
 * {@link OracleJsonDouble}, {@link OracleJsonFloat},
 * {@link OracleJsonTimestamp}, {@link OracleJsonTimestampTZ}, {@link OracleJsonDate},
 * {@link OracleJsonBinary}, {@link OracleJsonIntervalDS}, and
 * {@link OracleJsonIntervalYM}.</td>
 * </tr>
 * <tr>
 * <td>JSON type event-stream reader and writer</td>
 * <td>{@link OracleJsonParser}<br>
 *     {@link OracleJsonGenerator}</td>
 * </tr>
 * <tr>
 * <td>Factory for reading, writing, and creating JSON type values</td>
 * <td>{@link OracleJsonFactory}</td>
 * </tr>
 * </table>
 * 
 * <p>
 * The following example shows how to insert, get, and modify JSON type values.
 * </p>
 * <pre><code>import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleType;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonObject;

public class JsonExample {

  public static void main(String[] args) throws SQLException {
    OracleDataSource ds = new OracleDataSource();
    ds.setURL("jdbc:oracle:thin:@myhost:1521:orcl");
    ds.setUser("SCOTT");
    ds.setPassword("tiger");
    OracleConnection con = (OracleConnection) ds.getConnection();
    
    // create a table with a JSON column and insert one value
    Statement stmt = con.createStatement();
    stmt.executeUpdate("CREATE TABLE fruit (data JSON)");
    stmt.executeUpdate("INSERT INTO fruit VALUES ('{\"name\":\"pear\",\"count\":10}')");
    
    // create another JSON object
    OracleJsonFactory factory = new OracleJsonFactory();
    OracleJsonObject orange = factory.createObject();
    orange.put("name", "orange");
    orange.put("count", 12);
    
    // insert the orange
    PreparedStatement pstmt = con.prepareStatement("INSERT INTO fruit VALUES (:1)");
    pstmt.setObject(1, orange, OracleType.JSON);
    pstmt.executeUpdate();
    pstmt.close();
    
    // select the pear
    ResultSet rs = stmt.executeQuery("SELECT data FROM fruit f WHERE f.data.name = 'pear'");
    rs.next();
    OracleJsonObject pear = rs.getObject(1, OracleJsonObject.class);
    int count = pear.getInt("count");
    
    // create a modifiable copy of the pear
    pear = factory.createObject(pear);
    pear.put("count", count + 1);
    pear.put("color", "green");
    
    // update the pear
    pstmt = con.prepareStatement("UPDATE fruit f SET data = :1 WHERE f.data.name = 'pear'");
    pstmt.setObject(1, pear, OracleType.JSON);
    pstmt.executeUpdate();
    pstmt.close(); 
    
    rs.close();
    stmt.close();
    con.close();
  }
}</code></pre>
 *
 * <p> In this example, {@code pstmt.setObject(...)} is called to set
 * a parameter to a JSON value and {@code rs.getObject(...)} is called
 * to get the value of a JSON type column.  </p>
 *
 * <h1>Storing and Retrieving JSON</h1>
 *
 * <p>The following methods in JDBC can accept and return JSON values:</p>
 *
 * <table border="1" cellpadding="5" width="800px">
 * <tr><th colspan="2">Methods that support JSON type</th></tr>
 * <tr>
 *   <td>{@code java.sql.ResultSet}</td>
 *   <td>
 *   {@code getObject(int, Class<T>)}<br>
 *   {@code getObject(String, Class<T>)}<br>
 *   {@code updateObject(int, Object)}<br>
 *   {@code updateObject(String, Object)}
 *   </td>
 * </tr> 
 * <tr>
 *   <td>{@code java.sql.PreparedStatement}</td>
 *   <td>
 *     {@code setObject(String, Object, int)}<br>
 *     {@code setObject(String, Object, SQLType)}
 *   </td>
 * </tr>
 * <tr>
 *  <td>{@code java.sql.CallableStatement}</td>
 *  <td>
 *   {@code getObject(int, Class<T>)}<br>
 *   {@code getObject(String, Class<T>)}<br>
 *   {@code setObject(String, Object, int)}<br>
 *   {@code setObject(String, Object, SQLType)}
 *  </td>
 * </tr>
 * <tr>
 *   <td>{@code javax.sql.RowSet}</td>
 *   <td>
 *     {@code setObject(int, Object, int)}<br>
 *     {@code setObject(String, Object, int)}
 *   </td>
 * </tr>
 * </table>
 * <br>
 * <p> Methods that accept JSON values (e.g. {@code setObject(...)}) support instances of the following Java types: </p>
 * <table border="1" cellpadding="5" width="800px">
 * <tr>
 * <th width="280px">Class</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>{@code java.lang.String}<br>
 *     {@code java.lang.CharSequence}<br>
 *     {@code java.io.Reader}</br>
 * </td>
 * <td>A JSON text value. For example: <pre><code>  stmt.setObject(1, "{\"hello\":\"world\"}", OracleType.JSON);</code></pre>
 * </td>
 * </tr>
 * <tr>
 * <td>{@code java.io.InputStream}<br>
 *     {@code byte[]}<br>
 * </td>
 * <td>Either a JSON text value (UTF8, UTF16, etc) or OSON 
 * (see below).</td>
 * </tr>
 * <tr>
 * <td>
 * {@link OracleJsonValue oracle.sql.json.OracleJsonValue}<br>
 * {@code jakarta.json.JsonValue}<br>
 * {@code javax.json.JsonValue} (deprecated)
 * </td>
 * <td> A JSON object-model value.  This includes any value derived
 * from {@link OracleJsonValue} such as {@link
 * OracleJsonObject} and {@link OracleJsonArray}. 
 * 
 * For example: <pre><code>  OracleJsonFactory factory = new OracleJsonFactory(); 
 * OracleJsonArray arr = factory.createArray();
 * arr.add("hello");
 * arr.add("world");
 * stmt.setObject(1, arr, OracleType.JSON);</code></pre>
 * JSON-P interfaces in {@code javax.json} and {@code jakarta.json} are also supported.
 * </td>
 * </tr>
 * <tr>
 * <td>
 * {@code oracle.sql.json.OracleJsonParser}<br>
 * {@code jakarta.json.stream.JsonParser}<br>
 * {@code javax.json.stream.JsonParser} (deprecated)<br>
 * </td>
 * <td>
 * A JSON event stream. 
 * </td>
 * </tr>
 * <tr>
 * <td>
 * {@code oracle.sql.json.OracleJsonDatum}
 * </td>
 * <td>
 * Container for Oracle binary JSON (OSON).
 * </td>
 * </tr>
 * </table>
 *
 * <p>Use either {@link oracle.jdbc.OracleType#JSON} or {@link
 * oracle.jdbc.OracleTypes#JSON} to specify the input is JSON, as
 * shown in the above examples. </p>
 *
 * <p>Methods that return JSON values (e.g. {@code getObject(...)}) support the following Java types: </p>
 * <table border="1" cellpadding="5" width="800px">
 * <tr>
 * <th width="280px">Class</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>{@code java.lang.String}<br>
 *     {@code java.io.Reader}
 * </td>
 * <td>The JSON type value is returned as JSON text. For example: <pre><code>  String json = resultSet.getObject(1, String.class);</code></pre></td>
 * </tr>
 * <tr>
 * <td>{@code java.io.InputStream}</td>
 * <td>The JSON type value is returned as UTF8 JSON text. </td>
 * </tr>
 * <tr>
 * <td>{@link OracleJsonValue oracle.sql.json.OracleJsonValue}<br>
 *     {@code jakarta.json.JsonValue}<br>
 *     {@code javax.json.JsonValue} (deprecated)</td>
 * <td>The JSON type value is returned as {@link OracleJsonValue}.
 * Any derived interface, such as {@link OracleJsonObject} and
 * {@link OracleJsonArray}, may also be used.  <pre><code>  OracleJsonArray arr = resultSet.getObject(1, OracleJsonArray.class);</code></pre>
 *
 * JSON objects and arrays
 * returned will reference the underlying Oracle binary JSON (see
 * below) directly and will be immutable. To make a mutable copy of a
 * returned object or array, use {@link
 * OracleJsonFactory#createObject(OracleJsonObject)} and {@link
 * OracleJsonFactory#createArray(OracleJsonArray)} respectively.
 * JSON-P interfaces ({@code javax.json} and {@code jakarta.json}) may also be used - 
 * see {@link OracleJsonValue#wrap(Class)}.
 * </tr>
 * <tr>
 * <td>
 * {@link OracleJsonParser oracle.sql.json.OracleJsonParser}<br>
 * {@code jakarta.json.stream.JsonParser}<br>
 * {@code javax.json.stream.JsonParser} (deprecated)
 * </td>
 * <td>
 * The JSON type value is returned as an event stream.
 * </td>
 * </tr>
 * <tr>
 * <td>
 * {@code oracle.sql.json.OracleJsonDatum}
 * </td>
 * <td>
 * Use this to directly access the underlying Oracle binary JSON.
 * </td>
 * </tr>
 * </table>
 * 
 * <h1>JSON Extended Types</h1>
 * 
 * A JSON text can only contain objects, arrays, strings, numbers,
 * true, false, and null.  However, a JSON type value from Oracle
 * Database is extended to support additional SQL types.  Specifically:
 * 
 * <ul>
 * 
 * <li>{@link OracleJsonNumber JSON numbers} are broken down into the
 * three SQL number types: {@link OracleJsonDecimal NUMBER},
 * {@link OracleJsonDouble BINARY_DOUBLE}, and {@link
 * OracleJsonFloat BINARY_FLOAT}.  </li>
 *
 * <li> Oracle JSON type values can contain the SQL primitives {@link
 * OracleJsonTimestamp TIMESTAMP}, {@link OracleJsonTimestampTZ TIMESTAMPTZ},
 * {@link OracleJsonDate DATE}, {@link OracleJsonBinary RAW}, 
 * {@link OracleJsonIntervalDS INTERVALDS}, and
 * {@link OracleJsonIntervalYM INTERVALYM}.  </li>
 *
 * </ul>
 *
 * For more information, see {@link OracleJsonValue}.
 * 
 * <h1>Oracle Binary JSON (OSON)</h1>
 * 
 * <p>
 * This package also provides facilities for converting JSON values to and from
 * Oracle binary JSON (OSON). OSON is the encoding format used by Oracle
 * Database to store JSON type values. It is an indexed format that supports
 * efficient random access within JSON objects and arrays. This enables JSON
 * type values to be read in-place without need to copy the data to other
 * in-memory structures such as hash tables. It also provides a richer
 * type-system than JSON text, allowing SQL types such as timestamp, date,
 * intervals, and raw binary to be stored within JSON type values.
 * </p>
 * 
 * <p>
 * JSON values obtained from the database are implicitly mapped from Oracle
 * binary JSON and values sent to the database are implicitly encoded to binary
 * JSON. For example, in the previous example, {@code orange} is directly
 * encoded as binary JSON before being sent to the database. Later when
 * {@code pear} is retrieved from the database, the binary JSON value is
 * accessed in-place by the call to {@code pear.getInt("count")}. Direct control
 * over conversions to and from binary JSON is also possible. See
 * {@link OracleJsonFactory} for more information.
 * </p>
 * 
 * <p> JSON type values can also be set and get as JSON text (for
 * example, using methods like {@link
 * java.sql.ResultSet#getString(int)} and {@link
 * java.sql.PreparedStatement#setString(int, String)}). The JDBC
 * driver or the database will convert JSON text to and from Oracle
 * binary JSON. However, avoiding JSON text conversions is ideal as
 * parsing and generation of JSON text can be expensive and will also
 * cause some nested SQL types to be rendered as JSON strings. When
 * setting JSON as text, use the {@code setObject()} methods described
 * above, rather than {@code setString()}, to ensure the JSON text can
 * be encoded in the JDBC driver.  The JDBC driver can only perform
 * the binary encoding when the type is known to be JSON, as indicated
 * by setting the type parameter of {@code setObject()} to {@code
 * OracleType.JSON}.  </p>
 *
 * <h1>Integration with JSON-P (javax.json)</h1>
 * 
 * <p>
 * JSON-P is a Java API to parse and generate JSON text and is defined by
 * <a href="https://www.jcp.org/en/jsr/detail?id=374">JSR 374: Java API for JSON
 * Processing 1.1</a>. The interfaces in {@code oracle.sql.json} are 
 * similar to ones found in JSON-P but there are two key differences:
 * </p>
 * <ul>
 * <li>The Oracle JSON type API supports an extended set of
 * {@link OracleJsonValue primitive types} that includes SQL timestamp, date,
 * raw binary, etc. JSON-P only supports the standard JSON primitives string, number, true, false, and null.
 * </li>
 * <li>{@link OracleJsonObject oracle.sql.json.OracleJsonObject} and
 * {@link OracleJsonArray oracle.sql.json.OracleJsonArray} may be mutable while
 * {@code javax.json.JsonObject} and {@code javax.json.JsonArray} are always
 * immutable.</li>
 * </ul>
 * 
 * <p>
 * In some cases it may be to desirable read and write JSON type values using
 * JSON-P interfaces. For example, if the consuming
 * application is already built on JSON-P or if you want to
 * ensure your application uses standard JSON types. In general, JDBC supports
 * consuming 3rd-party implementations of JSON-P by methods like
 * {@link java.sql.PreparedStatement#setObject(int, Object)} and also supports
 * <i>wrapping</i> values as {@code javax.json} interfaces. For more information
 * see {@link OracleJsonValue#wrap(Class)},
 * {@link OracleJsonGenerator#wrap(Class)} and
 * {@link OracleJsonParser#wrap(Class)}.
 * </p>
 * 
 * <p>
 * <b>JSON-P deprecation notice:</b>
 * Starting with version 2.0, JSON-P is part of 
 * <a href="https://eclipse-ee4j.github.io/jsonp/">Jakarta EE 9</a>
 * and it has been repackaged from {@code javax.json} to {@code jakarta.json}. 
 * Since Oracle Database JDBC Release 23ai both JSON-P 1.0 ({@code javax.json}) 
 * and JSON-P 2.0 ({@code jakarta.json}) are supported.  However, support 
 * for {@code javax.json} is deprecated and may be removed in a future release.
 * </p>
 *
 * <h1>Dependencies</h1>
 * 
 * <p>
 * JDBC depends on the <a href="https://javaee.github.io/jsonp/">JSON-P API</a>.
 * However, typically JSON-P is only required to be in the classpath when an application 
 * directly binds or gets JSON type values using JSON-P interfaces.  For example: 
 * {@code rs.getObject(1, javax.json.JsonObject)}
 * </p>
 * 
 * @see <a href="https://javaee.github.io/jsonp/">Java API for JSON
 * Processing (JSON-P)</a>
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
package oracle.sql.json;

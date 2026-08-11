OCA Verified check  

# OSON Reference Implementation

This project is the reference implementation for [OSON](https://osonspec.org/),
Oracle's compact binary format for JSON. OSON stores JSON values in a binary
representation that can be read and written without first converting to text.

This is also the OSON implementation included in Oracle's JDBC driver. The
source is provided here for informational purposes only. Artifacts built from
this project are not supported and are not recommended for direct inclusion in
applications. Applications should instead consume these classes from Oracle's
JDBC driver:

```xml
<dependency>
  <groupId>com.oracle.database.jdbc</groupId>
  <artifactId>ojdbc11</artifactId>
  <version><!-- use the JDBC version required by your application --></version>
</dependency>
```

See [Oracle JDBC on Maven Central](https://central.sonatype.com/artifact/com.oracle.database.jdbc/ojdbc11)
for available versions, and the [Oracle JSON API Javadocs](https://javadoc.io/doc/com.oracle.database.jdbc/ojdbc17/latest/oracle/sql/json/package-summary.html)
for API documentation. For questions or issues, contact Josh Spiegel at
[josh.spiegel@oracle.com](mailto:josh.spiegel@oracle.com).

## Building

Prerequisites:

* JDK 11 or later
* Maven 3.5.2 or later

From this directory, build and run the copied tests with:

```sh
mvn test
```

The resulting JAR is written to `target/oson-1.0.0.jar`.

## Hello, OSON

The following example encodes a JSON object as OSON and then decodes it back
to an `OracleJsonObject`:

```java
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import oracle.sql.json.OracleJsonFactory;
import oracle.sql.json.OracleJsonGenerator;
import oracle.sql.json.OracleJsonObject;

OracleJsonFactory factory = new OracleJsonFactory();
ByteArrayOutputStream output = new ByteArrayOutputStream();

OracleJsonGenerator generator = factory.createJsonBinaryGenerator(output);
generator.writeStartObject();
generator.write("message", "Hello, OSON!");
generator.writeEnd();
generator.close();

byte[] oson = output.toByteArray();
OracleJsonObject object =
    factory.createJsonBinaryValue(ByteBuffer.wrap(oson)).asJsonObject();

System.out.println(object.getString("message")); // Hello, OSON!
```

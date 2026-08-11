// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public final class JsonpExceptionFactory implements OracleJsonExceptions.ExceptionFactory {

    public static JsonpExceptionFactory INSTANCE = new JsonpExceptionFactory();

  private JsonpExceptionFactory() {
      // singleton
  }
    
  @Override
  public RuntimeException createJsonException(String message, Throwable cause) {
    return new javax.json.JsonException(message, cause);
  }

  @Override
  public RuntimeException createJsonException(String message) {
    return new javax.json.JsonException(message);
  }

  @Override
  public RuntimeException createGenerationException(String message, Throwable cause) {
    return new javax.json.stream.JsonGenerationException(message, cause);
  }

  @Override
  public RuntimeException createGenerationException(String message) {
    return new javax.json.stream.JsonGenerationException(message);
  }

  @Override
  public RuntimeException createParsingException(String message, Throwable cause) {
    return new javax.json.stream.JsonParsingException(message, cause, null);
  }

  @Override
  public RuntimeException createParsingException(String message) {
    return new javax.json.stream.JsonParsingException(message, null);
  }

}

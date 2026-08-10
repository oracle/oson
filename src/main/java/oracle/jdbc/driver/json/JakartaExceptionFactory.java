/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json;

/**
 * Creates JSON-P 2.0 exceptions (jakarta.json packaging)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpExceptionFactory.  
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpExceptionFactory as well.
 *       
 */
public final class JakartaExceptionFactory implements OracleJsonExceptions.ExceptionFactory {

  public static JakartaExceptionFactory INSTANCE = new JakartaExceptionFactory();

  private JakartaExceptionFactory() {
      // singleton
  }
    
  @Override
  public RuntimeException createJsonException(String message, Throwable cause) {
    return new jakarta.json.JsonException(message, cause);
  }

  @Override
  public RuntimeException createJsonException(String message) {
    return new jakarta.json.JsonException(message);
  }

  @Override
  public RuntimeException createGenerationException(String message, Throwable cause) {
    return new jakarta.json.stream.JsonGenerationException(message, cause);
  }

  @Override
  public RuntimeException createGenerationException(String message) {
    return new jakarta.json.stream.JsonGenerationException(message);
  }

  @Override
  public RuntimeException createParsingException(String message, Throwable cause) {
    return new jakarta.json.stream.JsonParsingException(message, cause, null);
  }

  @Override
  public RuntimeException createParsingException(String message) {
    return new jakarta.json.stream.JsonParsingException(message, null);
  }

}

/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.sql.json;

/**
 * Indicates that a problem occurred during JSON generation.
 * 
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OracleJsonGenerationException extends OracleJsonException {

  private static final long serialVersionUID = 1L;
  
  /**
   * Constructs a new exception with the specified message.
   * 
   * @param message the detail message.
   */
  public OracleJsonGenerationException(String message) {
    super(message);
  }
  
  /**
   * Constructs a new exception with the specified message and cause. 
   * 
   * @param message the detail message
   * @param cause the cause
   */
  public OracleJsonGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
  
}

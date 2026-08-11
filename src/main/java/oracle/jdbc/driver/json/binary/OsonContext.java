// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;

/**
 * Wraps a byte buffer and records header values.
 * 
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public class OsonContext {

  protected final OsonBuffer b;
  
  protected final OsonHeader header;
  
  protected final OsonValueFactory valueFactory;
  
  protected final ExceptionFactory exceptionFactory;

  public OsonContext(OsonBuffer buffer, OsonHeader header, OsonValueFactory valueFactory, ExceptionFactory exceptionFactory) {
    this.b = buffer;
    this.header = header;
    this.exceptionFactory = exceptionFactory;
    this.valueFactory = valueFactory;
  }
  
  public OsonContext(OsonBuffer buffer, OsonHeader header) {
    this (buffer, header, OracleOsonValueFactory.INSTANCE, OracleJsonExceptions.ORACLE_FACTORY);
  }
  
  public OsonContext(OsonBuffer buffer) {
    this(
      buffer, 
      new OsonHeader(buffer, OracleJsonExceptions.ORACLE_FACTORY)
    );
  }
  
  public OsonContext(OsonContext other) {
    this(other.b, other.header);
  }
    
  public OsonBuffer getBuffer() {
    return b;
  }
  
  public OsonHeader getHeader() {
    return header;
  }
  
  public OsonValueFactory getFactory() {
    return valueFactory;
  }
  
  public ExceptionFactory getExceptionFactory() {
    return exceptionFactory;
  }
  
}

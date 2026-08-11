// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import oracle.jdbc.driver.json.JakartaExceptionFactory;

/**
 * Adapter for JSON-P 2.0 (jakarta.json)
 * 
 * NOTE: This wrapper is the repackaged version of JsonpOsonContext.  
 *       For changes here, it should be considered if they are applicable to 
 *       JsonpOsonContext as well.
 *       
 */
public class JakartaOsonContext extends OsonContext {

  public JakartaOsonContext(OsonBuffer buffer) {
    this(buffer, new OsonHeader(buffer, JakartaExceptionFactory.INSTANCE));
  }
  
  public JakartaOsonContext(OsonContext other) {
    this(other.getBuffer(), other.getHeader());
  }

  public JakartaOsonContext(OsonBuffer buffer, OsonHeader header) {
    super(buffer, header, JakartaOsonValueFactory.INSTANCE, JakartaExceptionFactory.INSTANCE);
  }

}

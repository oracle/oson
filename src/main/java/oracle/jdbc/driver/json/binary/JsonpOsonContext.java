/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2018, 2026, Oracle and/or its affiliates.*/

package oracle.jdbc.driver.json.binary;

import oracle.jdbc.driver.json.JsonpExceptionFactory;

/**
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public class JsonpOsonContext extends OsonContext {

  public JsonpOsonContext(OsonBuffer buffer) {
    this(buffer, new OsonHeader(buffer, JsonpExceptionFactory.INSTANCE));
  }
  
  public JsonpOsonContext(OsonContext other) {
    this(other.getBuffer(), other.getHeader());
  }

  public JsonpOsonContext(OsonBuffer buffer, OsonHeader header) {
    super(buffer, header, JsonpOsonValueFactory.INSTANCE, JsonpExceptionFactory.INSTANCE);
  }

}

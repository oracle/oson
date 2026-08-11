// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.binary;

import java.util.BitSet;

import oracle.jdbc.driver.json.OracleJsonExceptions;
import oracle.jdbc.driver.json.OracleJsonExceptions.ExceptionFactory;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public class StreamContext {
    
  int depth;
  
  /** stack.get(depth) means in object at depth */
  BitSet stack;
  
  /** hasChildren.get(depth) means children at this level */
  BitSet hasChildren;
  
  boolean pendingKey;
  
  /** No more events are valid */
  boolean done;
  
  ExceptionFactory exceptionFactory;
  
  public StreamContext(ExceptionFactory exceptionFactory) {
    stack = new BitSet();
    hasChildren = new BitSet();
    this.exceptionFactory = exceptionFactory;
    init();
  }
  
  void init() {
    stack.clear();
    hasChildren.clear();
    depth = 0;
    pendingKey = false;
    done = false;
  }
  
  public void startObject() {
    beginValue();
    depth++;
    stack.set(depth);
    hasChildren.clear(depth);
  }

  public void pendingKey() {
    if (!inObject() || pendingKey) {
      throw OracleJsonExceptions.BAD_KEY.create(exceptionFactory);
    }
    this.pendingKey = true;
  }
  
  public void startArray() {
    beginValue();
    depth++;
    stack.clear(depth);
    hasChildren.clear(depth);
  }
  
  public void end() {
    if (pendingKey) {
      throw OracleJsonExceptions.EXPECTED_VALUE.create(exceptionFactory);
    }
    if (depth == 0) {
      throw OracleJsonExceptions.BAD_END.create(exceptionFactory);
    }
    depth--;
    if (depth == 0) {
      done = true;
    }
  }
  
  public void primitive() {
    beginValue();
    if (depth == 0) {
      done = true;
    }
  }
  
  private void beginValue() {
    if (inObject() && !pendingKey) {
      throw OracleJsonExceptions.MISSING_KEY.create(exceptionFactory);
    } else if (done) {
      throw OracleJsonExceptions.EXTRA_EVENTS.create(exceptionFactory);
    }
    pendingKey = false;
    hasChildren.set(depth);
  }

  public boolean inObject() {
    return depth > 0 && stack.get(depth);
  }
  
  public boolean hasChildren() {
    return hasChildren.get(depth);
  }

  public void close() {
    if (!done) {
      throw OracleJsonExceptions.GENERATION_INCOMPLETE.create(exceptionFactory);
    }
  }

  public void setExceptionFactory(ExceptionFactory f) {
    this.exceptionFactory = f;
  }

}

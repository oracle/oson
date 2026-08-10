/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.sql.json;
/**
 * A raw binary value (in any format) stored within JSON data.  Used,
 * for example, to model a binary value stored witihin a JSON object.
 *   
 * @author Josh Spiegel [josh.spiegel@oracle.com]
 */
public interface OracleJsonBinary extends OracleJsonValue {
  
  /**
   * Returns this binary value as a byte array.
   * 
   * @return the binary value
   */
  byte[] getBytes();
    /**
   * Returns this binary value as a base-16 encoded string.
   * 
   * @return the string value
   */
  String getString();
  
  /**
   * Returns true if this binary value is annotated as an identifier
   * 
   * @return true if an identifier, false otherwise.
   */
  boolean isId();
  
  /**
   * The hashCode of this binary value. Must return a hashCode equal to
   * {@code java.util.Arrays.hashCode(getBytes())}.
   * 
   * @return the hash code
   */
  @Override
  int hashCode();
  
  /**
   * Compares the specified object with this {@code OracleJsonBinary}. Returns
   * true if and only if the other object is an instance of
   * {@code OracleJsonBinary} and the result of calling
   * {@code java.util.Arrays.equals(this.getBytes() , (OracleJsonBinary)obj.getBytes())}
   * returns true.
   * 
   * @param obj the object to be compared for equality
   * 
   * @return true if the specified object is equal to this
   * {@code OracleJsonBinary}.
   */
  @Override
  boolean equals(Object obj);
  
}

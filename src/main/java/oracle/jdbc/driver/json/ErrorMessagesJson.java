/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
/* Copyright (c) 2022, 2026, Oracle and/or its affiliates.*/

/*
   DESCRIPTION
    This file contains JDBC driver internal error codes starting with 'JSON' along with their descriptions
 */

/**
 *  @author  sreekarr
 *  @since   release 23ai
 */

package oracle.jdbc.driver.json;


public class ErrorMessagesJson extends java.util.ListResourceBundle {

  public ErrorMessagesJson() {};

  public Object[][] getContents() {
    return contents;
  }

  public static final Object[][] contents = {

    /*
     * # US English Error messages for JDBC # 
     * 
     * # Note: # - Error codes are defined
     * in OracleJsonExceptions.java. # 
     * 
     * # Message Guidelines: # (The existing messages are not consistent, but do follow this guideline 
     * #when you are creating new ones, or changing old ones.) # 
     * 
     * # - Messages start in lower-cases (eg."invalid data type"). 
     * # - Do not put signs in message. This is bad:"-> NULL". 
     * # - Use past tense (eg. "failed to convert data"). #
     * 
     * #-------------------------------------------------------------------------- #
     * # Messages #
     * #--------------------------------------------------------------------------
     */
  
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26301",
     "An i/o exception occurred"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "The year \"{0}\" is not supported"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Overflow, value too large: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Unsupported option (not implemented)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Binary JSON is invalid or corrupt."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Unsupported binary JSON version: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "The UTF-8 encoded key length must not be greater than 256 bytes. The following key exceeds this limit: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "The specified JSON is too large to be encoded as binary JSON.  The encoded images size must not exceed 2GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Binary JSON is invalid or corrupt. Specified image only contains {0} bytes."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "The specified java.time.Period has days set but the Oracle year to month interval does not support days."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generator closed before before end."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "An object key must be specified in this context."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Invalid write. A complete value has already been written."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "End not allowed in this context."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Key not allowed in this context."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Expected value after key."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Parser state must be {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Parser state must not be {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Parser must be on a value."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" is not a supported wrapper type."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "This object can not be modified. To make a modifiable copy, use OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "This array can not be modified. To make a modifiable copy, use OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON object contains duplicate key: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Cannot auto-detect encoding, not enough chars."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Expected EOF token, but got {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Unexpected character {0} at line {1}, column {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Unexpected character {0} at line {1}, column {2}. Expected: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Invalid token {0} at line {1}, column {2}. Expected tokens are: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() is valid only KEY_NAME, VALUE_STRING, VALUE_NUMBER parser states. But current parser state is {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() is valid only VALUE_NUMBER parser state. But current parser state is {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() is valid only VALUE_NUMBER parser state. But current parser state is {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() is valid only VALUE_NUMBER parser state. But current parser state is {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() is valid only VALUE_NUMBER parser state. But current parser state is {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() is valid only for START_ARRAY parser state. But current parser state is {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() is valid only for START_OBJECT parser state. But current parser state is {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "A timestamp with a region is not supported. Only offset timezones are supported."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "The objects and arrays in the JSON value may not nest deeper than {0} levels"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "The keys of a JSON object may not exceed 65,535 bytes"},    
  };
    
}

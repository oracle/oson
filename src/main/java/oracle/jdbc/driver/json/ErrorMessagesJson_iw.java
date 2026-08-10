/* $Header: dbjava/src/java/oracle/jdbc/driver/json/ErrorMessagesJson.java /main/7 2024/03/27 10:13:52 sreekarr Exp $ */

/* Copyright (c) 2022, 2024, Oracle and/or its affiliates.*/

/*
   DESCRIPTION
    This file contains JDBC driver internal error codes starting with 'JSON' along with their descriptions
 */

/**
 *  @version $Header: dbjava/src/java/oracle/jdbc/driver/json/ErrorMessagesJson.java /main/7 2024/03/27 10:13:52 sreekarr Exp $
 *  @author  sreekarr
 *  @since   release 23ai
 */

package oracle.jdbc.driver.json;


public class ErrorMessagesJson_iw extends java.util.ListResourceBundle {

  public ErrorMessagesJson_iw() {};

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
     "\u05D0\u05D9\u05E8\u05E2 \u05D7\u05E8\u05D9\u05D2 \u05E7\u05DC\u05D8/\u05E4\u05DC\u05D8"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u05D4\u05E9\u05E0\u05D4 \"{0}\" \u05D0\u05D9\u05E0\u05D4 \u05E0\u05EA\u05DE\u05DB\u05EA"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\u05D2\u05DC\u05D9\u05E9\u05D4, \u05D4\u05E2\u05E8\u05DA \u05D2\u05D3\u05D5\u05DC \u05DE\u05D3\u05D9: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "\u05D0\u05E4\u05E9\u05E8\u05D5\u05EA \u05DC\u05D0 \u05E0\u05EA\u05DE\u05DB\u05EA (\u05DC\u05D0 \u05DE\u05D5\u05DE\u05E9\u05D4)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "\u05D4-JSON \u05D4\u05D1\u05D9\u05E0\u05D0\u05E8\u05D9 \u05D0\u05D9\u05E0\u05D5 \u05EA\u05E7\u05E3 \u05D0\u05D5 \u05E9\u05D4\u05D5\u05D0 \u05E4\u05D2\u05D5\u05DD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "\u05D2\u05E8\u05E1\u05D4 \u05DC\u05D0 \u05E0\u05EA\u05DE\u05DB\u05EA \u05E9\u05DC JSON \u05D1\u05D9\u05E0\u05D0\u05E8\u05D9: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "\u05D0\u05E1\u05D5\u05E8 \u05E9\u05D0\u05D5\u05E8\u05DA \u05D4\u05DE\u05E4\u05EA\u05D7 \u05D4\u05DE\u05E7\u05D5\u05D3\u05D3 \u05DC\u05E4\u05D9 UTF-8 \u05D9\u05E2\u05DC\u05D4 \u05E2\u05DC 256 \u05D1\u05D9\u05D9\u05D8. \u05D4\u05DE\u05E4\u05EA\u05D7 \u05DC\u05D4\u05DC\u05DF \u05D7\u05D5\u05E8\u05D2 \u05DE\u05D2\u05D1\u05D5\u05DC \u05D6\u05D4: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "\u05D4-JSON \u05E9\u05E6\u05D5\u05D9\u05DF \u05D2\u05D3\u05D5\u05DC \u05DE\u05D3\u05D9 \u05DC\u05E7\u05D9\u05D3\u05D5\u05D3 \u05DB-JSON \u05D1\u05D9\u05E0\u05D0\u05E8\u05D9. \u05D0\u05E1\u05D5\u05E8 \u05E9\u05D2\u05D5\u05D3\u05DC \u05D4\u05EA\u05DE\u05D5\u05E0\u05D5\u05EA \u05D4\u05DE\u05E7\u05D5\u05D3\u05D3\u05D5\u05EA \u05D9\u05E2\u05DC\u05D4 \u05E2\u05DC 2 \u05D2'\u05D9\u05D2\u05D4\u05D1\u05D9\u05D9\u05D8."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "\u05D4-JSON \u05D4\u05D1\u05D9\u05E0\u05D0\u05E8\u05D9 \u05D0\u05D9\u05E0\u05D5 \u05EA\u05E7\u05E3 \u05D0\u05D5 \u05E9\u05D4\u05D5\u05D0 \u05E4\u05D2\u05D5\u05DD. \u05D4\u05EA\u05DE\u05D5\u05E0\u05D4 \u05E9\u05E6\u05D5\u05D9\u05E0\u05D4 \u05DE\u05DB\u05D9\u05DC\u05D4 \u05E8\u05E7 {0} \u05D1\u05D9\u05D9\u05D8."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "\u05DC-java.time.Period \u05E9\u05E6\u05D5\u05D9\u05DF \u05D4\u05D5\u05D2\u05D3\u05E8\u05D5 \u05D9\u05DE\u05D9\u05DD \u05D0\u05DA \u05D4\u05DE\u05E8\u05D5\u05D5\u05D7 \u05E9\u05DC \u05E9\u05E0\u05D4 \u05E2\u05D3 \u05D7\u05D5\u05D3\u05E9 \u05D1-Oracle \u05D0\u05D9\u05E0\u05D5 \u05EA\u05D5\u05DE\u05DA \u05D1\u05D9\u05DE\u05D9\u05DD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "\u05D4\u05DE\u05D7\u05D5\u05DC\u05DC \u05E0\u05E1\u05D2\u05E8 \u05DC\u05E4\u05E0\u05D9 \u05D4\u05E1\u05D9\u05D5\u05DD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "\u05D7\u05D5\u05D1\u05D4 \u05DC\u05E6\u05D9\u05D9\u05DF \u05DE\u05E4\u05EA\u05D7 \u05D0\u05D5\u05D1\u05D9\u05D9\u05E7\u05D8 \u05D1\u05D4\u05E7\u05E9\u05E8 \u05D6\u05D4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\u05DB\u05EA\u05D9\u05D1\u05D4 \u05DC\u05D0 \u05EA\u05E7\u05E4\u05D4. \u05DB\u05D1\u05E8 \u05E0\u05DB\u05EA\u05D1 \u05E2\u05E8\u05DA \u05E9\u05DC\u05DD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\u05E1\u05D9\u05D5\u05DD \u05D0\u05E1\u05D5\u05E8 \u05D1\u05D4\u05E7\u05E9\u05E8 \u05D6\u05D4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "\u05DE\u05E4\u05EA\u05D7 \u05D0\u05E1\u05D5\u05E8 \u05D1\u05D4\u05E7\u05E9\u05E8 \u05D6\u05D4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "\u05DE\u05E6\u05D5\u05E4\u05D4 \u05E2\u05E8\u05DA \u05D0\u05D7\u05E8\u05D9 \u05DE\u05E4\u05EA\u05D7."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "\u05DE\u05E6\u05D1 \u05D4-parser \u05D7\u05D9\u05D9\u05D1 \u05DC\u05D4\u05D9\u05D5\u05EA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "\u05D0\u05E1\u05D5\u05E8 \u05E9\u05DE\u05E6\u05D1 \u05D4-parser \u05D9\u05D4\u05D9\u05D4 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "\u05D4-parser \u05D7\u05D9\u05D9\u05D1 \u05DC\u05D4\u05D9\u05D5\u05EA \u05E2\u05DC \u05E2\u05E8\u05DA."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" \u05D0\u05D9\u05E0\u05D5 \u05E1\u05D5\u05D2 wrapper \u05E0\u05EA\u05DE\u05DA."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "\u05D0\u05D9 \u05D0\u05E4\u05E9\u05E8 \u05DC\u05E9\u05E0\u05D5\u05EA \u05D0\u05D5\u05D1\u05D9\u05D9\u05E7\u05D8 \u05D6\u05D4. \u05DC\u05D9\u05E6\u05D9\u05E8\u05EA \u05E2\u05D5\u05EA\u05E7 \u05E0\u05D9\u05EA\u05DF \u05DC\u05E9\u05D9\u05E0\u05D5\u05D9, \u05D4\u05E9\u05EA\u05DE\u05E9 \u05D1-OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "\u05D0\u05D9 \u05D0\u05E4\u05E9\u05E8 \u05DC\u05E9\u05E0\u05D5\u05EA \u05DE\u05E2\u05E8\u05DA \u05D6\u05D4. \u05DC\u05D9\u05E6\u05D9\u05E8\u05EA \u05E2\u05D5\u05EA\u05E7 \u05E0\u05D9\u05EA\u05DF \u05DC\u05E9\u05D9\u05E0\u05D5\u05D9, \u05D4\u05E9\u05EA\u05DE\u05E9 \u05D1-OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "\u05D0\u05D5\u05D1\u05D9\u05D9\u05E7\u05D8 JSON \u05DE\u05DB\u05D9\u05DC \u05DE\u05E4\u05EA\u05D7 \u05DB\u05E4\u05D5\u05DC: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "\u05DC\u05D0 \u05E0\u05D9\u05EA\u05DF \u05DC\u05D6\u05D4\u05D5\u05EA \u05D1\u05D0\u05D5\u05E4\u05DF \u05D0\u05D5\u05D8\u05D5\u05DE\u05D8\u05D9 \u05D4\u05E6\u05E4\u05E0\u05D4, \u05D0\u05D9\u05DF \u05DE\u05E1\u05E4\u05D9\u05E7 \u05EA\u05D5\u05D5\u05D9\u05DD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "\u05D4\u05D9\u05D4 \u05E6\u05E4\u05D5\u05D9 \u05D0\u05E1\u05D9\u05DE\u05D5\u05DF \u05E1\u05D5\u05E3 \u05E7\u05D5\u05D1\u05E5, \u05D0\u05DA \u05D4\u05EA\u05E7\u05D1\u05DC  {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "\u05EA\u05D5 \u05DC\u05D0 \u05E6\u05E4\u05D5\u05D9 {0} \u05D1\u05E9\u05D5\u05E8\u05D4 {1}, \u05D1\u05E2\u05DE\u05D5\u05D3\u05D4 {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "\u05EA\u05D5 \u05DC\u05D0 \u05E6\u05E4\u05D5\u05D9 {0} \u05D1\u05E9\u05D5\u05E8\u05D4 {1}, \u05E2\u05DE\u05D5\u05D3\u05D4 {2}. \u05D4\u05D9\u05D4 \u05E6\u05E4\u05D5\u05D9 {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "\u05D0\u05E1\u05D9\u05DE\u05D5\u05DF \u05DC\u05D0 \u05EA\u05E7\u05E3 {0} \u05D1\u05E9\u05D5\u05E8\u05D4 {1}, \u05E2\u05DE\u05D5\u05D3\u05D4 {2}. \u05D4\u05D0\u05E1\u05D9\u05DE\u05D5\u05E0\u05D9\u05DD \u05D4\u05E6\u05E4\u05D5\u05D9\u05D9\u05DD \u05D4\u05DD: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() \u05EA\u05E7\u05E3 \u05E8\u05E7 \u05D1\u05DE\u05E6\u05D1\u05D9 parser KEY_NAME, VALUE_STRING, VALUE_NUMBER; t\u05DA \u05DE\u05E6\u05D1 \u05D4-parser \u05D4\u05E0\u05D5\u05DB\u05D7\u05D9 \u05D4\u05D5\u05D0 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() \u05EA\u05E7\u05E3 \u05E8\u05E7 \u05DC\u05DE\u05E6\u05D1parser  VALUE_NUMBER, \u05D0\u05DA \u05DE\u05E6\u05D1 \u05D4-parser \u05D4\u05E0\u05D5\u05DB\u05D7\u05D9 \u05D4\u05D5\u05D0 {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() \u05EA\u05E7\u05E3 \u05E8\u05E7 \u05DC\u05DE\u05E6\u05D1parser  VALUE_NUMBER, \u05D0\u05DA \u05DE\u05E6\u05D1 \u05D4-parser \u05D4\u05E0\u05D5\u05DB\u05D7\u05D9 \u05D4\u05D5\u05D0 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() \u05EA\u05E7\u05E3 \u05E8\u05E7 \u05DC\u05DE\u05E6\u05D1parser  VALUE_NUMBER, \u05D0\u05DA \u05DE\u05E6\u05D1 \u05D4-parser \u05D4\u05E0\u05D5\u05DB\u05D7\u05D9 \u05D4\u05D5\u05D0 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() \u05EA\u05E7\u05E3 \u05E8\u05E7 \u05DC\u05DE\u05E6\u05D1parser  VALUE_NUMBER, \u05D0\u05DA \u05DE\u05E6\u05D1 \u05D4-parser \u05D4\u05E0\u05D5\u05DB\u05D7\u05D9 \u05D4\u05D5\u05D0 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() \u05EA\u05E7\u05E3 \u05E8\u05E7 \u05DC\u05DE\u05E6\u05D1parser  START_ARRAY. \u05D0\u05DA \u05DE\u05E6\u05D1 \u05D4-parser \u05D4\u05E0\u05D5\u05DB\u05D7\u05D9 \u05D4\u05D5\u05D0 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() \u05EA\u05E7\u05E3 \u05E8\u05E7 \u05DC\u05DE\u05E6\u05D1 parser  START_OBJECT, \u05D0\u05DA \u05DE\u05E6\u05D1 \u05D4-parser \u05D4\u05E0\u05D5\u05DB\u05D7\u05D9 \u05D4\u05D5\u05D0 {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u05D7\u05D5\u05EA\u05DE\u05EA \u05D6\u05DE\u05DF \u05E2\u05DD \u05D0\u05D6\u05D5\u05E8 \u05DC\u05D0 \u05E0\u05EA\u05DE\u05DB\u05EA. \u05E8\u05E7 \u05D0\u05D6\u05D5\u05E8\u05D9 \u05D6\u05DE\u05DF \u05E9\u05DC \u05D4\u05D9\u05E1\u05D8 \u05E0\u05EA\u05DE\u05DB\u05D9\u05DD."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "\u05D4\u05D0\u05D5\u05D1\u05D9\u05D9\u05E7\u05D8\u05D9\u05DD \u05D5\u05D4\u05DE\u05E2\u05E8\u05DB\u05D9\u05DD \u05D1\u05E2\u05E8\u05DA JSON \u05D0\u05D9\u05E0\u05DD \u05D9\u05DB\u05D5\u05DC\u05D9\u05DD \u05DC\u05E7\u05E0\u05DF \u05D1\u05D9\u05D5\u05EA\u05E8 \u05DE-{0} \u05E8\u05DE\u05D5\u05EA"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "\u05D4\u05DE\u05E4\u05EA\u05D7\u05D5\u05EA \u05E9\u05DC \u05D0\u05D5\u05D1\u05D9\u05D9\u05E7\u05D8 JSON \u05DC\u05D0 \u05D9\u05E2\u05DC\u05D5 \u05E2\u05DC 65,535 \u05D1\u05D9\u05D9\u05D8\u05D9\u05DD"},    
  };
    
}
/*
   MODIFIED    (MM/DD/YY)

    sreekarr    12/01/22 - Bug 34853330
    sreekarr    08/29/22 - Modifying error messages based as per the guidelines
    sreekarr    01/19/22 - Bug#33733515-Moving the jdbc error messages starting
                           with 'JSON' from Messages.properties to this class
    sreekarr    01/19/22 - Creation
 */

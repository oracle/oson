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


public class ErrorMessagesJson_th extends java.util.ListResourceBundle {

  public ErrorMessagesJson_th() {};

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
     "\u0E40\u0E01\u0E34\u0E14\u0E02\u0E49\u0E2D\u0E1C\u0E34\u0E14\u0E1E\u0E25\u0E32\u0E14 I/O"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u0E44\u0E21\u0E48\u0E23\u0E2D\u0E07\u0E23\u0E31\u0E1A\u0E1B\u0E35 \"{0}\""},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\u0E42\u0E2D\u0E40\u0E27\u0E2D\u0E23\u0E4C\u0E42\u0E1F\u0E25\u0E27\u0E4C \u0E04\u0E48\u0E32\u0E21\u0E32\u0E01\u0E40\u0E01\u0E34\u0E19\u0E44\u0E1B: {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "\u0E44\u0E21\u0E48\u0E23\u0E2D\u0E07\u0E23\u0E31\u0E1A\u0E15\u0E31\u0E27\u0E40\u0E25\u0E37\u0E2D\u0E01 (\u0E44\u0E21\u0E48\u0E44\u0E14\u0E49\u0E43\u0E0A\u0E49\u0E07\u0E32\u0E19)"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "JSON \u0E44\u0E1A\u0E19\u0E32\u0E23\u0E35\u0E44\u0E21\u0E48\u0E16\u0E39\u0E01\u0E15\u0E49\u0E2D\u0E07\u0E2B\u0E23\u0E37\u0E2D\u0E40\u0E2A\u0E35\u0E22\u0E2B\u0E32\u0E22"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "\u0E44\u0E21\u0E48\u0E23\u0E2D\u0E07\u0E23\u0E31\u0E1A\u0E40\u0E27\u0E2D\u0E23\u0E4C\u0E0A\u0E31\u0E19\u0E02\u0E2D\u0E07 JSON \u0E44\u0E1A\u0E19\u0E32\u0E23\u0E35: {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "\u0E04\u0E35\u0E22\u0E4C\u0E17\u0E35\u0E48\u0E40\u0E02\u0E49\u0E32\u0E23\u0E2B\u0E31\u0E2A UTF-8 \u0E15\u0E49\u0E2D\u0E07\u0E21\u0E35\u0E04\u0E27\u0E32\u0E21\u0E22\u0E32\u0E27\u0E44\u0E21\u0E48\u0E40\u0E01\u0E34\u0E19 256 \u0E44\u0E1A\u0E15\u0E4C \u0E04\u0E35\u0E22\u0E4C\u0E15\u0E48\u0E2D\u0E44\u0E1B\u0E19\u0E35\u0E49\u0E40\u0E01\u0E34\u0E19\u0E02\u0E35\u0E14\u0E08\u0E33\u0E01\u0E31\u0E14\u0E19\u0E35\u0E49: \"{0}\""},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "JSON \u0E17\u0E35\u0E48\u0E23\u0E30\u0E1A\u0E38\u0E21\u0E35\u0E02\u0E19\u0E32\u0E14\u0E43\u0E2B\u0E0D\u0E48\u0E40\u0E01\u0E34\u0E19\u0E01\u0E27\u0E48\u0E32\u0E17\u0E35\u0E48\u0E08\u0E30\u0E40\u0E02\u0E49\u0E32\u0E23\u0E2B\u0E31\u0E2A\u0E40\u0E1B\u0E47\u0E19 JSON \u0E44\u0E1A\u0E19\u0E32\u0E23\u0E35  \u0E02\u0E19\u0E32\u0E14\u0E23\u0E39\u0E1B\u0E20\u0E32\u0E1E\u0E17\u0E35\u0E48\u0E40\u0E02\u0E49\u0E32\u0E23\u0E2B\u0E31\u0E2A\u0E15\u0E49\u0E2D\u0E07\u0E44\u0E21\u0E48\u0E40\u0E01\u0E34\u0E19 2GB"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "JSON \u0E44\u0E1A\u0E19\u0E32\u0E23\u0E35\u0E44\u0E21\u0E48\u0E16\u0E39\u0E01\u0E15\u0E49\u0E2D\u0E07\u0E2B\u0E23\u0E37\u0E2D\u0E40\u0E2A\u0E35\u0E22\u0E2B\u0E32\u0E22 \u0E23\u0E39\u0E1B\u0E20\u0E32\u0E1E\u0E17\u0E35\u0E48\u0E23\u0E30\u0E1A\u0E38\u0E21\u0E35\u0E40\u0E1E\u0E35\u0E22\u0E07 {0} \u0E44\u0E1A\u0E15\u0E4C"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "java.time.Period \u0E17\u0E35\u0E48\u0E23\u0E30\u0E1A\u0E38\u0E21\u0E35\u0E01\u0E32\u0E23\u0E15\u0E31\u0E49\u0E07\u0E04\u0E48\u0E32\u0E08\u0E33\u0E19\u0E27\u0E19\u0E27\u0E31\u0E19\u0E44\u0E27\u0E49 \u0E41\u0E15\u0E48\u0E0A\u0E48\u0E27\u0E07\u0E40\u0E27\u0E25\u0E32\u0E08\u0E32\u0E01\u0E15\u0E49\u0E19\u0E1B\u0E35\u0E16\u0E36\u0E07\u0E40\u0E14\u0E37\u0E2D\u0E19\u0E02\u0E2D\u0E07 Oracle \u0E44\u0E21\u0E48\u0E23\u0E2D\u0E07\u0E23\u0E31\u0E1A\u0E08\u0E33\u0E19\u0E27\u0E19\u0E27\u0E31\u0E19"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "\u0E15\u0E31\u0E27\u0E2A\u0E23\u0E49\u0E32\u0E07\u0E1B\u0E34\u0E14\u0E01\u0E48\u0E2D\u0E19\u0E17\u0E35\u0E48\u0E08\u0E30\u0E2A\u0E34\u0E49\u0E19\u0E2A\u0E38\u0E14"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "\u0E15\u0E49\u0E2D\u0E07\u0E23\u0E30\u0E1A\u0E38\u0E04\u0E35\u0E22\u0E4C\u0E2D\u0E2D\u0E1A\u0E40\u0E08\u0E01\u0E15\u0E4C\u0E43\u0E19\u0E04\u0E2D\u0E19\u0E40\u0E17\u0E47\u0E01\u0E0B\u0E4C\u0E19\u0E35\u0E49"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\u0E01\u0E32\u0E23\u0E40\u0E02\u0E35\u0E22\u0E19\u0E44\u0E21\u0E48\u0E16\u0E39\u0E01\u0E15\u0E49\u0E2D\u0E07 \u0E21\u0E35\u0E01\u0E32\u0E23\u0E40\u0E02\u0E35\u0E22\u0E19\u0E04\u0E48\u0E32\u0E17\u0E35\u0E48\u0E2A\u0E21\u0E1A\u0E39\u0E23\u0E13\u0E4C\u0E41\u0E25\u0E49\u0E27"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\u0E44\u0E21\u0E48\u0E2A\u0E32\u0E21\u0E32\u0E23\u0E16\u0E43\u0E0A\u0E49\u0E08\u0E38\u0E14\u0E2A\u0E34\u0E49\u0E19\u0E2A\u0E38\u0E14\u0E43\u0E19\u0E04\u0E2D\u0E19\u0E40\u0E17\u0E47\u0E01\u0E0B\u0E4C\u0E19\u0E35\u0E49"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "\u0E44\u0E21\u0E48\u0E2A\u0E32\u0E21\u0E32\u0E23\u0E16\u0E43\u0E0A\u0E49\u0E04\u0E35\u0E22\u0E4C\u0E43\u0E19\u0E04\u0E2D\u0E19\u0E40\u0E17\u0E47\u0E01\u0E0B\u0E4C\u0E19\u0E35\u0E49"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "\u0E04\u0E48\u0E32\u0E17\u0E35\u0E48\u0E15\u0E49\u0E2D\u0E07\u0E01\u0E32\u0E23\u0E2B\u0E25\u0E31\u0E07\u0E08\u0E32\u0E01\u0E04\u0E35\u0E22\u0E4C"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E02\u0E2D\u0E07\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E15\u0E49\u0E2D\u0E07\u0E40\u0E1B\u0E47\u0E19 {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E02\u0E2D\u0E07\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E15\u0E49\u0E2D\u0E07\u0E44\u0E21\u0E48\u0E40\u0E1B\u0E47\u0E19 {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E15\u0E49\u0E2D\u0E07\u0E17\u0E33\u0E07\u0E32\u0E19\u0E01\u0E31\u0E1A\u0E04\u0E48\u0E32"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" \u0E44\u0E21\u0E48\u0E43\u0E0A\u0E48\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E41\u0E23\u0E1B\u0E40\u0E1B\u0E2D\u0E23\u0E4C\u0E17\u0E35\u0E48\u0E23\u0E2D\u0E07\u0E23\u0E31\u0E1A"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "\u0E44\u0E21\u0E48\u0E2A\u0E32\u0E21\u0E32\u0E23\u0E16\u0E41\u0E01\u0E49\u0E44\u0E02\u0E2D\u0E2D\u0E1A\u0E40\u0E08\u0E01\u0E15\u0E4C\u0E19\u0E35\u0E49 \u0E2B\u0E32\u0E01\u0E15\u0E49\u0E2D\u0E07\u0E01\u0E32\u0E23\u0E2A\u0E23\u0E49\u0E32\u0E07\u0E2A\u0E33\u0E40\u0E19\u0E32\u0E17\u0E35\u0E48\u0E41\u0E01\u0E49\u0E44\u0E02\u0E44\u0E14\u0E49 \u0E43\u0E2B\u0E49\u0E43\u0E0A\u0E49 OracleJsonFactory.createObject(OracleJsonObject)"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "\u0E44\u0E21\u0E48\u0E2A\u0E32\u0E21\u0E32\u0E23\u0E16\u0E41\u0E01\u0E49\u0E44\u0E02\u0E2D\u0E32\u0E40\u0E23\u0E22\u0E4C\u0E19\u0E35\u0E49 \u0E2B\u0E32\u0E01\u0E15\u0E49\u0E2D\u0E07\u0E01\u0E32\u0E23\u0E2A\u0E23\u0E49\u0E32\u0E07\u0E2A\u0E33\u0E40\u0E19\u0E32\u0E17\u0E35\u0E48\u0E41\u0E01\u0E49\u0E44\u0E02\u0E44\u0E14\u0E49 \u0E43\u0E2B\u0E49\u0E43\u0E0A\u0E49 OracleJsonFactory.createArray(OracleJsonArray)"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "\u0E2D\u0E2D\u0E1A\u0E40\u0E08\u0E01\u0E15\u0E4C JSON \u0E21\u0E35\u0E04\u0E35\u0E22\u0E4C\u0E17\u0E35\u0E48\u0E0B\u0E49\u0E33\u0E01\u0E31\u0E19: {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "\u0E44\u0E21\u0E48\u0E2A\u0E32\u0E21\u0E32\u0E23\u0E16\u0E15\u0E23\u0E27\u0E08\u0E2B\u0E32\u0E01\u0E32\u0E23\u0E40\u0E02\u0E49\u0E32\u0E23\u0E2B\u0E31\u0E2A\u0E2D\u0E31\u0E15\u0E42\u0E19\u0E21\u0E31\u0E15\u0E34 \u0E40\u0E19\u0E37\u0E48\u0E2D\u0E07\u0E08\u0E32\u0E01\u0E2D\u0E31\u0E01\u0E02\u0E23\u0E30\u0E44\u0E21\u0E48\u0E40\u0E1E\u0E35\u0E22\u0E07\u0E1E\u0E2D"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "\u0E15\u0E49\u0E2D\u0E07\u0E01\u0E32\u0E23\u0E42\u0E17\u0E40\u0E04\u0E47\u0E19 EOF \u0E41\u0E15\u0E48\u0E44\u0E14\u0E49\u0E23\u0E31\u0E1A {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "\u0E2D\u0E31\u0E01\u0E02\u0E23\u0E30\u0E17\u0E35\u0E48\u0E44\u0E21\u0E48\u0E04\u0E32\u0E14\u0E2B\u0E21\u0E32\u0E22 {0} \u0E17\u0E35\u0E48\u0E1A\u0E23\u0E23\u0E17\u0E31\u0E14 {1}, \u0E04\u0E2D\u0E25\u0E31\u0E21\u0E19\u0E4C {2}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "\u0E2D\u0E31\u0E01\u0E02\u0E23\u0E30\u0E17\u0E35\u0E48\u0E44\u0E21\u0E48\u0E04\u0E32\u0E14\u0E2B\u0E21\u0E32\u0E22 {0} \u0E17\u0E35\u0E48\u0E1A\u0E23\u0E23\u0E17\u0E31\u0E14 {1}, \u0E04\u0E2D\u0E25\u0E31\u0E21\u0E19\u0E4C {2} \u0E15\u0E49\u0E2D\u0E07\u0E01\u0E32\u0E23 {3}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "\u0E42\u0E17\u0E40\u0E04\u0E47\u0E19\u0E44\u0E21\u0E48\u0E16\u0E39\u0E01\u0E15\u0E49\u0E2D\u0E07 {0} \u0E17\u0E35\u0E48\u0E1A\u0E23\u0E23\u0E17\u0E31\u0E14 {1}, \u0E04\u0E2D\u0E25\u0E31\u0E21\u0E19\u0E4C {2} \u0E04\u0E2D\u0E25\u0E31\u0E21\u0E19\u0E4C\u0E17\u0E35\u0E48\u0E15\u0E49\u0E2D\u0E07\u0E01\u0E32\u0E23\u0E04\u0E37\u0E2D: {3}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() \u0E43\u0E0A\u0E49\u0E44\u0E14\u0E49\u0E40\u0E09\u0E1E\u0E32\u0E30 KEY_NAME, VALUE_STRING, \u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C VALUE_NUMBER \u0E41\u0E15\u0E48\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E1B\u0E31\u0E08\u0E08\u0E38\u0E1A\u0E31\u0E19\u0E04\u0E37\u0E2D {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() \u0E43\u0E0A\u0E49\u0E44\u0E14\u0E49\u0E40\u0E09\u0E1E\u0E32\u0E30\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C VALUE_NUMBER \u0E41\u0E15\u0E48\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E1B\u0E31\u0E08\u0E08\u0E38\u0E1A\u0E31\u0E19\u0E04\u0E37\u0E2D {0}"},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() \u0E43\u0E0A\u0E49\u0E44\u0E14\u0E49\u0E40\u0E09\u0E1E\u0E32\u0E30\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C VALUE_NUMBER \u0E41\u0E15\u0E48\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E1B\u0E31\u0E08\u0E08\u0E38\u0E1A\u0E31\u0E19\u0E04\u0E37\u0E2D {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() \u0E43\u0E0A\u0E49\u0E44\u0E14\u0E49\u0E40\u0E09\u0E1E\u0E32\u0E30\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C VALUE_NUMBER \u0E41\u0E15\u0E48\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E1B\u0E31\u0E08\u0E08\u0E38\u0E1A\u0E31\u0E19\u0E04\u0E37\u0E2D {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() \u0E43\u0E0A\u0E49\u0E44\u0E14\u0E49\u0E40\u0E09\u0E1E\u0E32\u0E30\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C VALUE_NUMBER \u0E41\u0E15\u0E48\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E1B\u0E31\u0E08\u0E08\u0E38\u0E1A\u0E31\u0E19\u0E04\u0E37\u0E2D {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() \u0E43\u0E0A\u0E49\u0E44\u0E14\u0E49\u0E40\u0E09\u0E1E\u0E32\u0E30\u0E2A\u0E33\u0E2B\u0E23\u0E31\u0E1A\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C START_ARRAY \u0E41\u0E15\u0E48\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E1B\u0E31\u0E08\u0E08\u0E38\u0E1A\u0E31\u0E19\u0E04\u0E37\u0E2D {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() \u0E43\u0E0A\u0E49\u0E44\u0E14\u0E49\u0E40\u0E09\u0E1E\u0E32\u0E30\u0E2A\u0E33\u0E2B\u0E23\u0E31\u0E1A\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C START_OBJECT \u0E41\u0E15\u0E48\u0E2A\u0E16\u0E32\u0E19\u0E30\u0E1E\u0E32\u0E23\u0E4C\u0E40\u0E0B\u0E2D\u0E23\u0E4C\u0E1B\u0E31\u0E08\u0E08\u0E38\u0E1A\u0E31\u0E19\u0E04\u0E37\u0E2D {0}"},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u0E44\u0E21\u0E48\u0E23\u0E2D\u0E07\u0E23\u0E31\u0E1A\u0E40\u0E27\u0E25\u0E32\u0E23\u0E30\u0E1A\u0E1A\u0E17\u0E35\u0E48\u0E21\u0E35\u0E1E\u0E37\u0E49\u0E19\u0E17\u0E35\u0E48 \u0E23\u0E30\u0E1A\u0E1A\u0E08\u0E30\u0E23\u0E2D\u0E07\u0E23\u0E31\u0E1A\u0E42\u0E0B\u0E19\u0E40\u0E27\u0E25\u0E32\u0E2D\u0E2D\u0E1F\u0E40\u0E0B\u0E47\u0E15\u0E40\u0E17\u0E48\u0E32\u0E19\u0E31\u0E49\u0E19"},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "\u0E2D\u0E2D\u0E1A\u0E40\u0E08\u0E01\u0E15\u0E4C\u0E41\u0E25\u0E30\u0E2D\u0E32\u0E23\u0E4C\u0E40\u0E23\u0E22\u0E4C\u0E43\u0E19\u0E04\u0E48\u0E32 JSON \u0E15\u0E49\u0E2D\u0E07\u0E44\u0E21\u0E48\u0E0B\u0E49\u0E2D\u0E19\u0E01\u0E31\u0E19\u0E25\u0E36\u0E01\u0E01\u0E27\u0E48\u0E32 {0} \u0E23\u0E30\u0E14\u0E31\u0E1A"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "\u0E04\u0E35\u0E22\u0E4C\u0E02\u0E2D\u0E07\u0E2D\u0E2D\u0E1A\u0E40\u0E08\u0E01\u0E15\u0E4C JSON \u0E15\u0E49\u0E2D\u0E07\u0E44\u0E21\u0E48\u0E40\u0E01\u0E34\u0E19 65,535 \u0E44\u0E1A\u0E15\u0E4C"},    
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

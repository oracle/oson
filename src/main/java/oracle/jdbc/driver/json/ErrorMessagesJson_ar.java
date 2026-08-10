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


public class ErrorMessagesJson_ar extends java.util.ListResourceBundle {

  public ErrorMessagesJson_ar() {};

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
     "\u062D\u062F\u062B \u0627\u0633\u062A\u062B\u0646\u0627\u0621 \u0641\u064A \u0627\u0644\u0645\u062F\u062E\u0644\u0627\u062A/\u0627\u0644\u0645\u062E\u0631\u062C\u0627\u062A"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u0627\u0644\u0633\u0646\u0629 \"{0}\" \u063A\u064A\u0631 \u0645\u062F\u0639\u0648\u0645\u0629"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\u0627\u0644\u062A\u062C\u0627\u0648\u0632\u060C \u0627\u0644\u0642\u064A\u0645\u0629 \u0643\u0628\u064A\u0631\u0629 \u0644\u0644\u063A\u0627\u064A\u0629: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "\u062E\u064A\u0627\u0631 \u063A\u064A\u0631 \u0645\u062F\u0639\u0648\u0645 (\u063A\u064A\u0631 \u0645\u064F\u0646\u0641\u0651\u064E\u0630)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "\u064A\u0643\u0648\u0646 JSON \u0627\u0644\u062B\u0646\u0627\u0626\u064A \u063A\u064A\u0631 \u0635\u0627\u0644\u062D \u0623\u0648 \u062A\u0627\u0644\u0641\u064B\u0627."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "\u0625\u0635\u062F\u0627\u0631 JSON \u0627\u0644\u062B\u0646\u0627\u0626\u064A \u063A\u064A\u0631 \u0645\u062F\u0639\u0648\u0645: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "\u064A\u062C\u0628 \u0623\u0644\u0627 \u064A\u0643\u0648\u0646 \u0637\u0648\u0644 \u0627\u0644\u0645\u0641\u062A\u0627\u062D \u0628\u062A\u0634\u0641\u064A\u0631 UTF-8 \u0623\u0643\u0628\u0631 \u0645\u0646 256 \u0628\u0627\u064A\u062A. \u064A\u062A\u062C\u0627\u0648\u0632 \u0627\u0644\u0645\u0641\u062A\u0627\u062D \u0627\u0644\u062A\u0627\u0644\u064A \u0647\u0630\u0627 \u0627\u0644\u062D\u062F: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "\u064A\u0643\u0648\u0646 JSON \u0627\u0644\u0645\u064F\u062D\u062F\u0651\u064E\u062F \u0643\u0628\u064A\u0631\u064B\u0627 \u0644\u0644\u063A\u0627\u064A\u0629 \u0644\u064A\u062A\u0645 \u062A\u0634\u0641\u064A\u0631\u0647 \u0643\u0640 JSON \u062B\u0646\u0627\u0626\u064A. \u064A\u062C\u0628 \u0623\u0644\u0627 \u064A\u062A\u062C\u0627\u0648\u0632 \u062D\u062C\u0645 \u0627\u0644\u0635\u0648\u0631 \u0627\u0644\u0645\u064F\u0634\u0641\u0651\u064E\u0631\u0629 2 \u062C\u064A\u062C\u0627\u0628\u0627\u064A\u062A."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "\u064A\u0643\u0648\u0646 JSON \u0627\u0644\u062B\u0646\u0627\u0626\u064A \u063A\u064A\u0631 \u0635\u0627\u0644\u062D \u0623\u0648 \u062A\u0627\u0644\u0641\u064B\u0627. \u062A\u062D\u062A\u0648\u064A \u0627\u0644\u0635\u0648\u0631 \u0627\u0644\u0645\u064F\u062D\u062F\u0651\u064E\u062F\u0629 \u0639\u0644\u0649 {0} \u0645\u0646 \u0648\u062D\u062F\u0627\u062A \u0627\u0644\u0628\u0627\u064A\u062A \u0641\u0642\u0637."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "\u062A\u0645 \u062A\u0639\u064A\u064A\u0646 \u0623\u064A\u0627\u0645 \u0641\u064A java.time.Period \u0627\u0644\u0645\u064F\u062D\u062F\u0651\u064E\u062F\u060C \u0648\u0644\u0643\u0646 \u0644\u0627 \u064A\u062F\u0639\u0645 \u0627\u0644\u0641\u0627\u0635\u0644 \u0627\u0644\u0632\u0645\u0646\u064A \"\u0633\u0646\u0629 \u0625\u0644\u0649 \u0634\u0647\u0631\" \u0641\u064A Oracle \u0627\u0644\u0623\u064A\u0627\u0645."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "\u062A\u0645 \u0625\u063A\u0644\u0627\u0642 \u0627\u0644\u0645\u064F\u0646\u0634\u0626 \u0642\u0628\u0644 \u0627\u0644\u0646\u0647\u0627\u064A\u0629."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "\u064A\u062C\u0628 \u062A\u062D\u062F\u064A\u062F \u0645\u0641\u062A\u0627\u062D \u0627\u0644\u0643\u0627\u0626\u0646 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0633\u064A\u0627\u0642."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\u0643\u062A\u0627\u0628\u0629 \u063A\u064A\u0631 \u0635\u0627\u0644\u062D\u0629. \u062A\u0645\u062A \u0643\u062A\u0627\u0628\u0629 \u0642\u064A\u0645\u0629 \u0645\u0643\u062A\u0645\u0644\u0629."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\u0627\u0644\u0646\u0647\u0627\u064A\u0629 \u063A\u064A\u0631 \u0645\u0633\u0645\u0648\u062D \u0628\u0647\u0627 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0633\u064A\u0627\u0642."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "\u0627\u0644\u0645\u0641\u062A\u0627\u062D \u063A\u064A\u0631 \u0645\u0633\u0645\u0648\u062D \u0628\u0647 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0633\u064A\u0627\u0642."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "\u0627\u0644\u0642\u064A\u0645\u0629 \u0627\u0644\u0645\u062A\u0648\u0642\u0639\u0629 \u0628\u0639\u062F \u0627\u0644\u0645\u062A\u0627\u062D."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "\u064A\u062C\u0628 \u0623\u0646 \u062A\u0643\u0648\u0646 \u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "\u064A\u062C\u0628 \u0623\u0644\u0627 \u062A\u0643\u0648\u0646 \u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "\u064A\u062C\u0628 \u0623\u0646 \u064A\u0643\u0648\u0646 \u0627\u0644\u0645\u062D\u0644\u0644 \u0639\u0644\u0649 \u0642\u064A\u0645\u0629."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\u0644\u0627 \u064A\u0643\u0648\u0646 \"{0}\" \u0646\u0648\u0639 \u0627\u0644\u063A\u0644\u0627\u0641 \u0627\u0644\u0645\u062F\u0639\u0648\u0645."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "\u0644\u0627 \u064A\u0645\u0643\u0646 \u062A\u0639\u062F\u064A\u0644 \u0647\u0630\u0627 \u0627\u0644\u0643\u0627\u0626\u0646. \u0648\u0644\u0644\u062D\u0635\u0648\u0644 \u0639\u0644\u0649 \u0646\u0633\u062E\u0629 \u0645\u0639\u062F\u0644\u0629\u060C \u064A\u0644\u0632\u0645 \u0627\u0633\u062A\u062E\u062F\u0627\u0645 OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "\u0644\u0627 \u064A\u0645\u0643\u0646 \u062A\u0639\u062F\u064A\u0644 \u0647\u0630\u0627 \u0627\u0644\u0635\u0641\u064A\u0641. \u0648\u0644\u0644\u062D\u0635\u0648\u0644 \u0639\u0644\u0649 \u0646\u0633\u062E\u0629 \u0645\u0639\u062F\u0644\u0629\u060C \u064A\u0644\u0632\u0645 \u0627\u0633\u062A\u062E\u062F\u0627\u0645 OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "\u064A\u0634\u062A\u0645\u0644 \u0643\u0627\u0626\u0646 JSON \u0639\u0644\u0649 \u0645\u0641\u062A\u0627\u062D \u0645\u0643\u0631\u0631: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "\u0644\u0627 \u064A\u0645\u0643\u0646 \u0627\u0643\u062A\u0634\u0627\u0641 \u0627\u0644\u062A\u0631\u0645\u064A\u0632 \u0622\u0644\u064A\u064B\u0627\u060C \u0644\u0627 \u062A\u0648\u062C\u062F \u0623\u062D\u0631\u0641 \u0643\u0627\u0641\u064A\u0629."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "\u0643\u0627\u0646 \u0627\u0644\u0645\u062A\u0648\u0642\u0639 \u0647\u0648 \u0645\u0642\u0637\u0639 \u0646\u0647\u0627\u064A\u0629 \u0627\u0644\u0645\u0644\u0641 EOF\u060C \u0644\u0643\u0646 \u062A\u0645 \u0627\u0644\u062D\u0635\u0648\u0644 \u0639\u0644\u0649 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "\u062D\u0631\u0641 \u063A\u064A\u0631 \u0645\u062A\u0648\u0642\u0639 {0} \u0641\u064A \u0627\u0644\u0633\u0637\u0631 {1}\u060C \u0627\u0644\u0639\u0645\u0648\u062F {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "\u062D\u0631\u0641 \u063A\u064A\u0631 \u0645\u062A\u0648\u0642\u0639 {0} \u0641\u064A \u0627\u0644\u0633\u0637\u0631 {1}\u060C \u0627\u0644\u0639\u0645\u0648\u062F {2}. \u0627\u0644\u0645\u062A\u0648\u0642\u0639 {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "\u0631\u0645\u0632 \u063A\u064A\u0631 \u0635\u0627\u0644\u062D {0} \u0641\u064A \u0627\u0644\u0633\u0637\u0631 {1}\u060C \u0627\u0644\u0639\u0645\u0648\u062F {2}. \u0627\u0644\u0631\u0645\u0648\u0632 \u0627\u0644\u0645\u062A\u0648\u0642\u0639\u0629 \u0647\u064A: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() \u0635\u0627\u0644\u062D\u0629 \u0641\u0642\u0637 \u0644\u062D\u0627\u0644\u0627\u062A \u0627\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A KEY_NAME\u060C \u0648VALUE_STRING\u060C \u0648VALUE_NUMBER. \u0644\u0643\u0646 \u0627\u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u062D\u0627\u0644\u064A\u0629 \u0644\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A \u0647\u064A {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() \u0635\u0627\u0644\u062D\u0629 \u0641\u0642\u0637 \u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A VALUE_NUMBER. \u0644\u0643\u0646 \u0627\u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u062D\u0627\u0644\u064A\u0629 \u0644\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A \u0647\u064A {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() \u0635\u0627\u0644\u062D\u0629 \u0641\u0642\u0637 \u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A VALUE_NUMBER. \u0644\u0643\u0646 \u0627\u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u062D\u0627\u0644\u064A\u0629 \u0644\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A \u0647\u064A {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() \u0635\u0627\u0644\u062D\u0629 \u0641\u0642\u0637 \u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A VALUE_NUMBER. \u0644\u0643\u0646 \u0627\u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u062D\u0627\u0644\u064A\u0629 \u0644\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A \u0647\u064A {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() \u0635\u0627\u0644\u062D\u0629 \u0641\u0642\u0637 \u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A VALUE_NUMBER. \u0644\u0643\u0646 \u0627\u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u062D\u0627\u0644\u064A\u0629 \u0644\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A \u0647\u064A {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() \u0635\u0627\u0644\u062D\u0629 \u0641\u0642\u0637 \u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A. \u0644\u0643\u0646 \u0627\u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u062D\u0627\u0644\u064A\u0629 \u0644\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A \u0647\u064A {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() \u0635\u0627\u0644\u062D\u0629 \u0641\u0642\u0637 \u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A START_OBJECT. \u0644\u0643\u0646 \u0627\u0644\u062D\u0627\u0644\u0629 \u0627\u0644\u062D\u0627\u0644\u064A\u0629 \u0644\u0644\u0645\u062D\u0644\u0644 \u0627\u0644\u0644\u063A\u0648\u064A \u0647\u064A {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u0637\u0627\u0628\u0639 \u0632\u0645\u0646\u064A \u0628\u0645\u0646\u0637\u0642\u0629 \u063A\u064A\u0631 \u0645\u062F\u0639\u0648\u0645. \u0644\u0627 \u064A\u062A\u0645 \u062F\u0639\u0645 \u0633\u0648\u0649 \u0627\u0644\u0645\u0646\u0627\u0637\u0642 \u0627\u0644\u0632\u0645\u0646\u064A\u0629 \u0644\u0644\u0645\u0642\u0627\u0635\u0629."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "\u0644\u0627 \u064A\u062C\u0648\u0632 \u0644\u0644\u0643\u0627\u0626\u0646\u0627\u062A \u0648\u0627\u0644\u0645\u0635\u0641\u0648\u0641\u0627\u062A \u0627\u0644\u0645\u0648\u062C\u0648\u062F\u0629 \u0641\u064A \u0642\u064A\u0645\u0629 JSON \u0623\u0646 \u062A\u062A\u062F\u0627\u062E\u0644 \u0628\u0634\u0643\u0644 \u0623\u0639\u0645\u0642 \u0645\u0646 {0} \u0645\u0633\u062A\u0648\u0649."},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "\u0644\u0627 \u064A\u062C\u0648\u0632 \u0623\u0646 \u062A\u062A\u062C\u0627\u0648\u0632 \u0645\u0641\u0627\u062A\u064A\u062D \u0643\u0627\u0626\u0646 JSON 65.535 \u0628\u0627\u064A\u062A"},    
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

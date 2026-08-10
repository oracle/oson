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


public class ErrorMessagesJson_ko extends java.util.ListResourceBundle {

  public ErrorMessagesJson_ko() {};

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
     "I/O \uC608\uC678\uC0AC\uD56D\uC774 \uBC1C\uC0DD\uD588\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\"{0}\" \uC5F0\uB3C4\uB294 \uC9C0\uC6D0\uB418\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\uC624\uBC84\uD50C\uB85C\uC6B0, \uAC12\uC774 \uB108\uBB34 \uD07C: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "\uC9C0\uC6D0\uB418\uC9C0 \uC54A\uB294 \uC635\uC158(\uAD6C\uD604\uB418\uC9C0 \uC54A\uC74C)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "\uBC14\uC774\uB108\uB9AC JSON\uC774 \uBD80\uC801\uD569\uD558\uAC70\uB098 \uC190\uC0C1\uB418\uC5C8\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "\uC9C0\uC6D0\uB418\uC9C0 \uC54A\uB294 \uBC14\uC774\uB108\uB9AC JSON \uBC84\uC804: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "UTF-8 \uC778\uCF54\uB529\uB41C \uD0A4 \uAE38\uC774\uB294 256\uBC14\uC774\uD2B8\uBCF4\uB2E4 \uD06C\uC9C0 \uC54A\uC544\uC57C \uD569\uB2C8\uB2E4. \uB2E4\uC74C \uD0A4\uAC00 \uC774 \uC81C\uD55C\uC744 \uCD08\uACFC\uD568: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "\uC9C0\uC815\uB41C JSON\uC774 \uB108\uBB34 \uCEE4\uC11C \uBC14\uC774\uB108\uB9AC JSON\uC73C\uB85C \uC778\uCF54\uB529\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4. \uC778\uCF54\uB529\uB41C \uC774\uBBF8\uC9C0 \uD06C\uAE30\uB294 2GB\uB97C \uCD08\uACFC\uD558\uC9C0 \uC54A\uC544\uC57C \uD569\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "\uBC14\uC774\uB108\uB9AC JSON\uC774 \uBD80\uC801\uD569\uD558\uAC70\uB098 \uC190\uC0C1\uB418\uC5C8\uC2B5\uB2C8\uB2E4. \uC9C0\uC815\uB41C \uC774\uBBF8\uC9C0\uC5D0\uB294 {0}\uBC14\uC774\uD2B8\uB9CC \uD3EC\uD568\uB429\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "\uC9C0\uC815\uB41C java.time.Period\uC5D0 \uC77C\uC774 \uC124\uC815\uB418\uC5C8\uC9C0\uB9CC \uC624\uB77C\uD074 \uC5F0\uB3C4-\uC6D4 \uAC04\uACA9\uC5D0\uC11C \uC77C\uC744 \uC9C0\uC6D0\uD558\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "\uC0DD\uC131\uAE30\uAC00 \uC885\uB8CC \uC804\uC5D0 \uB2EB\uD614\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "\uC774 \uCEE8\uD14D\uC2A4\uD2B8\uC5D0\uC11C\uB294 \uAC1D\uCCB4 \uD0A4\uAC00 \uC9C0\uC815\uB418\uC5B4\uC57C \uD569\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\uC4F0\uAE30\uAC00 \uBD80\uC801\uD569\uD569\uB2C8\uB2E4. \uC804\uCCB4 \uAC12\uC774 \uC774\uBBF8 \uC4F0\uC5EC\uC84C\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\uC774 \uCEE8\uD14D\uC2A4\uD2B8\uC5D0\uC11C\uB294 \uC885\uB8CC\uAC00 \uD5C8\uC6A9\uB418\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "\uC774 \uCEE8\uD14D\uC2A4\uD2B8\uC5D0\uC11C\uB294 \uD0A4\uAC00 \uD5C8\uC6A9\uB418\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "\uD0A4 \uB4A4\uC5D0 \uAC12\uC774 \uD544\uC694\uD569\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "\uAD6C\uBB38\uBD84\uC11D\uAE30\uB294 {0} \uC0C1\uD0DC\uC5EC\uC57C \uD569\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "\uAD6C\uBB38\uBD84\uC11D\uAE30\uB294 {0} \uC0C1\uD0DC\uAC00 \uC544\uB2C8\uC5B4\uC57C \uD569\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "\uAD6C\uBB38\uBD84\uC11D\uAE30\uB294 \uAC12\uC5D0 \uC788\uC5B4\uC57C \uD569\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\"\uC740(\uB294) \uC9C0\uC6D0\uB418\uB294 \uB798\uD37C \uC720\uD615\uC774 \uC544\uB2D9\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "\uC774 \uAC1D\uCCB4\uB97C \uC218\uC815\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4. \uC218\uC815 \uAC00\uB2A5\uD55C \uBCF5\uC0AC\uBCF8\uC744 \uB9CC\uB4E4\uB824\uBA74 OracleJsonFactory.createObject(OracleJsonObject)\uB97C \uC0AC\uC6A9\uD558\uC2ED\uC2DC\uC624."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "\uC774 \uBC30\uC5F4\uC744 \uC218\uC815\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4. \uC218\uC815 \uAC00\uB2A5\uD55C \uBCF5\uC0AC\uBCF8\uC744 \uB9CC\uB4E4\uB824\uBA74 OracleJsonFactory.createArray(OracleJsonArray)\uB97C \uC0AC\uC6A9\uD558\uC2ED\uC2DC\uC624."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON \uAC1D\uCCB4\uC5D0 \uC911\uBCF5 \uD0A4 \uC788\uC74C: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "\uC778\uCF54\uB529\uC744 \uC790\uB3D9 \uAC10\uC9C0\uD560 \uC218 \uC5C6\uC74C, \uBB38\uC790 \uC218\uAC00 \uBD80\uC871\uD568."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "EOF \uD1A0\uD070\uC774 \uD544\uC694\uD558\uC9C0\uB9CC {0}\uC744(\uB97C) \uBC1B\uC558\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "\uC608\uC0C1\uCE58 \uC54A\uC740 \uBB38\uC790 {0}\uC774(\uAC00) {1}\uD589 {2}\uC5F4\uC5D0 \uC788\uC2B5\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "\uC608\uC0C1\uCE58 \uC54A\uC740 \uBB38\uC790 {0}\uC774(\uAC00) {1}\uD589 {2}\uC5F4\uC5D0 \uC788\uC2B5\uB2C8\uB2E4. \uD544\uC694\uD55C \uAC12: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "\uBD80\uC801\uD569\uD55C \uD1A0\uD070 {0}\uC774(\uAC00) {1}\uD589, {2}\uC5F4\uC5D0 \uC788\uC2B5\uB2C8\uB2E4. \uD544\uC694\uD55C \uD1A0\uD070: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString()\uC740 KEY_NAME, VALUE_STRING, VALUE_NUMBER \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB9CC \uC801\uD569\uD569\uB2C8\uB2E4. \uD604\uC7AC \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB294 {0}\uC785\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber()\uC740 VALUE_NUMBER \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB9CC \uC801\uD569\uD569\uB2C8\uB2E4. \uD604\uC7AC \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB294 {0}\uC785\uB2C8\uB2E4."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt()\uC740 VALUE_NUMBER \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB9CC \uC801\uD569\uD569\uB2C8\uB2E4. \uD604\uC7AC \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB294 {0}\uC785\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong()\uC740 VALUE_NUMBER \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB9CC \uC801\uD569\uD569\uB2C8\uB2E4. \uD604\uC7AC \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB294 {0}\uC785\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal()\uC740 VALUE_NUMBER \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB9CC \uC801\uD569\uD569\uB2C8\uB2E4. \uD604\uC7AC \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB294 {0}\uC785\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray()\uB294 START_ARRAY \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uC5D0\uB9CC \uC801\uD569\uD569\uB2C8\uB2E4. \uD604\uC7AC \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB294 {0}\uC785\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject()\uC740 START_OBJECT \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uC5D0\uB9CC \uC801\uD569\uD569\uB2C8\uB2E4. \uD604\uC7AC \uAD6C\uBB38\uBD84\uC11D\uAE30 \uC0C1\uD0DC\uB294 {0}\uC785\uB2C8\uB2E4."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\uC9C0\uC5ED\uC774 \uC788\uB294 \uC2DC\uAC04\uAE30\uB85D\uC740 \uC9C0\uC6D0\uB418\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4. \uC624\uD504\uC14B \uC2DC\uAC04\uB300\uB9CC \uC9C0\uC6D0\uB429\uB2C8\uB2E4."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "JSON \uAC12\uC758 \uAC1D\uCCB4 \uBC0F \uBC30\uC5F4\uC774 {0} \uB808\uBCA8 \uC774\uC0C1 \uC911\uCCA9\uB418\uC9C0 \uC54A\uC744 \uC218 \uC788\uC2B5\uB2C8\uB2E4."},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "JSON \uAC1D\uCCB4\uC758 \uD0A4\uB294 65,535\uBC14\uC774\uD2B8\uB97C \uCD08\uACFC\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4."},    
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

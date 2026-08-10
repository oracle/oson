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


public class ErrorMessagesJson_zh_CN extends java.util.ListResourceBundle {

  public ErrorMessagesJson_zh_CN() {};

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
     "\u51FA\u73B0 I/O \u5F02\u5E38\u9519\u8BEF"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u4E0D\u652F\u6301\u5E74\u4EFD \"{0}\""},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\u6EA2\u51FA\uFF0C\u503C\u592A\u5927\uFF1A{0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "\u4E0D\u652F\u6301\u6B64\u9009\u9879\uFF08\u672A\u5B9E\u65BD\uFF09"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "\u4E8C\u8FDB\u5236 JSON \u65E0\u6548\u6216\u635F\u574F\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "\u4E0D\u652F\u6301\u6B64\u4E8C\u8FDB\u5236 JSON \u7248\u672C\uFF1A{0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "UTF-8 \u7F16\u7801\u952E\u957F\u5EA6\u4E0D\u80FD\u5927\u4E8E 256 \u5B57\u8282\u3002\u4EE5\u4E0B\u952E\u8D85\u8FC7\u4E86\u6B64\u9650\u5236\uFF1A\"{0}\"\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "\u6307\u5B9A\u7684 JSON \u592A\u5927\uFF0C\u65E0\u6CD5\u7F16\u7801\u4E3A\u4E8C\u8FDB\u5236 JSON\u3002\u7F16\u7801\u7684\u56FE\u50CF\u5927\u5C0F\u4E0D\u80FD\u8D85\u8FC7 2GB\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "\u4E8C\u8FDB\u5236 JSON \u65E0\u6548\u6216\u635F\u574F\u3002\u6307\u5B9A\u7684\u56FE\u50CF\u4EC5\u5305\u542B {0} \u4E2A\u5B57\u8282\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "\u6307\u5B9A\u7684 java.time.Period \u8BBE\u7F6E\u4E86\u5929\u6570\uFF0C\u4F46\u662F Oracle \u5E74\u5230\u6708\u65F6\u95F4\u95F4\u9694\u4E0D\u652F\u6301\u5929\u6570\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "\u751F\u6210\u5668\u5728\u7ED3\u675F\u4E4B\u524D\u5173\u95ED\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "\u5728\u6B64\u4E0A\u4E0B\u6587\u4E2D\u5FC5\u987B\u6307\u5B9A\u5BF9\u8C61\u952E\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\u5199\u5165\u65E0\u6548\u3002\u5DF2\u5199\u5165\u5B8C\u6574\u503C\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\u5728\u6B64\u4E0A\u4E0B\u6587\u4E2D\u4E0D\u5141\u8BB8\u7ED3\u675F\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "\u5728\u6B64\u4E0A\u4E0B\u6587\u4E2D\u4E0D\u5141\u8BB8\u4F7F\u7528\u952E\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "\u952E\u540E\u9700\u8981\u6709\u503C\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u5FC5\u987B\u4E3A {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E0D\u80FD\u4E3A {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "\u5FC5\u987B\u5BF9\u503C\u4F7F\u7528\u8BED\u6CD5\u5206\u6790\u5668\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" \u4E0D\u662F\u652F\u6301\u7684\u5305\u88C5\u7C7B\u578B\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "\u65E0\u6CD5\u4FEE\u6539\u6B64\u5BF9\u8C61\u3002\u8981\u521B\u5EFA\u53EF\u4FEE\u6539\u526F\u672C\uFF0C\u8BF7\u4F7F\u7528 OracleJsonFactory.createObject(OracleJsonObject)\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "\u65E0\u6CD5\u4FEE\u6539\u6B64\u6570\u7EC4\u3002\u8981\u521B\u5EFA\u53EF\u4FEE\u6539\u526F\u672C\uFF0C\u8BF7\u4F7F\u7528 OracleJsonFactory.createArray(OracleJsonArray)\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON \u5BF9\u8C61\u5305\u542B\u91CD\u590D\u7684\u952E\uFF1A{0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "\u65E0\u6CD5\u81EA\u52A8\u68C0\u6D4B\u7F16\u7801\uFF0C\u5B57\u7B26\u6570\u4E0D\u591F\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "\u9700\u8981 EOF \u6807\u8BB0\uFF0C\u4F46\u5F97\u5230\u7684\u662F {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "\u7B2C {1} \u884C\u3001\u7B2C {2} \u5217\u5305\u542B\u610F\u5916\u7684\u5B57\u7B26 {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "\u7B2C {1} \u884C\u3001\u7B2C {2} \u5217\u5305\u542B\u610F\u5916\u7684\u5B57\u7B26 {0}\u3002\u5E94\u4E3A {3}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "\u7B2C {1} \u884C\u3001\u7B2C {2} \u5217\u5305\u542B\u65E0\u6548\u6807\u8BB0 {0}\u3002\u6807\u8BB0\u5E94\u4E3A\uFF1A{3}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() \u4EC5\u5BF9 KEY_NAME\u3001VALUE_STRING\u3001VALUE_NUMBER \u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u6709\u6548\u3002\u4F46\u5F53\u524D\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E3A {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() \u4EC5\u5BF9 VALUE_NUMBER \u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u6709\u6548\u3002\u4F46\u5F53\u524D\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E3A {0}\u3002"},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() \u4EC5\u5BF9 VALUE_NUMBER \u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u6709\u6548\u3002\u4F46\u5F53\u524D\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E3A {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() \u4EC5\u5BF9 VALUE_NUMBER \u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u6709\u6548\u3002\u4F46\u5F53\u524D\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E3A {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() \u4EC5\u5BF9 VALUE_NUMBER \u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u6709\u6548\u3002\u4F46\u5F53\u524D\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E3A {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() \u4EC5\u5BF9 START_ARRAY \u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u6709\u6548\u3002\u4F46\u5F53\u524D\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E3A {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() \u4EC5\u5BF9 START_OBJECT \u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u6709\u6548\u3002\u4F46\u5F53\u524D\u8BED\u6CD5\u5206\u6790\u5668\u72B6\u6001\u4E3A {0}\u3002"},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u4E0D\u652F\u6301\u5E26\u533A\u57DF\u7684\u65F6\u95F4\u6233\u3002\u4EC5\u652F\u6301\u542B\u504F\u79FB\u91CF\u7684\u65F6\u533A\u3002"},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "JSON \u503C\u4E2D\u7684\u5BF9\u8C61\u548C\u6570\u7EC4\u5D4C\u5957\u6DF1\u5EA6\u4E0D\u80FD\u8D85\u8FC7 {0} \u7EA7"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "JSON \u5BF9\u8C61\u7684\u952E\u4E0D\u80FD\u8D85\u8FC7 65535 \u5B57\u8282"},    
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

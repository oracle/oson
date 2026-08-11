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


public class ErrorMessagesJson_zh_TW extends java.util.ListResourceBundle {

  public ErrorMessagesJson_zh_TW() {};

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
     "\u767C\u751F I/O \u7570\u5E38\u72C0\u6CC1"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u4E0D\u652F\u63F4\u5E74\u4EFD \"{0}\""},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\u6EA2\u4F4D, \u503C\u592A\u5927: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "\u4E0D\u652F\u63F4\u7684\u9078\u9805 (\u672A\u5BE6\u884C)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "\u4E8C\u9032\u4F4D JSON \u7121\u6548\u6216\u640D\u6BC0."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "\u4E0D\u652F\u63F4\u7684\u4E8C\u9032\u4F4D JSON \u7248\u672C: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "UTF-8 \u7DE8\u78BC\u7684\u7D22\u5F15\u9375\u9577\u5EA6\u4E0D\u5F97\u8D85\u904E 256 \u500B\u4F4D\u5143\u7D44. \u4EE5\u4E0B\u7D22\u5F15\u9375\u8D85\u51FA\u6B64\u9650\u5236: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "\u6307\u5B9A\u7684 JSON \u592A\u9577, \u7121\u6CD5\u7DE8\u78BC\u6210\u4E8C\u9032\u4F4D JSON.  \u7DE8\u78BC\u7684\u6620\u50CF\u6A94\u5927\u5C0F\u4E0D\u5F97\u8D85\u904E 2GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "\u4E8C\u9032\u4F4D JSON \u7121\u6548\u6216\u640D\u6BC0. \u6307\u5B9A\u7684\u6620\u50CF\u6A94\u53EA\u6709 {0} \u500B\u4F4D\u5143\u7D44."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "\u6307\u5B9A\u7684 java.time.Period \u5DF2\u8A2D\u5B9A\u5929\u6578, \u4F46 Oracle \u5E74\u4EFD\u81F3\u6708\u4EFD\u9593\u9694\u4E0D\u652F\u63F4\u5929\u6578\u8A2D\u5B9A\u503C."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "\u7522\u751F\u5668\u5728\u7D50\u675F\u4E4B\u524D\u95DC\u9589."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "\u5FC5\u9808\u5728\u9019\u500B\u76F8\u95DC\u8CC7\u8A0A\u74B0\u5883\u4E2D\u6307\u5B9A\u7269\u4EF6\u7D22\u5F15\u9375."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\u5BEB\u5165\u7121\u6548. \u5DF2\u5BEB\u5165\u5B8C\u6574\u7684\u503C."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\u6B64\u76F8\u95DC\u8CC7\u8A0A\u74B0\u5883\u4E2D\u4E0D\u5141\u8A31\u7D50\u675F."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "\u6B64\u76F8\u95DC\u8CC7\u8A0A\u74B0\u5883\u4E2D\u4E0D\u5141\u8A31\u4F7F\u7528\u7D22\u5F15\u9375."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "\u7D22\u5F15\u9375\u5F8C\u61C9\u8A72\u8981\u6709\u503C."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "\u5256\u6790\u5668\u72C0\u614B\u5FC5\u9808\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "\u5256\u6790\u5668\u72C0\u614B\u4E0D\u53EF\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "\u5256\u6790\u5668\u5FC5\u9808\u91DD\u5C0D\u503C."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" \u4E0D\u662F\u652F\u63F4\u7684\u5305\u88DD\u51FD\u5F0F\u985E\u578B."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "\u7121\u6CD5\u4FEE\u6539\u6B64\u7269\u4EF6. \u82E5\u8981\u88FD\u4F5C\u53EF\u4FEE\u6539\u7684\u8907\u672C, \u8ACB\u4F7F\u7528 OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "\u7121\u6CD5\u4FEE\u6539\u6B64\u9663\u5217. \u82E5\u8981\u88FD\u4F5C\u53EF\u4FEE\u6539\u7684\u8907\u672C, \u8ACB\u4F7F\u7528 OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON \u7269\u4EF6\u5305\u542B\u91CD\u8907\u7684\u7D22\u5F15\u9375: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "\u7121\u6CD5\u81EA\u52D5\u5075\u6E2C\u7DE8\u78BC, \u5B57\u5143\u6578\u4E0D\u8DB3."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "\u9810\u671F\u61C9\u70BA EOF \u8A18\u865F, \u4F46\u5BE6\u969B\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "\u5728\u7B2C {1} \u884C\u7B2C {2} \u6B04\u4E2D, \u767C\u73FE\u975E\u9810\u671F\u7684\u5B57\u5143 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "\u5728\u7B2C {1} \u884C\u7B2C {2} \u6B04\u4E2D, \u767C\u73FE\u975E\u9810\u671F\u7684\u5B57\u5143 {0}. \u9810\u671F\u61C9\u70BA: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "\u5728\u7B2C {1} \u884C\u7B2C {2} \u6B04\u4E2D, \u767C\u73FE\u7121\u6548\u7684\u8A18\u865F {0}. \u9810\u671F\u7684\u8A18\u865F\u70BA: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() \u53EA\u5C0D KEY_NAME\u3001VALUE_STRING \u4EE5\u53CA VALUE_NUMBER \u5256\u6790\u5668\u72C0\u614B\u6709\u6548. \u4F46\u76EE\u524D\u7684\u5256\u6790\u5668\u72C0\u614B\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() \u53EA\u5C0D VALUE_NUMBER \u5256\u6790\u5668\u72C0\u614B\u6709\u6548. \u4F46\u76EE\u524D\u7684\u5256\u6790\u5668\u72C0\u614B\u70BA {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() \u53EA\u5C0D VALUE_NUMBER \u5256\u6790\u5668\u72C0\u614B\u6709\u6548. \u4F46\u76EE\u524D\u7684\u5256\u6790\u5668\u72C0\u614B\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() \u53EA\u5C0D VALUE_NUMBER \u5256\u6790\u5668\u72C0\u614B\u6709\u6548. \u4F46\u76EE\u524D\u7684\u5256\u6790\u5668\u72C0\u614B\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() \u53EA\u5C0D VALUE_NUMBER \u5256\u6790\u5668\u72C0\u614B\u6709\u6548. \u4F46\u76EE\u524D\u7684\u5256\u6790\u5668\u72C0\u614B\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() \u53EA\u5C0D START_ARRAY \u5256\u6790\u5668\u72C0\u614B\u6709\u6548. \u4F46\u76EE\u524D\u7684\u5256\u6790\u5668\u72C0\u614B\u70BA {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() \u53EA\u5C0D START_OBJECT \u5256\u6790\u5668\u72C0\u614B\u6709\u6548. \u4F46\u76EE\u524D\u7684\u5256\u6790\u5668\u72C0\u614B\u70BA {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u4E0D\u652F\u63F4\u5340\u57DF\u7684\u6642\u6233. \u53EA\u652F\u63F4\u504F\u79FB\u6642\u5340."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "JSON \u503C\u7684\u7269\u4EF6\u548C\u9663\u5217\u5DE2\u72C0\u5C64\u7D1A\u4E0D\u53EF\u8D85\u904E {0} \u5C64"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "JSON \u7269\u4EF6\u7684\u7D22\u5F15\u9375\u4E0D\u53EF\u8D85\u904E 65,535 \u500B\u4F4D\u5143\u7D44"},    
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

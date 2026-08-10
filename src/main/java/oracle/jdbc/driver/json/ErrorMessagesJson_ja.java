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


public class ErrorMessagesJson_ja extends java.util.ListResourceBundle {

  public ErrorMessagesJson_ja() {};

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
     "I/O\u4F8B\u5916\u304C\u767A\u751F\u3057\u307E\u3057\u305F"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u5E74\"{0}\"\u306F\u30B5\u30DD\u30FC\u30C8\u3055\u308C\u3066\u3044\u307E\u305B\u3093"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\u30AA\u30FC\u30D0\u30FC\u30D5\u30ED\u30FC\u3002\u5024\u304C\u5927\u304D\u3059\u304E\u307E\u3059: {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "\u30B5\u30DD\u30FC\u30C8\u3055\u308C\u3066\u3044\u306A\u3044\u30AA\u30D7\u30B7\u30E7\u30F3(\u5B9F\u88C5\u3055\u308C\u3066\u3044\u307E\u305B\u3093)\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "\u30D0\u30A4\u30CA\u30EAJSON\u304C\u7121\u52B9\u3067\u3042\u308B\u304B\u3001\u7834\u640D\u3057\u3066\u3044\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "\u30B5\u30DD\u30FC\u30C8\u3055\u308C\u3066\u3044\u306A\u3044\u30D0\u30A4\u30CA\u30EAJSON\u30D0\u30FC\u30B8\u30E7\u30F3: {0}\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "UTF-8\u3067\u30A8\u30F3\u30B3\u30FC\u30C9\u3055\u308C\u305F\u30AD\u30FC\u306E\u9577\u3055\u306F256\u30D0\u30A4\u30C8\u4EE5\u4E0B\u306B\u3059\u308B\u5FC5\u8981\u304C\u3042\u308A\u307E\u3059\u3002\u6B21\u306E\u30AD\u30FC\u306F\u3053\u306E\u5236\u9650\u3092\u8D85\u3048\u3066\u3044\u307E\u3059: \"{0}\"\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "\u6307\u5B9A\u3057\u305FJSON\u306F\u5927\u304D\u3059\u304E\u308B\u305F\u3081\u3001\u30D0\u30A4\u30CA\u30EAJSON\u3068\u3057\u3066\u30A8\u30F3\u30B3\u30FC\u30C9\u3067\u304D\u307E\u305B\u3093\u3002\u30A8\u30F3\u30B3\u30FC\u30C9\u3055\u308C\u305F\u30A4\u30E1\u30FC\u30B8\u30FB\u30B5\u30A4\u30BA\u306F2GB\u3092\u8D85\u3048\u308B\u3053\u3068\u306F\u3067\u304D\u307E\u305B\u3093\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "\u30D0\u30A4\u30CA\u30EAJSON\u304C\u7121\u52B9\u3067\u3042\u308B\u304B\u3001\u7834\u640D\u3057\u3066\u3044\u307E\u3059\u3002\u6307\u5B9A\u3057\u305F\u30A4\u30E1\u30FC\u30B8\u306B\u306F{0}\u30D0\u30A4\u30C8\u306E\u307F\u304C\u542B\u307E\u308C\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "\u6307\u5B9A\u3057\u305Fjava.time.Period\u306B\u65E5\u6570\u304C\u8A2D\u5B9A\u3055\u308C\u3066\u3044\u307E\u3059\u304C\u3001Oracle\u306Eyear to month interval\u3067\u65E5\u6570\u306F\u30B5\u30DD\u30FC\u30C8\u3055\u308C\u3066\u3044\u307E\u305B\u3093\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "\u30B8\u30A7\u30CD\u30EC\u30FC\u30BF\u306F\u7D42\u4E86\u524D\u306B\u30AF\u30ED\u30FC\u30BA\u3057\u307E\u3057\u305F\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "\u3053\u306E\u30B3\u30F3\u30C6\u30AD\u30B9\u30C8\u306B\u30AA\u30D6\u30B8\u30A7\u30AF\u30C8\u30FB\u30AD\u30FC\u3092\u6307\u5B9A\u3059\u308B\u5FC5\u8981\u304C\u3042\u308A\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\u66F8\u8FBC\u307F\u304C\u7121\u52B9\u3067\u3059\u3002\u5B8C\u5168\u306A\u5024\u304C\u3059\u3067\u306B\u66F8\u304D\u8FBC\u307E\u308C\u3066\u3044\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\u7D42\u4E86\u306F\u3053\u306E\u30B3\u30F3\u30C6\u30AD\u30B9\u30C8\u3067\u306F\u4F7F\u7528\u3067\u304D\u307E\u305B\u3093\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "\u30AD\u30FC\u306F\u3053\u306E\u30B3\u30F3\u30C6\u30AD\u30B9\u30C8\u3067\u306F\u4F7F\u7528\u3067\u304D\u307E\u305B\u3093\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "\u30AD\u30FC\u306E\u5F8C\u306B\u5FC5\u8981\u306A\u5024\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u3092{0}\u306B\u3059\u308B\u5FC5\u8981\u304C\u3042\u308A\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u3092{0}\u306B\u3059\u308B\u3053\u3068\u306F\u3067\u304D\u307E\u305B\u3093\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "\u30D1\u30FC\u30B5\u30FC\u306F\u5024\u306B\u5B58\u5728\u3059\u308B\u5FC5\u8981\u304C\u3042\u308A\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\"\u306F\u30B5\u30DD\u30FC\u30C8\u3055\u308C\u3066\u3044\u308B\u30E9\u30C3\u30D1\u30FC\u30FB\u30BF\u30A4\u30D7\u3067\u306F\u3042\u308A\u307E\u305B\u3093"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "\u3053\u306E\u30AA\u30D6\u30B8\u30A7\u30AF\u30C8\u306F\u5909\u66F4\u3067\u304D\u307E\u305B\u3093\u3002\u5909\u66F4\u53EF\u80FD\u306A\u30B3\u30D4\u30FC\u3092\u4F5C\u6210\u3059\u308B\u306B\u306F\u3001OracleJsonFactory.createObject(OracleJsonObject)\u3092\u4F7F\u7528\u3057\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "\u3053\u306E\u914D\u5217\u306F\u5909\u66F4\u3067\u304D\u307E\u305B\u3093\u3002\u5909\u66F4\u53EF\u80FD\u306A\u30B3\u30D4\u30FC\u3092\u4F5C\u6210\u3059\u308B\u306B\u306F\u3001OracleJsonFactory.createArray(OracleJsonArray)\u3092\u4F7F\u7528\u3057\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON\u30AA\u30D6\u30B8\u30A7\u30AF\u30C8\u306B\u306F\u91CD\u8907\u3059\u308B\u30AD\u30FC\u304C\u542B\u307E\u308C\u3066\u3044\u307E\u3059: {0}"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "\u30A8\u30F3\u30B3\u30FC\u30C7\u30A3\u30F3\u30B0\u3092\u81EA\u52D5\u691C\u51FA\u3067\u304D\u307E\u305B\u3093\u3002\u6587\u5B57\u304C\u4E0D\u8DB3\u3057\u3066\u3044\u307E\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "\u4E88\u671F\u3055\u308C\u308BEOF\u30C8\u30FC\u30AF\u30F3\u3067\u3059\u304C\u3001{0}\u3092\u53D6\u5F97\u3057\u307E\u3057\u305F"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "\u4E88\u671F\u3057\u306A\u3044\u6587\u5B57{0}\u304C\u884C{1}\u3001\u5217{2}\u306B\u3042\u308A\u307E\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "\u4E88\u671F\u3057\u306A\u3044\u6587\u5B57{0}\u304C\u884C{1}\u3001\u5217{2}\u306B\u3042\u308A\u307E\u3059\u3002{3}\u304C\u5FC5\u8981\u3067\u3059\u3002"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "\u7121\u52B9\u306A\u30C8\u30FC\u30AF\u30F3{0}\u304C\u884C{1}\u3001\u5217{2}\u306B\u3042\u308A\u307E\u3059\u3002\u5FC5\u8981\u306A\u30C8\u30FC\u30AF\u30F3\u306F{3}\u3067\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString()\u306FKEY_NAME\u3001VALUE_STRING\u3001VALUE_NUMBER\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306E\u5834\u5408\u306B\u306E\u307F\u6709\u52B9\u3067\u3059\u304C\u3001\u73FE\u5728\u306E\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306F{0}\u3067\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber()\u306FVALUE_NUMBER\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306E\u5834\u5408\u306B\u306E\u307F\u6709\u52B9\u3067\u3059\u304C\u3001\u73FE\u5728\u306E\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306F{0}\u3067\u3059"},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt()\u306FVALUE_NUMBER\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306E\u5834\u5408\u306B\u306E\u307F\u6709\u52B9\u3067\u3059\u304C\u3001\u73FE\u5728\u306E\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306F{0}\u3067\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong()\u306FVALUE_NUMBER\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306E\u5834\u5408\u306B\u306E\u307F\u6709\u52B9\u3067\u3059\u304C\u3001\u73FE\u5728\u306E\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306F{0}\u3067\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal()\u306FVALUE_NUMBER\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306E\u5834\u5408\u306B\u306E\u307F\u6709\u52B9\u3067\u3059\u304C\u3001\u73FE\u5728\u306E\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306F{0}\u3067\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray()\u306FSTART_ARRAY\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306E\u5834\u5408\u306B\u306E\u307F\u6709\u52B9\u3067\u3059\u304C\u3001\u73FE\u5728\u306E\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306F{0}\u3067\u3059"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject()\u306FSTART_OBJECT\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306E\u5834\u5408\u306B\u306E\u307F\u6709\u52B9\u3067\u3059\u304C\u3001\u73FE\u5728\u306E\u30D1\u30FC\u30B5\u30FC\u72B6\u614B\u306F{0}\u3067\u3059"},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u30EA\u30FC\u30B8\u30E7\u30F3\u304C\u3042\u308B\u30BF\u30A4\u30E0\u30B9\u30BF\u30F3\u30D7\u306F\u30B5\u30DD\u30FC\u30C8\u3055\u308C\u307E\u305B\u3093\u3002\u30AA\u30D5\u30BB\u30C3\u30C8\u30FB\u30BF\u30A4\u30E0\u30BE\u30FC\u30F3\u306E\u307F\u30B5\u30DD\u30FC\u30C8\u3055\u308C\u307E\u3059\u3002"},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "JSON\u5024\u5185\u306E\u30AA\u30D6\u30B8\u30A7\u30AF\u30C8\u3068\u914D\u5217\u306F\u3001{0}\u30EC\u30D9\u30EB\u3088\u308A\u6DF1\u304F\u30CD\u30B9\u30C8\u3067\u304D\u307E\u305B\u3093"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "JSON\u30AA\u30D6\u30B8\u30A7\u30AF\u30C8\u306E\u30AD\u30FC\u306F\u300165,535\u30D0\u30A4\u30C8\u3092\u8D85\u3048\u306A\u3044\u3088\u3046\u306B\u3059\u308B\u5FC5\u8981\u304C\u3042\u308A\u307E\u3059"},    
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

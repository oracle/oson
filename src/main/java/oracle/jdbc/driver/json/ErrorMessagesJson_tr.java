/* $Header: wptg/dbjava/src/java/oracle/jdbc/driver/json/ErrorMessagesJson_tr.java /generic/10 2025/05/27 19:52:23 vesegu Exp $ */

/* Copyright (c) 2022, 2025, Oracle and/or its affiliates.*/

/*
   DESCRIPTION
    This file contains JDBC driver internal error codes starting with 'JSON' along with their descriptions
 */

/**
 *  @version $Header: wptg/dbjava/src/java/oracle/jdbc/driver/json/ErrorMessagesJson_tr.java /generic/10 2025/05/27 19:52:23 vesegu Exp $
 *  @author  sreekarr
 *  @since   release 23ai
 */

package oracle.jdbc.driver.json;


public class ErrorMessagesJson_tr extends java.util.ListResourceBundle {

  public ErrorMessagesJson_tr() {};

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
     "Bir G/\u00C7 istisnas\u0131 olu\u015Ftu"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\"{0}\" y\u0131l\u0131 desteklenmiyor"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Ta\u015Fma, de\u011Fer \u00E7ok b\u00FCy\u00FCk: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Desteklenmeyen se\u00E7enek (uygulanmad\u0131)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "\u0130kili JSON ge\u00E7ersiz veya hatal\u0131."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Desteklenmeyen ikili JSON s\u00FCr\u00FCm\u00FC: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "UTF-8 ile kodlanm\u0131\u015F anahtar uzunlu\u011Fu 256 bayt de\u011Ferinden fazla olamaz. \u015Eu anahtar bu s\u0131n\u0131r\u0131 a\u015F\u0131yor: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Belirtilen JSON, ikili JSON olarak kodlanmak i\u00E7in \u00E7ok b\u00FCy\u00FCk. Kodlanm\u0131\u015F resimler 2 GB'\u0131 a\u015Fmamal\u0131d\u0131r."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "\u0130kili JSON ge\u00E7ersiz veya hatal\u0131. Belirtilen resim yaln\u0131zca {0} bayt i\u00E7eriyor."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Belirtilen java.time.Period \u00F6\u011Fesinde g\u00FCn ayarl\u0131 ancak Oracle y\u0131ldan aya aral\u0131\u011F\u0131 g\u00FCnleri desteklemez."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Olu\u015Fturucu biti\u015Ften \u00F6nce kapat\u0131ld\u0131."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Bu ba\u011Flamda bir nesne anahtar\u0131 belirtilmelidir."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Ge\u00E7ersiz yazma i\u015Flemi. Tam bir de\u011Fer zaten yaz\u0131ld\u0131."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Bu ba\u011Flamda biti\u015Fe izin verilmez."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Bu ba\u011Flamda anahtara izin verilmez."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Anahtardan sonra beklenen de\u011Fer."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0} olmal\u0131d\u0131r."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0} olmamal\u0131d\u0131r."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Ayr\u0131\u015Ft\u0131r\u0131c\u0131 bir de\u011Fer \u00FCzerinde olmal\u0131d\u0131r."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" desteklenen bir sar\u0131c\u0131 tipi de\u011Fil."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Bu nesne de\u011Fi\u015Ftirilemiyor. De\u011Fi\u015Ftirilebilir bir kopya olu\u015Fturmak i\u00E7in OracleJsonFactory.createObject(OracleJsonObject) kullan\u0131n."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Bu dizi de\u011Fi\u015Ftirilemiyor. De\u011Fi\u015Ftirilebilir bir kopya olu\u015Fturmak i\u00E7in OracleJsonFactory.createArray(OracleJsonArray) kullan\u0131n."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON nesnesi tekrarlanan anahtar i\u00E7eriyor: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Kodlama otomatik olarak alg\u0131lanam\u0131yor, yeterli karakter yok."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Beklenen Dosya Sonu belirteci, ancak {0} al\u0131nd\u0131."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "{1} sat\u0131r\u0131 ve {2} s\u00FCtununda beklenmeyen karakter: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "{1} sat\u0131r\u0131 ve {2} s\u00FCtununda beklenmeyen karakter: {0}. Beklenen: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "{1} sat\u0131r\u0131 ve {2} s\u00FCtununda ge\u00E7ersiz belirte\u00E7: {0}. Beklenen belirte\u00E7ler: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() sadece KEY_NAME, VALUE_STRING, VALUE_NUMBER ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu i\u00E7in ge\u00E7erlidir. Ancak ge\u00E7erli ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() sadece VALUE_NUMBER ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu i\u00E7in ge\u00E7erlidir. Ancak ge\u00E7erli ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() sadece VALUE_NUMBER ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu i\u00E7in ge\u00E7erlidir. Ancak ge\u00E7erli ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() sadece VALUE_NUMBER ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu i\u00E7in ge\u00E7erlidir. Ancak ge\u00E7erli ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() sadece VALUE_NUMBER ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu i\u00E7in ge\u00E7erlidir. Ancak ge\u00E7erli ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() sadece START_ARRAY ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu i\u00E7in ge\u00E7erlidir. Ancak ge\u00E7erli ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() sadece START_OBJECT ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu i\u00E7in ge\u00E7erlidir. Ancak ge\u00E7erli ayr\u0131\u015Ft\u0131r\u0131c\u0131 durumu {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "B\u00F6lgesi olan zaman damgas\u0131 desteklenmiyor. Yaln\u0131zca konum saat dilimleri destekleniyor."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "JSON de\u011Ferindeki nesneler ve diziler en fazla {0} d\u00FCzey i\u00E7 i\u00E7e ge\u00E7ebilir"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "JSON nesnesinin anahtarlar\u0131 65.535 bayt de\u011Ferini a\u015Famaz."},    
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

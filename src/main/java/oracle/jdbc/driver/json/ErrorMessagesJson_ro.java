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


public class ErrorMessagesJson_ro extends java.util.ListResourceBundle {

  public ErrorMessagesJson_ro() {};

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
     "A survenit o excep\u0163ie I/O"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "Anul \"{0}\" nu este acceptat"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Dep\u0103\u015Fire, valoare prea mare: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Op\u0163iune neacceptat\u0103 (neimplementat\u0103)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "JSON-ul binar este nevalid sau corupt."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Versiune neacceptat\u0103 de JSON binar: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "Lungimea cheii codificate cu UTF-8 nu trebuie s\u0103 dep\u0103\u015Feasc\u0103 256 bytes. Urm\u0103toarea cheie dep\u0103\u015Fe\u015Fte aceast\u0103 limit\u0103: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "JSON-ul specificat este prea mare pentru a fi codificat ca JSON binar. Dimensiunea imaginilor codificate nu trebuie s\u0103 dep\u0103\u015Feasc\u0103 2GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "JSON-ul binar este nevalid sau corupt. Imaginea specificat\u0103 con\u0163ine doar {0} bytes."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Elementul java.time.Period specificat are setate zile, dar INTERVAL YEAR TO MONTH al Oracle nu accept\u0103 zile."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generator \u00EEnchis \u00EEnainte de \u00EEncheiere."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Trebuie specificat\u0103 o cheie de obiect \u00EEn acest context."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Scriere nevalid\u0103. O valoare complet\u0103 a fost scris\u0103 deja."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "\u00CEncheiere nepermis\u0103 \u00EEn acest context."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Cheie nepermis\u0103 \u00EEn acest context."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Se a\u015Fteapt\u0103 o valoare dup\u0103 cheie."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Starea interpretorului trebuie s\u0103 fie {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Starea interpretorului trebuie s\u0103 nu fie {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Interpretorul trebuie s\u0103 fie la o valoare."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" nu este un tip de wrapper acceptat."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Acest obiect nu poate fi modificat. Pentru a crea o copie care poate fi modificat\u0103, utiliza\u0163i OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Aceast\u0103 matrice nu poate fi modificat\u0103. Pentru a crea o copie care poate fi modificat\u0103, utiliza\u0163i OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "Obiectul JSON con\u0163ine cheie duplicat: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Nu se poate detecta automat codificarea, caractere insuficiente."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Se a\u015Ftepta tokenul EOF, dar s-a ob\u0163inut {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Caracter nea\u015Fteptat {0} la linia {1}, coloana {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Caracter nea\u015Fteptat {0} la linia {1}, coloana {2}. Se a\u015Ftepta {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Token nevalid {0} la linia {1}, coloana {2}. Tokenurile a\u015Fteptate sunt: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() este valid numai pentru st\u0103rile KEY_NAME, VALUE_STRING, VALUE_NUMBER ale interpretorului. Dar starea curent\u0103 a interpretorului este {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() este valid numai pentru starea VALUE_NUMBER a interpretorului. Dar starea curent\u0103 a interpretorului este {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() este valid numai pentru starea VALUE_NUMBER a interpretorului. Dar starea curent\u0103 a interpretorului este {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() este valid numai pentru starea VALUE_NUMBER a interpretorului. Dar starea curent\u0103 a interpretorului este {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() este valid numai pentru starea VALUE_NUMBER a interpretorului. Dar starea curent\u0103 a interpretorului este {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() este valid doar pentru starea interpretorului START_ARRAY. \u00CEns\u0103 starea curent\u0103 a interpretorului este {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() este valid numai pentru starea interpretorului START_ARRAY. Dar starea curent\u0103 a interpretorului este {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Nu este acceptat un marcaj temporal cu o regiune. Se accept\u0103 numai fusuri orare decalate."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Obiectele \u015Fi matricele din valoarea JSON nu se pot imbrica mai mult de {0} niveluri"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "Cheile unui obiect JSON nu pot dep\u0103\u015Fi 65.535 bytes"},    
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

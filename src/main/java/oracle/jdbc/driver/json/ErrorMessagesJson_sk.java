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


public class ErrorMessagesJson_sk extends java.util.ListResourceBundle {

  public ErrorMessagesJson_sk() {};

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
     "Vyskytla sa v\u00FDnimka I/O"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "Rok \"{0}\" nie je podporovan\u00FD"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Prete\u010Denie, hodnota je pr\u00EDli\u0161 ve\u013Ek\u00E1: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Nepodporovan\u00E1 vo\u013Eba (neimplementovan\u00E1)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Bin\u00E1rny s\u00FAbor JSON je neplatn\u00FD alebo po\u0161koden\u00FD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Nepodporovan\u00E1 verzia bin\u00E1rneho s\u00FAboru JSON: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "K\u013E\u00FA\u010D s k\u00F3dovan\u00EDm UTF-8 nesmie by\u0165 v\u00E4\u010D\u0161\u00ED ako 256 bajtov. Tento limit je prekro\u010Den\u00FD pri nasleduj\u00FAcom k\u013E\u00FA\u010Di: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Zadan\u00FD s\u00FAbor JSON je pr\u00EDli\u0161 ve\u013Ek\u00FD na to, aby bol k\u00F3dovan\u00FD ako bin\u00E1rny s\u00FAbor JSON. Ve\u013Ekos\u0165 k\u00F3dovan\u00FDch obr\u00E1zkov nesmie prekro\u010Di\u0165 2 GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Bin\u00E1rny s\u00FAbor JSON je neplatn\u00FD alebo po\u0161koden\u00FD. Zadan\u00FD obr\u00E1zok obsahuje len {0} B."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Zadan\u00E1 trieda java.time.Period m\u00E1 nastaven\u00E9 dni, ale interval rok a\u017E mesiac definovan\u00FD spolo\u010Dnos\u0165ou Oracle dni nepodporuje."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Gener\u00E1tor sa zatvoril pred skon\u010Den\u00EDm."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "V tomto kontexte mus\u00ED by\u0165 zadan\u00FD k\u013E\u00FA\u010D objektu."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Neplatn\u00FD z\u00E1pis. Cel\u00E1 hodnota u\u017E bola zap\u00EDsan\u00E1."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Ukon\u010Denie nie je v tomto kontexte povolen\u00E9."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "K\u013E\u00FA\u010D nie je v tomto kontexte povolen\u00FD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "O\u010Dak\u00E1van\u00E1 hodnota za k\u013E\u00FA\u010Dom."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Stav syntaktick\u00E9ho analyz\u00E1tora mus\u00ED by\u0165 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Stav syntaktick\u00E9ho analyz\u00E1tora nesmie by\u0165 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Syntaktick\u00FD analyz\u00E1tor mus\u00ED by\u0165 na hodnote."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" nie je podporovan\u00FD typ wrappera."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Tento objekt nie je mo\u017En\u00E9 modifikova\u0165. Ak chcete vytvori\u0165 k\u00F3piu, ktor\u00FA je mo\u017En\u00E9 modifikova\u0165, pou\u017Eite pr\u00EDkaz OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Toto pole nie je mo\u017En\u00E9 modifikova\u0165. Ak chcete vytvori\u0165 k\u00F3piu, ktor\u00FA je mo\u017En\u00E9 modifikova\u0165, pou\u017Eite pr\u00EDkaz OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "Objekt JSON obsahuje duplicitn\u00FD k\u013E\u00FA\u010D: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "K\u00F3dovanie nie je mo\u017En\u00E9 zisti\u0165 automaticky pre nedostatok znakov."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "O\u010Dak\u00E1val sa token EOF, ale prijat\u00E9 bolo {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Neo\u010Dak\u00E1van\u00FD znak {0} v riadku {1} a st\u013Apci {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Neo\u010Dak\u00E1van\u00FD znak {0} v riadku {1} a st\u013Apci {2}. O\u010Dak\u00E1val sa znak {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Neplatn\u00FD token {0} v riadku {1} a st\u013Apci {2}. O\u010Dak\u00E1van\u00E9 tokeny s\u00FA: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "Met\u00F3da JsonParser#getString() je platn\u00E1 len pre tieto stavy syntaktick\u00E9ho analyz\u00E1tora: KEY_NAME, VALUE_STRING a VALUE_NUMBER. Ale aktu\u00E1lny stav syntaktick\u00E9ho analyz\u00E1tora je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "Met\u00F3da JsonParser#isIntegralNumber() je platn\u00E1 len pre tento stav syntaktick\u00E9ho analyz\u00E1tora: VALUE_NUMBER. Ale aktu\u00E1lny stav syntaktick\u00E9ho analyz\u00E1tora je {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "Met\u00F3da JsonParser#getInt() je platn\u00E1 len pre tento stav syntaktick\u00E9ho analyz\u00E1tora: VALUE_NUMBER. Ale aktu\u00E1lny stav syntaktick\u00E9ho analyz\u00E1tora je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "Met\u00F3da JsonParser#getLong() je platn\u00E1 len pre tento stav syntaktick\u00E9ho analyz\u00E1tora: VALUE_NUMBER. Ale aktu\u00E1lny stav syntaktick\u00E9ho analyz\u00E1tora je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "Met\u00F3da JsonParser#getBigDecimal() je platn\u00E1 len pre tento stav syntaktick\u00E9ho analyz\u00E1tora: VALUE_NUMBER. Ale aktu\u00E1lny stav syntaktick\u00E9ho analyz\u00E1tora je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "Met\u00F3da JsonParser#getArray() je platn\u00E1 len pre stav syntaktick\u00E9ho analyz\u00E1tora START_ARRAY. Aktu\u00E1lny stav syntaktick\u00E9ho analyz\u00E1tora v\u0161ak je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "Met\u00F3da JsonParser#getObject() je platn\u00E1 len pre tento stav syntaktick\u00E9ho analyz\u00E1tora: START_OBJECT. Ale aktu\u00E1lny stav syntaktick\u00E9ho analyz\u00E1tora je {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u010Casov\u00E1 zna\u010Dka s oblas\u0165ou nie je podporovan\u00E1. Podporovan\u00E9 s\u00FA len posunut\u00E9 \u010Dasov\u00E9 p\u00E1sma."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Objekty a polia v hodnote JSON nem\u00F4\u017Eu by\u0165 vnoren\u00E9 hlb\u0161ie ne\u017E do {0} \u00FArovn\u00ED"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "K\u013E\u00FA\u010De objektu JSON nesm\u00FA prekro\u010Di\u0165 65\u00A0535 bajtov"},    
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

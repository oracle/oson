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


public class ErrorMessagesJson_cs extends java.util.ListResourceBundle {

  public ErrorMessagesJson_cs() {};

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
     "Do\u0161lo k v\u00FDjimce vstupu/v\u00FDstupu"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "Rok \"{0}\" nen\u00ED podporov\u00E1n"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "P\u0159ete\u010Den\u00ED, p\u0159\u00EDli\u0161 velk\u00E1 hodnota: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Nepodporovan\u00E1 volba (nen\u00ED implementov\u00E1na)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Bin\u00E1rn\u00ED soubor JSON je neplatn\u00FD nebo po\u0161kozen\u00FD."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Nepodporovan\u00E1 verze bin\u00E1rn\u00EDho souboru JSON: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "D\u00E9lka kl\u00ED\u010De k\u00F3dovan\u00E9ho v UTF-8 nesm\u00ED b\u00FDt vet\u0161\u00ED ne\u017E 256 bajt\u016F. N\u00E1sleduj\u00EDc\u00ED kl\u00ED\u010D tento limit p\u0159ekra\u010Duje: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Zadan\u00FD soubor JSON je p\u0159\u00EDli\u0161 velk\u00FD na to, aby mohl b\u00FDt k\u00F3dov\u00E1n jako bin\u00E1rn\u00ED JSON. Velikost k\u00F3dovan\u00FDch obraz\u016F nesm\u00ED p\u0159ekro\u010Dit 2 GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Bin\u00E1rn\u00ED soubor JSON je neplatn\u00FD nebo po\u0161kozen\u00FD. Zadan\u00FD obraz obsahuje jen {0} bajt\u016F."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Pro zadan\u00E9 obdob\u00ED java.time.Period jsou nastaveny dny, ale interval Oracle roku a\u017E m\u011Bs\u00EDce dny nepodporuje."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Gener\u00E1tor byl uzav\u0159en p\u0159ed dokon\u010Den\u00EDm."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "V tomto kontextu je nutn\u00E9 zadat kl\u00ED\u010D objektu."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Neplatn\u00FD z\u00E1pis. Ji\u017E byla zaps\u00E1na \u00FApln\u00E1 hodnota."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Konec nen\u00ED v tomto kontextu povolen."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Kl\u00ED\u010D nen\u00ED v tomto kontextu povolen."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "O\u010Dek\u00E1v\u00E1na hodnota za kl\u00ED\u010Dem."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Stav syntaktick\u00E9ho analyz\u00E1toru mus\u00ED b\u00FDt {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Stav syntaktick\u00E9ho analyz\u00E1toru nesm\u00ED b\u00FDt {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Syntaktick\u00FD analyz\u00E1tor mus\u00ED b\u00FDt na hodnot\u011B."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" nen\u00ED podporovan\u00FD typ ob\u00E1lky."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Tento objekt nelze zm\u011Bnit. Chcete-li vytvo\u0159it m\u011Bnitelnou kopii, pou\u017Eijte z\u00E1pis OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Toto pole nelze zm\u011Bnit. Chcete-li vytvo\u0159it m\u011Bnitelnou kopii, pou\u017Eijte z\u00E1pis OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "Objekt JSON obsahuje duplicitn\u00ED kl\u00ED\u010D: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Nelze automaticky zjistit k\u00F3dov\u00E1n\u00ED z d\u016Fvodu nedostate\u010Dn\u00E9ho po\u010Dtu znak\u016F."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Byl o\u010Dek\u00E1v\u00E1n token EOF, ale bylo p\u0159ijato {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Neo\u010Dek\u00E1van\u00FD znak {0} na \u0159\u00E1dku {1}, ve sloupci {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Neo\u010Dek\u00E1van\u00FD znak {0} na \u0159\u00E1dku {1}, ve sloupci {2}. O\u010Dek\u00E1van\u00E1 hodnota: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Neplatn\u00FD token {0} na \u0159\u00E1dku {1}, ve sloupci {2}. O\u010Dek\u00E1van\u00E9 tokeny jsou: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "Platn\u00E9 stavy syntaktick\u00E9ho analyz\u00E1toru pro JsonParser#getString() jsou pouze KEY_NAME, VALUE_STRING a VALUE_NUMBER. Ale aktu\u00E1ln\u00ED stav syntaktick\u00E9ho analyz\u00E1toru je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "Platn\u00FD stav syntaktick\u00E9ho analyz\u00E1toru pro JsonParser#isIntegralNumber() je pouze VALUE_NUMBER. Ale aktu\u00E1ln\u00ED stav syntaktick\u00E9ho analyz\u00E1toru je {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "Platn\u00FD stav syntaktick\u00E9ho analyz\u00E1toru pro JsonParser#getInt() je pouze VALUE_NUMBER. Ale aktu\u00E1ln\u00ED stav syntaktick\u00E9ho analyz\u00E1toru je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "Platn\u00FD stav syntaktick\u00E9ho analyz\u00E1toru pro JsonParser#getLong() je pouze VALUE_NUMBER. Ale aktu\u00E1ln\u00ED stav syntaktick\u00E9ho analyz\u00E1toru je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "Platn\u00FD stav syntaktick\u00E9ho analyz\u00E1toru pro JsonParser#getBigDecimal() je pouze VALUE_NUMBER. Ale aktu\u00E1ln\u00ED stav syntaktick\u00E9ho analyz\u00E1toru je {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() je platn\u00E9 pouze pro stav syntaktick\u00E9ho analyz\u00E1toru START_ARRAY. Aktu\u00E1ln\u00ED stav syntaktick\u00E9ho analyz\u00E1toru je ale {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "Platn\u00FD stav syntaktick\u00E9ho analyz\u00E1toru pro JsonParser#getObject() je pouze START_OBJECT. Ale aktu\u00E1ln\u00ED stav syntaktick\u00E9ho analyz\u00E1toru je {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "\u010Casov\u00E1 zna\u010Dka s oblast\u00ED nen\u00ED podporov\u00E1na. Podporov\u00E1ny jsou pouze \u010Dasov\u00E9 z\u00F3ny s posunem."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Objekty a\u00A0pole v\u00A0hodnot\u011B JSON nesm\u00ED b\u00FDt vno\u0159eny hloub\u011Bji ne\u017E {0} \u00FArovn\u00ED"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "Kl\u00ED\u010De objektu JSON nesm\u00ED p\u0159ekro\u010Dit 65\u00A0535\u00A0bajt\u016F"},    
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

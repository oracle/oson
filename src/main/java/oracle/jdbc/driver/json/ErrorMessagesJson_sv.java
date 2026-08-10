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


public class ErrorMessagesJson_sv extends java.util.ListResourceBundle {

  public ErrorMessagesJson_sv() {};

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
     "Ett I/O-undantag intr\u00E4ffade"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u00C5ret \"{0}\" st\u00F6ds inte"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Spill, f\u00F6r stort v\u00E4rde: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Alternativet st\u00F6ds inte (inte implementerat)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Den bin\u00E4ra JSON \u00E4r ogiltig eller skadad."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Den bin\u00E4ra JSON-versionen st\u00F6ds inte: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "Den UTF-8-krypterade nyckeln f\u00E5r inte vara st\u00F6rre \u00E4n 256 byte. F\u00F6ljande nyckel \u00F6verskrider den gr\u00E4nsen: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Den angivna JSON \u00E4r f\u00F6r stor f\u00F6r att krypteras som en bin\u00E4r JSON. Den krypterade bildstorleken f\u00E5r inte \u00F6verskrida 2 GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Den bin\u00E4ra JSON \u00E4r ogiltig eller skadad. Den angivna bilden inneh\u00E5ller endast {0} byte."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Den angivna java.time.Period har angivna dagar, men Oracles \u00E5r-till-m\u00E5nad-intervall st\u00F6der inte dagar."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generatorn st\u00E4ngdes f\u00F6re slutet."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "En objektnyckel m\u00E5ste anges i den h\u00E4r kontexten."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Ogiltig skrivning. Ett fullst\u00E4ndigt v\u00E4rde har redan skrivits."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Slutet \u00E4r inte till\u00E5tet i den h\u00E4r kontexten."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Nyckeln \u00E4r inte till\u00E5ten i den h\u00E4r kontexten."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "F\u00F6rv\u00E4ntat v\u00E4rde efter nyckel."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Parsertillst\u00E5ndet m\u00E5ste vara {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Parsertillst\u00E5ndet f\u00E5r inte vara {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Parser m\u00E5ste ha ett v\u00E4rde."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" \u00E4r en wrappertyp som inte st\u00F6ds."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Det h\u00E4r objektet kan inte \u00E4ndras. Om du vill skapa en kopia som kan \u00E4ndras anv\u00E4nder du OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Den h\u00E4r uppst\u00E4llningen kan inte \u00E4ndras. Om du vill skapa en kopia som kan \u00E4ndras anv\u00E4nder du OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON-objektet inneh\u00E5ller dubblettnyckel: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Kan inte autoavk\u00E4nna kodning, otillr\u00E4ckligt antal tecken."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "F\u00F6rv\u00E4ntade EOF-token, men fick {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Ov\u00E4ntat tecken ({0}) p\u00E5 raden {1} i kolumnen {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Ov\u00E4ntat tecken ({0}) p\u00E5 raden {1} i kolumnen {2}. F\u00F6rv\u00E4ntat: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Ogiltigt token ({0}) p\u00E5 raden {1} i kolumnen {2}. F\u00F6rv\u00E4ntade token \u00E4r: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() \u00E4r endast giltigt f\u00F6r parsertillst\u00E5nden KEY_NAME, VALUE_STRING, VALUE_NUMBER. Men det aktuella parsertillst\u00E5ndet \u00E4r {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() \u00E4r endast giltigt f\u00F6r parsertillst\u00E5ndet VALUE_NUMBER. Men det aktuella parsertillst\u00E5ndet \u00E4r {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() \u00E4r endast giltigt f\u00F6r parsertillst\u00E5ndet VALUE_NUMBER. Men det aktuella parsertillst\u00E5ndet \u00E4r {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() \u00E4r endast giltigt f\u00F6r parsertillst\u00E5ndet VALUE_NUMBER. Men det aktuella parsertillst\u00E5ndet \u00E4r {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() \u00E4r endast giltigt f\u00F6r parsertillst\u00E5ndet VALUE_NUMBER. Men det aktuella parsertillst\u00E5ndet \u00E4r {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() \u00E4r endast giltigt f\u00F6r parsertillst\u00E5ndet START_ARRAY. Men det aktuella parsertillst\u00E5ndet \u00E4r {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() \u00E4r endast giltigt f\u00F6r parsertillst\u00E5ndet START_OBJECT. Men det aktuella parsertillst\u00E5ndet \u00E4r {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "En tidsst\u00E4mpel med en region st\u00F6ds inte. Endast f\u00F6rskjutna tidszoner st\u00F6ds."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Objekten och uppst\u00E4llningarna i JSON-v\u00E4rdet f\u00E5r inte kapslas djupare \u00E4n {0} niv\u00E5er"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "Nycklarna till ett JSON-objekt f\u00E5r inte \u00F6verstiga 65\u00A0535 byte"},    
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

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


public class ErrorMessagesJson_hu extends java.util.ListResourceBundle {

  public ErrorMessagesJson_hu() {};

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
     "I/O kiv\u00E9tel t\u00F6rt\u00E9nt"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "A(z) \"{0}\" \u00E9v nem t\u00E1mogatott"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "T\u00FAlcsordul\u00E1s, t\u00FAl nagy \u00E9rt\u00E9k: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Nem t\u00E1mogatott opci\u00F3 (nincs implement\u00E1lva)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "A bin\u00E1ris JSON \u00E9rv\u00E9nytelen vagy s\u00E9r\u00FClt."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Nem t\u00E1mogatott bin\u00E1ris JSON verzi\u00F3: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "Az UTF-8 k\u00F3dolt kulcs nem lehet hosszabb mint 256 b\u00E1jt. A k\u00F6vetkez\u0151 kulcs meghaladja ezt a maxim\u00E1lis hosszt: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "A megadott JSON t\u00FAl nagy ahhoz, hogy k\u00F3dolhat\u00F3 legyen bin\u00E1ris JSON-k\u00E9nt. A k\u00F3dolt k\u00E9p m\u00E9rete nem lehet nagyobb mint 2 GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "A bin\u00E1ris JSON \u00E9rv\u00E9nytelen vagy s\u00E9r\u00FClt. A megadott k\u00E9p csak {0} b\u00E1jtot tartalmaz."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "A megadott java.time.Period param\u00E9terhez nap van be\u00E1ll\u00EDtva, azonban az Oracle \u00E9v-h\u00F3nap id\u0151k\u00F6ze nem t\u00E1mogatja a napot."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "A gener\u00E1tor lez\u00E1rt a z\u00E1r\u00F3 \u00E9rt\u00E9k el\u0151tt."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Ebben a k\u00F6rnyezetben meg kell adni egy objektumkulcsot."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "\u00C9rv\u00E9nytelen \u00EDr\u00E1s. M\u00E1r van be\u00EDrva teljes \u00E9rt\u00E9k."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Z\u00E1r\u00F3 \u00E9rt\u00E9k nem megengedett ebben a k\u00F6rnyezetben."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Kulcs nem megengedett ebben a k\u00F6rnyezetben."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "A rendszer \u00E9rt\u00E9ket v\u00E1r a kulcs ut\u00E1n."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Az elemz\u0151 \u00E1llapot\u00E1nak {0} \u00E9rt\u00E9k\u0171nek kell lennie."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Az elemz\u0151 \u00E1llapot\u00E1nak \u00E9rt\u00E9ke nem lehet {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Az elemz\u0151nek \u00E9rt\u00E9kkel kell rendelkeznie."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "a(z) \"{0}\" nem t\u00E1mogatott lek\u00E9pez\u0151t\u00EDpus."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Ez az objektum nem m\u00F3dos\u00EDthat\u00F3. M\u00F3dos\u00EDthat\u00F3 p\u00E9ld\u00E1ny l\u00E9trehoz\u00E1s\u00E1hoz haszn\u00E1lja a k\u00F6vetkez\u0151t: OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Ez a t\u00F6mb nem m\u00F3dos\u00EDthat\u00F3. M\u00F3dos\u00EDthat\u00F3 p\u00E9ld\u00E1ny l\u00E9trehoz\u00E1s\u00E1hoz haszn\u00E1lja a k\u00F6vetkez\u0151t: OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "A JSON objektum ism\u00E9tl\u0151d\u0151 kulcsot tartalmaz: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "K\u00F3dol\u00E1s automatikus felismer\u00E9se nem lehets\u00E9ges, nincs elegend\u0151 karakter."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "A v\u00E1rt EOF token helyett {0} \u00E9rkezett."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "V\u00E1ratlan {0} karakter: {1}. sor, {2}. oszlop."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "V\u00E1ratlan {0} karakter: {1}. sor, {2}. oszlop. V\u00E1rt karakter: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "V\u00E1ratlan {0} token: {1}. sor, {2}. oszlop. V\u00E1rt tokenek: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "A JsonParser#getString() csak az elemz\u0151 KEY_NAME, VALUE_STRING, VALUE_NUMBER \u00E1llapot\u00E1ban \u00E9rv\u00E9nyes. Az elemz\u0151 aktu\u00E1lis \u00E1llapota {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "A JsonParser#isIntegralNumber() csak az elemz\u0151 VALUE_NUMBER \u00E1llapot\u00E1ban \u00E9rv\u00E9nyes. Az elemz\u0151 aktu\u00E1lis \u00E1llapota {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "A JsonParser#getInt() csak az elemz\u0151 VALUE_NUMBER \u00E1llapot\u00E1ban \u00E9rv\u00E9nyes. Az elemz\u0151 aktu\u00E1lis \u00E1llapota {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "A JsonParser#getLong() csak az elemz\u0151 VALUE_NUMBER \u00E1llapot\u00E1ban \u00E9rv\u00E9nyes. Az elemz\u0151 aktu\u00E1lis \u00E1llapota {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "A JsonParser#getBigDecimal() csak az elemz\u0151 VALUE_NUMBER \u00E1llapot\u00E1ban \u00E9rv\u00E9nyes. Az elemz\u0151 aktu\u00E1lis \u00E1llapota {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "A JsonParser#getArray() csak az elemz\u0151 START_ARRAY \u00E1llapot\u00E1ban \u00E9rv\u00E9nyes. Az elemz\u0151 aktu\u00E1lis \u00E1llapota {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "A JsonParser#getObject() csak az elemz\u0151 START_OBJECT \u00E1llapot\u00E1ban \u00E9rv\u00E9nyes. Az elemz\u0151 aktu\u00E1lis \u00E1llapota {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "A r\u00E9gi\u00F3val megadott id\u0151b\u00E9lyeg nem t\u00E1mogatott. Csak az eltol\u00E1sos id\u0151z\u00F3n\u00E1k t\u00E1mogatottak."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "A JSON-\u00E9rt\u00E9kben tal\u00E1lhat\u00F3 objektumok \u00E9s t\u00F6mb\u00F6k nem \u00E1gyazhat\u00F3k be {0} szintn\u00E9l m\u00E9lyebbre"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "A JSON-objektum kulcsainak m\u00E9rete nem haladhatja meg a 65535 b\u00E1jtot."},    
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

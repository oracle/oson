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


public class ErrorMessagesJson_fi extends java.util.ListResourceBundle {

  public ErrorMessagesJson_fi() {};

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
     "Tapahtui I/O-poikkeus"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "Vuotta {0} ei tueta"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Ylivuoto, arvo on liian suuri: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Ei-tuettu valinta (ei otettu k\u00E4ytt\u00F6\u00F6n)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Binaarinen JSON on virheellinen tai vioittunut."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Ei-tuettu binaarinen JSON-versio: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "UTF-8-avaimen enimm\u00E4ispituus on 256 tavua. Seuraava avain ylitt\u00E4\u00E4 pituuden: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "M\u00E4\u00E4ritetty JSON on liian suuri koodattavaksi binaarisena JSON-kuvana. Koodattavien kuvien enimm\u00E4iskoko on 2 Gt."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Binaarinen JSON on virheellinen tai vioittunut. M\u00E4\u00E4ritetty kuva sis\u00E4lt\u00E4\u00E4 vain {0} tavua."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Kohteessa java.time.Period on m\u00E4\u00E4ritetty p\u00E4ivi\u00E4, mutta Oraclen vuosi-kuukausi-v\u00E4liss\u00E4 ei tueta p\u00E4ivi\u00E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Luontiohjelma suljettiin ennen loppua."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Objektin avain on m\u00E4\u00E4ritett\u00E4v\u00E4 t\u00E4ss\u00E4 kontekstissa."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Virheellinen kirjoitus. T\u00E4ysi arvo on jo kirjoitettu."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Loppu ei ole sallittu t\u00E4ss\u00E4 kontekstissa."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Avain ei ole sallittu t\u00E4ss\u00E4 kontekstissa."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Odotettiin arvoa avaimen j\u00E4lkeen."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "J\u00E4sent\u00E4j\u00E4n tilan on oltava {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "J\u00E4sent\u00E4j\u00E4n tila ei saa olla {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "J\u00E4sent\u00E4j\u00E4n on perustuttava arvoon."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "k\u00E4\u00E4reen tyyppi\u00E4 {0} ei tueta."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "T\u00E4t\u00E4 objektia ei voi muokata. Voit luoda muokattavan kopion k\u00E4ytt\u00E4m\u00E4ll\u00E4 menetelm\u00E4\u00E4 OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "T\u00E4t\u00E4 taulukkoa ei voi muokata. Voit luoda muokattavan kopion k\u00E4ytt\u00E4m\u00E4ll\u00E4 menetelm\u00E4\u00E4 OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON-objekti sis\u00E4lt\u00E4\u00E4 toistuvan avaimen: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Koodausta ei voi havaita automaattisesti, ei tarpeeksi merkkej\u00E4."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Odotettiin EOF-merkki\u00E4, mutta saatiin {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Odottamaton merkki {0} rivill\u00E4 {1} sarakkeessa {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Odottamaton merkki {0} rivill\u00E4 {1} sarakkeessa {2}. Odotus oli {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Virheellinen merkki {0} rivill\u00E4 {1} sarakkeessa {2}. Odotetut merkit ovat: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() on sallittu vain j\u00E4sentimen tiloissa KEY_NAME, VALUE_STRING, VALUE_NUMBER. Mutta nykyinen j\u00E4sentimen tila on {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() on sallittu vain j\u00E4sentimen tilassa VALUE_NUMBER. Mutta nykyinen j\u00E4sentimen tila on {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() on sallittu vain j\u00E4sentimen tilassa VALUE_NUMBER. Mutta nykyinen j\u00E4sentimen tila on {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() on sallittu vain j\u00E4sentimen tilassa VALUE_NUMBER. Mutta nykyinen j\u00E4sentimen tila on {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() on sallittu vain j\u00E4sentimen tilassa VALUE_NUMBER. Mutta nykyinen j\u00E4sentimen tila on {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() on sallittu vain j\u00E4sentimen tilassa START_ARRAY, mutta nykyinen j\u00E4sentimen tila on {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() on sallittu vain j\u00E4sentimen tilassa START_OBJECT. Mutta nykyinen j\u00E4sentimen tila on {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Aluetta sis\u00E4lt\u00E4v\u00E4\u00E4 aikaleimaa ei tueta. Vain siirtym\u00E4ll\u00E4 ilmoitettuja aikavy\u00F6hykkeit\u00E4 tuetaan."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "JSON-arvon objektien ja matriisien sis\u00E4kk\u00E4isyys ei voi olla yli {0} tasoa"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "JSON-objektin avaimet eiv\u00E4t saa ylitt\u00E4\u00E4 65\u00A0535 tavua"},    
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

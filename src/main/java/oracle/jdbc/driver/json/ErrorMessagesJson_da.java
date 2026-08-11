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


public class ErrorMessagesJson_da extends java.util.ListResourceBundle {

  public ErrorMessagesJson_da() {};

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
     "En I/O-undtagelse opstod"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u00C5ret \"{0}\" underst\u00F8ttes ikke"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Overl\u00F8b, v\u00E6rdi er for stor: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Ikke-underst\u00F8ttet valg (ikke implementeret)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Bin\u00E6r JSON er ugyldig eller beskadiget."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Ikke underst\u00F8ttet bin\u00E6r JSON-version: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "Den UTF-8-kodede n\u00F8glel\u00E6ngde m\u00E5 ikke v\u00E6re st\u00F8rre end 256 byte. F\u00F8lgende n\u00F8gle overskrider denne gr\u00E6nse: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Den angivne JSON er for stor til at blive kodet som bin\u00E6r JSON. Den kodede billedst\u00F8rrelse m\u00E5 ikke overstige 2 GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Bin\u00E6r JSON er ugyldig eller beskadiget. Det angivne billede indeholder kun {0} byte."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Den angivne java.time.Period har dage sat, men Oracle-\u00E5r til m\u00E5ned-intervallet underst\u00F8tter ikke dage."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generator lukket f\u00F8r afslutning."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "En objektn\u00F8gle skal v\u00E6re angivet i denne kontekst."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Ugyldig skrivning. Der er allerede skrevet en komplet v\u00E6rdi."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Afslutning er ikke tilladt i denne kontekst."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "N\u00F8gle er ikke tilladt i denne kontekst."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Forventet v\u00E6rdi efter n\u00F8gle."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Parser-tilstand skal v\u00E6re {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Parser-tilstand m\u00E5 ikke v\u00E6re {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Parser skal have en v\u00E6rdi."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" er ikke en underst\u00F8ttet wrapper-type."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Dette objekt kan ikke modificeres. Du kan oprette en modificerbar kopi ved at bruge OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Dette array kan ikke modificeres. Du kan oprette en modificerbar kopi ved at bruge OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON-objekt indeholder dubleret n\u00F8gle: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Kan ikke registrere kodning automatisk, ikke nok tegn."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Forventede EOF-token, men modtog {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Uventet tegn {0} p\u00E5 linje {1}, kolonne {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Uventet tegn {0} p\u00E5 linje {1}, kolonne {2}. Forventede: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Ugyldigt token {0} p\u00E5 linje {1}, kolonne {2}. Forventede tokens er: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() er kun gyldig for parser-tilstandene KEY_NAME, VALUE_STRING, VALUE_NUMBER. Men den aktuelle parser-tilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() er kun gyldig for parser-tilstanden VALUE_NUMBER. Men den aktuelle parser-tilstand er {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() er kun gyldig for parser-tilstanden VALUE_NUMBER. Men den aktuelle parser-tilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() er kun gyldig for parser-tilstanden VALUE_NUMBER. Men den aktuelle parser-tilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() er kun gyldig for parser-tilstanden VALUE_NUMBER. Men den aktuelle parser-tilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() er kun gyldig for parser-tilstanden START_ARRAY. Men den aktuelle parser-tilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() er kun gyldig for parser-tilstanden START_OBJECT. Men den aktuelle parser-tilstand er {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Et tidsstempel med en region underst\u00F8ttes ikke. Kun forskudte tidszoner underst\u00F8ttes."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Objekter og arrays i JSON-v\u00E6rdien m\u00E5 ikke indlejres dybere end {0} niveauer"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "N\u00F8glerne til et JSON-objekt m\u00E5 ikke overskride 65.535 bytes"},    
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

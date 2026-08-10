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


public class ErrorMessagesJson_no extends java.util.ListResourceBundle {

  public ErrorMessagesJson_no() {};

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
     "Det oppstod et I/U-unntak"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "\u00C5ret {0} st\u00F8ttes ikke"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Overflyt, for stor verdi: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Valget st\u00F8ttes ikke (ikke implementert)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Bin\u00E6r JSON er ugyldig eller skadet."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Bin\u00E6r JSON-versjon st\u00F8ttes ikke: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "Lengden p\u00E5 den UTF-8-kodede n\u00F8kkelen kan ikke overskride 256 byte. F\u00F8lgende n\u00F8kkel overskrider denne grensen: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Angitt JSON er for stor til \u00E5 kodes som bin\u00E6r JSON. St\u00F8rrelsen p\u00E5 de kodede bildene kan ikke overskride 2 GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Bin\u00E6r JSON er ugyldig eller skadet. Det angitte bildet inneholder bare {0} byte."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Angitt java.time.Period har dager angitt, men Oracle-intervallet for \u00E5r til m\u00E5ned st\u00F8tter ikke dager."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generatoren ble lukket f\u00F8r slutten."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "En objektn\u00F8kkel m\u00E5 v\u00E6re angitt i denne konteksten."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Ugyldig skriving. En fullstendig verdi er allerede skrevet."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Slutt er ikke tillatt i denne konteksten."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "N\u00F8kkel er ikke tillatt i denne konteksten."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Forventet verdi etter n\u00F8kkel."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Analysatortilstand m\u00E5 v\u00E6re {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Analysatortilstand kan ikke v\u00E6re {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Analysator m\u00E5 v\u00E6re p\u00E5 en verdi."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "{0} er ikke en st\u00F8ttet innpakningstype."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Dette objektet kan ikke endres. Hvis du vil opprette en kopi som kan endres, kan du bruke OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Denne matrisen kan ikke endres. Hvis du vil opprette en kopi som kan endres, kan du bruke OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON-objekt inneholder duplisert n\u00F8kkel: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Kan ikke oppdage koding automatisk, ikke nok tegn."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Forventet EOF-symbol, men fikk {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Uventet tegn, {0}, p\u00E5 linje {1}, i kolonne {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Tegnet {0} var uventet p\u00E5 linje {1}, kolonnen {2}. Forventet: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Ugyldig symbol, {0}, p\u00E5 linje {1}, i kolonne {2}. Forventede symboler: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() er bare gyldig for analysatortilstandene KEY_NAME, VALUE_STRING, VALUE_NUMBER, men gjeldende analysatortilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() er bare gyldig for analysatortilstanden VALUE_NUMBER, men gjeldende analysatortilstand er {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() er bare gyldig for analysatortilstanden VALUE_NUMBER, men gjeldende analysatortilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() er bare gyldig for analysatortilstanden VALUE_NUMBER, men gjeldende analysatortilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() er bare gyldig for analysatortilstanden VALUE_NUMBER, men gjeldende analysatortilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() er bare gyldig for analysatortilstanden START_ARRAY, men gjeldende analysatortilstand er {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() er bare gyldig for analysatortilstanden START_OBJECT, men gjeldende analysatortilstand er {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Et tidsstempel med et omr\u00E5de st\u00F8ttes ikke. Bare forskj\u00F8vne tidssoner st\u00F8ttes."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Objektene og matrisene i JSON-verdien kan ikke n\u00F8stes dypere enn {0} niv\u00E5er"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "N\u00F8klene for et JSON-objekt kan ikke overskride 65\u00A0535 byte"},    
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

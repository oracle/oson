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


public class ErrorMessagesJson_pl extends java.util.ListResourceBundle {

  public ErrorMessagesJson_pl() {};

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
     "Wyst\u0105pi\u0142 wyj\u0105tek we-wy"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "Rok \"{0}\" nie jest obs\u0142ugiwany"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Przepe\u0142nienie, zbyt du\u017Ca warto\u015B\u0107: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Nieobs\u0142ugiwana opcja (niezaimplementowana)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Binarny plik JSON jest niepoprawny lub uszkodzony."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Nieobs\u0142ugiwana wersja binarnego pliku JSON: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "D\u0142ugo\u015B\u0107 klucza w formacie UTF-8 nie mo\u017Ce przekracza\u0107 256 bajt\u00F3w. Limit ten zosta\u0142 przekroczony przez nast\u0119puj\u0105cy klucz: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Podany plik JSON jest zbyt du\u017Cy, aby m\u00F3g\u0142 zosta\u0107 zakodowany jako binarny plik JSON. Rozmiar zakodowanych obraz\u00F3w nie mo\u017Ce przekracza\u0107 2\u00A0GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Binarny plik JSON jest niepoprawny lub uszkodzony. Podany obraz zawiera tylko {0} bajty(-\u00F3w)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Podana w\u0142a\u015Bciwo\u015B\u0107 java.time.Period ma ustawione dni, lecz interwa\u0142 Oracle od roku do miesi\u0105ca nie obs\u0142uguje dni."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generator zosta\u0142 zamkni\u0119ty przed zako\u0144czeniem."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "W tym kontek\u015Bcie musi zosta\u0107 okre\u015Blony klucz kontekstu."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Niepoprawny zapis. Pe\u0142na warto\u015B\u0107 ju\u017C zosta\u0142a zapisana."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Koniec nie jest dozwolony w tym kontek\u015Bcie."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Klucz nie jest dozwolony w tym kontek\u015Bcie."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Oczekiwano warto\u015Bci po kluczu."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "stanem analizatora sk\u0142adni musi by\u0107 \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Stanem analizatora sk\u0142adni nie mo\u017Ce by\u0107 \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Analizator sk\u0142adni musi by\u0107 ustawiony dla warto\u015Bci."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" nie jest obs\u0142ugiwanym typem izolatora."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Tego obiektu nie mo\u017Cna zmodyfikowa\u0107. Aby utworzy\u0107 modyfikowaln\u0105 kopi\u0119, prosz\u0119 u\u017Cy\u0107 OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Tej tablicy nie mo\u017Cna zmodyfikowa\u0107. Aby utworzy\u0107 modyfikowaln\u0105 kopi\u0119, prosz\u0119 u\u017Cy\u0107 OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "obiekt JSON zawiera zduplikowany klucz: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Nie mo\u017Cna automatycznie wykry\u0107 kodowania; za ma\u0142o znak\u00F3w."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Oczekiwano tokenu EOF, lecz uzyskano {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Nieoczekiwany znak {0} (linia {1}, kolumna {2})."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Nieoczekiwany znak {0} (linia {1}, kolumna {2}); Oczekiwano: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Niepoprawny token {0} (linia {1}, kolumna {2}). Oczekiwane tokeny: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "Metoda JsonParser#getString() jest poprawna tylko przy nast\u0119puj\u0105cych stanach analizatora sk\u0142adni: KEY_NAME, VALUE_STRING, VALUE_NUMBER. Bie\u017C\u0105cym stanem jest {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "Metoda JsonParser#isIntegralNumber() jest poprawna tylko przy stanie VALUE_NUMBER analizatora sk\u0142adni. Bie\u017C\u0105cym stanem jest {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "Metoda JsonParser#getInt() jest poprawna tylko przy stanie VALUE_NUMBER analizatora sk\u0142adni. Bie\u017C\u0105cym stanem jest {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "Metoda JsonParser#getLong() jest poprawna tylko przy stanie VALUE_NUMBER analizatora sk\u0142adni. Bie\u017C\u0105cym stanem jest {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "Metoda JsonParser#getBigDecimal() jest poprawna tylko przy stanie VALUE_NUMBER analizatora sk\u0142adni. Bie\u017C\u0105cym stanem jest {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "Metoda JsonParser#getArray() jest poprawna tylko przy stanie START_ARRAY analizatora sk\u0142adni. Bie\u017C\u0105cym stanem jest {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "Metoda JsonParser#getObject() jest poprawna tylko przy stanie START_ARRAY analizatora sk\u0142adni. Bie\u017C\u0105cym stanem jest {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Znacznik czasu z regionem nie jest obs\u0142ugiwany. Obs\u0142ugiwane s\u0105 tylko strefy czasowe z przesuni\u0119ciem."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Zagnie\u017Cdzenie obiekt\u00F3w i tablic w warto\u015Bci JSON nie mo\u017Ce przekracza\u0107 {0} poziom\u00F3w"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "Rozmiar kluczy obiektu JSON nie mo\u017Ce przekracza\u0107 65 535 bajt\u00F3w"},    
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

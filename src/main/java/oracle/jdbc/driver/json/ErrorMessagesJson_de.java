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


public class ErrorMessagesJson_de extends java.util.ListResourceBundle {

  public ErrorMessagesJson_de() {};

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
     "Eine I/O-Ausnahme ist aufgetreten"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "Das Jahr \"{0}\" wird nicht unterst\u00FCtzt"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "\u00DCberlauf, Wert zu gro\u00DF: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Nicht unterst\u00FCtzte Option (nicht implementiert)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Bin\u00E4re JSON ist ung\u00FCltig oder besch\u00E4digt."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Nicht unterst\u00FCtzte bin\u00E4re JSON-Version: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "Der mit UTF-8 codierte Schl\u00FCssel darf nicht l\u00E4nger als 256 Byte sein. Der folgende Schl\u00FCssel \u00FCberschreitet diesen Grenzwert: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Die angegebene JSON ist zu gro\u00DF, um als bin\u00E4re JSON codiert zu werden. Die codierten Bilder d\u00FCrfen nicht gr\u00F6\u00DFer als 2 GB sein."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Die bin\u00E4re JSON ist ung\u00FCltig oder besch\u00E4digt. Das angegebene Bild enth\u00E4lt nur {0} Byte."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "F\u00FCr die angegebene java.time.Period wurden Tage festgelegt, aber das Jahr-Monat-Intervall von Oracle unterst\u00FCtzt keine Tage."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generator vor Ende geschlossen."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Ein Objektschl\u00FCssel muss in diesem Kontext angegeben werden."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Ung\u00FCltiger Schreibvorgang. Ein vollst\u00E4ndiger Wert wurde bereits geschrieben."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Ende in diesem Kontext nicht zul\u00E4ssig."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Schl\u00FCssel in diesem Kontext nicht zul\u00E4ssig."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Wert nach Schl\u00FCssel erwartet."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "Parserstatus muss {0} sein."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "Parserstatus darf nicht {0} sein."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "Parser muss auf einem Wert liegen."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" ist kein unterst\u00FCtzter Wrapper-Typ."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Dieses Objekt kann nicht ge\u00E4ndert werden. Um eine \u00E4nderbare Kopie zu erstellen, verwenden Sie OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Dieses Array kann nicht ge\u00E4ndert werden. Um eine \u00E4nderbare Kopie zu erstellen, verwenden Sie OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "JSON-Objekt enth\u00E4lt doppelten Schl\u00FCssel: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "Codierung kann nicht automatisch erkannt werden. Zu wenige Zeichen vorhanden."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "EOF-Token wurde erwartet, aber {0} wurde empfangen."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Unerwartetes Zeichen {0} bei Zeile {1}, Spalte {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Unerwartetes Zeichen {0} bei Zeile {1}, Spalte {2}. Erwartet: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Ung\u00FCltiges Token {0} bei Zeile {1}, Spalte {2}. Erwartete Token: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() ist nur f\u00FCr den Parserstatus KEY_NAME, VALUE_STRING, VALUE_NUMBER g\u00FCltig. Der aktuelle Parserstatus ist aber {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() ist nur f\u00FCr den Parserstatus VALUE_NUMBER g\u00FCltig. Der aktuelle Parserstatus ist aber {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() ist nur f\u00FCr den Parserstatus VALUE_NUMBER g\u00FCltig. Der aktuelle Parserstatus ist aber {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() ist nur f\u00FCr den Parserstatus VALUE_NUMBER g\u00FCltig. Der aktuelle Parserstatus ist aber {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() ist nur f\u00FCr den Parserstatus VALUE_NUMBER g\u00FCltig. Der aktuelle Parserstatus ist aber {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() ist nur g\u00FCltig f\u00FCr Parserstatus START_ARRAY. Aktueller Parserstatus ist jedoch {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() ist nur f\u00FCr den Parserstatus START_OBJECT g\u00FCltig. Der aktuelle Parserstatus ist aber {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Zeitstempel mit Regionen werden nicht unterst\u00FCtzt. Nur Offsetzeitzonen werden unterst\u00FCtzt."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Die Objekte und Arrays im JSON-Wert d\u00FCrfen nicht tiefer als {0} Ebenen verschachtelt sein"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "Die Schl\u00FCssel eines JSON-Objekts d\u00FCrfen 65.535 Byte nicht \u00FCberschreiten"},    
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

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


public class ErrorMessagesJson_es extends java.util.ListResourceBundle {

  public ErrorMessagesJson_es() {};

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
     "Se ha producido una excepci\u00F3n de E/S"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "El a\u00F1o \"{0}\" no est\u00E1 soportado"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Desbordamiento. El valor es demasiado grande: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Opci\u00F3n no soportada (no implantada)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "El archivo JSON binario no es v\u00E1lido o est\u00E1 corrupto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Versi\u00F3n del archivo JSON binario no soportada: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "La clave codificada en UTF-8 no debe superar los 256 bytes. La siguiente clave excede este l\u00EDmite: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "El archivo JSON especificado es demasiado grande para codificarse como archivo JSON binario. El tama\u00F1o de las im\u00E1genes codificadas no debe exceder los 2\u00A0GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "El archivo JSON binario no es v\u00E1lido o est\u00E1 corrupto. La imagen especificada solo contiene {0}\u00A0bytes."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "El per\u00EDodo java.time.Period especificado tiene d\u00EDas configurados, pero el intervalo de a\u00F1o a mes de Oracle no soporta los d\u00EDas."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Generador cerrado antes de finalizar."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Se debe especificar una clave de objeto en este contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Escritura no v\u00E1lida. Ya se ha escrito un valor completo."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Finalizaci\u00F3n no permitida en este contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Clave no permitida en este contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Valor esperado tras la clave."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "El estado del analizador debe ser {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "El estado del analizador no debe ser {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "El analizador debe tener un valor."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" no es un tipo de envoltorio soportado."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Este objeto no se puede modificar. Para realizar una copia modificable, utilice OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Esta matriz no se puede modificar. Para realizar una copia modificable, utilice OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "El objeto JSON contiene una clave duplicada: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "No se puede detectar autom\u00E1ticamente la codificaci\u00F3n. No hay suficientes caracteres."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Se esperaba el token EOF, pero se ha obtenido {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Car\u00E1cter {0} inesperado en la l\u00EDnea {1} y la columna {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Car\u00E1cter inesperado {0} en la l\u00EDnea {1} y la columna {2}. Valor esperado: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Token no v\u00E1lido {0} en la l\u00EDnea {1} y la columna {2}. Los tokens esperados son: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() solo es v\u00E1lido para los estados del analizador KEY_NAME, VALUE_STRING, VALUE_NUMBER, pero el estado actual del analizador es {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() solo es v\u00E1lido para el estado del analizador VALUE_NUMBER, pero el estado actual del analizador es {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() solo es v\u00E1lido para el estado del analizador VALUE_NUMBER, pero el estado actual del analizador es {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() solo es v\u00E1lido para el estado del analizador VALUE_NUMBER, pero el estado actual del analizador es {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() solo es v\u00E1lido para el estado del analizador VALUE_NUMBER, pero el estado actual del analizador es {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() solo es v\u00E1lido para el estado de analizador START_ARRAY, pero el estado actual del analizador es {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() solo es v\u00E1lido para el estado de analizador START_OBJECT, pero el estado actual del analizador es {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "No est\u00E1 soportado un registro de hora con una regi\u00F3n. Solo est\u00E1n soportadas las zonas horarias con desfase."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Puede que los objetos y matrices en el valor JSON no aniden a niveles m\u00E1s profundos que {0}."},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "Las claves de un objeto JSON no pueden exceder los 65\u00A0535 bytes"},    
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

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


public class ErrorMessagesJson_pt extends java.util.ListResourceBundle {

  public ErrorMessagesJson_pt() {};

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
     "Ocorreu uma exce\u00E7\u00E3o de I/O"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "O ano \"{0}\" n\u00E3o \u00E9 suportado"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Excesso de dados, valor demasiado grande: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Op\u00E7\u00E3o n\u00E3o suportada (n\u00E3o implementada)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "O JSON bin\u00E1rio \u00E9 inv\u00E1lido ou est\u00E1 corrompido."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Vers\u00E3o de JSON bin\u00E1rio n\u00E3o suportada: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "O comprimento da chave codificada em UTF-8 n\u00E3o deve ser superior a 256 bytes. A chave seguinte excede este limite: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "O JSON especificado \u00E9 demasiado grande para ser codificado como JSON bin\u00E1rio. O tamanho das imagens codificadas n\u00E3o deve exceder os 2 GB."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "O JSON bin\u00E1rio \u00E9 inv\u00E1lido ou est\u00E1 corrompido. A imagem especificada cont\u00E9m apenas {0} bytes."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "O java.time.Period especificado tem dias definidos, mas o intervalo de ano para m\u00EAs da Oracle n\u00E3o suporta dias."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Gerador fechado antes do fim."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Deve ser especificada uma chave de objeto neste contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Escrita inv\u00E1lida. J\u00E1 foi escrito um valor completo."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Fim n\u00E3o permitido neste contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Chave n\u00E3o permitida neste contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Valor esperado ap\u00F3s a chave."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "O estado do analisador deve ser {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "O estado do analisador n\u00E3o deve ser {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "O analisador deve ser sobre um valor."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" n\u00E3o \u00E9 um tipo de wrapper suportado."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Este objeto n\u00E3o pode ser modificado. Para criar uma c\u00F3pia modific\u00E1vel, utilize OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Esta matriz n\u00E3o pode ser modificada. Para criar uma c\u00F3pia modific\u00E1vel, utilize OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "o objeto de JSON cont\u00E9m uma chave em duplicado: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "N\u00E3o \u00E9 poss\u00EDvel detetar automaticamente a codifica\u00E7\u00E3o, caracteres insuficientes."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "S\u00EDmbolo EOF esperado, mas foi recebido {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Car\u00E1cter {0} inesperado na linha {1}, coluna {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Car\u00E1cter {0} inesperado na linha {1}, coluna {2}. Esperado: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "S\u00EDmbolo {0} inv\u00E1lido na linha {1}, coluna {2}. Os s\u00EDmbolos esperados s\u00E3o: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() \u00E9 v\u00E1lido apenas para estados do analisador KEY_NAME, VALUE_STRING, VALUE_NUMBER. No entanto, o atual estado do analisador \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() \u00E9 v\u00E1lido apenas para o estado do analisador VALUE_NUMBER. No entanto, o atual estado do analisador \u00E9 {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() \u00E9 v\u00E1lido apenas para o estado do analisador VALUE_NUMBER. No entanto, o atual estado do analisador \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() \u00E9 v\u00E1lido apenas para o estado do analisador VALUE_NUMBER. No entanto, o atual estado do analisador \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() \u00E9 v\u00E1lido apenas para o estado do analisador VALUE_NUMBER. No entanto, o atual estado do analisador \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() \u00E9 v\u00E1lido apenas para o estado do analisador START_ARRAY. No entanto, o estado atual do analisador \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() \u00E9 v\u00E1lido apenas para o estado do analisador START_OBJECT. No entanto, o atual estado do analisador \u00E9 {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Uma indica\u00E7\u00E3o de data/hora com uma regi\u00E3o n\u00E3o \u00E9 suportada. S\u00F3 os fusos hor\u00E1rios da diferen\u00E7a s\u00E3o suportados."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Os objetos e as matrizes no valor JSON n\u00E3o poder\u00E3o encadear-se mais do que {0} n\u00EDveis"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "As chaves de um objeto JSON n\u00E3o poder\u00E3o exceder 65.535 bytes"},    
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

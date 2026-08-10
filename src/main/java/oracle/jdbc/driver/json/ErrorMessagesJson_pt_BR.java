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


public class ErrorMessagesJson_pt_BR extends java.util.ListResourceBundle {

  public ErrorMessagesJson_pt_BR() {};

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
     "Exce\u00E7\u00E3o de entrada/sa\u00EDda"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "N\u00E3o h\u00E1 suporte para o ano \"{0}\""},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "Estouro; valor muito grande: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Op\u00E7\u00E3o sem suporte (n\u00E3o implementada)."},

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
     "Vers\u00E3o de JSON bin\u00E1rio sem suporte: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "O tamanho da chave codificada UTF-8 n\u00E3o deve ser maior que 256 bytes. A seguinte chave excede esse limite: \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "O JSON especificado \u00E9 muito grande para ser codificado como JSON bin\u00E1rio. O tamanho das imagens codificadas n\u00E3o deve exceder 2GB."},

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
     "\u00C9 necess\u00E1rio especificar uma chave de objeto nesse contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Grava\u00E7\u00E3o inv\u00E1lida. Um valor completo j\u00E1 foi gravado."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Fim n\u00E3o permitido nesse contexto."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Chave n\u00E3o permitida nesse contexto."},

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
     "O estado do parser deve ser {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "O estado do parser n\u00E3o deve ser {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "O parser deve se referir a um valor."},

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
     "Este objeto n\u00E3o pode ser modificado. Para fazer uma c\u00F3pia modific\u00E1vel, use OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Este array n\u00E3o pode ser modificado. Para fazer uma c\u00F3pia modific\u00E1vel, use OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "O objeto JSON cont\u00E9m uma chave duplicada: {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "N\u00E3o \u00E9 poss\u00EDvel detectar automaticamente a codifica\u00E7\u00E3o; caracteres insuficientes."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Token EOF esperado, mas obtido {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Caractere inesperado {0} na linha {1}, coluna {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Caractere inesperado {0} na linha {1}, coluna {2}. Esperado {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Token inv\u00E1lido {0} na linha {1}, coluna {2}. Os tokens esperados s\u00E3o: {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() s\u00F3 \u00E9 v\u00E1lido nos estados KEY_NAME, VALUE_STRING, VALUE_NUMBER do parser, mas o estado atual do parser \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() s\u00F3 \u00E9 v\u00E1lido no estado VALUE_NUMBER do parser, mas o estado atual do parser \u00E9 {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() s\u00F3 \u00E9 v\u00E1lido no estado VALUE_NUMBER do parser, mas o estado atual do parser \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() s\u00F3 \u00E9 v\u00E1lido no estado VALUE_NUMBER do parser, mas o estado atual do parser \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() s\u00F3 \u00E9 v\u00E1lido no estado VALUE_NUMBER do parser, mas o estado atual do parser \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() s\u00F3 \u00E9 v\u00E1lido no estado VALUE_NUMBER do parser, mas o estado atual do parser \u00E9 {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() s\u00F3 \u00E9 v\u00E1lido no estado START_OBJECT do parser, mas o estado atual do parser \u00E9 {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "N\u00E3o h\u00E1 suporte para um timestamp com uma regi\u00E3o. S\u00F3 h\u00E1 suporte para fusos hor\u00E1rios de deslocamento."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Os objetos e matrizes no valor JSON n\u00E3o podem aninhar mais do que {0} n\u00EDveis"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "As chaves de um objeto JSON n\u00E3o podem exceder 65.535 bytes"},    
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

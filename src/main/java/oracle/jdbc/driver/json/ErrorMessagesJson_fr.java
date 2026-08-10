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


public class ErrorMessagesJson_fr extends java.util.ListResourceBundle {

  public ErrorMessagesJson_fr() {};

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
     "Exception d'E/S"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26302",
     "L''ann\u00E9e \"{0}\" n''est pas prise en charge"},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26303",
     "D\u00E9bordement, valeur trop \u00E9lev\u00E9e : {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26304",
     "Option non prise en charge (non impl\u00E9ment\u00E9e)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26305",
     "Le fichier JSON binaire n'est pas valide ou est endommag\u00E9."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26306",
     "Version de fichier JSON binaire non prise en charge : {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26307",
     "La longueur de cl\u00E9 encod\u00E9e en UTF-8 ne doit pas d\u00E9passer 256 octets. La cl\u00E9 suivante d\u00E9passe cette limite : \"{0}\"."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26308",
     "Le fichier JSON sp\u00E9cifi\u00E9 est trop volumineux pour \u00EAtre encod\u00E9 en tant que fichier JSON binaire. La taille des images encod\u00E9es ne doit pas d\u00E9passer 2 Go."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26309",
     "Le fichier JSON binaire n''est pas valide ou est endommag\u00E9. L''image sp\u00E9cifi\u00E9e contient uniquement {0} octets."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26310",
     "Des jours sont d\u00E9finis pour l'\u00E9l\u00E9ment java.time.Period indiqu\u00E9 mais le type de donn\u00E9es INTERVAL YEAR TO MONTH d'Oracle ne prend pas en charge les jours."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26311",
     "Le g\u00E9n\u00E9rateur a \u00E9t\u00E9 ferm\u00E9 avant la fin."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26312",
     "Une cl\u00E9 d'objet doit \u00EAtre indiqu\u00E9e dans ce contexte."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26313",
     "Ecriture non valide. Une valeur compl\u00E8te a d\u00E9j\u00E0 \u00E9t\u00E9 \u00E9crite."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26314",
     "Fin non autoris\u00E9e dans ce contexte."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26315",
     "Cl\u00E9 non autoris\u00E9e dans ce contexte."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26316",
     "Valeur attendue apr\u00E8s la cl\u00E9."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26317",
     "L''\u00E9tat de l''analyseur doit \u00EAtre {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26318",
     "L''\u00E9tat de l''analyseur ne doit pas \u00EAtre {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26319",
     "L'analyseur doit \u00EAtre d\u00E9fini sur une valeur."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26320",
     "\"{0}\" n''est pas un type de wrapper pris en charge."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26321",
     "Impossible de modifier cet objet. Pour cr\u00E9er une copie modifiable, utilisez OracleJsonFactory.createObject(OracleJsonObject)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26322",
     "Impossible de modifier ce tableau. Pour cr\u00E9er une copie modifiable, utilisez OracleJsonFactory.createArray(OracleJsonArray)."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26323",
     "L''objet JSON contient une cl\u00E9 en double : {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26324",
     "D\u00E9tection automatique de l'encodage impossible, nombre de caract\u00E8res insuffisant."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26325",
     "Token EOF attendu, obtenu : {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26326",
     "Caract\u00E8re inattendu {0} \u00E0 la ligne {1}, colonne {2}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26327",
     "Caract\u00E8re inattendu {0} \u00E0 la ligne {1}, colonne {2}. Valeur attendue : {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26328",
     "Token non valide {0} \u00E0 la ligne {1}, colonne {2}. Tokens attendus : {3}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26329",
     "JsonParser#getString() est valide uniquement pour les \u00E9tats d''analyseur KEY_NAME, VALUE_STRING, VALUE_NUMBER, mais l''\u00E9tat d''analyseur en cours est {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26330",
     "JsonParser#isIntegralNumber() est valide uniquement pour l''\u00E9tat d''analyseur VALUE_NUMBER, mais l''\u00E9tat d''analyseur en cours est {0}."},

    // Document : No
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26331",
     "JsonParser#getInt() est valide uniquement pour l''\u00E9tat d''analyseur VALUE_NUMBER, mais l''\u00E9tat d''analyseur en cours est {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26332",
     "JsonParser#getLong() est valide uniquement pour l''\u00E9tat d''analyseur VALUE_NUMBER, mais l''\u00E9tat d''analyseur en cours est {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26333",
     "JsonParser#getBigDecimal() est valide uniquement pour l''\u00E9tat d''analyseur VALUE_NUMBER, mais l''\u00E9tat d''analyseur en cours est {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26334",
     "JsonParser#getArray() est valide uniquement pour l''\u00E9tat d''analyseur START_ARRAY, mais l''\u00E9tat d''analyseur en cours est {0}."},

    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26335",
     "JsonParser#getObject() est valide uniquement pour l''\u00E9tat d''analyseur START_OBJECT, mais l''\u00E9tat d''analyseur en cours est {0}."},
 
    // Document : Yes
    // Cause    : 
    // Action   : 
    // Comment  : N/A
    {"26336",
     "Un horodatage avec une r\u00E9gion n'est pas pris en charge. Seuls les fuseaux horaires de d\u00E9calage sont pris en charge."},

    // Document : Yes
    // Cause    : The JSON document being encoded has objects and arrays that 
    //            nest deeper than 65,535 levels.
    // Action   : Change the JSON document so that it has fewer than 65,535 
    //            levels of nesting. 
    // Comment  : N/A
    {"26337",
     "Les objets et les tableaux dans la valeur JSON ne peuvent pas imbriquer plus de {0} niveaux"},
    
    // Document : Yes
    // Cause    : The keys of a JSON object may not exceed 65,535 bytes.
    // Action   : Use a shorter key name.
    // Comment  : N/A
    {"26338",
     "Les cl\u00E9s d'un objet JSON ne peuvent pas d\u00E9passer 65 535 octets"},    
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

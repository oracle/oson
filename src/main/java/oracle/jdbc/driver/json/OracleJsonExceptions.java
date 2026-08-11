// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import oracle.sql.json.OracleJsonException;
import oracle.sql.json.OracleJsonGenerationException;
import oracle.sql.json.OracleJsonParsingException;

/**
 * @author  Josh Spiegel [josh.spiegel@oracle.com]
 */
public abstract class OracleJsonExceptions {

  /** Creates exceptions for OracleJson* implementation */
  public static final ExceptionFactory ORACLE_FACTORY = new OracleExceptionFactory();

  /*
   * JSON Exceptions
   * 
   * Reserved error range for JDBC thin client Json errors:
   *
   * ORA-26300 - ORA-26399
   *
   * error range file: https://codesearch.oraclecorp.com/cs/xref/RDBMS_MAIN_LINUX.X64/rdbms/mesg/e24280.msg
   */
  private static final int JSON_ERROR_BASE = 26300;

  public static final OracleJsonExceptions
    IO                      = jzn(getKey(1)),
    BAD_YEAR                = jzn(getKey(2)),
    NOT_IMPLEMENTED         = jzn(getKey(4)),
    CORRUPT                 = jzn(getKey(5)),
    UNSUPPORTED_VERSION     = jzn(getKey(6)),
    LONG_KEY                = jzn(getKey(7)),
    IMAGE_TOO_BIG           = jzn(getKey(8)),
    CORRUPT2                = jzn(getKey(9)),
    NO_DAYS_ALLOWED         = jzn(getKey(10)),
    BAD_WRAP                = jzn(getKey(20)),
    PARSER_ENC_DETECT_FAIL  = jzn(getKey(24)),
    BAD_TIMESTAMP_TZ        = jzn(getKey(36));
  
  /** Generation Exceptions */
  public static final OracleJsonExceptions 
    GENERATION_INCOMPLETE   = gen(getKey(11)),
    MISSING_KEY             = gen(getKey(12)),
    EXTRA_EVENTS            = gen(getKey(13)),
    BAD_END                 = gen(getKey(14)),
    BAD_KEY                 = gen(getKey(15)),
    EXPECTED_VALUE          = gen(getKey(16)),
    DUPLICATE_KEY           = gen(getKey(23)),
    NEST_DEPTH_EXCEEDED     = gen(getKey(37)),
    KEY_TOO_LONG            = gen(getKey(38));
  
  /** Parsing Exceptions */
  public static final OracleJsonExceptions 
    PARSER_EXPECTED_EOF     = par(getKey(25)),
    TOKEN_UNEXPECTED_CHAR   = par(getKey(26)),
    TOKEN_EXPECTED_CHAR     = par(getKey(27)),
    PARSER_INVALID_TOKEN    = par(getKey(28));
  
  /** IllegalStateExceptions */
  public static final OracleJsonExceptions
    OVERFLOW                = ill(getKey(3)),
    BAD_PARSER_STATE        = ill(getKey(17)),
    BAD_PARSER_STATE3       = ill(getKey(18)),
    BAD_PARSER_STATE_VALUE  = ill(getKey(19)),
    PARSER_GETSTRING_ERR    = ill(getKey(29)),
    PARSER_ISINTEGRAL_ERR   = ill(getKey(30)),
    PARSER_GETINT_ERR       = ill(getKey(31)),
    PARSER_GETLONG_ERR      = ill(getKey(32)),
    PARSER_GETBIGDECIMAL_ERR= ill(getKey(33)),
    PARSER_GETARRAY_ERR     = ill(getKey(34)),
    PARSER_GETOBJECT_ERR    = ill(getKey(35));
  
  /** UnsupportedOperationException */
  public static final OracleJsonExceptions
    OBJ_NOT_MUTABLE         = uso(getKey(21)),
    ARR_NOT_MUTABLE         = uso(getKey(22));
  
  private static String getKey(int errorNumber) {
    return String.format("%05d", JSON_ERROR_BASE + errorNumber);
  }

  private static final ResourceBundle MESSAGES =
    ResourceBundle.getBundle("oracle.jdbc.driver.json.ErrorMessagesJson");  

  public interface ExceptionFactory {

    RuntimeException createJsonException(String message, Throwable cause);

    RuntimeException createJsonException(String message);

    RuntimeException createGenerationException(String message);

    RuntimeException createGenerationException(String message, Throwable cause);

    RuntimeException createParsingException(String message);

    RuntimeException createParsingException(String message, Throwable cause);

  }
  
  private static final class OracleExceptionFactory implements ExceptionFactory {

    private OracleExceptionFactory() {
      // singleton
    }
    
    @Override
    public RuntimeException createJsonException(String message, Throwable cause) {
      return new OracleJsonException(message, cause);
    }

    @Override
    public RuntimeException createJsonException(String message) {
      return new OracleJsonException(message);
    }

    @Override
    public RuntimeException createGenerationException(String message, Throwable cause) {
      return new OracleJsonGenerationException(message, cause);
    }

    @Override
    public RuntimeException createGenerationException(String message) {
      return new OracleJsonGenerationException(message);
    }

    @Override
    public RuntimeException createParsingException(String message) {
      return new OracleJsonParsingException(message);
    }

    @Override
    public RuntimeException createParsingException(String message, Throwable cause) {
      return new OracleJsonParsingException(message, cause);
    }
    
  }
  
  private String key;
  
  private OracleJsonExceptions(String key) {
    this.key = key;
  }

  public abstract RuntimeException create(ExceptionFactory f, Throwable cause, Object... params);
    
  //return 
  public abstract RuntimeException create(ExceptionFactory f, Object... params);

  public String getMessage(Object... params) {
    String url = "";
        return "ORA-" + key + ": " + 
                 MessageFormat.format(MESSAGES.getString(key), params) + url;
  }
 
  private static OracleJsonExceptions jzn(String key) {
    return new OracleJsonExceptions(key) {
      @Override
      public RuntimeException create(ExceptionFactory f, Throwable cause, Object... params) {
        return f.createJsonException(getMessage(params), cause);
      }

      @Override
      public RuntimeException create(ExceptionFactory f, Object... params) {
        return f.createJsonException(getMessage(params));
      }
    };
  }
  
  private static OracleJsonExceptions par(String key) {
    return new OracleJsonExceptions(key) {
      @Override
      public RuntimeException create(ExceptionFactory f, Throwable cause, Object... params) {
        return f.createJsonException(getMessage(params), cause);
      }

      @Override
      public RuntimeException create(ExceptionFactory f, Object... params) {
        return f.createJsonException(getMessage(params));
      }
    };
  }
  
  private static OracleJsonExceptions gen(String key) {
    return new OracleJsonExceptions(key) {
      @Override
      public RuntimeException create(ExceptionFactory f,  Throwable cause, Object... params) {
        return f.createGenerationException(getMessage(params), cause);
      }

      @Override
      public RuntimeException create(ExceptionFactory f, Object... params) {
        return f.createGenerationException(getMessage(params));
      }
    };
  }
  
  private static OracleJsonExceptions ill(String key) {
    return new OracleJsonExceptions(key) {
      @Override
      public IllegalStateException create(ExceptionFactory f, Throwable cause, Object... params) {
        return new IllegalStateException(getMessage(params), cause);
      }

      @Override
      public IllegalStateException create(ExceptionFactory f, Object... params) {
        return new IllegalStateException(getMessage(params));
      }
    };
  }   
  
  private static OracleJsonExceptions uso(String key) {
    return new OracleJsonExceptions(key) {
      @Override
      public UnsupportedOperationException create(ExceptionFactory f, Throwable cause, Object... params) {
        return new UnsupportedOperationException(getMessage(params), cause);
      }

      @Override
      public UnsupportedOperationException create(ExceptionFactory f, Object... params) {
        return new UnsupportedOperationException(getMessage(params));
      }
    };
  }   
}

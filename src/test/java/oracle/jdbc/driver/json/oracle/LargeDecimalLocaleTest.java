// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json.oracle;

import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

import oracle.jdbc.driver.json.JsonTestCase;
import oracle.sql.json.OracleJsonFactory;

/**
 * Numbers with more than 40 significant digits are written in scientific
 * notation. The text must not depend on the default locale.
 */
public class LargeDecimalLocaleTest extends JsonTestCase {

  private static final String INPUT = "[12345678901234567890123456789012345678901]";

  private static final String EXPECTED = "[1.23456789012345678901234567890123456789E+40]";

  public void testLargeDecimalIgnoresDefaultLocale() throws Exception {
    Locale saved = Locale.getDefault();
    try {
      for (String tag : new String[] { "en-US", "de-DE", "fr-FR", "ar-EG" }) {
        Locale.setDefault(Locale.forLanguageTag(tag));
        assertEquals(tag, EXPECTED, toStringInNewLoader(INPUT));
      }
    } finally {
      Locale.setDefault(saved);
    }
  }

  /**
   * The number formats are created when OracleJsonDecimalImpl is initialized,
   * so load the classes again after the default locale has been set.
   */
  private static String toStringInNewLoader(String json) throws Exception {
    URL[] urls = new URL[] {
      location(OracleJsonFactory.class),
      location(jakarta.json.JsonValue.class),
      location(javax.json.JsonValue.class)
    };
    try (URLClassLoader loader = new URLClassLoader(urls, ClassLoader.getPlatformClassLoader())) {
      Class<?> factoryClass = Class.forName(OracleJsonFactory.class.getName(), true, loader);
      Object factory = factoryClass.getConstructor().newInstance();
      Object value = factoryClass.getMethod("createJsonTextValue", Reader.class)
          .invoke(factory, new StringReader(json));
      return value.toString();
    }
  }

  private static URL location(Class<?> c) {
    return c.getProtectionDomain().getCodeSource().getLocation();
  }
}

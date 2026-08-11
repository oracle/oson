// Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/ 
package oracle.jdbc.driver.json;

public class Jsonp {
  
  public static final Class<?> JAVAX_JSON_PARSER = 
      forNameNoError("javax.json.stream.JsonParser");
  
  public static final Class<?> JAKARTA_JSON_PARSER = 
      forNameNoError("jakarta.json.stream.JsonParser");
  
  public static boolean hasJakarta() {
    return JAKARTA_JSON_PARSER != null;
  }
  
  public static boolean isJakartaJson(Class<?> c) {
    String pkg = c.getPackage().getName();
    return "jakarta.json".equals(pkg);
  }
  
  public static boolean isJakartaJsonStream(Class<?> c) {
    String pkg = c.getPackage().getName();
    return "jakarta.json.stream".equals(pkg);
  }

  private static Class<?> forNameNoError(String clazz) {
    try {
      return Class.forName(clazz);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }
  
  private Jsonp () {
    
  }
}

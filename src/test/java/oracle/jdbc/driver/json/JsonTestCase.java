/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json;

import java.util.regex.Pattern;

import junit.framework.TestCase;
import junit.framework.TestResult;

public abstract class JsonTestCase extends TestCase {
   
    /** Used to filter by test method name */
    private static final String TEST_MATCH = System.getProperty("test.match");
    
    private static final Pattern PATTERN;
    
    static {
        if (TEST_MATCH == null || TEST_MATCH.trim().equals("")) {
            PATTERN = null;
        } else {
            PATTERN = Pattern.compile(".*" + TEST_MATCH, Pattern.CASE_INSENSITIVE);
        }
    }
    
    @Override
    public void run(TestResult result) {
        if (PATTERN == null) {
            super.run(result);
            return;
        }
        String name = getClass().getName() + "#" + getName();
        if (PATTERN.matcher(name).lookingAt()) {
            super.run(result);
        }
    }    
}

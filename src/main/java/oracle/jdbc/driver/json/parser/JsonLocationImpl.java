/* 
 * Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json.parser;

/**
 * @author Jitendra Kotamraju
 */
public class JsonLocationImpl {
  
    static final JsonLocationImpl UNKNOWN = new JsonLocationImpl(-1, -1, -1);

    private final long columnNo;
    private final long lineNo;
    private final long offset;

    public JsonLocationImpl(long lineNo, long columnNo, long streamOffset) {
        this.lineNo = lineNo;
        this.columnNo = columnNo;
        this.offset = streamOffset;
    }

    public long getLineNumber() {
        return lineNo;
    }

    public long getColumnNumber() {
        return columnNo;
    }

    public long getStreamOffset() {
        return offset;
    }

    public String toString() {
        return "(line no="+lineNo+", column no="+columnNo+", offset="+ offset +")";
    }

}

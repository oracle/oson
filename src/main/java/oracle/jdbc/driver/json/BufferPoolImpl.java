/* 
* Copyright (c) 2018, 2026, Oracle and/or its affiliates. 
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl/
 */
package oracle.jdbc.driver.json;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * char[] pool that pool instances of char[] which are expensive to create.
 *
 * @author Jitendra Kotamraju
 */
public class BufferPoolImpl {

    // volatile since multiple threads may access queue reference
    private volatile WeakReference<ConcurrentLinkedQueue<char[]>> queue;

    /**
     * Gets a new object from the pool.
     *
     * <p>
     * If no object is available in the pool, this method creates a new one.
     *
     * @return
     *      always non-null.
     */
    public final char[] take() {
        char[] t = getQueue().poll();
        if (t==null)
            return new char[4096];
        return t;
    }

    private ConcurrentLinkedQueue<char[]> getQueue() {
        WeakReference<ConcurrentLinkedQueue<char[]>> q = queue;
        if (q != null) {
            ConcurrentLinkedQueue<char[]> d = q.get();
            if (d != null)
                return d;
        }

        // overwrite the queue
        ConcurrentLinkedQueue<char[]> d = new ConcurrentLinkedQueue<char[]>();
        queue = new WeakReference<ConcurrentLinkedQueue<char[]>>(d);

        return d;
    }

    /**
     * Returns an object back to the pool.
     */
    public final void recycle(char[] t) {
        getQueue().offer(t);
    }

}

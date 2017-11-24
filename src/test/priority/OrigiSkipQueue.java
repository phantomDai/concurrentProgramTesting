package test.priority;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import junit.framework.*;
import priority.SkipQueue;

import static junit.framework.TestCase.fail;

public class OrigiSkipQueue extends TestCase{
    static final int LOG_RANGE = 5;
    static final int TEST_SIZE = 128;
    static final int RANGE = 32;
    private final static int THREADS = 8;
    private final static int PER_THREAD = TEST_SIZE / THREADS;
    static Random random = new Random();
    SkipQueue<Integer> instance;
    Thread[] thread = new Thread[THREADS];
    List<Integer> list = new ArrayList<Integer>();
    /**
     * Parallel adds, sequential removeMin
     * @throws java.lang.Exception
     */
    public void testParallelBoth()  throws Exception {
        System.out.println("testParallelBoth");
        int key, value;
        instance = new SkipQueue<Integer>();

        for (int i = 0; i < THREADS; i++) {
            thread[i] = new AddThread(i * PER_THREAD);
        }
        for (int i = 0; i < THREADS; i ++) {
            thread[i].start();
        }
        for (int i = 0; i < THREADS; i ++) {
            thread[i].join();
        }
        for (int i = 0; i < THREADS; i++) {
            thread[i] = new RemoveMinThread();
        }
        for (int i = 0; i < THREADS; i ++) {
            thread[i].start();
        }
        for (int i = 0; i < THREADS; i ++) {
            thread[i].join();
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println("OK.");
    }
    class AddThread extends Thread {
        int base;
        AddThread(int i) {
            base = i;
        }
        public void run() {
            for (int i = 0; i < PER_THREAD; i++) {
                int x = base + i;

                instance.add(x, x);
            }
        }
    }
    class RemoveMinThread extends Thread {
        int last;
        RemoveMinThread() {
            last = Integer.MIN_VALUE;
        }
        public void run() {
            for (int i = 0; i < PER_THREAD; i++) {
                int x = instance.removeMin();
                if (x < last) {
                    fail("non-ascending priorities: " + last + ", " + x);
                }
                last = x;
            }
        }
    }
}

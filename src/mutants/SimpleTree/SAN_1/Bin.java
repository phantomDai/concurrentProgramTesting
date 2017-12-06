package mutants.SimpleTree.SAN_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple bin implementation used to test priority queues
 * Bin is a pool which has some elements
 * @param <T> item type
 * @author phantom
 */
public class Bin<T> {
    List<T> list;
    public Bin() {
        list = new ArrayList<T>();
    }

    synchronized void put(T item) {
        list.add(item);
    }

    synchronized T get() {
        try {
            return list.remove(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    synchronized boolean isEmpty() {
        return list.isEmpty();
    }

}

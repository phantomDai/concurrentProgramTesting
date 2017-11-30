package mutants.SimpleLinear.A_original;

import java.util.Random;

/**
 * Simple bounded priority queue
 * @param <T> item type
 * @auther phantom
 */
public class SimpleLinear<T> implements PQueue<T> {
    int range ;
    Bin<T>[] pqueue ;
    public SimpleLinear(int range){
        this.range = range ;
        pqueue = (Bin<T>[]) new Bin[range] ;
        for (int i = 0; i < pqueue.length; i++) {
            pqueue[i] = new Bin<T>();
        }
    }

    /**
     * add item to list
     * @param item item to add
     * @param priority item's value
     */
    @Override
    public void add(T item, int priority) {
        pqueue[priority].put(item);
    }

    /**
     * return and remove least item
     * @return least item
     */
    @Override
    public T removeMin() {
        for (int i = 0; i < range; i++) {
            T item = pqueue[i].get() ;
            if(item != null){
                return item ;
            }
        }
        return null ;
    }

    public static void main(String[] args) {
        Random random = new Random(1);
        int[] test = new int[1024];
        for (int i = 0; i < 1024; i++) {
            test[i] = random.nextInt(1024);
        }
        SimpleLinear simpleLinear = new SimpleLinear(1024);
        for (int i = 0; i < 1024; i++) {
            simpleLinear.add(test[i],test[i]);
        }
        System.out.println(simpleLinear.removeMin());

    }

}

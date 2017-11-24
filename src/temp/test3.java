package temp;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class test3 {
    Vector<Integer> vector = new Vector<Integer>();
    Random random = new Random();
    public test3() {
    }
    public void test(){
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new task();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try{
                System.out.println("线程启动");
                threads[i].join(3000);
                if (threads[i].isAlive()){
                    vector.add(random.nextInt(1024));
                    threads[i].interrupt();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程结束");

        for (int i = 0; i < vector.size(); i++) {
            System.out.print(vector.get(i)+",");
        }

    }


    synchronized public void add(){
        while(true){

        }
    }

    class task extends Thread{
        public void run(){
            add();
        }

    }


    public static void main(String[] args) {

        test3 test = new test3();
        test.test();

    }

}

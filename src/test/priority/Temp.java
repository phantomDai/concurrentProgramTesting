package test.priority;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Temp {

    /*Thread类*/
    class Task extends Thread{
        public volatile boolean flag = false;

        public void stopme(){
            flag = true;
        }
        public void run(){
            while(!flag){
                System.out.println("~~~~~~~~~~~~~~~~~~");
            }
        }
    }

    public void test(){
        Task[] threads = new Task[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Task();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try{
                threads[i].join(1000);
                if (threads[i].isAlive()){
                    threads[i].stopme();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    public static void main(String[] args) {
        Temp temp = new Temp();
        temp.test();
    }



}

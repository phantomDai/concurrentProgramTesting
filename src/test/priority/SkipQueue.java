package test.priority;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class SkipQueue {
    Object mutantInstance ; //变异体对象
    private static final String METHODNAME_ADD = "add" ;//add方法
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法
    private Class clazz = null;//引用待测的对象
    private Constructor constructor = null ;
    Thread[] thread ;
    private volatile int addToVector = 0;
    Vector<Integer> vector = new Vector<Integer>();
    private int THREADS = 10 ; //the numbers of threads

    public SkipQueue(int mythreads){
        this.THREADS = mythreads;
        thread = new Thread[mythreads];
    }

    /**
     *
     * Method: removeMin()
     *
     */
    public void testRemoveMin(int[] list,String mymutantFullName){
        try{
            vector.clear();
            clazz = Class.forName(mymutantFullName);
            constructor = clazz.getConstructor(null) ;

            mutantInstance = constructor.newInstance(null);
            Method method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
            //向变异体对象中添加元素

            for (int i = 0; i < list.length; i++) {
                method_add.invoke(mutantInstance,list[i],list[i]);
            }
            //多线程移除优先级最高的十个元素
            for (int i = 0; i < THREADS; i++) {
                thread[i] = new RemoveMinThread();
            }
            for (int i = 0; i < THREADS; i++) {
                thread[i].start();
            }
            for (int i = 0; i < THREADS; i++) {
                try{
                    thread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //判断vector中是否有重复元素
            Vector<Integer> tempVector = new Vector<Integer>();
            for (int i = 0; i < vector.size(); i++) {
                if(!tempVector.contains(vector.get(i))){
                    tempVector.add(vector.get(i));
                }
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public int[] getTop() {
        Collections.sort(vector); //对向量中的元素排序
        int[] templist = new int[vector.size()];
        for (int i = 0; i < templist.length; i++) {
            templist[i] = vector.get(i);
        }
        return templist;
    }

    synchronized public void addElementToVector(){
        try{
            Random random = new Random();
            Object result ;
            Method method = clazz.getMethod(METHODNAME_REMOVEMIN,null);
            result = method.invoke(mutantInstance,null);
            if (result == null ){
                vector.add(random.nextInt(924)+100);
            }else {
                addToVector = (int) result;
                while (vector.contains(addToVector)) {
                    result = method.invoke(mutantInstance,null);
                    if(result == null){
                        vector.add(random.nextInt(924)+100);
                        break;
                    }else{
                        addToVector = (int) result;
                    }
                }
                vector.add(addToVector);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }



    class RemoveMinThread extends Thread{

        RemoveMinThread(){ }
        public void run(){
            addElementToVector();
        }
    }

    public static void main(String[] args) {
        SkipQueue skipQueue = new SkipQueue(10);
        int[] mylist = new int[1024];
        Random ran = new Random(1);
        for (int i = 0; i < mylist.length; i++) {
            mylist[i] = ran.nextInt(1024);
        }
        skipQueue.testRemoveMin(mylist,"mutants.SkipQueue.SAN_1.SkipQueue");
        int[] toplist = skipQueue.getTop();
        for (int i = 0; i < toplist.length; i++) {
            System.out.print(toplist[i]+",");
        }



    }



}

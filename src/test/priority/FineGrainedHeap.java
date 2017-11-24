package test.priority;

import generatelist.Generatedata;
import generatelist.QuickSort;

import org.junit.Before;
import org.junit.Test;
import priority.FineGraineHeap;
import set.mutants.MutantSet;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FineGrainedHeap {
    private static final int RANGE = 1024;
    private int THREADS = 10;
    Object mutantInstance ; //变异体对象
    private static final String METHODNAME_ADD = "add" ;//add方法
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法
    private Class clazz = null;//引用待测的对象
    private Constructor constructor = null ;
    private volatile int addToVector = 0;
    Thread[] thread;
    Vector<Integer> vector = new Vector<Integer>() ;
    Random ran = new Random();
    public FineGrainedHeap(int mythreads){
        THREADS = mythreads;
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
            constructor = clazz.getConstructor(int.class) ;
            if (list.length<1024){
                mutantInstance = constructor.newInstance(1024);
            }else {
                mutantInstance = constructor.newInstance(list.length);
            }

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
                    thread[i].join(3000);//设置超时时间为10s
                    if (thread[i].isAlive()){
                        for (int k = 0; k < THREADS; k++) {
                            thread[k].stop();//超时打断
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int templength = vector.size();
            System.out.println("templength:"+templength);
            for (int i = 0; i < THREADS - templength; i++) {
                vector.add(ran.nextInt(1024));
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



    class RemoveMinThread extends Thread {

        public void run() {
            addElementToVector();
        }
    }


    public static void main(String[] args) {
        int length1 = 46;
        int length2 = 1024 - length1;
        int[] mylist1 = new int[length1];
        int[] mylist2 = new int[length2];
        for (int m = 0; m < 1; m++) {
            Random random = new Random(m+1);
            for (int i = 0; i < 1024; i++) {
                if (i < length1)
                    mylist1[i] = random.nextInt(1024);
                else
                    mylist2[i - mylist1.length] = random.nextInt(1024);
            }
            FineGrainedHeap fineGrainedHeap1 = new FineGrainedHeap(10);
            FineGrainedHeap fineGrainedHeap2 = new FineGrainedHeap(10);
            MutantSet ms = new MutantSet("FineGrainedHeap");
            fineGrainedHeap1.testRemoveMin(mylist1, ms.getMutantFullName(1));
            fineGrainedHeap2.testRemoveMin(mylist2, ms.getMutantFullName(1));
            int[] top1 = fineGrainedHeap1.getTop();
            for (int i = 0; i < top1.length; i++) {
                System.out.print(top1[i] + ",");
            }
            System.out.println();
            int[] top2 = fineGrainedHeap2.getTop();
            for (int i = 0; i < top2.length; i++) {
                System.out.print(top2[i]+",");
            }
        }


    }

}

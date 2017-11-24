package test.priority;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class TempFineGrainedHeap {
    /*产生的序列中元素的个数*/
    private static final int RANGE = 1024;

    /*开启的线程的数目,10为默认的值*/
    private int THREADS = 10;

    /*待测试的添加元素方法的名称*/
    private static final String METHODNAME_ADD = "add" ;

    /*待测试的返回并删除元素方法的名称*/
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法

    /*有关反射技术的一些变量*/
    Object mutantInstance ;
    private Class clazz = null;
    private Constructor constructor = null ;


    private volatile int addToVector = 0;

    /*线程组*/
    Thread[] thread;

    /*记录每一个线程返回的结果*/
    Vector<Integer> vector = new Vector<Integer>() ;

    /*产生随机数的对象*/
    private Random ran = new Random();

    /*初始化线程组*/
    public TempFineGrainedHeap(int mythreads){
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
        /**/


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
        private boolean flag = false;

        public void stopme(){
            flag = true;
        }
        public void run() {
            while(!flag){
                addElementToVector();
            }

        }
    }







}

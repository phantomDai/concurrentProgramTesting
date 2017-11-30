package test.priority;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/** 
* SimpleLinear Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 31, 2017</pre> 
* @version 1.0 
*/ 
public class SimpleLinear {
    private static int MYRANGE = 1024 ;//序列的大小
    private int THREADS = 10 ;//开启的线程的个数
    Vector<Integer> vector = new Vector<Integer>();//存放每次线程执行的结果
    Object mutantInstance ; //变异体对象
    private static final String METHODNAME_ADD = "add" ;//add方法
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法
    private Class clazz = null;//引用待测的对象
    private Constructor constructor = null ;
    Thread[] thread ;
    private volatile int addToVector = 0;

    Method method_add;
    Method method_remove;
    public SimpleLinear(int mythreads){
        this.THREADS = mythreads;
        thread = new Thread[mythreads];
    }

    /**
     * 测试removeMin方法
     * @param list 传入的测试的序列
     * @param mymutantFullName 变异体的全名：包名+程序名
     */
    public void testRemoveMin(int[] list,String mymutantFullName) {
        try{
            vector.clear();
            clazz = Class.forName(mymutantFullName);
            constructor = clazz.getConstructor(int.class) ;
            Arrays.sort(list);

            mutantInstance = constructor.newInstance(10240);
            //向序列中添加元素的方法
            method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);

            //向变异体对象中添加元素
            for (int i = 0; i < list.length; i++) {
                method_add.invoke(mutantInstance,list[i],list[i]);
            }
            //多线程移除优先级最高的十个元素
            /*初始化移除元素的方法*/
            method_remove = clazz.getMethod(METHODNAME_REMOVEMIN,null);
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


    /*向vector中添加元素,同一时间只能有一个线程访问*/
    private synchronized boolean addElements(Object element){
        if (element == null){
            Random random = new Random();
            vector.add(random.nextInt(824));
            return true;
        }else {
            int temp = (int) element;
            if (vector.contains(temp))
                return false;
            else{
                vector.add(temp);
                return true;
            }
        }
    }


    class RemoveMinThread extends Thread{
        volatile boolean flag = false;//标志位
        public void run(){
            while(!flag){
                try{
                    Object result = method_remove.invoke(mutantInstance,null);
                    flag = addElements(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

} 

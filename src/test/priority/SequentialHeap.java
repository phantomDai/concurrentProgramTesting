package test.priority;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author mph
 */
public class SequentialHeap {
    private final static int TEST_SIZE = 1024;
    private int THREADS=10;
    List<Integer> list = new ArrayList<Integer>();
    Vector<Integer> vector = new Vector<Integer>();//存放每次线程执行的结果
    Object mutantInstance ; //变异体对象
    private static final String METHODNAME_ADD = "add" ;//add方法
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法
    private Class clazz = null;//引用待测的对象
    private Constructor constructor = null ;
    Thread[] thread ;
    private volatile int addToVector = 0;
    public SequentialHeap(int mythreads){
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
            constructor = clazz.getConstructor(int.class) ;

            mutantInstance = constructor.newInstance(list.length+1024);
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

}


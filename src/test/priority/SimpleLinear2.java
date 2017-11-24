package test.priority;

import set.mutants.MutantSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;



public class SimpleLinear2 {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~固定的变量~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    private static final String METHODNAME_ADD = "add" ;//add方法
    private static final String METHODNAME_REMOVEMIN = "removeMin" ;//removeMin方法
    private static final int MYRANGE = 1024 ;//原始序列的长度
    private static final int LOOPS = 1 ;//循环的次数
    private static final int NUMBEROFMRS = 1;//蜕变关系的数目

    private int THREADS = 10;//开启的线程的个数

    private int numberOftestcaces = 1 ;

    Vector<Integer> sourcevector = new Vector<Integer>();//存放每次source线程执行的结果
    Vector<Integer> followvector = new Vector<Integer>();//存放每次follow线程执行的结果
    Vector<Integer> followvector_2 = new Vector<Integer>();//存放每次follow_2线程执行的结果

    Object MRInstance ;     //MR对象
    Object mutantsourceInstance ; //添加原始序列的变异体对象
    Object mutantfollowInstance ;//添加衍生序列的变异体对象
    Object mutantfollowInstance_2 ; //有的蜕变关系需要3个变异体的对象
    Method method_sourceRemove = null ;
    Method method_followRemove = null ;
    Method method_followRemove2 = null ;

    Thread[] thread = new Thread[THREADS];

    public SimpleLinear2(){ }

    /**
     * 设置线程的数目在蜕变关系14-17会用到
     * @param THREADS 开启的线程的数目
     */
    public void setTHREADS(int THREADS) {
        this.THREADS = THREADS;
    }

    /**
     * 向某一个实例对象中添加数据：1表示向添加原始序列的对象2表示添加衍生序列的对象3表示添加另外一个衍生序列的对象
     * @param object 需要实例化的对象的编号
     * @param list 要添加的数据
     */
    private void addElementToLinear(int object,int[] list,String mutantFullName){
        mutantsourceInstance = null;
        mutantfollowInstance = null ;
        mutantfollowInstance_2 = null ;
        if (object == 1){//对操作原始序列的对象实例化并且向实例中添加数据
            try{
                Class clazz = Class.forName(mutantFullName);
                Constructor constructor = clazz.getConstructor(int.class);
                mutantsourceInstance = constructor.newInstance(list.length);
                Method method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
                method_sourceRemove = clazz.getMethod(METHODNAME_REMOVEMIN,null);
                for (int i = 0; i < list.length; i++) {
                    method_add.invoke(mutantsourceInstance,list[i],list[i]);
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
        }else if (object == 2){
            try{
                Class clazz = Class.forName(mutantFullName);
                Constructor constructor = clazz.getConstructor(int.class);
                mutantfollowInstance = constructor.newInstance(list.length);
                Method method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
                method_followRemove = clazz.getMethod(METHODNAME_REMOVEMIN,null);
                for (int i = 0; i < list.length; i++) {
                    method_add.invoke(mutantfollowInstance,list[i],list[i]);
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
        }else{
            try{
                Class clazz = Class.forName(mutantFullName);
                Constructor constructor = clazz.getConstructor(int.class);
                mutantfollowInstance_2 = constructor.newInstance(list.length);
                Method method_add = clazz.getMethod(METHODNAME_ADD,Object.class,int.class);
                method_followRemove2 = clazz.getMethod(METHODNAME_REMOVEMIN,null);
                for (int i = 0; i < list.length; i++) {
                    method_add.invoke(mutantfollowInstance_2,list[i],list[i]);
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
    }

    /**
     * 传入随机数的种子以便形成最初的序列
     * @param seed 随机数的种子
     * @return 最初的序列
     */
    private int[] originalList(int seed){
        int[] originalList = new int[MYRANGE];
        Random random = new Random(seed);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < originalList.length; i++) {
            int temp = random.nextInt(1024);
            while (temp < 30) {
                if (list.contains(temp)){
                    temp = random.nextInt(1024);
                }else{
                    break;
                }
            }
            list.add(temp);
        }

        for (int i = 0; i < originalList.length; i++) {
            originalList[i] = list.get(i);
        }
        return originalList;
    }

    /**
     * 获得原始序列
     * @param originallist 最初的序列
     * @param MRName MR的名字不用全名
     * @return
     */
    private int[] getsourcelist(int[] originallist,String MRName){
        int[] sourcelist = new int[originallist.length];
        try{
            Class clazz = Class.forName("metamorphic.relations." + MRName);
            Constructor constructor = clazz.getConstructor(null);
            MRInstance = constructor.newInstance(null);
            Method method_getsourcelist = clazz.getMethod("sourceList",int[].class);
            sourcelist = (int[])method_getsourcelist.invoke(MRInstance,originallist);
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
        return sourcelist;
    }

    /**
     * 获得衍生序列
     * @param sourcelist 原始序列
     * @param sourcetoplist 原始最优序列
     * @param MRName 要作用的MR的名字不必是全名
     * @return 衍生序列
     */
    private int[] getfollowlist(int[] sourcelist,int[] sourcetoplist,String MRName){
        int[] followlist = null;
        try{
            Class clazz = Class.forName("metamorphic.relations." + MRName);
            Constructor constructor = clazz.getConstructor(null);
            MRInstance = constructor.newInstance(null);
            Method method_getsourcelist = clazz.getMethod("followUpList",int[].class,int[].class);
            followlist = (int[])method_getsourcelist.invoke(MRInstance,sourcelist,sourcetoplist);
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
        return followlist;
    }

    public void test (){
        for (int i = 0; i < LOOPS; i++) {//进行重复试验
            for (int j = 0; j < NUMBEROFMRS; j++) {//每一个序列都要在所有的蜕变关系上进行作用
                String MRName = "MR" + String.valueOf(j+1);
                for (int k = 0; k < numberOftestcaces; k++) {//产生指定的序列
                    int[] originallist = originalList(k+1);//根据随机数的种子产生相应的序列1-10
                    int[] sourcelist = getsourcelist(originallist,MRName);//获取原始序列
                    //对某一个进行变异体实例化
                    MutantSet ms = new MutantSet("SimpleLinear");
                    for (int l = 0; l < ms.size(); l++) {//对所有的变异体进行遍历
                        //初始化操作原始序列的一个变异体
                        addElementToLinear(1,sourcelist,ms.getMutantFullName(l));

                        //获得原始序列的最优序列
                        getresult(1,ms.getMutantFullName(l));
                        //获得衍生序列
                        int[] sourcetoplist = new int[sourcevector.size()];
                        for (int m = 0; m < sourcevector.size(); m++) {
                            sourcetoplist[m] = sourcevector.get(m);
                        }

                        while(isHasCommenElement(sourcevector)){
                            getresult(1,ms.getMutantFullName(l));
                        }
                        int[] followlist = getfollowlist(sourcelist,sourcetoplist,MRName);
                        addElementToLinear(2,followlist,ms.getMutantFullName(l));
                        getresult(2,ms.getMutantFullName(l));



                        //输出结果检查是否正确
                        System.out.println("原始最优序列：");
                        Enumeration<Integer> ensource = sourcevector.elements();
                        while(ensource.hasMoreElements()){
                            int temp = ensource.nextElement();
                            System.out.print(temp+",");
                        }
                        System.out.println();
                        System.out.println("衍生最优序列：");

                        Enumeration<Integer> enfollow = followvector.elements();
                        while(enfollow.hasMoreElements()){
                            int temp = enfollow.nextElement();
                            System.out.print(temp + ",");
                        }



                    }//对所有的变异体进行遍历
                }//产生指定的序列
            }//每一个序列都要在所有的蜕变关系上进行作用
        }//进行重复试验
    }

    /**
     * 获取最优序列
     * @param object 变异体的实例化对象
     * @param mutantFullName 变异体的全部名字
     * @return 最优序列
     */
    private void getresult(int object,String mutantFullName){
//        sourcevector.clear();
//        followvector.clear();
//        followvector_2.clear();
        if (object == 1){
            for (int i = 0; i < THREADS; i++) {
                thread[i] = new SourceRemoveMinThread();
            }
            for (int i = 0; i < THREADS; i ++) {
                thread[i].start();
            }
            for (int i = 0; i < THREADS; i ++) {
                try{
                    thread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }else if(object == 2){
            for (int i = 0; i < THREADS; i++) {
                thread[i] = new FollowRemoveMinThread();
            }
            for (int i = 0; i < THREADS; i ++) {
                thread[i].start();
            }
            for (int i = 0; i < THREADS; i ++) {
                try{
                    thread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }else{
            for (int i = 0; i < THREADS; i++) {
                thread[i] = new Follow2RemoveMinThread();
            }
            for (int i = 0; i < THREADS; i ++) {
                thread[i].start();
            }
            for (int i = 0; i < THREADS; i ++) {
                try{
                    thread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class SourceRemoveMinThread extends Thread{

        SourceRemoveMinThread(){ }
        public void run(){
            try{
                Random random = new Random();
                Object result ;
                result = method_sourceRemove.invoke(mutantsourceInstance,null);
                if (result == null ){
                    sourcevector.add(random.nextInt(5000)+1024);
                }else {
                    int temp = (int) result;
                    boolean flag = sourcevector.contains(temp);
                    while (flag) {
                        result = method_sourceRemove.invoke(mutantsourceInstance,null);
                        if(result == null){
                            sourcevector.add(random.nextInt(5000)+1024);
                            break;
                        }else{
                            temp = (int) result;
                            flag = sourcevector.contains(temp);
                        }
                    }
                    sourcevector.add(temp);
                }
            }  catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    class FollowRemoveMinThread extends Thread{

        FollowRemoveMinThread(){}
        public void run(){
            try{
                Random random = new Random();
                Object result ;

                result = method_followRemove.invoke(mutantfollowInstance,null);
                if (result == null ){
                    followvector.add(random.nextInt(5000)+1024);
                }else {
                    int temp = (int) result;
                    boolean flag = followvector.contains(temp);
                    while (flag) {
                        result = method_followRemove.invoke(mutantfollowInstance,null);
                        if(result == null){
                            followvector.add(random.nextInt(5000)+1024);
                            break;
                        }else{
                            temp = (int) result;
                            flag = followvector.contains(temp);
                        }
                    }
                    followvector.add(temp);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    class Follow2RemoveMinThread extends Thread{

        Follow2RemoveMinThread(){ }
        public void run(){
            try{
                Random random = new Random();
                Object result ;

                result = method_followRemove2.invoke(mutantfollowInstance_2,null);
                if (result == null ){
                    followvector_2.add(random.nextInt(5000)+1024);
                }else {
                    int temp = (int) result;
                    boolean flag = followvector_2.contains(temp);
                    while (flag) {
                        result = method_followRemove2.invoke(mutantfollowInstance_2,null);
                        if(result == null){
                            followvector_2.add(random.nextInt(5000)+1024);
                            break;
                        }else{
                            temp = (int) result;
                            flag = followvector_2.contains(temp);
                        }
                    }
                    followvector_2.add(temp);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断向量中是否存在两个相同的元素，如果存在则重新运行
     * @param myvector 要判断的额向量
     * @return 是否存在相同的值，true为存在，false为不存在
     */
    private boolean isHasCommenElement(Vector<Integer> myvector){
        boolean flag = false;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < myvector.size(); i++) {
            if(list.contains(myvector.get(i))){
                flag = true;
                break;
            }else{
                list.add(myvector.get(i));
            }
        }


        return flag;


    }


    public static void main(String[] args) {
        SimpleLinear2 sl = new SimpleLinear2();
        sl.test();
    }


}

package metamorphic.relations;
/**
 * mr7
 */

import logrecorder.LogRecorder;
import logrecorder.MRKilledInfoRecorder;
import logrecorder.MutantBeKilledInfo;
import logrecorder.WrongReport;
import set.mutants.MutantSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MR7 implements MetamorphicRelations {
    private static int target_index;//插入的数据应在衍生最优序列中的值
    private static int target ;//插入的元素的值
    private static List<String> killedMutans ;
    public MR7() {
        killedMutans = new ArrayList<String>();
    }

    public int getNumberOfKilledMutants(){
        return killedMutans.size();
    }

    public List<String> getKilledMutants(){
        return killedMutans;
    }


    @Override
    public int[] sourceList(int[] mylist) { return mylist; }


    public int[] followUpList(int[] mylist,int[] toplist) {
        int[] followlist = new int[mylist.length + 1] ;
        boolean flag = false ;
        for (int i = 1; i < toplist.length; i++) {
            int max = toplist[i];
            int min = toplist[i-1];
            if ((max - min) != 1) {
                flag = true;
                break ;
            }
        }
        if (!flag){
            System.out.println("top序列不符合要求没有办法插入！");
            return null ;
        }else{
            Random random = new Random();
            int additem = 0 ;
            while(true){
                int temp = random.nextInt(toplist.length-1) + 1;
                if((toplist[temp] - toplist[temp - 1]) != 1){
                    additem = toplist[temp] - 1;
                    this.target_index = temp ;
                    this.target = additem ;
                    break ;
                }
            }
            for (int i = 0; i < followlist.length; i++) {
                if (i < mylist.length)
                    followlist[i] = mylist[i] ;
                else
                    followlist[i] = additem ;
            }
            return followlist;
        }
    }


    private static final int NUMBEROFLIST = 10;
    private static final int NUMBEROFELEMENT = 1024 ;
    private static final int TOPLENGTH = 10;
    private int threads = 10 ;
    private List<List<String>> reportKilledInfo = new ArrayList<List<String>>();
    private List<List<String>> MRKilledInfo = new ArrayList<List<String>>();
    public void testProgram(String testpriorityName,int loopTimes){

        for (int i = 0; i < NUMBEROFLIST; i++) { //产生序列对应的种子为1-10
            //记录某一个序列在所有变异体下的执行信息
            List<String> tempInfoList = new ArrayList<String>();
            //对记录某一个变异算子在某一个序列下能够杀死的变异体ID
            List<String> killedmutants = new ArrayList<String>();
            //某一个序列根据本MR在所有的变异体上运行
            MutantSet ms = new MutantSet(testpriorityName);//仅这里是待测程序的名字，其它的都需要全名
            MutantBeKilledInfo mutantBeKilledInfo  = new MutantBeKilledInfo();
            //总时间
            long totalTime = 0 ;

            for (int j = 0; j < ms.size(); j++) {//对每一个变异体进行测试
                System.out.println("test begin:" + ms.getMutantFullName(j));
                try{
                    //~~~~~~~~~~~~~~~~~~~~对象、构造器、实例、方法~~~~~~~~~~~~~~~~~//
                    Class clazz = Class.forName("test.priority." + testpriorityName);
                    Constructor constructor = clazz.getConstructor(int.class);
                    Object instance = constructor.newInstance(threads);//toplist的大小
                    Object instance_follow = constructor.newInstance(threads);
                    Method method = clazz.getMethod("testRemoveMin", int[].class, String.class);
                    //~~~~~~~~~~~~~~~~~~原始序列~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
                    Random random = new Random(i+1);
                    int[] temp = new int[NUMBEROFELEMENT];
                    List<Integer> templist = new ArrayList<Integer>();
                    templist.clear();
                    for (int h = 0; h < temp.length; h++) {
                        int data = random.nextInt(1024);
//                        while (data <30){
//                            if (templist.contains(data)){
//                                data = random.nextInt(1024);
//                            }else {
//                                break;
//                            }
//                        }
                        templist.add(data);
                    }
                    for (int k = 0; k < templist.size(); k++) {
                        temp[k] = templist.get(k);
                    }

                    int[] source = sourceList(temp);//获得原始序列

                    long startTime = System.currentTimeMillis();
                    method.invoke(instance,source,ms.getMutantFullName(j));//在原始序列下进行测试
                    Method get = clazz.getMethod("getTop", null);//获得最优序列的方法

                    int[] getlist = (int[]) get.invoke(instance, null);//获得原始最优序列


                    int[] follow = followUpList(source, getlist); //获得衍生序列

                    method.invoke(instance_follow, follow, ms.getMutantFullName(j));
                    int[] getlisttwo = (int[]) get.invoke(instance_follow, null);//获得衍生最优序列

//                    for (int k = 0; k < getlist.length; k++) {
//                        System.out.print(getlist[k]+",");
//                    }
//                    System.out.println();
//                    for (int k = 0; k < getlisttwo.length; k++) {
//                        System.out.print(getlisttwo[k]+",");
//                    }


                    long endTime = System.currentTimeMillis();
                    totalTime = totalTime + (endTime - startTime) ;
                    //判断原始最优序列与衍生最优序列是否违反了蜕变关系,并作好记录
                    boolean flag = isConformToMR(getlist,getlisttwo,i,ms.getMutantFullName(j),loopTimes);
                    if (!flag){
                        String str = ms.getMutantFullName(j);
                        killedmutants.add(String.valueOf(ms.getMutantID(str)));
                        mutantBeKilledInfo.add(loopTimes,testpriorityName,"MR7",ms.getMutantFullName(j));
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }//某一个序列遍历完了所有的变异体


            //按照表格中的表头顺序向tempInfoList<String>填入数据
            tempInfoList.clear();
            tempInfoList.add(String.valueOf(i));//记录序列信息
            tempInfoList.add(String.valueOf(loopTimes));//记录第几次重复试验
            tempInfoList.add("MR7");//记录MR信息
            tempInfoList.add(String.valueOf(ms.size()));//记录所有的变异体个数
            if (killedmutants.size() == 0){
                tempInfoList.add("无");
            }else {
                MRKilledInfo.add(killedmutants);
                String killInfo = "";
                for (int j = 0; j < killedmutants.size(); j++) {
                    killInfo = killInfo + killedmutants.get(j) + ",";
                }
                tempInfoList.add(killInfo);
            }//记录杀死的变异体信息
            //记录杀死的变异体的个数
            tempInfoList.add(String.valueOf(killedmutants.size()));
            tempInfoList.add(String.valueOf((ms.size() - killedmutants.size())));//记录该序列作用后还剩下多少变异体
            tempInfoList.add(String.valueOf(totalTime));//记录此次序列在所有的变异体上执行需要的时间
            //将此次的执行信息加入到二位的list中以便写入excel中
            reportKilledInfo.add(tempInfoList);
        }
        reportMRKilledInfo(testpriorityName,"MR7",MRKilledInfo);
        LogRecorder logRecorder = new LogRecorder();
        logRecorder.writeToEXCEL(testpriorityName,loopTimes,reportKilledInfo);
    }

    private int[] repetedTest(int[] list,String testpriorityname,int length,String mutantName){
        int[] temp = new int[length];
        try{
            Class clazz = Class.forName("test.priority." + testpriorityname);
            Constructor constructor = clazz.getConstructor(int.class);
            Object ins = constructor.newInstance(length);
            Method method = clazz.getMethod("testRemoveMin", int[].class, String.class);
            Method get = clazz.getMethod("getTop", null);
            method.invoke(ins,list,mutantName);
            temp = (int[])get.invoke(ins,null);
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
        return temp ;
    }

    /**
     * 判断原始最优序列以及衍生最优序列是否违反了蜕变关系
     * @param sourceToplist 原始最优序列
     * @param followToplist 衍生最优序列
     * @return {flag} true为没有揭示变异体，false为揭示了变异体
     */
    private boolean isConformToMR(int[] sourceToplist,int[] followToplist,int seed,String SUTFullName,int loopTimes){
        boolean flag = true ;//标志位是否与符合蜕变关系：true表示符合蜕变关系
        for (int i = 0; i < sourceToplist.length; i++) {
            if (i < target_index){
                if(sourceToplist[i] != followToplist[i]){
                    flag = false;
                    break;
                }
            }else if (i == target_index ){
                if (followToplist[i] != target){
                    flag = false;
                    break;
                }
            }else{
                if (followToplist[i] != sourceToplist[i-1]){
                    flag = true;
                    break;
                }
            }
        }

        if (flag){
            return true;
        }else {
            String source = "";
            for (int i = 0; i < sourceToplist.length; i++) {
                source = source + String.valueOf(sourceToplist[i]) + ", ";
            }
            String follow = "";
            for (int i = 0; i < followToplist.length; i++) {
                follow = follow + String.valueOf(followToplist[i] + ", ");
            }
            String report = SUTFullName + "在第" + String.valueOf(seed) + "个序列的第" + String.valueOf(loopTimes) + "次重复试验，两次执行结果违反了" +
                    "蜕变关系MR7：原始最优序列为：" + source + "衍生最优序列为：" + follow;
            WrongReport wrongReport = new WrongReport();
            wrongReport.writeLog(SUTFullName,report);
            return false;
        }
    }

    public void reportMRKilledInfo(String SUTName,String MRID,List<List<String>> list){
        List<String> temp = new ArrayList<String>();
        MRKilledInfoRecorder mr = new MRKilledInfoRecorder();
        if (list.size() == 0){
            mr.write(SUTName,"MR7",0);
        }else{
            for (int i = 0; i < list.size(); i++) {
                List<String> sublist = list.get(i);
                for (int j = 0; j < sublist.size(); j++) {
                    if(!temp.contains(sublist.get(j))){
                        temp.add(sublist.get(j));
                    }
                }
            }
            mr.write(SUTName,"MR7",temp.size());
        }
    }
    public static void main(String[] args) {
        MR7 mr = new MR7();
        mr.testProgram("SimpleLinear",0);
    }
}

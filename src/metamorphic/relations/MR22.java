package metamorphic.relations;
/**
 * mr17
 */

import generatelist.BinList;
import logrecorder.LogRecorder;
import logrecorder.MRKilledInfoRecorder;
import logrecorder.MutantBeKilledInfo;
import logrecorder.WrongReport;
import org.apache.commons.lang3.ArrayUtils;
import priority.Bin;
import set.mutants.MutantSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MR22 implements MetamorphicRelations {
    public MR22() { }


    @Override
    public int[] sourceList(int[] mylist) {
        int[] sourcelist = mylist ;
        return sourcelist;
    }
    public BinList[] followUpList(int[] mylist){
        BinList[] binlists = new BinList[2] ;
        for (int i = 0; i < binlists.length; i++) {
            binlists[i] = new BinList();
        }
        Random random = new Random();
        int[] templocal={136,235,154,254,83,104,201,223,380,336,415,465,595,535,
                620,665,771,712,879,46};
        int local = templocal[random.nextInt(20)];//得到断开原始序列的位置

        for (int i = 0; i < mylist.length; i++) {
            if (i < local)
                binlists[0].put(mylist[i]);
            else
                binlists[1].put(mylist[i]);
        }
        return binlists ;
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
            MutantBeKilledInfo mutantBeKilledInfo = new MutantBeKilledInfo();
            //总时间
            long totalTime = 0 ;

            for (int j = 0; j < ms.size(); j++) {//对每一个变异体进行测试
                System.out.println("test begin:" + ms.getMutantFullName(j));
                try{
                    //~~~~~~~~~~~~~~~~~~~~对象、构造器、实例、方法~~~~~~~~~~~~~~~~~//
                    Class clazz = Class.forName("test.priority."+testpriorityName);
                    Constructor constructor = clazz.getConstructor(int.class);
                    Object instance = constructor.newInstance(threads);
                    Object instance_follow1 = constructor.newInstance(threads);
                    Object instance_follow2 = constructor.newInstance(threads);
                    Object instance_follow3 = constructor.newInstance(threads);
                    Method method = clazz.getMethod("testRemoveMin", int[].class, String.class);
                    //~~~~~~~~~~~~~~~~~~原始序列~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
                    Random random = new Random(i+1);
                    int[] temp = new int[NUMBEROFELEMENT];
                    for (int h = 0; h < temp.length; h++) {
                        temp[h] = random.nextInt(1024);
                    }
                    int[] source = sourceList(temp);//获得原始序列


                    long startTime = System.currentTimeMillis();
                    method.invoke(instance,source,ms.getMutantFullName(j));//在原始序列下进行测试
                    Method get = clazz.getMethod("getTop", null);//获得最优序列的方法
                    int[] getlist = (int[]) get.invoke(instance, null);//获得原始最优序列
                    BinList[] follow = followUpList(source); //获得衍生序列
                    int[] follow1 = new int[follow[0].list.size()];
                    int[] follow2 = new int[follow[1].list.size()];
                    for (int k = 0; k < follow[0].list.size(); k++) {
                        follow1[k] = follow[0].list.get(k);
                    }
                    for (int k = 0; k < follow[1].list.size(); k++) {
                        follow2[k] = follow[1].list.get(k);
                    }




                    method.invoke(instance_follow1, follow1, ms.getMutantFullName(j));
                    int[] getlisttwo_1 = (int[]) get.invoke(instance_follow1, null);//获得衍生最优序列
                    method.invoke(instance_follow2,follow2,ms.getMutantFullName(j));
                    int[] getlisttwo_2 = (int[]) get.invoke(instance_follow2,null);
                    //将两个衍生序列的最优序列合并
                    int[] addlist = new int[getlisttwo_1.length+getlisttwo_2.length];
                    System.arraycopy(getlisttwo_1,0,addlist,0,getlisttwo_1.length);
                    System.arraycopy(getlisttwo_2,0,addlist,getlisttwo_1.length,getlisttwo_2.length);
                    method.invoke(instance_follow3,addlist,ms.getMutantFullName(j));
                    int[] finallist = (int[]) get.invoke(instance_follow3,null);
                    long endTime = System.currentTimeMillis();
                    totalTime = totalTime + (endTime - startTime) ;


                    for (int k = 0; k < getlist.length; k++) {
                        System.out.print(getlist[k]+",");
                    }
                    System.out.println();
                    for (int k = 0; k < getlisttwo_1.length; k++) {
                        System.out.print(getlisttwo_1[k]+",");
                    }
                    System.out.println();
                    for (int k = 0; k < getlisttwo_2.length; k++) {
                        System.out.print(getlisttwo_2[k]+",");
                    }







                    //判断原始最优序列与衍生最优序列是否违反了蜕变关系,并作好记录
                    boolean flag = isConformToMR(getlist,finallist,i,ms.getMutantFullName(j),loopTimes);
                    if (!flag){

                        String str = ms.getMutantFullName(j);
                        killedmutants.add(String.valueOf(ms.getMutantID(str)));
                        mutantBeKilledInfo.add(loopTimes,testpriorityName,"MR22",ms.getMutantFullName(j));
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
            tempInfoList.add("MR22");//记录MR信息
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
        reportMRKilledInfo(testpriorityName,"MR22",MRKilledInfo);
        LogRecorder logRecorder = new LogRecorder();
        logRecorder.writeToEXCEL(testpriorityName,loopTimes,reportKilledInfo);
    }

    private boolean isConformToMR(int[] sourceToplist,int[] followToplist,int seed,String SUTFullName,int loopTimes){
        if (Arrays.equals(sourceToplist,followToplist)){
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
                    "蜕变关系MR22：原始最优序列为：" + source + "衍生最优序列为：" + follow;
            WrongReport wrongReport = new WrongReport();
            wrongReport.writeLog(SUTFullName,report);
            return false;
        }
    }

    public void reportMRKilledInfo(String SUTName,String MRID,List<List<String>> list){
        List<String> temp = new ArrayList<String>();
        MRKilledInfoRecorder mr = new MRKilledInfoRecorder();
        if (list.size() == 0){
            mr.write(SUTName,"MR22",0);
        }else{
            for (int i = 0; i < list.size(); i++) {
                List<String> sublist = list.get(i);
                for (int j = 0; j < sublist.size(); j++) {
                    if(!temp.contains(sublist.get(j))){
                        temp.add(sublist.get(j));
                    }
                }
            }
            mr.write(SUTName,"MR22",temp.size());
        }
    }


    public static void main(String[] args) {
        MR22 mr = new MR22();
        LogRecorder.creatTableAndTitle("FineGrainedHeap");
        for (int i = 0; i < 1; i++) {
            mr.testProgram("FineGrainedHeap",i);
        }
//        Random random = new Random(1);
//        int[] mylist = new int[1024];
//        for (int i = 0; i < 1024; i++) {
//            mylist[i] = random.nextInt(1024);
//        }
//        BinList[] binLists = new BinList[2];
//        for (int i = 0; i < binLists.length; i++) {
//          binLists[i] = new BinList();
//        }
//        binLists = mr.followUpList(mylist);
//        System.out.println("1 list的长度："+binLists[0].list.size());
//        System.out.println("2 list的长度"+binLists[1].list.size());
//        Collections.sort(binLists[0].list);
//        Collections.sort(binLists[1].list);
//        for (int i = 0; i < binLists[0].list.size(); i++) {
//            System.out.print(binLists[0].list.get(i)+",");
//        }
//        System.out.println();
//        for (int i = 0; i < binLists[1].list.size(); i++) {
//            System.out.print(binLists[1].list.get(i)+",");
//        }


    }


}

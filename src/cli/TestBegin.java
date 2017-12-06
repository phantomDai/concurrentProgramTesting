package cli;

import logrecorder.LogRecorder;
import metamorphic.relations.MRSet;
import result.paeser.MutantBeKilledInfo;
import result.paeser.MutationScore;
import test.priority.Main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TestBegin {
    private static final int LOOPS = 10 ;
    /**
     * 传入要测试的软件的名字进行测试
     * @param SUTName 传入的待测程序的名字列表
     */
    public static void begintest(List<String> SUTName){
        for (int i = 0; i < SUTName.size(); i++) {
            LogRecorder.creatTableAndTitle(SUTName.get(i));
        }
        MutationScore mutationScore = new MutationScore();
        for (int j = 0; j < SUTName.size(); j++) {//对所有的SUT进行测试
            for (int i = 0; i < TestBegin.LOOPS; i++) {//控制重复实验的次数
                //获得所有的蜕变关系
                MRSet mrSet = new MRSet();
                System.out.println(mrSet.getFullMRName(j));
                for (int k = 0; k < mrSet.size(); k++) {//原始序列在所有的蜕变关系上作用

                    Object instance = null ;
                    try{
                        Class clazz = Class.forName(mrSet.getFullMRName(k));
                        Constructor constructor = clazz.getConstructor(null);
                        instance = constructor.newInstance(null);
                        Method method = clazz.getMethod("testProgram",String.class,int.class);
                        method.invoke(instance,SUTName.get(j),i);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }//所有的MR测试完毕
            }//执行了所有规定的重复次数
            //计算变异得分
            mutationScore.calculateMutationScore(SUTName.get(j));
            MutantBeKilledInfo.change(SUTName.get(j));
        }//所有的SUT测试完毕
    }

}

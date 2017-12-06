package cli;

import logrecorder.LogRecorder;
import metamorphic.relations.MRSet;
import result.paeser.MRkilledInfo;
import result.paeser.MutantBeKilledInfo;
import result.paeser.MutationScore;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author phantom
 */
public class TestSimpleTree {
    public static void main(String[] args) {
        String[] SUTName = {"SimpleTree"};
        for (int i = 0; i < SUTName.length; i++) {
            LogRecorder.creatTableAndTitle(SUTName[i]);
        }
        MutationScore mutationScore = new MutationScore();
        for (int j = 0; j < SUTName.length; j++) {//对所有的SUT进行测试
            for (int i = 0; i < TestSimpleLinear.loops; i++) {//控制重复实验的次数
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
                        method.invoke(instance,SUTName[j],i);
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
            mutationScore.calculateMutationScore(SUTName[j]);
            MutantBeKilledInfo.change(SUTName[j]);
            MRkilledInfo mRkilledInfo = new MRkilledInfo();
            mRkilledInfo.parseMutantBeKilledInfo("SimpleTree",TestSimpleLinear.loops);
        }//所有的SUT测试完毕
    }

}

package experiment.util;

import set.mutants.MutantSimpleLinearSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class Executer {
    public Executer() {}

    /**
     * 构造指定类型的对象
     * @param fullClassName 要构造的指定对象的类名
     * @param parameterClasslist 构造实例对象所需要的类型列表
     * @param objects 构造实例对象所需要的实例列表
     * @return 指定的类型列表
     */
    public Object constructInstance(String fullClassName,Class[] parameterClasslist,
                                    Object[] objects){
        Object instance = null ;
        Class clazz ;
        Constructor cs ;
        try{
            clazz = Class.forName(fullClassName) ;//载入变异体
            cs = clazz.getConstructor(parameterClasslist);//构造器创建
            cs.setAccessible(true);//安全检查的开关
            instance = cs.newInstance(objects);
        }catch (ClassNotFoundException e){
            System.out.println("没有找到指定类");
            e.printStackTrace();
        }catch(IllegalArgumentException e){
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
        return instance;
    }

    /**
     * 动态调用指定的方法
     * @param className 完整的类名
     * @param instance 指定类型的实例对象
     * @param methodName 被调用的方法名
     * @param classes 被调用的方法的参数列表
     * @param objects 被调用的方法的参数实例列表
     * @return 被调用方法的运行结果
     */
    public Object invoke(String className, Object instance,String methodName,
                         Class[] classes,Object[] objects){
        Class clazz;
        Constructor cs ;
        Method method ;
        Object result = null;
        try{
            clazz = Class.forName(className);
            method = clazz.getMethod(methodName,classes);//变异体中的待测方法
            result = method.invoke(instance,objects);//创建的实例并将参数实例传给待测方法之后返回计算结果
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        Object mutantObject; //变异体的实例化程序
        Object sourceResult;//原始序列的执行结果
        Object followResult;//衍生序列的执行结果
        int range = 1024;
        Class[] cs = new Class[] {int.class}; //构造函数的参数类型
        Object[] os ; //参数实例
        String method_addName = "add" ;
        String method_removeName = "removeMin" ;
        os = new Object[] { range };
        Executer executer = new Executer();
        MutantSimpleLinearSet mutantSimpleLinearSet = new MutantSimpleLinearSet("SimpleLinear");
        mutantObject = executer.constructInstance(mutantSimpleLinearSet.getMutantFullName(0),cs,os);
        Class[] addMethodParameters = new Class[]{Integer.class,Integer.class};
        for (int i = 0; i < 1; i++) {
            Random random = new Random(1);
            int temp = random.nextInt(5000);
            executer.invoke(mutantSimpleLinearSet.getMutantFullName(0),
                    mutantObject,method_addName,addMethodParameters,new Object[]{temp,temp});
        }
        sourceResult = executer.invoke(mutantSimpleLinearSet.getMutantFullName(0),
                mutantObject,method_removeName,null,null);
        System.out.println(sourceResult);
    }


}

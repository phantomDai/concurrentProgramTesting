package temp;

import priority.SimpleLinear;
import set.mutants.Mutant;
import set.mutants.MutantSimpleLinearSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class testStudent {
    public static void main(String[] args) {
        try{
            MutantSimpleLinearSet ms = new MutantSimpleLinearSet("SimpleLinear");
            Class clazz = Class.forName(ms.getMutantFullName(1));
            Constructor constructor = clazz.getConstructor(int.class) ;//获得对象的构造器
            Object simpleLinear = constructor.newInstance(1024);

            Method method_add = clazz.getMethod("add", Object.class,int.class) ;
            method_add.invoke(simpleLinear,20,20);
            Method method_remove = clazz.getMethod("removeMin",null);
            Object result = method_remove.invoke(simpleLinear,null);
            int a = (int) result;
            System.out.println(a);
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

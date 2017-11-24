package metamorphic.relations;
/**
 * 该类实现自动在MRs文件下搜索蜕变关系并自动加入集合中
 */

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MRSet {
    private List<MR> MRlist;
    public MRSet() {
//        String path = System.getProperty("user.dir")+"\\src\\metamorphic\\relations";
//        File file = new File(path);
//        String[] filelist = file.list();
        MR mr;
        MRlist = new ArrayList<MR>();
        for (int i = 0; i < 26; i++) {
            String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i+1);
            mr = new MR(String.valueOf(i+1),MRFullName);
            MRlist.add(mr);
        }
    }

    public String getFullMRName(int x){
        MR mr = MRlist.get(x);
        return mr.getMRname();
    }

    public int size(){
        return MRlist.size();
    }

    public static void main(String[] args) {
        MRSet set = new MRSet();
        Object instance ;
        try{
            Class clazz = Class.forName(set.getFullMRName(0));
            Constructor constructor = clazz.getConstructor(null);
            instance = constructor.newInstance(null);
            Method method = clazz.getMethod("testProgram",String.class,int.class);
            method.invoke(instance,"SimpleLinear",0);
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

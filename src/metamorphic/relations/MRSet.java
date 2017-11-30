package metamorphic.relations;
/**
 * 该类实现自动在MRs文件下搜索蜕变关系并自动加入集合中
 */


import java.util.ArrayList;
import java.util.List;

public class MRSet {
    private List<MR> MRlist;
    public MRSet() {
        MR mr;
        MRlist = new ArrayList<MR>();
        for (int i = 1; i <= 19; i++) {
            if (i <= 9){
                String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i);
                mr = new MR(String.valueOf(i+1),MRFullName);
                MRlist.add(mr);
            }else if (i == 10){
                for (int j = 1; j <=3 ; j++) {
                    String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i) + "_" + String.valueOf(j);
                    mr = new MR(String.valueOf(i+1),MRFullName);
                    MRlist.add(mr);
                }
            }else if (i == 11){
                for (int j = 1; j <=3 ; j++) {
                    String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i) + "_" + String.valueOf(j);
                    mr = new MR(String.valueOf(i+1),MRFullName);
                    MRlist.add(mr);
                }
            }else if (i > 11 && i <= 15){
                String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i);
                mr = new MR(String.valueOf(i+1),MRFullName);
                MRlist.add(mr);
            }else if (i == 16){
                for (int j = 1; j <=2 ; j++) {
                    String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i) + "_" + String.valueOf(j);
                    mr = new MR(String.valueOf(i+1),MRFullName);
                    MRlist.add(mr);
                }
            }else if (i == 17){
                String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i);
                mr = new MR(String.valueOf(i+1),MRFullName);
                MRlist.add(mr);
            }else if (i == 18){
                for (int j = 1; j <=2 ; j++) {
                    String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i) + "_" + String.valueOf(j);
                    mr = new MR(String.valueOf(i+1),MRFullName);
                    MRlist.add(mr);
                }
            }else if (i == 19){
                for (int j = 1; j <=2 ; j++) {
                    String MRFullName = "metamorphic.relations."+"MR" + String.valueOf(i) + "_" + String.valueOf(j);
                    mr = new MR(String.valueOf(i+1),MRFullName);
                    MRlist.add(mr);
                }
            }
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
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.getFullMRName(i));
        }
    }

}

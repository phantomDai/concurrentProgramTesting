package result.paeser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BinSet {
    private Set<String> set ;
    public BinSet(){
        set = new HashSet<String>();
    }
    public void add (String item){
        set.add(item);
    }
    public String get(int i){
        Iterator it = set.iterator();
        if (i >= size()){
            System.out.println("超出范围");
        }
        int x = 0 ;
        String temp = "";
        while (it.hasNext()){
            if (x == i){
                temp = (String) it.next();
                break;
            }else {
                it.next();
                x++;
            }
        }
        return temp;
    }

    public int size(){
        return set.size();
    }

    public void removeAll(){
        set.clear();
    }

    public static void main(String[] args) {
        BinSet set = new BinSet();
        set.add("1");
        set.add("2");
        set.add("3");
//        for (int i = 0; i < set.size(); i++) {
//            System.out.print(set.get(i) + ", ");
//        }
        System.out.println(set.get(0));



    }
}

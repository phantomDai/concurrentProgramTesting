package result.paeser;

import java.util.ArrayList;
import java.util.List;

public class BinListMutant {
    private List<String> list ;
    public BinListMutant(){
        list = new ArrayList<String>();
    }
    public int size(){
        return list.size();
    }
    public void add(String item){
        list.add(item);
    }
    public String get(int i){
        return list.get(i);
    }
}

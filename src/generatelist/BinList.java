package generatelist;

import java.util.ArrayList;
import java.util.List;

public class BinList {
    public List<Integer> list ;
    public BinList(){
        list = new ArrayList<Integer>() ;
    }

    /**
     * 向列表中添加元素
     * @param item 要添加的元素
     */
    public void put(int item){
        list.add(item) ;
    }

}

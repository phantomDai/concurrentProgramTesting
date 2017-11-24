package priority;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class test {

    //用快排的算法对产生的序列进行排序





    //产生1000个数并按照从小到大排列
    public int[] generateArray(int size){
        int[] m = new int[size] ;
        Random random = new Random(1) ;
        for (int i = 0; i < size; i++) {
            m[i] = random.nextInt(size) ;
        }
        return m ;
    }


    public static void main(String[] args) throws IOException {
        String elementPath = "//element[@id='" + String.valueOf(1) + "']" ;
        System.out.println(elementPath);





    }
}

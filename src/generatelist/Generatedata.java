package generatelist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Generatedata {

    /**
     * 产生原始序列
     * @param seed 随机数的种子
     * @param length 序列的长度
     * @param path 写入的文件名字
     * @throws IOException 文件异常
     */
    public int[] generateArray(int seed,int length,String path) throws IOException {
        Random random = new Random(seed) ;
        int[] m = new int[length] ;
        for (int i = 0; i < length; i++) {
            m[i] = random.nextInt(length) ;
        }
        writeToFile(path,m);
        return m ;
    }
    public int[] generateArray(int seed,int length) {
        Random random = new Random(seed) ;
        int[] m = new int[length] ;
        for (int i = 0; i < length; i++) {
            m[i] = random.nextInt(length) ;
        }
        return m ;
    }




    public void writeToFile(String path,int[] m) throws IOException {
        File file = new File(path) ;
        if (!file.exists())
            file.createNewFile() ;
        FileWriter fw = new FileWriter(file) ;
        BufferedWriter bw = new BufferedWriter(fw) ;
        for (int i = 0; i < m.length; i++) {
            bw.write(m[i] + "\r");
        }
        bw.close();
    }



    public int[] getPriority(int[] m){
        int[] priority = new int[m.length] ;
        for (int i = 0; i < m.length; i++) {
            if(i == 0) {
                priority[i] = i;
            }else {
                if (m[i] == m[i - 1])
                    priority[i] = priority[i - 1];
                else
                    priority[i] = priority[i - 1] + 1;
            }
        }
        return priority ;
    }


    public static void main(String[] args) {
        String listOnefile = System.getProperty("user.dir") + "\\datafile" + "dataOne.txt" ;
        String listOneSortedfile = System.getProperty("user.dir") + "\\datafile" + "dataOneSorted.txt" ;
        String listOneSortedPriorityfile = System.getProperty("user.dir") + "\\datafile" + "dataOneSortedPriority.txt" ;
        System.out.println(listOnefile);
        System.out.println(listOneSortedfile);
        System.out.println(listOneSortedPriorityfile);
    }

}

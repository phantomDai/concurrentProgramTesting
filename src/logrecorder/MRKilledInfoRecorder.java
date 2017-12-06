package logrecorder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static java.io.File.separator;

public class MRKilledInfoRecorder {
    public MRKilledInfoRecorder() { }


    /**
     * 向datafile文件中写入每一个待测程序下每一个MR在10个序列下能够揭示的故障数目
     * 格式为：SUT.MR.numberOfKilledMutant
     * @param SUTName
     * @param MRID
     * @param numberOfKilled
     */
    public void write(String SUTName, String MRID, int numberOfKilled){
        String path = System.getProperty("user.dir") +separator +"datafile"+separator + SUTName + ".txt" ;
        String info = SUTName + "." + MRID + "." + String.valueOf(numberOfKilled);
        File file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,true),"UTF-8"));
            bw.write(info + "\r");
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MRKilledInfoRecorder mki = new MRKilledInfoRecorder();
        MRKilledInfoRecorder mk = new MRKilledInfoRecorder();
        mki.write("test","mr1",10);
        mk.write("test","mr1",11);

    }


}

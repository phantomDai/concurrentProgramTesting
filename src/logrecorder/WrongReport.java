package logrecorder;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static java.io.File.separator;

public class WrongReport {
    public WrongReport() {}

    /**
     * 将错误信息记录到日志文件中
     * @param mutantFullName 待测变异体的全名
     * @param info 记录的信息
     */
    public void writeLog(String mutantFullName,String info){
        String[] name = mutantFullName.split("\\.");
        String logname = name[1];
        String path = System.getProperty("user.dir") + separator+"logfile" + separator + logname + ".txt";
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


}

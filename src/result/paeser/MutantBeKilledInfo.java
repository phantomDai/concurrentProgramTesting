package result.paeser;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.io.File.separator;

public class MutantBeKilledInfo {
    public static void change(String SUTName){
        for (int i = 0; i < 10; i++) {
            String path = System.getProperty("user.dir")+separator+"logfile"+separator+"mutantBekilledInfo_"+SUTName+String.valueOf(i)+".txt";
            File file = new File(path);
            if (!file.exists()){
                System.out.println("文件不存在");
            }
            try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                StringBuffer sb = new StringBuffer();
                String temp = "";
                while((temp = bufferedReader.readLine()) != null){
                    String[] str = temp.split(":");
                    if (str.length == 1){
                        sb.append(str[0] + "\r");
                    }else {
                        sb.append(str[0]);
                        sb.append(":");
                        String[] MRs = str[1].split(";");
                        Set<String> set = new HashSet<String>();
                        for (int j = 0; j < MRs.length; j++) {
                            set.add(MRs[j]);
                        }
                        String newMRs = "";
                        Iterator it = set.iterator();
                        while(it.hasNext()){
                            newMRs = newMRs + it.next() + ";";
                        }
                        sb.append(newMRs+"\r");
                    }
                }
                bufferedReader.close();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
                bufferedWriter.write(sb.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}

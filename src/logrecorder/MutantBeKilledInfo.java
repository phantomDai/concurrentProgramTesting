package logrecorder;

import set.mutants.MutantSet;

import java.io.*;

import static java.io.File.separator;

public class MutantBeKilledInfo {
    public MutantBeKilledInfo() { }


    public void add(int loops,String SUTName,String MRName,String mutantName){
        String path = System.getProperty("user.dir")+separator+"logfile"+separator+"mutantBekilledInfo_" + SUTName+String.valueOf(loops)+".txt";
        File file = new File(path);
        if (!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(file.length() ==0){
            createfile(SUTName,path);
        }

        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String tempStr = "";
            while((tempStr = bufferedReader.readLine()) != null){
                if(tempStr.contains(mutantName)){
                    tempStr = tempStr + MRName +";";

                }
                stringBuffer.append(tempStr+"\n");
            }
            stringBuffer.setLength(stringBuffer.length()-1);
            bufferedReader.close();
            //写入文件
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(path)));
            printWriter.write(stringBuffer.toString());
            printWriter.flush();
            printWriter.close();




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createfile (String SUTName,String path){
        MutantSet ms = new MutantSet(SUTName);
        File file = new File(path);
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,true),"UTF-8"));
            for (int i = 0; i < ms.size(); i++) {
                bw.write(ms.getMutantFullName(i) + ":" + "\r");
            }
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        MutantBeKilledInfo mutantBeKilledInfo = new MutantBeKilledInfo();
        MutantSet ms = new MutantSet("SimpleLinear");
        mutantBeKilledInfo.add(0,"SimpleLinear","MR1",ms.getMutantFullName(0));
    }

}

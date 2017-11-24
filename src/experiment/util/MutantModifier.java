package experiment.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MutantModifier {
    public MutantModifier() { }


    public List<String> getPackageName(String SUTName){
        List<String> packageNames = new ArrayList<String>();
        String path = System.getProperty("user.dir") + "\\src\\mutants\\" + SUTName ;
        File file = new File(path) ;
        File[] mutants = file.listFiles();

        for (int i = 0; i < mutants.length; i++) {
            if (mutants[i].isDirectory()){
                packageNames.add("mutants." + SUTName + "." + mutants[i].getName());
            }
        }
        return packageNames;
    }



    public void modify(String SUTName){

        List<String> packageNames = getPackageName(SUTName);
        for (int i = 0; i < packageNames.size(); i++) {
            String mPostfix = SUTName + ".java" ;
            System.out.println(packageNames.get(i));
            String[] temp = packageNames.get(i).split("\\.");

            String path = System.getProperty("user.dir") + "\\src\\"
                    + temp[0] + "\\" + temp[1] + "\\" + temp[2] + "\\" + mPostfix;

            String content_package = "package " + packageNames.get(i) + ";" ;
            File file = new File(path);
            try{
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuffer mutantContentBuf = new StringBuffer(0);
                String tempstr = "" ;
                int index = 1;
                while((tempstr = bufferedReader.readLine()) != null){
                    if (index == 1){
                        mutantContentBuf.append(content_package + "\r");
                    }else {
                        mutantContentBuf.append(tempstr+"\r");
                    }
                    index++ ;
                }
                bufferedReader.close();
                fileReader.close();

                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(mutantContentBuf.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            }catch (FileNotFoundException e1){
                e1.printStackTrace();
                System.out.println("û�з��ָ��ļ�");
            }catch (IOException e2){
                e2.printStackTrace();
                System.out.println("д�����");
            }
        }
    }

    public void modify(String parentName,String targetName){
        List<String> packageNames = getPackageName(parentName);
        for (int i = 0; i < packageNames.size(); i++) {
            String mPostfix = targetName + ".java" ;
            System.out.println(packageNames.get(i));
            String[] temp = packageNames.get(i).split("\\.");

            String path = System.getProperty("user.dir") + "\\src\\"
                    + temp[0] + "\\" + temp[1] + "\\" + temp[2] + "\\" + mPostfix;

            String content_package = "package " + packageNames.get(i) + ";" ;
            File file = new File(path);
            try{
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuffer mutantContentBuf = new StringBuffer(0);
                String tempstr = "" ;
                int index = 1;
                while((tempstr = bufferedReader.readLine()) != null){
                    if (index == 11){
                        mutantContentBuf.append(content_package + "\r");
//                    	mutantContentBuf.append("/*");
                    }else {
                        mutantContentBuf.append(tempstr+"\r");
                    }
                    index++ ;
                }
                bufferedReader.close();
                fileReader.close();

                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(mutantContentBuf.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            }catch (FileNotFoundException e1){
                e1.printStackTrace();
                System.out.println("û�з��ָ��ļ�");
            }catch (IOException e2){
                e2.printStackTrace();
                System.out.println("д�����");
            }
        }
    }

    public void addFile(String parentName,String fileName){
        List<String> packageNames = getPackageName(parentName);
        for (int i = 0; i < packageNames.size(); i++) {
            System.out.println(packageNames.get(i));
            String originalFileName = System.getProperty("user.dir")+
                    "\\src\\"+"priority\\"+fileName+".java";
            String[] temp = packageNames.get(i).split("\\.");
            String creatFileName = System.getProperty("user.dir")+"\\src\\"
                    +temp[0] + "\\" + temp[1] + "\\" + temp[2] + "\\" + fileName+".java";
            File creatFile = new File(creatFileName);
            if(!creatFile.exists()){
                creatFile.mkdirs();
            }

            try{
                FileReader fileReader = new FileReader(new File(originalFileName));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuffer buffer = new StringBuffer(0);
                String tempString = "";
                while((tempString=bufferedReader.readLine()) != null){
                    buffer.append(tempString+"\r");
                }
                bufferedReader.close();
                fileReader.close();
                FileWriter fileWriter = new FileWriter(creatFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(buffer.toString());
                bufferedWriter.flush();
                bufferedWriter.close();

            }catch (IOException e) {
                // TODO: handle exception
            }
        }
    }

    public void deleteFile(String parentName,String fileName){
        List<String> packageNames = getPackageName(parentName);
        for (int i = 0; i < packageNames.size(); i++){
            System.out.println(packageNames.get(i));

            String[] temp = packageNames.get(i).split("\\.");
            String creatFileName = System.getProperty("user.dir")+"\\src\\"
                    +temp[0] + "\\" + temp[1] + "\\" + temp[2] + "\\" + fileName;

                File file = new File(creatFileName);
                file.delete();

        }
    }




    public static void main(String[] args) {
        MutantModifier modifier = new MutantModifier();
//        modifier.modify("SimpleTree");
//        modifier.modify("SequentialHeap");
//        modifier.addFile("SequentialHeap", "PQueue");
//        modifier.modify("SequentialHeap", "PQueue");
//        modifier.deleteFile("SequentialHeap","SequentialHeap$1.class");
//        modifier.deleteFile("SequentialHeap","SequentialHeap$HeapNode.class");
//        modifier.deleteFile("SequentialHeap","SequentialHeap.class");
    }
}

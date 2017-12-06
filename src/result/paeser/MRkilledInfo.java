package result.paeser;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import metamorphic.relations.MRSet;

import java.io.*;
import java.util.*;

import static java.io.File.separator;

public class MRkilledInfo {
    MRSet mrSet = new MRSet();
    List<String> MRnames = mrSet.getMRnames();

    /**
     * 将每一个待测程序下的mutantBeKilledInfo_XX.txt文件中的信息提取出来写入到xls中
     * * @param SUTname 待测程序的名字
     * @param loops SUT重复测试的次数
     */
    public void parseMutantBeKilledInfo(String SUTname,int loops){
        //创建26个列表对应每一个MR杀死mutant的信息
        BinListMutant[] binListMutants = new BinListMutant[26];
        for (int i = 0; i < binListMutants.length; i++) {
            binListMutants[i] = new BinListMutant();
        }
        //创建26个集合，对应每一个MR杀死mutant的不重复信息
        BinSet[] sets = new BinSet[26];
        for (int j = 0; j < sets.length; j++) {
            sets[j] = new BinSet();
        }

        List<Integer> numofMRkilledMutants = new ArrayList<Integer>();

        //逐个遍历SUT下的所有mutantBeKilledInfo_XX.txt提取相关信息
        for (int i = 0; i < loops; i++) {
            String path = System.getProperty("user.dir") + separator+"logfile"+separator + "mutantBeKilledInfo_" +
                    SUTname + String.valueOf(i) + ".txt" ;
            File file = new File(path);
            if(!file.exists()){
                System.out.println("找不到指定的文件");
            }

            //开始读取文件
            try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String tempStr = "";
                while ((tempStr = bufferedReader.readLine()) != null){
                    if (!tempStr.contains(":")) {//如果成立说明该mutant在某一次试验下没有被任何MR检测出来
                        continue;
                    }else {
                        //将信息分成两个部分：变异体信息和杀死变异体的MR信息
                        String[] tempstr = tempStr.split(":");
                        String[] mutantstr = tempstr[0].split("\\.");
                        //得到变异体的名字
                        String mutantName = mutantstr[2];
                        //得到杀死该变异体的MR信息
                        String[] MRstr = tempstr[1].split(";");
                        //逐个提取MR并且将mutant的名字记录到对应的set中
                        for (int k = 0; k < MRstr.length; k++) {
                            String tempMRName = MRstr[k];
                            for (int l = 0; l <= MRnames.size(); l++) {
                                String name = MRnames.get(l);
                                if (name.equals(tempMRName)){
                                    sets[l].add(mutantName);
                                    break;
                                }
                            }
                        }
                    }
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//loops

        //每一个MR杀死的变异体的数量依次记录到列表中
        for (int i = 0; i < sets.length; i++) {
            numofMRkilledMutants.add(sets[i].size());
        }

        //得到某一个待测程序的所有MR的情况之后，将杀死的变异体进行分类，统计每一个类中的mutants被杀死的情况
        int[] record = new int[8];
        Set<String> tempSet = new HashSet<String>();//存放检测过的变异体
        for (int j = 0; j < sets.length; j++) {
            for (int k = 0; k < sets[j].size(); k++) {
                String tempmutant = sets[j].get(k);
                if (!tempSet.contains(tempmutant)){
                    tempSet.add(tempmutant);
                    String[] mutantArray = tempmutant.split("_");
                    String name = mutantArray[0];
                    if (name.equals("AORB") || name.equals("AORS")||name.equals("AOIU")||name.equals("AOIS")){
                        record[0] += 1 ;
                    }else if (name.equals("ROR")){
                        record[1] += 1 ;
                    }else if (name.equals("COR")||name.equals("COI")||name.equals("COD")){
                        record[2] += 1 ;
                    }else if (name.equals("LOI")){
                        record[3] += 1 ;
                    }else if (name.equals("SDL")||name.equals("VDL")||name.equals("CDL")||name.equals("ODL")){
                        record[4] += 1 ;
                    }else if (name.equals("RCXC")||name.equals("SAN")||name.equals("ELPA")){
                        record[5] += 1 ;
                    }else if (name.equals("ASTK")||name.equals("RSK")||name.equals("RFU")){
                        record[6] += 1 ;
                    }else {
                        record[7] += 1 ;
                    }
                }else {
                    continue;
                }
            }
        }
        writeToTxt(SUTname,sets,record);

    }

    private void writeToTxt(String SUTname,BinSet[] binSets,int[] record){
        String path = System.getProperty("user.dir") + separator + "datafile" + separator + SUTname + "_cotegoryInfo.txt";
        File file = new File(path);
        try{
            if (!file.exists())
                file.createNewFile();

            StringBuffer stringBuffer = new StringBuffer(10);
            if(SUTname.equals("SimpleLinear")){
                stringBuffer.append("SimpleLinear每一个category的情况：category_1有4个变异体;" +
                        "category_2有4个变异体;" +
                        "category_3有2个变异体;"+
                        "category_4有0个变异体;"+
                        "category_5有3个变异体;"+
                        "category_6有0个变异体;"+
                        "category_7有1个变异体;"+
                        "category_8有0个变异体;"+"\n");
            }else if (SUTname.equals("SimpleTree")){
                stringBuffer.append("SimpleTree每一个category的情况：category_1有3个变异体;" +
                        "category_2有8个变异体;" +
                        "category_3有3个变异体;"+
                        "category_4有0个变异体;"+
                        "category_5有14个变异体;"+
                        "category_6有1个变异体;"+
                        "category_7有0个变异体;"+
                        "category_8有0个变异体;"+"\n");
            }

            stringBuffer.append("经测试后每一个category中的变异体检测情况：category_1检测出"+String.valueOf(record[0])+"个变异体;"+
                    "category_2检测出"+String.valueOf(record[1])+"个变异体;" +
                    "category_3检测出"+String.valueOf(record[2])+"个变异体;" +
                    "category_4检测出"+String.valueOf(record[3])+"个变异体;" +
                    "category_5检测出"+String.valueOf(record[4])+"个变异体;" +
                    "category_6检测出"+String.valueOf(record[5])+"个变异体;" +
                    "category_7检测出"+String.valueOf(record[6])+"个变异体;" +
                    "category_8检测出"+String.valueOf(record[7])+"个变异体;" +"\n");
            MRSet mrSet = new MRSet();
            List<String> MRnames = mrSet.getMRnames();
            for (int i = 0; i < binSets.length; i++) {
                stringBuffer.append(MRnames.get(i)+"杀死的变异体个数为："+String.valueOf(binSets[i].size()) + "\n");
            }

            //将StringBuffer中的字符串写入txt文件中
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            printWriter.write(stringBuffer.toString());
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void paeserMutantBeKilledInfo(){

        String[] SUTName = {"SimpleLinear", "SimpleTree", "SequentialHeap", "SkipQueue","FineGrainedHeap"};
//        String[] SUTName = {"SimpleLinear"};
        //接下里将所有的记录汇总
        BinListMutant[] binlist = new BinListMutant[27];
        for (int i = 0; i < binlist.length; i++) {
            binlist[i] = new BinListMutant();
        }

        for (int i = 0; i < SUTName.length; i++) {
            BinSet[] sets = new BinSet[27];
            for (int j = 0; j < sets.length; j++) {
                sets[j] = new BinSet();
            }//初始化列表，每一个列表记录了对应MR杀死的变异体
            //逐个遍历某一程序下的所有记录
            for (int j = 0; j < 10; j++) {
                String path = System.getProperty("user.dir") + separator+"logfile"+separator + "mutantBeKilledInfo_" +
                        SUTName[i] + String.valueOf(j) + ".txt" ;
                File file = new File(path);
                if(!file.exists()){
                    System.out.println("找不到指定的文件");
                }
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String temp = "";
                    while((temp = bufferedReader.readLine()) != null){
                        if (!temp.contains(":")){
                            continue;
                        }
                        String[] tempstr = temp.split(":");
                        String[] mutantstr = tempstr[0].split("\\.");
                        //得到变异体的名字
                        String mutantName = mutantstr[2];

                        String[] MRstr = tempstr[1].split(";");
                        //得到一行数据，然后解析数据
                        for (int k = 0; k < MRstr.length; k++) {
                            String tempMRName = MRstr[k];

                            for (int l = 0; l <= MRnames.size(); l++) {
                                String name = MRnames.get(l);
                                if (name.equals(tempMRName)){
                                    sets[l].add(mutantName);
                                    break;
                                }
                            }
                        }

                    }
                    bufferedReader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 0; j < sets.length; j++) {
                for (int k = 0; k < sets[j].size(); k++) {
                    binlist[j].add(sets[j].get(k));
                }
            }
//            for (int j = 0; j < sets[9].size(); j++) {
//                System.out.print(sets[9].get(j)+", ");
//            }
            //得到某一个待测程序的所有MR的情况之后，将杀死的变异体进行分类
            List<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < sets.length; j++) {
                int[] record = new int[8];
                for (int k = 0; k < record.length; k++) {
                        record[k] = 0 ;
                }
                for (int k = 0; k < sets[j].size(); k++) {
                    String tempmutant = sets[j].get(k);
                    String[] mutantArray = tempmutant.split("_");
                    String name = mutantArray[0];
                    if (name.equals("AORB") || name.equals("AORS")||name.equals("AOIU")||name.equals("AOIS")){
                        record[0] += 1 ;
                    }else if (name.equals("ROR")){
                        record[1] += 1 ;
                    }else if (name.equals("COR")||name.equals("COI")||name.equals("COD")){
                        record[2] += 1 ;
                    }else if (name.equals("LOI")){
                        record[3] += 1 ;
                    }else if (name.equals("SDL")||name.equals("VDL")||name.equals("CDL")||name.equals("ODL")){
                        record[4] += 1 ;
                    }else if (name.equals("RCXC")||name.equals("SAN")||name.equals("ELPA")){
                        record[5] += 1 ;
                    }else if (name.equals("ASTK")||name.equals("RSK")||name.equals("RFU")){
                        record[6] += 1 ;
                    }else {
                        record[7] += 1 ;
                    }
                }
                //将MR杀死变异体的信息8个为一组按照次序分别记录到列表中
                for (int k = 0; k < record.length; k++) {
                    list.add(record[k]);
                }

            }
            //将MR杀死变异体的信息记录到excel表格中
            recordToExcel(list,SUTName[i]);
        }
        //将所有的记录记录到excel中
        List<Integer> allList = new ArrayList<Integer>();
        for (int i = 1; i < binlist.length ; i++) {
            int[] record = new int[8];
            for (int j = 0; j < record.length; j++) {
                record[j] = 0 ;
            }
            for (int j = 0; j < binlist[i].size(); j++) {
                String tempmutant = binlist[i].get(j);
                String[] mutantArray = tempmutant.split("_");
                String name = mutantArray[0];
                if (name.equals("AORB") || name.equals("AORS")||name.equals("AOIU")||name.equals("AOIS")){
                    record[0] += 1 ;
                }else if (name.equals("ROR")){
                    record[1] += 1 ;
                }else if (name.equals("COR")||name.equals("COI")||name.equals("COD")){
                    record[2] += 1 ;
                }else if (name.equals("LOI")){
                    record[3] += 1 ;
                }else if (name.equals("SDL")||name.equals("VDL")||name.equals("CDL")||name.equals("ODL")){
                    record[4] += 1 ;
                }else if (name.equals("RCXC")||name.equals("SAN")||name.equals("ELPA")){
                    record[5] += 1 ;
                }else if (name.equals("ASTK")||name.equals("RSK")||name.equals("RFU")){
                    record[6] += 1 ;
                }else {
                    record[7] += 1 ;
                }
            }
            for (int k = 0; k < record.length; k++) {
                allList.add(record[k]);
            }

        }
        recordToExcel(allList,"allSUT");
    }

    private void recordToExcel(List<Integer> list,String SUTName){
        String path = System.getProperty("user.dir") + separator+"logfile"+separator+"MRkilledMutantInfo_" + SUTName + ".xls" ;
        File file = new File(path);
        if (!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //创建工作部并向xls中写入数据
        try{
            WritableWorkbook workbook = Workbook.createWorkbook(file);//创建工作簿
            WritableSheet sheet = workbook.createSheet("sheet",0);//创建sheet

            WritableFont headFont = new WritableFont(WritableFont.ARIAL, 14);
            WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 12);
            /** ************以下设置几种格式的单元格************ */
            // 用于表头
            WritableCellFormat wcf_head = new WritableCellFormat(headFont);
            wcf_head.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_head.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_head.setAlignment(jxl.format.Alignment.CENTRE); // 文字水平对齐
            wcf_head.setWrap(false); // 文字是否换行
            // 用于正文居中
            WritableCellFormat wcf_center = new WritableCellFormat(normalFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(jxl.format.Alignment.CENTRE); // 文字水平对齐


            String[] title = {"category_1", "category_2","category_3","category_4","category_5","category_6","category_7","category_8"};
            for (int i = 0; i < title.length; i++) {
                sheet.addCell(new Label(i+1,0,title[i],wcf_head));
                sheet.setColumnView(i, 20);
            }
            for (int i = 0; i <= MRnames.size(); i++) {
                String temp = MRnames.get(i);
                sheet.addCell(new Label(0,i,temp,wcf_head));
            }
            //然后向cell中写入数据
            for (int i = 0; i < list.size(); i++) {
                int consult = i / 8 + 1 ;
                int remainder = i % 8 + 1;
                sheet.addCell(new Label(remainder,consult,String.valueOf(list.get(i)),wcf_center));
            }
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        MRkilledInfo info = new MRkilledInfo();
        info.parseMutantBeKilledInfo("SimpleLinear",1);
    }
}
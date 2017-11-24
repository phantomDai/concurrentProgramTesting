package result.paeser;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;

import java.io.*;
import java.util.*;

public class MRkilledInfo {
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
                String path = System.getProperty("user.dir") + "\\logfile\\" + "mutantBeKilledInfo_" +
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
                            for (int l = 1; l <= 26; l++) {
                                String name = "MR" + String.valueOf(l);
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
            for (int j = 1; j < sets.length; j++) {
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
        String path = System.getProperty("user.dir") + "\\logfile\\MRkilledMutantInfo_" + SUTName + ".xls" ;
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
            for (int i = 1; i <=26 ; i++) {
                String temp = "MR" + String.valueOf(i);
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
        info.paeserMutantBeKilledInfo();
    }
}
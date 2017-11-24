package logrecorder;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.*;
import java.util.List;

import static java.io.File.separator;

/**
 * 每对一个待测对象进行记录需要重新创建一个新的对象，但是对同一程序进行测试不需要创建新的对象。
 */
public class LogRecorder {

    public LogRecorder() { }

    /**
     *向EXCEL中写入内容
     * @param SUTName 待测程序的名字
     * @param circleTimes 序列的重复次数并且circleTimes+1对应sheet？
     * @param content 某一个序列在某一个待测程序下某一次循环的记录，是一个二维的列表
     */
    public void writeToEXCEL(String SUTName, int circleTimes, List<List<String>> content) {
        String path = System.getProperty("user.dir") + separator+"logfile\\" + SUTName +".xls" ;
        File file = new File(path);

        try{
            WritableFont headFont = new WritableFont(WritableFont.ARIAL, 14);
            WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 12);
            /** ************以下设置几种格式的单元格************ */
            // 用于表头
            WritableCellFormat wcf_head = new WritableCellFormat(headFont);
            wcf_head.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_head.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_head.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcf_head.setWrap(false); // 文字是否换行
            // 用于正文居中
            WritableCellFormat wcf_center = new WritableCellFormat(normalFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐


            //获得源文件中的内容
            Workbook originalWorkBook = Workbook.getWorkbook(file);
            //在原来的基础上写入数据
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file,originalWorkBook);
            //获得要添加内容的sheet
            WritableSheet sheet = writableWorkbook.getSheet(circleTimes);
            //获得之前该sheet写入到哪里了
            int temp = sheet.getRows();

            //追加数据
            if(content.size() > 0 && content != null){
                for (int i = 0; i < content.size(); i++) {
                    List<String> ele = content.get(i);
                    if(ele.size() > 0 && ele != null){
                        for (int j = 0; j < ele.size(); j++) {
                            sheet.addCell(new Label(j,temp+i,ele.get(j),wcf_center));
                        }
                    }
                }
            }
            originalWorkBook.close();
            writableWorkbook.write();
            writableWorkbook.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }
    public static void creatTableAndTitle(String SUTName){
        String path = System.getProperty("user.dir") + "\\logfile\\" + SUTName + ".xls";
        File file = new File(path);
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            for (int i = 0; i < 10; i++) {
                WritableSheet writableSheet = workbook.createSheet("sheet"+String.valueOf(i+1),i);
                WritableFont headFont = new WritableFont(WritableFont.ARIAL, 14);
                WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 12);
                /** ************以下设置几种格式的单元格************ */
                // 用于表头
                WritableCellFormat wcf_head = new WritableCellFormat(headFont);
                wcf_head.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
                wcf_head.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
                wcf_head.setAlignment(Alignment.CENTRE); // 文字水平对齐
                wcf_head.setWrap(false); // 文字是否换行
                // 用于正文居中
                WritableCellFormat wcf_center = new WritableCellFormat(normalFont);
                wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
                wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
                wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
                String[] title = {"sourcelist", "loopNumber", "MR", "mutants", "killed mutants", "numberOfkilled","residual mutants", "time(ms)"};
                for (int j = 0; j < title.length; j++) {
                    writableSheet.addCell(new Label(j,0,title[j],wcf_head));
                    writableSheet.setColumnView(j, 20);
                }
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
        LogRecorder.creatTableAndTitle("SimpleLinear");
    }
}

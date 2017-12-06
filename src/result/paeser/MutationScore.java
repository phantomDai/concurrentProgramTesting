package result.paeser;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import set.mutants.MutantSet;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static java.io.File.separator;

public class MutationScore {
    public MutationScore() {}

    public void calculateMutationScore(String SUTName){
        String path = System.getProperty("user.dir")+separator+"logfile"+separator+SUTName+".xls";
        File file = new File(path);
        if (!file.exists()){
            System.out.println("文件不存在！");
        }
        //读取excel的信息计算mutationScore
        try{
            Workbook workbook = Workbook.getWorkbook(file);//获得源文件中的信息
            Set<String> mutantSet = new HashSet<String>();//存放杀死的变异体
            //获取workbook中的sheets
            Sheet[] sheets = workbook.getSheets();
            for (int i = 0; i < sheets.length; i++) {
                //获得某一个sheet中的行数
                Sheet sheet = sheets[i];
                for (int j = 1; j < sheet.getRows(); j++) {//按行遍历所有的杀死的mutant
                    String data = sheet.getCell(4,j).getContents();
                    String[] temp = data.split(",");
                    for (int k = 0; k < temp.length; k++) {
                        mutantSet.add(temp[k]);
                    }

                }
            }

            MutantSet ms = new MutantSet(SUTName);
            double temp = mutantSet.size();
            int total = ms.size();
            double score = temp / total ;
            //向文件中输出score
            String outPath = System.getProperty("user.dir")+separator+"logfile"+separator+SUTName+"_mutationScore.txt" ;
            File outfile = new File(outPath);
            if (!outfile.exists()){
                outfile.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPath,true),"UTF-8"));
            bufferedWriter.write(String.valueOf(score));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MutationScore mutationScore = new MutationScore();
        mutationScore.calculateMutationScore("SequentialHeap");
    }

}

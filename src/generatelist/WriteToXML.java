//package generatelist;
//
//
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.io.XMLWriter;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
///**
// * 将产生的10个序列存入xml文件中
// */
//public class WriteToXML {
//    private static Element root ;
//    private static Document document ;
//    public WriteToXML() {
//        root = DocumentHelper.createElement("data") ;
//        document = DocumentHelper.createDocument(root) ;
//    }
//
//
//    /**
//     * 将数据放入document中
//     * @param i 第几个序列
//     * @param seed 种子
//     * @param length 序列的长度
//     */
//    public void writeDataToDocument(int i,int seed,int length) throws IOException{
//        String path = System.getProperty("user.dir") + "\\datafile" + "\\data_" + i + ".txt" ;
//        Generatedata generatedata = new Generatedata();
//        int[] sourcelist = generatedata.generateArray(1,length,path) ;
//        //将原始序列排序以便获得每一个值的优先级
//        QuickSort quickSort = new QuickSort();
//        sourcelist = quickSort.quickSort(sourcelist,0,sourcelist.length - 1) ;
//        int[] prioritylist = generatedata.getPriority(sourcelist);
//        for (int j = 0; j < sourcelist.length; j++) {
//            Element data = root.addElement("element");
//            data.addAttribute("id",String.valueOf(j));
//            Element priority = data.addElement("priority") ;
//            priority.setText(String.valueOf(prioritylist[j]));
//            Element item = data.addElement("item");
//            item.setText(String.valueOf(sourcelist[j]));
//        }
//        //将document中的内容写入文本文件中
//        String pathXml = System.getProperty("user.dir") + "\\datafile" + "\\data_" + i + ".xml" ;
//        XMLWriter writer = new XMLWriter(new FileWriter(new File(pathXml)));
//        writer.write(document);
//        writer.close();
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        WriteToXML wtx = new WriteToXML();
//
//        for (int i = 0; i < 10; i++) {
//            wtx.writeDataToDocument(i,i,1024);
//        }
//    }
//
//
//}

//package generatelist;
//
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
//import java.io.File;
//
///**
// * 该类主要的功能是：从xml文件中提取相关的数据,调用该类提供xml文件的绝对路径，
// * 然后在readerxml方法中输入要找的数字的id则通过get返回item和priority
// */
//public class ReaderXML {
//    private static int priority ;
//    private static int item ;
//    private static String filename ;
//
//    public static int getPriority() {
//        return priority;
//    }
//
//    public static void setPriority(int priority) {
//        ReaderXML.priority = priority;
//    }
//
//    public static int getItem() {
//        return item;
//    }
//
//    public static void setItem(int item) {
//        ReaderXML.item = item;
//    }
//
//    public ReaderXML(String myfilename) {
//        ReaderXML.filename = myfilename ;
//    }
//
//    public void readXML(int index){
//        String path = System.getProperty("user.dir") + "\\datafile" + "\\" + filename ;
//        File file = new File(filename) ;
//        if (file.exists()){
//            SAXReader saxReader = new SAXReader() ;
//            Document document = null ;
//            try {
//                document = saxReader.read(file);
//                Element rootelement = document.getRootElement();
//                String elementPath = "//element[@id='" + String.valueOf(index) + "']" ;
//                Element node = getElement(document,elementPath) ;
//                setPriority(Integer.parseInt(node.element("priority").getText()));
//                setItem(Integer.parseInt(node.element("item").getText()));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//    }
//    private Element getElement(Document document,String elementPath){
//        return (Element)document.selectSingleNode(elementPath);
//    }
//
//
//    public static void main(String[] args) {
//        String path = System.getProperty("user.dir") + "\\datafile" + "\\data_0.xml" ;
//        ReaderXML readerXML = new ReaderXML(path);
//        for (int i = 0; i < 10; i++) {
//            readerXML.readXML(i);
//            System.out.println("item:" + readerXML.getItem() + "priority:" + readerXML.getPriority());
//        }
//
//    }
//
//
//
//}

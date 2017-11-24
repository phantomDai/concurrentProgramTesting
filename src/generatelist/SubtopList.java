package generatelist;

/**
 * 产生一系列toplist的子集：1/10，3/10，5/10，7/10，10/10
 */
public class SubtopList {

    private static int N = 10 ;

    public static int getN() {
        return N;
    }

    public BinList[] generateSubList(int[] mylist){
        BinList[] binlist = new BinList[5] ;
        for (int i = 0; i <5 ; i++) {
            binlist[i] = new BinList();
        }
        for (int i = 0; i < mylist.length; i++) {
            binlist[0].put(mylist[i]);
            binlist[1].put(mylist[i]);
            binlist[2].put(mylist[i]);
            binlist[3].put(mylist[i]);
            binlist[4].put(mylist[i]);
        }
            for (int j = 0; j < mylist.length; j++) {
                if (j <  (mylist.length / 10)){
                    binlist[0].put(mylist[j]);
                    binlist[1].put(mylist[j]);
                    binlist[2].put(mylist[j]);
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (j >= mylist.length/10 &&
                        j < (mylist.length / 10 * 3)){
                    binlist[1].put(mylist[j]);
                    binlist[2].put(mylist[j]);
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (j >= (mylist.length / 10 * 3) &&
                        j < (mylist.length / 10 * 5)){
                    binlist[2].put(mylist[j]);
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (j >= (mylist.length / 10 * 5) &&
                        j < (mylist.length / 10 * 7)){
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (j >= (mylist.length / 10 * 7) &&
                        j < (mylist.length)){
                    binlist[4].put(mylist[j]);
                }
            }

        return binlist ;
    }

    public static void main(String[] args) {
        SubtopList subtoplist = new SubtopList();
        int[] toplist = {0,1,2,3,4,5,6,7,8,9} ;
        BinList[] binList = new BinList[5] ;
        for (int i = 0; i < 5; i++) {
            binList[i] = new BinList();
        }
        binList = subtoplist.generateSubList(toplist) ;

        for (int i = 0; i < 5; i++) {
            System.out.println("第" + i + "个序列");
            for (int j = 0; j < binList[i].list.size(); j++) {
                System.out.print(binList[i].list.get(j) + " ,");
            }
            System.out.println("");
        }



    }







}

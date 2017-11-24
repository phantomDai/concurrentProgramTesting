package generatelist;

/**
 * 产生一系列原始序列的子集：1/10，3/10，5/10，7/10，10/10
 */
public class SubList {
    private static int N = 10 ;

    public static int getN() {
        return N;
    }

    public BinList[] generateSubList(int[] mylist){
        BinList[] binlist = new BinList[5] ;
        for (int i = 0; i <5 ; i++) {
            binlist[i] = new BinList();
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < mylist.length; j++) {
                int temp = j + (mylist.length * i) ;
                if (temp < (mylist.length + mylist.length/10)){
                    binlist[0].put(mylist[j]);
                    binlist[1].put(mylist[j]);
                    binlist[2].put(mylist[j]);
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (temp >= mylist.length + mylist.length/10 &&
                        temp < (mylist.length + mylist.length / 10 * 3)){
                    binlist[1].put(mylist[j]);
                    binlist[2].put(mylist[j]);
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (temp >= (mylist.length + mylist.length / 10 * 3) &&
                        temp < (mylist.length + mylist.length / 10 * 5)){
                    binlist[2].put(mylist[j]);
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (temp >= (mylist.length + mylist.length / 10 * 5) &&
                        temp < (mylist.length + mylist.length / 10 * 7)){
                    binlist[3].put(mylist[j]);
                    binlist[4].put(mylist[j]);
                }
                if (temp >= (mylist.length + mylist.length / 10 * 7) &&
                        temp < (mylist.length * 2)){
                    binlist[4].put(mylist[j]);
                }
            }
        }
        return binlist ;
    }

    public static void main(String[] args) {
        SubList sublist = new SubList();
        int[] mylist = {0,1,2,3,4,5,6,7,8,9} ;
        BinList[] binList = new BinList[5] ;
        for (int i = 0; i < 5; i++) {
            binList[i] = new BinList();
        }
        binList = sublist.generateSubList(mylist) ;

        for (int i = 0; i < 5; i++) {
            System.out.println("第" + i + "个序列");
            for (int j = 0; j < binList[i].list.size(); j++) {
                System.out.print(binList[i].list.get(j) + " ,");
            }
            System.out.println("");
        }



    }
}

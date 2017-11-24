package metamorphic.relations;

public class GetMetamorphicRelation {
    private static int ID ;//蜕变关系的ID
    public GetMetamorphicRelation(int id) {
        GetMetamorphicRelation.ID = id ;
    }

    public MetamorphicRelations getMR(){
        switch (GetMetamorphicRelation.ID){
            case 1 :
                MR1 mr1 = new MR1();return mr1;
            case 2 :
                MR2 mr2 = new MR2();return mr2;
            case 3 :
                MR3 mr3 = new MR3();return mr3;
            case 4 :
                MR4 mr4 = new MR4();return mr4;
            case 5 :
                MR5 mr5 = new MR5();return mr5;
            case 6 :
                MR6 mr6 = new MR6();return mr6;
            case 7 :
                MR7 mr7 = new MR7();return mr7;
            case 8 :
                MR8 mr8 = new MR8();return mr8;
            case 9 :
                MR9 mr9 = new MR9();return mr9;
            case 10 :
                MR10 mr10 = new MR10();return mr10;
            case 11 :
                MR11 mr11 = new MR11();return mr11;
            case 12 :
                MR12 mr12 = new MR12();return mr12;
            case 13 :
                MR13 mr13 = new MR13();return mr13;
            case 14 :
                MR14 mr14 = new MR14();return mr14;
            case 15 :
                MR15 mr15 = new MR15();return mr15;
            case 16 :
                MR16 mr16 = new MR16();return mr16;
            case 17 :
                MR17 mr17 = new MR17();return mr17;
            case 18 :
                MR18 mr18 = new MR18();return mr18;
            case 19 :
                MR19 mr19 = new MR19();return mr19;
            case 20 :
                MR20 mr20 = new MR20();return mr20;
            case 21 :
                MR21 mr21 = new MR21();return mr21;
            case 22 :
                MR22 mr22 = new MR22();return mr22;
            case 23 :
                MR23 mr23 = new MR23();return mr23;
            case 24 :
                MR24 mr24 = new MR24();return mr24;
            case 25 :
                MR25 mr25 = new MR25();return mr25;
            default:
                System.out.println("id不符合规范，请重新输入id");


        }
        return null;
    }

    public static void main(String[] args) {
        GetMetamorphicRelation getmr = new GetMetamorphicRelation(1);
        MetamorphicRelations mr = getmr.getMR();
        int[] mylist = {1,2,3,4,5,6,7,8,9,10};
    }





}

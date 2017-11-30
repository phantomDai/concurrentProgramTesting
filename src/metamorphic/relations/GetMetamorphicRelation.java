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
                MR10_1 mr1011 = new MR10_1();return mr1011;
            case 11 :
                MR10_2 mr11 = new MR10_2();return mr11;
            case 12 :
                MR10_3 mr12 = new MR10_3();return mr12;
            case 13 :
                MR11_1 mr13 = new MR11_1();return mr13;
            case 14 :
                MR11_2 mr14 = new MR11_2();return mr14;
            case 15 :
                MR11_3 mr15 = new MR11_3();return mr15;
            case 16 :
                MR12 mr16 = new MR12();return mr16;
            case 17 :
                MR13 mr17 = new MR13();return mr17;
            case 18 :
                MR14 mr18 = new MR14();return mr18;
            case 19 :
                MR15 mr19 = new MR15();return mr19;
            case 20 :
                MR16_1 mr20 = new MR16_1();return mr20;
            case 21 :
                MR16_2 mr21 = new MR16_2();return mr21;
            case 22 :
                MR17 mr22 = new MR17();return mr22;
            case 23 :
                MR18_1 mr23 = new MR18_1();return mr23;
            case 24 :
                MR18_2 mr24 = new MR18_2();return mr24;
            case 25 :
                MR19_1 mr25 = new MR19_1();return mr25;
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

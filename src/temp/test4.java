package temp;

import java.text.DecimalFormat;

public class test4 {
    public test4() { }

    public static void main(String[] args) {
        double a = 135;
        int b = 231;
        double temp = 0 ;
        temp = a / b ;
        DecimalFormat df = new DecimalFormat("0.000");
        System.out.println(temp);

    }


}

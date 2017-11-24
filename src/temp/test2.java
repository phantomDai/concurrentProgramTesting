package temp;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
public class test2 {
    public test2() { }

    public String isConformToRequest(int[] list,int min,int next){
        int i = min ;
        int j = next ;
        boolean flag = false;
        for (int k = next; k < list.length-1; k++) {
            if ((list[i] * list[k]) % 4 == 0){
                flag = true;
                int temp = 0;
                temp = list[next];
                list[next] = list[k];
                list[k] = temp;
                i = i + 1;
                isConformToRequest(list,i,i+1);

            }else if (k != list.length-2){
                continue;
            }else {
                flag = false;
            }
        }
        if (flag)
            return "Yrs";
        else
            return "No" ;

    }





    public static void main(String[] args) {
            Result result = JUnitCore.runClasses(test1.class);
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(result.wasSuccessful());
        }

}

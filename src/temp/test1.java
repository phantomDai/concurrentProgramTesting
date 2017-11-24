package temp;

import org.junit.Test;

import static org.junit.Assert.*;

public class test1 {
    public test1() { }

    @Test
    public void testAdd() {
        //test data
        String str= "Junit is working fine";

        //check for equality
        assertEquals("Juni is working fine", str);
    }

    @Test
    public void test1(){
        int num = 5  ;
        assertFalse(num > 6 );
    }
    @Test
    public void test2(){
        int[] a = {1,2,3,4,5} ;
        int[] b = {1,2,3,4,6} ;
        assertArrayEquals(a,b);
    }


}

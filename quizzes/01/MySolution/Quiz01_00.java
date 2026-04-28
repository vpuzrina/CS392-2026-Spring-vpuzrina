//
// HX: 10 points
//

import MyLibrary.FnList.*;
import MyLibrary.FnA1sz.*;

public class Quiz01_00 {
    public static void main(String[] args) {
        FnList<Integer> fnListIntegerObj = new FnList<Integer>();
        FnA1sz<Integer> fnA1szIntegerObj = new FnA1sz<Integer>(new Integer[] {1, 2, 3});

        System.out.println("FnList length = " + fnListIntegerObj.length());
        System.out.println("FnA1sz length = " + fnA1szIntegerObj.length());
        return;
    }
}

//
// HX: 10 points
//

import MyLibrary.FnList.*;
import MyLibrary.FnA1sz.*;
import MyLibrary.MyStack.*;
import MyLibrary.MyQueue.*;
import MyLibrary.MyMap.*;
import MyLibrary.MyPQueue.*;
import MyLibrary.MyBST.*;

public class Quiz01_00 {
    public static void main(String[] args) {
        FnList<Integer> fnListIntegerObj = new FnList<Integer>();
        FnA1sz<Integer> fnA1szIntegerObj = new FnA1sz<Integer>(new Integer[] {1, 2, 3});
        MyStackArray<Integer> myStackArrayObj = new MyStackArray<Integer>(8);
        MyStackList<Integer> myStackListObj = new MyStackList<Integer>();
        MyQueueArray<Integer> myQueueArrayObj = new MyQueueArray<Integer>(8);
        MyQueueList<Integer> myQueueListObj = new MyQueueList<Integer>();
        MyMapHashProbe<String, Integer> myMapHashProbeObj =
            new MyMapHashProbe<String, Integer>();
        MyMapHashChain<String, Integer> myMapHashChainObj =
            new MyMapHashChain<String, Integer>();
        MyPQueueArray<Integer> myPQueueArrayObj = new MyPQueueArray<Integer>();
        MyBST<String, Integer> myBstObj = new MyBST<String, Integer>();

        System.out.println("FnList length = " + fnListIntegerObj.length());
        System.out.println("FnA1sz length = " + fnA1szIntegerObj.length());
        return;
    }
}

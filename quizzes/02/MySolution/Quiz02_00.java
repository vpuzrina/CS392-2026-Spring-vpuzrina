//
// HX: 20 points
//
import MyLibrary.FnList.*;
import MyLibrary.FnA1sz.*;
import MyLibrary.MyStack.*;
import MyLibrary.MyQueue.*;
import MyLibrary.FnTuple.*;
import MyLibrary.FnStrn.*;
import MyLibrary.LnList.*;
import MyLibrary.MyMap.*;
import MyLibrary.MyPQueue.*;
import MyLibrary.MyBST.*;
import MyLibrary.LnStrm.*;
import MyLibrary.FnGtree.*;

public class Quiz02_00 {
    /*
     Please give a description of your MyLibrary
     What classes have you implemented? For each class
     you have implemented in MyLibrary, please create an
     object of that class as follows:
     */
    public static void main (String[] args) {
	FnList<Integer> fnListObj = new FnList<Integer>();
	FnA1sz<Integer> fnA1szObj = new FnA1sz<Integer>(new Integer[] {1, 2, 3});
	MyStackArray<Integer> myStackArrayObj = new MyStackArray<Integer>(10);
	MyStackList<Integer> myStackListObj = new MyStackList<Integer>();
	MyQueueArray<Integer> myQueueArrayObj = new MyQueueArray<Integer>(10);
	MyQueueList<Integer> myQueueListObj = new MyQueueList<Integer>();
	FnTupl2<Integer, String> fnTupl2Obj = new FnTupl2<Integer, String>(1, "x");
	FnStrn fnStrnObj = new FnStrn("hello");
	LnList<Integer> lnListObj = new LnList<Integer>();
	MyMapHashChain<String, Integer> myMapHashChainObj = new MyMapHashChain<String, Integer>();
	MyMapHashProbe<String, Integer> myMapHashProbeObj = new MyMapHashProbe<String, Integer>();
	MyPQueueArray<Integer> myPQueueArrayObj = new MyPQueueArray<Integer>();
	MyBST<Integer, String> myBSTObj = new MyBST<Integer, String>();
	LnStrm<Integer> lnStrmObj = new LnStrm<Integer>(1);
	FnGtreeSUtil fnGtreeSUtilObj = new FnGtreeSUtil();
	return;
    }
} // end of [class Quiz01_00{...}]

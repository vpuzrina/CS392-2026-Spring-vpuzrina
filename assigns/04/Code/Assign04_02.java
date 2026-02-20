/*
HX-2026-02-13: 20 points
*/
import MyLibrary.FnList.*;
import MyLibrary.FnStrn.*;

public class Assign04_02 {
    static FnStrn
	FnList$FnStrn_concate(FnList<FnStrn> xs) {
		return FnList.foritm( 
			xs, 
			new FnStrn(""), 
			(acc,s ) -> FnStrn.concat(acc,s) 
		); 
    }

    public static void main(String[] argv) { 
		FnList<FnStrn> xs = 
			FnList.cons(new FnStrn("a"), FnList.cons(new FnStrn("ab"), FnList.cons(new FnStrn("dif"), FnList.nil()))); 
		FnStrn result = FnList$FnStrn_concate(xs);
		System.err.println(result);
    }
} // end of [public class Assign04_02{...}]

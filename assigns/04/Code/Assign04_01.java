/*
HX-2026-02-13: 10 points
*/
import MyLibrary.FnList.*;
import MyLibrary.FnStrn.*;

public class Assign04_01 { 
	static class State { 
		FnList<Character> st=FnList.<Character>nil(); 
		boolean ok =true; 
	}
    static boolean balencedq(String text) { 
		final State s = new State(); 

		FnStrn.forall(text,(c) -> { 
			if (!s.ok) return false; 

			if (c == '(' || c == '['  || c== '{') { 
				s.st = FnList.cons (c, s.st); 
				return true; 
			} 

			if (FnList.isNil(s.st)) { 
				s.ok =false; 
				return false; 
			} 
			char top = s.st.head(); 
			s.st =s.st.tail(); 
			if ((c== ')' && top != '(') || (c== ']' && top != '[') || (c== '}' && top != '{')) { 
				s.ok=false; 
				return false; 
			} 
			return true; 
		}); 
		return s.ok&& FnList.isNil(s.st); 

    }

    public static void main(String[] argv) {
	System.err.println(balencedq("{{]](]}})}}[}"));
	System.err.println(balencedq("{{}](())}}[[]"));
	System.err.println(balencedq("[][()({}")); 
	System.err.println(balencedq("{}}{))){}[]")); 
	System.err.println(balencedq("{{}{}(()"));

    }
} // end of [public class Assign04_01{...}]

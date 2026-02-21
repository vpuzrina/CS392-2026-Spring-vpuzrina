/*
HX-2026-02-05: 10 points
*/
import Library00.FnList.*;
public class Assign03_02 {
    static boolean balencedq(String text) {
		FnList<Character> stack = FnListSUtil.nil();
		for (int i=0; i < text.length();i++){ 
			char c =text.charAt(i);
			if (c == '(' ||c=='[' || c == '{') { 
				stack = FnListSUtil.cons(c, stack); 
			}
			else if (c == ')' || c == ']' || c == '}') { 
				if (stack.nilq()) return false; 
				char k = stack.hd();
				stack = stack.tl(); 
				if ((c==')' && k!='(') || (c == ']' && k!='[')||(c == '}' && k!='{')) { return false; 
				}
			}

		}
		return stack.nilq();
    }

    public static void main(String[] argv) {
	System.out.println(balencedq("({()[({})]})")); 
	System.out.println(balencedq("({()[({})])}")); 
	System.out.println(balencedq("(){}[]")); 
	System.out.println(balencedq("({]{")); 
    }
}

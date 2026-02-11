/*
HX-2026-02-05: 10 points
*/
import Library00.FnList.FnList;
public class Assign03_02 {
    static boolean balencedq(String text) {
		FnList<Character> stack= FnList.nil;
		for (int i=0; i < text.length();i++){ 
			char c =text.charAt(i);
			if (c == '(' ||c=='[' || c == '{') { 
				stack = stack.cons(c); 
			}
			else { 
				if (FnList.isNil(stack)) return false; 
				char k = stack.getHead();
				stack =stack.getTail(); 
				if ((c==')' && k!='(') || (c == ']' && k!='[')||(c == '}' && k!='{')) { return false; 
				}
			}	

		}
		return FnList.isNil(stack);
    }

    public static void main(String[] argv) {
	System.out.println(balencedq("({()[({})]})")); 
	System.out.println(balencedq("({()[({})])}")); 
	System.out.println(balencedq("(){}[]")); 
	System.out.println(balencedq("({]{")); 
    }
}

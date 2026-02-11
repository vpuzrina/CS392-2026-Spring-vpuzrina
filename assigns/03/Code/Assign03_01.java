/*
HX-2026-02-05: 10 points
*/
public class Assign03_01 {
    static int f91_extra(int n, int m) {
	    if (n > 100) { 
            if (m==0) { 
	            return n-10; 
            }else{  
                return f91_extra(n-10,m-1); 
            }


    } else {
	    return f91_extra(n+11,m + 1);
    }
    }
    
    static int f91(int n) { 
        return f91_extra(n,0);
    }

    public static void main(String[] argv) {
        for (int i =90; i<=110; i++) { 
            System.out.println("f91(" + i + ")=" + f91(i)); 
        }
    }
}

import java.util.Arrays;

public class Assign02_03 {
    public static boolean solve_3sum(Integer[] A) {
		Arrays.sort(A); 
		int n =A.length; 
		for (int k=0;k< n; k++) { 
			int i = 0; 
			int j = n -1; 
			while (i < j) { 
				if (i==k) { 
					i++; 
				} else if (j== k) { 
					j --; 
				} else { 
					int sum = A[i] +A[j]; 
					if (sum == A[k]) { 
						return true; 
					} else if (sum < A[k]) { 
						i++; 
					} else { 
						j--; 
					} 
				} 
			} 
		} 
		return false; 
    }
    public static void main(String[] argv) {
	System.out.println(solve_3sum(new Integer[] {2,4,6,8})); 
	System.out.println(solve_3sum(new Integer[] {3,5,9,14})); 
	System.out.println(solve_3sum(new Integer[] {1,2,3,4}));
    }
}

import java.util.HashMap;

public class Quiz01_02 {
    public static boolean solve_3prod(Integer[] A) {
		HashMap<Integer, Integer> count=new HashMap<>(); 
		for (int x :A) {
			count.put(x, count.getOrDefault(x,0)+ 1); 
		} 
		int n = A.length; 
		for (int i = 0; i <n;i++) { 
			for (int j = i +1; j < n; j++) { 
				int prod = A[i] * A[j]; 
				if (count.containsKey(prod)) { 
					int needed = 1;
					if (A[i]==prod) needed++; 
					if (A[j]==prod) needed++; 
					if (count.get(prod) >= needed) {
						return true;
					}
	
				}
			}
		} 
		return false; 
    } 
	// explanation: building hash map takes O(n) - there are O(n^2) which pairs to (i,j) and each look up on the hash map is approximately O(1) => total time = O(n^2)
    public static void main(String[] argv) {
		System.out.println(solve_3prod(new Integer[]{1,2,3}));
		System.out.println(solve_3prod(new Integer[]{1,0,1})); 
		System.out.println(solve_3prod(new Integer[]{4,7,3}));
		System.out.println(solve_3prod(new Integer[]{-1,-2,3}));
		System.out.println(solve_3prod(new Integer[]{1,-2,3}));
    }
}

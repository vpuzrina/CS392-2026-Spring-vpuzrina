//
// HX: For testing Quiz01_05
//
import Library00.FnList.*;
public class Quiz01_05_test {
    public static void main (String args[]) {
        FnList<Integer> xs =new FnList<Integer>();
        for (int i =999;i >=0; i--){
            xs=new FnList<Integer>(i,xs);
        }
        FnList<Integer> sorted = 
            Quiz01_05.someRevStableSort(
                xs,
                (a,b) -> Integer.compare(a%2, b%2)
            );
        sorted.foritm1(x-> System.out.print(x + " "));
        System.out.println();
    }
}

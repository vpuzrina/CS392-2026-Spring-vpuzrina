//
// HX-2026-04-21: 50 points
//
// Please see lectures/lecture-04-21 for an
// example using DFirstEnumerate/BFirstEnumerate
//
// Some "hard" Sudoku puzzles can be
// found here: https://sudoku.com/hard/.
// You are asked to use DFirstEnumerate and BFirstEnumerate
// in FnGtree to solve Sudoku puzzles. Your solution should
// be able to solve "hard" Sudoku puzzles effectively.
//
import Library00.FnList.*;
import Library00.LnStrm.*;
import Library00.FnGtree.*;

class Sudoku {
    static final int N = 81;

    final int[] g;

    Sudoku(int[] g0) {
        g = new int[N];
        System.arraycopy(g0, 0, g, 0, N);
    }

    boolean isSolved() {
        for (int i = 0; i < N; i++) {
            if (g[i] == 0) return false;
        }
        return isValid();
    }

    boolean isValid() {
        for (int i = 0; i < N; i++) {
            if (g[i] != 0 && !validAt(i)) return false;
        }
        return true;
    }

    private boolean validAt(int idx) {
        int v = g[idx];
        int r = idx / 9;
        int c = idx % 9;
        int br = (r / 3) * 3;
        int bc = (c / 3) * 3;
        for (int k = 0; k < 9; k++) {
            int j = r * 9 + k;
            if (j != idx && g[j] == v) return false;
        }
        for (int k = 0; k < 9; k++) {
            int j = k * 9 + c;
            if (j != idx && g[j] == v) return false;
        }
        for (int dr = 0; dr < 3; dr++) {
            for (int dc = 0; dc < 3; dc++) {
                int j = (br + dr) * 9 + (bc + dc);
                if (j != idx && g[j] == v) return false;
            }
        }
        return true;
    }

    private int countChoices(int idx) {
        int n = 0;
        for (int d = 1; d <= 9; d++) {
            g[idx] = d;
            if (validAt(idx)) n++;
            g[idx] = 0;
        }
        return n;
    }

    int bestEmpty() {
        int best = -1;
        int bestN = 10;
        for (int i = 0; i < N; i++) {
            if (g[i] != 0) continue;
            int n = countChoices(i);
            if (n == 0) return -2;
            if (n < bestN) {
                bestN = n;
                best = i;
                if (bestN == 1) break;
            }
        }
        return best;
    }

    boolean canPlace(int idx, int d) {
        g[idx] = d;
        boolean ok = validAt(idx);
        g[idx] = 0;
        return ok;
    }

    Sudoku place(int idx, int d) {
        int[] h = new int[N];
        System.arraycopy(g, 0, h, 0, N);
        h[idx] = d;
        return new Sudoku(h);
    }
}

final class SudokuNode implements FnGtree<Sudoku> {
    private final Sudoku bd;

    SudokuNode(Sudoku b) {
        bd = b;
    }

    public Sudoku value() {
        return bd;
    }

    public FnList<FnGtree<Sudoku>> children() {
        if (bd.isSolved()) return FnListSUtil.nil();
        int cell = bd.bestEmpty();
        if (cell < 0) return FnListSUtil.nil();
        FnList<FnGtree<Sudoku>> res = FnListSUtil.nil();
        for (int d = 9; d >= 1; d--) {
            if (bd.canPlace(cell, d)) {
                Sudoku next = bd.place(cell, d);
                res = FnListSUtil.cons(new SudokuNode(next), res);
            }
        }
        return res.reverse();
    }
}

public class Assign11_01 {
    public LnStrm<Sudoku> Soduku_dfs_solve(Sudoku puzzle) {
        SudokuNode root = new SudokuNode(puzzle);
        return FnGtreeSUtil.DFirstEnumerate(root).filter0((s) -> s.isSolved());
    }

    public LnStrm<Sudoku> Soduku_bfs_solve(Sudoku puzzle) {
        SudokuNode root = new SudokuNode(puzzle);
        return FnGtreeSUtil.BFirstEnumerate(root).filter0((s) -> s.isSolved());
    }

    static int[] hardPuzzle() {
        return new int[] {
            8, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 3, 6, 0, 0, 0, 0, 0,
            0, 7, 0, 0, 9, 0, 2, 0, 0,
            0, 5, 0, 0, 0, 7, 0, 0, 0,
            0, 0, 0, 0, 4, 5, 7, 0, 0,
            0, 0, 0, 1, 0, 0, 0, 3, 0,
            0, 0, 1, 0, 0, 0, 0, 6, 8,
            0, 0, 8, 5, 0, 0, 0, 1, 0,
            0, 9, 0, 0, 0, 0, 4, 0, 0
        };
    }

    public static void main(String[] args) {
        Assign11_01 sol = new Assign11_01();
        Sudoku p = new Sudoku(hardPuzzle());

        LnStrm<Sudoku> dfs = sol.Soduku_dfs_solve(p);
        dfs.foritm0((s) -> {
            int k = sol.count81(s);
            System.out.println("dfs solution sum81=" + k);
        });

        Sudoku p2 = new Sudoku(hardPuzzle());
        LnStrm<Sudoku> bfs = sol.Soduku_bfs_solve(p2);
        bfs.foritm0((s) -> {
            int k = sol.count81(s);
            System.out.println("bfs solution sum81=" + k);
        });

        return;
    }

    int count81(Sudoku s) {
        int t = 0;
        for (int i = 0; i < 81; i++) t += s.g[i];
        return t;
    }
}

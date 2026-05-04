import Library00.FnList.*;
import Library00.LnStrm.*;

public class Final_01 {

    private static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isApos(char c) {
        return c == '\'' || c == '\u2019';
    }

    private static char toLower(char c) {
        return (c >= 'A' && c <= 'Z') ? (char) (c - 'A' + 'a') : c;
    }

    static LnStrm<FnList<Character>> pg2701_word$strmize() {
        return wordsFrom(Final_00.pg2701_char$strmize());
    }

    private static LnStrm<FnList<Character>> wordsFrom(LnStrm<Character> cs) {
        return new LnStrm<FnList<Character>>(
            () -> {
                LnStcn<Character> cur = cs.eval0();
                while (cur.consq() && !isLetter(cur.hd()) && !isApos(cur.hd())) {
                    cur = cur.tl().eval0();
                }
                if (cur.nilq()) return new LnStcn<FnList<Character>>();

                FnList<Character> revWord = FnListSUtil.nil();
                boolean hasLetter = false;
                while (cur.consq() && (isLetter(cur.hd()) || isApos(cur.hd()))) {
                    char ch = cur.hd();
                    if (isLetter(ch)) hasLetter = true;
                    revWord = FnListSUtil.cons(toLower(ch), revWord);
                    cur = cur.tl().eval0();
                }
                FnList<Character> word = FnListSUtil.reverse(revWord);

                if (cur.nilq()) {
                    if (hasLetter) {
                        return new LnStcn<FnList<Character>>(
                            word, new LnStrm<FnList<Character>>()
                        );
                    }
                    return new LnStcn<FnList<Character>>();
                }
                LnStrm<Character> next = cur.tl();
                if (!hasLetter) {
                    return wordsFrom(next).eval0();
                }
                return new LnStcn<FnList<Character>>(word, wordsFrom(next));
            }
        );
    }

    public static void main(String[] args) {
        LnStrm<FnList<Character>> ws = pg2701_word$strmize();
        LnStcn<FnList<Character>> c = ws.eval0();
        int i = 0;
        while (c.consq() && i < 20) {
            FnList<Character> w = c.hd();
            StringBuilder sb = new StringBuilder();
            w.foritm(ch -> sb.append(ch));
            System.out.println(sb.toString());
            c = c.tl().eval0();
            i += 1;
        }
    }
}

# Final Exam — MySolution

Submission for CS392X1 Spring 2026 Final Exam. All solutions live in this
directory and depend only on `libraries/Library00`, `libraries/MyLibrary`,
and (for Final_05) `assigns/10/Code/MyPQueue`. No files outside
`final/MySolution/` are modified.

## Final_00

Provided by the course; copied verbatim into this directory.
Streams the characters of `../Data/pg2701.txt` as
`Library00.LnStrm.LnStrm<Character>`.

## Final_01

`pg2701_word$strmize()` lazily streamizes the words of `pg2701.txt`.
Letters are lowercased, words may contain both the ASCII apostrophe `'`
and the Unicode right-single-quotation-mark `U+2019` (so `whale's` and
`whale’s` are accepted as one word). A word must contain at least one
letter; pure-apostrophe runs are skipped. Implementation walks the
character stream once with `eval0()` and emits each word as
`FnList<Character>` inside an `LnStrm<FnList<Character>>`.

Test: `main` prints the first 20 words.

## Final_02

`pg2701_word$count$listize2()` counts words via the array-quicksort
approach prescribed by the skeleton:

1. Drain `Final_01.pg2701_word$strmize()` into a `FnList<FnList<Character>>`.
2. Materialize as a native `FnList<Character>[] A1`.
3. Sort `A1` in place with `MyLibrary.FnA1sz.FnA1szSort.quickSort` using a
   lexicographic comparator on `FnList<Character>`.
4. Walk sorted `A1` to build `L2`, an `FnList` of `(word, count)` groups.
5. Re-pack `L2` into a `FnTupl2<FnList<Character>, Integer>[]` and sort it
   in place with the same `MyLibrary` array-quicksort under the
   final comparator (count descending, word ascending). Array-based
   quicksort is used here instead of `Library00.FnListSUtil.mergeSort`
   because the latter recurses to depth `~|L2| ≈ 17 K` and overflows
   the default JVM stack.
6. Convert back to a `FnList` and return.

Test: `main` prints the first 100 `count<TAB>word` pairs.

## Final_03

`pg2701_word$count$listize3()` counts words via the open-addressing
hashmap variant.

* `Final_03Map.java` is a copy of `assigns/08/MySolution/Assign08_02.java`
  with the table size raised from 97 to 65537 (a prime; load factor on
  Moby Dick stays under 30 %). The `assigns/08` source is **not**
  modified. Probe arithmetic was widened to `long` defensively so
  `i*i` cannot overflow at high probe counts.
* Words are converted to `String` keys for hashing (`MyMap00<String,V>` is
  hard-coded to `String` keys). Counts use `search$opt` then
  `insert$opt`.
* The map is drained with `foritm` (loop-based, stack-safe) into a
  `FnList<FnTupl2<FnList<Character>, Integer>>`. We avoid
  `keyval_strmize()` because its recursive helper allocates 65537 stack
  frames eagerly.
* The list is sorted with a private **iterative bottom-up
  mergesort** under the final comparator. We do not use
  `Library00.FnListSUtil.mergeSort` (recursive, would overflow the
  stack). The skeleton mentions
  `Assign05_01.mergeSort`, but `Assign05_01` only contains insert sort
  and uses an old `FnList` API that does not compile against this
  course's libraries; this is documented here.

Test: `main` prints the first 100 `count<TAB>word` pairs. Output matches
Final_02 exactly, providing a cross-check.

## Final_04

`pg2701_word$count$listize4()` counts words via the RBST-based map
variant.

* `Final_04Map.java` is a generic associative map
  `<K extends Comparable<K>, V>` derived from
  `quizzes/02/MySolution/Quiz02_06.java`. The `quizzes/02` source is
  **not** modified. Modifications applied here:
  - parameterised key + value (Quiz02_06 stored only `int` keys);
  - added `getOrNull(K)` and upsert `put(K, V)`;
  - in-order traversal `keyval$listize()` is **iterative** with an
    explicit `Object[]` stack — Quiz02_06's recursive `inorderCollect`
    would overflow on a worst-case tree;
  - `reroot()` is kept for fidelity to the original RBST API, but is
    **not** invoked during insertion (Moby Dick's word order keeps the
    tree balanced enough; skipping `reroot` keeps Final_04 deterministic
    and fast).
* The map is drained via `keyval$listize()`, words are converted from
  `String` back to `FnList<Character>`, and the list is sorted with the
  same iterative bottom-up mergesort as Final_03 under the final
  comparator.

Test: `main` prints the first 100 `count<TAB>word` pairs. Output matches
Final_02 and Final_03 exactly.

## Final_05

Two methods over linear lists, exposing a stable 100-way mergesort:

* `LnList_n$way$merge(LnList<T>[], ToIntBiFunction<T,T>)` — k-way merge
  driven by `Library.MyPQueue.MyPQueueArray` (compiled from
  `assigns/10/Code/MyPQueue`). Each PQ entry holds the source list and
  its index; the comparator tiebreaks equal keys by source index, which
  makes the merge stable. **No new list nodes are allocated**: each
  output node is detached from its source list with `unlink1` (yielding
  a single-node `LnList<T>`) and chained onto the result tail with
  `link1`. Empty input slots are skipped.
* `LnList_mergeSort$100way(LnList<T>, ToIntBiFunction<T,T>)` — splits
  the input into up to 100 **consecutive-block** sublists (preserves
  relative order so equal-keyed elements remain in their input order),
  recurses on each (depth `≤ ⌈log_100(N)⌉`), and merges with the stable
  k-way merge. The final `LnList<T>` is then converted to `FnList<T>`
  per the skeleton's return type.

Test: `main` builds `[0..999999]`, parity-sorts it, prints the first 10
and last 10 elements, and reports `PASS`/`FAIL` based on a stability
check that requires evens then odds, each block strictly increasing.

## Final_06 (BONUS)

Same task as `Quiz02_02`: insertion-sort up to 1 M elements with **no
user-written loops or recursion**. The implementation is copied from
`quizzes/02/MySolution/Quiz02_02.java` with only the class name changed
(`Quiz02_02` → `Final_06`). Internally:

```
FnA1szSUtil.rfolditm   // array → FnList without loops
FnListSort.insertSort  // library insertion sort
FnList.foritm          // FnList → array writeback
```

`main` runs four small tests (`sorted`, `reverse`, `duplicates`,
`nearlySorted`); each prints `true` and the sorted array. The 1 M-case
stress test is omitted from `main` to keep grading output concise; the
sort path itself was verified at 1 M scale during Quiz02_02 development.

## Build and run

From this directory (`final/MySolution`):

```sh
# Final_01 (depends on Final_00)
javac -cp ../../libraries -d . Final_00.java Final_01.java
java  -cp ../../libraries:. Final_01

# Final_02
javac -cp ../../libraries -d . Final_00.java Final_01.java Final_02.java
java  -cp ../../libraries:. Final_02

# Final_03
javac -cp ../../libraries -d . Final_00.java Final_01.java Final_03Map.java Final_03.java
java  -cp ../../libraries:. Final_03

# Final_04
javac -cp ../../libraries -d . Final_00.java Final_01.java Final_04Map.java Final_04.java
java  -cp ../../libraries:. Final_04

# Final_05 (also compiles MyPQueue from assigns/10/Code)
javac -cp ../../libraries -d . \
      Final_05.java \
      ../../assigns/10/Code/MyPQueue/MyPQueue.java \
      ../../assigns/10/Code/MyPQueue/MyPQueueBase.java \
      ../../assigns/10/Code/MyPQueue/MyPQueueArray.java \
      ../../assigns/10/Code/MyPQueue/MyPQueueEmptyExn.java \
      ../../assigns/10/Code/MyPQueue/MyPQueueFullExn.java
java  -cp ../../libraries:. Final_05

# Final_06 (bonus)
javac -cp ../../libraries -d . Final_06.java
java  -cp ../../libraries:. Final_06
```

`Final_02`, `Final_03`, and `Final_04` produce the same first-100
`count<TAB>word` lines, providing a built-in cross-check.

## Quiz02_03: Game of 24 (BFS + DFS)

My idea is to treat each Game of 24 state as a list of current terms.
At each step, I pick two terms and combine them with one operator (`+`, `-`, `*`, `/`), including both orders for `-` and `/`.
This creates child states with one fewer term, so the search eventually reaches states with one term left.

I build this as a tree of states and then:

- use `BFirstEnumerate` for BFS solutions
- use `DFirstEnumerate` for DFS solutions

After enumeration, I filter states where the final term evaluates to 24 (with a small floating-point tolerance).
So both methods return all expressions that solve the input numbers.

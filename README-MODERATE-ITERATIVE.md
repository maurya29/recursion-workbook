# Recursion - Moderate Iterative Questions and Solutions

## Topic 10: Recursion

#### 13. Combination Sum - [LeetCode 39](https://leetcode.com/problems/combination-sum/)

Given distinct candidate numbers and a target, return all unique combinations where chosen numbers sum to target. A candidate may be chosen unlimited times.

```java
public List<List<Integer>> q13CombinationSumOptimized(int[] candidates, int target) {
  Arrays.sort(candidates);
  List<List<Integer>> answer = new ArrayList<>();
  Deque<StateQ13Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ13Optimized(0, target, new ArrayList<>()));

  while (!stack.isEmpty()) {
    StateQ13Optimized state = stack.pop();
    if (state.remaining == 0) {
      answer.add(state.path);
      continue;
    }
    for (int i = candidates.length - 1; i >= state.start; i--) {
      if (candidates[i] > state.remaining) continue;
      List<Integer> next = new ArrayList<>(state.path);
      next.add(candidates[i]);
      stack.push(new StateQ13Optimized(i, state.remaining - candidates[i], next));
    }
  }

  return answer;
}

private static class StateQ13Optimized {
  int start;
  int remaining;
  List<Integer> path;
  StateQ13Optimized(int start, int remaining, List<Integer> path) {
    this.start = start;
    this.remaining = remaining;
    this.path = path;
  }
}
```

#### 14. N-Queens - [LeetCode 51](https://leetcode.com/problems/n-queens/)

Given n, return all distinct solutions to the n-queens puzzle, placing n queens on an n x n chessboard so no two queens attack each other.

```java
public List<List<String>> q14SolveNQueensOptimized(int n) {
  List<List<String>> answer = new ArrayList<>();
  int[] queenCol = new int[n];
  Arrays.fill(queenCol, -1);
  boolean[] cols = new boolean[n];
  boolean[] diag1 = new boolean[2 * n];
  boolean[] diag2 = new boolean[2 * n];
  int row = 0;
  int col = 0;

  while (row >= 0) {
    boolean placed = false;
    for (; col < n; col++) {
      int d1 = row - col + n;
      int d2 = row + col;
      if (cols[col] || diag1[d1] || diag2[d2]) continue;
      queenCol[row] = col;
      cols[col] = diag1[d1] = diag2[d2] = true;
      row++;
      col = 0;
      placed = true;
      break;
    }

    if (row == n) {
      answer.add(buildQ14Optimized(queenCol));
      row--;
      col = queenCol[row] + 1;
      removeQ14Optimized(row, queenCol, cols, diag1, diag2, n);
    } else if (!placed) {
      row--;
      if (row >= 0) {
        col = queenCol[row] + 1;
        removeQ14Optimized(row, queenCol, cols, diag1, diag2, n);
      }
    }
  }
  return answer;
}

private void removeQ14Optimized(int row, int[] queenCol, boolean[] cols, boolean[] diag1, boolean[] diag2, int n) {
  int col = queenCol[row];
  cols[col] = false;
  diag1[row - col + n] = false;
  diag2[row + col] = false;
  queenCol[row] = -1;
}

private List<String> buildQ14Optimized(int[] queenCol) {
  List<String> board = new ArrayList<>();
  for (int col : queenCol) {
    char[] row = new char[queenCol.length];
    Arrays.fill(row, '.');
    row[col] = 'Q';
    board.add(new String(row));
  }
  return board;
}
```

#### 15. Sudoku Solver - [LeetCode 37](https://leetcode.com/problems/sudoku-solver/)

Write a program to solve a 9 x 9 Sudoku board by filling empty cells marked with dot characters.

```java
public void q15SolveSudokuOptimized(char[][] board) {
  List<int[]> blanks = new ArrayList<>();
  boolean[][] rows = new boolean[9][10];
  boolean[][] cols = new boolean[9][10];
  boolean[][] boxes = new boolean[9][10];

  for (int r = 0; r < 9; r++) {
    for (int c = 0; c < 9; c++) {
      if (board[r][c] == '.') blanks.add(new int[] {r, c});
      else placeQ15Optimized(rows, cols, boxes, r, c, board[r][c] - '0', true);
    }
  }

  int[] nextDigit = new int[blanks.size()];
  Arrays.fill(nextDigit, 1);
  int index = 0;
  while (index >= 0 && index < blanks.size()) {
    int r = blanks.get(index)[0], c = blanks.get(index)[1];
    if (board[r][c] != '.') {
      placeQ15Optimized(rows, cols, boxes, r, c, board[r][c] - '0', false);
      board[r][c] = '.';
    }

    boolean placed = false;
    for (int d = nextDigit[index]; d <= 9; d++) {
      if (!rows[r][d] && !cols[c][d] && !boxes[boxQ15Optimized(r, c)][d]) {
        board[r][c] = (char) ('0' + d);
        placeQ15Optimized(rows, cols, boxes, r, c, d, true);
        nextDigit[index] = d + 1;
        index++;
        placed = true;
        break;
      }
    }
    if (!placed) {
      nextDigit[index] = 1;
      index--;
    }
  }
}

private void placeQ15Optimized(boolean[][] rows, boolean[][] cols, boolean[][] boxes,
    int r, int c, int d, boolean used) {
  rows[r][d] = used;
  cols[c][d] = used;
  boxes[boxQ15Optimized(r, c)][d] = used;
}

private int boxQ15Optimized(int r, int c) {
  return (r / 3) * 3 + c / 3;
}
```

#### 16. Word Search - [LeetCode 79](https://leetcode.com/problems/word-search/)

Given an m x n board of characters and a word, return true if the word exists in the grid by moving horizontally or vertically without reusing a cell.

```java
private static final int[][] DIRSQ16Optimized = {{1,0},{-1,0},{0,1},{0,-1}};

public boolean q16ExistOptimized(char[][] board, String word) {
  int rows = board.length, cols = board[0].length;
  for (int r = 0; r < rows; r++) {
    for (int c = 0; c < cols; c++) {
      if (board[r][c] != word.charAt(0)) continue;
      Deque<StateQ16Optimized> stack = new ArrayDeque<>();
      stack.push(new StateQ16Optimized(r, c, 0, 1L << (r * cols + c)));
      while (!stack.isEmpty()) {
        StateQ16Optimized cur = stack.pop();
        if (cur.index == word.length() - 1) return true;
        for (int[] dir : DIRSQ16Optimized) {
          int nr = cur.row + dir[0], nc = cur.col + dir[1];
          int nextIndex = cur.index + 1;
          if (nr < 0 || nc < 0 || nr == rows || nc == cols) continue;
          long bit = 1L << (nr * cols + nc);
          if ((cur.used & bit) != 0 || board[nr][nc] != word.charAt(nextIndex)) continue;
          stack.push(new StateQ16Optimized(nr, nc, nextIndex, cur.used | bit));
        }
      }
    }
  }
  return false;
}

private static class StateQ16Optimized {
  int row, col, index;
  long used;
  StateQ16Optimized(int row, int col, int index, long used) {
    this.row = row;
    this.col = col;
    this.index = index;
    this.used = used;
  }
}
```

#### 17. Same Tree - [LeetCode 100](https://leetcode.com/problems/same-tree/)

Given the roots of two binary trees p and q, return true if they are structurally identical and node values are equal.

```java
public boolean q17IsSameTreeOptimized(TreeNode p, TreeNode q) {
  Deque<TreeNode[]> stack = new ArrayDeque<>();
  stack.push(new TreeNode[] {p, q});

  while (!stack.isEmpty()) {
    TreeNode[] pair = stack.pop();
    TreeNode a = pair[0], b = pair[1];
    if (a == null && b == null) continue;
    if (a == null || b == null || a.val != b.val) return false;
    stack.push(new TreeNode[] {a.left, b.left});
    stack.push(new TreeNode[] {a.right, b.right});
  }

  return true;
}
```

#### 18. Symmetric Tree - [LeetCode 101](https://leetcode.com/problems/symmetric-tree/)

Given the root of a binary tree, return true if it is symmetric around its center.

```java
public boolean q18IsSymmetricOptimized(TreeNode root) {
  if (root == null) return true;
  Queue<TreeNode> queue = new LinkedList<>();
  queue.offer(root.left);
  queue.offer(root.right);

  while (!queue.isEmpty()) {
    TreeNode a = queue.poll();
    TreeNode b = queue.poll();
    if (a == null && b == null) continue;
    if (a == null || b == null || a.val != b.val) return false;
    queue.offer(a.left);
    queue.offer(b.right);
    queue.offer(a.right);
    queue.offer(b.left);
  }

  return true;
}
```

#### 19. Maximum Depth of Binary Tree - [LeetCode 104](https://leetcode.com/problems/maximum-depth-of-binary-tree/)

Given the root of a binary tree, return its maximum depth.

```java
public int q19MaxDepthOptimized(TreeNode root) {
  if (root == null) return 0;
  Queue<TreeNode> queue = new ArrayDeque<>();
  queue.offer(root);
  int depth = 0;

  while (!queue.isEmpty()) {
    int size = queue.size();
    depth++;
    for (int i = 0; i < size; i++) {
      TreeNode node = queue.poll();
      if (node.left != null) queue.offer(node.left);
      if (node.right != null) queue.offer(node.right);
    }
  }

  return depth;
}
```

#### 20. Invert Binary Tree - [LeetCode 226](https://leetcode.com/problems/invert-binary-tree/)

Given the root of a binary tree, invert the tree and return its root.

```java
public TreeNode q20InvertTreeOptimized(TreeNode root) {
  if (root == null) return null;
  Queue<TreeNode> queue = new ArrayDeque<>();
  queue.offer(root);

  while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    TreeNode temp = node.left;
    node.left = node.right;
    node.right = temp;
    if (node.left != null) queue.offer(node.left);
    if (node.right != null) queue.offer(node.right);
  }

  return root;
}
```

## Topic 11: Backtracking

#### 13. Word Search - [LeetCode 79](https://leetcode.com/problems/word-search/)

Given an m x n character board and a word, return true if the word exists in the grid by adjacent horizontal or vertical moves without reusing cells.

```java
private static final int[][] DIRSQ13Optimized = {{1,0},{-1,0},{0,1},{0,-1}};

public boolean q13ExistOptimized(char[][] board, String word) {
  int rows = board.length, cols = board[0].length;
  for (int r = 0; r < rows; r++) {
    for (int c = 0; c < cols; c++) {
      if (board[r][c] != word.charAt(0)) continue;
      Deque<StateQ13Optimized> stack = new ArrayDeque<>();
      stack.push(new StateQ13Optimized(r, c, 0, 1L << (r * cols + c)));
      while (!stack.isEmpty()) {
        StateQ13Optimized cur = stack.pop();
        if (cur.index == word.length() - 1) return true;
        for (int[] dir : DIRSQ13Optimized) {
          int nr = cur.row + dir[0], nc = cur.col + dir[1];
          int nextIndex = cur.index + 1;
          if (nr < 0 || nc < 0 || nr == rows || nc == cols) continue;
          long bit = 1L << (nr * cols + nc);
          if ((cur.used & bit) != 0 || board[nr][nc] != word.charAt(nextIndex)) continue;
          stack.push(new StateQ13Optimized(nr, nc, nextIndex, cur.used | bit));
        }
      }
    }
  }
  return false;
}

private static class StateQ13Optimized {
  int row, col, index;
  long used;
  StateQ13Optimized(int row, int col, int index, long used) {
    this.row = row;
    this.col = col;
    this.index = index;
    this.used = used;
  }
}
```

#### 14. N-Queens - [LeetCode 51](https://leetcode.com/problems/n-queens/)

Given n, return all distinct boards that place n queens on an n x n board so no two queens attack each other.

```java
public List<List<String>> q14SolveNQueensOptimized(int n) {
  List<List<String>> answer = new ArrayList<>();
  int[] queenCol = new int[n];
  Arrays.fill(queenCol, -1);
  boolean[] cols = new boolean[n];
  boolean[] diag1 = new boolean[2 * n];
  boolean[] diag2 = new boolean[2 * n];
  int row = 0;
  int col = 0;

  while (row >= 0) {
    boolean placed = false;
    for (; col < n; col++) {
      int d1 = row - col + n;
      int d2 = row + col;
      if (cols[col] || diag1[d1] || diag2[d2]) continue;
      queenCol[row] = col;
      cols[col] = diag1[d1] = diag2[d2] = true;
      row++;
      col = 0;
      placed = true;
      break;
    }

    if (row == n) {
      answer.add(buildQ14Optimized(queenCol));
      row--;
      col = queenCol[row] + 1;
      removeQ14Optimized(row, queenCol, cols, diag1, diag2, n);
    } else if (!placed) {
      row--;
      if (row >= 0) {
        col = queenCol[row] + 1;
        removeQ14Optimized(row, queenCol, cols, diag1, diag2, n);
      }
    }
  }
  return answer;
}

private void removeQ14Optimized(int row, int[] queenCol, boolean[] cols, boolean[] diag1, boolean[] diag2, int n) {
  int col = queenCol[row];
  cols[col] = false;
  diag1[row - col + n] = false;
  diag2[row + col] = false;
  queenCol[row] = -1;
}

private List<String> buildQ14Optimized(int[] queenCol) {
  List<String> board = new ArrayList<>();
  for (int col : queenCol) {
    char[] row = new char[queenCol.length];
    Arrays.fill(row, '.');
    row[col] = 'Q';
    board.add(new String(row));
  }
  return board;
}
```

#### 15. Sudoku Solver - [LeetCode 37](https://leetcode.com/problems/sudoku-solver/)

Solve a 9 x 9 Sudoku board by filling empty cells marked with dots. The given board has exactly one solution.

```java
public void q15SolveSudokuOptimized(char[][] board) {
  List<int[]> blanks = new ArrayList<>();
  boolean[][] rows = new boolean[9][10], cols = new boolean[9][10], boxes = new boolean[9][10];
  for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) {
    if (board[r][c] == '.') blanks.add(new int[]{r,c});
    else setQ15Optimized(rows, cols, boxes, r, c, board[r][c] - '0', true);
  }
  int[] next = new int[blanks.size()];
  Arrays.fill(next, 1);
  int i = 0;
  while (i >= 0 && i < blanks.size()) {
    int r = blanks.get(i)[0], c = blanks.get(i)[1];
    if (board[r][c] != '.') { setQ15Optimized(rows, cols, boxes, r, c, board[r][c] - '0', false); board[r][c] = '.'; }
    boolean placed = false;
    for (int d = next[i]; d <= 9; d++) if (!rows[r][d] && !cols[c][d] && !boxes[boxQ15Optimized(r,c)][d]) {
      board[r][c] = (char)('0' + d); setQ15Optimized(rows, cols, boxes, r, c, d, true); next[i] = d + 1; i++; placed = true; break;
    }
    if (!placed) { next[i] = 1; i--; }
  }
}

private void setQ15Optimized(boolean[][] rows, boolean[][] cols, boolean[][] boxes, int r, int c, int d, boolean value) { rows[r][d] = cols[c][d] = boxes[boxQ15Optimized(r,c)][d] = value; }
private int boxQ15Optimized(int r, int c) { return (r / 3) * 3 + c / 3; }
```

#### 16. Rat in a Maze - [GeeksforGeeks](https://www.geeksforgeeks.org/problems/rat-in-a-maze-problem/1)

Given an n x n maze matrix where 1 means open and 0 means blocked, return all paths from top-left to bottom-right using moves D, L, R, and U without revisiting cells.

```java
public ArrayList<String> q16FindPathOptimized(int[][] mat) {
  ArrayList<String> answer = new ArrayList<>();
  if (mat[0][0] == 0) return answer;
  int n = mat.length;
  Deque<StateQ16Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ16Optimized(0, 0, "", new boolean[n][n]));
  char[] move = {'U','R','L','D'}; int[] dr = {-1,0,0,1}; int[] dc = {0,1,-1,0};
  while (!stack.isEmpty()) {
    StateQ16Optimized s = stack.pop();
    if (s.r < 0 || s.c < 0 || s.r == n || s.c == n || mat[s.r][s.c] == 0 || s.used[s.r][s.c]) continue;
    if (s.r == n - 1 && s.c == n - 1) { answer.add(s.path); continue; }
    s.used[s.r][s.c] = true;
    for (int i = 0; i < 4; i++) stack.push(new StateQ16Optimized(s.r + dr[i], s.c + dc[i], s.path + move[i], copyQ16Optimized(s.used)));
  }
  Collections.sort(answer);
  return answer;
}

private boolean[][] copyQ16Optimized(boolean[][] used) { boolean[][] next = new boolean[used.length][used.length]; for (int i = 0; i < used.length; i++) next[i] = used[i].clone(); return next; }
private static class StateQ16Optimized { int r,c; String path; boolean[][] used; StateQ16Optimized(int r,int c,String p,boolean[][] u){this.r=r;this.c=c;path=p;used=u;} }
```

#### 17. Unique Paths III - [LeetCode 980](https://leetcode.com/problems/unique-paths-iii/)

Given a grid with start, end, empty cells, and obstacles, return the number of 4-directional paths from start to end that visit every non-obstacle cell exactly once.

```java
public int q17UniquePathsIIIOptimized(int[][] grid) {
  int rows = grid.length, cols = grid[0].length, sr = 0, sc = 0, walk = 0;
  for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) {
    if (grid[r][c] != -1) walk++;
    if (grid[r][c] == 1) { sr = r; sc = c; }
  }
  int answer = 0;
  Deque<StateQ17Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ17Optimized(sr, sc, walk, 0));
  int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
  while (!stack.isEmpty()) {
    StateQ17Optimized s = stack.pop();
    if (s.r < 0 || s.c < 0 || s.r == rows || s.c == cols || grid[s.r][s.c] == -1) continue;
    int bit = 1 << (s.r * cols + s.c);
    if ((s.mask & bit) != 0) continue;
    if (grid[s.r][s.c] == 2) { if (s.remaining == 1) answer++; continue; }
    for (int[] d : dirs) stack.push(new StateQ17Optimized(s.r+d[0], s.c+d[1], s.remaining-1, s.mask | bit));
  }
  return answer;
}

private static class StateQ17Optimized { int r,c,remaining,mask; StateQ17Optimized(int r,int c,int rem,int mask){this.r=r;this.c=c;remaining=rem;this.mask=mask;} }
```

#### 18. Matchsticks to Square - [LeetCode 473](https://leetcode.com/problems/matchsticks-to-square/)

Given matchsticks, return true if they can form a square using every matchstick exactly once.

```java
public boolean q18MakesquareOptimized(int[] matchsticks) {
  int sum = 0;
  for (int stick : matchsticks) sum += stick;
  if (sum % 4 != 0) return false;
  int target = sum / 4;
  int totalMasks = 1 << matchsticks.length;
  int[] dp = new int[totalMasks];
  Arrays.fill(dp, -1);
  dp[0] = 0;
  for (int mask = 0; mask < totalMasks; mask++) {
    if (dp[mask] == -1) continue;
    for (int i = 0; i < matchsticks.length; i++) {
      if ((mask & (1 << i)) != 0) continue;
      int next = dp[mask] + matchsticks[i];
      if (next <= target) dp[mask | (1 << i)] = next % target;
    }
  }
  return dp[totalMasks - 1] == 0;
}
```

#### 19. Partition to K Equal Sum Subsets - [LeetCode 698](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/)

Given nums and k, return true if nums can be partitioned into k non-empty subsets with equal sum.

```java
public boolean q19CanPartitionKSubsetsOptimized(int[] nums, int k) {
  int sum = 0;
  for (int num : nums) sum += num;
  if (sum % k != 0) return false;
  int target = sum / k;
  int totalMasks = 1 << nums.length;
  int[] dp = new int[totalMasks];
  Arrays.fill(dp, -1);
  dp[0] = 0;
  for (int mask = 0; mask < totalMasks; mask++) {
    if (dp[mask] == -1) continue;
    for (int i = 0; i < nums.length; i++) {
      if ((mask & (1 << i)) != 0) continue;
      int next = dp[mask] + nums[i];
      if (next <= target) dp[mask | (1 << i)] = next % target;
    }
  }
  return dp[totalMasks - 1] == 0;
}
```

#### 20. Beautiful Arrangement - [LeetCode 526](https://leetcode.com/problems/beautiful-arrangement/)

Given n, count the number of arrangements of 1..n where at position i, either perm[i] is divisible by i or i is divisible by perm[i].

```java
public int q20CountArrangementOptimized(int n) {
  int[] dp = new int[1 << n];
  dp[0] = 1;
  for (int mask = 0; mask < dp.length; mask++) {
    int position = Integer.bitCount(mask) + 1;
    for (int num = 1; num <= n; num++) {
      int bit = 1 << (num - 1);
      if ((mask & bit) != 0) continue;
      if (num % position == 0 || position % num == 0) dp[mask | bit] += dp[mask];
    }
  }
  return dp[dp.length - 1];
}
```

## Topic 19: Greedy

#### 13. Reorganize String - [LeetCode 767](https://leetcode.com/problems/reorganize-string/)

Given a string s, rearrange it so no two adjacent characters are equal, or return an empty string if impossible.

```java
public String q13ReorganizeStringOptimized(String s) {
  int[] count = new int[26];
  for (char c : s.toCharArray()) count[c - 'a']++;
  PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
  for (int i = 0; i < 26; i++) if (count[i] > 0) heap.offer(new int[] {i, count[i]});

  StringBuilder answer = new StringBuilder();
  int[] previous = null;
  while (!heap.isEmpty()) {
    int[] current = heap.poll();
    answer.append((char) ('a' + current[0]));
    current[1]--;
    if (previous != null && previous[1] > 0) heap.offer(previous);
    previous = current;
  }
  return answer.length() == s.length() ? answer.toString() : "";
}
```

#### 14. Hand of Straights - [LeetCode 846](https://leetcode.com/problems/hand-of-straights/)

Given a hand of cards and groupSize, return true if the cards can be rearranged into groups of groupSize consecutive cards.

```java
public boolean q14IsNStraightHandOptimized(int[] hand, int groupSize) {
  if (hand.length % groupSize != 0) return false;
  TreeMap<Integer, Integer> count = new TreeMap<>();
  for (int card : hand) count.put(card, count.getOrDefault(card, 0) + 1);

  while (!count.isEmpty()) {
    int start = count.firstKey();
    for (int card = start; card < start + groupSize; card++) {
      Integer freq = count.get(card);
      if (freq == null) return false;
      if (freq == 1) count.remove(card); else count.put(card, freq - 1);
    }
  }
  return true;
}
```

#### 15. Boats to Save People - [LeetCode 881](https://leetcode.com/problems/boats-to-save-people/)

Given people weights and a boat limit where each boat carries at most two people, return the minimum number of boats needed.

```java
public int q15NumRescueBoatsOptimized(int[] people, int limit) {
  Arrays.sort(people);
  int left = 0;
  int right = people.length - 1;
  int boats = 0;

  while (left <= right) {
    if (people[left] + people[right] <= limit) left++;
    right--;
    boats++;
  }
  return boats;
}
```

#### 16. Two City Scheduling - [LeetCode 1029](https://leetcode.com/problems/two-city-scheduling/)

Given costs for flying each person to city A or B, send exactly n people to each city with minimum total cost.

```java
public int q16TwoCitySchedCostOptimized(int[][] costs) {
  Arrays.sort(costs, (a, b) -> Integer.compare(a[0] - a[1], b[0] - b[1]));
  int n = costs.length / 2;
  int total = 0;
  for (int i = 0; i < costs.length; i++) {
    total += i < n ? costs[i][0] : costs[i][1];
  }
  return total;
}
```

#### 17. Maximum Subarray - [LeetCode 53](https://leetcode.com/problems/maximum-subarray/)

Given an integer array, return the largest possible sum of a non-empty contiguous subarray.

```java
public int q17MaxSubArrayOptimized(int[] nums) {
  int current = nums[0];
  int best = nums[0];

  for (int i = 1; i < nums.length; i++) {
    current = Math.max(nums[i], current + nums[i]);
    best = Math.max(best, current);
  }
  return best;
}
```

#### 18. Best Time to Buy and Sell Stock II - [LeetCode 122](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)

Given daily stock prices, return the maximum profit with as many buy-sell transactions as desired, holding at most one share at a time.

```java
public int q18MaxProfitOptimized(int[] prices) {
  int profit = 0;
  for (int i = 1; i < prices.length; i++) {
    if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
  }
  return profit;
}
```

#### 19. Can Place Flowers - [LeetCode 605](https://leetcode.com/problems/can-place-flowers/)

Given a flowerbed of 0s and 1s and an integer n, return true if n new flowers can be planted without adjacent flowers.

```java
public boolean q19CanPlaceFlowersOptimized(int[] flowerbed, int n) {
  for (int i = 0; i < flowerbed.length && n > 0; i++) {
    boolean leftEmpty = i == 0 || flowerbed[i - 1] == 0;
    boolean rightEmpty = i + 1 == flowerbed.length || flowerbed[i + 1] == 0;
    if (flowerbed[i] == 0 && leftEmpty && rightEmpty) {
      flowerbed[i] = 1;
      n--;
    }
  }
  return n == 0;
}
```

#### 20. Increasing Triplet Subsequence - [LeetCode 334](https://leetcode.com/problems/increasing-triplet-subsequence/)

Given an integer array, return true if there exists a strictly increasing subsequence of length three.

```java
public boolean q20IncreasingTripletOptimized(int[] nums) {
  int first = Integer.MAX_VALUE;
  int second = Integer.MAX_VALUE;

  for (int num : nums) {
    if (num <= first) {
      first = num;
    } else if (num <= second) {
      second = num;
    } else {
      return true;
    }
  }
  return false;
}
```

## Topic 17: 1D Dynamic Programming

#### 13. Perfect Squares - [LeetCode 279](https://leetcode.com/problems/perfect-squares/)

Given n, return the minimum number of perfect square numbers whose sum is n.

```java
public int q13NumSquaresOptimized(int n) {
  int[] dp = new int[n + 1];
  Arrays.fill(dp, n + 1);
  dp[0] = 0;

  for (int value = 1; value <= n; value++) {
    for (int square = 1; square * square <= value; square++) {
      dp[value] = Math.min(dp[value], dp[value - square * square] + 1);
    }
  }
  return dp[n];
}
```

#### 14. Integer Break - [LeetCode 343](https://leetcode.com/problems/integer-break/)

Given integer n, break it into at least two positive integers and maximize the product of those integers.

```java
public int q14IntegerBreakOptimized(int n) {
  int[] dp = new int[n + 1];

  for (int value = 2; value <= n; value++) {
    for (int first = 1; first < value; first++) {
      int keep = first * (value - first);
      int split = first * dp[value - first];
      dp[value] = Math.max(dp[value], Math.max(keep, split));
    }
  }
  return dp[n];
}
```

#### 15. Maximum Product Subarray - [LeetCode 152](https://leetcode.com/problems/maximum-product-subarray/)

Given an integer array, return the maximum product of a non-empty contiguous subarray.

```java
public int q15MaxProductOptimized(int[] nums) {
  int maxEnding = nums[0];
  int minEnding = nums[0];
  int best = nums[0];

  for (int i = 1; i < nums.length; i++) {
    int value = nums[i];
    if (value < 0) {
      int temp = maxEnding;
      maxEnding = minEnding;
      minEnding = temp;
    }
    maxEnding = Math.max(value, maxEnding * value);
    minEnding = Math.min(value, minEnding * value);
    best = Math.max(best, maxEnding);
  }
  return best;
}
```

#### 16. Maximum Subarray - [LeetCode 53](https://leetcode.com/problems/maximum-subarray/)

Given an integer array, return the largest sum of any non-empty contiguous subarray.

```java
public int q16MaxSubArrayOptimized(int[] nums) {
  int current = nums[0];
  int best = nums[0];

  for (int i = 1; i < nums.length; i++) {
    current = Math.max(nums[i], current + nums[i]);
    best = Math.max(best, current);
  }
  return best;
}
```

#### 17. Best Time to Buy and Sell Stock - [LeetCode 121](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)

Given prices where prices[i] is the stock price on day i, return the maximum profit from one buy followed by one sell.

```java
public int q17MaxProfitOptimized(int[] prices) {
  int minPrice = Integer.MAX_VALUE;
  int best = 0;

  for (int price : prices) {
    minPrice = Math.min(minPrice, price);
    best = Math.max(best, price - minPrice);
  }
  return best;
}
```

#### 18. Best Time to Buy and Sell Stock with Cooldown - [LeetCode 309](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

Given daily stock prices, return the maximum profit with unlimited transactions and a one-day cooldown after selling.

```java
public int q18MaxProfitOptimized(int[] prices) {
  int hold = -prices[0];
  int sold = 0;
  int rest = 0;

  for (int i = 1; i < prices.length; i++) {
    int previousHold = hold;
    int previousSold = sold;
    int previousRest = rest;
    hold = Math.max(previousHold, previousRest - prices[i]);
    sold = previousHold + prices[i];
    rest = Math.max(previousRest, previousSold);
  }
  return Math.max(sold, rest);
}
```

#### 19. Best Time to Buy and Sell Stock with Transaction Fee - [LeetCode 714](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/)

Given daily stock prices and a transaction fee, return maximum profit with unlimited transactions where each sale pays the fee.

```java
public int q19MaxProfitOptimized(int[] prices, int fee) {
  int cash = 0;
  int hold = -prices[0];

  for (int i = 1; i < prices.length; i++) {
    int previousCash = cash;
    cash = Math.max(cash, hold + prices[i] - fee);
    hold = Math.max(hold, previousCash - prices[i]);
  }
  return cash;
}
```

#### 20. Delete and Earn - [LeetCode 740](https://leetcode.com/problems/delete-and-earn/)

Given nums, you may take a value x to earn x points per occurrence, but then all x - 1 and x + 1 values are deleted. Return maximum points.

```java
public int q20DeleteAndEarnOptimized(int[] nums) {
  int max = 0;
  for (int num : nums) max = Math.max(max, num);
  int[] points = new int[max + 1];
  for (int num : nums) points[num] += num;

  int twoBack = 0;
  int oneBack = 0;
  for (int value = 1; value <= max; value++) {
    int current = Math.max(oneBack, twoBack + points[value]);
    twoBack = oneBack;
    oneBack = current;
  }
  return oneBack;
}
```

## Topic 18: 2D Dynamic Programming

#### 13. Palindromic Substrings - [LeetCode 647](https://leetcode.com/problems/palindromic-substrings/)

Given a string s, return the number of contiguous substrings that are palindromes.

```java
public int q13CountSubstringsOptimized(String s) {
  int n = s.length();
  boolean[][] dp = new boolean[n][n];
  int count = 0;

  for (int length = 1; length <= n; length++) {
    for (int left = 0; left + length - 1 < n; left++) {
      int right = left + length - 1;
      boolean innerOk = length <= 2 || dp[left + 1][right - 1];
      dp[left][right] = s.charAt(left) == s.charAt(right) && innerOk;
      if (dp[left][right]) count++;
    }
  }
  return count;
}
```

#### 14. Coin Change II - [LeetCode 518](https://leetcode.com/problems/coin-change-ii/)

Given coin denominations and an amount, return the number of combinations that make the amount. Each coin can be used unlimited times.

```java
public int q14ChangeOptimized(int amount, int[] coins) {
  int[] dp = new int[amount + 1];
  dp[0] = 1;

  for (int coin : coins) {
    for (int sum = coin; sum <= amount; sum++) {
      dp[sum] += dp[sum - coin];
    }
  }
  return dp[amount];
}
```

#### 15. Target Sum - [LeetCode 494](https://leetcode.com/problems/target-sum/)

Given nums and target, assign either plus or minus before every number. Return how many assignments evaluate to target.

```java
public int q15FindTargetSumWaysOptimized(int[] nums, int target) {
  int total = 0;
  for (int num : nums) total += num;
  if (Math.abs(target) > total) return 0;

  int offset = total;
  int[] dp = new int[2 * total + 1];
  dp[offset] = 1;

  for (int num : nums) {
    int[] next = new int[2 * total + 1];
    for (int sum = -total; sum <= total; sum++) {
      int ways = dp[sum + offset];
      if (ways == 0) continue;
      next[sum + num + offset] += ways;
      next[sum - num + offset] += ways;
    }
    dp = next;
  }
  return dp[target + offset];
}
```

#### 16. Ones and Zeroes - [LeetCode 474](https://leetcode.com/problems/ones-and-zeroes/)

Given binary strings strs and limits m zeros and n ones, return the maximum number of strings that can be chosen without exceeding either limit.

```java
public int q16FindMaxFormOptimized(String[] strs, int m, int n) {
  int[][] dp = new int[m + 1][n + 1];

  for (String s : strs) {
    int[] cost = countQ16Optimized(s);
    for (int zeros = m; zeros >= cost[0]; zeros--) {
      for (int ones = n; ones >= cost[1]; ones--) {
        dp[zeros][ones] = Math.max(dp[zeros][ones], 1 + dp[zeros - cost[0]][ones - cost[1]]);
      }
    }
  }
  return dp[m][n];
}

private int[] countQ16Optimized(String s) {
  int zeros = 0;
  for (char c : s.toCharArray()) if (c == '0') zeros++;
  return new int[] {zeros, s.length() - zeros};
}
```

#### 17. Last Stone Weight II - [LeetCode 1049](https://leetcode.com/problems/last-stone-weight-ii/)

Given stone weights, repeatedly smashing stones is equivalent to splitting stones into two groups. Return the minimum possible remaining weight.

```java
public int q17LastStoneWeightIIOptimized(int[] stones) {
  int total = 0;
  for (int stone : stones) total += stone;
  int capacity = total / 2;
  boolean[] dp = new boolean[capacity + 1];
  dp[0] = true;

  for (int stone : stones) {
    for (int sum = capacity; sum >= stone; sum--) {
      dp[sum] |= dp[sum - stone];
    }
  }
  for (int sum = capacity; sum >= 0; sum--) {
    if (dp[sum]) return total - 2 * sum;
  }
  return total;
}
```

#### 18. Partition Equal Subset Sum - [LeetCode 416](https://leetcode.com/problems/partition-equal-subset-sum/)

Given nums, return true if the array can be split into two subsets with equal sum.

```java
public boolean q18CanPartitionOptimized(int[] nums) {
  int total = 0;
  for (int num : nums) total += num;
  if (total % 2 == 1) return false;

  int target = total / 2;
  boolean[] dp = new boolean[target + 1];
  dp[0] = true;

  for (int num : nums) {
    for (int sum = target; sum >= num; sum--) {
      dp[sum] |= dp[sum - num];
    }
  }
  return dp[target];
}
```

#### 19. Longest Increasing Path in a Matrix - [LeetCode 329](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)

Given an integer matrix, return the length of the longest path where each next cell is strictly larger and movement is allowed in four directions.

```java
private static final int[][] DIRSQ19Optimized = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

public int q19LongestIncreasingPathOptimized(int[][] matrix) {
  int rows = matrix.length;
  int cols = matrix[0].length;
  int[][] cells = new int[rows * cols][3];
  int index = 0;
  for (int row = 0; row < rows; row++) {
    for (int col = 0; col < cols; col++) {
      cells[index++] = new int[] {matrix[row][col], row, col};
    }
  }
  Arrays.sort(cells, (a, b) -> Integer.compare(a[0], b[0]));

  int[][] dp = new int[rows][cols];
  int best = 1;
  for (int[] cell : cells) {
    int row = cell[1];
    int col = cell[2];
    dp[row][col] = Math.max(dp[row][col], 1);
    for (int[] dir : DIRSQ19Optimized) {
      int nextRow = row + dir[0];
      int nextCol = col + dir[1];
      if (nextRow < 0 || nextCol < 0 || nextRow == rows || nextCol == cols) continue;
      if (matrix[nextRow][nextCol] > matrix[row][col]) {
        dp[nextRow][nextCol] = Math.max(dp[nextRow][nextCol], dp[row][col] + 1);
        best = Math.max(best, dp[nextRow][nextCol]);
      }
    }
  }
  return best;
}
```

#### 20. Maximal Square - [LeetCode 221](https://leetcode.com/problems/maximal-square/)

Given a binary matrix of characters, return the area of the largest square containing only 1s.

```java
public int q20MaximalSquareOptimized(char[][] matrix) {
  int rows = matrix.length;
  int cols = matrix[0].length;
  int[] dp = new int[cols + 1];
  int bestSide = 0;

  for (int row = 1; row <= rows; row++) {
    int diagonal = 0;
    for (int col = 1; col <= cols; col++) {
      int saved = dp[col];
      if (matrix[row - 1][col - 1] == '1') {
        dp[col] = 1 + Math.min(diagonal, Math.min(dp[col], dp[col - 1]));
        bestSide = Math.max(bestSide, dp[col]);
      } else {
        dp[col] = 0;
      }
      diagonal = saved;
    }
  }
  return bestSide * bestSide;
}
```

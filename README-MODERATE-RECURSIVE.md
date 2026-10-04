# Recursion - Moderate Recursive Questions and Solutions

## Topic 10: Recursion

#### 13. Combination Sum - [LeetCode 39](https://leetcode.com/problems/combination-sum/)

Given distinct candidate numbers and a target, return all unique combinations where chosen numbers sum to target. A candidate may be chosen unlimited times.

```java
public List<List<Integer>> q13CombinationSumRecursive(int[] candidates, int target) {
  Arrays.sort(candidates);
  List<List<Integer>> answer = new ArrayList<>();
  dfsQ13Recursive(candidates, 0, target, new ArrayList<>(), answer);
  return answer;
}

private void dfsQ13Recursive(int[] candidates, int start, int remaining,
    List<Integer> path, List<List<Integer>> answer) {
  if (remaining == 0) {
    answer.add(new ArrayList<>(path));
    return;
  }

  for (int i = start; i < candidates.length && candidates[i] <= remaining; i++) {
    path.add(candidates[i]);
    dfsQ13Recursive(candidates, i, remaining - candidates[i], path, answer);
    path.remove(path.size() - 1);
  }
}
```

#### 14. N-Queens - [LeetCode 51](https://leetcode.com/problems/n-queens/)

Given n, return all distinct solutions to the n-queens puzzle, placing n queens on an n x n chessboard so no two queens attack each other.

```java
public List<List<String>> q14SolveNQueensRecursive(int n) {
  List<List<String>> answer = new ArrayList<>();
  backtrackQ14Recursive(0, n, new int[n], new boolean[n], new boolean[2 * n], new boolean[2 * n], answer);
  return answer;
}

private void backtrackQ14Recursive(int row, int n, int[] queenCol, boolean[] cols,
    boolean[] diag1, boolean[] diag2, List<List<String>> answer) {
  if (row == n) {
    answer.add(buildQ14Recursive(queenCol));
    return;
  }

  for (int col = 0; col < n; col++) {
    int d1 = row - col + n;
    int d2 = row + col;
    if (cols[col] || diag1[d1] || diag2[d2]) continue;
    queenCol[row] = col;
    cols[col] = diag1[d1] = diag2[d2] = true;
    backtrackQ14Recursive(row + 1, n, queenCol, cols, diag1, diag2, answer);
    cols[col] = diag1[d1] = diag2[d2] = false;
  }
}

private List<String> buildQ14Recursive(int[] queenCol) {
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
public void q15SolveSudokuRecursive(char[][] board) {
  boolean[][] rows = new boolean[9][10];
  boolean[][] cols = new boolean[9][10];
  boolean[][] boxes = new boolean[9][10];
  List<int[]> blanks = new ArrayList<>();

  for (int r = 0; r < 9; r++) {
    for (int c = 0; c < 9; c++) {
      if (board[r][c] == '.') blanks.add(new int[] {r, c});
      else placeQ15Recursive(rows, cols, boxes, r, c, board[r][c] - '0', true);
    }
  }
  solveQ15Recursive(board, blanks, 0, rows, cols, boxes);
}

private boolean solveQ15Recursive(char[][] board, List<int[]> blanks, int index,
    boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
  if (index == blanks.size()) return true;

  int r = blanks.get(index)[0], c = blanks.get(index)[1];
  for (int d = 1; d <= 9; d++) {
    if (rows[r][d] || cols[c][d] || boxes[boxQ15Recursive(r, c)][d]) continue;
    board[r][c] = (char) ('0' + d);
    placeQ15Recursive(rows, cols, boxes, r, c, d, true);
    if (solveQ15Recursive(board, blanks, index + 1, rows, cols, boxes)) return true;
    placeQ15Recursive(rows, cols, boxes, r, c, d, false);
    board[r][c] = '.';
  }
  return false;
}

private void placeQ15Recursive(boolean[][] rows, boolean[][] cols, boolean[][] boxes,
    int r, int c, int d, boolean used) {
  rows[r][d] = used;
  cols[c][d] = used;
  boxes[boxQ15Recursive(r, c)][d] = used;
}

private int boxQ15Recursive(int r, int c) {
  return (r / 3) * 3 + c / 3;
}
```

#### 16. Word Search - [LeetCode 79](https://leetcode.com/problems/word-search/)

Given an m x n board of characters and a word, return true if the word exists in the grid by moving horizontally or vertically without reusing a cell.

```java
public boolean q16ExistRecursive(char[][] board, String word) {
  for (int r = 0; r < board.length; r++) {
    for (int c = 0; c < board[0].length; c++) {
      if (dfsQ16Recursive(board, word, r, c, 0)) return true;
    }
  }
  return false;
}

private boolean dfsQ16Recursive(char[][] board, String word, int r, int c, int index) {
  if (index == word.length()) return true;
  if (r < 0 || c < 0 || r == board.length || c == board[0].length) return false;
  if (board[r][c] != word.charAt(index)) return false;

  char saved = board[r][c];
  board[r][c] = '#';
  boolean found = dfsQ16Recursive(board, word, r + 1, c, index + 1)
      || dfsQ16Recursive(board, word, r - 1, c, index + 1)
      || dfsQ16Recursive(board, word, r, c + 1, index + 1)
      || dfsQ16Recursive(board, word, r, c - 1, index + 1);
  board[r][c] = saved;
  return found;
}
```

#### 17. Same Tree - [LeetCode 100](https://leetcode.com/problems/same-tree/)

Given the roots of two binary trees p and q, return true if they are structurally identical and node values are equal.

```java
public boolean q17IsSameTreeRecursive(TreeNode p, TreeNode q) {
  if (p == null || q == null) return p == q;
  return p.val == q.val
      && q17IsSameTreeRecursive(p.left, q.left)
      && q17IsSameTreeRecursive(p.right, q.right);
}
```

#### 18. Symmetric Tree - [LeetCode 101](https://leetcode.com/problems/symmetric-tree/)

Given the root of a binary tree, return true if it is symmetric around its center.

```java
public boolean q18IsSymmetricRecursive(TreeNode root) {
  return root == null || mirrorQ18Recursive(root.left, root.right);
}

private boolean mirrorQ18Recursive(TreeNode left, TreeNode right) {
  if (left == null || right == null) return left == right;
  return left.val == right.val
      && mirrorQ18Recursive(left.left, right.right)
      && mirrorQ18Recursive(left.right, right.left);
}
```

#### 19. Maximum Depth of Binary Tree - [LeetCode 104](https://leetcode.com/problems/maximum-depth-of-binary-tree/)

Given the root of a binary tree, return its maximum depth.

```java
public int q19MaxDepthRecursive(TreeNode root) {
  if (root == null) return 0;
  return 1 + Math.max(q19MaxDepthRecursive(root.left), q19MaxDepthRecursive(root.right));
}
```

#### 20. Invert Binary Tree - [LeetCode 226](https://leetcode.com/problems/invert-binary-tree/)

Given the root of a binary tree, invert the tree and return its root.

```java
public TreeNode q20InvertTreeRecursive(TreeNode root) {
  if (root == null) return null;

  TreeNode left = q20InvertTreeRecursive(root.left);
  TreeNode right = q20InvertTreeRecursive(root.right);
  root.left = right;
  root.right = left;
  return root;
}
```

## Topic 11: Backtracking

#### 13. Word Search - [LeetCode 79](https://leetcode.com/problems/word-search/)

Given an m x n character board and a word, return true if the word exists in the grid by adjacent horizontal or vertical moves without reusing cells.

```java
public boolean q13ExistRecursive(char[][] board, String word) {
  for (int r = 0; r < board.length; r++) {
    for (int c = 0; c < board[0].length; c++) {
      if (backtrackQ13Recursive(board, word, r, c, 0)) return true;
    }
  }
  return false;
}

private boolean backtrackQ13Recursive(char[][] board, String word, int r, int c, int index) {
  if (index == word.length()) return true;
  if (r < 0 || c < 0 || r == board.length || c == board[0].length) return false;
  if (board[r][c] != word.charAt(index)) return false;
  char saved = board[r][c];
  board[r][c] = '#';
  boolean found = backtrackQ13Recursive(board, word, r + 1, c, index + 1) || backtrackQ13Recursive(board, word, r - 1, c, index + 1)
      || backtrackQ13Recursive(board, word, r, c + 1, index + 1) || backtrackQ13Recursive(board, word, r, c - 1, index + 1);
  board[r][c] = saved;
  return found;
}
```

#### 14. N-Queens - [LeetCode 51](https://leetcode.com/problems/n-queens/)

Given n, return all distinct boards that place n queens on an n x n board so no two queens attack each other.

```java
public List<List<String>> q14SolveNQueensRecursive(int n) {
  List<List<String>> answer = new ArrayList<>();
  backtrackQ14Recursive(0, n, new int[n], new boolean[n], new boolean[2*n], new boolean[2*n], answer);
  return answer;
}

private void backtrackQ14Recursive(int row, int n, int[] cols, boolean[] used, boolean[] d1, boolean[] d2, List<List<String>> answer) {
  if (row == n) { answer.add(buildQ14Recursive(cols)); return; }
  for (int col = 0; col < n; col++) {
    int a = row - col + n, b = row + col;
    if (used[col] || d1[a] || d2[b]) continue;
    cols[row] = col; used[col] = d1[a] = d2[b] = true;
    backtrackQ14Recursive(row + 1, n, cols, used, d1, d2, answer);
    used[col] = d1[a] = d2[b] = false;
  }
}

private List<String> buildQ14Recursive(int[] cols) {
  List<String> board = new ArrayList<>();
  for (int col : cols) { char[] row = new char[cols.length]; Arrays.fill(row, '.'); row[col] = 'Q'; board.add(new String(row)); }
  return board;
}
```

#### 15. Sudoku Solver - [LeetCode 37](https://leetcode.com/problems/sudoku-solver/)

Solve a 9 x 9 Sudoku board by filling empty cells marked with dots. The given board has exactly one solution.

```java
public void q15SolveSudokuRecursive(char[][] board) {
  boolean[][] rows = new boolean[9][10], cols = new boolean[9][10], boxes = new boolean[9][10];
  List<int[]> blanks = new ArrayList<>();
  for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) {
    if (board[r][c] == '.') blanks.add(new int[]{r,c});
    else setQ15Recursive(rows, cols, boxes, r, c, board[r][c] - '0', true);
  }
  backtrackQ15Recursive(board, blanks, 0, rows, cols, boxes);
}

private boolean backtrackQ15Recursive(char[][] board, List<int[]> blanks, int index, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
  if (index == blanks.size()) return true;
  int r = blanks.get(index)[0], c = blanks.get(index)[1];
  for (int d = 1; d <= 9; d++) {
    if (rows[r][d] || cols[c][d] || boxes[boxQ15Recursive(r,c)][d]) continue;
    board[r][c] = (char)('0' + d); setQ15Recursive(rows, cols, boxes, r, c, d, true);
    if (backtrackQ15Recursive(board, blanks, index + 1, rows, cols, boxes)) return true;
    setQ15Recursive(rows, cols, boxes, r, c, d, false); board[r][c] = '.';
  }
  return false;
}

private void setQ15Recursive(boolean[][] rows, boolean[][] cols, boolean[][] boxes, int r, int c, int d, boolean value) { rows[r][d] = cols[c][d] = boxes[boxQ15Recursive(r,c)][d] = value; }
private int boxQ15Recursive(int r, int c) { return (r / 3) * 3 + c / 3; }
```

#### 16. Rat in a Maze - [GeeksforGeeks](https://www.geeksforgeeks.org/problems/rat-in-a-maze-problem/1)

Given an n x n maze matrix where 1 means open and 0 means blocked, return all paths from top-left to bottom-right using moves D, L, R, and U without revisiting cells.

```java
public ArrayList<String> q16FindPathRecursive(int[][] mat) {
  ArrayList<String> answer = new ArrayList<>();
  if (mat[0][0] == 0) return answer;
  backtrackQ16Recursive(mat, 0, 0, new boolean[mat.length][mat.length], new StringBuilder(), answer);
  return answer;
}

private void backtrackQ16Recursive(int[][] mat, int r, int c, boolean[][] used, StringBuilder path, ArrayList<String> answer) {
  int n = mat.length;
  if (r < 0 || c < 0 || r == n || c == n || mat[r][c] == 0 || used[r][c]) return;
  if (r == n - 1 && c == n - 1) { answer.add(path.toString()); return; }
  used[r][c] = true;
  char[] move = {'D','L','R','U'}; int[] dr = {1,0,0,-1}; int[] dc = {0,-1,1,0};
  for (int i = 0; i < 4; i++) {
    path.append(move[i]);
    backtrackQ16Recursive(mat, r + dr[i], c + dc[i], used, path, answer);
    path.deleteCharAt(path.length() - 1);
  }
  used[r][c] = false;
}
```

#### 17. Unique Paths III - [LeetCode 980](https://leetcode.com/problems/unique-paths-iii/)

Given a grid with start, end, empty cells, and obstacles, return the number of 4-directional paths from start to end that visit every non-obstacle cell exactly once.

```java
public int q17UniquePathsIIIRecursive(int[][] grid) {
  int sr = 0, sc = 0, walk = 0;
  for (int r = 0; r < grid.length; r++) for (int c = 0; c < grid[0].length; c++) {
    if (grid[r][c] != -1) walk++;
    if (grid[r][c] == 1) { sr = r; sc = c; }
  }
  return backtrackQ17Recursive(grid, sr, sc, walk);
}

private int backtrackQ17Recursive(int[][] grid, int r, int c, int remaining) {
  if (r < 0 || c < 0 || r == grid.length || c == grid[0].length || grid[r][c] == -1) return 0;
  if (grid[r][c] == 2) return remaining == 1 ? 1 : 0;
  int saved = grid[r][c];
  grid[r][c] = -1;
  int total = backtrackQ17Recursive(grid, r+1, c, remaining-1) + backtrackQ17Recursive(grid, r-1, c, remaining-1)
      + backtrackQ17Recursive(grid, r, c+1, remaining-1) + backtrackQ17Recursive(grid, r, c-1, remaining-1);
  grid[r][c] = saved;
  return total;
}
```

#### 18. Matchsticks to Square - [LeetCode 473](https://leetcode.com/problems/matchsticks-to-square/)

Given matchsticks, return true if they can form a square using every matchstick exactly once.

```java
public boolean q18MakesquareRecursive(int[] matchsticks) {
  int sum = 0;
  for (int stick : matchsticks) sum += stick;
  if (sum % 4 != 0) return false;
  Arrays.sort(matchsticks);
  reverseQ18Recursive(matchsticks);
  return backtrackQ18Recursive(matchsticks, 0, new int[4], sum / 4);
}

private boolean backtrackQ18Recursive(int[] sticks, int index, int[] sides, int target) {
  if (index == sticks.length) return true;
  for (int side = 0; side < 4; side++) {
    if (sides[side] + sticks[index] > target) continue;
    sides[side] += sticks[index];
    if (backtrackQ18Recursive(sticks, index + 1, sides, target)) return true;
    sides[side] -= sticks[index];
    if (sides[side] == 0) break;
  }
  return false;
}

private void reverseQ18Recursive(int[] nums) { for (int l = 0, r = nums.length - 1; l < r; l++, r--) { int t = nums[l]; nums[l] = nums[r]; nums[r] = t; } }
```

#### 19. Partition to K Equal Sum Subsets - [LeetCode 698](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/)

Given nums and k, return true if nums can be partitioned into k non-empty subsets with equal sum.

```java
public boolean q19CanPartitionKSubsetsRecursive(int[] nums, int k) {
  int sum = 0;
  for (int num : nums) sum += num;
  if (sum % k != 0) return false;
  Arrays.sort(nums);
  reverseQ19Recursive(nums);
  return backtrackQ19Recursive(nums, 0, new int[k], sum / k);
}

private boolean backtrackQ19Recursive(int[] nums, int index, int[] buckets, int target) {
  if (index == nums.length) return true;
  for (int i = 0; i < buckets.length; i++) {
    if (buckets[i] + nums[index] > target) continue;
    buckets[i] += nums[index];
    if (backtrackQ19Recursive(nums, index + 1, buckets, target)) return true;
    buckets[i] -= nums[index];
    if (buckets[i] == 0) break;
  }
  return false;
}

private void reverseQ19Recursive(int[] nums) { for (int l = 0, r = nums.length - 1; l < r; l++, r--) { int t = nums[l]; nums[l] = nums[r]; nums[r] = t; } }
```

#### 20. Beautiful Arrangement - [LeetCode 526](https://leetcode.com/problems/beautiful-arrangement/)

Given n, count the number of arrangements of 1..n where at position i, either perm[i] is divisible by i or i is divisible by perm[i].

```java
public int q20CountArrangementRecursive(int n) {
  int[] memo = new int[1 << n];
  Arrays.fill(memo, -1);
  return backtrackQ20Recursive(n, 1, 0, memo);
}

private int backtrackQ20Recursive(int n, int position, int mask, int[] memo) {
  if (position > n) return 1;
  if (memo[mask] != -1) return memo[mask];
  int count = 0;
  for (int num = 1; num <= n; num++) {
    int bit = 1 << (num - 1);
    if ((mask & bit) != 0) continue;
    if (num % position == 0 || position % num == 0) count += backtrackQ20Recursive(n, position + 1, mask | bit, memo);
  }
  memo[mask] = count;
  return count;
}
```

## Topic 19: Greedy

#### 13. Reorganize String - [LeetCode 767](https://leetcode.com/problems/reorganize-string/)

Given a string s, rearrange it so no two adjacent characters are equal, or return an empty string if impossible.

```java
public String q13ReorganizeStringRecursive(String s) {
  int[] count = new int[26];
  for (char c : s.toCharArray()) count[c - 'a']++;
  PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
  for (int i = 0; i < 26; i++) if (count[i] > 0) heap.offer(new int[] {i, count[i]});
  StringBuilder answer = new StringBuilder();
  buildQ13Recursive(heap, null, answer, s.length());
  return answer.length() == s.length() ? answer.toString() : "";
}

private void buildQ13Recursive(PriorityQueue<int[]> heap, int[] previous, StringBuilder answer, int total) {
  if (answer.length() == total || heap.isEmpty()) return;
  int[] current = heap.poll();
  answer.append((char) ('a' + current[0]));
  current[1]--;
  if (previous != null && previous[1] > 0) heap.offer(previous);
  buildQ13Recursive(heap, current, answer, total);
}
```

#### 14. Hand of Straights - [LeetCode 846](https://leetcode.com/problems/hand-of-straights/)

Given a hand of cards and groupSize, return true if the cards can be rearranged into groups of groupSize consecutive cards.

```java
public boolean q14IsNStraightHandRecursive(int[] hand, int groupSize) {
  if (hand.length % groupSize != 0) return false;
  TreeMap<Integer, Integer> count = new TreeMap<>();
  for (int card : hand) count.put(card, count.getOrDefault(card, 0) + 1);
  return consumeQ14Recursive(count, groupSize);
}

private boolean consumeQ14Recursive(TreeMap<Integer, Integer> count, int groupSize) {
  if (count.isEmpty()) return true;
  int start = count.firstKey();
  for (int card = start; card < start + groupSize; card++) {
    Integer freq = count.get(card);
    if (freq == null) return false;
    if (freq == 1) count.remove(card); else count.put(card, freq - 1);
  }
  return consumeQ14Recursive(count, groupSize);
}
```

#### 15. Boats to Save People - [LeetCode 881](https://leetcode.com/problems/boats-to-save-people/)

Given people weights and a boat limit where each boat carries at most two people, return the minimum number of boats needed.

```java
public int q15NumRescueBoatsRecursive(int[] people, int limit) {
  Arrays.sort(people);
  return boatsQ15Recursive(people, limit, 0, people.length - 1);
}

private int boatsQ15Recursive(int[] people, int limit, int left, int right) {
  if (left > right) return 0;
  if (people[left] + people[right] <= limit) return 1 + boatsQ15Recursive(people, limit, left + 1, right - 1);
  return 1 + boatsQ15Recursive(people, limit, left, right - 1);
}
```

#### 16. Two City Scheduling - [LeetCode 1029](https://leetcode.com/problems/two-city-scheduling/)

Given costs for flying each person to city A or B, send exactly n people to each city with minimum total cost.

```java
public int q16TwoCitySchedCostRecursive(int[][] costs) {
  Arrays.sort(costs, (a, b) -> Integer.compare(a[0] - a[1], b[0] - b[1]));
  return sumQ16Recursive(costs, 0, costs.length / 2);
}

private int sumQ16Recursive(int[][] costs, int index, int half) {
  if (index == costs.length) return 0;
  int cost = index < half ? costs[index][0] : costs[index][1];
  return cost + sumQ16Recursive(costs, index + 1, half);
}
```

#### 17. Maximum Subarray - [LeetCode 53](https://leetcode.com/problems/maximum-subarray/)

Given an integer array, return the largest possible sum of a non-empty contiguous subarray.

```java
public int q17MaxSubArrayRecursive(int[] nums) {
  return solveQ17Recursive(nums, 0, nums.length - 1);
}

private int solveQ17Recursive(int[] nums, int left, int right) {
  if (left == right) return nums[left];
  int mid = left + (right - left) / 2;
  int bestLeft = solveQ17Recursive(nums, left, mid);
  int bestRight = solveQ17Recursive(nums, mid + 1, right);
  int sum = 0;
  int crossLeft = Integer.MIN_VALUE;
  for (int i = mid; i >= left; i--) {
    sum += nums[i];
    crossLeft = Math.max(crossLeft, sum);
  }
  sum = 0;
  int crossRight = Integer.MIN_VALUE;
  for (int i = mid + 1; i <= right; i++) {
    sum += nums[i];
    crossRight = Math.max(crossRight, sum);
  }
  return Math.max(Math.max(bestLeft, bestRight), crossLeft + crossRight);
}
```

#### 18. Best Time to Buy and Sell Stock II - [LeetCode 122](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)

Given daily stock prices, return the maximum profit with as many buy-sell transactions as desired, holding at most one share at a time.

```java
public int q18MaxProfitRecursive(int[] prices) {
  return collectQ18Recursive(prices, 1);
}

private int collectQ18Recursive(int[] prices, int index) {
  if (index == prices.length) return 0;
  int gain = Math.max(0, prices[index] - prices[index - 1]);
  return gain + collectQ18Recursive(prices, index + 1);
}
```

#### 19. Can Place Flowers - [LeetCode 605](https://leetcode.com/problems/can-place-flowers/)

Given a flowerbed of 0s and 1s and an integer n, return true if n new flowers can be planted without adjacent flowers.

```java
public boolean q19CanPlaceFlowersRecursive(int[] flowerbed, int n) {
  return plantQ19Recursive(flowerbed, 0, n);
}

private boolean plantQ19Recursive(int[] bed, int index, int leftToPlant) {
  if (leftToPlant <= 0) return true;
  if (index == bed.length) return false;
  boolean leftEmpty = index == 0 || bed[index - 1] == 0;
  boolean rightEmpty = index + 1 == bed.length || bed[index + 1] == 0;
  if (bed[index] == 0 && leftEmpty && rightEmpty) {
    bed[index] = 1;
    return plantQ19Recursive(bed, index + 1, leftToPlant - 1);
  }
  return plantQ19Recursive(bed, index + 1, leftToPlant);
}
```

#### 20. Increasing Triplet Subsequence - [LeetCode 334](https://leetcode.com/problems/increasing-triplet-subsequence/)

Given an integer array, return true if there exists a strictly increasing subsequence of length three.

```java
public boolean q20IncreasingTripletRecursive(int[] nums) {
  return scanQ20Recursive(nums, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
}

private boolean scanQ20Recursive(int[] nums, int index, int first, int second) {
  if (index == nums.length) return false;
  int num = nums[index];
  if (num <= first) return scanQ20Recursive(nums, index + 1, num, second);
  if (num <= second) return scanQ20Recursive(nums, index + 1, first, num);
  return true;
}
```

## Topic 17: 1D Dynamic Programming

#### 13. Perfect Squares - [LeetCode 279](https://leetcode.com/problems/perfect-squares/)

Given n, return the minimum number of perfect square numbers whose sum is n.

```java
public int q13NumSquaresRecursive(int n) {
  int[] memo = new int[n + 1];
  Arrays.fill(memo, -1);
  return dpQ13Recursive(n, memo);
}

private int dpQ13Recursive(int n, int[] memo) {
  if (n == 0) return 0;
  if (memo[n] != -1) return memo[n];
  int best = n;
  for (int square = 1; square * square <= n; square++) {
    best = Math.min(best, 1 + dpQ13Recursive(n - square * square, memo));
  }
  memo[n] = best;
  return best;
}
```

#### 14. Integer Break - [LeetCode 343](https://leetcode.com/problems/integer-break/)

Given integer n, break it into at least two positive integers and maximize the product of those integers.

```java
public int q14IntegerBreakRecursive(int n) {
  int[] memo = new int[n + 1];
  return dpQ14Recursive(n, memo);
}

private int dpQ14Recursive(int n, int[] memo) {
  if (n == 1) return 0;
  if (memo[n] != 0) return memo[n];
  int best = 0;
  for (int first = 1; first < n; first++) {
    best = Math.max(best, first * Math.max(n - first, dpQ14Recursive(n - first, memo)));
  }
  memo[n] = best;
  return best;
}
```

#### 15. Maximum Product Subarray - [LeetCode 152](https://leetcode.com/problems/maximum-product-subarray/)

Given an integer array, return the maximum product of a non-empty contiguous subarray.

```java
public int q15MaxProductRecursive(int[] nums) {
  return scanQ15Recursive(nums, 1, nums[0], nums[0], nums[0]);
}

private int scanQ15Recursive(int[] nums, int index, int maxEnding, int minEnding, int best) {
  if (index == nums.length) return best;
  int value = nums[index];
  if (value < 0) {
    int temp = maxEnding;
    maxEnding = minEnding;
    minEnding = temp;
  }
  int nextMax = Math.max(value, maxEnding * value);
  int nextMin = Math.min(value, minEnding * value);
  return scanQ15Recursive(nums, index + 1, nextMax, nextMin, Math.max(best, nextMax));
}
```

#### 16. Maximum Subarray - [LeetCode 53](https://leetcode.com/problems/maximum-subarray/)

Given an integer array, return the largest sum of any non-empty contiguous subarray.

```java
public int q16MaxSubArrayRecursive(int[] nums) {
  return scanQ16Recursive(nums, 1, nums[0], nums[0]);
}

private int scanQ16Recursive(int[] nums, int index, int current, int best) {
  if (index == nums.length) return best;
  int next = Math.max(nums[index], current + nums[index]);
  return scanQ16Recursive(nums, index + 1, next, Math.max(best, next));
}
```

#### 17. Best Time to Buy and Sell Stock - [LeetCode 121](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)

Given prices where prices[i] is the stock price on day i, return the maximum profit from one buy followed by one sell.

```java
public int q17MaxProfitRecursive(int[] prices) {
  return scanQ17Recursive(prices, 0, Integer.MAX_VALUE, 0);
}

private int scanQ17Recursive(int[] prices, int index, int minPrice, int best) {
  if (index == prices.length) return best;
  int nextMin = Math.min(minPrice, prices[index]);
  int nextBest = Math.max(best, prices[index] - nextMin);
  return scanQ17Recursive(prices, index + 1, nextMin, nextBest);
}
```

#### 18. Best Time to Buy and Sell Stock with Cooldown - [LeetCode 309](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

Given daily stock prices, return the maximum profit with unlimited transactions and a one-day cooldown after selling.

```java
public int q18MaxProfitRecursive(int[] prices) {
  Integer[][] memo = new Integer[prices.length][2];
  return dpQ18Recursive(prices, 0, 0, memo);
}

private int dpQ18Recursive(int[] prices, int day, int holding, Integer[][] memo) {
  if (day >= prices.length) return 0;
  if (memo[day][holding] != null) return memo[day][holding];
  int skip = dpQ18Recursive(prices, day + 1, holding, memo);
  int best;
  if (holding == 1) {
    best = Math.max(skip, prices[day] + dpQ18Recursive(prices, day + 2, 0, memo));
  } else {
    best = Math.max(skip, -prices[day] + dpQ18Recursive(prices, day + 1, 1, memo));
  }
  memo[day][holding] = best;
  return best;
}
```

#### 19. Best Time to Buy and Sell Stock with Transaction Fee - [LeetCode 714](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/)

Given daily stock prices and a transaction fee, return maximum profit with unlimited transactions where each sale pays the fee.

```java
public int q19MaxProfitRecursive(int[] prices, int fee) {
  Integer[][] memo = new Integer[prices.length][2];
  return dpQ19Recursive(prices, fee, 0, 0, memo);
}

private int dpQ19Recursive(int[] prices, int fee, int day, int holding, Integer[][] memo) {
  if (day == prices.length) return 0;
  if (memo[day][holding] != null) return memo[day][holding];
  int skip = dpQ19Recursive(prices, fee, day + 1, holding, memo);
  int best;
  if (holding == 1) {
    best = Math.max(skip, prices[day] - fee + dpQ19Recursive(prices, fee, day + 1, 0, memo));
  } else {
    best = Math.max(skip, -prices[day] + dpQ19Recursive(prices, fee, day + 1, 1, memo));
  }
  memo[day][holding] = best;
  return best;
}
```

#### 20. Delete and Earn - [LeetCode 740](https://leetcode.com/problems/delete-and-earn/)

Given nums, you may take a value x to earn x points per occurrence, but then all x - 1 and x + 1 values are deleted. Return maximum points.

```java
public int q20DeleteAndEarnRecursive(int[] nums) {
  int max = 0;
  for (int num : nums) max = Math.max(max, num);
  int[] points = new int[max + 1];
  for (int num : nums) points[num] += num;
  int[] memo = new int[max + 1];
  Arrays.fill(memo, -1);
  return dpQ20Recursive(points, 1, memo);
}

private int dpQ20Recursive(int[] points, int value, int[] memo) {
  if (value >= points.length) return 0;
  if (memo[value] != -1) return memo[value];
  int take = points[value] + dpQ20Recursive(points, value + 2, memo);
  int skip = dpQ20Recursive(points, value + 1, memo);
  memo[value] = Math.max(take, skip);
  return memo[value];
}
```

## Topic 18: 2D Dynamic Programming

#### 13. Palindromic Substrings - [LeetCode 647](https://leetcode.com/problems/palindromic-substrings/)

Given a string s, return the number of contiguous substrings that are palindromes.

```java
public int q13CountSubstringsRecursive(String s) {
  int n = s.length();
  Boolean[][] memo = new Boolean[n][n];
  int count = 0;
  for (int left = 0; left < n; left++) {
    for (int right = left; right < n; right++) {
      if (isPalindromeQ13Recursive(s, left, right, memo)) count++;
    }
  }
  return count;
}

private boolean isPalindromeQ13Recursive(String s, int left, int right, Boolean[][] memo) {
  if (left >= right) return true;
  if (memo[left][right] != null) return memo[left][right];
  memo[left][right] = s.charAt(left) == s.charAt(right) && isPalindromeQ13Recursive(s, left + 1, right - 1, memo);
  return memo[left][right];
}
```

#### 14. Coin Change II - [LeetCode 518](https://leetcode.com/problems/coin-change-ii/)

Given coin denominations and an amount, return the number of combinations that make the amount. Each coin can be used unlimited times.

```java
public int q14ChangeRecursive(int amount, int[] coins) {
  int[][] memo = new int[coins.length][amount + 1];
  for (int[] row : memo) Arrays.fill(row, -1);
  return dpQ14Recursive(coins, 0, amount, memo);
}

private int dpQ14Recursive(int[] coins, int index, int amount, int[][] memo) {
  if (amount == 0) return 1;
  if (index == coins.length || amount < 0) return 0;
  if (memo[index][amount] != -1) return memo[index][amount];
  memo[index][amount] = dpQ14Recursive(coins, index + 1, amount, memo) + dpQ14Recursive(coins, index, amount - coins[index], memo);
  return memo[index][amount];
}
```

#### 15. Target Sum - [LeetCode 494](https://leetcode.com/problems/target-sum/)

Given nums and target, assign either plus or minus before every number. Return how many assignments evaluate to target.

```java
public int q15FindTargetSumWaysRecursive(int[] nums, int target) {
  int total = 0;
  for (int num : nums) total += num;
  if (Math.abs(target) > total) return 0;
  Integer[][] memo = new Integer[nums.length][2 * total + 1];
  return dpQ15Recursive(nums, 0, 0, target, total, memo);
}

private int dpQ15Recursive(int[] nums, int index, int sum, int target, int offset, Integer[][] memo) {
  if (index == nums.length) return sum == target ? 1 : 0;
  int key = sum + offset;
  if (memo[index][key] != null) return memo[index][key];
  int plus = dpQ15Recursive(nums, index + 1, sum + nums[index], target, offset, memo);
  int minus = dpQ15Recursive(nums, index + 1, sum - nums[index], target, offset, memo);
  memo[index][key] = plus + minus;
  return memo[index][key];
}
```

#### 16. Ones and Zeroes - [LeetCode 474](https://leetcode.com/problems/ones-and-zeroes/)

Given binary strings strs and limits m zeros and n ones, return the maximum number of strings that can be chosen without exceeding either limit.

```java
public int q16FindMaxFormRecursive(String[] strs, int m, int n) {
  int[][][] memo = new int[strs.length][m + 1][n + 1];
  for (int[][] layer : memo) {
    for (int[] row : layer) Arrays.fill(row, -1);
  }
  return dpQ16Recursive(strs, 0, m, n, memo);
}

private int dpQ16Recursive(String[] strs, int index, int zerosLeft, int onesLeft, int[][][] memo) {
  if (index == strs.length) return 0;
  if (memo[index][zerosLeft][onesLeft] != -1) return memo[index][zerosLeft][onesLeft];
  int skip = dpQ16Recursive(strs, index + 1, zerosLeft, onesLeft, memo);
  int[] cost = countQ16Recursive(strs[index]);
  int take = 0;
  if (cost[0] <= zerosLeft && cost[1] <= onesLeft) {
    take = 1 + dpQ16Recursive(strs, index + 1, zerosLeft - cost[0], onesLeft - cost[1], memo);
  }
  memo[index][zerosLeft][onesLeft] = Math.max(skip, take);
  return memo[index][zerosLeft][onesLeft];
}

private int[] countQ16Recursive(String s) {
  int zeros = 0;
  for (char c : s.toCharArray()) if (c == '0') zeros++;
  return new int[] {zeros, s.length() - zeros};
}
```

#### 17. Last Stone Weight II - [LeetCode 1049](https://leetcode.com/problems/last-stone-weight-ii/)

Given stone weights, repeatedly smashing stones is equivalent to splitting stones into two groups. Return the minimum possible remaining weight.

```java
public int q17LastStoneWeightIIRecursive(int[] stones) {
  int total = 0;
  for (int stone : stones) total += stone;
  int capacity = total / 2;
  Integer[][] memo = new Integer[stones.length][capacity + 1];
  int best = bestSubsetQ17Recursive(stones, 0, capacity, memo);
  return total - 2 * best;
}

private int bestSubsetQ17Recursive(int[] stones, int index, int capacity, Integer[][] memo) {
  if (index == stones.length || capacity == 0) return 0;
  if (memo[index][capacity] != null) return memo[index][capacity];
  int skip = bestSubsetQ17Recursive(stones, index + 1, capacity, memo);
  int take = 0;
  if (stones[index] <= capacity) {
    take = stones[index] + bestSubsetQ17Recursive(stones, index + 1, capacity - stones[index], memo);
  }
  memo[index][capacity] = Math.max(skip, take);
  return memo[index][capacity];
}
```

#### 18. Partition Equal Subset Sum - [LeetCode 416](https://leetcode.com/problems/partition-equal-subset-sum/)

Given nums, return true if the array can be split into two subsets with equal sum.

```java
public boolean q18CanPartitionRecursive(int[] nums) {
  int total = 0;
  for (int num : nums) total += num;
  if (total % 2 == 1) return false;
  Boolean[][] memo = new Boolean[nums.length][total / 2 + 1];
  return dpQ18Recursive(nums, 0, total / 2, memo);
}

private boolean dpQ18Recursive(int[] nums, int index, int target, Boolean[][] memo) {
  if (target == 0) return true;
  if (index == nums.length || target < 0) return false;
  if (memo[index][target] != null) return memo[index][target];
  boolean skip = dpQ18Recursive(nums, index + 1, target, memo);
  boolean take = target >= nums[index] && dpQ18Recursive(nums, index + 1, target - nums[index], memo);
  memo[index][target] = skip || take;
  return memo[index][target];
}
```

#### 19. Longest Increasing Path in a Matrix - [LeetCode 329](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)

Given an integer matrix, return the length of the longest path where each next cell is strictly larger and movement is allowed in four directions.

```java
private static final int[][] DIRSQ19Recursive = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

public int q19LongestIncreasingPathRecursive(int[][] matrix) {
  int rows = matrix.length;
  int cols = matrix[0].length;
  int[][] memo = new int[rows][cols];
  int best = 0;
  for (int row = 0; row < rows; row++) {
    for (int col = 0; col < cols; col++) {
      best = Math.max(best, dpQ19Recursive(matrix, row, col, memo));
    }
  }
  return best;
}

private int dpQ19Recursive(int[][] matrix, int row, int col, int[][] memo) {
  if (memo[row][col] != 0) return memo[row][col];
  int best = 1;
  for (int[] dir : DIRSQ19Recursive) {
    int nextRow = row + dir[0];
    int nextCol = col + dir[1];
    if (nextRow < 0 || nextCol < 0 || nextRow == matrix.length || nextCol == matrix[0].length) continue;
    if (matrix[nextRow][nextCol] > matrix[row][col]) {
      best = Math.max(best, 1 + dpQ19Recursive(matrix, nextRow, nextCol, memo));
    }
  }
  memo[row][col] = best;
  return best;
}
```

#### 20. Maximal Square - [LeetCode 221](https://leetcode.com/problems/maximal-square/)

Given a binary matrix of characters, return the area of the largest square containing only 1s.

```java
public int q20MaximalSquareRecursive(char[][] matrix) {
  int bestSide = 0;
  int rows = matrix.length;
  int cols = matrix[0].length;
  int[][] memo = new int[rows][cols];
  for (int[] row : memo) Arrays.fill(row, -1);
  for (int row = 0; row < rows; row++) {
    for (int col = 0; col < cols; col++) {
      bestSide = Math.max(bestSide, dpQ20Recursive(matrix, row, col, memo));
    }
  }
  return bestSide * bestSide;
}

private int dpQ20Recursive(char[][] matrix, int row, int col, int[][] memo) {
  if (row == matrix.length || col == matrix[0].length) return 0;
  if (memo[row][col] != -1) return memo[row][col];
  int down = dpQ20Recursive(matrix, row + 1, col, memo);
  int right = dpQ20Recursive(matrix, row, col + 1, memo);
  int diagonal = dpQ20Recursive(matrix, row + 1, col + 1, memo);
  memo[row][col] = matrix[row][col] == '1' ? 1 + Math.min(down, Math.min(right, diagonal)) : 0;
  return memo[row][col];
}
```

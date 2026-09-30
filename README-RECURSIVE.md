# Recursion - Recursive Questions and Solutions

## Topic 10: Recursion

### Basic (1-12)

#### 1. Fibonacci Number - [LeetCode 509](https://leetcode.com/problems/fibonacci-number/)

Given n, return the nth Fibonacci number where F(0) = 0, F(1) = 1, and F(n) = F(n - 1) + F(n - 2).

```java
public int q01FibRecursive(int n) {
  int[] memo = new int[n + 1];
  Arrays.fill(memo, -1);
  return dfsQ1Recursive(n, memo);
}

private int dfsQ1Recursive(int n, int[] memo) {
  if (n <= 1) return n;
  if (memo[n] != -1) return memo[n];
  memo[n] = dfsQ1Recursive(n - 1, memo) + dfsQ1Recursive(n - 2, memo);
  return memo[n];
}
```

#### 2. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

You are climbing a staircase with n steps. Each move can climb 1 or 2 steps. Return the number of distinct ways to reach the top.

```java
public int q02ClimbStairsRecursive(int n) {
  int[] memo = new int[n + 1];
  Arrays.fill(memo, -1);
  return waysQ2Recursive(0, n, memo);
}

private int waysQ2Recursive(int step, int n, int[] memo) {
  if (step == n) return 1;
  if (step > n) return 0;
  if (memo[step] != -1) return memo[step];
  memo[step] = waysQ2Recursive(step + 1, n, memo) + waysQ2Recursive(step + 2, n, memo);
  return memo[step];
}
```

#### 3. Pow(x, n) - [LeetCode 50](https://leetcode.com/problems/powx-n/)

Implement pow(x, n), which calculates x raised to the power n.

```java
public double q03MyPowRecursive(double x, int n) {
  long power = n;
  if (power < 0) return 1.0 / fastPowQ3Recursive(x, -power);
  return fastPowQ3Recursive(x, power);
}

private double fastPowQ3Recursive(double x, long power) {
  if (power == 0) return 1.0;
  double half = fastPowQ3Recursive(x, power / 2);
  double squared = half * half;
  return power % 2 == 0 ? squared : squared * x;
}
```

#### 4. Reverse String - [LeetCode 344](https://leetcode.com/problems/reverse-string/)

Given a character array s, reverse it in-place.

```java
public void q04ReverseStringRecursive(char[] s) {
  reverseQ4Recursive(s, 0, s.length - 1);
}

private void reverseQ4Recursive(char[] s, int left, int right) {
  if (left >= right) return;

  char temp = s[left];
  s[left] = s[right];
  s[right] = temp;
  reverseQ4Recursive(s, left + 1, right - 1);
}
```

#### 5. Swap Nodes in Pairs - [LeetCode 24](https://leetcode.com/problems/swap-nodes-in-pairs/)

Given a linked list, swap every two adjacent nodes and return its head. Node values must not be modified.

```java
public ListNode q05SwapPairsRecursive(ListNode head) {
  if (head == null || head.next == null) return head;

  ListNode second = head.next;
  head.next = q05SwapPairsRecursive(second.next);
  second.next = head;
  return second;
}
```

#### 6. Merge Two Sorted Lists - [LeetCode 21](https://leetcode.com/problems/merge-two-sorted-lists/)

Given the heads of two sorted linked lists, merge them into one sorted linked list and return its head.

```java
public ListNode q06MergeTwoListsRecursive(ListNode list1, ListNode list2) {
  if (list1 == null) return list2;
  if (list2 == null) return list1;

  if (list1.val <= list2.val) {
    list1.next = q06MergeTwoListsRecursive(list1.next, list2);
    return list1;
  }

  list2.next = q06MergeTwoListsRecursive(list1, list2.next);
  return list2;
}
```

#### 7. K-th Symbol in Grammar - [LeetCode 779](https://leetcode.com/problems/k-th-symbol-in-grammar/)

In row 1 the grammar is 0. Each 0 becomes 01 and each 1 becomes 10. Return the kth symbol in row n.

```java
public int q07KthGrammarRecursive(int n, int k) {
  if (n == 1) return 0;

  int parent = q07KthGrammarRecursive(n - 1, (k + 1) / 2);
  if (k % 2 == 1) return parent;
  return 1 - parent;
}
```

#### 8. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
public List<String> q08GenerateParenthesisRecursive(int n) {
  List<String> answer = new ArrayList<>();
  backtrackQ8Recursive(n, 0, 0, new StringBuilder(), answer);
  return answer;
}

private void backtrackQ8Recursive(int n, int open, int close, StringBuilder path, List<String> answer) {
  if (path.length() == 2 * n) {
    answer.add(path.toString());
    return;
  }

  if (open < n) {
    path.append('(');
    backtrackQ8Recursive(n, open + 1, close, path, answer);
    path.deleteCharAt(path.length() - 1);
  }
  if (close < open) {
    path.append(')');
    backtrackQ8Recursive(n, open, close + 1, path, answer);
    path.deleteCharAt(path.length() - 1);
  }
}
```

#### 9. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets of nums.

```java
public List<List<Integer>> q09SubsetsRecursive(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  dfsQ9Recursive(nums, 0, new ArrayList<>(), answer);
  return answer;
}

private void dfsQ9Recursive(int[] nums, int index, List<Integer> path, List<List<Integer>> answer) {
  if (index == nums.length) {
    answer.add(new ArrayList<>(path));
    return;
  }

  dfsQ9Recursive(nums, index + 1, path, answer);
  path.add(nums[index]);
  dfsQ9Recursive(nums, index + 1, path, answer);
  path.remove(path.size() - 1);
}
```

#### 10. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
public List<List<Integer>> q10PermuteRecursive(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  dfsQ10Recursive(nums, new boolean[nums.length], new ArrayList<>(), answer);
  return answer;
}

private void dfsQ10Recursive(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> answer) {
  if (path.size() == nums.length) {
    answer.add(new ArrayList<>(path));
    return;
  }

  for (int i = 0; i < nums.length; i++) {
    if (used[i]) continue;
    used[i] = true;
    path.add(nums[i]);
    dfsQ10Recursive(nums, used, path, answer);
    path.remove(path.size() - 1);
    used[i] = false;
  }
}
```

#### 11. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
private static final String[] MAPQ11Recursive = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

public List<String> q11LetterCombinationsRecursive(String digits) {
  List<String> answer = new ArrayList<>();
  if (digits.length() == 0) return answer;
  dfsQ11Recursive(digits, 0, new StringBuilder(), answer);
  return answer;
}

private void dfsQ11Recursive(String digits, int index, StringBuilder path, List<String> answer) {
  if (index == digits.length()) {
    answer.add(path.toString());
    return;
  }

  for (char ch : MAPQ11Recursive[digits.charAt(index) - '0'].toCharArray()) {
    path.append(ch);
    dfsQ11Recursive(digits, index + 1, path, answer);
    path.deleteCharAt(path.length() - 1);
  }
}
```

#### 12. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given integers n and k, return all possible combinations of k numbers chosen from the range 1 to n.

```java
public List<List<Integer>> q12CombineRecursive(int n, int k) {
  List<List<Integer>> answer = new ArrayList<>();
  dfsQ12Recursive(1, n, k, new ArrayList<>(), answer);
  return answer;
}

private void dfsQ12Recursive(int start, int n, int k, List<Integer> path, List<List<Integer>> answer) {
  if (path.size() == k) {
    answer.add(new ArrayList<>(path));
    return;
  }

  int need = k - path.size();
  for (int num = start; num <= n - need + 1; num++) {
    path.add(num);
    dfsQ12Recursive(num + 1, n, k, path, answer);
    path.remove(path.size() - 1);
  }
}
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets. The solution set must not contain duplicate subsets.

```java
public List<List<Integer>> q01SubsetsRecursive(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ1Recursive(nums, 0, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ1Recursive(int[] nums, int start, List<Integer> path, List<List<Integer>> answer) {
  answer.add(new ArrayList<>(path));
  for (int i = start; i < nums.length; i++) {
    path.add(nums[i]);
    backtrackQ1Recursive(nums, i + 1, path, answer);
    path.remove(path.size() - 1);
  }
}
```

#### 2. Subsets II - [LeetCode 90](https://leetcode.com/problems/subsets-ii/)

Given an integer array nums that may contain duplicates, return all possible subsets without duplicate subsets.

```java
public List<List<Integer>> q02SubsetsWithDupRecursive(int[] nums) {
  Arrays.sort(nums);
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ2Recursive(nums, 0, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ2Recursive(int[] nums, int start, List<Integer> path, List<List<Integer>> answer) {
  answer.add(new ArrayList<>(path));
  for (int i = start; i < nums.length; i++) {
    if (i > start && nums[i] == nums[i - 1]) continue;
    path.add(nums[i]);
    backtrackQ2Recursive(nums, i + 1, path, answer);
    path.remove(path.size() - 1);
  }
}
```

#### 3. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
public List<List<Integer>> q03PermuteRecursive(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ3Recursive(nums, new boolean[nums.length], new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ3Recursive(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> answer) {
  if (path.size() == nums.length) {
    answer.add(new ArrayList<>(path));
    return;
  }
  for (int i = 0; i < nums.length; i++) {
    if (used[i]) continue;
    used[i] = true;
    path.add(nums[i]);
    backtrackQ3Recursive(nums, used, path, answer);
    path.remove(path.size() - 1);
    used[i] = false;
  }
}
```

#### 4. Permutations II - [LeetCode 47](https://leetcode.com/problems/permutations-ii/)

Given a collection of numbers nums that may contain duplicates, return all unique permutations.

```java
public List<List<Integer>> q04PermuteUniqueRecursive(int[] nums) {
  Arrays.sort(nums);
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ4Recursive(nums, new boolean[nums.length], new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ4Recursive(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> answer) {
  if (path.size() == nums.length) {
    answer.add(new ArrayList<>(path));
    return;
  }
  for (int i = 0; i < nums.length; i++) {
    if (used[i]) continue;
    if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
    used[i] = true;
    path.add(nums[i]);
    backtrackQ4Recursive(nums, used, path, answer);
    path.remove(path.size() - 1);
    used[i] = false;
  }
}
```

#### 5. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given n and k, return all possible combinations of k numbers chosen from 1 to n.

```java
public List<List<Integer>> q05CombineRecursive(int n, int k) {
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ5Recursive(1, n, k, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ5Recursive(int start, int n, int k, List<Integer> path, List<List<Integer>> answer) {
  if (path.size() == k) {
    answer.add(new ArrayList<>(path));
    return;
  }
  int need = k - path.size();
  for (int num = start; num <= n - need + 1; num++) {
    path.add(num);
    backtrackQ5Recursive(num + 1, n, k, path, answer);
    path.remove(path.size() - 1);
  }
}
```

#### 6. Combination Sum - [LeetCode 39](https://leetcode.com/problems/combination-sum/)

Given distinct candidates and a target, return all unique combinations where chosen numbers sum to target. The same number may be chosen unlimited times.

```java
public List<List<Integer>> q06CombinationSumRecursive(int[] candidates, int target) {
  Arrays.sort(candidates);
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ6Recursive(candidates, 0, target, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ6Recursive(int[] candidates, int start, int remaining, List<Integer> path, List<List<Integer>> answer) {
  if (remaining == 0) {
    answer.add(new ArrayList<>(path));
    return;
  }
  for (int i = start; i < candidates.length && candidates[i] <= remaining; i++) {
    path.add(candidates[i]);
    backtrackQ6Recursive(candidates, i, remaining - candidates[i], path, answer);
    path.remove(path.size() - 1);
  }
}
```

#### 7. Combination Sum II - [LeetCode 40](https://leetcode.com/problems/combination-sum-ii/)

Given candidates that may contain duplicates and a target, return all unique combinations where each candidate may be used at most once.

```java
public List<List<Integer>> q07CombinationSum2Recursive(int[] candidates, int target) {
  Arrays.sort(candidates);
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ7Recursive(candidates, 0, target, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ7Recursive(int[] candidates, int start, int remaining, List<Integer> path, List<List<Integer>> answer) {
  if (remaining == 0) { answer.add(new ArrayList<>(path)); return; }
  for (int i = start; i < candidates.length && candidates[i] <= remaining; i++) {
    if (i > start && candidates[i] == candidates[i - 1]) continue;
    path.add(candidates[i]);
    backtrackQ7Recursive(candidates, i + 1, remaining - candidates[i], path, answer);
    path.remove(path.size() - 1);
  }
}
```

#### 8. Combination Sum III - [LeetCode 216](https://leetcode.com/problems/combination-sum-iii/)

Find all valid combinations of k numbers that sum to n using only numbers 1 through 9, where each number is used at most once.

```java
public List<List<Integer>> q08CombinationSum3Recursive(int k, int n) {
  List<List<Integer>> answer = new ArrayList<>();
  backtrackQ8Recursive(1, k, n, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ8Recursive(int start, int k, int remaining, List<Integer> path, List<List<Integer>> answer) {
  if (path.size() == k) {
    if (remaining == 0) answer.add(new ArrayList<>(path));
    return;
  }
  for (int num = start; num <= 9 && num <= remaining; num++) {
    path.add(num);
    backtrackQ8Recursive(num + 1, k, remaining - num, path, answer);
    path.remove(path.size() - 1);
  }
}
```

#### 9. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
public List<String> q09GenerateParenthesisRecursive(int n) {
  List<String> answer = new ArrayList<>();
  backtrackQ9Recursive(n, 0, 0, new StringBuilder(), answer);
  return answer;
}

private void backtrackQ9Recursive(int n, int open, int close, StringBuilder path, List<String> answer) {
  if (path.length() == 2 * n) { answer.add(path.toString()); return; }
  if (open < n) {
    path.append('(');
    backtrackQ9Recursive(n, open + 1, close, path, answer);
    path.deleteCharAt(path.length() - 1);
  }
  if (close < open) {
    path.append(')');
    backtrackQ9Recursive(n, open, close + 1, path, answer);
    path.deleteCharAt(path.length() - 1);
  }
}
```

#### 10. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
private static final String[] MAPQ10Recursive = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

public List<String> q10LetterCombinationsRecursive(String digits) {
  List<String> answer = new ArrayList<>();
  if (digits.length() == 0) return answer;
  backtrackQ10Recursive(digits, 0, new StringBuilder(), answer);
  return answer;
}

private void backtrackQ10Recursive(String digits, int index, StringBuilder path, List<String> answer) {
  if (index == digits.length()) { answer.add(path.toString()); return; }
  for (char ch : MAPQ10Recursive[digits.charAt(index) - '0'].toCharArray()) {
    path.append(ch);
    backtrackQ10Recursive(digits, index + 1, path, answer);
    path.deleteCharAt(path.length() - 1);
  }
}
```

#### 11. Palindrome Partitioning - [LeetCode 131](https://leetcode.com/problems/palindrome-partitioning/)

Given a string s, partition it so that every substring in the partition is a palindrome. Return all possible palindrome partitionings.

```java
public List<List<String>> q11PartitionRecursive(String s) {
  List<List<String>> answer = new ArrayList<>();
  boolean[][] pal = buildTableQ11Recursive(s);
  backtrackQ11Recursive(s, 0, pal, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ11Recursive(String s, int index, boolean[][] pal, List<String> path, List<List<String>> answer) {
  if (index == s.length()) { answer.add(new ArrayList<>(path)); return; }
  for (int end = index; end < s.length(); end++) {
    if (!pal[index][end]) continue;
    path.add(s.substring(index, end + 1));
    backtrackQ11Recursive(s, end + 1, pal, path, answer);
    path.remove(path.size() - 1);
  }
}

private boolean[][] buildTableQ11Recursive(String s) {
  int n = s.length(); boolean[][] pal = new boolean[n][n];
  for (int len = 1; len <= n; len++) for (int l = 0; l + len - 1 < n; l++) {
    int r = l + len - 1;
    pal[l][r] = s.charAt(l) == s.charAt(r) && (len <= 2 || pal[l + 1][r - 1]);
  }
  return pal;
}
```

#### 12. Restore IP Addresses - [LeetCode 93](https://leetcode.com/problems/restore-ip-addresses/)

Given a string containing only digits, return all possible valid IP addresses that can be formed by inserting three dots.

```java
public List<String> q12RestoreIpAddressesRecursive(String s) {
  List<String> answer = new ArrayList<>();
  backtrackQ12Recursive(s, 0, new ArrayList<>(), answer);
  return answer;
}

private void backtrackQ12Recursive(String s, int index, List<String> parts, List<String> answer) {
  if (parts.size() == 4) {
    if (index == s.length()) answer.add(String.join(".", parts));
    return;
  }
  for (int len = 1; len <= 3 && index + len <= s.length(); len++) {
    String part = s.substring(index, index + len);
    if (!validQ12Recursive(part)) continue;
    parts.add(part);
    backtrackQ12Recursive(s, index + len, parts, answer);
    parts.remove(parts.size() - 1);
  }
}

private boolean validQ12Recursive(String part) {
  return !(part.length() > 1 && part.charAt(0) == '0') && Integer.parseInt(part) <= 255;
}
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Assign Cookies - [LeetCode 455](https://leetcode.com/problems/assign-cookies/)

Given children greed factors and cookie sizes, return the maximum number of children that can receive one cookie with size at least their greed.

```java
public int q01FindContentChildrenRecursive(int[] g, int[] s) {
  Arrays.sort(g);
  Arrays.sort(s);
  return matchQ1Recursive(g, s, 0, 0);
}

private int matchQ1Recursive(int[] greed, int[] cookies, int child, int cookie) {
  if (child == greed.length || cookie == cookies.length) return 0;
  if (cookies[cookie] >= greed[child]) {
    return 1 + matchQ1Recursive(greed, cookies, child + 1, cookie + 1);
  }
  return matchQ1Recursive(greed, cookies, child, cookie + 1);
}
```

#### 2. Lemonade Change - [LeetCode 860](https://leetcode.com/problems/lemonade-change/)

Customers pay in order with 5, 10, or 20 dollar bills for 5 dollar lemonade. Return true if exact change can be given to every customer.

```java
public boolean q02LemonadeChangeRecursive(int[] bills) {
  return scanQ2Recursive(bills, 0, 0, 0);
}

private boolean scanQ2Recursive(int[] bills, int index, int five, int ten) {
  if (index == bills.length) return true;
  int bill = bills[index];
  if (bill == 5) return scanQ2Recursive(bills, index + 1, five + 1, ten);
  if (bill == 10) return five > 0 && scanQ2Recursive(bills, index + 1, five - 1, ten + 1);
  if (ten > 0 && five > 0) return scanQ2Recursive(bills, index + 1, five - 1, ten - 1);
  return five >= 3 && scanQ2Recursive(bills, index + 1, five - 3, ten);
}
```

#### 3. Jump Game - [LeetCode 55](https://leetcode.com/problems/jump-game/)

Given nums where nums[i] is the maximum jump length from index i, return true if the last index is reachable from index 0.

```java
public boolean q03CanJumpRecursive(int[] nums) {
  Boolean[] memo = new Boolean[nums.length];
  return canReachQ3Recursive(nums, 0, memo);
}

private boolean canReachQ3Recursive(int[] nums, int index, Boolean[] memo) {
  if (index >= nums.length - 1) return true;
  if (memo[index] != null) return memo[index];
  int limit = Math.min(nums.length - 1, index + nums[index]);
  for (int next = index + 1; next <= limit; next++) {
    if (canReachQ3Recursive(nums, next, memo)) return memo[index] = true;
  }
  memo[index] = false;
  return false;
}
```

#### 4. Jump Game II - [LeetCode 45](https://leetcode.com/problems/jump-game-ii/)

Given nums where nums[i] is the maximum jump length from index i, return the minimum number of jumps needed to reach the last index.

```java
public int q04JumpRecursive(int[] nums) {
  int[] memo = new int[nums.length];
  Arrays.fill(memo, -1);
  return dpQ4Recursive(nums, 0, memo);
}

private int dpQ4Recursive(int[] nums, int index, int[] memo) {
  if (index >= nums.length - 1) return 0;
  if (memo[index] != -1) return memo[index];
  int best = 1_000_000;
  int limit = Math.min(nums.length - 1, index + nums[index]);
  for (int next = index + 1; next <= limit; next++) {
    best = Math.min(best, 1 + dpQ4Recursive(nums, next, memo));
  }
  memo[index] = best;
  return best;
}
```

#### 5. Gas Station - [LeetCode 134](https://leetcode.com/problems/gas-station/)

Given gas and cost arrays around a circular route, return the starting station index that can complete the circuit, or -1 if impossible.

```java
public int q05CanCompleteCircuitRecursive(int[] gas, int[] cost) {
  int[] result = scanQ5Recursive(gas, cost, 0, 0, 0, 0);
  return result[0] >= 0 ? result[2] : -1;
}

private int[] scanQ5Recursive(int[] gas, int[] cost, int index, int total, int tank, int start) {
  if (index == gas.length) return new int[] {total, tank, start};
  int diff = gas[index] - cost[index];
  if (tank + diff < 0) {
    return scanQ5Recursive(gas, cost, index + 1, total + diff, 0, index + 1);
  }
  return scanQ5Recursive(gas, cost, index + 1, total + diff, tank + diff, start);
}
```

#### 6. Candy - [LeetCode 135](https://leetcode.com/problems/candy/)

Given children ratings in a line, give each child at least one candy and give higher-rated children more candies than adjacent lower-rated children. Return the minimum candies needed.

```java
public int q06CandyRecursive(int[] ratings) {
  int[] memo = new int[ratings.length];
  int total = 0;
  for (int i = 0; i < ratings.length; i++) total += needQ6Recursive(ratings, i, memo);
  return total;
}

private int needQ6Recursive(int[] ratings, int index, int[] memo) {
  if (memo[index] != 0) return memo[index];
  int candies = 1;
  if (index > 0 && ratings[index] > ratings[index - 1]) candies = Math.max(candies, needQ6Recursive(ratings, index - 1, memo) + 1);
  if (index + 1 < ratings.length && ratings[index] > ratings[index + 1]) candies = Math.max(candies, needQ6Recursive(ratings, index + 1, memo) + 1);
  memo[index] = candies;
  return candies;
}
```

#### 7. Queue Reconstruction by Height - [LeetCode 406](https://leetcode.com/problems/queue-reconstruction-by-height/)

Given people as [height, k], reconstruct a queue where k is the number of people in front with height greater than or equal to height.

```java
public int[][] q07ReconstructQueueRecursive(int[][] people) {
  Arrays.sort(people, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
  List<int[]> queue = new ArrayList<>();
  insertQ7Recursive(people, 0, queue);
  return queue.toArray(new int[people.length][]);
}

private void insertQ7Recursive(int[][] people, int index, List<int[]> queue) {
  if (index == people.length) return;
  queue.add(people[index][1], people[index]);
  insertQ7Recursive(people, index + 1, queue);
}
```

#### 8. Non-overlapping Intervals - [LeetCode 435](https://leetcode.com/problems/non-overlapping-intervals/)

Given intervals, return the minimum number that must be removed so the remaining intervals do not overlap.

```java
public int q08EraseOverlapIntervalsRecursive(int[][] intervals) {
  Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
  return scanQ8Recursive(intervals, 0, Integer.MIN_VALUE);
}

private int scanQ8Recursive(int[][] intervals, int index, int end) {
  if (index == intervals.length) return 0;
  if (intervals[index][0] < end) {
    return 1 + scanQ8Recursive(intervals, index + 1, end);
  }
  return scanQ8Recursive(intervals, index + 1, intervals[index][1]);
}
```

#### 9. Merge Intervals - [LeetCode 56](https://leetcode.com/problems/merge-intervals/)

Given intervals, merge all overlapping intervals and return the non-overlapping result.

```java
public int[][] q09MergeRecursive(int[][] intervals) {
  Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
  List<int[]> merged = new ArrayList<>();
  scanQ9Recursive(intervals, 0, merged);
  return merged.toArray(new int[merged.size()][]);
}

private void scanQ9Recursive(int[][] intervals, int index, List<int[]> merged) {
  if (index == intervals.length) return;
  if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < intervals[index][0]) {
    merged.add(intervals[index].clone());
  } else {
    int[] last = merged.get(merged.size() - 1);
    last[1] = Math.max(last[1], intervals[index][1]);
  }
  scanQ9Recursive(intervals, index + 1, merged);
}
```

#### 10. Minimum Number of Arrows to Burst Balloons - [LeetCode 452](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/)

Given balloon intervals on the x-axis, return the minimum arrows needed; one arrow shot at x bursts every balloon containing x.

```java
public int q10FindMinArrowShotsRecursive(int[][] points) {
  Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
  return scanQ10Recursive(points, 0, Long.MIN_VALUE, 0);
}

private int scanQ10Recursive(int[][] points, int index, long arrow, int arrows) {
  if (index == points.length) return arrows;
  if (arrows == 0 || points[index][0] > arrow) {
    return scanQ10Recursive(points, index + 1, points[index][1], arrows + 1);
  }
  return scanQ10Recursive(points, index + 1, arrow, arrows);
}
```

#### 11. Partition Labels - [LeetCode 763](https://leetcode.com/problems/partition-labels/)

Given a string, split it into as many parts as possible so each letter appears in at most one part. Return the sizes of the parts.

```java
public List<Integer> q11PartitionLabelsRecursive(String s) {
  int[] last = new int[26];
  for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
  List<Integer> result = new ArrayList<>();
  buildQ11Recursive(s, last, 0, result);
  return result;
}

private void buildQ11Recursive(String s, int[] last, int start, List<Integer> result) {
  if (start == s.length()) return;
  int end = start;
  for (int i = start; i <= end; i++) {
    end = Math.max(end, last[s.charAt(i) - 'a']);
  }
  result.add(end - start + 1);
  buildQ11Recursive(s, last, end + 1, result);
}
```

#### 12. Task Scheduler - [LeetCode 621](https://leetcode.com/problems/task-scheduler/)

Given CPU tasks represented by letters and a cooldown n, return the least time units needed to execute all tasks with identical tasks separated by at least n intervals.

```java
public int q12LeastIntervalRecursive(char[] tasks, int n) {
  int[] count = new int[26];
  for (char task : tasks) count[task - 'A']++;
  return simulateQ12Recursive(count, new int[26], tasks.length, 0, n);
}

private int simulateQ12Recursive(int[] count, int[] nextAllowed, int remaining, int time, int cooldown) {
  if (remaining == 0) return time;
  int best = -1;
  for (int i = 0; i < 26; i++) {
    if (count[i] > 0 && nextAllowed[i] <= time && (best == -1 || count[i] > count[best])) best = i;
  }
  if (best != -1) {
    count[best]--;
    nextAllowed[best] = time + cooldown + 1;
    return simulateQ12Recursive(count, nextAllowed, remaining - 1, time + 1, cooldown);
  }
  return simulateQ12Recursive(count, nextAllowed, remaining, time + 1, cooldown);
}
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

Given n stairs, you can climb either 1 or 2 steps at a time. Return the number of distinct ways to reach the top.

```java
public int q01ClimbStairsRecursive(int n) {
  int[] memo = new int[n + 1];
  return waysQ1Recursive(n, memo);
}

private int waysQ1Recursive(int step, int[] memo) {
  if (step <= 2) return step;
  if (memo[step] != 0) return memo[step];
  memo[step] = waysQ1Recursive(step - 1, memo) + waysQ1Recursive(step - 2, memo);
  return memo[step];
}
```

#### 2. Min Cost Climbing Stairs - [LeetCode 746](https://leetcode.com/problems/min-cost-climbing-stairs/)

Given cost[i] for stepping on stair i, return the minimum cost to reach the top when you may climb 1 or 2 steps each move.

```java
public int q02MinCostClimbingStairsRecursive(int[] cost) {
  int[] memo = new int[cost.length];
  Arrays.fill(memo, -1);
  return Math.min(dpQ2Recursive(cost, 0, memo), dpQ2Recursive(cost, 1, memo));
}

private int dpQ2Recursive(int[] cost, int index, int[] memo) {
  if (index >= cost.length) return 0;
  if (memo[index] != -1) return memo[index];
  memo[index] = cost[index] + Math.min(dpQ2Recursive(cost, index + 1, memo), dpQ2Recursive(cost, index + 2, memo));
  return memo[index];
}
```

#### 3. House Robber - [LeetCode 198](https://leetcode.com/problems/house-robber/)

Given money in houses along a street, return the maximum amount you can rob without robbing adjacent houses.

```java
public int q03RobRecursive(int[] nums) {
  int[] memo = new int[nums.length];
  Arrays.fill(memo, -1);
  return dpQ3Recursive(nums, 0, memo);
}

private int dpQ3Recursive(int[] nums, int index, int[] memo) {
  if (index >= nums.length) return 0;
  if (memo[index] != -1) return memo[index];
  int take = nums[index] + dpQ3Recursive(nums, index + 2, memo);
  int skip = dpQ3Recursive(nums, index + 1, memo);
  memo[index] = Math.max(take, skip);
  return memo[index];
}
```

#### 4. House Robber II - [LeetCode 213](https://leetcode.com/problems/house-robber-ii/)

Given money in houses arranged in a circle, return the maximum amount you can rob without robbing adjacent houses.

```java
public int q04RobRecursive(int[] nums) {
  if (nums.length == 1) return nums[0];
  int[] leftMemo = new int[nums.length];
  int[] rightMemo = new int[nums.length];
  Arrays.fill(leftMemo, -1);
  Arrays.fill(rightMemo, -1);
  return Math.max(dpQ4Recursive(nums, 0, nums.length - 2, leftMemo), dpQ4Recursive(nums, 1, nums.length - 1, rightMemo));
}

private int dpQ4Recursive(int[] nums, int index, int end, int[] memo) {
  if (index > end) return 0;
  if (memo[index] != -1) return memo[index];
  memo[index] = Math.max(nums[index] + dpQ4Recursive(nums, index + 2, end, memo), dpQ4Recursive(nums, index + 1, end, memo));
  return memo[index];
}
```

#### 5. Decode Ways - [LeetCode 91](https://leetcode.com/problems/decode-ways/)

Given a digit string where A-Z maps to 1-26, return the number of valid decodings.

```java
public int q05NumDecodingsRecursive(String s) {
  int[] memo = new int[s.length()];
  Arrays.fill(memo, -1);
  return dpQ5Recursive(s, 0, memo);
}

private int dpQ5Recursive(String s, int index, int[] memo) {
  if (index == s.length()) return 1;
  if (s.charAt(index) == '0') return 0;
  if (memo[index] != -1) return memo[index];

  int ways = dpQ5Recursive(s, index + 1, memo);
  if (index + 1 < s.length()) {
    int value = (s.charAt(index) - '0') * 10 + (s.charAt(index + 1) - '0');
    if (value <= 26) ways += dpQ5Recursive(s, index + 2, memo);
  }
  memo[index] = ways;
  return ways;
}
```

#### 6. Coin Change - [LeetCode 322](https://leetcode.com/problems/coin-change/)

Given coin denominations and an amount, return the fewest number of coins needed to make that amount, or -1 if impossible.

```java
public int q06CoinChangeRecursive(int[] coins, int amount) {
  int[] memo = new int[amount + 1];
  Arrays.fill(memo, -2);
  int answer = dpQ6Recursive(coins, amount, memo);
  return answer >= 1_000_000 ? -1 : answer;
}

private int dpQ6Recursive(int[] coins, int amount, int[] memo) {
  if (amount == 0) return 0;
  if (amount < 0) return 1_000_000;
  if (memo[amount] != -2) return memo[amount];
  int best = 1_000_000;
  for (int coin : coins) best = Math.min(best, 1 + dpQ6Recursive(coins, amount - coin, memo));
  memo[amount] = best;
  return best;
}
```

#### 7. Coin Change II - [LeetCode 518](https://leetcode.com/problems/coin-change-ii/)

Given coin denominations and an amount, return the number of combinations that make up the amount. Each coin may be used unlimited times.

```java
public int q07ChangeRecursive(int amount, int[] coins) {
  int[][] memo = new int[coins.length + 1][amount + 1];
  for (int[] row : memo) Arrays.fill(row, -1);
  return dpQ7Recursive(coins, 0, amount, memo);
}

private int dpQ7Recursive(int[] coins, int index, int amount, int[][] memo) {
  if (amount == 0) return 1;
  if (index == coins.length) return 0;
  if (memo[index][amount] != -1) return memo[index][amount];
  int skip = dpQ7Recursive(coins, index + 1, amount, memo);
  int take = amount >= coins[index] ? dpQ7Recursive(coins, index, amount - coins[index], memo) : 0;
  memo[index][amount] = skip + take;
  return memo[index][amount];
}
```

#### 8. Combination Sum IV - [LeetCode 377](https://leetcode.com/problems/combination-sum-iv/)

Given distinct positive nums and target, return the number of ordered combinations that sum to target.

```java
public int q08CombinationSum4Recursive(int[] nums, int target) {
  int[] memo = new int[target + 1];
  Arrays.fill(memo, -1);
  return dpQ8Recursive(nums, target, memo);
}

private int dpQ8Recursive(int[] nums, int target, int[] memo) {
  if (target == 0) return 1;
  if (memo[target] != -1) return memo[target];
  int ways = 0;
  for (int num : nums) {
    if (num <= target) ways += dpQ8Recursive(nums, target - num, memo);
  }
  memo[target] = ways;
  return ways;
}
```

#### 9. Longest Increasing Subsequence - [LeetCode 300](https://leetcode.com/problems/longest-increasing-subsequence/)

Given an integer array, return the length of the longest strictly increasing subsequence.

```java
public int q09LengthOfLISRecursive(int[] nums) {
  int[][] memo = new int[nums.length][nums.length + 1];
  for (int[] row : memo) Arrays.fill(row, -1);
  return dpQ9Recursive(nums, 0, -1, memo);
}

private int dpQ9Recursive(int[] nums, int index, int previous, int[][] memo) {
  if (index == nums.length) return 0;
  if (memo[index][previous + 1] != -1) return memo[index][previous + 1];
  int skip = dpQ9Recursive(nums, index + 1, previous, memo);
  int take = previous == -1 || nums[index] > nums[previous]
      ? 1 + dpQ9Recursive(nums, index + 1, index, memo)
      : 0;
  memo[index][previous + 1] = Math.max(take, skip);
  return memo[index][previous + 1];
}
```

#### 10. Longest Arithmetic Subsequence - [LeetCode 1027](https://leetcode.com/problems/longest-arithmetic-subsequence/)

Given an array, return the length of the longest arithmetic subsequence.

```java
public int q10LongestArithSeqLengthRecursive(int[] nums) {
  Map<String, Integer> memo = new HashMap<>();
  int best = 2;
  for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
      best = Math.max(best, 2 + extendQ10Recursive(nums, j, nums[j] - nums[i], memo));
    }
  }
  return best;
}

private int extendQ10Recursive(int[] nums, int index, int diff, Map<String, Integer> memo) {
  String key = index + ":" + diff;
  if (memo.containsKey(key)) return memo.get(key);
  int best = 0;
  for (int next = index + 1; next < nums.length; next++) {
    if (nums[next] - nums[index] == diff) {
      best = Math.max(best, 1 + extendQ10Recursive(nums, next, diff, memo));
    }
  }
  memo.put(key, best);
  return best;
}
```

#### 11. Partition Equal Subset Sum - [LeetCode 416](https://leetcode.com/problems/partition-equal-subset-sum/)

Given an integer array, return true if it can be partitioned into two subsets with equal sum.

```java
public boolean q11CanPartitionRecursive(int[] nums) {
  int sum = 0;
  for (int num : nums) sum += num;
  if (sum % 2 == 1) return false;
  Boolean[][] memo = new Boolean[nums.length][sum / 2 + 1];
  return dpQ11Recursive(nums, 0, sum / 2, memo);
}

private boolean dpQ11Recursive(int[] nums, int index, int target, Boolean[][] memo) {
  if (target == 0) return true;
  if (index == nums.length || target < 0) return false;
  if (memo[index][target] != null) return memo[index][target];
  memo[index][target] = dpQ11Recursive(nums, index + 1, target - nums[index], memo)
      || dpQ11Recursive(nums, index + 1, target, memo);
  return memo[index][target];
}
```

#### 12. Word Break - [LeetCode 139](https://leetcode.com/problems/word-break/)

Given a string and a dictionary, return true if the string can be segmented into a sequence of one or more dictionary words.

```java
public boolean q12WordBreakRecursive(String s, List<String> wordDict) {
  Set<String> words = new HashSet<>(wordDict);
  Boolean[] memo = new Boolean[s.length()];
  return dpQ12Recursive(s, 0, words, memo);
}

private boolean dpQ12Recursive(String s, int start, Set<String> words, Boolean[] memo) {
  if (start == s.length()) return true;
  if (memo[start] != null) return memo[start];
  for (int end = start + 1; end <= s.length(); end++) {
    if (words.contains(s.substring(start, end)) && dpQ12Recursive(s, end, words, memo)) {
      return memo[start] = true;
    }
  }
  return memo[start] = false;
}
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Unique Paths - [LeetCode 62](https://leetcode.com/problems/unique-paths/)

Given an m x n grid, a robot starts at the top-left and can move only right or down. Return the number of paths to the bottom-right.

```java
public int q01UniquePathsRecursive(int m, int n) {
  int[][] memo = new int[m][n];
  return waysQ1Recursive(0, 0, m, n, memo);
}

private int waysQ1Recursive(int row, int col, int rows, int cols, int[][] memo) {
  if (row == rows - 1 && col == cols - 1) return 1;
  if (row == rows || col == cols) return 0;
  if (memo[row][col] != 0) return memo[row][col];
  memo[row][col] = waysQ1Recursive(row + 1, col, rows, cols, memo) + waysQ1Recursive(row, col + 1, rows, cols, memo);
  return memo[row][col];
}
```

#### 2. Unique Paths II - [LeetCode 63](https://leetcode.com/problems/unique-paths-ii/)

Given an m x n grid where 1 marks an obstacle and 0 marks open space, return the number of right/down paths from top-left to bottom-right.

```java
public int q02UniquePathsWithObstaclesRecursive(int[][] obstacleGrid) {
  int[][] memo = new int[obstacleGrid.length][obstacleGrid[0].length];
  for (int[] row : memo) Arrays.fill(row, -1);
  return waysQ2Recursive(obstacleGrid, 0, 0, memo);
}

private int waysQ2Recursive(int[][] grid, int row, int col, int[][] memo) {
  if (row == grid.length || col == grid[0].length || grid[row][col] == 1) return 0;
  if (row == grid.length - 1 && col == grid[0].length - 1) return 1;
  if (memo[row][col] != -1) return memo[row][col];
  memo[row][col] = waysQ2Recursive(grid, row + 1, col, memo) + waysQ2Recursive(grid, row, col + 1, memo);
  return memo[row][col];
}
```

#### 3. Minimum Path Sum - [LeetCode 64](https://leetcode.com/problems/minimum-path-sum/)

Given a grid of non-negative numbers, return the minimum sum path from top-left to bottom-right moving only right or down.

```java
public int q03MinPathSumRecursive(int[][] grid) {
  int[][] memo = new int[grid.length][grid[0].length];
  for (int[] row : memo) Arrays.fill(row, -1);
  return dpQ3Recursive(grid, 0, 0, memo);
}

private int dpQ3Recursive(int[][] grid, int row, int col, int[][] memo) {
  if (row == grid.length || col == grid[0].length) return 1_000_000_000;
  if (row == grid.length - 1 && col == grid[0].length - 1) return grid[row][col];
  if (memo[row][col] != -1) return memo[row][col];
  memo[row][col] = grid[row][col] + Math.min(dpQ3Recursive(grid, row + 1, col, memo), dpQ3Recursive(grid, row, col + 1, memo));
  return memo[row][col];
}
```

#### 4. Triangle - [LeetCode 120](https://leetcode.com/problems/triangle/)

Given a triangle array, return the minimum path sum from top to bottom by moving to adjacent numbers on the next row.

```java
public int q04MinimumTotalRecursive(List<List<Integer>> triangle) {
  Integer[][] memo = new Integer[triangle.size()][triangle.size()];
  return dpQ4Recursive(triangle, 0, 0, memo);
}

private int dpQ4Recursive(List<List<Integer>> triangle, int row, int col, Integer[][] memo) {
  if (row == triangle.size() - 1) return triangle.get(row).get(col);
  if (memo[row][col] != null) return memo[row][col];
  int down = dpQ4Recursive(triangle, row + 1, col, memo);
  int diagonal = dpQ4Recursive(triangle, row + 1, col + 1, memo);
  memo[row][col] = triangle.get(row).get(col) + Math.min(down, diagonal);
  return memo[row][col];
}
```

#### 5. Longest Common Subsequence - [LeetCode 1143](https://leetcode.com/problems/longest-common-subsequence/)

Given two strings, return the length of their longest common subsequence.

```java
public int q05LongestCommonSubsequenceRecursive(String text1, String text2) {
  int[][] memo = new int[text1.length()][text2.length()];
  for (int[] row : memo) Arrays.fill(row, -1);
  return dpQ5Recursive(text1, text2, 0, 0, memo);
}

private int dpQ5Recursive(String a, String b, int i, int j, int[][] memo) {
  if (i == a.length() || j == b.length()) return 0;
  if (memo[i][j] != -1) return memo[i][j];
  if (a.charAt(i) == b.charAt(j)) memo[i][j] = 1 + dpQ5Recursive(a, b, i + 1, j + 1, memo);
  else memo[i][j] = Math.max(dpQ5Recursive(a, b, i + 1, j, memo), dpQ5Recursive(a, b, i, j + 1, memo));
  return memo[i][j];
}
```

#### 6. Edit Distance - [LeetCode 72](https://leetcode.com/problems/edit-distance/)

Given two words, return the minimum number of insert, delete, and replace operations needed to convert word1 into word2.

```java
public int q06MinDistanceRecursive(String word1, String word2) {
  int[][] memo = new int[word1.length()][word2.length()];
  for (int[] row : memo) Arrays.fill(row, -1);
  return dpQ6Recursive(word1, word2, 0, 0, memo);
}

private int dpQ6Recursive(String a, String b, int i, int j, int[][] memo) {
  if (i == a.length()) return b.length() - j;
  if (j == b.length()) return a.length() - i;
  if (memo[i][j] != -1) return memo[i][j];
  if (a.charAt(i) == b.charAt(j)) memo[i][j] = dpQ6Recursive(a, b, i + 1, j + 1, memo);
  else memo[i][j] = 1 + Math.min(dpQ6Recursive(a, b, i, j + 1, memo), Math.min(dpQ6Recursive(a, b, i + 1, j, memo), dpQ6Recursive(a, b, i + 1, j + 1, memo)));
  return memo[i][j];
}
```

#### 7. Regular Expression Matching - [LeetCode 10](https://leetcode.com/problems/regular-expression-matching/)

Given string s and pattern p containing . and *, return whether p matches the entire string. Dot matches any single character and star repeats the previous element zero or more times.

```java
public boolean q07IsMatchRecursive(String s, String p) {
  Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
  return dpQ7Recursive(s, p, 0, 0, memo);
}

private boolean dpQ7Recursive(String s, String p, int i, int j, Boolean[][] memo) {
  if (j == p.length()) return i == s.length();
  if (memo[i][j] != null) return memo[i][j];
  boolean first = i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
  boolean answer;
  if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
    answer = dpQ7Recursive(s, p, i, j + 2, memo) || first && dpQ7Recursive(s, p, i + 1, j, memo);
  } else {
    answer = first && dpQ7Recursive(s, p, i + 1, j + 1, memo);
  }
  memo[i][j] = answer;
  return answer;
}
```

#### 8. Wildcard Matching - [LeetCode 44](https://leetcode.com/problems/wildcard-matching/)

Given string s and pattern p containing ? and *, return whether p matches the entire string. ? matches one character and * matches any sequence including empty.

```java
public boolean q08IsMatchRecursive(String s, String p) {
  Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
  return dpQ8Recursive(s, p, 0, 0, memo);
}

private boolean dpQ8Recursive(String s, String p, int i, int j, Boolean[][] memo) {
  if (j == p.length()) return i == s.length();
  if (memo[i][j] != null) return memo[i][j];
  boolean answer;
  if (p.charAt(j) == '*') {
    answer = dpQ8Recursive(s, p, i, j + 1, memo) || i < s.length() && dpQ8Recursive(s, p, i + 1, j, memo);
  } else {
    boolean first = i < s.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i));
    answer = first && dpQ8Recursive(s, p, i + 1, j + 1, memo);
  }
  memo[i][j] = answer;
  return answer;
}
```

#### 9. Distinct Subsequences - [LeetCode 115](https://leetcode.com/problems/distinct-subsequences/)

Given strings s and t, return the number of distinct subsequences of s equal to t.

```java
public int q09NumDistinctRecursive(String s, String t) {
  int[][] memo = new int[s.length()][t.length()];
  for (int[] row : memo) Arrays.fill(row, -1);
  return dpQ9Recursive(s, t, 0, 0, memo);
}

private int dpQ9Recursive(String s, String t, int i, int j, int[][] memo) {
  if (j == t.length()) return 1;
  if (i == s.length()) return 0;
  if (memo[i][j] != -1) return memo[i][j];
  int ways = dpQ9Recursive(s, t, i + 1, j, memo);
  if (s.charAt(i) == t.charAt(j)) ways += dpQ9Recursive(s, t, i + 1, j + 1, memo);
  memo[i][j] = ways;
  return ways;
}
```

#### 10. Interleaving String - [LeetCode 97](https://leetcode.com/problems/interleaving-string/)

Given s1, s2, and s3, return true if s3 is formed by interleaving s1 and s2 while preserving the order of characters from each string.

```java
public boolean q10IsInterleaveRecursive(String s1, String s2, String s3) {
  if (s1.length() + s2.length() != s3.length()) return false;
  Boolean[][] memo = new Boolean[s1.length() + 1][s2.length() + 1];
  return dpQ10Recursive(s1, s2, s3, 0, 0, memo);
}

private boolean dpQ10Recursive(String a, String b, String c, int i, int j, Boolean[][] memo) {
  if (i + j == c.length()) return true;
  if (memo[i][j] != null) return memo[i][j];
  boolean ok = false;
  if (i < a.length() && a.charAt(i) == c.charAt(i + j)) ok |= dpQ10Recursive(a, b, c, i + 1, j, memo);
  if (j < b.length() && b.charAt(j) == c.charAt(i + j)) ok |= dpQ10Recursive(a, b, c, i, j + 1, memo);
  memo[i][j] = ok;
  return ok;
}
```

#### 11. Longest Palindromic Subsequence - [LeetCode 516](https://leetcode.com/problems/longest-palindromic-subsequence/)

Given a string s, return the length of the longest subsequence that reads the same forward and backward.

```java
public int q11LongestPalindromeSubseqRecursive(String s) {
  int n = s.length();
  int[][] memo = new int[n][n];
  return dpQ11Recursive(s, 0, n - 1, memo);
}

private int dpQ11Recursive(String s, int left, int right, int[][] memo) {
  if (left > right) return 0;
  if (left == right) return 1;
  if (memo[left][right] != 0) return memo[left][right];
  if (s.charAt(left) == s.charAt(right)) {
    memo[left][right] = 2 + dpQ11Recursive(s, left + 1, right - 1, memo);
  } else {
    memo[left][right] = Math.max(dpQ11Recursive(s, left + 1, right, memo), dpQ11Recursive(s, left, right - 1, memo));
  }
  return memo[left][right];
}
```

#### 12. Longest Palindromic Substring - [LeetCode 5](https://leetcode.com/problems/longest-palindromic-substring/)

Given a string s, return the longest contiguous substring that is a palindrome.

```java
public String q12LongestPalindromeRecursive(String s) {
  int n = s.length();
  Boolean[][] memo = new Boolean[n][n];
  int bestStart = 0;
  int bestLength = 1;

  for (int left = 0; left < n; left++) {
    for (int right = left; right < n; right++) {
      int length = right - left + 1;
      if (length > bestLength && isPalindromeQ12Recursive(s, left, right, memo)) {
        bestStart = left;
        bestLength = length;
      }
    }
  }
  return s.substring(bestStart, bestStart + bestLength);
}

private boolean isPalindromeQ12Recursive(String s, int left, int right, Boolean[][] memo) {
  if (left >= right) return true;
  if (memo[left][right] != null) return memo[left][right];
  memo[left][right] = s.charAt(left) == s.charAt(right) && isPalindromeQ12Recursive(s, left + 1, right - 1, memo);
  return memo[left][right];
}
```

### Moderate (13-20)

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

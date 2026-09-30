# Recursion - Iterative Questions and Solutions

## Topic 10: Recursion

### Basic (1-12)

#### 1. Fibonacci Number - [LeetCode 509](https://leetcode.com/problems/fibonacci-number/)

Given n, return the nth Fibonacci number where F(0) = 0, F(1) = 1, and F(n) = F(n - 1) + F(n - 2).

```java
public int q01FibOptimized(int n) {
  if (n <= 1) return n;

  int prev = 0;
  int curr = 1;
  for (int i = 2; i <= n; i++) {
    int next = prev + curr;
    prev = curr;
    curr = next;
  }

  return curr;
}
```

#### 2. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

You are climbing a staircase with n steps. Each move can climb 1 or 2 steps. Return the number of distinct ways to reach the top.

```java
public int q02ClimbStairsOptimized(int n) {
  if (n <= 2) return n;

  int oneStepBefore = 2;
  int twoStepsBefore = 1;
  for (int step = 3; step <= n; step++) {
    int current = oneStepBefore + twoStepsBefore;
    twoStepsBefore = oneStepBefore;
    oneStepBefore = current;
  }

  return oneStepBefore;
}
```

#### 3. Pow(x, n) - [LeetCode 50](https://leetcode.com/problems/powx-n/)

Implement pow(x, n), which calculates x raised to the power n.

```java
public double q03MyPowOptimized(double x, int n) {
  long power = n;
  if (power < 0) {
    x = 1.0 / x;
    power = -power;
  }

  double answer = 1.0;
  while (power > 0) {
    if ((power & 1L) == 1L) answer *= x;
    x *= x;
    power >>= 1;
  }

  return answer;
}
```

#### 4. Reverse String - [LeetCode 344](https://leetcode.com/problems/reverse-string/)

Given a character array s, reverse it in-place.

```java
public void q04ReverseStringOptimized(char[] s) {
  int left = 0;
  int right = s.length - 1;

  while (left < right) {
    char temp = s[left];
    s[left++] = s[right];
    s[right--] = temp;
  }
}
```

#### 5. Swap Nodes in Pairs - [LeetCode 24](https://leetcode.com/problems/swap-nodes-in-pairs/)

Given a linked list, swap every two adjacent nodes and return its head. Node values must not be modified.

```java
public ListNode q05SwapPairsOptimized(ListNode head) {
  ListNode dummy = new ListNode(0, head);
  ListNode prev = dummy;

  while (prev.next != null && prev.next.next != null) {
    ListNode first = prev.next;
    ListNode second = first.next;
    first.next = second.next;
    second.next = first;
    prev.next = second;
    prev = first;
  }

  return dummy.next;
}
```

#### 6. Merge Two Sorted Lists - [LeetCode 21](https://leetcode.com/problems/merge-two-sorted-lists/)

Given the heads of two sorted linked lists, merge them into one sorted linked list and return its head.

```java
public ListNode q06MergeTwoListsOptimized(ListNode list1, ListNode list2) {
  ListNode dummy = new ListNode(0);
  ListNode tail = dummy;

  while (list1 != null && list2 != null) {
    if (list1.val <= list2.val) {
      tail.next = list1;
      list1 = list1.next;
    } else {
      tail.next = list2;
      list2 = list2.next;
    }
    tail = tail.next;
  }

  tail.next = list1 != null ? list1 : list2;
  return dummy.next;
}
```

#### 7. K-th Symbol in Grammar - [LeetCode 779](https://leetcode.com/problems/k-th-symbol-in-grammar/)

In row 1 the grammar is 0. Each 0 becomes 01 and each 1 becomes 10. Return the kth symbol in row n.

```java
public int q07KthGrammarOptimized(int n, int k) {
  int flips = 0;
  while (k > 1) {
    if (k % 2 == 0) flips++;
    k = (k + 1) / 2;
  }
  return flips % 2;
}
```

#### 8. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
public List<String> q08GenerateParenthesisOptimized(int n) {
  List<String> answer = new ArrayList<>();
  Deque<StateQ8Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ8Optimized("", 0, 0));

  while (!stack.isEmpty()) {
    StateQ8Optimized current = stack.pop();
    if (current.value.length() == 2 * n) {
      answer.add(current.value);
      continue;
    }
    if (current.close < current.open) {
      stack.push(new StateQ8Optimized(current.value + ")", current.open, current.close + 1));
    }
    if (current.open < n) {
      stack.push(new StateQ8Optimized(current.value + "(", current.open + 1, current.close));
    }
  }

  return answer;
}

private static class StateQ8Optimized {
  String value;
  int open;
  int close;
  StateQ8Optimized(String value, int open, int close) {
    this.value = value;
    this.open = open;
    this.close = close;
  }
}
```

#### 9. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets of nums.

```java
public List<List<Integer>> q09SubsetsOptimized(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  answer.add(new ArrayList<>());

  for (int num : nums) {
    int size = answer.size();
    for (int i = 0; i < size; i++) {
      List<Integer> next = new ArrayList<>(answer.get(i));
      next.add(num);
      answer.add(next);
    }
  }

  return answer;
}
```

#### 10. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
public List<List<Integer>> q10PermuteOptimized(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  answer.add(new ArrayList<>());

  for (int num : nums) {
    List<List<Integer>> nextLevel = new ArrayList<>();
    for (List<Integer> perm : answer) {
      for (int pos = 0; pos <= perm.size(); pos++) {
        List<Integer> next = new ArrayList<>(perm);
        next.add(pos, num);
        nextLevel.add(next);
      }
    }
    answer = nextLevel;
  }

  return answer;
}
```

#### 11. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
private static final String[] MAPQ11Optimized = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

public List<String> q11LetterCombinationsOptimized(String digits) {
  List<String> answer = new ArrayList<>();
  if (digits.length() == 0) return answer;

  Queue<String> queue = new ArrayDeque<>();
  queue.offer("");
  for (char digit : digits.toCharArray()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
      String prefix = queue.poll();
      for (char ch : MAPQ11Optimized[digit - '0'].toCharArray()) {
        queue.offer(prefix + ch);
      }
    }
  }

  answer.addAll(queue);
  return answer;
}
```

#### 12. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given integers n and k, return all possible combinations of k numbers chosen from the range 1 to n.

```java
public List<List<Integer>> q12CombineOptimized(int n, int k) {
  List<List<Integer>> answer = new ArrayList<>();
  Deque<StateQ12Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ12Optimized(1, new ArrayList<>()));

  while (!stack.isEmpty()) {
    StateQ12Optimized state = stack.pop();
    if (state.path.size() == k) {
      answer.add(state.path);
      continue;
    }
    for (int num = n; num >= state.start; num--) {
      if (state.path.size() + (n - num + 1) < k) continue;
      List<Integer> next = new ArrayList<>(state.path);
      next.add(num);
      stack.push(new StateQ12Optimized(num + 1, next));
    }
  }

  return answer;
}

private static class StateQ12Optimized {
  int start;
  List<Integer> path;
  StateQ12Optimized(int start, List<Integer> path) {
    this.start = start;
    this.path = path;
  }
}
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets. The solution set must not contain duplicate subsets.

```java
public List<List<Integer>> q01SubsetsOptimized(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  answer.add(new ArrayList<>());
  for (int num : nums) {
    int size = answer.size();
    for (int i = 0; i < size; i++) {
      List<Integer> next = new ArrayList<>(answer.get(i));
      next.add(num);
      answer.add(next);
    }
  }
  return answer;
}
```

#### 2. Subsets II - [LeetCode 90](https://leetcode.com/problems/subsets-ii/)

Given an integer array nums that may contain duplicates, return all possible subsets without duplicate subsets.

```java
public List<List<Integer>> q02SubsetsWithDupOptimized(int[] nums) {
  Arrays.sort(nums);
  List<List<Integer>> answer = new ArrayList<>();
  answer.add(new ArrayList<>());
  int previousStart = 0;
  int previousEnd = 0;

  for (int i = 0; i < nums.length; i++) {
    int start = i > 0 && nums[i] == nums[i - 1] ? previousStart : 0;
    previousStart = answer.size();
    previousEnd = answer.size();
    for (int j = start; j < previousEnd; j++) {
      List<Integer> next = new ArrayList<>(answer.get(j));
      next.add(nums[i]);
      answer.add(next);
    }
  }
  return answer;
}
```

#### 3. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
public List<List<Integer>> q03PermuteOptimized(int[] nums) {
  List<List<Integer>> answer = new ArrayList<>();
  answer.add(new ArrayList<>());
  for (int num : nums) {
    List<List<Integer>> nextLevel = new ArrayList<>();
    for (List<Integer> perm : answer) {
      for (int pos = 0; pos <= perm.size(); pos++) {
        List<Integer> next = new ArrayList<>(perm);
        next.add(pos, num);
        nextLevel.add(next);
      }
    }
    answer = nextLevel;
  }
  return answer;
}
```

#### 4. Permutations II - [LeetCode 47](https://leetcode.com/problems/permutations-ii/)

Given a collection of numbers nums that may contain duplicates, return all unique permutations.

```java
public List<List<Integer>> q04PermuteUniqueOptimized(int[] nums) {
  Arrays.sort(nums);
  List<List<Integer>> answer = new ArrayList<>();
  answer.add(new ArrayList<>());
  for (int num : nums) {
    List<List<Integer>> nextLevel = new ArrayList<>();
    for (List<Integer> perm : answer) {
      for (int pos = 0; pos <= perm.size(); pos++) {
        if (pos > 0 && perm.get(pos - 1) == num) break;
        List<Integer> next = new ArrayList<>(perm);
        next.add(pos, num);
        nextLevel.add(next);
      }
    }
    answer = nextLevel;
  }
  return answer;
}
```

#### 5. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given n and k, return all possible combinations of k numbers chosen from 1 to n.

```java
public List<List<Integer>> q05CombineOptimized(int n, int k) {
  List<List<Integer>> answer = new ArrayList<>();
  Deque<StateQ5Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ5Optimized(1, new ArrayList<>()));
  while (!stack.isEmpty()) {
    StateQ5Optimized state = stack.pop();
    if (state.path.size() == k) {
      answer.add(state.path);
      continue;
    }
    for (int num = n; num >= state.start; num--) {
      List<Integer> next = new ArrayList<>(state.path);
      next.add(num);
      stack.push(new StateQ5Optimized(num + 1, next));
    }
  }
  return answer;
}

private static class StateQ5Optimized {
  int start; List<Integer> path;
  StateQ5Optimized(int start, List<Integer> path) { this.start = start; this.path = path; }
}
```

#### 6. Combination Sum - [LeetCode 39](https://leetcode.com/problems/combination-sum/)

Given distinct candidates and a target, return all unique combinations where chosen numbers sum to target. The same number may be chosen unlimited times.

```java
public List<List<Integer>> q06CombinationSumOptimized(int[] candidates, int target) {
  Arrays.sort(candidates);
  List<List<Integer>> answer = new ArrayList<>();
  Deque<StateQ6Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ6Optimized(0, target, new ArrayList<>()));
  while (!stack.isEmpty()) {
    StateQ6Optimized state = stack.pop();
    if (state.remaining == 0) { answer.add(state.path); continue; }
    for (int i = candidates.length - 1; i >= state.start; i--) {
      if (candidates[i] > state.remaining) continue;
      List<Integer> next = new ArrayList<>(state.path);
      next.add(candidates[i]);
      stack.push(new StateQ6Optimized(i, state.remaining - candidates[i], next));
    }
  }
  return answer;
}

private static class StateQ6Optimized {
  int start, remaining; List<Integer> path;
  StateQ6Optimized(int start, int remaining, List<Integer> path) { this.start = start; this.remaining = remaining; this.path = path; }
}
```

#### 7. Combination Sum II - [LeetCode 40](https://leetcode.com/problems/combination-sum-ii/)

Given candidates that may contain duplicates and a target, return all unique combinations where each candidate may be used at most once.

```java
public List<List<Integer>> q07CombinationSum2Optimized(int[] candidates, int target) {
  Arrays.sort(candidates);
  List<List<Integer>> answer = new ArrayList<>();
  Deque<StateQ7Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ7Optimized(0, target, new ArrayList<>()));
  while (!stack.isEmpty()) {
    StateQ7Optimized state = stack.pop();
    if (state.remaining == 0) { answer.add(state.path); continue; }
    for (int i = candidates.length - 1; i >= state.start; i--) {
      if (i > state.start && candidates[i] == candidates[i - 1]) continue;
      if (candidates[i] > state.remaining) continue;
      List<Integer> next = new ArrayList<>(state.path);
      next.add(candidates[i]);
      stack.push(new StateQ7Optimized(i + 1, state.remaining - candidates[i], next));
    }
  }
  return answer;
}

private static class StateQ7Optimized {
  int start, remaining; List<Integer> path;
  StateQ7Optimized(int start, int remaining, List<Integer> path) { this.start = start; this.remaining = remaining; this.path = path; }
}
```

#### 8. Combination Sum III - [LeetCode 216](https://leetcode.com/problems/combination-sum-iii/)

Find all valid combinations of k numbers that sum to n using only numbers 1 through 9, where each number is used at most once.

```java
public List<List<Integer>> q08CombinationSum3Optimized(int k, int n) {
  List<List<Integer>> answer = new ArrayList<>();
  Deque<StateQ8Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ8Optimized(1, n, new ArrayList<>()));
  while (!stack.isEmpty()) {
    StateQ8Optimized state = stack.pop();
    if (state.path.size() == k) {
      if (state.remaining == 0) answer.add(state.path);
      continue;
    }
    for (int num = 9; num >= state.start; num--) {
      if (num > state.remaining) continue;
      List<Integer> next = new ArrayList<>(state.path);
      next.add(num);
      stack.push(new StateQ8Optimized(num + 1, state.remaining - num, next));
    }
  }
  return answer;
}

private static class StateQ8Optimized {
  int start, remaining; List<Integer> path;
  StateQ8Optimized(int start, int remaining, List<Integer> path) { this.start = start; this.remaining = remaining; this.path = path; }
}
```

#### 9. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
public List<String> q09GenerateParenthesisOptimized(int n) {
  List<String> answer = new ArrayList<>();
  Deque<StateQ9Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ9Optimized("", 0, 0));
  while (!stack.isEmpty()) {
    StateQ9Optimized state = stack.pop();
    if (state.value.length() == 2 * n) { answer.add(state.value); continue; }
    if (state.close < state.open) stack.push(new StateQ9Optimized(state.value + ")", state.open, state.close + 1));
    if (state.open < n) stack.push(new StateQ9Optimized(state.value + "(", state.open + 1, state.close));
  }
  return answer;
}

private static class StateQ9Optimized {
  String value; int open, close;
  StateQ9Optimized(String value, int open, int close) { this.value = value; this.open = open; this.close = close; }
}
```

#### 10. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
private static final String[] MAPQ10Optimized = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

public List<String> q10LetterCombinationsOptimized(String digits) {
  List<String> answer = new ArrayList<>();
  if (digits.length() == 0) return answer;
  Queue<String> queue = new ArrayDeque<>();
  queue.offer("");
  for (char digit : digits.toCharArray()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
      String prefix = queue.poll();
      for (char ch : MAPQ10Optimized[digit - '0'].toCharArray()) queue.offer(prefix + ch);
    }
  }
  answer.addAll(queue);
  return answer;
}
```

#### 11. Palindrome Partitioning - [LeetCode 131](https://leetcode.com/problems/palindrome-partitioning/)

Given a string s, partition it so that every substring in the partition is a palindrome. Return all possible palindrome partitionings.

```java
public List<List<String>> q11PartitionOptimized(String s) {
  boolean[][] pal = buildTableQ11Optimized(s);
  List<List<String>> answer = new ArrayList<>();
  Deque<StateQ11Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ11Optimized(0, new ArrayList<>()));
  while (!stack.isEmpty()) {
    StateQ11Optimized state = stack.pop();
    if (state.index == s.length()) { answer.add(state.path); continue; }
    for (int end = s.length() - 1; end >= state.index; end--) {
      if (!pal[state.index][end]) continue;
      List<String> next = new ArrayList<>(state.path);
      next.add(s.substring(state.index, end + 1));
      stack.push(new StateQ11Optimized(end + 1, next));
    }
  }
  return answer;
}

private boolean[][] buildTableQ11Optimized(String s) {
  int n = s.length(); boolean[][] pal = new boolean[n][n];
  for (int len = 1; len <= n; len++) for (int l = 0; l + len - 1 < n; l++) {
    int r = l + len - 1;
    pal[l][r] = s.charAt(l) == s.charAt(r) && (len <= 2 || pal[l + 1][r - 1]);
  }
  return pal;
}

private static class StateQ11Optimized { int index; List<String> path; StateQ11Optimized(int index, List<String> path){this.index=index;this.path=path;} }
```

#### 12. Restore IP Addresses - [LeetCode 93](https://leetcode.com/problems/restore-ip-addresses/)

Given a string containing only digits, return all possible valid IP addresses that can be formed by inserting three dots.

```java
public List<String> q12RestoreIpAddressesOptimized(String s) {
  List<String> answer = new ArrayList<>();
  Deque<StateQ12Optimized> stack = new ArrayDeque<>();
  stack.push(new StateQ12Optimized(0, new ArrayList<>()));
  while (!stack.isEmpty()) {
    StateQ12Optimized state = stack.pop();
    if (state.parts.size() == 4) {
      if (state.index == s.length()) answer.add(String.join(".", state.parts));
      continue;
    }
    for (int len = 3; len >= 1; len--) {
      if (state.index + len > s.length()) continue;
      String part = s.substring(state.index, state.index + len);
      if (!validQ12Optimized(part)) continue;
      List<String> next = new ArrayList<>(state.parts);
      next.add(part);
      stack.push(new StateQ12Optimized(state.index + len, next));
    }
  }
  return answer;
}

private boolean validQ12Optimized(String part) {
  return !(part.length() > 1 && part.charAt(0) == '0') && Integer.parseInt(part) <= 255;
}

private static class StateQ12Optimized { int index; List<String> parts; StateQ12Optimized(int index, List<String> parts){this.index=index;this.parts=parts;} }
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Assign Cookies - [LeetCode 455](https://leetcode.com/problems/assign-cookies/)

Given children greed factors and cookie sizes, return the maximum number of children that can receive one cookie with size at least their greed.

```java
public int q01FindContentChildrenOptimized(int[] g, int[] s) {
  Arrays.sort(g);
  Arrays.sort(s);
  int child = 0;
  int cookie = 0;

  while (child < g.length && cookie < s.length) {
    if (s[cookie] >= g[child]) child++;
    cookie++;
  }
  return child;
}
```

#### 2. Lemonade Change - [LeetCode 860](https://leetcode.com/problems/lemonade-change/)

Customers pay in order with 5, 10, or 20 dollar bills for 5 dollar lemonade. Return true if exact change can be given to every customer.

```java
public boolean q02LemonadeChangeOptimized(int[] bills) {
  int five = 0;
  int ten = 0;

  for (int bill : bills) {
    if (bill == 5) {
      five++;
    } else if (bill == 10) {
      if (five == 0) return false;
      five--;
      ten++;
    } else {
      if (ten > 0 && five > 0) {
        ten--;
        five--;
      } else if (five >= 3) {
        five -= 3;
      } else {
        return false;
      }
    }
  }
  return true;
}
```

#### 3. Jump Game - [LeetCode 55](https://leetcode.com/problems/jump-game/)

Given nums where nums[i] is the maximum jump length from index i, return true if the last index is reachable from index 0.

```java
public boolean q03CanJumpOptimized(int[] nums) {
  int farthest = 0;
  for (int i = 0; i < nums.length; i++) {
    if (i > farthest) return false;
    farthest = Math.max(farthest, i + nums[i]);
    if (farthest >= nums.length - 1) return true;
  }
  return true;
}
```

#### 4. Jump Game II - [LeetCode 45](https://leetcode.com/problems/jump-game-ii/)

Given nums where nums[i] is the maximum jump length from index i, return the minimum number of jumps needed to reach the last index.

```java
public int q04JumpOptimized(int[] nums) {
  int jumps = 0;
  int currentEnd = 0;
  int farthest = 0;

  for (int i = 0; i < nums.length - 1; i++) {
    farthest = Math.max(farthest, i + nums[i]);
    if (i == currentEnd) {
      jumps++;
      currentEnd = farthest;
    }
  }
  return jumps;
}
```

#### 5. Gas Station - [LeetCode 134](https://leetcode.com/problems/gas-station/)

Given gas and cost arrays around a circular route, return the starting station index that can complete the circuit, or -1 if impossible.

```java
public int q05CanCompleteCircuitOptimized(int[] gas, int[] cost) {
  int total = 0;
  int tank = 0;
  int start = 0;

  for (int i = 0; i < gas.length; i++) {
    int diff = gas[i] - cost[i];
    total += diff;
    tank += diff;
    if (tank < 0) {
      start = i + 1;
      tank = 0;
    }
  }
  return total >= 0 ? start : -1;
}
```

#### 6. Candy - [LeetCode 135](https://leetcode.com/problems/candy/)

Given children ratings in a line, give each child at least one candy and give higher-rated children more candies than adjacent lower-rated children. Return the minimum candies needed.

```java
public int q06CandyOptimized(int[] ratings) {
  int n = ratings.length;
  int[] candies = new int[n];
  Arrays.fill(candies, 1);

  for (int i = 1; i < n; i++) {
    if (ratings[i] > ratings[i - 1]) candies[i] = candies[i - 1] + 1;
  }
  for (int i = n - 2; i >= 0; i--) {
    if (ratings[i] > ratings[i + 1]) candies[i] = Math.max(candies[i], candies[i + 1] + 1);
  }

  int total = 0;
  for (int candy : candies) total += candy;
  return total;
}
```

#### 7. Queue Reconstruction by Height - [LeetCode 406](https://leetcode.com/problems/queue-reconstruction-by-height/)

Given people as [height, k], reconstruct a queue where k is the number of people in front with height greater than or equal to height.

```java
public int[][] q07ReconstructQueueOptimized(int[][] people) {
  Arrays.sort(people, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
  List<int[]> queue = new ArrayList<>();

  for (int[] person : people) {
    queue.add(person[1], person);
  }
  return queue.toArray(new int[people.length][]);
}
```

#### 8. Non-overlapping Intervals - [LeetCode 435](https://leetcode.com/problems/non-overlapping-intervals/)

Given intervals, return the minimum number that must be removed so the remaining intervals do not overlap.

```java
public int q08EraseOverlapIntervalsOptimized(int[][] intervals) {
  Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
  int removed = 0;
  int end = Integer.MIN_VALUE;

  for (int[] interval : intervals) {
    if (interval[0] < end) {
      removed++;
    } else {
      end = interval[1];
    }
  }
  return removed;
}
```

#### 9. Merge Intervals - [LeetCode 56](https://leetcode.com/problems/merge-intervals/)

Given intervals, merge all overlapping intervals and return the non-overlapping result.

```java
public int[][] q09MergeOptimized(int[][] intervals) {
  Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
  List<int[]> merged = new ArrayList<>();

  for (int[] interval : intervals) {
    if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
      merged.add(interval.clone());
    } else {
      int[] last = merged.get(merged.size() - 1);
      last[1] = Math.max(last[1], interval[1]);
    }
  }
  return merged.toArray(new int[merged.size()][]);
}
```

#### 10. Minimum Number of Arrows to Burst Balloons - [LeetCode 452](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/)

Given balloon intervals on the x-axis, return the minimum arrows needed; one arrow shot at x bursts every balloon containing x.

```java
public int q10FindMinArrowShotsOptimized(int[][] points) {
  Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
  int arrows = 0;
  long arrow = Long.MIN_VALUE;

  for (int[] point : points) {
    if (arrows == 0 || point[0] > arrow) {
      arrows++;
      arrow = point[1];
    }
  }
  return arrows;
}
```

#### 11. Partition Labels - [LeetCode 763](https://leetcode.com/problems/partition-labels/)

Given a string, split it into as many parts as possible so each letter appears in at most one part. Return the sizes of the parts.

```java
public List<Integer> q11PartitionLabelsOptimized(String s) {
  int[] last = new int[26];
  for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;

  List<Integer> result = new ArrayList<>();
  int start = 0;
  int end = 0;
  for (int i = 0; i < s.length(); i++) {
    end = Math.max(end, last[s.charAt(i) - 'a']);
    if (i == end) {
      result.add(end - start + 1);
      start = i + 1;
    }
  }
  return result;
}
```

#### 12. Task Scheduler - [LeetCode 621](https://leetcode.com/problems/task-scheduler/)

Given CPU tasks represented by letters and a cooldown n, return the least time units needed to execute all tasks with identical tasks separated by at least n intervals.

```java
public int q12LeastIntervalOptimized(char[] tasks, int n) {
  int[] count = new int[26];
  int max = 0;
  for (char task : tasks) {
    count[task - 'A']++;
    max = Math.max(max, count[task - 'A']);
  }

  int maxCount = 0;
  for (int value : count) if (value == max) maxCount++;
  int frame = (max - 1) * (n + 1) + maxCount;
  return Math.max(tasks.length, frame);
}
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

Given n stairs, you can climb either 1 or 2 steps at a time. Return the number of distinct ways to reach the top.

```java
public int q01ClimbStairsOptimized(int n) {
  if (n <= 2) return n;

  int twoBack = 1;
  int oneBack = 2;
  for (int step = 3; step <= n; step++) {
    int current = oneBack + twoBack;
    twoBack = oneBack;
    oneBack = current;
  }
  return oneBack;
}
```

#### 2. Min Cost Climbing Stairs - [LeetCode 746](https://leetcode.com/problems/min-cost-climbing-stairs/)

Given cost[i] for stepping on stair i, return the minimum cost to reach the top when you may climb 1 or 2 steps each move.

```java
public int q02MinCostClimbingStairsOptimized(int[] cost) {
  int twoBack = 0;
  int oneBack = 0;

  for (int i = 2; i <= cost.length; i++) {
    int current = Math.min(oneBack + cost[i - 1], twoBack + cost[i - 2]);
    twoBack = oneBack;
    oneBack = current;
  }
  return oneBack;
}
```

#### 3. House Robber - [LeetCode 198](https://leetcode.com/problems/house-robber/)

Given money in houses along a street, return the maximum amount you can rob without robbing adjacent houses.

```java
public int q03RobOptimized(int[] nums) {
  int twoBack = 0;
  int oneBack = 0;

  for (int money : nums) {
    int current = Math.max(oneBack, twoBack + money);
    twoBack = oneBack;
    oneBack = current;
  }
  return oneBack;
}
```

#### 4. House Robber II - [LeetCode 213](https://leetcode.com/problems/house-robber-ii/)

Given money in houses arranged in a circle, return the maximum amount you can rob without robbing adjacent houses.

```java
public int q04RobOptimized(int[] nums) {
  if (nums.length == 1) return nums[0];
  return Math.max(robLineQ4Optimized(nums, 0, nums.length - 2), robLineQ4Optimized(nums, 1, nums.length - 1));
}

private int robLineQ4Optimized(int[] nums, int start, int end) {
  int twoBack = 0;
  int oneBack = 0;
  for (int i = start; i <= end; i++) {
    int current = Math.max(oneBack, twoBack + nums[i]);
    twoBack = oneBack;
    oneBack = current;
  }
  return oneBack;
}
```

#### 5. Decode Ways - [LeetCode 91](https://leetcode.com/problems/decode-ways/)

Given a digit string where A-Z maps to 1-26, return the number of valid decodings.

```java
public int q05NumDecodingsOptimized(String s) {
  int next = 1;
  int nextNext = 0;

  for (int i = s.length() - 1; i >= 0; i--) {
    int current = 0;
    if (s.charAt(i) != '0') {
      current = next;
      if (i + 1 < s.length()) {
        int value = (s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0');
        if (value <= 26) current += nextNext;
      }
    }
    nextNext = next;
    next = current;
  }
  return next;
}
```

#### 6. Coin Change - [LeetCode 322](https://leetcode.com/problems/coin-change/)

Given coin denominations and an amount, return the fewest number of coins needed to make that amount, or -1 if impossible.

```java
public int q06CoinChangeOptimized(int[] coins, int amount) {
  int[] dp = new int[amount + 1];
  Arrays.fill(dp, amount + 1);
  dp[0] = 0;

  for (int value = 1; value <= amount; value++) {
    for (int coin : coins) {
      if (coin <= value) {
        dp[value] = Math.min(dp[value], dp[value - coin] + 1);
      }
    }
  }
  return dp[amount] > amount ? -1 : dp[amount];
}
```

#### 7. Coin Change II - [LeetCode 518](https://leetcode.com/problems/coin-change-ii/)

Given coin denominations and an amount, return the number of combinations that make up the amount. Each coin may be used unlimited times.

```java
public int q07ChangeOptimized(int amount, int[] coins) {
  int[] dp = new int[amount + 1];
  dp[0] = 1;

  for (int coin : coins) {
    for (int value = coin; value <= amount; value++) {
      dp[value] += dp[value - coin];
    }
  }
  return dp[amount];
}
```

#### 8. Combination Sum IV - [LeetCode 377](https://leetcode.com/problems/combination-sum-iv/)

Given distinct positive nums and target, return the number of ordered combinations that sum to target.

```java
public int q08CombinationSum4Optimized(int[] nums, int target) {
  int[] dp = new int[target + 1];
  dp[0] = 1;

  for (int total = 1; total <= target; total++) {
    for (int num : nums) {
      if (num <= total) dp[total] += dp[total - num];
    }
  }
  return dp[target];
}
```

#### 9. Longest Increasing Subsequence - [LeetCode 300](https://leetcode.com/problems/longest-increasing-subsequence/)

Given an integer array, return the length of the longest strictly increasing subsequence.

```java
public int q09LengthOfLISOptimized(int[] nums) {
  int[] tails = new int[nums.length];
  int size = 0;

  for (int num : nums) {
    int index = Arrays.binarySearch(tails, 0, size, num);
    if (index < 0) index = -index - 1;
    tails[index] = num;
    if (index == size) size++;
  }
  return size;
}
```

#### 10. Longest Arithmetic Subsequence - [LeetCode 1027](https://leetcode.com/problems/longest-arithmetic-subsequence/)

Given an array, return the length of the longest arithmetic subsequence.

```java
public int q10LongestArithSeqLengthOptimized(int[] nums) {
  Map<Integer, Integer>[] dp = new HashMap[nums.length];
  for (int i = 0; i < nums.length; i++) dp[i] = new HashMap<>();
  int best = 2;

  for (int i = 0; i < nums.length; i++) {
    for (int j = 0; j < i; j++) {
      int diff = nums[i] - nums[j];
      int length = dp[j].getOrDefault(diff, 1) + 1;
      dp[i].put(diff, Math.max(dp[i].getOrDefault(diff, 0), length));
      best = Math.max(best, length);
    }
  }
  return best;
}
```

#### 11. Partition Equal Subset Sum - [LeetCode 416](https://leetcode.com/problems/partition-equal-subset-sum/)

Given an integer array, return true if it can be partitioned into two subsets with equal sum.

```java
public boolean q11CanPartitionOptimized(int[] nums) {
  int sum = 0;
  for (int num : nums) sum += num;
  if (sum % 2 == 1) return false;

  int target = sum / 2;
  boolean[] dp = new boolean[target + 1];
  dp[0] = true;
  for (int num : nums) {
    for (int value = target; value >= num; value--) {
      dp[value] = dp[value] || dp[value - num];
    }
  }
  return dp[target];
}
```

#### 12. Word Break - [LeetCode 139](https://leetcode.com/problems/word-break/)

Given a string and a dictionary, return true if the string can be segmented into a sequence of one or more dictionary words.

```java
public boolean q12WordBreakOptimized(String s, List<String> wordDict) {
  Set<String> words = new HashSet<>(wordDict);
  boolean[] dp = new boolean[s.length() + 1];
  dp[0] = true;

  for (int end = 1; end <= s.length(); end++) {
    for (int start = 0; start < end; start++) {
      if (dp[start] && words.contains(s.substring(start, end))) {
        dp[end] = true;
        break;
      }
    }
  }
  return dp[s.length()];
}
```

### Moderate (13-20)

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

### Basic (1-12)

#### 1. Unique Paths - [LeetCode 62](https://leetcode.com/problems/unique-paths/)

Given an m x n grid, a robot starts at the top-left and can move only right or down. Return the number of paths to the bottom-right.

```java
public int q01UniquePathsOptimized(int m, int n) {
  int[] dp = new int[n];
  for (int col = 0; col < n; col++) dp[col] = 1;

  for (int row = 1; row < m; row++) {
    for (int col = 1; col < n; col++) {
      dp[col] += dp[col - 1];
    }
  }
  return dp[n - 1];
}
```

#### 2. Unique Paths II - [LeetCode 63](https://leetcode.com/problems/unique-paths-ii/)

Given an m x n grid where 1 marks an obstacle and 0 marks open space, return the number of right/down paths from top-left to bottom-right.

```java
public int q02UniquePathsWithObstaclesOptimized(int[][] obstacleGrid) {
  int rows = obstacleGrid.length;
  int cols = obstacleGrid[0].length;
  int[] dp = new int[cols];
  dp[0] = obstacleGrid[0][0] == 1 ? 0 : 1;

  for (int row = 0; row < rows; row++) {
    for (int col = 0; col < cols; col++) {
      if (obstacleGrid[row][col] == 1) {
        dp[col] = 0;
      } else if (col > 0) {
        dp[col] += dp[col - 1];
      }
    }
  }
  return dp[cols - 1];
}
```

#### 3. Minimum Path Sum - [LeetCode 64](https://leetcode.com/problems/minimum-path-sum/)

Given a grid of non-negative numbers, return the minimum sum path from top-left to bottom-right moving only right or down.

```java
public int q03MinPathSumOptimized(int[][] grid) {
  int rows = grid.length;
  int cols = grid[0].length;
  int[] dp = new int[cols];

  for (int row = 0; row < rows; row++) {
    for (int col = 0; col < cols; col++) {
      if (row == 0 && col == 0) {
        dp[col] = grid[row][col];
      } else if (row == 0) {
        dp[col] = dp[col - 1] + grid[row][col];
      } else if (col == 0) {
        dp[col] += grid[row][col];
      } else {
        dp[col] = Math.min(dp[col], dp[col - 1]) + grid[row][col];
      }
    }
  }
  return dp[cols - 1];
}
```

#### 4. Triangle - [LeetCode 120](https://leetcode.com/problems/triangle/)

Given a triangle array, return the minimum path sum from top to bottom by moving to adjacent numbers on the next row.

```java
public int q04MinimumTotalOptimized(List<List<Integer>> triangle) {
  int rows = triangle.size();
  int[] dp = new int[rows + 1];

  for (int row = rows - 1; row >= 0; row--) {
    for (int col = 0; col < triangle.get(row).size(); col++) {
      dp[col] = triangle.get(row).get(col) + Math.min(dp[col], dp[col + 1]);
    }
  }
  return dp[0];
}
```

#### 5. Longest Common Subsequence - [LeetCode 1143](https://leetcode.com/problems/longest-common-subsequence/)

Given two strings, return the length of their longest common subsequence.

```java
public int q05LongestCommonSubsequenceOptimized(String text1, String text2) {
  int[] dp = new int[text2.length() + 1];

  for (int i = 1; i <= text1.length(); i++) {
    int diagonal = 0;
    for (int j = 1; j <= text2.length(); j++) {
      int saved = dp[j];
      if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
        dp[j] = diagonal + 1;
      } else {
        dp[j] = Math.max(dp[j], dp[j - 1]);
      }
      diagonal = saved;
    }
  }
  return dp[text2.length()];
}
```

#### 6. Edit Distance - [LeetCode 72](https://leetcode.com/problems/edit-distance/)

Given two words, return the minimum number of insert, delete, and replace operations needed to convert word1 into word2.

```java
public int q06MinDistanceOptimized(String word1, String word2) {
  int m = word1.length();
  int n = word2.length();
  int[] dp = new int[n + 1];
  for (int j = 0; j <= n; j++) dp[j] = j;

  for (int i = 1; i <= m; i++) {
    int diagonal = dp[0];
    dp[0] = i;
    for (int j = 1; j <= n; j++) {
      int saved = dp[j];
      if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
        dp[j] = diagonal;
      } else {
        dp[j] = 1 + Math.min(diagonal, Math.min(dp[j], dp[j - 1]));
      }
      diagonal = saved;
    }
  }
  return dp[n];
}
```

#### 7. Regular Expression Matching - [LeetCode 10](https://leetcode.com/problems/regular-expression-matching/)

Given string s and pattern p containing . and *, return whether p matches the entire string. Dot matches any single character and star repeats the previous element zero or more times.

```java
public boolean q07IsMatchOptimized(String s, String p) {
  int m = s.length();
  int n = p.length();
  boolean[][] dp = new boolean[m + 1][n + 1];
  dp[m][n] = true;

  for (int i = m; i >= 0; i--) {
    for (int j = n - 1; j >= 0; j--) {
      boolean first = i < m && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
      if (j + 1 < n && p.charAt(j + 1) == '*') {
        dp[i][j] = dp[i][j + 2] || first && dp[i + 1][j];
      } else {
        dp[i][j] = first && dp[i + 1][j + 1];
      }
    }
  }
  return dp[0][0];
}
```

#### 8. Wildcard Matching - [LeetCode 44](https://leetcode.com/problems/wildcard-matching/)

Given string s and pattern p containing ? and *, return whether p matches the entire string. ? matches one character and * matches any sequence including empty.

```java
public boolean q08IsMatchOptimized(String s, String p) {
  int m = s.length();
  int n = p.length();
  boolean[] dp = new boolean[n + 1];
  dp[0] = true;

  for (int j = 1; j <= n; j++) {
    dp[j] = dp[j - 1] && p.charAt(j - 1) == '*';
  }

  for (int i = 1; i <= m; i++) {
    boolean diagonal = dp[0];
    dp[0] = false;
    for (int j = 1; j <= n; j++) {
      boolean saved = dp[j];
      char ch = p.charAt(j - 1);
      if (ch == '*') dp[j] = dp[j] || dp[j - 1];
      else dp[j] = diagonal && (ch == '?' || ch == s.charAt(i - 1));
      diagonal = saved;
    }
  }
  return dp[n];
}
```

#### 9. Distinct Subsequences - [LeetCode 115](https://leetcode.com/problems/distinct-subsequences/)

Given strings s and t, return the number of distinct subsequences of s equal to t.

```java
public int q09NumDistinctOptimized(String s, String t) {
  long[] dp = new long[t.length() + 1];
  dp[0] = 1;

  for (int i = 0; i < s.length(); i++) {
    for (int j = t.length() - 1; j >= 0; j--) {
      if (s.charAt(i) == t.charAt(j)) {
        dp[j + 1] += dp[j];
      }
    }
  }
  return (int) dp[t.length()];
}
```

#### 10. Interleaving String - [LeetCode 97](https://leetcode.com/problems/interleaving-string/)

Given s1, s2, and s3, return true if s3 is formed by interleaving s1 and s2 while preserving the order of characters from each string.

```java
public boolean q10IsInterleaveOptimized(String s1, String s2, String s3) {
  if (s1.length() + s2.length() != s3.length()) return false;
  boolean[] dp = new boolean[s2.length() + 1];
  dp[0] = true;

  for (int i = 0; i <= s1.length(); i++) {
    for (int j = 0; j <= s2.length(); j++) {
      if (i == 0 && j == 0) continue;
      int k = i + j - 1;
      boolean fromS1 = i > 0 && dp[j] && s1.charAt(i - 1) == s3.charAt(k);
      boolean fromS2 = j > 0 && dp[j - 1] && s2.charAt(j - 1) == s3.charAt(k);
      dp[j] = fromS1 || fromS2;
    }
  }
  return dp[s2.length()];
}
```

#### 11. Longest Palindromic Subsequence - [LeetCode 516](https://leetcode.com/problems/longest-palindromic-subsequence/)

Given a string s, return the length of the longest subsequence that reads the same forward and backward.

```java
public int q11LongestPalindromeSubseqOptimized(String s) {
  int n = s.length();
  int[] dp = new int[n];

  for (int left = n - 1; left >= 0; left--) {
    dp[left] = 1;
    int diagonal = 0;
    for (int right = left + 1; right < n; right++) {
      int saved = dp[right];
      if (s.charAt(left) == s.charAt(right)) {
        dp[right] = diagonal + 2;
      } else {
        dp[right] = Math.max(dp[right], dp[right - 1]);
      }
      diagonal = saved;
    }
  }
  return dp[n - 1];
}
```

#### 12. Longest Palindromic Substring - [LeetCode 5](https://leetcode.com/problems/longest-palindromic-substring/)

Given a string s, return the longest contiguous substring that is a palindrome.

```java
public String q12LongestPalindromeOptimized(String s) {
  int n = s.length();
  boolean[][] dp = new boolean[n][n];
  int bestStart = 0;
  int bestLength = 1;

  for (int length = 1; length <= n; length++) {
    for (int left = 0; left + length - 1 < n; left++) {
      int right = left + length - 1;
      boolean innerOk = length <= 2 || dp[left + 1][right - 1];
      dp[left][right] = s.charAt(left) == s.charAt(right) && innerOk;
      if (dp[left][right] && length > bestLength) {
        bestStart = left;
        bestLength = length;
      }
    }
  }
  return s.substring(bestStart, bestStart + bestLength);
}
```

### Moderate (13-20)

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

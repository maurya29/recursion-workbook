package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 10: Recursion. Moderate questions 13-20. */
public class RecursionModerate {

  /*
   * Question 13: Combination Sum
   * 
   * Question: Given distinct candidate numbers and a target, return all unique combinations where chosen numbers sum to target. A candidate may be chosen unlimited times.
   * 
   * Constraints: 1 <= candidates.length <= 30; 2 <= candidates[i] <= 40; all candidates are distinct; 1 <= target <= 40.
   * 
   * Optimized time/space complexity: Time O(S * (n + D)); auxiliary space O(F*D), plus output. n is candidate count, D = target/minCandidate, S is explored states and F is maximum pending states; worst-case exponential search.
   * Recursive time/space complexity: Time O(n^(D+1) + R*D) upper bound; auxiliary space O(D + log n), plus O(R*D) output. D = target/minCandidate and R is solution count; sorted pruning reduces the search.
   * 
   * Example 1:
   * Input: candidates = [2,3,6,7], target = 7
   * Output: [[2,2,3],[7]]
   * Explanation: 2 can be reused and 7 is an exact match.
   * 
   * Example 2:
   * Input: candidates = [2,3,5], target = 8
   * Output: [[2,2,2,2],[2,3,3],[3,5]]
   * Explanation: All unique combinations summing to 8 are returned.
   * 
   * Example 3:
   * Input: candidates = [2], target = 1
   * Output: []
   * Explanation: No candidate can sum to the target.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 14: N-Queens
   * 
   * Question: Given n, return all distinct solutions to the n-queens puzzle, placing n queens on an n x n chessboard so no two queens attack each other.
   * 
   * Constraints: 1 <= n <= 9.
   * 
   * Optimized time/space complexity: Time O(n * n! + R * n^2) upper bound; auxiliary space O(n), plus O(R * n^2) output, where R is the number of valid boards.
   * Recursive time/space complexity: Time O(n * n! + R * n^2) upper bound; auxiliary space O(n), plus O(R * n^2) output, where R is the number of valid boards.
   * 
   * Example 1:
   * Input: n = 4
   * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
   * Explanation: There are two valid 4-queen boards.
   * 
   * Example 2:
   * Input: n = 1
   * Output: [["Q"]]
   * Explanation: A single queen is valid.
   * 
   * Example 3:
   * Input: n = 2
   * Output: []
   * Explanation: No arrangement avoids attacks.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 15: Sudoku Solver
   * 
   * Question: Write a program to solve a 9 x 9 Sudoku board by filling empty cells marked with dot characters.
   * 
   * Constraints: board.length == 9; board[i].length == 9; board contains digits 1..9 or dot; the input has exactly one solution.
   * 
   * Optimized time/space complexity: Time O(9^E); auxiliary space O(E), where E is the number of empty cells. Iterative backtracking uses row/column/box boolean tables.
   * Recursive time/space complexity: Time O(9^e) worst case with O(1) validity checks; Space O(e). Recursive backtracking fills one blank per level.
   * 
   * Example 1:
   * Input: board = [["5", "3", ".", ".", "7", ".", ".", ".", "."], ["6", ".", ".", "1", "9", "5", ".", ".", "."], [".", "9", "8", ".", ".", ".", ".", "6", "."], ["8", ".", ".", ".", "6", ".", ".", ".", "3"], ["4", ".", ".", "8", ".", "3", ".", ".", "1"], ["7", ".", ".", ".", "2", ".", ".", ".", "6"], [".", "6", ".", ".", ".", ".", "2", "8", "."], [".", ".", ".", "4", "1", "9", ".", ".", "5"], [".", ".", ".", ".", "8", ".", ".", "7", "9"]]
   * Output: [["5", "3", "4", "6", "7", "8", "9", "1", "2"], ["6", "7", "2", "1", "9", "5", "3", "4", "8"], ["1", "9", "8", "3", "4", "2", "5", "6", "7"], ["8", "5", "9", "7", "6", "1", "4", "2", "3"], ["4", "2", "6", "8", "5", "3", "7", "9", "1"], ["7", "1", "3", "9", "2", "4", "8", "5", "6"], ["9", "6", "1", "5", "3", "7", "2", "8", "4"], ["2", "8", "7", "4", "1", "9", "6", "3", "5"], ["3", "4", "5", "2", "8", "6", "1", "7", "9"]]
   * Explanation: Fill all blanks while preserving the given digits.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 16: Word Search
   * 
   * Question: Given an m x n board of characters and a word, return true if the word exists in the grid by moving horizontally or vertically without reusing a cell.
   * 
   * Constraints: 1 <= m, n <= 6; 1 <= word.length <= 15; board and word contain uppercase/lowercase English letters in the original problem.
   * 
   * Optimized time/space complexity: Time O(m*n*3^L); Space O(L). Iterative DFS avoids immediately revisiting the previous path cells.
   * Recursive time/space complexity: Time O(m*n*3^L); Space O(L). Recursive backtracking marks cells in-place during the path.
   * 
   * Example 1:
   * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
   * Output: true
   * Explanation: A valid adjacent path spells the word.
   * 
   * Example 2:
   * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
   * Output: true
   * Explanation: The word can be formed on the right side.
   * 
   * Example 3:
   * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
   * Output: false
   * Explanation: The path would need to reuse B.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 17: Same Tree
   * 
   * Question: Given the roots of two binary trees p and q, return true if they are structurally identical and node values are equal.
   * 
   * Constraints: 0 <= number of nodes <= 100; -10000 <= Node.val <= 10000.
   * 
   * Optimized time/space complexity: Time O(n); auxiliary space O(h) for the explicit DFS stack, where h is tree height.
   * Recursive time/space complexity: Time O(n); Space O(h). Recursive DFS compares corresponding subtrees.
   * 
   * Example 1:
   * Input: p = [1,2,3], q = [1,2,3]
   * Output: true
   * Explanation: Structure and values match.
   * 
   * Example 2:
   * Input: p = [1,2], q = [1,null,2]
   * Output: false
   * Explanation: The shapes differ.
   * 
   * Example 3:
   * Input: p = [1,2,1], q = [1,1,2]
   * Output: false
   * Explanation: The values differ by position.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public boolean q17IsSameTreeRecursive(TreeNode p, TreeNode q) {
    if (p == null || q == null) return p == q;
    return p.val == q.val
        && q17IsSameTreeRecursive(p.left, q.left)
        && q17IsSameTreeRecursive(p.right, q.right);
  }

  /*
   * Question 18: Symmetric Tree
   * 
   * Question: Given the root of a binary tree, return true if it is symmetric around its center.
   * 
   * Constraints: 1 <= number of nodes <= 1000; -100 <= Node.val <= 100.
   * 
   * Optimized time/space complexity: Time O(n); Space O(w). Iterative queue compares mirror pairs.
   * Recursive time/space complexity: Time O(n); Space O(h). Recursive mirror comparison follows opposite branches.
   * 
   * Example 1:
   * Input: root = [1,2,2,3,4,4,3]
   * Output: true
   * Explanation: The left and right sides mirror each other.
   * 
   * Example 2:
   * Input: root = [1,2,2,null,3,null,3]
   * Output: false
   * Explanation: Null positions do not mirror.
   * 
   * Example 3:
   * Input: root = [1]
   * Output: true
   * Explanation: A single node is symmetric.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public boolean q18IsSymmetricRecursive(TreeNode root) {
    return root == null || mirrorQ18Recursive(root.left, root.right);
  }

  private boolean mirrorQ18Recursive(TreeNode left, TreeNode right) {
    if (left == null || right == null) return left == right;
    return left.val == right.val
        && mirrorQ18Recursive(left.left, right.right)
        && mirrorQ18Recursive(left.right, right.left);
  }

  /*
   * Question 19: Maximum Depth of Binary Tree
   * 
   * Question: Given the root of a binary tree, return its maximum depth.
   * 
   * Constraints: 0 <= number of nodes <= 10000; -100 <= Node.val <= 100.
   * 
   * Optimized time/space complexity: Time O(n); Space O(w). BFS counts levels iteratively.
   * Recursive time/space complexity: Time O(n); Space O(h). Recursive DFS returns height from children.
   * 
   * Example 1:
   * Input: root = [3,9,20,null,null,15,7]
   * Output: 3
   * Explanation: The longest root-to-leaf path has 3 nodes.
   * 
   * Example 2:
   * Input: root = [1,null,2]
   * Output: 2
   * Explanation: The right-skewed path has length 2.
   * 
   * Example 3:
   * Input: root = []
   * Output: 0
   * Explanation: An empty tree has depth 0.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public int q19MaxDepthRecursive(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(q19MaxDepthRecursive(root.left), q19MaxDepthRecursive(root.right));
  }

  /*
   * Question 20: Invert Binary Tree
   * 
   * Question: Given the root of a binary tree, invert the tree and return its root.
   * 
   * Constraints: 0 <= number of nodes <= 100; -100 <= Node.val <= 100.
   * 
   * Optimized time/space complexity: Time O(n); Space O(w). Iterative BFS swaps every node once.
   * Recursive time/space complexity: Time O(n); Space O(h). Recursive DFS swaps current node and subtrees.
   * 
   * Example 1:
   * Input: root = [4,2,7,1,3,6,9]
   * Output: [4,7,2,9,6,3,1]
   * Explanation: Every left and right child pair is swapped.
   * 
   * Example 2:
   * Input: root = [2,1,3]
   * Output: [2,3,1]
   * Explanation: The two children of the root swap.
   * 
   * Example 3:
   * Input: root = []
   * Output: []
   * Explanation: Empty tree remains empty.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public TreeNode q20InvertTreeRecursive(TreeNode root) {
    if (root == null) return null;

    TreeNode left = q20InvertTreeRecursive(root.left);
    TreeNode right = q20InvertTreeRecursive(root.right);
    root.left = right;
    root.right = left;
    return root;
  }
}

package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 11: Backtracking. Moderate questions 13-20. */
public class BacktrackingModerate {

  /*
   * Question 13: Word Search
   * 
   * Question: Given an m x n character board and a word, return true if the word exists in the grid by adjacent horizontal or vertical moves without reusing cells.
   * 
   * Constraints: 1 <= m, n <= 6; 1 <= word.length <= 15.
   * 
   * Optimized time/space complexity: Time O(m*n*3^L); Space O(L). Iterative DFS avoids revisiting cells already in the path.
   * Recursive time/space complexity: Time O(m*n*3^L); Space O(L). In-place marking keeps visited state compact.
   * 
   * Example 1:
   * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
   * Output: true
   * Explanation: A valid adjacent path spells the word.
   * 
   * Example 2:
   * Input: board = same, word = "SEE"
   * Output: true
   * Explanation: SEE exists on the board.
   * 
   * Example 3:
   * Input: board = same, word = "ABCB"
   * Output: false
   * Explanation: The path would need to reuse B.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   * Adaptation: Uses the equivalent 64-bit visited-mask search from Recursion Q16; the stated board bounds are at most 6 by 6.
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 14: N-Queens
   * 
   * Question: Given n, return all distinct boards that place n queens on an n x n board so no two queens attack each other.
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
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   * Adaptation: Uses the equivalent iterative row/backtrack solution from Recursion Q14 to avoid cloning constraint arrays per state.
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

  /*
   * Question 15: Sudoku Solver
   * 
   * Question: Solve a 9 x 9 Sudoku board by filling empty cells marked with dots. The given board has exactly one solution.
   * 
   * Constraints: board is 9 x 9; cells are digits 1..9 or dot; the puzzle has exactly one solution.
   * 
   * Optimized time/space complexity: Time O(9^E); auxiliary space O(E), where E is the number of empty cells. Iterative backtracking uses row/column/box boolean tables.
   * Recursive time/space complexity: Time O(9^e); Space O(e). Backtracking with constraint tables tests digits in O(1).
   * 
   * Example 1:
   * Input: board = [["5", "3", ".", ".", "7", ".", ".", ".", "."], ["6", ".", ".", "1", "9", "5", ".", ".", "."], [".", "9", "8", ".", ".", ".", ".", "6", "."], ["8", ".", ".", ".", "6", ".", ".", ".", "3"], ["4", ".", ".", "8", ".", "3", ".", ".", "1"], ["7", ".", ".", ".", "2", ".", ".", ".", "6"], [".", "6", ".", ".", ".", ".", "2", "8", "."], [".", ".", ".", "4", "1", "9", ".", ".", "5"], [".", ".", ".", ".", "8", ".", ".", "7", "9"]]
   * Output: [["5", "3", "4", "6", "7", "8", "9", "1", "2"], ["6", "7", "2", "1", "9", "5", "3", "4", "8"], ["1", "9", "8", "3", "4", "2", "5", "6", "7"], ["8", "5", "9", "7", "6", "1", "4", "2", "3"], ["4", "2", "6", "8", "5", "3", "7", "9", "1"], ["7", "1", "3", "9", "2", "4", "8", "5", "6"], ["9", "6", "1", "5", "3", "7", "2", "8", "4"], ["2", "8", "7", "4", "1", "9", "6", "3", "5"], ["3", "4", "5", "2", "8", "6", "1", "7", "9"]]
   * Explanation: Fill all blanks while preserving the given digits.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 16: Rat in a Maze
   * 
   * Question: Given an n x n maze matrix where 1 means open and 0 means blocked, return all paths from top-left to bottom-right using moves D, L, R, and U without revisiting cells.
   * 
   * Constraints: 1 <= n <= 5 in common interview variants; mat[i][j] is 0 or 1.
   * 
   * Optimized time/space complexity: Time O(n^2 * 4^(n^2)); auxiliary space O(n^4), plus output. Pending DFS states copy an n-by-n visited matrix and a path.
   * Recursive time/space complexity: Time O(4^(n^2)); Space O(n^2). Recursive DFS marks and unmarks visited cells.
   * 
   * Example 1:
   * Input: mat = [[1,0,0,0],[1,1,0,1],[1,1,0,0],[0,1,1,1]]
   * Output: ["DDRDRR","DRDDRR"]
   * Explanation: Two valid paths reach the bottom-right cell.
   * 
   * Example 2:
   * Input: mat = [[1,0],[0,1]]
   * Output: []
   * Explanation: The destination is isolated.
   * 
   * Example 3:
   * Input: mat = [[1]]
   * Output: [""]
   * Explanation: The rat starts at the destination.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 17: Unique Paths III
   * 
   * Question: Given a grid with start, end, empty cells, and obstacles, return the number of 4-directional paths from start to end that visit every non-obstacle cell exactly once.
   * 
   * Constraints: 1 <= m, n <= 20; total cells <= 20 in the original problem.
   * 
   * Optimized time/space complexity: Time O(4^E); Space O(E). Iterative stack carries position, remaining count, and visited mask.
   * Recursive time/space complexity: Time O(4^E); Space O(E). Backtracking marks cells in-place and decrements remaining count.
   * 
   * Example 1:
   * Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
   * Output: 2
   * Explanation: Two paths visit all non-obstacle cells.
   * 
   * Example 2:
   * Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
   * Output: 4
   * Explanation: Four Hamiltonian paths exist.
   * 
   * Example 3:
   * Input: grid = [[0,1],[2,0]]
   * Output: 0
   * Explanation: No path can visit every open cell exactly once.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 18: Matchsticks to Square
   * 
   * Question: Given matchsticks, return true if they can form a square using every matchstick exactly once.
   * 
   * Constraints: 1 <= matchsticks.length <= 15; 1 <= matchsticks[i] <= 100000000.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); Space O(2^n). Bitmask DP tracks current side sum modulo target.
   * Recursive time/space complexity: Time O(4^n) worst case with strong pruning; Space O(n). Descending backtracking reduces branches.
   * 
   * Example 1:
   * Input: matchsticks = [1,1,2,2,2]
   * Output: true
   * Explanation: Each side can sum to 2.
   * 
   * Example 2:
   * Input: matchsticks = [3,3,3,3,4]
   * Output: false
   * Explanation: The total cannot form four equal sides.
   * 
   * Example 3:
   * Input: matchsticks = [5,5,5,5]
   * Output: true
   * Explanation: Each side gets one stick.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 19: Partition to K Equal Sum Subsets
   * 
   * Question: Given nums and k, return true if nums can be partitioned into k non-empty subsets with equal sum.
   * 
   * Constraints: 1 <= k <= nums.length <= 16; 1 <= nums[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); Space O(2^n). Bitmask DP tracks partial bucket sum modulo target.
   * Recursive time/space complexity: Time O(k^n) worst case with pruning; Space O(n + k). Sorted bucket backtracking skips symmetric states.
   * 
   * Example 1:
   * Input: nums = [4,3,2,3,5,2,1], k = 4
   * Output: true
   * Explanation: Each subset can sum to 5.
   * 
   * Example 2:
   * Input: nums = [1,2,3,4], k = 3
   * Output: false
   * Explanation: Total cannot split into equal integer sums.
   * 
   * Example 3:
   * Input: nums = [2,2,2,2], k = 2
   * Output: true
   * Explanation: Two subsets can each sum to 4.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 20: Beautiful Arrangement
   * 
   * Question: Given n, count the number of arrangements of 1..n where at position i, either perm[i] is divisible by i or i is divisible by perm[i].
   * 
   * Constraints: 1 <= n <= 15.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); Space O(2^n). Iterative DP over used-number masks.
   * Recursive time/space complexity: Time O(n * 2^n); Space O(2^n + n). Memoized backtracking counts each mask once.
   * 
   * Example 1:
   * Input: n = 2
   * Output: 2
   * Explanation: [1,2] and [2,1] are valid.
   * 
   * Example 2:
   * Input: n = 1
   * Output: 1
   * Explanation: Only one arrangement exists.
   * 
   * Example 3:
   * Input: n = 3
   * Output: 3
   * Explanation: Three arrangements satisfy the rule.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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
}

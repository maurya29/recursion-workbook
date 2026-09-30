package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 18: 2D Dynamic Programming. Basic questions 1-12. */
public class DynamicProgramming2DBasic {

  /*
   * Question 1: Unique Paths
   * 
   * Question: Given an m x n grid, a robot starts at the top-left and can move only right or down. Return the number of paths to the bottom-right.
   * 
   * Constraints: 1 <= m, n <= 100; answer is at most 2 * 10^9.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). 1D row DP updates path counts left to right.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion computes each cell once.
   * 
   * Example 1:
   * Input: m = 3, n = 7
   * Output: 28
   * Explanation: There are 28 right/down paths.
   * 
   * Example 2:
   * Input: m = 3, n = 2
   * Output: 3
   * Explanation: The paths are DDR, DRD, and RDD in move notation.
   * 
   * Example 3:
   * Input: m = 1, n = 5
   * Output: 1
   * Explanation: Only right moves are possible.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 2: Unique Paths II
   * 
   * Question: Given an m x n grid where 1 marks an obstacle and 0 marks open space, return the number of right/down paths from top-left to bottom-right.
   * 
   * Constraints: 1 <= m, n <= 100; obstacleGrid[i][j] is 0 or 1.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). A 1D row DP resets blocked cells to zero.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion stores path counts per cell.
   * 
   * Example 1:
   * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
   * Output: 2
   * Explanation: Two paths go around the obstacle.
   * 
   * Example 2:
   * Input: obstacleGrid = [[0,1],[0,0]]
   * Output: 1
   * Explanation: Only the down-right path is open.
   * 
   * Example 3:
   * Input: obstacleGrid = [[1]]
   * Output: 0
   * Explanation: The start cell is blocked.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 3: Minimum Path Sum
   * 
   * Question: Given a grid of non-negative numbers, return the minimum sum path from top-left to bottom-right moving only right or down.
   * 
   * Constraints: 1 <= m, n <= 200; 0 <= grid[i][j] <= 200.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). Rolling row DP keeps minimum cost for each column.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion stores minimum suffix cost per cell.
   * 
   * Example 1:
   * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
   * Output: 7
   * Explanation: The path 1 -> 3 -> 1 -> 1 -> 1 has sum 7.
   * 
   * Example 2:
   * Input: grid = [[1,2,3],[4,5,6]]
   * Output: 12
   * Explanation: The minimum path is 1 -> 2 -> 3 -> 6.
   * 
   * Example 3:
   * Input: grid = [[5]]
   * Output: 5
   * Explanation: Only one cell is used.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 4: Triangle
   * 
   * Question: Given a triangle array, return the minimum path sum from top to bottom by moving to adjacent numbers on the next row.
   * 
   * Constraints: 1 <= triangle.length <= 200; triangle[i].length == i + 1; -10000 <= values <= 10000.
   * 
   * Optimized time/space complexity: Time O(rows^2); Space O(rows). Bottom-up DP keeps one row of costs.
   * Recursive time/space complexity: Time O(rows^2); Space O(rows^2). Memoized recursion stores each triangle position.
   * 
   * Example 1:
   * Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
   * Output: 11
   * Explanation: The path 2 -> 3 -> 5 -> 1 has sum 11.
   * 
   * Example 2:
   * Input: triangle = [[-10]]
   * Output: -10
   * Explanation: Only one value exists.
   * 
   * Example 3:
   * Input: triangle = [[1],[2,3]]
   * Output: 3
   * Explanation: Choose 1 -> 2.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 5: Longest Common Subsequence
   * 
   * Question: Given two strings, return the length of their longest common subsequence.
   * 
   * Constraints: 1 <= text1.length, text2.length <= 1000; strings contain lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). Rolling row DP stores LCS lengths for the second string.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion computes each pair of indexes once.
   * 
   * Example 1:
   * Input: text1 = "abcde", text2 = "ace"
   * Output: 3
   * Explanation: ace is a common subsequence.
   * 
   * Example 2:
   * Input: text1 = "abc", text2 = "abc"
   * Output: 3
   * Explanation: The full string is common.
   * 
   * Example 3:
   * Input: text1 = "abc", text2 = "def"
   * Output: 0
   * Explanation: There are no common characters.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 6: Edit Distance
   * 
   * Question: Given two words, return the minimum number of insert, delete, and replace operations needed to convert word1 into word2.
   * 
   * Constraints: 0 <= word1.length, word2.length <= 500; words contain lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). Rolling DP keeps previous row and current row values.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion solves each index pair once.
   * 
   * Example 1:
   * Input: word1 = "horse", word2 = "ros"
   * Output: 3
   * Explanation: horse -> rorse -> rose -> ros.
   * 
   * Example 2:
   * Input: word1 = "intention", word2 = "execution"
   * Output: 5
   * Explanation: Five edits are required in an optimal sequence.
   * 
   * Example 3:
   * Input: word1 = "", word2 = "abc"
   * Output: 3
   * Explanation: Insert all three characters.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 7: Regular Expression Matching
   * 
   * Question: Given string s and pattern p containing . and *, return whether p matches the entire string. Dot matches any single character and star repeats the previous element zero or more times.
   * 
   * Constraints: 1 <= s.length <= 20; 1 <= p.length <= 20; pattern is valid and uses lowercase letters, dot, and star.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(mn). Bottom-up DP fills string-pattern states.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion stores each pair of indexes.
   * 
   * Example 1:
   * Input: s = "aa", p = "a"
   * Output: false
   * Explanation: The pattern covers only one a.
   * 
   * Example 2:
   * Input: s = "aa", p = "a*"
   * Output: true
   * Explanation: a* can repeat a twice.
   * 
   * Example 3:
   * Input: s = "ab", p = ".*"
   * Output: true
   * Explanation: .* can match any full string.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 8: Wildcard Matching
   * 
   * Question: Given string s and pattern p containing ? and *, return whether p matches the entire string. ? matches one character and * matches any sequence including empty.
   * 
   * Constraints: 0 <= s.length, p.length <= 2000; p contains lowercase letters, ? and *.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). Rolling pattern DP can match every string-pattern state.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion stores each pair of indexes.
   * 
   * Example 1:
   * Input: s = "aa", p = "a"
   * Output: false
   * Explanation: The pattern does not cover the full string.
   * 
   * Example 2:
   * Input: s = "aa", p = "*"
   * Output: true
   * Explanation: * can match both characters.
   * 
   * Example 3:
   * Input: s = "cb", p = "?a"
   * Output: false
   * Explanation: ? matches c but a does not match b.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 9: Distinct Subsequences
   * 
   * Question: Given strings s and t, return the number of distinct subsequences of s equal to t.
   * 
   * Constraints: 1 <= s.length, t.length <= 1000; strings contain English letters.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). 1D DP updates target positions backward.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion stores source and target indexes.
   * 
   * Example 1:
   * Input: s = "rabbbit", t = "rabbit"
   * Output: 3
   * Explanation: There are three ways to delete one b.
   * 
   * Example 2:
   * Input: s = "babgbag", t = "bag"
   * Output: 5
   * Explanation: Five subsequences spell bag.
   * 
   * Example 3:
   * Input: s = "abc", t = "abcd"
   * Output: 0
   * Explanation: t is longer than s.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 10: Interleaving String
   * 
   * Question: Given s1, s2, and s3, return true if s3 is formed by interleaving s1 and s2 while preserving the order of characters from each string.
   * 
   * Constraints: 0 <= s1.length, s2.length <= 100; 0 <= s3.length <= 200; strings contain lowercase letters.
   * 
   * Optimized time/space complexity: Time O(mn); Space O(n). Rolling DP tracks feasible counts from s2 for each s1 prefix.
   * Recursive time/space complexity: Time O(mn); Space O(mn). Memoized recursion solves each pair of consumed lengths once.
   * 
   * Example 1:
   * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
   * Output: true
   * Explanation: s3 can be formed while preserving both orders.
   * 
   * Example 2:
   * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
   * Output: false
   * Explanation: No valid interleaving matches all characters.
   * 
   * Example 3:
   * Input: s1 = "", s2 = "", s3 = ""
   * Output: true
   * Explanation: All strings are empty.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 11: Longest Palindromic Subsequence
   * 
   * Question: Given a string s, return the length of the longest subsequence that reads the same forward and backward.
   * 
   * Constraints: 1 <= s.length <= 1000; s contains lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(n^2); Space O(n). Rolling interval DP stores results for the current left boundary.
   * Recursive time/space complexity: Time O(n^2); Space O(n^2). Memoization stores every interval once.
   * 
   * Example 1:
   * Input: s = "bbbab"
   * Output: 4
   * Explanation: bbbb is a palindromic subsequence.
   * 
   * Example 2:
   * Input: s = "cbbd"
   * Output: 2
   * Explanation: bb is the longest palindromic subsequence.
   * 
   * Example 3:
   * Input: s = "abc"
   * Output: 1
   * Explanation: Any single character is valid.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 12: Longest Palindromic Substring
   * 
   * Question: Given a string s, return the longest contiguous substring that is a palindrome.
   * 
   * Constraints: 1 <= s.length <= 1000; s contains digits and English letters.
   * 
   * Optimized time/space complexity: Time O(n^2); Space O(n^2). Boolean interval DP marks palindromic substrings.
   * Recursive time/space complexity: Time O(n^2); Space O(n^2). Memoized palindrome checks are reused while scanning intervals.
   * 
   * Example 1:
   * Input: s = "babad"
   * Output: "bab"
   * Explanation: aba is also a valid longest answer.
   * 
   * Example 2:
   * Input: s = "cbbd"
   * Output: "bb"
   * Explanation: bb is the longest palindromic substring.
   * 
   * Example 3:
   * Input: s = "a"
   * Output: "a"
   * Explanation: A single character is a palindrome.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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
}

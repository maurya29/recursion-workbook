package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 18: 2D Dynamic Programming. Moderate questions 13-20. */
public class DynamicProgramming2DModerate {

  /*
   * Question 13: Palindromic Substrings
   * 
   * Question: Given a string s, return the number of contiguous substrings that are palindromes.
   * 
   * Constraints: 1 <= s.length <= 1000; s contains lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(n^2); Space O(n^2). Interval DP computes palindrome status once per substring.
   * Recursive time/space complexity: Time O(n^2); Space O(n^2). Recursive palindrome checks are memoized for all intervals.
   * 
   * Example 1:
   * Input: s = "abc"
   * Output: 3
   * Explanation: Only a, b, and c are palindromes.
   * 
   * Example 2:
   * Input: s = "aaa"
   * Output: 6
   * Explanation: All three singles, two aa substrings, and aaa count.
   * 
   * Example 3:
   * Input: s = "abccba"
   * Output: 9
   * Explanation: The full string and inner palindromes are included.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 14: Coin Change II
   * 
   * Question: Given coin denominations and an amount, return the number of combinations that make the amount. Each coin can be used unlimited times.
   * 
   * Constraints: 1 <= coins.length <= 300; 1 <= coins[i] <= 5000; 0 <= amount <= 5000.
   * 
   * Optimized time/space complexity: Time O(n * amount); Space O(amount). 1D unbounded knapsack adds current coin contributions left to right.
   * Recursive time/space complexity: Time O(n * amount); Space O(n * amount). Memoization stores coin index and remaining amount.
   * 
   * Example 1:
   * Input: amount = 5, coins = [1,2,5]
   * Output: 4
   * Explanation: The combinations are 5, 2+2+1, 2+1+1+1, and five 1s.
   * 
   * Example 2:
   * Input: amount = 3, coins = [2]
   * Output: 0
   * Explanation: No combination reaches 3.
   * 
   * Example 3:
   * Input: amount = 0, coins = [1,2]
   * Output: 1
   * Explanation: The empty combination makes amount zero.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 15: Target Sum
   * 
   * Question: Given nums and target, assign either plus or minus before every number. Return how many assignments evaluate to target.
   * 
   * Constraints: 1 <= nums.length <= 20; 0 <= nums[i] <= 1000; -1000 <= target <= 1000; total sum <= 1000.
   * 
   * Optimized time/space complexity: Time O(n * sum); Space O(sum). Offset DP stores counts for reachable signed sums.
   * Recursive time/space complexity: Time O(n * sum); Space O(n * sum). Memoization stores index and offset running sum.
   * 
   * Example 1:
   * Input: nums = [1,1,1,1,1], target = 3
   * Output: 5
   * Explanation: Five sign assignments produce 3.
   * 
   * Example 2:
   * Input: nums = [1], target = 1
   * Output: 1
   * Explanation: Only +1 works.
   * 
   * Example 3:
   * Input: nums = [0,0,1], target = 1
   * Output: 4
   * Explanation: Each zero can be plus or minus without changing the sum.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 16: Ones and Zeroes
   * 
   * Question: Given binary strings strs and limits m zeros and n ones, return the maximum number of strings that can be chosen without exceeding either limit.
   * 
   * Constraints: 1 <= strs.length <= 600; 1 <= strs[i].length <= 100; 0 <= m, n <= 100.
   * 
   * Optimized time/space complexity: Time O(K * (L + m*n)); Space O(m*n), where K is string count and L is maximum string length. Each string is counted once.
   * Recursive time/space complexity: Time O(k * m * n * L); Space O(k * m * n). Memoization stores index and two capacities.
   * 
   * Example 1:
   * Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
   * Output: 4
   * Explanation: Choose 10, 0001, 1, and 0.
   * 
   * Example 2:
   * Input: strs = ["10","0","1"], m = 1, n = 1
   * Output: 2
   * Explanation: Choose 0 and 1 or choose 10.
   * 
   * Example 3:
   * Input: strs = ["111","1000"], m = 2, n = 2
   * Output: 0
   * Explanation: Neither string fits both capacities.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 17: Last Stone Weight II
   * 
   * Question: Given stone weights, repeatedly smashing stones is equivalent to splitting stones into two groups. Return the minimum possible remaining weight.
   * 
   * Constraints: 1 <= stones.length <= 30; 1 <= stones[i] <= 100.
   * 
   * Optimized time/space complexity: Time O(n * total); Space O(total). 0/1 subset DP finds the closest half-sum.
   * Recursive time/space complexity: Time O(n * total); Space O(n * total). Memoization stores index and current bounded subset sum.
   * 
   * Example 1:
   * Input: stones = [2,7,4,1,8,1]
   * Output: 1
   * Explanation: Groups can be split with sums 11 and 12.
   * 
   * Example 2:
   * Input: stones = [31,26,33,21,40]
   * Output: 5
   * Explanation: The closest partition leaves difference 5.
   * 
   * Example 3:
   * Input: stones = [1]
   * Output: 1
   * Explanation: One stone remains unchanged.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 18: Partition Equal Subset Sum
   * 
   * Question: Given nums, return true if the array can be split into two subsets with equal sum.
   * 
   * Constraints: 1 <= nums.length <= 200; 1 <= nums[i] <= 100.
   * 
   * Optimized time/space complexity: Time O(n * target); Space O(target). Descending 0/1 subset DP avoids reusing a number.
   * Recursive time/space complexity: Time O(n * target); Space O(n * target). Memoization stores index and remaining target.
   * 
   * Example 1:
   * Input: nums = [1,5,11,5]
   * Output: true
   * Explanation: One subset can sum to 11.
   * 
   * Example 2:
   * Input: nums = [1,2,3,5]
   * Output: false
   * Explanation: No subset reaches half of 11 because the total is odd.
   * 
   * Example 3:
   * Input: nums = [2,2,3,5]
   * Output: false
   * Explanation: The target is 6, but no subset sums to 6.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 19: Longest Increasing Path in a Matrix
   * 
   * Question: Given an integer matrix, return the length of the longest path where each next cell is strictly larger and movement is allowed in four directions.
   * 
   * Constraints: 1 <= m, n <= 200; 0 <= matrix[i][j] <= 2^31 - 1.
   * 
   * Optimized time/space complexity: Time O(mn log(mn)); Space O(mn). Sort cells by value and relax from smaller cells to larger neighbors.
   * Recursive time/space complexity: Time O(mn); Space O(mn). DFS memoization computes the best path from each cell once.
   * 
   * Example 1:
   * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
   * Output: 4
   * Explanation: One longest path is 1 -> 2 -> 6 -> 9.
   * 
   * Example 2:
   * Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
   * Output: 4
   * Explanation: One longest path is 3 -> 4 -> 5 -> 6.
   * 
   * Example 3:
   * Input: matrix = [[1]]
   * Output: 1
   * Explanation: The single cell is the path.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 20: Maximal Square
   * 
   * Question: Given a binary matrix of characters, return the area of the largest square containing only 1s.
   * 
   * Constraints: 1 <= rows, cols <= 300; matrix[i][j] is 0 or 1 as a character.
   * 
   * Optimized time/space complexity: Time O(rows * cols); Space O(cols). Rolling DP stores square side lengths for the current row.
   * Recursive time/space complexity: Time O(rows * cols); Space O(rows * cols). Memoized recursion computes each starting cell once.
   * 
   * Example 1:
   * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
   * Output: 4
   * Explanation: The largest square has side length 2.
   * 
   * Example 2:
   * Input: matrix = [["0","1"],["1","0"]]
   * Output: 1
   * Explanation: Each single 1 forms a square of area 1.
   * 
   * Example 3:
   * Input: matrix = [["0"]]
   * Output: 0
   * Explanation: There is no square of 1s.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-2d.html
   * Adaptation: The maximum is local to each call so a module instance can be reused.
   */
  // Optimized solution
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

  // Recursive solution
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
}

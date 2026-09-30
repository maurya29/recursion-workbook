package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 17: 1D Dynamic Programming. Moderate questions 13-20. */
public class DynamicProgramming1DModerate {

  /*
   * Question 13: Perfect Squares
   * 
   * Question: Given n, return the minimum number of perfect square numbers whose sum is n.
   * 
   * Constraints: 1 <= n <= 10000.
   * 
   * Optimized time/space complexity: Time O(n * sqrt(n)); Space O(n). Bottom-up DP computes best square count for each total.
   * Recursive time/space complexity: Time O(n * sqrt(n)); Space O(n). Memoized recursion solves each remainder once.
   * 
   * Example 1:
   * Input: n = 12
   * Output: 3
   * Explanation: 12 = 4 + 4 + 4.
   * 
   * Example 2:
   * Input: n = 13
   * Output: 2
   * Explanation: 13 = 4 + 9.
   * 
   * Example 3:
   * Input: n = 1
   * Output: 1
   * Explanation: 1 is a perfect square.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 14: Integer Break
   * 
   * Question: Given integer n, break it into at least two positive integers and maximize the product of those integers.
   * 
   * Constraints: 2 <= n <= 58.
   * 
   * Optimized time/space complexity: Time O(n^2); Space O(n). Bottom-up DP tests all first split positions.
   * Recursive time/space complexity: Time O(n^2); Space O(n). Memoized recursion solves each integer size once.
   * 
   * Example 1:
   * Input: n = 2
   * Output: 1
   * Explanation: The only split is 1 + 1.
   * 
   * Example 2:
   * Input: n = 10
   * Output: 36
   * Explanation: 10 = 3 + 3 + 4 and product is 36.
   * 
   * Example 3:
   * Input: n = 4
   * Output: 4
   * Explanation: 2 + 2 gives product 4.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 15: Maximum Product Subarray
   * 
   * Question: Given an integer array, return the maximum product of a non-empty contiguous subarray.
   * 
   * Constraints: 1 <= nums.length <= 20000; -10 <= nums[i] <= 10; product fits in 32-bit integer.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Maintain max and min product ending at the current index.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan carries max/min ending states through the array.
   * 
   * Example 1:
   * Input: nums = [2,3,-2,4]
   * Output: 6
   * Explanation: The subarray [2,3] has product 6.
   * 
   * Example 2:
   * Input: nums = [-2,0,-1]
   * Output: 0
   * Explanation: The zero is larger than negative products.
   * 
   * Example 3:
   * Input: nums = [-2,3,-4]
   * Output: 24
   * Explanation: The entire array product is 24.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 16: Maximum Subarray
   * 
   * Question: Given an integer array, return the largest sum of any non-empty contiguous subarray.
   * 
   * Constraints: 1 <= nums.length <= 100000; -10000 <= nums[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Kadane keeps the best ending sum and global best.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan carries current and best sums.
   * 
   * Example 1:
   * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
   * Output: 6
   * Explanation: The subarray [4,-1,2,1] has sum 6.
   * 
   * Example 2:
   * Input: nums = [1]
   * Output: 1
   * Explanation: The only subarray is the array itself.
   * 
   * Example 3:
   * Input: nums = [5,4,-1,7,8]
   * Output: 23
   * Explanation: The full array has maximum sum.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
  public int q16MaxSubArrayOptimized(int[] nums) {
    int current = nums[0];
    int best = nums[0];

    for (int i = 1; i < nums.length; i++) {
      current = Math.max(nums[i], current + nums[i]);
      best = Math.max(best, current);
    }
    return best;
  }

  // Recursive solution
  public int q16MaxSubArrayRecursive(int[] nums) {
    return scanQ16Recursive(nums, 1, nums[0], nums[0]);
  }

  private int scanQ16Recursive(int[] nums, int index, int current, int best) {
    if (index == nums.length) return best;
    int next = Math.max(nums[index], current + nums[index]);
    return scanQ16Recursive(nums, index + 1, next, Math.max(best, next));
  }

  /*
   * Question 17: Best Time to Buy and Sell Stock
   * 
   * Question: Given prices where prices[i] is the stock price on day i, return the maximum profit from one buy followed by one sell.
   * 
   * Constraints: 1 <= prices.length <= 100000; 0 <= prices[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Track minimum price and best profit.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan carries minimum price and best profit.
   * 
   * Example 1:
   * Input: prices = [7,1,5,3,6,4]
   * Output: 5
   * Explanation: Buy at 1 and sell at 6.
   * 
   * Example 2:
   * Input: prices = [7,6,4,3,1]
   * Output: 0
   * Explanation: No profitable transaction exists.
   * 
   * Example 3:
   * Input: prices = [2,4,1]
   * Output: 2
   * Explanation: Buy at 2 and sell at 4.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
  public int q17MaxProfitOptimized(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int best = 0;

    for (int price : prices) {
      minPrice = Math.min(minPrice, price);
      best = Math.max(best, price - minPrice);
    }
    return best;
  }

  // Recursive solution
  public int q17MaxProfitRecursive(int[] prices) {
    return scanQ17Recursive(prices, 0, Integer.MAX_VALUE, 0);
  }

  private int scanQ17Recursive(int[] prices, int index, int minPrice, int best) {
    if (index == prices.length) return best;
    int nextMin = Math.min(minPrice, prices[index]);
    int nextBest = Math.max(best, prices[index] - nextMin);
    return scanQ17Recursive(prices, index + 1, nextMin, nextBest);
  }

  /*
   * Question 18: Best Time to Buy and Sell Stock with Cooldown
   * 
   * Question: Given daily stock prices, return the maximum profit with unlimited transactions and a one-day cooldown after selling.
   * 
   * Constraints: 1 <= prices.length <= 5000; 0 <= prices[i] <= 1000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Three state variables hold, sold, and rest are enough.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion stores day and holding/cooldown state.
   * 
   * Example 1:
   * Input: prices = [1,2,3,0,2]
   * Output: 3
   * Explanation: Buy day 0, sell day 1, cooldown, buy day 3, sell day 4.
   * 
   * Example 2:
   * Input: prices = [1]
   * Output: 0
   * Explanation: No sale is possible.
   * 
   * Example 3:
   * Input: prices = [2,1,4]
   * Output: 3
   * Explanation: Buy at 1 and sell at 4.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 19: Best Time to Buy and Sell Stock with Transaction Fee
   * 
   * Question: Given daily stock prices and a transaction fee, return maximum profit with unlimited transactions where each sale pays the fee.
   * 
   * Constraints: 1 <= prices.length <= 50000; 1 <= prices[i] < 50000; 0 <= fee < 50000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Two state variables cash and hold are enough.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion stores day and holding state.
   * 
   * Example 1:
   * Input: prices = [1,3,2,8,4,9], fee = 2
   * Output: 8
   * Explanation: Profit is 5 from 1->8 and 3 from 4->9.
   * 
   * Example 2:
   * Input: prices = [1,3,7,5,10,3], fee = 3
   * Output: 6
   * Explanation: One optimal sequence gains 6 after fees.
   * 
   * Example 3:
   * Input: prices = [9,8,7], fee = 1
   * Output: 0
   * Explanation: No profitable trade exists.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 20: Delete and Earn
   * 
   * Question: Given nums, you may take a value x to earn x points per occurrence, but then all x - 1 and x + 1 values are deleted. Return maximum points.
   * 
   * Constraints: 1 <= nums.length <= 20000; 1 <= nums[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n + U); Space O(U), where U is max value. House Robber DP runs over value buckets.
   * Recursive time/space complexity: Time O(n + U); Space O(U). Memoized recursion solves each value once.
   * 
   * Example 1:
   * Input: nums = [3,4,2]
   * Output: 6
   * Explanation: Take 4 and 2 for total 6.
   * 
   * Example 2:
   * Input: nums = [2,2,3,3,3,4]
   * Output: 9
   * Explanation: Take all 3s for 9.
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: 1
   * Explanation: Only one number can be taken.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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
}

package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 17: 1D Dynamic Programming. Basic questions 1-12. */
public class DynamicProgramming1DBasic {

  /*
   * Question 1: Climbing Stairs
   * 
   * Question: Given n stairs, you can climb either 1 or 2 steps at a time. Return the number of distinct ways to reach the top.
   * 
   * Constraints: 1 <= n <= 45.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative DP keeps only the previous two counts.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion computes each step count once.
   * 
   * Example 1:
   * Input: n = 2
   * Output: 2
   * Explanation: The ways are 1+1 and 2.
   * 
   * Example 2:
   * Input: n = 3
   * Output: 3
   * Explanation: The ways are 1+1+1, 1+2, and 2+1.
   * 
   * Example 3:
   * Input: n = 1
   * Output: 1
   * Explanation: Only one single-step climb is possible.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 2: Min Cost Climbing Stairs
   * 
   * Question: Given cost[i] for stepping on stair i, return the minimum cost to reach the top when you may climb 1 or 2 steps each move.
   * 
   * Constraints: 2 <= cost.length <= 1000; 0 <= cost[i] <= 999.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative DP keeps the best cost for the previous two positions.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion solves each position once.
   * 
   * Example 1:
   * Input: cost = [10,15,20]
   * Output: 15
   * Explanation: Start at index 1 and move directly to the top.
   * 
   * Example 2:
   * Input: cost = [1,100,1,1,1,100,1,1,100,1]
   * Output: 6
   * Explanation: The optimal path uses the cheap stairs.
   * 
   * Example 3:
   * Input: cost = [0,0]
   * Output: 0
   * Explanation: Start at either stair and reach the top for free.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 3: House Robber
   * 
   * Question: Given money in houses along a street, return the maximum amount you can rob without robbing adjacent houses.
   * 
   * Constraints: 1 <= nums.length <= 100; 0 <= nums[i] <= 400.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative DP keeps take/skip best values.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion computes each starting index once.
   * 
   * Example 1:
   * Input: nums = [1,2,3,1]
   * Output: 4
   * Explanation: Rob houses with values 1 and 3.
   * 
   * Example 2:
   * Input: nums = [2,7,9,3,1]
   * Output: 12
   * Explanation: Rob 2, 9, and 1.
   * 
   * Example 3:
   * Input: nums = [5]
   * Output: 5
   * Explanation: Only one house is available.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 4: House Robber II
   * 
   * Question: Given money in houses arranged in a circle, return the maximum amount you can rob without robbing adjacent houses.
   * 
   * Constraints: 1 <= nums.length <= 100; 0 <= nums[i] <= 1000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Run linear robber twice over two ranges.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion solves each range once.
   * 
   * Example 1:
   * Input: nums = [2,3,2]
   * Output: 3
   * Explanation: Rob only the middle house.
   * 
   * Example 2:
   * Input: nums = [1,2,3,1]
   * Output: 4
   * Explanation: Rob houses with values 1 and 3 without touching both ends.
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: 1
   * Explanation: Only one house exists.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 5: Decode Ways
   * 
   * Question: Given a digit string where A-Z maps to 1-26, return the number of valid decodings.
   * 
   * Constraints: 1 <= s.length <= 100; s contains only digits and may contain leading zero.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative DP keeps ways for the next one and next two positions.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion computes each index once.
   * 
   * Example 1:
   * Input: s = "12"
   * Output: 2
   * Explanation: 12 can be AB or L.
   * 
   * Example 2:
   * Input: s = "226"
   * Output: 3
   * Explanation: It can be BZ, VF, or BBF.
   * 
   * Example 3:
   * Input: s = "06"
   * Output: 0
   * Explanation: A code cannot start with zero.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 6: Coin Change
   * 
   * Question: Given coin denominations and an amount, return the fewest number of coins needed to make that amount, or -1 if impossible.
   * 
   * Constraints: 1 <= coins.length <= 12; 1 <= coins[i] <= 2^31 - 1; 0 <= amount <= 10000.
   * 
   * Optimized time/space complexity: Time O(amount * coins.length); Space O(amount). Bottom-up DP computes minimum coins for every amount.
   * Recursive time/space complexity: Time O(amount * coins.length); Space O(amount). Memoized recursion solves each remaining amount once.
   * 
   * Example 1:
   * Input: coins = [1,2,5], amount = 11
   * Output: 3
   * Explanation: 11 = 5 + 5 + 1.
   * 
   * Example 2:
   * Input: coins = [2], amount = 3
   * Output: -1
   * Explanation: No combination of 2-value coins makes 3.
   * 
   * Example 3:
   * Input: coins = [1], amount = 0
   * Output: 0
   * Explanation: No coins are needed for amount zero.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 7: Coin Change II
   * 
   * Question: Given coin denominations and an amount, return the number of combinations that make up the amount. Each coin may be used unlimited times.
   * 
   * Constraints: 1 <= coins.length <= 300; 0 <= amount <= 5000; answer fits in signed 32-bit integer.
   * 
   * Optimized time/space complexity: Time O(amount * coins.length); Space O(amount). 1D DP counts combinations by coin order.
   * Recursive time/space complexity: Time O(amount * coins.length); Space O(amount * coins.length). Memoized recursion uses coin index and remaining amount.
   * 
   * Example 1:
   * Input: amount = 5, coins = [1,2,5]
   * Output: 4
   * Explanation: The combinations are 5, 2+2+1, 2+1+1+1, and five ones.
   * 
   * Example 2:
   * Input: amount = 3, coins = [2]
   * Output: 0
   * Explanation: Coin 2 cannot make amount 3.
   * 
   * Example 3:
   * Input: amount = 0, coins = [7]
   * Output: 1
   * Explanation: The empty combination makes amount zero.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 8: Combination Sum IV
   * 
   * Question: Given distinct positive nums and target, return the number of ordered combinations that sum to target.
   * 
   * Constraints: 1 <= nums.length <= 200; 1 <= nums[i] <= 1000; 1 <= target <= 1000; answer fits in 32-bit integer.
   * 
   * Optimized time/space complexity: Time O(target * nums.length); Space O(target). Bottom-up DP counts ordered totals.
   * Recursive time/space complexity: Time O(target * nums.length); Space O(target). Memoized recursion solves each remaining target once.
   * 
   * Example 1:
   * Input: nums = [1,2,3], target = 4
   * Output: 7
   * Explanation: Different orders are counted separately.
   * 
   * Example 2:
   * Input: nums = [9], target = 3
   * Output: 0
   * Explanation: 9 cannot contribute to target 3.
   * 
   * Example 3:
   * Input: nums = [1], target = 2
   * Output: 1
   * Explanation: The only sequence is 1,1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 9: Longest Increasing Subsequence
   * 
   * Question: Given an integer array, return the length of the longest strictly increasing subsequence.
   * 
   * Constraints: 1 <= nums.length <= 2500; -10000 <= nums[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n log n); Space O(n). Patience sorting tails use binary search.
   * Recursive time/space complexity: Time O(n^2); Space O(n^2). Memoized recursion tracks index and previous index.
   * 
   * Example 1:
   * Input: nums = [10,9,2,5,3,7,101,18]
   * Output: 4
   * Explanation: One LIS is 2,3,7,101.
   * 
   * Example 2:
   * Input: nums = [0,1,0,3,2,3]
   * Output: 4
   * Explanation: One LIS is 0,1,2,3.
   * 
   * Example 3:
   * Input: nums = [7,7,7,7]
   * Output: 1
   * Explanation: Strict increase does not allow equal values.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 10: Longest Arithmetic Subsequence
   * 
   * Question: Given an array, return the length of the longest arithmetic subsequence.
   * 
   * Constraints: 2 <= nums.length <= 1000; 0 <= nums[i] <= 500.
   * 
   * Optimized time/space complexity: Time O(n^2); Space O(n^2). Hash maps store best length by difference for each ending index.
   * Recursive time/space complexity: Time O(n^2 * D) after memoized pair states; Space O(n^2). Recursion extends a chosen difference.
   * 
   * Example 1:
   * Input: nums = [3,6,9,12]
   * Output: 4
   * Explanation: The whole array has difference 3.
   * 
   * Example 2:
   * Input: nums = [9,4,7,2,10]
   * Output: 3
   * Explanation: One answer is 4,7,10.
   * 
   * Example 3:
   * Input: nums = [20,1,15,3,10,5,8]
   * Output: 4
   * Explanation: One answer is 20,15,10,5.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 11: Partition Equal Subset Sum
   * 
   * Question: Given an integer array, return true if it can be partitioned into two subsets with equal sum.
   * 
   * Constraints: 1 <= nums.length <= 200; 1 <= nums[i] <= 100.
   * 
   * Optimized time/space complexity: Time O(n * target); Space O(target). 1D boolean DP tracks reachable sums.
   * Recursive time/space complexity: Time O(n * target); Space O(n * target). Memoized recursion stores index and remaining target.
   * 
   * Example 1:
   * Input: nums = [1,5,11,5]
   * Output: true
   * Explanation: Subset [11] equals subset [1,5,5].
   * 
   * Example 2:
   * Input: nums = [1,2,3,5]
   * Output: false
   * Explanation: No subset sums to half the total.
   * 
   * Example 3:
   * Input: nums = [2,2,3,5]
   * Output: false
   * Explanation: Total is 12, but no subset makes 6.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 12: Word Break
   * 
   * Question: Given a string and a dictionary, return true if the string can be segmented into a sequence of one or more dictionary words.
   * 
   * Constraints: 1 <= s.length <= 300; 1 <= wordDict.length <= 1000; words contain lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(n^3) with substring creation; Space O(n + dictionary). Bottom-up DP checks all cuts.
   * Recursive time/space complexity: Time O(n^3); Space O(n + dictionary). Memoized recursion stores failed start positions.
   * 
   * Example 1:
   * Input: s = "leetcode", wordDict = ["leet","code"]
   * Output: true
   * Explanation: leetcode splits into leet + code.
   * 
   * Example 2:
   * Input: s = "applepenapple", wordDict = ["apple","pen"]
   * Output: true
   * Explanation: Words may be reused.
   * 
   * Example 3:
   * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
   * Output: false
   * Explanation: No split covers the entire string.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/dp-1d.html
   */
  // Optimized solution
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

  // Recursive solution
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
}

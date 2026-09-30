package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 19: Greedy. Moderate questions 13-20. */
public class GreedyModerate {

  /*
   * Question 13: Reorganize String
   * 
   * Question: Given a string s, rearrange it so no two adjacent characters are equal, or return an empty string if impossible.
   * 
   * Constraints: 1 <= s.length <= 500; s contains lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(n log 26); Space O(26). A max-heap chooses the highest remaining valid character.
   * Recursive time/space complexity: Time O(n log 26); Space O(n + 26). Recursive heap construction places one character per call.
   * 
   * Example 1:
   * Input: s = "aab"
   * Output: "aba"
   * Explanation: The two a characters can be separated.
   * 
   * Example 2:
   * Input: s = "aaab"
   * Output: ""
   * Explanation: Three a characters cannot be separated by one b.
   * 
   * Example 3:
   * Input: s = "vvvlo"
   * Output: "vlvov"
   * Explanation: A valid arrangement separates repeated v characters.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 14: Hand of Straights
   * 
   * Question: Given a hand of cards and groupSize, return true if the cards can be rearranged into groups of groupSize consecutive cards.
   * 
   * Constraints: 1 <= hand.length <= 10000; 0 <= hand[i] <= 10^9; 1 <= groupSize <= hand.length.
   * 
   * Optimized time/space complexity: Time O(n log u); Space O(u), where u is distinct card count. Each card is removed through TreeMap operations.
   * Recursive time/space complexity: Time O(n log u); Space O(u + n/groupSize), including the recursion stack.
   * 
   * Example 1:
   * Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
   * Output: true
   * Explanation: Groups can be [1,2,3], [2,3,4], and [6,7,8].
   * 
   * Example 2:
   * Input: hand = [1,2,3,4,5], groupSize = 4
   * Output: false
   * Explanation: The hand size is not divisible by 4.
   * 
   * Example 3:
   * Input: hand = [1,2,3], groupSize = 1
   * Output: true
   * Explanation: Every card forms its own group.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 15: Boats to Save People
   * 
   * Question: Given people weights and a boat limit where each boat carries at most two people, return the minimum number of boats needed.
   * 
   * Constraints: 1 <= people.length <= 50000; 1 <= people[i] <= limit <= 30000.
   * 
   * Optimized time/space complexity: Time O(n log n); Space O(log n) for sorting. Two pointers pair lightest with heaviest when possible.
   * Recursive time/space complexity: Time O(n log n + n); Space O(log n + n). Recursive two-pointer pairing after sorting.
   * 
   * Example 1:
   * Input: people = [1,2], limit = 3
   * Output: 1
   * Explanation: Both people fit in one boat.
   * 
   * Example 2:
   * Input: people = [3,2,2,1], limit = 3
   * Output: 3
   * Explanation: The person with weight 3 rides alone.
   * 
   * Example 3:
   * Input: people = [3,5,3,4], limit = 5
   * Output: 4
   * Explanation: No pair can include weight 5 or 4 with another person.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
  public int q15NumRescueBoatsRecursive(int[] people, int limit) {
    Arrays.sort(people);
    return boatsQ15Recursive(people, limit, 0, people.length - 1);
  }

  private int boatsQ15Recursive(int[] people, int limit, int left, int right) {
    if (left > right) return 0;
    if (people[left] + people[right] <= limit) return 1 + boatsQ15Recursive(people, limit, left + 1, right - 1);
    return 1 + boatsQ15Recursive(people, limit, left, right - 1);
  }

  /*
   * Question 16: Two City Scheduling
   * 
   * Question: Given costs for flying each person to city A or B, send exactly n people to each city with minimum total cost.
   * 
   * Constraints: 2 <= costs.length <= 100; costs.length is even; 1 <= costA, costB <= 1000.
   * 
   * Optimized time/space complexity: Time O(n log n); Space O(log n) for sorting. Difference sorting gives the exchange-optimal split. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * Recursive time/space complexity: Time O(n log n + n); Space O(log n + n). Recursive accumulation after sorting by cost difference. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * 
   * Example 1:
   * Input: costs = [[10,20],[30,200],[400,50],[30,20]]
   * Output: 110
   * Explanation: Send people 0 and 1 to A, people 2 and 3 to B.
   * 
   * Example 2:
   * Input: costs = [[259,770],[448,54],[926,667],[184,139],[840,118],[577,469]]
   * Output: 1859
   * Explanation: Sorting by savings gives the minimum split.
   * 
   * Example 3:
   * Input: costs = [[515,563],[451,713],[537,709],[343,819],[855,779],[457,60],[650,359],[631,42]]
   * Output: 3086
   * Explanation: Exactly half must go to each city.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
  public int q16TwoCitySchedCostOptimized(int[][] costs) {
    Arrays.sort(costs, (a, b) -> Integer.compare(a[0] - a[1], b[0] - b[1]));
    int n = costs.length / 2;
    int total = 0;
    for (int i = 0; i < costs.length; i++) {
      total += i < n ? costs[i][0] : costs[i][1];
    }
    return total;
  }

  // Recursive solution
  public int q16TwoCitySchedCostRecursive(int[][] costs) {
    Arrays.sort(costs, (a, b) -> Integer.compare(a[0] - a[1], b[0] - b[1]));
    return sumQ16Recursive(costs, 0, costs.length / 2);
  }

  private int sumQ16Recursive(int[][] costs, int index, int half) {
    if (index == costs.length) return 0;
    int cost = index < half ? costs[index][0] : costs[index][1];
    return cost + sumQ16Recursive(costs, index + 1, half);
  }

  /*
   * Question 17: Maximum Subarray
   * 
   * Question: Given an integer array, return the largest possible sum of a non-empty contiguous subarray.
   * 
   * Constraints: 1 <= nums.length <= 100000; -10000 <= nums[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Kadane keeps only current ending sum and global best.
   * Recursive time/space complexity: Time O(n log n); Space O(log n). Divide and conquer combines left, right, and crossing sums.
   * 
   * Example 1:
   * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
   * Output: 6
   * Explanation: The best subarray is [4,-1,2,1].
   * 
   * Example 2:
   * Input: nums = [1]
   * Output: 1
   * Explanation: The single element is the answer.
   * 
   * Example 3:
   * Input: nums = [5,4,-1,7,8]
   * Output: 23
   * Explanation: The entire array has the maximum sum.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
  public int q17MaxSubArrayOptimized(int[] nums) {
    int current = nums[0];
    int best = nums[0];

    for (int i = 1; i < nums.length; i++) {
      current = Math.max(nums[i], current + nums[i]);
      best = Math.max(best, current);
    }
    return best;
  }

  // Recursive solution
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

  /*
   * Question 18: Best Time to Buy and Sell Stock II
   * 
   * Question: Given daily stock prices, return the maximum profit with as many buy-sell transactions as desired, holding at most one share at a time.
   * 
   * Constraints: 1 <= prices.length <= 30000; 0 <= prices[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Sum every positive adjacent difference.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan accumulates positive differences.
   * 
   * Example 1:
   * Input: prices = [7,1,5,3,6,4]
   * Output: 7
   * Explanation: Profit 4 from 1->5 and 3 from 3->6.
   * 
   * Example 2:
   * Input: prices = [1,2,3,4,5]
   * Output: 4
   * Explanation: Capture every adjacent increase.
   * 
   * Example 3:
   * Input: prices = [7,6,4,3,1]
   * Output: 0
   * Explanation: No profitable transaction exists.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
  public int q18MaxProfitOptimized(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
      if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
    }
    return profit;
  }

  // Recursive solution
  public int q18MaxProfitRecursive(int[] prices) {
    return collectQ18Recursive(prices, 1);
  }

  private int collectQ18Recursive(int[] prices, int index) {
    if (index == prices.length) return 0;
    int gain = Math.max(0, prices[index] - prices[index - 1]);
    return gain + collectQ18Recursive(prices, index + 1);
  }

  /*
   * Question 19: Can Place Flowers
   * 
   * Question: Given a flowerbed of 0s and 1s and an integer n, return true if n new flowers can be planted without adjacent flowers.
   * 
   * Constraints: 1 <= flowerbed.length <= 20000; flowerbed[i] is 0 or 1; 0 <= n <= flowerbed.length.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). One scan mutates valid empty plots.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan applies the same earliest-placement rule.
   * 
   * Example 1:
   * Input: flowerbed = [1,0,0,0,1], n = 1
   * Output: true
   * Explanation: Plant at index 2.
   * 
   * Example 2:
   * Input: flowerbed = [1,0,0,0,1], n = 2
   * Output: false
   * Explanation: Only one new flower can fit.
   * 
   * Example 3:
   * Input: flowerbed = [0], n = 1
   * Output: true
   * Explanation: The only plot has no neighbors.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 20: Increasing Triplet Subsequence
   * 
   * Question: Given an integer array, return true if there exists a strictly increasing subsequence of length three.
   * 
   * Constraints: 1 <= nums.length <= 500000; -2^31 <= nums[i] <= 2^31 - 1.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Track the smallest first and second subsequence tails.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan carries the two current tail values.
   * 
   * Example 1:
   * Input: nums = [1,2,3,4,5]
   * Output: true
   * Explanation: 1, 2, 3 form a triplet.
   * 
   * Example 2:
   * Input: nums = [5,4,3,2,1]
   * Output: false
   * Explanation: No increasing subsequence of length three exists.
   * 
   * Example 3:
   * Input: nums = [2,1,5,0,4,6]
   * Output: true
   * Explanation: 0, 4, 6 form a triplet.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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
}

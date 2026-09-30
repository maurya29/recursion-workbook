package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 19: Greedy. Basic questions 1-12. */
public class GreedyBasic {

  /*
   * Question 1: Assign Cookies
   * 
   * Question: Given children greed factors and cookie sizes, return the maximum number of children that can receive one cookie with size at least their greed.
   * 
   * Constraints: 1 <= g.length, s.length <= 30000; 1 <= g[i], s[j] <= 2^31 - 1.
   * 
   * Optimized time/space complexity: Time O(g log g + s log s); Space O(log g + log s) for sorting. Two pointers make each local match final.
   * Recursive time/space complexity: Time O(g log g + s log s + g + s); Space O(log g + log s + g + s). Recursive two-pointer scan after sorting.
   * 
   * Example 1:
   * Input: g = [1,2,3], s = [1,1]
   * Output: 1
   * Explanation: Only the child with greed 1 can be satisfied.
   * 
   * Example 2:
   * Input: g = [1,2], s = [1,2,3]
   * Output: 2
   * Explanation: Cookies 1 and 2 satisfy both children.
   * 
   * Example 3:
   * Input: g = [10,9,8,7], s = [5,6,7,8]
   * Output: 2
   * Explanation: Only greed 7 and 8 can be satisfied.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 2: Lemonade Change
   * 
   * Question: Customers pay in order with 5, 10, or 20 dollar bills for 5 dollar lemonade. Return true if exact change can be given to every customer.
   * 
   * Constraints: 1 <= bills.length <= 100000; bills[i] is 5, 10, or 20.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Keep only counts of 5 and 10 dollar bills.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan applies the same safe change preference.
   * 
   * Example 1:
   * Input: bills = [5,5,5,10,20]
   * Output: true
   * Explanation: The 20 can be changed with 10 + 5.
   * 
   * Example 2:
   * Input: bills = [5,5,10,10,20]
   * Output: false
   * Explanation: The final 20 cannot be changed because no 5 remains.
   * 
   * Example 3:
   * Input: bills = [10]
   * Output: false
   * Explanation: There is no 5 dollar bill for change.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 3: Jump Game
   * 
   * Question: Given nums where nums[i] is the maximum jump length from index i, return true if the last index is reachable from index 0.
   * 
   * Constraints: 1 <= nums.length <= 10000; 0 <= nums[i] <= 100000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Maintain the farthest reachable index.
   * Recursive time/space complexity: Time O(n^2); Space O(n). Memoized recursion checks each index and possible jump once.
   * 
   * Example 1:
   * Input: nums = [2,3,1,1,4]
   * Output: true
   * Explanation: The frontier reaches the final index.
   * 
   * Example 2:
   * Input: nums = [3,2,1,0,4]
   * Output: false
   * Explanation: The frontier stops at index 3.
   * 
   * Example 3:
   * Input: nums = [0]
   * Output: true
   * Explanation: The start is already the end.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
  public boolean q03CanJumpOptimized(int[] nums) {
    int farthest = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i > farthest) return false;
      farthest = Math.max(farthest, i + nums[i]);
      if (farthest >= nums.length - 1) return true;
    }
    return true;
  }

  // Recursive solution
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

  /*
   * Question 4: Jump Game II
   * 
   * Question: Given nums where nums[i] is the maximum jump length from index i, return the minimum number of jumps needed to reach the last index.
   * 
   * Constraints: 1 <= nums.length <= 10000; 0 <= nums[i] <= 1000; the last index is reachable.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Greedy range expansion behaves like BFS levels over indexes.
   * Recursive time/space complexity: Time O(n^2); Space O(n). Memoized recursion computes the minimum jumps from each index.
   * 
   * Example 1:
   * Input: nums = [2,3,1,1,4]
   * Output: 2
   * Explanation: Jump from index 0 to 1, then to the last index.
   * 
   * Example 2:
   * Input: nums = [2,3,0,1,4]
   * Output: 2
   * Explanation: The same two-jump route works.
   * 
   * Example 3:
   * Input: nums = [0]
   * Output: 0
   * Explanation: No jump is needed.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 5: Gas Station
   * 
   * Question: Given gas and cost arrays around a circular route, return the starting station index that can complete the circuit, or -1 if impossible.
   * 
   * Constraints: gas.length == cost.length; 1 <= n <= 100000; 0 <= gas[i], cost[i] <= 10000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). One pass tracks total surplus and the current candidate start.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive scan applies the same reset rule.
   * 
   * Example 1:
   * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
   * Output: 3
   * Explanation: Starting at station 3 completes the circuit.
   * 
   * Example 2:
   * Input: gas = [2,3,4], cost = [3,4,3]
   * Output: -1
   * Explanation: Total gas is less than total cost.
   * 
   * Example 3:
   * Input: gas = [5], cost = [4]
   * Output: 0
   * Explanation: The single station has enough gas.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 6: Candy
   * 
   * Question: Given children ratings in a line, give each child at least one candy and give higher-rated children more candies than adjacent lower-rated children. Return the minimum candies needed.
   * 
   * Constraints: 1 <= ratings.length <= 20000; 0 <= ratings[i] <= 20000.
   * 
   * Optimized time/space complexity: Time O(n); Space O(n). Two directional passes compute minimum local requirements.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion computes candy need from lower-rated neighbors.
   * 
   * Example 1:
   * Input: ratings = [1,0,2]
   * Output: 5
   * Explanation: Candies [2,1,2] satisfy both sides.
   * 
   * Example 2:
   * Input: ratings = [1,2,2]
   * Output: 4
   * Explanation: Candies [1,2,1] are enough.
   * 
   * Example 3:
   * Input: ratings = [1,3,4,5,2]
   * Output: 11
   * Explanation: The peak must exceed both neighboring slopes.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 7: Queue Reconstruction by Height
   * 
   * Question: Given people as [height, k], reconstruct a queue where k is the number of people in front with height greater than or equal to height.
   * 
   * Constraints: 1 <= people.length <= 2000; 0 <= height <= 10^6; 0 <= k < people.length.
   * 
   * Optimized time/space complexity: Time O(n^2 + n log n); Space O(n). Sorting is followed by list insertions. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * Recursive time/space complexity: Time O(n^2 + n log n); Space O(n). Recursively insert sorted people into the queue. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * 
   * Example 1:
   * Input: people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
   * Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
   * Explanation: Every person has exactly k taller-or-equal people before them.
   * 
   * Example 2:
   * Input: people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
   * Output: [[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
   * Explanation: Taller-first insertion preserves constraints.
   * 
   * Example 3:
   * Input: people = [[1,0]]
   * Output: [[1,0]]
   * Explanation: A single person is already valid.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
  public int[][] q07ReconstructQueueOptimized(int[][] people) {
    Arrays.sort(people, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
    List<int[]> queue = new ArrayList<>();

    for (int[] person : people) {
      queue.add(person[1], person);
    }
    return queue.toArray(new int[people.length][]);
  }

  // Recursive solution
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

  /*
   * Question 8: Non-overlapping Intervals
   * 
   * Question: Given intervals, return the minimum number that must be removed so the remaining intervals do not overlap.
   * 
   * Constraints: 1 <= intervals.length <= 100000; -50000 <= start < end <= 50000.
   * 
   * Optimized time/space complexity: Time O(n log n); Space O(log n) for sorting. One end-time scan counts removals. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * Recursive time/space complexity: Time O(n log n + n); Space O(log n + n). Recursive scan after sorting by end time. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * 
   * Example 1:
   * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
   * Output: 1
   * Explanation: Remove [1,3].
   * 
   * Example 2:
   * Input: intervals = [[1,2],[1,2],[1,2]]
   * Output: 2
   * Explanation: Only one identical interval can remain.
   * 
   * Example 3:
   * Input: intervals = [[1,2],[2,3]]
   * Output: 0
   * Explanation: Intervals touching at endpoints do not overlap.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 9: Merge Intervals
   * 
   * Question: Given intervals, merge all overlapping intervals and return the non-overlapping result.
   * 
   * Constraints: 1 <= intervals.length <= 10000; 0 <= start <= end <= 10000.
   * 
   * Optimized time/space complexity: Time O(n log n); Space O(n). Sort by start and scan once. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * Recursive time/space complexity: Time O(n log n); Space O(n). Recursive scan appends or extends the last merged interval. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * 
   * Example 1:
   * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
   * Output: [[1,6],[8,10],[15,18]]
   * Explanation: [1,3] and [2,6] overlap.
   * 
   * Example 2:
   * Input: intervals = [[1,4],[4,5]]
   * Output: [[1,5]]
   * Explanation: Touching endpoints merge.
   * 
   * Example 3:
   * Input: intervals = [[1,4],[0,2],[3,5]]
   * Output: [[0,5]]
   * Explanation: All intervals connect after sorting.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 10: Minimum Number of Arrows to Burst Balloons
   * 
   * Question: Given balloon intervals on the x-axis, return the minimum arrows needed; one arrow shot at x bursts every balloon containing x.
   * 
   * Constraints: 1 <= points.length <= 100000; -2^31 <= start < end <= 2^31 - 1.
   * 
   * Optimized time/space complexity: Time O(n log n); Space O(log n) for sorting. One scan by right endpoint counts arrows. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * Recursive time/space complexity: Time O(n log n + n); Space O(log n + n). Recursive scan after sorting by right endpoint. Java sorting of int[][] uses O(n) worst-case temporary reference storage.
   * 
   * Example 1:
   * Input: points = [[10,16],[2,8],[1,6],[7,12]]
   * Output: 2
   * Explanation: Arrows at 6 and 12 can burst all balloons.
   * 
   * Example 2:
   * Input: points = [[1,2],[3,4],[5,6],[7,8]]
   * Output: 4
   * Explanation: No intervals overlap.
   * 
   * Example 3:
   * Input: points = [[1,2],[2,3]]
   * Output: 1
   * Explanation: An arrow at 2 bursts both.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 11: Partition Labels
   * 
   * Question: Given a string, split it into as many parts as possible so each letter appears in at most one part. Return the sizes of the parts.
   * 
   * Constraints: 1 <= s.length <= 500; s contains lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Last occurrence array has fixed alphabet size.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursive segment builder uses last occurrences.
   * 
   * Example 1:
   * Input: s = "ababcbacadefegdehijhklij"
   * Output: [9,7,8]
   * Explanation: Each letter appears in one partition only.
   * 
   * Example 2:
   * Input: s = "eccbbbbdec"
   * Output: [10]
   * Explanation: The characters force one full-string partition.
   * 
   * Example 3:
   * Input: s = "abc"
   * Output: [1,1,1]
   * Explanation: Each character can form its own partition.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 12: Task Scheduler
   * 
   * Question: Given CPU tasks represented by letters and a cooldown n, return the least time units needed to execute all tasks with identical tasks separated by at least n intervals.
   * 
   * Constraints: 1 <= tasks.length <= 10000; tasks[i] is uppercase English letter; 0 <= n <= 100.
   * 
   * Optimized time/space complexity: Time O(n + 26); Space O(26). Frequency formula computes the minimum frame length.
   * Recursive time/space complexity: Time O(answer * 26); Space O(answer + 26). Recursive simulation chooses the most frequent available task each time.
   * 
   * Example 1:
   * Input: tasks = ["A","A","A","B","B","B"], n = 2
   * Output: 8
   * Explanation: One schedule is A B idle A B idle A B.
   * 
   * Example 2:
   * Input: tasks = ["A","A","A","B","B","B"], n = 0
   * Output: 6
   * Explanation: No cooldown means no idles.
   * 
   * Example 3:
   * Input: tasks = ["A","A","A","A","B","B","C","C"], n = 2
   * Output: 10
   * Explanation: The four A tasks create the limiting frame.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/greedy.html
   */
  // Optimized solution
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

  // Recursive solution
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
}

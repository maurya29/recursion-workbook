package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 11: Backtracking. Basic questions 1-12. */
public class BacktrackingBasic {

  /*
   * Question 1: Subsets
   * 
   * Question: Given an integer array nums with unique elements, return all possible subsets. The solution set must not contain duplicate subsets.
   * 
   * Constraints: 1 <= nums.length <= 10; -10 <= nums[i] <= 10; all elements are unique.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); Space O(n * 2^n). Iterative expansion doubles the result set for each number.
   * Recursive time/space complexity: Time O(n * 2^n); Space O(n) recursion stack excluding output.
   * 
   * Example 1:
   * Input: nums = [1,2,3]
   * Output: [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]
   * Explanation: Each number is either chosen or skipped.
   * 
   * Example 2:
   * Input: nums = [0]
   * Output: [[],[0]]
   * Explanation: There are two subsets for one element.
   * 
   * Example 3:
   * Input: nums = [1,2]
   * Output: [[],[1],[1,2],[2]]
   * Explanation: There are four subsets.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 2: Subsets II
   * 
   * Question: Given an integer array nums that may contain duplicates, return all possible subsets without duplicate subsets.
   * 
   * Constraints: 1 <= nums.length <= 10; -10 <= nums[i] <= 10.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); Space O(n * 2^n). Iterative expansion only extends subsets created in the previous round for duplicates.
   * Recursive time/space complexity: Time O(n * 2^n); Space O(n) stack excluding output. Sorted backtracking skips same-depth duplicates.
   * 
   * Example 1:
   * Input: nums = [1,2,2]
   * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
   * Explanation: The duplicate subset [2] appears only once.
   * 
   * Example 2:
   * Input: nums = [0]
   * Output: [[],[0]]
   * Explanation: One element still has two subsets.
   * 
   * Example 3:
   * Input: nums = [2,2,2]
   * Output: [[],[2],[2,2],[2,2,2]]
   * Explanation: Only different counts of value 2 matter.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 3: Permutations
   * 
   * Question: Given an array nums of distinct integers, return all possible permutations.
   * 
   * Constraints: 1 <= nums.length <= 6; -10 <= nums[i] <= 10; all values are unique.
   * 
   * Optimized time/space complexity: Time O(n! * n); Space O(n! * n). Iterative insertion builds permutations level by level.
   * Recursive time/space complexity: Time O(n! * n); Space O(n) stack excluding output.
   * 
   * Example 1:
   * Input: nums = [1,2,3]
   * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
   * Explanation: All 3! orderings are returned.
   * 
   * Example 2:
   * Input: nums = [0,1]
   * Output: [[0,1],[1,0]]
   * Explanation: Two values have two orders.
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: [[1]]
   * Explanation: One value has one permutation.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 4: Permutations II
   * 
   * Question: Given a collection of numbers nums that may contain duplicates, return all unique permutations.
   * 
   * Constraints: 1 <= nums.length <= 8; -10 <= nums[i] <= 10.
   * 
   * Optimized time/space complexity: Time O(uniquePermutations * n); Space O(uniquePermutations * n). Iterative insertion skips same insertion positions for duplicates.
   * Recursive time/space complexity: Time O(uniquePermutations * n); Space O(n) stack excluding output.
   * 
   * Example 1:
   * Input: nums = [1,1,2]
   * Output: [[1,1,2],[1,2,1],[2,1,1]]
   * Explanation: Duplicate 1 values do not create duplicate permutations.
   * 
   * Example 2:
   * Input: nums = [1,2,3]
   * Output: six permutations
   * Explanation: With all unique values, all 3! permutations appear.
   * 
   * Example 3:
   * Input: nums = [2,2]
   * Output: [[2,2]]
   * Explanation: Only one unique ordering exists.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 5: Combinations
   * 
   * Question: Given n and k, return all possible combinations of k numbers chosen from 1 to n.
   * 
   * Constraints: 1 <= n <= 20; 1 <= k <= n.
   * 
   * Optimized time/space complexity: Time O(n*S + R*k); auxiliary space O(F*k), plus O(R*k) output. S is explored partial combinations and F is maximum pending stack states; each state copies up to k values.
   * Recursive time/space complexity: Time O(C(n,k) * k); Space O(k) recursion stack excluding output.
   * 
   * Example 1:
   * Input: n = 4, k = 2
   * Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
   * Explanation: Every size-2 choice is listed.
   * 
   * Example 2:
   * Input: n = 1, k = 1
   * Output: [[1]]
   * Explanation: Only one number exists.
   * 
   * Example 3:
   * Input: n = 3, k = 3
   * Output: [[1,2,3]]
   * Explanation: Choosing all numbers gives one combination.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 6: Combination Sum
   * 
   * Question: Given distinct candidates and a target, return all unique combinations where chosen numbers sum to target. The same number may be chosen unlimited times.
   * 
   * Constraints: 1 <= candidates.length <= 30; 2 <= candidates[i] <= 40; candidates are distinct; 1 <= target <= 40.
   * 
   * Optimized time/space complexity: Time O(S * (n + D)); auxiliary space O(F*D), plus output. n is candidate count, D = target/minCandidate, S is explored states and F is maximum pending states; worst-case exponential search.
   * Recursive time/space complexity: Time O(n^(D+1) + R*D) upper bound; auxiliary space O(D + log n), plus O(R*D) output. D = target/minCandidate and R is solution count; sorted pruning reduces the search.
   * 
   * Example 1:
   * Input: candidates = [2,3,6,7], target = 7
   * Output: [[2,2,3],[7]]
   * Explanation: 2 can be reused and 7 is exact.
   * 
   * Example 2:
   * Input: candidates = [2,3,5], target = 8
   * Output: [[2,2,2,2],[2,3,3],[3,5]]
   * Explanation: All combinations sum to 8.
   * 
   * Example 3:
   * Input: candidates = [2], target = 1
   * Output: []
   * Explanation: No combination can reach 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 7: Combination Sum II
   * 
   * Question: Given candidates that may contain duplicates and a target, return all unique combinations where each candidate may be used at most once.
   * 
   * Constraints: 1 <= candidates.length <= 100; 1 <= candidates[i] <= 50; 1 <= target <= 30.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); auxiliary space O(n^3) upper bound for pending copied paths, plus output. Sorted pruning skips duplicates.
   * Recursive time/space complexity: Time O(n * 2^n); auxiliary space O(n), plus output.
   * 
   * Example 1:
   * Input: candidates = [10,1,2,7,6,1,5], target = 8
   * Output: [[1,1,6],[1,2,5],[1,7],[2,6]]
   * Explanation: Each index is used at most once.
   * 
   * Example 2:
   * Input: candidates = [2,5,2,1,2], target = 5
   * Output: [[1,2,2],[5]]
   * Explanation: Duplicate 2 values are handled without duplicate combinations.
   * 
   * Example 3:
   * Input: candidates = [3], target = 2
   * Output: []
   * Explanation: The only candidate is too large.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 8: Combination Sum III
   * 
   * Question: Find all valid combinations of k numbers that sum to n using only numbers 1 through 9, where each number is used at most once.
   * 
   * Constraints: 2 <= k <= 9; 1 <= n <= 60.
   * 
   * Optimized time/space complexity: Time O(C(9,k)); Space O(k). Iterative stack prunes by remaining count and sum.
   * Recursive time/space complexity: Time O(C(9,k)); Space O(k). Backtracking prunes candidates greater than remaining sum.
   * 
   * Example 1:
   * Input: k = 3, n = 7
   * Output: [[1,2,4]]
   * Explanation: Only 1 + 2 + 4 equals 7.
   * 
   * Example 2:
   * Input: k = 3, n = 9
   * Output: [[1,2,6],[1,3,5],[2,3,4]]
   * Explanation: Three valid size-3 combinations exist.
   * 
   * Example 3:
   * Input: k = 4, n = 1
   * Output: []
   * Explanation: The target is too small for four positive numbers.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 9: Generate Parentheses
   * 
   * Question: Given n pairs of parentheses, generate all combinations of well-formed parentheses.
   * 
   * Constraints: 1 <= n <= 8.
   * 
   * Optimized time/space complexity: Time O(Cn * n); Space O(Cn * n). Iterative stack explores only valid prefixes.
   * Recursive time/space complexity: Time O(Cn * n); Space O(n) stack excluding output.
   * 
   * Example 1:
   * Input: n = 3
   * Output: ["((()))","(()())","(())()","()(())","()()()"]
   * Explanation: All valid strings with three pairs are generated.
   * 
   * Example 2:
   * Input: n = 1
   * Output: ["()"]
   * Explanation: Only one valid string exists.
   * 
   * Example 3:
   * Input: n = 2
   * Output: ["(())","()()"]
   * Explanation: Two valid strings exist.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 10: Letter Combinations of a Phone Number
   * 
   * Question: Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.
   * 
   * Constraints: 0 <= digits.length <= 4; digits[i] is between 2 and 9.
   * 
   * Optimized time/space complexity: Time O(4^n * n); Space O(4^n * n). Queue expansion processes one digit level at a time.
   * Recursive time/space complexity: Time O(4^n * n); Space O(n) stack excluding output.
   * 
   * Example 1:
   * Input: digits = "23"
   * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
   * Explanation: Every 2-letter choice combines with every 3-letter choice.
   * 
   * Example 2:
   * Input: digits = ""
   * Output: []
   * Explanation: No digits means no combinations.
   * 
   * Example 3:
   * Input: digits = "2"
   * Output: ["a","b","c"]
   * Explanation: Digit 2 maps to three letters.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 11: Palindrome Partitioning
   * 
   * Question: Given a string s, partition it so that every substring in the partition is a palindrome. Return all possible palindrome partitionings.
   * 
   * Constraints: 1 <= s.length <= 16; s contains lowercase English letters.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); auxiliary space O(n^3) conservative bound for the palindrome table and pending copied paths, plus output.
   * Recursive time/space complexity: Time O(n * 2^n); Space O(n^2 + n). Backtracking uses a palindrome DP table.
   * 
   * Example 1:
   * Input: s = "aab"
   * Output: [["a","a","b"],["aa","b"]]
   * Explanation: Both partitions contain only palindromes.
   * 
   * Example 2:
   * Input: s = "a"
   * Output: [["a"]]
   * Explanation: A single character is a palindrome.
   * 
   * Example 3:
   * Input: s = "efe"
   * Output: [["e","f","e"],["efe"]]
   * Explanation: The whole string is also a palindrome.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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

  /*
   * Question 12: Restore IP Addresses
   * 
   * Question: Given a string containing only digits, return all possible valid IP addresses that can be formed by inserting three dots.
   * 
   * Constraints: 1 <= s.length <= 20; s contains only digits.
   * 
   * Optimized time/space complexity: Time O(1); Space O(1). Iterative loops choose three cut positions with pruning.
   * Recursive time/space complexity: Time O(1); Space O(1). Backtracking depth is fixed at four segments.
   * 
   * Example 1:
   * Input: s = "25525511135"
   * Output: ["255.255.11.135","255.255.111.35"]
   * Explanation: Both addresses have four valid segments.
   * 
   * Example 2:
   * Input: s = "0000"
   * Output: ["0.0.0.0"]
   * Explanation: Each segment is a single zero.
   * 
   * Example 3:
   * Input: s = "101023"
   * Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
   * Explanation: Multiple segment lengths are valid.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/backtracking.html
   */
  // Optimized solution
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

  // Recursive solution
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
}

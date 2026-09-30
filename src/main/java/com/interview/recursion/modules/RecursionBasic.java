package com.interview.recursion.modules;

import java.util.*;
import com.interview.recursion.model.*;

/** Topic 10: Recursion. Basic questions 1-12. */
public class RecursionBasic {

  /*
   * Question 1: Fibonacci Number
   * 
   * Question: Given n, return the nth Fibonacci number where F(0) = 0, F(1) = 1, and F(n) = F(n - 1) + F(n - 2).
   * 
   * Constraints: 0 <= n <= 30 in the original problem; use int for the original range.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative DP keeps only the previous two values.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion computes each n once and uses call stack plus memo.
   * 
   * Example 1:
   * Input: n = 2
   * Output: 1
   * Explanation: F(2) = F(1) + F(0) = 1.
   * 
   * Example 2:
   * Input: n = 3
   * Output: 2
   * Explanation: F(3) = F(2) + F(1) = 2.
   * 
   * Example 3:
   * Input: n = 4
   * Output: 3
   * Explanation: F(4) = 3.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public int q01FibRecursive(int n) {
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    return dfsQ1Recursive(n, memo);
  }

  private int dfsQ1Recursive(int n, int[] memo) {
    if (n <= 1) return n;
    if (memo[n] != -1) return memo[n];
    memo[n] = dfsQ1Recursive(n - 1, memo) + dfsQ1Recursive(n - 2, memo);
    return memo[n];
  }

  /*
   * Question 2: Climbing Stairs
   * 
   * Question: You are climbing a staircase with n steps. Each move can climb 1 or 2 steps. Return the number of distinct ways to reach the top.
   * 
   * Constraints: 1 <= n <= 45.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative DP keeps the last two counts.
   * Recursive time/space complexity: Time O(n); Space O(n). Memoized recursion stores each step count once.
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
   * Explanation: Only one move is possible.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public int q02ClimbStairsRecursive(int n) {
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    return waysQ2Recursive(0, n, memo);
  }

  private int waysQ2Recursive(int step, int n, int[] memo) {
    if (step == n) return 1;
    if (step > n) return 0;
    if (memo[step] != -1) return memo[step];
    memo[step] = waysQ2Recursive(step + 1, n, memo) + waysQ2Recursive(step + 2, n, memo);
    return memo[step];
  }

  /*
   * Question 3: Pow(x, n)
   * 
   * Question: Implement pow(x, n), which calculates x raised to the power n.
   * 
   * Constraints: -100.0 < x < 100.0; -2147483648 <= n <= 2147483647; result fits standard double constraints.
   * 
   * Optimized time/space complexity: Time O(log |n|); Space O(1). Iterative binary exponentiation halves the exponent.
   * Recursive time/space complexity: Time O(log |n|); Space O(log |n|). Recursive fast power halves the exponent at each call.
   * 
   * Example 1:
   * Input: x = 2.00000, n = 10
   * Output: 1024.00000
   * Explanation: 2 raised to 10 is 1024.
   * 
   * Example 2:
   * Input: x = 2.10000, n = 3
   * Output: 9.26100
   * Explanation: 2.1 * 2.1 * 2.1 = 9.261.
   * 
   * Example 3:
   * Input: x = 2.00000, n = -2
   * Output: 0.25000
   * Explanation: Negative exponent returns reciprocal power.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public double q03MyPowRecursive(double x, int n) {
    long power = n;
    if (power < 0) return 1.0 / fastPowQ3Recursive(x, -power);
    return fastPowQ3Recursive(x, power);
  }

  private double fastPowQ3Recursive(double x, long power) {
    if (power == 0) return 1.0;
    double half = fastPowQ3Recursive(x, power / 2);
    double squared = half * half;
    return power % 2 == 0 ? squared : squared * x;
  }

  /*
   * Question 4: Reverse String
   * 
   * Question: Given a character array s, reverse it in-place.
   * 
   * Constraints: 1 <= s.length <= 100000; s[i] is a printable ASCII character in the original problem.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative two pointers swap in-place.
   * Recursive time/space complexity: Time O(n); Space O(n) call stack. Recursive two pointers swap one pair per call.
   * 
   * Example 1:
   * Input: s = ["h","e","l","l","o"]
   * Output: ["o","l","l","e","h"]
   * Explanation: The array is reversed in-place.
   * 
   * Example 2:
   * Input: s = ["H","a","n","n","a","h"]
   * Output: ["h","a","n","n","a","H"]
   * Explanation: Case is preserved while positions change.
   * 
   * Example 3:
   * Input: s = ["a"]
   * Output: ["a"]
   * Explanation: A single character is already reversed.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
  public void q04ReverseStringOptimized(char[] s) {
    int left = 0;
    int right = s.length - 1;

    while (left < right) {
      char temp = s[left];
      s[left++] = s[right];
      s[right--] = temp;
    }
  }

  // Recursive solution
  public void q04ReverseStringRecursive(char[] s) {
    reverseQ4Recursive(s, 0, s.length - 1);
  }

  private void reverseQ4Recursive(char[] s, int left, int right) {
    if (left >= right) return;

    char temp = s[left];
    s[left] = s[right];
    s[right] = temp;
    reverseQ4Recursive(s, left + 1, right - 1);
  }

  /*
   * Question 5: Swap Nodes in Pairs
   * 
   * Question: Given a linked list, swap every two adjacent nodes and return its head. Node values must not be modified.
   * 
   * Constraints: 0 <= number of nodes <= 100; -100 <= Node.val <= 100.
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Iterative pointer rewiring uses a dummy node.
   * Recursive time/space complexity: Time O(n); Space O(n) call stack. Each call swaps one pair.
   * 
   * Example 1:
   * Input: head = [1,2,3,4]
   * Output: [2,1,4,3]
   * Explanation: Each adjacent pair is swapped.
   * 
   * Example 2:
   * Input: head = []
   * Output: []
   * Explanation: There are no nodes to swap.
   * 
   * Example 3:
   * Input: head = [1,2,3]
   * Output: [2,1,3]
   * Explanation: The final unpaired node remains in place.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public ListNode q05SwapPairsRecursive(ListNode head) {
    if (head == null || head.next == null) return head;

    ListNode second = head.next;
    head.next = q05SwapPairsRecursive(second.next);
    second.next = head;
    return second;
  }

  /*
   * Question 6: Merge Two Sorted Lists
   * 
   * Question: Given the heads of two sorted linked lists, merge them into one sorted linked list and return its head.
   * 
   * Constraints: 0 <= number of nodes in each list <= 50; -100 <= Node.val <= 100; both lists are sorted nondecreasing.
   * 
   * Optimized time/space complexity: Time O(m+n); Space O(1). Iteratively relink existing nodes.
   * Recursive time/space complexity: Time O(m+n); Space O(m+n) call stack. Each call consumes one node.
   * 
   * Example 1:
   * Input: list1 = [1,2,4], list2 = [1,3,4]
   * Output: [1,1,2,3,4,4]
   * Explanation: Nodes are merged in sorted order.
   * 
   * Example 2:
   * Input: list1 = [], list2 = []
   * Output: []
   * Explanation: Both lists are empty.
   * 
   * Example 3:
   * Input: list1 = [], list2 = [0]
   * Output: [0]
   * Explanation: The non-empty list is already the answer.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public ListNode q06MergeTwoListsRecursive(ListNode list1, ListNode list2) {
    if (list1 == null) return list2;
    if (list2 == null) return list1;

    if (list1.val <= list2.val) {
      list1.next = q06MergeTwoListsRecursive(list1.next, list2);
      return list1;
    }

    list2.next = q06MergeTwoListsRecursive(list1, list2.next);
    return list2;
  }

  /*
   * Question 7: K-th Symbol in Grammar
   * 
   * Question: In row 1 the grammar is 0. Each 0 becomes 01 and each 1 becomes 10. Return the kth symbol in row n.
   * 
   * Constraints: 1 <= n <= 30; 1 <= k <= 2^(n - 1).
   * 
   * Optimized time/space complexity: Time O(n); Space O(1). Count flips while walking from k toward the root.
   * Recursive time/space complexity: Time O(n); Space O(n). Recursively ask for the parent symbol.
   * 
   * Example 1:
   * Input: n = 1, k = 1
   * Output: 0
   * Explanation: The first row is only 0.
   * 
   * Example 2:
   * Input: n = 2, k = 1
   * Output: 0
   * Explanation: Row 2 is 01.
   * 
   * Example 3:
   * Input: n = 2, k = 2
   * Output: 1
   * Explanation: The second symbol in 01 is 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
  public int q07KthGrammarOptimized(int n, int k) {
    int flips = 0;
    while (k > 1) {
      if (k % 2 == 0) flips++;
      k = (k + 1) / 2;
    }
    return flips % 2;
  }

  // Recursive solution
  public int q07KthGrammarRecursive(int n, int k) {
    if (n == 1) return 0;

    int parent = q07KthGrammarRecursive(n - 1, (k + 1) / 2);
    if (k % 2 == 1) return parent;
    return 1 - parent;
  }

  /*
   * Question 8: Generate Parentheses
   * 
   * Question: Given n pairs of parentheses, generate all combinations of well-formed parentheses.
   * 
   * Constraints: 1 <= n <= 8.
   * 
   * Optimized time/space complexity: Time O(Cn * n); Space O(Cn * n). Iterative stack explores only valid prefixes, where Cn is the nth Catalan number.
   * Recursive time/space complexity: Time O(Cn * n); Space O(n) call stack excluding output. Recursion prunes invalid prefixes early.
   * 
   * Example 1:
   * Input: n = 3
   * Output: ["((()))","(()())","(())()","()(())","()()()"]
   * Explanation: All valid strings with three pairs are generated.
   * 
   * Example 2:
   * Input: n = 1
   * Output: ["()"]
   * Explanation: Only one valid pair exists.
   * 
   * Example 3:
   * Input: n = 2
   * Output: ["(())","()()"]
   * Explanation: Two valid combinations exist.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public List<String> q08GenerateParenthesisRecursive(int n) {
    List<String> answer = new ArrayList<>();
    backtrackQ8Recursive(n, 0, 0, new StringBuilder(), answer);
    return answer;
  }

  private void backtrackQ8Recursive(int n, int open, int close, StringBuilder path, List<String> answer) {
    if (path.length() == 2 * n) {
      answer.add(path.toString());
      return;
    }

    if (open < n) {
      path.append('(');
      backtrackQ8Recursive(n, open + 1, close, path, answer);
      path.deleteCharAt(path.length() - 1);
    }
    if (close < open) {
      path.append(')');
      backtrackQ8Recursive(n, open, close + 1, path, answer);
      path.deleteCharAt(path.length() - 1);
    }
  }

  /*
   * Question 9: Subsets
   * 
   * Question: Given an integer array nums with unique elements, return all possible subsets of nums.
   * 
   * Constraints: 1 <= nums.length <= 10; -10 <= nums[i] <= 10; all nums values are unique.
   * 
   * Optimized time/space complexity: Time O(n * 2^n); Space O(n * 2^n). Iteratively extend previous subsets.
   * Recursive time/space complexity: Time O(n * 2^n); Space O(n) recursion stack excluding output. Include-exclude recursion creates every subset.
   * 
   * Example 1:
   * Input: nums = [1,2,3]
   * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
   * Explanation: All 2^3 subsets are returned.
   * 
   * Example 2:
   * Input: nums = [0]
   * Output: [[],[0]]
   * Explanation: A single element can be excluded or included.
   * 
   * Example 3:
   * Input: nums = [1,2]
   * Output: [[],[1],[2],[1,2]]
   * Explanation: There are four subsets.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public List<List<Integer>> q09SubsetsRecursive(int[] nums) {
    List<List<Integer>> answer = new ArrayList<>();
    dfsQ9Recursive(nums, 0, new ArrayList<>(), answer);
    return answer;
  }

  private void dfsQ9Recursive(int[] nums, int index, List<Integer> path, List<List<Integer>> answer) {
    if (index == nums.length) {
      answer.add(new ArrayList<>(path));
      return;
    }

    dfsQ9Recursive(nums, index + 1, path, answer);
    path.add(nums[index]);
    dfsQ9Recursive(nums, index + 1, path, answer);
    path.remove(path.size() - 1);
  }

  /*
   * Question 10: Permutations
   * 
   * Question: Given an array nums of distinct integers, return all possible permutations.
   * 
   * Constraints: 1 <= nums.length <= 6; -10 <= nums[i] <= 10; all nums values are unique.
   * 
   * Optimized time/space complexity: Time O(n! * n); Space O(n! * n). Iterative insertion builds permutations level by level.
   * Recursive time/space complexity: Time O(n! * n); Space O(n) recursion stack excluding output. Backtracking chooses unused values.
   * 
   * Example 1:
   * Input: nums = [1,2,3]
   * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
   * Explanation: All 3! orderings are generated.
   * 
   * Example 2:
   * Input: nums = [0,1]
   * Output: [[0,1],[1,0]]
   * Explanation: Two elements have two permutations.
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: [[1]]
   * Explanation: One element has one ordering.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public List<List<Integer>> q10PermuteRecursive(int[] nums) {
    List<List<Integer>> answer = new ArrayList<>();
    dfsQ10Recursive(nums, new boolean[nums.length], new ArrayList<>(), answer);
    return answer;
  }

  private void dfsQ10Recursive(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> answer) {
    if (path.size() == nums.length) {
      answer.add(new ArrayList<>(path));
      return;
    }

    for (int i = 0; i < nums.length; i++) {
      if (used[i]) continue;
      used[i] = true;
      path.add(nums[i]);
      dfsQ10Recursive(nums, used, path, answer);
      path.remove(path.size() - 1);
      used[i] = false;
    }
  }

  /*
   * Question 11: Letter Combinations of a Phone Number
   * 
   * Question: Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.
   * 
   * Constraints: 0 <= digits.length <= 4; digits[i] is in 2..9.
   * 
   * Optimized time/space complexity: Time O(4^n * n); Space O(4^n * n). Queue expansion processes combinations level by level.
   * Recursive time/space complexity: Time O(4^n * n); Space O(n) call stack excluding output. Backtracking builds one string at a time.
   * 
   * Example 1:
   * Input: digits = "23"
   * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
   * Explanation: Every letter from 2 combines with every letter from 3.
   * 
   * Example 2:
   * Input: digits = ""
   * Output: []
   * Explanation: No digits means no combinations.
   * 
   * Example 3:
   * Input: digits = "2"
   * Output: ["a","b","c"]
   * Explanation: Digit 2 maps to a, b, and c.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  private static final String[] MAPQ11Recursive = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

  public List<String> q11LetterCombinationsRecursive(String digits) {
    List<String> answer = new ArrayList<>();
    if (digits.length() == 0) return answer;
    dfsQ11Recursive(digits, 0, new StringBuilder(), answer);
    return answer;
  }

  private void dfsQ11Recursive(String digits, int index, StringBuilder path, List<String> answer) {
    if (index == digits.length()) {
      answer.add(path.toString());
      return;
    }

    for (char ch : MAPQ11Recursive[digits.charAt(index) - '0'].toCharArray()) {
      path.append(ch);
      dfsQ11Recursive(digits, index + 1, path, answer);
      path.deleteCharAt(path.length() - 1);
    }
  }

  /*
   * Question 12: Combinations
   * 
   * Question: Given integers n and k, return all possible combinations of k numbers chosen from the range 1 to n.
   * 
   * Constraints: 1 <= n <= 20; 1 <= k <= n.
   * 
   * Optimized time/space complexity: Time O(n*S + R*k); auxiliary space O(F*k), plus O(R*k) output. S is explored partial combinations and F is maximum pending stack states; each state copies up to k values.
   * Recursive time/space complexity: Time O(C(n,k) * k); Space O(k) call stack excluding output. Pruned recursion builds only valid-size paths.
   * 
   * Example 1:
   * Input: n = 4, k = 2
   * Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
   * Explanation: All size-2 choices from 1..4 are returned.
   * 
   * Example 2:
   * Input: n = 1, k = 1
   * Output: [[1]]
   * Explanation: Only one combination exists.
   * 
   * Example 3:
   * Input: n = 3, k = 3
   * Output: [[1,2,3]]
   * Explanation: Choosing all numbers gives one combination.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/recursion.html
   */
  // Optimized solution
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

  // Recursive solution
  public List<List<Integer>> q12CombineRecursive(int n, int k) {
    List<List<Integer>> answer = new ArrayList<>();
    dfsQ12Recursive(1, n, k, new ArrayList<>(), answer);
    return answer;
  }

  private void dfsQ12Recursive(int start, int n, int k, List<Integer> path, List<List<Integer>> answer) {
    if (path.size() == k) {
      answer.add(new ArrayList<>(path));
      return;
    }

    int need = k - path.size();
    for (int num = start; num <= n - need + 1; num++) {
      path.add(num);
      dfsQ12Recursive(num + 1, n, k, path, answer);
      path.remove(path.size() - 1);
    }
  }
}

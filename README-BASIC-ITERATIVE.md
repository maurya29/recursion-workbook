# Recursion - Basic Iterative Questions and Solutions

## Topic 10: Recursion

#### 1. Fibonacci Number - [LeetCode 509](https://leetcode.com/problems/fibonacci-number/)

Given n, return the nth Fibonacci number where F(0) = 0, F(1) = 1, and F(n) = F(n - 1) + F(n - 2).

```java
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
```

#### 2. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

You are climbing a staircase with n steps. Each move can climb 1 or 2 steps. Return the number of distinct ways to reach the top.

```java
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
```

#### 3. Pow(x, n) - [LeetCode 50](https://leetcode.com/problems/powx-n/)

Implement pow(x, n), which calculates x raised to the power n.

```java
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
```

#### 4. Reverse String - [LeetCode 344](https://leetcode.com/problems/reverse-string/)

Given a character array s, reverse it in-place.

```java
public void q04ReverseStringOptimized(char[] s) {
  int left = 0;
  int right = s.length - 1;

  while (left < right) {
    char temp = s[left];
    s[left++] = s[right];
    s[right--] = temp;
  }
}
```

#### 5. Swap Nodes in Pairs - [LeetCode 24](https://leetcode.com/problems/swap-nodes-in-pairs/)

Given a linked list, swap every two adjacent nodes and return its head. Node values must not be modified.

```java
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
```

#### 6. Merge Two Sorted Lists - [LeetCode 21](https://leetcode.com/problems/merge-two-sorted-lists/)

Given the heads of two sorted linked lists, merge them into one sorted linked list and return its head.

```java
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
```

#### 7. K-th Symbol in Grammar - [LeetCode 779](https://leetcode.com/problems/k-th-symbol-in-grammar/)

In row 1 the grammar is 0. Each 0 becomes 01 and each 1 becomes 10. Return the kth symbol in row n.

```java
public int q07KthGrammarOptimized(int n, int k) {
  int flips = 0;
  while (k > 1) {
    if (k % 2 == 0) flips++;
    k = (k + 1) / 2;
  }
  return flips % 2;
}
```

#### 8. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
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
```

#### 9. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets of nums.

```java
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
```

#### 10. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
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
```

#### 11. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
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
```

#### 12. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given integers n and k, return all possible combinations of k numbers chosen from the range 1 to n.

```java
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
```

## Topic 11: Backtracking

#### 1. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets. The solution set must not contain duplicate subsets.

```java
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
```

#### 2. Subsets II - [LeetCode 90](https://leetcode.com/problems/subsets-ii/)

Given an integer array nums that may contain duplicates, return all possible subsets without duplicate subsets.

```java
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
```

#### 3. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
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
```

#### 4. Permutations II - [LeetCode 47](https://leetcode.com/problems/permutations-ii/)

Given a collection of numbers nums that may contain duplicates, return all unique permutations.

```java
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
```

#### 5. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given n and k, return all possible combinations of k numbers chosen from 1 to n.

```java
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
```

#### 6. Combination Sum - [LeetCode 39](https://leetcode.com/problems/combination-sum/)

Given distinct candidates and a target, return all unique combinations where chosen numbers sum to target. The same number may be chosen unlimited times.

```java
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
```

#### 7. Combination Sum II - [LeetCode 40](https://leetcode.com/problems/combination-sum-ii/)

Given candidates that may contain duplicates and a target, return all unique combinations where each candidate may be used at most once.

```java
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
```

#### 8. Combination Sum III - [LeetCode 216](https://leetcode.com/problems/combination-sum-iii/)

Find all valid combinations of k numbers that sum to n using only numbers 1 through 9, where each number is used at most once.

```java
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
```

#### 9. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
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
```

#### 10. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
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
```

#### 11. Palindrome Partitioning - [LeetCode 131](https://leetcode.com/problems/palindrome-partitioning/)

Given a string s, partition it so that every substring in the partition is a palindrome. Return all possible palindrome partitionings.

```java
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
```

#### 12. Restore IP Addresses - [LeetCode 93](https://leetcode.com/problems/restore-ip-addresses/)

Given a string containing only digits, return all possible valid IP addresses that can be formed by inserting three dots.

```java
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
```

## Topic 19: Greedy

#### 1. Assign Cookies - [LeetCode 455](https://leetcode.com/problems/assign-cookies/)

Given children greed factors and cookie sizes, return the maximum number of children that can receive one cookie with size at least their greed.

```java
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
```

#### 2. Lemonade Change - [LeetCode 860](https://leetcode.com/problems/lemonade-change/)

Customers pay in order with 5, 10, or 20 dollar bills for 5 dollar lemonade. Return true if exact change can be given to every customer.

```java
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
```

#### 3. Jump Game - [LeetCode 55](https://leetcode.com/problems/jump-game/)

Given nums where nums[i] is the maximum jump length from index i, return true if the last index is reachable from index 0.

```java
public boolean q03CanJumpOptimized(int[] nums) {
  int farthest = 0;
  for (int i = 0; i < nums.length; i++) {
    if (i > farthest) return false;
    farthest = Math.max(farthest, i + nums[i]);
    if (farthest >= nums.length - 1) return true;
  }
  return true;
}
```

#### 4. Jump Game II - [LeetCode 45](https://leetcode.com/problems/jump-game-ii/)

Given nums where nums[i] is the maximum jump length from index i, return the minimum number of jumps needed to reach the last index.

```java
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
```

#### 5. Gas Station - [LeetCode 134](https://leetcode.com/problems/gas-station/)

Given gas and cost arrays around a circular route, return the starting station index that can complete the circuit, or -1 if impossible.

```java
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
```

#### 6. Candy - [LeetCode 135](https://leetcode.com/problems/candy/)

Given children ratings in a line, give each child at least one candy and give higher-rated children more candies than adjacent lower-rated children. Return the minimum candies needed.

```java
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
```

#### 7. Queue Reconstruction by Height - [LeetCode 406](https://leetcode.com/problems/queue-reconstruction-by-height/)

Given people as [height, k], reconstruct a queue where k is the number of people in front with height greater than or equal to height.

```java
public int[][] q07ReconstructQueueOptimized(int[][] people) {
  Arrays.sort(people, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
  List<int[]> queue = new ArrayList<>();

  for (int[] person : people) {
    queue.add(person[1], person);
  }
  return queue.toArray(new int[people.length][]);
}
```

#### 8. Non-overlapping Intervals - [LeetCode 435](https://leetcode.com/problems/non-overlapping-intervals/)

Given intervals, return the minimum number that must be removed so the remaining intervals do not overlap.

```java
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
```

#### 9. Merge Intervals - [LeetCode 56](https://leetcode.com/problems/merge-intervals/)

Given intervals, merge all overlapping intervals and return the non-overlapping result.

```java
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
```

#### 10. Minimum Number of Arrows to Burst Balloons - [LeetCode 452](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/)

Given balloon intervals on the x-axis, return the minimum arrows needed; one arrow shot at x bursts every balloon containing x.

```java
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
```

#### 11. Partition Labels - [LeetCode 763](https://leetcode.com/problems/partition-labels/)

Given a string, split it into as many parts as possible so each letter appears in at most one part. Return the sizes of the parts.

```java
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
```

#### 12. Task Scheduler - [LeetCode 621](https://leetcode.com/problems/task-scheduler/)

Given CPU tasks represented by letters and a cooldown n, return the least time units needed to execute all tasks with identical tasks separated by at least n intervals.

```java
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
```

## Topic 17: 1D Dynamic Programming

#### 1. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

Given n stairs, you can climb either 1 or 2 steps at a time. Return the number of distinct ways to reach the top.

```java
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
```

#### 2. Min Cost Climbing Stairs - [LeetCode 746](https://leetcode.com/problems/min-cost-climbing-stairs/)

Given cost[i] for stepping on stair i, return the minimum cost to reach the top when you may climb 1 or 2 steps each move.

```java
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
```

#### 3. House Robber - [LeetCode 198](https://leetcode.com/problems/house-robber/)

Given money in houses along a street, return the maximum amount you can rob without robbing adjacent houses.

```java
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
```

#### 4. House Robber II - [LeetCode 213](https://leetcode.com/problems/house-robber-ii/)

Given money in houses arranged in a circle, return the maximum amount you can rob without robbing adjacent houses.

```java
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
```

#### 5. Decode Ways - [LeetCode 91](https://leetcode.com/problems/decode-ways/)

Given a digit string where A-Z maps to 1-26, return the number of valid decodings.

```java
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
```

#### 6. Coin Change - [LeetCode 322](https://leetcode.com/problems/coin-change/)

Given coin denominations and an amount, return the fewest number of coins needed to make that amount, or -1 if impossible.

```java
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
```

#### 7. Coin Change II - [LeetCode 518](https://leetcode.com/problems/coin-change-ii/)

Given coin denominations and an amount, return the number of combinations that make up the amount. Each coin may be used unlimited times.

```java
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
```

#### 8. Combination Sum IV - [LeetCode 377](https://leetcode.com/problems/combination-sum-iv/)

Given distinct positive nums and target, return the number of ordered combinations that sum to target.

```java
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
```

#### 9. Longest Increasing Subsequence - [LeetCode 300](https://leetcode.com/problems/longest-increasing-subsequence/)

Given an integer array, return the length of the longest strictly increasing subsequence.

```java
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
```

#### 10. Longest Arithmetic Subsequence - [LeetCode 1027](https://leetcode.com/problems/longest-arithmetic-subsequence/)

Given an array, return the length of the longest arithmetic subsequence.

```java
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
```

#### 11. Partition Equal Subset Sum - [LeetCode 416](https://leetcode.com/problems/partition-equal-subset-sum/)

Given an integer array, return true if it can be partitioned into two subsets with equal sum.

```java
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
```

#### 12. Word Break - [LeetCode 139](https://leetcode.com/problems/word-break/)

Given a string and a dictionary, return true if the string can be segmented into a sequence of one or more dictionary words.

```java
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
```

## Topic 18: 2D Dynamic Programming

#### 1. Unique Paths - [LeetCode 62](https://leetcode.com/problems/unique-paths/)

Given an m x n grid, a robot starts at the top-left and can move only right or down. Return the number of paths to the bottom-right.

```java
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
```

#### 2. Unique Paths II - [LeetCode 63](https://leetcode.com/problems/unique-paths-ii/)

Given an m x n grid where 1 marks an obstacle and 0 marks open space, return the number of right/down paths from top-left to bottom-right.

```java
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
```

#### 3. Minimum Path Sum - [LeetCode 64](https://leetcode.com/problems/minimum-path-sum/)

Given a grid of non-negative numbers, return the minimum sum path from top-left to bottom-right moving only right or down.

```java
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
```

#### 4. Triangle - [LeetCode 120](https://leetcode.com/problems/triangle/)

Given a triangle array, return the minimum path sum from top to bottom by moving to adjacent numbers on the next row.

```java
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
```

#### 5. Longest Common Subsequence - [LeetCode 1143](https://leetcode.com/problems/longest-common-subsequence/)

Given two strings, return the length of their longest common subsequence.

```java
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
```

#### 6. Edit Distance - [LeetCode 72](https://leetcode.com/problems/edit-distance/)

Given two words, return the minimum number of insert, delete, and replace operations needed to convert word1 into word2.

```java
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
```

#### 7. Regular Expression Matching - [LeetCode 10](https://leetcode.com/problems/regular-expression-matching/)

Given string s and pattern p containing . and *, return whether p matches the entire string. Dot matches any single character and star repeats the previous element zero or more times.

```java
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
```

#### 8. Wildcard Matching - [LeetCode 44](https://leetcode.com/problems/wildcard-matching/)

Given string s and pattern p containing ? and *, return whether p matches the entire string. ? matches one character and * matches any sequence including empty.

```java
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
```

#### 9. Distinct Subsequences - [LeetCode 115](https://leetcode.com/problems/distinct-subsequences/)

Given strings s and t, return the number of distinct subsequences of s equal to t.

```java
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
```

#### 10. Interleaving String - [LeetCode 97](https://leetcode.com/problems/interleaving-string/)

Given s1, s2, and s3, return true if s3 is formed by interleaving s1 and s2 while preserving the order of characters from each string.

```java
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
```

#### 11. Longest Palindromic Subsequence - [LeetCode 516](https://leetcode.com/problems/longest-palindromic-subsequence/)

Given a string s, return the length of the longest subsequence that reads the same forward and backward.

```java
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
```

#### 12. Longest Palindromic Substring - [LeetCode 5](https://leetcode.com/problems/longest-palindromic-substring/)

Given a string s, return the longest contiguous substring that is a palindrome.

```java
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
```

# Recursion - Basic Recursive Questions and Solutions

## Topic 10: Recursion

#### 1. Fibonacci Number - [LeetCode 509](https://leetcode.com/problems/fibonacci-number/)

Given n, return the nth Fibonacci number where F(0) = 0, F(1) = 1, and F(n) = F(n - 1) + F(n - 2).

```java
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
```

#### 2. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

You are climbing a staircase with n steps. Each move can climb 1 or 2 steps. Return the number of distinct ways to reach the top.

```java
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
```

#### 3. Pow(x, n) - [LeetCode 50](https://leetcode.com/problems/powx-n/)

Implement pow(x, n), which calculates x raised to the power n.

```java
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
```

#### 4. Reverse String - [LeetCode 344](https://leetcode.com/problems/reverse-string/)

Given a character array s, reverse it in-place.

```java
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
```

#### 5. Swap Nodes in Pairs - [LeetCode 24](https://leetcode.com/problems/swap-nodes-in-pairs/)

Given a linked list, swap every two adjacent nodes and return its head. Node values must not be modified.

```java
public ListNode q05SwapPairsRecursive(ListNode head) {
  if (head == null || head.next == null) return head;

  ListNode second = head.next;
  head.next = q05SwapPairsRecursive(second.next);
  second.next = head;
  return second;
}
```

#### 6. Merge Two Sorted Lists - [LeetCode 21](https://leetcode.com/problems/merge-two-sorted-lists/)

Given the heads of two sorted linked lists, merge them into one sorted linked list and return its head.

```java
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
```

#### 7. K-th Symbol in Grammar - [LeetCode 779](https://leetcode.com/problems/k-th-symbol-in-grammar/)

In row 1 the grammar is 0. Each 0 becomes 01 and each 1 becomes 10. Return the kth symbol in row n.

```java
public int q07KthGrammarRecursive(int n, int k) {
  if (n == 1) return 0;

  int parent = q07KthGrammarRecursive(n - 1, (k + 1) / 2);
  if (k % 2 == 1) return parent;
  return 1 - parent;
}
```

#### 8. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
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
```

#### 9. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets of nums.

```java
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
```

#### 10. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
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
```

#### 11. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
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
```

#### 12. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given integers n and k, return all possible combinations of k numbers chosen from the range 1 to n.

```java
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
```

## Topic 11: Backtracking

#### 1. Subsets - [LeetCode 78](https://leetcode.com/problems/subsets/)

Given an integer array nums with unique elements, return all possible subsets. The solution set must not contain duplicate subsets.

```java
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
```

#### 2. Subsets II - [LeetCode 90](https://leetcode.com/problems/subsets-ii/)

Given an integer array nums that may contain duplicates, return all possible subsets without duplicate subsets.

```java
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
```

#### 3. Permutations - [LeetCode 46](https://leetcode.com/problems/permutations/)

Given an array nums of distinct integers, return all possible permutations.

```java
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
```

#### 4. Permutations II - [LeetCode 47](https://leetcode.com/problems/permutations-ii/)

Given a collection of numbers nums that may contain duplicates, return all unique permutations.

```java
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
```

#### 5. Combinations - [LeetCode 77](https://leetcode.com/problems/combinations/)

Given n and k, return all possible combinations of k numbers chosen from 1 to n.

```java
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
```

#### 6. Combination Sum - [LeetCode 39](https://leetcode.com/problems/combination-sum/)

Given distinct candidates and a target, return all unique combinations where chosen numbers sum to target. The same number may be chosen unlimited times.

```java
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
```

#### 7. Combination Sum II - [LeetCode 40](https://leetcode.com/problems/combination-sum-ii/)

Given candidates that may contain duplicates and a target, return all unique combinations where each candidate may be used at most once.

```java
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
```

#### 8. Combination Sum III - [LeetCode 216](https://leetcode.com/problems/combination-sum-iii/)

Find all valid combinations of k numbers that sum to n using only numbers 1 through 9, where each number is used at most once.

```java
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
```

#### 9. Generate Parentheses - [LeetCode 22](https://leetcode.com/problems/generate-parentheses/)

Given n pairs of parentheses, generate all combinations of well-formed parentheses.

```java
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
```

#### 10. Letter Combinations of a Phone Number - [LeetCode 17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)

Given a string digits containing digits 2 through 9, return all possible letter combinations the number could represent.

```java
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
```

#### 11. Palindrome Partitioning - [LeetCode 131](https://leetcode.com/problems/palindrome-partitioning/)

Given a string s, partition it so that every substring in the partition is a palindrome. Return all possible palindrome partitionings.

```java
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
```

#### 12. Restore IP Addresses - [LeetCode 93](https://leetcode.com/problems/restore-ip-addresses/)

Given a string containing only digits, return all possible valid IP addresses that can be formed by inserting three dots.

```java
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
```

## Topic 19: Greedy

#### 1. Assign Cookies - [LeetCode 455](https://leetcode.com/problems/assign-cookies/)

Given children greed factors and cookie sizes, return the maximum number of children that can receive one cookie with size at least their greed.

```java
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
```

#### 2. Lemonade Change - [LeetCode 860](https://leetcode.com/problems/lemonade-change/)

Customers pay in order with 5, 10, or 20 dollar bills for 5 dollar lemonade. Return true if exact change can be given to every customer.

```java
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
```

#### 3. Jump Game - [LeetCode 55](https://leetcode.com/problems/jump-game/)

Given nums where nums[i] is the maximum jump length from index i, return true if the last index is reachable from index 0.

```java
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
```

#### 4. Jump Game II - [LeetCode 45](https://leetcode.com/problems/jump-game-ii/)

Given nums where nums[i] is the maximum jump length from index i, return the minimum number of jumps needed to reach the last index.

```java
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
```

#### 5. Gas Station - [LeetCode 134](https://leetcode.com/problems/gas-station/)

Given gas and cost arrays around a circular route, return the starting station index that can complete the circuit, or -1 if impossible.

```java
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
```

#### 6. Candy - [LeetCode 135](https://leetcode.com/problems/candy/)

Given children ratings in a line, give each child at least one candy and give higher-rated children more candies than adjacent lower-rated children. Return the minimum candies needed.

```java
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
```

#### 7. Queue Reconstruction by Height - [LeetCode 406](https://leetcode.com/problems/queue-reconstruction-by-height/)

Given people as [height, k], reconstruct a queue where k is the number of people in front with height greater than or equal to height.

```java
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
```

#### 8. Non-overlapping Intervals - [LeetCode 435](https://leetcode.com/problems/non-overlapping-intervals/)

Given intervals, return the minimum number that must be removed so the remaining intervals do not overlap.

```java
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
```

#### 9. Merge Intervals - [LeetCode 56](https://leetcode.com/problems/merge-intervals/)

Given intervals, merge all overlapping intervals and return the non-overlapping result.

```java
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
```

#### 10. Minimum Number of Arrows to Burst Balloons - [LeetCode 452](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/)

Given balloon intervals on the x-axis, return the minimum arrows needed; one arrow shot at x bursts every balloon containing x.

```java
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
```

#### 11. Partition Labels - [LeetCode 763](https://leetcode.com/problems/partition-labels/)

Given a string, split it into as many parts as possible so each letter appears in at most one part. Return the sizes of the parts.

```java
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
```

#### 12. Task Scheduler - [LeetCode 621](https://leetcode.com/problems/task-scheduler/)

Given CPU tasks represented by letters and a cooldown n, return the least time units needed to execute all tasks with identical tasks separated by at least n intervals.

```java
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
```

## Topic 17: 1D Dynamic Programming

#### 1. Climbing Stairs - [LeetCode 70](https://leetcode.com/problems/climbing-stairs/)

Given n stairs, you can climb either 1 or 2 steps at a time. Return the number of distinct ways to reach the top.

```java
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
```

#### 2. Min Cost Climbing Stairs - [LeetCode 746](https://leetcode.com/problems/min-cost-climbing-stairs/)

Given cost[i] for stepping on stair i, return the minimum cost to reach the top when you may climb 1 or 2 steps each move.

```java
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
```

#### 3. House Robber - [LeetCode 198](https://leetcode.com/problems/house-robber/)

Given money in houses along a street, return the maximum amount you can rob without robbing adjacent houses.

```java
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
```

#### 4. House Robber II - [LeetCode 213](https://leetcode.com/problems/house-robber-ii/)

Given money in houses arranged in a circle, return the maximum amount you can rob without robbing adjacent houses.

```java
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
```

#### 5. Decode Ways - [LeetCode 91](https://leetcode.com/problems/decode-ways/)

Given a digit string where A-Z maps to 1-26, return the number of valid decodings.

```java
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
```

#### 6. Coin Change - [LeetCode 322](https://leetcode.com/problems/coin-change/)

Given coin denominations and an amount, return the fewest number of coins needed to make that amount, or -1 if impossible.

```java
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
```

#### 7. Coin Change II - [LeetCode 518](https://leetcode.com/problems/coin-change-ii/)

Given coin denominations and an amount, return the number of combinations that make up the amount. Each coin may be used unlimited times.

```java
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
```

#### 8. Combination Sum IV - [LeetCode 377](https://leetcode.com/problems/combination-sum-iv/)

Given distinct positive nums and target, return the number of ordered combinations that sum to target.

```java
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
```

#### 9. Longest Increasing Subsequence - [LeetCode 300](https://leetcode.com/problems/longest-increasing-subsequence/)

Given an integer array, return the length of the longest strictly increasing subsequence.

```java
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
```

#### 10. Longest Arithmetic Subsequence - [LeetCode 1027](https://leetcode.com/problems/longest-arithmetic-subsequence/)

Given an array, return the length of the longest arithmetic subsequence.

```java
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
```

#### 11. Partition Equal Subset Sum - [LeetCode 416](https://leetcode.com/problems/partition-equal-subset-sum/)

Given an integer array, return true if it can be partitioned into two subsets with equal sum.

```java
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
```

#### 12. Word Break - [LeetCode 139](https://leetcode.com/problems/word-break/)

Given a string and a dictionary, return true if the string can be segmented into a sequence of one or more dictionary words.

```java
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
```

## Topic 18: 2D Dynamic Programming

#### 1. Unique Paths - [LeetCode 62](https://leetcode.com/problems/unique-paths/)

Given an m x n grid, a robot starts at the top-left and can move only right or down. Return the number of paths to the bottom-right.

```java
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
```

#### 2. Unique Paths II - [LeetCode 63](https://leetcode.com/problems/unique-paths-ii/)

Given an m x n grid where 1 marks an obstacle and 0 marks open space, return the number of right/down paths from top-left to bottom-right.

```java
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
```

#### 3. Minimum Path Sum - [LeetCode 64](https://leetcode.com/problems/minimum-path-sum/)

Given a grid of non-negative numbers, return the minimum sum path from top-left to bottom-right moving only right or down.

```java
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
```

#### 4. Triangle - [LeetCode 120](https://leetcode.com/problems/triangle/)

Given a triangle array, return the minimum path sum from top to bottom by moving to adjacent numbers on the next row.

```java
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
```

#### 5. Longest Common Subsequence - [LeetCode 1143](https://leetcode.com/problems/longest-common-subsequence/)

Given two strings, return the length of their longest common subsequence.

```java
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
```

#### 6. Edit Distance - [LeetCode 72](https://leetcode.com/problems/edit-distance/)

Given two words, return the minimum number of insert, delete, and replace operations needed to convert word1 into word2.

```java
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
```

#### 7. Regular Expression Matching - [LeetCode 10](https://leetcode.com/problems/regular-expression-matching/)

Given string s and pattern p containing . and *, return whether p matches the entire string. Dot matches any single character and star repeats the previous element zero or more times.

```java
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
```

#### 8. Wildcard Matching - [LeetCode 44](https://leetcode.com/problems/wildcard-matching/)

Given string s and pattern p containing ? and *, return whether p matches the entire string. ? matches one character and * matches any sequence including empty.

```java
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
```

#### 9. Distinct Subsequences - [LeetCode 115](https://leetcode.com/problems/distinct-subsequences/)

Given strings s and t, return the number of distinct subsequences of s equal to t.

```java
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
```

#### 10. Interleaving String - [LeetCode 97](https://leetcode.com/problems/interleaving-string/)

Given s1, s2, and s3, return true if s3 is formed by interleaving s1 and s2 while preserving the order of characters from each string.

```java
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
```

#### 11. Longest Palindromic Subsequence - [LeetCode 516](https://leetcode.com/problems/longest-palindromic-subsequence/)

Given a string s, return the length of the longest subsequence that reads the same forward and backward.

```java
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
```

#### 12. Longest Palindromic Substring - [LeetCode 5](https://leetcode.com/problems/longest-palindromic-substring/)

Given a string s, return the longest contiguous substring that is a palindrome.

```java
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
```

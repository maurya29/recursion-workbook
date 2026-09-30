# Recursion, Backtracking, Greedy and Dynamic Programming Workbook

**100 questions, 200 solutions, 10 module classes.** This Java 8 compatible Maven project follows topics **10, 11, 19, 17, 18**, in that order, from the [DSA Pattern Workbook](https://maurya29.github.io/DSA-Pattern-Workbook/dsa.html), retrieved September 30, 2026.

Each topic has a **Basic class containing source questions 1–12** and a **Moderate class containing source questions 13–20**. These labels follow your requested ranges, rather than the website's difficulty ratings. Every question includes both an optimized method and a recursive method, with the question, constraints, examples, and separate time/space complexity comments.

## Modules

| Topic | Module | Basic: 1–12 | Moderate: 13–20 |
|---|---|---|---|
| 10 | Recursion | [RecursionBasic](src/main/java/com/interview/recursion/modules/RecursionBasic.java) | [RecursionModerate](src/main/java/com/interview/recursion/modules/RecursionModerate.java) |
| 11 | Backtracking | [BacktrackingBasic](src/main/java/com/interview/recursion/modules/BacktrackingBasic.java) | [BacktrackingModerate](src/main/java/com/interview/recursion/modules/BacktrackingModerate.java) |
| 19 | Greedy | [GreedyBasic](src/main/java/com/interview/recursion/modules/GreedyBasic.java) | [GreedyModerate](src/main/java/com/interview/recursion/modules/GreedyModerate.java) |
| 17 | 1D Dynamic Programming | [DynamicProgramming1DBasic](src/main/java/com/interview/recursion/modules/DynamicProgramming1DBasic.java) | [DynamicProgramming1DModerate](src/main/java/com/interview/recursion/modules/DynamicProgramming1DModerate.java) |
| 18 | 2D Dynamic Programming | [DynamicProgramming2DBasic](src/main/java/com/interview/recursion/modules/DynamicProgramming2DBasic.java) | [DynamicProgramming2DModerate](src/main/java/com/interview/recursion/modules/DynamicProgramming2DModerate.java) |

[Browse all questions and their two method names](CATALOG.md).

## Build and run

Requires a JDK (8 or later) and Maven. In this project directory:

```sh
mvn clean package
java -jar target/recursion-workbook-1.0.0.jar
```

Run the tests independently with `mvn test`. Import `pom.xml` in IntelliJ IDEA, Eclipse, or VS Code. Maven downloads build/test dependencies on the first build. Application code has no external runtime dependencies.

## Use either version

```java
import com.interview.recursion.modules.RecursionBasic;
import com.interview.recursion.modules.DynamicProgramming1DBasic;

RecursionBasic recursion = new RecursionBasic();
int iterative = recursion.q01FibOptimized(10); // 55
int recursive = recursion.q01FibRecursive(10); // 55, memoized

DynamicProgramming1DBasic dp = new DynamicProgramming1DBasic();
int minimumCoins = dp.q06CoinChangeOptimized(new int[]{1, 2, 5}, 11); // 3
int recursiveMinimum = dp.q06CoinChangeRecursive(new int[]{1, 2, 5}, 11); // 3
```

The `qNN` prefix preserves the website question number and disambiguates problems with identical original method names, such as House Robber I/II, stock variants, and regex/wildcard matching. All 200 solution entry points are public instance methods. Helpers, constants, and nested search-state classes belong to their individual question and variant. Shared `ListNode` and `TreeNode` models support the linked-list and tree questions.

## Implementation notes

Optimized answers are based on the source's optimized approach; recursive answers use its recursive approach, often memoization or backtracking. They are separate implementations, not wrappers that call the optimized method. Recursive versions may use more stack or memory; Java does not eliminate tail calls, so deep recursion can exceed the JVM stack on large valid inputs. Use the optimized version for those inputs.

Inputs are expected to satisfy the documented constraints. Sorting, Sudoku, linked-list rewiring, tree inversion, and other in-place algorithms may mutate their arguments. Pass independent copies when comparing versions. Numeric return types follow the source's judge-style APIs; integer-count examples assume results fit their return type. “Optimized” describes the supplied approach, not a claim of optimality under every time/space tradeoff.

Adaptations include:

- Separate public method names and isolated helpers/fields for all question variants.
- Concrete Sudoku examples in place of abbreviated boards.
- A call-local maximum in recursive Maximal Square so instances are reusable.
- Reuse of the equivalent Recursion topic's iterative Word Search bitmask and N-Queens row-backtracking implementations in Backtracking, avoiding per-state visited-grid/constraint-array copies.
- Corrections to selected complexity notes, including explicit-stack storage and output construction.

## Tests and provenance

The suite contains 200 source-example tests, one for every solution version, plus 10 regression tests. Regression checks cover base cases, extreme exponents, pruning, board restoration, module reuse, impossible states, and randomized agreement between selected recursive and optimized methods. These checks are not exhaustive proofs of correctness.

`workbook-data/` contains selected source metadata and both solution snippets, with source links. `workbook-index.json` and `CATALOG.md` map questions to their classes and entry points. Upstream material is attributed to the linked website; no new license is asserted for it.

Optional Python scripts regenerate the modules, catalog, and test fixtures from the saved snapshot:

```sh
python tools/build_workbook.py
python tools/build_example_tests.py
```

These scripts overwrite generated classes and fixtures. Maintain adaptations in the generator if regenerating. Python is not needed to compile or run the Java project.

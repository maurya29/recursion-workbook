package com.interview.recursion;

import com.interview.recursion.modules.*;
import com.interview.recursion.model.*;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/** Boundary and cross-variant checks for base cases, state reuse, and pruning. */
public class WorkbookRegressionTest {
    @Test public void fibonacciAndPowerBaseCases() {
        RecursionBasic module=new RecursionBasic();
        assertEquals(0,module.q01FibOptimized(0));
        assertEquals(0,module.q01FibRecursive(0));
        assertEquals(832040,module.q01FibRecursive(30));
        assertEquals(1.0,module.q03MyPowRecursive(2,0),0.0);
        assertEquals(0.25,module.q03MyPowRecursive(2,-2),0.0);
        assertEquals(module.q03MyPowOptimized(1,Integer.MIN_VALUE),module.q03MyPowRecursive(1,Integer.MIN_VALUE),0.0);
        assertEquals(1.0,module.q03MyPowRecursive(-1,Integer.MIN_VALUE),0.0);
    }

    @Test public void linkedListAndTreeBaseCases() {
        RecursionBasic basic=new RecursionBasic();
        RecursionModerate trees=new RecursionModerate();
        assertNull(basic.q05SwapPairsRecursive(null));
        assertNull(basic.q06MergeTwoListsOptimized(null,null));
        assertEquals(Arrays.asList(2,1,3),WorkbookExamplesTest.listValues(basic.q05SwapPairsRecursive(WorkbookExamplesTest.list(1,2,3))));
        assertTrue(trees.q17IsSameTreeOptimized(null,null));
        assertTrue(trees.q17IsSameTreeRecursive(null,null));
        assertFalse(trees.q17IsSameTreeRecursive(new TreeNode(1),null));
        assertTrue(trees.q18IsSymmetricRecursive(null));
        assertEquals(0,trees.q19MaxDepthRecursive(null));
        TreeNode one=new TreeNode(1,new TreeNode(2),null);
        assertEquals(2,trees.q19MaxDepthOptimized(one));
        assertFalse(trees.q18IsSymmetricOptimized(one));
        assertSame(one,trees.q20InvertTreeRecursive(one));
        assertNull(one.left); assertEquals(2,one.right.val);
    }

    @Test public void queensCountsAndCombinationPruning() {
        RecursionModerate recursion=new RecursionModerate();
        BacktrackingModerate backtracking=new BacktrackingModerate();
        int[] counts={1,0,0,2,10};
        for(int n=1;n<=5;n++) {
            assertEquals(counts[n-1],recursion.q14SolveNQueensOptimized(n).size());
            assertEquals(counts[n-1],recursion.q14SolveNQueensRecursive(n).size());
            assertEquals(counts[n-1],backtracking.q14SolveNQueensOptimized(n).size());
            assertEquals(counts[n-1],backtracking.q14SolveNQueensRecursive(n).size());
        }
        assertEquals(Arrays.asList(Arrays.asList(1,2,3,4)),new RecursionBasic().q12CombineOptimized(4,4));
        assertTrue(new BacktrackingBasic().q06CombinationSumRecursive(new int[]{4,6},3).isEmpty());
        assertEquals(3,new BacktrackingBasic().q04PermuteUniqueOptimized(new int[]{1,1,2}).size());
    }

    @Test public void wordSearchDoesNotReuseCellsAndRestoresBoard() {
        RecursionModerate recursion=new RecursionModerate();
        BacktrackingModerate backtracking=new BacktrackingModerate();
        char[][] board={{'A','B'}};
        assertFalse(recursion.q16ExistOptimized(board,"ABA"));
        assertFalse(recursion.q16ExistRecursive(board,"ABA"));
        assertFalse(backtracking.q13ExistOptimized(board,"ABA"));
        assertFalse(backtracking.q13ExistRecursive(board,"ABA"));
        assertTrue(backtracking.q13ExistRecursive(board,"AB"));
        assertArrayEquals(new char[]{'A','B'},board[0]);
        assertTrue(recursion.q16ExistOptimized(new char[][]{{'A'}},"A"));
    }

    @Test public void maximalSquareModuleCanBeReused() {
        DynamicProgramming2DModerate module=new DynamicProgramming2DModerate();
        assertEquals(4,module.q20MaximalSquareRecursive(new char[][]{{'1','1'},{'1','1'}}));
        assertEquals(0,module.q20MaximalSquareRecursive(new char[][]{{'0'}}));
        assertEquals(1,module.q20MaximalSquareRecursive(new char[][]{{'1'}}));
    }

    @Test public void knapsackZerosAndUnreachableStates() {
        DynamicProgramming1DBasic dp1=new DynamicProgramming1DBasic();
        DynamicProgramming2DModerate dp2=new DynamicProgramming2DModerate();
        assertEquals(0,dp1.q06CoinChangeRecursive(new int[]{2},0));
        assertEquals(-1,dp1.q06CoinChangeRecursive(new int[]{2},3));
        assertEquals(1,dp1.q07ChangeRecursive(0,new int[]{2}));
        assertEquals(0,dp1.q05NumDecodingsRecursive("06"));
        assertEquals(3,dp1.q05NumDecodingsRecursive("226"));
        assertEquals(8,dp2.q15FindTargetSumWaysOptimized(new int[]{0,0,0},0));
        assertEquals(8,dp2.q15FindTargetSumWaysRecursive(new int[]{0,0,0},0));
        assertEquals(0,dp2.q15FindTargetSumWaysRecursive(new int[]{1},2));
        assertFalse(dp1.q11CanPartitionRecursive(new int[]{1,2,5}));
    }

    @Test public void regexAndWildcardKeepDifferentSemantics() {
        DynamicProgramming2DBasic dp=new DynamicProgramming2DBasic();
        assertTrue(dp.q07IsMatchOptimized("aab","c*a*b"));
        assertTrue(dp.q07IsMatchRecursive("aab","c*a*b"));
        assertTrue(dp.q08IsMatchOptimized("adceb","*a*b"));
        assertTrue(dp.q08IsMatchRecursive("adceb","*a*b"));
        assertFalse(dp.q08IsMatchRecursive("acdcb","a*c?b"));
        assertEquals(3,dp.q06MinDistanceRecursive("","abc"));
    }

    @Test public void greedyImpossibleAndSingletonCases() {
        GreedyBasic basic=new GreedyBasic();
        GreedyModerate moderate=new GreedyModerate();
        assertFalse(basic.q03CanJumpRecursive(new int[]{3,2,1,0,4}));
        assertTrue(basic.q03CanJumpOptimized(new int[]{0}));
        assertEquals(0,basic.q04JumpRecursive(new int[]{0}));
        assertEquals(-1,basic.q05CanCompleteCircuitRecursive(new int[]{2,3,4},new int[]{3,4,3}));
        assertEquals("",moderate.q13ReorganizeStringOptimized("aaab"));
        assertEquals("",moderate.q13ReorganizeStringRecursive("aaab"));
        assertTrue(moderate.q19CanPlaceFlowersRecursive(new int[]{0},1));
        assertFalse(moderate.q20IncreasingTripletRecursive(new int[]{2,2,2}));
    }

    @Test public void randomizedDpAndGreedyVersionsAgree() {
        Random random=new Random(2910);
        DynamicProgramming1DBasic basic=new DynamicProgramming1DBasic();
        DynamicProgramming1DModerate moderate=new DynamicProgramming1DModerate();
        GreedyModerate greedy=new GreedyModerate();
        for(int trial=0;trial<60;trial++) {
            int[] values=new int[2+random.nextInt(10)];
            for(int i=0;i<values.length;i++) values[i]=random.nextInt(15);
            assertEquals(basic.q03RobOptimized(values),basic.q03RobRecursive(values));
            assertEquals(basic.q04RobOptimized(values),basic.q04RobRecursive(values));
            assertEquals(basic.q09LengthOfLISOptimized(values),basic.q09LengthOfLISRecursive(values));
            assertEquals(basic.q10LongestArithSeqLengthOptimized(values),basic.q10LongestArithSeqLengthRecursive(values));
            assertEquals(moderate.q18MaxProfitOptimized(values),moderate.q18MaxProfitRecursive(values));
            assertEquals(moderate.q19MaxProfitOptimized(values,2),moderate.q19MaxProfitRecursive(values,2));
            assertEquals(greedy.q18MaxProfitOptimized(values),greedy.q18MaxProfitRecursive(values));
        }
    }

    @Test public void blockedGridAndIncreasingPathAgree() {
        DynamicProgramming2DBasic basic=new DynamicProgramming2DBasic();
        DynamicProgramming2DModerate moderate=new DynamicProgramming2DModerate();
        assertEquals(0,basic.q02UniquePathsWithObstaclesRecursive(new int[][]{{1}}));
        assertEquals(1,basic.q02UniquePathsWithObstaclesOptimized(new int[][]{{0}}));
        assertEquals(1,moderate.q19LongestIncreasingPathRecursive(new int[][]{{7,7},{7,7}}));
        int[][] matrix={{1,2,3},{6,5,4},{7,8,9}};
        assertEquals(9,moderate.q19LongestIncreasingPathOptimized(matrix));
        assertEquals(9,moderate.q19LongestIncreasingPathRecursive(matrix));
    }
}

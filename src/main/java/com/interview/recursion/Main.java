package com.interview.recursion;

import com.interview.recursion.modules.*;

/** Small demo comparing both solution styles across all five topics. */
public class Main {
    public static void main(String[] args) {
        RecursionBasic recursion = new RecursionBasic();
        BacktrackingBasic backtracking = new BacktrackingBasic();
        GreedyBasic greedy = new GreedyBasic();
        DynamicProgramming1DBasic dp1 = new DynamicProgramming1DBasic();
        DynamicProgramming2DBasic dp2 = new DynamicProgramming2DBasic();
        System.out.println("Recursion workbook: 5 topics, 10 classes, 100 questions, 200 solutions");
        System.out.println("Fibonacci: " + recursion.q01FibOptimized(10) + " / " + recursion.q01FibRecursive(10));
        System.out.println("Subsets: " + backtracking.q01SubsetsOptimized(new int[]{1,2}).size() + " / " + backtracking.q01SubsetsRecursive(new int[]{1,2}).size());
        System.out.println("Jump game: " + greedy.q03CanJumpOptimized(new int[]{2,3,1,1,4}) + " / " + greedy.q03CanJumpRecursive(new int[]{2,3,1,1,4}));
        System.out.println("Coin change: " + dp1.q06CoinChangeOptimized(new int[]{1,2,5}, 11) + " / " + dp1.q06CoinChangeRecursive(new int[]{1,2,5}, 11));
        System.out.println("Unique paths: " + dp2.q01UniquePathsOptimized(3,7) + " / " + dp2.q01UniquePathsRecursive(3,7));
    }
}

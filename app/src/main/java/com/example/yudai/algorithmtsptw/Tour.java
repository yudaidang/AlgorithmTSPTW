package com.example.yudai.algorithmtsptw;

public class Tour {
    public double score;
    public int[] tour;
    public Tour() {
        score = 0;
        tour = new int[TSPTW.N+1];
    }
}

package com.example.yudai.algorithmtsptw.Algorithm;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DynamicProgrammingTSPTW {
    private final int N;
    private final int FINISHED_STATE;
    private final int START_NODE;
    private Integer[][] travelTime;
    private ArrayList<Locate> mLocates;
    private boolean ranSolver = false;
    private int minTravelTime = Integer.MAX_VALUE;
    private HashMap<Integer, Integer> mBefore = new HashMap<>();
    private List<Integer> tour = new ArrayList<>();

    public DynamicProgrammingTSPTW(Integer[][] travelTime, int startNode, ArrayList mLocates) {
        N = travelTime.length;
        FINISHED_STATE = (1 << N) - 1;
        this.travelTime = travelTime;
        START_NODE = startNode;
        this.mLocates = mLocates;
    }

    public List<Integer> getTour() {
        if (!ranSolver) solve();
        return tour;
    }

    // Returns the minimal tour cost.
    public double getTourTravelTime() {
        if (!ranSolver) solve();
        return minTravelTime;
    }

    public void solve() {

        // Run the solver
        int state = 1 << START_NODE;
        Integer[][] memo = new Integer[N][1 << N];
        Integer[][] prev = new Integer[N][1 << N];
        Log.d("YUALGORITHM", "START - NODE: " + START_NODE);
        minTravelTime = tsp(START_NODE, state, prev, memo, 540);

        // Regenerate path
        int index = START_NODE;
        while (true) {
            tour.add(index);
            Integer nextIndex = prev[index][state];
            if (nextIndex == null) break;
            int nextState = state | (1 << nextIndex);
            state = nextState;
            index = nextIndex;
        }
        tour.add(START_NODE);
        ranSolver = true;
    }

    private int tsp(int i, int state, Integer[][] prev, Integer[][] memo, int mStartTSV) {
        if (state == FINISHED_STATE) {
            Log.d("YUALGORITHM", "FINISH: " + i + " ");

            return travelTime[i][START_NODE];
        }
        int minTravelTime = Integer.MAX_VALUE;
        int index = -1;
        for (int next = 0; next < N; next++) {
            if ((state & (1 << next)) != 0) continue;
            Log.d("YUALGORITHM", "NODE: " + next);
            int nextState = state | (1 << next);
            //TEST
            if (!TEST1(mStartTSV, i, next) || !TEST2(nextState, next) || !TEST3(mStartTSV, nextState, i, next)) {
                continue;
            }
            int mStartTSVNext = mStartTSV + mLocates.get(i).getmStay() + travelTime[i][next];
            int newTravelTime;

            newTravelTime = travelTime[i][next] + tsp(next, nextState, prev, memo, mStartTSVNext);

            if (newTravelTime < minTravelTime && newTravelTime >= 0) {
                Log.d("YUALGORITHM", "NODE: " + i + " " + next + " DUYET ");
                minTravelTime = newTravelTime;
                index = next;
            }
        }

        if (((memo[i][state] != null && memo[i][state] > minTravelTime) || memo[i][state] == null) && index != -1) {
            memo[i][state] = minTravelTime;
            prev[i][state] = index;
            Log.d("YUALGORITHM", "NODE: " + i + " memo " + state);

        }
        if (index != -1) {

            return memo[i][state];
        } else {
            Log.d("YUALGORITHM", "NODE: " + i + " NOT FOR");
            return Integer.MAX_VALUE;
        }
    }

    private boolean TEST1(int mStartTime, int i, int j) {
        if (mStartTime + mLocates.get(i).getmStay() > mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
            Log.d("YUALGORITHM", "NODE: " + j + " REMOVE 1");

            return false;
        }
        return true;
    }

    private boolean TEST2(int state, int j) {
        if ((state & (1 << j)) == 0) {
            if (state != (state & mBefore.get(j)) && mBefore.get(j) != (state & mBefore.get(j))) {
                Log.d("YUALGORITHM", "NODE: " + j + " REMOVE 2");
                return false;
            }
        }
        return true;
    }

    //mStartTimei thoi gian bat dau phuc vu tai i
    private boolean TEST3(int mStartTimei, int state, int i, int j) {
        if (state == FINISHED_STATE) {
            return true;
        }
        if (mStartTimei <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
            for (int index = 0; index < N; index++) {
                if ((state & (1 << index)) != 0) continue;
                if (mStartTimei + travelTime[i][j] <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
                    return true;
                }
            }
            Log.d("YUALGORITHM", "NODE: " + j + " REMOVE 3");

            return false;
        }
        return true;
    }
}

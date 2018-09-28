package com.example.yudai.algorithmtsptw.Algorithm;

import android.util.Log;

import com.example.yudai.algorithmtsptw.Locate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DynamicProgrammingTSPTW {
    private final int N;
    private final int FINISHED_STATE;
    private final int START_NODE;
    private Integer[][] travelTime;
    private Integer[][] distance;
    private ArrayList<Locate> mLocates;
    private StringBuilder mTest = new StringBuilder();
    private boolean ranSolver = false;
    private int minTravelTime = Integer.MAX_VALUE;
    private HashMap<Integer, Integer> mBefore = new HashMap<>();
    private List<Integer> tour = new ArrayList<>();
    private StringBuilder str = new StringBuilder();

    public DynamicProgrammingTSPTW(Integer[][] travelTime, int startNode, ArrayList mLocates, Integer[][] distance) {
        this.distance = distance;
        N = travelTime.length;
        FINISHED_STATE = (1 << N) - 1;
        this.travelTime = travelTime;
        START_NODE = startNode;
        this.mLocates = mLocates;
/*        int state = 1 << START_NODE;
        Integer[][] prev = new Integer[N][1 << N];
        tsp(0, state, prev, 540, 0);*/
    }

    public StringBuilder getTour() {
        if (!ranSolver) solve();
        return str;
    }

    public double getTravelTime() {
        if (!ranSolver) solve();
        return minTravelTime;
    }

    public void solve() {

        // Run the solver
        int state = 1 << START_NODE;
        Double[][] memo = new Double[N][1 << N];
        Integer[][] prev = new Integer[N][1 << N];
        minTravelTime = tsp(0, state, prev, 540, 0);


        // Regenerate path
        int index = START_NODE;
        while (true) {
            tour.add(index);
            Integer nextIndex = prev[index][state];
            if (nextIndex == null || nextIndex == -1) break;
            int nextState = state | (1 << nextIndex);
            state = nextState;
            index = nextIndex;
        }
        tour.add(START_NODE);
        ranSolver = true;
    }

    private boolean TEST1(int mStartTime, int i, int j) {
        if (mStartTime + mLocates.get(i).getmStay() > mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
            return false;
        }
        return true;
    }

    private boolean TEST2(int state, int j) {
        if ((state & (1 << j)) == 0) {
            if (state != (state & mBefore.get(j)) && mBefore.get(j) != (state & mBefore.get(j))) {
                return false;
            }
        }
        return true;
    }

    //mStartTimei thoi gian bat dau phuc vu tai i
    private boolean TEST3(int mStartTimeI, int state, int i, int j, int mCheck) {
        if (mStartTimeI <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
            for (int index = 0; index < N; index++) {
                if ((state & (1 << index)) != 0 && state != 31) continue;
                if (mStartTimeI + travelTime[i][j] <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean DOMINANCETEST(int mStartTimeI, int mCost, int i, int j) {
        if (mCost <= (mCost + distance[i][j]) && mStartTimeI <= mStartTimeI + travelTime[i][j] + mLocates.get(i).getmStay()) {
            return false;
        }
        return true;
    }

    //mStartTSV" Start time service time
    private int tsp(int i, int state, Integer[][] prev, int mStartTSV, int mCost) {
        if (state == FINISHED_STATE) {
            return travelTime[i][START_NODE];
        }
        int minTravelTime = Integer.MAX_VALUE;
        int index = -1;
        int mCheck = 1 << START_NODE;

        for (int next = 0; next < N; next++) {
            if ((state & (1 << next)) != 0) continue;
            if (!TEST1(mStartTSV, i, next) || !TEST2(state | (1 << next), next) || !TEST3(mStartTSV, state | (1 << next), i, next, mCheck)
                /*|| DOMINANCETEST(mStartTSV, mCost, i, next)*/)
                continue;
            mCheck = mCheck | (1 << next);
            int nextState = state | (1 << next);

            int mStartTSVNext = mStartTSV + mLocates.get(i).getmStay() + travelTime[i][next];
            int temp = tsp(next, nextState, prev, mStartTSVNext, mCost + distance[i][next]);
            int newTravelTime = temp;
            if (temp != Integer.MAX_VALUE) {
                newTravelTime = mStartTSVNext + temp;
            }
            if (newTravelTime < minTravelTime) {

                minTravelTime = newTravelTime;
                index = next;
            }
        }
        if (mCheck == ((1 << N) - 1)) {
            prev[i][state] = index;
        }

        if (index == -1) {
            return Integer.MAX_VALUE;
        }

        return minTravelTime;
    }
}

package com.example.yudai.algorithmtsptw.Algorithm;

import android.util.Log;

import com.example.yudai.algorithmtsptw.Locate;

import java.util.ArrayList;
import java.util.HashMap;

public class DynamicProgrammingTSPTW {
    private final int N;
    private final int FINISHED_STATE;
    private Integer[][] travelTime;
    private final int START_NODE;
    private ArrayList<Locate> mLocates;
    private StringBuilder mTest = new StringBuilder();
    private boolean ranSolver = false;
    private int minTravelTime = Integer.MAX_VALUE;
    private HashMap<Integer, Integer> mBefore = new HashMap<>();

    public DynamicProgrammingTSPTW(Integer[][] travelTime, int startNode, ArrayList mLocates) {
        N = travelTime.length;
        FINISHED_STATE = (1 << N) - 1;
        this.travelTime = travelTime;
        START_NODE = startNode;
        this.mLocates = mLocates;
        int state = 1 << START_NODE;
        Integer[][] prev = new Integer[N][1 << N];
        tsp(0, state, prev, 540);
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
    private boolean TEST3(int mStartTimei, int state, int i, int j) {
        if (mStartTimei <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
            for (int index = 0; index < N; index++) {
                if ((state & (1 << index)) != 0) continue;
                if (mStartTimei + travelTime[i][j] <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean DOMINANCETEST(int mStartTimei, int state, int i, int j) {
        if (mStartTimei <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
            for (int index = 0; index < N; index++) {
                if ((state & (1 << index)) != 0) continue;
                if (mStartTimei + travelTime[i][j] <= mLocates.get(j).getmClose() - mLocates.get(j).getmStay() - travelTime[i][j]) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private int tsp(int i, int state, Integer[][] prev, int mStartTSV) {
        if (state == FINISHED_STATE) {
            Log.d("CONTINUEFINISH222", " STARTNODE: " + i + " " + mTest + " STATE " + (mStartTSV + mLocates.get(i).getmStay() + travelTime[i][START_NODE]));
            return travelTime[i][START_NODE];

        }

        int minTravelTime = Integer.MAX_VALUE;
        int index = -1;
        for (int next = 0; next < N; next++) {

            if ((state & (1 << next)) != 0) continue;
            int nextState = state | (1 << next);
            if (!TEST1(mStartTSV, i, next) || !TEST2(nextState, next) || !TEST3(mStartTSV, nextState, i, next))
                continue;

            int mStartTSVNext = mStartTSV + mLocates.get(i).getmStay() + travelTime[i][next];
            int newTravelTime;

            newTravelTime = mStartTSVNext + tsp(next, nextState, prev, mStartTSVNext);

            if (newTravelTime < minTravelTime) {

                minTravelTime = newTravelTime;
                Log.d("CONTINUE", " STARTNODE: " + i + " STATE: " + state + " NEXT: " + next + " " + minTravelTime);

                index = next;
            }
        }

        prev[i][state] = index;
        Log.d("HUHUHU2", "" + minTravelTime);
        return minTravelTime;
    }
}

package com.example.yudai.algorithmtsptw.Algorithm;

import android.util.Log;

import com.example.yudai.algorithmtsptw.Locate;

import java.util.ArrayList;

public class DynamicProgrammingTSPTW {
    private final int N;
    private final int FINISHED_STATE;
    private Integer[][] travelTime;
    private final int START_NOTE;
    private ArrayList<Locate> mLocates;

    public DynamicProgrammingTSPTW(Integer[][] travelTime, int startNode, ArrayList mLocates) {
        N = travelTime.length;
        FINISHED_STATE = (1 << N) - 1;
        this.travelTime = travelTime;
        START_NOTE = startNode;
        this.mLocates = mLocates;
        int state = 1 << START_NOTE;
        Integer[][] memo = new Integer[N][1 << N];
        Integer[][] prev = new Integer[N][1 << N];
        tsp(0, state, memo, prev, 540);
    }

    private int tsp(int i, int state, Integer[][] memo, Integer[][] prev, int mStartTime) {
        if (state == FINISHED_STATE) {
            Log.d("FINISH", " STARTNODE: "+ i + " ");
            return travelTime[i][START_NOTE];
        }

        if (memo[i][state] != null) return memo[i][state];


        int minTravelTime = Integer.MAX_VALUE;
        int index = -1;
        for (int next = 0; next < N; next++) {
            if ((state & (1 << next)) != 0) continue;

            int nextState = state | (1 << next);
            int tempStartTimeNext = mStartTime + travelTime[i][next] + mLocates.get(i).getmStay();
            int newTravelTime;
            if (tempStartTimeNext > mLocates.get(i).getmClose()) {
                continue;
            } else {
                newTravelTime = tempStartTimeNext + tsp(next, nextState, memo, prev, mStartTime);
            }

            if (newTravelTime < minTravelTime) {
                minTravelTime = newTravelTime;
                Log.d("HUHUHU1", " STARTNODE: " + i + " STATE: " + state + " NEXT: " + next + " " + minTravelTime);

                index = next;
            }
        }

        prev[i][state] = index;
        Log.d("HUHUHU2", "" + minTravelTime);
        return memo[i][state] = minTravelTime;
    }
}

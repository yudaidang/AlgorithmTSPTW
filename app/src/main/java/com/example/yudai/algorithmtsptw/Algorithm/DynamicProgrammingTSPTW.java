package com.example.yudai.algorithmtsptw.Algorithm;

import android.util.Log;

import com.example.yudai.algorithmtsptw.Locate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
        tsp(0, state,  prev, 540);
    }

    private int tsp(int i, int state, Integer[][] prev, int mStartTime, Integer[][] memo){
        if(state == FINISHED_STATE){
            return travelTime[i][START_NODE];
        }
    }

    private boolean TEST1 (int mStartTime, int i, int j, Integer[][] memo, int state){
        if(memo[i][state] > mLocates.get(j).getmClose() - mLocates.get(j).getmStay()){
            return false;
        }
        return true;
    }

    private boolean TEST2 (int state, int j){
        if((state & (1 << j)) == 0){
            if(state != (state & mBefore.get(j)) && mBefore.get(j) != (state & mBefore.get(j))){
                return false;
            }
        }
        return true;
    }

    private boolean TEST3(int mStartTimei, int state){
        if(mStartTimei <= (state & mBefore.get(j)) && mBefore.get(j) != (state & mBefore.get(j)))
    }

    private int tsp(int i, int state,  Integer[][] prev, int mStartTime) {
        if (state == FINISHED_STATE) {
            Log.d("CONTINUEFINISH222", " STARTNODE: "+ i + " " + mTest + " STATE " +  (mStartTime + mLocates.get(i).getmStay() + travelTime[i][START_NODE]));
            mTest.delete(mTest.length() -3, mTest.length());
            return travelTime[i][START_NODE];

        }

        int minTravelTime = Integer.MAX_VALUE;
        int index = -1;
        for (int next = 0; next < N; next++) {
            if ((state & (1 << next)) != 0) continue;

            int nextState = state | (1 << next);
            int mStartTimeNext = mStartTime + travelTime[i][next];
            int newTravelTime;
            if (mStartTimeNext < mLocates.get(next).getmOpen() || (mStartTimeNext + mLocates.get(next).getmStay()) > mLocates.get(next).getmClose() ) {
                continue;
            } else {
                mTest.append(" " + i);
                Log.d("CONTINUE", " STARTNODE: " + i + " STATE: " + state + " NEXT: " + next + " " + minTravelTime);


                newTravelTime = mStartTimeNext + tsp(next, nextState,  prev, (mStartTimeNext + mLocates.get(next).getmStay()));
            }

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

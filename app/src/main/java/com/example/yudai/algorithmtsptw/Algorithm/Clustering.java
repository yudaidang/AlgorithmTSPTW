package com.example.yudai.algorithmtsptw.Algorithm;

import android.util.Log;

import com.example.yudai.algorithmtsptw.Locate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Clustering {
    ArrayList<TimeWindow> listGroup = new ArrayList<>();
    private Integer[][] travelTime;

    private ArrayList<Locate> mLocates;
    private DateTravel mNumberDateTravel;
    private LinkedHashMap<TimeWindow, HashSet<Locate>> listTimeGroupClone = new LinkedHashMap<>();
    private HashMap<Integer, HashSet<Locate>> listDate = new HashMap<>();

    public Clustering(Integer[][] travelTime, ArrayList<Locate> mLocates, DateTravel mNumberDateTravel) {
        this.travelTime = travelTime;
        this.mLocates = mLocates;
        this.mNumberDateTravel = mNumberDateTravel;

        setUpGroup();
        ImplementClustering();
    }

    public void setUpGroup() {
        int mStartTemp = 540;
        while (mStartTemp < 1440) {
            int temp = mStartTemp + 120;
            listTimeGroupClone.put(new TimeWindow(mStartTemp, temp > 1440 ? 1440 : temp), new HashSet<Locate>());
            mStartTemp += 120;
        }
        for (int i = 0; i < mLocates.size(); i++) {
            for (TimeWindow time : listTimeGroupClone.keySet()) {
                if (mLocates.get(i).getmOpen() < time.getmOpen() && mLocates.get(i).getmClose() > time.getmClose()) {
                    HashSet<Locate> list;
                    if (listTimeGroupClone.get(time) != null) {
                        list = listTimeGroupClone.get(time);
                    } else {
                        list = new HashSet<>();
                    }
                    list.add(mLocates.get(i));
                    listTimeGroupClone.put(time, list);
                }
                if (mLocates.get(i).getmClose() < time.getmClose()) {
                    break;
                }
           }
        }
        Log.d("YUDAIDAG", "");
    }

    public void ImplementClustering() {
        int state = 1 << 0;

        for (int i = 0; i < mNumberDateTravel.getList().size(); i++) {
            int j = 0;
            HashSet<Locate> list = new HashSet<>();
            Locate preLocate = new Locate("The Shells Resort ", "10.243979, 103.948455", 540, 1440, 0, 0);
            int mLimitTimeTravel = mNumberDateTravel.getList().get(i).getmClose() - mNumberDateTravel.getList().get(i).getmOpen();

            int mTotalTravelTimeDate = 0;

            for (TimeWindow timeWindow : listTimeGroupClone.keySet()) {
                int preTravel = Integer.MAX_VALUE;
                Iterator<Locate> iterator = listTimeGroupClone.get(timeWindow).iterator();
                while (iterator.hasNext()) {
                    if ((state & (1 << iterator.next().getmId())) != 0) {
                        iterator.remove();
                        continue;
                    }

                    if (j == 0) {
                        preLocate = iterator.next();
                        iterator.remove();
                        state = state | (1 << iterator.next().getmId());
                        preTravel = travelTime[preLocate.getmId()][iterator.next().getmId()];
                        break;
                    } else {
                        int k = iterator.next().getmId();
                        if (travelTime[preLocate.getmId()][k] < preTravel) {
                            preLocate = iterator.next();
                            preTravel = travelTime[preLocate.getmId()][iterator.next().getmId()];
                            state = state | (1 << iterator.next().getmId());
                        }
                    }
                }
                list.add(preLocate);
                mTotalTravelTimeDate += preTravel;
                j++;
                if (mTotalTravelTimeDate > 0.8 * mLimitTimeTravel) {
                    break;
                }
            }
            listDate.put(i, list);

        }
    }

}

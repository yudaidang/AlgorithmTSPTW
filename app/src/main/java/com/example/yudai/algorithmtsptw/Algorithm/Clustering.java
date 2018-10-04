package com.example.yudai.algorithmtsptw.Algorithm;

import com.example.yudai.algorithmtsptw.Locate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Clustering {
    ArrayList<TimeWindow> listGroup = new ArrayList<>();
    private Integer[][] travelTime;

    private ArrayList<Locate> mLocates;
    private DateTravel mNumberDateTravel;
    private HashMap<TimeWindow, HashSet<Locate>> listTimeGroup = new HashMap<>();


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
            listGroup.add(new TimeWindow(mStartTemp, temp> 1440 ? 1440 : temp));
            mStartTemp += 120;
        }

        for (int i = 0; i < mLocates.size(); i++) {
            for (int j = 0; j < listGroup.size(); j++) {
                if (mLocates.get(i).getmOpen() < listGroup.get(j).getmOpen() && mLocates.get(i).getmClose() > listGroup.get(j).getmClose()) {
                    HashSet<Locate> list;
                    if (listTimeGroup.containsKey(listGroup.get(j))) {
                        list = listTimeGroup.get(listGroup.get(j));
                    } else {
                        list = new HashSet<>();
                    }
                    list.add(mLocates.get(i));
                    listTimeGroup.put(listGroup.get(j), list);
                }
                if (mLocates.get(i).getmClose() < listGroup.get(j).getmClose()) {
                    break;
                }
            }
        }
    }

    public void ImplementClustering() {
        int state = 1 << 0;

        for (int i = 0; i < mNumberDateTravel.getList().size(); i++) {
            int j = 0;
            Locate preLocate = new Locate("The Shells Resort ", "10.243979, 103.948455", 540, 1440, 0, 0);
            int mLimitTimeTravel = mNumberDateTravel.getList().get(i).getmClose() - mNumberDateTravel.getList().get(i).getmOpen();
            for (TimeWindow timeWindow : listTimeGroup.keySet()) {
                int preTravel = Integer.MAX_VALUE;
                Iterator<Locate> iterator = listTimeGroup.get(timeWindow).iterator();
                int mTotalTravelTimeDate = 0;
                while (iterator.hasNext()) {
                    if ((state & (1 << iterator.next().getmId())) != 0) {
                        iterator.remove();
                        continue;
                    }

                    if (j == 0) {
                        mTotalTravelTimeDate += travelTime[preLocate.getmId()][iterator.next().getmId()];
                        preLocate = iterator.next();
                        iterator.remove();
                        state = state | (1 << iterator.next().getmId());
                        break;
                    } else {
                        if (travelTime[preLocate.getmId()][iterator.next().getmId()] < preTravel) {
                            preLocate = iterator.next();
                            preTravel = travelTime[preLocate.getmId()][iterator.next().getmId()];
                            state = state | (1 << iterator.next().getmId());
                        }
                    }
                }
                listTimeGroup.get(timeWindow).remove(preLocate);
                mTotalTravelTimeDate += preTravel;
                j++;
                if (mTotalTravelTimeDate > 0.8 * mLimitTimeTravel) {
                    break;
                }
            }

        }
    }

}

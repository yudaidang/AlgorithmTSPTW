package com.example.yudai.algorithmtsptw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.yudai.algorithmtsptw.Algorithm.Clustering;
import com.example.yudai.algorithmtsptw.Algorithm.DateTravel;
import com.example.yudai.algorithmtsptw.Algorithm.DynamicProgrammingTSPTW;
import com.example.yudai.algorithmtsptw.Algorithm.Haversine;
import com.example.yudai.algorithmtsptw.Algorithm.TimeWindow;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private int n = 5;
    private Haversine haversine = new Haversine();
    private ArrayList<ArcLocate> listArc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Integer[][] travelTime = new Integer[n][n];
        travelTime[1][0] = travelTime[0][1] = 28;
        travelTime[2][0] = travelTime[0][2] = 55;
        travelTime[3][0] = travelTime[0][3] = 7;
        travelTime[4][0] = travelTime[0][4] = 31;
//        travelTime[5][0] = travelTime[0][5] = 18;
//        travelTime[6][0] = travelTime[0][6] = 44;

        travelTime[1][2] = travelTime[2][1] = 83;
        travelTime[1][3] = travelTime[3][1] = 36;
        travelTime[1][4] = travelTime[4][1] = 58;
//        travelTime[1][5] = travelTime[5][1] = 41;
//        travelTime[1][6] = travelTime[6][1] = 69;

        travelTime[2][3] = travelTime[3][2] = 48;
        travelTime[2][4] = travelTime[4][2] = 36;
//        travelTime[2][5] = travelTime[5][2] = 33;
//        travelTime[2][6] = travelTime[6][2] = 8;

        travelTime[3][4] = travelTime[4][3] = 25;
//        travelTime[3][5] = travelTime[5][3] = 3;
//        travelTime[3][6] = travelTime[6][3] = 37;

//        travelTime[4][5] = travelTime[5][4] = 27;
//        travelTime[4][6] = travelTime[6][4] = 32;
//
//        travelTime[5][6] = travelTime[6][5] = 36;


/*        Integer[][] distance = new Integer[n][n];
        distance[1][0] = distance[0][1] = 18;
        distance[2][0] = distance[0][2] = 35;
        distance[3][0] = distance[0][3] = 5;
        distance[4][0] = distance[0][4] = 20;
        distance[1][2] = distance[2][1] = 53;
        distance[1][3] = distance[3][1] = 23;
        distance[1][4] = distance[4][1] = 37;
        distance[2][3] = distance[3][2] = 31;
        distance[2][4] = distance[4][2] = 23;
        distance[3][4] = distance[4][3] = 16;*/

        ArrayList<Locate> mLocates = new ArrayList<>();


        //**
        mLocates.add(new Locate("The Shells Resort ", "10.243979, 103.948455", 540, 1440, 0, 0));
        mLocates.add(new Locate("Vinpearl Safari Phú Quốc 1", "10.337086, 103.891322 ", 540, 990, 180, 1));
        mLocates.add(new Locate("Di tích Nhà tù Phú Quốc 2", "10.043501, 104.018480  ", 480, 1020, 90, 2));
        mLocates.add(new Locate("Chợ đêm Phú Quốc 3", "10.218282, 103.960214  ", 990, 1440, 180, 3));
        mLocates.add(new Locate("Làng chài Hàm Ninh 4", "10.179362, 104.049026  ", 240, 1320, 120, 4));
        mLocates.add(new Locate("Dinh Cậu 5", "10.217232, 103.956423  ", 540, 1200, 60, 5));
        mLocates.add(new Locate("Bãi Sao 6", "10.055666, 104.035671  ", 540, 1440, 120, 6));
        mLocates.add(new Locate("Xưởng nước mắm Phụng Hưng 7", "10.044376, 104.016972", 540, 1140, 60, 7));
        mLocates.add(new Locate("Bãi Khem 8", "10.033389, 104.030324", 540, 1140, 120, 8));
        mLocates.add(new Locate("Chợ Đông Dương 9", "10.221933, 103.957209", 540, 1140, 90, 9));
        mLocates.add(new Locate("Trung tâm bảo tồn chó xoáy 10", "10.178127, 104.008562", 540, 1080, 60, 10));
        mLocates.add(new Locate("Ngọc Trai Ngọc Hiền 11", "10.207466, 103.962319", 540, 1260, 90, 11));
        mLocates.add(new Locate("Vườn tiêu Ngọc Hà 12", "10.210645, 103.984373", 540, 1140, 60, 12));
        mLocates.add(new Locate("Làng Chài Rạch Vẹm 13", "10.365665, 103.932144", 540, 1140, 90, 13));

        long time = System.currentTimeMillis();


        DynamicProgrammingTSPTW dynamicProgrammingTSPTW = new DynamicProgrammingTSPTW(travelTime, 0, mLocates);
        Log.d("YUYUUYYYUYUYYUYU1", dynamicProgrammingTSPTW.getTour() + " ");
        Log.d("YUYUUYYYUYUYYUYU2", dynamicProgrammingTSPTW.getTourTravelTime() + " " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time));

        ArrayList<TimeWindow> list = new ArrayList<>();
        list.add(new TimeWindow(540, 1320));
        list.add(new TimeWindow(540, 1320));
        DateTravel date = new DateTravel(2, list);
        Clustering clustering = new Clustering(createTableTravelTime(mLocates), mLocates, date);

        TSPTW tsptw = new TSPTW();
       /* MyCodeHihi myCodeHihi = new MyCodeHihi(travelTime, 0, mLocates, distance);
        HashMap<Integer, HashSet<Object>> list = myCodeHihi.result();
        for(Integer key  : list.keySet()){
            for(Object object: list.get(key)){
                Log.d("YUYUYUYUYUYU", object.getmI() + " " + object.getmCost() + " " + object.getmS() + " " + object.getmT() + " " + key);
            }
        }*/
    }

    public Integer[][] createTableTravelTime(ArrayList<Locate> list) {
        int mNumber = list.size();
        Integer[][] mArray = new Integer[mNumber][mNumber];
        for (int m = 0; m < mNumber; m++) {
            for (int n = 0; n < mNumber; n++) {
                Integer travel = (int) Math.round(haversine.travelTime(list.get(m).getmCoordinate(), list.get(n).getmCoordinate()));
                mArray[m][n] = travel;
                if (m != n && (list.get(m).getmOpen() + list.get(m).getmStay() + travel) <= list.get(n).getmClose()) {
                    listArc.add(new ArcLocate(list.get(m), list.get(n)));
                }
            }
        }
        return mArray;
    }
}

package com.example.yudai.algorithmtsptw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yudai.algorithmtsptw.Algorithm.DynamicProgrammingTSPTW;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int n = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Integer[][] travelTime = new Integer[n][n];
        travelTime[1][0] = travelTime[0][1] = 28;
        travelTime[2][0] = travelTime[0][2] = 55;
        travelTime[3][0] = travelTime[0][3] = 7;
        travelTime[4][0] = travelTime[0][4] = 31;
        travelTime[1][2] = travelTime[2][1] = 83;
        travelTime[1][3] = travelTime[3][1] = 36;
        travelTime[1][4] = travelTime[4][1] = 58;
        travelTime[2][3] = travelTime[3][2] = 48;
        travelTime[2][4] = travelTime[4][2] = 36;
        travelTime[3][4] = travelTime[4][3] = 25;

        ArrayList<Locate> mLocates = new ArrayList<>();


        //**
        mLocates.add(new Locate("The Shells Resort ", "10.243979, 103.948455", 540,990,180));
        mLocates.add(new Locate("Vinpearl Safari Phú Quốc 1", "10.337086, 103.891322 ", 540,990,180));
        mLocates.add(new Locate("Di tích Nhà tù Phú Quốc 2", "10.043501, 104.018480  ",480,690,90));
        mLocates.add(new Locate("Chợ đêm Phú Quốc 3", "10.218282, 103.960214  ",990,1440,180));
        mLocates.add(new Locate("Làng chài Hàm Ninh 4", "10.179362, 104.049026  ",240,1320,120));

        DynamicProgrammingTSPTW dynamicProgrammingTSPTW = new DynamicProgrammingTSPTW(travelTime, 0, mLocates);

    }
}

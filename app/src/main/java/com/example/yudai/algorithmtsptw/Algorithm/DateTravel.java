package com.example.yudai.algorithmtsptw.Algorithm;

import java.util.ArrayList;

public class DateTravel {
    private int mNumberDate;
    private ArrayList<TimeWindow> list;

    public DateTravel(int mNumberDate, ArrayList<TimeWindow> list) {
        this.mNumberDate = mNumberDate;
        this.list = list;
    }

    public int getmNumberDate() {
        return mNumberDate;
    }

    public void setmNumberDate(int mNumberDate) {
        this.mNumberDate = mNumberDate;
    }

    public ArrayList<TimeWindow> getList() {
        return list;
    }

    public void setList(ArrayList<TimeWindow> list) {
        this.list = list;
    }
}

package com.example.yudai.algorithmtsptw;

public class Locate {
    private String mName;
    private String mCoordinate;
    private int mStay;
    private int mOpen;
    private int mClose;

    public Locate(String mName, String mCoordinate, int mOpen, int mClose, int mStay) {
        this.mName = mName;
        this.mCoordinate = mCoordinate;
        this.mStay = mStay;
        this.mClose = mClose;
        this.mOpen = mOpen;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCoordinate() {
        return mCoordinate;
    }

    public void setmCoordinate(String mCoordinate) {
        this.mCoordinate = mCoordinate;
    }

    public int getmStay() {
        return mStay;
    }

    public void setmStay(int mStay) {
        this.mStay = mStay;
    }

    public int getmOpen() {
        return mOpen;
    }

    public void setmOpen(int mOpen) {
        this.mOpen = mOpen;
    }

    public int getmClose() {
        return mClose;
    }

    public void setmClose(int mClose) {
        this.mClose = mClose;
    }
}

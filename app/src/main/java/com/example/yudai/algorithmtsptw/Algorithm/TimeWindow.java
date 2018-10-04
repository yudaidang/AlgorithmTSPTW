package com.example.yudai.algorithmtsptw.Algorithm;

public class TimeWindow {
    private int mOpen;
    private int mClose;

    public TimeWindow(int mOpen, int mClose) {
        this.mOpen = mOpen;
        this.mClose = mClose;
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

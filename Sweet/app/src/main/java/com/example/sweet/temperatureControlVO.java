package com.example.sweet;


public class temperatureControlVO {
    private int houseID;
    private double threshold;
    private boolean fanState;


    public int getHouseID() {
        return this.houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }


    public double getThreshold() {
        return this.getThreshold();
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public boolean isFanState() {
        return this.fanState;
    }

    public void setFanState(boolean fanState) {
        this.fanState = fanState;
    }

}

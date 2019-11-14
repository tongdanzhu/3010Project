package com.example.sweet;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class temperatureVO {
    private int houseID;
    private double threshold;
    private double currTemp;
    private String currTime;
    private String currDate;
    private boolean fanState;
    Calendar calendar = Calendar.getInstance();


    public int getHouseID(){
        return this.houseID;
    }
    public void setHouseID(int houseID){
        this.houseID=houseID;}


    public double getThreshold(){
        return this.getThreshold();
    }
    public void setThreshold(double threshold){
        this.threshold=threshold;}


    public double getCurrTemp(){
        return this.getCurrTemp();
    }
    public void setCurrTemp(double currTemp){
        this.currTemp=currTemp;}


    public String getCurrTime(){
        return this.currTime;}

    public void setCurrTime(){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        this.currTime= hour+":"+minute+":"+second;

    }

    public String getCurrDate(){
        return this.currDate;
    }

    public void setCurrDate(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        this.currDate = year+"."+month+"."+day;
    }

}

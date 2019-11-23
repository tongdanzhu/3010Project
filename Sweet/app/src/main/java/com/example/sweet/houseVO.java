package com.example.sweet;

public class houseVO {
    private int houseID;
    private String houseAddress;
    private boolean mailboxState;
    private boolean doorbellState;
    private double currDarkness;

    public void setHouseID(int houseID)
    {
        this.houseID=houseID;
    }
    public int getHouseID(){
        return this.houseID;
    }
    public void setHouseAddress(String houseAddress){
        this.houseAddress=houseAddress;
    }
    public String getHouseAddress(){
        return this.getHouseAddress();
    }
    public void setMailboxState(boolean mailboxState){
        this.mailboxState=mailboxState;
    }
    public boolean isMailboxState(){
        return this.mailboxState;
    }
    public void setDoorbellState(boolean doorbellState){
        this.doorbellState=doorbellState;
    }
    public boolean isDoorbellState(){
        return this.doorbellState;
    }
    public void setCurrDarkness(double currDarkness){
        this.currDarkness=currDarkness;
    }
    public double getCurrDarkness(){
        return this.currDarkness;
    }

}

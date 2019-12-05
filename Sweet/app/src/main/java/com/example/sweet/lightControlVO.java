package com.example.sweet;

public class lightControlVO {
    private int houseID;
    private boolean manualControl;
    private boolean switches;


    public int getHouseID() {
        return this.houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }


    public void setManualControl(boolean manualControl){
        this.manualControl=manualControl;
    }

    public boolean isManualControl(){
        return this.manualControl;
    }

    public void setSwitches(boolean switches){
        this.switches=switches;
    }
    public boolean isSwitches(){
        return this.switches;
    }

}

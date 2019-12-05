package com.example.sweet;
/*
 * Construction of a house.
 */
public class houseVO {
    private int houseID;
    private String password;
    private boolean mailboxState;

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public int getHouseID() {
        return this.houseID;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return this.password;
    }


    public void setMailboxState(boolean mailboxState) {
        this.mailboxState = mailboxState;
    }

    public boolean isMailboxState() {
        return this.mailboxState;
    }


}

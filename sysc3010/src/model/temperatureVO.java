package model;

public class temperatureVO {
	private int dataID;
	private int roomID;
	private String currDate;
	private String currTime;
	private int currTemp;
	private boolean fanState;
	public int getDataID() {
		return dataID;
	}
	public void setDataID(int dataID) {
		this.dataID = dataID;
	}
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public String getCurrDate() {
		return currDate;
	}
	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}
	public String getCurrTime() {
		return currTime;
	}
	public void setCurrTime(String currTime) {
		this.currTime = currTime;
	}
	public int getCurrTemp() {
		return currTemp;
	}
	public void setCurrTemp(int currTemp) {
		this.currTemp = currTemp;
	}
	public boolean isFanState() {
		return fanState;
	}
	public void setFanState(boolean fanState) {
		this.fanState = fanState;
	}
	

}

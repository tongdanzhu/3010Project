package model;

public class temperatureVO {
	private int houseID;
	private double threshold;
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	private String currDate;
	private String currTime;
	private double currTemp;
	private boolean fanState;
	
	public int getHouseID() {
		return houseID;
	}
	public void setHouseID(int houseID) {
		this.houseID = houseID;
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
	public double getCurrTemp() {
		return currTemp;
	}
	public void setCurrTemp(double currTemp) {
		this.currTemp = currTemp;
	}
	public boolean isFanState() {
		return fanState;
	}
	public void setFanState(boolean fanState) {
		this.fanState = fanState;
	}
	

}

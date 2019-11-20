package model;

public class visitorVO {
	private int visitorID;
	private int houseID;
	private String currDate;
	private String currTime;
	private boolean confirm;
	public int getVisitorID() {
		return visitorID;
	}
	public void setVisitorID(int visitorID) {
		this.visitorID = visitorID;
	}
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
	public boolean isConfirm() {
		return confirm;
	}
	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	

}

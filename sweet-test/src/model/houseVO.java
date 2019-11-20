package model;

public class houseVO {
	private int houseID;
	private String houseAddress;
	private boolean mailboxState;
	private boolean doorbellState;
	private double currDarkness;
	public int getHouseID() {
		return houseID;
	}
	public void setHouseID(int houseID) {
		this.houseID = houseID;
	}
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public boolean isMailboxState() {
		return mailboxState;
	}
	public void setMailboxState(boolean mailboxState) {
		this.mailboxState = mailboxState;
	}
	public boolean isDoorbellState() {
		return doorbellState;
	}
	public void setDoorbellState(boolean doorbellState) {
		this.doorbellState = doorbellState;
	}
	public double getCurrDarkness() {
		return currDarkness;
	}
	public void setCurrDarkness(double currDarkness) {
		this.currDarkness = currDarkness;
	}

}


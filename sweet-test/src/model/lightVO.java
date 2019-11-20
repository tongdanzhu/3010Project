package model;

public class lightVO {
	private int lightID;	
	private int houseID;
	private boolean lightState;
	private boolean manualControl;
	private boolean controlSwitch;
	
	public int getLightID() {
		return lightID;
	}
	public void setLightID(int lightID) {
		this.lightID = lightID;
	}
	public int getHouseID() {
		return houseID;
	}
	public void setHouseID(int houseID) {
		this.houseID = houseID;
	}
	public boolean isLightState() {
		return lightState;
	}
	public void setLightState(boolean lightState) {
		this.lightState = lightState;
	}
	public boolean isManualControl() {
		return manualControl;
	}
	public void setManualControl(boolean manualControl) {
		this.manualControl = manualControl;
	}
	public boolean isControlSwitch() {
		return controlSwitch;
	}
	public void setControlSwitch(boolean controlSwitch) {
		this.controlSwitch = controlSwitch;
	}

	
	

}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test_cases {

	// check whether the house id exists in database
	private static final String USER_EXIST = "SELECT * FROM user WHERE houseID=?";
	// check the houseid and password are correct
	private static final String USER_VALIDATION = "SELECT * FROM user WHERE houseID=? & password=?";
	// check whether the threshold exist
	private static final String THRESHOLD_EXIST = "SELECT threshold FROM temperature WHERE houseID=?";
	// update threshold
	private static final String UPDATE_THRESHOLD = "UPDATE temperature SET threshold =? WHERE houseID =?";
	// get current light state
	private static final String GET_LIGHT_STATE = "SELECT lightState FROM light WHERE houseID =?";
	// update manual control
	private static final String UPDATE_MANUAL_CONTROL = "UPDATE light SET manualControl=? WHERE houseID=?";
	// update light state
	private static final String UPDATE_LIGHT_STATE = "UPDATE light SET lightState =? WHERE houseID=?";
	// get current mailbox state
	private static final String GET_MAILBOX_STATE = "SELECT mailboxState FROM house WHERE houseID=?";
	// update mailbox confirm
	private static final String UPDATE_MAILBOX_CONFIRM = "UPDATE house SET mailboxState=? WHERE houseID=?;";
	// get current visitor
	private static final String GET_NEW_VISITORS = "SELECT * FROM visitor WHERE confirm=? and houseID=?";
	// update doorbell confirm
	private static final String UPDATE_DOORBELL_STATE = "UPDATE house SET doorbellState=? WHERE houseID=?";
	private static final String UPDATE_VISITOR = "UPDATE visitor SET confirm =1 WHERE visitorID =? and houseID=? and confirm =0";
	// list history visitors
	private static final String GET_ALL_VISITORS = "SELECT * FROM visitor WHERE houseID=?";
	
	// initial connection
	private static Connection conn = MyConnection.getConnect();

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		System.out.println("house id exist: " + userExist(1));
		System.out.println("password correct: " + validUser(1, "1"));
		System.out.println("password incorrect: " + validUser(1, "2"));
		System.out.println("threshold exists: " + isThresholdExist(1));
		System.out.println("threshold not exists: " + isThresholdExist(2));
		System.out.println("update threshold: " + updateThreshold(1, 20.1));
		System.out.println("update threshold: " + updateThreshold(2, 20.1));
		System.out.println("current light state: " + currLightState(1));
		System.out.println("current light state: " + currLightState(2));
		System.out.println("update manual control: " + updateManualControl(1, true));
		System.out.println("update manual control: " + updateManualControl(1, false));
		System.out.println("update manual control: " + updateManualControl(2, true));
		System.out.println("update light state: " + updateLightState(1, true));
		System.out.println("get mailbox state: " + getMailboxState(1));
		System.out.println("update mailbox confirm: " + updateMailboxConfirm(1));
		List<visitorVO> newVisitors = new ArrayList<visitorVO>();
		newVisitors = getNewVisitors(1);
		for (int i = 0; i < newVisitors.size(); i++) {
			System.out
					.println("get new visitors: " + newVisitors.get(i).getVisitorID() + " " + newVisitors.get(i).getHouseID()
							+ " " + newVisitors.get(i).getCurrDate() + " " + newVisitors.get(i).getCurrTime());
		}

		System.out.println("update doorbell state: " + updateDoorbellState(1));
		System.out.println("update visitor confirm: " + updateVisitorConfirm(1, 2));
		List<visitorVO> allVisitors = new ArrayList<visitorVO>();
		allVisitors = getAllVisitors(1);
		for (int i = 0; i < allVisitors.size(); i++) {
			System.out
					.println("get all visitors: " + allVisitors.get(i).getVisitorID() + " " + allVisitors.get(i).getHouseID()
							+ " " + allVisitors.get(i).getCurrDate() + " " + allVisitors.get(i).getCurrTime()+" "+allVisitors.get(i).isConfirm());
		}
	}

	// check whether the house id exists in database
	public static boolean userExist(int houseID) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(USER_EXIST);
		pstmt.setInt(1, houseID);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	// check the houseid and password are correct
	public static boolean validUser(int houseID, String password) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(USER_VALIDATION);
		pstmt.setInt(1, houseID);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}

		return false;
	}

	// whether the threshold exist
	public static boolean isThresholdExist(int houseID) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(THRESHOLD_EXIST);
		pstmt.setInt(1, houseID);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	// update the threshold
	public static int updateThreshold(int houseID, double threshold) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_THRESHOLD);
		pstmt.setDouble(1, threshold);
		pstmt.setInt(2, houseID);
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	// get current light state
	public static boolean currLightState(int houseID) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(GET_LIGHT_STATE);
		pstmt.setInt(1, houseID);
		ResultSet rs = pstmt.executeQuery();
		lightVO light = new lightVO();
		while (rs.next()) {
			light.setLightState(rs.getBoolean("lightState"));
		}
		if (light.isLightState()) {
			return true;
		} else {
			return false;
		}
	}

	// update manual control
	public static int updateManualControl(int houseID, boolean manualControl) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_MANUAL_CONTROL);
		pstmt.setBoolean(1, manualControl);
		pstmt.setInt(2, houseID);
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	// update light state
	public static int updateLightState(int houseID, boolean lightState) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_LIGHT_STATE);
		pstmt.setBoolean(1, lightState);
		pstmt.setInt(2, houseID);
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	// get current mailbox state
	public static boolean getMailboxState(int houseID) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(GET_MAILBOX_STATE);
		pstmt.setInt(1, houseID);
		ResultSet rs = pstmt.executeQuery();
		houseVO house = new houseVO();
		while (rs.next()) {
			house.setMailboxState(rs.getBoolean("mailboxState"));
		}
		if (house.isMailboxState()) {
			return true;
		} else {
			return false;
		}

	}

	// update mailbox confirm
	public static int updateMailboxConfirm(int houseID) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_MAILBOX_CONFIRM);
		pstmt.setBoolean(1, true);
		pstmt.setInt(2, houseID);
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	// get new visitor
	public static List<visitorVO> getNewVisitors(int houseID) throws SQLException {
		visitorVO visitor = null;
		List<visitorVO> visitors = new ArrayList<visitorVO>();
		PreparedStatement pstmt = conn.prepareStatement(GET_NEW_VISITORS);
		pstmt.setBoolean(1, false);
		pstmt.setInt(2, houseID);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			visitor = new visitorVO();
			visitor.setVisitorID(rs.getInt("visitorID"));
			visitor.setHouseID(rs.getInt("houseID"));
			visitor.setCurrDate(rs.getString("currDate"));
			visitor.setCurrTime(rs.getString("currTime"));
			visitor.setConfirm(rs.getBoolean("confirm"));

			visitors.add(visitor);
		}
		return visitors;
	}

	// update doorbell confirm
	public static int updateDoorbellState(int houseID) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_DOORBELL_STATE);
		pstmt.setBoolean(1, false);
		pstmt.setInt(2, houseID);
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	//update user confirm of new visitors
	public static int updateVisitorConfirm(int houseID, int visitorID) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_VISITOR);
		pstmt.setInt(1, visitorID);
		pstmt.setInt(2, houseID);
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}
	
	// list history visitors
	public static List<visitorVO> getAllVisitors(int houseID) throws SQLException {
		visitorVO visitor = null;
		List<visitorVO> visitors = new ArrayList<visitorVO>();
		PreparedStatement pstmt = conn.prepareStatement(GET_ALL_VISITORS);
		pstmt.setInt(1, houseID);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			visitor = new visitorVO();
			visitor.setVisitorID(rs.getInt("visitorID"));
			visitor.setHouseID(rs.getInt("houseID"));
			visitor.setCurrDate(rs.getString("currDate"));
			visitor.setCurrTime(rs.getString("currTime"));
			visitor.setConfirm(rs.getBoolean("confirm"));

			visitors.add(visitor);
		}
		return visitors;
	}

}

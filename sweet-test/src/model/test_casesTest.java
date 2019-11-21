package model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class test_casesTest {
	/*
	 * System.out.println("update manual control: " + updateManualControl(1, true));
	 * System.out.println("update manual control: " + updateManualControl(1, false)); 
	 * System.out.println("update manual control: " +updateManualControl(2, true)); 
	 * System.out.println("update light state: " + updateLightState(1, true)); 
	 * System.out.println("get mailbox state: " + getMailboxState(1)); 
	 * System.out.println("update mailbox confirm: " + updateMailboxConfirm(1)); 
	 * System.out.println("update doorbell state: " + updateDoorbellState(1));
	 * System.out.println("update visitor confirm: " + updateVisitorConfirm(1, 2));
	 * 
	 */
	@Test
	// test case when the house_id is exist in database
	void testUserExist() {
		test_cases test = new test_cases();
		int houseID = 1;
		boolean exist = false;
		try {
			exist = test.userExist(houseID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, exist);
	}

	@Test
	// test case when the house_id is not exist in database
	void testUserNotExist() {
		test_cases test = new test_cases();
		int houseID = 2;
		boolean rs = false;
		try {
			rs = test.userExist(houseID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(false, rs);
	}

	@Test
	// test case when the password is correct
	void testPasswordCorrect() {
		test_cases test = new test_cases();
		int houseID = 1;
		String password = "1";
		boolean re = false;
		try {
			re = test.validUser(houseID, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, re);
	}

	@Test
	// test case when the password is not correct
	void testPasswordNotCorrect() {
		test_cases test = new test_cases();
		int houseID = 1;
		String password = "2";
		boolean re = false;
		try {
			re = test.validUser(houseID, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(false, re);
	}

	@Test
	// test case when the threshold is exist where the house_id is given
	void testIsThresholdExist() {
		test_cases test = new test_cases();
		int houseID = 1;
		boolean re = false;
		try {
			re = test.isThresholdExist(houseID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, re);
	}

	@Test
	// test case when the threshold is not exist where the house_id is given
	void testIsThresholdNotExist() {
		test_cases test = new test_cases();
		int houseID = 353;
		boolean re = false;
		try {
			re = test.isThresholdExist(houseID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(false, re);
	}

	@Test
	// test case when the threshold is updated successfully
	void testUpdateThresholdSuccessful() {
		test_cases test = new test_cases();
		int houseID = 1;
		double threshold = 20.1;
		int re = 0;
		try {
			re = test.updateThreshold(houseID, threshold);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, re);

	}

	@Test
	// test case when the threshold is not updated successfully
	void testUpdateThresholdNotSuccessful() {
		test_cases test = new test_cases();
		int houseID = 2;
		double threshold = 20.1;
		int re = 0;
		try {
			re = test.updateThreshold(houseID, threshold);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, re);

	}

	// System.out.println("current light state: " + currLightState(1));
	// * System.out.println("current light state: " + currLightState(2));

}

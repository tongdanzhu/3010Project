package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





public class test {

	private static final String tem="SELECT * FROM temperature ";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn=MyConnection.getConnect();
		try {
			PreparedStatement pstmt=conn.prepareStatement(tem);
			ResultSet rs=pstmt.executeQuery();
			temperatureVO templist=null;
			while(rs.next())
			{
				templist=new temperatureVO();
				templist.setRoomID(rs.getInt("roomID"));
				templist.setCurrDate(rs.getString("currDate"));
				templist.setCurrTime(rs.getString("currTime"));
				System.out.print(templist.getRoomID()+" "+templist.getCurrDate()+" "+templist.getCurrTime()+"\n");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return;

}
}

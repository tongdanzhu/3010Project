package model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.*;
import java.sql.*;

public class MyConnection {
private static Connection conn=null;
	
	
	public static Connection getConnect()
	{
	
		if (conn==null)
		{ try{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/sysc3010?useSSL=false";
			String user="root";
			String password="sysc3010!";
			conn=DriverManager.getConnection(url, user, password);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		}


	return conn;		
	}
	
}
	
	


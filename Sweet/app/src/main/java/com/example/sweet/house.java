package com.example.sweet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class house extends AppCompatActivity {

    private EditText et_house_id;
    private EditText et_password;
    private Button bt_enter_house_id;

    private static final String USER_VALIDATION = "SELECT * FROM user WHERE houseID=? & password=?";
    // check whether the house id exists in database
    private static final String USER_EXIST = "SELECT * FROM user WHERE houseID=?";
    private static Connection conn = MyConnection.getConnect();
    //private static Connection conn=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house);

        /*new Thread(new Runnable() {//访问MySQL要开一个新线程
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/sysc3010?useSSL=false";
                    String user = "root";
                    String password = "sysc3010!";
                    conn = DriverManager.getConnection(url, user, password);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });*/

        bt_enter_house_id = (Button) findViewById(R.id.bt_enter_house_id);
        et_house_id = (EditText) findViewById(R.id.et_house_id);
        et_password = (EditText) findViewById(R.id.et_password);


        et_house_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bt_enter_house_id.setEnabled(Boolean.FALSE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_enter_house_id.setEnabled(Boolean.FALSE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_house_id.getText())) {
                    bt_enter_house_id.setEnabled(Boolean.FALSE);//disable the button if where is no input
                    return;
                } else {
                    bt_enter_house_id.setEnabled(Boolean.TRUE);//enable the button if where is input
                }
            }
        });

        //button listener for house id
        bt_enter_house_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (houseIDisDigital(et_house_id.getText().toString()))//if the house id is digital type
                {
                    toast("is digital");
                    try {
                        if (userExist(houseIDtoDigital(et_house_id.getText().toString()))) {//if the house id exists

                            Intent intent = new Intent(house.this, house_activity.class); // go to next page
                            intent.putExtra("house_id", et_house_id.getText().toString()); // transfer the house_id to next page

                            startActivity(intent);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    toast("house id is invalid");
                }

            }
        });


    }

    /*
     *  whether the house id is a digital
     *  input: string from edit text
     *  output: return true if the house id is a digital
     *          reuturn false if the house id is arbitrary string
     */
    public boolean houseIDisDigital(String houseID) {
        for (int i = 0; i < houseID.length(); i++) {
            if (!Character.isDigit(houseID.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /*
     *  convert String houseID to int type
     */
    public int houseIDtoDigital(String houseID) {
        int id = Integer.parseInt(houseID);
        return id;
    }

    /*
     *  check whether the house_id exists in database
     *  input: houseID in int
     *  output: return true if the house id exists
     *          return false if the house id not exists
     */
    public boolean userExist(int houseID) throws SQLException {
        //Connection conn = MyConnection.getConnect();
        PreparedStatement pstmt = conn.prepareStatement(USER_EXIST);
        pstmt.setInt(1, houseID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    /*
     *   check whether the combination of house id and password is correct in database
     *   input: houseID in String; password in String
     *   output: return true if they are correct combination
     *           return false if they are incorrect
     * */
    public boolean password_validation(String houseID, String password) throws SQLException {
        //Connection conn = MyConnection.getConnect();
        PreparedStatement pstmt = conn.prepareStatement(USER_VALIDATION);
        pstmt.setInt(1, houseIDtoDigital(houseID));
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            rs.close();
            pstmt.close();
            return true;
        }
        return false;
    }

    /*
     *   display message on screen
     * */
    private void toast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }
}
package com.example.sweet;
/*
 * An user interface for loggin.
 * @author: Tongdan Zhu
 */
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;


public class house extends AppCompatActivity {

    private EditText et_house_id;
    private EditText et_password;
    private Button bt_enter_house_id;

    /* state code */
    private static final int PASSWORD_INCORRECT = 1;
    private static final int ID_NOT_EXIST = 2;

    /* message handler */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isValid;
            switch (msg.what) {
                case PASSWORD_INCORRECT:
                    toast(msg.obj.toString());
                    break;
                case ID_NOT_EXIST:
                    toast(msg.obj.toString());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house);

        /* element initialize */
        bt_enter_house_id = (Button) findViewById(R.id.bt_enter_house_id);
        et_house_id = (EditText) findViewById(R.id.et_house_id);
        et_password = (EditText) findViewById(R.id.et_password);


        /* edit text listener */
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

        /* button listener */
        bt_enter_house_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // new a thread to handle the database accessing
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyConnection conn = new MyConnection();
                        try {
                            // to check whether the house Id exists in the database
                            boolean userExist = conn.userExist(houseIDtoDigital(et_house_id.getText().toString()));
                            // to check whether the password is correct
                            boolean passwordCorrect = conn.password_validation(et_house_id.getText().toString(), et_password.getText().toString());
                            Message message = handler.obtainMessage();
                            if (userExist) {// if the house Id is valid
                                if (passwordCorrect) { // if the password is correct
                                    Intent intent = new Intent(house.this, house_activity.class);
                                    intent.putExtra("house_id", et_house_id.getText().toString());
                                    startActivity(intent);
                                } else { // if the password is wrong
                                    String s = "password not correct";
                                    message.what = PASSWORD_INCORRECT;
                                    message.obj = s;
                                }
                            } else { // if the house ID is not exist
                                String s = "house id not exist";
                                message.what = ID_NOT_EXIST;
                                message.obj = s;
                            }
                            handler.sendMessage(message);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


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
     *   display message on screen
     * */
    private void toast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }
}
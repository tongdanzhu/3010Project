package com.example.sweet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class light extends AppCompatActivity {

    private TextView tv_light_state;
    private Switch sw_manual_control;
    private lightControlVO light;
    private ToggleButton tbt_manual_control;
    private TextView tv_currHouse;

    private static final int MANUAL_CONTROL_ON = 1;
    private static final int MANUAL_CONTROL_OFF = 2;
    private static final int UPDATE_MANUAL_CONTROL_ON = 3;
    private static final int UPDATE_MANUAL_CONTROL_OFF = 4;
    private static final int UPDATE_MANUAL_CONTROL_FAILURE = 5;

    private String houseid;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MANUAL_CONTROL_ON:
                    toast(msg.obj.toString());
                    sw_manual_control.setChecked(Boolean.TRUE);
                    break;
                case MANUAL_CONTROL_OFF:
                    toast(msg.obj.toString());
                    sw_manual_control.setChecked(Boolean.FALSE);
                    break;
                case UPDATE_MANUAL_CONTROL_ON:
                    toast(msg.obj.toString());
                    tbt_manual_control.setEnabled(Boolean.TRUE);
                    break;
                case UPDATE_MANUAL_CONTROL_OFF:
                    toast(msg.obj.toString());
                    tbt_manual_control.setEnabled(Boolean.FALSE);
                    break;
                case UPDATE_MANUAL_CONTROL_FAILURE:
                    toast(msg.obj.toString());
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light);


        /*
            initial elements
         */
        //text view for current house
        tv_currHouse = (TextView) findViewById(R.id.tv_currHouse_light);
        //toggle button of turn on or off the light manually
        tbt_manual_control = (ToggleButton) findViewById(R.id.tbt_manual_control);
        //switch of manual controller of light
        sw_manual_control = (Switch) findViewById(R.id.sw_manual_control);


        //receive parameter from previous page
        Intent getIntent = getIntent();
        houseid = getIntent.getStringExtra("house_id");
        //display current house id
        tv_currHouse.setText(houseid);


        light = new lightControlVO();

        /*
            initial the state of manual control switch
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyConnection conn = new MyConnection();
                try {
                    Message message = handler.obtainMessage();

                    if (conn.getManualControl(Integer.parseInt(houseid))) {// if the manual control is on
                        String s = "Manual control is on";
                        message.what = MANUAL_CONTROL_ON;
                        message.obj = s;
                    } else { // if the manual control is off
                        String s = "Manual control is off";
                        message.what = MANUAL_CONTROL_OFF;
                        message.obj = s;
                    }

                    handler.sendMessage(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        /*
            update change on the switch (manual control)
         */
        sw_manual_control.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    toast("manual control is: " + isChecked);
                    // new a thread to update the manual control
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyConnection conn = new MyConnection();
                            try {
                                Message message = handler.obtainMessage();
                                if (conn.updateManualControl(Integer.parseInt(houseid),isChecked)==1) {// if the manual control is on
                                    String s = "Update manual control to "+isChecked;
                                    message.what = UPDATE_MANUAL_CONTROL_ON;
                                    message.obj = s;
                                } else { // if the manual control is off
                                    String s ="Update manual control failure";
                                    message.what = UPDATE_MANUAL_CONTROL_FAILURE;
                                    message.obj = s;
                                }

                                handler.sendMessage(message);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    tbt_manual_control.setEnabled(Boolean.TRUE);
                } else {
                    toast("manual control is: " + isChecked);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyConnection conn = new MyConnection();
                            try {
                                Message message = handler.obtainMessage();
                                if (conn.updateManualControl(Integer.parseInt(houseid),isChecked)==1) {// if the manual control is on
                                    String s = "Update manual control to "+isChecked;
                                    message.what = UPDATE_MANUAL_CONTROL_OFF;
                                    message.obj = s;
                                } else { // if the manual control is off
                                    String s = "Update manual control failure";
                                    message.what =UPDATE_MANUAL_CONTROL_FAILURE;
                                    message.obj = s;
                                }

                                handler.sendMessage(message);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    tbt_manual_control.setEnabled(Boolean.FALSE);
                }
            }
        });


        /*
            initial the state of switches (current state of the light
         */

        /*
            update the change on the toggle button
         */
        tbt_manual_control.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toast("Turn On Light");
                    //light.setLightState(isChecked);
                } else {
                    toast("Turn Off Light");
                    // light.setLightState(isChecked);
                }
            }
        });


    }

    private void toast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }

}

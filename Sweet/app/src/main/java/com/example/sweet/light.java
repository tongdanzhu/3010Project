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
    private static final int SWITCH_IS_ON = 6;
    private static final int SWITCH_IS_OFF = 7;
    private static final int UPDATE_SWITCH_ON = 8;
    private static final int UPDATE_SWITCH_OFF = 9;
    private static final int UPDATE_SWITCH_FAILURE = 10;
    private static final int MANUAL_CONTROL_OFF_SWITCH_ON = 11;
    private String houseid;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MANUAL_CONTROL_ON:

                    sw_manual_control.setChecked(Boolean.TRUE);
                    tbt_manual_control.setEnabled(Boolean.TRUE);
                    break;
                case MANUAL_CONTROL_OFF:

                    sw_manual_control.setChecked(Boolean.FALSE);
                    tbt_manual_control.setEnabled(Boolean.FALSE);
                    break;

                case UPDATE_MANUAL_CONTROL_ON:

                    tbt_manual_control.setEnabled(Boolean.TRUE);
                    break;
                case UPDATE_MANUAL_CONTROL_OFF:

                    tbt_manual_control.setEnabled(Boolean.FALSE);
                    break;
                case UPDATE_MANUAL_CONTROL_FAILURE:
                    toast(msg.obj.toString());
                    break;
                case SWITCH_IS_ON:
                    tbt_manual_control.setChecked(Boolean.TRUE);

                    break;
                case SWITCH_IS_OFF:
                    tbt_manual_control.setChecked(Boolean.FALSE);


                    break;
                case UPDATE_SWITCH_ON:
                    tbt_manual_control.setChecked(Boolean.TRUE);

                    break;
                case UPDATE_SWITCH_OFF:
                    tbt_manual_control.setChecked(Boolean.FALSE);

                    break;
                case UPDATE_SWITCH_FAILURE:
                    toast(msg.obj.toString());
                    break;
                case MANUAL_CONTROL_OFF_SWITCH_ON:
                   
                    sw_manual_control.setChecked(Boolean.FALSE);
                    tbt_manual_control.setChecked(Boolean.TRUE);
                    tbt_manual_control.setEnabled(Boolean.FALSE);
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

                    // new a thread to update the manual control when it turned on
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyConnection conn = new MyConnection();
                            try {
                                Message message = handler.obtainMessage();
                                if (conn.updateManualControl(Integer.parseInt(houseid), isChecked) == 1) {// if the manual control is on
                                    String s = "Update manual control to ON";
                                    message.what = UPDATE_MANUAL_CONTROL_ON;
                                    message.obj = s;
                                } else { // if the manual control is off
                                    String s = "Update manual control failure";
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

                    // new a thread to update the manual control when it turned off
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyConnection conn = new MyConnection();
                            try {
                                Message message = handler.obtainMessage();
                                if (conn.updateManualControl(Integer.parseInt(houseid), isChecked) == 1) {// if the manual control is on
                                    String s = "Update manual control to OFF";
                                    message.what = UPDATE_MANUAL_CONTROL_OFF;
                                    message.obj = s;
                                } else { // if the manual control is off
                                    String s = "Update manual control failure";
                                    message.what = UPDATE_MANUAL_CONTROL_FAILURE;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyConnection conn = new MyConnection();
                try {
                    Message message = handler.obtainMessage();
                    // manual control is on, switch is on: open switch(manual control), enable toggle button(switch) ,toggle button is ON
                    // manual control is off, switch is off: close switch(manual control), disable toggle button(switch), toggle button is OFF
                    // manual control is on, switch if off: open switch(manual control), enable toggle button(switch), toggle button is OFF
                    // manual control is off, switch if on: close switch(manual control), disable toggle button(switch), toggle button is ON
                    if (conn.getSwitches(Integer.parseInt(houseid)) && conn.getManualControl(Integer.parseInt(houseid))) {
                        String s = "Switch is ON";
                        message.what = SWITCH_IS_ON;
                        message.obj = s;
                    } else { // if the manual control is off
                        //if the switch is on
                        if (conn.getSwitches(Integer.parseInt(houseid)) && !conn.getManualControl(Integer.parseInt(houseid))){
                            String s = "Switch is ON";
                            message.what = MANUAL_CONTROL_OFF_SWITCH_ON;
                            message.obj = s;
                        }else{
                            String s = "Switch is OFF";
                            message.what = SWITCH_IS_OFF;
                            message.obj = s;

                        }
                    }

                    handler.sendMessage(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /*
            update the change on the toggle button
         */
        tbt_manual_control.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    // new a thread to update the switch when it turned on
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyConnection conn = new MyConnection();
                            try {
                                Message message = handler.obtainMessage();
                                if (conn.updateSwitches(Integer.parseInt(houseid), isChecked) == 1) {// if the switch is turned on
                                    String s = "Update switch to ON";
                                    message.what = UPDATE_SWITCH_ON;
                                    message.obj = s;
                                } else { // if the manual control is off
                                    String s = "Update switch failure";
                                    message.what = UPDATE_SWITCH_FAILURE;
                                    message.obj = s;
                                }

                                handler.sendMessage(message);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } else {

                    // new a thread to update the switch when it turned off
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyConnection conn = new MyConnection();
                            try {
                                Message message = handler.obtainMessage();
                                if (conn.updateSwitches(Integer.parseInt(houseid), isChecked) == 1) {// if the switch is turned off
                                    String s = "Update switch to OFF";
                                    message.what = UPDATE_SWITCH_OFF;
                                    message.obj = s;
                                } else { // if the manual control is off
                                    String s = "Update switch failure";
                                    message.what = UPDATE_SWITCH_FAILURE;
                                    message.obj = s;
                                }

                                handler.sendMessage(message);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
            }
        });


    }

    private void toast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }

}

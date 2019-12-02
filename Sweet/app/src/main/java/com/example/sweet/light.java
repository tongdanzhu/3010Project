package com.example.sweet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class light extends AppCompatActivity {

    private TextView tv_light_state;
    private Switch sw_manual_control;
    private lightControlVO light;
    private ToggleButton tbt_manual_control;
    private TextView tv_currHouse;

    private String houseid;

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

        /*
            initial the state of switches (current state of the light
         */

        /*
            update change on the switch
         */
        sw_manual_control.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toast("manual control is: " + isChecked);
                    light.setManualControl(isChecked);
                    tbt_manual_control.setEnabled(Boolean.TRUE);
                } else {
                    toast("manual control is: " + isChecked);
                    light.setManualControl(isChecked);
                    tbt_manual_control.setEnabled(Boolean.FALSE);
                }
            }
        });

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

package com.example.sweet;

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
    private lightVO light;
    private ToggleButton tbt_manual_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light);

        light = new lightVO();
        light.setLightState(false);

        tv_light_state = (TextView) findViewById(R.id.tv_light_state);
        if(light.isLightState()){
            tv_light_state.setText("Light is ON");}
        else{
            tv_light_state.setText("Light if OFF");
        }

        //switch of manual controller of light
        sw_manual_control = (Switch) findViewById(R.id.sw_manual_control);
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


        //toggle button of turn on or off the light manually
        tbt_manual_control = (ToggleButton) findViewById(R.id.tbt_manual_control);

        tbt_manual_control.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toast("Turn On Light");
                    light.setLightState(isChecked);
                } else {
                    toast("Turn Off Light");
                    light.setLightState(isChecked);
                }
            }
        });


    }
    private void toast(String s){
        Toast.makeText(getApplication(),s, Toast.LENGTH_SHORT).show();
    }

}

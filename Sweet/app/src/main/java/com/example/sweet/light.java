package com.example.sweet;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class light extends AppCompatActivity {

    private TextView tv_light_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light);
        lightVO light=new lightVO();
        light.setManualControl(false);

        tv_light_state=(TextView) findViewById(R.id.tv_light_state);
        tv_light_state.setText(light.isManualControl()+"");

    }
}

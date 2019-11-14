package com.example.sweet;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class temperature extends AppCompatActivity {

    private TextView tv_currDate;
    private TextView tv_currTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);

        temperatureVO t=new temperatureVO();
        t.setCurrDate();
        t.setCurrTime();


        tv_currDate=(TextView) findViewById(R.id.tv_currDate);
        tv_currDate.setText(t.getCurrDate());

        tv_currTime=(TextView) findViewById(R.id.tv_currTime);
        tv_currTime.setText(t.getCurrTime());


    }
}

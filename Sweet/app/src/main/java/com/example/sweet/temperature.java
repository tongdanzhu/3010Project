package com.example.sweet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class temperature extends AppCompatActivity {

    private TextView tv_currDate;
    private TextView tv_currTime;
    private TextView tv_threshold;
    private Button bt_set_threshold;
    private EditText et_set_threshold;

    private temperatureVO temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);

        temp=new temperatureVO();
        temp.setCurrDate();
        temp.setCurrTime();


        tv_currDate=(TextView) findViewById(R.id.tv_currDate);
        tv_currDate.setText(temp.getCurrDate());

        tv_currTime=(TextView) findViewById(R.id.tv_currTime);
        tv_currTime.setText(temp.getCurrTime());

        bt_set_threshold =(Button) findViewById(R.id.bt_set_threshold);
        et_set_threshold = (EditText) findViewById(R.id.et_set_threshold);

        //edit text for inputting the threshold
        et_set_threshold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //et_set_threshold.setEnabled(Boolean.FALSE);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //et_set_threshold.setEnabled(Boolean.FALSE);
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_set_threshold.getText())) {
                    bt_set_threshold.setEnabled(Boolean.FALSE);
                    return;
                } else {
                    bt_set_threshold.setEnabled(Boolean.TRUE);
                }
            }
        });

        //display current threshold
        tv_threshold.setText(temp.getThreshold()+"");

        //button listener for updating the threshold
        bt_set_threshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isThresholdValid(et_set_threshold.getText().toString())){//if the threshold is not valid
                    toast("the threshold must be in range of 0C to 40C");
                    bt_set_threshold.setEnabled(Boolean.FALSE);
                }
                else
                {
                    toast(et_set_threshold.getText().toString());
                }
                //startActivity(intent);

            }
        });


    }

    // check the validation of threshold in range from 0(C) to 40 (C)
    public  boolean isThresholdValid (String threshold){
        //toast(threshold.length()+"")
        double d_threshold = Double.valueOf(threshold);
        if (d_threshold<=0 || d_threshold>=40){
            return false;
        }

        return true;
    }

    private void toast(String s){
        Toast.makeText(getApplication(),s, Toast.LENGTH_SHORT).show();
    }

}

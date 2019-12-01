package com.example.sweet;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class temperature extends AppCompatActivity {


    private TextView tv_threshold;
    private Button bt_set_threshold;
    private EditText et_set_threshold;
    private TextView tv_currHouse;
    private TextView tv_currTemp;

    //private temperatureControlVO temp;
    private String houseid;

    private static final int THRESHOLDEXIST = 1;
    private static final int ID_NOT_EXIST = 2;
    private boolean thresholdExist = false;
    private double defaultValue = -0.5;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isValid;
            switch (msg.what) {
                case THRESHOLDEXIST:
                    toast(msg.obj.toString());
                    thresholdExist = true;
                    break;
                case 2:
                    toast(msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);

        // initialization of temperature object
        //temp = new temperatureControlVO();
        //temp.setCurrDate();
        //temp.setCurrTime();

        // receive parameter from previous page
        Intent getIntent = getIntent();
        houseid = getIntent.getStringExtra("house_id");


        /* elements initialization
         */
        tv_currTemp = (TextView) findViewById(R.id.tv_currTemp); // text view for latest temperature
        bt_set_threshold = (Button) findViewById(R.id.bt_set_threshold); //button for setting threshold
        et_set_threshold = (EditText) findViewById(R.id.et_set_threshold); // edit text for inputting threshold
        tv_currHouse = (TextView) findViewById(R.id.tv_currHouse);
        tv_threshold = (TextView) findViewById(R.id.tv_threshold);


        //display current house id
        tv_currHouse.setText(houseid);

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

        //display threhold where the house id is the one received from previous page
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyConnection conn = new MyConnection();
                try {
                    boolean isThresholdExist = conn.isThresholdExist(Integer.parseInt(houseid));

                    Message message = handler.obtainMessage();
                    if (!isThresholdExist) {
                        String s = "Threshold is not exist";
                        message.what = THRESHOLDEXIST;
                        message.obj = s;
                    } else {
                        if (conn.getThreshold(Integer.parseInt(houseid)) == defaultValue) {
                            tv_threshold.setText("Threshold is not exist");
                        } else {
                            System.out.println("Threshold=" + conn.getThreshold(Integer.parseInt(houseid)) + "------");
                            tv_threshold.setText(conn.getThreshold(Integer.parseInt(houseid)) + " ℃");
                        }
                    }
                    handler.sendMessage(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        //display latest temperature where the house id is the one received from previous page
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyConnection conn = new MyConnection();
                try {
                    double latestTemp =conn.getLatestTemp(Integer.parseInt(houseid));
                    //Message message = handler.obtainMessage();
                    if(latestTemp==defaultValue){
                        tv_currTemp.setText("No current temperature information.");
                    }
                    else{
                        tv_currTemp.setText(latestTemp+" ℃");
                    }
                    //handler.sendMessage(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //button listener for updating the threshold
        bt_set_threshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setThreshold = et_set_threshold.getText().toString();

                if (!isThresholdValid(setThreshold) || !isThresholdisDigit(setThreshold)) {//if the threshold is not valid
                    toast("the threshold must be in range of 0C to 40C");
                    bt_set_threshold.setEnabled(Boolean.FALSE);
                } else {
                    toast(et_set_threshold.getText().toString());
                }
                //startActivity(intent);

            }
        });


    }


    //check whether the input threshold is a digital
    public boolean isThresholdisDigit(String threshold) {
        for (int i = 0; i < threshold.length(); i++) {
            if (!Character.isDigit(threshold.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    // check the validation of threshold in range from 0(C) to 40 (C)
    public boolean isThresholdValid(String threshold) {
        double d_threshold = Double.valueOf(threshold);
        if (d_threshold <= 0 || d_threshold > 40) {
            return false;
        }
        return true;
    }

    private void toast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }

}

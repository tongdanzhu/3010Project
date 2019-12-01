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
import android.view.ViewGroup;
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

    private static final int THRESHOLD_NOT_EXIST = 1;
    private static final int THRESHOLD_EXIST = 2;
    private static final int UPDATE_NOT_SUCCESSFUL = 3;
    private static final int UPDATE_SUCCESSFUL = 4;
    private static final int NO_TEMP_INFO = 5;
    private static final int LATEST_TEMP = 6;
    private boolean thresholdExist = false;
    private double defaultValue = -0.5;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isValid;
            switch (msg.what) {
                case THRESHOLD_NOT_EXIST:
                    tv_threshold.setText("Threshold is not exist");
                    toast(msg.obj.toString());
                    thresholdExist = true;
                    break;
                case THRESHOLD_EXIST:
                    tv_threshold.setText(msg.obj.toString());
                    break;
                case UPDATE_NOT_SUCCESSFUL:
                    toast(msg.obj.toString());
                    break;
                case UPDATE_SUCCESSFUL:
                    toast(msg.obj.toString());
                    refresh();
                    break;
                case NO_TEMP_INFO:
                    tv_currTemp.setText(msg.obj.toString());
                    break;
                case LATEST_TEMP:
                    tv_currTemp.setText(msg.obj.toString());

                    break;

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
                    //boolean isThresholdExist = conn.isThresholdExist(Integer.parseInt(houseid));
                    Message message = handler.obtainMessage();

                    if (conn.getThreshold(Integer.parseInt(houseid)) == defaultValue) {

                        String s = "Threshold is not exist";
                        message.what = THRESHOLD_NOT_EXIST;
                        message.obj = s;
                    } else {
                        //System.out.println("Threshold=" + conn.getThreshold(Integer.parseInt(houseid)) + "------");
                        //tv_threshold.setText(conn.getThreshold(Integer.parseInt(houseid)) + " ℃");
                        String s = conn.getThreshold(Integer.parseInt(houseid)) + " ℃";
                        System.out.println(s+"----------");
                        message.what = THRESHOLD_EXIST;
                        message.obj = s;
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
                Message message = handler.obtainMessage();
                try {
                    double latestTemp = conn.getLatestTemp(Integer.parseInt(houseid));
                    if (latestTemp == defaultValue) {
                        String s = "No current temperature information.";
                        message.what = NO_TEMP_INFO;
                        message.obj = s;

                    } else {
                        String s = latestTemp + " ℃";
                        System.out.println("latest temperature" + s + "============");
                        message.what = LATEST_TEMP;
                        message.obj = s;

                    }
                    handler.sendMessage(message);
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
                if (!isThresholdValid(setThreshold)) {//if the threshold is not valid
                    toast("the threshold must be in range of 0C to 40C");
                    bt_set_threshold.setEnabled(Boolean.FALSE);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyConnection conn = new MyConnection();
                            Message message = handler.obtainMessage();
                            try {
                                int updateThreshold = conn.updateThreshold(Integer.parseInt(houseid), Double.valueOf(et_set_threshold.getText().toString()));
                                if (updateThreshold == 0) { //house id not exist in temperatureControl table

                                    // insert a row for the new house id
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MyConnection conn = new MyConnection();
                                            Message message = handler.obtainMessage();
                                            try {
                                                if(conn.insertThreshold(Integer.parseInt(houseid),Double.valueOf(et_set_threshold.getText().toString()))!=0){
                                                    String s = "Update is not successful.";
                                                    message.what = UPDATE_NOT_SUCCESSFUL;
                                                    message.obj = s;
                                                }
                                                else{
                                                    String s = "Update is successful.";
                                                    message.what = UPDATE_SUCCESSFUL;
                                                    message.obj = s;
                                                }

                                                handler.sendMessage(message);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    //end of insert a new row

                                } else { // update the threshold successfully to the certain house id
                                    String s = "Update is successful.";
                                    message.what = UPDATE_SUCCESSFUL;
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

    /*
        refresh page
     */
    private void refresh() {
        finish();
        Intent intent = new Intent(temperature.this, temperature.class);
        intent.putExtra("house_id",houseid);
        startActivity(intent);
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

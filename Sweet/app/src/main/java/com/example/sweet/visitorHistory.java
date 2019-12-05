package com.example.sweet;

/*
 *  This file is to design an user interface for the confirmed history of visitors.
 * This file is a sub-layer of the "history_bt" button in "doorbell.java"
 * @author: Lixuan Luo
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class visitorHistory extends AppCompatActivity {

    //private TextView num;
    private TextView info;
    private String houseid;
    private List<visitorVO> list = new ArrayList<>(); //a list of visitors
    private int i; //count the number of visitors in list.
    ArrayList<String> stringArray = new ArrayList<String>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    //num.setText(msg.obj.toString());  //display the number of visitors on the top left corner

                    //print visitors' visit date and time
                    for (int j = 0; j < stringArray.size(); j++) {
                        info.append(stringArray.get(j));
                        info.append("\n");
                    }
                    break;
            }
        }
    };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.visitor_history);

            /*
            initial elements
            */
            info = (TextView) findViewById(R.id.historyInfo);

            /*
            receive parameter from previous page
            */
            Intent getIntent = getIntent();
            houseid = getIntent.getStringExtra("house_id");

            new Thread(new Runnable() {

                @Override
                public void run() {
                    MyConnection connection = new MyConnection();

                    //get the visitors list from database
                    try {
                        Message message = handler.obtainMessage();

                        list = connection.getHistory(Integer.parseInt(houseid));
                        i = list.size(); //count the number of visitors in list.
                        message.what = 1;
                        //print the list of visitors
                        for(int n=0;n<i;n++){
                            visitorVO v = list.get(n);

                            stringArray.add("visitor " + v.getVisitorID() + ": " + v.print_as_String(v));
                        }

                        handler.sendMessage(message);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }





}

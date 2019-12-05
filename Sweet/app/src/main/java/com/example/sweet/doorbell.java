package com.example.sweet;

/*
 *  This file is to design a user interface of visitors information.
 * This file is a sub-layer of the "bt_doorbell" button in "house_activity.java"
 * @author: Lixuan Luo
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class doorbell extends AppCompatActivity {

    private TextView visitorsNum;
    private TextView visitorInfo;
    private Button confirm_bt;
    private Button history_bt;
    private String houseid;
    private List<visitorVO> list = new ArrayList<>(); //a list of visitors
    private int i; //count the number of visitors in list.
    ArrayList<String> stringArray = new ArrayList<String>();

    /* message handler */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 1:
                    visitorsNum.setText(msg.obj.toString());  //display the number of visitors on the top left corner

                    //print visitors' visit date and time
                    for (int j = 0; j < stringArray.size(); j++) {
                        visitorInfo.append(stringArray.get(j));
                        visitorInfo.append("\n");
                    }
                    break;

                case 2:
                    //display a toast of confirmation and refresh the page
                    Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    refresh();
                    break;

                case 3:
                    //display a confirm failed msg
                    Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doorbell);

        /*
            initial elements
         */
        visitorsNum = (TextView) findViewById(R.id.visitorCount);
        visitorInfo = (TextView) findViewById(R.id.visitorInfo);
        confirm_bt = (Button) findViewById(R.id.visitorConfirmButton);
        confirm_bt.setEnabled(Boolean.TRUE);
        history_bt = (Button) findViewById(R.id.history);
        history_bt.setEnabled(Boolean.TRUE);


        /*
        receive parameter from previous page
        */
        Intent getIntent = getIntent();
        houseid = getIntent.getStringExtra("house_id");

        /* new a thread to handle the database accessing */
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyConnection connection = new MyConnection();

                //get the visitors list from database
                try {
                    Message message = handler.obtainMessage();
                    list = connection.getVisitorList(Integer.parseInt(houseid));
                    i = list.size(); //count the number of visitors in list.
                    String s = Integer.toString(i);
                    message.what = 1;
                    message.obj = s;

                    //print the list of visitors
                    for (int n = 0; n < i; n++) {
                        visitorVO v = list.get(n);
                        stringArray.add("visitor " + Integer.toString(n + 1) + ": " + v.print_as_String(v));
                    }

                    handler.sendMessage(message);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        /*
        confirm button listener
         */
        confirm_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyConnection connection = new MyConnection();
                        String s;

                        //update confirm status when visitor click the confirm button
                        try {
                            Message message = handler.obtainMessage();
                            list = connection.getVisitorList(Integer.parseInt(houseid));
                            int a = connection.confirmVisitor(Integer.parseInt(houseid), list);
                            if (a != 0) {
                                s = "confirm";
                                message.what = 2;
                                message.obj = s;
                            } else {
                                s = "You have no visitors";
                                message.what = 3;
                                message.obj = s;
                            }

                            handler.sendMessage(message);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        /*
        history page listener
        */
        history_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(doorbell.this, visitorHistory.class);
                intent.putExtra("house_id", houseid);
                startActivity(intent);

            }
        });


    }

    /*
       refresh page
      */
    private void refresh() {
        finish();
        Intent intent = new Intent(this, doorbell.class);
        intent.putExtra("house_id", houseid);
        startActivity(intent);
    }
}

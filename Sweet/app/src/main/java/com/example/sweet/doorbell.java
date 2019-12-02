package com.example.sweet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class doorbell extends AppCompatActivity {

    private TextView visitorsNum;
    private TextView visitorInfo;
    private String houseid;
    private List<visitorVO> list = new ArrayList<>(); //a list of visitors
    private int i; //count the number of visitors in list.
    private String listInfo;
    String str[] = new String[i];
    ArrayList<String> stringArray = new ArrayList<String>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    visitorsNum.setText(msg.obj.toString());  //display the number of visitors on the top left corner

                    //print visitors' visit date and time
                    for(int j=0; j<stringArray.size();j++){
                        visitorInfo.append(stringArray.get(j));
                        visitorInfo.append("\n");
                    }
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
                    Message message2 = handler.obtainMessage();

                    list = connection.getVisitorList(Integer.parseInt(houseid));
                    i = list.size(); //count the number of visitors in list.
                    String s = Integer.toString(i);
                    message.what = 1;
                    message.obj = s;

                    //print the list of visitors
                    for(int n=0;n<i;n++){
                      visitorVO v = list.get(n);
                      stringArray.add("visitor " + Integer.toString(n+1) + ": " + v.print_as_String(v));
                    }

                    handler.sendMessage(message);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }).start();









    }
}

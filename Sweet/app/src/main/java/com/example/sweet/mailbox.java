package com.example.sweet;

/*
 *  This file is an user interface for mailbox.
 * @author: Tongdan Zhu
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class mailbox extends AppCompatActivity {

    private TextView tv_new_letter;
    private Button bt_confirm;
    private TextView tv_currHouse;


    private static final int NEW_LETTER = 1;
    private static final int NO_NEW_LETTER = 2;
    private static final int CONFIRMED=3;
    private static final int CONFIRMED_FAILURE=4;
    private String houseid;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case NEW_LETTER:
                    tv_new_letter.setText(msg.obj.toString());
                    bt_confirm.setEnabled(Boolean.TRUE);
                    break;
                case NO_NEW_LETTER:
                    tv_new_letter.setText(msg.obj.toString());
                    break;
                case CONFIRMED:
                    toast(msg.obj.toString());
                    refresh();
                    break;
                case CONFIRMED_FAILURE:
                    toast(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailbox);



        /*
            initial elements
         */
        tv_new_letter = (TextView) findViewById(R.id.tv_new_letter);
        // button for confirmation of receiving a new letter
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        bt_confirm.setEnabled(Boolean.FALSE);
        //text view for current house
        tv_currHouse = (TextView) findViewById(R.id.tv_currHouse_mailbox);

        /*
            receive parameter from previous page
         */
        Intent getIntent = getIntent();
        houseid = getIntent.getStringExtra("house_id");
        //display current house id
        tv_currHouse.setText(houseid);


        // new a thread to check database whether having a new letter
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyConnection conn = new MyConnection();
                try {
                    Message message = handler.obtainMessage();

                    if (conn.getMailboxState(Integer.parseInt(houseid))) {

                        String s = "NEW";

                        message.what =NEW_LETTER  ;
                        message.obj = s;
                    } else {
                        String s = "NO NEW";
                        message.what =NO_NEW_LETTER  ;
                        message.obj = s;
                    }

                    handler.sendMessage(message);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // confirm button listener
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // new a thread to confirm new letter message
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyConnection conn = new MyConnection();
                        try {
                            Message message = handler.obtainMessage();

                            if (conn.updateMailboxConfirm(Integer.parseInt(houseid))==1) {

                                String s = "Confirmed";
                                message.what =CONFIRMED  ;
                                message.obj = s;
                            } else {
                                String s = "Confirm failure";
                                message.what =CONFIRMED_FAILURE  ;
                                message.obj = s;
                            }

                            handler.sendMessage(message);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                bt_confirm.setEnabled(Boolean.FALSE); //disable the button when the confirm is received
            }
        });

    }

    /*
       refresh page
    */
    private void refresh() {
        finish();
        Intent intent = new Intent(mailbox.this,mailbox.class);
        intent.putExtra("house_id", houseid);
        startActivity(intent);
    }
    /*
     *   display message on screen
     * */
    private void toast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }
}
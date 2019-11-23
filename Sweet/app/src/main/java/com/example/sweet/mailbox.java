package com.example.sweet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mailbox extends AppCompatActivity {

    private TextView tv_new_letter;
    private Button bt_confirm;
    private houseVO h;
    private String new_letter="NEW";
    private String no_new_letter="No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailbox);

        h=new houseVO();
        h.setMailboxState(true);
        tv_new_letter=(TextView) findViewById(R.id.tv_new_letter);
        bt_confirm=(Button) findViewById(R.id.bt_confirm);
        bt_confirm.setEnabled(Boolean.FALSE);

        if(h.isMailboxState()){
            tv_new_letter.setText(new_letter);
            bt_confirm.setEnabled(Boolean.TRUE);
        }
        else{
            tv_new_letter.setText(no_new_letter);
        }

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update database
                bt_confirm.setEnabled(Boolean.FALSE);
                tv_new_letter.setText(no_new_letter);
            }
        });

    }
}
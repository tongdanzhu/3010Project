package com.example.sweet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class house_activity extends AppCompatActivity {

    private Button bt_temperature;
    private Button bt_light;
    private Button bt_mailbox;
    private Button bt_doorbell;
    private TextView tv_house_id;
    private String houseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_activity);


        Intent getIntent = getIntent();
        houseid = getIntent.getStringExtra("house_id");

        tv_house_id=(TextView) findViewById(R.id.tv_house_id);
        tv_house_id.setText(houseid);

        bt_temperature =(Button) findViewById(R.id.bt_temperature);
        bt_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(house_activity.this, temperature.class);

                startActivity(intent);

            }
        });

        bt_light=(Button) findViewById(R.id.bt_light);
        bt_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(house_activity.this,light.class);
                startActivity(intent);
            }
        });


        bt_mailbox=(Button) findViewById(R.id.bt_mailbox);
        bt_mailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(house_activity.this,mailbox.class);
                startActivity(intent);
            }
        });

        bt_doorbell=(Button) findViewById(R.id.bt_doorbell);
        bt_doorbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(house_activity.this,doorbell.class);
                startActivity(intent);
            }
        });


    }


}

package com.example.sweet;
/*
 *  An user interface for all house activities.
 * @author:Tongdan Zhu
 */
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

        // receive parameter from previous page
        Intent getIntent = getIntent();
        houseid = getIntent.getStringExtra("house_id");

        // elements initialization
        tv_house_id = (TextView) findViewById(R.id.tv_house_id);
        tv_house_id.setText(houseid);
        bt_temperature = (Button) findViewById(R.id.bt_temperature);
        bt_light = (Button) findViewById(R.id.bt_light);
        bt_mailbox = (Button) findViewById(R.id.bt_mailbox);
        bt_doorbell=(Button) findViewById(R.id.bt_doorbell);


        // jump to the temperature function page
        bt_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(house_activity.this, temperature.class);
                intent.putExtra("house_id",houseid);
                startActivity(intent);

            }
        });

        //jump to the light function page
        bt_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(house_activity.this, light.class);
                intent.putExtra("house_id",houseid);
                startActivity(intent);
            }
        });

        // jump to the mailbox function page
        bt_mailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(house_activity.this, mailbox.class);
                intent.putExtra("house_id",houseid);
                startActivity(intent);
            }
        });


        // jump to the doorbell function page
        bt_doorbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(house_activity.this, doorbell.class);
                intent.putExtra("house_id",houseid);
                startActivity(intent);
            }
        });


    }


}

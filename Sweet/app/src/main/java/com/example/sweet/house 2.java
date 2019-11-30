package com.example.sweet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class house extends AppCompatActivity {

    private EditText et_house_id;
    private Button bt_enter_house_id;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house);

        bt_enter_house_id = (Button) findViewById(R.id.bt_enter_house_id);
        et_house_id = (EditText) findViewById(R.id.et_house_id);
        et_password =(EditText) findViewById(R.id.et_password);


        et_house_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bt_enter_house_id.setEnabled(Boolean.FALSE);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_enter_house_id.setEnabled(Boolean.FALSE);
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_house_id.getText())) {
                    bt_enter_house_id.setEnabled(Boolean.FALSE);
                    return;
                } else {
                    bt_enter_house_id.setEnabled(Boolean.TRUE);
                }
            }
        });


        //button listener for house id
        bt_enter_house_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(house_id_validation(et_house_id.getText().toString())
                        && password_validation(et_house_id.getText().toString(),et_password.getText().toString()))//if the house id is exist in database
                {
                    Intent intent = new Intent(house.this, house_activity.class);
                    intent.putExtra("house_id", et_house_id.getText().toString());
                    //toast(et_house_id.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    toast("house id is invalid");
                }

            }
        });

    }


    //connect to database to check where the house_id is in exist
    public boolean house_id_validation(String house_id){
        String command=1+":"+house_id;
        udpSender udpSender = new udpSender();
        //toast(command);
        if( udpSender.checkValidationHouseID(command))
        {
            return false;
        }
        return true;
    }

    //check whether the combination of house id and password is correct
    public boolean password_validation(String house_id, String password){
        if(house_id.equals("1")&& password.equals("11"))
        {
            return true;}
        else {
            toast("password is incorrect");
            return false;

        }
    }



    private void toast(String s){
        Toast.makeText(getApplication(),s, Toast.LENGTH_SHORT).show();
    }
}
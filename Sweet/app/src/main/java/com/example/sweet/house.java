package com.example.sweet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class house extends AppCompatActivity {

    private EditText et_house_id;
    private Button bt_enter_house_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house);

        bt_enter_house_id = (Button) findViewById(R.id.bt_enter_house_id);
        et_house_id = (EditText) findViewById(R.id.et_house_id);


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

        bt_enter_house_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(house.this, house_activity.class);
                intent.putExtra("house_id",et_house_id.getText().toString());
                //toast(et_house_id.getText().toString());
                startActivity(intent);

            }
        });

    }

    public boolean house_id_validation(String house_id){
        if(house_id.length()==0 )
        {return false;}
        return true;
    }



    private void toast(String s){
        Toast.makeText(getApplication(),s, Toast.LENGTH_SHORT).show();
    }
}
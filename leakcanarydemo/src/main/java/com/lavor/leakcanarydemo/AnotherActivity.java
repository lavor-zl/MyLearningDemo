package com.lavor.leakcanarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by shitian on 2016-04-10.
 */
public class AnotherActivity extends AppCompatActivity{
    private android.widget.Button startmainactivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_activity);
        this.startmainactivity = (Button) findViewById(R.id.start_main_activity);
        this.startmainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AnotherActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

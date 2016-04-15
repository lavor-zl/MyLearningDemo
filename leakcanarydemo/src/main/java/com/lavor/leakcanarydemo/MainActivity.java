package com.lavor.leakcanarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity {

    private android.widget.Button startanotheractivity;
    private static View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leak_canary_activity_main);
        this.startanotheractivity = (Button) findViewById(R.id.start_another_activity);
//        view=this.startanotheractivity;
//        ViewModle.button=this.startanotheractivity;

        this.startanotheractivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AnotherActivity.class);
                startActivity(intent);
                Log.i("Hello","startActivity");
                finish();
            }
        });
    }
}

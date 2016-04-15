package com.example.shitian.rxbinding;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewEvent;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;


public class RxBindingMainActivity extends Activity {

    private android.widget.Button simple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding_main);
        this.simple = (Button) findViewById(R.id.simple);
        RxView.clicks(simple)
                .throttleFirst(500, TimeUnit.MILLISECONDS) //去除抖动而不小心点了多下
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void viewEvent) {
                        Log.i("RxBinding", "RxBinding:Button点击事件");

                    }
                });

    }
}

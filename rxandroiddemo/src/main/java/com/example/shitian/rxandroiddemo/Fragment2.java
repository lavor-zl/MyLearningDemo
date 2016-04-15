package com.example.shitian.rxandroiddemo;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import rx.functions.Action1;

/**
 * Created by shitian on 2015-12-08.
 */
public class Fragment2 extends Fragment {
    private android.widget.EditText text2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        this.text2 = (EditText) view.findViewById(R.id.text2);
        RxBus._rxBus.toObserverable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                       text2.setText(((TextShow)event).text);
                    }
                });
        return view;
    }
}

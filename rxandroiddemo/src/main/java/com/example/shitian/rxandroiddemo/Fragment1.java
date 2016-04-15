package com.example.shitian.rxandroiddemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by shitian on 2015-12-08.
 */
public class Fragment1 extends Fragment {
    private android.widget.EditText text1;
    private android.widget.Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        this.button = (Button) view.findViewById(R.id.button);
        this.text1 = (EditText) view.findViewById(R.id.text1);
        //通过按钮点击事件将text1中的文本内容通过EventBus传递到Fragment2中的text2中
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=text1.getText().toString();
                RxBus._rxBus.send(new TextShow(text));
            }
        });


        return view;
    }
}

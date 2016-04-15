package com.lavor.leakcanarydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.leakcanary.RefWatcher;

/**
 * Created by shitian on 2016-04-10.
 */
public class MyFragment extends Fragment{
    private android.widget.Button fragmentbutton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_fragment,container,false);
        this.fragmentbutton = (Button) view.findViewById(R.id.fragment_button);
        ViewModle.fragmentButton=this.fragmentbutton;
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //LeakCanary会自动监测Activity，但是监测Fragment时，需要添加下面两行代码
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}

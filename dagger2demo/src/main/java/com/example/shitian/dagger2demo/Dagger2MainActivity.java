package com.example.shitian.dagger2demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;


public class Dagger2MainActivity extends Activity {

    private android.widget.Button simple;
    @Inject
    @Named("userTest")
    UserTest userTest;
    @Inject
    @Named("user")
    User user;
@Inject
Coffce coffce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2_main);
        UserComponent userComponent=DaggerUserComponent.builder().build();
        userComponent.inject(this);
        User componenntGetUser=userComponent.getUser();
        if(componenntGetUser==null){
            Log.i("Click","user注解失败");
        }else if(componenntGetUser.getName()==null){
            Log.i("Click","user注解成功：provide");
        }else{
            Log.i("Click","user注解成功:inject");
        }
        this.simple = (Button) findViewById(R.id.simple);
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","Start");
                if(user==null){
                    Log.i("Click","user为空");
                }
                else if(user.getName()==null){
                    Log.i("Click","provide构造方法注解成功");
                }
                if(userTest==null){
                    Log.i("Click","userTest为空");
                }
                else{
                    Log.i("Click","userTest注解成功");
                    if(userTest.user!=null){
                        Log.i("Click","userTest中的user注解成功");
                    }
                }
                if(coffce==null){
                    Log.i("Click","coffce为空");
                }
                else{
                    Log.i("Click","coffce注解成功");
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dagger2_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

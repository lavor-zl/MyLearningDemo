package com.example.shitian.okhttpdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;




public class MainActivity extends Activity {
    private Button get;//get请求
    private Button post;//post请求
    private Button post_form;//提交form表单
    private Button post_mutipart;//提交mutipart


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get=(Button)findViewById(R.id.get);
        post= (Button) findViewById(R.id.post);
        post_form= (Button) findViewById(R.id.post_form);
        post_mutipart= (Button) findViewById(R.id.post_mutipart);
        View.OnClickListener listener=new MyOnClickListener(this);
        get.setOnClickListener(listener);
        post.setOnClickListener(listener);
        post_form.setOnClickListener(listener);
        post_mutipart.setOnClickListener(listener);
    }
    /**
     * 用内部类建立点击事件的监听者
     */
    class MyOnClickListener implements View.OnClickListener
    {
        private Context context;
        private String str;
        private OkHttpClient okHttpClient=new OkHttpClient();
        public MyOnClickListener(Context context){
            this.context=context;
        }
        public void showToast(){
            Toast.makeText(context,str,Toast.LENGTH_LONG).show();
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //get请求
                case R.id.get :{
                    Request request=new Request.Builder()
                            .url("https://github.com/lavor-zl").build();
                    Call call=okHttpClient.newCall(request);

                    /*
                    try {
                        //Android4.0前可以这样使用，之后不可以在主线程中访问网络
                        Response response=call.execute();
                        if(response.isSuccessful()){
                            String str=response.body().string();
                            Toast.makeText(context,str,Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                        }
                        @Override
                        public void onResponse(Response response) throws IOException {
                            str=response.body().string();

                            Log.i("find", str);
                            //这里不能用Toast，因为它在一个异步返回方法中，不在主线程中
                            //Toast.makeText(context,str,Toast.LENGTH_LONG).show();
                            //如果我想要用Toast在Android设备上面显示怎么办，可以用Activity内部的runOnUiThread()方法
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast();
                                }
                            });
                        }
                    });
                    break;

                }
                //post请求
                case R.id.post:{
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    String json="{name:lavor_zl,country:china}";
                    RequestBody requestBody=RequestBody.create(JSON,json);

                    //创建字符串请求体
                    //MediaType STRING=MediaType.parse("text/x-markdown; charset=utf-8");
                    //RequestBody requestBody=RequestBody.create(STRING,json);

                    Request request=new Request.Builder().url("https://github.com/lavor-zl")
                            .post(requestBody).build();
                    Call call=okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            str=response.body().string();
                            Log.i("find",str);
                        }
                    });
                    break;
                }
                case R.id.post_form:{
//                    FormEncodingBuilder builder=new FormEncodingBuilder();
//                    builder.add("q","okhttp");
//                    Request request=new Request.Builder().url("https://github.com/lavor-zl")
//                            .post(builder.build()).build();
//                    Call call=okHttpClient.newCall(request);
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Response response) throws IOException {
//                            str=response.body().string();
//                            Log.i("find",str);
//                        }
//                    });

                    FormEncodingBuilder builder=new FormEncodingBuilder();
                    builder.add("name","Z14070515");
                    builder.add("password","710195");
                    Request request=new Request.Builder()
                            .url("http://cas.upc.edu.cn/cas/login")
                            .post(builder.build()).build();
                    Call call=okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            str=response.body().string();
                            Log.i("find",str);
                        }
                    });


                    break;
                }
                case R.id.post_mutipart:{
                    File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    RequestBody requestBody=new MultipartBuilder().addPart
                            (Headers.of("Content-Disposition","form-data")
                            ,fileBody).build();
                    Request request=new Request.Builder().url("https://github.com/lavor-zl")
                            .post(requestBody).build();
                    Call call=okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {

                        }
                    });

                    break;
                }
                default:break;
            }

        }
    }
}

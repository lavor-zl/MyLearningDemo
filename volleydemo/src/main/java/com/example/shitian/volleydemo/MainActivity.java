package com.example.shitian.volleydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private android.widget.Button getString;
    private android.widget.Button postString;
    private android.widget.Button getJson;
    private RequestQueue mQueue;
    private android.widget.ImageView imageView;
    private Button imageRequest;
    private Button imageLoader;
    private Button nImageView;
    private com.android.volley.toolbox.NetworkImageView networkImageView;
    private ImageLoader imgLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_main);
        this.networkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        this.nImageView = (Button) findViewById(R.id.nImageView);
        this.imageLoader = (Button) findViewById(R.id.imageLoader);
        this.imageRequest = (Button) findViewById(R.id.imageRequest);
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.getJson = (Button) findViewById(R.id.getJson);
        this.postString = (Button) findViewById(R.id.postString);
        this.getString = (Button) findViewById(R.id.getString);
        mQueue = Volley.newRequestQueue(this);
        imgLoader=new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        getString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request=new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("请求结果：","成功");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("请求结果：","失败");
                    }
                });
                mQueue.add(request);
            }
        });
        postString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request=new StringRequest(Request.Method.POST,"http://www.baidu.com", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("请求结果：","成功");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("请求结果：","失败");
                    }
                }){
                    //通过重写谷类方法来实现post请求参数的添加
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("params1", "value1");
                        map.put("params2", "value2");
                        return map;
                    }
                };
                mQueue.add(request);
            }
        });
        getJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest request=new JsonObjectRequest("http://ww.baidu.com",null,new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("请求结果：","成功");
                    }
                },new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("请求结果：","失败");
                    }
                });
                mQueue.add(request);
            }
        });
        imageRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequest request=new ImageRequest("http://www.baidu.com/img/bd_logo1.png", new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.i("请求结果：","成功");
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("请求结果：","失败");
                    }
                });
                mQueue.add(request);
            }
        });
        imageLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageLoader.ImageListener listener=ImageLoader.getImageListener
                        (imageView,R.drawable.abc_btn_borderless_material,R.drawable.abc_btn_borderless_material);
                imgLoader.get("http://www.baidu.com/img/bd_logo1.png",listener);
            }
        });
        nImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkImageView.setErrorImageResId(R.drawable.abc_btn_borderless_material);
                networkImageView.setDefaultImageResId(R.drawable.abc_btn_borderless_material);
                networkImageView.setImageUrl("http://www.baidu.com/img/bd_logo1.png",imgLoader);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

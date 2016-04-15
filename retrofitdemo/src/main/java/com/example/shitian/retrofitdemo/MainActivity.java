package com.example.shitian.retrofitdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import converterfactory.StringConverterFactory;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncTask<Void,Void,Void> task=new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {

//                //请求返回的对象由添加的ConverterFactory决定
//                //请求返回Gson
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://www.weather.com.cn")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                WeatherService service = retrofit.create(WeatherService.class);
//                Call<Weather> call=service.getWeather("101010100");
//                try {
//                    Response<Weather> result=call.execute();
//                    String weather=result.body().getWeatherinfo().getWeather();
//                    Log.i("天气", weather);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                //请求返回字符串
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.weather.com.cn")
                        .addConverterFactory(StringConverterFactory.create())
                        .build();
                WeatherStringService service = retrofit.create(WeatherStringService.class);
                Call<String> call=service.getWeather("101010100");
                try {
                    Response<String> result=call.execute();
                    String weather=result.body();
                    Log.i("天气", weather);
                } catch (IOException e) {
                    e.printStackTrace();
                }



                return null;
            }
        };
        task.execute();

    }



    interface WeatherService{
        @GET("adat/cityinfo/{cityCode}.html")
        Call<Weather> getWeather(@Path("cityCode") String cityCode);
    }

    interface WeatherStringService{
        @GET("adat/cityinfo/{cityCode}.html")
        Call<String> getWeather(@Path("cityCode") String cityCode);
    }


}

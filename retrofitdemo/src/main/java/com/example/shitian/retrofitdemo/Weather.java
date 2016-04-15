package com.example.shitian.retrofitdemo;

/**
 * Created by shitian on 2015/11/24.
 */
public class Weather {

    /**http://www.weather.com.cn/adat/cityinfo/101010100.html返回的json对象
     * city : 北京
     * cityid : 101010100
     * temp1 : 15℃
     * temp2 : 5℃
     * weather : 多云
     * img1 : d1.gif
     * img2 : n1.gif
     * ptime : 08:00
     */

    private WeatherinfoEntity weatherinfo;

    public void setWeatherinfo(WeatherinfoEntity weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public WeatherinfoEntity getWeatherinfo() {
        return weatherinfo;
    }

    public static class WeatherinfoEntity {
        private String city;
        private String cityid;
        private String temp1;
        private String temp2;
        private String weather;
        private String img1;
        private String img2;
        private String ptime;

        public void setCity(String city) {
            this.city = city;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public void setTemp1(String temp1) {
            this.temp1 = temp1;
        }

        public void setTemp2(String temp2) {
            this.temp2 = temp2;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getCity() {
            return city;
        }

        public String getCityid() {
            return cityid;
        }

        public String getTemp1() {
            return temp1;
        }

        public String getTemp2() {
            return temp2;
        }

        public String getWeather() {
            return weather;
        }

        public String getImg1() {
            return img1;
        }

        public String getImg2() {
            return img2;
        }

        public String getPtime() {
            return ptime;
        }
    }
}

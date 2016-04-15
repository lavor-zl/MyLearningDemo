package com.example.shitian.imageloaderdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class ImageloaderMainActivity extends Activity {

    private android.widget.ImageView imageView;
    private android.widget.Button loadimage;
    private android.widget.Button displayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageloader_main);
        this.displayImage = (Button) findViewById(R.id.displayImage);
        this.loadimage = (Button) findViewById(R.id.loadimage);
        this.imageView = (ImageView) findViewById(R.id.imageView);

        /**
         * ImageLoade还可以加载文件系统中的图片，只需要将url改变即可
         * String imagePath = "/mnt/sdcard/image.png";
         * String imageUrl = ImageDownloader.Scheme.FILE.wrap(imagePath);
         * 最后的imageUrl其实就暗示"file:///mnt/sdcard/image.png"
         * 还可以这样设置url
         * //图片来源于Content provider
         * String contentprividerUrl = "content://media/external/audio/albumart/13";
         * 图片来源于assets
         * String assetsUrl = Scheme.ASSETS.wrap("image.png");
         * //图片来源于
         * String drawableUrl = Scheme.DRAWABLE.wrap("R.drawable.image");
         */

        loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这只是个简单的使用例子，真正用的时候我们会添加图片显示配置
                /**
                 * //显示图片的配置
                 DisplayImageOptions options = new DisplayImageOptions.Builder()
                 .cacheInMemory(true)
                 .cacheOnDisk(true)
                 .bitmapConfig(Bitmap.Config.RGB_565)
                 .build();
                 .ImageLoader.getInstance().loadImage(url,options,listener)
                 */
                ImageLoader.getInstance().loadImage("http://www.baidu.com/img/bd_logo1.png", new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
            }
        });
        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示图片的配置
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.abc_btn_check_material)
                        .showImageOnFail(R.drawable.abc_btn_radio_material)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
                ImageLoader.getInstance()
                        .displayImage("http://www.baidu.com/img/bd_logo1.png",imageView,options);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imageloader_main, menu);
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

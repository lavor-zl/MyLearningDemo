package com.example.shitian.imageloaderdemo;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by shitian on 2015/11/26.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration= ImageLoaderConfiguration.createDefault(this);
        //用configuration初始化ImageLoader
        ImageLoader.getInstance().init(configuration);


        //我们还可以自己配置ImageLoaderConfiguration的参数
        /**
         * ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
         .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
         .diskCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
         .taskExecutor(...)
         .taskExecutorForCachedImages(...)
         .threadPoolSize(3) // default
         .threadPriority(Thread.NORM_PRIORITY - 1) // default
         .tasksProcessingOrder(QueueProcessingType.FIFO) // default
         .denyCacheImageMultipleSizesInMemory()
         .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
         .memoryCacheSize(2 * 1024 * 1024)
         .memoryCacheSizePercentage(13) // default
         .diskCache(new UnlimitedDiscCache(cacheDir)) // default
         .diskCacheSize(50 * 1024 * 1024)
         .diskCacheFileCount(100)
         .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
         .imageDownloader(new BaseImageDownloader(context)) // default
         .imageDecoder(new BaseImageDecoder()) // default
         .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
         .writeDebugLogs()
         .build();
         */

    }
}

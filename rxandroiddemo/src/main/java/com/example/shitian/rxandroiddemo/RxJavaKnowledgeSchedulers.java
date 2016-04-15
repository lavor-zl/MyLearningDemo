package com.example.shitian.rxandroiddemo;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.io.IOException;

import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by shitian on 2016-04-15.
 */
public class RxJavaKnowledgeSchedulers {
    //利用RxJava的Schedulers实现非阻塞式存储bitmap到磁盘上
    public static void storeBitmap(final Context context, final Bitmap bitmap, final String filename) {
        Schedulers.io().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                blockingStoreBitmap(context,bitmap,filename);
            }
        });
    }
    //阻塞式存储bitmap到磁盘上
    private static void blockingStoreBitmap(Context context, Bitmap bitmap, String filename) {
        FileOutputStream fOut = null;
        try {
            fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fOut != null) {
                    fOut.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

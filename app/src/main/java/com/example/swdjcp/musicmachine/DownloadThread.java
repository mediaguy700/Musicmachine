package com.example.swdjcp.musicmachine;

import android.os.Looper;
import android.util.Log;

/**
 * Created by SWDJCP on 6/3/2016.
 */
public class DownloadThread extends Thread {


    private static final String TAG = DownloadThread.class.getSimpleName();

    public DownloadHandler mHandler;

    @Override
    public void run() {

        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();
    }







}

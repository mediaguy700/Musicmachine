package com.example.swdjcp.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by SWDJCP on 6/6/2016.
 */
public class PlayerService  extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mPlayer;
    public Messenger mMessenger = new Messenger(new PlayerHandler(this));

    @Override
    public void onCreate() {
        Log.d(TAG, "onCeate");
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {

        Log.d(TAG,"onUnbind");

        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {

        Log.d(TAG,"onDestroy");

        mPlayer.release();
    }




    //client Methods

    public boolean isPlaying(){

      return   mPlayer.isPlaying();

    }






    public void play(){

        mPlayer.start();


    }

    public void pause(){

        mPlayer.pause();

    }





}






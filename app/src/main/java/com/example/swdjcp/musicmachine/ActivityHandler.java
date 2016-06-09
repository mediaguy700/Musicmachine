package com.example.swdjcp.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by SWDJCP on 6/6/2016.
 */
public class ActivityHandler extends Handler {
    private MainActivity mMainActivity;


    public ActivityHandler(MainActivity mainActivity){

mMainActivity = mainActivity;

    }


    @Override
    public void handleMessage(Message msg) {
        if(msg.arg1 == 0) {
            //music is NOT playing


            if(msg.arg2 == 1){

                mMainActivity.changePlayButtonText("Play");

            }else {
                //play the music

                Message message = Message.obtain();
                message.arg1 = 0;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //change play button to say "Pause"

                mMainActivity.changePlayButtonText("Pause");
            }
        }else if (msg.arg1 == 1){

            if(msg.arg2 == 1){

mMainActivity.changePlayButtonText("pause");

            }else {


                //Music is playing
                //Pause the music
                //Change play button to say "Play"

                mMainActivity.changePlayButtonText("Play");
            }

            Message message = Message.obtain();
            message.arg1 = 1;
            try {
                msg.replyTo.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }
    }
}

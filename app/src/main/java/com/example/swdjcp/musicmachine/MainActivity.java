package com.example.swdjcp.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONG ="song" ;
    private boolean mBound = false;

    private Button mDownloadbutton;
    private Button mPlayButton;
    private Messenger mServiceMessenger;
    private Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));



    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mBound = true;
            mServiceMessenger = new Messenger(binder);
            Message message = Message.obtain();
            message.arg1 = 2;
            message.arg2 = 1;
            message.replyTo = mActivityMessenger;
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mDownloadbutton = (Button) findViewById(R.id.downbutton);
        mPlayButton = (Button)  findViewById(R.id.playbutton);

        mDownloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"downloading",Toast.LENGTH_LONG).show();

                //snd messages  to Handler for processing

               for(String song : Playlist.songs){

                   Intent intent = new Intent(MainActivity.this,DownloadService.class);
                   intent.putExtra(KEY_SONG,song);
                   startService(intent);

               }

            }
        });


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mBound){

                    Intent intent = new Intent(MainActivity.this , PlayerService.class);
                     startService(intent);

                    Message message = Message.obtain();
                    message.arg1 = 2;
                    message.replyTo = mActivityMessenger;
                    try {
                        mServiceMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }


                }

            }
        });




    }

    public void changePlayButtonText(String text){
            mPlayButton.setText(text);

    }





    @Override
    protected void onStart() {
        super.onStart();


        Intent intent = new Intent (this , PlayerService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);


    }


    @Override
    protected void onStop() {
        super.onStop();


        if(mBound) {

            unbindService(mServiceConnection);


            mBound = false;
        }
    }
}

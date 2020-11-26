package in.astroconsult.astroconsult.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ServerValue;

import in.astroconsult.astroconsult.Chat.ChatActivity;
import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.Constants;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.EndChatResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendEndChatService extends Service {
    private static final String API_KEY = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String astroMob = "", userMob = "", duration = "", timestamp = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("patchsharma", "onStartCommand");


        if(intent.hasExtra(Constants.SUMMARY_END_CHAT_ASTRO_MOB)){
            astroMob = intent.getStringExtra(Constants.SUMMARY_END_CHAT_ASTRO_MOB);
        }
        if(intent.hasExtra(Constants.SUMMARY_END_CHAT_USER_MOB)){
            userMob = intent.getStringExtra(Constants.SUMMARY_END_CHAT_USER_MOB);
        }
        if(intent.hasExtra(Constants.SUMMARY_END_CHAT_TIMESTAMP)){
            duration = intent.getStringExtra(Constants.SUMMARY_END_CHAT_TIMESTAMP);
        }
        if(intent.hasExtra(Constants.SUMMARY_END_CHAT_DURATION)){
            timestamp = intent.getStringExtra(Constants.SUMMARY_END_CHAT_DURATION);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
               // setInProgressNotification();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForeground(10001, generateNotfication());
                }else {
                    generateNotfication();
                }
            }
        }).start();
        return START_NOT_STICKY;
    }



    private void setInProgressNotification() {
        try {
            //startservice in foreground fot oreo and onwards other show only notication
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(10001, generateNotfication());
            }else {
                generateNotfication();
            }
        } catch (Exception e) {

        }
    }


    final String CHANNEL_ID = "com.astro.pushnotification";
    final String CHANNEL_NAME = "Astro Push Notifications";
    final String CHANNEL_DESC = "push notification to end chat";
//    PendingIntent pendingIntent = null;
//    String title = "", body = "", subText = "";
//    int smallIcon, largeIcon = 0, stickyColor = 0;
    Notification.Builder notificationBuilder1;
    NotificationCompat.Builder notificationBuilder2;

    public Notification generateNotfication() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationBuilder1 = new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("Support Chat!")
                        .setContentText("We're here to solve your problems!")
                        .setShowWhen(true)
                        .setSmallIcon(R.drawable.ic_help)
                        .setAutoCancel(false);

                notificationBuilder1.setOngoing(true)
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));

                return notificationBuilder1.build();
                //startForeground(199, notificationBuilder1.build());
            } else {
                notificationBuilder2 = new NotificationCompat.Builder(this)
                        .setContentTitle("Support Chat!")
                        .setContentText("We're here to solve your problems!")
                        .setShowWhen(true)
                        .setSmallIcon(R.drawable.ic_help)
                        .setAutoCancel(false);

                notificationBuilder2.setOngoing(true)
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));

                return notificationBuilder2.build();
                //startForeground(199, notificationBuilder2.build());
            }

        } catch (Exception e) {
            return null;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("patchsharma", "onTaskRemoved12343");
        setInProgressNotification();
        ChatActivity.sendChatSummary1();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                endForeground();
                ChatActivity.cancelChatTimer();
                ChatActivity.resetValues();
            }
        }, 2000);
    }


    public void sendChatSummary() {
        Call<EndChatResponse> call = ApiClient.getCliet().endChat(API_KEY, astroMob, userMob, timestamp, duration);
        Log.d("patchsharma", "endchat");
        call.enqueue(new Callback<EndChatResponse>() {
            @Override
            public void onResponse(Call<EndChatResponse> call, Response<EndChatResponse> response) {
                Log.d("patchsharma", "endchat onResponse");
                if (response.isSuccessful()) {
                    endForeground();
                }
                else {
                    endForeground();
                }
            }

            @Override
            public void onFailure(Call<EndChatResponse> call, Throwable throwable) {
                endForeground();
                Log.d("patchsharma", "endchat onFailure : " + throwable.getMessage());
            }
        });

    }

    private void endForeground() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true);
            }
            stopSelf();
        }catch (Exception e){

        }
    }
}
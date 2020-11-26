package in.astroconsult.astroconsult;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.firebase.database.ServerValue;

import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.services.StickyService;

public class Root extends Application implements LifecycleObserver {
    NotificationManager mNotificationManager;
    final String CHANNEL_ID = "com.astro.pushnotification";
    final String CHANNEL_NAME = "Astro Push Notifications";
    final String CHANNEL_DESC = "push notification to end chat";

    @Override
    public void onCreate() {
        super.onCreate();
        Intent stickyService = new Intent(getApplicationContext(), StickyService.class);
        startService(stickyService);

        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(getNotficationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_DESC));
        }
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void appInResumeState() {
        FirebaseAuthUtil.setCurrentUserOnline(true);
        //Toast.makeText(this,"In Foreground",Toast.LENGTH_LONG).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void appInPauseState() {
        FirebaseAuthUtil.setCurrentUserOnline(ServerValue.TIMESTAMP);
        //Toast.makeText(this,"In Background",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannel getNotficationChannel(String CHANNEL_ID, String CHANNEL_NAME, String CHANNEL_DESC){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(CHANNEL_DESC);
        channel.setShowBadge(true);
        channel.canShowBadge();
        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.enableVibration(false);
        channel.setSound(null, null);
        channel.setVibrationPattern(new long[]{0});
        return channel;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void disconnect() {
        //here you may add job you needed to fire in onTaskRemove()
        Log.d("patchsharma", "disconnect");

    }
}

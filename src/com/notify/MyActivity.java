package com.notify;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    View button;
    private NotificationCompat.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            PendingIntent pendingIntent;
            @Override
            public void onClick(View v) {
                buildNotif();
                createPendingIntent();
                builder.setContentIntent(pendingIntent);

            }

            private void createPendingIntent() {
                Intent resultIntent = new Intent(MyActivity.this,MyActivity.class);
                pendingIntent = PendingIntent.getActivity(MyActivity.this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            }

            private void buildNotif() {
               builder = new NotificationCompat.Builder(MyActivity.this)
                               .setSmallIcon(R.drawable.ic_launcher)
                               .setContentTitle("My notification")
                               .setContentText("Hello World!");

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(007,builder.build());
    }
}

package com.courseproject.controllers.background;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.courseproject.R;
import com.courseproject.database.AlertNotificationDatabase;
import com.courseproject.database.AssessmentDatabase;
import com.courseproject.database.CourseDatabase;
import com.courseproject.model.AlertNotification;
import com.courseproject.model.Assessment;
import com.courseproject.model.Course;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

public class AlertService extends IntentService {

    public AlertService() {

        super("AlertService");

    }

    // Starting the alert service.
    public static void startAlertService(Context context) {

        Intent intent = new Intent(context, AlertService.class);
        context.startService(intent);

    }

    // is service running
    public static boolean isAlertServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AlertService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            AlertNotificationDatabase db = new AlertNotificationDatabase(this);

            new Thread(() -> {

                Log.d("testingalert", "Started");
                while (true) {

                    Map<Integer, AlertNotification> alerts = db.get();

                    LocalDate date = LocalDate.now();
                    Log.d("testingalert", "Size: " + alerts.size());

                    for (Iterator<AlertNotification> iterator = alerts.values().iterator(); iterator.hasNext(); ) {

                        AlertNotification each = iterator.next();
                        Log.d("testingalert", "date: " + date + ", each: " + each.getDate());
                        if (each.getDate().isEqual(date)) {

                            generateNotification(each.getOccurence() + " Alert!",
                                    "Today, '" + each.getType() + "' " + each.getOccurence());
                            iterator.remove();
                            db.remove(each);

                        }

                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {}

                }

            }).start();


        }
    }

    protected void generateNotification(String title, String content) {

        Log.d("testingalert", "generating notification..");
        final Intent emptyIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "Course Console")
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(1, mBuilder.build());

    }

}
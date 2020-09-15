package doctor.dococean.com.doctorapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import doctor.dococean.com.doctorapp.services.BackgroundService;

/**
 * Created by nagendrasrivastava on 15/10/16.
 */

public class ServiceManager {

    private static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to stop ping service
     */
    public static void stopBackgroundService(Context context) {
        cancelAlarm(context);
        if (isServiceRunning(BackgroundService.class, context)) {
            try {
                Intent intent = new Intent(context, BackgroundService.class);
                context.stopService(intent);
            } catch (SecurityException seEx) {
                seEx.printStackTrace();
            }
        }

    }

    private static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, BackgroundService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


    public static void startBackgroundService(Context context, Bundle extras) {
        try {
            Intent intent = new Intent(context, BackgroundService.class);
            if (extras != null)
                intent.putExtras(extras);
            context.startService(intent);
        } catch (SecurityException seEx) {
            seEx.printStackTrace();
        }
    }


}

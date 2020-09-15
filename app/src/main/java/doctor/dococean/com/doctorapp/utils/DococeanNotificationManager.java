package doctor.dococean.com.doctorapp.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.db.DBHelper;
import doctor.dococean.com.doctorapp.views.activities.HomeActivity;
import doctor.dococean.com.doctorapp.views.activities.SplashActivity;

/**
 * Created by nagendrasrivastava on 05/11/16.
 */

public class DococeanNotificationManager {

    public static void sendNotification(Context context, String messageBody, String messageTitle, int notificationId) {
        if (DBHelper.getUserAuthKey(context) != null) {
            notificationIfUserIsLoggedIn(context, messageBody, messageTitle, notificationId);
        } else {
            notificationIfUserIsNotLoggedIn(context, messageBody, messageTitle, notificationId);
        }
    }

    private static void notificationIfUserIsLoggedIn(Context context, String messageBody, String messageTitle, int notificationId) {
        Intent intent = new Intent(context, HomeActivity.class);
        Bundle extras = new Bundle();
        extras.putString(DocOceanConstants.IntentConstants.SERVICE_TYPE, DocOceanConstants.ServiceType.SCHEDULED);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        displayNotification(context, messageBody, messageTitle, notificationId, pendingIntent);
    }


    private static void notificationIfUserIsNotLoggedIn(Context context, String messageBody, String messageTitle, int notificationId) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        displayNotification(context, messageBody, messageTitle, notificationId, pendingIntent);
    }

    private static void displayNotification(Context context, String messageBody, String messageTitle, int notificationId, PendingIntent pendingIntent) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

}

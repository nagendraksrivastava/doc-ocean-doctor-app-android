package doctor.dococean.com.doctorapp.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.firebase.incomingcommands.IncomingCommand;
import doctor.dococean.com.doctorapp.network.api.DocOceanRestApi;
import doctor.dococean.com.doctorapp.network.api.RetrofitProvider;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.utils.DococeanNotificationManager;
import doctor.dococean.com.doctorapp.utils.ServiceManager;
import doctor.dococean.com.doctorapp.views.activities.HomeActivity;

/**
 * Created by nagendrasrivastava on 13/08/16.
 */
public class DococeanFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private DocOceanRestApi mDocOceanRestApi = RetrofitProvider.getInstance().getRestApi();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d(TAG, " Json payload value is = " + new Gson().toJson(remoteMessage.getData()));
            processCommandMessage(remoteMessage);
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            DococeanNotificationManager.sendNotification(this, remoteMessage.getNotification().getBody(),
                    remoteMessage.getNotification().getTitle(), 0);

        }

    }


    private void processCommandMessage(RemoteMessage remoteMessage) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(remoteMessage.getData()));
            if (jsonObject.has("payload")) {
                IncomingCommand incomingCommand = new Gson().fromJson(jsonObject.getString("payload"), IncomingCommand.class);
                Log.d(TAG, "Incoming command value is = " + incomingCommand.getAppointmentNumber() + incomingCommand.getNotificationId());
                //notificationACKToServer(incomingCommand.getNotificationId());
                Bundle extras = new Bundle();
                extras.putString(DocOceanConstants.IntentConstants.BOOKING_ID, incomingCommand.getAppointmentNumber());
                extras.putInt(DocOceanConstants.IntentConstants.NOTIFICATION_ID, incomingCommand.getNotificationId());
                extras.putString(DocOceanConstants.IntentConstants.SERVICE_TYPE, incomingCommand.getServiceType());
                if (incomingCommand.getNotificationContent() != null)
                    extras.putParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA, incomingCommand.getNotificationContent());
                ServiceManager.startBackgroundService(this, extras);
            }
        } catch (JSONException jsex) {
            jsex.printStackTrace();
        }

    }
}

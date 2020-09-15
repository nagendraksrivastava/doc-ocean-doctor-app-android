package doctor.dococean.com.doctorapp.services;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.db.DBHelper;
import doctor.dococean.com.doctorapp.firebase.incomingcommands.NotificationContent;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.notificationack.NotificationAckResponse;
import doctor.dococean.com.doctorapp.network.response.singleappointmentresponse.SingleAppointmentResponse;
import doctor.dococean.com.doctorapp.presenters.AppointmentPresenter;
import doctor.dococean.com.doctorapp.presenters.NotificationPresenter;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.AppUtils;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.utils.DococeanNotificationManager;
import doctor.dococean.com.doctorapp.utils.ServiceManager;
import doctor.dococean.com.doctorapp.views.activities.HomeActivity;

public class BackgroundService extends Service implements UIUpdateListener {

    private static final String TAG = "BackgroundService";
    private AppointmentPresenter mAppointmentPresenter;
    private NotificationPresenter mNotificationPresenter;
    private int mNotificationId;
    private String mServiceType = DocOceanConstants.ServiceType.ON_DEMAND;
    private NotificationContent mNotificationContent;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initPresenters();
        if (intent != null && intent.getExtras() != null) {
            String bookingId = intent.getExtras().getString(DocOceanConstants.IntentConstants.BOOKING_ID);
            mNotificationId = intent.getExtras().getInt(DocOceanConstants.IntentConstants.NOTIFICATION_ID);
            mServiceType = intent.getExtras().getString(DocOceanConstants.IntentConstants.SERVICE_TYPE, DocOceanConstants.ServiceType.ON_DEMAND);
            mNotificationPresenter.notificationACk(this, mNotificationId);
            if (mServiceType.equalsIgnoreCase(DocOceanConstants.ServiceType.ON_DEMAND)) {
                mAppointmentPresenter.getSingleAppointment(this, bookingId);
            } else if (mServiceType.equalsIgnoreCase(DocOceanConstants.ServiceType.SCHEDULED)) {
                mNotificationContent = intent.getExtras().getParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA);
                if (mNotificationContent != null)
                    DococeanNotificationManager.sendNotification(this, mNotificationContent.getBody(), mNotificationContent.getTitle(), mNotificationId);
            }

        }
        return START_NOT_STICKY;
    }


    private void initPresenters() {
        if (mNotificationPresenter == null) {
            mNotificationPresenter = new NotificationPresenter();
            mNotificationPresenter.bindView(this);
        }
        if (mAppointmentPresenter == null) {
            mAppointmentPresenter = new AppointmentPresenter();
            mAppointmentPresenter.bindView(this);
        }
    }

    @Override
    public void onDestroy() {
        mAppointmentPresenter.unbindView(this);
        mAppointmentPresenter = null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void showLoading(boolean isLoading) {
        //DO NOTHING
    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof SingleAppointmentResponse) {
                SingleAppointmentResponse singleAppointmentResponse = (SingleAppointmentResponse) items;
                if (singleAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    DBHelper.clearSingleAppointmentTable(this);
                    Log.d(TAG, " notification ID before storing int DB = " + mNotificationId);
                    DBHelper.insertToBookingTable(this, mNotificationId, singleAppointmentResponse.getAppointment().getNumber(), new Gson().toJson(singleAppointmentResponse));
                    ServiceManager.stopBackgroundService(this);
                    ActivityManager.startOnDemandAcceptBookingActivity(this, null);
                    Crashlytics.log(DocOceanConstants.CrashlyticsConstants.SUCCESS, AppUtils.getDeviceId(getBaseContext()), "Successful single appointment response");
                } else {
                    Crashlytics.log(DocOceanConstants.CrashlyticsConstants.FAILURE, AppUtils.getDeviceId(getBaseContext()), "Unsuccessful single appointment response ");
                }
            } else if (items instanceof NotificationAckResponse) {
                NotificationAckResponse notificationAckResponse = (NotificationAckResponse) items;
                if (notificationAckResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    Crashlytics.log(DocOceanConstants.CrashlyticsConstants.SUCCESS, AppUtils.getDeviceId(getBaseContext()), " Successful notification ACK ");
                    if (mServiceType.equalsIgnoreCase(DocOceanConstants.ServiceType.SCHEDULED)) {
                        ServiceManager.stopBackgroundService(this);
                    }
                } else {
                    Crashlytics.log(DocOceanConstants.CrashlyticsConstants.SUCCESS, AppUtils.getDeviceId(getBaseContext()), " Unsuccessful notification ACK ");
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        if (error != null)
            Crashlytics.log(error.getMessage());
    }

}

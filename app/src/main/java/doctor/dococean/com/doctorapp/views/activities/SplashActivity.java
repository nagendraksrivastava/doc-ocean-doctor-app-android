package doctor.dococean.com.doctorapp.views.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.db.DBHelper;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.initafterlogin.InitAfterLoginResponse;
import doctor.dococean.com.doctorapp.presenters.AppointmentPresenter;
import doctor.dococean.com.doctorapp.presenters.SettingsPresenter;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.AppUtils;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends BaseActivity implements UIUpdateListener {

    private static final String TAG = "SplashActivity";
    private Handler mHandler = new Handler();
    private AppointmentPresenter mAppointmentPresenter;
    private SettingsPresenter mSettingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, " Fire base token = " + FirebaseInstanceId.getInstance().getToken());
        DococeanTextView dococean = (DococeanTextView) findViewById(R.id.dococean);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        dococean.startAnimation(animation);
        mAppointmentPresenter = new AppointmentPresenter();
        mSettingsPresenter = new SettingsPresenter();
        mSettingsPresenter.bindView(this);
        mAppointmentPresenter.bindView(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= Build.VERSION_CODES.M) {
                    requestRequiredPermissions();
                } else {
                    navigate();
                }
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        mAppointmentPresenter.unbindView(this);
        mAppointmentPresenter = null;
        super.onDestroy();
    }

    private void requestRequiredPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, DocOceanConstants.REQUIRED_PERMISSION.PERMISSION, DocOceanConstants.RUNTIME_PERMISSION.PERMISSION);
        } else {
            navigate();
        }
    }

    private void navigate() {
        if (DBHelper.getUserAuthKey(this) != null) {
            mSettingsPresenter.initAfterLogin(this);
        } else {
            ActivityManager.startLoginActivity(SplashActivity.this);
            this.finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case DocOceanConstants.RUNTIME_PERMISSION.PERMISSION:
                if (grantResults.length == 2) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED
                            ) {
                        navigate();
                    } else {
                        showShortToast(" Please give us required permission to use our app ");
                        Crashlytics.setUserIdentifier(AppUtils.getDeviceId(this));
                        Crashlytics.log(DocOceanConstants.CrashlyticsConstants.PERMISSION_DENIED, AppUtils.getDeviceId(this), " Not giving permissions at Splash Activity ");
                    }
                }
        }
    }


    private void callHome() {
        ActivityManager.startHomeActivity(this, null);
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            cancelProgressDialog();
            if (items instanceof InitAfterLoginResponse) {
                InitAfterLoginResponse initAfterLoginResponse = (InitAfterLoginResponse) items;
                if (initAfterLoginResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    if (initAfterLoginResponse.getData().getSignupPipeline().equalsIgnoreCase(DocOceanConstants.SignupPipeLine.COMPLETE)) {
                        callHome();
                        this.finish();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(DocOceanConstants.IntentConstants.SIGN_UP_PIPELINE, initAfterLoginResponse.getData().getSignupPipeline());
                        bundle.putParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA, initAfterLoginResponse);
                        ActivityManager.startSignupActivity(this, bundle);
                        this.finish();
                    }
                } else {
                    showShortToast(initAfterLoginResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }
}

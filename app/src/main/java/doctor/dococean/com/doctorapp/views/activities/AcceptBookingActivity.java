package doctor.dococean.com.doctorapp.views.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.db.DBHelper;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.network.response.notificationaccept.NotificationAcceptResponse;
import doctor.dococean.com.doctorapp.network.response.notificationreject.NotificationRejectResponse;
import doctor.dococean.com.doctorapp.network.response.singleappointmentresponse.SingleAppointmentResponse;
import doctor.dococean.com.doctorapp.presenters.NotificationPresenter;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.utils.DocOceanLogger;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;

public class AcceptBookingActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

    private static final String TAG = "AcceptBookingActivity";
    private DococeanTextView mBookingIdTv;
    private DococeanTextView mBookingTimeTv;
    private DococeanTextView mPatientNameTv;
    private DococeanTextView mPatientAgeTv;
    private DococeanTextView mDistanceTv;
    private DococeanTextView mSymptomTv;
    private DococeanTextView mInstructionTv;
    private DococeanTextView mPatientGenderTv;
    private DococeanTextView mAddressTV;
    private LinearLayout mAddressLL;
    private DocOceanButton mRejectButton;
    private DocOceanButton mAcceptButton;
    private NotificationPresenter mNotificationPresenter;
    private int mNotificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_booking);
        mNotificationPresenter = new NotificationPresenter();
        mNotificationPresenter.bindView(this);
        init();
        setToolbarTitle(getString(R.string.tool_bar_accept_booking));
        setHomeAsUpEnabled(false);
        inflateDataToViews();
        mAcceptButton.setOnClickListener(this);
        mRejectButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (mNotificationPresenter != null) {
            mNotificationPresenter.unbindView(this);
            mNotificationPresenter = null;
        }
        super.onDestroy();
    }

    private void init() {
        mBookingIdTv = (DococeanTextView) findViewById(R.id.booking_id_TV);
        mBookingTimeTv = (DococeanTextView) findViewById(R.id.booked_at_TV);
        mPatientNameTv = (DococeanTextView) findViewById(R.id.patient_name_TV);
        mPatientAgeTv = (DococeanTextView) findViewById(R.id.patient_age_TV);
        mDistanceTv = (DococeanTextView) findViewById(R.id.distance_TV);
        mSymptomTv = (DococeanTextView) findViewById(R.id.symptoms_TV);
        mInstructionTv = (DococeanTextView) findViewById(R.id.instruction_TV);
        mPatientGenderTv = (DococeanTextView) findViewById(R.id.patient_gender_TV);
        mRejectButton = (DocOceanButton) findViewById(R.id.reject_button);
        mAcceptButton = (DocOceanButton) findViewById(R.id.accept_button);
        mAddressTV = (DococeanTextView) findViewById(R.id.address_TV);
        mAddressLL = (LinearLayout) findViewById(R.id.address_LL);
    }

    private void inflateDataToViews() {
        String bookingData = DBHelper.getBookingData(this);
        SingleAppointmentResponse singleAppointmentResponse = new Gson().fromJson(bookingData, SingleAppointmentResponse.class);
        Appointment appointment = singleAppointmentResponse.getAppointment();
        mNotificationId = DBHelper.getNotificationId(this, appointment.getNumber());
        mBookingIdTv.setText(appointment.getNumber());
        mBookingTimeTv.setText(appointment.getCreatedAt());
        mPatientNameTv.setText(appointment.getPatientName());
        mPatientAgeTv.setText(String.valueOf(appointment.getPatientAge()));
        mPatientGenderTv.setText(appointment.getPatientGender());
        if (appointment.getDistance() != null)
            mDistanceTv.setText(String.valueOf(appointment.getDistance()));
        else
            mDistanceTv.setText("Not Available");
        mSymptomTv.setText(appointment.getSymptoms());
        mInstructionTv.setText(appointment.getInstructions());
        if (appointment.getPatientAddress() != null) {
            mAddressLL.setVisibility(View.VISIBLE);
            if (appointment.getPatientAddress().getCity() != null)
                mAddressTV.setText(appointment.getPatientAddress().getAddressLine() + " " + appointment.getPatientAddress().getCity());
            else
                mAddressTV.setText(appointment.getPatientAddress().getAddressLine());
        } else {
            mAddressLL.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reject_button:
                if (mNotificationId != DocOceanConstants.DEFAULT_NOTIFICATION_ID) {
                    mNotificationPresenter.onDemandBookingRejected(this, mNotificationId);
                    showProgressDialog(getString(R.string.text_reject_booking));
                } else
                    showShortToast(getString(R.string.no_notification_id_message));
                break;
            case R.id.accept_button:
                if (mNotificationId != DocOceanConstants.DEFAULT_NOTIFICATION_ID) {
                    mNotificationPresenter.onDemandBookingAccepted(this, mNotificationId);
                    showProgressDialog(getString(R.string.text_accept_booking));
                } else {
                    showShortToast(getString(R.string.no_notification_id_message));
                }
                break;
        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof NotificationAcceptResponse) {
                cancelProgressDialog();
                NotificationAcceptResponse notificationAcceptResponse = (NotificationAcceptResponse) items;
                if (notificationAcceptResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    ActivityManager.startHomeActivity(this, null);
                    this.finish();
                } else {
                    showShortToast(notificationAcceptResponse.getStatus().getMessage());
                }

            } else if (items instanceof NotificationRejectResponse) {
                cancelProgressDialog();
                NotificationRejectResponse notificationRejectResponse = (NotificationRejectResponse) items;
                if (notificationRejectResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    this.finish();
                } else {
                    showShortToast(notificationRejectResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }

    @Override
    public void onBackPressed() {
        showShortToast(getString(R.string.back_button_mesage_accept_booking));
    }
}

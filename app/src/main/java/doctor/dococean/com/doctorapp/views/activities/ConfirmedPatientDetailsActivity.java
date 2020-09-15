package doctor.dococean.com.doctorapp.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.request.AppointmentStatusRequest;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.Status;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.network.response.cancelappointment.CancelAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.completeappointment.CompleteAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.enrouteappointment.EnrouteAppointmentResponse;
import doctor.dococean.com.doctorapp.presenters.AppointmentPresenter;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;

public class ConfirmedPatientDetailsActivity extends ToolbarActivity implements
        View.OnClickListener, UIUpdateListener {

    private static final String TAG = "ConfirmedPatientDetails";
    private DococeanTextView mBookingIdTv;
    private DococeanTextView mBookingTimeTv;
    private DococeanTextView mPatientNameTv;
    private DococeanTextView mPatientAgeTv;
    private DococeanTextView mDistanceTv;
    private DococeanTextView mSymptomTv;
    private DococeanTextView mInstructionTv;
    private DococeanTextView mPatientGenderTv;
    private DococeanTextView mAddressTV;
    private ImageView mNavigateIV;
    private LinearLayout mAddressLL;
    private DocOceanButton mStartButton;
    private DocOceanButton mCompletedButton;
    private AppointmentPresenter mAppointmentPresenter;
    private String mAppointmentId;
    private DococeanTextView mPatientPlaceIndicator;
    Appointment mAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_patinet_details_acivity);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        setUpToolBar();
        setHomeAsUpEnabled(true);
        setToolbarTitle("Confirmed Patient Details ");
        init();
        Bundle bundle = getIntent().getBundleExtra(DocOceanConstants.IntentConstants.EXTRAS);
        mAppointment = bundle.getParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA);
        mAppointmentId = mAppointment.getNumber();
        mBookingIdTv.setText(mAppointment.getNumber());
        mBookingTimeTv.setText(mAppointment.getCreatedAt());
        mPatientNameTv.setText(mAppointment.getPatientName());
        mPatientAgeTv.setText(String.valueOf(mAppointment.getPatientAge()));
        mPatientGenderTv.setText(mAppointment.getPatientGender());
        mDistanceTv.setText("1.2KM");
        mSymptomTv.setText(mAppointment.getSymptoms());
        mInstructionTv.setText(mAppointment.getInstructions());
        Log.d(TAG, " servce plave is = " + mAppointment.getServicePlace());
        if (mAppointment.getServicePlace() != null && mAppointment.getServicePlace().equalsIgnoreCase(DocOceanConstants.ServicePlace.PATIENT_PLACE)) {
            mPatientPlaceIndicator.setVisibility(View.VISIBLE);
        } else {
            mPatientPlaceIndicator.setVisibility(View.GONE);
        }

        if (mAppointment.getPatientAddress() != null) {
            mAddressLL.setVisibility(View.VISIBLE);
            if (mAppointment.getPatientAddress().getCity() != null)
                mAddressTV.setText(mAppointment.getPatientAddress().getAddressLine() + " " + mAppointment.getPatientAddress().getCity());
            else
                mAddressTV.setText(mAppointment.getPatientAddress().getAddressLine());
        } else {
            mAddressLL.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        mAppointmentPresenter.unbindView(this);
        mAppointmentPresenter = null;
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
        mStartButton = (DocOceanButton) findViewById(R.id.starting_button);
        mPatientPlaceIndicator = (DococeanTextView) findViewById(R.id.expert_service_place_indicator_TV);
        mCompletedButton = (DocOceanButton) findViewById(R.id.completed_button);
        mAddressTV = (DococeanTextView) findViewById(R.id.address_TV);
        mAddressLL = (LinearLayout) findViewById(R.id.address_LL);
        mNavigateIV = (ImageView) findViewById(R.id.navigate_IV);
        mStartButton.setOnClickListener(this);
        mCompletedButton.setOnClickListener(this);
        mNavigateIV.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patient_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cancel) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.starting_button:
                mAppointmentPresenter.enrouteAppointment(this, mAppointmentId);
                break;
            case R.id.completed_button:
                mAppointmentPresenter.completedAppointment(this, mAppointmentId);
                break;
            case R.id.navigate_IV:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + mAppointment.getPatientAddress().getLatitude() + "," + mAppointment.getPatientAddress().getLatitude()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof EnrouteAppointmentResponse) {
                EnrouteAppointmentResponse enrouteAppointmentResponse = (EnrouteAppointmentResponse) items;
                if (enrouteAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast(getString(R.string.enroute_appointment_server_success_message));
                    mStartButton.setVisibility(View.GONE);
                    mCompletedButton.setVisibility(View.VISIBLE);
                } else {
                    showShortToast(enrouteAppointmentResponse.getStatus().getMessage());
                }
            } else if (items instanceof CompleteAppointmentResponse) {
                CompleteAppointmentResponse completeAppointmentResponse = (CompleteAppointmentResponse) items;
                if (completeAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast(getString(R.string.complete_appointment_server_success_message));
                    this.finish();
                } else {
                    showShortToast(completeAppointmentResponse.getStatus().getMessage());
                }
            } else if (items instanceof CancelAppointmentResponse) {
                cancelProgressDialog();
                CancelAppointmentResponse cancelAppointmentResponse = (CancelAppointmentResponse) items;
                if (cancelAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast(getString(R.string.cancelled_booking));
                    this.finish();
                } else {
                    showShortToast(cancelAppointmentResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmedPatientDetailsActivity.this);
        builder.setTitle(getString(R.string.dialog_booking_cancel_confirmation_title));
        builder.setMessage(getString(R.string.dialog_booking_cancel_confirmation_message));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAppointmentPresenter.cancelAppointment(ConfirmedPatientDetailsActivity.this, mAppointmentId);
                showProgressDialog(getString(R.string.text_please_wait));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

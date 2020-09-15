package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.request.AppointmentStatusRequest;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.Status;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.network.response.cancelappointment.CancelAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.confirmappointment.ConfirmAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.rejectappointment.RejectAppointmentResponse;
import doctor.dococean.com.doctorapp.presenters.AppointmentPresenter;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;

public class ScheduledPatientDetailsActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

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
    private DocOceanButton mAcceptButton;
    private DocOceanButton mRejectButton;

    private AppointmentPresenter mAppointmentPresenter;
    private String mAppointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        setUpToolBar();
        setHomeAsUpEnabled(true);
        setToolbarTitle(" Patient Details ");
        init();
        Bundle bundle = getIntent().getBundleExtra(DocOceanConstants.IntentConstants.EXTRAS);
        Appointment appointment = bundle.getParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA);
        mAppointmentId = appointment.getNumber();
        mBookingIdTv.setText(appointment.getNumber());
        mBookingTimeTv.setText(appointment.getCreatedAt());
        mPatientNameTv.setText(appointment.getPatientName());
        mPatientAgeTv.setText(String.valueOf(appointment.getPatientAge()));
        mPatientGenderTv.setText(appointment.getPatientGender());
        mDistanceTv.setText("1.2KM");
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
        mAcceptButton = (DocOceanButton) findViewById(R.id.accept_button);
        mRejectButton = (DocOceanButton) findViewById(R.id.reject_button);
        mAddressTV = (DococeanTextView) findViewById(R.id.address_TV);
        mAddressLL = (LinearLayout) findViewById(R.id.address_LL);
        mAcceptButton.setOnClickListener(this);
        mRejectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accept_button:
                mAppointmentPresenter.confirmAppointment(this, mAppointmentId);
                break;
            case R.id.reject_button:
                mAppointmentPresenter.rejectAppointment(this, mAppointmentId);
                break;
        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof ConfirmAppointmentResponse) {
                ConfirmAppointmentResponse confirmAppointmentResponse = (ConfirmAppointmentResponse) items;
                if (confirmAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mAcceptButton.setOnClickListener(null);
                    mAcceptButton.setText("Accepted");
                    mRejectButton.setVisibility(View.GONE);
                    this.finish();
                } else {
                    showShortToast(confirmAppointmentResponse.getStatus().getMessage());
                }
            } else if (items instanceof RejectAppointmentResponse) {
                RejectAppointmentResponse rejectAppointmentResponse = (RejectAppointmentResponse) items;
                if (rejectAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast(getString(R.string.rejected_booking));
                    this.finish();
                } else {
                    showShortToast(rejectAppointmentResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }
}

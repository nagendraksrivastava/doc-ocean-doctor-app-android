package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;

public class BookingHistoryPatientDetailsActivity extends ToolbarActivity {

    private DococeanTextView mBookingIdTv;
    private DococeanTextView mBookingTimeTv;
    private DococeanTextView mPatientNameTv;
    private DococeanTextView mPatientAgeTv;
    private DococeanTextView mDistanceTv;
    private DococeanTextView mSymptomTv;
    private DococeanTextView mInstructionTv;
    private DococeanTextView mPatientGenderTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history_patient_details);
        setUpToolBar();
        setHomeAsUpEnabled(true);
        setToolbarTitle("Patient Details ");
        init();
        Bundle bundle = getIntent().getBundleExtra(DocOceanConstants.IntentConstants.EXTRAS);
        Appointment appointment = bundle.getParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA);
        mBookingIdTv.setText(appointment.getNumber());
        mBookingTimeTv.setText(appointment.getCreatedAt());
        mPatientNameTv.setText(appointment.getPatientName());
        mPatientAgeTv.setText(String.valueOf(appointment.getPatientAge()));
        mPatientGenderTv.setText(appointment.getPatientGender());
        mDistanceTv.setText("1.2KM");
        mSymptomTv.setText(appointment.getSymptoms());
        mInstructionTv.setText(appointment.getInstructions());
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
    }

}

package doctor.dococean.com.doctorapp.adapters;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;


public class BookingHistoryRecyclerViewAdapter extends RecyclerView.Adapter<BookingHistoryRecyclerViewAdapter.ViewHolder> {

    private final List<Appointment> mAppointmentList;

    public BookingHistoryRecyclerViewAdapter(List<Appointment> appointmentList) {
        mAppointmentList = appointmentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_my_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appointment appointment = mAppointmentList.get(position);
        holder.mBookingIdTv.setText(appointment.getNumber());
        holder.mBookingTimeTv.setText(appointment.getCreatedAt());
        holder.mPatientNameTv.setText(appointment.getPatientName());
        holder.mPatientAgeTv.setText(String.valueOf(appointment.getPatientAge()));
        holder.mPatientGenderTv.setText(appointment.getPatientGender());
        holder.mDistanceTv.setText("1.2KM");
        holder.mSymptomTv.setText(appointment.getSymptoms());
        holder.mInstructionTv.setText(appointment.getInstructions());
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DococeanTextView mBookingIdTv;
        private DococeanTextView mBookingTimeTv;
        private DococeanTextView mPatientNameTv;
        private DococeanTextView mPatientAgeTv;
        private DococeanTextView mDistanceTv;
        private DococeanTextView mSymptomTv;
        private DococeanTextView mInstructionTv;
        private DococeanTextView mPatientGenderTv;

        ViewHolder(View view) {
            super(view);
            mBookingIdTv = (DococeanTextView) view.findViewById(R.id.booking_id_TV);
            mBookingTimeTv = (DococeanTextView) view.findViewById(R.id.booked_at_TV);
            mPatientNameTv = (DococeanTextView) view.findViewById(R.id.patient_name_TV);
            mPatientAgeTv = (DococeanTextView) view.findViewById(R.id.patient_age_TV);
            mDistanceTv = (DococeanTextView) view.findViewById(R.id.distance_TV);
            mSymptomTv = (DococeanTextView) view.findViewById(R.id.symptoms_TV);
            mInstructionTv = (DococeanTextView) view.findViewById(R.id.instruction_TV);
            mPatientGenderTv = (DococeanTextView) view.findViewById(R.id.patient_gender_TV);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA, mAppointmentList.get(getAdapterPosition()));
            ActivityManager.startBookingHistoryDetailsActivity(view.getContext(), bundle);
        }
    }
}

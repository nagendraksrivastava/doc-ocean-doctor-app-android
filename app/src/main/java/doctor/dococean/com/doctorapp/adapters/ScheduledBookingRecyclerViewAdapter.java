package doctor.dococean.com.doctorapp.adapters;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.interfaces.RVAdapterCommunication;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;

public class ScheduledBookingRecyclerViewAdapter extends RecyclerView.Adapter<ScheduledBookingRecyclerViewAdapter.ViewHolder> {

    private final List<Appointment> mAppointmentList;
    private RVAdapterCommunication mRvAdapterCommunication;

    public ScheduledBookingRecyclerViewAdapter(List<Appointment> appointmentList, RVAdapterCommunication rvAdapterCommunication) {
        this.mAppointmentList = appointmentList;
        this.mRvAdapterCommunication = rvAdapterCommunication;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_scheduled_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Appointment appointment = mAppointmentList.get(position);
        holder.mBookingIdTv.setText(appointment.getNumber());
        holder.mBookingTimeTv.setText(appointment.getCreatedAt());
        holder.mPatientNameTv.setText(appointment.getPatientName());
        holder.mPatientAgeTv.setText(String.valueOf(appointment.getPatientAge()));
        holder.mPatientGenderTv.setText(appointment.getPatientGender());
        holder.mDistanceTv.setText("1.2KM");
        holder.mSymptomTv.setText(appointment.getSymptoms());
        holder.mInstructionTv.setText(appointment.getInstructions());
        if (appointment.getPatientAddress() != null) {
            holder.mAddressLL.setVisibility(View.VISIBLE);
            if (appointment.getPatientAddress().getCity() != null)
                holder.mAddressTV.setText(appointment.getPatientAddress().getAddressLine() + " " + appointment.getPatientAddress().getCity());
            else
                holder.mAddressTV.setText(appointment.getPatientAddress().getAddressLine());
        } else {
            holder.mAddressLL.setVisibility(View.GONE);
        }
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
        private DocOceanButton mAcceptButton;
        private DocOceanButton mRejectButton;
        private DococeanTextView mAddressTV;
        private LinearLayout mAddressLL;


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
            mAcceptButton = (DocOceanButton) view.findViewById(R.id.accept_button);
            mRejectButton = (DocOceanButton) view.findViewById(R.id.reject_button);
            mAddressTV = (DococeanTextView) view.findViewById(R.id.address_TV);
            mAddressLL = (LinearLayout) view.findViewById(R.id.address_LL);
            view.setOnClickListener(this);
            mAcceptButton.setOnClickListener(this);
            mRejectButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.accept_button:
                    mRvAdapterCommunication.onAccept(mAppointmentList.get(getAdapterPosition()).getNumber());
                    break;
                case R.id.reject_button:
                    mRvAdapterCommunication.onReject(mAppointmentList.get(getAdapterPosition()).getNumber());
                    break;
                default:
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA, mAppointmentList.get(getAdapterPosition()));
                    ActivityManager.startSchedulePatinetDetailsActivity(view.getContext(), bundle);
                    break;
            }
        }
    }
}

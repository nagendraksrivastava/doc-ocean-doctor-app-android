package doctor.dococean.com.doctorapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.network.request.Availability;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;
import doctor.dococean.com.doctorapp.views.fragments.AvailabilityFragment;

/**
 * Created by nagendrasrivastava on 03/09/16.
 */
public class AvailibilityRecyclerAdapter extends RecyclerView.Adapter<AvailibilityRecyclerAdapter.AvailabiltyViewHolder> {


    private List<Availability> mAvailabilityList;
    private AvailabilityFragment mAvailabilityFragment;

    public AvailibilityRecyclerAdapter(AvailabilityFragment signupFragmentStepThree, List<Availability> mAvailabilityList) {
        this.mAvailabilityList = mAvailabilityList;
        this.mAvailabilityFragment = signupFragmentStepThree;
    }

    @Override
    public AvailabiltyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AvailabiltyViewHolder viewHolder = new AvailabiltyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expert_availibility_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AvailabiltyViewHolder holder, int position) {
        Availability availibality = mAvailabilityList.get(position);
        holder.mChoosenDayTv.setText(availibality.getDay());
        holder.mChoosenFromDateTV.setText(availibality.getStartTime());
        holder.mChoosenToDateTV.setText(availibality.getEndTime());
    }

    @Override
    public int getItemCount() {
        return mAvailabilityList.size();
    }


    class AvailabiltyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DococeanTextView mChoosenDayTv;
        private DococeanTextView mChoosenFromDateTV;
        private DococeanTextView mChoosenToDateTV;
        private DococeanTextView mDeleteAvailTV;


        public AvailabiltyViewHolder(View itemView) {
            super(itemView);
            mChoosenDayTv = (DococeanTextView) itemView.findViewById(R.id.expert_chossen_day);
            mChoosenFromDateTV = (DococeanTextView) itemView.findViewById(R.id.expert_chossen_from_time);
            mChoosenToDateTV = (DococeanTextView) itemView.findViewById(R.id.expert_chossen_to_time);
            mDeleteAvailTV = (DococeanTextView) itemView.findViewById(R.id.expert_delete_chosen_time);
            mDeleteAvailTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.expert_delete_chosen_time:
                    mAvailabilityFragment.onDelete(getAdapterPosition());
                    break;
            }
        }
    }
}

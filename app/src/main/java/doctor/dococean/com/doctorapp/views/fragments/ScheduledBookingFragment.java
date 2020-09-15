package doctor.dococean.com.doctorapp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.adapters.ScheduledBookingRecyclerViewAdapter;
import doctor.dococean.com.doctorapp.interfaces.RVAdapterCommunication;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.AppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.confirmappointment.ConfirmAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.rejectappointment.RejectAppointmentResponse;
import doctor.dococean.com.doctorapp.presenters.AppointmentPresenter;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.activities.HomeActivity;


public class ScheduledBookingFragment extends BaseFragment implements UIUpdateListener, RVAdapterCommunication {


    private AppointmentPresenter mAppointmentPresenter;
    private RecyclerView mRecyclerView;
    private List<Appointment> mAppointmentList;
    private ScheduledBookingRecyclerViewAdapter mScheduledBookingRecyclerViewAdapter;
    private HomeActivity mHomeActivity;

    public ScheduledBookingFragment() {
    }


    public static ScheduledBookingFragment newInstance() {
        return new ScheduledBookingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scheduled_booking, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mScheduledBookingRecyclerViewAdapter);
        showProgressDialog(getString(R.string.text_general_progress_dialog), mHomeActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAppointmentPresenter.getAppointments(getContext(), DocOceanConstants.AppointmentStatus.SCHEDULED);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity)
            mHomeActivity = (HomeActivity) context;
        mAppointmentList = new ArrayList<>();
        mScheduledBookingRecyclerViewAdapter = new ScheduledBookingRecyclerViewAdapter(mAppointmentList, this);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
    }

    @Override
    public void onDetach() {
        mAppointmentPresenter.unbindView(this);
        super.onDetach();
    }

    private void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof AppointmentResponse) {
                cancelProgressDialog();
                if (mAppointmentList != null && mAppointmentList.size() > 0) {
                    mAppointmentList.clear();
                }
                AppointmentResponse appointmentResponse = (AppointmentResponse) items;
                if (appointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mAppointmentList.addAll(appointmentResponse.getAppointments());
                    mScheduledBookingRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    showShortToast(appointmentResponse.getStatus().getMessage());
                }
            } else if (items instanceof ConfirmAppointmentResponse) {
                ConfirmAppointmentResponse confirmAppointmentResponse = (ConfirmAppointmentResponse) items;
                if (confirmAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mHomeActivity.reloadData();
                    mAppointmentPresenter.getAppointments(getContext(), DocOceanConstants.AppointmentStatus.SCHEDULED);
                } else {
                    showShortToast(confirmAppointmentResponse.getStatus().getMessage());
                }
            } else if (items instanceof RejectAppointmentResponse) {
                RejectAppointmentResponse rejectAppointmentResponse = (RejectAppointmentResponse) items;
                if (rejectAppointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast(getString(R.string.rejected_booking));
                    mAppointmentPresenter.getAppointments(getContext(), DocOceanConstants.AppointmentStatus.SCHEDULED);
                } else {
                    showShortToast(rejectAppointmentResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    @Override
    public void onAccept(String appointmentId) {
        mAppointmentPresenter.confirmAppointment(getContext(), appointmentId);

    }

    @Override
    public void onReject(String appointmentId) {
        mAppointmentPresenter.rejectAppointment(getContext(), appointmentId);
    }
}

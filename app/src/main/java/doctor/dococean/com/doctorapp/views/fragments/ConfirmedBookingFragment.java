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
import doctor.dococean.com.doctorapp.adapters.ConfirmedBookingRecyclerViewAdapter;
import doctor.dococean.com.doctorapp.interfaces.FromActivityToFragment;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.AppointmentResponse;
import doctor.dococean.com.doctorapp.presenters.AppointmentPresenter;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.activities.HomeActivity;


public class ConfirmedBookingFragment extends BaseFragment implements UIUpdateListener {

    private static AppointmentPresenter mAppointmentPresenter;
    private RecyclerView mRecyclerView;
    private ConfirmedBookingRecyclerViewAdapter mConfirmedBookingRecyclerViewAdapter;
    private List<Appointment> mAppointmentList;
    private HomeActivity mHomeActivity;
    private static Context mContext;


    public ConfirmedBookingFragment() {
    }

    public static ConfirmedBookingFragment newInstance() {
        return new ConfirmedBookingFragment();
    }

    public static void reloadConfirmDataAgain() {
        mAppointmentPresenter.getAppointments(mContext, DocOceanConstants.AppointmentStatus.CONFIRMED);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_immediate_booking, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mConfirmedBookingRecyclerViewAdapter);
        showProgressDialog(getString(R.string.text_general_progress_dialog), mHomeActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAppointmentPresenter.getAppointments(getContext(), DocOceanConstants.AppointmentStatus.CONFIRMED);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity)
            mHomeActivity = (HomeActivity) context;
        mAppointmentList = new ArrayList<>();
        mConfirmedBookingRecyclerViewAdapter = new ConfirmedBookingRecyclerViewAdapter(mAppointmentList);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
    }

    @Override
    public void onDetach() {
        mAppointmentPresenter.unbindView(this);
        super.onDetach();
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
                    mConfirmedBookingRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    showShortToast(appointmentResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
    }
}

package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.adapters.BookingHistoryRecyclerViewAdapter;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.AppointmentResponse;
import doctor.dococean.com.doctorapp.presenters.AppointmentPresenter;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;

public class BookingHistoryActivity extends ToolbarActivity implements UIUpdateListener {

    private AppointmentPresenter mAppointmentPresenter;
    private RecyclerView mRecyclerView;
    private List<Appointment> mAppointmentList;
    private BookingHistoryRecyclerViewAdapter mBookingHistoryRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        setToolbarTitle("My Bookings");
        setHomeAsUpEnabled(true);
        mAppointmentPresenter = new AppointmentPresenter();
        mAppointmentPresenter.bindView(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mAppointmentList = new ArrayList<>();
        mBookingHistoryRecyclerViewAdapter = new BookingHistoryRecyclerViewAdapter(mAppointmentList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mBookingHistoryRecyclerViewAdapter);
        mAppointmentPresenter.getAppointments(this, DocOceanConstants.AppointmentStatus.COMPLETED);
    }

    @Override
    protected void onDestroy() {
        mAppointmentPresenter.unbindView(this);
        mAppointmentPresenter = null;
        super.onDestroy();
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {

        if (items != null) {
            if (items instanceof AppointmentResponse) {
                AppointmentResponse appointmentResponse = (AppointmentResponse) items;
                if (appointmentResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mAppointmentList.addAll(appointmentResponse.getAppointments());
                    mBookingHistoryRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    showShortToast(appointmentResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }
}

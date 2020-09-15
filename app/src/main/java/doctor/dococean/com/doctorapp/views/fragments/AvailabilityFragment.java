package doctor.dococean.com.doctorapp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.adapters.AvailibilityRecyclerAdapter;
import doctor.dococean.com.doctorapp.datetimepicker.SlideDayTimeListener;
import doctor.dococean.com.doctorapp.datetimepicker.SlideDayTimePicker;
import doctor.dococean.com.doctorapp.interfaces.AvailabilityDelete;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.request.Availability;
import doctor.dococean.com.doctorapp.network.request.SignupAddAvailabilityRequest;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.signupaddavailability.SignupAddAvailabilityResponse;
import doctor.dococean.com.doctorapp.presenters.AuthPresenter;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.activities.SignupActivity;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;


public class AvailabilityFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, AvailabilityDelete, UIUpdateListener {

    private static final String TAG = "AvailabilityFragment";
    private String mSelectedAddress;
    //private DococeanTextView mSelectedAddressTV;
    private DocOceanButton mSaveAvailabilityButton;
    private DocOceanButton mAddAvailability;
    // private Spinner mExpertDaySpinner;
    //private Spinner mExpertFromTimeSpinner;
    //private Spinner mExpertToTimeSpinner;
    //private DocOceanButton mAddAvailabilityButton;
    //private DocOceanEditText mSelectedAddressEt;
    private RecyclerView mAvailabilityRecyclerView;
    private AvailibilityRecyclerAdapter mAvailibilityRecyclerAdapter;
    private List<Availability> mAvailibalityList = new ArrayList<>();
    private String mDay;
    private String mFromTIme;
    private String mToTime;
    private int mAddressId;
    private AuthPresenter mAuthPresenter;
    private SignupActivity mSignupActivity;
    private boolean isBothTimeSelected = false;
    private int mSelectedDay;
    private int mSelectedStartTimeHour;
    private int mSelectedStartTimeMinute;
    private int mSelectedEndTimeHour;
    private int mSelectedEndTimeMinute;
    private int mSelectedEndTime;


    final SlideDayTimeListener listener = new SlideDayTimeListener() {

        @Override
        public void onDayTimeSet(int day, int hour, int minute) {
            if (!isBothTimeSelected) {
                mSelectedDay = day;
                //mDay = getDayName(day);
                mSelectedStartTimeHour = hour;
                mSelectedStartTimeMinute = minute;
//                if (minute < 10)
//                    mFromTIme = String.valueOf(hour) + ":0" + String.valueOf(minute);
//                else
//                    mFromTIme = String.valueOf(hour) + ":" + String.valueOf(minute);
                showShortToast("Select End Time ");
                displayDayTimeSelector(day);
                isBothTimeSelected = true;
            } else {
                mSelectedEndTimeHour = hour;
                mSelectedEndTimeMinute = minute;
//                if (minute < 10)
//                    mToTime = String.valueOf(hour) + ":0" + String.valueOf(minute);
//                else
//                    mToTime = String.valueOf(hour) + ":" + String.valueOf(minute);
                addAvailibilityToRvAdapter();
                isBothTimeSelected = false;
            }
        }

        @Override
        public void onDayTimeCancel() {
            isBothTimeSelected = false;
        }
    };

    public AvailabilityFragment() {
        // Required empty public constructor
    }

    public static AvailabilityFragment newInstance(int addressId, String selectedAddress) {
        AvailabilityFragment fragment = new AvailabilityFragment();
        Bundle args = new Bundle();
        args.putInt(DocOceanConstants.IntentConstants.SELECTED_ADDRESS_ID, addressId);
        args.putString(DocOceanConstants.IntentConstants.SELECTED_ADDRESS, selectedAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthPresenter = new AuthPresenter();
        mAuthPresenter.bindView(this);
        if (getArguments() != null) {
            mSelectedAddress = getArguments().getString(DocOceanConstants.IntentConstants.SELECTED_ADDRESS);
            mAddressId = getArguments().getInt(DocOceanConstants.IntentConstants.SELECTED_ADDRESS_ID);
        }
    }

    @Override
    public void onDestroy() {
        if (mAuthPresenter != null) {
            mAuthPresenter.unbindView(this);
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_availability, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setListener();
        addDataToDaySpinner();
        addDataToFromTimeSpinner();
        addDataToTimeSpinner();
        //mSelectedAddressTV.setText(mSelectedAddress);
        setupToolBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignupActivity)
            mSignupActivity = (SignupActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void init(View view) {
        //mSelectedAddressTV = (DococeanTextView) view.findViewById(R.id.selected_address_TV);
        mSaveAvailabilityButton = (DocOceanButton) view.findViewById(R.id.save_availabily_button);
        mAddAvailability = (DocOceanButton) view.findViewById(R.id.expert_add_availability_button);
        //mExpertDaySpinner = (Spinner) view.findViewById(R.id.expert_day_spinner);
        //mExpertFromTimeSpinner = (Spinner) view.findViewById(R.id.expert_from_time_spinner);
        //mExpertToTimeSpinner = (Spinner) view.findViewById(R.id.expert_to_time_spinner);
        //mAddAvailabilityButton = (DocOceanButton) view.findViewById(R.id.expert_add_availability_button);
        mAvailabilityRecyclerView = (RecyclerView) view.findViewById(R.id.expert_availability_RV);
        mAvailabilityRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAvailabilityRecyclerView.setLayoutManager(layoutManager);
        mAvailibilityRecyclerAdapter = new AvailibilityRecyclerAdapter(this, mAvailibalityList);
        mAvailabilityRecyclerView.setAdapter(mAvailibilityRecyclerAdapter);
    }


    private void setListener() {
        mSaveAvailabilityButton.setOnClickListener(this);
        mAddAvailability.setOnClickListener(this);
        //mAddAvailabilityButton.setOnClickListener(this);
        //mExpertDaySpinner.setOnItemSelectedListener(this);
        //mExpertFromTimeSpinner.setOnItemSelectedListener(this);
        //mExpertToTimeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_availabily_button:
                SignupAddAvailabilityRequest signupAddAvailabilityRequest = new SignupAddAvailabilityRequest();
                signupAddAvailabilityRequest.setAddressId(mAddressId);
                signupAddAvailabilityRequest.setAvailabilities(mAvailibalityList);
                mAuthPresenter.signUpAddAvailibility(getContext(), signupAddAvailabilityRequest);
                showProgressDialog(getString(R.string.text_general_progress_dialog), mSignupActivity);
                break;
            case R.id.expert_add_availability_button:
                showShortToast("Select Start Time ");
                displayDayTimeSelector(1);
                //addAvailibilityToRvAdapter();
                break;
        }
    }

    private void addAvailibilityToRvAdapter() {
        if (mSelectedEndTimeHour <= mSelectedStartTimeHour) {
            showShortToast("Please select valid time ");
            return;
        }
        mDay = getDayName(mSelectedDay);
        if (mDay.equalsIgnoreCase("NONE")) {
            showShortToast("Please select Day and time availability");
            return;
        }

        if (mSelectedStartTimeMinute < 10)
            mFromTIme = String.valueOf(mSelectedStartTimeHour) + ":0" + String.valueOf(mSelectedStartTimeMinute);
        else
            mFromTIme = String.valueOf(mSelectedStartTimeHour) + ":" + String.valueOf(mSelectedStartTimeMinute);

        if (mSelectedEndTimeMinute < 10)
            mToTime = String.valueOf(mSelectedEndTimeHour) + ":0" + String.valueOf(mSelectedEndTimeMinute);
        else
            mToTime = String.valueOf(mSelectedEndTimeHour) + ":" + String.valueOf(mSelectedEndTimeMinute);
        Availability availability = new Availability();
        availability.setDay(mDay);
        availability.setStartTime(mFromTIme);
        availability.setEndTime(mToTime);
        //String lodatime = mFromTIme.replaceAll("[^0-9]", "").trim();
        //Log.d(TAG, " loda time is = " +lodatime);
//        int availStartTime = Integer.parseInt(mFromTIme.replaceAll("[^0-9]", "").trim());
//        int availEndTime = Integer.parseInt(mToTime.replaceAll("[^0-9]", "").trim());
//
//        if (availStartTime == availEndTime) {
//            showShortToast("start time and end time can not be same ");
//            return;
//        }
//
        if (mAvailibalityList.contains(availability)) {
            showShortToast(" Already added please add different timings ");
            return;
        }
        mAvailibalityList.add(availability);
        mAvailibilityRecyclerAdapter.notifyDataSetChanged();


    }

    @Override
    public void onDelete(int position) {
        mAvailibalityList.remove(position);
        mAvailibilityRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (adapterView.getId()) {
            case R.id.expert_day_spinner:
                if (adapterView.getItemAtPosition(position) != null) {
                    mDay = (String) adapterView.getItemAtPosition(position);
                }
                break;
            case R.id.expert_from_time_spinner:
                if (adapterView.getItemAtPosition(position) != null) {
                    mFromTIme = (String) adapterView.getItemAtPosition(position);
                }
                break;
            case R.id.expert_to_time_spinner:
                if (adapterView.getItemAtPosition(position) != null) {
                    mToTime = (String) adapterView.getItemAtPosition(position);
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void addDataToDaySpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.day_name_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //mExpertDaySpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, getContext(), getResources().getString(R.string.select_day)));
    }


    private void addDataToFromTimeSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.time_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //mExpertFromTimeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, getContext(), getResources().getString(R.string.select_from_time)));
    }

    private void addDataToTimeSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.time_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //mExpertToTimeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, getContext(), getResources().getString(R.string.select_to_time)));
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof SignupAddAvailabilityResponse) {
                SignupAddAvailabilityResponse signupAddAvailabilityResponse = (SignupAddAvailabilityResponse) items;
                if (signupAddAvailabilityResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    showShortToast("Availibilty added");
                    ActivityManager.startHomeActivity(getContext(), null);
                    mSignupActivity.finish();
                } else {
                    showShortToast(signupAddAvailabilityResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }

    private void setupToolBar() {
        if (mSignupActivity != null) {
            mSignupActivity.setToolbarTitle(getString(R.string.text_selected_address));
            mSignupActivity.setToolbarSubTitle(mSelectedAddress);
        }
    }

    private void displayDayTimeSelector(int day) {
        new SlideDayTimePicker.Builder(mSignupActivity.getSupportFragmentManager())
                .setListener(listener)
                .setInitialDay(day)
                .setInitialHour(8)
                .setInitialMinute(0)
                .setIs24HourTime(true)
                .build()
                .show();
    }

    private String getDayName(int day) {
        switch (day) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THU";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                return "NONE";
        }
    }
}

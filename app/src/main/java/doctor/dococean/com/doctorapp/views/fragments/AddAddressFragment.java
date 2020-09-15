package doctor.dococean.com.doctorapp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.request.Address;
import doctor.dococean.com.doctorapp.network.request.AddressRequest;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.addressresponse.AddressResponse;
import doctor.dococean.com.doctorapp.presenters.AddressPresenter;
import doctor.dococean.com.doctorapp.rvmodels.AddressModel;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.activities.SignupActivity;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DocOceanEditText;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;


public class AddAddressFragment extends BaseFragment implements View.OnClickListener, UIUpdateListener {

    private static final String TAG = "AddAddressFragment";
    private static final String HOME_ADDRESS_TAG = "home";
    private static final String WORK_ADDRESS_TAG = "work";
    private static final String CLINIC_ADDRESS_TAG = "clinic";
    private AddressModel mSelectedAddress;
    private DocOceanEditText mSelectedAddressEditText;
    private DocOceanEditText mBuildingNumberET;
    private DocOceanEditText mLandmarkEt;
    private DocOceanButton mSaveAddressButton;
    private LinearLayout mHomeAddressLL;
    private LinearLayout mWorkAddressLL;
    private LinearLayout mClinicAddressLL;
    private DococeanTextView mHomeAddressTV;
    private DococeanTextView mWorkAddressTV;
    private DococeanTextView mClinicAddressTV;

    private String mSelectedAddressTag;

    private AddressPresenter mAddressPresenter;
    private SignupActivity mSignupActivity;
    private String mDiplayAddress;


    public AddAddressFragment() {
        // Required empty public constructor
    }


    public static AddAddressFragment newInstance(AddressModel selectedAddress) {
        AddAddressFragment fragment = new AddAddressFragment();
        Bundle args = new Bundle();
        args.putParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS, selectedAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedAddress = getArguments().getParcelable(DocOceanConstants.IntentConstants.SELECTED_ADDRESS);
        }
        mAddressPresenter = new AddressPresenter();
        mAddressPresenter.bindView(this);
    }

    @Override
    public void onDestroy() {
        if (mAddressPresenter != null) {
            mAddressPresenter.unbindView(this);
            mAddressPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setOnClickListeners();
        mSelectedAddressEditText.setText(mSelectedAddress.getAddress());
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
        mSelectedAddressEditText = (DocOceanEditText) view.findViewById(R.id.address_Et);
        mBuildingNumberET = (DocOceanEditText) view.findViewById(R.id.building_flat_no_Et);
        mLandmarkEt = (DocOceanEditText) view.findViewById(R.id.landmark_Et);
        mSaveAddressButton = (DocOceanButton) view.findViewById(R.id.save_address);
        mHomeAddressLL = (LinearLayout) view.findViewById(R.id.home_address_layout);
        mWorkAddressLL = (LinearLayout) view.findViewById(R.id.work_address_layout);
        mClinicAddressLL = (LinearLayout) view.findViewById(R.id.clinic_address_layout);
        mHomeAddressTV = (DococeanTextView) view.findViewById(R.id.home_address_TV);
        mWorkAddressTV = (DococeanTextView) view.findViewById(R.id.work_address_TV);
        mClinicAddressTV = (DococeanTextView) view.findViewById(R.id.clinic_address_TV);
    }

    private void setOnClickListeners() {
        mSaveAddressButton.setOnClickListener(this);
        mHomeAddressLL.setOnClickListener(this);
        mWorkAddressLL.setOnClickListener(this);
        mClinicAddressLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_address:
                validateAndSendData();
                break;
            case R.id.home_address_layout:
                mSelectedAddressTag = HOME_ADDRESS_TAG;
                mHomeAddressTV.setTextColor(getResources().getColor(R.color.selected_address_color));
                mWorkAddressTV.setTextColor(getResources().getColor(R.color.black));
                mClinicAddressTV.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.work_address_layout:
                mSelectedAddressTag = WORK_ADDRESS_TAG;
                mHomeAddressTV.setTextColor(getResources().getColor(R.color.black));
                mWorkAddressTV.setTextColor(getResources().getColor(R.color.selected_address_color));
                mClinicAddressTV.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.clinic_address_layout:
                mSelectedAddressTag = CLINIC_ADDRESS_TAG;
                mHomeAddressTV.setTextColor(getResources().getColor(R.color.black));
                mWorkAddressTV.setTextColor(getResources().getColor(R.color.black));
                mClinicAddressTV.setTextColor(getResources().getColor(R.color.selected_address_color));
                break;
        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof AddressResponse) {
                cancelProgressDialog();
                AddressResponse addressResponse = (AddressResponse) items;
                if (addressResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mSignupActivity.changeFragmentNoBackstack(R.id.flContent, AvailabilityFragment.newInstance(addressResponse.getAddressId(), mDiplayAddress));
                } else {
                    showShortToast(addressResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void validateAndSendData() {
//        if (mBuildingNumberET.getText() == null || mBuildingNumberET.getText().toString().isEmpty()) {
//            mBuildingNumberET.setError("Enter building no ");
//            return;
//        }

        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();
        mDiplayAddress = mBuildingNumberET.getText().toString() + ", " + mSelectedAddress.getAddress();
        address.setAddressLine(mDiplayAddress);
        address.setLatitude(mSelectedAddress.getLatitude());
        address.setLongitude(mSelectedAddress.getLongitude());
        if (mSelectedAddressTag != null) {
            address.setTag(mSelectedAddressTag);
        }
        if (mLandmarkEt.getText() != null)
            address.setLandmark(mLandmarkEt.getText().toString().trim());
        addressRequest.setAddress(address);
        mAddressPresenter.sendAddress(getContext(), addressRequest);
        showProgressDialog(getString(R.string.text_general_progress_dialog), mSignupActivity);

    }

}

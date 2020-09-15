package doctor.dococean.com.doctorapp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.adapters.NothingSelectedSpinnerAdapter;
import doctor.dococean.com.doctorapp.adapters.ProfessionSpinnerArrayAdapter;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.request.ExpertDetail;
import doctor.dococean.com.doctorapp.network.request.SignupRequestExpertDetails;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.professionlist.Profession;
import doctor.dococean.com.doctorapp.network.response.professionlist.ProfessionListResponse;
import doctor.dococean.com.doctorapp.network.response.signupresponse.SignupExpertDetailsResponse;
import doctor.dococean.com.doctorapp.presenters.AuthPresenter;
import doctor.dococean.com.doctorapp.presenters.ProfessionPresenter;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.activities.SignupActivity;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DocOceanEditText;


public class SignupFragmentStepTwo extends BaseFragment implements View.OnClickListener, UIUpdateListener, AdapterView.OnItemSelectedListener {

    private Spinner mSelectProfessionSpinner;
    private Spinner mSelectServicePlaceSpinner;
    private DocOceanEditText mExpertDegreeEt;
    private DocOceanEditText mExpertSpecialisationEt;
    private DocOceanEditText mExpertExperianceNo;
    private DocOceanEditText mExpertLicenseNumberEt;
    private DocOceanEditText mExpertConsultingFee;
    private DocOceanButton mSubmitButton;
    private AuthPresenter mAuthPresenter;
    private ProfessionPresenter mProfessionPresenter;
    private SignupActivity mActivity;
    private Profession mSelectedProfession;
    private String mSelectedServicePlace;


    public SignupFragmentStepTwo() {
    }


    public static SignupFragmentStepTwo newInstance() {
        SignupFragmentStepTwo fragment = new SignupFragmentStepTwo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthPresenter = new AuthPresenter();
        mProfessionPresenter = new ProfessionPresenter();
        mProfessionPresenter.bindView(this);
        mAuthPresenter.bindView(this);
    }


    @Override
    public void onDestroy() {
        mAuthPresenter.unbindView(this);
        mProfessionPresenter.unbindView(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_fragment_step_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setListeners();
        mProfessionPresenter.getPropfessionList(getContext());
        showProgressDialog(getString(R.string.text_general_progress_dialog), mActivity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignupActivity) {
            mActivity = (SignupActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void init(View view) {
        mSelectProfessionSpinner = (Spinner) view.findViewById(R.id.select_profession_spinner);
        mSelectServicePlaceSpinner = (Spinner) view.findViewById(R.id.select_service_place_spinner);
        mExpertDegreeEt = (DocOceanEditText) view.findViewById(R.id.expert_degree_et);
        mExpertSpecialisationEt = (DocOceanEditText) view.findViewById(R.id.expert_specialisation_et);
        mExpertExperianceNo = (DocOceanEditText) view.findViewById(R.id.expert_experiance_no_et);
        mExpertLicenseNumberEt = (DocOceanEditText) view.findViewById(R.id.expert_license_no_et);
        mExpertConsultingFee = (DocOceanEditText) view.findViewById(R.id.expert_consulting_fee_et);
        mSubmitButton = (DocOceanButton) view.findViewById(R.id.submit_button);
        addDataToServicePlaceSpinner();
    }

    private void setListeners() {
        mSubmitButton.setOnClickListener(this);
        mSelectProfessionSpinner.setOnItemSelectedListener(this);
        mSelectServicePlaceSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                validateAndSendDataToServer();
                break;
        }
    }


    private void validateAndSendDataToServer() {

        if (mSelectedProfession == null) {
            showShortToast("Please select profession ");
            return;
        }

        if (mExpertDegreeEt.getText() == null || mExpertDegreeEt.getText().toString().isEmpty()) {
            mExpertDegreeEt.setError(" Please enter degree");
            return;
        }

        if (mExpertSpecialisationEt.getText() == null || mExpertSpecialisationEt.getText().toString().isEmpty()) {
            mExpertSpecialisationEt.setError(" Please enter specialisation");
            return;
        }

        if (mExpertExperianceNo.getText() == null || mExpertExperianceNo.getText().toString().isEmpty()) {
            mExpertExperianceNo.setError(" Please enter your experience no");
            return;
        }

        if (mExpertLicenseNumberEt.getText() == null || mExpertLicenseNumberEt.getText().toString().isEmpty()) {
            mExpertLicenseNumberEt.setError(" Please enter license no");
            return;
        }

        if (mExpertConsultingFee.getText() == null || mExpertConsultingFee.getText().toString().isEmpty()) {
            mExpertConsultingFee.setError(" Please enter your consulting fee ");
            return;
        }
        if (mSelectedServicePlace == null) {
            showShortToast("Please select service place ");
            return;
        }

        SignupRequestExpertDetails signupRequestExpertDetails = new SignupRequestExpertDetails();
        ExpertDetail expertDetail = new ExpertDetail();
        expertDetail.setProfessionId(mSelectedProfession.getId());
        expertDetail.setConsultingFee(Integer.parseInt(mExpertConsultingFee.getText().toString()));
        expertDetail.setLicenseNo(mExpertLicenseNumberEt.getText().toString());
        expertDetail.setSpecializationList(mExpertSpecialisationEt.getText().toString());

        if (mSelectedServicePlace.equalsIgnoreCase(DocOceanConstants.SelectServicePlaceConstants.PATIENT_PLACE)) {
            expertDetail.setServicePlace(DocOceanConstants.ServicePlace.PATIENT_PLACE);
        } else if (mSelectedServicePlace.equalsIgnoreCase(DocOceanConstants.SelectServicePlaceConstants.EXPERT_PLACE)) {
            expertDetail.setServicePlace(DocOceanConstants.ServicePlace.EXPERT_PLACE);
        } else if (mSelectedServicePlace.equalsIgnoreCase(DocOceanConstants.SelectServicePlaceConstants.BOTH)) {
            expertDetail.setServicePlace(DocOceanConstants.ServicePlace.ALL);
        }
        signupRequestExpertDetails.setExpertDetail(expertDetail);
        mAuthPresenter.signUpExpertDetails(getContext(), signupRequestExpertDetails);
        showProgressDialog(getString(R.string.text_general_progress_dialog), mActivity);

    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof ProfessionListResponse) {
                cancelProgressDialog();
                ProfessionListResponse professionListResponse = (ProfessionListResponse) items;
                addDataToProfessionSpinner(professionListResponse.getProfessions());
            } else if (items instanceof SignupExpertDetailsResponse) {
                cancelProgressDialog();
                SignupExpertDetailsResponse signupExpertDetailsResponse = (SignupExpertDetailsResponse) items;
                if (signupExpertDetailsResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    mActivity.changeFragmentNoBackstack(R.id.flContent, SignupFragmentStepThree.newInstance());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }

    private void addDataToProfessionSpinner(List<Profession> professionList) {
        ProfessionSpinnerArrayAdapter professionSpinnerArrayAdapter = new ProfessionSpinnerArrayAdapter(mActivity, professionList);
        professionSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectProfessionSpinner.setAdapter(new NothingSelectedSpinnerAdapter(professionSpinnerArrayAdapter, android.R.layout.simple_spinner_dropdown_item, getContext(), getResources().getString(R.string.select_profession)));
    }

    private void addDataToServicePlaceSpinner() {
        List<String> servicePlaceList = new ArrayList<>();
        servicePlaceList.add(DocOceanConstants.SelectServicePlaceConstants.PATIENT_PLACE);
        servicePlaceList.add(DocOceanConstants.SelectServicePlaceConstants.EXPERT_PLACE);
        servicePlaceList.add(DocOceanConstants.SelectServicePlaceConstants.BOTH);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, servicePlaceList);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectServicePlaceSpinner.setAdapter(new NothingSelectedSpinnerAdapter(stringArrayAdapter, android.R.layout.simple_spinner_dropdown_item, getContext(), getResources().getString(R.string.select_service_place)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.select_profession_spinner:
                if (adapterView.getItemAtPosition(position) != null) {
                    mSelectedProfession = (Profession) adapterView.getItemAtPosition(position);
                }
                break;
            case R.id.select_service_place_spinner:
                if (adapterView.getItemAtPosition(position) != null) {
                    mSelectedServicePlace = (String) adapterView.getItemAtPosition(position);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

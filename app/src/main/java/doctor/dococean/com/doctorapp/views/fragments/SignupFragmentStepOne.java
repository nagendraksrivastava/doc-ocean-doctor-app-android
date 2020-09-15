
package doctor.dococean.com.doctorapp.views.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.adapters.NothingSelectedSpinnerAdapter;
import doctor.dococean.com.doctorapp.db.DBHelper;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.request.SignUpRequest;
import doctor.dococean.com.doctorapp.network.request.User;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.signupresponse.SignupResponse;
import doctor.dococean.com.doctorapp.presenters.AuthPresenter;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.AppUtils;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.utils.DocOceanUtils;
import doctor.dococean.com.doctorapp.views.activities.SignupActivity;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DocOceanEditText;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;


public class SignupFragmentStepOne extends BaseFragment implements UIUpdateListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "SignupFragmentStepOne";
    private DocOceanEditText mFirstNameEt;
    private DocOceanEditText mLastNameEt;
    private DocOceanEditText mEmailEt;
    private DocOceanEditText mMobileEt;
    private DocOceanEditText mPasswordEt;
    private DocOceanEditText mReTypePasswordEt;
    private DocOceanEditText mDateOfBirthEt;
    private DocOceanButton mSignupButton;
    private TextInputLayout mDateOfBirthTIL;
    private String mDateOfBirthErrorMessage;

    private LinearLayout mAlreadyAccountTv;
    private DococeanTextView mAcceptTermsAndConditionTv;
    private AuthPresenter mAuthPresenter;
    private SignupActivity mSignupActivity;
    private Spinner mGenderSpinner;
    private String mExpertGender;


    public SignupFragmentStepOne() {
        // Required empty public constructor
    }

    public static SignupFragmentStepOne newInstance() {
        SignupFragmentStepOne fragment = new SignupFragmentStepOne();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_fragment_step_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
        initPresenters();
        setupSpannableForTermsAndCondition();
        addDataToGenderSpinner();
        mAuthPresenter.bindView(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignupActivity) {
            mSignupActivity = (SignupActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        mAuthPresenter.unbindView(this);
        super.onDestroy();
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        cancelProgressDialog();
        if (items != null) {
            if (items instanceof SignupResponse) {
                SignupResponse signupResponse = (SignupResponse) items;
                if (signupResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    DBHelper.saveToExpertUserTable(getContext(), signupResponse.getData().getName(), signupResponse.getData().getEmail(),
                            signupResponse.getData().getPhoneNo(), signupResponse.getData().getAuthToken(), signupResponse.getData().getImageUrl());
                    mSignupActivity.changeFragmentNoBackstack(R.id.flContent, SignupFragmentStepTwo.newInstance());
                } else {
                    showShortToast(signupResponse.getStatus().getMessage());
                }
            }
        }

    }

    @Override
    public void showError(Throwable error) {
        Log.d(TAG, " inside error is = " + error.getMessage());
    }


    private void initViews(View view) {
        mFirstNameEt = (DocOceanEditText) view.findViewById(R.id.first_name_et);
        mLastNameEt = (DocOceanEditText) view.findViewById(R.id.last_name_et);
        mEmailEt = (DocOceanEditText) view.findViewById(R.id.email_et);
        mMobileEt = (DocOceanEditText) view.findViewById(R.id.phone_no_et);
        mSignupButton = (DocOceanButton) view.findViewById(R.id.signup_button);
        mAlreadyAccountTv = (LinearLayout) view.findViewById(R.id.already_account_tv);
        mAcceptTermsAndConditionTv = (DococeanTextView) view.findViewById(R.id.accept_terms_and_condition);
        mPasswordEt = (DocOceanEditText) view.findViewById(R.id.password_et);
        mReTypePasswordEt = (DocOceanEditText) view.findViewById(R.id.re_password_et);
        mDateOfBirthEt = (DocOceanEditText) view.findViewById(R.id.expert_date_of_birth_ET);
        mDateOfBirthTIL = (TextInputLayout) view.findViewById(R.id.expert_date_of_birth_TIL);
        mGenderSpinner = (Spinner) view.findViewById(R.id.gender_spinner);

    }

    private void setListeners() {
        mSignupButton.setOnClickListener(this);
        mAlreadyAccountTv.setOnClickListener(this);
        mAcceptTermsAndConditionTv.setOnClickListener(this);
        mDateOfBirthEt.setOnClickListener(this);
        mGenderSpinner.setOnItemSelectedListener(this);
    }

    private void initPresenters() {
        mAuthPresenter = new AuthPresenter();
    }


    private void validateFieldsAndRegister() {
        if (mFirstNameEt.getText() == null || mFirstNameEt.getText().toString().isEmpty()) {
            mFirstNameEt.setError("Please enter first Name ");
            return;
        }

        if (mLastNameEt.getText() == null || mLastNameEt.getText().toString().isEmpty()) {
            mLastNameEt.setError("Please enter last Name ");
            return;
        }

        if (mExpertGender == null) {
            showShortToast("Please select gender");
            return;
        }

        if (mEmailEt.getText() == null || mEmailEt.getText().toString().isEmpty()) {
            mEmailEt.setError(" Please enter email Id ");
            return;
        } else if (!DocOceanUtils.isValidMail(mEmailEt.getText().toString().trim())) {
            mEmailEt.setError(" Please enter a valid email address ");
            return;
        }

        if (mMobileEt.getText() == null || mMobileEt.getText().toString().isEmpty()) {
            mMobileEt.setError(" Please enter mobile no ");
            return;
        }
        if (!DocOceanUtils.isValidMobile(mMobileEt.getText().toString().trim()) || mMobileEt.getText().toString().trim().length() < 10) {
            mMobileEt.setError(" Please enter a valid mobile no ");
            return;
        }

        if (mPasswordEt.getText() == null || mPasswordEt.getText().toString().isEmpty()) {
            mPasswordEt.setError(" Please enter password ");
            return;
        }

        if (mPasswordEt.getText().toString().trim().length() < 8) {
            mPasswordEt.setError(" password length can not be less than 8 ");
            return;
        }

        if (mReTypePasswordEt.getText() == null || mReTypePasswordEt.getText().toString().isEmpty()) {
            mReTypePasswordEt.setError(" Please re enter password ");
            return;
        }

        if (!mPasswordEt.getText().toString().trim().equals(mReTypePasswordEt.getText().toString().trim())) {
            mReTypePasswordEt.setError(" Password did not match ");
            return;
        }

        if (mDateOfBirthErrorMessage != null) {
            mDateOfBirthTIL.setError(mDateOfBirthErrorMessage);
            return;
        }
        SignUpRequest signUpRequest = new SignUpRequest();
        User user = new User();
        user.setName(mFirstNameEt.getText().toString().trim() + " " + mLastNameEt.getText().toString().trim());
        user.setEmail(mEmailEt.getText().toString().trim());
        user.setPassword(mPasswordEt.getText().toString().trim());
        user.setPhoneNo(mMobileEt.getText().toString().trim());
        user.setType(DocOceanConstants.USER_TYPE);
        user.setGender(mExpertGender);
        signUpRequest.setUser(user);
        signUpRequest.setDeviceId(AppUtils.getDeviceId(getContext()));
        mAuthPresenter.signUp(signUpRequest);
        AppUtils.hideKeyboard(mSignupActivity);
        showProgressDialog(getString(R.string.text_general_progress_dialog), mSignupActivity);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_button:
                validateFieldsAndRegister();
                break;
            case R.id.already_account_tv:
                ActivityManager.startLoginActivity(getContext());
                mSignupActivity.finish();
                break;
            case R.id.accept_terms_and_condition:
                break;
            case R.id.expert_date_of_birth_ET:
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }


    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        int userAge = 0;
        try {
            userAge = AppUtils.getAge(myCalendar.getTime());
            if (userAge >= 18) {
                mDateOfBirthEt.setText(sdf.format(myCalendar.getTime()));
                mDateOfBirthErrorMessage = null;
            } else {
                mDateOfBirthErrorMessage = getString(R.string.at_least_18_year_old_declaration);
                mDateOfBirthTIL.setError(getString(R.string.at_least_18_year_old_declaration));
            }

        } catch (IllegalArgumentException ex) {
            mDateOfBirthErrorMessage = getString(R.string.at_least_18_year_old_declaration);
            mDateOfBirthTIL.setError(getString(R.string.at_least_18_year_old_declaration));
        }
    }

    private void setupSpannableForTermsAndCondition() {
        String clickableText = "By signing up you are accepting our Terms and Conditions";
        SpannableString termsAndConditionSpannable = new SpannableString(clickableText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ActivityManager.startTermsAndConditionsActivity(getContext(), null);
            }
        };
        termsAndConditionSpannable.setSpan(clickableSpan, 35, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsAndConditionSpannable.setSpan(new ForegroundColorSpan(Color.RED), 36, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAcceptTermsAndConditionTv.setText(termsAndConditionSpannable);
        mAcceptTermsAndConditionTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getItemAtPosition(position) != null) {
            mExpertGender = adapterView.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void addDataToGenderSpinner() {
        List<String> genderList = new ArrayList<>();
        genderList.add("MALE");
        genderList.add("FEMALE");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mSignupActivity, android.R.layout.simple_spinner_item, genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, android.R.layout.simple_spinner_dropdown_item, mSignupActivity, getResources().getString(R.string.select_gender)));
    }
}

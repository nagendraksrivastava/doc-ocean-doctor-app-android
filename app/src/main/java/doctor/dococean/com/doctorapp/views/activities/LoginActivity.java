package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;
import android.view.View;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.datetimepicker.SlideDayTimeListener;
import doctor.dococean.com.doctorapp.datetimepicker.SlideDayTimePicker;
import doctor.dococean.com.doctorapp.db.DBHelper;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.request.LoginRequest;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.loginresponse.LoginResponse;
import doctor.dococean.com.doctorapp.presenters.AuthPresenter;
import doctor.dococean.com.doctorapp.utils.ActivityManager;
import doctor.dococean.com.doctorapp.utils.AppUtils;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.utils.DocOceanUtils;
import doctor.dococean.com.doctorapp.views.custom.DocOceanButton;
import doctor.dococean.com.doctorapp.views.custom.DocOceanEditText;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;

public class LoginActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

    private DocOceanEditText mUserNameEt;
    private DocOceanEditText mPasswordEt;
    private DocOceanButton mLoginButton;
    private DococeanTextView mForgotPasswordTv;
    private DococeanTextView mCreateNewAccountTv;
    private AuthPresenter mAuthPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setOnClickListeners();
        setToolbarTitle(getString(R.string.action_bar_login));
        setHomeAsUpEnabled(false);
        mAuthPresenter = new AuthPresenter();
        mAuthPresenter.bindView(this);
    }


    private void initViews() {
        mUserNameEt = (DocOceanEditText) findViewById(R.id.username_et);
        mPasswordEt = (DocOceanEditText) findViewById(R.id.password_et);
        mLoginButton = (DocOceanButton) findViewById(R.id.login);
        mForgotPasswordTv = (DococeanTextView) findViewById(R.id.forgot_password);
        mCreateNewAccountTv = (DococeanTextView) findViewById(R.id.create_new_account);
    }


    private void setOnClickListeners() {
        mLoginButton.setOnClickListener(this);
        mForgotPasswordTv.setOnClickListener(this);
        mCreateNewAccountTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                validateAndLogin();
                break;
            case R.id.forgot_password:
                ActivityManager.startForgotPasswordActivity(this, null);
                break;
            case R.id.create_new_account:
                ActivityManager.startSignupActivity(this, null);
                finish();
                break;
        }
    }

    private void validateAndLogin() {
        if (mUserNameEt.getText() == null || mUserNameEt.getText().toString().isEmpty()) {
            mUserNameEt.setError(" User name can not be empty ");
            return;
        }

        if (!DocOceanUtils.isValidMail(mUserNameEt.getText().toString().trim())) {
            mUserNameEt.setError(" Please enter your registered email ");
            return;
        }

        if (mPasswordEt.getText() == null || mPasswordEt.getText().toString().isEmpty()) {
            mPasswordEt.setError(" Please enter password");
            return;
        }
        AppUtils.hideKeyboard(this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(mUserNameEt.getText().toString().trim());
        loginRequest.setPassword(mPasswordEt.getText().toString().trim());
        loginRequest.setType(DocOceanConstants.USER_TYPE);
        loginRequest.setDeviceId(AppUtils.getDeviceId(this));
        mAuthPresenter.signIn(loginRequest);
        showProgressDialog(getString(R.string.text_general_progress_dialog));


    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof LoginResponse) {
                LoginResponse loginResponse = (LoginResponse) items;
                cancelProgressDialog();
                if (loginResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    DBHelper.saveToExpertUserTable(this, loginResponse.getData().getName(), loginResponse.getData().getEmail(),
                            loginResponse.getData().getPhoneNo(), loginResponse.getData().getAuthToken(), loginResponse.getData().getImageUrl());
                    if (loginResponse.getData().getSignupPipeline().equalsIgnoreCase(DocOceanConstants.SignupPipeLine.COMPLETE)) {
                        callHome();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(DocOceanConstants.IntentConstants.SIGN_UP_PIPELINE, loginResponse.getData().getSignupPipeline());
                        ActivityManager.startSignupActivity(this, bundle);
                        this.finish();
                    }
                } else {
                    showShortToast(loginResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {

    }

    private void callHome() {
        ActivityManager.startHomeActivity(this, null);
        this.finish();
    }
}

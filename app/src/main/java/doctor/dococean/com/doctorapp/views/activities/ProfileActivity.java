package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.response.ResponseCodes;
import doctor.dococean.com.doctorapp.network.response.userprofile.UserProfileResponse;
import doctor.dococean.com.doctorapp.presenters.ProfilePresenter;
import doctor.dococean.com.doctorapp.views.custom.DococeanTextView;

public class ProfileActivity extends ToolbarActivity implements View.OnClickListener, UIUpdateListener {

    private CircleImageView mUserImageIV;
    private DococeanTextView mExpertNameTV;
    private DococeanTextView mExpertDegreeTV;
    private DococeanTextView mExpertCountryTV;
    private DococeanTextView mExpertCityTV;
    private DococeanTextView mExpertProfessionTV;
    private DococeanTextView mExpertSpecilisationTV;
    private DococeanTextView mExpertLicenseNoTV;
    private DococeanTextView mExpertConsultingFeeTV;
    private DococeanTextView mExpertEmailTV;
    private DococeanTextView mExpertPhoneNoTv;
    private DococeanTextView mExpertServicePlaceTV;
    private ProfilePresenter mProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mProfilePresenter = new ProfilePresenter();
        mProfilePresenter.bindView(this);
        setToolbarTitle("View Profile");
        setHomeAsUpEnabled(true);
        init();
        showProgressDialog(getString(R.string.text_please_wait));
        mProfilePresenter.getUserProfile(this);
    }

    @Override
    protected void onDestroy() {
        if (mProfilePresenter != null) {
            mProfilePresenter.unbindView(this);
            mProfilePresenter = null;
        }
        super.onDestroy();
    }

    private void init() {
        mUserImageIV = (CircleImageView) findViewById(R.id.user_image);
        mExpertNameTV = (DococeanTextView) findViewById(R.id.profile_expert_name);
        mExpertDegreeTV = (DococeanTextView) findViewById(R.id.profile_expert_degree_TV);
        mExpertCountryTV = (DococeanTextView) findViewById(R.id.profile_country_name_TV);
        mExpertCityTV = (DococeanTextView) findViewById(R.id.profile_city_name_TV);
        mExpertProfessionTV = (DococeanTextView) findViewById(R.id.profile_expert_profession_TV);
        mExpertSpecilisationTV = (DococeanTextView) findViewById(R.id.profile_expert_specialisation_TV);
        mExpertLicenseNoTV = (DococeanTextView) findViewById(R.id.profile_expert_license_TV);
        mExpertConsultingFeeTV = (DococeanTextView) findViewById(R.id.profile_expert_consulting_fee_TV);
        mExpertEmailTV = (DococeanTextView) findViewById(R.id.profile_expert_email_TV);
        mExpertPhoneNoTv = (DococeanTextView) findViewById(R.id.profile_expert_phone_TV);
        mExpertServicePlaceTV = (DococeanTextView) findViewById(R.id.profile_expert_service_place_TV);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            cancelProgressDialog();
            if (items instanceof UserProfileResponse) {
                UserProfileResponse userProfileResponse = (UserProfileResponse) items;
                if (userProfileResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    infalteDataToUi(userProfileResponse);
                } else {
                    showShortToast(userProfileResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
    }

    private void infalteDataToUi(UserProfileResponse userProfileResponse) {
        mExpertConsultingFeeTV.setText(String.valueOf(userProfileResponse.getDetails().getExpertDetail().getConsultingFee()));
        mExpertLicenseNoTV.setText(userProfileResponse.getDetails().getExpertDetail().getLicenseNo());
        String specilisation = TextUtils.join(", ", userProfileResponse.getDetails().getExpertDetail().getSpecializationList());
        mExpertSpecilisationTV.setText(specilisation);
        mExpertEmailTV.setText(userProfileResponse.getDetails().getEmail());
        mExpertNameTV.setText(userProfileResponse.getDetails().getName());
        mExpertPhoneNoTv.setText(userProfileResponse.getDetails().getPhoneNo());
        mExpertCityTV.setText(userProfileResponse.getDetails().getCity());
        mExpertCountryTV.setText(userProfileResponse.getDetails().getCountry());
        mExpertProfessionTV.setText(userProfileResponse.getDetails().getExpertDetail().getProfession());
        String expertDegree = TextUtils.join(", ", userProfileResponse.getDetails().getExpertDetail().getDegreeList());
        mExpertDegreeTV.setText(expertDegree);
        mExpertServicePlaceTV.setText(userProfileResponse.getDetails().getExpertDetail().getServicePlace());
        if (userProfileResponse.getDetails().getImageUrl() != null)
            Glide.with(this).load(userProfileResponse.getDetails().getImageUrl()).placeholder(R.drawable.placeholder).into(mUserImageIV);
    }
}

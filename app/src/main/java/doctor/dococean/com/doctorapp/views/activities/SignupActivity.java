package doctor.dococean.com.doctorapp.views.activities;

import android.os.Bundle;
import android.util.Log;

import doctor.dococean.com.doctorapp.R;
import doctor.dococean.com.doctorapp.network.response.initafterlogin.InitAfterLoginResponse;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.views.fragments.AvailabilityFragment;
import doctor.dococean.com.doctorapp.views.fragments.SignupFragmentStepOne;
import doctor.dococean.com.doctorapp.views.fragments.SignupFragmentStepThree;
import doctor.dococean.com.doctorapp.views.fragments.SignupFragmentStepTwo;

public class SignupActivity extends ToolbarActivity {

    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setToolbarTitle("Register With Us ");
        setHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getBundleExtra(DocOceanConstants.IntentConstants.EXTRAS);
        String routeString = null;
        if (bundle != null) {
            routeString = bundle.getString(DocOceanConstants.IntentConstants.SIGN_UP_PIPELINE);
            Log.d(TAG, " route string value is = " + routeString);
        }
        if (routeString != null && routeString.equalsIgnoreCase(DocOceanConstants.SignupPipeLine.USER_INFO)) {
            changeFragmentNoBackstack(R.id.flContent, SignupFragmentStepTwo.newInstance());
        } else if (routeString != null && routeString.equalsIgnoreCase(DocOceanConstants.SignupPipeLine.ADDRESS)) {
            changeFragmentNoBackstack(R.id.flContent, SignupFragmentStepThree.newInstance());
        } else if (routeString != null && routeString.equalsIgnoreCase(DocOceanConstants.SignupPipeLine.AVAILABILITY)) {
            InitAfterLoginResponse initAfterLoginResponse = bundle.getParcelable(DocOceanConstants.IntentConstants.PARCELABLE_DATA);
            changeFragmentNoBackstack(R.id.flContent, AvailabilityFragment.newInstance(
                    initAfterLoginResponse.getData().getSignupPipelineData().getAddress().getId(),
                    initAfterLoginResponse.getData().getSignupPipelineData().getAddress().getAddressLine()));
        } else {
            changeFragmentNoBackstack(R.id.flContent, SignupFragmentStepOne.newInstance());
        }

    }
}

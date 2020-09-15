package doctor.dococean.com.doctorapp.presenters;

import android.content.Context;

import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.observables.DocOceanApiObservable;
import doctor.dococean.com.doctorapp.network.request.ForgotPasswordRequest;
import doctor.dococean.com.doctorapp.network.request.LoginRequest;
import doctor.dococean.com.doctorapp.network.request.SignUpRequest;
import doctor.dococean.com.doctorapp.network.request.SignupAddAvailabilityRequest;
import doctor.dococean.com.doctorapp.network.request.SignupRequestExpertDetails;
import doctor.dococean.com.doctorapp.network.response.forgotpassword.ForgotPasswordResponse;
import doctor.dococean.com.doctorapp.network.response.loginresponse.LoginResponse;
import doctor.dococean.com.doctorapp.network.response.logoutresponse.LogoutResponse;
import doctor.dococean.com.doctorapp.network.response.signupaddavailability.SignupAddAvailabilityResponse;
import doctor.dococean.com.doctorapp.network.response.signupresponse.SignupExpertDetailsResponse;
import doctor.dococean.com.doctorapp.network.response.signupresponse.SignupResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public class AuthPresenter extends BasePresenter<UIUpdateListener> {

    public void signIn(LoginRequest loginRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<LoginResponse> signInObs = DocOceanApiObservable.getInstance().signInObservable(loginRequest);
            Subscription signInSubscription = signInObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(signInSubscription);
        }
    }


    public void signUp(SignUpRequest signUpRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<SignupResponse> signInObs = DocOceanApiObservable.getInstance().signUpObservable(signUpRequest);
            Subscription signUpSubscription = signInObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(signUpSubscription);
        }
    }

    public void signUpExpertDetails(Context context, SignupRequestExpertDetails signUpRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<SignupExpertDetailsResponse> signInObs = DocOceanApiObservable.getInstance().signUpExpertObservable(context, signUpRequest);
            Subscription signUpSubscription = signInObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(signUpSubscription);
        }
    }


    public void signUpAddAvailibility(Context context, SignupAddAvailabilityRequest signupAddAvailabilityRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<SignupAddAvailabilityResponse> signInObs = DocOceanApiObservable.getInstance().signUpAddAvailibilityObservable(context, signupAddAvailabilityRequest);
            Subscription signUpSubscription = signInObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(signUpSubscription);
        }
    }


    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<ForgotPasswordResponse> forgotPasswordObs = DocOceanApiObservable.getInstance().forgotpasswordObservable(forgotPasswordRequest);
            Subscription forgotPasswordSubscription = forgotPasswordObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(forgotPasswordSubscription);
        }
    }


    public void logout(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<LogoutResponse> logoutObs = DocOceanApiObservable.getInstance().logoutObservable(context);
            Subscription logoutSubscription = logoutObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(logoutSubscription);
        }
    }
}

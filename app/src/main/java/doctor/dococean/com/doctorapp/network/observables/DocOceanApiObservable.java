package doctor.dococean.com.doctorapp.network.observables;

import android.content.Context;

import doctor.dococean.com.doctorapp.db.DBHelper;
import doctor.dococean.com.doctorapp.network.api.DocOceanRestApi;
import doctor.dococean.com.doctorapp.network.api.RetrofitProvider;
import doctor.dococean.com.doctorapp.network.request.AddressRequest;
import doctor.dococean.com.doctorapp.network.request.AppointmentStatusRequest;
import doctor.dococean.com.doctorapp.network.request.ForgotPasswordRequest;
import doctor.dococean.com.doctorapp.network.request.LoginRequest;
import doctor.dococean.com.doctorapp.network.request.SignUpRequest;
import doctor.dococean.com.doctorapp.network.request.SignupAddAvailabilityRequest;
import doctor.dococean.com.doctorapp.network.request.SignupRequestExpertDetails;
import doctor.dococean.com.doctorapp.network.response.Status;
import doctor.dococean.com.doctorapp.network.response.addressresponse.AddressResponse;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.AppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.cancelappointment.CancelAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.completeappointment.CompleteAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.confirmappointment.ConfirmAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.enrouteappointment.EnrouteAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.forgotpassword.ForgotPasswordResponse;
import doctor.dococean.com.doctorapp.network.response.initafterlogin.InitAfterLoginResponse;
import doctor.dococean.com.doctorapp.network.response.loginresponse.LoginResponse;
import doctor.dococean.com.doctorapp.network.response.logoutresponse.LogoutResponse;
import doctor.dococean.com.doctorapp.network.response.notificationaccept.NotificationAcceptResponse;
import doctor.dococean.com.doctorapp.network.response.notificationack.NotificationAckResponse;
import doctor.dococean.com.doctorapp.network.response.notificationreject.NotificationRejectResponse;
import doctor.dococean.com.doctorapp.network.response.professionlist.ProfessionListResponse;
import doctor.dococean.com.doctorapp.network.response.rejectappointment.RejectAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.signupaddavailability.SignupAddAvailabilityResponse;
import doctor.dococean.com.doctorapp.network.response.signupresponse.SignupExpertDetailsResponse;
import doctor.dococean.com.doctorapp.network.response.signupresponse.SignupResponse;
import doctor.dococean.com.doctorapp.network.response.singleappointmentresponse.SingleAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.userprofile.UserProfileResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public class DocOceanApiObservable {
    private static DocOceanApiObservable ourInstance = new DocOceanApiObservable();

    private DocOceanRestApi mDocOceanRestApi;

    public static DocOceanApiObservable getInstance() {
        return ourInstance;
    }

    private DocOceanApiObservable() {
        if (mDocOceanRestApi == null) {
            mDocOceanRestApi = RetrofitProvider.getInstance().getRestApi();
        }
    }

    public Observable<LoginResponse> signInObservable(LoginRequest loginRequest) {
        return mDocOceanRestApi.signIn(loginRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<InitAfterLoginResponse> initAfterLogin(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.initAfterLogin(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<ForgotPasswordResponse> forgotpasswordObservable(ForgotPasswordRequest forgotPasswordRequest) {
        return mDocOceanRestApi.forgotPassword(forgotPasswordRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<SignupResponse> signUpObservable(SignUpRequest signUpRequest) {
        return mDocOceanRestApi.signUp(signUpRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }



    public Observable<SignupExpertDetailsResponse> signUpExpertObservable(Context context, SignupRequestExpertDetails signUpRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.signupExpertDetails(authKey, signUpRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<SignupAddAvailabilityResponse> signUpAddAvailibilityObservable(Context context, SignupAddAvailabilityRequest signupAddAvailabilityRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.signupAddAvailability(authKey, signupAddAvailabilityRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<ProfessionListResponse> getProfessionObservable(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getProfessions(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<UserProfileResponse> getUserProfile(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getUserProfile(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<LogoutResponse> logoutObservable(Context context) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.logout(authKey).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<SingleAppointmentResponse> getSingleAppointment(Context context, String bookingId) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getSignleAppointment(authKey, bookingId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<Object> forgotPasswordObservable() {
        return mDocOceanRestApi.forgotPassword().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

//    public Observable<Object> registerTokenObservable(Context context, String firebase) {
//        String authKey = DBHelper.getUserAuthKey(context);
//        return mDocOceanRestApi.registerToken(authKey, firebase).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
//    }


    public Observable<AddressResponse> sendAddressObservable(Context context, AddressRequest addressRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.sendAddress(authKey, addressRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<AppointmentResponse> getAppointmentObservable(Context context, String status) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.getAppointments(authKey, status).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<ConfirmAppointmentResponse> confirmAppointmentObservable(Context context, String id, AppointmentStatusRequest appointmentStatusRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.confirmAppointment(authKey, id, appointmentStatusRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<EnrouteAppointmentResponse> enrouteAppointmentObservable(Context context, String id, AppointmentStatusRequest appointmentStatusRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.enrouteAppointment(authKey, id, appointmentStatusRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<CancelAppointmentResponse> cancelAppointmentObservable(Context context, String id, AppointmentStatusRequest appointmentStatusRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.cancelAppointment(authKey, id, appointmentStatusRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<RejectAppointmentResponse> rejectAppointmentObservable(Context context, String id, AppointmentStatusRequest appointmentStatusRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.rejectAppointment(authKey, id, appointmentStatusRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<CompleteAppointmentResponse> completedAppointmentObservable(Context context, String id, AppointmentStatusRequest appointmentStatusRequest) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.completeAppointment(authKey, id, appointmentStatusRequest).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<NotificationAckResponse> notificationACKObservable(Context context, int notificationId) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.notificationAck(authKey, notificationId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<NotificationAcceptResponse> onDemandBookingAcceptedObservable(Context context, int notificationId) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.onDemandBookingAccepted(authKey, notificationId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public Observable<NotificationRejectResponse> onDemandBookingRejectedObservable(Context context, int notificationId) {
        String authKey = DBHelper.getUserAuthKey(context);
        return mDocOceanRestApi.onDemandBookingRejected(authKey, notificationId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


    public Observable<Object> sendFeedbackObservable() {
        return mDocOceanRestApi.sendFeedback().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }


}

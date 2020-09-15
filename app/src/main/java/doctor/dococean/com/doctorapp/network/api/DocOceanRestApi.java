package doctor.dococean.com.doctorapp.network.api;

import doctor.dococean.com.doctorapp.network.request.AddressRequest;
import doctor.dococean.com.doctorapp.network.request.AppointmentStatusRequest;
import doctor.dococean.com.doctorapp.network.request.ForgotPasswordRequest;
import doctor.dococean.com.doctorapp.network.request.LoginRequest;
import doctor.dococean.com.doctorapp.network.request.SendGcmRequest;
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
import doctor.dococean.com.doctorapp.network.response.firebaseresponse.FirebaseTokenResponse;
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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public interface DocOceanRestApi {

    @POST(" /api/login")
    Observable<LoginResponse> signIn(@Body LoginRequest loginRequest);

    @POST("/api/forgot_password")
    Observable<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("api/register")
    Observable<SignupResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST("/api/logout")
    Observable<LogoutResponse> logout(@Header("Authorization") String token);

    @POST("/api/sign_up/expert_detail")
    Observable<SignupExpertDetailsResponse> signupExpertDetails(@Header("Authorization") String token,
                                                                @Body SignupRequestExpertDetails signUpRequest);

    @POST("/api/sign_up/availabilities")
    Observable<SignupAddAvailabilityResponse> signupAddAvailability(@Header("Authorization") String token,
                                                                    @Body SignupAddAvailabilityRequest signupAddAvailabilityRequest);

    @GET("/api/init/after_login")
    Observable<InitAfterLoginResponse> initAfterLogin(@Header("Authorization") String token);

    @GET("/api/professions")
    Observable<ProfessionListResponse> getProfessions(@Header("Authorization") String token);

    @GET("/api/profile")
    Observable<UserProfileResponse> getUserProfile(@Header("Authorization") String token);

    @POST("auth/forgot_password")
    Observable<Object> forgotPassword();


//    @GET("/api/patients")
//    Observable<GetPatientResponse> getPatient(@Header("Authorization") String token);


    @POST("api/user_devices/add_reg_id")
    Observable<FirebaseTokenResponse> registerToken(@Body SendGcmRequest sendGcmRequest);


    @POST("/api/notifications/{notification_id}/received")
    Observable<NotificationAckResponse> notificationAck(@Header("Authorization") String token,
                                                        @Path("notification_id") int notificationId);

    @POST("/api/notifications/{notification_id}/accept")
    Observable<NotificationAcceptResponse> onDemandBookingAccepted(@Header("Authorization") String token,
                                                                   @Path("notification_id") int notificationId);

    @POST("/api/notifications/{notification_id}/reject")
    Observable<NotificationRejectResponse> onDemandBookingRejected(@Header("Authorization") String token,
                                                                   @Path("notification_id") int notificationId);


    @POST("/api/sign_up/address")
    Observable<AddressResponse> sendAddress(@Header("Authorization") String token,
                                            @Body AddressRequest addressRequest);

    @GET("/api/expert/appointments")
    Observable<AppointmentResponse> getAppointments(@Header("Authorization") String token,
                                                    @Query("status") String status);

    @POST("api/appointments/{number}/update_status")
    Observable<ConfirmAppointmentResponse> confirmAppointment(@Header("Authorization") String token,
                                                              @Path("number") String id,
                                                              @Body AppointmentStatusRequest appointmentStatusRequest);

    @POST("api/appointments/{number}/update_status")
    Observable<EnrouteAppointmentResponse> enrouteAppointment(@Header("Authorization") String token,
                                                              @Path("number") String id,
                                                              @Body AppointmentStatusRequest appointmentStatusRequest);

    @POST("api/appointments/{number}/update_status")
    Observable<CancelAppointmentResponse> cancelAppointment(@Header("Authorization") String token,
                                                            @Path("number") String id,
                                                            @Body AppointmentStatusRequest appointmentStatusRequest);

    @POST("api/appointments/{number}/update_status")
    Observable<RejectAppointmentResponse> rejectAppointment(@Header("Authorization") String token,
                                                            @Path("number") String id,
                                                            @Body AppointmentStatusRequest appointmentStatusRequest);

    @POST("api/appointments/{number}/update_status")
    Observable<CompleteAppointmentResponse> completeAppointment(@Header("Authorization") String token,
                                                                @Path("number") String id,
                                                                @Body AppointmentStatusRequest appointmentStatusRequest);


    @GET("api/appointments/{number}/soft_assigned_appointment")
    Observable<SingleAppointmentResponse> getSignleAppointment(@Header("Authorization") String token,
                                                               @Path("number") String id);


    /**
     * Will be add this feature into the app later
     *
     * @return
     */
    @GET("/veterinary")
    Observable<Object> getveterinary();


    @POST("/feedback")
    Observable<Object> sendFeedback();


    @GET("/booking_history")
    Observable<Object> getBookingHistory();


    @POST("/issue")
    Observable<Object> reportIssue();

    @POST("/add_patient")
    Observable<Object> addPatient();
}

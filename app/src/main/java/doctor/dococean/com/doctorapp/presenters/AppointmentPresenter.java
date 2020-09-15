package doctor.dococean.com.doctorapp.presenters;

import android.content.Context;

import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.observables.DocOceanApiObservable;
import doctor.dococean.com.doctorapp.network.request.AppointmentStatusRequest;
import doctor.dococean.com.doctorapp.network.response.Status;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.AppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.cancelappointment.CancelAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.completeappointment.CompleteAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.confirmappointment.ConfirmAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.enrouteappointment.EnrouteAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.rejectappointment.RejectAppointmentResponse;
import doctor.dococean.com.doctorapp.network.response.singleappointmentresponse.SingleAppointmentResponse;
import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 16/07/16.
 */
public class AppointmentPresenter extends BasePresenter<UIUpdateListener> {


    public void getAppointments(Context context, String status) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<AppointmentResponse> getAppointmentObs = DocOceanApiObservable.getInstance().getAppointmentObservable(context, status);
            Subscription getAppointmentSubscription = getAppointmentObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(getAppointmentSubscription);
        }
    }

    public void confirmAppointment(Context context, String id) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            AppointmentStatusRequest appointmentStatusRequest = new AppointmentStatusRequest();
            appointmentStatusRequest.setStatus(DocOceanConstants.AppointmentConfirmationState.CONFIRM);
            Observable<ConfirmAppointmentResponse> appointmentActionObs = DocOceanApiObservable.getInstance().confirmAppointmentObservable(context, id, appointmentStatusRequest);
            Subscription appointmentActionSubscription = appointmentActionObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(appointmentActionSubscription);
        }
    }


    public void enrouteAppointment(Context context, String id) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            AppointmentStatusRequest appointmentEnrouteRequest = new AppointmentStatusRequest();
            appointmentEnrouteRequest.setStatus(DocOceanConstants.AppointmentConfirmationState.EN_ROUTE);
            Observable<EnrouteAppointmentResponse> appointmentActionObs = DocOceanApiObservable.getInstance().enrouteAppointmentObservable(context, id, appointmentEnrouteRequest);
            Subscription appointmentActionSubscription = appointmentActionObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(appointmentActionSubscription);
        }
    }

    public void cancelAppointment(Context context, String id) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            AppointmentStatusRequest appointmentCancelRequest = new AppointmentStatusRequest();
            appointmentCancelRequest.setStatus(DocOceanConstants.AppointmentConfirmationState.CANCEL);
            Observable<CancelAppointmentResponse> appointmentActionObs = DocOceanApiObservable.getInstance().cancelAppointmentObservable(context, id, appointmentCancelRequest);
            Subscription appointmentActionSubscription = appointmentActionObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(appointmentActionSubscription);
        }
    }

    public void rejectAppointment(Context context, String id) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            AppointmentStatusRequest appointmentRejectRequest = new AppointmentStatusRequest();
            appointmentRejectRequest.setStatus(DocOceanConstants.AppointmentConfirmationState.REJECT);
            Observable<RejectAppointmentResponse> appointmentActionObs = DocOceanApiObservable.getInstance().rejectAppointmentObservable(context, id, appointmentRejectRequest);
            Subscription appointmentActionSubscription = appointmentActionObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(appointmentActionSubscription);
        }
    }

    public void completedAppointment(Context context, String id) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            AppointmentStatusRequest appointmentCompletedRequest = new AppointmentStatusRequest();
            appointmentCompletedRequest.setStatus(DocOceanConstants.AppointmentConfirmationState.COMPLETE);
            Observable<CompleteAppointmentResponse> appointmentActionObs = DocOceanApiObservable.getInstance().completedAppointmentObservable(context, id, appointmentCompletedRequest);
            Subscription appointmentActionSubscription = appointmentActionObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(appointmentActionSubscription);
        }
    }

    public void getSingleAppointment(Context context, String id) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<SingleAppointmentResponse> getAppointmentObs = DocOceanApiObservable.getInstance().getSingleAppointment(context, id);
            Subscription getAppointmentSubscription = getAppointmentObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(getAppointmentSubscription);
        }
    }
}

package doctor.dococean.com.doctorapp.presenters;

import android.content.Context;

import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.observables.DocOceanApiObservable;
import doctor.dococean.com.doctorapp.network.response.notificationaccept.NotificationAcceptResponse;
import doctor.dococean.com.doctorapp.network.response.notificationack.NotificationAckResponse;
import doctor.dococean.com.doctorapp.network.response.notificationreject.NotificationRejectResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 08/10/16.
 */

public class NotificationPresenter extends BasePresenter<UIUpdateListener> {

    public void notificationACk(Context context, int notificationId) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<NotificationAckResponse> notificationObservable = DocOceanApiObservable.getInstance().notificationACKObservable(context, notificationId);
            Subscription notificationSubscription = notificationObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(notificationSubscription);
        }
    }

    public void onDemandBookingAccepted(Context context, int notificationId) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<NotificationAcceptResponse> notificationObservable = DocOceanApiObservable.getInstance().onDemandBookingAcceptedObservable(context, notificationId);
            Subscription notificationSubscription = notificationObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(notificationSubscription);
        }
    }

    public void onDemandBookingRejected(Context context, int notificationId) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<NotificationRejectResponse> notificationObservable = DocOceanApiObservable.getInstance().onDemandBookingRejectedObservable(context, notificationId);
            Subscription notificationSubscription = notificationObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(notificationSubscription);
        }
    }
}

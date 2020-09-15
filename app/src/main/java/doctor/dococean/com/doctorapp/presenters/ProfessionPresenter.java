package doctor.dococean.com.doctorapp.presenters;

import android.content.Context;

import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.observables.DocOceanApiObservable;
import doctor.dococean.com.doctorapp.network.response.professionlist.ProfessionListResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 02/09/16.
 */
public class ProfessionPresenter extends BasePresenter<UIUpdateListener> {

    public void getPropfessionList(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<ProfessionListResponse> signInObs = DocOceanApiObservable.getInstance().getProfessionObservable(context);
            Subscription signInSubscription = signInObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(signInSubscription);
        }
    }
}

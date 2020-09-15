package doctor.dococean.com.doctorapp.presenters;

import android.content.Context;


import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.observables.DocOceanApiObservable;
import doctor.dococean.com.doctorapp.network.response.initafterlogin.InitAfterLoginResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 27/08/16.
 */
public class SettingsPresenter extends BasePresenter<UIUpdateListener> {


    public void initAfterLogin(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<InitAfterLoginResponse> initAfterLoginResponseObservable = DocOceanApiObservable.getInstance().initAfterLogin(context);
            Subscription initAfterLoginSubscription = initAfterLoginResponseObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(initAfterLoginSubscription);
        }
    }

}

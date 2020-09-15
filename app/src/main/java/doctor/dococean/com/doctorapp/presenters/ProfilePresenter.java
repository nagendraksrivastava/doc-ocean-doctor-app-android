package doctor.dococean.com.doctorapp.presenters;

import android.content.Context;

import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.observables.DocOceanApiObservable;
import doctor.dococean.com.doctorapp.network.response.professionlist.ProfessionListResponse;
import doctor.dococean.com.doctorapp.network.response.userprofile.UserProfileResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 19/11/16.
 */

public class ProfilePresenter extends BasePresenter<UIUpdateListener> {

    public void getUserProfile(Context context) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<UserProfileResponse> profileObs = DocOceanApiObservable.getInstance().getUserProfile(context);
            Subscription profileSubscription = profileObs.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(profileSubscription);
        }
    }
}

package doctor.dococean.com.doctorapp.presenters;

import android.content.Context;

import doctor.dococean.com.doctorapp.interfaces.UIUpdateListener;
import doctor.dococean.com.doctorapp.network.observables.DocOceanApiObservable;
import doctor.dococean.com.doctorapp.network.request.AddressRequest;
import doctor.dococean.com.doctorapp.network.response.addressresponse.AddressResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by nagendrasrivastava on 10/09/16.
 */
public class AddressPresenter extends BasePresenter<UIUpdateListener> {

    public void sendAddress(Context context, AddressRequest addressRequest) {
        final UIUpdateListener listener = getUiUpdateListener();
        if (listener != null) {
            listener.showLoading(true);
            Observable<AddressResponse> sendAddressObservable = DocOceanApiObservable.getInstance().sendAddressObservable(context, addressRequest);
            Subscription sendAddressSubscription = sendAddressObservable.subscribe(getSubscriber(listener));
            queueSubscriptionForRemoval(sendAddressSubscription);
        }
    }
}

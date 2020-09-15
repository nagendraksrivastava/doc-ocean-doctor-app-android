package doctor.dococean.com.doctorapp.interfaces;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public interface UIUpdateListener<T> {

    void showLoading(boolean isLoading);

    void showContent(T items);

    void showError(Throwable error);
}

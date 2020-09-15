package doctor.dococean.com.doctorapp.interfaces;

/**
 * Created by nagendrasrivastava on 23/07/16.
 */
public interface FromActivityToFragment<T> {

    void sendData(T data);

    void sendError(T data);
}

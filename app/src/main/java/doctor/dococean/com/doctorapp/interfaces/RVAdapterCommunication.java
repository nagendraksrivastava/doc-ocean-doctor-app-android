package doctor.dococean.com.doctorapp.interfaces;

/**
 * Created by nagendrasrivastava on 05/11/16.
 */

public interface RVAdapterCommunication {
    void onAccept(String appointmentId);

    void onReject(String appointmentId);
}

package doctor.dococean.com.doctorapp.network.response.notificationreject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import doctor.dococean.com.doctorapp.network.response.Status;

/**
 * Created by nagendrasrivastava on 15/10/16.
 */

public class NotificationRejectResponse {

    @SerializedName("status")
    @Expose
    private Status status;

    /**
     *
     * @return
     * The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}

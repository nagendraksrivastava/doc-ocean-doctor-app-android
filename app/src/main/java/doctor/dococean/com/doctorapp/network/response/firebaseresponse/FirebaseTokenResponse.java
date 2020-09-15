package doctor.dococean.com.doctorapp.network.response.firebaseresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import doctor.dococean.com.doctorapp.network.response.Status;

/**
 * Created by nagendrasrivastava on 08/10/16.
 */

public class FirebaseTokenResponse {

    @SerializedName("status")
    @Expose
    private Status status;

    /**
     * @return The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

}

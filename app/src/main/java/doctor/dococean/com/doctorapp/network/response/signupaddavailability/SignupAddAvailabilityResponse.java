package doctor.dococean.com.doctorapp.network.response.signupaddavailability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import doctor.dococean.com.doctorapp.network.response.Status;

public class SignupAddAvailabilityResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("signup_pipeline")
    @Expose
    private String signupPipeline;

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

    /**
     * @return The signupPipeline
     */
    public String getSignupPipeline() {
        return signupPipeline;
    }

    /**
     * @param signupPipeline The signup_pipeline
     */
    public void setSignupPipeline(String signupPipeline) {
        this.signupPipeline = signupPipeline;
    }

}
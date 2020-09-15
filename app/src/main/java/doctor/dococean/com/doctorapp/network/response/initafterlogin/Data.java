package doctor.dococean.com.doctorapp.network.response.initafterlogin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

    @SerializedName("signup_pipeline")
    @Expose
    private String signupPipeline;
    @SerializedName("ongoing_appointments")
    @Expose
    private Boolean ongoingAppointments;
    @SerializedName("rating_pending")
    @Expose
    private Boolean ratingPending;
    @SerializedName("on_demand_appointment_number")
    @Expose
    private String onDemandAppointmentNumber;
    @SerializedName("signup_pipeline_data")
    @Expose
    private SignupPipelineData signupPipelineData;

    /**
     *
     * @return
     * The signupPipeline
     */
    public String getSignupPipeline() {
        return signupPipeline;
    }

    /**
     *
     * @param signupPipeline
     * The signup_pipeline
     */
    public void setSignupPipeline(String signupPipeline) {
        this.signupPipeline = signupPipeline;
    }

    /**
     *
     * @return
     * The ongoingAppointments
     */
    public Boolean getOngoingAppointments() {
        return ongoingAppointments;
    }

    /**
     *
     * @param ongoingAppointments
     * The ongoing_appointments
     */
    public void setOngoingAppointments(Boolean ongoingAppointments) {
        this.ongoingAppointments = ongoingAppointments;
    }

    /**
     *
     * @return
     * The ratingPending
     */
    public Boolean getRatingPending() {
        return ratingPending;
    }

    /**
     *
     * @param ratingPending
     * The rating_pending
     */
    public void setRatingPending(Boolean ratingPending) {
        this.ratingPending = ratingPending;
    }

    /**
     *
     * @return
     * The onDemandAppointmentNumber
     */
    public String getOnDemandAppointmentNumber() {
        return onDemandAppointmentNumber;
    }

    /**
     *
     * @param onDemandAppointmentNumber
     * The on_demand_appointment_number
     */
    public void setOnDemandAppointmentNumber(String onDemandAppointmentNumber) {
        this.onDemandAppointmentNumber = onDemandAppointmentNumber;
    }

    /**
     *
     * @return
     * The signupPipelineData
     */
    public SignupPipelineData getSignupPipelineData() {
        return signupPipelineData;
    }

    /**
     *
     * @param signupPipelineData
     * The signup_pipeline_data
     */
    public void setSignupPipelineData(SignupPipelineData signupPipelineData) {
        this.signupPipelineData = signupPipelineData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.signupPipeline);
        dest.writeValue(this.ongoingAppointments);
        dest.writeValue(this.ratingPending);
        dest.writeString(this.onDemandAppointmentNumber);
        dest.writeParcelable(this.signupPipelineData, flags);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.signupPipeline = in.readString();
        this.ongoingAppointments = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ratingPending = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.onDemandAppointmentNumber = in.readString();
        this.signupPipelineData = in.readParcelable(SignupPipelineData.class.getClassLoader());
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
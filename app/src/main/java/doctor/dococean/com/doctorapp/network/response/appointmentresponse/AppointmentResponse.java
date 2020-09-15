package doctor.dococean.com.doctorapp.network.response.appointmentresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import doctor.dococean.com.doctorapp.network.response.Status;

public class AppointmentResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("appointments")
    @Expose
    private List<Appointment> appointments = new ArrayList<Appointment>();

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
     * @return The appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * @param appointments The appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.status, flags);
        dest.writeList(this.appointments);
    }

    public AppointmentResponse() {
    }

    protected AppointmentResponse(Parcel in) {
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.appointments = new ArrayList<Appointment>();
        in.readList(this.appointments, Appointment.class.getClassLoader());
    }

    public static final Parcelable.Creator<AppointmentResponse> CREATOR = new Parcelable.Creator<AppointmentResponse>() {
        @Override
        public AppointmentResponse createFromParcel(Parcel source) {
            return new AppointmentResponse(source);
        }

        @Override
        public AppointmentResponse[] newArray(int size) {
            return new AppointmentResponse[size];
        }
    };
}
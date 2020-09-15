package doctor.dococean.com.doctorapp.network.response.singleappointmentresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import doctor.dococean.com.doctorapp.network.response.Status;
import doctor.dococean.com.doctorapp.network.response.appointmentresponse.Appointment;

public class SingleAppointmentResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("appointment")
    @Expose
    private Appointment appointment;

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

    /**
     *
     * @return
     * The appointment
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     *
     * @param appointment
     * The appointment
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

}
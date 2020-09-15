package doctor.dococean.com.doctorapp.firebase.incomingcommands;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncomingCommand implements Parcelable {

    @SerializedName("notification_id")
    @Expose
    private Integer notificationId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("appointment_number")
    @Expose
    private String appointmentNumber;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("notification_content")
    @Expose
    private NotificationContent notificationContent;

    /**
     * @return The notificationId
     */
    public Integer getNotificationId() {
        return notificationId;
    }

    /**
     * @param notificationId The notification_id
     */
    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The appointmentNumber
     */
    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    /**
     * @param appointmentNumber The appointment_number
     */
    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    /**
     * @return The serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType The service_type
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return The notificationContent
     */
    public NotificationContent getNotificationContent() {
        return notificationContent;
    }

    /**
     * @param notificationContent The notification_content
     */
    public void setNotificationContent(NotificationContent notificationContent) {
        this.notificationContent = notificationContent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.notificationId);
        dest.writeString(this.type);
        dest.writeString(this.appointmentNumber);
        dest.writeString(this.serviceType);
        dest.writeParcelable(this.notificationContent, flags);
    }

    public IncomingCommand() {
    }

    protected IncomingCommand(Parcel in) {
        this.notificationId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
        this.appointmentNumber = in.readString();
        this.serviceType = in.readString();
        this.notificationContent = in.readParcelable(NotificationContent.class.getClassLoader());
    }

    public static final Parcelable.Creator<IncomingCommand> CREATOR = new Parcelable.Creator<IncomingCommand>() {
        @Override
        public IncomingCommand createFromParcel(Parcel source) {
            return new IncomingCommand(source);
        }

        @Override
        public IncomingCommand[] newArray(int size) {
            return new IncomingCommand[size];
        }
    };
}
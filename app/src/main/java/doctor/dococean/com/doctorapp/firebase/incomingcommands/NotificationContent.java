package doctor.dococean.com.doctorapp.firebase.incomingcommands;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationContent implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body The body
     */
    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.body);
    }

    public NotificationContent() {
    }

    protected NotificationContent(Parcel in) {
        this.title = in.readString();
        this.body = in.readString();
    }

    public static final Parcelable.Creator<NotificationContent> CREATOR = new Parcelable.Creator<NotificationContent>() {
        @Override
        public NotificationContent createFromParcel(Parcel source) {
            return new NotificationContent(source);
        }

        @Override
        public NotificationContent[] newArray(int size) {
            return new NotificationContent[size];
        }
    };
}
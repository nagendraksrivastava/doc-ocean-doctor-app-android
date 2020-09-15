package doctor.dococean.com.doctorapp.network.response.initafterlogin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import doctor.dococean.com.doctorapp.network.response.Status;

public class InitAfterLoginResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private Data data;

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
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.status, flags);
        dest.writeParcelable(this.data, flags);
    }

    public InitAfterLoginResponse() {
    }

    protected InitAfterLoginResponse(Parcel in) {
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Parcelable.Creator<InitAfterLoginResponse> CREATOR = new Parcelable.Creator<InitAfterLoginResponse>() {
        @Override
        public InitAfterLoginResponse createFromParcel(Parcel source) {
            return new InitAfterLoginResponse(source);
        }

        @Override
        public InitAfterLoginResponse[] newArray(int size) {
            return new InitAfterLoginResponse[size];
        }
    };
}
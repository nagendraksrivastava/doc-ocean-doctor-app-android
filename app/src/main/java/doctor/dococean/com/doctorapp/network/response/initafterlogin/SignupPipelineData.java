package doctor.dococean.com.doctorapp.network.response.initafterlogin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupPipelineData implements Parcelable {

    @SerializedName("address")
    @Expose
    private Address address;

    /**
     * @return The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.address, flags);
    }

    public SignupPipelineData() {
    }

    protected SignupPipelineData(Parcel in) {
        this.address = in.readParcelable(Address.class.getClassLoader());
    }

    public static final Parcelable.Creator<SignupPipelineData> CREATOR = new Parcelable.Creator<SignupPipelineData>() {
        @Override
        public SignupPipelineData createFromParcel(Parcel source) {
            return new SignupPipelineData(source);
        }

        @Override
        public SignupPipelineData[] newArray(int size) {
            return new SignupPipelineData[size];
        }
    };
}
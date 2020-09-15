package doctor.dococean.com.doctorapp.network.request;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupAddAvailabilityRequest {

    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("availabilities")
    @Expose
    private List<Availability> availabilities = new ArrayList<Availability>();

    /**
     * @return The addressId
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * @param addressId The address_id
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     * @return The availabilities
     */
    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    /**
     * @param availabilities The availabilities
     */
    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

}
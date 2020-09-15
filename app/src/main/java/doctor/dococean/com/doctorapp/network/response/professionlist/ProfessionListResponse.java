package doctor.dococean.com.doctorapp.network.response.professionlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import doctor.dococean.com.doctorapp.network.response.Status;

public class ProfessionListResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("professions")
    @Expose
    private List<Profession> professions = new ArrayList<Profession>();

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
     * @return The professions
     */
    public List<Profession> getProfessions() {
        return professions;
    }

    /**
     * @param professions The professions
     */
    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

}
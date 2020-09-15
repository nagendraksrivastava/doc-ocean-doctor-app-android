package doctor.dococean.com.doctorapp.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupRequestExpertDetails {

    @SerializedName("expert_detail")
    @Expose
    private ExpertDetail expertDetail;

    /**
     * @return The expertDetail
     */
    public ExpertDetail getExpertDetail() {
        return expertDetail;
    }

    /**
     * @param expertDetail The expert_detail
     */
    public void setExpertDetail(ExpertDetail expertDetail) {
        this.expertDetail = expertDetail;
    }

}
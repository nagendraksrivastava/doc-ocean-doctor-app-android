package doctor.dococean.com.doctorapp.network.response.userprofile;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertDetail {

    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("degree_list")
    @Expose
    private List<String> degreeList = new ArrayList<String>();
    @SerializedName("specialization_list")
    @Expose
    private List<String> specializationList = new ArrayList<String>();
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("consulting_fee")
    @Expose
    private Double consultingFee;

    @SerializedName("service_place")
    @Expose
    private String servicePlace;


    public String getServicePlace() {
        return servicePlace;
    }

    public void setServicePlace(String servicePlace) {
        this.servicePlace = servicePlace;
    }

    /**
     * @return The profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     * @param profession The profession
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * @return The degreeList
     */
    public List<String> getDegreeList() {
        return degreeList;
    }

    /**
     * @param degreeList The degree_list
     */
    public void setDegreeList(List<String> degreeList) {
        this.degreeList = degreeList;
    }

    /**
     * @return The specializationList
     */
    public List<String> getSpecializationList() {
        return specializationList;
    }

    /**
     * @param specializationList The specialization_list
     */
    public void setSpecializationList(List<String> specializationList) {
        this.specializationList = specializationList;
    }

    /**
     * @return The licenseNo
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     * @param licenseNo The license_no
     */
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     * @return The consultingFee
     */
    public Double getConsultingFee() {
        return consultingFee;
    }

    /**
     * @param consultingFee The consulting_fee
     */
    public void setConsultingFee(Double consultingFee) {
        this.consultingFee = consultingFee;
    }

}
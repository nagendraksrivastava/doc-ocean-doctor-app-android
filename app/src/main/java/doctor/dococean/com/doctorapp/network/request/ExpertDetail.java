package doctor.dococean.com.doctorapp.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertDetail {

    @SerializedName("profession_id")
    @Expose
    private Integer professionId;
    @SerializedName("degree_list")
    @Expose
    private String degreeList;
    @SerializedName("specialization_list")
    @Expose
    private String specializationList;
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("consulting_fee")
    @Expose
    private Integer consultingFee;
    @SerializedName("service_place")
    @Expose
    private String servicePlace;
    @SerializedName("image")
    @Expose
    private Image image;

    /**
     *
     * @return
     * The professionId
     */
    public Integer getProfessionId() {
        return professionId;
    }

    /**
     *
     * @param professionId
     * The profession_id
     */
    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    /**
     *
     * @return
     * The degreeList
     */
    public String getDegreeList() {
        return degreeList;
    }

    /**
     *
     * @param degreeList
     * The degree_list
     */
    public void setDegreeList(String degreeList) {
        this.degreeList = degreeList;
    }

    /**
     *
     * @return
     * The specializationList
     */
    public String getSpecializationList() {
        return specializationList;
    }

    /**
     *
     * @param specializationList
     * The specialization_list
     */
    public void setSpecializationList(String specializationList) {
        this.specializationList = specializationList;
    }

    /**
     *
     * @return
     * The licenseNo
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     *
     * @param licenseNo
     * The license_no
     */
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     *
     * @return
     * The consultingFee
     */
    public Integer getConsultingFee() {
        return consultingFee;
    }

    /**
     *
     * @param consultingFee
     * The consulting_fee
     */
    public void setConsultingFee(Integer consultingFee) {
        this.consultingFee = consultingFee;
    }

    /**
     *
     * @return
     * The servicePlace
     */
    public String getServicePlace() {
        return servicePlace;
    }

    /**
     *
     * @param servicePlace
     * The service_place
     */
    public void setServicePlace(String servicePlace) {
        this.servicePlace = servicePlace;
    }

    /**
     *
     * @return
     * The image
     */
    public Image getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(Image image) {
        this.image = image;
    }

}
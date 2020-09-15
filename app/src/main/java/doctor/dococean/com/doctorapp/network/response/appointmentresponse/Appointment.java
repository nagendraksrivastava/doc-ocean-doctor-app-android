package doctor.dococean.com.doctorapp.network.response.appointmentresponse;

import android.os.Parcel;
import android.os.Parcelable;
import android.renderscript.Float2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment implements Parcelable {

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("distance")
    @Expose
    private Float distance;

    @SerializedName("patient_name")
    @Expose
    private String patientName;

    @SerializedName("patient_age")
    @Expose
    private Integer patientAge;
    @SerializedName("patient_gender")
    @Expose
    private String patientGender;
    @SerializedName("service_place")
    @Expose
    private String servicePlace;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("chronic_health_problems")
    @Expose
    private String chronicHealthProblems;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("patient_address")
    @Expose
    private PatientAddress patientAddress;


    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    /**
     * @return The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return The patientName
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * @param patientName The patient_name
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * @return The patientGender
     */
    public String getPatientGender() {
        return patientGender;
    }

    /**
     * @param patientGender The patient_gender
     */
    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    /**
     * @return The patientPlace
     */
    public String getServicePlace() {
        return servicePlace;
    }

    /**
     * @param patientPlace The patient_place
     */
    public void setServicePlace(String patientPlace) {
        this.servicePlace = patientPlace;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The symptoms
     */
    public String getSymptoms() {
        return symptoms;
    }

    /**
     * @param symptoms The symptoms
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * @return The chronicHealthProblems
     */
    public String getChronicHealthProblems() {
        return chronicHealthProblems;
    }

    /**
     * @param chronicHealthProblems The chronic_health_problems
     */
    public void setChronicHealthProblems(String chronicHealthProblems) {
        this.chronicHealthProblems = chronicHealthProblems;
    }

    /**
     * @return The instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions The instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The patientAddress
     */
    public PatientAddress getPatientAddress() {
        return patientAddress;
    }

    /**
     * @param patientAddress The patient_address
     */
    public void setPatientAddress(PatientAddress patientAddress) {
        this.patientAddress = patientAddress;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public Appointment() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeValue(this.distance);
        dest.writeString(this.patientName);
        dest.writeValue(this.patientAge);
        dest.writeString(this.patientGender);
        dest.writeString(this.servicePlace);
        dest.writeString(this.status);
        dest.writeString(this.symptoms);
        dest.writeString(this.chronicHealthProblems);
        dest.writeString(this.instructions);
        dest.writeString(this.createdAt);
        dest.writeParcelable(this.patientAddress, flags);
    }

    protected Appointment(Parcel in) {
        this.number = in.readString();
        this.distance = (Float) in.readValue(Float.class.getClassLoader());
        this.patientName = in.readString();
        this.patientAge = (Integer) in.readValue(Integer.class.getClassLoader());
        this.patientGender = in.readString();
        this.servicePlace = in.readString();
        this.status = in.readString();
        this.symptoms = in.readString();
        this.chronicHealthProblems = in.readString();
        this.instructions = in.readString();
        this.createdAt = in.readString();
        this.patientAddress = in.readParcelable(PatientAddress.class.getClassLoader());
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}
package doctor.dococean.com.doctorapp.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Availability {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;

    /**
     * @return The day
     */
    public String getDay() {
        return day;
    }

    /**
     * @param day The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * @return The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        Availability availability = (Availability) obj;
        if (availability.day.equals(this.day)
                && availability.startTime.equals(this.startTime)
                && availability.endTime.equals(this.endTime)) {
            isEqual = true;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.day.hashCode();
    }
}
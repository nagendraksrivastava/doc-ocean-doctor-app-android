package doctor.dococean.com.doctorapp.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_content")
    @Expose
    private String imageContent;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The imageContent
     */
    public String getImageContent() {
        return imageContent;
    }

    /**
     *
     * @param imageContent
     * The image_content
     */
    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

}
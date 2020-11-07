package in.astroconsult.astroconsult.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpeliyDataModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("thumbnail")
    @Expose
    private SpeciltyModel SpeciltyModel;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public SpeciltyModel getThumbnail() {
        return SpeciltyModel;
    }

    public void setThumbnail(SpeciltyModel thumbnail) {
        this.SpeciltyModel = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

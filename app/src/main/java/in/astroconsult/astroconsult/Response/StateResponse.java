package in.astroconsult.astroconsult.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("p_cat")
    @Expose
    private String pCat;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPCat() {
        return pCat;
    }

    public void setPCat(String pCat) {
        this.pCat = pCat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

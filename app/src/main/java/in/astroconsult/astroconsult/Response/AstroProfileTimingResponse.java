package in.astroconsult.astroconsult.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AstroProfileTimingResponse {
    @SerializedName("mondaystart")
    @Expose
    private String mondaystart;
    @SerializedName("mondayend")
    @Expose
    private String mondayend;

    public String getMondaystart() {
        return mondaystart;
    }

    public void setMondaystart(String mondaystart) {
        this.mondaystart = mondaystart;
    }

    public String getMondayend() {
        return mondayend;
    }

    public void setMondayend(String mondayend) {
        this.mondayend = mondayend;
    }
}

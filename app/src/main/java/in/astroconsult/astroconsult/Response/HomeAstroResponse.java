package in.astroconsult.astroconsult.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeAstroResponse implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("astrologers")
    @Expose
    private List<GetAstrologerResponse> astrologers = null;

    protected HomeAstroResponse(Parcel in) {
        id = in.readString();
        slug = in.readString();
        name = in.readString();
        icon = in.readString();
    }

    public static final Creator<HomeAstroResponse> CREATOR = new Creator<HomeAstroResponse>() {
        @Override
        public HomeAstroResponse createFromParcel(Parcel in) {
            return new HomeAstroResponse(in);
        }

        @Override
        public HomeAstroResponse[] newArray(int size) {
            return new HomeAstroResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<GetAstrologerResponse> getAstrologers() {
        return astrologers;
    }

    public void setAstrologers(List<GetAstrologerResponse> astrologers) {
        this.astrologers = astrologers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(slug);
        parcel.writeString(name);
        parcel.writeString(icon);
    }
}

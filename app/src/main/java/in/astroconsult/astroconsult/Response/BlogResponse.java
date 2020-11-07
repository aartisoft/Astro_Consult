package in.astroconsult.astroconsult.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogResponse implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("thumbnail")
    @Expose
    private ThumbnailResponse thumbnail;
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("seo")
    @Expose
    private BlogNextResponse seo;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ThumbnailResponse getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailResponse thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public BlogNextResponse getSeo() {
        return seo;
    }

    public void setSeo(BlogNextResponse seo) {
        this.seo = seo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}

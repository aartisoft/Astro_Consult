package in.astroconsult.astroconsult.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAstrologerResponse implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("expertise")
    @Expose
    private List<String> expertise = null;
    @SerializedName("speciality")
    @Expose
    private List<String> speciality = null;
    @SerializedName("languages")
    @Expose
    private List<String> languages = null;
    @SerializedName("timmings")
    @Expose
    private Timming timming;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("ccharge")
    @Expose
    private String ccharge;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("aadhar")
    @Expose
    private String aadhar;
    @SerializedName("pancard")
    @Expose
    private String pancard;
    @SerializedName("aadharcard")
    @Expose
    private String aadharcard;
    @SerializedName("certificates")
    @Expose
    private List<String> certificates;
    @SerializedName("fcharge")
    @Expose
    private String fcharge;
    @SerializedName("facereader")
    @Expose
    private String facereader;
    @SerializedName("palmreader")
    @Expose
    private String palmreader;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;

    protected GetAstrologerResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        mobile = in.readString();
        experience = in.readString();
        expertise = in.createStringArrayList();
        speciality = in.createStringArrayList();
        languages = in.createStringArrayList();
        location = in.readString();
        ccharge = in.readString();
        profile = in.readString();
        photo = in.readString();
        pan = in.readString();
        aadhar = in.readString();
        pancard = in.readString();
        aadharcard = in.readString();
        certificates = in.createStringArrayList();
        otp = in.readString();
        rating = in.readString();
        status = in.readString();
        date = in.readString();
    }

    public static final Creator<GetAstrologerResponse> CREATOR = new Creator<GetAstrologerResponse>() {
        @Override
        public GetAstrologerResponse createFromParcel(Parcel in) {
            return new GetAstrologerResponse(in);
        }

        @Override
        public GetAstrologerResponse[] newArray(int size) {
            return new GetAstrologerResponse[size];
        }
    };

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public List<String> getSpeciality() {
        return speciality;
    }

    public void setSpeciality(List<String> speciality) {
        this.speciality = speciality;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Timming getTimmings() {
        return timming;
    }

    public void setTimmings(Timming timming) {
        this.timming = timming;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCcharge() {
        return ccharge;
    }

    public void setCcharge(String ccharge) {
        this.ccharge = ccharge;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPancard() {
        return pancard;
    }

    public void setPancard(String pancard) {
        this.pancard = pancard;
    }

    public String getAadharcard() {
        return aadharcard;
    }

    public void setAadharcard(String aadharcard) {
        this.aadharcard = aadharcard;
    }

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public String getFcharge() {
        return fcharge;
    }

    public void setFcharge(String fcharge) {
        this.fcharge = fcharge;
    }

    public String getFacereader() {
        return facereader;
    }

    public void setFacereader(String facereader) {
        this.facereader = facereader;
    }

    public String getPalmreader() {
        return palmreader;
    }

    public void setPalmreader(String palmreader) {
        this.palmreader = palmreader;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(mobile);
        parcel.writeString(experience);
        parcel.writeStringList(expertise);
        parcel.writeStringList(speciality);
        parcel.writeStringList(languages);
        parcel.writeString(location);
        parcel.writeString(ccharge);
        parcel.writeString(profile);
        parcel.writeString(photo);
        parcel.writeString(pan);
        parcel.writeString(aadhar);
        parcel.writeString(pancard);
        parcel.writeString(aadharcard);
        parcel.writeStringList(certificates);
        parcel.writeString(otp);
        parcel.writeString(rating);
        parcel.writeString(status);
        parcel.writeString(date);
    }
}



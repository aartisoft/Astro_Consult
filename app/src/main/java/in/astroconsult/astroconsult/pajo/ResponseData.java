package in.astroconsult.astroconsult.pajo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseData{

	@SerializedName("date")
	private String date;

	@SerializedName("languages")
	private List<String> languages;

	@SerializedName("profile")
	private String profile;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("rating")
	private String rating;

	@SerializedName("photo")
	private String photo;

	@SerializedName("otp")
	private String otp;

	@SerializedName("experience")
	private String experience;

	@SerializedName("expertise")
	private List<String> expertise;

	@SerializedName("timmings")
	private List<TimmingsItem> timmings;

	@SerializedName("speciality")
	private List<String> speciality;

	@SerializedName("pancard")
	private String pancard;

	@SerializedName("certificates")
	private String certificates;

	@SerializedName("ccharge")
	private String ccharge;

	@SerializedName("name")
	private String name;

	@SerializedName("aadhar")
	private String aadhar;

	@SerializedName("location")
	private String location;

	@SerializedName("id")
	private String id;

	@SerializedName("pan")
	private String pan;

	@SerializedName("email")
	private String email;

	@SerializedName("aadharcard")
	private String aadharcard;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}

	public List<String> getLanguages(){
		return languages;
	}

	public String getProfile(){
		return profile;
	}

	public String getMobile(){
		return mobile;
	}

	public String getRating(){
		return rating;
	}

	public String getPhoto(){
		return photo;
	}

	public String getOtp(){
		return otp;
	}

	public String getExperience(){
		return experience;
	}

	public List<String> getExpertise(){
		return expertise;
	}

	public List<TimmingsItem> getTimmings(){
		return timmings;
	}

	public List<String> getSpeciality(){
		return speciality;
	}

	public String getPancard(){
		return pancard;
	}

	public String getCertificates(){
		return certificates;
	}

	public String getCcharge(){
		return ccharge;
	}

	public String getName(){
		return name;
	}

	public String getAadhar(){
		return aadhar;
	}

	public String getLocation(){
		return location;
	}

	public String getId(){
		return id;
	}

	public String getPan(){
		return pan;
	}

	public String getEmail(){
		return email;
	}

	public String getAadharcard(){
		return aadharcard;
	}

	public String getStatus(){
		return status;
	}
}
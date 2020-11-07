package in.astroconsult.astroconsult.pajo;

import com.google.gson.annotations.SerializedName;

public class TimmingsItem{

	@SerializedName("mondayend")
	private String mondayend;

	@SerializedName("mondaystart")
	private String mondaystart;

	public String getMondayend(){
		return mondayend;
	}

	public String getMondaystart(){
		return mondaystart;
	}
}
package in.astroconsult.astroconsult.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletHistoryModelResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<WalletHistoryResponse> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<WalletHistoryResponse> getData() {
        return data;
    }

    public void setData(List<WalletHistoryResponse> data) {
        this.data = data;
    }
}

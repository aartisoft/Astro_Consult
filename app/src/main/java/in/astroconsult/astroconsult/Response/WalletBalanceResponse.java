package in.astroconsult.astroconsult.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletBalanceResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("balance")
    @Expose
    private String balance;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}

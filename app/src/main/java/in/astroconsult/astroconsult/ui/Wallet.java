package in.astroconsult.astroconsult.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import in.astroconsult.astroconsult.DisplayPaymentDetails;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.RozarResponse;
import in.astroconsult.astroconsult.Response.WalletBalanceResponse;
import in.astroconsult.astroconsult.Response.WalletHistoryModelResponse;
import in.astroconsult.astroconsult.WalletListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wallet extends Fragment implements PaymentResultWithDataListener {

    EditText addMoney;
    Button submitAmount;
    WalletListAdapter walletListAdapter;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String mobile = LogInPreference.getMobileNo();
    String paymentId,remark,name,email,mobileNo;
    TextView walletBalance,walletLastBalance,lastDate;
    ListView UserWalletList;
    String  url;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    String[] arrPaymentType, arrAmount, arrRemark, arrPayment_Id, arrDate;
    RequestQueue requestQueue;
    List<WalletHistoryModelResponse> wallet = new ArrayList<>();

    public Wallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        addMoney = v.findViewById(R.id.walletAmount);
        submitAmount = v.findViewById(R.id.Wallet_admoney);
        walletBalance = v.findViewById(R.id.p1AvailWallet);
        walletLastBalance = v.findViewById(R.id.p2AvailWallet);
        lastDate = v.findViewById(R.id.p3AvailWallet);
        UserWalletList = (ListView)v.findViewById(R.id.User_wallet_History);
        requestQueue = Volley.newRequestQueue(getActivity());
        url = "http://astroconsult.in/api/userWalletHistory?api_key=w0fp55cIdJ6lLuOqVEd251zKw6lnNd&mobile=" + mobile;

        builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Searching...");
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
        getList(url);

        submitAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addMoney.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Amount Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startPayment();
                }
            }
        });



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        balance();
        last();
    }

    private void getList(String url) {
        /*Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());*/
        //requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    try {
                        loadIntoListView(response);
                    } catch (Exception e) {
                        Log.d("error", e.toString());
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (dialog.isShowing()) {
                    Log.d("error", error.toString());
                    dialog.dismiss();
                }
            }
        });

        requestQueue.add(stringRequest);
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray Jarray = obj.getJSONArray("data");
        arrPaymentType = new String[Jarray.length()];
        arrAmount = new String[Jarray.length()];
        arrRemark = new String[Jarray.length()];
        arrPayment_Id = new String[Jarray.length()];
        arrDate = new String[Jarray.length()];

        for (int i = 0; i < Jarray.length(); i++) {
            JSONObject Jasonobject = Jarray.getJSONObject(i);
            arrPaymentType[i] = Jasonobject.getString("type");
            arrAmount[i] = Jasonobject.getString("amount");
            arrRemark[i] = Jasonobject.getString("remark");
            arrPayment_Id[i] = Jasonobject.getString("payment_id");
            arrDate[i] = Jasonobject.getString("date");
        }

        CustAdpt adpt = new CustAdpt(getContext(), arrPaymentType, arrAmount, arrRemark, arrPayment_Id, arrDate);
        UserWalletList.setAdapter(adpt);
    }

    public class CustAdpt extends ArrayAdapter<String> {
        String[] sPaymentType, sAmount, sRemark, sPayment_Id, sDate;
        Context mContext;
        String Type;
        String txtType, Type1;

        public CustAdpt(Context context, String type[], String amount[], String remark[], String payment_id[], String date[]) {
            super(context, R.layout.activity_user_wallet__history_raw, R.id.User_wallet_Payment_Type, amount);
            sPaymentType = type;
            sAmount = amount;
            sRemark = remark;
            sPayment_Id = payment_id;
            sDate = date;
            mContext = context;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            CustAdpt.viewHolder vholder = null;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_user_wallet__history_raw, parent, false);
                vholder = new CustAdpt.viewHolder(row);
                row.setTag(vholder);
            } else {
                vholder = (CustAdpt.viewHolder) row.getTag();
            }
            Type = sPaymentType[position];
            if (Type.equalsIgnoreCase("cr")) {
                txtType = "Payment Credit";
                vholder.txt_Wallet_Amount.setTextColor(Color.parseColor("#DF00C853"));
                vholder.txt_Wallet_Amount.setText("Rs. +"+sAmount[position]);
            }else{
                txtType = "Payment Debit";
                vholder.txt_Wallet_Amount.setTextColor(Color.parseColor("#FF0000"));
                vholder.txt_Wallet_Amount.setText("Rs. -"+sAmount[position]);
            }
            vholder.txtPayment_Type.setText(txtType);

            vholder.txtRemark.setText(sRemark[position]);
            vholder.txtPayment_Id.setText(sPayment_Id[position]);

            String date = sDate[position];
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(Integer.parseInt(date)*1000L);
            String date1 = DateFormat.format("dd-MM-yyyy",calendar).toString();
            vholder.txtDate.setText(date1);

            return row;
        }

        public class viewHolder {
            TextView txtPayment_Type, txt_Wallet_Amount, txtRemark, txtPayment_Id, txtDate;

            public viewHolder(View v) {
                txtPayment_Type = (TextView) v.findViewById(R.id.User_wallet_Payment_Type);
                txt_Wallet_Amount = (TextView) v.findViewById(R.id.User_wallet_amount);
                txtRemark = (TextView) v.findViewById(R.id.User_wallet_History_Remark);
                txtPayment_Id = (TextView) v.findViewById(R.id.User_wallet_Payment_Id);
                txtDate = (TextView) v.findViewById(R.id.User_wallet_Payment_Date);
            }
        }
    }


    private void startPayment() {
        //Activity activity = (Activity) getContext();
        Checkout co = new Checkout();
        try
        {
            JSONObject object = new JSONObject();
            object.put("name","Astro Consult");
            object.put("description","App Payment");
            object.put("image","https://w0.pngwave.com/png/319/931/chinese-zodiac-chinese-astrology-astrological-sign-wu-xing-zodiac-png-clip-art-thumbnail.png");
            object.put("currency","INR");
            object.put("prefill.email",email);
            object.put("prefill.contact",mobile);
            String payment = addMoney.getText().toString();

            double total = Double.parseDouble(payment);
            total = total*100;
            object.put("amount",total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", LogInPreference.getInstance(getContext()).getEmailAdd());
            preFill.put("contact",LogInPreference.getInstance(getContext()).getMobileNo());
            object.put("prefill",preFill);

            co.open(getActivity(),object);

        }catch (Exception e){

        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        paymentId = paymentData.getPaymentId();
        remark = paymentData.getSignature();
        name = paymentData.getOrderId();
        email = paymentData.getUserEmail();
        mobileNo = paymentData.getUserContact();

        addMoney();
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        //Log.d("Heyy","error"+String.valueOf(i)+"== Payment failed"+s.toString());
        try {
            Toast.makeText(getContext(), "Payment error", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Log.d("OnPaymentError","Error",e);
        }
    }
    public void addMoney()
    {
        String money = addMoney.getText().toString();
        Call<RozarResponse> call = ApiClient.getCliet().rozarResponse(api_key,mobile,paymentId,money,remark);
        call.enqueue(new Callback<RozarResponse>() {
            @Override
            public void onResponse(Call<RozarResponse> call, Response<RozarResponse> response) {
                RozarResponse response1 = response.body();

                if (response1.getStatus() == 200)
                {
                    Intent i = new Intent(getContext(), DisplayPaymentDetails.class);
                    i.putExtra("payment_id", paymentId);
                    i.putExtra("order_id", name);
                    i.putExtra("email", email);
                    i.putExtra("contact_no", mobileNo);
                    startActivity(i);
                    Toast.makeText(getContext(), ""+response1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RozarResponse> call, Throwable throwable) {
                Toast.makeText(getContext(), "error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void balance()
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();
        //Log.d("TAG","hello");
        Call<WalletBalanceResponse> call = ApiClient.getCliet().walletBalance(api_key,mobile);
        call.enqueue(new Callback<WalletBalanceResponse>() {
            @Override
            public void onResponse(Call<WalletBalanceResponse> call, Response<WalletBalanceResponse> response) {
                WalletBalanceResponse wallet = response.body();


                if (wallet.getStatus() == 200)
                {
                    //Log.d("vishal","Hello");
                    walletBalance.setText(wallet.getBalance());
                    dialog.dismiss();
                }
                else if (wallet.getStatus() == 201)
                {
                    //walletBalance.setText(wallet.getBalance());
                    //Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WalletBalanceResponse> call, Throwable throwable) {
                dialog.dismiss();
                //Toast.makeText(getActivity(), "Error !! While fetching balance", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void last()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<WalletHistoryModelResponse> responseCall = ApiClient.getCliet().walletHistory(api_key,mobile);
        responseCall.enqueue(new Callback<WalletHistoryModelResponse>() {
            @Override
            public void onResponse(Call<WalletHistoryModelResponse> call, Response<WalletHistoryModelResponse> response) {
                WalletHistoryModelResponse base = response.body();

                if (base.getStatus() == 200)
                {
                    wallet.clear();
                    wallet.add(response.body());
                    //Log.d("tag",response.body().getData().get(0).getAmount());
                    String date = base.getData().get(0).getDate();
                    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                    calendar.setTimeInMillis(Integer.parseInt(date)*1000L);
                    String date1 = DateFormat.format("dd-MM-yyyy",calendar).toString();

                    walletLastBalance.setText("Last Payment: Rs. "+response.body().getData().get(0).getAmount());
                    //wallet1.setText("+RS. "+response.body().getData().get(0).getAmount());
                    lastDate.setText(date1);
                    //walletdate1.setText(date1);
                    //walletListAdapter = new WalletListAdapter(getContext(), wallet);
                    //walletList.setAdapter(walletListAdapter);
                    progressDialog.dismiss();
                }
                else if (base.getStatus() == 201)
                {
                    //walletLastBalance.setText("Last Payment: Rs"+response.body().getData().get(0).getAmount());
                    //Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WalletHistoryModelResponse> call, Throwable throwable) {
                //Log.d("Tag","hii");
                Toast.makeText(getActivity(), "error!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}

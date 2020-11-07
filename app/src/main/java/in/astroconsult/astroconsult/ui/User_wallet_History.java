package in.astroconsult.astroconsult.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.astroconsult.astroconsult.R;

public class User_wallet_History extends AppCompatActivity {
    ListView UserWalletList;
    String Mobile_No, url;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    String[] arrPaymentType, arrAmount, arrRemark, arrPayment_Id, arrDate;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet__history);
        UserWalletList = (ListView) findViewById(R.id.User_wallet_History);
        requestQueue = Volley.newRequestQueue(User_wallet_History.this);
        String UserId = "8058242568";
        url = "http://astroconsult.in/api/userWalletHistory?api_key=w0fp55cIdJ6lLuOqVEd251zKw6lnNd&mobile=" + UserId;

        builder = new AlertDialog.Builder(User_wallet_History.this);
        builder.setMessage("Searching...");
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
        getList(url);
    }

    void getList(String url) {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
        }, new Response.ErrorListener() {
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

        CustAdpt adpt = new CustAdpt(this, arrPaymentType, arrAmount, arrRemark, arrPayment_Id, arrDate);
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
            if (Type == "cr") {
                txtType = "Payment Credit";
            }else{
                txtType = "Payment Debit";
                //Response.setTextColor(Color.parseColor("#F31010"));
            }
            vholder.txtPayment_Type.setText(txtType);
            vholder.txt_Wallet_Amount.setText(sAmount[position]);
            vholder.txtRemark.setText(sRemark[position]);
            vholder.txtPayment_Id.setText(sPayment_Id[position]);
            vholder.txtDate.setText(sDate[position]);

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
}
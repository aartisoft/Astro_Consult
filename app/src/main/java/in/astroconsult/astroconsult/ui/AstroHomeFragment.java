package in.astroconsult.astroconsult.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.AstrologerWalletBalanceResponse;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.pajo.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AstroHomeFragment extends Fragment {

    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String mobileHome;
    RelativeLayout relative1,relative2,relative3;
    ImageView astrologerImage;
    TextView astrologerName,astrologerSpeciality,walletAmountAstroHome;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    public AstroHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_astro_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();
        FirebaseAuthUtil.getInstance(mAuth,mDatabaseRef).getAstro(getContext(),currentUserId);

        mobileHome = AstroLogInPreference.getAstroMobile();

        astrologerImage = v.findViewById(R.id.astrologerHomePageImage);
        astrologerName = v.findViewById(R.id.astrologerHomePageName);
        astrologerSpeciality = v.findViewById(R.id.astrologerHomeSpeciality);
        walletAmountAstroHome = v.findViewById(R.id.pAstrowallet);

        relative1 = v.findViewById(R.id.relative1);
        relative2 = v.findViewById(R.id.relative2);
        relative3 = v.findViewById(R.id.relative3);

        productSub();
        balance();

        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog);
                final Button radioGroup = dialog.findViewById(R.id.butonSubmit);
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER);

                radioGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog);
                final Button radioGroup = dialog.findViewById(R.id.butonSubmit);
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER);
                dialog.show();

                radioGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog);
                final Button radioGroup = dialog.findViewById(R.id.butonSubmit);
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER);
                dialog.show();

                radioGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });

        return v;
    }
    public void productSub() {

        try
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();

            Call<ResponseModel> call = ApiClient.getCliet().astroProfile(api_key,mobileHome);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    if (response.isSuccessful())
                    {
                        astrologerName.setText(response.body().getName());
                        astrologerSpeciality.setText(response.body().getSpeciality().get(0));
                        Picasso.get().load(response.body().getPhoto()).into(astrologerImage);

                        progressDialog.dismiss();
                    }

                    else
                    {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable throwable) {
                    Log.d("errororr",throwable.getMessage().toString());
                    progressDialog.dismiss();
                    // Toast.makeText(getActivity(), "good  "+throwable.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

    }

    public void balance()
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();
        //Log.d("TAG","hello");
        Call<AstrologerWalletBalanceResponse> call = ApiClient.getCliet().walletBalanceAstro(api_key,mobileHome);
        call.enqueue(new Callback<AstrologerWalletBalanceResponse>() {
            @Override
            public void onResponse(Call<AstrologerWalletBalanceResponse> call, Response<AstrologerWalletBalanceResponse> response) {
                AstrologerWalletBalanceResponse wallet = response.body();


                if (wallet.getStatus() == 200)
                {
                    //Log.d("vishal","Hello");
                    walletAmountAstroHome.setText(wallet.getBalance());
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
            public void onFailure(Call<AstrologerWalletBalanceResponse> call, Throwable throwable) {
                dialog.dismiss();
                //Toast.makeText(getActivity(), "Error !! While fetching balance", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
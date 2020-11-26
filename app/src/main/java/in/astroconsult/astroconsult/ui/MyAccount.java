package in.astroconsult.astroconsult.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.LogIn;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.UserProfileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccount extends Fragment {

    TextView update;
    TextView nameAstro, mobileAstro, emailAstro;
    Button logout;
    ImageView image;

    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String mobileUser, name, email, mobile, starshine;

    public MyAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_my_account, container, false);

        mobileUser = LogInPreference.getInstance(getContext()).getMobileNo();
        //Toast.makeText(getContext(), ""+mobileUser, Toast.LENGTH_SHORT).show();

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        update = inflate.findViewById(R.id.edit_profile);
        image = (ImageView) inflate.findViewById(R.id.Imageviewprofile);
        nameAstro = (TextView) inflate.findViewById(R.id.name);
        mobileAstro = (TextView) inflate.findViewById(R.id.my_Email);
        emailAstro = (TextView) inflate.findViewById(R.id.my_Number);
        logout = (Button) inflate.findViewById(R.id.logOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You are Successfully logged out", Toast.LENGTH_SHORT).show();
                LogInPreference.getInstance(getContext()).setClear();
                Intent i3 = new Intent(getContext(), LogIn.class);
                startActivity(i3);
                getActivity().finish();
            }
        });

        getProfile();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), P1_Update_Account.class);
                intent.putExtra("profile_name", name);
                intent.putExtra("profile_email", email);
                intent.putExtra("profile_mobile", mobile);
                intent.putExtra("profile_starshine", starshine);
                startActivity(intent);
            }
        });

        return inflate;
    }

    public void getProfile() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setMessage("please wait...");
        dialog.show();
        //Log.d("name","hii");
        Call<UserProfileResponse> call = ApiClient.getCliet().userProfile(api_key, mobileUser);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                if (response.isSuccessful()) {
                    name = response.body().getName();
                    email = response.body().getEmail();
                    mobile = response.body().getMobile();
                    starshine = response.body().getStarshine();
                    nameAstro.setText(name);
                    emailAstro.setText(mobile);
                    mobileAstro.setText(email);
                    if (response.body().getPhoto().isEmpty()) {
                        Picasso.get().load(R.drawable.rinku).into(image);
                    }
                    else
                    {
                        FirebaseAuthUtil.updateProfilePic(response.body().getPhoto());
                        Picasso.get().load(response.body().getPhoto()).into(image);
                    }
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable throwable) {

                Toast.makeText(getContext(), "" + throwable, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
}

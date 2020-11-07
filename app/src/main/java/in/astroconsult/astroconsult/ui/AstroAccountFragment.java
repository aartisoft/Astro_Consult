package in.astroconsult.astroconsult.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import in.astroconsult.astroconsult.AstroLogIn;
import in.astroconsult.astroconsult.AstrologerEditProfile;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.LogIn;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.AstroProfileResponse;
import in.astroconsult.astroconsult.pajo.ResponseForm;
import in.astroconsult.astroconsult.pajo.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AstroAccountFragment extends Fragment {

    ImageView astroImage;
    TextView nameAstro,mobileAstro,emailAstro,editProfile;
    Response<ResponseModel> respo;
    Button astroLogout;

    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String mobileAS;
    //String mobile = LogInPreference.getInstance(getContext()).getMobile();

    public AstroAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_astro_account, container, false);

        mobileAS = AstroLogInPreference.getInstance(getActivity()).getAstroMobile();
        //Toast.makeText(getContext(), ""+mobile, Toast.LENGTH_SHORT).show();

        astroImage = (ImageView)view.findViewById(R.id.Imageviewprofile);
        nameAstro = (TextView)view.findViewById(R.id.nameAstro);
        mobileAstro = (TextView)view.findViewById(R.id.my_Email_Astro);
        emailAstro = (TextView)view.findViewById(R.id.my_Number_Astro);
        astroLogout = view.findViewById(R.id.astroLogot);
        editProfile = view.findViewById(R.id.edit_profile_astro);

        getProfile();

        astroLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AstroLogInPreference.getInstance(getContext()).setClear();

                Intent intent = new Intent(getContext(), LogIn.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                //AstroLogInPreference.setClear();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AstrologerEditProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public  Response<ResponseModel>  getProfile()
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        dialog.show();
        //Log.d("name","hii");
        Call<ResponseModel> call = ApiClient.getCliet().astroProfile(api_key,mobileAS);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful())
                {
                       respo = response;
                       //String name =  response.body().getName();
                        nameAstro.setText(response.body().getName());
                        Picasso.get().load( response.body().getPhoto()).into(astroImage);
                        emailAstro.setText( response.body().getMobile());
                        mobileAstro.setText(response.body().getEmail());

                    dialog.dismiss();
                    //showData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable throwable) {
                Log.d("errororr",throwable.getMessage().toString());
                dialog.dismiss();
               // Toast.makeText(getActivity(), "good  "+throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return respo;
    }
/*
    private void showData(List<AstroProfileResponse> body)
    {
        for (AstroProfileResponse astroProfileResponse : body)
        {
            Picasso.get().load(astroProfileResponse.getPhoto()).into(astroImage);
            nameAstro.setText(astroProfileResponse.getName());
            mobileAstro.setText(astroProfileResponse.getMobile());
            emailAstro.setText(astroProfileResponse.getEmail());
        }
    }*/
}
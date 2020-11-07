package in.astroconsult.astroconsult;

import androidx.appcompat.app.AppCompatActivity;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.Response.AstroSignUpResponse;
import in.astroconsult.astroconsult.Response.SignUpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AstrologerSignUp extends AppCompatActivity {

    Button signUp;
    EditText name,email,mobile;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String astroMob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astrologer_sign_up);
        getSupportActionBar().hide();

        name = findViewById(R.id.AstroSignsignUpName);
        email = findViewById(R.id.AstroSignsignUpEmail);
        mobile = findViewById(R.id.AstroSignsignUpMobile);

        astroMob = AstroLogInPreference.getInstance(AstrologerSignUp.this).getAstroMobile();
        mobile.setText(astroMob);

        signUp = findViewById(R.id.AstroSignsubmitSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    public void sendData()
    {
        final String signUpName = name.getText().toString();
        final String signUpEmail = email.getText().toString();
        //final String signUpMobile = mobile.getText().toString();

        Call<AstroSignUpResponse> call = ApiClient.getCliet().astroSignUp(api_key,signUpName,signUpEmail,astroMob);
        call.enqueue(new Callback<AstroSignUpResponse>() {
            @Override
            public void onResponse(Call<AstroSignUpResponse> call, Response<AstroSignUpResponse> response) {
                AstroSignUpResponse signUpResponse = response.body();

                if (signUpResponse.getStatus()==205)
                {
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstroName(name.getText().toString());
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstroEmail(email.getText().toString());
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstroMobile(astroMob);
                    Intent intent = new Intent(AstrologerSignUp.this,AstroProfileSignUp.class);
                    //intent.putExtra("AstroName",signUpName);
                    //intent.putExtra("AstroEmail",signUpEmail);
                    //intent.putExtra("AstroMobile",astroMob);
                    startActivity(intent);
                    finish();
                }
                /*else if (signUpResponse.getStatus()==201)
                {
                    Toast.makeText(AstrologerSignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else if (signUpResponse.getStatus()==202)
                {
                    Toast.makeText(AstrologerSignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else if (signUpResponse.getStatus()==203)
                {
                    Toast.makeText(AstrologerSignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<AstroSignUpResponse> call, Throwable throwable) {
                Toast.makeText(AstrologerSignUp.this, "Hii", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package in.astroconsult.astroconsult;

import androidx.appcompat.app.AppCompatActivity;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.Response.AstroLogInResponse;
import in.astroconsult.astroconsult.Response.LogInResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AstroLogIn extends AppCompatActivity {

    Button logIn;
    EditText mobile;
    String mob;
    Context context = this;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_log_in);
        getSupportActionBar().hide();

        logIn = findViewById(R.id.AstrosignIn);
        mobile = findViewById(R.id.AstrologInMobile);

        //notRegister = findViewById(R.id.notRegister);

        /*notRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this,AstroLogIn.class);
                startActivity(intent);
            }
        });*/

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String MobilePattern = "[0-9]{10}";
                if(!mobile.getText().toString().matches(MobilePattern)) {
                    Snackbar.make(mobile, "Please enter valid 10 digit mobile number", Snackbar.LENGTH_SHORT).show();
                }else {
                    sendData();
                }
            }
        });

    }

    public void sendData()
    {
        final ProgressDialog dialog = new ProgressDialog(AstroLogIn.this);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setMessage("Please Wait...");
        String logInMobileAstro = mobile.getText().toString();
        Call<AstroLogInResponse> call = ApiClient.getCliet().astroLogIn(api_key,logInMobileAstro);
        call.enqueue(new Callback<AstroLogInResponse>() {
            @Override
            public void onResponse(Call<AstroLogInResponse> call, Response<AstroLogInResponse> response) {
                AstroLogInResponse logInResponse = response.body();

                if (logInResponse.getStatus() == 206 || logInResponse.getStatus() == 210)
                {
                    dialog.dismiss();
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstroMobile(mobile.getText().toString());
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstro("IsAstrologer");
                    Intent intent = new Intent(AstroLogIn.this,AstroOtpVerify.class);
                    intent.putExtra("mobileAstro",mobile.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else if (logInResponse.getStatus()==208)
                {
                    dialog.dismiss();
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstroMobile(mobile.getText().toString());
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstro("IsAstrologer");
                    Intent intent = new Intent(AstroLogIn.this,AstroOtp.class);
                    intent.putExtra("mobileNextAstro",mobile.getText().toString());
                    startActivity(intent);
                }

                else if (logInResponse.getStatus()==211)
                {
                    dialog.dismiss();
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstroMobile(mobile.getText().toString());
                    AstroLogInPreference.getInstance(getApplicationContext()).setAstro("IsAstrologer");
                    //Toast.makeText(AstroLogIn.this, ""+AstroLogInPreference.getInstance(getApplicationContext()).getAstroMobile(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(LogIn.this, ""+logInResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AstroLogIn.this,AstroOtpEditProfile.class);
                    intent.putExtra("mobileNextAstroEdit",mobile.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<AstroLogInResponse> call, Throwable throwable) {
                dialog.dismiss();
                Snackbar.make(mobile, "Something went wrong!! Try again..", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
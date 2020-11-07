package in.astroconsult.astroconsult;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Model.LogInModel;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.Response.LogInResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.google.android.material.snackbar.Snackbar;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {

    Button logIn;
    EditText mobile;
    TashieLoader tashieLoader;
    TextView notRegister;
    Context context = this;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    NewtonCradleLoading newtonCradleLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getSupportActionBar().hide();

        //newtonCradleLoading = findViewById(R.id.newton_cradle_loading);

        logIn = findViewById(R.id.signIn);
        mobile = findViewById(R.id.logInMobile);
        notRegister = findViewById(R.id.notRegister);
        notRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this,AstroLogIn.class);
                startActivity(intent);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //newtonCradleLoading.stop();
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
        final ProgressDialog dialog = new ProgressDialog(LogIn.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait..");
        dialog.show();
        final String logInMobile = mobile.getText().toString();
        LogInPreference.getInstance(getApplicationContext()).setMobileNo(logInMobile);
        Call<LogInResponse> call = ApiClient.getCliet().logIn(api_key,logInMobile);
        call.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                LogInResponse logInResponse = response.body();

                if (logInResponse.getStatus() == 206)
                {
                    dialog.dismiss();
                    //newtonCradleLoading.isStart();
                    LogInPreference.getInstance(getApplicationContext()).setMobileNo(logInMobile);
                    LogInPreference.getInstance(getApplicationContext()).setUser("IsUser");
                    //Toast.makeText(SignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIn.this,OtpVerify.class);
                    intent.putExtra("mobile",mobile.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else if (logInResponse.getStatus()==208)
                {
                    dialog.dismiss();
                    LogInPreference.getInstance(getApplicationContext()).setUser("IsUser");
                    //Toast.makeText(LogIn.this, ""+logInResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIn.this,otp.class);
                    LogInPreference.getInstance(getApplicationContext()).setMobileNo(logInMobile);
                    intent.putExtra("mobileNext",mobile.getText().toString());
                    startActivity(intent);
                }
                else if (logInResponse.getStatus()==204)
                {
                    dialog.dismiss();
                    Snackbar.make(mobile, ""+logInResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
//                    Toast.makeText(LogIn.this, ""+logInResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable throwable) {
                dialog.dismiss();
                Snackbar.make(mobile, "Something went wrong!! Try again..", Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(LogIn.this, "Something went wrong!!Try again..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

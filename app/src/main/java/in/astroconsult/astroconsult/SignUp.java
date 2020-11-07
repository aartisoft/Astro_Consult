package in.astroconsult.astroconsult;

import androidx.appcompat.app.AppCompatActivity;
import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Model.SignUpModel;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.Response.SignUpResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    Button signUp;
    EditText name,email,mobile;
    String mo;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mo = LogInPreference.getInstance(SignUp.this).getMobileNo();

        name = findViewById(R.id.signUpName);
        email = findViewById(R.id.signUpEmail);
        mobile = findViewById(R.id.signUpMobile);

        mobile.setText(mo);

        signUp = findViewById(R.id.submitSignUp);

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

        Call<SignUpResponse> call = ApiClient.getCliet().signUp(api_key,signUpName,signUpEmail,mo);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse signUpResponse = response.body();

                if (signUpResponse.getStatus()==205)
                {
                    LogInPreference.getInstance(getApplicationContext()).setName(signUpName);
                    LogInPreference.getInstance(getApplicationContext()).setEmailAdd(signUpEmail);

                    //Toast.makeText(SignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (signUpResponse.getStatus()==201)
                {
                    Toast.makeText(SignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else if (signUpResponse.getStatus()==202)
                {
                    Toast.makeText(SignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else if (signUpResponse.getStatus()==203)
                {
                    Toast.makeText(SignUp.this, ""+signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable throwable) {
                Toast.makeText(SignUp.this, "Hii", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
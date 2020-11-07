package in.astroconsult.astroconsult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class AstroOtpEditProfile extends AppCompatActivity {

    String verificatiobCodeBySystem;
    Button verify;
    PinEntryEditText pinEntry;
    ProgressBar progressBar;
    TextView resendOtp;
    String phone;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_otp_edit_profile);
        pinEntry = (PinEntryEditText) findViewById(R.id.Astro_txt_pin_entry_edit);
        progressBar = findViewById(R.id.astro_progress_bar_edit);
        verify = findViewById(R.id.AstrootpButtonEdit);
        resendOtp = findViewById(R.id.AstroresendOtpEdit);

        phone = getIntent().getStringExtra("mobileNextAstroEdit");

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        //LogInPreference.getInstance(getApplicationContext()).setMobile(phone);
        /*if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1234")) {
                        Toast.makeText(OtpVerify.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OtpVerify.this, "FAIL", Toast.LENGTH_SHORT).show();
                        pinEntry.setText(null);
                    }
                }
            });
        }*/
        resendOtp.postDelayed(new Runnable() {
            public void run() {
                resendOtp.setVisibility(View.VISIBLE);
            }
        }, 30000);
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCodeToUser(phone);
                Snackbar.make(resendOtp, "Otp Resend Successfully", Snackbar.LENGTH_SHORT).show();
            }
        });

        sendVerificationCodeToUser(phone);

        getSupportActionBar().hide();

        //verify = findViewById(R.id.otpButton);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = pinEntry.getText().toString();

                if (code.isEmpty() || code.length()<6)
                {
                    pinEntry.setError("Wrong Otp..");
                    pinEntry.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

    }
    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNo,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificatiobCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code!=null)
            {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(AstroOtpEditProfile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyCode(String verificationCode)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatiobCodeBySystem,verificationCode);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(AstroOtpEditProfile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            AstroLogInPreference.getInstance(AstroOtpEditProfile.this).setLogged(true);
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String currentUserId = currentUser.getUid();
                            FirebaseAuthUtil.getInstance(mAuth,mDatabaseRef).getAstro(getApplicationContext(),currentUserId);
                            Toast.makeText(AstroOtpEditProfile.this, "Otp Verified",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AstroProfileSignUp.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AstroOtpEditProfile.this, ""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
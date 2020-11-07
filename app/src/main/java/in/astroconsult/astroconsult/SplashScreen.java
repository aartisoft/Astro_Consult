package in.astroconsult.astroconsult;

import androidx.appcompat.app.AppCompatActivity;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Preferance.LogInPreference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent intent = new Intent(SplashScreen.this,LogIn.class);
                startActivity(intent);
                finish();*/
                if (LogInPreference.getInstance(SplashScreen.this).getLogged() && LogInPreference.getInstance(SplashScreen.this).getUser().equals("IsUser"))
                {
                    Log.d("Hello",LogInPreference.getInstance(SplashScreen.this).getLogged().toString());
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                else if(AstroLogInPreference.getInstance(SplashScreen.this).getLogged() && AstroLogInPreference.getInstance(SplashScreen.this).getAstro().equals("IsAstrologer"))
                {
                    Log.d("Hello",AstroLogInPreference.getInstance(SplashScreen.this).getLogged().toString());
                    Intent intent = new Intent(SplashScreen.this,AstroHome.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashScreen.this,LogIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2500);
    }
}

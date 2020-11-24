package in.astroconsult.astroconsult.Preferance;

import android.content.Context;
import android.content.SharedPreferences;

public class LogInPreference {

    private static final LogInPreference ourInstance = new LogInPreference();
    private  static SharedPreferences sharedPreferencesUser;

    public static LogInPreference getInstance(Context context)
    {
        sharedPreferencesUser = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return ourInstance;
    }

    public LogInPreference()
    {

    }

    public void setLogged(boolean logged)
    {
        sharedPreferencesUser.edit().putBoolean("loggedin",logged).apply();
    }

    public void setClear()
    {
        SharedPreferences.Editor editor = sharedPreferencesUser.edit();
        editor.clear();
        editor.apply();
        //sharedPreferencesUser.edit().remove("loggedin").apply();
        //sharedPreferencesUser.edit().remove("Name").apply();
        //sharedPreferencesUser.edit().remove("Mobile").apply();
        //sharedPreferencesUser.edit().remove("Email").apply();
    }

    public Boolean getLogged()
    {
        return sharedPreferencesUser.getBoolean("loggedin",false);
    }

    public void setName(String name)
    {
        sharedPreferencesUser.edit().putString("Name",name).apply();
    }
    public String getName()
    {
        return sharedPreferencesUser.getString("Name",null);
    }

    public void setAstroId(String astroId)
    {
        sharedPreferencesUser.edit().putString("AstroId",astroId).apply();
    }
    public String getAstroId()
    {
        return sharedPreferencesUser.getString("AstroId",null);
    }

    public void setFaceYes(String faceYes)
    {
        sharedPreferencesUser.edit().putString("FaceYes",faceYes).apply();
    }
    public String getFaceYes()
    {
        return sharedPreferencesUser.getString("FaceYes",null);
    }

    public void setPalmYes(String palmYes)
    {
        sharedPreferencesUser.edit().putString("PalmYes",palmYes).apply();
    }
    public String getPalmYes()
    {
        return sharedPreferencesUser.getString("PalmYes",null);
    }

    public void setPalmValue(String palmValue)
    {
        sharedPreferencesUser.edit().putString("PalmValue",palmValue).apply();
    }
    public String getPalmValue()
    {
        return sharedPreferencesUser.getString("PalmValue",null);
    }


    public void setMobileNo(String mobile)
    {
        sharedPreferencesUser.edit().putString("Mobile",mobile).apply();
    }
    public static String getMobileNo()
    {
        return sharedPreferencesUser.getString("Mobile",null);
    }

    public void setFirebaseKey(String firebaseKey)
    {
        sharedPreferencesUser.edit().putString("FirebaseKey",firebaseKey).apply();
    }

    public String getFirebaseKey()
    {
        return sharedPreferencesUser.getString("FirebaseKey",null);
    }

    public void setUser(String user)
    {
        sharedPreferencesUser.edit().putString("IsUser",user).apply();
    }
    public String getUser()
    {
        return sharedPreferencesUser.getString("IsUser",null);
    }

    public void setEmailAdd(String email)
    {
        sharedPreferencesUser.edit().putString("Email",email).apply();
    }
    public String getEmailAdd()
    {
        return sharedPreferencesUser.getString("Email",null);
    }

}

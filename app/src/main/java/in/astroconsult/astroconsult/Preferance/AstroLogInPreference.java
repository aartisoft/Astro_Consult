package in.astroconsult.astroconsult.Preferance;

import android.content.Context;
import android.content.SharedPreferences;

public class AstroLogInPreference {

    private static final AstroLogInPreference ourInstance = new AstroLogInPreference();
    private  static SharedPreferences sharedPreferencesAstro;

    public static AstroLogInPreference getInstance(Context context)
    {
        sharedPreferencesAstro = context.getSharedPreferences("astro",Context.MODE_PRIVATE);
        return ourInstance;
    }

    public AstroLogInPreference()
    {

    }

    public void setLogged(boolean logged)
    {
        sharedPreferencesAstro.edit().putBoolean("AstroLogged",logged).apply();
    }

    public void setClear()
    {
        sharedPreferencesAstro.edit().remove("AstroLogged").apply();
    }

    public Boolean getLogged()
    {
        return sharedPreferencesAstro.getBoolean("AstroLogged",false);
    }

    public void setAstroName(String astroName)
    {
        sharedPreferencesAstro.edit().putString("AstroName",astroName).apply();
    }
    public String getAstroName()
    {
        return sharedPreferencesAstro.getString("AstroName",null);
    }

    public void setAstroEmail(String astroEmail)
    {
        sharedPreferencesAstro.edit().putString("AstroEmail",astroEmail).apply();
    }
    public String getAstroEmail()
    {
        return sharedPreferencesAstro.getString("AstroEmail",null);
    }

    public void setAstroMobile(String astroMobile)
    {
        sharedPreferencesAstro.edit().putString("AstroMobile",astroMobile).apply();
    }
    public static String getAstroMobile()
    {
        return sharedPreferencesAstro.getString("AstroMobile",null);
    }

    public void setAstro(String isAstro)
    {
        sharedPreferencesAstro.edit().putString("isAstrologer",isAstro).apply();
    }
    public String getAstro()
    {
        return sharedPreferencesAstro.getString("isAstrologer",null);
    }
}

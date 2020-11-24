package in.astroconsult.astroconsult.Chat;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import androidx.annotation.NonNull;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.Response.UserProfileResponse;
import in.astroconsult.astroconsult.pajo.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseAuthUtil {

    public static FirebaseAuthUtil instance;
    private static FirebaseAuth mAuth;
    private static DatabaseReference mDatabaseRef;
    String nameAstro;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String mobileAS;

    private FirebaseAuthUtil(){
    }

    public static FirebaseAuthUtil getInstance(FirebaseAuth auth, DatabaseReference databaseRef){
        if(instance == null){
            mAuth = auth;
            mDatabaseRef = databaseRef;
            instance = new FirebaseAuthUtil();
        }
      return instance;
    }

    public void registerUser(final Context context,String displayName, String UId, String displayMobile )
    {
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        //mobileUser = LogInPreference.getMobileNo();
        //mobileAstro = AstroLogInPreference.getAstroMobile();

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("device_token", deviceToken);
        userMap.put("name", displayName);
        userMap.put("online","true");
        userMap.put("phone",displayMobile);


//                    userMap.put("image", "default");
//                    userMap.put("thumb_img", "default");

        mDatabaseRef.child(UId).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    LogInPreference.getInstance(context).setFirebaseKey("true");
//                                mRegProgress.dismiss();
                               /* Intent mainIntent = new Intent(RegisterActivity.this, ChatActivity.class);

                                // clears the all activity tasks in task manager
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(mainIntent);
                                finish();
                            }*/
                }
            }
        });
    }

    public void getUser(final Context context, final String uid)
    {
        String mobileUser = LogInPreference.getMobileNo();
        Call<UserProfileResponse> call = ApiClient.getCliet().userProfile(api_key,mobileUser);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                if (response.isSuccessful())
                {
                    registerUser(context, response.body().getName(), uid,response.body().getMobile());
                }
            }
            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable throwable) {
            }
        });
    }

    public void getAstro(final Context context, final String uid)
    {
        mobileAS = AstroLogInPreference.getInstance(context).getAstroMobile();
        Call<ResponseModel> call = ApiClient.getCliet().astroProfile(api_key,mobileAS);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful())
                {
                    //assert response.body() != null;
                    registerUser(context, response.body().getName(), uid,response.body().getMobile());
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable throwable) {
            }
        });
    }

    public static void setCurrentUserOnline(Object online)
    {
        DatabaseReference mRootRef;
        String mCurrentUserId;
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mRootRef = FirebaseDatabase.getInstance().getReference();
            mRootRef.keepSynced(true);
            mRootRef.child("Users").child(mCurrentUserId).child("online").setValue(online);
        }

    }
}

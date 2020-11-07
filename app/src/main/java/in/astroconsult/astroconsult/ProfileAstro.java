package in.astroconsult.astroconsult;

import androidx.appcompat.app.AppCompatActivity;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Response.ReviewResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ProfileAstro extends AppCompatActivity {

    LinearLayout showData,noData;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_reviews);
        getSupportActionBar().hide();

        showData = findViewById(R.id.layout_review_1);
        noData = findViewById(R.id.layout_review_2);

        id = getIntent().getStringExtra("id");

        reviews();
    }

    public void reviews()
    {
        Call<ReviewResponse> call = ApiClient.getCliet().reviews(api_key,id);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {

                if (response.body().getStatus() == 206)
                {
                    showData.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.VISIBLE);
                }
                else if (response.body().getStatus() == 201)
                {
                    showData.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
                else
                {
                    showData.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }
}

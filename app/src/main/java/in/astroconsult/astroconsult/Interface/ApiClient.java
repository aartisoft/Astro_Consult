package in.astroconsult.astroconsult.Interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://astroconsult.in/api/";

    private static Retrofit retrofit = null;
    private static APIInterface apiWorkInterface;

    public static APIInterface getCliet(){
        if (retrofit == null)
        {
            Gson gson = new GsonBuilder().setLenient().create();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(10, TimeUnit.MINUTES) // write timeout
                    .readTimeout(10, TimeUnit.MINUTES);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            /*Values Inserted in retrofit*/
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        if (apiWorkInterface == null)
        {
            apiWorkInterface = retrofit.create(APIInterface.class);
        }
        return apiWorkInterface;
    }
}

package in.astroconsult.astroconsult.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.astroconsult.astroconsult.Response.AstroLogInResponse;
import in.astroconsult.astroconsult.Response.AstroSignUpResponse;
import in.astroconsult.astroconsult.Response.AstrologerWalletBalanceResponse;
import in.astroconsult.astroconsult.Response.BlogResponse;
import in.astroconsult.astroconsult.Response.EditProfileResponse;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.Response.LanguageResponse;
import in.astroconsult.astroconsult.Response.LogInResponse;
import in.astroconsult.astroconsult.Response.PalmReadingResponse;
import in.astroconsult.astroconsult.Response.ReviewResponse;
import in.astroconsult.astroconsult.Response.RozarResponse;
import in.astroconsult.astroconsult.Response.SignUpResponse;

import in.astroconsult.astroconsult.Response.SpecialityHomeResponse;
import in.astroconsult.astroconsult.Response.SpeliyDataModel;
import in.astroconsult.astroconsult.Response.StateResponse;
import in.astroconsult.astroconsult.Response.UserEditProfileResponse;
import in.astroconsult.astroconsult.Response.UserProfileResponse;
import in.astroconsult.astroconsult.Response.WalletBalanceResponse;
import in.astroconsult.astroconsult.Response.WalletHistoryModelResponse;
import in.astroconsult.astroconsult.pajo.ResponseModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface APIInterface {

    @FormUrlEncoded
    @POST("user_signup")
    Call<SignUpResponse> signUp(@Query("api_key") String key,
                                @Field("name") String name,
                                @Field("email") String email,
                                @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("user_login")
    Call<LogInResponse> logIn(@Query("api_key") String key,
                              @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("astrologer_login")
    Call<AstroLogInResponse> astroLogIn(@Query("api_key") String key,
                                        @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("astrologer_signup")
    Call<AstroSignUpResponse> astroSignUp(@Query("api_key") String key,
                                          @Field("name") String astroName,
                                          @Field("email") String astroEmail,
                                          @Field("mobile") String astroMobile);


    @GET("countries")
    Call<List<StateResponse>> stateResponse(@Query("api_key") String key,
                                            @Query("id") Integer id);

    @GET("alldatas")
    Call<List<LanguageResponse>> languageResponse(@Query("api_key") String key,
                                                  @Query("table") String table,
                                                  @Query("name") String name);

    @GET("alldatas")
    Call<ArrayList<SpeliyDataModel>> Spictg(@Query("api_key") String key,
                                       @Query("table") String table,
                                       @Query("name") String name);
    @GET("alldatas")
    Call<ArrayList<SpeliyDataModel>> spcityly(@Query("api_key") String key,
                                       @Query("table") String table,
                                       @Query("name") String name);

    @GET("getAstrologer")
    Call<ResponseModel> astroProfile(@Query("api_key") String key,
                                     @Query("mobile") String mobile);

    /*@FormUrlEncoded
    @POST("astrologer_edit")
    Call<EditProfileResponse> profile(@Query("api_key") String apiKey,
                                      @Field("experience") Integer ex,
                                      @Field("ccharge") Integer charge,
                                      @Field("profile") String profile,
                                      @Field("languages") String[] language,
                                      @Field("expertise") String[] expertise,
                                      @Field("speciality") String[] speciality,
                                      @Field("timmings") String timing,
                                      @Field("pan") Integer panNo,
                                      @Field("aadhar") Integer aadhar,
                                      @Field("certificates[]")Array)*/


    @Multipart
    @POST("astrologer_edit")
    Call<EditProfileResponse> uploadSurvey(@Query("api_key") String key,
                                           // @Query("expertise") String[] exp,
                                           @Part MultipartBody.Part[] partexpertise,
                                           @Part MultipartBody.Part[] partlang,
                                           @Part MultipartBody.Part[] partspcy,
                                           //@Part MultipartBody.Part[] parttimig,
                                           @Part MultipartBody.Part[] multipartTypedOutput,
                                           @Part MultipartBody.Part[] doc,
                                           @Part MultipartBody.Part[] maindoc,
                                           @PartMap HashMap<String, RequestBody> dra );

    @Multipart
    @POST("user_edit")
    Call<UserEditProfileResponse> uploadResponse(@Query("api_key") String key,
                                                 @Query("mobile") String mobile,
                                                 @Query("name") String name,
                                                 @Query("email") RequestBody email,
                                                 @Part MultipartBody.Part photo,
                                                 @Query("starshine") String shine);
    @Multipart
    @POST("user_edit")
    Call<ResponseBody> uploadResponse1(@Query("api_key") String key,
                                       @Part MultipartBody.Part[] photo,
                                       @PartMap HashMap<String, RequestBody> dra
    );

    @Multipart
    @POST("userpalmreading/")
    Call<PalmReadingResponse> uploadPlam(@Query("api_key") String key,
                                         @Part MultipartBody.Part[] photo,
                                         @PartMap HashMap<String,RequestBody> dra);

    @POST("display_reviews")
    Call<ReviewResponse> reviews(@Query("api_key") String key,
                                 @Query("id") String id);

    @Multipart
    @POST("/api/V1/CreateTicket")
    Call<EditProfileResponse> postTicket(@Query("api_key") String key,
                                         @Part("experience") RequestBody site_id,
                                           @Part("ccharge") RequestBody ccharge,
                                         @Part("profile") RequestBody profile,
                                         @Part("expertise") RequestBody expertise,
                                         @Part("speciality") RequestBody speciality,
                                         @Part("timmings") RequestBody timmings,
                                         @Part("pan") RequestBody pan,
                                         @Part("aadhar") RequestBody aadhar,
                                         @Part("profile") RequestBody file,
                                         @Part MultipartBody.Part[] documents,
                                         @Part MultipartBody.Part[] certificates);

    @Multipart
    @POST("./")
    Call<EditProfileResponse> addSubEvent(@Query("api_key") String key,
                                          @Query("experience") Integer ex,
                                          @Query("ccharge") Integer charge,
                                          @Query("profile") String profile,
                                          @Query("languages[]") ArrayList<String> languages,
                                          @Query("expertise[]") ArrayList<String> expertise,
                                          @Query("timmings[]") ArrayList<String> timmings,
                                          @Query("pan") Integer panNo,
                                          @Query("aadhar") Integer aadhar,
                                          @PartMap HashMap<String, RequestBody> documents,
                                          @PartMap HashMap<String, RequestBody> dra,
                                          @PartMap HashMap<String, RequestBody> profilepic);

    @GET("getastrologers")
    Call<ArrayList<GetAstrologerResponse>> getAstrologer(@Query("api_key") String apiKey);

    @GET("alldatas")
    Call<List<BlogResponse>> blogResponse(@Query("api_key") String api,
                                          @Query("table") String table,
                                          @Query("name") String name);

    @GET("getUser")
    Call<UserProfileResponse> userProfile(@Query("api_key") String key,
                                          @Query("mobile") String mobile);

    @GET("getastrobytypes")
    Call<HomeAstroResponse> specialityHome(@Query("api_key") String key);

    @GET("getastrobytypes")
    Call<SpecialityHomeResponse> specialityRecycler(@Query("api_key") String key);

    @FormUrlEncoded
    @POST("addMoney")
    Call<RozarResponse> rozarResponse(@Query("api_key") String key,
                                      @Field("mobile") String mobile,
                                      @Field("payment_id") String payId,
                                      @Field("amount") String amount,
                                      @Field("remark") String remark);

    @GET("userWalletBalance")
    Call<WalletBalanceResponse> walletBalance(@Query("api_key") String api,
                                              @Query("mobile") String mobile);

    @GET("userWalletHistory")
    Call<WalletHistoryModelResponse> walletHistory(@Query("api_key")String key,
                                                   @Query("mobile")String mobile);

    @GET("astroWalletBalance/")
    Call<AstrologerWalletBalanceResponse> walletBalanceAstro(@Query("api_key") String api,
                                                        @Query("mobile") String mobile);


}

package in.astroconsult.astroconsult;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.astroconsult.astroconsult.Chat.ChatActivity;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Interface.Utility;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import in.astroconsult.astroconsult.Response.PalmReadingResponse;
import in.astroconsult.astroconsult.Response.WalletBalanceResponse;
import in.astroconsult.astroconsult.Response.WalletHistoryModelResponse;
import in.astroconsult.astroconsult.ui.Wallet;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    RelativeLayout relativeLayout, relativeLayout2;
    ArrayList<GetAstrologerResponse> user = new ArrayList<>();
    ImageView chat;
    int position;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String mobile = LogInPreference.getMobileNo();
    List<WalletHistoryModelResponse> wallet = new ArrayList<>();
    String walletAmount, walletUserAmount1;
    TextView profile, locationName, ExperienceName, timingName, chargeName, languageName, numerologyName, astrologerName, astrologerProfile, astrologerRating;
    ImageView image;
    //Button FaceorplamStatusTxt;
    Dialog dialog;
    String mobileAstrologer, userMobile, astrologerId;
    ArrayList<String> showLList = new ArrayList<>();
    List<String> showLang;
    String faceString, palmString;
    ArrayList<String> showExpertise = new ArrayList<>();
    List<String> showExper = new ArrayList<>();
    ArrayList<String> showSpecList = new ArrayList<>();
    List<String> showSpec = new ArrayList<>();
    RatingBar ratingBar;
    String astrologerAmount;

    Bitmap bitmap, bitmap_1;
    ImageView Face_photo, palm_photo;
    File photfile, photfile_1;
    Button uploadface, uploadpalm;
    String picturePath;
    String checkFaceReader, checkPalmReader;
    String PalmValue;
    TextView txtFaceInstraction, txtPalmInstraction;
    LinearLayout faceLayout, palmLayout;
    LinearLayout main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        faceLayout = view.findViewById(R.id.linear_layout_face);
        palmLayout = view.findViewById(R.id.linear_layout_palm);
        Face_photo = view.findViewById(R.id.Face_image_upload_choose);
        palm_photo = view.findViewById(R.id.Palm_image_upload_choose);
        uploadface = view.findViewById(R.id.upload_face_image_txt_choose);
        uploadpalm = view.findViewById(R.id.upload_palm_image_txt_choose);
        txtFaceInstraction = view.findViewById(R.id.face_instraction);
        txtPalmInstraction = view.findViewById(R.id.Palm_Instruction);

        PalmValue = LogInPreference.getInstance(getActivity()).getPalmValue();
        checkFaceReader = LogInPreference.getInstance(getActivity()).getFaceYes();
        checkPalmReader = LogInPreference.getInstance(getActivity()).getPalmYes();
        //readInstruction = view.findViewById(R.id.readInstruction);
        TextView speciality = view.findViewById(R.id.nametxt1);
        TextView expertise = view.findViewById(R.id.nametxt2);
        locationName = view.findViewById(R.id.nametxtLoc);
        ExperienceName = view.findViewById(R.id.nametxtLocEx);
        timingName = view.findViewById(R.id.nametxtLocTime);
        chargeName = view.findViewById(R.id.nametxtLocRs);
        languageName = view.findViewById(R.id.nametxtLang);
        astrologerName = view.findViewById(R.id.nametxt);
        astrologerProfile = view.findViewById(R.id.nametxt1);
        astrologerRating = view.findViewById(R.id.nametxtRating);
        numerologyName = view.findViewById(R.id.nametxtNum);
        profile = view.findViewById(R.id.astroProfileName);
        image = view.findViewById(R.id.imageRinku);
        chat = view.findViewById(R.id.chatAstro);
        relativeLayout = view.findViewById(R.id.languageRelative);
        relativeLayout2 = view.findViewById(R.id.timeRelative);
        ratingBar = view.findViewById(R.id.rating);
        main = view.findViewById(R.id.fragment_profile_layout);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        last();


        uploadface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (photfile == null) {
                    Toast.makeText(getContext(), "Please Select Photo", Toast.LENGTH_SHORT).show();
                } else if (walletAmount != null && Double.parseDouble(walletAmount) >= Integer.parseInt(astrologerAmount)) {
                    //Toast.makeText(getContext(), "Please wallet ", Toast.LENGTH_SHORT).show();
                        sendData();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Wallet Amount Low!!");
                        builder.setMessage("Your amount is low for this service. You need minimum wallet amount of Rupees " + astrologerAmount + ". Refill your wallet now.");
                        builder.setPositiveButton("Refill Wallet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.setVisibility(View.GONE);
                                FragmentTransaction transaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                                Wallet someFragment = new Wallet();
                                transaction.replace(R.id.faceplamFragment, someFragment);
                                transaction.commit();
//                            Toast.makeText(getActivity(), "Submit", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
            }
        });

        uploadpalm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photfile_1 == null) {
                    Toast.makeText(getContext(), "Please Select Photo", Toast.LENGTH_SHORT).show();
                } else {
                    if (walletAmount != null && Double.parseDouble(walletAmount) >= Integer.parseInt(astrologerAmount)) {
                        sendDataPalm();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Wallet Amount Low!!");
                        builder.setMessage("Your amount is low for this service. You need minimum wallet amount of Rupees " + astrologerAmount + ". Refill your wallet now.");
                        builder.setPositiveButton("Refill Wallet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.setVisibility(View.GONE);
                                FragmentTransaction transaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                                Wallet someFragment = new Wallet();
                                transaction.replace(R.id.faceplamFragment, someFragment);
                                transaction.commit();
//                            Toast.makeText(getActivity(), "Submit", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });


        Face_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        palm_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage_1();
            }
        });

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(getContext(), ProfileAstro.class);
                    intent.putExtra("id", user.get(position).getId());
                    startActivity(intent);
                }
                return true;
            }
        });

        dialog = new Dialog(getContext());

        userMobile = LogInPreference.getInstance(getContext()).getMobileNo();

        //Toast.makeText(getContext(), ""+userMobile, Toast.LENGTH_SHORT).show();

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (walletAmount != null && Float.parseFloat(walletAmount) >= Float.parseFloat(walletUserAmount1)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Start Chat");
                    builder.setMessage("You will be charged Rs." + walletUserAmount1 + " per minute. Do you want to Continue?");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            main.setVisibility(View.GONE);
                            fetchAstroDetails();
                            main.setVisibility(View.VISIBLE);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            main.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Wallet Amount Low!!");
                    builder.setMessage("Your amount is low for this service. You need minimum wallet amount of Rupees " + astrologerAmount + ". Refill your wallet now.");
                    builder.setPositiveButton("Refill Wallet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            main.setVisibility(View.GONE);
                            FragmentTransaction transaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                            Wallet someFragment = new Wallet();
                            transaction.replace(R.id.faceplamFragment, someFragment);
                            transaction.commit();
//                            Toast.makeText(getActivity(), "Submit", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            main.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("List of Languages")

                        .setItems(showLList.toArray(new String[0]), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), user.get(position).getLanguages().get(which) + " is clicked", Toast.LENGTH_SHORT).show();
                            }
                        });

                builder.setPositiveButton("OK", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timing();
            }
        });

        Bundle bundle = getArguments();

        if (bundle != null) {
            //Productid = bundle.getString("key");
            String key1 = bundle.getString("From");

            if (key1.contains("searchflag")) {

                if (key1.equalsIgnoreCase("searchflag")) {

                    user = bundle.getParcelableArrayList("searches");
                    position = bundle.getInt("posis");

                    mobileAstrologer = user.get(position).getMobile();
                    faceString = user.get(position).getFacereader();
                    LogInPreference.getInstance(getActivity()).setFaceYes(faceString);
                    palmString = user.get(position).getPalmreader();
                    LogInPreference.getInstance(getActivity()).setPalmYes(palmString);
                    astrologerId = user.get(position).getId();
                    LogInPreference.getInstance(getActivity()).setAstroId(astrologerId);
                    astrologerAmount = user.get(position).getFcharge();
                    LogInPreference.getInstance(getActivity()).setPalmValue(astrologerAmount);
                    walletUserAmount1 = user.get(position).getCcharge();

                    showLang = user.get(position).getLanguages();
                    //showLangString = showLang.split(",");
                    for (int i = 0; i < showLang.size(); i++) {
                        showLList.add(showLang.get(i));
                    }
                    timingName.setText(user.get(position).getTimmings().getMondaystart());

                    showExper = user.get(position).getExpertise();

                    for (int i = 0; i < showExper.size(); i++) {
                        showExpertise.add(showExper.get(i));
                        expertise.setText(showExpertise.get(i));
                    }

                    showSpec = user.get(position).getSpeciality();

                    for (int i = 0; i < showSpec.size(); i++) {
                        showSpecList.add(showSpec.get(i));
                        speciality.setText(showSpecList.get(i));
                    }

                    checkFaceReader = user.get(position).getFacereader();
                    checkPalmReader = user.get(position).getPalmreader();
                    locationName.setText(user.get(position).getLocation().substring(0, 1).toUpperCase() + user.get(position).getLocation().substring(1));
                    ExperienceName.setText(user.get(position).getExperience());
                    chargeName.setText(user.get(position).getCcharge() + " / Min.");
                    astrologerRating.setText(user.get(position).getRating());
                    profile.setText(user.get(position).getProfile());
                    Picasso.get().load(user.get(position).getPhoto()).into(image);
                 /*for (int i = 0; i < user.get(position).getAstrologers().get(position).getTimmings().size() ; i++) {
                     timingName.setText(user.get(position).getAstrologers().get(position).getTimmings().get(i));
                 }*/
                    languageName.setText(user.get(position).getLanguages().get(0));
                    astrologerName.setText(user.get(position).getName());
                    //speciality.setText(user.get(position).getName());
                    //expertise.setText(user.get(position).getSlug());

                    //Toast.makeText(getContext(),++ "hello"+user.get(position).getName(), Toast.LENGTH_LONG).show();
                }
            }
        }

        if (checkFaceReader.equalsIgnoreCase("Yes")) {
            faceLayout.setVisibility(View.VISIBLE);
            txtFaceInstraction.setVisibility(View.VISIBLE);
            txtFaceInstraction.setText("This astrologer know face reading, You are interest for this, Just clicked your face Photo and upload it in below. we charge for this service Rupees of " + PalmValue + ".");

        } else {
            faceLayout.setVisibility(View.GONE);
            txtFaceInstraction.setVisibility(View.GONE);
        }

        if (checkPalmReader.equalsIgnoreCase("Yes")) {
            palmLayout.setVisibility(View.VISIBLE);
            txtPalmInstraction.setText("This astrologer know palm reading, You are interest for this, Just clicked your palm Photo and upload it in below. we charge for this service Rupees of " + PalmValue + ".");
            txtPalmInstraction.setVisibility(View.VISIBLE);
        } else {
            palmLayout.setVisibility(View.GONE);
            txtPalmInstraction.setVisibility(View.GONE);
        }

        return view;
    }


    private void fetchAstroDetails() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        String phone = s.hasChild("phone") ? s.child("phone").getValue().toString() : null;
                        if (phone != null && phone.equals(user.get(position).getMobile())) {

                            String online =  s.hasChild("online") ? s.child("online").getValue().toString() : "";

                            if(true){
                                Intent intent = new Intent(getContext(), ChatActivity.class);
                                intent.putExtra("user_id", s.getKey().toString());
                                intent.putExtra("astro_mobile", mobileAstrologer);
                                if(getMaxMinutesToChat()!=0){
                                    intent.putExtra("maxMinutesToChat", getMaxMinutesToChat());
                                }
                                intent.putExtra("user_name", s.hasChild("name") ? s.child("name").toString() : "");
                                startActivity(intent);
                            }else {
                                Snackbar.make(locationName, "Astrologer not active now, Please try again Later!!!", Snackbar.LENGTH_SHORT).show();
                            }
                            break;
                        } else {
                            Snackbar.make(locationName, "There is something wrong with Astrologer!", Snackbar.LENGTH_SHORT).show();
                            //Snackbar.make(locationName, "Astrologer not active now, Please try again Later!!!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int getMaxMinutesToChat() {
        Float perMinCharge = Float.valueOf(user.get(position).getCcharge());
        if(walletAmount != null && perMinCharge != null && perMinCharge!=0)
        {
            return ((int)(Float.parseFloat(walletAmount)/perMinCharge));
        }else {
            return 0;
        }
    }

    public void amount() {
        dialog.setContentView(R.layout.profile_image_dialog);
        TextView showAmount = dialog.findViewById(R.id.dialog_astro_amount);
        Button submit = dialog.findViewById(R.id.ok_dialog);

        showAmount.setText(user.get(position).getFcharge());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 10000);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
    }

    public void timing() {
        dialog.setContentView(R.layout.profile_time_dialog);
        TextView sunday = (TextView) dialog.findViewById(R.id.sundayTime);
        TextView monday = (TextView) dialog.findViewById(R.id.mondayTime);
        TextView tuesday = (TextView) dialog.findViewById(R.id.tuesdayTime);
        TextView wednesday = (TextView) dialog.findViewById(R.id.wednesdayTime);
        TextView thursday = (TextView) dialog.findViewById(R.id.thursdayTime);
        TextView friday = (TextView) dialog.findViewById(R.id.fridayTime);
        TextView saturday = (TextView) dialog.findViewById(R.id.saturdayTime);

        String s = user.get(position).getTimmings().getFridayend();

        /*for (int i = 0; i < showTime.size(); i++) {
            time = showTime.get(i);
            stringTime = time.split(":");
            Toast.makeText(getContext(), ""+stringTime.toString(), Toast.LENGTH_SHORT).show();
        }*/


        //Toast.makeText(getContext(), ""+showTTime, Toast.LENGTH_SHORT).show();


        if (user.get(position).getTimmings().getMondayend() != null || user.get(position).getTimmings().getMondaystart() != null) {
            monday.setText(user.get(position).getTimmings().getMondaystart() + " - " + user.get(position).getTimmings().getMondayend());
        }
        /*if(user.get(position).getAstrologers().get(position).getTimmings().getMondayend() == null)
        {
            monday.setText("0:00");
        }
        else
        {
            monday.setText("0:00"+" - "+"0:00");
        }*/
        if (user.get(position).getTimmings().getSundaystart() != null || user.get(position).getTimmings().getSundayend() != null) {
            sunday.setText(user.get(position).getTimmings().getSundaystart() + " - " + user.get(position).getTimmings().getSundayend());
        }
        /*if (user.get(position).getAstrologers().get(position).getTimmings().getSundayend() == null)
        {
            sunday.setText("0:00");
        }
        else
        {
            sunday.setText("0:00"+" - "+"0:00");
        }*/
        if (user.get(position).getTimmings().getWednesdayend() != null || user.get(position).getTimmings().getWednesdaystart() != null) {
            wednesday.setText(user.get(position).getTimmings().getWednesdaystart() + " - " + user.get(position).getTimmings().getWednesdayend());
        }
        /*if (user.get(position).getAstrologers().get(position).getTimmings().getWednesdayend() == null)
        {
            wednesday.setText("0:00");
        }
        else
        {
            wednesday.setText("0:00"+" - "+"0:00");
        }*/
        if (user.get(position).getTimmings().getTuesdayend() != null || user.get(position).getTimmings().getTuesdaystart() != null) {
            tuesday.setText(user.get(position).getTimmings().getTuesdaystart() + " - " + user.get(position).getTimmings().getTuesdayend());
        }
        /*if (user.get(position).getAstrologers().get(position).getTimmings().getTuesdayend() == null)
        {
            tuesday.setText("0:00");
        }
        else
        {
            tuesday.setText("0:00"+" - "+"0:00");
        }*/
        if (user.get(position).getTimmings().getThursdayend() != null || user.get(position).getTimmings().getThursdaystart() != null) {
            thursday.setText(user.get(position).getTimmings().getThursdaystart() + " - " + user.get(position).getTimmings().getThursdayend());
        }
        /*if (user.get(position).getAstrologers().get(position).getTimmings().getThursdayend() == null)
        {
            thursday.setText("0:00");
        }
        else
        {
            thursday.setText("0:00"+" - "+"0:00");
        }*/
        if (user.get(position).getTimmings() != null || user.get(position).getTimmings().getFridaystart() != null) {
            friday.setText(user.get(position).getTimmings().getFridaystart() + " - " + user.get(position).getTimmings().getFridayend());
        }
        /*if (user.get(position).getAstrologers().get(position).getTimmings().getFridayend() == null)
        {
            friday.setText("0:00");
        }
        else
        {
            friday.setText("0:00"+" - "+"0:00");
        }*/
        if (user.get(position).getTimmings().getSaturdaystart() != null || user.get(position).getTimmings().getSaturdaystart() != null) {
            saturday.setText(user.get(position).getTimmings().getSaturdaystart() + " - " + user.get(position).getTimmings().getSaturdayend());
        }
        /*if (user.get(position).getAstrologers().get(position).getTimmings().getSaturdayend() == null)
        {
            saturday.setText("0:00");
        }
        else
        {
            saturday.setText("0:00"+" - "+"0:00");
        }*/
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 10000);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
    }

    public void last() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();
        //Log.d("TAG","hello");
        Call<WalletBalanceResponse> call = ApiClient.getCliet().walletBalance(api_key,mobile);
        call.enqueue(new Callback<WalletBalanceResponse>() {
            @Override
            public void onResponse(Call<WalletBalanceResponse> call, Response<WalletBalanceResponse> response) {
                WalletBalanceResponse wallet = response.body();


                if (wallet.getStatus() == 200)
                {
                    //Log.d("vishal","Hello");
                    walletAmount = wallet.getBalance();
                    dialog.dismiss();
                }
                else if (wallet.getStatus() == 201)
                {
                    //walletBalance.setText(wallet.getBalance());
                    //Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WalletBalanceResponse> call, Throwable throwable) {
                dialog.dismiss();
                //Toast.makeText(getActivity(), "Error !! While fetching balance", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage_1() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    //  File file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/attachments").getPath(),
                    //         System.currentTimeMillis()+ ".jpg");
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "image.jpg");
                    Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    Log.d("556677", "saveBitmapToFile: " + f);
                    photfile_1 = f;
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(getActivity())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri captureMediaFile = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
                    Log.e("capturemedia file url", "" + captureMediaFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    startActivityForResult(intent, 3);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 4);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void sendDataPalm() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String, RequestBody> signUpMap = new HashMap<>();

        //signUpMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString()));
        signUpMap.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), userMobile));
        signUpMap.put("astrologer_id", RequestBody.create(MediaType.parse("multipart/form-data"), astrologerId));
        //signUpMap.put("starshine", RequestBody.create(MediaType.parse("multipart/form-data"), spinner.getSelectedItem().toString()));
        signUpMap.put("photo", RequestBody.create(MediaType.parse("multipart/form-data"), photfile_1.getPath()));

        //RequestBody nameSend = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
        //RequestBody emailSend = RequestBody.create(MediaType.parse("multipart/form-data"), email.getText().toString());
        //RequestBody mobileSend = RequestBody.create(MediaType.parse("multipart/form_data"), mobile.getText().toString());
        //RequestBody spinnerSelect = RequestBody.create(MediaType.parse("multipart/form_data"), spinner.getSelectedItem().toString());
        MultipartBody.Part[] doc = new MultipartBody.Part[3];

        RequestBody mainpvi = RequestBody.create(MediaType.parse("image/*"), photfile_1);
        doc[0] = MultipartBody.Part.createFormData("documents", photfile_1.getPath(), mainpvi);

        Call<PalmReadingResponse> call = ApiClient.getCliet().uploadPlam(api_key, doc, signUpMap);

        call.enqueue(new Callback<PalmReadingResponse>() {
            @Override
            public void onResponse(Call<PalmReadingResponse> call, Response<PalmReadingResponse> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Uploaded Palm Image Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Server Issues Try After Sometime !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PalmReadingResponse> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getContext(), "" + throwable, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    //  File file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/attachments").getPath(),
                    //         System.currentTimeMillis()+ ".jpg");
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "image.jpg");
                    Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    Log.d("556677", "saveBitmapToFile: " + f);
                    photfile = f;
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(getActivity())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri captureMediaFile = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
                    Log.e("capturemedia file url", "" + captureMediaFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //SharedPreferences sharedpreferences = P1_Update_Account.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //SharedPreferences.Editor edit = sharedpreferences.edit();
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        // photfile = f.getAbsoluteFile();

                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(photfile.getAbsolutePath(),
                            bitmapOptions);
                    Log.d("334455", "photfile: " + photfile);
                    Log.d("334455", "onActivityResultabc: " + f.getAbsolutePath());
                    Log.d("334455", "onActivityResultbitmp: " + bitmap);
                    Face_photo.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    // Uri pic =  getImageUri(EditProfileActivity.this,bitmap);
                    //       picturePath = String.valueOf(pic);
                    //  encodedimg = Base64.encodeToString(b, Base64.DEFAULT);

                    //  Log.d("123456", "1245: "+encodedimg);
                    FileOutputStream fos = new FileOutputStream(photfile);
                    fos.write(b);
                    fos.flush();
                    fos.close();
                    Log.d("334455", "onActivityResultbitmp: " + photfile);
                    Log.d("334455", "fos: " + fos);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    //  photfile = f;
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String Path = c.getString(columnIndex);
                c.close();
                bitmap = (BitmapFactory.decodeFile(Path));
                picturePath = String.valueOf(selectedImage);
                Uri u = Uri.parse(Path);
                photfile = new File(u.getPath());
                Log.w("path of image from gallery......******************.........", picturePath + "");
                Glide.with(this)
                        .load(picturePath)

                        .into(Face_photo);

            }
            if (requestCode == 3) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        // photfile = f.getAbsoluteFile();

                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap_1 = BitmapFactory.decodeFile(photfile_1.getAbsolutePath(),
                            bitmapOptions);
                    Log.d("334455", "photfile: " + photfile_1);
                    Log.d("334455", "onActivityResultabc: " + f.getAbsolutePath());
                    Log.d("334455", "onActivityResultbitmp: " + bitmap_1);
                    palm_photo.setImageBitmap(bitmap_1);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap_1.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    // Uri pic =  getImageUri(EditProfileActivity.this,bitmap);
                    //       picturePath = String.valueOf(pic);
                    //  encodedimg = Base64.encodeToString(b, Base64.DEFAULT);

                    //  Log.d("123456", "1245: "+encodedimg);
                    FileOutputStream fos = new FileOutputStream(photfile_1);
                    fos.write(b);
                    fos.flush();
                    fos.close();
                    Log.d("334455", "onActivityResultbitmp: " + photfile_1);
                    Log.d("334455", "fos: " + fos);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    //  photfile = f;
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 4) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String Path = c.getString(columnIndex);
                c.close();
                bitmap_1 = (BitmapFactory.decodeFile(Path));
                picturePath = String.valueOf(selectedImage);
                Uri u = Uri.parse(Path);
                photfile_1 = new File(u.getPath());
                Log.w("path of image from gallery......******************.........", picturePath + "");
                Glide.with(this)
                        .load(picturePath)

                        .into(palm_photo);

            }
        }

    }

    public void sendData() {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String, RequestBody> signUpMap = new HashMap<>();

        //signUpMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString()));
        signUpMap.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), userMobile));
        signUpMap.put("astrologer_id", RequestBody.create(MediaType.parse("multipart/form-data"), astrologerId));
        //signUpMap.put("starshine", RequestBody.create(MediaType.parse("multipart/form-data"), spinner.getSelectedItem().toString()));
        signUpMap.put("photo", RequestBody.create(MediaType.parse("multipart/form-data"), photfile.getPath()));

        //RequestBody nameSend = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
        //RequestBody emailSend = RequestBody.create(MediaType.parse("multipart/form-data"), email.getText().toString());
        //RequestBody mobileSend = RequestBody.create(MediaType.parse("multipart/form_data"), mobile.getText().toString());
        //RequestBody spinnerSelect = RequestBody.create(MediaType.parse("multipart/form_data"), spinner.getSelectedItem().toString());
        MultipartBody.Part[] doc = new MultipartBody.Part[3];

        RequestBody mainpvi = RequestBody.create(MediaType.parse("image/*"), photfile);
        doc[0] = MultipartBody.Part.createFormData("documents", photfile.getPath(), mainpvi);

        Call<PalmReadingResponse> call = ApiClient.getCliet().uploadPlam(api_key, doc, signUpMap);

        call.enqueue(new Callback<PalmReadingResponse>() {
            @Override
            public void onResponse(Call<PalmReadingResponse> call, Response<PalmReadingResponse> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Uploaded Face Image Successfully  ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Server Issue Try after Sometime !!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PalmReadingResponse> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getContext(), "" + throwable, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onBackPressed()
    {

    }
}

package in.astroconsult.astroconsult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Interface.Utility;
import in.astroconsult.astroconsult.Model.ImageStoreModel;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Response.EditProfileResponse;
import in.astroconsult.astroconsult.Response.LanguageResponse;
import in.astroconsult.astroconsult.Response.SpeliyDataModel;
import in.astroconsult.astroconsult.Response.StateResponse;
import in.astroconsult.astroconsult.pajo.ResponseModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AstroProfileSignUp extends AppCompatActivity {
    Integer itemcat;
    JSONArray jsonArraytimimg= new JSONArray();

    String picturePath, cityString,stateString;
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    ArrayList<String> selectedspectyStrings = new ArrayList<>();
    ArrayList<String> selectedexpertiseStrings = new ArrayList<>();
    ArrayList<String> selectedlangStrings = new ArrayList<>();
    ArrayList<String> selectedTimeStrings = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> sid = new ArrayList<>();
    ArrayList<String> eid = new ArrayList<String>();
    String Arrexper[];
    String sundayStringStar, sundayStringEnd, mondayStringStart, mondayStringEnd, tuesdayStringStart, tuesdayStringEnd, wednesdayStringStart, wednesdayStringEnd, thursdayStringStart, thursdayStringEnd, fridayStringStart, fridayStringEnd, saturdayStringStart, saturdayStringEnd;

    ArrayList<SpeliyDataModel> SpeliyDatalist = new ArrayList<>();
    private String url = "http://astroconsult.in/api/alldatas?api_key=w0fp55cIdJ6lLuOqVEd251zKw6lnNd&table=_types&name=Types";
    ArrayList<Uri> Images = new ArrayList<>();
    List<String> imagesEncodedList;
    String imageEncoded;
    ArrayList<ImageStoreModel> imageStoreModels = new ArrayList<ImageStoreModel>();
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    ArrayList<File> mArrayFile = new ArrayList<File>();
    String imgpath, encodedimg;
    Bitmap bitmap, aadhrbitmp, penbip;
    TextView expertise, speciality, languages, location;
    TextView name, email, mobile, timeTable, p5_spineer_expertise;
    ImageView aadhar, pancard, certificate, photoYour;
    final Context context = this;
    String profileName, profileMobile, profileEmail;
    Button registerProfile;
    Spinner state, city;
    String st, cit, specyId,expeId,langId;
    EditText aadharNo, panNo, experience, profile, charges, palmEdit, faceEdit;
    List<String> stringList = new ArrayList<>();
    List<String> cityList = new ArrayList<>();
    String[] listLanguage,speclityList,expeList,languList;
    String language, spcilty, faceYES, faceNO, palmYES, palmNO, palmEditString, faceEditString;
    File photo, aadhfile, penfile;
    String mondayStart, mondayEnd, tuesdayStart, tuesdayEnd, wednesdayStart, wednesdayEnd, thursdayStart, thursdayEnd, fridayStart, fridayEnd, saturdayStart, saturdayEnd, sundayStart, sundayEnd;
    Date sundayDateStart, sundayDateEnd, mondayDateStart, mondayDateEnd, tuesdayDateStart, tuesdayDateEnd, wednesdayDateStart, wednesdayDateEnd, thursdayDateStart, thursdayDateEnd, fridayDateStart, fridayDateEnd, saturdayDateStart, saturdayDateEnd;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    Switch palm_btn, face_btn;
    RelativeLayout relativeLayoutFace,relativeLayoutPalm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_profile_sign_up);

        getSupportActionBar().hide();

        relativeLayoutFace = findViewById(R.id.edit_relative_face);
        relativeLayoutPalm = findViewById(R.id.edit_relative_palm);
        palm_btn = findViewById(R.id.abc_astro_profile_sign_up_switch_btn);
        aadharNo = (EditText) findViewById(R.id.P5_edit_aadhar);
        panNo = (EditText) findViewById(R.id.P5_edit_penNo);
        experience = (EditText) findViewById(R.id.P5_edit_experience);
        profile = (EditText) findViewById(R.id.P5_edit_profile);
        charges = (EditText) findViewById(R.id.P5_edit_ccharge);
        timeTable = (TextView) findViewById(R.id.P5_working_time);
        expertise = (TextView) findViewById(R.id.p5_spineer_expertise);
        speciality = (TextView) findViewById(R.id.p5_Spinner_Speciality);
        languages = (TextView) findViewById(R.id.p5_Spinner_Languages);
        // location = (TextView) findViewById(R.id.p5_Spinner_Location);
        aadhar = (ImageView) findViewById(R.id.aadharImage);
        pancard = (ImageView) findViewById(R.id.pancardImage);
        p5_spineer_expertise = findViewById(R.id.p5_spineer_expertise);
        certificate = (ImageView) findViewById(R.id.certificateImage);
        photoYour = (ImageView) findViewById(R.id.photoImage);
        name = (TextView) findViewById(R.id.P5_full_name_txt);
        email = (TextView) findViewById(R.id.P5_email_id_txt);
        mobile = (TextView) findViewById(R.id.P5_mobile_no_txt);
        registerProfile = (Button) findViewById(R.id.p5_btn_submit);
        state = (Spinner) findViewById(R.id.P5_spinner_state);
        city = (Spinner) findViewById(R.id.P5_spinner_city);
        faceEdit = (EditText) findViewById(R.id.edit_face);
        palmEdit = (EditText) findViewById(R.id.edit_palm);
        face_btn = findViewById(R.id.abc_astro_profile_sign_up_switch_btn_1);
        //saveData = findViewById(R.id.p5_btn_submit);
        profileName = AstroLogInPreference.getInstance(getApplicationContext()).getAstroName();
        profileMobile = AstroLogInPreference.getInstance(AstroProfileSignUp.this).getAstroMobile();
        profileEmail = AstroLogInPreference.getInstance(getApplicationContext()).getAstroEmail();

        name.setText(profileName);
        email.setText(profileEmail);
        mobile.setText(profileMobile);

        getAstroHistory();

        ActivityCompat.requestPermissions(AstroProfileSignUp.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        palm_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    palmYES = "Yes";
                    relativeLayoutPalm.setVisibility(View.VISIBLE);
                    palmEditString = palmEdit.getText().toString();
                    //Toast.makeText(AstroProfileSignUp.this, "" + palmYES, Toast.LENGTH_LONG).show();
                } else {
                    palmYES = "No";
                    relativeLayoutPalm.setVisibility(View.GONE);
                    //Toast.makeText(AstroProfileSignUp.this, "" + palmYES, Toast.LENGTH_LONG).show();
                }
            }
        });

        face_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    faceYES = "Yes";
                    relativeLayoutFace.setVisibility(View.VISIBLE);
                    faceEditString = faceEdit.getText().toString();
                    //Toast.makeText(AstroProfileSignUp.this, "" + faceYES, Toast.LENGTH_SHORT).show();
                } else {
                    faceYES = "No";
                    relativeLayoutFace.setVisibility(View.GONE);
                    //Toast.makeText(AstroProfileSignUp.this, "" + faceYES, Toast.LENGTH_SHORT).show();
                }
            }
        });


        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> selectTime = new ArrayList<>();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.time_picker_layout);
                ImageView mondayStartImage = dialog.findViewById(R.id.mondayStartImage);
                ImageView mondayEndImage = dialog.findViewById(R.id.mondayEndImage);
                ImageView tuesdayStartImage = dialog.findViewById(R.id.tuesdayStartImage);
                ImageView tuesdayEndImage = dialog.findViewById(R.id.tuesdayEndImage);
                final ImageView wednesdayStartImage = dialog.findViewById(R.id.wednesdayStartImage);
                ImageView wednesdayEndImage = dialog.findViewById(R.id.wednesdayEndImage);
                ImageView thursdayStartImage = dialog.findViewById(R.id.thursdayStartImage);
                ImageView thursdayEndImage = dialog.findViewById(R.id.thursdayEndImage);
                ImageView fridayStartImage = dialog.findViewById(R.id.fridayStartImage);
                ImageView fridayEndImage = dialog.findViewById(R.id.fridayEndImage);
                ImageView saturdayStartImage = dialog.findViewById(R.id.saturdayStartImage);
                ImageView saturdayEndImage = dialog.findViewById(R.id.saturdayEndImage);
                ImageView sundayStartImage = dialog.findViewById(R.id.sundayStartImage);
                ImageView sundayEndImage = dialog.findViewById(R.id.sundayEndImage);

                final EditText mondayStartTime = dialog.findViewById(R.id.mondayStartTime);
                final EditText mondayEndTime = dialog.findViewById(R.id.mondayEndTime);
                final EditText tuesdayStartTime = dialog.findViewById(R.id.tuesdayStartTime);
                final EditText tuesayEndTime = dialog.findViewById(R.id.tuesdayEndTime);
                final EditText wednesdayStartTime = dialog.findViewById(R.id.wednesdayStartTime);
                final EditText wednesdayEndTime = dialog.findViewById(R.id.wednesdayEndTime);
                final EditText thursdayStartTime = dialog.findViewById(R.id.thursdayStartTime);
                final EditText thursdayEndTime = dialog.findViewById(R.id.thursdayEndTime);
                final EditText fridayStartTime = dialog.findViewById(R.id.fridayStartTime);
                final EditText fridayEndTime = dialog.findViewById(R.id.fridayEndTime);
                final EditText saturdayStartTime = dialog.findViewById(R.id.saturdayStartTime);
                final EditText saturdayEndTime = dialog.findViewById(R.id.saturdayEndTime);
                final EditText sundayStartTime = dialog.findViewById(R.id.sundayStartTime);
                final EditText sundayEndTime = dialog.findViewById(R.id.sundayEndTime);

                Button submitTime = dialog.findViewById(R.id.submitTime);

                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER);

                sundayStartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    sundayStart = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        sundayStart = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        sundayStart = i + " : " + i1 + "PM";
                                    }
                                }
                                sundayStartTime.setText(sundayStart);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                sundayDateStart = null;
                                try {
                                    sundayDateStart = fmt.parse(time);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(sundayDateStart);
                                sundayStringStar = fmtOut.format(sundayDateStart);

                                sundayStartTime.setText(formattedTime);
                                //selectedTimeStrings.add(sundayStartTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                        //selectTime.add(sundayStartTime.getText().toString());
                    }
                });

                sundayEndImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedTimeStrings.clear();
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    sundayEnd = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        sundayEnd = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        sundayEnd = i + " : " + i1 + "PM";
                                    }
                                }
                                sundayEndTime.setText(sundayEnd);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                sundayDateEnd = null;
                                try {
                                    sundayDateEnd = fmt.parse(time);
                                    if (sundayDateEnd.compareTo(sundayDateStart) < 0) {
                                        // Do your staff
                                        Log.d("Return", "getTestTime11 less than getCurrentTime ");
                                        //Toast.makeText(AstroProfileSignUp.this, "start time less then end", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("Return", "getTestTime older than getCurrentTime ");
                                    }
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(sundayDateEnd);

                                sundayStringEnd = fmtOut.format(sundayDateEnd);

                                sundayEndTime.setText(formattedTime);
                                //selectedTimeStrings.add(sundayEndTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                mondayStartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    mondayStart = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        mondayStart = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        mondayStart = i + " : " + i1 + "PM";
                                    }
                                }
                                mondayStartTime.setText(mondayStart);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                mondayDateStart = null;
                                try {
                                    mondayDateStart = fmt.parse(time);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(mondayDateStart);
                                mondayStringStart = fmtOut.format(mondayDateStart);

                                mondayStartTime.setText(formattedTime);
                                //selectedTimeStrings.add(mondayStartTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                mondayEndImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    mondayEnd = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        mondayEnd = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        mondayEnd = i + " : " + i1 + "PM";
                                    }
                                }
                                mondayEndTime.setText(mondayEnd);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                mondayDateEnd = null;
                                try {
                                    mondayDateEnd = fmt.parse(time);
                                    if (mondayDateEnd.compareTo(mondayDateStart) < 0) {
                                        // Do your staff
                                        Log.d("Return", "getTestTime11 less than getCurrentTime ");
                                        //Toast.makeText(AstroProfileSignUp.this, "start time less then end", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("Return", "getTestTime older than getCurrentTime ");
                                    }
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(mondayDateEnd);
                                mondayStringEnd = fmtOut.format(mondayDateEnd);
                                mondayEndTime.setText(formattedTime);
                                //selectedTimeStrings.add(mondayEndTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                tuesdayStartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    tuesdayStart = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        tuesdayStart = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        tuesdayStart = i + " : " + i1 + "PM";
                                    }
                                }
                                tuesdayStartTime.setText(tuesdayStart);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                tuesdayDateStart = null;
                                try {
                                    tuesdayDateStart = fmt.parse(time);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(tuesdayDateStart);
                                tuesdayStringStart = fmtOut.format(tuesdayDateStart);

                                tuesdayStartTime.setText(formattedTime);
                                //selectedTimeStrings.add(tuesdayStartTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                tuesdayEndImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    tuesdayEnd = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        tuesdayEnd = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        tuesdayEnd = i + " : " + i1 + "PM";
                                    }
                                }
                                tuesayEndTime.setText(tuesdayEnd);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                tuesdayDateEnd = null;
                                try {
                                    tuesdayDateEnd = fmt.parse(time);
                                    if (tuesdayDateEnd.compareTo(tuesdayDateStart) < 0) {
                                        // Do your staff
                                        Log.d("Return", "getTestTime11 less than getCurrentTime ");
                                        //Toast.makeText(AstroProfileSignUp.this, "start time less then end", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("Return", "getTestTime older than getCurrentTime ");
                                    }
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");
                                String formattedTime = fmtOut.format(tuesdayDateEnd);
                                tuesdayStringEnd = fmtOut.format(tuesdayDateEnd);

                                tuesayEndTime.setText(formattedTime);
                                //selectedTimeStrings.add(tuesayEndTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                wednesdayStartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    wednesdayStart = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        wednesdayStart = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        wednesdayStart = i + " : " + i1 + "PM";
                                    }
                                }
                                wednesdayStartTime.setText(wednesdayStart);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                wednesdayDateStart = null;
                                try {
                                    wednesdayDateStart = fmt.parse(time);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(wednesdayDateStart);
                                wednesdayStringStart = fmtOut.format(mondayDateStart);

                                wednesdayStartTime.setText(formattedTime);

                                //selectedTimeStrings.add(wednesdayStartTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                wednesdayEndImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    wednesdayEnd = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        wednesdayEnd = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        wednesdayEnd = i + " : " + i1 + "PM";
                                    }
                                }
                                wednesdayEndTime.setText(wednesdayEnd);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                wednesdayDateEnd = null;
                                try {
                                    wednesdayDateEnd = fmt.parse(time);
                                    if (wednesdayDateEnd.compareTo(wednesdayDateStart) < 0) {
                                        // Do your staff
                                        Log.d("Return", "getTestTime11 less than getCurrentTime ");
                                        //Toast.makeText(AstroProfileSignUp.this, "start time less then end", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("Return", "getTestTime older than getCurrentTime ");
                                    }
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(wednesdayDateEnd);
                                wednesdayStringEnd = fmtOut.format(wednesdayDateEnd);
                                wednesdayEndTime.setText(formattedTime);
                                //selectedTimeStrings.add(wednesdayEndTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                thursdayStartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    thursdayStart = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        thursdayStart = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        thursdayStart = i + " : " + i1 + "PM";
                                    }
                                }
                                thursdayStartTime.setText(thursdayStart);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                thursdayDateStart = null;
                                try {
                                    thursdayDateStart = fmt.parse(time);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(thursdayDateStart);
                                thursdayStringStart = fmtOut.format(thursdayDateStart);

                                thursdayStartTime.setText(formattedTime);
                                //selectedTimeStrings.add(thursdayStartTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                thursdayEndImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    thursdayEnd = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        thursdayEnd = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        thursdayEnd = i + " : " + i1 + "PM";
                                    }
                                }
                                thursdayEndTime.setText(thursdayEnd);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                thursdayDateEnd = null;
                                try {
                                    thursdayDateEnd = fmt.parse(time);
                                    if (thursdayDateEnd.compareTo(thursdayDateStart) < 0) {
                                        // Do your staff
                                        Log.d("Return", "getTestTime11 less than getCurrentTime ");
                                        //Toast.makeText(AstroProfileSignUp.this, "start time less then end", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("Return", "getTestTime older than getCurrentTime ");
                                    }
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(thursdayDateEnd);
                                thursdayStringEnd = fmtOut.format(thursdayDateEnd);

                                thursdayEndTime.setText(formattedTime);
                                //selectedTimeStrings.add(thursdayEndTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                fridayStartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    fridayStart = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        fridayStart = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        fridayStart = i + " : " + i1 + "PM";
                                    }
                                }
                                fridayStartTime.setText(fridayStart);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                fridayDateStart = null;
                                try {
                                    fridayDateStart = fmt.parse(time);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(fridayDateStart);

                                fridayStringStart = fmtOut.format(fridayDateStart);

                                fridayStartTime.setText(formattedTime);
                                //selectedTimeStrings.add(fridayStartTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                fridayEndImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    fridayEnd = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        fridayEnd = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        fridayEnd = i + " : " + i1 + "PM";
                                    }
                                }
                                fridayEndTime.setText(fridayEnd);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                fridayDateEnd = null;
                                try {
                                    fridayDateEnd = fmt.parse(time);
                                    if (fridayDateEnd.compareTo(fridayDateStart) < 0) {
                                        // Do your staff
                                        Log.d("Return", "getTestTime11 less than getCurrentTime ");
                                        //Toast.makeText(AstroProfileSignUp.this, "start time less then end", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("Return", "getTestTime older than getCurrentTime ");
                                    }
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(fridayDateEnd);

                                fridayStringEnd = fmtOut.format(fridayDateEnd);
                                fridayEndTime.setText(formattedTime);
                                //selectedTimeStrings.add(fridayEndTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                saturdayStartImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    saturdayStart = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        saturdayStart = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        saturdayStart = i + " : " + i1 + "PM";
                                    }
                                }
                                saturdayStartTime.setText(saturdayStart);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                saturdayDateStart = null;
                                try {
                                    saturdayDateStart = fmt.parse(time);
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(saturdayDateStart);

                                saturdayStringStart = fmtOut.format(saturdayDateStart);

                                saturdayStartTime.setText(formattedTime);
                                //selectedTimeStrings.add(saturdayStartTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                saturdayEndImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int mHour, mMinute;
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR);
                        mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(AstroProfileSignUp.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if (mHour >= 0 && i < 12) {
                                    saturdayEnd = i + " : " + i1 + "AM";
                                } else {
                                    if (i == 12) {
                                        saturdayEnd = i + " : " + i1 + "PM";
                                    } else {
                                        i = i - 12;
                                        saturdayEnd = i + " : " + i1 + "PM";
                                    }
                                }
                                saturdayEndTime.setText(saturdayEnd);
                                //timeString = (i + ":" + i1);*//*
                                String time = i + ":" + i1;

                                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                                saturdayDateEnd = null;
                                try {
                                    saturdayDateEnd = fmt.parse(time);
                                    if (saturdayDateEnd.compareTo(saturdayDateStart) < 0) {
                                        // Do your staff
                                        Log.d("Return", "getTestTime11 less than getCurrentTime ");
                                        //Toast.makeText(AstroProfileSignUp.this, "start time less then end", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d("Return", "getTestTime older than getCurrentTime ");
                                    }
                                } catch (ParseException e) {

                                    e.printStackTrace();
                                }

                                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                                String formattedTime = fmtOut.format(saturdayDateEnd);
                                saturdayStringEnd = fmtOut.format(saturdayDateEnd);

                                saturdayEndTime.setText(formattedTime);
                                //selectedTimeStrings.add(saturdayEndTime.getText().toString());
                            }
                        }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                submitTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sundayStringStar == null || sundayStringEnd == null) {
                            //Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedTimeStrings.add("sundaystart"+sundayStringStar);
                            selectedTimeStrings.add("sundayend"+sundayStringEnd);
                        }
                        if (mondayStringStart == null || mondayStringEnd == null) {
                           // Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedTimeStrings.add("mondaystart"+mondayStringStart);
                            selectedTimeStrings.add("mondayend"+mondayStringEnd);
                        }
                        if (tuesdayStringStart == null || tuesdayStringEnd == null) {
                            //Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedTimeStrings.add("tuesdaystart"+tuesdayStringStart);
                            selectedTimeStrings.add("tuesdayend"+tuesdayStringEnd);
                        }
                        if (wednesdayStringStart == null || wednesdayStringEnd == null) {
                            //Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedTimeStrings.add("wednesdaystart"+wednesdayStringStart);
                            selectedTimeStrings.add("wednesdayend"+wednesdayStringEnd);
                        }
                        if (thursdayStringStart == null || thursdayStringEnd == null) {
                           // Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedTimeStrings.add("thursdaystart"+thursdayStringStart);
                            selectedTimeStrings.add("thursdayend"+thursdayStringEnd);
                        }
                        if (fridayStringStart == null || fridayStringEnd == null) {
                           // Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedTimeStrings.add("fridaystart"+fridayStringStart);
                            selectedTimeStrings.add("fridayend"+fridayStringEnd);
                        }

                        if (saturdayStringStart == null || saturdayStringEnd == null) {
                           // Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedTimeStrings.add("saturdaystart"+saturdayStringStart);
                            selectedTimeStrings.add("saturdayend"+saturdayStringEnd);
                        }
                        //Toast.makeText(AstroProfileSignUp.this, ""+selectedTimeStrings, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        /*if (sundayStringStar == null || sundayStringEnd == null) {
                            Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject dataToSend = new JSONObject();

                            JSONObject jsonObjectsunst = new JSONObject();
                            JSONObject jsonObjectsuned = new JSONObject();

                            try {
                                jsonObjectsunst.put("sundaystart"+":", sundayStringStar);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObjectsuned.put("sundayend"+":", sundayStringEnd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            jsonArraytimimg.put(jsonObjectsunst);
                            jsonArraytimimg.put(jsonObjectsuned);



                            selectedTimeStrings.add(sundayStringStar);
                            selectedTimeStrings.add(sundayStringEnd);
                        }
                        if (mondayStringStart == null || mondayStringEnd == null) {
                            Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObjectmonst = new JSONObject();
                            JSONObject jsonObjectmoned = new JSONObject();

                            try {
                                jsonObjectmonst.put("mondaystart"+":",mondayStart);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObjectmoned.put("mondayend"+":",mondayEnd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArraytimimg.put(jsonObjectmonst);
                            jsonArraytimimg.put(jsonObjectmoned);

                            selectedTimeStrings.add(mondayStringStart);
                            selectedTimeStrings.add(mondayStringEnd);
                        }
                        if (tuesdayStringStart == null || tuesdayStringEnd == null) {
                            Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObjecttuesst = new JSONObject();
                            JSONObject jsonObjecttuesed = new JSONObject();

                            try {
                                jsonObjecttuesst.put("tuesdaystart"+":",tuesdayStart);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObjecttuesed.put("tuesdayend"+":", tuesdayEnd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArraytimimg.put(jsonObjecttuesst);
                            jsonArraytimimg.put(jsonObjecttuesed);
                            selectedTimeStrings.add(tuesdayStringStart);
                            selectedTimeStrings.add(tuesdayStringEnd);
                        }
                        if (wednesdayStringStart == null || wednesdayStringEnd == null) {
                            Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObjectwedst = new JSONObject();
                            JSONObject jsonObjectweded = new JSONObject();

                            try {
                                jsonObjectwedst.put(" wednesdayStart"+":",  wednesdayStart);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObjectweded.put("wednesdayEnd"+":", wednesdayEnd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArraytimimg.put(jsonObjectwedst);
                            jsonArraytimimg.put(jsonObjectweded);
                            selectedTimeStrings.add(wednesdayStringStart);
                            selectedTimeStrings.add(wednesdayStringEnd);
                        }
                        if (thursdayStringStart == null || thursdayStringEnd == null) {
                            Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObjectthured = new JSONObject();
                            JSONObject jsonObjectthurst = new JSONObject();

                            try {
                                jsonObjectthurst.put("thursdayStart"+":",  thursdayStart);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObjectthured.put("thursdayEnd"+":", thursdayEnd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArraytimimg.put(jsonObjectthurst);
                            jsonArraytimimg.put(jsonObjectthured);
                            selectedTimeStrings.add(thursdayStringStart);
                            selectedTimeStrings.add(thursdayStringEnd);
                        }
                        if (fridayStringStart == null || fridayStringEnd == null) {
                            Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObjectfried = new JSONObject();
                            JSONObject jsonObjectfrist = new JSONObject();

                            try {
                                jsonObjectfrist.put("fridayStart"+":",  fridayStart);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObjectfried.put("fridayEnd"+":", fridayEnd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArraytimimg.put(jsonObjectfrist);
                            jsonArraytimimg.put(jsonObjectfried);
                            selectedTimeStrings.add(thursdayStringStart);
                            selectedTimeStrings.add(thursdayStringEnd);
                            selectedTimeStrings.add(fridayStringStart);
                            selectedTimeStrings.add(fridayStringEnd);
                        }

                        if (saturdayStringStart == null || saturdayStringEnd == null) {
                            Toast.makeText(AstroProfileSignUp.this, "nothing to be added", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObjectsatst = new JSONObject();
                            JSONObject jsonObjectsated = new JSONObject();

                            try {
                                jsonObjectsatst.put("saturdayStart"+":",  saturdayStart);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                jsonObjectsated.put("saturdayEnd"+":", saturdayEnd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArraytimimg.put(jsonObjectsatst);
                            jsonArraytimimg.put(jsonObjectsated);
                            selectedTimeStrings.add(saturdayStringStart);
                            selectedTimeStrings.add(saturdayStringEnd);*/

                        //Toast.makeText(AstroProfileSignUp.this, "" + selectedTimeStrings, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

      /*  RequestQueue requestQueue = Volley.newRequestQueue(AstroProfileSignUp.this);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params,
                this.createRequestSuccessListener(), this.createRequestErrorListener());

        requestQueue.add(jsObjRequest);*/

        registerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                }
                else if (experience.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Your Experience", Toast.LENGTH_SHORT).show();
                }
                else if (aadharNo.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Your Aadhar No.", Toast.LENGTH_SHORT).show();
                }
                else if (charges.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Charges", Toast.LENGTH_SHORT).show();
                }
                else if (panNo.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Pan No.", Toast.LENGTH_SHORT).show();
                }
                else if (profile.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Fill Your Profile", Toast.LENGTH_SHORT).show();
                }
                else if (selectedexpertiseStrings.size() == 0)
                {
                    Toast.makeText(context, "Please select expertise", Toast.LENGTH_SHORT).show();
                }
                else if (selectedspectyStrings.size() == 0)
                {
                    Toast.makeText(context, "Please select speciality", Toast.LENGTH_SHORT).show();
                }
                else if (selectedlangStrings.size() == 0)
                {
                    Toast.makeText(context, "Please select language", Toast.LENGTH_SHORT).show();
                }
                else if (selectedTimeStrings.size() == 0)
                {
                    Toast.makeText(context, "Please Select Time", Toast.LENGTH_SHORT).show();
                }
                else if (palmYES == "Yes" && palmEdit.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Palm Values", Toast.LENGTH_SHORT).show();
                }
                else if (faceYES == "Yes" && faceEdit.getText().toString() == null)
                {
                    Toast.makeText(context, "Please Enter Face Values", Toast.LENGTH_SHORT).show();
                }
                else if (photo == null)
                {
                    Toast.makeText(context, "Please upload photo", Toast.LENGTH_SHORT).show();
                }
                else if (aadhfile == null)
                {
                    Toast.makeText(context, "Please upload Aadhar Card", Toast.LENGTH_SHORT).show();
                }
                else  if (penfile == null)
                {
                    Toast.makeText(context, "Please upload Pen Card", Toast.LENGTH_SHORT).show();
                }
                else if (mArrayFile == null)
                {
                    Toast.makeText(context, "Please upload certificate", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendAllData();
                    //Toast.makeText(context, "Proile Update Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        p5_spineer_expertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gettypes();
                //Intent intent = new Intent(AstroProfileSignUp.this,MainActivity.class);
                //startActivity(intent);
                //finish();
            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cityName = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(NextQuery.this, ""+cityName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getCountryResponse();

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cityName = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(NextQuery.this, ""+cityName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getStateResponse();

        speciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getspectytypes();

            }
        });

        languages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLanguages();
            }
        });

        aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAdharImage();
            }
        });

        pancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectPenImage();
            }
        });

        certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectcertiImage();
            }
        });

        photoYour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }


    public void getCountryResponse() {

        Call<List<StateResponse>> call = ApiClient.getCliet().stateResponse(api_key, 1);
        call.enqueue(new Callback<List<StateResponse>>() {
            @Override
            public void onResponse(Call<List<StateResponse>> call, Response<List<StateResponse>> response) {

                if (response.isSuccessful()) {

                    showCountry(response.body());
                } else {
                    Toast.makeText(AstroProfileSignUp.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StateResponse>> call, Throwable t) {
                //Toast.makeText(AstroProfileSignUp.this, "server Issue", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showCountry(List<StateResponse> body) {
        for (StateResponse stateResponse : body) {
            st = stateResponse.getName();
            cityString = stateResponse.getId();
            //itemcat = Integer.parseInt(cityString);
            stringList.add(st);
            //Toast.makeText(context, ""+cityString, Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(AstroProfileSignUp.this, android.R.layout.simple_spinner_item, stringList);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            state.setAdapter(cityAdapter);
        }
    }

    public void getStateResponse() {

        Call<List<StateResponse>> call = ApiClient.getCliet().stateResponse(api_key, 2);
        call.enqueue(new Callback<List<StateResponse>>() {
            @Override
            public void onResponse(Call<List<StateResponse>> call, Response<List<StateResponse>> response) {

                if (response.isSuccessful()) {

                    showState(response.body());
                } else {
                    Toast.makeText(AstroProfileSignUp.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StateResponse>> call, Throwable t) {
                //Toast.makeText(AstroProfileSignUp.this, "server Issue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showState(List<StateResponse> body) {
        for (StateResponse stateResponse : body) {
            cit = stateResponse.getName();
            stateString = stateResponse.getId();
            cityList.add(cit);
            //Toast.makeText(context, ""+stringList, Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(AstroProfileSignUp.this, android.R.layout.simple_spinner_item, cityList);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            city.setAdapter(cityAdapter);
        }
    }

    public void getLanguages() {
        Call<List<LanguageResponse>> call = ApiClient.getCliet().languageResponse(api_key, "_languages", "Languages");
        call.enqueue(new Callback<List<LanguageResponse>>() {
            @Override
            public void onResponse(Call<List<LanguageResponse>> call, Response<List<LanguageResponse>> response) {
                if (response.isSuccessful()) {
                    showLanguage(response.body());
                } else {
                    Toast.makeText(AstroProfileSignUp.this, "no data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LanguageResponse>> call, Throwable throwable) {

            }
        });

    }

    private void showLanguage(List<LanguageResponse> body) {
        selectedlangStrings.clear();
        final List<String> languageList = new ArrayList<>();
        final List<String> languageIdList = new ArrayList<>();
        Log.d("list", languageList.toString());
        for (LanguageResponse languageResponse : body) {
            language = languageResponse.getName();
            langId = languageResponse.getId();
            listLanguage = language.split(",");
            languList = langId.split(",");
            for (int i = 0; i < listLanguage.length; i++) {
                languageList.add(listLanguage[i]);
            }
            for (int i = 0; i < listLanguage.length; i++) {
                languageIdList.add(listLanguage[i]);
            }
        }

        final ArrayList<Integer> selectedList = new ArrayList<>();
        selectedList.clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Languages");
// add a checkbox list
        Log.d("list2", languageList.toString());
        builder.setMultiChoiceItems(languageList.toArray(new String[0]), null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked) {
                    selectedList.add(which);
                } else if (selectedList.contains(which)) {
                    selectedList.remove(which);
                }
            }
        });
// add OK and Cancel button
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                for (int j = 0; j < selectedList.size(); j++) {
                    //selectedlangStrings.clear();
                    selectedlangStrings.add(languageList.get(selectedList.get(j)));
                    id.add(languageIdList.get(selectedList.get(j)));
                    //languages.setText(selectedlangStrings.get(j));
                }
                //Toast.makeText(getApplicationContext(), "Items selected are: " + Arrays.toString(selectedlangStrings.toArray()), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("LongLogTag")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            BitmapFactory.Options bo = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bo);
            aadhar.setImageBitmap(bitmap);
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == 11) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    aadhar.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    Uri pic = getImageUri(this, bitmap);
                    picturePath = String.valueOf(pic);
                    encodedimg = Base64.encodeToString(b, Base64.DEFAULT);

                    Log.d("123456", "1245: " + encodedimg);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    aadhfile = file;
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 12) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = AstroProfileSignUp.this.getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String Path = c.getString(columnIndex);
                c.close();
                bitmap = (BitmapFactory.decodeFile(Path));
                aadhar.setImageBitmap(bitmap);
                picturePath = String.valueOf(selectedImage).concat(".jpg");
                Uri u = Uri.parse(Path);
                aadhfile = new File(u.getPath());
                Log.w("path of image from gallery......******************.........", picturePath + ".jpg");
                Glide.with(this)
                        .load(picturePath)

                        .into(aadhar);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.PNG, 20, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimg = Base64.encodeToString(b, Base64.DEFAULT);
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 13) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    pancard.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    Uri pic = getImageUri(this, bitmap);
                    picturePath = String.valueOf(pic);
                    encodedimg = Base64.encodeToString(b, Base64.DEFAULT);

                    Log.d("123456", "1245: " + encodedimg);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    penfile = file;
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 14) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = AstroProfileSignUp.this.getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String Path = c.getString(columnIndex);
                c.close();
                bitmap = (BitmapFactory.decodeFile(Path));
                pancard.setImageBitmap(bitmap);
                picturePath = String.valueOf(selectedImage).concat(".jpg");
                Uri u = Uri.parse(Path);
                penfile = new File(u.getPath());
                Log.w("path of image from gallery......******************.........", picturePath + ".jpg");
                Glide.with(this)
                        .load(picturePath)
                        .into(pancard);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.PNG, 20, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimg = Base64.encodeToString(b, Base64.DEFAULT);
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 15) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    pancard.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    Uri pic = getImageUri(this, bitmap);
                    picturePath = String.valueOf(pic);
                    encodedimg = Base64.encodeToString(b, Base64.DEFAULT);


                    Log.d("123456", "1245: " + encodedimg);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mArrayFile.add(file);
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 16) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = AstroProfileSignUp.this.getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String Path = c.getString(columnIndex);
                c.close();
                bitmap = (BitmapFactory.decodeFile(Path));
                certificate.setImageBitmap(bitmap);
                picturePath = String.valueOf(selectedImage).concat(".jpg");
                Uri u = Uri.parse(Path);
                File f = new File(u.getPath());
                mArrayFile.add(f);
                Log.w("path of image from gallery......******************.........", picturePath + ".jpg");
                Glide.with(this)
                        .load(picturePath)

                        .into(certificate);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.PNG, 20, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimg = Base64.encodeToString(b, Base64.DEFAULT);
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    photoYour.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    Uri pic = getImageUri(this, bitmap);
                    picturePath = String.valueOf(pic);
                    encodedimg = Base64.encodeToString(b, Base64.DEFAULT);


                    Log.d("123456", "1245: " + encodedimg);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    photo = file;
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 4) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = AstroProfileSignUp.this.getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String Path = c.getString(columnIndex);
                c.close();
                bitmap = (BitmapFactory.decodeFile(Path));
                photoYour.setImageBitmap(bitmap);
                picturePath = String.valueOf(selectedImage).concat(".jpg");
                Uri u = Uri.parse(Path);
                photo = new File(u.getPath());
                Log.w("path of image from gallery......******************.........", picturePath + ".jpg");
                Glide.with(this)
                        .load(picturePath)

                        .into(photoYour);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.PNG, 20, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedimg = Base64.encodeToString(b, Base64.DEFAULT);
            }
        }
    }

    /* public Uri getImageUri(Context inContext, Bitmap inImage) {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
         String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
         return Uri.parse(path);
     }*/
    public void sendAllData() {
        final ProgressDialog progressDialog = new ProgressDialog(AstroProfileSignUp.this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");

        MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[mArrayFile.size()];
        MultipartBody.Part[] multipartexper = new MultipartBody.Part[eid.size()];
        MultipartBody.Part[] multipartlang = new MultipartBody.Part[id.size()];
        MultipartBody.Part[] multipartspecy = new MultipartBody.Part[sid.size()];
        //MultipartBody.Part[] multiming = new MultipartBody.Part[selectedTimeStrings.size()];
        MultipartBody.Part[] doc = new MultipartBody.Part[3];
        MultipartBody.Part[] maindoc = new MultipartBody.Part[1];

        HashMap<String, RequestBody> signUpMap = new HashMap<>();

        signUpMap.put("experience", RequestBody.create(MediaType.parse("multipart/form-data"), experience.getText().toString()));
        signUpMap.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), profileMobile));
        signUpMap.put("ccharge", RequestBody.create(MediaType.parse("multipart/form-data"), charges.getText().toString()));
        signUpMap.put("profile", RequestBody.create(MediaType.parse("multipart/form-data"), profile.getText().toString()));
        signUpMap.put("facereader", RequestBody.create(MediaType.parse("multipart/form-data"), faceYES));
        signUpMap.put("palmreader", RequestBody.create(MediaType.parse("multipart/form-data"), palmYES));
        signUpMap.put("location", RequestBody.create(MediaType.parse("multipart/form-data"), stateString));
        signUpMap.put("fcharge", RequestBody.create(MediaType.parse("multipart/form-data"), faceEdit.getText().toString()));
        signUpMap.put("timmings[mondaystart]",RequestBody.create(MediaType.parse("multipart/form-data"),mondayStringStart));
        signUpMap.put("timmings[mondayend]",RequestBody.create(MediaType.parse("multipart/form-data"),mondayStringEnd));
        signUpMap.put("timmings[tuesdaystart]",RequestBody.create(MediaType.parse("multipart/form-data"),tuesdayStringStart));
        signUpMap.put("timmings[tuesdayend]",RequestBody.create(MediaType.parse("multipart/form-data"),tuesdayStringEnd));
        signUpMap.put("timmings[wednesdaystart]",RequestBody.create(MediaType.parse("multipart/form-data"),wednesdayStringStart));
        signUpMap.put("timmings[wednesdayend]",RequestBody.create(MediaType.parse("multipart/form-data"),wednesdayStringEnd));
        signUpMap.put("timmings[thursdaystart]",RequestBody.create(MediaType.parse("multipart/form-data"),thursdayStringStart));
        signUpMap.put("timmings[thursdayend]",RequestBody.create(MediaType.parse("multipart/form-data"),thursdayStringEnd));
        signUpMap.put("timmings[fridaystart]",RequestBody.create(MediaType.parse("multipart/form-data"),fridayStringStart));
        signUpMap.put("timmings[fridayend]",RequestBody.create(MediaType.parse("multipart/form-data"),fridayStringEnd));
        signUpMap.put("timmings[saturdaystart]",RequestBody.create(MediaType.parse("multipart/form-data"),saturdayStringStart));
        signUpMap.put("timmings[saturdayend]",RequestBody.create(MediaType.parse("multipart/form-data"),saturdayStringEnd));
        signUpMap.put("timmings[sundaystart]",RequestBody.create(MediaType.parse("multipart/form-data"),sundayStringStar));
        signUpMap.put("timmings[sundayend]",RequestBody.create(MediaType.parse("multipart/form-data"),sundayStringEnd));
        //signUpMap.put("timmings",RequestBody.create(MediaType.parse("multipart/form-data"),"2"));
        //signUpMap.put("timmings",RequestBody.create(MediaType.parse("multipart/form-data"),"2"));
        signUpMap.put("pan", RequestBody.create(MediaType.parse("multipart/form-data"), panNo.getText().toString()));
        signUpMap.put("aadhar", RequestBody.create(MediaType.parse("multipart/form-data"), aadharNo.getText().toString()));
        //signUpMap.put("photo", RequestBody.create(MediaType.parse("image/*"), photo.getPath()));
        //signUpMap.put("certificates[0]", RequestBody.create(MediaType.parse("multipart/form-data"), photo.getName()));

        //  signUpMap.put("documents", RequestBody.create(MediaType.parse("multipart/form-data"), photo.getName()));
        // documents.put("documents[0]", RequestBody.create(MediaType.parse("multipart/form-data"),photo.getName()));
        //documents.put("documents", RequestBody.create(MediaType.parse("multipart/form-data"), photo.getName()));
        //signUpMap.put("after_pic", RequestBody.create(MediaType.parse("multipart/form-data"), f2.getName()));
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<String> expertise = new ArrayList<>();
        ArrayList<String> timing = new ArrayList<>();
        RequestBody adhsr = RequestBody.create(MediaType.parse("image/*"), aadhfile);
        RequestBody pen = RequestBody.create(MediaType.parse("image/*"), penfile);
        RequestBody mainpvi = RequestBody.create(MediaType.parse("image/*"), photo);
        for (int i = 0; i < mArrayFile.size(); i++) {
            File f = new File(mArrayFile.get(i).getPath());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), f);

            multipartTypedOutput[i] = MultipartBody.Part.createFormData("certificates[]", f.getPath(), surveyBody);
        }

        for (int i = 0; i < selectedexpertiseStrings.size(); i++) {
            //File f = new File(mArrayFile.get(i).getPath());
            //  RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),f);

            multipartexper[i] = MultipartBody.Part.createFormData("expertise[]", String.valueOf(eid.get(i)));

        }
        for (int i = 0; i < selectedlangStrings.size(); i++) {
            //File f = new File(mArrayFile.get(i).getPath());
            //  RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),f);

            multipartlang[i] = MultipartBody.Part.createFormData("languages[]", String.valueOf(id.get(i)));

        }
        for (int i = 0; i < selectedspectyStrings.size(); i++) {
            //File f = new File(mArrayFile.get(i).getPath());
            //  RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),f);

            multipartspecy[i] = MultipartBody.Part.createFormData("speciality[]", String.valueOf(sid.get(i)));

        }
        /*for (int i = 0; i < selectedTimeStrings.size(); i++) {
            //File f = new File(mArrayFile.get(i).getPath());
            //  RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),f);
            multiming[i] = MultipartBody.Part.createFormData("timmings[]", selectedTimeStrings.get(i));
        }*/

        doc[0] = MultipartBody.Part.createFormData("documents[0]", photo.getPath(), mainpvi);
        doc[1] = MultipartBody.Part.createFormData("documents[1]", penfile.getPath(), pen);
        doc[2] = MultipartBody.Part.createFormData("documents[2]", aadhfile.getPath(), adhsr);
        maindoc[0] = MultipartBody.Part.createFormData("photo", photo.getPath(), mainpvi);


        /*for (int i = 0; i < jsonArraytimimg.length(); i++) {
            //File f = new File(mArrayFile.get(i).getPath());
            //  RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),f);
            try {
                multiming[i] = MultipartBody.Part.createFormData("timmings[]",jsonArraytimimg.get(i).toString().replaceAll("",""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

        Call<EditProfileResponse> call = ApiClient.getCliet().uploadSurvey(api_key, multipartexper, multipartlang, multipartspecy, multipartTypedOutput, doc, maindoc, signUpMap);
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(AstroProfileSignUp.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    AstroLogInPreference.getInstance(AstroProfileSignUp.this).setLogged(true);
                    Intent intent = new Intent(AstroProfileSignUp.this, AstroHome.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<EditProfileResponse> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(AstroProfileSignUp.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AstroProfileSignUp.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "image.jpg");
                    Uri uri = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(AstroProfileSignUp.this)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri captureMediaFile = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
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

    private void selectPenImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AstroProfileSignUp.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    Uri uri = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(AstroProfileSignUp.this)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri captureMediaFile = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    Log.e("capturemedia file url", "" + captureMediaFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    startActivityForResult(intent, 13);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 14);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectAdharImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AstroProfileSignUp.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    Uri uri = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(AstroProfileSignUp.this)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri captureMediaFile = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    Log.e("capturemedia file url", "" + captureMediaFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    startActivityForResult(intent, 11);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 12);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void selectcertiImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AstroProfileSignUp.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    Uri uri = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(AstroProfileSignUp.this)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri captureMediaFile = FileProvider.getUriForFile(AstroProfileSignUp.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    Log.e("capturemedia file url", "" + captureMediaFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    startActivityForResult(intent, 15);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 16);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void gettypes() {

        Call<ArrayList<SpeliyDataModel>> call = ApiClient.getCliet().Spictg(api_key, "_types", "Types");
        call.enqueue(new Callback<ArrayList<SpeliyDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SpeliyDataModel>> call, Response<ArrayList<SpeliyDataModel>> response) {
                // JsonObject post = (JsonObject) new JsonObject().get(response.body().toString());
                if (response.isSuccessful()) {
                    SpeliyDatalist = response.body();
                    showspicty(response.body());

                    Log.d("fgfgg", "fgf" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SpeliyDataModel>> call, Throwable throwable) {
                Log.d("fgfgg", "fgf" + throwable.toString());

            }
        });

    }

    public void getspectytypes() {

        Call<ArrayList<SpeliyDataModel>> call = ApiClient.getCliet().spcityly(api_key, "_speciality", "Speciality");
        call.enqueue(new Callback<ArrayList<SpeliyDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SpeliyDataModel>> call, Response<ArrayList<SpeliyDataModel>> response) {
                // JsonObject post = (JsonObject) new JsonObject().get(response.body().toString());
                if (response.isSuccessful()) {
                    SpeliyDatalist = response.body();
                    showspicty1(response.body());

                    Log.d("fgfgg", "fgf" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SpeliyDataModel>> call, Throwable throwable) {
                Log.d("fgfgg", "fgf" + throwable.toString());

            }
        });

    }

    private void showspicty(List<SpeliyDataModel> body) {
        selectedexpertiseStrings.clear();
        final List<String> speList = new ArrayList<>();
        final List<String> speId = new ArrayList<>();
        Log.d("list", speList.toString());
        for (SpeliyDataModel spResponse : body) {
            spcilty = spResponse.getName();
            specyId = spResponse.getId();
            listLanguage = spcilty.split(",");
            speclityList = specyId.split(",");

            for (int i = 0; i < listLanguage.length; i++) {
                speList.add(listLanguage[i]);
            }
            for (int i= 0; i<speclityList.length;i++)
            {
                speId.add(speclityList[i]);
            }
        }

        final ArrayList<Integer> selectedList = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Expertise");
// add a checkbox list
        Log.d("list2", speList.toString());
        builder.setMultiChoiceItems(speList.toArray(new String[0]), null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked) {
                    selectedList.add(which);
                } else if (selectedList.contains(which)) {
                    selectedList.remove(which);
                }
            }
        });
// add OK and Cancel buttons
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK

                for (int j = 0; j < selectedList.size(); j++) {
                    selectedexpertiseStrings.add(speList.get(selectedList.get(j)));
                    eid.add(speId.get(selectedList.get(j)));
                    // Arrexper[j] = speList.get(selectedList.get(j));
                }
                expertise.setText(spcilty);
                //Toast.makeText(getApplicationContext(), "Items selected are: " + Arrays.toString(eid.toArray()), Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showspicty1(List<SpeliyDataModel> body) {
        selectedspectyStrings.clear();
        final List<String> speList = new ArrayList<>();
        final List<String> expList = new ArrayList<>();
        Log.d("list", speList.toString());
        for (SpeliyDataModel spResponse : body) {
            spcilty = spResponse.getName();
            expeId = spResponse.getId();
            listLanguage = spcilty.split(",");
            expeList = expeId.split(",");

            for (int i = 0; i < listLanguage.length; i++) {
                speList.add(listLanguage[i]);
            }

            for (int i = 0; i < listLanguage.length; i++) {
                expList.add(expeList[i]);
            }
        }

        final ArrayList<Integer> selectedList = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Specility");
// add a checkbox list
        Log.d("list2", speList.toString());
        builder.setMultiChoiceItems(speList.toArray(new String[0]), null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked) {
                    selectedList.add(which);
                } else if (selectedList.contains(which)) {
                    selectedList.remove(which);
                }
            }
        });
// add OK and Cancel buttons
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK

                for (int j = 0; j < selectedList.size(); j++) {
                    selectedspectyStrings.add(speList.get(selectedList.get(j)));
                    sid.add(expList.get(selectedList.get(j)));
                }
                speciality.setText(spcilty);

                //Toast.makeText(getApplicationContext(), "Items selected are: " + Arrays.toString(selectedspectyStrings.toArray()), Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void getAstroHistory() {
        Call<ResponseModel> call = ApiClient.getCliet().astroProfile(api_key, profileMobile);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.isSuccessful()) {
                    name.setText(response.body().getName());
                    email.setText(response.body().getEmail());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable throwable) {

            }
        });
    }
}
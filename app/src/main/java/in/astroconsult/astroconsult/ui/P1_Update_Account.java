package in.astroconsult.astroconsult.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.astroconsult.astroconsult.BuildConfig;
import in.astroconsult.astroconsult.Chat.FirebaseAuthUtil;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Interface.Utility;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.LogInResponse;
import in.astroconsult.astroconsult.Response.UserProfileResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class P1_Update_Account extends AppCompatActivity {
    String picturePath;
    Bitmap bitmap;
    EditText name, email, mobile;
    String n, e, m, s;
    Spinner spinner;
    ImageView photo;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    Button submit;
    File photfile;
    ImageView selectImage, updatePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__p1__update__account);
        getSupportActionBar().hide();

        ActivityCompat.requestPermissions(P1_Update_Account.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        name = findViewById(R.id.nameEdit);
        mobile = findViewById(R.id.phoneEdit);
        email = findViewById(R.id.addressEdit);
        spinner = findViewById(R.id.starShineSpinner);
        photo = findViewById(R.id.uploadPhotoEdit);
        submit = findViewById(R.id.updateUser);
        //rotatePhoto = findViewById(R.id.rotateImage);
        selectImage = findViewById(R.id.selectImage);
        updatePhoto = findViewById(R.id.uploadPhotoEdit);

        List<String> categories = new ArrayList<String>();
        categories.add("Aries");
        categories.add("Taurus");
        categories.add("Gemini");
        categories.add("Cancer");
        categories.add("Leo");
        categories.add("Virgo");
        categories.add("Libra");
        categories.add("Scorpius");
        categories.add("Sagittarius");
        categories.add("Capricornus");
        categories.add("Aquarius");
        categories.add("Pisces");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

//        rotatePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                photo.setRotation(photo.getRotation()+90);
//            }
//        });

        n = getIntent().getStringExtra("profile_name");
        e = getIntent().getStringExtra("profile_email");
        m = getIntent().getStringExtra("profile_mobile");
        s = getIntent().getStringExtra("profile_starshine");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString() == null) {
                    Toast.makeText(P1_Update_Account.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString() == null) {
                    Toast.makeText(P1_Update_Account.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (spinner.getSelectedItem().toString() == null) {
                    Toast.makeText(P1_Update_Account.this, "Please Select spinner Item", Toast.LENGTH_SHORT).show();
                } else if (photfile == null) {
                    Toast.makeText(P1_Update_Account.this, "Please Select Photo", Toast.LENGTH_SHORT).show();
                } else {
                    sendData();
                }
            }
        });
        //productSub();
    }

    public void sendData() {

        final ProgressDialog dialog = new ProgressDialog(P1_Update_Account.this);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String, RequestBody> signUpMap = new HashMap<>();

        signUpMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString()));
        signUpMap.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), mobile.getText().toString()));
        signUpMap.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email.getText().toString()));
        signUpMap.put("starshine", RequestBody.create(MediaType.parse("multipart/form-data"), spinner.getSelectedItem().toString()));
        signUpMap.put("photo", RequestBody.create(MediaType.parse("multipart/form-data"), photfile.getPath()));

        RequestBody nameSend = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
        RequestBody emailSend = RequestBody.create(MediaType.parse("multipart/form-data"), email.getText().toString());
        RequestBody mobileSend = RequestBody.create(MediaType.parse("multipart/form_data"), mobile.getText().toString());
        RequestBody spinnerSelect = RequestBody.create(MediaType.parse("multipart/form_data"), spinner.getSelectedItem().toString());
        MultipartBody.Part[] doc = new MultipartBody.Part[3];

        RequestBody mainpvi = RequestBody.create(MediaType.parse("image/*"), photfile);
        doc[0] = MultipartBody.Part.createFormData("documents", photfile.getPath(), mainpvi);

        Call<ResponseBody> call = ApiClient.getCliet().uploadResponse1(api_key, doc, signUpMap);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(P1_Update_Account.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(P1_Update_Account.this, "Server Issues", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(P1_Update_Account.this, "" + throwable, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(P1_Update_Account.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    //intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,90);
                    //  File file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/attachments").getPath(),
                    //         System.currentTimeMillis()+ ".jpg");
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "image.jpg");
                    Uri uri = FileProvider.getUriForFile(P1_Update_Account.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    Log.d("556677", "saveBitmapToFile: " + f);
                    photfile = f;
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(P1_Update_Account.this)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri captureMediaFile = FileProvider.getUriForFile(P1_Update_Account.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    Log.e("capturemedia file url", "" + captureMediaFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
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
                    //photo.setImageBitmap(bitmap);

                    rotateImage(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

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
                    File file = new File(path, System.currentTimeMillis() + ".jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = P1_Update_Account.this.getContentResolver().query(selectedImage, filePath, null, null, null);
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

                        .into(photo);

            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void rotateImage(Bitmap bitmap1)
    {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(photfile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation)
        {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;

            default:
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap1,0,0,bitmap1.getWidth(),bitmap1.getHeight(),matrix,true);
        photo.setImageBitmap(rotatedBitmap);
    }

    public void getProfile() {
        final ProgressDialog dialog = new ProgressDialog(P1_Update_Account.this);
        dialog.setCancelable(false);
        dialog.setMessage("please wait...");
        dialog.show();
        //Log.d("name","hii");
        Call<UserProfileResponse> call = ApiClient.getCliet().userProfile(api_key, m);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                if (response.isSuccessful()) {
                    String ProfileName = response.body().getName();
                    String ProfileEmail = response.body().getEmail();
                    String ProfileMobile = response.body().getMobile();
                    String ProfileStarshine = response.body().getStarshine();
                    name.setText(ProfileName);
                    email.setText(ProfileEmail);
                    mobile.setText(ProfileMobile);
                    if (response.body().getPhoto() == null) {
                        Picasso.get().load(R.drawable.rinku).into(updatePhoto);
                    } else {
                        Picasso.get().load(response.body().getPhoto()).into(updatePhoto);
                        FirebaseAuthUtil.updateProfilePic(response.body().getPhoto());
                    }
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable throwable) {

                Toast.makeText(P1_Update_Account.this, "" + throwable, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }
}

//package in.astroconsult.astroconsult.ui;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.FileProvider;
//import androidx.fragment.app.Fragment;
//
//import android.os.Environment;
//import android.os.StrictMode;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.HashMap;
//
//import in.astroconsult.astroconsult.BuildConfig;
//import in.astroconsult.astroconsult.Interface.ApiClient;
//import in.astroconsult.astroconsult.Interface.Utility;
//import in.astroconsult.astroconsult.LogIn;
//import in.astroconsult.astroconsult.Preferance.LogInPreference;
//import in.astroconsult.astroconsult.R;
//import in.astroconsult.astroconsult.Response.PalmReadingResponse;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static android.app.Activity.RESULT_OK;
//
//
//public class FacePalmFragment extends Fragment {
//
//    Bitmap bitmap,bitmap_1;
//    ImageView Face_photo,palm_photo;
//    File photfile,photfile_1;
//    Button uploadface,uploadpalm;
//    String picturePath,userMobile,astrologerId;
//    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
//    String checkFaceReader,checkPalmReader;
//    String PalmValue;
//    TextView txtFaceInstraction, txtPalmInstraction;
//    LinearLayout faceLayout, palmLayout;
//
//    public FacePalmFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_face_palm, container, false);
//
//        faceLayout = rootView.findViewById(R.id.linear_layout_face);
//        palmLayout = rootView.findViewById(R.id.linear_layout_palm);
//        Face_photo = rootView.findViewById(R.id.Face_image_upload_choose);
//        palm_photo = rootView.findViewById(R.id.Palm_image_upload_choose);
//        uploadface = rootView.findViewById(R.id.upload_face_image_txt_choose);
//        uploadpalm = rootView.findViewById(R.id.upload_palm_image_txt_choose);
//        txtFaceInstraction= rootView.findViewById(R.id.face_instraction);
//        txtPalmInstraction= rootView.findViewById(R.id.Palm_Instruction);
//
//        PalmValue = LogInPreference.getInstance(getActivity()).getPalmValue();
//        checkFaceReader = LogInPreference.getInstance(getActivity()).getFaceYes();
//        checkPalmReader = LogInPreference.getInstance(getActivity()).getPalmYes();
//
//        userMobile = LogInPreference.getMobileNo();
//        astrologerId = LogInPreference.getInstance(getActivity()).getAstroId();
//
//        if (checkFaceReader.equalsIgnoreCase("Yes"))
//        {
//            faceLayout.setVisibility(View.VISIBLE);
//            txtFaceInstraction.setVisibility(View.VISIBLE);
//            txtFaceInstraction.setText("This astrologer know face reading, You are interest for this, Just clicked your face Photo and upload it in below. we charge for this service Rupees of " + PalmValue +"." );
//
//        }
//        else
//        {
//            faceLayout.setVisibility(View.GONE);
//            txtFaceInstraction.setVisibility(View.GONE);
//        }
//
//        if (checkPalmReader.equalsIgnoreCase("Yes"))
//        {
//            palmLayout.setVisibility(View.VISIBLE);
//            txtPalmInstraction.setText("This astrologer know palm reading, You are interest for this, Just clicked your palm Photo and upload it in below. we charge for this service Rupees of "+ PalmValue + ".");
//            txtPalmInstraction.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            palmLayout.setVisibility(View.GONE);
//            txtPalmInstraction.setVisibility(View.GONE);
//        }
//
//
//
//        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//        StrictMode.VmPolicy.Builder builder1=new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder1.build());
//
//        uploadface.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (photfile == null)
//                {
//                    Toast.makeText(getContext(), "Please Select Photo", Toast.LENGTH_SHORT).show();
//                } else
//                {
//                    sendData();
//                }
//            }
//        });
//
//        uploadpalm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (photfile_1 == null)
//                {
//                    Toast.makeText(getContext(), "Please Select Photo", Toast.LENGTH_SHORT).show();
//                } else
//                {
//                    sendDataPalm();
//                }
//            }
//        });
//
//
////        Face_photo.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Toast.makeText(getActivity(), "BC", Toast.LENGTH_SHORT).show();
//////                selectImage();
////            }
////        });
//
//        palm_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImage_1();
//            }
//        });
//
//        return rootView;
//    }
//
//    private void selectImage_1()
//    {
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//                    //  File file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/attachments").getPath(),
//                    //         System.currentTimeMillis()+ ".jpg");
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "image.jpg");
//                    Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                    Log.d("556677", "saveBitmapToFile: "+f);
//                    photfile_1 = f;
//                    File tempFile = null;
//                    try {
//                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(getActivity())));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Uri captureMediaFile = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
//                    Log.e("capturemedia file url", "" + captureMediaFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
//                    startActivityForResult(intent, 3);
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 4);
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private void sendDataPalm()
//    {
//        final ProgressDialog dialog = new ProgressDialog(getContext());
//        dialog.setMessage("Please Wait..");
//        dialog.setCancelable(false);
//        dialog.show();
//
//        HashMap<String, RequestBody> signUpMap = new HashMap<>();
//
//        //signUpMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString()));
//        signUpMap.put("mobile",RequestBody.create(MediaType.parse("multipart/form-data"),userMobile ));
//        signUpMap.put("astrologer_id", RequestBody.create(MediaType.parse("multipart/form-data"), astrologerId));
//        //signUpMap.put("starshine", RequestBody.create(MediaType.parse("multipart/form-data"), spinner.getSelectedItem().toString()));
//        signUpMap.put("photo", RequestBody.create(MediaType.parse("multipart/form-data"), photfile_1.getPath()));
//
//        //RequestBody nameSend = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
//        //RequestBody emailSend = RequestBody.create(MediaType.parse("multipart/form-data"), email.getText().toString());
//        //RequestBody mobileSend = RequestBody.create(MediaType.parse("multipart/form_data"), mobile.getText().toString());
//        //RequestBody spinnerSelect = RequestBody.create(MediaType.parse("multipart/form_data"), spinner.getSelectedItem().toString());
//        MultipartBody.Part[] doc = new MultipartBody.Part[3];
//
//        RequestBody mainpvi = RequestBody.create(MediaType.parse("image/*"), photfile_1);
//        doc[0] = MultipartBody.Part.createFormData("documents", photfile_1.getPath(), mainpvi);
//
//        Call<PalmReadingResponse> call = ApiClient.getCliet().uploadPlam(api_key,doc,signUpMap );
//
//        call.enqueue(new Callback<PalmReadingResponse>() {
//            @Override
//            public void onResponse(Call<PalmReadingResponse> call, Response<PalmReadingResponse> response) {
//
//                if (response.isSuccessful()) {
//                    dialog.dismiss();
//                    Toast.makeText(getContext(), "Uploaded Palm Image Successfully", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getContext(), "Server Issues Try After Sometime !", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<PalmReadingResponse> call, Throwable throwable) {
//                dialog.dismiss();
//                Toast.makeText(getContext(), ""+throwable, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//
//    private void selectImage() {
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//                    //  File file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/attachments").getPath(),
//                    //         System.currentTimeMillis()+ ".jpg");
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "image.jpg");
//                    Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                    Log.d("556677", "saveBitmapToFile: "+f);
//                    photfile = f;
//                    File tempFile = null;
//                    try {
//                        tempFile = File.createTempFile("image", ".jpeg", new File(Utility.getTempMediaDirectory(getActivity())));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Uri captureMediaFile = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", f);
//                    Log.e("capturemedia file url", "" + captureMediaFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
//                    startActivityForResult(intent, 1);
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 2);
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    @SuppressLint("LongLogTag")
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //SharedPreferences sharedpreferences = P1_Update_Account.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        //SharedPreferences.Editor edit = sharedpreferences.edit();
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        // photfile = f.getAbsoluteFile();
//
//                        break;
//                    }
//                }
//                try {
//
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                    bitmap = BitmapFactory.decodeFile(photfile.getAbsolutePath(),
//                            bitmapOptions);
//                    Log.d("334455", "photfile: "+photfile);
//                    Log.d("334455", "onActivityResultabc: "+f.getAbsolutePath());
//                    Log.d("334455", "onActivityResultbitmp: "+bitmap);
//                    Face_photo.setImageBitmap(bitmap);
//
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
//                    byte[] b = baos.toByteArray();
//
//                    // Uri pic =  getImageUri(EditProfileActivity.this,bitmap);
//                    //       picturePath = String.valueOf(pic);
//                    //  encodedimg = Base64.encodeToString(b, Base64.DEFAULT);
//
//                    //  Log.d("123456", "1245: "+encodedimg);
//                    FileOutputStream fos = new FileOutputStream(photfile);
//                    fos.write(b);
//                    fos.flush();
//                    fos.close();
//                    Log.d("334455", "onActivityResultbitmp: "+photfile);
//                    Log.d("334455", "fos: "+fos);
//
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    //  photfile = f;
//                    OutputStream outFile = null;
//                    File file  = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == 2) {
//
//                Uri selectedImage = data.getData();
//                String[] filePath = {MediaStore.Images.Media.DATA};
//                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String Path = c.getString(columnIndex);
//                c.close();
//                bitmap = (BitmapFactory.decodeFile(Path));
//                picturePath = String.valueOf(selectedImage);
//                Uri u = Uri.parse(Path);
//                photfile = new File(u.getPath());
//                Log.w("path of image from gallery......******************.........", picturePath + "");
//                Glide.with(this)
//                        .load(picturePath)
//
//                        .into(Face_photo);
//
//            }
//            if (requestCode == 3) {
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        // photfile = f.getAbsoluteFile();
//
//                        break;
//                    }
//                }
//                try {
//
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                    bitmap_1 = BitmapFactory.decodeFile(photfile_1.getAbsolutePath(),
//                            bitmapOptions);
//                    Log.d("334455", "photfile: "+photfile_1);
//                    Log.d("334455", "onActivityResultabc: "+f.getAbsolutePath());
//                    Log.d("334455", "onActivityResultbitmp: "+bitmap_1);
//                    palm_photo.setImageBitmap(bitmap_1);
//
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap_1.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
//                    byte[] b = baos.toByteArray();
//
//                    // Uri pic =  getImageUri(EditProfileActivity.this,bitmap);
//                    //       picturePath = String.valueOf(pic);
//                    //  encodedimg = Base64.encodeToString(b, Base64.DEFAULT);
//
//                    //  Log.d("123456", "1245: "+encodedimg);
//                    FileOutputStream fos = new FileOutputStream(photfile_1);
//                    fos.write(b);
//                    fos.flush();
//                    fos.close();
//                    Log.d("334455", "onActivityResultbitmp: "+photfile_1);
//                    Log.d("334455", "fos: "+fos);
//
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    //  photfile = f;
//                    OutputStream outFile = null;
//                    File file  = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == 4) {
//
//                Uri selectedImage = data.getData();
//                String[] filePath = {MediaStore.Images.Media.DATA};
//                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String Path = c.getString(columnIndex);
//                c.close();
//                bitmap_1 = (BitmapFactory.decodeFile(Path));
//                picturePath = String.valueOf(selectedImage);
//                Uri u = Uri.parse(Path);
//                photfile_1 = new File(u.getPath());
//                Log.w("path of image from gallery......******************.........", picturePath + "");
//                Glide.with(this)
//                        .load(picturePath)
//
//                        .into(Face_photo);
//
//            }
//        }
//
//    }
//
//    public void sendData() {
//
//        final ProgressDialog dialog = new ProgressDialog(getContext());
//        dialog.setMessage("Please Wait..");
//        dialog.setCancelable(false);
//        dialog.show();
//
//        HashMap<String, RequestBody> signUpMap = new HashMap<>();
//
//        //signUpMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString()));
//        signUpMap.put("mobile",RequestBody.create(MediaType.parse("multipart/form-data"),userMobile ));
//        signUpMap.put("astrologer_id", RequestBody.create(MediaType.parse("multipart/form-data"), astrologerId));
//        //signUpMap.put("starshine", RequestBody.create(MediaType.parse("multipart/form-data"), spinner.getSelectedItem().toString()));
//        signUpMap.put("photo", RequestBody.create(MediaType.parse("multipart/form-data"), photfile.getPath()));
//
//        //RequestBody nameSend = RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString());
//        //RequestBody emailSend = RequestBody.create(MediaType.parse("multipart/form-data"), email.getText().toString());
//        //RequestBody mobileSend = RequestBody.create(MediaType.parse("multipart/form_data"), mobile.getText().toString());
//        //RequestBody spinnerSelect = RequestBody.create(MediaType.parse("multipart/form_data"), spinner.getSelectedItem().toString());
//        MultipartBody.Part[] doc = new MultipartBody.Part[3];
//
//        RequestBody mainpvi = RequestBody.create(MediaType.parse("image/*"), photfile);
//        doc[0] = MultipartBody.Part.createFormData("documents", photfile.getPath(), mainpvi);
//
//        Call<PalmReadingResponse> call = ApiClient.getCliet().uploadPlam(api_key,doc,signUpMap );
//
//        call.enqueue(new Callback<PalmReadingResponse>() {
//            @Override
//            public void onResponse(Call<PalmReadingResponse> call, Response<PalmReadingResponse> response) {
//
//                if (response.isSuccessful()) {
//                    dialog.dismiss();
//                    Toast.makeText(getContext(), "Uploaded Face Image Successfully  ", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getContext(), "Server Issue Try after Sometime !!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<PalmReadingResponse> call, Throwable throwable) {
//                dialog.dismiss();
//                Toast.makeText(getContext(), ""+throwable, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//}


package in.astroconsult.astroconsult.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import in.astroconsult.astroconsult.BuildConfig;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Interface.Utility;
import in.astroconsult.astroconsult.LogIn;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.PalmReadingResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class FacePalmFragment extends Fragment {

    Bitmap bitmap,bitmap_1;
    ImageView Face_photo,palmphoto;
    File photfile,photfile_1;
    Button uploadface,uploadpalm;
    String picturePath,userMobile,astrologerId;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    String checkFaceReader,checkPalmReader;
    String PalmValue;
    TextView txtFaceInstraction, txtPalmInstraction,hii;

    public FacePalmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_face_palm, container, false);

       // Face_photo = (ImageView)rootView.findViewById(R.id.profile_face_image_upload_choose);
       // palmphoto = (ImageView)rootView.findViewById(R.id.profile_palm_image_upload_choose);
        uploadface = (Button) rootView.findViewById(R.id.upload_face_image_txt_choose);
        uploadpalm = (Button) rootView.findViewById(R.id.upload_palm_image_txt_choose);
        txtFaceInstraction= (TextView) rootView.findViewById(R.id.face_instraction);
        txtPalmInstraction= (TextView) rootView.findViewById(R.id.Palm_Instruction);
        LinearLayout faceLayout = (LinearLayout)rootView.findViewById(R.id.linear_layout_face);
        LinearLayout palmLayout = (LinearLayout)rootView.findViewById(R.id.linear_layout_palm);



        PalmValue = LogInPreference.getInstance(getActivity()).getPalmValue();
        checkFaceReader = LogInPreference.getInstance(getActivity()).getFaceYes();
        checkPalmReader = LogInPreference.getInstance(getActivity()).getPalmYes();

        if (checkFaceReader.equalsIgnoreCase("Yes"))
        {
            faceLayout.setVisibility(View.VISIBLE);
            txtFaceInstraction.setVisibility(View.VISIBLE);
            txtFaceInstraction.setText("This astrologer know face reading, You are interest for this, Just clicked your face Photo and upload it in below. we charge for this service Rupees of " + PalmValue +"." );

        }
        else
        {
            faceLayout.setVisibility(View.GONE);
            txtFaceInstraction.setVisibility(View.GONE);
        }

        if (checkPalmReader.equalsIgnoreCase("Yes"))
        {
            palmLayout.setVisibility(View.VISIBLE);
            txtPalmInstraction.setText("This astrologer know palm reading, You are interest for this, Just clicked your palm Photo and upload it in below. we charge for this service Rupees of "+ PalmValue + ".");
            txtPalmInstraction.setVisibility(View.VISIBLE);
        }
        else
        {
            palmLayout.setVisibility(View.GONE);
            txtPalmInstraction.setVisibility(View.GONE);
        }

        userMobile = LogInPreference.getMobileNo();
        astrologerId = LogInPreference.getInstance(getActivity()).getAstroId();

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        StrictMode.VmPolicy.Builder builder1=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        uploadface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (photfile == null)
                {
                    Toast.makeText(getContext(), "Please Select Photo", Toast.LENGTH_SHORT).show();
                }
               /* else if (astrologerAmount.compareTo(walletAmount) < 0)
                {
                    amount();
                }*/
                else
                {
                    sendData();
                }
            }
        });

        uploadpalm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photfile_1 == null)
                {
                    Toast.makeText(getContext(), "Please Select Photo", Toast.LENGTH_SHORT).show();
                }
                /*else if ()
                {
                    amount();
                }*/
                else
                {
                    sendDataPalm();
                }
            }
        });


        Face_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        palmphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage_1();
            }
        });

        return rootView;
    }

    private void selectImage_1()
    {
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
                    Log.d("556677", "saveBitmapToFile: "+f);
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

    private void sendDataPalm()
    {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String, RequestBody> signUpMap = new HashMap<>();

        //signUpMap.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name.getText().toString()));
        signUpMap.put("mobile",RequestBody.create(MediaType.parse("multipart/form-data"),userMobile ));
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

        Call<PalmReadingResponse> call = ApiClient.getCliet().uploadPlam(api_key,doc,signUpMap );

        call.enqueue(new Callback<PalmReadingResponse>() {
            @Override
            public void onResponse(Call<PalmReadingResponse> call, Response<PalmReadingResponse> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Uploaded Palm Image Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Server Issues Try After Sometime !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PalmReadingResponse> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getContext(), ""+throwable, Toast.LENGTH_SHORT).show();

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
                    Log.d("556677", "saveBitmapToFile: "+f);
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
                    Log.d("334455", "photfile: "+photfile);
                    Log.d("334455", "onActivityResultabc: "+f.getAbsolutePath());
                    Log.d("334455", "onActivityResultbitmp: "+bitmap);
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
                    Log.d("334455", "onActivityResultbitmp: "+photfile);
                    Log.d("334455", "fos: "+fos);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    //  photfile = f;
                    OutputStream outFile = null;
                    File file  = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                }
                catch (Exception e) {
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
                    Log.d("334455", "photfile: "+photfile_1);
                    Log.d("334455", "onActivityResultabc: "+f.getAbsolutePath());
                    Log.d("334455", "onActivityResultbitmp: "+bitmap_1);
                    palmphoto.setImageBitmap(bitmap_1);

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
                    Log.d("334455", "onActivityResultbitmp: "+photfile_1);
                    Log.d("334455", "fos: "+fos);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    //  photfile = f;
                    OutputStream outFile = null;
                    File file  = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                }
                catch (Exception e) {
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

                        .into(Face_photo);

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
        signUpMap.put("mobile",RequestBody.create(MediaType.parse("multipart/form-data"),userMobile ));
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

        Call<PalmReadingResponse> call = ApiClient.getCliet().uploadPlam(api_key,doc,signUpMap );

        call.enqueue(new Callback<PalmReadingResponse>() {
            @Override
            public void onResponse(Call<PalmReadingResponse> call, Response<PalmReadingResponse> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Uploaded Face Image Successfully  ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Server Issue Try after Sometime !!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PalmReadingResponse> call, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getContext(), ""+throwable, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
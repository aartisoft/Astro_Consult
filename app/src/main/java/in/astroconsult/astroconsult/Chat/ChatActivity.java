package in.astroconsult.astroconsult.Chat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.view.PinView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import in.astroconsult.astroconsult.AstroLogIn;
import in.astroconsult.astroconsult.AstroOtp;
import in.astroconsult.astroconsult.AstroOtpEditProfile;
import in.astroconsult.astroconsult.AstroOtpVerify;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.MainActivity;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.AstroLogInResponse;
import in.astroconsult.astroconsult.Response.EndChatResponse;
import in.astroconsult.astroconsult.ui.Wallet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private static final String API_KEY = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";
    private static final String TAG = "ChatActivity";
    public static final int BILLING_INTERVAL = 60000; //1 minute
    private static int chargableTotalMinutes = 0;
    final int walletAmount = 60;  //100 rs inside user wallet
    final int chargableAmountPerMin = 60; //amount to be charged per minute
    Timer billingTimer;

    int secondsCount, minutesCount, maxMinutesToChat;

    private Toolbar mChatToolbar;

    //Custom Toolbar layout
    private TextView mTitleView;
    private TextView mLastSeenView;
    private CircleImageView mProfileImage;
    private FloatingActionButton mChatSendBtn;
    private EditText mChatMessageView;

    //Message list
    private RecyclerView mMessagesList;
    private final List<Messages> messagesList = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private String message;

    //Firebase
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;

    private String mChatUser; //this is opposite party in chat
    private String mUserName;
    private String astroMobile ;


    //TextView countDownTv;
    Button endChat;
    private PinView timerPinView;
    private Timer chatTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().hide();

        timerPinView = findViewById(R.id.timer_pin_view);
        //countDownTv = (TextView) findViewById(R.id.textTimmer);
        endChat = (Button) findViewById(R.id.end_chat);
        Intent intent = getIntent();
        if (intent.hasExtra("user_id")) {
            mChatUser = getIntent().getStringExtra("user_id");
        }
        if (intent.hasExtra("user_name")) {
            mUserName = getIntent().getStringExtra("user_name");
        }
        if (intent.hasExtra("astro_mobile")) {
            astroMobile = getIntent().getStringExtra("astro_mobile");
        }
        if (intent.hasExtra("maxMinutesToChat")) {
            maxMinutesToChat = getIntent().getIntExtra("maxMinutesToChat", 0);
        }

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.keepSynced(true);

        listenReceiverPresence();
        chatTimer = new Timer();
        chatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                secondsCount++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // long seconds = (int)((secondsCount / 1000) % 60);
                        timerPinView.setText(String.format("%02d:%02d:%02d", secondsCount / 3600,
                                (secondsCount % 3600) / 60, (secondsCount % 60)));

                        if(LogInPreference.getInstance(ChatActivity.this).getUser()!=null &&
                                LogInPreference.getInstance(ChatActivity.this).getUser().equals("IsUser")) {
                            if((secondsCount / 60) >= maxMinutesToChat){
                                showRefillAmmount();
                                chatTimer.cancel();
                                Log.d("patchsharma", "ayaaaa");
                            }
                        }
                    }
                });
            }
        }, 0, 1000);

        endChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndChatAlert();

            }
        });

        if (mAuth.getCurrentUser() != null) {
            mCurrentUserId = mAuth.getCurrentUser().getUid();
        } else {
//            finish();
//            startActivity(new Intent(this, RegisterActivity.class));
        }

        //setCustomActionBar();

        initViews();
        setCurrentUserOnline(true);
        //populateCustomActionBar();
        loadMessages();
    }

    private void listenReceiverPresence() {
        mRootRef.child("Users").child(mChatUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("online").getValue() instanceof Long){
                    //String online = dataSnapshot.child("online").getValue().toString();
                    final String name = dataSnapshot.child("name").getValue().toString();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            endChatDueToOfflinePresence(name);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    Boolean isRefillDialogShown = false;
    private void endChatDueToOfflinePresence(String receiverName) {
        if (!ChatActivity.this.isFinishing() && !isRefillDialogShown) {
            new AlertDialog.Builder(ChatActivity.this)
                    .setCancelable(false)
                    .setTitle(receiverName + " got offline!")
                    .setMessage("We're terminating chat for now, please try again later.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            isRefillDialogShown = false;
                            if (LogInPreference.getInstance(ChatActivity.this).getUser() !=null &&
                                    LogInPreference.getInstance(ChatActivity.this).getUser().equals("IsUser")) {
                                //I'm user, so sending summary before going back to profileFragment
                                sendChatSummary(true);
                            }
                            else if (AstroLogInPreference.getInstance(ChatActivity.this).getAstro()!=null
                            && AstroLogInPreference.getInstance(ChatActivity.this).getAstro().equals("IsAstrologer"))
                            {
                                //I'm astrologer, so going back to conversationActivity
                                finish();
                            }
                            else
                            {
                                //ChatActivity.super.onBackPressed();
                                finish();
                            }
                        }
                    })
//                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
                    .show();
            isRefillDialogShown = true;
        }

    }

    private void populateCustomActionBar() {
        mRootRef.child("Users").child(mChatUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String online = dataSnapshot.child("online").getValue().toString();
//                String image = dataSnapshot.child("thumb_img").getValue().toString();
//
//                Picasso.with(ChatActivity.this).load(image)
//                        .placeholder(R.drawable.default_avatar).into(mProfileImage);

                // mLastSeenView.setVisibility(View.VISIBLE);
                if (online.equals("true")) {
                    // mLastSeenView.setText("Online");
                } else {
                    GetTimeAgo getTimeAgo = new GetTimeAgo();
                    Long lastTime = Long.parseLong(online);
                    String lastSeentTime = getTimeAgo.getTimeAgo(lastTime, getApplicationContext());
                    //mLastSeenView.setText("" + lastSeentTime);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setCustomActionBar() {
        //mChatToolbar = findViewById(R.id.chat_app_bar);
        //setSupportActionBar(mChatToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("");

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = inflater.inflate(R.layout.chat_custom_bar, null);
        actionBar.setCustomView(actionBarView);

        // ------------ Custom Action bar Items ---------------
        mTitleView = findViewById(R.id.custom_bar_title);
        mProfileImage = findViewById(R.id.custom_bar_image);
        mLastSeenView = findViewById(R.id.tv_custom_bar_last_seen);

        mTitleView.setText("Astro Chat");
        //mTitleView.setText(mUserName);   //set here user name of opposite party
    }

    private void initViews() {
        billingTimer = new Timer();

        mChatSendBtn = findViewById(R.id.chat_send_btn);
        mChatMessageView = findViewById(R.id.chat_message_view);
        mMessagesList = findViewById(R.id.messages_list);
        chatAdapter = new ChatAdapter(messagesList);
        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        mMessagesList.setAdapter(chatAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        billingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                chargableTotalMinutes++;
                if (walletAmount < (chargableTotalMinutes * chargableAmountPerMin)) {
                    //show alert to abort the chat or continue the chat with wallet recharge
                    //billingTimer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*new MaterialAlertDialogBuilder(ChatActivity.this)
                                    .setTitle("Alert")
                                    .setMessage("You don't have minimum amount in wallet to continue the chat. Recharge your wallet now!")
                                    .setNegativeButton("Abort the chat", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).setPositiveButton("Recharge my wallet", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();*/

                        }
                    });
                }
            }
        }, 0, BILLING_INTERVAL);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setCurrentUserOnline(true);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(LogInPreference.getInstance(ChatActivity.this).getUser()!=null &&
                LogInPreference.getInstance(ChatActivity.this).getUser().equals("IsUser")){
            setCurrentUserOnline(ServerValue.TIMESTAMP);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(LogInPreference.getInstance(ChatActivity.this).getUser()!=null &&
                LogInPreference.getInstance(ChatActivity.this).getUser().equals("IsUser")){
            setCurrentUserOnline(ServerValue.TIMESTAMP);
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ChatActivity.this, RegisterActivity.class));
                break;
        }
        return true;
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(LogInPreference.getInstance(ChatActivity.this).getUser()!=null &&
                LogInPreference.getInstance(ChatActivity.this).getUser().equals("IsUser")){
            setCurrentUserOnline(ServerValue.TIMESTAMP);
        }      //  sendChatSummary(false);
    }

    private void sendMessage() {
        message = mChatMessageView.getText().toString().trim();

        if (!TextUtils.isEmpty(message)) {
            DatabaseReference user_message_push = mRootRef.child("messages").child(mCurrentUserId).child(mChatUser).push();
            String push_id = user_message_push.getKey();
            String currentUserRef = "messages/" + mCurrentUserId + "/" + mChatUser;
            String chatUserRef = "messages/" + mChatUser + "/" + mCurrentUserId;

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", "false");
            messageMap.put("type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mCurrentUserId);             //(Sender of the message)

            Map messageUserMap = new HashMap();
            messageUserMap.put(currentUserRef + "/" + push_id, messageMap);
            messageUserMap.put(chatUserRef + "/" + push_id, messageMap);

            mChatMessageView.setText("");  //Resetting the input message field
            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d(TAG, "Firebase Err : " + databaseError.getMessage());
                    } else if (databaseError == null) {
                        Log.d(TAG, "Firebase Success while writting messages");
                    }
                }
            });
        }
    }

    private void loadMessages() {
        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrentUserId).child(mChatUser);
        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Messages message = dataSnapshot.getValue(Messages.class);

                messagesList.add(message);
                chatAdapter.notifyDataSetChanged();

                mMessagesList.scrollToPosition(mMessagesList.getAdapter().getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private void setCurrentUserOnline(Object online)
    {
        mRootRef.child("Users").child(mCurrentUserId).child("online").setValue(online);
    }

    @Override
    public void onBackPressed() {
        showEndChatAlert();
    }

    private void showEndChatAlert() {
        new AlertDialog.Builder(this)
                .setTitle("End Chat!")
                .setMessage("Are You Sure You Want To Terminate Chat?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (LogInPreference.getInstance(ChatActivity.this).getUser() != null &&
                                LogInPreference.getInstance(ChatActivity.this).getUser().equals("IsUser")) {
                            //I'm user, so sending summary before going back to profileFragment
                            sendChatSummary(true);
                        }
                        else if (AstroLogInPreference.getInstance(ChatActivity.this).getAstro()!= null
                        && AstroLogInPreference.getInstance(ChatActivity.this).getAstro() .equals("IsAstrologer"))
                        {
                            //I'm astrologer, so going back to conversationActivity
                            finish();
                        }
                        else
                        {
                            //ChatActivity.super.onBackPressed();
                            finish();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void showRefillAmmount(){
        if(!ChatActivity.this.isFinishing())
        {
            new AlertDialog.Builder(this)
                    .setTitle("Attention !")
                    .setCancelable(false)
                    .setMessage("Your wallet has not sufficient amount to continue the chat, Recharge your wallet")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            finish();
                            sendChatSummary(false);
                        }
                    })
                    .show();
        }
    }

    private void sendChatSummary(final Boolean isProgressShown) {
        final ProgressDialog dialog = new ProgressDialog(this);
        if(isProgressShown){
            dialog.setCancelable(false);
            dialog.show();
            dialog.setMessage("Please Wait...");
        }
        String userMobile = LogInPreference.getInstance(this).getMobileNo();
//        String duration = timerPinView.getText().toString();

        String sentTime = String.format("%02d:%02d", (secondsCount % 3600) / 60, (secondsCount % 60));
        //TODO - mm:ss format
        String timestamp = getCurrentIsoDateTime();
        Call<EndChatResponse> call = ApiClient.getCliet().endChat(API_KEY, astroMobile, userMobile, timestamp, sentTime);
        call.enqueue(new Callback<EndChatResponse>() {
            @Override
            public void onResponse(Call<EndChatResponse> call, Response<EndChatResponse> response) {
                if(isProgressShown)
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    EndChatResponse endChatResponse = response.body();
                    //TODO :- Add code below according to API response handling
                    //Snackbar.make(mChatSendBtn, endChatResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Snackbar.make(mChatSendBtn, "Something went wrong, try again", Snackbar.LENGTH_SHORT).show();
                    //ChatActivity.super.onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<EndChatResponse> call, Throwable throwable) {
                if(isProgressShown){
                    dialog.dismiss();
                    Snackbar.make(mChatSendBtn, "" + throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String getCurrentIsoDateTime() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;
    }
}

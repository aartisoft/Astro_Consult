package in.astroconsult.astroconsult.Chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import in.astroconsult.astroconsult.MainActivity;
import in.astroconsult.astroconsult.R;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    public static final int BILLING_INTERVAL = 60000; //1 minute
    private static int chargableTotalMinutes = 0;
    final int walletAmount = 60;  //100 rs inside user wallet
    final int chargableAmountPerMin = 60; //amount to be charged per minute
    Timer billingTimer;

    int counter;

    private Toolbar mChatToolbar;

    //Custom Toolbar layout
    private TextView mTitleView;
    private TextView mLastSeenView;
    private CircleImageView mProfileImage;
    private AppCompatButton mChatSendBtn;
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

    TextView countDownTv;
    Button endChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().hide();

        countDownTv = (TextView) findViewById(R.id.textTimmer);
        endChat = (Button) findViewById(R.id.end_chat);

        new CountDownTimer(5000000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownTv.setText("Your Chat Begin: "+String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish() {
                //countDownTv.setText("Finished");
            }
        }.start();

        endChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Start Chat");
                builder.setMessage("Are You Sure You Want to Terminate Chat");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
        });

        mChatUser = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.keepSynced(true);

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
                if(online.equals("true"))
                {
                   // mLastSeenView.setText("Online");
                }
                else
                {
                    GetTimeAgo getTimeAgo = new GetTimeAgo();
                    Long lastTime = Long.parseLong(online);
                    String lastSeentTime = getTimeAgo.getTimeAgo(lastTime,getApplicationContext());
                    //mLastSeenView.setText("" + lastSeentTime);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setCustomActionBar() {
        mChatToolbar = findViewById(R.id.chat_app_bar);
        setSupportActionBar(mChatToolbar);

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
                if(walletAmount < (chargableTotalMinutes * chargableAmountPerMin)){
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
        },0,BILLING_INTERVAL);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setCurrentUserOnline(true);


    }

    @Override
    protected void onStop() {
        super.onStop();
        setCurrentUserOnline(ServerValue.TIMESTAMP);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setCurrentUserOnline(ServerValue.TIMESTAMP);
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
        setCurrentUserOnline(ServerValue.TIMESTAMP);
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
                        Log.d(TAG, "Firebase Err : "  +databaseError.getMessage());
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setCurrentUserOnline(Object online) {
        mRootRef.child("Users").child(mCurrentUserId).child("online").setValue(online);
    }
}

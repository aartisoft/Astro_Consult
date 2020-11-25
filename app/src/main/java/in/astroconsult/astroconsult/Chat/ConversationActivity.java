package in.astroconsult.astroconsult.Chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.R;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.Response.WalletBalanceResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConversationActivity extends AppCompatActivity {
    private DatabaseReference mMessageRef;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";

    private RecyclerView mchatUsersList;
    private final List<String> chatUsersList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;

    String message_type, lastMsg = "";

    Toolbar mToolbar;
    //ArrayList<GetAstrologerResponse> user = new ArrayList<>();

    ArrayList<GetAstrologerResponse> astrologers = new ArrayList<>();
    String mobile = LogInPreference.getMobileNo();
    String walletAmount;

    public ConversationActivity() {
        // Required empty p2ublic constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        mToolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(mToolbar);
        productSub();
        initViews();
    }

    private void initViews() {
        mchatUsersList = (RecyclerView) findViewById(R.id.chat_list);
        mchatUsersList.setHasFixedSize(true);
        mchatUsersList.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mMessageRef = FirebaseDatabase.getInstance().getReference().child("messages").child(mCurrent_user_id);
        mMessageRef.keepSynced(true);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDatabase.keepSynced(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users, ChatViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, ChatViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                ChatViewHolder.class,
                mMessageRef) {
            @Override
            protected void populateViewHolder(final ChatViewHolder ChatViewHolder, Users friends, int position) {

                final String mChatUserId = getRef(position).getKey();

                Query q = mMessageRef.child(mChatUserId).limitToLast(1);
                q.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Messages messages = dataSnapshot.getValue(Messages.class);
                        lastMsg = messages.getMessage();
                        message_type = messages.getType();
                        Log.i("lastMsg", lastMsg);
                        Log.i("message_type", message_type);
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

                mUserDatabase.child(mChatUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("name")) {
                            final String userName = dataSnapshot.child("name").getValue().toString();
                            final String online = dataSnapshot.hasChild("online") ? dataSnapshot.child("online").getValue().toString() : "";
                            final String phone = dataSnapshot.hasChild("phone") ? dataSnapshot.child("phone").getValue().toString() : null;

                            ChatViewHolder.setUserName(userName);
                            if (message_type.equals("text")) {
                                ChatViewHolder.userLastPhotoMsg.setVisibility(View.GONE);
                                ChatViewHolder.setLastMsg(lastMsg);
                            }

//                        else if (message_type.equals("image"))
//                        {
//                            ChatViewHolder.userLastPhotoMsg.setVisibility(View.VISIBLE);
//                            ChatViewHolder.setLastMsg("photo");
//                        }
                            //ChatViewHolder.setUserImage(userThumb);

                            ChatViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (online.equals("true")) {
                                        Intent chatIntent = new Intent(ConversationActivity.this, ChatActivity.class);
                                        chatIntent.putExtra("user_id", mChatUserId);
                                        chatIntent.putExtra("user_name", userName);
                                        if (LogInPreference.getInstance(ConversationActivity.this).getUser() != null &&
                                                LogInPreference.getInstance(ConversationActivity.this).getUser().equals("IsUser")) {

                                            if(walletAmount != null){
                                                if (getMaxMinutesToChat(phone) != 0) {
                                                    chatIntent.putExtra("maxMinutesToChat", getMaxMinutesToChat(phone));
                                                    chatIntent.putExtra("astro_mobile", phone);
                                                }else {
                                                    Snackbar.make(mchatUsersList,
                                                            "null walletamount, Please try again Later!!!", Snackbar.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                Snackbar.make(mchatUsersList,
                                                        "There is some problem with walletamount, Please try again Later!!!", Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                        startActivity(chatIntent);
                                    } else {
                                        Snackbar.make(mchatUsersList,
                                                "User is not active, Please try again Later!!!", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        //String userThumb = dataSnapshot.child("thumb_img").getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        };
        mchatUsersList.setAdapter(firebaseRecyclerAdapter);
    }


    public void productSub() {

        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show();

            Call<HomeAstroResponse> call = ApiClient.getCliet().specialityHome(api_key);
            call.enqueue(new Callback<HomeAstroResponse>() {
                @Override
                public void onResponse(Call<HomeAstroResponse> call, Response<HomeAstroResponse> response) {

                    if (response.isSuccessful()) {
                        // user.clear();
                        // user.add(response.body());
                        astrologers.addAll(response.body().getAstrologers());
                        setWalletAmount();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(ConversationActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HomeAstroResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(ConversationActivity.this, "No Data found: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(ConversationActivity.this, "" + e, Toast.LENGTH_SHORT).show();
        }

    }

    private int getMaxMinutesToChat(String phone) {
        for (GetAstrologerResponse astrologerResponse : astrologers) {
            if (astrologerResponse.getMobile().equals(phone)) {
                Float perMinCharge = Float.valueOf(astrologerResponse.getCcharge());
                if (walletAmount != null && perMinCharge != null && perMinCharge != 0) {
                    return ((int) (Float.parseFloat(walletAmount) / perMinCharge));
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }


    public void setWalletAmount() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();
        //Log.d("TAG","hello");
        Call<WalletBalanceResponse> call = ApiClient.getCliet().walletBalance(api_key, mobile);
        call.enqueue(new Callback<WalletBalanceResponse>() {
            @Override
            public void onResponse(Call<WalletBalanceResponse> call, Response<WalletBalanceResponse> response) {
                WalletBalanceResponse wallet = response.body();


                if (wallet.getStatus() == 200) {
                    //Log.d("vishal","Hello");
                    walletAmount = wallet.getBalance();
                    dialog.dismiss();
                } else if (wallet.getStatus() == 201) {
                    //walletBalance.setText(wallet.getBalance());
                    //Toast.makeText(getActivity(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuthUtil.setCurrentUserOnline(ServerValue.TIMESTAMP);

    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAuthUtil.setCurrentUserOnline(true);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView userNameView;
        TextView userLastMsgView;
        ImageView userLastPhotoMsg;
        CircleImageView userImageView;

        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            userNameView = (TextView) view.findViewById(R.id.user_single_name);
            userLastMsgView = (TextView) view.findViewById(R.id.user_single_status);
            userLastPhotoMsg = (ImageView) view.findViewById(R.id.last_photo_msg);
            userImageView = (CircleImageView) view.findViewById(R.id.user_single_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "position = " + getPosition() + " view: " + view, Toast.LENGTH_SHORT).show();
                    //  Log.i("CallingTest", String.valueOf(mChatList.get(getAdapterPosition())));
                }
            });
        }


        public void setUserName(String userName) {
            userNameView.setText(userName);
        }

        public void setLastMsg(String lastMsg) {
            userLastMsgView.setText(lastMsg);
        }

        public void setUserImage(String userImage) {
            Picasso.get().load(userImage).placeholder(R.drawable.default_avatar).into(userImageView);
        }
    }
}

package in.astroconsult.astroconsult;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.astroconsult.astroconsult.Chat.ChatActivity;
import in.astroconsult.astroconsult.Chat.Messages;
import in.astroconsult.astroconsult.Chat.Users;
import in.astroconsult.astroconsult.Interface.ApiClient;
import in.astroconsult.astroconsult.Preferance.AstroLogInPreference;
import in.astroconsult.astroconsult.Preferance.LogInPreference;
import in.astroconsult.astroconsult.Response.GetAstrologerResponse;
import in.astroconsult.astroconsult.Response.HomeAstroResponse;
import in.astroconsult.astroconsult.Response.WalletBalanceResponse;
import in.astroconsult.astroconsult.ui.Help;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AstroConversations extends Fragment {

    public AstroConversations() {
        // Required empty public constructor
    }

    private DatabaseReference mMessageRef;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    String api_key = "w0fp55cIdJ6lLuOqVEd251zKw6lnNd";

    private RecyclerView mchatUsersList;
    private final List<String> chatUsersList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;

    String message_type, lastMsg = "";
    //ArrayList<GetAstrologerResponse> user = new ArrayList<>();

    ArrayList<GetAstrologerResponse> astrologers = new ArrayList<>();
    String mobile = LogInPreference.getMobileNo();
    String walletAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setSupportActionBar(mToolbar);
        initViews();
    }

    private void initViews() {
        mchatUsersList = getView().findViewById(R.id.chat_list);
        mchatUsersList.setHasFixedSize(true);
        mchatUsersList.setLayoutManager(new LinearLayoutManager(getContext()));
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
            protected void populateViewHolder(final ChatViewHolder chatViewHolder, Users friends, int position) {

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

                        Log.d("patchsharma", "ondatasetchange");
                        if (dataSnapshot.hasChild("name")) {
                            final String userName = dataSnapshot.child("name").getValue().toString();
                            final String online = dataSnapshot.hasChild("online") ? dataSnapshot.child("online").getValue().toString() : "";
                            final String phone = dataSnapshot.hasChild("phone") ? dataSnapshot.child("phone").getValue().toString() : null;
                            final String profile = dataSnapshot.hasChild("profile") ? dataSnapshot.child("profile").getValue().toString() : null;

                            chatViewHolder.setUserName(userName);
                            if (message_type.equals("text")) {
                                chatViewHolder.userLastPhotoMsg.setVisibility(View.GONE);
                                chatViewHolder.setLastMsg(lastMsg);
                            }
                            if(online!=null && online instanceof String && online.equals("true")){
                                chatViewHolder.userSingleOnlineIcon.setColorFilter(
                                        chatViewHolder.userSingleOnlineIcon.getContext().getResources().getColor(R.color.green),
                                        PorterDuff.Mode.SRC_ATOP);
                                chatViewHolder.userSingleOnlineIcon.setVisibility(View.VISIBLE);
                            }else {
                                chatViewHolder.userSingleOnlineIcon.setColorFilter(
                                        chatViewHolder.userSingleOnlineIcon.getContext().getResources().getColor(R.color.red),
                                        PorterDuff.Mode.SRC_ATOP);
                                chatViewHolder.userSingleOnlineIcon.setVisibility(View.VISIBLE);
                            }

                            if(profile!=null)
                                Picasso.get().load(profile).into(chatViewHolder.userImageView);

//                        else if (message_type.equals("image"))
//                        {
//                            ChatViewHolder.userLastPhotoMsg.setVisibility(View.VISIBLE);
//                            ChatViewHolder.setLastMsg("photo");
//                        }
                            //ChatViewHolder.setUserImage(userThumb);

                            chatViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (AstroLogInPreference.getInstance(getContext()).getAstro()!=null
                                            && AstroLogInPreference.getInstance(getContext()).getAstro().equals("IsAstrologer")) {
                                        if (online.equals("true")) {
                                            Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                            chatIntent.putExtra("user_id", mChatUserId);
                                            chatIntent.putExtra("user_name", userName);
                                            if (LogInPreference.getInstance(getContext()).getUser() != null &&
                                                    LogInPreference.getInstance(getContext()).getUser().equals("IsUser")) {

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
                                    }else {
                                        Float perMinCharge = getPerMinCharge(phone);
                                        if (walletAmount != null && Float.parseFloat(walletAmount) >= perMinCharge) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setTitle("Start Chat");
                                            builder.setMessage("You will be charged Rs." + perMinCharge + " per minute. Do you want to Continue?");
                                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (online.equals("true")) {
                                                        Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                                        chatIntent.putExtra("user_id", mChatUserId);
                                                        chatIntent.putExtra("user_name", userName);
                                                        if (LogInPreference.getInstance(getContext()).getUser() != null &&
                                                                LogInPreference.getInstance(getContext()).getUser().equals("IsUser")) {

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
                                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setTitle("Wallet Amount Low!!");
                                            builder.setMessage("Your amount is low for this service. You need minimum wallet amount of Rupees " + perMinCharge + ". Refill your wallet now.");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HomeAstroResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "No Data found: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }

    }

    public Float getPerMinCharge(String phone) {
        for (GetAstrologerResponse astrologerResponse : astrologers) {
            if (astrologerResponse.getMobile().equals(phone)) {
                Float perMinCharge = Float.valueOf(astrologerResponse.getCcharge());
                return perMinCharge;
            }
        }
        return 0f;
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
        final ProgressDialog dialog = new ProgressDialog(getContext());
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
    public void onDestroy() {
        super.onDestroy();
        // FirebaseAuthUtil.setCurrentUserOnline(ServerValue.TIMESTAMP);
    }


    @Override
    public void onResume() {
        super.onResume();
        productSub();
        //FirebaseAuthUtil.setCurrentUserOnline(true);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView userNameView;
        TextView userLastMsgView;
        ImageView userLastPhotoMsg, userSingleOnlineIcon;
        CircleImageView userImageView;

        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            userSingleOnlineIcon= (ImageView) view.findViewById(R.id.user_single_online_icon);
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

package in.astroconsult.astroconsult.Chat;

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
import in.astroconsult.astroconsult.R;


public class ConversationActivity extends AppCompatActivity {
    private DatabaseReference  mMessageRef;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;

    private RecyclerView mchatUsersList;
    private final List<String> chatUsersList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;

    String message_type, lastMsg = "";

    Toolbar mToolbar;

    public ConversationActivity()
    {
        // Required empty p2ublic constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        mToolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(mToolbar);

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
        mUserDatabase =  FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDatabase.keepSynced(true);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerAdapter<Users, ChatViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, ChatViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                ChatViewHolder.class,
                mMessageRef)
        {
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
                        Log.i("lastMsg" , lastMsg);
                        Log.i("message_type" , message_type);
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

                        if (dataSnapshot.hasChild("name"))
                        {
                            final String userName =  dataSnapshot.child("name").getValue().toString();
                            ChatViewHolder.setUserName(userName);
                            if(message_type.equals("text"))
                            {
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
                                public void onClick(View view)
                                {
                                    Intent chatIntent = new Intent(ConversationActivity.this,ChatActivity.class);
                                    chatIntent.putExtra("user_id",mChatUserId);
                                    chatIntent.putExtra("user_name",userName);
                                    startActivity(chatIntent);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuthUtil.setCurrentUserOnline(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAuthUtil.setCurrentUserOnline(true);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        TextView userNameView;
        TextView userLastMsgView;
        ImageView userLastPhotoMsg;
        CircleImageView userImageView;

        public ChatViewHolder(View view)
        {
            super(view);
            mView = view;
            userNameView = (TextView) view.findViewById(R.id.user_single_name);
            userLastMsgView = (TextView) view.findViewById(R.id.user_single_status);
            userLastPhotoMsg = (ImageView) view.findViewById(R.id.last_photo_msg);
            userImageView = (CircleImageView) view.findViewById(R.id.user_single_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Toast.makeText(view.getContext(), "position = " + getPosition() + " view: " + view, Toast.LENGTH_SHORT).show();
                  //  Log.i("CallingTest", String.valueOf(mChatList.get(getAdapterPosition())));
                }
            });
        }


        public void setUserName(String userName)
        {
            userNameView.setText(userName);
        }

        public void setLastMsg(String lastMsg)
        {
            userLastMsgView.setText(lastMsg);
        }

        public void setUserImage(String userImage)
        {
            Picasso.get().load(userImage).placeholder(R.drawable.default_avatar).into(userImageView);
        }
    }
}

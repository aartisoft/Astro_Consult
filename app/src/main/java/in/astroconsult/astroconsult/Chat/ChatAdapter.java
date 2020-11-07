package in.astroconsult.astroconsult.Chat;

import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import in.astroconsult.astroconsult.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;


/**
 * Created by shivam sharma on 6/6/2018.
 */


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHOlder>
{
    private List<Messages> mMessageList;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private String currentUser_id;

    public ChatAdapter(List<Messages> mMessageList)
    {
        this.mMessageList = mMessageList;
        mAuth = FirebaseAuth.getInstance();
        currentUser_id = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    public ChatAdapter.MessageViewHOlder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);
        return new MessageViewHOlder(v);
    }

    @Override
    public void onBindViewHolder(final MessageViewHOlder messageHolder, int position)
    {
        Messages c = mMessageList.get(position);
        String from_user = c.getFrom();
        String message_type = c.getType();
        long message_timeStamp = c.getTime();

        if(from_user.equals(currentUser_id))
        {
            messageHolder.messageText.setBackgroundResource(R.drawable.bg_message_right);
            messageHolder.messageText.setTextColor(Color.BLACK);
            //messageHolder.relLayout.setGravity(Gravity.RIGHT);
            messageHolder.updateGravity(Gravity.END);
        }
        else
        {
            messageHolder.messageText.setBackgroundResource(R.drawable.bg_message_left);
            messageHolder.messageText.setTextColor(Color.WHITE);
            //messageHolder.relLayout.setGravity(Gravity.LEFT);
            messageHolder.updateGravity(Gravity.START);

        }

        //geting time from timeStamp value
        final String message_time = DateUtils.formatDateTime(messageHolder.mView.getContext(), message_timeStamp, DateUtils.FORMAT_SHOW_TIME);

        mUserDatabase.child(from_user).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String name = dataSnapshot.child("name").getValue().toString();
                //messageHolder.displayName.setText(name);
                messageHolder.messageTime.setText(message_time);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(message_type.equals("text"))
        {
            messageHolder.messageText.setText(c.getMessage());
        }
//        else if(message_type.equals("image"))
//        {
//            messageHolder.messageImage.setVisibility(View.VISIBLE);
//            Picasso.with(messageHolder.profileImage.getContext()).load(c.getMessage())
//                    .placeholder(R.drawable.default_avatar).into(messageHolder.messageImage);
//            messageHolder.messageText.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount()
    {
        return mMessageList.size();
    }

    public class MessageViewHOlder extends RecyclerView.ViewHolder
    {
        public View mView;
       // public TextView displayName;
        public TextView messageText;
        public TextView messageTime;
        public RelativeLayout relLayout;
//        public CircleImageView profileImage;
//        public ImageView messageImage;

        public MessageViewHOlder(View view)
        {
            super(view);
            this.mView =view;
            messageText =  view.findViewById(R.id.message_text_layout);
           // displayName =  view.findViewById(R.id.name_text_layout);
            messageTime =  view.findViewById(R.id.time_text_layout);
            relLayout =  view.findViewById(R.id.message_single_layout);

            //profileImage =  view.findViewById(R.id.message_profile_layout);
//            messageImage =  view.findViewById(R.id.message_image_layout);
        }

        public void updateGravity(int gravity){
            relLayout.setGravity(gravity);
        }
    }
}
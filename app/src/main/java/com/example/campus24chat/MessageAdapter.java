package com.example.campus24chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<MessageModel>         messageList;
    private FirebaseAuth mAuth;
    CollectionReference mRef;
    private int                  userType;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        private String               mesaage_content;
//        private String               sent_date_time;
//        private String               senders_name;
//        private String               senders_profile_pic;
//        private String               senders_unique_id;
//        private String               seen_by;                     // todo: later
//
//        private String               date_changed;
//        private String               group_joined_by;
//        private String               group_left_by;
//        private String               new_group_admin;
        public TextView             outgoingMsgContent;
        public ImageView            outgoingMsgImage;
        public ImageView            incomingMsgImage;
        public TextView             outgoingmsgTime;
        public ImageView            outgoingmsgSent;
        public ImageView            outgoingmsgRecieved;

        public TextView             incomingMsgContent;
        public TextView             incomingmsgTime;
        public ImageView            incomingmsgSent;
        public ImageView            incomingmsgRecieved;

        public TextView             leftJoinDateAdminRemovedText;
        public ImageView            sendersProfileImage;
        public TextView             sendersName;

        LinearLayout                incomingLinearLayout;
        RelativeLayout              outgoingRelativeLayout;




        public MyViewHolder(View view) {
            super(view);
//            mAuth = FirebaseAuth.getInstance();

            outgoingMsgContent              = (TextView) view.findViewById(R.id.t_Message_out);
            outgoingMsgImage                = (ImageView) view.findViewById(R.id.senderImageView);
            incomingMsgImage                = (ImageView) view.findViewById(R.id.recieverImageView);
            incomingMsgContent              = (TextView) view.findViewById(R.id.t_Message_in);
            outgoingmsgTime                 = (TextView) view.findViewById(R.id.t_time_out);
            incomingmsgTime                 = (TextView) view.findViewById(R.id.t_time_in);
            sendersProfileImage             = (ImageView) view.findViewById(R.id.incoming_profile_image);
            leftJoinDateAdminRemovedText    = (TextView) view.findViewById(R.id.left_join_admin_date_textView);
            sendersName                     = (TextView) view.findViewById(R.id.t_senders_name);
            outgoingMsgContent.setMovementMethod(LinkMovementMethod.getInstance());
            incomingMsgContent.setMovementMethod(LinkMovementMethod.getInstance());
//            outgoingmsgSent                 = view.findViewById(R.id.o_sent);
//            outgoingmsgRecieved             = view.findViewById(R.id.o_recieved);
//            incomingmsgRecieved             = view.findViewById(R.id.i_received);
//            incomingmsgSent                 = view.findViewById(R.id.i_sent);

            incomingLinearLayout            = view.findViewById(R.id.custom_incoming_message);
            outgoingRelativeLayout          = view.findViewById(R.id.custom_outgoing_message);


        }
    }
    public MessageAdapter(List<MessageModel> message_modelList) {
        this.messageList = message_modelList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_message_layout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        mAuth = FirebaseAuth.getInstance();

        final MessageModel message = messageList.get(position);
        //this fn puts the data onto the each view holder.

//        if(!message.getDate_changed().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getDate_changed());
//        else if (message.getGroup_joined_by()!= null && !message.getGroup_joined_by().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getGroup_joined_by()+" joined the group");
//        else if (message.getGroup_left_by()!= null && !message.getGroup_left_by().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getGroup_left_by()+" left the group");
//        else if (message.getAdmin_name_who_removed_the_participant()!= null && message.getAdmin_removed_participant_with_name()!=null && (!message.getAdmin_name_who_removed_the_participant().equals("0") && !message.getAdmin_removed_participant_with_name().equals("0")))
//            holder.leftJoinDateAdminRemovedText.setText(message.getAdmin_name_who_removed_the_participant()+" removed "+message.getAdmin_removed_participant_with_name());
//        else if (message.getNew_group_admin() != null && !message.getNew_group_admin().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getNew_group_admin()+" is now the admin");
//        else if (message.getSenders_unique_id()!= null && message.getSenders_unique_id().equals( "1"/*firebaseUser.getUid()*/)){
//            holder.outgoingMsgContent.setText(message.getMessage_content());
//            holder.outgoingmsgTime.setText(message.getSent_date_time());
//        }
//        else {
        if(! mAuth.getUid().equals(message.getSenders_unique_id())) {
            //receive

//            holder.sendersProfileImage.setImageURI(Uri.parse(message.getSenders_profile_pic()));
            if(!message.getMessage_image_url().equals("0") )
            {
                if (message.getType().equals("image"))
                {
                    holder.outgoingRelativeLayout.setVisibility(View.GONE);
                    holder.incomingLinearLayout.setVisibility(View.VISIBLE);
                    holder.outgoingMsgImage.setVisibility(View.GONE);
                    holder.incomingMsgContent.setVisibility(View.GONE);
                    Picasso
                            .get()
                            .load(message.getMessage_image_url())
//                    .transform(new CircleTransform())
                            .into(holder.outgoingMsgImage);
                    holder.incomingMsgImage.setVisibility(View.VISIBLE);
                    holder.incomingmsgTime.setText(message.getSent_date_time());
                    //}
                    //holder.sendersProfileImage.setText(user.getSenders_profile_pic());
                }
                else
                {
                    holder.outgoingRelativeLayout.setVisibility(View.GONE);
                    holder.incomingLinearLayout.setVisibility(View.VISIBLE);
                    holder.outgoingMsgImage.setVisibility(View.GONE);
                    holder.incomingMsgContent.setVisibility(View.GONE);
                    Picasso
                            .get()
                            .load(message.getMessage_image_url())
//                    .transform(new CircleTransform())
                            .into(holder.outgoingMsgImage);
                    holder.incomingMsgImage.setBackgroundResource(R.drawable.file);
                    holder.incomingMsgImage.setVisibility(View.VISIBLE);
                    holder.incomingMsgImage.setImageResource(R.drawable.file);
                    holder.incomingmsgTime.setText(message.getSent_date_time());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message.getMessage_image_url()));
                            holder.itemView.getContext().startActivity(intent);

                        }
                    });
                    //}
                    //holder.sendersProfileImage.setText(user.getSenders_profile_pic());
                }

            }
            else // incoming text
            {
                holder.incomingMsgImage.setVisibility(View.GONE);
                holder.outgoingMsgImage.setVisibility(View.GONE);
                holder.incomingLinearLayout.setVisibility(View.VISIBLE);
                holder.outgoingRelativeLayout.setVisibility(View.GONE);
                final SpannableString s = new SpannableString(message.getMessage_content()); // msg should have url to enable clicking
                Linkify.addLinks(s, Linkify.ALL);
                holder.incomingMsgContent.setText(s);
                holder.incomingmsgTime.setText(message.getSent_date_time());
//            holder.sendersName.setText(message.getSenders_name());
//            holder.sendersProfileImage.setImageURI(Uri.parse(message.getSenders_profile_pic()));
//            Picasso
//                    .get()
//                    .load(message.getSenders_profile_pic())
//                    .into(holder.sendersProfileImage);
            }

        }
        else  // send
        {
            if(!message.getMessage_image_url().equals("0") ) // send image
            {
                if (message.getType().equals("image"))
                {
                    holder.outgoingMsgContent.setVisibility(View.GONE);
                    holder.outgoingRelativeLayout.setVisibility(View.VISIBLE);
//                holder.out
                    holder.incomingLinearLayout.setVisibility(View.GONE);
                    holder.incomingMsgImage.setVisibility(View.GONE);
                    Picasso
                            .get()
                            .load(message.getMessage_image_url())
//                    .transform(new CircleTransform())
                            .into(holder.outgoingMsgImage);
                    holder.outgoingMsgImage.setVisibility(View.VISIBLE);
                    holder.outgoingmsgTime.setText(message.getSent_date_time());
                    //}
                    //holder.sendersProfileImage.setText(user.getSenders_profile_pic());
                }
                else
                {
                    holder.outgoingMsgContent.setVisibility(View.GONE);
                    holder.outgoingRelativeLayout.setVisibility(View.VISIBLE);
//                holder.out
                    holder.incomingLinearLayout.setVisibility(View.GONE);
                    holder.incomingMsgImage.setVisibility(View.GONE);
                    holder.outgoingMsgImage.setVisibility(View.VISIBLE);
                    holder.outgoingMsgImage.setImageResource(R.drawable.file);
                    holder.outgoingmsgTime.setText(message.getSent_date_time());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message.getMessage_image_url()));
                            Toast.makeText(holder.itemView.getContext(), message.getMessage_image_url(), Toast.LENGTH_LONG).show();
                            holder.itemView.getContext().startActivity(intent);

                        }
                    });
                    //}
                    //holder.sendersProfileImage.setText(user.getSenders_profile_pic());
                }
            }
            else // send text
            {
                holder.outgoingMsgImage.setVisibility(View.GONE);
                holder.incomingMsgImage.setVisibility(View.GONE);
                holder.outgoingRelativeLayout.setVisibility(View.VISIBLE);
                holder.incomingLinearLayout.setVisibility(View.GONE);
                final SpannableString s = new SpannableString(message.getMessage_content()); // msg should have url to enable clicking
                Linkify.addLinks(s, Linkify.ALL);
                holder.outgoingMsgContent.setText(s);
                holder.outgoingmsgTime.setText(message.getSent_date_time());
//            holder.sendersName.setText(message.getSenders_name());
//            holder.sendersProfileImage.setImageURI(Uri.parse(message.getSenders_profile_pic()));
//            Picasso
//                    .get()
//                    .load(message.getSenders_profile_pic())
//                    .into(holder.sendersProfileImage);
            }

        }
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(v.getContext(), "Opening! Please wait.....", Toast.LENGTH_LONG).show();
//                if(flag == 1)
//                {
//                    flag = 0;
//                    holder.t_description.setVisibility(View.VISIBLE);
//                    holder.description.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    flag = 1;
//                    holder.t_description.setVisibility(View.GONE);
//                    holder.description.setVisibility(View.GONE);
//
//                }
//            }
//        });
//        }
    }
    //
    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
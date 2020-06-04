package com.example.campus24chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Messenger extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CollectionReference firebaseDatabase;
    private CollectionReference mRefChats;
    private CollectionReference mRefAdmin;
    private ArrayList<MessageModel> message_lists;
    private Toolbar mToolbar;
    private ImageView sendButton;
    private EditText textMessage;
    private String senderAdminName;
    private ImageView welcomeMessage;
    private LinearLayout messageLinearLayout;
    private ScrollView mScrollView;
    private ImageView attachfileImg;
    private ProgressDialog loadingBar;
    String chatName;
    String chatKey;
    String chatType;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String checker = "", myUri="";
    StorageTask uploadTask;
    Uri fileUri;

    Uri downloadUrl;


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth== null)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        mToolbar = (Toolbar) findViewById(R.id.group_page_toolbar);
        messageLinearLayout = findViewById(R.id.message_layout);
        mScrollView = (ScrollView) findViewById(R.id.chat_scroll);
        attachfileImg = (ImageView) findViewById(R.id.attach_file_image_view);
        loadingBar = new ProgressDialog(this);
        //setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);

        chatName = getIntent().getExtras().get("CHAT_NAME").toString();
        chatKey = getIntent().getExtras().get("CHAT_KEY").toString();
        chatType = getIntent().getExtras().get("CHAT_TYPE").toString();
        getSupportActionBar().setTitle(chatName);

        firebaseDatabase = FirebaseFirestore.getInstance().collection("groups");
//        mRefAdmin = firebaseDatabase.getReference(chatName).child("admin");
        mRefChats = firebaseDatabase.document(chatKey).collection("messages");
//        mRefAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot x: dataSnapshot.getChildren())
//                {
////                    Toast.makeText(ChannelGroup.this, x.child("user_id").getValue().toString(), Toast.LENGTH_SHORT).show();
//                    if (x.child("user_id").getValue().toString().equals(mAuth.getUid()))
//                    {
//                        messageLinearLayout.setVisibility(View.VISIBLE);
//                        senderAdminName = x.child("name").getValue().toString();
//                    }
//                    else
//                        messageLinearLayout.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        final MessageAdapter messageAdapter;
        recyclerView = findViewById(R.id.chat_recycler_view);
        final RecyclerView.LayoutManager recyce = new GridLayoutManager(recyclerView.getContext(), 1);
        recyclerView.setLayoutManager(recyce);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();
        message_lists = new ArrayList<MessageModel>();
        mRefChats
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        Toast.makeText(Messenger.this, "Message fetched", Toast.LENGTH_SHORT).show();
                        MessageModel messageModel;
                        message_lists.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Toast.makeText(Messenger.this, "Message fetched", Toast.LENGTH_SHORT).show();
                                messageModel = documentSnapshot.toObject(MessageModel.class);
                                message_lists.add(messageModel);
                            }
                            recyclerView.clearOnScrollListeners();
                            recyclerView.clearOnChildAttachStateChangeListeners();

                            //This sets the all data from the firebase onto the adapter
                             MessageAdapter messageAdapter = new MessageAdapter(message_lists);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(recyclerView.getContext(), 1);

                            recyclerView.setLayoutManager(recyce);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(messageAdapter);
                            recyclerView.scrollToPosition(message_lists.size()-1);
                        }
                        else
                        {
                            Toast.makeText(Messenger.this, "Error fetching message", Toast.LENGTH_SHORT).show();
                        }
                        
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Messenger.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
        // Sending the message
        sendButton = findViewById(R.id.send_button);
        textMessage = findViewById(R.id.editTextMessage);

        final MessageModel message_model = new MessageModel();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textMessage.getText().toString().trim();
                if (!message.equals(""))
                {
                    textMessage.setText("");
                    message_model.setMessage_content(message);
                    message_model.setSenders_username(senderAdminName);
                    message_model.setSenders_unique_id(mAuth.getUid());
                    message_model.setSent_date_time(""+System.currentTimeMillis());
                    mRefChats.document(""+System.currentTimeMillis()).set(message_model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(Messenger.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                    message_lists.add(message_lists.size(),message_model);

//                                    new MessageAdapter(message_lists).notifyItemRangeInserted(message_lists.size()-1,message_lists.size());
                                    new MessageAdapter(message_lists).notifyDataSetChanged();

//
//
//                                    mScrollView.fullScroll(View.FOCUS_DOWN);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Messenger.this, "Message Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                }

            }
        });
        attachfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]
                        {
                                "Images",
                                "PDF Files",
                                "Ms Word Files"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(Messenger.this);
                builder.setTitle("Select the File");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    Intent intent = new Intent();
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:checker = "image";
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    startActivityForResult(intent.createChooser(intent,"Select Image"),438);
                                    break;
                            case 1:checker = "pdf";
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    intent.setType("application/pdf/*");
                                    startActivityForResult(intent.createChooser(intent,"Select Pdf file"),438);
                                    break;
                            case 2:checker = "docx";
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    intent.setType("application/msword/*");
                                    startActivityForResult(intent.createChooser(intent,"Select Word file"),438);
                                    break;
                        }
                    }
                }).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 438 && resultCode == RESULT_OK && data!= null && data.getData()!=null)
        {
            loadingBar.setTitle("Sending "+checker);
            loadingBar.setMessage("Please wait, your "+checker+" is updating...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            fileUri = data.getData();
            final MessageModel message_model = new MessageModel();
            Toast.makeText(this, "fileUri", Toast.LENGTH_SHORT).show();

            if(!checker.equals("image"))
            {

                Toast.makeText(this, "Not image", Toast.LENGTH_SHORT).show();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                DocumentReference dbRef ;
                final StorageReference filePath = storageReference.child(chatKey).child(mAuth.getUid()).child(""+System.currentTimeMillis()+"."+checker);
//                filePath.putFile(fileUri);

                /************/
                filePath.putFile(fileUri)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Toast.makeText(Messenger.this, "Hello Pdf", Toast.LENGTH_SHORT).show();

                                textMessage.setText("");
                                message_model.setMessage_content("");

                            message_model.setMessage_image_url(fileUri.toString());
//                            Toast.makeText(Messenger.this,data.getData().toString(), Toast.LENGTH_SHORT).show();
                                message_model.setSenders_username(senderAdminName); // todo: trace in userid to get username
                                message_model.setSenders_unique_id(mAuth.getUid());
                                message_model.setType(checker);
                                String timeInMillis = ""+System.currentTimeMillis();
                                message_model.setSent_date_time(timeInMillis);
                                mRefChats.document(timeInMillis).set(message_model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Messenger.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                                message_lists.add(message_lists.size(),message_model);
                                                mScrollView.fullScroll(View.FOCUS_DOWN);

//                                    new MessageAdapter(message_lists).notifyItemRangeInserted(message_lists.size()-1,message_lists.size());
                                                new MessageAdapter(message_lists).notifyDataSetChanged();

//
//
//                                    mScrollView.fullScroll(View.FOCUS_DOWN);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Messenger.this, "Message Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                mScrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(Messenger.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double p = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        loadingBar.setMessage((int) p + "% Uploading...");
                    }
                });
            }
            else if(checker.equals("image"))
            {
                Toast.makeText(this, "Image", Toast.LENGTH_SHORT).show();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                DocumentReference dbRef ;
                final StorageReference filePath = storageReference.child(chatKey).child(mAuth.getUid()).child(""+System.currentTimeMillis()+".jpg");
                uploadTask = filePath.putFile(fileUri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Uri downloadUrl = task.getResult();
                            myUri = downloadUrl.toString();
                            Toast.makeText(Messenger.this, "Hello Url", Toast.LENGTH_SHORT).show();
                            if (!myUri.equals(""))
                            {
                                textMessage.setText("");
                                message_model.setMessage_content("");
                                message_model.setMessage_image_url(myUri);
                                message_model.setSenders_username(senderAdminName); // todo: trace in userid to get username
                                message_model.setSenders_unique_id(mAuth.getUid());
                                message_model.setType(checker);
                                String timeInMillis = ""+System.currentTimeMillis();
                                message_model.setSent_date_time(timeInMillis);
                                mRefChats.document(timeInMillis).set(message_model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Messenger.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                                message_lists.add(message_lists.size(),message_model);
                                                mScrollView.fullScroll(View.FOCUS_DOWN);

//                                    new MessageAdapter(message_lists).notifyItemRangeInserted(message_lists.size()-1,message_lists.size());
                                                new MessageAdapter(message_lists).notifyDataSetChanged();

//
//
//                                    mScrollView.fullScroll(View.FOCUS_DOWN);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Messenger.this, "Message Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                mScrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        }
                        else{
                            loadingBar.dismiss();
                        }
                    }
                });
            }
            else
            {
                loadingBar.dismiss();
                Toast.makeText(this, "Nothing Selected Error!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
//
//    private void displayMessage(DataSnapshot dataSnapshot) {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.clearOnScrollListeners();
//        recyclerView.clearOnChildAttachStateChangeListeners();
//        message_lists.add(dataSnapshot.getValue(MessageModel.class));//add((Message_Model) ((DataSnapshot)iterator.next()).getValue(Message_Model.class));
//
//        MessageAdapter messageAdapter = new MessageAdapter(message_lists);
//
//        recyclerView.setAdapter(messageAdapter);
//        mScrollView.fullScroll(View.FOCUS_DOWN);
//        messageAdapter.notifyDataSetChanged();
//    }
}
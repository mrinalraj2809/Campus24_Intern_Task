package com.example.campus24chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    CollectionReference dbref;
    CollectionReference pinnedChatRef;
    CollectionReference recentChatRef;
    public RecyclerView mRecyclerViewPinnedChat;
    RecyclerView mRecyclerViewRecentChat;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    ArrayList<ChatList> pinnedChatLists;
    ArrayList<ChatList> recentChatLists;
    @Override
    public void onStart() {
        super.onStart();
        if(currentUser == null)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        dbref = FirebaseFirestore.getInstance().collection("Users");
        pinnedChatRef = dbref.document(mAuth.getUid()).collection("pinned_chats");
        recentChatRef = dbref.document(mAuth.getUid()).collection("recent_chats");
        mRecyclerViewPinnedChat = findViewById(R.id.recycler_pinned_chat);
        mRecyclerViewRecentChat = findViewById(R.id.recycler_recent_chat);
        retrievePinnedChats();
        retrieveRecentChats();
        Button signout = findViewById(R.id.sign_out_button);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });
    }

    private void retrievePinnedChats() {
        mRecyclerViewPinnedChat.clearOnScrollListeners();
        mRecyclerViewPinnedChat.clearOnChildAttachStateChangeListeners();
        pinnedChatLists = new ArrayList<ChatList>();
        pinnedChatRef
            .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ChatList pinnedList;
                        pinnedChatLists.clear();
                        if (task.isSuccessful())
                        {
                            Toast.makeText(HomeActivity.this, "Pinned Chat retrieved Successfully", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document: task.getResult())
                            {
                                pinnedList = document.toObject(ChatList.class);
//                                Toast.makeText(HomeActivity.this, pinnedList.getChats_name(), Toast.LENGTH_LONG).show();
                                pinnedChatLists.add(pinnedList);
                            }
                            mRecyclerViewPinnedChat.clearOnScrollListeners();
                            mRecyclerViewPinnedChat.clearOnChildAttachStateChangeListeners();
                            //This sets the all data from the firebase onto the adapter
                            ChatAdapter myPinnedAdapter= new ChatAdapter(HomeActivity.this,pinnedChatLists);
                            //myGroupAdapter.imageGroupIcon.setImageResource(R.drawable.official);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(mRecyclerViewPinnedChat.getContext(),1);
                            mRecyclerViewPinnedChat.setLayoutManager(recyce);
                            mRecyclerViewPinnedChat.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerViewPinnedChat.setAdapter(myPinnedAdapter);
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this, "Error Reading Pinned Chats data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void retrieveRecentChats() {
        recentChatLists = new ArrayList<ChatList>();
        recentChatRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ChatList recentList;
                        recentChatLists.clear();
                        if (task.isSuccessful())
                        {
                            Toast.makeText(HomeActivity.this, "Recent Chat retrieved Successfully", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document: task.getResult())
                            {
                                recentList = document.toObject(ChatList.class);
//                                Toast.makeText(HomeActivity.this, recentList.getChats_name(), Toast.LENGTH_LONG).show();
                                recentChatLists.add(recentList);
                            }
                            mRecyclerViewRecentChat.clearOnScrollListeners();
                            mRecyclerViewRecentChat.clearOnChildAttachStateChangeListeners();
                            //This sets the all data from the firebase onto the adapter
                            ChatAdapter myRecentAdapter= new ChatAdapter(HomeActivity.this,recentChatLists);
                            //myGroupAdapter.imageGroupIcon.setImageResource(R.drawable.official);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(mRecyclerViewRecentChat.getContext(),1);
                            mRecyclerViewRecentChat.setLayoutManager(recyce);
                            mRecyclerViewRecentChat.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerViewRecentChat.setAdapter(myRecentAdapter);
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this, "Error reading recent chats data", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}

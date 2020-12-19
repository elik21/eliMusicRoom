package com.example.musicroom.Chat.ChatActivities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicroom.Adapters.MessageAdapter;
import com.example.musicroom.Models.Chat;
import com.example.musicroom.Models.User;
import com.example.musicroom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
TextView  userName;
ImageView imageView;
RecyclerView rv;
DatabaseReference myDB;
List<Chat> mChat;

FirebaseUser mUser;
MessageAdapter MA;
String userid;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        userName=findViewById(R.id.user1);
        imageView=findViewById(R.id.imageV);
        Toolbar tb=findViewById(R.id.toolbar3);
        ImageButton sendBtn= findViewById(R.id.btn_send);
        final EditText textS=findViewById(R.id.text_send);


       setSupportActionBar(tb);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        rv=findViewById(R.id.recyc_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        mChat=new ArrayList<>();
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent=getIntent();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=textS.getText().toString();
                if(!msg.equals("")){
                    sendMessage(mUser.getUid(),userid,msg);
                }
                else{
                    Toast.makeText(MessageActivity.this, "please fill the message", Toast.LENGTH_SHORT).show();
                }
                textS.setText("");
            }
        });

        userid=intent.getStringExtra("UID");
        mUser= FirebaseAuth.getInstance().getCurrentUser();

        myDB=FirebaseDatabase.getInstance().getReference("Users").child(userid);

        myDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User ra=dataSnapshot.getValue(User.class);
                userName.setText(ra.getName());

                if(ra.getImageUrl().equals("default")){
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                else {
                    Glide.with(MessageActivity.this).load(ra.getImageUrl()).into(imageView);
                }
                readMessages(mUser.getUid(),userid, ra.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

public void sendMessage(String sender,String receiver,String message){

    DatabaseReference referance= FirebaseDatabase.getInstance().getReference();
    HashMap<String,Object> hashMap=new HashMap<>();
    hashMap.put("sender",sender);
    hashMap.put("receiver",receiver);
    hashMap.put("message",message);
    referance.child("Chats").push().setValue(hashMap);


    userid=intent.getStringExtra("UID");


    final DatabaseReference chatRef= FirebaseDatabase.getInstance().getReference("ChatList")
                                    .child(mUser.getUid())
                                    .child(userid);
    chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(!dataSnapshot.exists())
            {
                chatRef.child("id").setValue(userid);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}
public void readMessages(final String myId, final String userId, final String image){
    mChat=new ArrayList<>();
    myDB= FirebaseDatabase.getInstance().getReference("Chats");
    myDB.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mChat.clear();
            for(DataSnapshot ds:dataSnapshot.getChildren()){
                Chat chat=ds.getValue(Chat.class);
                if(chat.getReceiver().equals(myId)&&chat.getSender().equals(userId)||
                        chat.getReceiver().equals(userId)&&chat.getSender().equals(myId)){
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MessageActivity.this,"1");
                   // mBuilder.setLargeIcon(Bitmap.createScaledBitmap(bm,50,50,true));
                    mBuilder.setSmallIcon(R.drawable.com_facebook_send_button_icon);
                    mBuilder.setContentTitle("message sent");
                    mBuilder.setLargeIcon(BitmapFactory.decodeResource(MessageActivity.this.getResources(),
                            R.mipmap.ic_launcher));
                  mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MessageActivity.this);

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(2, mBuilder.build());
                   mChat.add(chat);
                }
                MA=new MessageAdapter(MessageActivity.this, mChat,image);
                rv.setAdapter(MA);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
    public void checkStatus(String status){
        myDB=FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
        HashMap<String,Object> map=new HashMap<>();
        map.put("status",status);
        myDB.updateChildren(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStatus("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkStatus("Offline");
    }
}

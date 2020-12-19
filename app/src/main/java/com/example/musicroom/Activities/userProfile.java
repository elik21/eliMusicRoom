package com.example.musicroom.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicroom.Models.roomAdmin;
import com.example.musicroom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;

import de.hdodenhof.circleimageview.CircleImageView;

public class userProfile extends AppCompatActivity {
    private EditText username,phone
  ,adress,facebook,age,imageUrl,mail;
    private TextView accountType,permission1;
private CircleImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AdminsInfo");
        username=findViewById(R.id.username);
        phone=findViewById(R.id.phone2);
        adress=findViewById(R.id.adress2);
        facebook=findViewById(R.id.facebook);
        age=findViewById(R.id.age2);
        imageUrl=findViewById(R.id.imageUrl3);
        accountType=findViewById(R.id.AccountType);
        permission1=findViewById(R.id.permission2);
        iv=findViewById(R.id.circleImageView);
        mail=findViewById(R.id.email2);




        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    roomAdmin user1=ds.getValue(roomAdmin.class);
                    if(user1.getId().equals(user.getUid()))
                    username.setText(user1.getName());
                    phone.setText(user1.getPhone());
                    try {
                        age.setText(user1.countAge(user1.getDay(),user1.getMonth(),user1.getYear()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    facebook.setText(user1.getFacebook());
                    imageUrl.setText(user.getPhotoUrl().toString());
                    accountType.setText(user1.getAcountType());
                    permission1.setText(user1.getPermission());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(!imageUrl.getText().toString().equals(""))
           Glide.with(this).load(user.getPhotoUrl()).into(iv);
           DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("ClientInfo").child(user.getUid()).child("profile");
    }
}
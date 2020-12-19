package com.example.musicroom.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.musicroom.Activities.AddRoom;
import com.example.musicroom.Activities.Settings;
import com.example.musicroom.Activities.userProfile;
import com.example.musicroom.Chat.frags.ChatFrag;
import com.example.musicroom.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends Fragment {
    private FirebaseUser user;
    private Button myDetails,settings,weekview,logout,allRoom,addRoom,chatsfrag;
   private FirebaseAuth mAuth;
   private TextView user_name;
    private GoogleApiClient mGoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("name");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getActivity().setTitle("Hello "+snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.main_menu, container, false);
        mAuth = FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        ImageView im_profile=v.findViewById(R.id.profile_im);

        myDetails = (Button) v.findViewById(R.id.user_details);
        settings = (Button) v.findViewById(R.id.settings);
        weekview = (Button) v.findViewById(R.id.weekview);
        logout = (Button) v.findViewById(R.id.logout1);
        allRoom= (Button) v.findViewById(R.id.main_list);
        addRoom= (Button) v.findViewById(R.id.add_room);
        user_name=v.findViewById(R.id.username1);
        chatsfrag=v.findViewById(R.id.chats_frag);

        myDetails.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
        myDetails();
        }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Settings();
            }
        });
        allRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allrooms();
            }
        });
        weekview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowWeekView();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //final RoomActivity ra=new RoomActivity();

        DatabaseReference refU = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        refU.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Glide.with(getContext()).load(user.getPhotoUrl()).into(im_profile);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminsInfo").child(user.getUid()).child("permission");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                if(!snapshot.getValue().toString().equals("Admin")){
                    addRoom.setVisibility(View.VISIBLE);
                    weekview.setVisibility(View.VISIBLE);
                }}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ClientsInfo").child(user.getUid()).child("profile");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.getValue().toString().equals("Client")&&dataSnapshot.getKey().toString().equals("permission")){
                        addRoom.setVisibility(View.INVISIBLE);
                        weekview.setVisibility(View.INVISIBLE);
                     }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient =  new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        /*allRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ra.allrooms();
            }
        });*/
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoom();
            }
        });
        chatsfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoChat();
            }
        });

                return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleSignInClient.connect();
    }
    //Function to add personal details
    public void myDetails(){
        Intent intent =new Intent(getContext(), userProfile.class);
        startActivity(intent);
    }
    //Function to add settings
    public void Settings(){
        Intent intent =new Intent(getContext(), Settings.class);
        startActivity(intent);
    }

    public void logout(){
        getActivity().setTitle("hello");
       mAuth=FirebaseAuth.getInstance();
       mAuth.signOut();
       mGoogleSignInClient.clearDefaultAccountAndReconnect();
       LoginActivity LA = new LoginActivity();
       FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.login, LA).commit();

    }

    public void allrooms(){
        AllRooms LA= new AllRooms();
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.login, LA).addToBackStack(null).commit();
    }
    public void gotoChat(){
        ChatFrag CF= new ChatFrag();
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.login, CF).addToBackStack(null).commit();
    }
    public void ShowWeekView(){
        AddEvents wv = new AddEvents();
        FragmentTransaction transaction = null;
        transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, wv);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void addRoom(){
        Intent intent =new Intent(getContext(), AddRoom.class);
        startActivity(intent);
    }
}
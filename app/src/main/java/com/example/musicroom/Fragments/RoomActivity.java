package com.example.musicroom.Fragments;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicroom.Chat.frags.ChatFrag;
import com.example.musicroom.Models.clientObj;
import com.example.musicroom.Models.roomAdmin;
import com.example.musicroom.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.HashMap;

public  class RoomActivity extends FragmentActivity implements PermissionDialog.DialogListener {
    private EditText email;
    private  EditText password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private boolean isSafe;
    private PermissionDialog.DialogListener listener;
    private GoogleApiClient mGoogleSignInClient;
FirebaseAuth mau;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);
setTitle("hello");
        email=(EditText) findViewById(R.id.Email1);
        password=(EditText)findViewById(R.id.password1);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(findViewById(R.id.login)!=null)
        {
           if (savedInstanceState != null)
           {
           return;
           }

           LoginActivity cr = new LoginActivity();
           getSupportFragmentManager().beginTransaction().add(R.id.login, cr).commit();
        }

    }
    public void LoginFunc(String e, String p, final FirebaseAuth mAuth1) {

        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Authentication succeded.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            mainScreen();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void AdminRegFunc(final String e, final String p, final FirebaseAuth mAuth1, final String  phone, final String name, final String exper , FirebaseUser user, final FirebaseDatabase database, final String permission) {
        if (!(name.equals("") && phone.equals("") && exper.equals("") && phone.equals("") && permission.equals(""))) {
            mAuth.createUserWithEmailAndPassword(e, p)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Authentication succeded",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth1.getCurrentUser();
                                String userId = user.getUid();


                                DatabaseReference myRef = database.getReference("Users").child(userId);
                                roomAdmin RA = new roomAdmin(name, e, "06764774", "123", "", "Email/password", "Admin");
                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AdminsInfo").child(userId);
                                reference.setValue(RA);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userId);
                                hashMap.put("name", name);
                                hashMap.put("imageUrl", "default");
                                myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mainScreen();
                                        }
                                    }
                                });

                               reference.setValue(RA);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
        }
        else{
            Toast.makeText(getApplicationContext(), "Please fill required fields.",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void regFuncClient(final String e, final String status, String p, final FirebaseAuth mAuth1, String  phone, final String name,
                              final String id, FirebaseUser user , final FirebaseDatabase database, final String permission) {

        mAuth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Authentication succeded",
                                    Toast.LENGTH_SHORT).show();
                            //Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                            FirebaseUser user = mAuth1.getCurrentUser();
                            String userId=user.getUid();
                            DatabaseReference myRef = database.getReference("ClientsInfo").child(userId).child("profile");
                            final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                                    clientObj client=new clientObj(name,e,userId, "Client");
                                    myRef.setValue(client);
                            HashMap<String,String> hashMap=new HashMap<>();

                            hashMap.put("id",userId);
                            hashMap.put("name",name);
                            hashMap.put("imageUrl","default");
                            database.setValue(hashMap);
                            if(permission!=null) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("permission", permission);
                                myRef.updateChildren(map);
                            }
                            try {
                                client.getAge();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                              mainScreen();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    public void AdminRegFuncGoogle(FirebaseAuth mAuth2, String phoneS, String nameS, String experienceS, FirebaseUser user,String permission) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminsInfo").child(user.getUid());
        mAuth2=FirebaseAuth.getInstance();
        user = mAuth2.getCurrentUser();
        final String userId=user.getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        if(!(nameS.equals("") &&phoneS.equals("")&&experienceS.equals("")&&experienceS.equals("")&&permission.equals(""))){
            roomAdmin RA=new roomAdmin(nameS,user.getEmail(),phoneS,userId,"","google",permission);

            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("id",userId);
            hashMap.put("name",nameS);
            hashMap.put("imageUrl","default");
            ref.setValue(RA);
            database.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        database.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    if(ds.getKey().toString().equals("permission")&&
                                            ds.getValue().toString().equals("Admin"))
                                        gotoMenu();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                    }
                    else
                    {
                        Toast.makeText(RoomActivity.this, "nooo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void AdminRegFuncFacebook(FirebaseAuth mAuth2, String phoneS, String nameS, String experienceS, FirebaseUser user,String permission) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminsInfo").child(user.getUid());
        mAuth2=FirebaseAuth.getInstance();
        user = mAuth2.getCurrentUser();
        final String userId=user.getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        if(!(nameS.equals("") &&phoneS.equals("")&&experienceS.equals("")&&experienceS.equals("")&&permission.equals(""))){
            roomAdmin RA=new roomAdmin(nameS,user.getEmail(),phoneS,userId,"","google",permission);
            Toast.makeText(this,userId,Toast.LENGTH_SHORT);
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("id",userId);
            hashMap.put("name",nameS);
            hashMap.put("imageUrl","default");
            ref.setValue(RA);
            database.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        database.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    if(ds.getKey().toString().equals("permission")&&
                                            ds.getValue().toString().equals("Admin"))
                                        gotoMenu();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                    }
                    else
                    {
                        Toast.makeText(RoomActivity.this, "nooo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void ClientRegFuncFacebook(FirebaseAuth mAuth2, String phoneS, String nameS, String experienceS, FirebaseUser user,String permission) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ClientsInfo").child(user.getUid());
        mAuth2=FirebaseAuth.getInstance();
        user = mAuth2.getCurrentUser();
        final String userId=user.getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        if(!(nameS.equals("") &&phoneS.equals("")&&experienceS.equals("")&&experienceS.equals("")&&permission.equals(""))){
            roomAdmin RA=new roomAdmin(nameS,user.getEmail(),phoneS,userId,"","google",permission);
            Toast.makeText(this,userId,Toast.LENGTH_SHORT);
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("id",userId);
            hashMap.put("name",nameS);
            hashMap.put("imageUrl","default");
            ref.setValue(RA);
            database.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        database.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    if(ds.getKey().toString().equals("permission")&&
                                            ds.getValue().toString().equals("Admin"))
                                        gotoMenu();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                    }
                    else
                    {
                        Toast.makeText(RoomActivity.this, "nooo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void openDialog(){
        PermissionDialog dialog=new PermissionDialog();
        dialog.show(getSupportFragmentManager(),"Who are you?");
    }

    public void loadRegAct() {
        adminRegActivity RAA= new adminRegActivity();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, RAA).addToBackStack(null).commit();
    }
    public void mainScreen() {
        MainMenu AA= new MainMenu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, AA).commit();
    }
    public void gotoRegister(){
        adminRegActivity AA= new adminRegActivity();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.login, AA).commit();
    }
    public void gotoRegisterClient(){
        ClientRegAct AA= new ClientRegAct();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, AA).commit();
    }
    public void gotoChats(){
        ChatFrag AA= new ChatFrag();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, AA).commit();
    }
    public void gotoMenu(){
        if(isSafe){
        FirebaseUser user=mAuth.getCurrentUser();
        MainMenu MM= new MainMenu();
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, MM).commit();
        }

    }
    public void onPostResume(){
        super.onPostResume();
        isSafe=true;
    }
    public void onPause(){
        super.onPause();
        isSafe=false;

    }
    public void signout() {
        LoginActivity LA= new LoginActivity();
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login, LA).commit();
    }





    @Override
    public void applyText(String permission) {
        FirebaseUser user = mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient =  new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        if(permission.equals("Admin")){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("AdminsInfo").child(user.getUid());
            myRef.child("permission").setValue(permission);
            gotoRegister();
        }
        if(permission.equals("Client")){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("ClientsInfo").child(user.getUid());
            myRef.child("permission").setValue(permission);
            gotoRegisterClient();
        }
        if(permission.equals("")){
            mAuth.signOut();
            mGoogleSignInClient.clearDefaultAccountAndReconnect();
        }
    }
}






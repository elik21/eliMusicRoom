package com.example.musicroom.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musicroom.Activities.FacebookLogin;
import com.example.musicroom.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static com.example.musicroom.Fragments.PermissionDialog.DialogListener;


public class LoginActivity extends Fragment implements View.OnClickListener,DialogListener {
    private  final static int RC_SIGN_IN =123;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText Name;
    private EditText phone;
    private DialogListener listener;
    private FirebaseUser user;
Button facebook;
   private FirebaseDatabase database ;
    private GoogleApiClient mGoogleSignInClient;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.login_activity, container, false);
        mAuth=FirebaseAuth.getInstance();
         user = mAuth.getCurrentUser();
        final RoomActivity LF=(RoomActivity) getActivity();



         if(user!=null){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
             final RoomActivity LF1=(RoomActivity) getActivity();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(user!=null&&snapshot.exists()){

                     LF1.gotoMenu();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient =  new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        SignInButton signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        Button b=(Button) view.findViewById(R.id.login);
        email = (EditText) view.findViewById(R.id.Email1);
        password =(EditText)view.findViewById(R.id.password1);
        facebook=view.findViewById(R.id.to_facebook);
        Button b2=view.findViewById(R.id.regAdmin);
        Button b3=view.findViewById(R.id.regClient);
        signInButton.setOnClickListener(this);
        //updat details to database
        final String s1= email.getText().toString();
        String s2= password.getText().toString();

        //creatRequest();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // RA.loadRegScreen();
                String mail=email.getText().toString();
                String password1=password.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                LF.LoginFunc(mail,password1,mAuth);
            }
        });

       b2.setOnClickListener((View.OnClickListener) LoginActivity.this);
       b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1= email.getText().toString();
                LF.loadRegAct();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LF.gotoRegisterClient();
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1= email.getText().toString();
                facebooklogin();
            }
        });
        return view;
    }


  /*  @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      /*  if(currentUser!=null) {
            MainMenu AA = new MainMenu();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.login, AA).commit();
        }


    }*/
  private void updateUI(FirebaseUser currentUser) {
      /*  MainMenu AA= new MainMenu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.login, AA).commit();*/
  }

private void creatRequest(){
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
   // mGoogleSignInClient =(GoogleSignInApi) GoogleSignIn.getClient(getContext(),gso);
}
    private void signIn() {

        Intent signInIntent =  Auth.GoogleSignInApi.getSignInIntent((GoogleApiClient) this.mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }

    }




    private void firebaseAuthWithGoogle(String idToken) {
        final RoomActivity LF=(RoomActivity) getActivity();
        final AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);


        Task<AuthResult> authResultTask = mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();


                            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(!snapshot.exists()){
                                            //LF.gotoRegister();
                                            LF.openDialog();


                                        }
                                        else{
                                            LF.gotoMenu();
                                        }
                                    }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            getActivity().finish();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void applyText(String permission) {

    }

    private class gotoOutput extends AsyncTask<Void,Void,Void> {


    @Override
    protected Void doInBackground(Void... voids) {


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {


    }
}


    @Override
    public void onClick(View v) {
        new gotoOutput().execute();
        signIn();

    }
    public void facebooklogin() {
        Intent intent=new Intent(getContext(), FacebookLogin.class);
        startActivity(intent);
    }


}

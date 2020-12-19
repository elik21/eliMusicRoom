package com.example.musicroom.Chat.frags;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicroom.Adapters.UserAdapter;
import com.example.musicroom.R;
import com.example.musicroom.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFrag extends Fragment {

RecyclerView rv;
private List<User> clients;
private FirebaseUser mAuth;
private UserAdapter Ua;
private FirebaseDatabase mData;
public UserFrag(){

}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_users, container, false);
        rv= v.findViewById(R.id.rec_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        clients=new ArrayList<>();
        readUsers();
        return v;
    }
private void readUsers(){
final FirebaseUser auth=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference dr=FirebaseDatabase.getInstance().getReference("Users");
    dr.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            clients.clear();
            for(DataSnapshot ds:dataSnapshot.getChildren()){
               User client1= ds.getValue(User.class);

                assert client1!=null;
if(client1.getId()!=null)
                if(!client1.getId().equals(auth.getUid())){
                    clients.add(client1);
                }



            }
            Ua=new UserAdapter(getContext(),clients);
            rv.setAdapter(Ua);
Ua.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}
}

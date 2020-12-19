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
import com.example.musicroom.Models.ChatList;
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


public class ChatFrag extends Fragment {
  private List<User> raList;
  private UserAdapter Uad;
  FirebaseUser fUser;
  RecyclerView rv;
  DatabaseReference reference;
  private List<ChatList> chatList=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chatfrag, container, false);
        rv=v.findViewById(R.id.r_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        chatList=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("ChatList").child(fUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                assert fUser!=null;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    ChatList cl=ds.getValue(ChatList.class);
                    chatList.add(cl);
                }
                chatList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
    private void chatList() {
        raList=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                raList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    User ra=ds.getValue(User.class);
                    for(ChatList chLi:chatList)
                    {
                        if(ra.getId()!=null)
                        if(ra.getId().equals(chLi.getId())){
                            raList.add(ra);
                        }
                    }
                }
               Uad=new UserAdapter(getContext(), raList);
                rv.setAdapter(Uad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

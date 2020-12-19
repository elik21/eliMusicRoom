package com.example.musicroom.Chat.ChatActivities;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.musicroom.Chat.frags.ChatFrag;
import com.example.musicroom.Chat.frags.ProfileFrag;
import com.example.musicroom.Chat.frags.UserFrag;
import com.example.musicroom.R;
import com.example.musicroom.Models.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    private FirebaseUser mAuth;
    private DatabaseReference myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);

    mAuth=FirebaseAuth.getInstance().getCurrentUser();
    myDB=FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());

        myDB.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            User ra=dataSnapshot.getValue(User.class);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    TabLayout tl =findViewById(R.id.tabLayout);
    ViewPager viewPager=findViewById(R.id.viewPager);
    ViewPagerAdapter VPA=new ViewPagerAdapter(getSupportFragmentManager());
    
        VPA.addfragment(new ChatFrag(),"chats");
        VPA.addfragment(new UserFrag(),"users");
        VPA.addfragment(new ProfileFrag(),"profile");
        viewPager.setAdapter(VPA);
        tl.setupWithViewPager(viewPager);
}
  class ViewPagerAdapter extends FragmentPagerAdapter{
      private ArrayList<Fragment> frags;
      private ArrayList <String> titles;
      public ViewPagerAdapter(@NonNull FragmentManager fm) {
          super(fm);
          this.frags=new ArrayList<>();
          this.titles=new ArrayList<>();
      }

      @NonNull
      @Override
      public Fragment getItem(int position) {
          return frags.get(position);
      }

      @Override
      public int getCount() {
          return frags.size();
      }
      public void addfragment(Fragment frag,String title){
          frags.add(frag);
          titles.add(title);
      }

      @Nullable
      @Override
      public CharSequence getPageTitle(int position) {
          return titles.get(position);
      }

  }

    public void checkStatus(String status){
        myDB=FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
        HashMap<String,Object> map=new HashMap<>();
        map.put("status",status);
        myDB.updateChildren(map);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);
        return true;
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

package com.example.musicroom.Fragments;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicroom.Activities.SelectedItemActivity;
import com.example.musicroom.Adapters.Adapter;
import com.example.musicroom.Chat.ChatActivities.ChatActivity;
import com.example.musicroom.Models.rehersalRoom;
import com.example.musicroom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllRooms extends Fragment implements Adapter.selectedItem, Comparator<rehersalRoom>,android.widget.SearchView.OnQueryTextListener{

    TextView addItem1,addItem;
   private List<rehersalRoom> rr3 ;
    FirebaseUser user;
    FirebaseAuth mau;
    List<rehersalRoom> myrr;
    private RecyclerView rv;
    private Adapter adapter;
    String item;
  android.widget.Toolbar tb;
    private android.widget.SearchView searchView=null;
    private FirebaseAuth mUser;

    DatabaseReference reference;
    private Uri imageUri;
    ImageView uploadIv;
    private  android.widget.SearchView.OnQueryTextListener queryTextListener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Getting the user instance.
       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setHasOptionsMenu(true);
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


       /* if (user != null) {
            //You need to get here the token you saved at logging-in time.
            String token = "userSavedToken";
            //You need to get here the password you saved at logging-in time.
            String password = "userSavedPassword";

            AuthCredential credential = null;

            //This means you didn't have the token because user used like Facebook Sign-in method.
          /*  if (token == null) {
                credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            }

            //We have to reauthenticate user because we don't know how long
            //it was the sign-in. Calling reauthenticate, will update the
            //user login and prevent FirebaseException (CREDENTIAL_TOO_OLD_LOGIN_AGAIN) on user.delete()
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Calling delete to remove the user and wait for a result.
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Ok, user remove
                                    } else {
                                        //Handle the exception
                                        task.getException();
                                    }
                                }
                            });
                        }
                    });
        }*/

    }
// Rooms searching
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);


        loadDada();
        //getActivity().setTitle("Hello Admin");
       // rr3= new ArrayList<>();
       // addItem = view.findViewById(R.id.add_item1);
        rv = view.findViewById(R.id.recyclerView);
        //tb= view.findViewById(R.id.toolbar);
        user=FirebaseAuth.getInstance().getCurrentUser();

        rv.setHasFixedSize(true);

        //((RoomActivity)getActivity()).setActionBar(tb);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        adapter=new Adapter(getContext(),rr3, AllRooms.this);
      //  rv.setAdapter(adapter);
        //loadDada();
        //rr. removeAll(rr);
       // rr=new ArrayList<>();

        final DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("Rooms");

        dbref.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(final DataSnapshot ds:snapshot.getChildren()) {

                    String name = ds.getKey().toString();
                    String img = ds.getValue().toString();

                    final rehersalRoom rr1 = new rehersalRoom(name, "default", img, user.getUid(), user.getEmail());

                    if (name.equals("אדוש סטודיו")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("90");
                        rr1.setPrice1("");
                        rr1.setPrice2("");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("תל אביב");
                        rr1.setCountry("Israel");
                        rr1.setSite("https://www.adushstudio.com/live-room");
                        rr1.setAdress("התנופה 4, תל אביב");
                     }
                    if (name.equals("Keoss")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("20:00");
                        rr1.setStartHour("14:00");
                        if(rr1.getStartHour().equals("")||rr1.getEndHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("תל אביב");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("המקצוע 4.תל אביב");
                    }

                    if (name.equals("Musebreak")){
                        rr1.setFacebookPage("https://www.facebook.com/MuseBreakStudio/");
                        rr1.setPrice("");
                        rr1.setPrice1("");
                        rr1.setPrice2("");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("תל אביב");
                        rr1.setCountry("Israel");
                        rr1.setSite("");
                        rr1.setAdress("Herzl St 142, Tel Aviv-Yafo");
                    }
                    if (name.equals("MyRoom")){
                        rr1.setFacebookPage("https://www.facebook.com/myroom99/");
                        rr1.setPrice("80");
                        rr1.setPrice1("");
                        rr1.setPrice2("");
                        rr1.setEndHour("22:00");
                        rr1.setStartHour("8:00");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("תל אביב");
                        rr1.setCountry("Israel");
                        rr1.setSite("");
                        rr1.setAdress("רבניצקי 7, תל אביב");
                    }
                    if (name.equals("Label1")){
                        rr1.setFacebookPage("https://www.studiolabel1.com/");
                        rr1.setPrice("");
                        rr1.setPrice1("");
                        rr1.setPrice2("");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("תל אביב");
                        rr1.setCountry("Israel");
                        rr1.setSite("");
                        rr1.setAdress("לייבל1");
                    }
                    if (name.equals("מנגו סטודיוס")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("תל אביב");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("הירקון 39");
                    }
                    if (name.equals("הצוללת האדומה")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("קריית אונו");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("סוקולוב 8");
                    }
                    if (name.equals("הצוללת האדומה")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("קריית אונו");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("סוקולוב 8");
                    }
                    if (name.equals("Big Beat")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("קריית אונו");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("סוקולוב 8");
                    }
                    if (name.equals("הצוללת האדומה")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("קריית אונו");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("סוקולוב 8");
                    }
                    if (name.equals("נוטא מיוזיק")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour().equals("")||rr1.getStartHour().equals("")){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("קריית אונו");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("סוקולוב 8");
                    }
                    if (name.equals("פורטה")){
                        rr1.setFacebookPage("");
                        rr1.setPrice("70");
                        rr1.setPrice1("80");
                        rr1.setPrice2("100");
                        rr1.setEndHour("");
                        rr1.setStartHour("");
                        if(rr1.getEndHour()==null||rr1.getStartHour()==null){
                            rr1.setOpenningHours("24/7");}
                        else
                        {
                            rr1.setOpenningHours(rr1.getStartHour()+"-"+rr1.getEndHour());
                        }
                        rr1.setCity("קריית אונו");
                        rr1.setCountry("Israel");
                        rr1.setSite("http://www.keoss.info/");
                        rr1.setAdress("סוקולוב 8");
                    }

                    if(rr1.getEmail().equals("eli73746@gmail.com")) {
                        final DatabaseReference TotalRoomInforef = FirebaseDatabase.getInstance().getReference("TotalRoomsInfo").child(rr1.getUid());
                        TotalRoomInforef.child(rr1.getRoomName()).setValue(rr1);
                        final DatabaseReference TotalRoomInforefu = FirebaseDatabase.getInstance().getReference("Users").child(rr1.getUid());
                        TotalRoomInforefu.child("RoomsInfo").child(rr1.getRoomName()).setValue(rr1);
                    }


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        final DatabaseReference RoomInforef= FirebaseDatabase.getInstance().getReference("TotalRoomsInfo");

        RoomInforef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()) {
                    final DatabaseReference RoomInforef2 = FirebaseDatabase.getInstance().getReference("TotalRoomsInfo").child(ds.getKey().toString());
                    RoomInforef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds1 : snapshot.getChildren()) {
                                final rehersalRoom rrobj = ds1.getValue(rehersalRoom.class);
                                assert rrobj != null;
                                if (rrobj.getUid() != null)
                                    // rr3.clear();
                                    rr3.add(rrobj);
                            }
                            Collections.sort(rr3, AllRooms.this);
                            adapter = new Adapter(getContext(),
                                    rr3,
                                    AllRooms.this);

                            adapter.notifyDataSetChanged();
                            rv.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference= FirebaseDatabase.getInstance().getReference("TotalRoomsInfo").child(user.getUid());



        return view;
    }




    @Override
    public void selectedItem1(rehersalRoom RehersalRoom) {

        startActivity(new Intent(getContext(), SelectedItemActivity.class).putExtra("data", RehersalRoom));

    }




    @Override
    public int compare(rehersalRoom o1, rehersalRoom o2) {
        return 0;
    }


      @SuppressLint("ResourceAsColor")
      @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
          super.onCreateOptionsMenu(menu, inflater);
          inflater.inflate(R.menu.menu3, menu);
          MenuItem searchItem = menu.findItem(R.id.action_filter);
          SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

          if (searchItem != null) {
              searchView = (android.widget.SearchView) searchItem.getActionView();
searchView.setBackgroundColor(R.color.colorPrimaryDark);

          }

          if (searchView != null) {
searchView.setQueryHint("search");
              searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

              queryTextListener = new android.widget.SearchView.OnQueryTextListener() {
                  @Override
                  public boolean onQueryTextChange(final String newText) {


                adapter.getFilter().filter(newText);

                      return false;
                  }

                  @Override
                  public boolean onQueryTextSubmit(String query) {


                      return false;
                  }
              };
              searchView.setOnQueryTextListener(queryTextListener);
          }

    }


    public void insertItem(int position) {
        rr3.add(position, new rehersalRoom(addItem1.getText().toString(), "ariel", R.drawable.ic_launcher_background));
        adapter.notifyItemInserted(position);


    }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {

        user=FirebaseAuth.getInstance().getCurrentUser();
        switch (item.getItemId()) {
            case R.id.action_filter:
                DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("Rooms");
                return true;
            case R.id.logout:
                 RoomActivity ra=new RoomActivity();
                // ra.logout();
                 mau=FirebaseAuth.getInstance();
                 mau.signOut();
                LoginActivity LA= new LoginActivity();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.login, LA).addToBackStack(null).commit();
                return true;

            case R.id.chat:
                if(user!=null) {
                    Intent intent1 = new Intent(getContext(), ChatActivity.class);
                    startActivity(intent1);
                }
                return true;
            case  R.id.Week_view:
                AddEvents Wv= new AddEvents();
                 FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                 transaction1.replace(R.id.login, Wv).addToBackStack(null).commit();
                 return true;
            default:
                break;
        }
         searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void saveData(){
        SharedPreferences sp= AllRooms.this.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        Gson gson=new Gson();
        String json=gson.toJson(rr3);
        editor.putString("task list",json);

        editor.apply();
    }
    private void loadDada(){
        SharedPreferences sp= AllRooms.this.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        editor.apply();
        Gson gson=new Gson();
        String json=sp.getString("task list",null);
        Type type=new TypeToken<ArrayList<rehersalRoom>>(){}.getType();
        rr3=gson.fromJson(json,type);
        if(rr3==null){
            rr3=new ArrayList<>();
        }


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
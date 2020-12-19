package com.example.musicroom.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicroom.Activities.WeekView.RoomSchedule;
import com.example.musicroom.Models.rehersalRoom;
import com.example.musicroom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;

public class SelectedItemActivity extends AppCompatActivity implements Serializable {
FirebaseUser user;
TextView contact;
    Intent intent1;
  /*  private TextView tv;
    private TextView tv;
    private TextView tv;
private ImageView iv;
    private ImageView iv;
    private ImageView iv;
    private ImageView iv;
    private ImageView iv;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        //List<TextView> tvs=new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final TextView roomname = findViewById(R.id.selected_room);
        final TextView rooAdress = findViewById(R.id.adress1);
        final ImageButton ib = findViewById(R.id.waze);
        intent1 = getIntent();
        int tags = 6;
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.selection);
        boolean success = formIsValid(layout);

        Glide.with(getApplicationContext()).load(R.mipmap.wazejpg).apply(new RequestOptions().override(100, 100)).into(ib);
        if (intent1.getExtras() != null) {
            final rehersalRoom rr1 = (rehersalRoom) intent1.getSerializableExtra("data");
            TextView tv = findViewById(R.id.Schedule);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentSch = new Intent(getApplicationContext(), RoomSchedule.class);
                    intent1.putExtra("id", rr1.getUid());
                    startActivity(intentSch);
                }
            });}
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Launch Waze to look for Hawaii:
                        final rehersalRoom rr = (rehersalRoom) intent1.getSerializableExtra("data");
                        String url = "https://waze.com/ul?q=" + rooAdress.getText().toString();
                        if(rr.getRoomName().equals("Label1"))
                            url= "https://waze.com/ul?q="+"32.0597538,34.784026";
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent2);
                    } catch (ActivityNotFoundException ex) {
                        // If Waze is not installed, open it in Google Play:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                        startActivity(intent2);
                    }
                }
            });


        }
        public boolean formIsValid (ConstraintLayout layout){
            final Intent intent = getIntent();


            for (int i = 0; i < layout.getChildCount(); i++) {
                View v = layout.getChildAt(i);
                if (v instanceof EditText) {
                    switch (v.getId()) {

                        case R.id.city:
                            if (intent.getExtras() != null) {
                                final TextView city = (EditText) v;
                                final rehersalRoom rr = (rehersalRoom) intent.getSerializableExtra("data");
                                city.setText(rr.getCity());
                                city.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("city",city.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }
                        case R.id.selected_room:
                            //Stop MediaPlayer
                            if (intent.getExtras() != null) {
                                final TextView RoomName = (EditText) v;
                                final rehersalRoom rr1 = (rehersalRoom) intent.getSerializableExtra("data");
                                RoomName.setText(rr1.getRoomName());
                                RoomName.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr1.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("roomName",RoomName.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }
                        case R.id.activity:
                            //Stop MediaPlayer
                            if (intent.getExtras() != null) {
                                final TextView activityHours = (EditText) v;
                                final rehersalRoom rr1 = (rehersalRoom) intent.getSerializableExtra("data");
                                activityHours.setText(rr1.getOpenningHours());
                                activityHours.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr1.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("openningHours",activityHours.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }
                        case R.id.fbook:
                            //Stop MediaPlayer
                            if (intent.getExtras() != null) {
                                final TextView facebook = (EditText) v;
                                final rehersalRoom rr1 = (rehersalRoom) intent.getSerializableExtra("data");
                                facebook.setText(rr1.getFacebookPage());
                                facebook.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr1.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("facebookPage",facebook.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }
                        case R.id.site1:
                            //Stop MediaPlayer
                            if (intent.getExtras() != null) {
                                final TextView site = (EditText) v;
                                final rehersalRoom rr1 = (rehersalRoom) intent.getSerializableExtra("data");
                                site.setText(rr1.getSite());
                                site.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr1.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("site",site.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }
                        case R.id.room_type1:
                            //Stop MediaPlayer
                            if (intent.getExtras() != null) {
                                final TextView price = (EditText) v;
                                final rehersalRoom rr1 = (rehersalRoom) intent.getSerializableExtra("data");
                                price.setText(rr1.getPrice());
                                price.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr1.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("price",price.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }
                        case R.id.room_type2:
                            //Stop MediaPlayer
                            if (intent.getExtras() != null) {
                                final TextView price1 = (EditText) v;
                                final rehersalRoom rr1 = (rehersalRoom) intent.getSerializableExtra("data");
                                price1.setText(rr1.getPrice1());
                               price1.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr1.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("price1",price1.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }
                        case R.id.room_type3:
                            //Stop MediaPlayer
                            if (intent.getExtras() != null) {
                                final EditText price2 = (EditText) v;
                                final rehersalRoom rr1 = (rehersalRoom) intent.getSerializableExtra("data");
                                price2.setText(rr1.getPrice2());
                                price2.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("TotalRoomInfo").child(rr1.getRoomName());
                                        HashMap <String,Object> map=new HashMap<>();
                                        map.put("price2",price2.getText().toString());
                                        reference.updateChildren(map);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                                break;
                            }


                    }

                }
                //validate your EditText here

                else if (v instanceof ImageView) {

                    switch (v.getId()) {
                        case R.id.imageView3:
                            if (intent.getExtras() != null) {
                                ImageView im = (ImageView) v;
                                rehersalRoom rr = (rehersalRoom) intent.getSerializableExtra("data");
                                Glide.with(getApplicationContext()).load(rr.getImageUrl()).into(im);
                            }
                            break;

                    }

                } //etc. If it fails anywhere, just return false.

            }
            return true;
        }

}

package com.example.musicroom.Activities;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicroom.Models.rehersalRoom;
import com.example.musicroom.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public  class AddRoom extends AppCompatActivity implements View.OnClickListener {
    private EditText roomName, country, imageUrl, Site, FacebookPage
            ,  services,  adminName, city, adress,  email,phone,price,price1,price2,openningHours;
    private FirebaseUser user;
    DatabaseReference ref;
    FirebaseDatabase database;
    DatabaseReference ref1,reference;
    private TimePickerDialog picker;
    TextView startHour, endHour;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST=1;
    private StorageTask uploadTask;
    private Uri imageUri;
    private int mHour, mMinute;
    Button add;
    ImageButton imageB;
    Spinner spinner;
    ArrayAdapter<String> workhours;
    ArrayList<String> workhoursSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        spinner=findViewById(R.id.spinnerhour);

        workhoursSp=new ArrayList<>();
        workhoursSp.add("24 h/7");
        workhoursSp.add("בין השעות");
        workhours=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,workhoursSp);
        spinner.setAdapter(workhours);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem().toString().equals("בין השעות")){
                    startHour.setVisibility(View.VISIBLE);
                    endHour.setVisibility(View.VISIBLE);

                }
                else{
                    startHour.setVisibility(View.INVISIBLE);
                    endHour.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        user= FirebaseAuth.getInstance().getCurrentUser();
        add= findViewById(R.id.additem2);

        imageB=findViewById(R.id.imageButton);
        roomName=findViewById(R.id.roon_name_info);
        adminName=findViewById(R.id.admin_name1);
        city=findViewById(R.id.city1);
        country=findViewById(R.id.country);
        adress=findViewById(R.id.adress);

        imageUrl=findViewById(R.id.imageUrl2);
        Site=findViewById(R.id.site);
        FacebookPage=findViewById(R.id.facebookPage);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone1);

        startHour=findViewById(R.id.startHour);
        endHour=findViewById(R.id.endHour);


        price=findViewById(R.id.price);
        price1=findViewById(R.id.price1);
        price2=findViewById(R.id.price2);

        final Calendar c = Calendar.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
imageB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        selectImage();
    }
});

        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();

        final rehersalRoom rr=new rehersalRoom();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user= FirebaseAuth.getInstance().getCurrentUser();

                final DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("Rooms");


                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // for (DataSnapshot ds : snapshot.getChildren())
                        //  if (ds.getKey().toString().equals( "Keoss"))
                        //rr.setImageUrl(ds.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                rr.setRoomName(roomName.getText().toString());
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("TotalRoomsInfo").child(user.getUid()).child(rr.getRoomName());
if(startHour.getText().toString()!=""&&endHour.getText().toString()!=""){
    rr.setOpenningHours(startHour.getText().toString()+"-"+endHour.getText().toString());
}
                rr.setAdminName(adminName.getText().toString());
                rr.setCity(city.getText().toString());
                rr.setCountry(country.getText().toString());
                rr.setAdress(adress.getText().toString());

                rr.setUid(user.getUid());
                rr.setEmail(user.getEmail());

                rr.setImageUrl(imageUrl.getText().toString());
                rr.setSite(Site.getText().toString());
                rr.setFacebookPage(FacebookPage.getText().toString());

                rr.setContact(phone.getText().toString());

                rr.setStartHour(startHour.getText().toString());
                rr.setEndHour(endHour.getText().toString());
                rr.setOpenningHours(endHour.getText().toString()+"-"+endHour.getText().toString());
                rr.setPrice(price.getText().toString());
                rr.setPrice1(price1.getText().toString());
                rr.setPrice2(price2.getText().toString());

                ref.setValue(rr);
                Map<String,Object> map=new HashMap<>();
                map.put("RoomName",rr.getRoomName());
                DatabaseReference refU= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("RoomsInfo").child(rr.getRoomName());

                refU.setValue(rr);
                ref.setValue(rr);

                   /* ref1= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("RoomsInfo").child(rr.getRoomName());
                    ref1.setValue(rr);*/
Toast.makeText(AddRoom.this,"Room Added,please check",Toast.LENGTH_LONG).show();
            }
        });




        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        startHour.setOnClickListener(this);
        endHour.setOnClickListener(this);



    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

    @Override
    public void onClick(View v) {
        if(v==startHour)
            picker = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            startHour.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
        picker.show();
        if(v==endHour)
            picker = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            startHour.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
        picker.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imageUri=data.getData();
            if(uploadTask!=null&&uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            }
            else
            {
                UploadmyImage();
            }
        }
    }
    private void selectImage() {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent,IMAGE_REQUEST);
    }
    private String getFileExtention(Uri uri){
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void UploadmyImage(){
        final ProgressDialog pd=new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading");
        pd.show();
        if(imageUri!=null){
            final StorageReference fileReference=storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtention(imageUri));
            uploadTask=fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri DownloadUri=task.getResult();
                        String mUri=DownloadUri.toString();
                        reference=FirebaseDatabase.getInstance().getReference("UserImage").child(user.getUid());
                        HashMap<String,Object> Map=new HashMap<>();
                        Map.put("imageUrl1",mUri);

                        reference.setValue(Map);
                        pd.dismiss();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "failed!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "No Image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
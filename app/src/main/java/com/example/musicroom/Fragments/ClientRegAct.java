package com.example.musicroom.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.musicroom.R;
import com.example.musicroom.Models.clientObj;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;

public class ClientRegAct extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ArrayList<Integer> daySP,month_sp,years_sp;
    ArrayList<String> d;
    Spinner day,month,year;
    TextView age;
    private FirebaseUser user ;
    EditText email, password, Id, Name, phone, exp;
    TextView permission1;

    private  FirebaseDatabase database;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view= inflater.inflate(R.layout.fragment_reg, container, false);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        day=(Spinner) view.findViewById(R.id.day1);
        Id=(EditText) view.findViewById(R.id.id);
        month=(Spinner) view.findViewById(R.id.month1);
        year=(Spinner) view.findViewById(R.id.year1);
        age=(TextView)view.findViewById(R.id.age);
        email=view.findViewById(R.id.email);
        Name=view.findViewById(R.id.admin_name);
        phone=view.findViewById(R.id.phone);
        password=view.findViewById(R.id.password);
        exp=view.findViewById(R.id.experience);
        permission1=view.findViewById(R.id.permission1);


        daySP=new ArrayList<>();
        month_sp=new ArrayList<>();
        years_sp=new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();


        String emailS=email.getText().toString();
        String phoneS=email.getText().toString();
        final String nameS=email.getText().toString();

        clientObj Ra=new clientObj(nameS,emailS,phoneS, "");
        //Day spinner values

        for(int i=1;i<=31;i++)
        {
            daySP.add(i);
        }
        ArrayAdapter<Integer> ArrayAdpater=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,daySP);
        day.setAdapter(ArrayAdpater);

        //month spinner values
        for(int i=1;i<=12;i++)
        {
            month_sp.add(i);
        }



        ArrayAdpater=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,month_sp);
        month.setAdapter(ArrayAdpater);
        for(int i=1900;i<2020;i++)
        {
            years_sp.add(i);
        }

        ArrayAdpater=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,years_sp);
        year.setAdapter(ArrayAdpater);





        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Integer dayI =Integer.parseInt( day.getSelectedItem().toString());
                Integer monthI =Integer.parseInt(month.getSelectedItem().toString());
                Integer yearI = Integer.parseInt(year.getSelectedItem().toString());

                String emailS=email.getText().toString();
                String phoneS=phone.getText().toString();
                String nameS=Name.getText().toString();
                clientObj Ra=new clientObj(nameS,emailS,phoneS, "");

                try {
                    String age1= Ra.countAge(yearI,monthI,dayI);
                    age.setText(age1);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer dayI =Integer.parseInt( day.getSelectedItem().toString());
                Integer monthI =Integer.parseInt(month.getSelectedItem().toString());
                Integer yearI = Integer.parseInt(year.getSelectedItem().toString());

                String emailS=email.getText().toString();
                String phoneS=phone.getText().toString();
                String nameS=Name.getText().toString();

                clientObj Ra=new clientObj(nameS,emailS,phoneS, "");

                try {
                    String age1= Ra.countAge(yearI,monthI,dayI);
                    age.setText(age1);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer dayI =Integer.parseInt( day.getSelectedItem().toString());
                Integer monthI =Integer.parseInt(month.getSelectedItem().toString());
                Integer yearI = Integer.parseInt(year.getSelectedItem().toString());

                String emailS=email.getText().toString();
                String phoneS=Name.getText().toString();
                String nameS=phone.getText().toString();
                clientObj Ra=new clientObj(nameS,emailS,phoneS,"");

                try {
                    String age1= Ra.countAge(yearI,monthI,dayI);
                    age.setText(age1);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button b2=view.findViewById(R.id.reg_client);
        final RoomActivity RF= (RoomActivity) getActivity();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailS=email.getText().toString();
                String PhoneS=phone.getText().toString();
                String NameS=Name.getText().toString();
                String id=Id.getText().toString();
                String Password= password.getText().toString();
                String ageS= age.getText().toString();
                String permission= permission1.getText().toString();
                user = FirebaseAuth.getInstance().getCurrentUser();
                database = FirebaseDatabase.getInstance();
                RF.regFuncClient(EmailS,"on",Password,mAuth,PhoneS,nameS,id,user,database,permission);
            }
        });

        return view;
    }



}

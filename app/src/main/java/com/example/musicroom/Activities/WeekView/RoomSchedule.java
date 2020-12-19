package com.example.musicroom.Activities.WeekView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.musicroom.Activities.BaseActivity;
import com.example.musicroom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoomSchedule extends BaseActivity implements View.OnClickListener, WeekView.EventClickListener,WeekView.EmptyViewClickListener,MonthLoader.MonthChangeListener{
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    boolean calledNetwork = false;
    private  WeekView wv;
    private Date date1,date;
static int id=0;
     Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddEvents();

    }
    public void AddEvents(){
        events = new ArrayList<WeekViewEvent>();
        wv = findViewById(R.id.weekView);
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        intent=getIntent();
        final String id=intent.getStringExtra("id");
        assert user != null;
        final String s=user.getUid();
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("selectedid");
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot1) {
                DatabaseReference DR = FirebaseDatabase.getInstance().getReference("users").child(snapshot1.getValue().toString()).child("WeekViewEvent");
                DR.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {


                            DatabaseReference DR = FirebaseDatabase.getInstance().getReference("users").child(snapshot1.getValue().toString()).child("WeekViewEvent").child(ds.getKey().toString());
                            DR.addValueEventListener(new ValueEventListener() {
                                @SuppressLint("NewApi")
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    final WeekViewEvent event1 = new WeekViewEvent();
                                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                                        try {

                                            Calendar c1 = null, c = null;
                                            date = null;
                                            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                                            if (ds1.getKey().toString().equals("AstartTime")) {
                                                c1 = Calendar.getInstance();
                                                date1 = ((SimpleDateFormat) df).parse(ds1.getValue().toString());
                                            }

                                            if (ds1.getKey().toString().equals("BendTime")) {
                                                c1 = Calendar.getInstance();
                                                c1.setTime(date1);
                                                date = ((SimpleDateFormat) df).parse(ds1.getValue().toString());
                                                c = (Calendar) c1.clone();
                                                c.setTime(date);
                                            }

                                                event1.setName(ds1.getValue().toString());

                                            if (date != null && date1 != null) {


                                                final Calendar finalC1 = c1;
                                                final Calendar finalC = c;
                                                event1.setStartTime(finalC1);
                                                event1.setEndTime(finalC);

                                                Toast.makeText(RoomSchedule.this, "hi2 " + finalC1.getTime().toString(), Toast.LENGTH_SHORT).show();
                                                events.add(event1);
                                                getWeekView().setMonthChangeListener(new MonthLoader.MonthChangeListener() {

                                                    @Override
                                                    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                                                        ///WeekViewEvent event1 = new WeekViewEvent();
                                                        if (newMonth == finalC.get(Calendar.MONTH) && newYear == finalC1.get(Calendar.YEAR))
                                                            return events;
                                                        else {
                                                            return new ArrayList<WeekViewEvent>();
                                                        }


                                                    }
                                                });
                                                getWeekView().notifyDatasetChanged();
                                            }
                                        }catch (ParseException e){
                                            e.printStackTrace();
                                        }

                                    }
                                    event1.setName(snapshot.child("eventName").getValue().toString());

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

                }
        });
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * Triggered when the users clicks on a empty space of the calendar.
     *
     * @param time : {@link Calendar} object set with the date and time of the clicked position on the view.
     */
    @Override
    public void onEmptyViewClicked(Calendar time) {

    }

    /**
     * Very important interface, it's the base to load events in the calendar.
     * This method is called three times: once to load the previous month, once to load the next month and once to load the current month.<br/>
     * <strong>That's why you can have three times the same event at the same place if you mess up with the configuration</strong>
     *
     * @param newYear  : year of the events required by the view.
     * @param newMonth : month of the events required by the view <br/><strong>1 based (not like JAVA API) --> January = 1 and December = 12</strong>.
     * @return a list of the events happening <strong>during the specified month</strong>.
     */
    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
return  matchedEvents;
    }

    /**
     * Successful HTTP response.
     *
     * @param events
     * @param response
     */

}
package com.example.musicroom.Activities.WeekView;

import android.app.TimePickerDialog;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.musicroom.Activities.BaseActivity;
import com.example.musicroom.R;
import com.example.musicroom.apiclient.Event;
import com.example.musicroom.apiclient.MyJsonService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
//Week view Activity
public class asynchroneusActivity extends BaseActivity implements Callback<List<Event>>,View.OnClickListener, WeekView.EventClickListener,WeekView.EmptyViewClickListener, MonthLoader.MonthChangeListener{
//Events list
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    boolean calledNetwork = false;
    private  WeekView wv;
    private TimePickerDialog picker;
    private int mHour, mMinute;
    private EditText time,duration;
    private  TextView hour,minuteOfDay;
    private static Long id=Long.valueOf(0);
    private FirebaseUser user;
    private EditText title;
    Calendar endTime;
    Boolean b=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         final Button add_btn;
         final Button removeEvent;
         user= FirebaseAuth.getInstance().getCurrentUser();
        wv = findViewById(R.id.weekView);
        final EditText Day = findViewById(R.id.day1);
        final EditText Month = findViewById(R.id.month1);
        final EditText Year = findViewById(R.id.year1);
        hour = findViewById(R.id.hour);
        title = findViewById(R.id.event_name);
        minuteOfDay = findViewById(R.id.minute);
         time= findViewById(R.id.start_time);
        duration = findViewById(R.id.duration);
        add_btn = findViewById(R.id.add_event);
        removeEvent = findViewById(R.id.button);
//initiatiting events datails
        Day.setText("0");
        final WeekViewEvent event1 = new WeekViewEvent();
        time.setOnClickListener(this);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar startTime = Calendar.getInstance();
                mHour = startTime.get(Calendar.HOUR_OF_DAY);
                mMinute = startTime.get(Calendar.MINUTE);
                List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
                startTime.set(Calendar.MONTH, Integer.parseInt(Month.getText().toString()) - 1);
                startTime.set(Calendar.HOUR_OF_DAY,  Integer.parseInt(hour.getText().toString()));
                startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(Day.getText().toString()));
                startTime.set(Calendar.MINUTE, Integer.parseInt(minuteOfDay.getText().toString()));
                startTime.set(Calendar.YEAR, Integer.parseInt(Year.getText().toString()));
                try
                {
                    NumberFormat.getInstance().parse(duration.getText().toString());
                     endTime = (Calendar) startTime.clone();
                    endTime.add(Calendar.HOUR, Integer.parseInt(duration.getText().toString()));
                    endTime.set(Calendar.MONTH, Integer.parseInt(Month.getText().toString()) - 1);
                    HashMap<String,String> map= new HashMap();
                    map.put("AstartTime",startTime.getTime().toString());
                    map.put("BendTime",endTime.getTime().toString());
                    DatabaseReference DR= FirebaseDatabase.getInstance().getReference("users")
                            .child(user.getUid()).child("WeekViewEvent").child((String.valueOf(id)));

                    event1.setStartTime(startTime);
                    event1.setEndTime(endTime);
                    event1.setId(id++);
                    event1.setName(title.getText().toString()+"-"+getEventTitle(startTime));
                    event1.setColor(getResources().getColor(R.color.event_color_01));
                    events.add(event1);
                    map.put("eventName",event1.getName());
                    DR.setValue(map);
                }
                catch(ParseException e)
                {
                    // Not a number.
                    b=true;
                    Toast.makeText(asynchroneusActivity.this,"Please add and event duration",Toast.LENGTH_LONG).show();
                }




                getWeekView().setMonthChangeListener(new MonthLoader.MonthChangeListener() {

                    @Override
                    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                        ///WeekViewEvent event1 = new WeekViewEvent();
                        if (newMonth-1== startTime.get(Calendar.MONTH)&&newYear == startTime.get(Calendar.YEAR))
                            return events;
                        else {
                            return new ArrayList<WeekViewEvent>();
                        }
                    }


                });

                getWeekView().notifyDatasetChanged();

            }
        });

        removeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                events.remove(event1);
                getWeekView().notifyDatasetChanged();
            }
        });
        wv.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
             event1.setColor(getResources().getColor(R.color.event_color_03));
             getWeekView().notifyDatasetChanged();
             events.remove(event);
             getWeekView().notifyDatasetChanged();
            }
        });















    }



        /*add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=Month.getText().toString();
                Month.setText(s);
            }
        });*/


    @Override

//Changle month locotion according to month
    public List<? extends WeekViewEvent> onMonthChange(final int newYear, final int newMonth) {
        if (!calledNetwork) {

            RestAdapter retrofit = new RestAdapter.Builder()
                    .setEndpoint("https://api.myjson.com/bins")
                    .build();
            MyJsonService service = retrofit.create(MyJsonService.class);
            service.listEvents(this);
            calledNetwork = true;
        }
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
return matchedEvents;
    }



    @Override
    public void onClick(View v) {
        if(v==time)
            picker = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                          time.setText(hourOfDay + ":" + minute);
                            hour.setText(String.valueOf(hourOfDay));
                            minuteOfDay.setText(String.valueOf(minute));
                        }
                    }, mHour, mMinute, false);
        picker.show();

        picker.show();
    }
    //Check collapsing events- events in the same moment
    private boolean eventMatches(WeekViewEvent event, int year, int month)
    {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }
    @Override

    public void success(List<Event> events, Response response)
    {
        this.events.clear();

        for (Event event : events)
        {
                 this.events.add(event.toWeekViewEvent());
        }
        getWeekView().notifyDatasetChanged();
    }
// check wrong inputs from retrofit
    @Override
    public void failure(RetrofitError error)
    {
        error.printStackTrace();
        Toast.makeText(this, R.string.async_error, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onEmptyViewClicked(Calendar time) {

    }
}

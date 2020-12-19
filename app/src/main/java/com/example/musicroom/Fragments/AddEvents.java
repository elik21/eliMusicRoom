package com.example.musicroom.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.musicroom.Activities.WeekView.BasicActivity;
import com.example.musicroom.Activities.WeekView.asynchroneusActivity;
import com.example.musicroom.R;


public class AddEvents extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_week_view, container, false);
        Button btn=view.findViewById(R.id.buttonBasic);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BasicActivity.class);
                startActivity(intent);
            }

        });
        Button b=(Button) view.findViewById(R.id.buttonAsynchronous);
        b.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), asynchroneusActivity.class);
                startActivity(intent);
            }

        });
        return view;
    }

}

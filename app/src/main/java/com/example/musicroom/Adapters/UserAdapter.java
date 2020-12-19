package com.example.musicroom.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicroom.Chat.ChatActivities.MessageActivity;
import com.example.musicroom.R;
import com.example.musicroom.Models.User;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
private Context context;
private List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    final User client=users.get(position);
    holder.username.setText(client.getName());
    assert client!=null;
    if(client.getImageUrl().equals("default")){
    holder.iv.setImageResource(R.mipmap.ic_launcher);
    }
    else{
    Glide.with(context).load(client.getImageUrl()).apply(new RequestOptions().override(300,300)).into(holder.iv);
    }
    holder.itemView.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context, MessageActivity.class);
        intent.putExtra("UID",client.getId());
        context.startActivity(intent);
      }
    });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView iv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.user1);
            iv=itemView.findViewById(R.id.pic);
        }

    }

}

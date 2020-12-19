package com.example.musicroom.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicroom.R;
import com.example.musicroom.Models.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private List<Chat> chats;
    FirebaseUser fUser;
    private String imageUrl1;
    public final static int MSG_TYPE_RIGHT=1;
    public final static int MSG_TYPE_LEFT=0;

    public MessageAdapter(Context context, List<Chat> chats,String imageUrl1) {
        this.context = context;
        this.chats = chats;
        this.imageUrl1=imageUrl1;
    }


    @NonNull


    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT) {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(v);
        }
        else{
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(v);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        final Chat chat=chats.get(position);
        holder.showMessage.setText(chat.getMessage());
        if(imageUrl1.equals("default")){
               holder.profile.setImageResource(R.mipmap.ic_launcher);
        }
        else{
               Glide.with(context).load(imageUrl1).into(holder.profile);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
    public class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView showMessage;
        public ImageView profile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showMessage=itemView.findViewById(R.id.show_message);
            profile=itemView.findViewById(R.id.profile_image);
        }

    }
@Override
public int getItemViewType(int position){
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
}
}

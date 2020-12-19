package com.example.musicroom.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicroom.Chat.ChatActivities.MessageActivity;
import com.example.musicroom.Models.rehersalRoom;
import com.example.musicroom.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    List<rehersalRoom> rrL;
    List<rehersalRoom> rrList1;

    private List<rehersalRoom> mSelectedItemsIds;
    private selectedItem selected;
private Context context;
    public Adapter(Context context, List<rehersalRoom> rr){

        this.rrL=rr;
        this.selected=selected;
        this.rrList1=new ArrayList<>(rr);

        this.context=context;
    }
    public Adapter(Context context, List<rehersalRoom> rr, selectedItem selected){

        this.rrL=rr;
        this.selected=selected;
        this.rrList1=new ArrayList<>(rr);
        this.context=context;
        this.mSelectedItemsIds=new ArrayList<rehersalRoom>();
        //this.myrr=myrr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.room_view,null);
        return new ViewHolder(view);
    }
    public List<rehersalRoom> getSelectedIds() {
        return mSelectedItemsIds;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final rehersalRoom rr=rrL.get(position);
        Integer resource=rrL.get(position).getImageIcon();
        String title=rrL.get(position).getRoomName();
        String title2=rrL.get(position).getCity();
        holder.setData(resource,title,title2);
        if(rrL.get(position).getImageUrl()=="default"&&resource!=null){
            holder.iv.setImageResource(resource);
        }
      else
Glide.with(context).load(rrL.get(position).getImageUrl()).apply(new RequestOptions().override(100,100)).into(holder.iv);

        assert rr!=null;
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rehersalRoom rr=rrL.get(position);
                DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("selectedid");
                dbr.setValue(rr.getUid());
                selected.selectedItem1( rr);
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("UID",rr.getUid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rrL.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    public interface selectedItem
    {
        void selectedItem1(rehersalRoom RehersalRoom);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       ImageView iv;
       TextView tv;
       TextView tv2;
       TextView info;
       TextView chat;

       public ViewHolder(@NonNull final View itemView) {
           super(itemView);
           this.iv=itemView.findViewById(R.id.imageView);
           this.tv=itemView.findViewById(R.id.room_name);
           this.tv2=itemView.findViewById(R.id.room_city);
           this.info=itemView.findViewById(R.id.information);
           this.chat=itemView.findViewById(R.id.chat1);


       }
       private void setData(int immageResourse,String titleText,String bodyText){
          iv.setImageResource(immageResourse);
           tv.setText(titleText);
           tv2.setText(bodyText);
       }

   }
   private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<rehersalRoom> filteredList=new ArrayList<rehersalRoom>();
            if(constraint==null||constraint.length()==0)
            {
             filteredList.addAll(rrList1);
            }
            else
            {
                String Fpattern=constraint.toString().toLowerCase().trim();
                for(rehersalRoom item:rrList1)
                {
                    if(item.getRoomName().toLowerCase().contains(Fpattern))
                    filteredList.add(item);
                }
            }
            FilterResults res=new FilterResults();
            res.values=filteredList;
            return res;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rrL.clear();
            rrL.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}

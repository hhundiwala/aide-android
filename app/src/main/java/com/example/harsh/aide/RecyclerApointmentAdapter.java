package com.example.harsh.aide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by HARSH on 21-05-2017.
 */

public class RecyclerApointmentAdapter extends RecyclerView.Adapter<RecyclerApointmentHolder> {
    LayoutInflater inflater;
    List<apointment> list;
    Context context;
    ItemClickCallback itemClickCallback;
    int layout;
    boolean isGrid=false;

    public RecyclerApointmentAdapter(List<apointment> episodeList, Context context, int layout, boolean isGrid) {
        this.list =episodeList;
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.itemClickCallback= (ItemClickCallback) context;
        this.layout=layout;
        this.isGrid=isGrid;
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    public RecyclerApointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(this.layout, parent, false);
        return new RecyclerApointmentHolder(view,itemClickCallback);
    }

    @Override
    public void onBindViewHolder(final RecyclerApointmentHolder holder, int position) {
        final apointment item = list.get(position);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(item.getRequester_id());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                holder.apointee.setText(u.getFname()+" "+u.getLname());
                holder.task.setText(item.getTask());
                holder.date.setText(item.getDate());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


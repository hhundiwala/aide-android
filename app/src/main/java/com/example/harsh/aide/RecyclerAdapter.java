package com.example.harsh.aide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by HARSH on 20-05-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    LayoutInflater inflater;
    List<conversation> list;
    Context context;
    ItemClickCallback itemClickCallback;
    int layout;
    boolean isGrid=false;

    public RecyclerAdapter(List<conversation> episodeList, Context context, int layout, boolean isGrid) {
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
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(this.layout, parent, false);
        return new RecyclerHolder(view,itemClickCallback);
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, int position) {
            final conversation item = list.get(position);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(item.getFrom());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                holder.name.setText(u.getFname()+" "+u.getLname());
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

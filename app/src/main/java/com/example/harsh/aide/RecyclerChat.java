package com.example.harsh.aide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

/**
 * Created by HARSH on 21-04-2017.
 */

public class RecyclerChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Message> messagelist;
    Context context;
    final int SEND = 0;
    final int RECIEVED = 1;
    ItemClick itemClick;

    public RecyclerChat(List<Message> messagelist, Context context) {
        this.messagelist = messagelist;
        this.context = context;
        this.itemClick = (ItemClick) context;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case SEND:
                View v1 = inflater.inflate(R.layout.msg_sent_layout, parent, false);
                viewHolder = new MsgSentHolder(v1,itemClick);
                Log.d("demo","Send");
                break;
            case RECIEVED:
                View v2 = inflater.inflate(R.layout.msg_recieved_layout, parent, false);
                Log.d("demo","Recieved");
                viewHolder = new MsgRecievedHolder(v2,itemClick);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SEND:
                MsgSentHolder sent_holder = (MsgSentHolder) holder;
                configureViewHolder_RECIEVE(sent_holder, position);
                break;
            case RECIEVED:
                MsgRecievedHolder recievedHolder = (MsgRecievedHolder) holder;
                configureViewHolder_SEND(recievedHolder, position);
                break;
        }
    }

    private void configureViewHolder_RECIEVE(final MsgSentHolder sent_holder, final int position) {
        //sent_holder.tv_sender.setText(messagelist.get(position).getSent_by());
        //retrieving sender name from this ID
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Aide");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Aide u=dataSnapshot.child(messagelist.get(position).sent_by).getValue(Aide.class);
                sent_holder.tv_sender.setText(u.getFname()+" "+u.getLname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(messagelist.get(position).getMessage()!=null){
            sent_holder.tv_message.setText(messagelist.get(position).getMessage());
            sent_holder.iv.setVisibility(View.INVISIBLE);
        }else {
            sent_holder.iv.setVisibility(View.VISIBLE);
            Picasso.with(this.context).load(messagelist.get(position).getImgurl()).resize(150,150).into(sent_holder.iv);
            sent_holder.tv_message.setVisibility(View.INVISIBLE);
        }
        PrettyTime p = new PrettyTime();
        sent_holder.tv_time.setText(p.format(new Date(messagelist.get(position).getTime())));

        //for comments

//        for (final String s:messagelist.get(position).getComments()) {
//            DatabaseReference cref = FirebaseDatabase.getInstance().getReference("comments");
//            cref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    comment c = dataSnapshot.child(s).getValue(comment.class);
//                    TextView tv = new TextView(context);
//                    tv.setText(c.getComment());
//                    tv.setPadding(300,0,0,0);
//                    sent_holder.ll.addView(tv);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
    }

    private void configureViewHolder_SEND(final MsgRecievedHolder recievedHolder, final int position) {
        recievedHolder.tv_sender.setText(messagelist.get(position).getSent_by());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u=dataSnapshot.child(messagelist.get(position).sent_by).getValue(User.class);
                recievedHolder.tv_sender.setText(u.getFname() + " "+ u.getLname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(messagelist.get(position).getMessage()!=null){
            recievedHolder.tv_message.setText(messagelist.get(position).getMessage());
            recievedHolder.iv.setVisibility(View.INVISIBLE);
        }else {
            recievedHolder.iv.setVisibility(View.VISIBLE);
            Picasso.with(this.context).load(messagelist.get(position).getImgurl()).resize(150,150).into(recievedHolder.iv);
            recievedHolder.tv_message.setVisibility(View.INVISIBLE);
        }
        PrettyTime p = new PrettyTime();
        recievedHolder.tv_time.setText(p.format(new Date(messagelist.get(position).getTime())));
        //for comments
//        for (final String s:messagelist.get(position).getComments()) {
//            DatabaseReference cref = FirebaseDatabase.getInstance().getReference("comments");
//            cref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    comment c = dataSnapshot.child(s).getValue(comment.class);
//                    TextView tv = new TextView(context);
//                    tv.setText(c.getComment());
//                    recievedHolder.ll.addView(tv);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }

    }

    @Override
    public int getItemCount() {
        return this.messagelist.size();
    }

    @Override
    public int getItemViewType(int position) {
        //Add UserActivity here
        if(MainActivity.user.getKey().equals(messagelist.get(position).getSent_by())){
            return SEND;
        }else{
            return RECIEVED;
        }
    }


}

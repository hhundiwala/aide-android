package com.example.harsh.aide;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class ChatList extends AppCompatActivity implements ItemClickCallback {

    Aide helper = new Aide();
    RecyclerView recyclerView;
    ArrayList<conversation> cList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        helper = (Aide) getIntent().getExtras().getSerializable("USER");
        getSupportActionBar().setTitle("Chat List");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Aide");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                helper = dataSnapshot.child(helper.getKey()).getValue(Aide.class);
                //got latest helper..now load the conversations
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("conversation");
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            if(helper.getConversations()!=null){
                                if(helper.getConversations().contains(postSnapshot.getValue(conversation.class).getKey())){
                                    cList.add(postSnapshot.getValue(conversation.class));
                                }
                            }
                        }
                        inflateLayout(cList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void inflateLayout(ArrayList<conversation> cList){
        if(cList!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(ChatList.this));
            RecyclerAdapter adapter=new RecyclerAdapter(cList,ChatList.this,R.layout.conversation_layout,false);
            recyclerView.setAdapter(adapter);
            adapter.setItemClickCallback(ChatList.this);
        }
    }

    @Override
    public void OnItemClick(int id) {
        Intent intent = new Intent(ChatList.this,ChatActivity.class);
        intent.putExtra("conversation",cList.get(id));
        startActivity(intent);
    }

    @Override
    public void OnSubItemClick(int id) throws IOException {

    }
}

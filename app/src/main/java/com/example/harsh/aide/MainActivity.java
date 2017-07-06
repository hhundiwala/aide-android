package com.example.harsh.aide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickCallback {
    String key;
    static Aide user = new Aide();
    Button chat;
    RecyclerView recyclerView;
    ArrayList<apointment> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        user = (Aide) getIntent().getExtras().getSerializable("USER");
        chat = (Button) findViewById(R.id.btn_chat);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Aide").child(user.getKey());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(Aide.class);
                DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("apointment");
                dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list = new ArrayList<apointment>();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            if(user.getApointments()!=null){
                                if(user.getApointments().contains(postSnapshot.getValue(apointment.class).getKey())){
                                    list.add(postSnapshot.getValue(apointment.class));
                                }
                            }
                        }
                        if(list!=null){
                            LinearLayoutManager ll = new LinearLayoutManager(MainActivity.this);
                            ll.setReverseLayout(true);
                            recyclerView.setLayoutManager(ll);
                            RecyclerApointmentAdapter adapter=new RecyclerApointmentAdapter(list,MainActivity.this,R.layout.apointment_row,false);
                            recyclerView.setAdapter(adapter);
                            adapter.setItemClickCallback(MainActivity.this);
                        }
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
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChatList.class);
                intent.putExtra("USER",user);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnItemClick(int id) {

    }

    @Override
    public void OnSubItemClick(int id) throws IOException {

    }
}

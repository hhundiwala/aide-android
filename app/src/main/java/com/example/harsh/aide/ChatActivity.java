package com.example.harsh.aide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity implements ItemClick {

    RecyclerView recyclerView;
    RecyclerChat adapter;
    String photourl;
    ArrayList<Message> MessageList = new ArrayList<>();
    EditText message;
    ImageButton send,image;
    Aide helper = new Aide();
    conversation c;
    int flag =0;
    String conversation_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.rv_chat);
        send = (ImageButton) findViewById(R.id.ib_send);
        message = (EditText) findViewById(R.id.et_msg);
        image = (ImageButton) findViewById(R.id.ib_image);

        c = (conversation) getIntent().getExtras().getSerializable("conversation");
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("conversation").child(c.getKey());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c = dataSnapshot.getValue(conversation.class);
                DatabaseReference messageDataRef = FirebaseDatabase.getInstance().getReference("messages");
                messageDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                MessageList = new ArrayList<Message>();
                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                    if(c.getMessages().contains(postSnapshot.getValue(Message.class).getMeg_key())){
                                        MessageList.add(postSnapshot.getValue(Message.class));
                                    }
                                }
                                LinearLayoutManager ll = new LinearLayoutManager(ChatActivity.this);
                                adapter=new RecyclerChat(MessageList,ChatActivity.this);
                                recyclerView.setLayoutManager(ll);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString()!=null){
                    Message m = new Message();
                    m.setTime(System.currentTimeMillis());
                    m.setMessage(message.getText().toString());
                    m.setSent_by(MainActivity.user.getKey());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");
                    DatabaseReference mRef = reference.push();
                    m.setMeg_key(mRef.getKey());
                    mRef.setValue(m);
                    message.setText("");
                    DatabaseReference dreference = FirebaseDatabase.getInstance().getReference("conversation");
                    ArrayList<String> msgList = new ArrayList<String>();
                    if(c.getMessages()!=null){
                        msgList = c.getMessages();
                    }
                    msgList.add(m.getMeg_key());
                    flag=1;
                    c.setMessages(msgList);
                    dreference.child(c.getKey()).setValue(c);
                }
            }
        });

   image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = data.getData();
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            FirebaseStorage.getInstance().getReference("Images").child(UUID.randomUUID().toString()).putStream(inputStream).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    photourl = downloadUrl.toString();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("messages");
                    final Message m = new Message();
                    m.setImgurl(photourl);
                    m.setSent_by(MainActivity.user.getKey());
                    m.setTime(System.currentTimeMillis());
                    final DatabaseReference push = databaseReference.push();
                    m.setMeg_key(push.getKey());
                    push.setValue(m);
                    DatabaseReference dreference = FirebaseDatabase.getInstance().getReference("conversation");
                    ArrayList<String> msgList = new ArrayList<String>();
                    if(c.getMessages()!=null){
                        msgList = c.getMessages();
                    }
                    msgList.add(m.getMeg_key());
                    flag=1;
                    c.setMessages(msgList);
                    dreference.child(c.getKey()).setValue(c);
                    photourl = null;
                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}

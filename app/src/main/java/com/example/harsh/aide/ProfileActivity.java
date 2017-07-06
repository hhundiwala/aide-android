package com.example.harsh.aide;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import static android.R.attr.key;

public class ProfileActivity extends AppCompatActivity {
    Spinner s;
    EditText et_description,phone,address;
    Button update;
    ImageButton ib;
    String key1;
    Aide user = new Aide();
    ArrayList<String> profiles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Edit Profile");

        key1 = getIntent().getExtras().getString("Aide");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Aide");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot snap : children) {
                    Aide value = snap.getValue(Aide.class);
                    Log.d("user2",value.toString());
                    if(value.getKey().equals(key1)){
                        user = value;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ib = (ImageButton) findViewById(R.id.ib_profile);
        s = (Spinner) findViewById(R.id.spinner);
        et_description = (EditText) findViewById(R.id.et_description);
        phone = (EditText) findViewById(R.id.et_phone);
        address = (EditText) findViewById(R.id.et_address);
        update = (Button) findViewById(R.id.btn_update);
        profiles.add("Plumber");
        profiles.add("Carpenter");
        profiles.add("LockSmith");
        profiles.add("Cook");
        profiles.add("Mason");
        profiles.add("Helper");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, profiles);
        s.setAdapter(adapter);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setEnabled(false);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_description.getText().toString()!="" && phone.getText().toString()!="" && address.getText().toString()!=""){
                    if(et_description.getText().toString().length()!=0 && phone.getText().toString().length()!=0 && address.getText().toString().length()!=0 ) {
                        // go ahead and update the profile
                        user.setAddress(address.getText().toString());
                        user.setJob_description(et_description.getText().toString());
                        user.setPhone(phone.getText().toString());
                        user.setJob_profile(s.getSelectedItem().toString());
                        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Aide");
                        dref.child(user.getKey()).setValue(user);
                        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                        intent.putExtra("USER",user);
                        startActivity(intent);
                        finish();
                    }
                }
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
                    String photourl = downloadUrl.toString();
                    Picasso.with(ProfileActivity.this).load(photourl).resize(100,100).into(ib);
                    user.setImage(photourl);
                    update.setEnabled(true);
                }
            });
        }
    }
}

package com.example.harsh.aide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText et_fname,et_lname,et_email,et_password,et_confirm;
    Button btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_fname = (EditText) findViewById(R.id.etfname);
        et_lname = (EditText) findViewById(R.id.etlname);
        et_email = (EditText) findViewById(R.id.etemail);
        et_password = (EditText) findViewById(R.id.etpwd);
        et_confirm = (EditText) findViewById(R.id.etcpwd);
        btn_signup = (Button) findViewById(R.id.btnsignup1);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_fname.getText().toString()!="" && et_lname.getText().toString()!="" && et_email.getText().toString()!="" && et_password.getText().toString()!="" && et_confirm.getText().toString()!=""){
                    if(et_fname.getText().toString().length()!=0 && et_lname.getText().toString().length()!=0 && et_email.getText().toString().length()!=0 && et_password.getText().toString().length()!=0 && et_confirm.getText().toString().length()!=0){
                        if(et_password.getText().toString().equals(et_confirm.getText().toString())){
                            Aide u  = new Aide();
                            u.setEmail(et_email.getText().toString());
                            u.setFname(et_fname.getText().toString());
                            u.setLname(et_lname.getText().toString());
                            u.setPassword(et_password.getText().toString());
                            DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("Aide");
                            DatabaseReference uRef = Ref.push();
                            u.setKey(uRef.getKey());
                            uRef.setValue(u);
                            Toast.makeText(SignUpActivity.this,"Thank you for Signing Up with us!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignUpActivity.this,"Password doesnt match",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignUpActivity.this,"Please input all the fields",Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(SignUpActivity.this,"Please input all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

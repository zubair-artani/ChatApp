package com.example.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {
    EditText uname,email,pass;
    Button btn_register;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        Toolbar toolbars = findViewById(R.id.bar_layout);
        setSupportActionBar(toolbars);
//        toolbars.setTitle("Register");
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_name = uname.getText().toString();
                String u_email = email.getText().toString();
                String u_pass = pass.getText().toString();
                if (TextUtils.isEmpty(u_name) || TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_pass)) {
                    Toast.makeText(Register_Activity.this, "All fields are Required", Toast.LENGTH_SHORT).show();
                } else if (u_pass.length() < 6) {
                    Toast.makeText(Register_Activity.this, "Password must be at least 6", Toast.LENGTH_SHORT).show();
                } else {
                    register(u_name,u_email,u_pass);
                }
            }

        });
    }

    private void register(final String u_name, String u_email, String u_pass) {

        auth.createUserWithEmailAndPassword(u_email,u_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    user = auth.getCurrentUser();
                    assert user != null;
                    String user_id = user.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",user_id);
                    hashMap.put("username",u_name);
                    hashMap.put("imageUrl","Default");
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Register_Activity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    private void initialize() {
        uname = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btn_register = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
    }
}

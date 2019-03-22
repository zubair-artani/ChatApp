package com.example.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Start_Activity extends AppCompatActivity {
    Button btn_login,btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_);
        btn_login  = findViewById(R.id.login);
        btn_signup  = findViewById(R.id.signup);
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null) {
            Intent intent = new Intent(Start_Activity.this,MainActivity.class);
            startActivity(intent);
        }
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start_Activity.this,Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}

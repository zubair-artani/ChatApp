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

public class Login_Activity extends AppCompatActivity {
    EditText email,pass;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        Toolbar toolbars = findViewById(R.id.bar_layout);
        setSupportActionBar(toolbars);
//        toolbars.setTitle("Register");
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString();
                String user_pass = pass.getText().toString();
                if (TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_pass)) {
                    Toast.makeText(Login_Activity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login_Activity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initialize() {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btn_login = findViewById(R.id.login);

    }
}

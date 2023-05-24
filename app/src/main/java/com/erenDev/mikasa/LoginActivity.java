package com.erenDev.mikasa;

import static com.erenDev.mikasa.R.*;
import static com.erenDev.mikasa.R.id.l_email;
import static com.erenDev.mikasa.R.id.not_Registered;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText usrlEmail , usrlPasswd;
    private Button loginButton;
    private TextView redirectsignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);

        auth = FirebaseAuth.getInstance();
        usrlEmail = findViewById(id.l_email);
        usrlPasswd = findViewById(id.l_passwd);
        loginButton = findViewById(id.login_button);
        redirectsignUp = findViewById(id.not_Registered);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usrlEmail.getText().toString();
                String pass = usrlPasswd.getText().toString();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        usrlPasswd.setError("Password cannot be empty");
                    }
                }   else if(!email.isEmpty()) {
                        usrlEmail.setError("Email cannot be empty");
                    } else {
                        usrlEmail.setError("Enter Valid Email");
                    }
                }

        });

        redirectsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }
}
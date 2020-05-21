package com.example.gymvision.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.example.gymvision.R;


public class ResetPassActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button ResetPasswordButton;
    private EditText ResetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        ResetPasswordButton = findViewById(R.id.resetPassButton);
        ResetEmail = findViewById(R.id.resetPassEmail);


        ResetPasswordButton.setOnClickListener(v -> {
            String rEmail = ResetEmail.getText().toString();

            if (TextUtils.isEmpty(rEmail)){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
            }
            else{
                mAuth.sendPasswordResetEmail(rEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ResetPassActivity.this, "Please check your email", Toast.LENGTH_LONG).show();
                            updateUI();
                        }
                        else{
                            String message = task.getException().getMessage();
                            Toast.makeText(ResetPassActivity.this, "An error has occurred"+message, Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }

        });


    }

    private void updateUI() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }}



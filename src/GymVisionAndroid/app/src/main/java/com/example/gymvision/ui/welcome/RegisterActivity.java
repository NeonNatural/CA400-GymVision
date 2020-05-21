package com.example.gymvision.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.example.gymvision.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText userMail;
    private EditText userPass;
    private EditText userPass2;
    private EditText userName;


    private Button regBtn;
    private TextView loginInstead;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userMail = findViewById(R.id.regiMail);
        userPass = findViewById(R.id.regiPass);
        userPass2 = findViewById(R.id.regiPass2);
        userName = findViewById(R.id.regiName);
        regBtn = findViewById(R.id.regiButton);
        loginInstead = findViewById(R.id.lgnInstead);

        mAuth = FirebaseAuth.getInstance();
        regBtn.setOnClickListener(v -> {

            final String email = userMail.getText().toString();
            final String password = userPass.getText().toString();
            final String password2 = userPass2.getText().toString();
            final String name = userName.getText().toString();

            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || password2.isEmpty() || !password.equals(password2)) {

                showMessage("Please Verify all fields");





            } else {

                CreateUserAccount(email, name, password);
            }

        });

        loginInstead.setOnClickListener(view -> {
            Intent loginactivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginactivity);
        });



    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void CreateUserAccount(String email, final String name, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                updateUsr(name, mAuth.getCurrentUser());
                showMessage("Account created Successfully");

            } else {
                showMessage("Account was not created" + task.getException().getMessage());
            }
        });
    }

    private void updateUsr(String name, FirebaseUser currentUser) {
        UserProfileChangeRequest profUpdate = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

        currentUser.updateProfile(profUpdate).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                updateUi();
            }
        }
        );

    }

    private void updateUi() {
        Intent homeActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(homeActivity);
        finish();
    }

}

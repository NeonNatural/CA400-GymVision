package com.example.gymvision.ui.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.gymvision.R;
import com.example.gymvision.ui.HomeActivity;

public class LoginActivity extends AppCompatActivity {




    private FirebaseAuth mAuth;


    private SharedPreferences.Editor sharedPrefEditor;

    private EditText emailInput;
    private EditText passwordInput;

    private Button loginBtn;
    private Button registerBtn;
    private TextView resetPasswordButton;

    private ProgressBar loginBtnProgress;
    private static final int MY_PERMISSIONS_REQUEST =1;

    Boolean loggedOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        sharedPrefEditor = getSharedPreferences("com.test", MODE_PRIVATE).edit();



        emailInput = findViewById(R.id.resetPassEmail);
        passwordInput = findViewById(R.id.login_password);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.resetPassButton);

        loginBtnProgress = findViewById(R.id.loginBtnProgress);
        resetPasswordButton = findViewById(R.id.forgotPass);




        registerBtn.setOnClickListener(view -> {
            Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(registerActivity);
        });

        loginBtn.setOnClickListener(view -> {
            final String mail = emailInput.getText().toString();
            final String password = passwordInput.getText().toString();


            if (mail.isEmpty() || password.isEmpty()) {
                showMessage("Please Verify All Field");
            } else {
                loginBtn.setVisibility(View.INVISIBLE);
                loginBtnProgress.setVisibility(View.VISIBLE);

                signIn(mail, password);
            }
        });

        resetPasswordButton.setOnClickListener(view -> {
            Intent resetactivity = new Intent(getApplicationContext(), ResetPassActivity.class);
            startActivity(resetactivity);
        });
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            updateUI();
        }

    }

    private void signIn(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sharedPrefEditor.putString("email", mail).commit();
                        updateUI();
                    } else {
                        loginBtnProgress.setVisibility(View.INVISIBLE);
                        loginBtn.setVisibility(View.VISIBLE);
                        showMessage(task.getException().getMessage());
                    }
                });
    }

    public void updateUI() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }



}

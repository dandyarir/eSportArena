package com.example.dendi.esportarena.AuthForm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dendi.esportarena.MainActivity;
import com.example.dendi.esportarena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button login,signup;
    private EditText usr,pass;
    ProgressDialog loadingLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.login_btn);
        signup = (Button) findViewById(R.id.signup_btn);
        usr = (EditText) findViewById(R.id.username_et);
        pass = (EditText) findViewById(R.id.password_et);
        loadingLogin = new ProgressDialog(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void startSignIn(){
        loadingLogin.setMessage("Logging in. .");
        loadingLogin.show();
        String email = usr.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Enter Username dan Password!", Toast.LENGTH_LONG).show();
            loadingLogin.dismiss();
        }else if(TextUtils.isEmpty(password) && !isPasswordValid(password)){
            Toast.makeText(LoginActivity.this, "Password is Too Short", Toast.LENGTH_LONG).show();
            loadingLogin.dismiss();
        }else if (TextUtils.isEmpty(email) && !isEmailValid(email)){
            Toast.makeText(LoginActivity.this, "isn't Corret Email Adress", Toast.LENGTH_LONG).show();
            loadingLogin.dismiss();
        }else {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "login failed.", Toast.LENGTH_LONG).show();
                        loadingLogin.dismiss();
                    }
                }
            });
        }
    }
}

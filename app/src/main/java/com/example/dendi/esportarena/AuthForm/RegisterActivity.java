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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button create;
    private EditText inputEmail,inputPass;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        create = (Button) findViewById(R.id.register_btn);
        inputEmail = (EditText) findViewById(R.id.email_et_register);
        inputPass = (EditText) findViewById(R.id.password_et_register);
        progressDialog = new ProgressDialog(this);



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String pass = inputPass.getText().toString();

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "Enter Username dan Password!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }else if(TextUtils.isEmpty(pass) || !isPasswordValid(pass)){
                    Toast.makeText(RegisterActivity.this, "Password is Too Short", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }else if (TextUtils.isEmpty(email) || !isEmailValid(email)){
                    Toast.makeText(RegisterActivity.this, "isn't Corret Email Adress", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }else {
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful()
                                                ,Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    } else {
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}

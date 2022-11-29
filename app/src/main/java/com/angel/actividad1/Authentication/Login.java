package com.angel.actividad1.Authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.util.Log;

import com.angel.actividad1.BottomNavigation.Home;
import com.angel.actividad1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class Login extends AppCompatActivity {
    EditText txtLoginEmail, txtLoginPassword;
    Button btnLogin, btnLoginBack,btnLoginForgot;
    Switch switchLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginBack = findViewById(R.id.btnLoginBack);
        btnLoginForgot = findViewById(R.id.btnLoginForgot);
        switchLogin = findViewById(R.id.switchLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnLoginBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(Login.this, SignUp.class));
                //finish();
            }
        });

        btnLoginForgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(Login.this,ResetPassword.class));
            }
        });

        SharedPreferences prefs = this.getSharedPreferences("recordar", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");
        boolean stateSwitch = prefs.getBoolean("stateSwitch", false);
        switchLogin.setChecked(stateSwitch);
        txtLoginEmail.setText(email);
        txtLoginPassword.setText(password);
    }

    private void login() {

        String email = txtLoginEmail.getText().toString().toLowerCase(Locale.ROOT);
        String password = txtLoginPassword.getText().toString().toLowerCase(Locale.ROOT);
        SharedPreferences prefs = this.getSharedPreferences("recordar", Context.MODE_PRIVATE);
        if(switchLogin.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", txtLoginEmail.getText().toString());
            editor.putString("password", txtLoginPassword.getText().toString());
            editor.putBoolean("stateSwitch", switchLogin.isChecked());
            editor.commit();
        }else {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email","");
            editor.putString("password", "");
            editor.putBoolean("stateSwitch", switchLogin.isChecked());
            editor.commit();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("iniciosesion",e.getMessage());
                    }
                });;



    }
    private void updateUI (FirebaseUser user){
        if(user != null){
            Toast.makeText(Login.this, "Acceso exitoso!",
                    Toast.LENGTH_SHORT).show();
           goToHome();

        }else{
            Toast.makeText(Login.this, "ERROR: Acceso fallido",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void goToHome(){
        startActivity(new Intent(Login.this, Home.class));
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToHome();
        }
    }
}

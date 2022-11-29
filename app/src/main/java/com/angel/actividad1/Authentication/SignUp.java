package com.angel.actividad1.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUp extends AppCompatActivity {
    EditText txtSignUpEmail, txtSignUpPassword;
    Button btnSignUp,btnSignUpBack;

    private FirebaseAuth mAuth;


        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtSignUpEmail = findViewById(R.id.txtSignUpEmail);
        txtSignUpPassword = findViewById(R.id.txtSignUpPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUpBack = findViewById(R.id.btnSignUpBack);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signup();
            }
        });

        btnSignUpBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(SignUp.this, Login.class));
                //finish();
            }
        });

    }

    public void signup(){

        String email = txtSignUpEmail.getText().toString();
        String password = txtSignUpPassword.getText().toString();

        AlertDialog alertDialog = new AlertDialog.Builder(SignUp.this).create();
        alertDialog.setTitle("ERROR");
        alertDialog.setMessage("Contraseña invalida, no debe ser similar al mail");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        boolean result = passwordCheck();
        if (result == false){
            alertDialog.show();
        }else{
            startActivity(new Intent(SignUp.this, Home.class));
        };

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("registro",e.getMessage());
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Toast.makeText(SignUp.this, "Registro exitoso!",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(SignUp.this, "ERROR: Registro fallido",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public boolean passwordCheck(){

        String email = txtSignUpEmail.getText().toString().toLowerCase(Locale.ROOT);
        String password = txtSignUpPassword.getText().toString().toLowerCase(Locale.ROOT);

        String[] separaEmail = email.split("\\@");

        char[] chP = password.toCharArray();
        char[] chE = separaEmail[0].toCharArray();

        char[] grande;
        char[] chico;
        if(chP.length>chE.length){
            grande = chP;
            chico = chE;
        }else{
            grande = chE;
            chico = chP;
        }

        int contador = 0;
        for(int j=0;j<grande.length;j++){
            //System.out.println(chP[j]);
            for(int f=0; f<chico.length;f++){
                //System.out.println(chE[f]);
                if(grande[j]==chico[f]){
                    contador++;

                };
            };
        };
        double total = chP.length;
        double tolerancia = contador/total;
        System.out.println("Coinicidencia: "+tolerancia*100+"%");
        if(tolerancia <= 0.46){
            return true;
        };
        return false;

    };


}

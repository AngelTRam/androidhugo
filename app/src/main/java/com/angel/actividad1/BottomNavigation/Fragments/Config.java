package com.angel.actividad1.BottomNavigation.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angel.actividad1.Authentication.Login;
import com.angel.actividad1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Config extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_config, container, false);

        SharedPreferences sharedPref = getContext().getSharedPreferences(
                "userInfo", Context.MODE_PRIVATE);
        String uid = sharedPref.getString("uid", "No se encontro");
        TextView txtUid = view.findViewById(R.id.txtUid);
        txtUid.setText(uid);

        // Inflate the layout for this fragment

        Button deleteAccount = view.findViewById(R.id.btnDeleteAccount);
        Button changePassword = view.findViewById(R.id.btnChangePassword);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlertDialog(1);
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlertDialog(2);
            }
        });
        return view;
    }

    private void getAlertDialog(int option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_authentication, null);

        EditText email = dialogView.findViewById(R.id.dialogEmail);
        EditText password = dialogView.findViewById(R.id.dialogPassword);
        EditText newPassword = dialogView.findViewById(R.id.txtNewPassword);
        TextView newPasswordB = dialogView.findViewById(R.id.textView7);
        Button reAuth = dialogView.findViewById(R.id.btnReAuth);

        if (option == 1) {
            newPassword.setVisibility(view.VISIBLE);
            newPasswordB.setVisibility(view.VISIBLE);
        }

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        reAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email.getText().toString(), password.getText().toString());
                if (user != null) {
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        getOption(option, alertDialog, newPassword.getText().toString());
                                    } else {
                                        alertDialog.dismiss();
                                        Toast.makeText(getContext(), "Error al autenticar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    private void getOption(int option, AlertDialog alertDialog, String newPassword) {
        if (option == 1) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                alertDialog.dismiss();
                                Toast.makeText(getContext(), "Contraseña actualizada.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Usuario eliminado.", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), Login.class));
                        getActivity().finish();
                    } else {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Error al autenticar.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
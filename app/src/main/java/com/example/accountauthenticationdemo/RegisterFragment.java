package com.example.accountauthenticationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;

    private EditText reg_email, reg_password, reg_password_again;
    private ProgressBar reg_progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();

        reg_email = v.findViewById(R.id.reg_email);
        reg_password = v.findViewById(R.id.reg_password);
        reg_password_again = v.findViewById(R.id.reg_password_again);
        Button reg_button = v.findViewById(R.id.reg_button);
        reg_progressBar = v.findViewById(R.id.reg_progressBar);





        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = reg_email.getText().toString().trim();
                String password = reg_password.getText().toString();
                String password_again = reg_password_again.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    reg_email.setError("Email is required!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    reg_password.setError("Enter a password!");
                    return;
                }

                if (password.length() < 6) {
                    reg_password.setError("Password is too short! (Must be more than 6 characters)");
                    return;
                }


                if (TextUtils.isEmpty(password_again)) {
                    reg_password_again.setError("Re-Enter your password!");
                    return;
                }

                if (!password.equals(password_again)) {
                    reg_password_again.setError("Passwords do not match!");
                    return;
                }

                reg_progressBar.setVisibility(View.VISIBLE);

                //Register user in firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        return v;


    }
}

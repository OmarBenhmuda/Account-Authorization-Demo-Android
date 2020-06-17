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

import com.example.accountauthenticationdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private EditText log_email, log_password;
    private ProgressBar log_progressBar;

    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();




        log_email = v.findViewById(R.id.log_email);
        log_password = v.findViewById(R.id.log_password);

        Button log_button = v.findViewById(R.id.log_button);

        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();


                String email = log_email.getText().toString().trim();
                String password = log_password.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    log_email.setError("Email is required!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    log_password.setError("Enter a password!");
                    return;
                }



                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(getContext(), LoggedInActivity.class));
                        } else {
                            Toast.makeText(getActivity(), "Error logging in! (User does not exist)", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        return v;

    }
}

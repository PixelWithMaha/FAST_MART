package com.example.assignment3.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignupFragment extends Fragment {

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        TextInputEditText etEmail = view.findViewById(R.id.et_signup_email);
        TextInputEditText etPassword = view.findViewById(R.id.et_signup_password);
        TextInputEditText etVerifyPassword = view.findViewById(R.id.et_signup_verify_password);
        MaterialButton btnSignup = view.findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String verifyPassword = etVerifyPassword.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(getContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                etEmail.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Please enter a valid email (e.g., name@gmail.com)", Toast.LENGTH_SHORT).show();
                etEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                etPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                etPassword.requestFocus();
                return;
            }

            if (verifyPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please confirm your password", Toast.LENGTH_SHORT).show();
                etVerifyPassword.requestFocus();
                return;
            }

            if (!password.equals(verifyPassword)) {
                Toast.makeText(getContext(), "Passwords do not match. Try again.", Toast.LENGTH_SHORT).show();
                etVerifyPassword.requestFocus();
                return;
            }

            SharedPreferences sharedPref = getActivity().getSharedPreferences("FastMartPrefs", Context.MODE_PRIVATE);
            String existingPassword = sharedPref.getString("pass_"+email, null);

            if (existingPassword != null) {
                Toast.makeText(getContext(), "Account already exists!", Toast.LENGTH_LONG).show();
                return;
            }
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("pass_"+email,password);
            editor.putString("current_user_email", email);
            editor.putBoolean("user.isLoggedIn", true);
            editor.putBoolean("user.isFirstTime",false);
            editor.apply();

            Toast.makeText(getContext(), "Account created! Welcome ", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

            getActivity().finish();

        });

        return view;
    }
}
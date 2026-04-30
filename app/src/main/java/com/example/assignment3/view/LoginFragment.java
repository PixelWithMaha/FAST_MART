package com.example.assignment3.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.example.assignment3.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    private AuthViewModel authViewModel;
    private TextInputEditText etEmail, etPassword;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = v.findViewById(R.id.et_login_email);
        etPassword = v.findViewById(R.id.et_login_password);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        v.findViewById(R.id.btn_login).setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            authViewModel.login(email, password);
        });

        setupObservers();

        return v;
    }

    private void setupObservers() {
        authViewModel.userLiveData.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                SharedPreferences pref = getActivity().getSharedPreferences("FastMartPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("user.uid", user.userId);
                editor.putString("user.name", user.fullName);
                editor.putString("user.type", user.accountType);
                editor.putBoolean("user.isLoggedIn", true);
                editor.putBoolean("user.isFirstTime", false);
                editor.apply();

                Toast.makeText(getContext(), "Login Successful!", Toast.LENGTH_SHORT).show();

                Intent intent;
                if ("Seller".equals(user.accountType)) {
                    intent = new Intent(getActivity(), SellerDashboardActivity.class);

                } else {
                    // intent = new Intent(getActivity(), BuyerDashboardActivity.class);
                    intent = new Intent(getActivity(), MainActivity.class);
                }
                startActivity(intent);
                getActivity().finish();
            }
        });

        authViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Login Failed: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
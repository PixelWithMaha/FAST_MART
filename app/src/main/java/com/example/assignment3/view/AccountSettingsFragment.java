package com.example.assignment3.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assignment3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountSettingsFragment extends Fragment {

    private EditText etName, etAddress, etCountry, etDob, etGender, etPhone;
    private DatabaseReference userRef;
    private String currentUid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);

        // 1. Initialize EditTexts
        etName = view.findViewById(R.id.et_acc_name);
        etAddress = view.findViewById(R.id.et_acc_address);
        etCountry = view.findViewById(R.id.et_acc_country);
        etDob = view.findViewById(R.id.et_acc_dob);
        etGender = view.findViewById(R.id.et_acc_gender);
        etPhone = view.findViewById(R.id.et_acc_phone);

        currentUid = FirebaseAuth.getInstance().getUid();
        if (currentUid != null) {
            userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUid);
            loadUserData();
        }

        // 2. Logout and Delete Logic
        view.findViewById(R.id.btn_logout).setOnClickListener(v -> performFullLogout());

        return view;
    }

    private void loadUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Set data from Firebase to UI
                    etName.setText(snapshot.child("fullName").getValue(String.class));
                    etAddress.setText(snapshot.child("address").getValue(String.class));
                    etCountry.setText(snapshot.child("country").getValue(String.class));
                    etDob.setText(snapshot.child("dob").getValue(String.class));
                    etGender.setText(snapshot.child("gender").getValue(String.class));
                    etPhone.setText(snapshot.child("phoneNumber").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performFullLogout() {
        // Requirement: Remove info from Firebase Realtime DB
        userRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Requirement: Logout from Firebase Auth
                FirebaseAuth.getInstance().signOut();

                // Requirement: Clear Shared Prefs
                SharedPreferences pref = getActivity().getSharedPreferences("FastMartPrefs", Context.MODE_PRIVATE);
                pref.edit().clear().apply();

                // Requirement: Navigate to Sign In screen
                Toast.makeText(getContext(), "Account deleted and logged out", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), AuthActivity.class); // Adjust to your Sign In Activity name
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Error removing data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
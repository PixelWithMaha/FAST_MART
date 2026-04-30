package com.example.assignment3.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.example.assignment3.data.model.User;
import com.example.assignment3.viewmodel.AuthViewModel;

public class ProfileFragment extends Fragment {
    private AuthViewModel authViewModel;
    private EditText etName, etPhone, etCountry, etAddress;
    private Spinner spinnerAccountType;
    private RadioGroup rgGender;
    private CheckBox cbTerms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        etName = v.findViewById(R.id.et_profile_name);
        etPhone = v.findViewById(R.id.et_profile_phone);
        etCountry = v.findViewById(R.id.et_profile_country);
        etAddress = v.findViewById(R.id.et_profile_address);
        cbTerms = v.findViewById(R.id.cb_terms);
        rgGender = v.findViewById(R.id.rg_gender);
        spinnerAccountType = v.findViewById(R.id.spinner_account_type);

        String[] types = {"Buyer", "Seller"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, types);
        spinnerAccountType.setAdapter(adapter);

        v.findViewById(R.id.btn_save_profile).setOnClickListener(view -> {
            if(!cbTerms.isChecked()){
                Toast.makeText(getContext(), "Please accept terms", Toast.LENGTH_SHORT).show();
                return;
            }

            String email = getArguments().getString("email");
            String password = getArguments().getString("password");
            String gender = rgGender.getCheckedRadioButtonId() == R.id.rb_male ? "Male" : "Female";
            String accountType = spinnerAccountType.getSelectedItem().toString();

            User newUser = new User(null, etName.getText().toString(), email, accountType,
                    etPhone.getText().toString(), gender, etCountry.getText().toString(),
                    etAddress.getText().toString());

            authViewModel.signUp(newUser, password);
        });

        authViewModel.userLiveData.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {

                SharedPreferences pref = getActivity().getSharedPreferences("FastMartPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("user.uid", user.userId);
                editor.putString("user.name", user.fullName);
                editor.putString("user.type", user.accountType);
                editor.putBoolean("user.isLoggedIn", true);
                editor.apply();

                Toast.makeText(getContext(), "Welcome " + user.fullName, Toast.LENGTH_SHORT).show();

                Intent intent;
                if ("Seller".equals(user.accountType)) {
                     intent = new Intent(getActivity(), SellerDashboardActivity.class);
                } else {
                    // intent = new Intent(getActivity(), HomeActivity.class);
                    intent = new Intent(getActivity(), MainActivity.class); // Placeholder
                }
                startActivity(intent);
                getActivity().finish();
            }
        });

        authViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if(error != null) Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });

        return v;
    }
}
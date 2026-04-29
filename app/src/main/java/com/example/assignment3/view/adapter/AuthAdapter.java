package com.example.assignment3.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.assignment3.view.LoginFragment;
import com.example.assignment3.view.SignupFragment;

public class AuthAdapter extends FragmentStateAdapter {

    public AuthAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new LoginFragment();
        }
        return new SignupFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
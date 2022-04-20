package com.example.cnpm;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;



public class LoginAdapter extends FragmentStateAdapter {
    Context context;
    int totalTabs;


    public LoginAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context, int totalTabs) {
        super(fragmentManager, lifecycle);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new SignupFragment();
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return totalTabs;
    }
}

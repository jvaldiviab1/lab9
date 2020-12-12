package com.jvaldiviab.lab9.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayoutMediator;
import com.jvaldiviab.lab9.databinding.ActivityMainBinding;
import com.jvaldiviab.lab9.util.UtilsPermissions;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private String[] titles = new String[]{"FLP","LBS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        UtilsPermissions.checkLocationPermission(MainActivity.this);
        init();
    }


    private void init(){
        getSupportActionBar().setElevation(0);
        binding.viewPager.setAdapter(new ViewPagerFragmentAdapter(this));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position)-> tab.setText(titles[position])).attach();
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {
        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FlpFragment();
                case 1:
                    return new LbsFragment();
            }
            return new FlpFragment();
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}
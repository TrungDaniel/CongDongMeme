package com.trungdaniel.congdongmeme.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trungdaniel.congdongmeme.R;

public class HomeFragment extends Fragment {
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private TextView tvBanner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_home);
        bottomNavigationView = view.findViewById(R.id.bottom_home);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}
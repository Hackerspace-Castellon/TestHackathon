package com.example.colibri.ui.friends;

import static java.lang.Math.min;
import static java.lang.Math.round;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.colibri.MainActivity;
import com.example.colibri.Puntuaje;
import com.example.colibri.R;
import com.example.colibri.data.TodayFitnessData;
import com.example.colibri.databinding.FragmentFriendsBinding;
import com.example.colibri.databinding.FragmentHomeBinding;
import com.example.colibri.ui.home.HomeViewModel;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.ncorti.slidetoact.SlideToActView;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.colibri.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.colibri.R;
import com.example.colibri.databinding.FragmentHomeBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.ncorti.slidetoact.SlideToActView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private CircularProgressIndicator progressIndicator;

    private SlideToActView slider_bus;

    boolean slider_reversed = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        progressIndicator = (CircularProgressIndicator) binding.PROGRESSBAR;
        initProgressBar();


        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // register callbacks
        slider_bus = binding.sliderBus;
        slider_bus.setOnSlideCompleteListener((new SliderCallback()));

        return root;
    }

    private class SliderCallback implements SlideToActView.OnSlideCompleteListener {
        @Override
        public void onSlideComplete(SlideToActView view) {
            if (!slider_reversed){
                slider_reversed = true;
                slider_bus.setReversed(true);
                slider_bus.setOuterColor(ContextCompat.getColor(view.getContext(), R.color.green));
                slider_bus.setCompleted(false, false);


            } else {
                slider_reversed = false;
                slider_bus.setReversed(false);
                slider_bus.setOuterColor(ContextCompat.getColor(view.getContext(), R.color.red));
                slider_bus.setCompleted(false, false);
            }
        }
    }


    private void initProgressBar(){
        // init progress bar
        progressIndicator.setIndicatorSize(950);
        progressIndicator.setTrackThickness(50);
        progressIndicator.setProgress(30);
        progressIndicator.setTrackColor(Color.GRAY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
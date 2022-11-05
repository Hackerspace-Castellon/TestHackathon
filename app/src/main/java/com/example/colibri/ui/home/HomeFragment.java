package com.example.colibri.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView infoText;

    boolean slider_reversed = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // init big circle
        progressIndicator = (CircularProgressIndicator) binding.PROGRESSBAR;
        initProgressBar();

        // init textview

        infoText = binding.textViewP;

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // register callbacks
        slider_bus = binding.sliderBus;
        slider_bus.setOnSlideCompleteListener((new SliderCallback()));

        return root;
    }

    private class SliderCallback implements SlideToActView.OnSlideCompleteListener {
        Context ctx;

        private void setSlider(){
            slider_bus.setReversed(true);
            slider_bus.setOuterColor(ContextCompat.getColor(ctx, R.color.green));
            slider_bus.setCompleted(false, false);
            slider_bus.setSliderIcon(R.drawable.esperandoalbus_02_esperando);
            infoText.setText("Desliza para bajarte del bus");
        }

        private void resetSlider(){
            slider_bus.setReversed(false);
            slider_bus.setOuterColor(ContextCompat.getColor(ctx, R.color.red));
            slider_bus.setCompleted(false, false);
            slider_bus.setSliderIcon(R.drawable.ic_esperandoalbus_desactivado);
            infoText.setText("Desliza para subirte en el bus");
        }

        @Override
        public void onSlideComplete(SlideToActView view) {
            ctx = view.getContext();

            if (!slider_reversed){
                slider_reversed = true;

                // se ha subido
                setSlider();

                // no hay conexion
/*                Toast.makeText(view.getContext(),"No estas cerca de una parada de bus",Toast.LENGTH_SHORT ).show();
                // volver atras despues de un rato
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetSlider();
                    }
                }, 2000);*/


            } else {
                slider_reversed = false;

                // sea ha bajado
                resetSlider();

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
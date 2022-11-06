package com.example.colibri.ui.home;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.colibri.LoginActivity;
import com.example.colibri.MainActivity;
import com.example.colibri.Puntuaje;
import com.example.colibri.R;
import com.example.colibri.data.TodayFitnessData;
import com.example.colibri.databinding.FragmentHomeBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.ncorti.slidetoact.SlideToActView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private CircularProgressIndicator progressIndicator;

    private SlideToActView slider_bus;
    private TextView infoText;

    boolean slider_reversed = false;

    MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();


        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // add onClick to ptos

        binding.puntuation.setOnClickListener(new PtoListener());

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.puntuation.setText(String.valueOf(TodayFitnessData.pasos));
                binding.PROGRESSBAR.setProgress(min(round(TodayFitnessData.pasos / 100), 100));
            }
        }, 300);



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

    void navigateToPuntajeActivity(){
        Intent intent = new Intent(getContext(), Puntuaje.class);
        intent.putExtra("pasos", activity.getPasos());
        startActivity(intent);
    }

    private class PtoListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            navigateToPuntajeActivity();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
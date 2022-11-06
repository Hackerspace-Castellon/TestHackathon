package com.example.colibri.ui.mascota;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.colibri.R;
import com.example.colibri.databinding.FragmentMascotaBinding;

public class MacotaFragment extends Fragment {

    private static int puntos_totales = 28890;
    private static int hp = 5;

    private FragmentMascotaBinding binding;

    private ProgressBar healthBar;
    private TextView userScore;
    private ImageButton nectarBtn;
    private ImageButton shopBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MascotaViewModel notificationsViewModel =
                new ViewModelProvider(this).get(MascotaViewModel.class);

        binding = FragmentMascotaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        healthBar = binding.progressBar;
        userScore = binding.puntuation;
        nectarBtn = binding.feedBtn;
        shopBtn = binding.shopBtn;

        updatehp();
        nectarBtn.setOnClickListener(new FoodListener());
        shopBtn.setOnClickListener(new ShopListener());


        return root;
    }

    private void updatehp(){
        healthBar.setProgress(hp);
        userScore.setText(Integer.toString(puntos_totales));

        // update gif
        int gif;

        if (hp < 30){
            gif = R.raw.colibridelgado;
        } else if (hp < 90){
            gif = R.raw.colibri;
        } else {
            gif = R.raw.colibrigordo;
        }

        Glide.with(this).asGif().load(gif).into(binding.imageView);

    }


    private class FoodListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (puntos_totales > 1000 && hp < 100){
                puntos_totales -= 1000;
                hp += 5;
                updatehp();
            }
        }
    }

    private class ShopListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new secondFragment().show(
                    getChildFragmentManager(), "TAG");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
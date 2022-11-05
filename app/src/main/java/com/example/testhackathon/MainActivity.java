package com.example.testhackathon;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.testhackathon.databinding.ActivityMainBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.ncorti.slidetoact.SlideToActView;

public class MainActivity extends AppCompatActivity{

    private SplashScreen splashScreen;

    private ActivityMainBinding binding;

    private SlideToActView slider_bus;

    boolean slider_reversed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        checkLogin();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNavControls();

        // register callbacks
        slider_bus = findViewById(R.id.slider_bus);
        slider_bus.setOnSlideCompleteListener((new SliderCallback()));


    }

    private void checkLogin(){


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



    private void createNavControls(){

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_bicitravel, R.id.navigation_mascota).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); quitar barra de arriba
        NavigationUI.setupWithNavController(binding.navView, navController);


    }
}
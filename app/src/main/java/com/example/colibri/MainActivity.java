package com.example.colibri;

import android.os.Bundle;
import android.util.Log;

import com.example.colibri.data.TodayFitnessData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.colibri.databinding.ActivityMainBinding;
import com.ncorti.slidetoact.SlideToActView;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;
    private static int pasos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        calcularPasos();
        setTheme(R.style.Theme_TestHackathon);
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNavControls();

        // el mapa el mapa donde esta el mapa
    }

    private void calcularPasos(){
        TodayFitnessData calculator = new TodayFitnessData(this);
        calculator.getData();
    }

    public int getPasos(){
        return pasos;
    }




    private void createNavControls(){

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home,R.id.navigation_friends ,R.id.navigation_bicitravel, R.id.navigation_mascota, R.id.navigation_perfil).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); quitar barra de arriba
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
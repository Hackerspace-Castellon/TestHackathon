package com.example.colibri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.colibri.data.TodayFitnessData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;

import org.w3c.dom.Text;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

public class Puntuaje extends AppCompatActivity {

    FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .build();

    TextView puntapie;
    TextView puntabus;
    TextView puntabici;
    TextView puntatotal;

    int puntuacionPasos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        puntuacionPasos = TodayFitnessData.pasos;


        setTheme(R.style.Theme_TestHackathon);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaje);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);


        puntapie = findViewById(R.id.puntapie);
        puntabus = findViewById(R.id.puntabus);
        puntabici = findViewById(R.id.puntabici);
        puntatotal = findViewById(R.id.puntatotal);
        Log.i("TAG",String.valueOf(acct));

        puntapie.setText(String.valueOf(puntuacionPasos));
        puntatotal.setText(String.valueOf(puntuacionPasos));

    }
}
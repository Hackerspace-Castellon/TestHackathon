package com.example.colibri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    int puntuacionPasos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TestHackathon);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaje);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        puntapie = findViewById(R.id.puntapie);
        puntabus = findViewById(R.id.puntabus);
        puntabici = findViewById(R.id.puntabici);
        puntatotal = findViewById(R.id.puntatotal);
        Log.i("TAG",String.valueOf(acct));
        if(acct!=null) {
            //if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
                ZonedDateTime endTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
                ZonedDateTime startTime = endTime.minusDays(1);
                GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);
                if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                    GoogleSignIn.requestPermissions(
                            this, // your activity
                            1, // e.g. 1
                            account,
                            fitnessOptions);
                }
                if (GoogleSignIn.hasPermissions(account, fitnessOptions)) {

                    DataReadRequest readRequest = new DataReadRequest.Builder()
                            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                            .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                            .bucketByTime(1, TimeUnit.DAYS)
                            .build();
                    account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);
                    Log.i("TAG","entre account y fitness");
                    Log.i("TAG",String.valueOf(account));
                    Fitness.getHistoryClient(this, account)
                            .readData(readRequest)
                            .addOnSuccessListener(response -> {
                                // Use response data here
                                for (Bucket bucket : response.getBuckets()) {
                                    for (DataSet dataSet2 : bucket.getDataSets()) {
                                        dumpDataSet(dataSet2);
                                    }
                                }

                            })
                            .addOnFailureListener(e -> Log.d("TAG", "OnFailure()", e));
                }
            //}

        }


    }

    private void dumpDataSet(DataSet dataSet) {
        Log.i("TAG", "Data returned for Data type: ${dataSet.dataType.name}");
        for (DataPoint dp : dataSet.getDataPoints()) {
//            Log.i("TAG","Data point:");
//            Log.i("TAG","\tType: " + dp.getDataType().getName());
//            Log.i("TAG","\tStart: " + dp.getStartTime(TimeUnit.SECONDS));
//            Log.i("TAG","\tEnd: " + dp.getEndTime(TimeUnit.SECONDS));
            for (Field field : dp.getDataType().getFields()) {
                if(field.getName().equals("steps")){
                    puntuacionPasos += Integer.parseInt(String.valueOf(dp.getValue(field)));
                    Log.i("TAG","\tStart: " + dp.getStartTime(TimeUnit.SECONDS));
                    Log.i("DATO","\tField: " + field.getName().toString() + " Value: " + dp.getValue(field));

                }
                else{
                    Log.i("DATO","\tField: " + field.getName().toString() + " Value: " + dp.getValue(field));
                }
            }
            puntapie.setText(String.valueOf(puntuacionPasos));
            puntatotal.setText(String.valueOf(puntuacionPasos));


//            puntatotal.setText(String.valueOf(Integer.parseInt((String) puntapie.getText()) + Integer.parseInt((String) puntabici.getText())));
        }
    }

}
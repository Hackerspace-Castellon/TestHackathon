package com.example.colibri.data;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.colibri.R;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

public class TodayFitnessData {

    FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .build();

    private Context ctx;
    private Activity activity;
    public static int pasos = 0;
    private int bicicleta = 0;
    private int autobus = 0;

    public TodayFitnessData(Activity act){
        this.ctx = act.getApplicationContext();
        this.activity = act;
    }

    public void getData(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(ctx);

        Log.i("TAG",String.valueOf(acct));
        if(acct!=null) {
            //if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            ZonedDateTime endTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
            ZonedDateTime startTime = endTime.minusDays(1);
            GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(ctx, fitnessOptions);
            if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
                GoogleSignIn.requestPermissions(
                        activity, // your activity
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
                account = GoogleSignIn.getAccountForExtension(ctx, fitnessOptions);
                Log.i("TAG","entre account y fitness");
                Log.i("TAG",String.valueOf(account));
                Fitness.getHistoryClient(ctx, account)
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
        int puntuacionPasos = 0;
        Log.i("TAG", "Data returned for Data type: ${dataSet.dataType.name}");
        for (DataPoint dp : dataSet.getDataPoints()) {
//            Log.i("TAG","Data point:");
//            Log.i("TAG","\tType: " + dp.getDataType().getName());
//            Log.i("TAG","\tStart: " + dp.getStartTime(TimeUnit.SECONDS));
//            Log.i("TAG","\tEnd: " + dp.getEndTime(TimeUnit.SECONDS));
            for (Field field : dp.getDataType().getFields()) {
                if (field.getName().equals("steps")) {
                    puntuacionPasos += Integer.parseInt(String.valueOf(dp.getValue(field)));
                    Log.i("TAG", "\tStart: " + dp.getStartTime(TimeUnit.SECONDS));
                    Log.i("DATO", "\tField: " + field.getName().toString() + " Value: " + dp.getValue(field));

                } else {
                    Log.i("DATO", "\tField: " + field.getName().toString() + " Value: " + dp.getValue(field));
                }
            }
            pasos = puntuacionPasos;


//            puntatotal.setText(String.valueOf(Integer.parseInt((String) puntapie.getText()) + Integer.parseInt((String) puntabici.getText())));
        }
    }

    public int getPasos() {
        return pasos;
    }

    public void setPasos(int pasos) {
        this.pasos = pasos;
    }

    public int getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(int bicicleta) {
        this.bicicleta = bicicleta;
    }

    public int getAutobus() {
        return autobus;
    }

    public void setAutobus(int autobus) {
        this.autobus = autobus;
    }



}

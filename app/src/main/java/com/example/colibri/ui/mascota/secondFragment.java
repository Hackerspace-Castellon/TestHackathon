package com.example.colibri.ui.mascota;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.colibri.R;


public class secondFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext()).setView(R.layout.fragment_second)
                .setPositiveButton(getString(R.string.cerrar), (dialog, which) -> {} )
                .create();
    }

    public static String TAG = "PurchaseConfirmationDialog";
}
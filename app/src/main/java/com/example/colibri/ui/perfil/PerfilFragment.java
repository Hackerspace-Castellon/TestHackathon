package com.example.colibri.ui.perfil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.colibri.LoginActivity;
import com.example.colibri.MainActivity;
import com.example.colibri.R;
import com.example.colibri.databinding.FragmentNotificationsBinding;
import com.example.colibri.databinding.FragmentPerfilBinding;
import com.example.colibri.ui.notifications.NotificationsViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class PerfilFragment extends Fragment {
    Context ctx;

    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    Button sign_out;

    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        ctx = this.getContext();

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(ctx, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(ctx);

        ImageView iv = binding.perfil;
        if(acct.getPhotoUrl()!=null) {
            try {
                URL imageUrl = new URL(acct.getPhotoUrl().toString());
                Picasso.get()
                        .load(String.valueOf(imageUrl))
                        .into(iv);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else{
            iv.setImageResource(R.drawable.colibrimascotaperfil);
        }

        TextView tvnombre = binding.nombre;
        tvnombre.setText(acct.getDisplayName());

        TextView tvemail = binding.email;
        tvemail.setText(acct.getEmail());


        sign_out = binding.cerrarsesion;
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //finish();
                startActivity(new Intent(ctx, LoginActivity.class));
            }
        });
    }
}

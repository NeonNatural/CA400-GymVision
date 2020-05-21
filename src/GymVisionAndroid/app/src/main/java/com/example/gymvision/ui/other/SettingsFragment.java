package com.example.gymvision.ui.other;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.gymvision.R;
import com.example.gymvision.ui.welcome.ResetPassActivity;

public class SettingsFragment extends Fragment {

    public String SECONDS = "30";
    EditText text;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_settings, container, false);

        layout.findViewById(R.id.enableStorage).setOnClickListener(
                view -> {
                    if(ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);


                    }
                    else{
                        Toast.makeText(getActivity(), "Storage permission already granted", Toast.LENGTH_SHORT).show();
                    }

                }
        );
        layout.findViewById(R.id.enableCamera).setOnClickListener(
                view -> {
                    if(ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.CAMERA},
                                1);


                    }
                    else{
                        Toast.makeText(getActivity(), "Camera permission already granted", Toast.LENGTH_SHORT).show();
                    }

                }
        );



        layout.findViewById(R.id.resetPassSettings).setOnClickListener(view ->
    {
            Intent resetactivity = new Intent(getContext(), ResetPassActivity.class);
            startActivity(resetactivity);
        });

        // Inflate the layout for this fragment
        return layout;
    }

}

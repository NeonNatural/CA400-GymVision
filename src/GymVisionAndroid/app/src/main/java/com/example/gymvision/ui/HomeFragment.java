package com.example.gymvision.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.gymvision.R;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    VideoView videos;
    ListView list;
    ArrayList<String> vlist;
    ArrayAdapter adapter;
    LottieAnimationView sample;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        if(ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA},
                    1);

        }
        videos =layout.findViewById(R.id.videoview);
        list =layout.findViewById(R.id.lvideo);
        sample = layout.findViewById(R.id.preview);
        vlist = new ArrayList<>();
        vlist.add("How to perform a squat");


        adapter= new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, vlist);

        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    videos.setVisibility(View.VISIBLE);
                    videos.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.appdemo));

                    break;
                default:
                    break;
            }
            videos.setMediaController(new MediaController(getContext()));
            videos.requestFocus();
            videos.start();
        });


        return layout;
    }

}

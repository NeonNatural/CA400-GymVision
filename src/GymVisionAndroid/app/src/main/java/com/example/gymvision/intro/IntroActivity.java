package com.example.gymvision.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.gymvision.R;
import com.example.gymvision.ui.welcome.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager pager;
    ViewAdapter adapter;
    TabLayout tabs;
    Button nextbutton;
    int pos = 0 ;
    LottieAnimationView gettingStarted;
    Animation animation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (restorePrefData()) { //checking prefs data

            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class );
            startActivity(mainActivity);
            finish();

        }

        setContentView(R.layout.activity_intro);

        nextbutton = findViewById(R.id.btn_next);
        gettingStarted = findViewById(R.id.btn_get_started);
       // gettingStarted = findViewById(R.id.btn_get_started);
        tabs = findViewById(R.id.tab_indicator);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);


        final List<ScreenItem> list = new ArrayList<>();
        list.add(new ScreenItem("Welcome to GymVision","The app is designed to aid you in correcting your form while performing gym exercises in order to insure your health and safety.", R.drawable.gymvision));
        list.add(new ScreenItem("How to evaluate your form","In order to evaluate your form, navigate over to the exercises tab using the pullout menu and select one of the exercises.", R.drawable.exercisescreen));
        list.add(new ScreenItem("Exercise screen","Here you will need to place your mobile device on a steady surface and then perform the exercise 2 meters away from the device.", R.drawable.camerascreen));
        list.add(new ScreenItem("Summary screen","Once you have completed one repetition of the exercise, press the stop button on the timer or simply wait for it to complete. You will then be taken to the summary screen to view your performance.", R.drawable.summaryscreen));
        list.add(new ScreenItem("Sample videos","On the home screen, you can view videos to show you how to perform the exercises in front of the camera and receive your score.", R.drawable.homescreen));
        list.add(new ScreenItem("Get Started!","Press the button below to create an account and get started evaluating your form.", R.drawable.ic_gym));




        pager = findViewById(R.id.pager);
        adapter = new ViewAdapter(this,list);
        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);

        nextbutton.setOnClickListener(v -> {
            pos = pager.getCurrentItem();
            if (pos < list.size()) {
                pos++;
                pager.setCurrentItem(pos);

            }

            if (pos == list.size()-1) {
                nextbutton.setVisibility(View.INVISIBLE);
                gettingStarted.setVisibility(View.VISIBLE);

                tabs.setVisibility(View.INVISIBLE);

                gettingStarted.setAnimation(animation);

            }

        });

        tabs.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == list.size()-1) {

                    nextbutton.setVisibility(View.INVISIBLE);
                    gettingStarted.setVisibility(View.VISIBLE);
                    tabs.setVisibility(View.INVISIBLE);
                    gettingStarted.setAnimation(animation);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        gettingStarted.setOnClickListener(v -> {
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            savePrefsData();
            finish();


        });

    }


    //using pref data to keep track of previous app activity
    public boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        return pref.getBoolean("opened",false);

    }
    public void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("opened",true);
        editor.apply();


    }

    public class ViewAdapter extends PagerAdapter {

        private Context context;
        private List<ScreenItem> screen;

        ImageView slide;
        TextView title, desc;
        View layout;


        ViewAdapter(Context context, List<ScreenItem> screen) {
            this.context = context;
            this.screen = screen;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflate.inflate(R.layout.intro,null);

            slide = layout.findViewById(R.id.intro_img);
            title = layout.findViewById(R.id.intro_title);
            desc = layout.findViewById(R.id.intro_description);

            title.setText(screen.get(position).getScreentitle());
            desc.setText(screen.get(position).getDesc());
            slide.setImageResource(screen.get(position).getImage());

            container.addView(layout);

            return layout;


        }

        @Override
        public int getCount() {
            return screen.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) { return view == o; }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) { container.removeView((View)object); }
    }




}

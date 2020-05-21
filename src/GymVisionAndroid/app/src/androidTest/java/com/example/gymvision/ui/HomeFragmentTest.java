package com.example.gymvision.ui;

import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.test.rule.ActivityTestRule;

import com.example.gymvision.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class HomeFragmentTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);




    private  HomeActivity activity = null;



    @Before
    public void setUp() throws Exception {

        activity = activityTestRule.getActivity();



    }

    @Test
    public void testLaunch(){
            FrameLayout rLayout = activity.findViewById(R.id.nav_host_fragment);
            assertNotNull(rLayout);
            Fragment fragment = new HomeFragment();
            //activity.getFragmentManager().beginTransaction().add(rLayout.getId(),new HomeFragmentTest()).commitAllowingStateLoss();
            activity.getSupportFragmentManager().beginTransaction().add(rLayout.getId(), fragment).commitAllowingStateLoss();

            getInstrumentation().waitForIdleSync();

            View view = fragment.getView().findViewById(R.id.videoview);
            View listViewAdapter = fragment.getView().findViewById(R.id.lvideo);
            assertNotNull(view);
            assertNotNull(listViewAdapter);
    }

/*
   @Test
   public void videoSelectionTest(){
       FrameLayout rLayout = activity.findViewById(R.id.nav_host_fragment);
       assertNotNull(rLayout);
       Fragment fragment = new HomeFragment();
       //activity.getFragmentManager().beginTransaction().add(rLayout.getId(),new HomeFragmentTest()).commitAllowingStateLoss();
       activity.getSupportFragmentManager().beginTransaction().add(rLayout.getId(), fragment).commitAllowingStateLoss();
       getInstrumentation().waitForIdleSync();

       onView(withId(R.id.lvideo))
               .perform(actionOnItemAtPosition(0, click()));

   }*/



    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}
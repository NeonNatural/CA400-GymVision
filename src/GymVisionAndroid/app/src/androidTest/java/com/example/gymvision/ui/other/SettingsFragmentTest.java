package com.example.gymvision.ui.other;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.test.rule.ActivityTestRule;

import com.example.gymvision.R;
import com.example.gymvision.ui.HomeActivity;
import com.example.gymvision.ui.welcome.ResetPassActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class SettingsFragmentTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    private HomeActivity activity = null;

    Instrumentation.ActivityMonitor resetPassMonitor = getInstrumentation().addMonitor(ResetPassActivity.class.getName(), null, false);



    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();

    }

    @Test
    public void testLaunch(){
        FrameLayout rLayout = activity.findViewById(R.id.nav_host_fragment);
        assertNotNull(rLayout);
        Fragment fragment = new SettingsFragment();

        activity.getSupportFragmentManager().beginTransaction().add(rLayout.getId(), fragment).commitAllowingStateLoss();

        getInstrumentation().waitForIdleSync();

        View enableCameraPerms = fragment.getView().findViewById(R.id.enableCamera);
        View textViewPerms = fragment.getView().findViewById(R.id.textView);
        View enableStoragePerms = fragment.getView().findViewById(R.id.enableStorage);
        View textView = fragment.getView().findViewById(R.id.textView2);
        View resetPassSettings = fragment.getView().findViewById(R.id.resetPassSettings);


        assertNotNull(enableCameraPerms);
        assertNotNull(textViewPerms);
        assertNotNull(textView);
        assertNotNull(resetPassSettings);
        assertNotNull(enableCameraPerms);
        assertNotNull(enableStoragePerms);
    }

    @Test
    public void testSettingsButtons(){

        FrameLayout rLayout = activity.findViewById(R.id.nav_host_fragment);
        assertNotNull(rLayout);
        Fragment fragment = new SettingsFragment();
        activity.getSupportFragmentManager().beginTransaction().add(rLayout.getId(), fragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();

        onView(withId(R.id.enableCamera)).perform(click());
        onView(withId(R.id.enableStorage)).perform(click());


    }


    @Test
    public void testResetButtonInSettings(){

        FrameLayout rLayout = activity.findViewById(R.id.nav_host_fragment);
        assertNotNull(rLayout);
        Fragment fragment = new SettingsFragment();
        activity.getSupportFragmentManager().beginTransaction().add(rLayout.getId(), fragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();

        onView(withId(R.id.resetPassSettings)).perform(click());

        Activity resetActivity = getInstrumentation().waitForMonitorWithTimeout(resetPassMonitor, 1000);

        assertNotNull(resetActivity);
        resetActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}
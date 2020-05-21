package com.example.gymvision.ui.ExerciseMangement;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.test.rule.ActivityTestRule;

import com.example.gymvision.R;
import com.example.gymvision.camera.InferenceActivity;
import com.example.gymvision.ui.HomeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;


public class exerFragmentTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    private Instrumentation.ActivityMonitor cameraActivityMonitor = getInstrumentation().addMonitor(InferenceActivity.class.getName(), null, false);

    private HomeActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();

    }

    @Test
    public void testLaunch(){
        FrameLayout rLayout = activity.findViewById(R.id.nav_host_fragment);
        assertNotNull(rLayout);
        Fragment fragment = new exerFragment();

        activity.getSupportFragmentManager().beginTransaction().add(rLayout.getId(), fragment).commitAllowingStateLoss();

        getInstrumentation().waitForIdleSync();

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testExerciseSelection(){

        FrameLayout rLayout = activity.findViewById(R.id.nav_host_fragment);
        assertNotNull(rLayout);
        Fragment fragment = new exerFragment();
        activity.getSupportFragmentManager().beginTransaction().add(rLayout.getId(), fragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();

        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        Activity cameraActivity = getInstrumentation().waitForMonitorWithTimeout(cameraActivityMonitor, 1);
        assertNotNull(cameraActivity);
        cameraActivity.finish();
    }


    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}
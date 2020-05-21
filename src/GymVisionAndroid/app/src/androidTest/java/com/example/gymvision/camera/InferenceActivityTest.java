package com.example.gymvision.camera;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.gymvision.R;
import com.example.gymvision.ui.ExerciseMangement.SummaryActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class InferenceActivityTest {
    @Rule
    public ActivityTestRule<InferenceActivity> activityTestRule = new ActivityTestRule<>(InferenceActivity.class);

    private InferenceActivity activity = null;

    Instrumentation.ActivityMonitor summaryScreenMonitor = getInstrumentation().addMonitor(SummaryActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View title = activity.findViewById(R.id.title);
        View exerCheck1 = activity.findViewById(R.id.exer_check_1);
        View exerCheck2 = activity.findViewById(R.id.exer_check_2);
        View exerCheck3 = activity.findViewById(R.id.exer_check_3);
        View timer = activity.findViewById(R.id.timer);
        View overlaySwitch = activity.findViewById(R.id.overlay_switch);
        View camera = activity.findViewById(R.id.camera);
        assertNotNull(title);
        assertNotNull(exerCheck1);
        assertNotNull(exerCheck2);
        assertNotNull(exerCheck3);
        assertNotNull(timer);
        assertNotNull(overlaySwitch);
        assertNotNull(camera);


    }
    @Test
    public void timerPresTest(){

        onView(withId(R.id.timer)).perform(click());
        onView(withId(R.id.timer)).perform(click());

        Activity summaryActivity = getInstrumentation().waitForMonitorWithTimeout(summaryScreenMonitor, 1);

        assertNotNull(summaryActivity);
        summaryActivity.finish();
    }



    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}
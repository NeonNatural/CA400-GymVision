package com.example.gymvision.ui.ExerciseMangement;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.gymvision.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SummaryActivityTest {
    @Rule
    public ActivityTestRule<SummaryActivity> activityTestRule = new ActivityTestRule<>(SummaryActivity.class);

    private SummaryActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }
    @Test
    public void testLaunch(){
        View sequenceLayout = activity.findViewById(R.id.sequenceLayout);
        View exerciseName = activity.findViewById(R.id.exercisename);
        View check1 = activity.findViewById(R.id.check1);
        View check2 = activity.findViewById(R.id.check2);
        View  failed1 = activity.findViewById(R.id.failed1);
        View success1 = activity.findViewById(R.id.success1);

        assertNotNull(sequenceLayout);
        assertNotNull(exerciseName);
        assertNotNull(check1);
        assertNotNull(success1);
        assertNotNull(check1);
        assertNotNull(check2);
        assertNotNull(failed1);
    }



    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}
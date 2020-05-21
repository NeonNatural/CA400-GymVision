package com.example.gymvision.ui.welcome;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import com.example.gymvision.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ResetPassActivityTest {

    @Rule
    public ActivityTestRule<ResetPassActivity> activityTestRule = new ActivityTestRule<>(ResetPassActivity.class);

    private  ResetPassActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }
    @Test
    public void testLaunch(){
        View resetPassButton  = activity.findViewById(R.id.resetPassButton);
        View resetPassEmail  = activity.findViewById(R.id.resetPassEmail);
        assertNotNull(resetPassButton);
        assertNotNull(resetPassEmail);
    }

    @Test
    public void testingResettingEmail(){
        try{
            Espresso.onView(withId(R.id.resetPassEmail)).perform(typeText("fawaz.alsafadi2@mail.dcu.ie"));//email

            Espresso.closeSoftKeyboard();
            onView(withId(R.id.resetPassButton)).perform(click());
        }
        catch (NoMatchingViewException e){
            //NoMatchingViewException
        }
    }



    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}
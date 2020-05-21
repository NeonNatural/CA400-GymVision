package com.example.gymvision.ui.welcome;

import android.app.Activity;
import android.app.Instrumentation;
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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    private  RegisterActivity activity = null;

    @Before
    public void setUp() throws Exception {

        activity = activityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View registerButton  = activity.findViewById(R.id.regiButton);
        assertNotNull(registerButton);
    }
    @Test
    public void testingRegisteringUser(){
        try{
            Espresso.onView(withId(R.id.regiName)).perform(typeText("Fawaz Alsafadi2"));//email
            Espresso.onView(withId(R.id.regiMail)).perform(typeText("fawazalsafadi2@gmail.com"));//password
            Espresso.onView(withId(R.id.regiPass)).perform(typeText("123456"));//password
            Espresso.onView(withId(R.id.regiPass2)).perform(typeText("123456"));//password
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.regiButton)).perform(click());
        }
        catch (NoMatchingViewException e){
            //NoMatchingViewException
        }
        }

    @Test
    public void testingAlreadyHaveAnAccountButton(){
        Instrumentation.ActivityMonitor loginMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
        try{
            onView(withId(R.id.lgnInstead)).perform(click());
            Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(loginMonitor, 2000);
            assertNotNull(loginActivity);
            loginActivity.finish();
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
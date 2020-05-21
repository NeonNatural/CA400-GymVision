package com.example.gymvision.ui.welcome;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import com.example.gymvision.R;
import com.example.gymvision.ui.HomeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;



public class LoginActivityTest {
     View loginEmail, loginPass, loginButton, registerButton, forgotPassButton;

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity activity = null;








    @Before
    public void setUp() throws Exception {


        activity = activityTestRule.getActivity();

    }

    @Test
    public void testLaunch(){
         loginEmail = activity.findViewById(R.id.resetPassEmail);
         loginPass = activity.findViewById(R.id.login_password);
         loginButton = activity.findViewById(R.id.loginBtn);
         registerButton = activity.findViewById(R.id.resetPassButton);
         forgotPassButton = activity.findViewById(R.id.forgotPass);
         assertNotNull(loginEmail);
         assertNotNull(loginPass);
         assertNotNull(loginButton);
         assertNotNull(registerButton);
         assertNotNull(forgotPassButton);
    }

    @Test
    public void testingRegisterButton(){
        Instrumentation.ActivityMonitor registerMonitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(), null, false);
        try{
            onView(withId(R.id.resetPassButton)).perform(click());
            Activity registerActivity = getInstrumentation().waitForMonitorWithTimeout(registerMonitor, 1);
            assertNotNull(registerActivity);
            registerActivity.finish();
        }
        catch (NoMatchingViewException e){
            //NoMatchingViewException
        }


    }

    @Test
    public void testingForgotPassButton(){

        Instrumentation.ActivityMonitor forgotPassMonitor = getInstrumentation().addMonitor(ResetPassActivity.class.getName(), null, false);

        try{
            onView(withId(R.id.forgotPass)).perform(click());
            Activity registerActivity = getInstrumentation().waitForMonitorWithTimeout(forgotPassMonitor, 1);
            assertNotNull(registerActivity);
            registerActivity.finish();

        }
        catch (NoMatchingViewException e){
            //NoMatchingViewExcpetion


        }




    }

    @Test
    public void testingLaunchOfAppAfterLogin(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(HomeActivity.class.getName(), null, false);


        try {
            // Type email and password
            loginEmail = activity.findViewById(R.id.resetPassEmail);
            loginPass = activity.findViewById(R.id.login_password);
            assertNotNull(loginEmail);
            assertNotNull(loginPass);

            Espresso.onView(withId(R.id.resetPassEmail)).perform(typeText("123@gmail.com"));//email
            Espresso.onView(withId(R.id.login_password)).perform(typeText("123456"));//password
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.loginBtn)).perform(click());

            Activity homeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

            assertNotNull(homeActivity);
            homeActivity.finish();
        } catch (NoMatchingViewException e) {
            //NoMatchingViewExcpetion
        }


    }


    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}
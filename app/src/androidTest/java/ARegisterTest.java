import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import trempApplication.R;
import trempApplication.app.RegisterActivity;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//import static androidx.test.espresso.intent.Intents.intended;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ARegisterTest {

    @Before
    public void setUp() {
    }
    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule = new ActivityScenarioRule<RegisterActivity>(RegisterActivity.class);
    @Test
    public void register() {
        // Simulate registration process
        // Provide necessary details for registration
        String ID = "123456";
        String username = "testuser";
        String password = "test";
        String validatePassword = "test";
        String phoneNumber = "052111111";
        String faculty = "Faculty";

        // Fill in the registration form fields
        onView(withId(R.id.et_ID_reg)).perform(typeText(ID));
        onView(withId(R.id.et_username)).perform(typeText(username));
        onView(withId(R.id.et_password_reg)).perform(typeText(password));
        onView(withId(R.id.et_validation_password)).perform(typeText(validatePassword));
        onView(withId(R.id.et_phone_number_reg)).perform(typeText(phoneNumber));
        onView(withId(R.id.et_faculty)).perform(typeText(faculty));

        // Perform registration by clicking the register button
        onView(withId(R.id.register_button)).perform(click());
    }
}

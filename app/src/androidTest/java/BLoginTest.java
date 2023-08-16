import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import trempApplication.R;
import trempApplication.app.LoginActivity;

public class BLoginTest {

    @Before
    public void setUp() {
    }
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
    @Test
    public void login() {
        // Simulate registration process
        // Provide necessary details for registration
        String ID = "207375700";
        String password = "1234";

        // Fill in the registration form fields
        onView(withId(R.id.et_Id)).perform(typeText(ID));
        onView(withId(R.id.et_password)).perform(typeText(password));

        // Perform registration by clicking the register button
        onView(withId(R.id.btnToLogin)).perform(click());
    }
    @Test
    public void loginWithWrongUser() {
        // Simulate registration process
        // Provide necessary details for registration
        String ID = "1111";
        String password = "1111";

        // Fill in the registration form fields
        onView(withId(R.id.et_Id)).perform(typeText(ID));
        onView(withId(R.id.et_password)).perform(typeText(password));

        // Perform registration by clicking the register button
        onView(withId(R.id.btnToLogin)).perform(click());
    }
}

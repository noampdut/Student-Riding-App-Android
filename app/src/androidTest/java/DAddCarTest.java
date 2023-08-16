import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import trempApplication.R;
import trempApplication.app.AddNewCarActivity;

public class DAddCarTest {

    @Before
    public void setUp() {
    }

    @Rule
    public ActivityScenarioRule<AddNewCarActivity> activityRule = new ActivityScenarioRule<AddNewCarActivity>(AddNewCarActivity.class);

    @Test
    public void addCar() {
        // Simulate registration process
        // Provide necessary details for registration
        String carNumber = "2825698";
        String carName = "my car";
        String carType = "mazda";
        String carColor = "white";


        // Fill in the registration form fields
        onView(withId(R.id.et_carNumber_new)).perform(typeText(carNumber));
        onView(withId(R.id.et_nickname_new)).perform(typeText(carName));
        onView(withId(R.id.et_type_new)).perform(typeText(carType));
        onView(withId(R.id.et_color_new)).perform(typeText(carColor));

        // Perform registration by clicking the register button
        onView(withId(R.id.add_new_car)).perform(click());
    }
}

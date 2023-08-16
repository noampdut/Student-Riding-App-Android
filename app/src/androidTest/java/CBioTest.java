import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import trempApplication.R;
import trempApplication.app.BioActivity;

public class CBioTest {
    @Before
    public void setUp() {

    }
    @Rule
    public ActivityScenarioRule<BioActivity> activityRule = new ActivityScenarioRule<BioActivity>(BioActivity.class);

    @Test
    public void createBio() {
        onView(withId(R.id.checkbox_sport)).perform(click());
        onView(withId(R.id.et_bio)).perform(typeText("I like dance"));
        onView(withId(R.id.bio_button)).perform(click());
    }
}

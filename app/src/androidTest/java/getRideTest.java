import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import trempApplication.R;
import trempApplication.app.GetRideActivity;
import trempApplication.app.OfferRideActivity;

public class getRideTest {

    //private Context context;

    @Before
    public void setUp() {
        //context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
    @Rule
    public ActivityScenarioRule<GetRideActivity> activityRule = new ActivityScenarioRule<GetRideActivity>(GetRideActivity.class);

    @Test
    public void getRide() {
        onView((withId(R.id.from_et_get))).perform(typeText("Moshe Dayan road, tel aviv, israel"));
        onView((withId(R.id.to_et_get))).perform(typeText("bar ilan university"));
        onView((withId(R.id.editTextTime))).perform(typeText("12:00"));
        onView((withId(R.id.tomorrow_radio_get))).perform(click());
        onView((withId(R.id.get_optional_rides))).perform(click());
    }

}

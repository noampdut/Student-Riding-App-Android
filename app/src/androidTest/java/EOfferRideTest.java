import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import trempApplication.R;
import trempApplication.app.OfferRideActivity;

public class EOfferRideTest {

    //private Context context;

    @Before
    public void setUp() {
        //context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
    @Rule
    public ActivityScenarioRule<OfferRideActivity> activityRule = new ActivityScenarioRule<OfferRideActivity>(OfferRideActivity.class);

    @Test
    public void offerRide() {
        onView((withId(R.id.from_et_offer))).perform(typeText("Moshe Dayan road, tel aviv, israel"));
        onView((withId(R.id.to_et_offer))).perform(typeText("bar ilan university"));
        onView((withId(R.id.et_number))).perform(typeText("3"));
        onView((withId(R.id.et_time))).perform(typeText("12:00"));
        onView((withId(R.id.tomorrow_radio))).perform(click());
        onView((withId(R.id.submit_offer))).perform(click());
    }
}

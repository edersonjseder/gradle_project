package com.udacity.gradle.builditbigger.testing;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;

import com.udacity.gradle.builditbigger.MainActivityFragment;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.paid.GetJokesTask;
import com.udacity.gradle.builditbigger.paid.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class PaidEchoAndroidTest {

    Context context;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, new MainActivityFragment()).commit();
    }

    @Test
    public void testVerifyJoke() throws InterruptedException {

        assertTrue(true);

        final CountDownLatch latch = new CountDownLatch(1);

        context = InstrumentationRegistry.getContext();

        GetJokesTask testTask = new GetJokesTask() {

            @Override
            protected void onPostExecute(String result) {

                assertNotNull(result);

                if (result != null){

                    assertTrue(result.length() > 0);

                    latch.countDown();

                }
            }
        };

        testTask.execute(new Pair<Context, String>(context, ""));

        latch.await();
    }

    @Test
    public void onJokeButtonClick() throws InterruptedException {

        onView(withId(R.id.btn_click_joke)).perform(click());

        assertTrue(mActivityRule.getActivity().isFinishing());

    }

    @Test
    public void onViewVerify() {

        onView(withId(R.id.instructions_text_view)).check(matches((isDisplayed())));

    }

}

package com.moin.sparknetworks

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.moin.sparknetworks.presentation.view.PersonalityActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PersonalActivityInstrumentedTest {

    private val packageName: String = "com.moin.sparknetworks"

    @get:Rule
    var mainActivityInstrumentedTestRule = ActivityTestRule(PersonalityActivity::class.java, true, true)

    @Before
    fun setUp() {
        //before test case execution.
        mainActivityInstrumentedTestRule.activity
    }

    @Test
    fun testPackageName() {
        // Context of the app under test.
        val appContext = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        assertEquals(packageName, appContext.targetContext.packageName)
    }

    @Test
    fun containerPerformClick() {
        Espresso.onView(withId(R.id.parent_container)).perform(click())
    }

    @Test
    fun checkIfRecyclerViewIsDisplayed() {
        Espresso.onView(withId(R.id.item_rv)).check(matches(isDisplayed()))
    }

    @Test
    fun performClickOnMainFragment() {
        Espresso.onView(withId(R.id.personality_fragment_layout)).perform(click())
    }

}

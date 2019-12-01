package com.moin.sparknetworks

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityInstrumentedTest {

    @get:Rule
    var mainActivityInstrumentedTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    fun setUp() {
        //before test case execution.
        mainActivityInstrumentedTestRule.activity
    }

    @Test
    fun testPackageName() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.moin.sparknetworks", appContext.packageName)
    }

    @Test
    fun containerPerformClick() {
        Espresso.onView(withId(R.id.container)).perform(click())
    }

    @Test
    fun checkIfRecyclerViewIsDisplayed() {
        Espresso.onView(withId(R.id.item_rv)).check(matches(isDisplayed()))
    }

    @Test
    fun performClickOnMainFragment() {
        Espresso.onView(withId(R.id.main)).perform(click())
    }

}

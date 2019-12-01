package com.moin.sparknetworks

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainFragmentUnitTest {

    private val gender = arrayOf("male", "female", "others")
    private val isGenderImportant = arrayOf("not important", "important", "very important")
    private val isAgeImportant = arrayOf("not important", "important", "very important")
    private val havingChildrenWithPartner = arrayOf("yes", "maybe", "no")
    private val alcoholFrequency = arrayOf("never", "once or twice a year", "once or twice a month", "once or twice a week")


    @Test
    fun isMale() {
        assertEquals("male",gender[0])
    }

    @Test
    fun isFemale() {
        assertEquals("female",gender[1])
    }

    @Test
    fun isOthers() {
        assertEquals("others",gender[2])
    }

    @Test
    fun genderNotImportant() {
        assertTrue(("not important" == isGenderImportant[0]))
    }

    @Test
    fun genderImportant() {
        assertTrue(("important" == isGenderImportant[1]))
    }

    @Test
    fun genderVeryImportant() {
        assertTrue(("very important" == isGenderImportant[2]))
    }

    @Test
    fun ageNotImportant() {
        assertTrue(("not important" == isAgeImportant[0]))
    }

    @Test
    fun ageImportant() {
        assertTrue(("important" == isAgeImportant[1]))
    }

    @Test
    fun ageVeryImportant() {
        assertTrue(("very important" == isAgeImportant[2]))
    }

    @Test
    fun canHaveChildrenWithPartner() {
        assertEquals("yes",havingChildrenWithPartner[0])
    }

    @Test
    fun mayHaveChildrenWithPartner() {
        assertEquals("maybe",havingChildrenWithPartner[1])
    }

    @Test
    fun willNotHaveChildrenWithPartner() {
        assertEquals("no",havingChildrenWithPartner[2])
    }

    @Test
    fun neverDrinksAlcohol() {
        assertEquals("never", alcoholFrequency[0])
    }

    @Test
    fun onceOrTwiceAYear() {
        assertEquals("once or twice a year", alcoholFrequency[1])
    }

    @Test
    fun onceOrTwiceAMonth() {
        assertEquals("once or twice a month", alcoholFrequency[2])
    }

    @Test
    fun onceOrTwiceAWeek() {
        assertEquals("once or twice a week", alcoholFrequency[3])
    }

}

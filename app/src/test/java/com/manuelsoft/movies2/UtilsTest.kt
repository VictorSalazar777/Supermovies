package com.manuelsoft.movies2

import com.manuelsoft.movies2.Utils.Companion.getRandomDoubleBetweenRange
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilsTest {

    @Test
    fun randomIsInRange() {
        val result = getRandomDoubleBetweenRange(1.0, 40000.0)
        for (x in 0 until 10) {
            assertTrue(result >= 1.0)
            assertTrue(result <= 40000.0)
        }
    }

}
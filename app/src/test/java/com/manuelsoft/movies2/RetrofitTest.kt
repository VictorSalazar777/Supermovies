package com.manuelsoft.movies2

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Test

import org.junit.Assert.*


class RetrofitTest {

    @Test
    fun fileIsCorrect() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("data1.json")
        assertThat(inputStream, `is`(notNullValue()))
    }


}

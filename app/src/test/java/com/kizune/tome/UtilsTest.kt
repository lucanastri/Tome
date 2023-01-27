package com.kizune.tome

import com.kizune.tome.utils.FilterType
import com.kizune.tome.utils.criteria
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun verify_criteria_by_genre() {
        val expected = FakeDataSource.fakeFilteredFantasy
        val result = FakeDataSource.fakeBooks.values.toList().criteria(FilterType.Genre, "Fantasy")

        assertEquals(expected, result)
    }

    @Test
    fun verify_criteria_by_title() {
        val expected = FakeDataSource.fakeFilteredTitle
        val result = FakeDataSource.fakeBooks.values.toList().criteria(FilterType.Title, "Title4")

        assertEquals(expected, result)
    }

    @Test
    fun verify_criteria_by_author() {
        val expected = FakeDataSource.fakeFilteredAuthor
        val result = FakeDataSource.fakeBooks.values.toList().criteria(FilterType.Author, "Autore4")

        assertEquals(expected, result)
    }
}
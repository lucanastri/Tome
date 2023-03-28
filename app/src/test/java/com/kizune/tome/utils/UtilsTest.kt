package com.kizune.tome.utils

import com.kizune.tome.FakeDataSource
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

    @Test
    fun verify_criteria_by_publisher() {
        val expected = FakeDataSource.fakeFilteredPublisher
        val result = FakeDataSource.fakeBooks.values.toList().criteria(FilterType.Publisher, "publisher1")

        assertEquals(expected, result)
    }

    @Test
    fun verify_criteria_by_isbn() {
        val expected = FakeDataSource.fakeFilteredIsbn
        val result = FakeDataSource.fakeBooks.values.toList().criteria(FilterType.Isbn, "123")

        assertEquals(expected, result)
    }

    @Test
    fun verify_book_attribute_list() {
        val expected = FakeDataSource.book1Attributes
        val result = FakeDataSource.fakeBooks["id_1"]?.createAttributesList()

        assertEquals(expected, result)
    }
}
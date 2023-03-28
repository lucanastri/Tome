package com.kizune.tome

import com.kizune.tome.network.Book
import com.kizune.tome.network.IndustryIdentifiers
import com.kizune.tome.network.Library
import com.kizune.tome.network.VolumeInfo

object FakeDataSource {
    private val fakeVolumeInfo = listOf(
        VolumeInfo(
            title = "Title1",
            categories = listOf("Fiction"),
            authors = listOf("Autore1"),
            publisher = "publisher1",
            industryIdentifiers = listOf(IndustryIdentifiers("isbn", "123"))
        ),
        VolumeInfo(
            title = "Title2",
            categories = listOf("Fantasy"),
            authors = listOf("Autore2"),
            publisher = "publisher1",
            industryIdentifiers = listOf(IndustryIdentifiers("isbn", "4443"))
        ),
        VolumeInfo(
            title = "Title3",
            categories = listOf("Adventure"),
            authors = listOf("Autore3"),
            publisher = "publisher2",
            industryIdentifiers = listOf(IndustryIdentifiers("isbn", "125"))
        ),
        VolumeInfo(
            title = "Title4",
            categories = listOf("Fantasy"),
            authors = listOf("Autore4"),
            publisher = "publisher3",
            industryIdentifiers = emptyList()
        ),
    )

    val fakeBooks = hashMapOf(
        Pair("id_1", Book(id = "id_1", volumeInfo = fakeVolumeInfo[0])),
        Pair("id_2", Book(id = "id_2", volumeInfo = fakeVolumeInfo[1])),
        Pair("id_3", Book(id = "id_3", volumeInfo = fakeVolumeInfo[2])),
        Pair("id_4", Book(id = "id_4", volumeInfo = fakeVolumeInfo[3])),
    )

    val fakeLibrary = Library(
        items = fakeBooks.values.toList()
    )

    val fakeFilteredFantasy = listOf(
        fakeBooks["id_4"],
        fakeBooks["id_2"]
    )

    val fakeFilteredTitle = listOf(
        fakeBooks["id_4"]
    )

    val fakeFilteredAuthor = listOf(
        fakeBooks["id_4"]
    )

    val fakeFilteredPublisher = listOf(
        fakeBooks["id_1"],
        fakeBooks["id_2"]
    )

    val fakeFilteredIsbn = listOf(
        fakeBooks["id_1"]
    )

    val book1Attributes = listOf(
        Pair(R.string.detail_author, "Autore1"),
        Pair(R.string.detail_publisher, "publisher1"),
        Pair(R.string.detail_genre, "Fiction"),
        Pair(R.string.detail_language, "Inglese"),
        Pair(R.string.detail_date, "1994"),
        Pair(R.string.detail_pages, "555"),
        Pair(R.string.detail_isbn_10, "123"),
    )

}
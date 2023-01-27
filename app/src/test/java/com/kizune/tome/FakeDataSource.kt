package com.kizune.tome

import com.kizune.tome.network.Book
import com.kizune.tome.network.Library
import com.kizune.tome.network.VolumeInfo

object FakeDataSource {
    private val fakeVolumeInfo = listOf(
        VolumeInfo(
            title = "Title1",
            categories = listOf("Fiction"),
            authors = listOf("Autore1")
        ),
        VolumeInfo(
            title = "Title2",
            categories = listOf("Fantasy"),
            authors = listOf("Autore2")
        ),
        VolumeInfo(
            title = "Title3",
            categories = listOf("Adventure"),
            authors = listOf("Autore3"),
        ),
        VolumeInfo(
            title = "Title4",
            categories = listOf("Fantasy"),
            authors = listOf("Autore4")
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

    val fakeCategories = hashMapOf(
        Pair("Fantasy", "found"),
        Pair("Adventure", "found"),
        Pair("Fiction", "found")
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

}
package com.kizune.tome.network

import com.kizune.tome.ui.BookCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Library(
    @SerialName(value = "kind")
    val kind: String = "",
    @SerialName(value = "totalItems")
    val totalItems: Int = 0,
    @SerialName(value = "items")
    val items: List<Book>
)

@Serializable
data class Book(
    @SerialName(value = "kind")
    val kind: String = "",
    @SerialName(value = "id")
    val id: String = "",
    @SerialName(value = "etag")
    val etag: String = "",
    @SerialName(value = "selfLink")
    val selfLink: String = "",
    @SerialName(value = "volumeInfo")
    val volumeInfo: VolumeInfo,
    @SerialName(value = "saleInfo")
    val saleInfo: SaleInfo? = null,
    @SerialName(value = "accessInfo")
    val accessInfo: AccessInfo? = null,
)

val previewVolumeInfo = VolumeInfo(
    title = "Titolo",
    authors = listOf("Autore"),
    categories = listOf(
        BookCategory.Adventure.text,
        BookCategory.Fiction.text,
        BookCategory.Horror.text,
        BookCategory.Literature.text,
        BookCategory.Thriller.text
    ),
    publishedDate = "2000",
    description = "Questa è la descrizione",
    pageCount = 500,
    averageRating = 3.5
)

val previewBook = Book(
    volumeInfo = previewVolumeInfo
)

val previewHashMap = hashMapOf(
    "1" to previewBook,
    "2" to previewBook,
    "3" to previewBook,
    "4" to previewBook,
    "5" to previewBook,
    "6" to previewBook
)

package com.kizune.tome.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    @SerialName(value = "title")
    val title: String,
    @SerialName(value = "subtitle")
    val subtitle: String = "",
    @SerialName(value = "authors")
    val authors: List<String>? = null,
    @SerialName(value = "publisher")
    val publisher: String = "",
    @SerialName(value = "publishedDate")
    val publishedDate: String = "1994",
    @SerialName(value = "description")
    val description: String = "",
    @SerialName(value = "industryIdentifiers")
    val industryIdentifiers: List<IndustryIdentifiers>? = null,
    @SerialName(value = "readingModes")
    val readingModes: ReadingModes? = null,
    @SerialName(value = "pageCount")
    val pageCount: Int = 555,
    @SerialName(value = "printType")
    val printType: String = "",
    @SerialName(value = "categories")
    val categories: List<String>? = null,
    @SerialName(value = "averageRating")
    val averageRating: Double = 0.0,
    @SerialName(value = "ratingsCount")
    val ratingsCount: Int = 0,
    @SerialName(value = "maturityRating")
    val maturityRating: String = "",
    @SerialName(value = "allowAnonLogging")
    val allowAnonLogging: Boolean = false,
    @SerialName(value = "contentVersion")
    val contentVersion: String = "",
    @SerialName(value = "panelizationSummary")
    val panelizationSummary: PanelizationSummary? = null,
    @SerialName(value = "imageLinks")
    val imageLinks: ImageLinks? = null,
    @SerialName(value = "language")
    val language: String = "en",
    @SerialName(value = "previewLink")
    val previewLink: String = "",
    @SerialName(value = "infoLink")
    val infoLink: String = "",
    @SerialName(value = "canonicalVolumeLink")
    val canonicalVolumeLink: String = ""
)

@Serializable
data class IndustryIdentifiers(
    @SerialName(value = "type")
    val type: String,
    @SerialName(value = "identifier")
    val identifier: String
)

@Serializable
data class ReadingModes(
    @SerialName(value = "text")
    val text: Boolean,
    @SerialName(value = "image")
    val image: Boolean
)

@Serializable
data class PanelizationSummary(
    @SerialName(value = "containsEpubBubbles")
    val containsEpubBubbles: Boolean,
    @SerialName(value = "containsImageBubbles")
    val containsImageBubbles: Boolean
)

@Serializable
data class ImageLinks(
    @SerialName(value = "thumbnail")
    val thumbnail: String = ""
)
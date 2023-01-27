package com.kizune.tome.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessInfo(
    @SerialName(value = "country")
    val country: String = "",
    @SerialName(value = "viewability")
    val viewability: String = "",
    @SerialName(value = "embeddable")
    val embeddable: Boolean = false,
    @SerialName(value = "publicDomain")
    val publicDomain: Boolean = false,
    @SerialName(value = "textToSpeechPermission")
    val textToSpeechPermission: String = "",
    @SerialName(value = "epub")
    val epub: VolumeFormat = VolumeFormat(false, ""),
    @SerialName(value = "pdf")
    val pdf: VolumeFormat = VolumeFormat(false, ""),
    @SerialName(value = "webReaderLink")
    val webReaderLink: String = "",
    @SerialName(value = "accessViewStatus")
    val accessViewStatus: String = "",
    @SerialName(value = "quoteSharingAllowed")
    val quoteSharingAllowed: Boolean = false
)

@Serializable
data class VolumeFormat(
    @SerialName(value = "isAvailable")
    val isAvailable: Boolean,
    @SerialName(value = "acsTokenLink")
    val acsTokenLink: String = "",
)
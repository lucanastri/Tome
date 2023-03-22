package com.kizune.tome.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaleInfo(
    @SerialName(value = "country")
    val country: String? = null,
    @SerialName(value = "saleability")
    val saleability: String? = null,
    @SerialName(value = "isEbook")
    val isEbook: Boolean = false,
    @SerialName(value = "listPrice")
    val listPrice: ListPrice? = null,
    @SerialName(value = "retailPrice")
    val retailPrice: ListPrice? = null,
    @SerialName(value = "buyLink")
    val buyLink: String? = null,
    @SerialName(value = "offers")
    val offers: List<Offer> = ArrayList()
)

@Serializable
data class ListPrice(
    @SerialName(value = "amount")
    val amount: Double,
    @SerialName(value = "currencyCode")
    val currencyCode: String
)

@Serializable
data class Offer(
    @SerialName(value = "finskyOfferType")
    val finskyOfferType: Int,
    @SerialName(value = "listPrice")
    val listPrice: ListPriceMicros,
    @SerialName(value = "retailPrice")
    val retailPrice: ListPriceMicros
)

@Serializable
data class ListPriceMicros(
    @SerialName(value = "amountInMicros")
    val amountInMicros: Long,
    @SerialName(value = "currencyCode")
    val currencyCode: String
)
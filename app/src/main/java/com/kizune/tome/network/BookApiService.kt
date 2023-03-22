package com.kizune.tome.network

import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {
    @GET("books/v1/volumes?")
    suspend fun getBooks(
        @Query("q") q: String,
        @Query("maxResults") maxResults: Int,
        @Query("startIndex") startIndex: Int
    ): Library
}
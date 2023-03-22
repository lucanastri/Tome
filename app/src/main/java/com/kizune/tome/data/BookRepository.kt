package com.kizune.tome.data

import com.kizune.tome.network.BookApiService
import com.kizune.tome.network.Library

interface BookRepository {
    suspend fun getBooks(
        q: String = "",
        maxResults: Int = 40,
        startIndex: Int = 0
    ): Library
}

class DefaultBookRepository(
    private val bookApiService: BookApiService
) : BookRepository {

    // Funzione che permette di prendere i libri dalla API di Google books
    override suspend fun getBooks(
        q: String,
        maxResults: Int,
        startIndex: Int
    ): Library {
        return bookApiService.getBooks(
            q = q,
            maxResults = maxResults,
            startIndex = startIndex
        )
    }
}
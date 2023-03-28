package com.kizune.tome

import com.kizune.tome.network.BookApiService
import com.kizune.tome.network.Library


class FakeBookApiService : BookApiService {
    override suspend fun getBooks(q: String, maxResults: Int, startIndex: Int): Library {
        return Library(items = FakeDataSource.fakeBooks.values.toList())
    }
    
}
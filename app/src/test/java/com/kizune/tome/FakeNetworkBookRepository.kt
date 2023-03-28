package com.kizune.tome

import com.kizune.tome.data.BookRepository
import com.kizune.tome.network.Library

class FakeNetworkBookRepository : BookRepository {
    override suspend fun getBooks(q: String, maxResults: Int, startIndex: Int): Library {
        return Library(items = FakeDataSource.fakeBooks.values.toList())
    }
}
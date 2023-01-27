package com.kizune.tome

import com.kizune.tome.data.DefaultBookRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBookRepositoryTest {
    @Test
    fun networkBookRepository_getBooks_verifyBookList() =
        runTest {
            val repository = DefaultBookRepository(
                bookApiService = FakeBookApiService()
            )
            assertEquals(FakeDataSource.fakeLibrary, repository.getBooks())
        }
}
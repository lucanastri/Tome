package com.kizune.tome

import com.kizune.tome.data.DefaultBookRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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
package com.kizune.tome

import com.kizune.tome.rules.TestDispatcherRule
import com.kizune.tome.ui.BookUiState
import com.kizune.tome.ui.BookViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BookViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun viewModel_getBooks_verifyBookUiStateSuccess() =
        runTest {
            val bookViewModel = BookViewModel(
                bookRepository = FakeNetworkBookRepository()
            )

            assertEquals(
                BookUiState.Success(
                    books = FakeDataSource.fakeBooks,
                ),
                bookViewModel.bookUiState
            )
        }
}
package com.kizune.tome.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kizune.tome.R
import com.kizune.tome.TomeApplication
import com.kizune.tome.data.BookRepository
import com.kizune.tome.network.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

enum class BookCategory(
    @StringRes val id: Int,
    val text: String,
) {
    Literature(R.string.category_literature, "literature"),
    Adventure(R.string.category_adventure, "adventure"),
    Fiction(R.string.category_fiction, "fiction"),
    Horror(R.string.category_horror, "horror"),
    Thriller(R.string.category_thriller, "thriller")
}

/**
 * Permette di tenere traccia dell'UIState in modo da riflettere i cambiamenti al data layer verso
 * il livello di UI
 */
interface BookUiState {
    data class Success(
        val books: HashMap<String, Book>,
    ) : BookUiState

    object Error : BookUiState
    object Loading : BookUiState
}

class BookViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            //Viene inizializzato nel caso di retry
            bookUiState = BookUiState.Loading
            try {
                val map = HashMap<String, Book>()

                val maxResults = 40
                var startIndex = 0

                val categories = enumValues<BookCategory>()

                categories.forEach { category ->
                    val q = "subject:$category"

                    bookRepository.getBooks(q, maxResults, startIndex).items.map { book ->
                        Log.d("MyTag", book.toString())
                        map[book.id] = book
                    }
                    startIndex += 40
                }

                bookUiState = BookUiState.Success(map)
            } catch (e: IOException) {
                bookUiState = BookUiState.Error
                e.printStackTrace()
            } catch (e: HttpException) {
                bookUiState = BookUiState.Error
                e.printStackTrace()
            } catch (e: Exception) {
                bookUiState = BookUiState.Error
                e.printStackTrace()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TomeApplication)
                val tomeRepository = application.container.bookRepository
                BookViewModel(tomeRepository)
            }
        }
    }

    //Ui state riguardante il libro selezionato dall'utente
    private val _bookUiStateSelected = MutableStateFlow(BookSelectedUiState())
    val bookUiStateSelected: StateFlow<BookSelectedUiState> = _bookUiStateSelected.asStateFlow()

    fun updateBookSelected(book: Book) {
        _bookUiStateSelected.update { currentState ->
            currentState.copy(book = book)
        }
    }

    //Ui state riguardante la categoria selezionata dall'utente
    private val _bookUiStateCategory = MutableStateFlow(BookCategoryUiState())
    val bookUiStateCategory: StateFlow<BookCategoryUiState> = _bookUiStateCategory.asStateFlow()

    fun updateCategorySelected(category: BookCategory) {
        _bookUiStateCategory.update { currentState ->
            currentState.copy(category = category)
        }
    }
}

data class BookSelectedUiState(
    val book: Book? = null,
)

data class BookCategoryUiState(
    val category: BookCategory? = null,
)
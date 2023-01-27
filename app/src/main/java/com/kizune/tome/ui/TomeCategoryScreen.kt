package com.kizune.tome.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kizune.tome.network.Book
import com.kizune.tome.utils.FilterType
import com.kizune.tome.utils.criteria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    bookUiState: BookUiState,
    bookCategory: BookCategory,
    onCardPressed: (Book) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val books: List<Book> = when (bookUiState) {
        is BookUiState.Success ->
            bookUiState.books.values
                .toList()
                .criteria(FilterType.Genre, filter = bookCategory.text)
        else -> ArrayList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                onBackClicked = onBackClicked,
                title = stringResource(id = bookCategory.id)
            )
        },
        content = { padding ->
            CategoryScreenContent(
                books = books,
                onCardPressed = onCardPressed,
                modifier = modifier.padding(padding)
            )
        },
        modifier = modifier
    )
}

@Composable
fun CategoryScreenContent(
    books: List<Book>,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(books) { item ->
            ResearchCard(
                book = item,
                onCardPressed = onCardPressed
            )
        }
    }
}
package com.kizune.tome.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizune.tome.R
import com.kizune.tome.network.Book
import com.kizune.tome.network.previewBook
import com.kizune.tome.network.previewHashMap
import com.kizune.tome.theme.TomeTheme
import com.kizune.tome.utils.FilterType
import com.kizune.tome.utils.criteria

@Composable
fun HomeScreen(
    bookUiState: BookUiState,
    bookSelected: Book?,
    bookCategory: BookCategory?,
    showSecondaryPane: Boolean,
    contentType: ContentType,
    onShowMoreClicked: (BookCategory) -> Unit,
    onCardPressed: (Book) -> Unit,
    onRetryClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onBackClicked()
    }

    Box(modifier = modifier) {
        Crossfade(targetState = bookUiState) { state ->
            when (state) {
                is BookUiState.Success ->
                    HomeScreenLoaded(
                        contentType = contentType,
                        showSecondaryPane = showSecondaryPane,
                        bookList = state.books.values.toList(),
                        bookSelected = bookSelected ?: state.books.values.toList()[0],
                        bookCategory = bookCategory ?: BookCategory.Literature,
                        categoryList = enumValues<BookCategory>().toList(),
                        onShowMoreClicked = onShowMoreClicked,
                        onCardPressed = onCardPressed
                    )
                is BookUiState.Error ->
                    HomeScreenLoadOrError(
                        contentType = contentType,
                        Content = { ErrorScreen(onRetryClicked) }
                    )
                else ->
                    HomeScreenLoadOrError(
                        contentType = contentType,
                        Content = { LoadingScreen() }
                    )
            }
        }
    }
}

@Composable
fun HomeScreenLoaded(
    showSecondaryPane: Boolean,
    contentType: ContentType,
    bookList: List<Book>,
    bookSelected: Book,
    bookCategory: BookCategory,
    categoryList: List<BookCategory>,
    onShowMoreClicked: (BookCategory) -> Unit,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        HomeContent(
            bookList = bookList,
            categoryList = categoryList,
            onShowMoreClicked = onShowMoreClicked,
            onCardPressed = onCardPressed,
            modifier = modifier.weight(0.5f)
        )
        if (contentType == ContentType.Expanded) {
            if (showSecondaryPane) {
                Spacer(Modifier.width(16.dp))
                CategoryScreenContent(
                    books = bookList.criteria(FilterType.Genre, bookCategory.text),
                    onCardPressed = onCardPressed,
                    modifier = modifier.weight(0.5f)
                )
            } else {
                Spacer(Modifier.width(16.dp))
                BookScreenContent(
                    book = bookSelected,
                    contentType = contentType,
                    modifier = modifier.weight(0.5f)
                )
            }

        }
    }
}

@Composable
fun HomeScreenLoadOrError(
    Content: @Composable () -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier
) {
    val fraction = when (contentType) {
        ContentType.Compact, ContentType.Medium -> 1f
        ContentType.Expanded -> 0.5f
    }
    Column(modifier = modifier.fillMaxSize(fraction)) {
        DashboardHeader(
            icon = Icons.Rounded.LocalLibrary,
            title = R.string.home_header_title,
            body = R.string.home_header_body,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Content()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenCompactPreview() {
    TomeTheme {
        HomeScreen(
            bookUiState = BookUiState.Success(previewHashMap),
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            contentType = ContentType.Compact,
            showSecondaryPane = true,
            onShowMoreClicked = {},
            onCardPressed = {},
            onRetryClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenFailCompactPreview() {
    TomeTheme {
        HomeScreen(
            bookUiState = BookUiState.Error,
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            contentType = ContentType.Compact,
            showSecondaryPane = true,
            onShowMoreClicked = {},
            onCardPressed = {},
            onRetryClicked = {},
            onBackClicked = {}
        )
    }
}


@Preview(showBackground = true, widthDp = 700)
@Composable
fun HomeScreenMediumPreview() {
    TomeTheme {
        HomeScreen(
            bookUiState = BookUiState.Success(previewHashMap),
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            contentType = ContentType.Medium,
            showSecondaryPane = true,
            onShowMoreClicked = {},
            onCardPressed = {},
            onRetryClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun HomeScreenFailMediumPreview() {
    TomeTheme {
        HomeScreen(
            bookUiState = BookUiState.Error,
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            contentType = ContentType.Medium,
            showSecondaryPane = true,
            onShowMoreClicked = {},
            onCardPressed = {},
            onRetryClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
fun HomeScreenExpandedPreview() {
    TomeTheme {
        HomeScreen(
            bookUiState = BookUiState.Success(previewHashMap),
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            contentType = ContentType.Expanded,
            showSecondaryPane = false,
            onShowMoreClicked = {},
            onCardPressed = {},
            onRetryClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
fun HomeScreenExpandedSecondaryPreview() {
    TomeTheme {
        HomeScreen(
            bookUiState = BookUiState.Success(previewHashMap),
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            contentType = ContentType.Expanded,
            showSecondaryPane = true,
            onShowMoreClicked = {},
            onCardPressed = {},
            onRetryClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
fun HomeScreenFailExpandedPreview() {
    TomeTheme {
        HomeScreen(
            bookUiState = BookUiState.Error,
            bookSelected = previewBook,
            bookCategory = BookCategory.Literature,
            contentType = ContentType.Expanded,
            showSecondaryPane = true,
            onShowMoreClicked = {},
            onCardPressed = {},
            onRetryClicked = {},
            onBackClicked = {}
        )
    }
}
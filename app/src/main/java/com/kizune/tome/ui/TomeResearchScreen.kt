package com.kizune.tome.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizune.tome.R
import com.kizune.tome.network.Book
import com.kizune.tome.network.previewBook
import com.kizune.tome.network.previewHashMap
import com.kizune.tome.theme.TomeTheme

val filters = listOf(
    R.string.filter_title,
    R.string.filter_author,
    R.string.filter_genre,
    R.string.filter_publisher,
    R.string.filter_isbn,
)

@Composable
fun ResearchScreen(
    bookUiState: BookUiState,
    bookSelected: Book?,
    contentType: ContentType,
    onCardPressed: (Book) -> Unit,
    onRetryClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackClicked()
    }

    Box(modifier = modifier) {
        Crossfade(targetState = bookUiState) { state ->
            when (state) {
                is BookUiState.Success ->
                    ResearchScreenLoaded(
                        contentType = contentType,
                        bookList = state.books.values.toList(),
                        bookSelected = bookSelected ?: state.books.values.toList()[0],
                        onCardPressed = onCardPressed
                    )
                is BookUiState.Error ->
                    ResearchScreenLoadOrError(
                        Content = { ErrorScreen(onRetryClicked) },
                        contentType = contentType,
                    )
                else ->
                    ResearchScreenLoadOrError(
                        Content = { LoadingScreen() },
                        contentType = contentType,
                    )
            }
        }
    }
}

@Composable
fun ResearchScreenLoaded(
    contentType: ContentType,
    bookList: List<Book>,
    bookSelected: Book,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        ResearchContent(
            books = bookList,
            onCardPressed = onCardPressed,
            modifier.weight(0.5f)
        )
        if (contentType == ContentType.Expanded) {
            Spacer(Modifier.width(16.dp))
            BookScreenContent(
                book = bookSelected,
                contentType = contentType,
                modifier = modifier.weight(0.5f)
            )
        }
    }
}

@Composable
fun ResearchScreenLoadOrError(
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
            icon = Icons.Rounded.PersonSearch,
            title = R.string.research_header_title,
            body = R.string.research_header_body,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Content()
    }
}

@Preview(showBackground = true)
@Composable
fun ResearchScreenCompactPreview() {
    TomeTheme {
        ResearchScreen(
            bookUiState = BookUiState.Success(previewHashMap),
            bookSelected = previewBook,
            contentType = ContentType.Compact,
            onCardPressed = { },
            onRetryClicked = { },
            onBackClicked = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResearchScreenFailCompactPreview() {
    TomeTheme {
        ResearchScreen(
            bookUiState = BookUiState.Error,
            bookSelected = previewBook,
            contentType = ContentType.Compact,
            onCardPressed = { },
            onRetryClicked = { },
            onBackClicked = { }
        )
    }
}


@Preview(showBackground = true, widthDp = 700)
@Composable
fun ResearchScreenMediumPreview() {
    TomeTheme {
        ResearchScreen(
            bookUiState = BookUiState.Success(previewHashMap),
            bookSelected = previewBook,
            contentType = ContentType.Medium,
            onCardPressed = { },
            onRetryClicked = { },
            onBackClicked = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun ResearchScreenFailMediumPreview() {
    TomeTheme {
        ResearchScreen(
            bookUiState = BookUiState.Error,
            bookSelected = previewBook,
            contentType = ContentType.Medium,
            onCardPressed = { },
            onRetryClicked = { },
            onBackClicked = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
fun ResearchScreenExpandedPreview() {
    TomeTheme {
        ResearchScreen(
            bookUiState = BookUiState.Success(previewHashMap),
            bookSelected = previewBook,
            contentType = ContentType.Expanded,
            onCardPressed = { },
            onRetryClicked = { },
            onBackClicked = { }
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
fun ResearchScreenFailExpandedPreview() {
    TomeTheme {
        ResearchScreen(
            bookUiState = BookUiState.Error,
            bookSelected = previewBook,
            contentType = ContentType.Expanded,
            onCardPressed = { },
            onRetryClicked = { },
            onBackClicked = { }
        )
    }
}



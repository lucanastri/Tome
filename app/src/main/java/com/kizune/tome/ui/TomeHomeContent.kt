package com.kizune.tome.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kizune.tome.R
import com.kizune.tome.network.Book
import com.kizune.tome.utils.FilterType
import com.kizune.tome.utils.criteria

@Composable
fun HomeContent(
    bookList: List<Book>,
    categoryList: List<BookCategory>,
    onShowMoreClicked: (BookCategory) -> Unit,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            DashboardHeader(
                icon = Icons.Rounded.LocalLibrary,
                title = R.string.home_header_title,
                body = R.string.home_header_body,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(categoryList) { item ->
            HomeSection(
                bookList = bookList.criteria(FilterType.Genre, item.text).take(6),
                category = item,
                onShowMoreClicked = onShowMoreClicked,
                onCardPressed = onCardPressed,
            )
        }
    }
}

@Composable
fun HomeSection(
    bookList: List<Book>,
    category: BookCategory,
    onShowMoreClicked: (BookCategory) -> Unit,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Text(
                text = stringResource(id = category.id),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(5f)
            )
            Spacer(Modifier.weight(1f))
            TextButton(
                onClick = { onShowMoreClicked(category) },
            ) {
                Text(
                    text = stringResource(id = R.string.show_more),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(bookList) { book ->
                HomeCard(
                    book = book,
                    onCardPressed = onCardPressed
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(
    book: Book,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageLink = book.volumeInfo.imageLinks?.thumbnail ?: ""
    val fixedImageLink = imageLink.replace("http", "https")
    Card(
        onClick = {
            onCardPressed(book)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.width(160.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(fixedImageLink)
                .crossfade(true)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(width = 160.dp, height = 256.dp)
        )
        Text(
            text = book.volumeInfo.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp
            )
        )
        Text(
            text = book.volumeInfo.authors?.get(0) ?: stringResource(id = R.string.unknown_author),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(8.dp)
        )
    }
}
package com.kizune.tome.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kizune.tome.R
import com.kizune.tome.network.Book
import com.kizune.tome.utils.FilterType
import com.kizune.tome.utils.criteria
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResearchContent(
    books: List<Book>,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedChip by rememberSaveable { mutableStateOf(0) }
    val filterType = FilterType.values()[selectedChip]

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            DashboardHeader(
                icon = Icons.Rounded.PersonSearch,
                title = R.string.research_header_title,
                body = R.string.research_header_body,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        stickyHeader {
            ResearchPanel(
                searchQuery = searchQuery,
                onValueChange = { searchQuery = it },
                onCloseSearch = { searchQuery = "" },
                selectedChip = selectedChip,
                onSelectedChip = { selectedChip = it },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
            )
        }
        items(books.criteria(filterType, searchQuery)) { book ->
            ResearchCard(
                book = book,
                onCardPressed = onCardPressed,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResearchPanel(
    searchQuery: String,
    onValueChange: (String) -> Unit,
    onCloseSearch: () -> Unit,
    selectedChip: Int,
    onSelectedChip: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { value ->
                onValueChange(value)
            },
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.research_hint,
                        stringResource(filters[selectedChip])
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(id = R.string.research)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchQuery.isNotBlank(),
                    enter = fadeIn(
                        animationSpec = tween(400)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(250)
                    )
                ) {
                    IconButton(
                        onClick = onCloseSearch,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(id = R.string.filter_description),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(40.dp)
                .padding(horizontal = 16.dp)
                .testTag("search_input")
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(filters) { index, chip ->
                FilterChip(
                    selected = selectedChip == index,
                    onClick = { onSelectedChip(index) },
                    label = {
                        Text(
                            text = stringResource(id = chip),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResearchCard(
    book: Book,
    onCardPressed: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageLink = book.volumeInfo.imageLinks?.thumbnail ?: ""
    val fixedImageLink = imageLink.replace("http", "https")

    Card(
        onClick = { onCardPressed(book) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fixedImageLink)
                    .crossfade(true)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 100.dp, height = 160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = book.volumeInfo.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.volumeInfo.authors?.get(0) ?: " ",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                RatingBar(
                    rating = book.volumeInfo.averageRating
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    val locale = Locale.forLanguageTag(book.volumeInfo.language)
                    Text(
                        text = locale.displayLanguage.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        },
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = book.volumeInfo.publishedDate.substring(0, 4),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

package com.kizune.tome.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kizune.tome.R
import com.kizune.tome.network.Book
import com.kizune.tome.network.previewBook
import com.kizune.tome.theme.TomeTheme
import com.kizune.tome.utils.createAttributesList
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    book: Book,
    contentType: ContentType,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(onBackClicked = onBackClicked)
        },
        content = { padding ->
            BookScreenContent(
                book = book,
                contentType = contentType,
                modifier = Modifier.padding(padding)
            )
        },
        modifier = modifier
    )
}

@Composable
fun BookScreenContent(
    book: Book,
    contentType: ContentType,
    modifier: Modifier = Modifier
) {
    val columns = when (contentType) {
        ContentType.Compact -> 1
        ContentType.Medium -> 2
        ContentType.Expanded -> 1
    }

    val imageLink = book.volumeInfo.imageLinks?.thumbnail ?: ""
    val fixedImageLink = imageLink.replace("http", "https")

    val attributeList = book.createAttributesList()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fixedImageLink)
                    .crossfade(true)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 150.dp, height = 240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .testTag("thumbnail")
            )
        }
        item {
            RatingBar(
                rating = book.volumeInfo.averageRating,
                modifier = Modifier.testTag("rating_bar")
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        items(attributeList.chunked(columns)) { sequence ->
            AttributeGrid(
                attributes = sequence,
            )
        }
    }
}

@Composable
fun AttributeGrid(
    attributes: List<Pair<Int, String>>,
    modifier: Modifier = Modifier
) {
    var columns = attributes.size
    var showDescriptionOnBottom = false
    if (columns > 1 && attributes.last().first == R.string.detail_description) {
        columns = attributes.size - 1
        showDescriptionOnBottom = true
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .height(intrinsicSize = IntrinsicSize.Max)
    ) {
        repeat(columns) { index ->
            Log.d("MyTag", index.toString())
            AttributeCard(
                attribute = attributes[index],
                modifier = Modifier
                    .weight(1f / columns)
                    .fillMaxHeight()
                    .testTag("${index + 1}_attribute")
            )
        }
    }
    //Per permettere alla descrizione di occupare sempre l'intero schermo in fondo
    if (showDescriptionOnBottom) {
        AttributeCard(
            attribute = attributes.last(),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 16.dp)
                .testTag("bottom_description")
        )
    }
}

@Composable
fun AttributeCard(
    attribute: Pair<Int, String>,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = attribute.first),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = attribute.second,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookScreenCompactPreview() {
    TomeTheme {
        BookScreen(
            book = previewBook,
            contentType = ContentType.Compact,
            onBackClicked = { /*TODO*/ }
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun BookScreenMediumPreview() {
    TomeTheme {
        BookScreen(
            book = previewBook,
            contentType = ContentType.Medium,
            onBackClicked = { /*TODO*/ }
        )
    }
}

package com.kizune.tome.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizune.tome.R
import com.kizune.tome.model.Profile
import com.kizune.tome.model.ProfileInfo
import com.kizune.tome.theme.TomeTheme

val profile = Profile()

val profileInfoList = listOf(
    ProfileInfo(
        imageVector = Icons.Rounded.ChromeReaderMode,
        label = R.string.profile_read_books,
        body = profile.readBooks.toString()
    ),
    ProfileInfo(
        imageVector = Icons.Rounded.Person,
        label = R.string.profile_favorite_author,
        body = profile.favoriteAuthor
    ),
    ProfileInfo(
        imageVector = Icons.Rounded.Favorite,
        label = R.string.profile_favorite_book,
        body = profile.favoriteBook
    ),
    ProfileInfo(
        imageVector = Icons.Rounded.Category,
        label = R.string.profile_favorite_genre,
        body = profile.favoriteGenre
    ),
    ProfileInfo(
        imageVector = Icons.Rounded.Equalizer,
        label = R.string.profile_reading_books,
        body = profile.readingBooks.toString()
    ),
    ProfileInfo(
        imageVector = Icons.Rounded.Pending,
        label = R.string.profile_toread_books,
        body = profile.toReadBooks.toString()
    )
)

@Composable
fun ProfileScreen(
    contentType: ContentType,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackClicked()
    }

    val showList = contentType != ContentType.Expanded
    Row(modifier = modifier.fillMaxSize()) {
        ProfileContent(
            showList = showList,
            modifier = Modifier.weight(0.5f)
        )
        if (contentType == ContentType.Expanded) {
            Spacer(modifier = Modifier.width(16.dp))
            ProfileSideContent(
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenCompactPreview() {
    TomeTheme {
        ProfileScreen(
            contentType = ContentType.Compact,
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun ProfileScreenMediumPreview() {
    TomeTheme {
        ProfileScreen(
            contentType = ContentType.Medium,
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
fun ProfileScreenExpandedPreview() {
    TomeTheme {
        ProfileScreen(
            contentType = ContentType.Expanded,
            onBackClicked = {}
        )
    }
}
package com.kizune.tome.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kizune.tome.R
import com.kizune.tome.model.ProfileInfo
import com.kizune.tome.utils.conditional

@Composable
fun ProfileContent(
    showList: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .conditional(showList) {
                verticalScroll(scrollState)
            }
            .padding(16.dp)
    ) {
        DashboardHeader(
            icon = Icons.Rounded.Person,
            title = R.string.profile_header_title,
            body = R.string.profile_header_body
        )
        ProfileTitle()
        ProfileImage(
            modifier = Modifier
                .conditional(!showList) {
                    fillMaxSize()
                }
                .conditional(showList) {
                    size(200.dp)
                }
                .conditional(!showList) {
                    weight(1f)
                }
                .align(Alignment.CenterHorizontally)
        )
        if (showList) {
            Spacer(modifier = Modifier.height(16.dp))
            profileInfoList.chunked(2).forEach { items ->
                ProfileGrid(info = items)
            }
        }
    }
}

@Composable
fun ProfileSideContent(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        profileInfoList.chunked(1).forEach { items ->
            ProfileGrid(info = items)
        }
    }
}

@Composable
fun ProfileTitle() {
    Text(
        text = profile.name + " " + profile.surname,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.profile_pic)
                .crossfade(true)
                .build(),
            placeholder = asyncImagePlaceholder(placeholder = R.drawable.profile_pic),
            contentDescription = stringResource(R.string.profile_pic_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProfileGrid(
    info: List<ProfileInfo>,
    modifier: Modifier = Modifier
) {
    val columns = info.size
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .height(intrinsicSize = IntrinsicSize.Max)
    ) {
        repeat(columns) { index ->
            ProfileCard(
                profileInfo = info[index],
                modifier = Modifier
                    .weight(1f / columns)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun ProfileCard(
    profileInfo: ProfileInfo,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = profileInfo.imageVector,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(profileInfo.label),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = profileInfo.body,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
package com.kizune.tome.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kizune.tome.R

@Composable
fun TopAppBar(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.back_description),
                modifier = Modifier.size(24.dp)
            )
        }
        if (title != null) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
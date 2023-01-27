package com.kizune.tome.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.floor

/**
 * Composable che serve a visualizzare il rating in una barra composta da stelle
 */
@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    showUnfilledStar: Boolean = false
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = stars - filledStars

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        if (showUnfilledStar) {
            repeat(unfilledStars) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
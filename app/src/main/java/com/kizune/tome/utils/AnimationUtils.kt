package com.kizune.tome.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

fun enterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(650))
}

fun exitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(650))
}
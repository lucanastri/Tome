package com.kizune.tome.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.kizune.tome.R

data class Profile(
    @DrawableRes val imgSrc: Int = R.drawable.profile_pic,
    val name: String = "Luca",
    val surname: String = "Nastri",
    val readBooks: Int = 12,
    val favoriteAuthor: String = "Giovanni Verga",
    val favoriteBook: String = "La Folie Baudelaire",
    val favoriteGenre: String = "Letteratura",
    val readingBooks: Int = 2,
    val toReadBooks: Int = 5
)

data class ProfileInfo(
    val imageVector: ImageVector,
    @StringRes val label: Int,
    val body: String
)

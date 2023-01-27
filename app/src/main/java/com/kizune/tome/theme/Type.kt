package com.kizune.tome.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kizune.tome.R

val Noto = FontFamily(
    Font(R.font.noto_sans_regular),
    Font(R.font.noto_sans_medium, FontWeight.Medium)
)

val typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Noto,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 30.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = Noto,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 21.8.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Noto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 19.1.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Noto,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.3.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = Noto,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 15.sp,
    )

)
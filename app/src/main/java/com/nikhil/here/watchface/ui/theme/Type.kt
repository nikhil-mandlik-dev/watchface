package com.nikhil.here.watchface.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nikhil.here.watchface.R


val WorkSans = FontFamily(
    Font(R.font.ws_regular,FontWeight.Normal),
    Font(R.font.ws_black,FontWeight.Black),
    Font(R.font.ws_bold,FontWeight.Bold),
    Font(R.font.ws_extra_bold,FontWeight.ExtraBold),
    Font(R.font.ws_extra_light,FontWeight.ExtraLight),
    Font(R.font.ws_light,FontWeight.Light),
    Font(R.font.ws_medium,FontWeight.Medium),
    Font(R.font.ws_semi_bold,FontWeight.SemiBold),
    Font(R.font.ws_thin,FontWeight.Thin),
)

val MerriWeather = FontFamily(
    Font(R.font.merriweather_regular,FontWeight.Normal),
    Font(R.font.merriweather_light,FontWeight.Light),
    Font(R.font.merriweather_bold,FontWeight.Bold),
    Font(R.font.merriweather_black,FontWeight.Black)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
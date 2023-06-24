package com.nikhil.here.watchface.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhil.here.watchface.ui.theme.*

data class DialStyle(
    val stepsWidth: Dp = 1.2.dp,
    val stepsColor: Color = Color.Black,
    val normalStepsLineHeight: Dp = 8.dp,
    val fiveStepsLineHeight: Dp = 16.dp,
    val stepsTextStyle: TextStyle = TextStyle(),
    val stepsLabelTopPadding: Dp = 12.dp,
)

data class ClockStyle(
    val secondsDialStyle: DialStyle = DialStyle(),
    val minutesDialStyle: DialStyle = DialStyle(),
    val hourLabelStyle: TextStyle = TextStyle(),
    val overlayStrokeWidth: Dp = 2.dp,
    val overlayStrokeColor: Color = Color.Red
)


val StandardClockStyle = ClockStyle(
    secondsDialStyle = DialStyle().copy(
        stepsTextStyle = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = WorkSans
        ),
        stepsColor = Color.White.copy(alpha = 0.8f),
        stepsLabelTopPadding = 20.dp
    ),
    minutesDialStyle = DialStyle().copy(
        stepsTextStyle = TextStyle(
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = WorkSans
        ),
        stepsColor = Color.White.copy(alpha = 0.8f),
        stepsLabelTopPadding = 20.dp
    ),
    hourLabelStyle = TextStyle(
        color = Color.White,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = WorkSans
    ),
    overlayStrokeColor = Color.White,
    overlayStrokeWidth = 2.dp
)




val MarronStyle = ClockStyle(
    secondsDialStyle = DialStyle().copy(
        stepsTextStyle = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = WorkSans
        ),
        stepsColor = Marron2,
        stepsLabelTopPadding = 20.dp
    ),
    minutesDialStyle = DialStyle().copy(
        stepsTextStyle = TextStyle(
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = WorkSans
        ),
        stepsColor = Marron2,
        stepsLabelTopPadding = 20.dp
    ),
    hourLabelStyle = TextStyle(
        color = Color.White,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = WorkSans
    ),
    overlayStrokeColor = Color.White,
    overlayStrokeWidth = 2.dp
)



val lightStyle = ClockStyle(
    secondsDialStyle = DialStyle().copy(
        stepsTextStyle = TextStyle(
            color = Color.Black.copy(alpha = 0.8f),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = WorkSans
        ),
        stepsColor = Color.Black.copy(alpha = 0.5f),
        stepsLabelTopPadding = 20.dp
    ),
    minutesDialStyle = DialStyle().copy(
        stepsTextStyle = TextStyle(
            color = Color.Black.copy(alpha = 0.8f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = WorkSans
        ),
        stepsColor = Color.Black.copy(alpha = 0.5f),
        stepsLabelTopPadding = 20.dp
    ),
    hourLabelStyle = TextStyle(
        color = Color.Black,
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = WorkSans
    ),
    overlayStrokeColor = Color.Black,
    overlayStrokeWidth = 2.dp
)



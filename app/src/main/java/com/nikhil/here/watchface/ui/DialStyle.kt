package com.nikhil.here.watchface.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DialStyle(
    val stepsWidth: Dp = 1.2.dp,
    val stepsColor: Color = Color.Black,
    val normalStepsLineHeight: Dp = 8.dp,
    val fiveStepsLineHeight: Dp = 16.dp,
    val stepsTextStyle: TextStyle = TextStyle(),
    val stepsLabelTopPadding: Dp = 12.dp,
)

data class ClockStyle(
    val secondsDialStyle  : DialStyle = DialStyle(),
    val minutesDialStyle  : DialStyle = DialStyle(),
    val hourLabelStyle : TextStyle = TextStyle(),
    val overlayStrokeWidth : Dp = 2.dp,
    val overlayStrokeColor : Color = Color.Red
)
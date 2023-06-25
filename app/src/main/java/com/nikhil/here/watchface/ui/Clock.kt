package com.nikhil.here.watchface.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

private const val TAG = "Clock"

@OptIn(ExperimentalTextApi::class)
@Composable
fun Clock(
    modifier: Modifier,
    clockStyle: ClockStyle = ClockStyle()
) {
    val textMeasurer = rememberTextMeasurer()

    var minuteRotation by remember { mutableStateOf(0f) }
    var secondRotation by remember { mutableStateOf(0f) }
    var hour by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = true) {
        val calender = Calendar.getInstance()
        val currentHour = calender.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calender.get(Calendar.MINUTE)
        val currentSecond = calender.get(Calendar.SECOND)
        secondRotation = -(currentSecond) * 6f
        minuteRotation = -(currentMinute) * 6f
        hour = currentHour
    }

    //secondRotation is updated by 6 degree clockwise every one second
    //here rotation is in negative, in order to get clockwise rotation
    LaunchedEffect(key1 = true) {
        while (true) {
            //in-order to get smooth transition we are updating rotation angle every 16ms
            //1000ms -> 6 degree
            //16ms -> 0.084
            delay(16)
            secondRotation -= 0.084f
        }
    }

    //minuteRotation is updated by 0.1 degree clockwise every one second
    //here rotation is in negative, in order to get clockwise rotation
    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            minuteRotation -= 0.1f
        }
    }

    //this will update hour label after every one hour
    LaunchedEffect(key1 = true) {
        while (true) {
            delay(60 * 60 * 1000L)
            hour = (hour + 1) % 24
        }
    }

    Canvas(
        modifier = modifier
    ) {
        val outerRadius = minOf(this.size.width, this.size.height) / 2f
        val innerRadius = outerRadius - 60.dp.toPx()

        //Seconds Dial
        dial(
            radius = outerRadius,
            rotation = secondRotation,
            textMeasurer = textMeasurer,
            dialStyle = clockStyle.secondsDialStyle
        )

        //Minute Dial
        dial(
            radius = innerRadius,
            rotation = minuteRotation,
            textMeasurer = textMeasurer,
            dialStyle = clockStyle.minutesDialStyle
        )

        //draw hour
        val hourString = String.format("%02d", hour)
        val hourTextMeasureOutput = textMeasurer.measure(
            text = buildAnnotatedString { append(hourString) },
            style = clockStyle.hourLabelStyle
        )
        val hourTopLeft = Offset(
            x = this.center.x - (hourTextMeasureOutput.size.width / 2),
            y = this.center.y - (hourTextMeasureOutput.size.height / 2),
        )
        drawText(
            textMeasurer = textMeasurer,
            text = hourString,
            topLeft = hourTopLeft,
            style = clockStyle.hourLabelStyle
        )

        //drawing minute-second overlay
        val minuteHandOverlayPath = Path().apply {
            val startOffset = Offset(
                x = center.x + (outerRadius * cos(8f * Math.PI / 180f)).toFloat(),
                y = center.y - (outerRadius * sin(8f * Math.PI / 180f)).toFloat(),
            )
            val endOffset = Offset(
                x = center.x + (outerRadius * cos(-8f * Math.PI / 180f)).toFloat(),
                y = center.y - (outerRadius * sin(-8f * Math.PI / 180f)).toFloat(),
            )
            val overlayRadius = (endOffset.y - startOffset.y) / 2f

            val secondsLabelMaxWidth = textMeasurer.measure(
                text = buildAnnotatedString { append("60") },
                style = clockStyle.secondsDialStyle.stepsTextStyle
            ).size.width

            val minutesLabelMaxWidth = textMeasurer.measure(
                text = buildAnnotatedString { append("60") },
                style = clockStyle.minutesDialStyle.stepsTextStyle
            ).size.width

            val overlayLineX =
                size.width - clockStyle.secondsDialStyle.fiveStepsLineHeight.toPx() - clockStyle.secondsDialStyle.stepsLabelTopPadding.toPx() - secondsLabelMaxWidth - clockStyle.minutesDialStyle.fiveStepsLineHeight.toPx() - clockStyle.minutesDialStyle.stepsLabelTopPadding.toPx() - (minutesLabelMaxWidth /2f)
            moveTo(x = startOffset.x, y = startOffset.y)
            lineTo(x = overlayLineX, y = startOffset.y)
            cubicTo(
                x1 = overlayLineX - overlayRadius,
                y1 = startOffset.y,
                x2 = overlayLineX - overlayRadius,
                y2 = endOffset.y,
                x3 = overlayLineX,
                y3 = endOffset.y
            )
            lineTo(endOffset.x, endOffset.y)
        }

        drawPath(
            path = minuteHandOverlayPath,
            color = clockStyle.overlayStrokeColor,
            style = Stroke(width = clockStyle.overlayStrokeWidth.toPx(),)
        )
    }
}

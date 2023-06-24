package com.nikhil.here.watchface.ui

import android.util.Log
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

private const val TAG = "Clock"

@OptIn(ExperimentalTextApi::class)
@Composable
fun Clock() {
    val textMeasurer = rememberTextMeasurer()
    var minuteRotation by remember {
        mutableStateOf(0f)
    }
    var secondRotation by remember {
        mutableStateOf(0f)
    }

    var hour by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        val calender = Calendar.getInstance()
        val currentHour = calender.get(Calendar.HOUR)
        val currentMinute = calender.get(Calendar.MINUTE)
        val currentSecond = calender.get(Calendar.SECOND)
        secondRotation =  -(currentSecond) * 6f
        minuteRotation = -(currentMinute) * 6f
        hour = currentHour
    }


    LaunchedEffect(key1 = true) {
        while (true) {
            delay(14)
            secondRotation -= 0.084f
        }
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            minuteRotation -= 0.1f
        }
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(60 * 60 * 1000L)
            hour = (hour + 1) % 24
        }
    }

    Canvas(
        modifier = Modifier
            .size(340.dp)
    ) {

        val outerRadius = minOf(this.size.width, this.size.height) / 2f
        val innerRadius = outerRadius - 42.dp.toPx()

        //Seconds Dial
        Dial(radius = outerRadius, rotation = secondRotation, textMeasurer = textMeasurer)

        //Minute Dial
        Dial(radius = innerRadius, rotation = minuteRotation, textMeasurer = textMeasurer)

        //draw hour
        val hourString = String.format("%02d", hour)
        val hourTextMeasureOutput = textMeasurer.measure(
            buildAnnotatedString { append(hourString) },
            style = TextStyle(fontSize = 56.sp, fontWeight = FontWeight.Bold)
        )
        val hourTopLeft = Offset(
            x = this.center.x - (hourTextMeasureOutput.size.width / 2),
            y = this.center.y - (hourTextMeasureOutput.size.height / 2),
        )
        drawText(
            textMeasurer = textMeasurer,
            text = hourString,
            topLeft = hourTopLeft,
            style = TextStyle(
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold
            )
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
            val overlayLineX =
                center.x + (hourTextMeasureOutput.size.width / 2f) + (overlayRadius * 2.5).toFloat()
            moveTo(x = startOffset.x, y = startOffset.y)
            lineTo(
                x = overlayLineX,
                y = startOffset.y
            )
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
            minuteHandOverlayPath,
            color = Color.Red,
            style = Stroke(
                width = 4f,
                join = StrokeJoin.Round,
                cap = StrokeCap.Round
            )
        )
    }
}


@OptIn(ExperimentalTextApi::class)
fun DrawScope.Dial(
    radius : Float,
    rotation : Float,
    textMeasurer: TextMeasurer,
    logComposition : Boolean = false
) {

    var stepsAngle = 0

    if (logComposition) {
        Log.i(TAG, "Dial: ")
    }

    //draw minute dial
    drawCircle(
        color = Color.Black,
        radius = radius,
        center = center,
        style = Stroke(width = 1f)
    )

    repeat(60) { handIndex ->
        val stepsHeight = if (handIndex % 5 == 0) {
            16.dp.toPx()
        } else {
            8.dp.toPx()
        }

        val secondsStepStartOffsetV2 = Offset(
            x = center.x + (radius * cos((stepsAngle + rotation) * (Math.PI / 180))).toFloat(),
            y = center.y - (radius * sin((stepsAngle + rotation) * (Math.PI / 180))).toFloat()
        )

        val secondsStepsEndOffsetV2 = Offset(
            x = center.x + (radius - stepsHeight) * cos(
                (stepsAngle + rotation) * (Math.PI / 180)
            ).toFloat(),
            y = center.y - (radius - stepsHeight) * sin(
                (stepsAngle + rotation) * (Math.PI / 180)
            ).toFloat()
        )

        drawLine(
            color = Color.Black,
            start = secondsStepStartOffsetV2,
            end = secondsStepsEndOffsetV2,
            strokeWidth = 1f
        )

        if (handIndex % 5 == 0) {

            val secondsLabelV2 = String.format("%02d", handIndex)

            val output =
                textMeasurer.measure(buildAnnotatedString {
                    append(
                        secondsLabelV2
                    )
                })

            val secondsLabelOffsetV2 = Offset(
                x = center.x + (radius - stepsHeight - 12.dp.toPx()) * cos(
                    (stepsAngle + rotation) * (Math.PI / 180)
                ).toFloat(),
                y = center.y - (radius - stepsHeight - 12.dp.toPx()) * sin(
                    (stepsAngle + rotation) * (Math.PI / 180)
                ).toFloat()
            )

            val topLeft = Offset(
                secondsLabelOffsetV2.x - ((output.size.width) / 2f),
                secondsLabelOffsetV2.y - (output.size.height / 2f)
            )

            drawText(
                textMeasurer = textMeasurer,
                text = secondsLabelV2,
                topLeft = topLeft
            )
        }
        stepsAngle += 6
    }
}
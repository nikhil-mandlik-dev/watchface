package com.nikhil.here.watchface.ui.theme

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin


private const val TAG = "Dial"

/**
 * Dial
 * This composable is not a part of main file,
 * its created in order to test the recomposition issues
 *
 * @param modifier
 * @param radius
 * @param rotation
 * @param logComposition
 * @receiver
 * @receiver
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun Dial(
    modifier: Modifier,
    radius: DrawScope.() -> Float,
    rotation: () -> Float,
    logComposition: Boolean = false
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier) {
        val outerRadius = radius()
        var secondHandAngleV2 = 0
        if (logComposition) {
            Log.i(TAG, "Dial: radius $outerRadius ")
        }

        //draw minute dial
        drawCircle(
            color = Color.Black,
            radius = outerRadius,
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
                x = center.x + (outerRadius * cos((secondHandAngleV2 + rotation()) * (Math.PI / 180))).toFloat(),
                y = center.y - (outerRadius * sin((secondHandAngleV2 + rotation()) * (Math.PI / 180))).toFloat()
            )

            val secondsStepsEndOffsetV2 = Offset(
                x = center.x + (outerRadius - stepsHeight) * cos(
                    (secondHandAngleV2 + rotation()) * (Math.PI / 180)
                ).toFloat(),
                y = center.y - (outerRadius - stepsHeight) * sin(
                    (secondHandAngleV2 + rotation()) * (Math.PI / 180)
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
                    x = center.x + (outerRadius - stepsHeight - 12.dp.toPx()) * cos(
                        (secondHandAngleV2 + rotation()) * (Math.PI / 180)
                    ).toFloat(),
                    y = center.y - (outerRadius - stepsHeight - 12.dp.toPx()) * sin(
                        (secondHandAngleV2 + rotation()) * (Math.PI / 180)
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
            secondHandAngleV2 += 6
        }
    }
}
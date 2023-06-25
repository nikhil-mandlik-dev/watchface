package com.nikhil.here.watchface.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import kotlin.math.cos
import kotlin.math.sin


@OptIn(ExperimentalTextApi::class)
fun DrawScope.dial(
    radius: Float,
    rotation: Float,
    textMeasurer: TextMeasurer,
    dialStyle: DialStyle = DialStyle()
) {
    var stepsAngle = 0

    //this will draw 60 steps
    repeat(60) { steps ->

        //fiveStep lineHeight > normalStep lineHeight
        val stepsHeight = if (steps % 5 == 0) {
            dialStyle.fiveStepsLineHeight.toPx()
        } else {
            dialStyle.normalStepsLineHeight.toPx()
        }

        //calculate steps, start and end offset
        val stepsStartOffset = Offset(
            x = center.x + (radius * cos((stepsAngle + rotation) * (Math.PI / 180f))).toFloat(),
            y = center.y - (radius * sin((stepsAngle + rotation) * (Math.PI / 180))).toFloat()
        )
        val stepsEndOffset = Offset(
            x = center.x + (radius - stepsHeight) * cos(
                (stepsAngle + rotation) * (Math.PI / 180)
            ).toFloat(),
            y = center.y - (radius - stepsHeight) * sin(
                (stepsAngle + rotation) * (Math.PI / 180)
            ).toFloat()
        )

        //draw step
        drawLine(
            color = dialStyle.stepsColor,
            start = stepsStartOffset,
            end = stepsEndOffset,
            strokeWidth = dialStyle.stepsWidth.toPx(),
            cap = StrokeCap.Round
        )

        //draw steps labels
        if (steps % 5 == 0) {
            //measure the given label width and height
            val stepsLabel = String.format("%02d", steps)
            val stepsLabelTextLayout = textMeasurer.measure(
                text = buildAnnotatedString { append(stepsLabel) },
                style = dialStyle.stepsTextStyle
            )

            //calculate the offset
            val stepsLabelOffset = Offset(
                x = center.x + (radius - stepsHeight - dialStyle.stepsLabelTopPadding.toPx()) * cos(
                    (stepsAngle + rotation) * (Math.PI / 180)
                ).toFloat(),
                y = center.y - (radius - stepsHeight - dialStyle.stepsLabelTopPadding.toPx()) * sin(
                    (stepsAngle + rotation) * (Math.PI / 180)
                ).toFloat()
            )

            //subtract the label width and height to position label at the center of the step
            val stepsLabelTopLeft = Offset(
                stepsLabelOffset.x - ((stepsLabelTextLayout.size.width) / 2f),
                stepsLabelOffset.y - (stepsLabelTextLayout.size.height / 2f)
            )

            drawText(
                textMeasurer = textMeasurer,
                text = stepsLabel,
                topLeft = stepsLabelTopLeft,
                style = dialStyle.stepsTextStyle
            )
        }
        stepsAngle += 6
    }
}
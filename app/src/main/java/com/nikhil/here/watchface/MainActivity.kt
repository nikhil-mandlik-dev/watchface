package com.nikhil.here.watchface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhil.here.watchface.ui.theme.WatchfaceTheme
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin


class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    @OptIn(ExperimentalTextApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchfaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
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
                                delay(100)
                                secondRotation -= 0.6f
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

                            //draw minute dial
                            drawCircle(
                                color = Color.Black,
                                radius = outerRadius,
                                center = center,
                                style = Stroke(width = 1f)
                            )

                            var secondHandAngleV2 = 0
                            repeat(60) { handIndex ->

                                val stepsHeight = if (handIndex % 5 == 0) {
                                    16.dp.toPx()
                                } else {
                                    8.dp.toPx()
                                }

                                val secondsStepStartOffsetV2 = Offset(
                                    x = center.x + (outerRadius * cos((secondHandAngleV2 + secondRotation) * (Math.PI / 180))).toFloat(),
                                    y = center.y - (outerRadius * sin((secondHandAngleV2 + secondRotation) * (Math.PI / 180))).toFloat()
                                )

                                val secondsStepsEndOffsetV2 = Offset(
                                    x = center.x + (outerRadius - stepsHeight) * cos(
                                        (secondHandAngleV2 + secondRotation) * (Math.PI / 180)
                                    ).toFloat(),
                                    y = center.y - (outerRadius - stepsHeight) * sin(
                                        (secondHandAngleV2 + secondRotation) * (Math.PI / 180)
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
                                            (secondHandAngleV2 + secondRotation) * (Math.PI / 180)
                                        ).toFloat(),
                                        y = center.y - (outerRadius - stepsHeight - 12.dp.toPx()) * sin(
                                            (secondHandAngleV2 + secondRotation) * (Math.PI / 180)
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


                            //draw minute dial
                            drawCircle(
                                color = Color.Black,
                                radius = innerRadius,
                                center = center,
                                style = Stroke(width = 1f)
                            )

                            var minuteHandAngle = 0
                            repeat(60) { handIndex ->
                                val stepsHeight = if (handIndex % 5 == 0) {
                                    16.dp.toPx()
                                } else {
                                    8.dp.toPx()
                                }

                                val minuteStepsStartOffset = Offset(
                                    x = center.x + (innerRadius * cos((minuteHandAngle + minuteRotation) * (Math.PI / 180))).toFloat(),
                                    y = center.y - (innerRadius * sin((minuteHandAngle + minuteRotation) * (Math.PI / 180))).toFloat()
                                )

                                val minuteStepsEndOffset = Offset(
                                    x = center.x + (innerRadius - stepsHeight) * cos(
                                        (minuteHandAngle + minuteRotation) * (Math.PI / 180)
                                    ).toFloat(),
                                    y = center.y - (innerRadius - stepsHeight) * sin(
                                        (minuteHandAngle + minuteRotation) * (Math.PI / 180)
                                    ).toFloat()
                                )

                                drawLine(
                                    color = Color.Black,
                                    start = minuteStepsStartOffset,
                                    end = minuteStepsEndOffset,
                                    strokeWidth = 1f
                                )

                                if (handIndex % 5 == 0) {

                                    val minuteLabel = String.format("%02d", handIndex)

                                    val output =
                                        textMeasurer.measure(buildAnnotatedString {
                                            append(
                                                minuteLabel
                                            )
                                        })

                                    val minuteLabelOffset = Offset(
                                        x = center.x + (innerRadius - stepsHeight - 12.dp.toPx()) * cos(
                                            (minuteHandAngle + minuteRotation) * (Math.PI / 180)
                                        ).toFloat(),
                                        y = center.y - (innerRadius - stepsHeight - 12.dp.toPx()) * sin(
                                            (minuteHandAngle + minuteRotation) * (Math.PI / 180)
                                        ).toFloat()
                                    )

                                    val topLeft = Offset(
                                        minuteLabelOffset.x - ((output.size.width) / 2f),
                                        minuteLabelOffset.y - (output.size.height / 2f)
                                    )

                                    drawText(
                                        textMeasurer = textMeasurer,
                                        text = minuteLabel,
                                        topLeft = topLeft
                                    )
                                }
                                minuteHandAngle += 6
                            }


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
                }
            }
        }
    }
}
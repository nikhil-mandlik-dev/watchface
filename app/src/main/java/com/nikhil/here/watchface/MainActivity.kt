package com.nikhil.here.watchface

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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


                        Canvas(
                            modifier = Modifier
                                .size(340.dp)
                                .border(1.dp, color = Color.Red)
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

                            var minuteHandAngle = 0
                            repeat(60) { handIndex ->

                                val stepsHeight = if (handIndex % 5 == 0) {
                                    16.dp.toPx()
                                } else {
                                    8.dp.toPx()
                                }

                                val minuteStepsStartOffset = Offset(
                                    x = center.x + (outerRadius * cos(minuteHandAngle * (Math.PI / 180))).toFloat(),
                                    y = center.y - (outerRadius * sin(minuteHandAngle * (Math.PI / 180))).toFloat()
                                )

                                val minuteStepsEndOffset = Offset(
                                    x = center.x + (outerRadius - stepsHeight) * cos(
                                        minuteHandAngle * (Math.PI / 180)
                                    ).toFloat(),
                                    y = center.y - (outerRadius - stepsHeight) * sin(
                                        minuteHandAngle * (Math.PI / 180)
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

                                    val minutesLabelOffset = Offset(
                                        x = center.x + (outerRadius - stepsHeight - 12.dp.toPx()) * cos(
                                            minuteHandAngle * (Math.PI / 180)
                                        ).toFloat(),
                                        y = center.y - (outerRadius - stepsHeight - 12.dp.toPx()) * sin(
                                            minuteHandAngle * (Math.PI / 180)
                                        ).toFloat()
                                    )

                                    val topLeft = Offset(
                                        minutesLabelOffset.x - ((output.size.width) / 2f),
                                        minutesLabelOffset.y - (output.size.height / 2f)
                                    )

                                    drawText(
                                        textMeasurer = textMeasurer,
                                        text = minuteLabel,
                                        topLeft = topLeft
                                    )
                                }
                                minuteHandAngle += 6
                            }


                            //draw second dial
                            drawCircle(
                                color = Color.Black,
                                radius = innerRadius,
                                center = center,
                                style = Stroke(width = 1f)
                            )

                            var secondHandAngle = 0
                            repeat(60) { handIndex ->

                                val stepsHeight = if (handIndex % 5 == 0) {
                                    16.dp.toPx()
                                } else {
                                    8.dp.toPx()
                                }

                                val secondsStepsStartOffset = Offset(
                                    x = center.x + (innerRadius * cos(secondHandAngle * (Math.PI / 180))).toFloat(),
                                    y = center.y - (innerRadius * sin(secondHandAngle * (Math.PI / 180))).toFloat()
                                )

                                val secondsStepsEndOffset = Offset(
                                    x = center.x + (innerRadius - stepsHeight) * cos(
                                        secondHandAngle * (Math.PI / 180)
                                    ).toFloat(),
                                    y = center.y - (innerRadius - stepsHeight) * sin(
                                        secondHandAngle * (Math.PI / 180)
                                    ).toFloat()
                                )

                                drawLine(
                                    color = Color.Black,
                                    start = secondsStepsStartOffset,
                                    end = secondsStepsEndOffset,
                                    strokeWidth = 1f
                                )

                                if (handIndex % 5 == 0) {

                                    val secondLabel = String.format("%02d", handIndex)

                                    val output =
                                        textMeasurer.measure(buildAnnotatedString {
                                            append(
                                                secondLabel
                                            )
                                        })

                                    val secondsLabelOffset = Offset(
                                        x = center.x + (innerRadius - stepsHeight - 12.dp.toPx()) * cos(
                                            secondHandAngle * (Math.PI / 180)
                                        ).toFloat(),
                                        y = center.y - (innerRadius - stepsHeight - 12.dp.toPx()) * sin(
                                            secondHandAngle * (Math.PI / 180)
                                        ).toFloat()
                                    )

                                    val topLeft = Offset(
                                        secondsLabelOffset.x - ((output.size.width) / 2f),
                                        secondsLabelOffset.y - (output.size.height / 2f)
                                    )

                                    drawText(
                                        textMeasurer = textMeasurer,
                                        text = secondLabel,
                                        topLeft = topLeft
                                    )
                                }
                                secondHandAngle += 6
                            }


                            //draw hour
                            val hourTextMeasureOutput = textMeasurer.measure(
                                buildAnnotatedString { append("12") },
                                style = TextStyle(fontSize = 56.sp, fontWeight = FontWeight.Bold)
                            )
                            val hourTopLeft = Offset(
                                x = this.center.x - (hourTextMeasureOutput.size.width / 2),
                                y = this.center.y - (hourTextMeasureOutput.size.height / 2),
                            )
                            drawText(
                                textMeasurer = textMeasurer,
                                text = "12",
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
                                    center.x + (hourTextMeasureOutput.size.width / 2f) + (overlayRadius * 2)
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
                                style = Stroke(width = 4f, join = StrokeJoin.Round, cap = StrokeCap.Round)
                            )


                        }
                    }
                }
            }
        }
    }
}
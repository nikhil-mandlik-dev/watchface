package com.nikhil.here.watchface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhil.here.watchface.ui.Clock
import com.nikhil.here.watchface.ui.ClockStyle
import com.nikhil.here.watchface.ui.DialStyle
import com.nikhil.here.watchface.ui.theme.WatchfaceTheme
import com.nikhil.here.watchface.ui.theme.WorkSans


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
                    color = Color.Black
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Clock(
                            modifier = Modifier
                                .size(360.dp),
                            clockStyle = ClockStyle(
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
                        )
                    }
                }
            }
        }
    }
}
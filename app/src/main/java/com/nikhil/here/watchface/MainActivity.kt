package com.nikhil.here.watchface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.nikhil.here.watchface.ui.Clock
import com.nikhil.here.watchface.ui.MarronStyle
import com.nikhil.here.watchface.ui.StandardClockStyle
import com.nikhil.here.watchface.ui.lightStyle
import com.nikhil.here.watchface.ui.theme.Marron
import com.nikhil.here.watchface.ui.theme.WatchfaceTheme


class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    @OptIn(ExperimentalTextApi::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchfaceTheme {
                // A surface container using the 'background' color from the theme

                HorizontalPager(
                    pageCount = 3,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    when(it) {
                        0 -> {
                            Column(
                                modifier = Modifier.fillMaxSize().background(Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Clock(
                                    modifier = Modifier
                                        .size(360.dp),
                                    clockStyle = lightStyle
                                )
                            }

                        }
                        1 -> {
                            Column(
                                modifier = Modifier.fillMaxSize().background(Color.Black),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Clock(
                                    modifier = Modifier
                                        .size(360.dp),
                                    clockStyle = StandardClockStyle
                                )
                            }
                        }
                        2 -> {
                            Column(
                                modifier = Modifier.fillMaxSize().background(Marron),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Clock(
                                    modifier = Modifier
                                        .size(360.dp),
                                    clockStyle = MarronStyle
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}
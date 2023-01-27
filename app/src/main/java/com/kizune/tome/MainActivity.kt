package com.kizune.tome

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kizune.tome.theme.TomeTheme
import com.kizune.tome.ui.BookViewModel
import com.kizune.tome.ui.TomeApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.d("MyTag", "exception", throwable)
        }
        super.onCreate(savedInstanceState)
        setContent {
            TomeTheme {
                val widthSize = calculateWindowSizeClass(activity = this).widthSizeClass
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(
                    color = when (widthSize) {
                        WindowWidthSizeClass.Compact -> MaterialTheme.colorScheme.background
                        WindowWidthSizeClass.Medium -> colorResource(id = R.color.surface2)
                        WindowWidthSizeClass.Expanded -> colorResource(id = R.color.surface1)
                        else -> MaterialTheme.colorScheme.background
                    },
                    darkIcons = !isSystemInDarkTheme()
                )
                val viewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TomeApp(
                        widthSize = widthSize,
                        viewModel = viewModel,
                        onBackButton = { moveTaskToBack(true) }
                    )
                }
            }
        }
    }
}

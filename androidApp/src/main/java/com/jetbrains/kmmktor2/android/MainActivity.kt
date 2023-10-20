package com.jetbrains.kmmktor2.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jetbrains.kmmktor2.Greeting
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    private val greeting = Greeting()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                var text by remember {
                    mutableStateOf("Loading...")
                }

                Text(text)

                LaunchedEffect(Unit) {
                    runCatching {
                        greeting.greeting()
                    }.onSuccess {
                        text = it
                    }.onFailure {
                        text = it.localizedMessage
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

}

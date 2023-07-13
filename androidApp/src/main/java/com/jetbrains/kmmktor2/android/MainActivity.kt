package com.jetbrains.kmmktor2.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jetbrains.kmmktor2.Greeting
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    private val greeting = Greeting()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = "Loading..."

        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
        mainScope.launch {
            kotlin.runCatching {
                greeting.greeting()
            }.onSuccess {
                tv.text = it
            }.onFailure {
                tv.text = it.localizedMessage
            }

            Toast.makeText(this@MainActivity, "hello2", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

}

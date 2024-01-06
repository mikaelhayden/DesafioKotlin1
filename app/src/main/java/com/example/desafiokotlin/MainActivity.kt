package com.example.desafiokotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var ObserverConnectivity: observerConnectivity

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ObserverConnectivity = NetworkConnectivityObserver(applicationContext)
        ObserverConnectivity.observe().onEach {
            println("Status is $it")
        }.launchIn(lifecycleScope)
        setContent {
            val status by ObserverConnectivity.observe().collectAsState(initial = observerConnectivity.Status.Unavailable)
                Box(
                    modifier =   Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Alguma coisa $status")
                }
            }
        }
    }

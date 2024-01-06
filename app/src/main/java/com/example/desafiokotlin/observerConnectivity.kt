package com.example.desafiokotlin
import kotlinx.coroutines.flow.Flow
import javax.net.ssl.SSLEngineResult.Status

interface observerConnectivity {
    fun observe(): Flow<Status>
    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}

package com.example.desafiokotlin

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(
    private val context: Context
): observerConnectivity{

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    override fun observe(): Flow<observerConnectivity.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(observerConnectivity.Status.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(observerConnectivity.Status.Losing)}
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(observerConnectivity.Status.Lost)}
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(observerConnectivity.Status.Unavailable)}
                }
            }
            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

}
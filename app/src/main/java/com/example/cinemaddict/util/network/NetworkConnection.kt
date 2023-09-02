package com.example.cinemaddict.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectionImpl @Inject constructor(
    @ApplicationContext context: Context
) : LiveNetworkConnection, NetworkConnection {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isConnected = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isConnected = false
        }
    }

    init {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    override val isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable

    override var isConnected: Boolean = false
        private set(value) {
            _isNetworkAvailable.postValue(value)
            field = value
        }
}
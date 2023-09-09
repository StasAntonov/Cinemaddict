package com.example.cinemaddict.util.network

import androidx.lifecycle.LiveData

interface NetworkConnection {
    val isConnected: Boolean
}

interface LiveNetworkConnection {
    val isNetworkAvailable: LiveData<Boolean>
}
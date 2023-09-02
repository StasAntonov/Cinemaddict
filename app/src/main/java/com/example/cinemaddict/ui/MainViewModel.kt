package com.example.cinemaddict.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinemaddict.util.network.LiveNetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    network: LiveNetworkConnection
) : ViewModel() {

    val isNetworkAvailable: LiveData<Boolean> = network.isNetworkAvailable
}
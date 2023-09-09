package com.example.cinemaddict.ui

import androidx.lifecycle.LiveData

interface MainViewModel {
    val isNetworkAvailable: LiveData<Boolean>
}
package com.example.cinemaddict.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface ViewModelHandler {
    val isRefresh: LiveData<Boolean>
}

abstract class BaseViewModel : ViewModel(), ViewModelHandler {
    override val isRefresh = MutableLiveData(false)
}
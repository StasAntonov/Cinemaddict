package com.example.cinemaddict.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface ViewModelHandler {
    val isRefresh: LiveData<Boolean>
}

abstract class BaseViewModel : ViewModel(), ViewModelHandler {
    override val isRefresh = MutableLiveData(false)
}

@HiltViewModel
class DiscoverViewModelImpl @Inject constructor() : BaseViewModel() {

    val count = MutableLiveData("1")

    fun refresh(num: Int) {
        count.postValue(num.plus(1).toString())
    }
}
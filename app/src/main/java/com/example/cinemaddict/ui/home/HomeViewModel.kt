package com.example.cinemaddict.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.cinemaddict.ui.discover.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel() {

    val count = MutableLiveData("1")

    fun refresh(num: Int) {
        count.postValue(num.plus(1).toString())
    }
}
package com.example.cinemaddict.common

typealias PullToRefreshCallback = suspend () -> Unit
interface PullToRefreshListener {
    fun setOnRefreshListener(listener: PullToRefreshCallback?)
}
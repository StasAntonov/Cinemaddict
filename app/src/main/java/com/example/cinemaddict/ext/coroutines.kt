package com.example.cinemaddict.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ioScope(block: suspend CoroutineScope.() -> Unit): Job =
    CoroutineScope(Dispatchers.IO).launch(block = block)

fun mainScope(block: suspend CoroutineScope.() -> Unit): Job =
    CoroutineScope(Dispatchers.Main).launch(block = block)

fun defaultScope(block: suspend CoroutineScope.() -> Unit): Job =
    CoroutineScope(Dispatchers.Default).launch(block = block)
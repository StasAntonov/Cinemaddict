package com.example.cinemaddict.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val throwable: Throwable) : ApiResponse<T>()
}

@Parcelize
data class ErrorResponse(
    val errors: List<String>
) : Parcelable

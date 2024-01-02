package com.example.cinemaddict.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val throwable: Throwable) : ApiResponse<T>()
}

fun <IN, OUT> ApiResponse<IN>.map(transform: (IN) -> OUT): ApiResponse<OUT> {
    return when (this) {
        is ApiResponse.Error -> ApiResponse.Error(throwable = this.throwable)
        is ApiResponse.Success -> ApiResponse.Success(data = transform.invoke(this.data))
    }
}

@Parcelize
data class ErrorResponse(
    val errors: List<String>
) : Parcelable

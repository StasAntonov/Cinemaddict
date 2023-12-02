package com.example.cinemaddict.common.paging

import com.google.gson.annotations.SerializedName

data class MovPagingResponseWrapper<T : MovPagingResponse>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<T>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

abstract class MovPagingResponse(
    val id: Int = -1
)
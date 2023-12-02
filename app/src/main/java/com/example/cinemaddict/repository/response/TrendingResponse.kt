package com.example.cinemaddict.repository.response

import android.os.Parcelable
import com.example.cinemaddict.common.paging.MovPagingResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrendingResponse(
    val title: String,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Float
) : MovPagingResponse(), Parcelable
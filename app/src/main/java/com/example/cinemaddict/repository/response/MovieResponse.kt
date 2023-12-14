package com.example.cinemaddict.repository.response

import android.os.Parcelable
import com.example.cinemaddict.common.paging.MovPagingResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("genre_ids") val genreIds: ArrayList<Int> = arrayListOf(),
//    override val id: Int,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    val title: String,
    val video: Boolean? = null
) : Parcelable, MovPagingResponse()
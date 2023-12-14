package com.example.cinemaddict.repository.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieListResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val totalPages: Int
) : Parcelable

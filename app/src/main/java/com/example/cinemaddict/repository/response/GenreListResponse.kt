package com.example.cinemaddict.repository.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreListResponse(
    val genres: List<GenreResponse>
) : Parcelable
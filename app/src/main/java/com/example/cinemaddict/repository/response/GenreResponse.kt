package com.example.cinemaddict.repository.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreResponse(
    val id: Int,
    val name: String
) : Parcelable
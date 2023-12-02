package com.example.cinemaddict.domain.entity

import com.example.cinemaddict.common.paging.MovPagingData

data class TrendingMovieData(
    override val id: Int,
    val imageUrl: String?,
    val title: String,
    val rate: Float
) : MovPagingData() {
    val testText: String
        get() = "$id - $title"
}
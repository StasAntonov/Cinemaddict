package com.example.cinemaddict.domain.entity

import com.example.cinemaddict.common.paging.MovPagingData

data class TrendingMovieData(
    val imageUrl: String,
    val title: String,
    val rate: Float
): MovPagingData()
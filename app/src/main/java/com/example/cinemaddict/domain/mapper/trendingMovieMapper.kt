package com.example.cinemaddict.domain.mapper

import com.example.cinemaddict.domain.entity.TrendingMovieData
import com.example.cinemaddict.repository.response.TrendingResponse

fun TrendingResponse.toTrendingData() = TrendingMovieData(
    id = id,
    imageUrl = backdropPath,
    title = title,
    rate = voteAverage
)
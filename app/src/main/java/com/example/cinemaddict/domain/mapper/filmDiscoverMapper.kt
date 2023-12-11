package com.example.cinemaddict.domain.mapper

import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.repository.response.MovieResponse
import com.example.cinemaddict.util.DateTimeUtils.toLocalDate

fun MovieResponse.toFilmDiscoverData() = FilmDiscoverData(
    title = title,
    releaseDate = toLocalDate(releaseDate),
    poster = backdropPath
)
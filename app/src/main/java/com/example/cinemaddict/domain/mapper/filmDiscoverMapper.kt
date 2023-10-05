package com.example.cinemaddict.domain.mapper

import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.repository.response.MovieResponse

fun MovieResponse.toFilmDiscoverData() = FilmDiscoverData(
    title = title,
    releaseDate = releaseDate,
    poster = backdropPath
)
package com.example.cinemaddict.domain.mapper

import com.example.cinemaddict.domain.entity.GenreData
import com.example.cinemaddict.repository.response.GenreResponse

fun GenreResponse.toGenreData() = GenreData(
    id = id,
    name = name
)
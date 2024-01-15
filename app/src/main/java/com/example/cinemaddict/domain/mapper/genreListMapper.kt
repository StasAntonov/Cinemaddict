package com.example.cinemaddict.domain.mapper

import com.example.cinemaddict.domain.entity.GenreData
import com.example.cinemaddict.domain.entity.GenreList
import com.example.cinemaddict.repository.response.GenreListResponse
import com.example.cinemaddict.repository.response.GenreResponse

fun GenreListResponse.toGenreList(
    transform: (List<GenreResponse>) -> List<GenreData>
) = GenreList(
    genres = transform.invoke(genres)
)